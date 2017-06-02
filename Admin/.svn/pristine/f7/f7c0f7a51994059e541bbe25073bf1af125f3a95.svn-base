package xinxing.boss.admin.boss.other.cmd;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import xinxing.boss.admin.common.utils.parameter.ParameterListener;
import xinxing.boss.admin.boss.other.domain.BossScheduleChange;
import xinxing.boss.admin.boss.other.service.BossScheduleChangeService;
import xinxing.boss.admin.system.user.utils.UserUtil;
import xinxing.boss.admin.common.persistence.Page;
import xinxing.boss.admin.common.persistence.PropertyFilter;
import xinxing.boss.admin.common.web.BaseController;
import xinxing.boss.admin.common.utils.string.StringUtils;
import xinxing.boss.admin.common.utils.json.JsonUtils;
import xinxing.boss.admin.common.excel.ExcelUtils;
import xinxing.boss.admin.common.utils.http.HttpUtils;

@Controller
@RequestMapping(value = "boss/bossScheduleChange")
public class BossScheduleChangeCmd extends BaseController {
	private static Logger log = LoggerFactory.getLogger(BossScheduleChangeCmd.class);
	@Autowired
	private BossScheduleChangeService bossScheduleChangeService;

	// 弹出框的方法
	@RequestMapping(value = "alertWindow")
	@ResponseBody
	public String alertWindow(HttpServletRequest req, BossScheduleChange bossScheduleChange, Integer[] ids, String method) {
		Map<String, String> reqParams = HttpUtils.getReqParams(req);
		if (reqParams == null) {
			reqParams = new HashMap<String, String>();
		}
		log.info("reqParams:" + JsonUtils.obj2Json(reqParams));
		if ("discount".equals(method) && reqParams.get("changeNum") != null) {
			for (int i = 0; i < ids.length; i++) {
				BossScheduleChange obj = bossScheduleChangeService.get(ids[i]);
				obj.setChangeNum(new BigDecimal(reqParams.get("changeNum")));
				update(obj);
			}
		}
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
		if (status != null && status >= 0 && status <= 2) {
			for (Integer id : ids) {
				BossScheduleChange bossScheduleChange = bossScheduleChangeService.get(id);
				bossScheduleChange.setStatus(status);
				update(bossScheduleChange);
			}
		}
		return "success";
	}

	// 获取数据到页面
	@RequestMapping(value = "bossScheduleChangeList")
	@ResponseBody
	public Map<String, Object> getConfigList(HttpServletRequest req) {
		Page<BossScheduleChange> page = getPage(req);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(req);
		page = bossScheduleChangeService.search(page, filters);
		return getEasyUIData(page);
	}

	// 跳转页面
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list(HttpServletRequest req, Integer id, BossScheduleChange bossScheduleChange, Model model) {
		Map<String, String> reqMap = HttpUtils.getReqParams(req);
		if (reqMap == null) {
			reqMap = new HashMap<String, String>();
		}
		String page = reqMap.get("page");
		if (StringUtils.isEmpty(page)) {
			page = "List";
		}
		if (id != null) {
			bossScheduleChange = bossScheduleChangeService.get(id);
		}
		model.addAttribute("reqMap", reqMap);
		model.addAttribute("action", (id != null ? "update" : "add"));
		model.addAttribute("bossScheduleChange", bossScheduleChange);
		return "boss/bossScheduleChange/bossScheduleChange" + page;
	}

	// 添加
	@RequiresPermissions("boss:bossScheduleChange:add")
	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ResponseBody
	public String add(@Valid BossScheduleChange bossScheduleChange, Model model, String batchs, HttpServletRequest req) {
		String addIds = "";
		bossScheduleChange.addAndUpdateTimeAndUserId();
		String tableIds = req.getParameter("tableIds");
		if (StringUtils.isNotEmpty(tableIds)) {// 批量添加
			String[] split = tableIds.split(",");
			for (String change : split) {
				BossScheduleChange obj = JsonUtils.json2Obj(JsonUtils.obj2Json(bossScheduleChange), BossScheduleChange.class);
				// change内容修改
				obj.setTableId(Integer.parseInt(change));
				obj.setId(null);
				bossScheduleChangeService.save(obj);
				addIds = obj.getId() + " ";
			}
		} else {// 单个添加
			// bossScheduleChangeService.save(bossScheduleChange);
			// addIds = bossScheduleChange.getId() + " ";
			return "对应表必须填";
		}
		//ParameterListener.refresh("bossScheduleChange");
		//ParameterListener.flushStatus("bossScheduleChange");
		log.info("bossScheduleChange add:" + addIds);
		return "success";
	}

	// 修改
	@RequiresPermissions("boss:bossScheduleChange:update")
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody BossScheduleChange bossScheduleChange) {
		bossScheduleChange.updateTimeAndUserId();
		bossScheduleChangeService.update(bossScheduleChange);
		//ParameterListener.refresh("bossScheduleChange");
		// ParameterListener.flushStatus("bossScheduleChange");
		return "success";
	}

	// 批量删除
	@RequiresPermissions("boss:bossScheduleChange:delete")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(Integer[] ids) {
		if (ids != null && ids.length > 0) {
			for (Integer id : ids) {
				bossScheduleChangeService.delete(id);
				log.info("bossScheduleChange delete:" + id);
			}
			//ParameterListener.refresh("bossScheduleChange");
			// ParameterListener.flushStatus("bossScheduleChange");
		}
		return "success";
	}

	// 导出excel
	@RequiresPermissions("boss:bossScheduleChange:exportExcel")
	@RequestMapping("exportExcel")
	public void createExcel(HttpServletRequest req, HttpServletResponse resp) throws Exception, InterruptedException {
		Integer maxNumber = 100000;
		Page<BossScheduleChange> page = getPage(req);
		page.setPageNo(1);
		page.setPageSize(maxNumber);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(req);
		page = bossScheduleChangeService.exportSearch(page, filters, maxNumber);
		if (page.getTotalCount() > maxNumber) {
			ExcelUtils.writeErrorMsg(resp, page.getTotalCount(), "导出数据不能超过10万条");
			return;
		}
		List<BossScheduleChange> list = page.getResult();// 获取数据
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		for (BossScheduleChange bossScheduleChange : list) {
			Map<String, Object> beanToMap = ExcelUtils.beanToMap(bossScheduleChange);
			// ExcelUtils.showValue(beanToMap, "status", ParameterListener.freezeStr);
			mapList.add(beanToMap);
		}
		String title = "";

		String[] hearders = new String[] { "对应表", "表id", "修改类型",//
				"是否生效", "修改时间", "折扣数",//
				"添加时间", "添加用户", };
		String[] fields = new String[] { "tableType", "tableId", "type",//
				"status", "changeTime", "changeNum",//
				"addTime", "addUser", };
		ExcelUtils.productExcel(resp, mapList, title, hearders, fields, log);
	}
}
