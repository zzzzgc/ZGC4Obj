package xinxing.boss.admin.boss.other.cmd;

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
import xinxing.boss.admin.boss.other.domain.BossOrderPhoneAndSizeRecord;
import xinxing.boss.admin.boss.other.service.BossOrderPhoneAndSizeRecordService;
import xinxing.boss.admin.system.user.utils.UserUtil;
import xinxing.boss.admin.common.persistence.Page;
import xinxing.boss.admin.common.persistence.PropertyFilter;
import xinxing.boss.admin.common.web.BaseController;
import xinxing.boss.admin.common.utils.string.StringUtils;
import xinxing.boss.admin.common.utils.json.JsonUtils;
import xinxing.boss.admin.common.excel.ExcelUtils;
import xinxing.boss.admin.common.utils.http.HttpUtils;

@Controller
@RequestMapping(value = "boss/bossOrderPhoneAndSizeRecord")
public class BossOrderPhoneAndSizeRecordCmd extends BaseController {
	private static Logger log = LoggerFactory.getLogger(BossOrderPhoneAndSizeRecordCmd.class);
	@Autowired
	private BossOrderPhoneAndSizeRecordService bossOrderPhoneAndSizeRecordService;

	// 弹出框的方法
	@RequestMapping(value = "alertWindow")
	@ResponseBody
	public String alertWindow(HttpServletRequest req, BossOrderPhoneAndSizeRecord bossOrderPhoneAndSizeRecord, Integer[] ids, String method) {
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
		if (status != null && status >= 0 && status <= 3) {
			for (Integer id : ids) {
				BossOrderPhoneAndSizeRecord bossOrderPhoneAndSizeRecord = bossOrderPhoneAndSizeRecordService.get(id);
				bossOrderPhoneAndSizeRecord.setStatus(status);
				update(bossOrderPhoneAndSizeRecord);
				if(bossOrderPhoneAndSizeRecord.getStatus()==2){
					bossOrderPhoneAndSizeRecordService.saveOrUpdate("update boss_order set isSecondChannel = 0 where id = "+bossOrderPhoneAndSizeRecord.getOrderId());
				}
			}
		}
		if(status==4){//作废
			bossOrderPhoneAndSizeRecordService.saveOrUpdate("select GROUP_CONCAT(orderId) from boss_order_phone_and_size_record where url is null and status = 0;");
//			select GROUP_CONCAT(orderId) from boss_order_phone_and_size_record where url is null and status = 0;
		}
		return "success";
	}

	// 获取数据到页面
	@RequestMapping(value = "bossOrderPhoneAndSizeRecordList")
	@ResponseBody
	public Map<String, Object> getConfigList(HttpServletRequest req) {
		Page<BossOrderPhoneAndSizeRecord> page = getPage(req);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(req);
		page = bossOrderPhoneAndSizeRecordService.search(page, filters);
		return getEasyUIData(page);
	}

	// 跳转页面
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list(HttpServletRequest req, Integer id, BossOrderPhoneAndSizeRecord bossOrderPhoneAndSizeRecord, Model model) {
		Map<String, String> reqMap = HttpUtils.getReqParams(req);
		if (reqMap == null) {
			reqMap = new HashMap<String, String>();
		}
		String page = reqMap.get("page");
		if (StringUtils.isEmpty(page)) {
			page = "List";
		}
		if (id != null) {
			bossOrderPhoneAndSizeRecord = bossOrderPhoneAndSizeRecordService.get(id);
		}
		model.addAttribute("reqMap", reqMap);
		model.addAttribute("action", (id != null ? "update" : "add"));
		model.addAttribute("bossOrderPhoneAndSizeRecord", bossOrderPhoneAndSizeRecord);
		return "boss/bossOrderPhoneAndSizeRecord/bossOrderPhoneAndSizeRecord" + page;
	}

	// 添加
	@RequiresPermissions("boss:bossOrderPhoneAndSizeRecord:add")
	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ResponseBody
	public String add(@Valid BossOrderPhoneAndSizeRecord bossOrderPhoneAndSizeRecord, Model model, String batchs, HttpServletRequest req) {
		String addIds = "";
		if (StringUtils.isNotEmpty(batchs)) {// 批量添加
			String[] split = batchs.split(",");
			for (String change : split) {
				BossOrderPhoneAndSizeRecord obj = JsonUtils.json2Obj(JsonUtils.obj2Json(bossOrderPhoneAndSizeRecord),
						BossOrderPhoneAndSizeRecord.class);
				// change内容修改
				obj.setId(null);
				bossOrderPhoneAndSizeRecordService.save(obj);
				addIds = obj.getId() + " ";
			}
		} else {// 单个添加
			bossOrderPhoneAndSizeRecordService.save(bossOrderPhoneAndSizeRecord);
			addIds = bossOrderPhoneAndSizeRecord.getId() + " ";
		}
		ParameterListener.refresh("bossOrderPhoneAndSizeRecord");
		// ParameterListener.flushStatus("bossOrderPhoneAndSizeRecord");
		log.info("bossOrderPhoneAndSizeRecord add:" + addIds);
		return "success";
	}

	// 修改
	@RequiresPermissions("boss:bossOrderPhoneAndSizeRecord:update")
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody BossOrderPhoneAndSizeRecord bossOrderPhoneAndSizeRecord) {
		bossOrderPhoneAndSizeRecordService.update(bossOrderPhoneAndSizeRecord);
		ParameterListener.refresh("bossOrderPhoneAndSizeRecord");
		// ParameterListener.flushStatus("bossOrderPhoneAndSizeRecord");
		return "success";
	}

	// 批量删除
	@RequiresPermissions("boss:bossOrderPhoneAndSizeRecord:delete")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(Integer[] ids) {
		if (ids != null && ids.length > 0) {
			for (Integer id : ids) {
				bossOrderPhoneAndSizeRecordService.delete(id);
				log.info("bossOrderPhoneAndSizeRecord delete:" + id);
			}
			ParameterListener.refresh("bossOrderPhoneAndSizeRecord");
			// ParameterListener.flushStatus("bossOrderPhoneAndSizeRecord");
		}
		return "success";
	}

	// 导出excel
	@RequiresPermissions("boss:bossOrderPhoneAndSizeRecord:exportExcel")
	@RequestMapping("exportExcel")
	public void createExcel(HttpServletRequest req, HttpServletResponse resp) throws Exception, InterruptedException {
		Integer maxNumber = 1000000;
		Page<BossOrderPhoneAndSizeRecord> page = getPage(req);
		page.setPageNo(1);
		page.setPageSize(maxNumber);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(req);
		page = bossOrderPhoneAndSizeRecordService.exportSearch(page, filters, maxNumber);
		if (page.getTotalCount() > maxNumber) {
			ExcelUtils.writeErrorMsg(resp, page.getTotalCount(), "导出数据不能超过100万条");
			return;
		}
		List<BossOrderPhoneAndSizeRecord> list = page.getResult();// 获取数据
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		for (BossOrderPhoneAndSizeRecord bossOrderPhoneAndSizeRecord : list) {
			Map<String, Object> beanToMap = ExcelUtils.beanToMap(bossOrderPhoneAndSizeRecord);
			// ExcelUtils.showValue(beanToMap, "status", ParameterListener.freezeStr);
			mapList.add(beanToMap);
		}
		String title = "";

		String[] hearders = new String[] { "订单id", "手机号", "规格",//
				"添加时间", };
		String[] fields = new String[] { "orderId", "phone", "size",//
				"addTime", };
		ExcelUtils.productExcel(resp, mapList, title, hearders, fields, log);
	}
}
