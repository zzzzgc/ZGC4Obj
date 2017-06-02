package xinxing.boss.admin.boss.other.cmd;

import java.util.Date;
import java.util.HashMap;
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
import xinxing.boss.admin.boss.other.domain.BossCustomerRoute;
import xinxing.boss.admin.boss.other.service.BossCustomerRouteService;
import xinxing.boss.admin.system.user.utils.UserUtil;
import xinxing.boss.admin.common.persistence.Page;
import xinxing.boss.admin.common.persistence.PropertyFilter;
import xinxing.boss.admin.common.web.BaseController;
import xinxing.boss.admin.common.utils.string.StringUtils;
import xinxing.boss.admin.common.utils.json.JsonUtils;
import xinxing.boss.admin.common.excel.ExcelUtils;
import xinxing.boss.admin.common.utils.http.HttpUtils;

@Controller
@RequestMapping(value = "boss/bossCustomerRoute")
public class BossCustomerRouteCmd extends BaseController {
	private static Logger log = LoggerFactory.getLogger(BossCustomerRouteCmd.class);
	@Autowired
	private BossCustomerRouteService bossCustomerRouteService;

	// 弹出框的方法
	@RequestMapping(value = "alertWindow")
	@ResponseBody
	public String alertWindow(HttpServletRequest request, BossCustomerRoute bossCustomerRoute, Integer[] ids, String method) {
		log.info("ids:" + JsonUtils.obj2Json(ids));
		return "success";
	}

	// 额外的方法
	@RequestMapping(value = "updateStatus", method = RequestMethod.POST)
	@ResponseBody
	public String updateStatus(HttpServletRequest request, Integer[] ids, Integer status) {
		if (ids == null || ids.length == 0 || status == null) {
			return "没有id";
		}
		if (status != null && status >= 0 && status <= 2) {
			for (Integer id : ids) {
				BossCustomerRoute bossCustomerRoute = bossCustomerRouteService.get(id);
				bossCustomerRoute.setStatus(status);
				update(bossCustomerRoute);
			}
		}
		return "success";
	}

	// 跳转页面
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list(HttpServletRequest request) {
		Map<String, String> reqMap = HttpUtils.getReqParams(request);
		if (reqMap == null) {
			reqMap = new HashMap<String, String>();
		}
		String page = reqMap.get("page");
		if (StringUtils.isEmpty(page)) {
			page = "List";
		}
		request.getSession().setAttribute("reqMap", reqMap);
		return "boss/bossCustomerRoute/bossCustomerRoute" + page;
	}

	// 获取数据到页面
	@RequestMapping(value = "bossCustomerRouteList")
	@ResponseBody
	public Map<String, Object> getConfigList(HttpServletRequest request) {
		Page<BossCustomerRoute> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		page = bossCustomerRouteService.search(page, filters);
		return getEasyUIData(page);
	}

	// 添加跳转
	@RequiresPermissions("boss:bossCustomerRoute:add")
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String create(Model model, BossCustomerRoute bossCustomerRoute) {
		model.addAttribute("bossCustomerRoute", bossCustomerRoute);
		return "boss/bossCustomerRoute/bossCustomerRouteAdd";
	}

	// 添加
	@RequiresPermissions("boss:bossCustomerRoute:add")
	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ResponseBody
	public String add(@Valid BossCustomerRoute bossCustomerRoute, Model model, HttpServletRequest req) {
		String[] customerIds = (req.getParameter("customerIds") == null ? "" : req.getParameter("customerIds")).split(",");
		String[] citys = (req.getParameter("citys") == null ? "" : req.getParameter("citys")).split(",");
		String[] productNums = (req.getParameter("productNums") == null ? "" : req.getParameter("productNums")).split(",");
		String[] provinces = (req.getParameter("provinces") == null ? "" : req.getParameter("provinces")).split(",");
		if (provinces.length > 1) {
			citys = new String[] { "0" };
		}
		String addIds = "";
		for (String province : provinces) {
			for (String productNum : productNums) {
				for (String city : citys) {
					for (String customerId : customerIds) {
						if (StringUtils.isNotBlank(city) && StringUtils.isNotBlank(customerId) && StringUtils.isNotBlank(productNum)) {
							BossCustomerRoute bcr = JsonUtils.json2Obj(JsonUtils.obj2Json(bossCustomerRoute), BossCustomerRoute.class);
							bcr.setId(null);
							bcr.setProvince(province);
							bcr.setCity(city);
							bcr.setCustomerId(Integer.parseInt(customerId));
							bcr.setProductNum(Integer.parseInt(productNum));
							bossCustomerRouteService.save(bcr);
							addIds = bcr.getId() + " ";
						}
					}
				}
			}
		}
		ParameterListener.refresh("bossCustomerRoute");
		ParameterListener.flushStatus("bossCustomerRoute");
		log.info("bossCustomerRoute add:" + addIds);
		return "success";
	}

	// 修改跳转
	@RequiresPermissions("boss:bossCustomerRoute:update")
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String update(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("bossCustomerRoute", bossCustomerRouteService.get(id));
		return "boss/bossCustomerRoute/bossCustomerRouteUpdate";
	}

	// 修改
	@RequiresPermissions("boss:bossCustomerRoute:update")
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody BossCustomerRoute bossCustomerRoute) {

		if ("全部".equals(bossCustomerRoute.getCity())) {
			bossCustomerRoute.setCity("0");
		}

		bossCustomerRouteService.update(bossCustomerRoute);
		ParameterListener.refresh("bossCustomerRoute");
		ParameterListener.flushStatus("bossCustomerRoute");
		return "success";
	}

	// 批量删除
	@RequiresPermissions("boss:bossCustomerRoute:delete")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(Integer[] ids) {
		if (ids != null && ids.length > 0) {
			for (Integer id : ids) {
				bossCustomerRouteService.delete(id);
				log.info("bossCustomerRoute delete:" + id);
			}
			ParameterListener.refresh("bossCustomerRoute");
			ParameterListener.flushStatus("bossCustomerRoute");
		}
		return "success";
	}

	// 导出excel
	@RequiresPermissions("boss:bossCustomerRoute:exportExcel")
	@RequestMapping("exportExcel")
	public void createExcel(HttpServletRequest request, HttpServletResponse response) throws Exception, InterruptedException {
		Page<BossCustomerRoute> page = getPage(request);
		page.setPageNo(1);
		page.setPageSize(100000);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		page = bossCustomerRouteService.search(page, filters);
		List<BossCustomerRoute> list = page.getResult();// 获取数据
		List<Map<String, Object>> mapList = new ArrayList<>();
		for (BossCustomerRoute bossCustomerRoute : list) {
			Map<String, Object> beanToMap = ExcelUtils.beanToMap(bossCustomerRoute);
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
		ExcelUtils.productExcel(response, mapList, title, hearders, fields, log);
	}
}
