package xinxing.boss.admin.boss.customer.cmd;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
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

import xinxing.boss.admin.boss.customer.domain.CustomerInfo;
import xinxing.boss.admin.boss.customer.domain.CustomerProductInfo;
import xinxing.boss.admin.boss.customer.service.CustomerProductInfoService;
import xinxing.boss.admin.boss.other.domain.DiscountChange;
import xinxing.boss.admin.boss.other.service.DiscountChangeService;
import xinxing.boss.admin.boss.provider.domain.ProductCategoryInfo;
import xinxing.boss.admin.boss.provider.domain.ProviderProductInfo;
import xinxing.boss.admin.boss.provider.service.ProductCategoryInfoService;
import xinxing.boss.admin.common.excel.ExcelUtils;
import xinxing.boss.admin.common.persistence.Page;
import xinxing.boss.admin.common.persistence.PropertyFilter;
import xinxing.boss.admin.common.utils.json.JsonUtils;
import xinxing.boss.admin.common.utils.parameter.ParameterListener;
import xinxing.boss.admin.common.web.BaseController;
import xinxing.boss.admin.system.user.domain.User;
import xinxing.boss.admin.system.user.utils.UserUtil;

@Controller
@RequestMapping(value = "boss/customerProductInfo")
public class CustomerProductInfoCmd extends BaseController {
	private static Logger log = LoggerFactory.getLogger(CustomerProductInfoCmd.class);

	@Autowired
	private CustomerProductInfoService customerProductInfoService;
	@Autowired
	private ProductCategoryInfoService productCategoryInfoService;
	@Autowired
	private DiscountChangeService discountChangeService;

	/**
	 * 默认页面
	 */
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list(Integer customerId, Integer categoryId, HttpServletRequest request) {
		request.setAttribute("categoryId", categoryId);
		request.setAttribute("customerId", customerId);
		return "boss/customerProductInfo/customerProductInfoList";
	}

	/**
	 * 获取所有订单信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "customerProductInfoList")
	@ResponseBody
	public Map<String, Object> getConfigList(HttpServletRequest request) {
		Page<CustomerProductInfo> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		page = customerProductInfoService.search(page, filters);
		return getEasyUIData(page);
	}

	/**
	 * 添加订单信息跳转
	 * 
	 * @param model
	 * @return
	 */
	@RequiresPermissions("boss:customerProductInfo:add")
	@RequestMapping(value = "create")
	public String create(Model model, String customerId) {
		model.addAttribute("customerId", customerId);
		return "boss/customerProductInfo/customerProductInfoAdd";
	}

	/**
	 * 添加订单信息
	 * 
	 * @param dict
	 * @param model
	 */
	@RequiresPermissions("boss:customerProductInfo:add")
	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ResponseBody
	public String add(@Valid CustomerProductInfo customerProductInfo, Model model, HttpServletRequest request) {
		String[] values = request.getParameterValues("categoryIds");

		for (String value : values) {
			CustomerProductInfo customerInfo = new CustomerProductInfo(customerProductInfo);
			ProductCategoryInfo categoryInfo = productCategoryInfoService.get(Integer.parseInt(value));
			customerInfo.setCategoryId(categoryInfo.getId());
			customerInfo.setProductName(categoryInfo.getCategoryName());
			customerInfo.setSellPrice(new BigDecimal(categoryInfo.getStandarPrice()));
			customerProductInfoService.save(customerInfo);
			log.info("customerProductInfo add:" + customerInfo.getId());
		}
		ParameterListener.refresh("customerProductInfo");
		return "success";
	}

