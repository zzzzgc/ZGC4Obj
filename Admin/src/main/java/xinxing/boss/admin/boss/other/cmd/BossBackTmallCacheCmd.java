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
import xinxing.boss.admin.boss.other.domain.BossBackTmallCache;
import xinxing.boss.admin.boss.other.service.BossBackTmallCacheService;
import xinxing.boss.admin.system.user.utils.UserUtil;
import xinxing.boss.admin.common.persistence.Page;
import xinxing.boss.admin.common.persistence.PropertyFilter;
import xinxing.boss.admin.common.web.BaseController;
import xinxing.boss.admin.common.utils.string.StringUtils;
import xinxing.boss.admin.common.utils.json.JsonUtils;
import xinxing.boss.admin.common.excel.ExcelUtils;
import xinxing.boss.admin.common.utils.http.HttpUtils;
@Controller
@RequestMapping(value = "boss/bossBackTmallCache")
public class BossBackTmallCacheCmd extends BaseController {
	private static Logger log = LoggerFactory.getLogger(BossBackTmallCacheCmd.class);
	@Autowired
	private BossBackTmallCacheService bossBackTmallCacheService;
	//弹出框的方法
	@RequestMapping(value = "alertWindow")
	@ResponseBody
	public String alertWindow(HttpServletRequest req,BossBackTmallCache bossBackTmallCache,Integer[] ids,String method) {
		Map<String, String> reqParams = HttpUtils.getReqParams(req);
		if (reqParams == null) {
			reqParams = new HashMap<String, String>();
		}
		log.info("reqParams:" + JsonUtils.obj2Json(reqParams));
		return "success";
	}
	//额外的方法
	@RequestMapping(value = "updateStatus", method = RequestMethod.POST)
	@ResponseBody
	public String updateStatus(HttpServletRequest req, Integer[] ids, Integer status) {
		Map<String, String> reqParams = HttpUtils.getReqParams(req);
		if (reqParams == null) {
			reqParams = new HashMap<String, String>();
		}
		log.info("reqParams:" + JsonUtils.obj2Json(reqParams));
//		if (status!=null&&status >= 0 && status <= 2){
//			for (Integer id : ids) {
//				BossBackTmallCache bossBackTmallCache = bossBackTmallCacheService.get(id);
//				bossBackTmallCache.setStatus(status);
//		    	update(bossBackTmallCache);
//			}
//		}
		return "success";
	}
	//获取数据到页面
	@RequestMapping(value = "bossBackTmallCacheList")
	@ResponseBody
	public Map<String, Object> getConfigList(HttpServletRequest req) {
		Page<BossBackTmallCache> page = getPage(req);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(req);
		page = bossBackTmallCacheService.search(page, filters);
		return getEasyUIData(page);
	}
	//跳转页面
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list(HttpServletRequest req,Integer id, BossBackTmallCache bossBackTmallCache, Model model) {
		Map<String, String> reqMap = HttpUtils.getReqParams(req);
		if(reqMap==null){
			reqMap = new HashMap<String, String>();
		}
		String page = reqMap.get("page");
		if(StringUtils.isEmpty(page)){
			page = "List"; 
		}
		if (id != null) {
			bossBackTmallCache = bossBackTmallCacheService.get(id);
		}
		model.addAttribute("reqMap", reqMap);
		model.addAttribute("action", (id != null ? "update" : "add"));
		model.addAttribute("bossBackTmallCache", bossBackTmallCache);
		return "boss/bossBackTmallCache/bossBackTmallCache"+page;
	}
	//添加
	@RequiresPermissions("boss:bossBackTmallCache:add")
	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ResponseBody
	public String add(@Valid BossBackTmallCache bossBackTmallCache, Model model, String batchs, HttpServletRequest req) {
		String addIds = "";
		if (StringUtils.isNotEmpty(batchs)) {// 批量添加
			String[] split = batchs.split(","); 
			for (String change : split) {
				BossBackTmallCache obj = JsonUtils.json2Obj(JsonUtils.obj2Json(bossBackTmallCache), BossBackTmallCache.class);
				//change内容修改
				obj.setId(null);
				bossBackTmallCacheService.save(obj);
				addIds = obj.getId()+" ";
			}
		} else {//单个添加
			bossBackTmallCacheService.save(bossBackTmallCache);
			addIds = bossBackTmallCache.getId()+" ";
		}
		ParameterListener.refresh("bossBackTmallCache");
		//ParameterListener.flushStatus("bossBackTmallCache");
		log.info("bossBackTmallCache add:" + addIds);
		return "success";
	}
	//修改
	@RequiresPermissions("boss:bossBackTmallCache:update")
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody BossBackTmallCache bossBackTmallCache) {
		bossBackTmallCacheService.update(bossBackTmallCache);
		ParameterListener.refresh("bossBackTmallCache");
		//ParameterListener.flushStatus("bossBackTmallCache");
		return "success";
	}
	//批量删除
	@RequiresPermissions("boss:bossBackTmallCache:delete")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(Integer[] ids) {
		if (ids != null && ids.length > 0) {
			for (Integer id : ids) {
				bossBackTmallCacheService.delete(id);
				log.info("bossBackTmallCache delete:" + id);
			}
			ParameterListener.refresh("bossBackTmallCache");
			//ParameterListener.flushStatus("bossBackTmallCache");
		}
		return "success";
	}
	//导出excel
	@RequiresPermissions("boss:bossBackTmallCache:exportExcel")
	@RequestMapping("exportExcel")
	public void createExcel(HttpServletRequest req, HttpServletResponse resp)
			throws Exception, InterruptedException {
		Integer maxNumber = 1000000;
		Page<BossBackTmallCache> page = getPage(req);
		page.setPageNo(1);
		page.setPageSize(maxNumber);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(req);
		page = bossBackTmallCacheService.exportSearch(page, filters, maxNumber);
		if (page.getTotalCount() > maxNumber) {
			ExcelUtils.writeErrorMsg(resp, page.getTotalCount(), "导出数据不能超过100万条");
			return;
		}
		List<BossBackTmallCache> list = page.getResult();//获取数据
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		for (BossBackTmallCache bossBackTmallCache : list) {
			Map<String, Object> beanToMap = ExcelUtils.beanToMap(bossBackTmallCache);
			//ExcelUtils.showValue(beanToMap, "status", ParameterListener.freezeStr);
			mapList.add(beanToMap);
		}
		String title = "";
		
		
		String[] hearders = new String[] {
			  "供应商id",
			  "产品id",
			  "订单id逗号隔开",//
			  "计时时间",
			  "是否缓存",
			  "备注",//
			  "添加时间",
		};
		String[] fields = new String[] {
			  "providerId",
			  "categoryId",
			  "orderIds",//
			  "beginTime",
			  "isCache",
			  "remark",//
			  "addTime",
		};
		ExcelUtils.productExcel(resp, mapList, title, hearders, fields, log);
	}
}
