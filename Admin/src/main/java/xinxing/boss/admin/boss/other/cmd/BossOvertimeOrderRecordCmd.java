package xinxing.boss.admin.boss.other.cmd;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
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

import xinxing.boss.admin.boss.order.domain.OrderInfo;
import xinxing.boss.admin.boss.order.service.OrderInfoService;
import xinxing.boss.admin.boss.other.domain.BossOvertimeOrderRecord;
import xinxing.boss.admin.boss.other.service.BossOvertimeOrderRecordService;
import xinxing.boss.admin.common.excel.ExcelUtils;
import xinxing.boss.admin.common.persistence.Page;
import xinxing.boss.admin.common.persistence.PropertyFilter;
import xinxing.boss.admin.common.utils.http.HttpUtils;
import xinxing.boss.admin.common.utils.json.JsonUtils;
import xinxing.boss.admin.common.utils.parameter.ParameterListener;
import xinxing.boss.admin.common.utils.string.StringUtils;
import xinxing.boss.admin.common.utils.time.DateUtils;
import xinxing.boss.admin.common.web.BaseController;
import xinxing.boss.admin.system.user.utils.UserUtil;

@Controller
@RequestMapping(value = "boss/bossOvertimeOrderRecord")
public class BossOvertimeOrderRecordCmd extends BaseController {
	private static Logger log = LoggerFactory.getLogger(BossOvertimeOrderRecordCmd.class);
	@Autowired
	private BossOvertimeOrderRecordService bossOvertimeOrderRecordService;
	@Autowired
	private OrderInfoService orderInfoService;

	// 弹出框的方法
	@RequestMapping(value = "alertWindow")
	@ResponseBody
	public String alertWindow(HttpServletRequest req, BossOvertimeOrderRecord bossOvertimeOrderRecord, Integer[] ids, String method) {
		Map<String, String> reqParams = HttpUtils.getReqParams(req);
		if (reqParams == null) {
			reqParams = new HashMap<String, String>();
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
		// BossOvertimeOrderRecord bossOvertimeOrderRecord = bossOvertimeOrderRecordService.get(id);
		// bossOvertimeOrderRecord.setStatus(status);
		// update(bossOvertimeOrderRecord);
		// }
		// }
		return "success";
	}

	// 获取数据到页面
	@RequestMapping(value = "bossOvertimeOrderRecordList")
	@ResponseBody
	public Map<String, Object> getConfigList(HttpServletRequest req) {
		Page<BossOvertimeOrderRecord> page = getPage(req);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(req);
		page = bossOvertimeOrderRecordService.search(page, filters);
		return getEasyUIData(page);
	}

	// 跳转页面
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list(HttpServletRequest req, Integer id, BossOvertimeOrderRecord bossOvertimeOrderRecord, Model model) {
		Map<String, String> reqMap = HttpUtils.getReqParams(req);
		if (reqMap == null) {
			reqMap = new HashMap<String, String>();
		}
		String page = reqMap.get("page");
		if (StringUtils.isEmpty(page)) {
			page = "List";
		}
		if (id != null) {
			bossOvertimeOrderRecord = bossOvertimeOrderRecordService.get(id);
		}
		model.addAttribute("reqMap", reqMap);
		model.addAttribute("action", (id != null ? "update" : "add"));
		model.addAttribute("bossOvertimeOrderRecord", bossOvertimeOrderRecord);
		return "boss/bossOvertimeOrderRecord/bossOvertimeOrderRecord" + page;
	}

	// 添加
	@RequiresPermissions("boss:bossOvertimeOrderRecord:add")
	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ResponseBody
	public String add(@Valid BossOvertimeOrderRecord bossOvertimeOrderRecord, Model model, String batchs, HttpServletRequest req) {
		String addIds = "";
		// bossOvertimeOrderRecord.addAndUpdateTimeAndUserId();
		bossOvertimeOrderRecord.setAddTime(new Date());
		bossOvertimeOrderRecord.setAddUser(UserUtil.getCurrentUser().getName());
		if (StringUtils.isNotEmpty(batchs)) {// 批量添加
			String[] split = batchs.split(",");
			for (String change : split) {
				BossOvertimeOrderRecord obj = JsonUtils.json2Obj(JsonUtils.obj2Json(bossOvertimeOrderRecord), BossOvertimeOrderRecord.class);
				// change内容修改
				obj.setId(null);
				bossOvertimeOrderRecordService.save(obj);
				addIds = obj.getId() + " ";
			}
		} else {// 单个添加
			bossOvertimeOrderRecordService.save(bossOvertimeOrderRecord);
			addIds = bossOvertimeOrderRecord.getId() + " ";
		}
		ParameterListener.refresh("bossOvertimeOrderRecord");
		// ParameterListener.flushStatus("bossOvertimeOrderRecord");
		log.info("bossOvertimeOrderRecord add:" + addIds);
		return "success";
	}

	// 修改
	@RequiresPermissions("boss:bossOvertimeOrderRecord:update")
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody BossOvertimeOrderRecord bossOvertimeOrderRecord) {
		// bossOvertimeOrderRecord.updateTimeAndUserId();
		bossOvertimeOrderRecordService.update(bossOvertimeOrderRecord);
		ParameterListener.refresh("bossOvertimeOrderRecord");
		// ParameterListener.flushStatus("bossOvertimeOrderRecord");
		return "success";
	}

	// 批量删除
	@RequiresPermissions("boss:bossOvertimeOrderRecord:delete")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(Integer[] ids) {
		if (ids != null && ids.length > 0) {
			for (Integer id : ids) {
				bossOvertimeOrderRecordService.delete(id);
				log.info("bossOvertimeOrderRecord delete:" + id);
			}
			ParameterListener.refresh("bossOvertimeOrderRecord");
			// ParameterListener.flushStatus("bossOvertimeOrderRecord");
		}
		return "success";
	}

	// 导出excel
	@RequiresPermissions("boss:bossOvertimeOrderRecord:exportExcel")
	@RequestMapping("exportExcel")
	public void createExcel(HttpServletRequest req, HttpServletResponse resp) throws Exception, InterruptedException {
		Integer maxNumber = 100000;
		Page<BossOvertimeOrderRecord> page = getPage(req);
		page.setPageNo(1);
		page.setPageSize(maxNumber);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(req);
		page = bossOvertimeOrderRecordService.exportSearch(page, filters, maxNumber);
		if (page.getTotalCount() > maxNumber) {
			ExcelUtils.writeErrorMsg(resp, page.getTotalCount(), "导出数据不能超过10万条");
			return;
		}
		List<BossOvertimeOrderRecord> list = page.getResult();// 获取数据
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		for (BossOvertimeOrderRecord bossOvertimeOrderRecord : list) {
			Map<String, Object> beanToMap = ExcelUtils.beanToMap(bossOvertimeOrderRecord);
			// ExcelUtils.showValue(beanToMap, "status", ParameterListener.freezeStr);
			mapList.add(beanToMap);
		}
		String title = "";

		String[] hearders = new String[] { "", "", "",//
		};
		String[] fields = new String[] { "orderId", "addTime", "addUser",//
		};
		ExcelUtils.productExcel(resp, mapList, title, hearders, fields, log);
	}
}