	/**
	 * 添加折扣信息跳转
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "discount", method = RequestMethod.GET)
	public String discount(Model model) {
		return "boss/customerProductInfo/customerProductInfoDiscount";
	}

	/**
	 * 添加折扣信息
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "updateDiscount", method = RequestMethod.GET)
	@ResponseBody
	public String discount(HttpServletRequest request) {
		Double discount = Double.parseDouble(request.getParameterValues("discount")[0]);
		String[] ids = request.getParameterValues("ids");
		String[] standardPrices = request.getParameterValues("standardPrices");
		try {
			for (int i = 0; i < ids.length; i++) {
				try {
					int id = Integer.parseInt(ids[i]);
					double standardPrice = Double.parseDouble(standardPrices[i]);
					BigDecimal sellprice = BigDecimal.valueOf(discount * standardPrice);

					CustomerProductInfo cpi = customerProductInfoService.get(id);
					User user = UserUtil.getCurrentUser();
					BigDecimal standardPriceBig = BigDecimal.valueOf(standardPrice);
					BigDecimal discountBig = BigDecimal.valueOf(discount);
					BigDecimal sellPriceBig = cpi.getSellPrice();
					BigDecimal divide = new BigDecimal(sellPriceBig.doubleValue() / standardPriceBig.doubleValue());
					CustomerInfo pi = ParameterListener.customerInfoMap.get(cpi.getCustomerId());
					DiscountChange disc = new DiscountChange(pi.getCustomerName(), null, new Date(), user.getId(), cpi.getCategoryId(), divide,
							discountBig, user.getName(), 0);
					discountChangeService.save(disc);

					customerProductInfoService.updateSellPriceByDiscount(id, sellprice);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (NumberFormatException e) {
			log.error(e.getMessage());
			return "fail";
		}
		ParameterListener.refresh("customerProductInfo");
		return "success";
	}

	/**
	 * 修改订单信息跳转
	 */
	@RequiresPermissions("boss:customerProductInfo:update")
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String update(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("customerProductInfo", customerProductInfoService.get(id));
		return "boss/customerProductInfo/customerProductInfoUpdate";
	}

