package xinxing.boss.admin.boss.provider.cmd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
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

import xinxing.boss.admin.boss.customer.domain.CustomerProductInfo;
import xinxing.boss.admin.boss.customer.service.CustomerProductInfoService;
import xinxing.boss.admin.boss.order.domain.OrderInfo;
import xinxing.boss.admin.boss.provider.domain.ProductCategoryInfo;
import xinxing.boss.admin.boss.provider.domain.ProviderProductInfo;
import xinxing.boss.admin.boss.provider.service.ProductCategoryInfoService;
import xinxing.boss.admin.boss.provider.service.ProviderProductInfoService;
import xinxing.boss.admin.common.persistence.Page;
import xinxing.boss.admin.common.persistence.PropertyFilter;
import xinxing.boss.admin.common.utils.http.HttpUtils;
import xinxing.boss.admin.common.utils.json.JsonUtils;
import xinxing.boss.admin.common.utils.parameter.ParameterListener;
import xinxing.boss.admin.common.utils.string.StringUtils;
import xinxing.boss.admin.common.web.BaseController;

@Controller
@RequestMapping(value = "boss/productCategoryInfo")
public class ProductCategoryInfoCmd extends BaseController {
	private static Logger logger = LoggerFactory.getLogger(ProductCategoryInfoCmd.class);

	@Autowired
	private ProductCategoryInfoService productCategoryInfoService;
	@Autowired
	private ProviderProductInfoService providerProductInfoService;
	@Autowired
	private CustomerProductInfoService customerProductInfoService;

	/**
	 * 默认页面
	 */
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list(HttpServletRequest req, OrderInfo orderInfo) {
		Map<String, String> reqMap = HttpUtils.getReqParams(req);
		if (reqMap == null) {
			reqMap = new HashMap<String, String>();
		}
		String page = reqMap.get("page");
		if (StringUtils.isEmpty(page)) {
			page = "List";
		}
		req.setAttribute("orderInfo", orderInfo);
		req.setAttribute("reqMap", reqMap);
		return "boss/productCategoryInfo/productCategoryInfo" + page;
	}

	/**
	 * 获取所有订单信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "productCategoryInfoList", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getConfigList(HttpServletRequest request) {
		Page<ProductCategoryInfo> page = getPage(request);
		page.setOrderBy("productUnit,productNum");
		page.setOrder("asc,asc");
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		page = productCategoryInfoService.search(page, filters);
		return getEasyUIData(page);
	}

	// 广东移动1元话费 批量导入
	// String[] provinces = ParameterListener.productProvinceStr;
	// String[] operators =new String[]{"移动","联通","电信"};
	// Integer[] productNums = new Integer[]{1,2,3,4,5,6,7,8,9,10,20,30,50,100,200,300,500};
	// Integer status = 1;
	// Integer productUnit = 3;
	// Integer productType =2;
	// Integer isSecondChannel = 1;
	// Integer area = 1;
	// for (int i = 0; i < provinces.length; i++) {
	// for (int j = 0; j < operators.length; j++) {
	// for (int j2 = 0; j2 < productNums.length; j2++) {
	// ProductCategoryInfo p = new ProductCategoryInfo(null, provinces[i]+operators[j]+productNums[j2]+"元话费", status, productNums[j2], productUnit,
	// productType, isSecondChannel, operators[j], provinces[i], area, productNums[j2].doubleValue());
	// productCategoryInfoService.save(p);
	// }
	// }
	// }

	// 多一个字段是否限价 批量生成
	// List<ProductCategoryInfo> search = productCategoryInfoService
	// .search("from ProductCategoryInfo  where productUnit in (1,2) and province != '1' and pid = null");
	//
	// System.out.println("search:" + search.size());
	// List<ProductCategoryInfo> saveList = new ArrayList<>();
	// for (ProductCategoryInfo productCategoryInfo : search) {
	// ProductCategoryInfo obj = JsonUtils.json2Obj(JsonUtils.obj2Json(productCategoryInfo), ProductCategoryInfo.class);
	// obj.setCategoryName(obj.getCategoryName() + "(不限)");
	// obj.setId(0);
	// obj.setIsLimit(0);
	// saveList.add(obj);
	// }
	// productCategoryInfoService.saveBatch(saveList);
	// ParameterListener.refresh("product");
	/**
	 * 添加订单信息跳转
	 * 
	 * @param model
	 * @return
	 */
	@RequiresPermissions("boss:productCategoryInfo:add")
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String create(Model model, HttpServletRequest request) {
		model.addAttribute("productCategoryInfo", new ProductCategoryInfo());
		if (StringUtils.isBlank(request.getParameter("flag"))) {
			return "boss/productCategoryInfo/productCategoryInfoAdd";
		} else {
			return "boss/productCategoryInfo/productCategoryInfoForm";
		}
	}

	/**
	 * 添加产品类别信息
	 * 
	 * @param dict
	 * @param model
	 */
	@RequiresPermissions("boss:productCategoryInfo:add")
	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ResponseBody
	public String add(@Valid ProductCategoryInfo productCategoryInfo, Model model) {
		// 添加菜单
		createMenu(productCategoryInfo);
		productCategoryInfoService.save(productCategoryInfo);
		productCategoryInfoService.flush();
		logger.info("productCategoryInfo add:" + productCategoryInfo.getId());
		ParameterListener.refresh("product");
		ParameterListener.flushStatus("init_category");
		return "success";
	}

