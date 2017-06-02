package xinxing.boss.admin.boss.other.cmd;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import xinxing.boss.admin.boss.other.domain.BossGdDataAnalyze;
import xinxing.boss.admin.boss.other.service.BossGdDataAnalyzeService;
import xinxing.boss.admin.common.excel.ExcelUtils;
import xinxing.boss.admin.common.persistence.Page;
import xinxing.boss.admin.common.persistence.PropertyFilter;
import xinxing.boss.admin.common.utils.http.HttpUtils;
import xinxing.boss.admin.common.utils.json.JsonUtils;
import xinxing.boss.admin.common.utils.parameter.ParameterListener;
import xinxing.boss.admin.common.utils.string.StringUtils;
import xinxing.boss.admin.common.utils.time.DateUtils;
import xinxing.boss.admin.common.web.BaseController;

@Controller
@RequestMapping(value = "boss/bossGdDataAnalyze")
public class BossGdDataAnalyzeCmd extends BaseController {
	private static Logger log = LoggerFactory.getLogger(BossGdDataAnalyzeCmd.class);
	@Autowired
	private BossGdDataAnalyzeService bossGdDataAnalyzeService;

	// 弹出框的方法
	@RequestMapping(value = "alertWindow")
	@ResponseBody
	public String alertWindow(HttpServletRequest req, BossGdDataAnalyze bossGdDataAnalyze, Integer[] ids, String method) {
		Map<String, String> reqParams = HttpUtils.getReqParams(req);
		if (reqParams == null) {
			reqParams = new HashMap<String, String>();
		}
		log.info("reqParams:" + JsonUtils.obj2Json(reqParams));
		return "success";
	}

	// 获取数据到页面
	@RequestMapping(value = "getPicMap")
	@ResponseBody
	public Map<String, Object> getPicMap(HttpServletRequest req) {

		String filter_LIKES_city = req.getParameter("filter_INS_city");
		String filter_INI_area = req.getParameter("filter_INI_area");

		String type = "2";
		// String type = req.getParameter("type");
		// <option value="0">成功数</option>
		// <option value="1">成功率</option>
		// <option value="2" selected="selected">总数</option>
		Map<String, Object> map = new HashMap<String, Object>();
		if (StringUtils.isBlank(filter_LIKES_city) || StringUtils.isBlank(filter_INI_area)) {
			map.put("failReason", "城市和使用区域必须填");
			return map;
		}
		Page<BossGdDataAnalyze> page = getPage(req);
		page.setOrder("asc");
		page.setPageSize(50000);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(req);
		Date yesterDay = DateUtils.addDate(new Date(), -1);
		String LED_addTime = DateUtils.getDate(yesterDay, "yyyy-MM-dd") + " 23:59:59";
		String GED_addTime = DateUtils.getDate(DateUtils.addDate(yesterDay, -30), "yyyy-MM-dd") + " 00:00:00";
		// filters.add(new PropertyFilter("GED_addTime", GED_addTime));
		// filters.add(new PropertyFilter("LED_addTime", LED_addTime));
		log.info("--" + JsonUtils.obj2Json(filters));
		page = bossGdDataAnalyzeService.search(page, filters);
		List<BossGdDataAnalyze> result = page.getResult();
		Map<String, List<BossGdDataAnalyze>> cityMap = new HashMap<String, List<BossGdDataAnalyze>>();
		for (BossGdDataAnalyze bossGdDataAnalyze : result) {
			String city = bossGdDataAnalyze.getCity();
			if (cityMap.get(city) == null) {
				cityMap.put(city, new ArrayList<BossGdDataAnalyze>());
			}
			cityMap.get(city).add(bossGdDataAnalyze);
		}
		List<Map<String, Object>> values = new ArrayList<Map<String, Object>>();
		List<String> dates = new ArrayList<String>();
		String title = filter_INI_area;
		int index = 0;
		String[] colors = new String[] { "red", "green", "black", "blue", "#1f7e92", "#CD3278", "#BEBEBE", "#9400D3", "#8B6508", "#7171C6",
				"#4B0082", "#191970" };
		for (Entry<String, List<BossGdDataAnalyze>> entry : cityMap.entrySet()) {
			List<BigDecimal> successNums = new ArrayList<BigDecimal>();
			List<BigDecimal> successRates = new ArrayList<BigDecimal>();
			List<BigDecimal> totalNum = new ArrayList<BigDecimal>();
			List<BossGdDataAnalyze> value = entry.getValue();
			String city = entry.getKey();
			Map<String, Object> indexMap = new HashMap<String, Object>();
			for (BossGdDataAnalyze bossGdDataAnalyze : value) {
				// successNums.add(new BigDecimal(bossGdDataAnalyze.getSuccessNum()));
				// successRates.add(bossGdDataAnalyze.getSuccessRate());
				totalNum.add(new BigDecimal(bossGdDataAnalyze.getTotalNum()));
				Date addTime = bossGdDataAnalyze.getAddTime();
				if (index == 0)
					dates.add(DateUtils.formatDate(addTime, "dd"));
			}
			indexMap.put("name", city);
			if ("0".equals(type)) {
				if (index == 0)
					title = title + "成功数";
				indexMap.put("value", successNums);
			} else if ("1".equals(type)) {
				if (index == 0)
					title = title + "成功率";
				indexMap.put("value", successRates);
			} else if ("2".equals(type)) {
				if (index == 0)
					title = title + "总数";
				indexMap.put("value", totalNum);
			} else
				log.error("错误的状态:" + type);
			indexMap.put("color", colors[index]);
			indexMap.put("line_width", 3);
			values.add(indexMap);
			index++;
		}
		map.put("values", values);
		map.put("dates", dates);
		map.put("title", title);
		return map;
	}

