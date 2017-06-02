package xinxing.boss.admin.boss.other.cmd;

import java.text.ParseException;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import xinxing.boss.admin.boss.customer.domain.CustomerInfo;
import xinxing.boss.admin.boss.order.domain.OrderInfo;
import xinxing.boss.admin.boss.order.service.OrderInfoService;
import xinxing.boss.admin.boss.order.util.OrderUtils;
import xinxing.boss.admin.boss.other.domain.BossOrderResendRecord;
import xinxing.boss.admin.boss.other.service.BossOrderResendRecordService;
import xinxing.boss.admin.boss.provider.domain.ProductCategoryInfo;
import xinxing.boss.admin.boss.provider.domain.ProviderInfo;
import xinxing.boss.admin.boss.provider.service.ProductCategoryInfoService;
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
@RequestMapping(value = "boss/bossOrderResendRecord")
public class BossOrderResendRecordCmd extends BaseController {
	public static String[] inside_hearders = new String[] { "订单号", "采购商ID", "采购商名称", "采购商订单号", "运营商", "省份", "城市", "供应商ID", "供应商", "供应商流水号", "手机",
			"销售价", "成本价", "利润", "订单状态", "最终状态", "订单提交时间", "订单处理时间", "耗时(s)", "产品名称", "产品规格", "充值次数" };// 表头数组
	public static String[] inside_fields = new String[] { "id", "customerId", "customerName", "orderKey", "beyoudOperation", "province", "city",
			"successId", "providerName", "providerKey", "phone", "price", "cost", "earn", "status", "finalStatus", "receiveTime", "handleTime",
			"useTime", "categoryName", "productScale", "chargeCount" };
	private static Logger log = LoggerFactory.getLogger(BossOrderResendRecordCmd.class);
	@Autowired
	private BossOrderResendRecordService bossOrderResendRecordService;
	@Autowired
	private ProductCategoryInfoService productCategoryInfoService;
	@Autowired
	private OrderInfoService orderInfoService;

	// 弹出框的方法
	@RequestMapping(value = "alertWindow")
	@ResponseBody
	public String alertWindow(HttpServletRequest req, BossOrderResendRecord bossOrderResendRecord, Integer[] ids, String method) {
		log.info("ids:" + JsonUtils.obj2Json(ids));
		return "success";
	}

	// 额外的方法
	@RequestMapping(value = "updateStatus", method = RequestMethod.POST)
	@ResponseBody
	public String updateStatus(HttpServletRequest req, Integer[] ids, Integer status, HttpServletResponse resp) {
		if (ids == null || ids.length == 0 || status == null) {
			return "没有id";
		}
		// if (status!=null&&status >= 0 && status <= 2){
		// for (Integer id : ids) {
		// BossOrderResendRecord bossOrderResendRecord = bossOrderResendRecordService.get(id);
		// bossOrderResendRecord.setStatus(status);
		// update(bossOrderResendRecord);
		// }
		// }
		return "success";
	}

	// 跳转页面
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list(HttpServletRequest req) {
		Map<String, String> reqMap = HttpUtils.getReqParams(req);
		if (reqMap == null) {
			reqMap = new HashMap<String, String>();
		}
		String page = reqMap.get("page");
		if (StringUtils.isEmpty(page)) {
			page = "List";
		}
		System.out.println(page);
		req.getSession().setAttribute("reqMap", reqMap);
		return "boss/bossOrderResendRecord/bossOrderResendRecord" + page;
	}

	// 获取数据到页面
	@RequestMapping(value = "bossOrderResendRecordList")
	@ResponseBody
	public Map<String, Object> getConfigList(HttpServletRequest req) {
		Page<BossOrderResendRecord> page = getPage(req);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(req);
		page = bossOrderResendRecordService.search(page, filters);
		return getEasyUIData(page);
	}

	// 添加跳转
	@RequiresPermissions("boss:bossOrderResendRecord:add")
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String create(Model model, BossOrderResendRecord bossOrderResendRecord) {
		model.addAttribute("bossOrderResendRecord", bossOrderResendRecord);
		return "boss/bossOrderResendRecord/bossOrderResendRecordAdd";
	}

	// 添加
	@RequiresPermissions("boss:bossOrderResendRecord:add")
	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ResponseBody
	public String add(@Valid BossOrderResendRecord bossOrderResendRecord, Model model) {
		Date date = new Date();
		Integer userId = UserUtil.getCurrentUser() != null ? UserUtil.getCurrentUser().getId() : null;
		bossOrderResendRecordService.save(bossOrderResendRecord);
		ParameterListener.refresh("bossOrderResendRecord");
		ParameterListener.flushStatus("bossOrderResendRecord");
		log.info("bossOrderResendRecord add:" + bossOrderResendRecord.getId());
		return "success";
	}

	// 修改跳转
	@RequiresPermissions("boss:bossOrderResendRecord:update")
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String update(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("bossOrderResendRecord", bossOrderResendRecordService.get(id));
		return "boss/bossOrderResendRecord/bossOrderResendRecordUpdate";
	}