	/**
	 * 修改订单信息
	 * 
	 * @param tmallOrder
	 * @param model
	 * @return
	 */
	@RequiresPermissions("boss:customerProductInfo:update")
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody CustomerProductInfo customerProductInfo) {

		try {
			CustomerProductInfo ppi = customerProductInfoService.get(customerProductInfo.getId());
			if (ppi != null && !(ppi.getSellPrice() + "").equals(customerProductInfo.getSellPrice() + "")) {
				BigDecimal oldCost = ppi.getSellPrice();
				BigDecimal newCost = customerProductInfo.getSellPrice();
				User user = UserUtil.getCurrentUser();
				ProductCategoryInfo productCategoryInfo = ParameterListener.productCategoryInfoMap.get(ppi.getCategoryId());
				Double standardPrice = productCategoryInfo.getStandarPrice();
				CustomerInfo pi = ParameterListener.customerInfoMap.get(ppi.getCustomerId());
				DiscountChange disc = new DiscountChange(pi.getCustomerName(), null, new Date(), user.getId(), ppi.getCategoryId(),
						BigDecimal.valueOf(oldCost.doubleValue() / standardPrice), BigDecimal.valueOf(newCost.doubleValue() / standardPrice),
						user.getName(),0);
				discountChangeService.save(disc);
			}
			customerProductInfoService.getEntityDao().getSession().clear();
			customerProductInfoService.update(customerProductInfo);
		} catch (Exception e) {
			log.error(e.getMessage());
			return e.getMessage();
		}
		ParameterListener.refresh("customerProductInfo");
		return "success";
	}

	/**
	 * 修改订单状态 开通/冻结
	 * 
	 * @param tmallOrder
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "updateStatus", method = RequestMethod.POST)
	@ResponseBody
	public String updateStatus(HttpServletRequest request) {
		String[] ids = request.getParameterValues("ids");
		String state = request.getParameter("state");
		if (ids == null || StringUtils.isBlank(state)) {
			return "error";
		}
		log.info(UserUtil.getCurrentUser().getLoginName() + ",updateStatus更新状态:ids" + JsonUtils.obj2Json(ids) + ",status:" + state);
		for (String id : ids) {
			CustomerProductInfo customerProductInfo = customerProductInfoService.get(Integer.parseInt(id));
			log.info(UserUtil.getCurrentUser().getId()+"-"+UserUtil.getCurrentUser().getName()+"修改前:" + customerProductInfo.getStatus() + ",修改后:" + state + "---" + JsonUtils.obj2Json(customerProductInfo));
			customerProductInfo.setStatus(Integer.parseInt(state));
			update(customerProductInfo);
		}
		ParameterListener.refresh("customerProductInfo");
		return "success";
	}

	/**
	 * 删除订单
	 * 
	 * @param id
	 * @return
	 */
	@RequiresPermissions("boss:customerProductInfo:delete")
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Integer id) {
		customerProductInfoService.delete(id);
		log.info("customerProductInfo delete:" + id);
		ParameterListener.refresh("customerProductInfo");
		return "success";
	}

	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("exportExcel")
	public void createExcel(HttpServletRequest request, HttpServletResponse response) {

		Page<CustomerProductInfo> page = getPage(request);
		page.setPageNo(1);
		page.setPageSize(10000);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		page = customerProductInfoService.search(page, filters);
		List<CustomerProductInfo> list = page.getResult();
		Map<Integer, CustomerInfo> customerInfoMap = ParameterListener.customerInfoMap;
		Map<Integer, ProductCategoryInfo> productCategoryInfoMap = ParameterListener.productCategoryInfoMap;
		List<Map<String, Object>> mapList = new ArrayList<>();
		for (CustomerProductInfo customerProductInfo : list) {
			try {
				Map<String, Object> beanToMap = ExcelUtils.beanToMap(customerProductInfo);
				ProductCategoryInfo productCategoryInfo = productCategoryInfoMap.get(customerProductInfo.getCategoryId());
				CustomerInfo customerInfo = customerInfoMap.get(customerProductInfo.getCustomerId());

				beanToMap.put("customerId", customerInfo.getId());
				beanToMap.put("customerName", customerInfo.getCustomerName());
				beanToMap.put("categoryId", productCategoryInfo.getId());
				beanToMap.put("productName", productCategoryInfo.getCategoryName());
				beanToMap.put("standardPrice", productCategoryInfo.getStandarPrice());
				beanToMap.put("productArea", productCategoryInfo.getArea());
				beanToMap.put("province", productCategoryInfo.getProvince());
				double discount = (((BigDecimal) beanToMap.get("sellPrice")).doubleValue() / productCategoryInfo.getStandarPrice());
				String result = String.format("%.4f", discount);
				beanToMap.put("discount", result);
				ExcelUtils.showValue(beanToMap, "status", "冻结", "正常");
				ExcelUtils.showValue(beanToMap, "productArea", "本省", "全国");
				mapList.add(beanToMap);
			} catch (Exception e) {
				log.error("error", e);
				String msg = String.format("{error:'%s/%s'}", customerProductInfo.getId());// string带参数，利用String.format
				log.error(msg, e);
				log.info("系统异常:" + e);
			}
		}
		String title = "采购商产品信息";
		String[] hearders = new String[] { "采购商名称", "产品名", "运营商", "省份", "使用区域", "销售价", "标准价", "折扣率", "状态", "分类id", "采购商对接产品id", "采购商id" };// 表头数组
		String[] fields = new String[] { "customerName", "productName", "mobileOperator", "province", "productArea", "sellPrice", "standardPrice",
				"discount", "status", "categoryId", "id", "customerId" };
		ExcelUtils.productExcel(response, mapList, title, hearders, fields, log);

	}

	/**
	 * 批量删除
	 * 
	 * @return
	 */
	@RequestMapping("delAll")
	@ResponseBody
	public String delAll(HttpServletRequest request) {
		String[] ids = request.getParameterValues("ids");
		for (String id : ids) {
			delete(Integer.parseInt(id));
		}
		return "success";
	}

	/* ---------------------------------------------------------电子券------------------------------------------------------------ */
	/**
	 * 默认页面
	 */
	@RequestMapping("productlist")
	public String eleCouponslist(Integer customerId, Integer categoryId, HttpServletRequest request) {
		request.setAttribute("categoryId", categoryId);
		request.setAttribute("customerId", customerId);
		return "boss/yc/YCCustomerProductInfoList";
	}

}