	// 额外的方法
	@RequestMapping(value = "updateStatus", method = RequestMethod.POST)
	@ResponseBody
	public String updateStatus(HttpServletRequest req, Integer[] ids, Integer status) {
		Map<String, String> reqParams = HttpUtils.getReqParams(req);
		if (reqParams == null) {
			reqParams = new HashMap<String, String>();
		}
		log.info("reqParams:" + JsonUtils.obj2Json(reqParams));
		// if (status!=null&&status >= 0 && status <= 2){
		// for (Integer id : ids) {
		// BossGdDataAnalyze bossGdDataAnalyze = bossGdDataAnalyzeService.get(id);
		// bossGdDataAnalyze.setStatus(status);
		// update(bossGdDataAnalyze);
		// }
		// }
		return "success";
	}

	// 获取数据到页面
	@RequestMapping(value = "getTotalList")
	@ResponseBody
	public Map<String, Object> getTotalList(HttpServletRequest req) {
		Page<BossGdDataAnalyze> page = getPage(req);
		page.setPageNo(1);
		page.setPageSize(10000);
		
		String filter_EQI_area = req.getParameter("filter_EQI_area");
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(req);
		page = bossGdDataAnalyzeService.search(page, filters);
		Map<String, Object> map = new HashMap<String, Object>();
		List<BossGdDataAnalyze> result = page.getResult();

		Map<String, List<BossGdDataAnalyze>> cityMap = new HashMap<>();
		for (BossGdDataAnalyze bossGdDataAnalyze : result) {
			String city = bossGdDataAnalyze.getCity();
			if (cityMap.get(city) == null) {
				cityMap.put(city, new ArrayList<BossGdDataAnalyze>());
			}
			List<BossGdDataAnalyze> list = cityMap.get(city);
			list.add(bossGdDataAnalyze);
		}

		List<BossGdDataAnalyze> list = new ArrayList<>();
		int id = 1;
		for (Entry<String, List<BossGdDataAnalyze>> entry : cityMap.entrySet()) {
			List<BossGdDataAnalyze> value = entry.getValue();
			BossGdDataAnalyze obj = new BossGdDataAnalyze();
			for (BossGdDataAnalyze bossGdDataAnalyze : value) {
				obj.count(bossGdDataAnalyze);
			}
			if(StringUtils.isBlank(filter_EQI_area)){
				obj.setDaySuccessCost(obj.getDaySuccessCost().divide(new BigDecimal(2),4,BigDecimal.ROUND_HALF_UP));
				obj.setDayTotalCost(obj.getDayTotalCost().divide(new BigDecimal(2),4,BigDecimal.ROUND_HALF_UP));
				obj.setDaySuccessNum(obj.getDaySuccessNum()/2);
				obj.setDayTotalNum(obj.getDayTotalNum()/2);
			}
			obj.setCity(value.get(0).getCity()); 
			obj.setId(id++);
			obj.getRate();
			list.add(obj);
		}
		map.put("rows", list);
		map.put("total", 20);
		return map;
	}

	// 获取数据到页面
	@RequestMapping(value = "bossGdDataAnalyzeList")
	@ResponseBody
	public Map<String, Object> getConfigList(HttpServletRequest req) {
		Page<BossGdDataAnalyze> page = getPage(req);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(req);
		page = bossGdDataAnalyzeService.search(page, filters);
		return getEasyUIData(page);
	}

