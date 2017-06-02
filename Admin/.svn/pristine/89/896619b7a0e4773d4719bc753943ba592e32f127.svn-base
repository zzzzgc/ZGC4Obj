package xinxing.boss.admin.boss.other.cmd;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import xinxing.boss.admin.boss.other.domain.BossProviderSame;
import xinxing.boss.admin.boss.other.service.BossProviderSameService;
import xinxing.boss.admin.boss.provider.domain.ProviderInfo;
import xinxing.boss.admin.boss.provider.service.ProviderInfoService;
import xinxing.boss.admin.common.excel.ExcelUtils;
import xinxing.boss.admin.common.persistence.Page;
import xinxing.boss.admin.common.persistence.PropertyFilter;
import xinxing.boss.admin.common.utils.http.HttpUtils;
import xinxing.boss.admin.common.utils.json.JsonUtils;
import xinxing.boss.admin.common.utils.parameter.ParameterListener;
import xinxing.boss.admin.common.utils.string.StringUtils;
import xinxing.boss.admin.common.web.BaseController;

@Controller
@RequestMapping(value = "boss/bossProviderSame")
public class BossProviderSameCmd extends BaseController {
	private static Logger log = LoggerFactory.getLogger(BossProviderSameCmd.class);
	@Autowired
	private BossProviderSameService bossProviderSameService;
	@Autowired
	private ProviderInfoService providerInfoService;

	// 弹出框的方法
	@RequestMapping(value = "alertWindow")
	@ResponseBody
	public String alertWindow(HttpServletRequest req, BossProviderSame bossProviderSame, Integer[] ids, String method) {
		Map<String, String> reqParams = HttpUtils.getReqParams(req);
		if (reqParams == null) {
			reqParams = new HashMap<String, String>();
		}
		if ("changeAlarmBalance".equals(method) && ids != null && ids.length > 0) {
			String alarmBalance = req.getParameter("alarmBalance");
			if (alarmBalance != null) {
				for (Integer id : ids) {
					BossProviderSame bps = bossProviderSameService.get(id);
					bps.setAlarmBalance(new BigDecimal(alarmBalance));
					update(bps);
				}
			}
		}
		log.info("reqParams:" + JsonUtils.obj2Json(reqParams));
		return "success";
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
		// BossProviderSame bossProviderSame = bossProviderSameService.get(id);
		// bossProviderSame.setStatus(status);
		// update(bossProviderSame);
		// }
		// }
		return "success";
	}

	// 获取数据到页面
	@RequestMapping(value = "bossProviderSameList")
	@ResponseBody
	public Map<String, Object> getConfigList(HttpServletRequest req) {
		Page<BossProviderSame> page = getPage(req);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(req);
		page = bossProviderSameService.search(page, filters);
		Map<String, Object> map = new HashMap<String, Object>();
		List<BossProviderSame> result = page.getResult();
		List<Map<String, Object>> json2Obj = JsonUtils.json2Obj(JsonUtils.obj2Json(result), List.class);
		for (Map<String, Object> objMap : json2Obj) {
			String providerIds = (String) objMap.get("providerIds");
			String[] split = providerIds.split(",");
			List<Integer> list = new ArrayList<>();
			for (String string : split) {
				list.add(Integer.parseInt(string));
			}
			Integer[] array = (Integer[]) list.toArray(new Integer[list.size()]);
			List<ProviderInfo> search = providerInfoService.search("from ProviderInfo where id in (?0)", list);
			BigDecimal sum = new BigDecimal(0);
			for (ProviderInfo providerInfo : search) {
				if (providerInfo.getBalance() != null) {
					sum = providerInfo.getBalance().add(sum);
				}
			}
			objMap.put("balance", sum);
		}
		map.put("rows", json2Obj);
		map.put("total", page.getTotalCount());
		return map;
	}

	// 跳转页面
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list(HttpServletRequest req, Integer id, BossProviderSame bossProviderSame, Model model) {
		Map<String, String> reqMap = HttpUtils.getReqParams(req);
		if (reqMap == null) {
			reqMap = new HashMap<String, String>();
		}
		String page = reqMap.get("page");
		if (StringUtils.isEmpty(page)) {
			page = "List";
		}
		if (id != null) {
			bossProviderSame = bossProviderSameService.get(id);
		}
		
		model.addAttribute("reqMap", reqMap);
		model.addAttribute("action", (id != null ? "update" : "add"));
		model.addAttribute("bossProviderSame", bossProviderSame);
		return "boss/bossProviderSame/bossProviderSame" + page;
	}

	// 添加
	@RequiresPermissions("boss:bossProviderSame:add")
	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ResponseBody
	public String add(@Valid BossProviderSame bossProviderSame, Model model, String batchs, HttpServletRequest req) {
		String addIds = "";
		// bossProviderSame.addAndUpdateTimeAndUserId();
		if (StringUtils.isNotEmpty(batchs)) {// 批量添加
			String[] split = batchs.split(",");
			for (String change : split) {
				BossProviderSame obj = JsonUtils.json2Obj(JsonUtils.obj2Json(bossProviderSame), BossProviderSame.class);
				// change内容修改
				obj.setId(null);
				bossProviderSameService.save(obj);
				addIds = obj.getId() + " ";
			}
		} else {// 单个添加
			bossProviderSameService.save(bossProviderSame);
			addIds = bossProviderSame.getId() + " ";
		}
		ParameterListener.refresh("bossProviderSame");
		ParameterListener.flushStatus("bossProviderSame");
		log.info("bossProviderSame add:" + addIds);
		return "success";
	}

	// 修改
	@RequiresPermissions("boss:bossProviderSame:update")
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody BossProviderSame bossProviderSame) {
		// bossProviderSame.updateTimeAndUserId();
		bossProviderSameService.update(bossProviderSame);
		ParameterListener.refresh("bossProviderSame");
		ParameterListener.flushStatus("bossProviderSame");
		return "success";
	}

	// 批量删除
	@RequiresPermissions("boss:bossProviderSame:delete")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(Integer[] ids) {
		if (ids != null && ids.length > 0) {
			for (Integer id : ids) {
				bossProviderSameService.delete(id);
				log.info("bossProviderSame delete:" + id);
			}
			ParameterListener.refresh("bossProviderSame");
			ParameterListener.flushStatus("bossProviderSame");
		}
		return "success";
	}

	// 导出excel
	@RequiresPermissions("boss:bossProviderSame:exportExcel")
	@RequestMapping("exportExcel")
	public void createExcel(HttpServletRequest req, HttpServletResponse resp) throws Exception, InterruptedException {
		Integer maxNumber = 100000;
		Page<BossProviderSame> page = getPage(req);
		page.setPageNo(1);
		page.setPageSize(maxNumber);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(req);
		page = bossProviderSameService.exportSearch(page, filters, maxNumber);
		if (page.getTotalCount() > maxNumber) {
			ExcelUtils.writeErrorMsg(resp, page.getTotalCount(), "导出数据不能超过10万条");
			return;
		}
		List<BossProviderSame> list = page.getResult();// 获取数据
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		for (BossProviderSame bossProviderSame : list) {
			Map<String, Object> beanToMap = ExcelUtils.beanToMap(bossProviderSame);
			// ExcelUtils.showValue(beanToMap, "status", ParameterListener.freezeStr);
			mapList.add(beanToMap);
		}
		String title = "";

		String[] hearders = new String[] { "供应商id", "报警金额", };
		String[] fields = new String[] { "providerIds", "alarmBalance", };
		ExcelUtils.productExcel(resp, mapList, title, hearders, fields, log);
	}
}
