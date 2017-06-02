package xinxing.boss.admin.boss.provider.cmd;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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

import xinxing.boss.admin.boss.customer.domain.CustomerProductInfo;
import xinxing.boss.admin.boss.customer.service.CustomerProductInfoService;
import xinxing.boss.admin.boss.other.domain.DiscountChange;
import xinxing.boss.admin.boss.other.service.DiscountChangeService;
import xinxing.boss.admin.boss.provider.domain.ProductCategoryInfo;
import xinxing.boss.admin.boss.provider.domain.ProviderInfo;
import xinxing.boss.admin.boss.provider.domain.ProviderProductInfo;
import xinxing.boss.admin.boss.provider.service.ProductCategoryInfoService;
import xinxing.boss.admin.boss.provider.service.ProviderProductInfoService;
import xinxing.boss.admin.common.excel.ExcelUtils;
import xinxing.boss.admin.common.persistence.Page;
import xinxing.boss.admin.common.persistence.PropertyFilter;
import xinxing.boss.admin.common.utils.base.BaseUtil;
import xinxing.boss.admin.common.utils.json.JsonUtils;
import xinxing.boss.admin.common.utils.parameter.ParameterListener;
import xinxing.boss.admin.common.utils.time.DateUtils;
import xinxing.boss.admin.common.web.BaseController;
import xinxing.boss.admin.system.user.domain.User;
import xinxing.boss.admin.system.user.utils.UserUtil;

@Controller
@RequestMapping(value = "boss/providerProductInfo")
public class ProviderProductInfoCmd extends BaseController {
	private static Logger log = LoggerFactory.getLogger(ProviderProductInfoCmd.class);

	@Autowired
	private ProviderProductInfoService providerProductInfoService;
	@Autowired
	private ProductCategoryInfoService productCategoryInfoService;
	@Autowired
	private CustomerProductInfoService customerProductInfoService;
	@Autowired
	private DiscountChangeService discountChangeService;

	/**
	 * 是否发送短信批量更改
	 * 
	 * @param tmallOrder
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "updateIsSendMsg", method = RequestMethod.POST)
	@ResponseBody
	public String updateStatus(HttpServletRequest request, Integer[] ids, Integer status) {
		if (ids == null) {
			return "没有id";
		}
		if (status != null && status >= 0 && status <= 2) {
			for (Integer id : ids) {
				ProviderProductInfo providerProductInfo = providerProductInfoService.get(id);
				providerProductInfo.setIsSendMsg(status);
				update(providerProductInfo);
			}
			log.info(UserUtil.getCurrentUser().getLoginName() + ",updateStatus更新状态:ids" + ids + ",status:" + status);
		}
		return "success";
	}

	/**
	 * 默认页面
	 */
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list(Integer providerId, Integer categoryId, HttpServletRequest request) {
		request.setAttribute("providerId", providerId);
		request.setAttribute("categoryId", categoryId);
		return "boss/providerProductInfo/providerProductInfoList";
	}

	/**
	 * 获取所有订单信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "providerProductInfoList")
	@ResponseBody
	public Map<String, Object> getConfigList(HttpServletRequest request) {
		Page<ProviderProductInfo> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		page = providerProductInfoService.search(page, filters);
		return getEasyUIData(page);
	}

	/**
	 * 获得供应商产品 通过供应商id
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "getProviderProductsByProviderIds")
	@ResponseBody
	public List<ProviderProductInfo> getProviderProductsByProviderIds(Model model, String[] providerIds) {
		Map<Integer, ProviderProductInfo> providerProductMap = ParameterListener.providerProductMap;

		List<ProviderProductInfo> ppiList = new ArrayList<>();
		for (Entry<Integer, ProviderProductInfo> entry : providerProductMap.entrySet()) {
			for (String pid : providerIds) {
				if ((entry.getValue().getProviderId() + "").equals(pid)) {
					ppiList.add(entry.getValue());
					break;
				}
			}
		}
		log.info(JsonUtils.obj2Json(ppiList));
		log.info("-------");
		
		return ppiList;
	}

	/**
	 * 添加订单信息跳转
	 * 
	 * @param model
	 * @return
	 */
	@RequiresPermissions("boss:providerProductInfo:add")
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String create(Model model, Integer providerId) {
		model.addAttribute("providerId", providerId);
		return "boss/providerProductInfo/providerProductInfoAdd";
	}