	// 修改
	@RequiresPermissions("boss:bossOrderResendRecord:update")
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody BossOrderResendRecord bossOrderResendRecord) {
		Date date = new Date();
		Integer userId = UserUtil.getCurrentUser() != null ? UserUtil.getCurrentUser().getId() : null;
		bossOrderResendRecordService.update(bossOrderResendRecord);
		ParameterListener.refresh("bossOrderResendRecord");
		ParameterListener.flushStatus("bossOrderResendRecord");
		return "success";
	}

	// 批量删除
	@RequiresPermissions("boss:bossOrderResendRecord:delete")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(Integer[] ids) {
		if (ids != null && ids.length > 0) {
			for (Integer id : ids) {
				bossOrderResendRecordService.delete(id);
				log.info("bossOrderResendRecord delete:" + id);
			}
			ParameterListener.refresh("bossOrderResendRecord");
			ParameterListener.flushStatus("bossOrderResendRecord");
		}
		return "success";
	}

	// 导出excel
	@SuppressWarnings("unchecked")
	@RequiresPermissions("boss:bossOrderResendRecord:exportExcel")
	@RequestMapping("exportExcel")
	public void createExcel(HttpServletRequest request, HttpServletResponse response) throws ParseException {
		@SuppressWarnings("rawtypes")
		Map attribute = (Map) request.getSession().getServletContext().getAttribute("sysParam");
		//Map<Integer, String> orderStatusMap = (Map<Integer, String>) attribute.get("orderStatusMap");
		Map<Integer, CustomerInfo> customerInfoMap = (Map<Integer, CustomerInfo>) attribute.get("customerInputMap");
		Map<Integer, ProviderInfo> providerInfoMap = (Map<Integer, ProviderInfo>) attribute.get("providerInputMap");
		Map<Integer, ProductCategoryInfo> productCategoryInfoMap = (Map<Integer, ProductCategoryInfo>) attribute.get("productCategoryInputMap");
		log.info("excel启动 " + DateUtils.dateFormat(new Date()));
		Page<BossOrderResendRecord> page = getPage(request);
		Integer maxNumber = 1000000;
		page.setPageNo(1);
		page.setPageSize(maxNumber);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		page = bossOrderResendRecordService.exportSearch(page, filters, maxNumber);
		if (page.getTotalCount() > maxNumber) {
			ExcelUtils.writeErrorMsg(response, page.getTotalCount(), "导出数据不能超过100万条");
			return;
		}
		Long center = System.currentTimeMillis();
		List<BossOrderResendRecord> list = page.getResult();// 获取数据
		List<Map<String, Object>> mapList = new ArrayList<>();
		List<ProductCategoryInfo> productCategorys = productCategoryInfoService.getAll();
		Map<Integer, ProductCategoryInfo> productCategoryMap = new HashMap<>();
		for (ProductCategoryInfo productCategoryInfo : productCategorys) {
			productCategoryMap.put(productCategoryInfo.getId(), productCategoryInfo);
		}
		for (BossOrderResendRecord bossOrderResendRecord : list) {
			if (productCategoryMap.containsKey(bossOrderResendRecord.getCategoryId())) {
				Map<String, Object> beanToMap = ExcelUtils.beanToMap(bossOrderResendRecord);
				ProductCategoryInfo productCategoryInfo = productCategoryMap.get(bossOrderResendRecord.getCategoryId());
				beanToMap.put("handleTime",
						(bossOrderResendRecord.getStatus() == 3 ? bossOrderResendRecord.getSuccessTime() : bossOrderResendRecord.getFailTime()));
				// 耗时
				Date time = (bossOrderResendRecord.getStatus() == 3 ? bossOrderResendRecord.getSuccessTime() : bossOrderResendRecord.getFailTime());
				if (time != null && bossOrderResendRecord.getReceiveTime() != null) {
					beanToMap.put("useTime", (int) ((time.getTime() - bossOrderResendRecord.getReceiveTime().getTime()) / 1000));
				} else
					beanToMap.put("useTime", "");
				Integer productUnit = productCategoryInfo.getProductUnit();
				String productGuige = "";
				if (productUnit == 1) {
					productGuige = "M";
				} else if (productUnit == 2) {
					productGuige = "G";
				} else if (productUnit == 3) {
					productGuige = "元";
				}
				beanToMap.put("productScale", productCategoryInfo.getProductNum() + productGuige + "");
				if (bossOrderResendRecord.getCost() != null && bossOrderResendRecord.getPrice() != null && bossOrderResendRecord.getStatus() == 3) {
					beanToMap.put("earn", bossOrderResendRecord.getPrice().subtract(bossOrderResendRecord.getCost()));
				} else
					beanToMap.put("earn", null);
				if (productCategoryInfoMap.get(bossOrderResendRecord.getCategoryId()) != null) {
					beanToMap.put("categoryName", productCategoryInfoMap.get(bossOrderResendRecord.getCategoryId()).getCategoryName());
				}
				if (customerInfoMap.get(bossOrderResendRecord.getCustomerId()) != null) {
					beanToMap.put("customerName", customerInfoMap.get(bossOrderResendRecord.getCustomerId()).getCustomerName());
				}
				if (providerInfoMap.get(bossOrderResendRecord.getSuccessId()) != null) {
					beanToMap.put("providerName", providerInfoMap.get(bossOrderResendRecord.getSuccessId()).getProviderName());
				}
				// 订单状态 1新增2充值中3充值成功4充值失败5等待确认6需手工处理',
				ExcelUtils.showValue(beanToMap, "status", "", "新增", "充值中", "充值成功", "充值失败", "等待确认", "需手工处理");
				ExcelUtils.showValue(beanToMap, "finalStatus", "", "新增", "充值中", "充值成功", "充值失败", "等待确认", "需手工处理");
				mapList.add(beanToMap);
			}

		}
		String title = "订单信息";
		try {
			ExcelUtils.productExcel(response, mapList, title, BossOrderResendRecordCmd.inside_hearders, BossOrderResendRecordCmd.inside_fields, log);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	/**
	 * 获取统计信息
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "getCountInfo")
	@ResponseBody
	public Map<String, Object> getCountInfo(HttpServletRequest request) {
		Page<OrderInfo> page = getPage(request);
		page.setPageNo(1);
		page.setPageSize(10000000);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		Map<String, Object> map = null;
		try {
			map = OrderUtils.getCountMap(orderInfoService, filters, "boss_order_resend_record");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
}