	/**
	 * 修改订单信息跳转
	 */
	@RequiresPermissions("boss:productCategoryInfo:update")
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String update(@PathVariable("id") Integer id, Model model, HttpServletRequest request) {
		model.addAttribute("productCategoryInfo", productCategoryInfoService.get(id));
		return "boss/productCategoryInfo/productCategoryInfoUpdate";
	}

	/**
	 * 修改订单信息跳转
	 */
	@RequestMapping(value = "update1/{id}", method = RequestMethod.GET)
	public String update(@PathVariable("id") Integer id, Model model, String flag, HttpServletRequest request) {
		model.addAttribute("productCategoryInfo", productCategoryInfoService.get(id));
		return "boss/productCategoryInfo/productCategoryInfoForm";
	}

	/**
	 * 修改订单信息
	 * 
	 * @param tmallOrder
	 * @param model
	 * @return
	 */
	@RequiresPermissions("boss:productCategoryInfo:update")
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody ProductCategoryInfo productCategoryInfo) {
		productCategoryInfoService.update(productCategoryInfo);
		ParameterListener.refresh("product");
		ParameterListener.flushStatus("init_category");
		return "success";
	}

	/**
	 * 批量修改订单状态
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

		List<ProductCategoryInfo> list = new ArrayList<ProductCategoryInfo>();
		for (String id : ids) {
			ProductCategoryInfo productCategoryInfo = productCategoryInfoService.get(Integer.parseInt(id));
			productCategoryInfo.setStatus(Integer.parseInt(state));
			list.add(productCategoryInfo);
		}

		for (ProductCategoryInfo productCategoryInfo : list) {
			update(productCategoryInfo);
		}
		return "success";
	}

	/**
	 * 删除分类信息
	 * 
	 * @param id
	 * @return
	 */
	@RequiresPermissions("boss:productCategoryInfo:delete")
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Integer id) {
		// 检测该分类ID下是否有对应的供应商、采购商产品，有则无法删除
		List<ProviderProductInfo> providerProductInfoGroup = providerProductInfoService.getProviderProductInfoByProductId(id);
		List<CustomerProductInfo> customerProductInfoGroup = customerProductInfoService.getCustomerProductInfoByProductId(id);

		if (providerProductInfoGroup.size() > 0 || customerProductInfoGroup.size() > 0) {
			return "请先删除该产品类型的供应商产品和采购商产品";
		}

		ProductCategoryInfo productCategoryInfo = productCategoryInfoService.get(id);
		productCategoryInfoService.delete(id);
		List<Integer> ids = productCategoryInfoService.getMenuId(productCategoryInfo.getOperator(), productCategoryInfo.getProvince(),
				productCategoryInfo.getArea());
		for (Integer integer : ids) {
			if (integer != null)
				productCategoryInfoService.delete(integer);
		}
		logger.info("productCategoryInfo delete:" + id);
		ParameterListener.refresh("product");
		ParameterListener.flushStatus("init_category");
		return "success";
	}

	/**
	 * 获取目录信息
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "getMenu")
	@ResponseBody
	public List<ProductCategoryInfo> getMenu(Integer id) {
		return productCategoryInfoService.getMenu();

	}

	private void createMenu(ProductCategoryInfo productCategoryInfo) {
		Map<String, Integer> pidMap = productCategoryInfoService.getPidMap();
		List<ProductCategoryInfo> all = productCategoryInfoService.getList();
		List<Map<String, Integer>> threeList = new ArrayList<>();
		for (int i = 0; i < 3; i++) {
			threeList.add(new HashMap<String, Integer>());
		}
		String[] threeStrs = new String[] { "联通", "移动", "电信" };
		for (ProductCategoryInfo info : all) {
			for (int i = 0; i < threeStrs.length; i++) {
				if (StringUtils.equals(info.getOperator(), threeStrs[i])) {
					threeList.get(i).put(info.getProvince(), info.getArea());
					break;
				}
			}
		}
		for (int i = 0; i < threeStrs.length; i++) {
			if (StringUtils.equals(productCategoryInfo.getOperator(), threeStrs[i])) {
				if (!threeList.get(i).containsKey(productCategoryInfo.getProvince())) {
					ProductCategoryInfo pci = new ProductCategoryInfo();
					pci.setArea(1);
					pci.setProvince("1");
					pci.setOperator("1");
					pci.setIsSecondChannel(1);
					pci.setProductType(1);
					pci.setProductUnit(1);
					pci.setProductNum(1);
					pci.setStatus(1);
					pci.setCategoryName(productCategoryInfo.getProvince());
					pci.setPid(pidMap.get(threeStrs[i]));
					productCategoryInfoService.save(pci);
				}
				ProductCategoryInfo byOperatorAndCategoryName = productCategoryInfoService.getByOperatorAndCategoryName(threeStrs[i],
						productCategoryInfo.getProvince(), productCategoryInfo.getArea());
				if (byOperatorAndCategoryName != null) {
					ProductCategoryInfo pci = new ProductCategoryInfo();
					pci.setArea(1);
					pci.setProvince("1");
					pci.setOperator("1");
					pci.setIsSecondChannel(1);
					pci.setProductType(1);
					pci.setProductUnit(1);
					pci.setProductNum(1);
					pci.setStatus(1);
					pci.setCategoryName(productCategoryInfo.getArea() == 1 ? "全国" : "本省");
					pci.setPid(byOperatorAndCategoryName.getId());
					productCategoryInfoService.save(pci);
				}
			}
		}
	}
}