	/**
	 * 添加订单信息
	 * 
	 * @param dict
	 * @param model
	 */
	@RequiresPermissions("boss:providerProductInfo:add")
	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ResponseBody
	public String add(@Valid ProviderProductInfo providerProductInfo, Model model) {
		log.info("providerProductInfo add:" + ",--内容:" + JsonUtils.obj2Json(providerProductInfo));
		providerProductInfo.setAddTime(new Date());
		providerProductInfoService.save(providerProductInfo);
		log.info("providerProductInfo add:" + providerProductInfo.getId());
		ParameterListener.flushStatus("init_provider");
		ParameterListener.refresh("providerProduct");
		return "success";
	}

	/**
	 * 添加订单信息跳转
	 * 
	 * @param model
	 * @return
	 */
	@RequiresPermissions("boss:providerProductInfo:add")
	@RequestMapping(value = "createBatch")
	public String createBatch(Model model, Integer providerId) {
		model.addAttribute("providerId", providerId);
		return "boss/providerProductInfo/providerProductInfoAddBatch";
	}

	/**
	 * 添加订单信息
	 * 
	 * @param dict
	 * @param model
	 */
	// @RequiresPermissions("boss:providerProductInfo:addBatch")
	@RequestMapping(value = "addBatch")
	@ResponseBody
	public String addBatch(@Valid ProviderProductInfo providerProductInfo, Model model, Integer categoryId, String categoryIds) {
		String[] split = categoryIds.split(",");
		List<Integer> cidList = new ArrayList<>();
		for (int i = 0; i < split.length; i++) {
			cidList.add(Integer.parseInt(split[i]));
		}
		Date date = new Date();
		ParameterListener.refresh("product");
		Map<Integer, ProductCategoryInfo> productCategoryInfoMap = ParameterListener.productCategoryInfoMap;
		for (Integer cid : cidList) {
			ProductCategoryInfo pci = productCategoryInfoMap.get(cid);
			ProviderProductInfo ppi = new ProviderProductInfo();
			String productCode = "";
			Integer productNum = pci.getProductNum();
			Integer productUnit = pci.getProductUnit();
			if (productUnit == 1) {// M
				productCode += productNum;
			} else if (productUnit == 2) {// G
				productCode += productNum * 1000;
			} else if (productUnit == 3) {//
				productCode += productNum;
			}
			ppi.setProductCode(productCode);
			ppi.setProductName(pci.getCategoryName());
			ppi.setAddTime(date);
			ppi.setAllowCustomer(providerProductInfo.getAllowCustomer());
			ppi.setCategoryId(pci.getId());
			ppi.setCostPrice(0);
			ppi.setForbidCustomer(providerProductInfo.getForbidCustomer());
			ppi.setIsSecondChannel(providerProductInfo.getIsSecondChannel());
			ppi.setIsSendMsg(providerProductInfo.getIsSendMsg());
			ppi.setMobileOperator(providerProductInfo.getMobileOperator());
			ppi.setPriority(providerProductInfo.getPriority());
			ppi.setProviderId(providerProductInfo.getProviderId());
			ppi.setProvince(providerProductInfo.getProvince());
			ppi.setRemark(providerProductInfo.getRemark());
			ppi.setStatus(providerProductInfo.getStatus());
			ppi.setValidityTime(providerProductInfo.getValidityTime());
			providerProductInfoService.save(ppi);
			log.info("providerProductInfo add:" + providerProductInfo.getId());
		}
		ParameterListener.flushStatus("init_provider");
		ParameterListener.refresh("providerProduct");
		return "success";
	}