	// 跳转页面
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list(HttpServletRequest req, Integer id, BossGdDataAnalyze bossGdDataAnalyze, Model model) {
		Map<String, String> reqMap = HttpUtils.getReqParams(req);
		if (reqMap == null) {
			reqMap = new HashMap<String, String>();
		}
		String page = reqMap.get("page");
		if (StringUtils.isEmpty(page)) {
			page = "List";
		}
		if (id != null) {
			bossGdDataAnalyze = bossGdDataAnalyzeService.get(id);
		}
		Date now = new Date();
		reqMap.put("start", DateUtils.formatDateTime(DateUtils.getMonthStart(now)));
		reqMap.put("end", DateUtils.getDate() + " 23:59:59");
		model.addAttribute("reqMap", reqMap);
		model.addAttribute("action", (id != null ? "update" : "add"));
		model.addAttribute("bossGdDataAnalyze", bossGdDataAnalyze);
		return "boss/bossGdDataAnalyze/bossGdDataAnalyze" + page;
	}

	// 添加
	@RequiresPermissions("boss:bossGdDataAnalyze:add")
	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ResponseBody
	public String add(@Valid BossGdDataAnalyze bossGdDataAnalyze, Model model, String batchs, HttpServletRequest req) {
		String addIds = "";
		if (StringUtils.isNotEmpty(batchs)) {// 批量添加
			String[] split = batchs.split(",");
			for (String change : split) {
				BossGdDataAnalyze obj = JsonUtils.json2Obj(JsonUtils.obj2Json(bossGdDataAnalyze), BossGdDataAnalyze.class);
				// change内容修改
				obj.setId(null);
				bossGdDataAnalyzeService.save(obj);
				addIds = obj.getId() + " ";
			}
		} else {// 单个添加
			bossGdDataAnalyzeService.save(bossGdDataAnalyze);
			addIds = bossGdDataAnalyze.getId() + " ";
		}
		ParameterListener.refresh("bossGdDataAnalyze");
		// ParameterListener.flushStatus("bossGdDataAnalyze");
		log.info("bossGdDataAnalyze add:" + addIds);
		return "success";
	}

	// 修改
	@RequiresPermissions("boss:bossGdDataAnalyze:update")
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody BossGdDataAnalyze bossGdDataAnalyze) {
		bossGdDataAnalyzeService.update(bossGdDataAnalyze);
		ParameterListener.refresh("bossGdDataAnalyze");
		// ParameterListener.flushStatus("bossGdDataAnalyze");
		return "success";
	}

	// 批量删除
	@RequiresPermissions("boss:bossGdDataAnalyze:delete")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(Integer[] ids) {
		if (ids != null && ids.length > 0) {
			for (Integer id : ids) {
				bossGdDataAnalyzeService.delete(id);
				log.info("bossGdDataAnalyze delete:" + id);
			}
			ParameterListener.refresh("bossGdDataAnalyze");
			// ParameterListener.flushStatus("bossGdDataAnalyze");
		}
		return "success";
	}

	// 导出excel
	@RequiresPermissions("boss:bossGdDataAnalyze:exportExcel")
	@RequestMapping("exportExcel")
	public void createExcel(HttpServletRequest req, HttpServletResponse resp) throws Exception, InterruptedException {
		Integer maxNumber = 1000000;
		Page<BossGdDataAnalyze> page = getPage(req);
		page.setPageNo(1);
		page.setPageSize(maxNumber);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(req);
		page = bossGdDataAnalyzeService.exportSearch(page, filters, maxNumber);
		if (page.getTotalCount() > maxNumber) {
			ExcelUtils.writeErrorMsg(resp, page.getTotalCount(), "导出数据不能超过100万条");
			return;
		}
		List<BossGdDataAnalyze> list = page.getResult();// 获取数据
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		for (BossGdDataAnalyze bossGdDataAnalyze : list) {
			Map<String, Object> beanToMap = ExcelUtils.beanToMap(bossGdDataAnalyze);
			// ExcelUtils.showValue(beanToMap, "status", ParameterListener.freezeStr);
			mapList.add(beanToMap);
		}
		String title = "";

		String[] hearders = new String[] { "城市", "订单笔数", "订单成本",//
				"成功笔数", "成功成本", "订单笔数占比",//
				"成功笔数占比", "订单金额占比", "成功金额占比",//
				"使用区域", "备注", };
		String[] fields = new String[] { "city", "totalNum", "totalCost",//
				"successNum", "successCost", "totalNumRate",//
				"successNumRate", "totalCostRate", "successCostRate",//
				"area", "remark", };
		ExcelUtils.productExcel(resp, mapList, title, hearders, fields, log);
	}
}
