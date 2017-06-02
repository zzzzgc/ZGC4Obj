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
import xinxing.boss.admin.boss.other.domain.BossOperatorCloseConfig;
import xinxing.boss.admin.boss.other.domain.BossOperatorCloseConfig;
import xinxing.boss.admin.boss.other.service.BossOperatorCloseConfigService;
import xinxing.boss.admin.system.user.utils.UserUtil;
import xinxing.boss.admin.common.persistence.Page;
import xinxing.boss.admin.common.persistence.PropertyFilter;
import xinxing.boss.admin.common.web.BaseController;
import xinxing.boss.admin.common.utils.string.StringUtils;
import xinxing.boss.admin.common.utils.json.JsonUtils;
import xinxing.boss.admin.common.excel.ExcelUtils;
import xinxing.boss.admin.common.utils.http.HttpUtils;

@Controller
@RequestMapping(value = "boss/bossOperatorCloseConfig")
public class BossOperatorCloseConfigCmd extends BaseController {
	private static Logger log = LoggerFactory.getLogger(BossOperatorCloseConfigCmd.class);
	@Autowired
	private BossOperatorCloseConfigService bossOperatorCloseConfigService;

	// 弹出框的方法
	@RequestMapping(value = "alertWindow")
	@ResponseBody
	public String alertWindow(HttpServletRequest req, BossOperatorCloseConfig bossOperatorCloseConfig, Integer[] ids, String method) {
		log.info("ids:" + JsonUtils.obj2Json(ids));
		return "success";
	}

	// 额外的方法
	@RequestMapping(value = "updateStatus", method = RequestMethod.POST)
	@ResponseBody
	public String updateStatus(HttpServletRequest req, Integer[] ids, Integer status) {
		if (ids == null || ids.length == 0 || status == null) {
			return "没有id";
		}
		if (status != null && status >= 0 && status <= 2) {
			for (Integer id : ids) {
				BossOperatorCloseConfig bossOperatorCloseConfig = bossOperatorCloseConfigService.get(id);
				bossOperatorCloseConfig.setStatus(status);
				update(bossOperatorCloseConfig);
			}
		}
		return "success";
	}

	// 跳转页面
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list(HttpServletRequest req, Integer id, BossOperatorCloseConfig bossOperatorCloseConfig, Model model) {
		Map<String, String> reqMap = HttpUtils.getReqParams(req);
		if (reqMap == null) {
			reqMap = new HashMap<String, String>();
		}
		String page = reqMap.get("page");
		if (StringUtils.isEmpty(page)) {
			page = "List";
		}
		if (id != null) {
			bossOperatorCloseConfig = bossOperatorCloseConfigService.get(id);
		}
		model.addAttribute("reqMap", reqMap);
		model.addAttribute("action", (id != null ? "update" : "add"));
		model.addAttribute("bossOperatorCloseConfig", bossOperatorCloseConfig);
		return "boss/bossOperatorCloseConfig/bossOperatorCloseConfig" + page;
	}

	// 获取数据到页面
	@RequestMapping(value = "bossOperatorCloseConfigList")
	@ResponseBody
	public Map<String, Object> getConfigList(HttpServletRequest req) {
		Page<BossOperatorCloseConfig> page = getPage(req);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(req);
		page = bossOperatorCloseConfigService.search(page, filters);
		return getEasyUIData(page);
	}

	// 添加
	@RequiresPermissions("boss:bossOperatorCloseConfig:add")
	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ResponseBody
	public String add(@Valid BossOperatorCloseConfig bossOperatorCloseConfig, Model model, String batchs, HttpServletRequest req) {
		String[] customerIds = (req.getParameter("customerIds") == null ? "" : req.getParameter("customerIds")).split(",");
		String[] citys = (req.getParameter("citys") == null ? "" : req.getParameter("citys")).split(",");
		String[] sizes = (req.getParameter("sizes") == null ? "" : req.getParameter("sizes")).split(",");
		String addIds = "";

		for (String size : sizes) {
			for (String city : citys) {
				for (String customerId : customerIds) {
					if (StringUtils.isNotBlank(city) && StringUtils.isNotBlank(customerId) && StringUtils.isNotBlank(size)) {
						System.out.println(city+","+customerId+","+size);
						BossOperatorCloseConfig bcr = JsonUtils.json2Obj(JsonUtils.obj2Json(bossOperatorCloseConfig), BossOperatorCloseConfig.class);
						bcr.setId(null);
						bcr.setCity(city);
						bcr.setCustomerId(Integer.parseInt(customerId));
						bcr.setSize(Integer.parseInt(size));
						bossOperatorCloseConfigService.save(bcr);
						addIds = bcr.getId() + " ";
					}
				}
			}
		}
		ParameterListener.refresh("bossOperatorCloseConfig");
		ParameterListener.flushStatus("init_operator_close_config");
		log.info("bossOperatorCloseConfig add:" + addIds);
		return "success";
	}

	// 修改
	@RequiresPermissions("boss:bossOperatorCloseConfig:update")
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody BossOperatorCloseConfig bossOperatorCloseConfig) {
		bossOperatorCloseConfigService.update(bossOperatorCloseConfig);
		ParameterListener.refresh("bossOperatorCloseConfig");
		ParameterListener.flushStatus("init_operator_close_config");
		return "success";
	}

	// 批量删除
	@RequiresPermissions("boss:bossOperatorCloseConfig:delete")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(Integer[] ids) {
		if (ids != null && ids.length > 0) {
			for (Integer id : ids) {
				bossOperatorCloseConfigService.delete(id);
				log.info("bossOperatorCloseConfig delete:" + id);
			}
			ParameterListener.refresh("bossOperatorCloseConfig");
			ParameterListener.flushStatus("init_operator_close_config");
		}
		return "success";
	}

	// 导出excel
	@RequiresPermissions("boss:bossOperatorCloseConfig:exportExcel")
	@RequestMapping("exportExcel")
	public void createExcel(HttpServletRequest req, HttpServletResponse resp) throws Exception, InterruptedException {
		Integer maxNumber = 100000;
		Page<BossOperatorCloseConfig> page = getPage(req);
		page.setPageNo(1);
		page.setPageSize(maxNumber);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(req);
		page = bossOperatorCloseConfigService.exportSearch(page, filters, maxNumber);
		if (page.getTotalCount() > maxNumber) {
			ExcelUtils.writeErrorMsg(resp, page.getTotalCount(), "导出数据不能超过10万条");
			return;
		}
		List<BossOperatorCloseConfig> list = page.getResult();// 获取数据
		List<Map<String, Object>> mapList = new ArrayList<>();
		for (BossOperatorCloseConfig bossOperatorCloseConfig : list) {
			Map<String, Object> beanToMap = ExcelUtils.beanToMap(bossOperatorCloseConfig);
			// ExcelUtils.showValue(beanToMap, "status", ParameterListener.freezeStr);
			mapList.add(beanToMap);
		}
		String title = "";
		String[] hearders = new String[] { "", "", ""//
				, "", "", ""//
				, "", "", ""//
				, "", "", ""//
				, "", "", "" };
		String[] fields = new String[] { "", "", ""//
				, "", "", ""//
				, "", "", ""//
				, "", "", ""//
				, "", "", "" };
		ExcelUtils.productExcel(resp, mapList, title, hearders, fields, log);
	}
}