	/**
	 * 修改订单信息跳转
	 */
	@RequiresPermissions("boss:providerProductInfo:update")
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String update(@PathVariable("id") Integer id, Model model) {
		ProviderProductInfo info = providerProductInfoService.get(id);

		// 京东卡 供货商不允许设置只允许
		if (info.getProductName().contains("京东")) {
			model.addAttribute("isCard", 1);
		} else {
			model.addAttribute("isCard", 0);
		}
		model.addAttribute("providerProductInfo", info);
		return "boss/providerProductInfo/providerProductInfoUpdate";
	}

	/**
	 * 修改订单信息
	 * 
	 * @param tmallOrder
	 * @param model
	 * @return
	 */
	@RequiresPermissions("boss:providerProductInfo:update")
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody ProviderProductInfo providerProductInfo) {

		// 如果是京东卡 ，则查询数据库把原来的数据覆盖掉新的
		if (providerProductInfo.getProductName().contains("京东卡")) {
			ProviderProductInfo info = providerProductInfoService.get(providerProductInfo.getId());
			providerProductInfo.setAllowCustomer(info.getAllowCustomer());
		}

		ProviderProductInfo ppi = ParameterListener.providerProductMap.get(providerProductInfo.getId());
		if (ppi != null && ppi.getCostPrice() != providerProductInfo.getCostPrice()) {
			double oldCost = ppi.getCostPrice();
			double newCost = providerProductInfo.getCostPrice();
			User user = UserUtil.getCurrentUser();
			ProductCategoryInfo productCategoryInfo = ParameterListener.productCategoryInfoMap.get(ppi.getCategoryId());
			ProviderInfo pi = ParameterListener.providerInfoMap.get(ppi.getProviderId());
			Double standardPrice = productCategoryInfo.getStandarPrice();
			DiscountChange disc = new DiscountChange(pi.getProviderName(), null, new Date(), user.getId(), ppi.getCategoryId(),
					BigDecimal.valueOf(oldCost / standardPrice), BigDecimal.valueOf(newCost / standardPrice), user.getName(), 1);
			discountChangeService.save(disc);

		}

		providerProductInfoService.update(providerProductInfo);
		ParameterListener.flushStatus("init_provider");
		ParameterListener.refresh("providerProduct");
		return "success";
	}

	/**
	 * 批量修改供货商产品状态 开通/冻结
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
		try {
			for (String id : ids) {
				ProviderProductInfo providerProductInfo = providerProductInfoService.get(Integer.parseInt(id));
				log.info(UserUtil.getCurrentUser().getId() + "-" + UserUtil.getCurrentUser().getName() + "修改前:" + providerProductInfo.getStatus()
						+ ",修改后:" + state + "---" + JsonUtils.obj2Json(providerProductInfo));
				providerProductInfo.setStatus(Integer.parseInt(state));
				update(providerProductInfo);
			}
		} catch (Exception e) {
			log.error(JsonUtils.obj2Json(ids) + "--" + e.getMessage());
			return e.getMessage();
		}
		return "success";
	}

	/**
	 * 删除订单
	 * 
	 * @param id
	 * @return
	 */
	@RequiresPermissions("boss:providerProductInfo:delete")
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Integer id) {
		providerProductInfoService.delete(id);
		log.info("providerProductInfo delete:" + id);
		ParameterListener.flushStatus("init_provider");
		ParameterListener.refresh("providerProduct");
		return "success";
	}

	/**
	 * 获取对应产品列表
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "getProductCategoryInfo")
	@ResponseBody
	public List<Object> getProductCategoryInfo(String operator, String province, String useArea, String name, String id) {
		List<ProductCategoryInfo> list = productCategoryInfoService.getOperator(operator, province, useArea, name);
		List<Object> returnList = new ArrayList();
		boolean flag = true;
		if ("provider".equals(name.trim())) {
			List<ProviderProductInfo> providerList = providerProductInfoService.getProviderProductInfoByProviderId(id);
			for (ProductCategoryInfo productCategoryInfo : list) {
				// 判断是否已存在相同的产品类别
				for (ProviderProductInfo providerProduct : providerList) {
					if (providerProduct.getCategoryId() == productCategoryInfo.getId()) {
						flag = false;
						break;
					}
				}
				if (flag == true && productCategoryInfo.getPid() == null) {
					returnList.add(productCategoryInfo);
				} else if (flag == false) {
					flag = true;
				}
			}
		} else if ("customer".equals(name.trim())) {
			List<CustomerProductInfo> customerList = customerProductInfoService.getCustomerProductInfoByCustomerId(id);
			for (ProductCategoryInfo productCategoryInfo : list) {
				for (CustomerProductInfo customerProductInfo : customerList) {
					if (customerProductInfo.getCategoryId() == productCategoryInfo.getId()) {
						flag = false;
						break;
					}
				}
				if (flag == true && productCategoryInfo.getPid() == null) {
					returnList.add(productCategoryInfo);
				} else if (flag == false) {
					flag = true;
				}
			}
		}

		log.info("ProductCategoryInfo list.size:" + returnList.size());
		return returnList;
	}

	/**
	 * 根据运营商获取对应产品列表
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "getProductCategoryInfoPro")
	@ResponseBody
	public List<ProductCategoryInfo> getProductCategoryInfo(String operator) {
		List<ProductCategoryInfo> list = productCategoryInfoService.getOperator(operator);
		log.info("ProductCategoryInfo list.size:" + list.size());
		return list;
	}

	/**
	 * 添加折扣信息跳转
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "discount", method = RequestMethod.GET)
	public String discount(Model model) {
		return "boss/providerProductInfo/providerProductInfoDiscount";
	}

	/**
	 * 修改折扣
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
				int id = Integer.parseInt(ids[i]);
				double standardPrice = Double.parseDouble(standardPrices[i]);
				double costPrice = discount * standardPrice;

				ProviderProductInfo ppi = ParameterListener.providerProductMap.get(id);
				ProviderInfo pi = ParameterListener.providerInfoMap.get(ppi.getProviderId());
				User user = UserUtil.getCurrentUser();
				DiscountChange disc = new DiscountChange(pi.getProviderName(), null, new Date(), user.getId(), ppi.getCategoryId(),
						BigDecimal.valueOf(ppi.getCostPrice() / standardPrice), BigDecimal.valueOf(discount), user.getName(), 1);
				discountChangeService.save(disc);
				providerProductInfoService.updateSellPriceByDiscount(id, costPrice);

			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return "fail";
		}
		ParameterListener.flushStatus("init_provider");
		ParameterListener.refresh("providerProduct");
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
	public void createExcel(HttpServletRequest request, HttpServletResponse response) throws ParseException {
		Page<ProviderProductInfo> page = getPage(request);
		page.setPageNo(1);
		page.setPageSize(1000000);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		page = providerProductInfoService.search(page, filters);
		List<ProviderProductInfo> list = page.getResult();
		Map<Integer, ProviderInfo> providerInfoMap = ParameterListener.providerInfoMap;
		Map<Integer, ProductCategoryInfo> productCategoryInfoMap = ParameterListener.productCategoryInfoMap;
		List<Map<String, Object>> mapList = new ArrayList<>();
		for (ProviderProductInfo providerProductInfo : list) {
			try {
				Map<String, Object> beanToMap = ExcelUtils.beanToMap(providerProductInfo);
				ProductCategoryInfo productCategoryInfo = productCategoryInfoMap.get(providerProductInfo.getCategoryId());
				ProviderInfo providerInfo = providerInfoMap.get(providerProductInfo.getProviderId());
				if (providerInfo == null)
					continue;
				beanToMap.put("providerId", providerInfo.getId());
				beanToMap.put("providerName", providerInfo.getProviderName());
				beanToMap.put("categoryId", productCategoryInfo.getId());
				beanToMap.put("productName", productCategoryInfo.getCategoryName());
				beanToMap.put("standardPrice", productCategoryInfo.getStandarPrice());
				beanToMap.put("productArea", productCategoryInfo.getArea());
				beanToMap.put("addTime", DateUtils.formatDate((Date) beanToMap.get("addTime"), "yyyy-MM-dd HH:mm:ss"));
				beanToMap.put(
						"discount",
						productCategoryInfo.getStandarPrice() != null && productCategoryInfo.getStandarPrice() != 0 ? providerProductInfo
								.getCostPrice() / productCategoryInfo.getStandarPrice() : 0);
				ExcelUtils.showValue(beanToMap, "status", "", "使用中", "已下架");
				ExcelUtils.showValue(beanToMap, "productArea", "本省", "全国");
				mapList.add(beanToMap);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
			}
		}
		String title = "供应商产品信息";
		String[] hearders = new String[] { "供应商名称", "产品名称", "成本价", "标准价", "折扣率", "省份", "使用区域", "状态", "运营商", "添加时间", "通道优先级", "供货商产品编号", "供货商id",
				"产品id", "有效期", "备注" };// 表头数组
		String[] fields = new String[] { "providerName", "productName", "costPrice", "standardPrice", "discount", "province", "productArea",
				"status", "mobileOperator", "addTime", "priority", "productCode", "providerId", "categoryId", "validityTime", "remark" };
		ExcelUtils.productExcel(response, mapList, title, hearders, fields, log);
	}

	/**
	 * 渠道部要求的产品正常/供应商正常/供应商产品正常的产品列表界面 谈判专用
	 */
	@RequestMapping(value = "listTalk", method = RequestMethod.GET)
	public String listTalk(Integer providerId, Integer categoryId, HttpServletRequest request) {
		return "boss/providerProductInfo/providerProductInfoListTalk";
	}

	/**
	 * 渠道部要求的产品正常/供应商正常/供应商产品正常的产品列表界面 谈判专用
	 * 
	 * @return
	 */
	@RequestMapping(value = "providerProductInfoListTalk")
	@ResponseBody
	public Map<String, Object> providerProductInfoListTalk(HttpServletRequest request) {
		Page<ProviderProductInfo> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		normalOfcategoryIdAndProviderId(filters);
		try {
			page = providerProductInfoService.search(page, filters);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return getEasyUIData(page);
	}

	/**
	 * 添加过滤条件 产品正常/供应商正常/供应商产品正常
	 * 
	 * @param filters
	 */
	private void normalOfcategoryIdAndProviderId(List<PropertyFilter> filters) {
		StringBuffer categoryIdStr = new StringBuffer(100);
		StringBuffer providerIdStr = new StringBuffer(100);
		Map<Integer, ProductCategoryInfo> productCategoryInfoMap = ParameterListener.productCategoryInfoMap;
		for (Map.Entry<Integer, ProductCategoryInfo> entry : productCategoryInfoMap.entrySet()) {
			if (entry.getValue().getStatus() == 1)// 状态正常
				categoryIdStr.append(entry.getKey()).append(",");
		}
		Map<Integer, ProviderInfo> providerInfoMap = ParameterListener.providerInfoMap;
		for (Map.Entry<Integer, ProviderInfo> entry : providerInfoMap.entrySet()) {
			if (entry.getValue().getStatus() == 1)// 状态正常
				providerIdStr.append(entry.getKey()).append(",");
		}
		if (categoryIdStr.length() == 0) {
			categoryIdStr.append("666666,");
		}
		if (providerIdStr.length() == 0) {
			providerIdStr.append("666666,");
		}
		// filters.add(new PropertyFilter("INI_categoryId",
		// BaseUtil.subLastWord(categoryIdStr.toString()))); //产品大类状态没用!!
		filters.add(new PropertyFilter("INI_providerId", BaseUtil.subLastWord(providerIdStr.toString())));
		filters.add(new PropertyFilter("EQI_status", "1"));// 状态正常
	}

	/**
	 * 谈判专用 导出excel
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("exportExcelTalk")
	public void exportExcelTalk(HttpServletRequest request, HttpServletResponse response) throws ParseException {
		Page<ProviderProductInfo> page = getPage(request);
		page.setPageNo(1);
		page.setPageSize(1000000);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		normalOfcategoryIdAndProviderId(filters);
		page = providerProductInfoService.search(page, filters);
		List<ProviderProductInfo> list = page.getResult();
		Map<Integer, ProviderInfo> providerInfoMap = ParameterListener.providerInfoMap;
		Map<Integer, ProductCategoryInfo> productCategoryInfoMap = ParameterListener.productCategoryInfoMap;
		List<Map<String, Object>> mapList = new ArrayList<>();
		for (ProviderProductInfo providerProductInfo : list) {
			try {
				Map<String, Object> beanToMap = ExcelUtils.beanToMap(providerProductInfo);
				ProductCategoryInfo productCategoryInfo = productCategoryInfoMap.get(providerProductInfo.getCategoryId());
				ProviderInfo providerInfo = providerInfoMap.get(providerProductInfo.getProviderId());
				if (providerInfo == null)
					continue;
				beanToMap.put("providerId", providerInfo.getId());
				beanToMap.put("providerName", providerInfo.getProviderName());
				beanToMap.put("categoryId", productCategoryInfo.getId());
				beanToMap.put("productName", productCategoryInfo.getCategoryName());
				beanToMap.put("standardPrice", productCategoryInfo.getStandarPrice());
				beanToMap.put("productArea", productCategoryInfo.getArea());
				beanToMap.put("province", providerProductInfo.getProvince());
				beanToMap.put("addTime", DateUtils.formatDate((Date) beanToMap.get("addTime"), "yyyy-MM-dd HH:mm:ss"));
				beanToMap.put(
						"discount",
						productCategoryInfo.getStandarPrice() != null && productCategoryInfo.getStandarPrice() != 0 ? providerProductInfo
								.getCostPrice() / productCategoryInfo.getStandarPrice() : 0);
				ExcelUtils.showValue(beanToMap, "status", "", "使用中", "已下架");
				ExcelUtils.showValue(beanToMap, "productArea", "本省", "全国");
				beanToMap.put("provinceOperator", providerProductInfo.getProvince() + providerProductInfo.getMobileOperator());
				mapList.add(beanToMap);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
			}
		}
		String title = "供应商产品信息";
		// 产品名称 规格 供应商名称 标准价 折扣率
		String[] hearders = new String[] { "省份运营商", "供应商名称", "标准价", "折扣率", "折扣价", "运营商", "省份", "使用区域", "产品名称" };
		String[] fields = new String[] { "provinceOperator", "providerName", "standardPrice", "discount", "costPrice", "mobileOperator", "province",
				"productArea", "productName" };
		// String[] hearders = new String[] {"产品名称", "成本价", "供应商名称","标准价",
		// "折扣率","折扣价","省份","使用区域","状态","运营商","添加时间",
		// "通道优先级","供货商产品编号","供货商id","产品id","备注"};//表头数组
		// String[] fields = new String[]
		// {"productName","cost","providerName","standardPrice","discount","discountPrice","province","productArea","status","mobileOperator","addTime","priority","productCode","providerId","categoryId","remark"};
		ExcelUtils.productExcel(response, mapList, title, hearders, fields, log);
	}

	
	/**
	 * 添加折扣信息跳转
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "updateDataList")
	public String updateDataList(Model model) {
		return "boss/providerProductInfo/updateDataList";
	}
	
	/**
	 * 优先级批量修改
	 * 
	 * @param tmallOrder
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "updatePrior", method = RequestMethod.POST)
	@ResponseBody
	public String updateData(HttpServletRequest request, Integer[] ids, Integer status) {
		if (ids == null) {
			return "没有id";
		}
			for (Integer id : ids) {
				ProviderProductInfo providerProductInfo = providerProductInfoService.get(id);
				providerProductInfo.setPriority(status);
				update(providerProductInfo);
			}
			log.info(UserUtil.getCurrentUser().getLoginName() + ",updateStatus更新状态:ids" + ids + ",status:" + status);
		return "success";
	}
	
	
	
	
	/**
	 * 批量删除供货商产品
	 * 
	 * @param id
	 * @return
	 */
	@RequiresPermissions("boss:providerProductInfo:delete")
	@RequestMapping(value = "delAll")
	@ResponseBody
	public String delAll(Integer[] ids) {
		if(ids!=null && ids.length>0){
			for (Integer id : ids) {
				
				providerProductInfoService.delete(id);
				log.info("providerProductInfo delete:" + id);
			}
			ParameterListener.flushStatus("init_provider");
			ParameterListener.refresh("providerProduct");
			return "success";
		}
		return "error";
	}
}
