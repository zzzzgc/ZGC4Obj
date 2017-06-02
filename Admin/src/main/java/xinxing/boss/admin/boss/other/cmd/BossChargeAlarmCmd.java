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
import xinxing.boss.admin.boss.other.domain.BossChargeAlarm;
import xinxing.boss.admin.boss.other.service.BossChargeAlarmService;
import xinxing.boss.admin.system.user.utils.UserUtil;
import xinxing.boss.admin.common.persistence.Page;
import xinxing.boss.admin.common.persistence.PropertyFilter;
import xinxing.boss.admin.common.web.BaseController;
import xinxing.boss.admin.common.utils.string.StringUtils;
import xinxing.boss.admin.common.utils.json.JsonUtils;
import xinxing.boss.admin.common.excel.ExcelUtils;
import xinxing.boss.admin.common.utils.http.HttpUtils;
@Controller
@RequestMapping(value = "boss/bossChargeAlarm")
public class BossChargeAlarmCmd extends BaseController {
	private static Logger log = LoggerFactory.getLogger(BossChargeAlarmCmd.class);
	@Autowired
	private BossChargeAlarmService bossChargeAlarmService;
	//弹出框的方法
	@RequestMapping(value = "alertWindow")
	@ResponseBody
	public String alertWindow(HttpServletRequest req,BossChargeAlarm bossChargeAlarm,Integer[] ids,String method) {
		log.info("ids:"+JsonUtils.obj2Json(ids)); 
		return "success";
	}
	//额外的方法
	@RequestMapping(value = "updateStatus", method = RequestMethod.POST)
	@ResponseBody
	public String updateStatus(HttpServletRequest req, Integer[] ids, Integer status) {
		if (ids == null||ids.length==0||status == null) {
			return "没有id";
		}
//		if (status!=null&&status >= 0 && status <= 2){
//			for (Integer id : ids) {
//				BossChargeAlarm bossChargeAlarm = bossChargeAlarmService.get(id);
//				bossChargeAlarm.setStatus(status);
//		    	update(bossChargeAlarm);
//			}
//		}
		return "success";
	}
	
	//跳转页面
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list(HttpServletRequest req,Integer id, BossChargeAlarm bossChargeAlarm, Model model) {
		Map<String, String> reqMap = HttpUtils.getReqParams(req);
		if(reqMap==null){
			reqMap = new HashMap<String, String>();
		}
		String page = reqMap.get("page");
		if(StringUtils.isEmpty(page)){
			page = "List"; 
		}
		if (id != null) {
			bossChargeAlarm = bossChargeAlarmService.get(id);
		}
		model.addAttribute("reqMap", reqMap);
		model.addAttribute("action", (id != null ? "update" : "add"));
		model.addAttribute("bossChargeAlarm", bossChargeAlarm);
		return "boss/bossChargeAlarm/bossChargeAlarm"+page;
	}
	//获取数据到页面
	@RequestMapping(value = "bossChargeAlarmList")
	@ResponseBody
	public Map<String, Object> getConfigList(HttpServletRequest req) {
		Page<BossChargeAlarm> page = getPage(req);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(req);
		page = bossChargeAlarmService.search(page, filters);
		return getEasyUIData(page);
	}
	//添加
	@RequiresPermissions("boss:bossChargeAlarm:add")
	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ResponseBody
	public String add(@Valid BossChargeAlarm bossChargeAlarm, Model model, String batchs, HttpServletRequest req) {
		
		String addIds = "";
		bossChargeAlarm.addAndUpdateTimeAndUserId();
		if (StringUtils.isNotEmpty(batchs)) {// 批量添加
			String[] split = batchs.split(","); 
			for (String change : split) {
				BossChargeAlarm obj = JsonUtils.json2Obj(JsonUtils.obj2Json(bossChargeAlarm), BossChargeAlarm.class);
				//change内容修改
				obj.setId(null);
				obj.setProductId(Integer.valueOf(change));
				bossChargeAlarmService.save(obj);
				addIds = obj.getId()+" ";
			}
		} else {//单个添加
			bossChargeAlarmService.save(bossChargeAlarm);
			addIds = bossChargeAlarm.getId()+" ";
		}
		ParameterListener.refresh("bossChargeAlarm");
		ParameterListener.flushStatus("bossChargeAlarm");
		log.info("bossChargeAlarm add:" + addIds);
		return "success";
	}
	//修改
	@RequiresPermissions("boss:bossChargeAlarm:update")
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody BossChargeAlarm bossChargeAlarm) {
		bossChargeAlarm.updateTimeAndUserId(bossChargeAlarm);
		bossChargeAlarmService.update(bossChargeAlarm);
		ParameterListener.refresh("bossChargeAlarm");
		ParameterListener.flushStatus("bossChargeAlarm");
		return "success";
	}
	//批量删除
	@RequiresPermissions("boss:bossChargeAlarm:delete")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(Integer[] ids) {
		if (ids != null && ids.length > 0) {
			for (Integer id : ids) {
				bossChargeAlarmService.delete(id);
				log.info("bossChargeAlarm delete:" + id);
			}
			ParameterListener.refresh("bossChargeAlarm");
			ParameterListener.flushStatus("bossChargeAlarm");
		}
		return "success";
	}
	//导出excel
	@RequiresPermissions("boss:bossChargeAlarm:exportExcel")
	@RequestMapping("exportExcel")
	public void createExcel(HttpServletRequest req, HttpServletResponse resp)
			throws Exception, InterruptedException {
		Integer maxNumber = 100000;
		Page<BossChargeAlarm> page = getPage(req);
		page.setPageNo(1);
		page.setPageSize(maxNumber);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(req);
		page = bossChargeAlarmService.exportSearch(page, filters, maxNumber);
		if (page.getTotalCount() > maxNumber) {
			ExcelUtils.writeErrorMsg(resp, page.getTotalCount(), "导出数据不能超过10万条");
			return;
		}
		List<BossChargeAlarm> list = page.getResult();//获取数据
		List<Map<String, Object>> mapList = new ArrayList<>();
		for (BossChargeAlarm bossChargeAlarm : list) {
			Map<String, Object> beanToMap = ExcelUtils.beanToMap(bossChargeAlarm);
			//ExcelUtils.showValue(beanToMap, "status", ParameterListener.freezeStr);
			mapList.add(beanToMap);
		}
		String title = "";
		String[] hearders = new String[] {"","",""//
		,"","",""//
		,"","",""//
		,"","",""//
		,"","",""};
		String[] fields = new String[] {"","",""//
		,"","",""//
		,"","",""//
		,"","",""//
		,"","",""};
		ExcelUtils.productExcel(resp, mapList, title, hearders, fields, log);
	}
}
