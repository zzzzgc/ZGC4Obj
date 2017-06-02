package xinxing.boss.admin.boss.order.cmd;

import java.io.File;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang.time.DateFormatUtils;
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
import xinxing.boss.admin.boss.customer.service.CustomerInfoService;
import xinxing.boss.admin.boss.operation.domain.AlarmEvent;
import xinxing.boss.admin.boss.operation.domain.OperationRecords;
import xinxing.boss.admin.boss.operation.service.AlarmEventService;
import xinxing.boss.admin.boss.operation.service.OperationRecordsService;
import xinxing.boss.admin.boss.order.domain.OrderInfo;
import xinxing.boss.admin.boss.order.service.OrderInfoService;
import xinxing.boss.admin.boss.order.util.OrderUtils;
import xinxing.boss.admin.boss.other.domain.BossInvoiceAudit;
import xinxing.boss.admin.boss.other.domain.BossOvertimeOrderRecord;
import xinxing.boss.admin.boss.other.service.BossInvoiceAuditService;
import xinxing.boss.admin.boss.other.service.BossOvertimeOrderRecordService;
import xinxing.boss.admin.boss.other.service.BossProviderChargeService;
import xinxing.boss.admin.boss.provider.domain.NumberSegment;
import xinxing.boss.admin.boss.provider.domain.ProductCategoryInfo;
import xinxing.boss.admin.boss.provider.domain.ProviderInfo;
import xinxing.boss.admin.boss.provider.domain.ProviderProductInfo;
import xinxing.boss.admin.boss.provider.service.NumberSegmentService;
import xinxing.boss.admin.boss.provider.service.ProductCategoryInfoService;
import xinxing.boss.admin.boss.provider.service.ProviderInfoService;
import xinxing.boss.admin.common.excel.ExcelReader;
import xinxing.boss.admin.common.excel.ExcelUtils;
import xinxing.boss.admin.common.persistence.Page;
import xinxing.boss.admin.common.persistence.PropertyFilter;
import xinxing.boss.admin.common.utils.base.BaseUtil;
import xinxing.boss.admin.common.utils.constants.Constants;
import xinxing.boss.admin.common.utils.http.HttpUtils;
import xinxing.boss.admin.common.utils.json.JsonUtils;
import xinxing.boss.admin.common.utils.parameter.ParameterListener;
import xinxing.boss.admin.common.utils.string.StringUtils;
import xinxing.boss.admin.common.utils.time.DateUtils;
import xinxing.boss.admin.common.web.BaseController;
import xinxing.boss.admin.system.user.domain.User;
import xinxing.boss.admin.system.user.utils.UserUtil;

@Controller
@RequestMapping(value = "boss/orderInfo")
public class OrderInfoCmd extends BaseController {
	private static Logger log = LoggerFactory.getLogger(OrderInfoCmd.class);
	public static String[] hearders = new String[] { "采购商订单号", "采购商ID", "采购商名称", "运营商", "省份", "城市", "手机", "总价", "订单状态", "订单提交时间", "产品名称", "产品规格" };// 表头数组
	public static String[] fields = new String[] { "orderKey", "customerId", "customerName", "beyoudOperation", "province", "city", "phone", "price", "status", "receiveTime", "categoryName", "productScale", };
	public static String[] inside_hearders = new String[] { "订单号", "采购商ID", "采购商名称", "采购商订单号", "运营商", "省份", "城市", "供应商ID", "供应商", "供应商流水号", "手机", "销售价", "成本价", "利润", "订单状态", "订单提交时间", "订单处理时间", "耗时(s)", "产品名称", "产品规格", "充值次数" };// 表头数组
	public static String[] inside_fields = new String[] { "id", "customerId", "customerName", "orderKey", "beyoudOperation", "province", "city", "successId", "providerName", "providerKey", "phone", "price", "cost", "earn", "status", "receiveTime", "handleTime", "useTime", "categoryName", "productScale", "chargeCount" };
	@Autowired
	private OrderInfoService orderInfoService;
	@Autowired
	private ProductCategoryInfoService productCategoryInfoService;
	@Autowired
	private CustomerInfoService customerInfoService;
	@Autowired
	private AlarmEventService alarmEventService;
	@Autowired
	private OperationRecordsService operationRecordsService;
	@Autowired
	private ProviderInfoService providerInfoService;
	@Autowired
	private BossInvoiceAuditService bossInvoiceAuditService;
	@Autowired
	private BossProviderChargeService bossProviderChargeService;
	@Autowired
	private NumberSegmentService numberSegmentService;
	@Autowired
	private BossOvertimeOrderRecordService bossOvertimeOrderRecordService;

	// 额外的方法 返回@ResponseBody
	@RequestMapping(value = "updateStatus", method = RequestMethod.POST)
	@ResponseBody
	public String updateStatus(HttpServletRequest request, Integer[] ids, Integer status) {
		try {
			if (status == 6) { // (充值中,等待确认)转手工
				for (Integer id : ids) {
					User currentUser = UserUtil.getCurrentUser();
					OrderInfo orderInfo = orderInfoService.get(id);
					log.info("updateStatus:orderInfo:" + JsonUtils.obj2Json(orderInfo));
					if (orderInfo.getStatus() == OrderInfo.Status.CHARGE.status || orderInfo.getStatus() == OrderInfo.Status.WAIT.status || orderInfo.getStatus() == OrderInfo.Status.NEW.status) {
						if (orderInfo.getStatus() == OrderInfo.Status.CHARGE.status) {
							orderInfo.setHandleStatus(3);
						} else if (orderInfo.getStatus() == OrderInfo.Status.WAIT.status) {
							Map<Integer, ProviderInfo> providerInfoMap = ParameterListener.providerInfoMap;
							ProviderInfo providerInfo = providerInfoMap.get(orderInfo.getSuccessId());
							orderInfo.setHandleStatus(7);
							String failReason = orderInfo.getFailReason() + ",供应商:" + (providerInfo != null ? providerInfo.getProviderName() : "供应商不存在") + "-未完成转手工处理  ";
							orderInfo.setFailReason(failReason);
						} else {
							orderInfo.setHandleStatus(2);
						}
						orderInfo.setStatus(OrderInfo.Status.HANDLE.status);
						orderInfoService.save(orderInfo);
						if (currentUser != null) {
							log.info("用户id:" + currentUser.getId() + ",用户名:" + currentUser.getLoginName() + ",(新增,充值中,等待确认)转手工,订单id:" + orderInfo.getId());
						}
					} else {
						return "订单状态必须为新增/充值中/等待确认";
					}
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			return e.getMessage();
		}
		return "success";
	}
	/**
	 * 默认页面
	 */
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list(HttpServletRequest req, OrderInfo orderInfo) {
		long  startTime = System.currentTimeMillis();
		Map<String, String> reqMap = HttpUtils.getReqParams(req);
		if (reqMap == null) {
			reqMap = new HashMap<String, String>();
		}
		
		String page = reqMap.get("page");
		if (StringUtils.isEmpty(page)) {
			page = "List";
		}
		
		reqMap.put("todayStart", DateUtils.getDate() + " 00:00:00");
		reqMap.put("todayEnd", DateUtils.getDate() + " 23:59:59");
		reqMap.put("handleOrderUseTime", ParameterListener.bossConfigMap.get("handleOrderUseTime"));
		req.setAttribute("orderInfo", orderInfo);
		req.setAttribute("reqMap", reqMap);
		req.setAttribute("handleStatus", req.getParameter("handleStatus"));
		long endTime = System.currentTimeMillis();
		log.info(req.getRequestURL()+"的执行时间为:"+(endTime-startTime)/1000+"s,"+(endTime-startTime)+"ms");
		return "boss/orderInfo/orderInfo" + page;
	}

	/**
	 * 手工处理页面
	 */
	@RequestMapping(value = "handleOrderList")
	public String handleOrderList(HttpServletRequest req) {
		list(req, null);
		return "boss/orderInfo/orderInfo" + "HandleList";
	}

	/**
	 * 获取负利润信息
	 * 
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = "orderInfoLossMoneyList")
	@ResponseBody
	public Map<String, Object> orderInfoLossMoneyList(HttpServletRequest req) throws ParseException {
		Page<OrderInfo> page = getPage(req);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(req);
		String sql = BaseUtil.getSqlConditionByFilters(filters, log);
		String hql = "from OrderInfo where price-cost<0 and status = 3 " + sql.replaceAll("where", "and");
		String totalHql = "select count(id) " + hql;
		List<Object> total = orderInfoService.query(totalHql, null, null);
		log.info(page.getPageNo() + "," + page.getFirst() + "," + page.getPageSize());

		List<Object> query = orderInfoService.query(hql + "  order by successTime desc ", page.getFirst() - 1, page.getPageSize());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", query);
		map.put("total", total.get(0));
		return map;
	}

	/**
	 * 获取所有订单信息
	 * 
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = "orderInfoList")
	@ResponseBody
	public Map<String, Object> getConfigList(HttpServletRequest request) throws ParseException {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Page<OrderInfo> page = getPage(request);
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
			BaseUtil.testSearch(filters, "receiveTime");
			page = orderInfoService.search(page, filters);
			List<OrderInfo> result = page.getResult();
			for (OrderInfo orderInfo : result) {
				if (orderInfo != null && orderInfo.getFailReason() != null)
					orderInfo.setFailReason(orderInfo.getFailReason().replaceAll("<!--", "").replace("<", ""));// 带<
																												// 的
																												// 前台不显示
			}
			// List<Map<String, Object>> bossOrderList =
			// getTmallPrice(page.getResult());
			map.put("rows", page.getResult());
			map.put("total", page.getTotalCount());
		} catch (Exception e) {
			log.info(e.getMessage(), e);
		}
		return map;
	}

	/**
	 * 获取手工处理并且是customerId是天猫采购的商品价格
	 * 
	 * @param result
	 * @param bossOrderList
	 */
	public List<Map<String, Object>> getTmallPrice(List<OrderInfo> result) {
		List<String> tmallOrderIds = new ArrayList<>();
		List<Map<String, Object>> bossOrderList = JsonUtils.json2Obj(JsonUtils.obj2Json(result), List.class);
		for (OrderInfo orderInfo : result) {
			String customerId = orderInfo.getCustomerId() != null ? orderInfo.getCustomerId() + "" : "";
			if (Constants.getString("tmallCustomerId").equals(customerId) && orderInfo.getStatus() != null && orderInfo.getStatus() == OrderInfo.Status.HANDLE.status) {
				tmallOrderIds.add(orderInfo.getOrderKey());
			}
		}
		log.info("tmallIds数量:" + tmallOrderIds.size());
		if (tmallOrderIds.size() > 0) {
			String url = Constants.getString("tmallGetPriceUrl");
			log.info("tmall地址:" + url + "发送数据:" + JsonUtils.obj2Json(tmallOrderIds));
			try {
				if (url != null) {
					try {
						Map map = new HashMap<>();
						map.put("str", BaseUtil.getStrByArraysOrList(tmallOrderIds));
						String sendReq = JsonUtils.obj2Json(map);
						log.info("url:" + url + ",tmall发送:" + sendReq);
						String sendResp = HttpUtils.sendPost(url + "?str=" + BaseUtil.getStrByArraysOrList(tmallOrderIds), null, "utf-8");
						// log.info("tmall返回:" + JsonUtils.obj2Json(sendResp));
						if (StringUtils.isNotBlank(sendResp)) {
							List<Map<String, Object>> tmallOrderList = JsonUtils.json2Obj(sendResp, List.class);
							for (Map<String, Object> tmallOrder : tmallOrderList) {
								for (Map<String, Object> bossOrder : bossOrderList) {
									if (tmallOrder.get("id").toString().equals(bossOrder.get("orderKey").toString())) { // tmallOrder------------bossOrder
										bossOrder.put("tmallPrice", tmallOrder.get("tmallPrice"));
										bossOrder.put("tmallCost", tmallOrder.get("tmallCost"));
									}
								}
							}
						}
					} catch (Exception e) {
						log.error(e.getMessage());
					}
				}
			} catch (Exception e) {
			}
		}
		return bossOrderList;
	}

	/**
	 * 订单统计
	 * 
	 * @return
	 */
	@RequestMapping(value = "getCountInfo")
	@ResponseBody
	public Map<String, Object> getCountInfo(HttpServletRequest request) {
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		Map<String, Object> map = new HashMap<>();
		try {
			map = OrderUtils.getCountMap(orderInfoService, filters, "boss_order");
		} catch (Exception e) {
			map.put("failReason", "统计异常");
		}
		return map;
	}

	/**
	 * 添加订单信息跳转
	 * 
	 * @param model
	 * @return
	 */
	@RequiresPermissions("boss:orderInfo:add")
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String create(Model model) {
		model.addAttribute("orderInfo", new OrderInfo());
		return "boss/orderInfo/orderInfoAdd";
	}

	/**
	 * 平账用
	 * 
	 * @param dict
	 * @param model
	 */
	@RequiresPermissions("boss:orderInfo:add")
	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ResponseBody
	public String createAdd(@Valid OrderInfo orderInfo, Model model) {
		orderInfo.setStatus(OrderInfo.Status.AUDIT.status);
		String failReason = orderInfo.getFailReason() == null ? "" : orderInfo.getFailReason();
		orderInfo.setFailReason(failReason + "---财务平账订单");
		orderInfoService.save(orderInfo);
		BossInvoiceAudit bia = new BossInvoiceAudit();
		bia.addAndUpdateTimeAndUserId();
		bia.setAuditStatus(0);
		bia.setOrderId(orderInfo.getId());
		bossInvoiceAuditService.save(bia);
		return "success";
	}

	/**
	 * 手工重发
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "handleResendUrl", method = RequestMethod.GET)
	public String handleResendUrl(HttpServletRequest request, Model model) {
		String[] ids = request.getParameterValues("ids");
		Integer categoryId = request.getParameter("categoryId") != null ? Integer.parseInt(request.getParameter("categoryId")) : null;
		log.info("ids:" + Arrays.toString(ids));
		log.info("categoryId:" + categoryId);
		Integer quanGuoCategoryId = null;
		Map<Integer, ProductCategoryInfo> productCategoryInfoMap = ParameterListener.productCategoryInfoMap;
		ProductCategoryInfo productCategoryInfo = productCategoryInfoMap.get(categoryId);
		// 如果是全国就给省份 是省份就给全国
		OrderInfo orderInfo = orderInfoService.get(Integer.parseInt(ids[0]));
		String province = productCategoryInfo.getProvince().equals("全国") ? orderInfo.getProvince().replaceAll("省", "") : "全国";
		log.info("orderProvince:" + orderInfo.getProvince() + "--------categoryProvince:" + productCategoryInfo.getProvince() + "------查的值:" + province);
		log.info("产品信息:" + JsonUtils.obj2Json(productCategoryInfo));
		// 如果省份不是全国 则获得对应的省份的categoryId

		// Boolean isNeedSecondCategoryId = true;
		// if (!"全国".equals(province)) {
		// List<OrderInfo> search = orderInfoService.search(" from OrderInfo
		// where id in (" + StringUtils.join(ids, ",") + ")");
		// for (int i = 0; i < search.size(); i++) {
		// if (!search.get(i).getProvince().equals(search.get(0).getProvince()))
		// {
		// isNeedSecondCategoryId = false;
		// break;
		// }
		// }
		// }
		// if (isNeedSecondCategoryId) {
		List<ProductCategoryInfo> pciList = productCategoryInfoService.search("from ProductCategoryInfo where operator = ?0 and productNum = ?1 and productUnit = ?2 and " + " productType = ?3 and province like ?4 and area = ?5 and isLimit = ?6", productCategoryInfo.getOperator(), productCategoryInfo.getProductNum(), productCategoryInfo.getProductUnit(), productCategoryInfo.getProductType(), "%" + province + "%", productCategoryInfo.getArea(), productCategoryInfo.getIsLimit());
		if (pciList.size() > 0) {
			quanGuoCategoryId = pciList.get(0).getId();
		}
		// }
		// 由于productCategory的status未使用 所以不比较status
		log.info("另一个categoryId:" + quanGuoCategoryId);
		Map<Integer, ProviderProductInfo> ppMap = ParameterListener.providerProductMap;
		Map<Integer, ProviderInfo> providerInfoMap = ParameterListener.providerInfoMap;
		List list = new ArrayList<>();
		for (Entry<Integer, ProviderProductInfo> entry : ppMap.entrySet()) {
			if (categoryId == entry.getValue().getCategoryId() || (quanGuoCategoryId != null && quanGuoCategoryId == entry.getValue().getCategoryId())) {
				String customerId = orderInfo.getCustomerId().toString();
				ProviderProductInfo ppi = entry.getValue();
				ProviderInfo providerInfo = providerInfoMap.get(ppi.getProviderId());
				// // 错误,判断允许采购商 应该先全部 加起来 禁止采购商也是 没有先后这种说法
				// Boolean isAllow = true;
				// // 判断供应商信息的禁止采购商和允许采购商
				// if (StringUtils.isNotBlank(providerInfo.getAllowCustomer()))
				// {
				// String[] allows = providerInfo.getAllowCustomer().split(",");
				// for (String string : allows) {
				// if (string.equals(customerId)) {
				// isAllow = true;
				// break;
				// } else
				// isAllow = false;
				// }
				// } else {
				// String[] forbins =
				// providerInfo.getForbinCustomer().split(",");
				// for (String string : forbins) {
				// if (string.equals(orderInfo.getCustomerId().toString())) {
				// isAllow = false;
				// break;
				// }
				// }
				// }
				// if (isAllow) {
				// // 判断供应商产品信息的禁止采购商和允许采购商
				// if (ppi.getAllowCustomer() != null && ppi.getAllowCustomer()
				// != "") {
				// String[] allows = ppi.getAllowCustomer().split(",");
				// for (String string : allows) {
				// if (string.equals(customerId)) {
				// isAllow = true;
				// break;
				// } else
				// isAllow = false;
				// }
				// } else {
				// String[] forbins = ppi.getForbidCustomer().split(",");
				// for (String string : forbins) {
				// if (string.equals(orderInfo.getCustomerId().toString())) {
				// isAllow = false;
				// break;
				// }
				// }
				// }
				// }
				// if (isAllow && ppi != null && providerInfo != null &&
				// ppi.getStatus() == 1 && providerInfo.getStatus() == 1) {
				if (providerInfo != null && ppi.getStatus() == 1 && providerInfo.getStatus() == 1) {
					Map value = new HashMap<>();
					value.put("costPrice", ppi.getCostPrice());
					value.put("providerProductInfoId", ppi.getId());
					// value.put("providerId", providerInfo.getId());
					value.put("providerName", providerInfo.getProviderName());
					value.put("productName", ppi.getProductName());
					list.add(value);
				}
			}

		}
		model.addAttribute("providerProductList", list);
		Collections.sort(list, comparator);
		String idStr = BaseUtil.getStrByArraysOrList(ids);
		model.addAttribute("ids", idStr);
		return "boss/orderInfo/handleResend";
	}

	Comparator<Map> comparator = new Comparator<Map>() {
		public int compare(Map map1, Map map2) {
			Double map1CostPrice = (Double) map1.get("costPrice");
			Double map2CostPrice = (Double) map2.get("costPrice");
			Double value = map1CostPrice - map2CostPrice;
			// 降序排序
			return value.intValue();
		}
	};

	/**
	 * 手工重发
	 * 
	 * @param dict
	 * @param model
	 */
	@RequestMapping(value = "handleResend", method = RequestMethod.POST)
	@ResponseBody
	public String handleResend(HttpServletRequest request) {
		String ids = request.getParameter("ids");
		String providerProductId = request.getParameter("providerProductId");
		List handleOrderIdList = new ArrayList<>();
		List notFinishOrderIdList = new ArrayList<>();

		String[] split = ids.split(",");
		Map<Integer, ProviderProductInfo> providerProductMap = ParameterListener.providerProductMap;
		Map<Integer, ProductCategoryInfo> productCategoryInfoMap = ParameterListener.productCategoryInfoMap;
		ProviderProductInfo ppi = providerProductMap.get(Integer.parseInt(providerProductId));
		if (ppi == null) {
			return "供应商id为空,请与技术人员联系";
		}
		ProductCategoryInfo productCategoryInfo = productCategoryInfoMap.get(orderInfoService.get(Integer.parseInt(split[0])).getCategoryId());
		String product_province = productCategoryInfo.getProvince();
		String province = ppi.getProvince();
		String errorMsg = "";
		Boolean isCanSend = true;
		if ("全国".equals(product_province) && !"全国".equals(province)) {
			List<OrderInfo> search = orderInfoService.search(" from OrderInfo where id in (" + ids + ")");
			for (int i = 0; i < search.size(); i++) {
				if (!search.get(i).getProvince().equals(search.get(0).getProvince())) {
					isCanSend = false;
					break;
				}
			}
		}
		log.info("isCanSend:" + isCanSend);
		for (String id : split) {
			OrderInfo orderInfo = orderInfoService.get(Integer.parseInt(id));
			String order_province = orderInfo.getProvince();
			log.info("order_province" + order_province);
			if (isCanSend || order_province.equals(province)) {
				if (orderInfo.getStatus() == OrderInfo.Status.HANDLE.status && orderInfo.getHandleStatus() == 7) {
					notFinishOrderIdList.add(id);
				} else if (orderInfo.getStatus() == OrderInfo.Status.HANDLE.status && orderInfo.getHandleStatus() != 7) {
					handleOrderIdList.add(id);
				}
				createOverTimeOrderRecord(orderInfo);

				if (errorMsg.length() > 0) {
					return errorMsg + " 与" + ppi.getProductName() + "不符,请重新选择";
				}
			} else {
				errorMsg += "-订单id:" + id + ",省份:" + order_province;
			}
		}
		if (handleOrderIdList.size() > 0) {
			log.info("toProviderSend,ids:" + JsonUtils.obj2Json(handleOrderIdList) + ",providerProductId:" + providerProductId);
			orderInfoService.handleOrder("toProviderSend", JsonUtils.obj2Json(handleOrderIdList), providerProductId);
		}
		if (notFinishOrderIdList.size() > 0) {
			log.info("toProviderSendWithNoFinished,ids:" + JsonUtils.obj2Json(notFinishOrderIdList) + ",providerProductId:" + providerProductId);
			orderInfoService.handleOrder("toProviderSendWithNoFinished", JsonUtils.obj2Json(notFinishOrderIdList), providerProductId);
		}
		return "success";
	}

	/**
	 * 超时手工的订单需要记录
	 * 
	 * @param orderInfo
	 */
	public void createOverTimeOrderRecord(OrderInfo orderInfo) {
		// 状态手工处理并且手工处理状态是超时的需要记录
		if (orderInfo.getStatus() == OrderInfo.Status.HANDLE.status && orderInfo.getHandleStatus() == OrderInfo.HANDLE_OVERTIME) {
			BossOvertimeOrderRecord bot = JsonUtils.json2Obj(JsonUtils.obj2Json(orderInfo), BossOvertimeOrderRecord.class);
			bot.setOrderId(bot.getId());
			bot.setId(null);
			bot.setAddTime(new Date());
			bot.setAddUser(UserUtil.getCurrentUser().getName());
			bossOvertimeOrderRecordService.save(bot);
		}
	}

	/**
	 * 修改订单信息跳转
	 */
	@RequiresPermissions("boss:orderInfo:update")
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String update(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("orderInfo", orderInfoService.get(id));
		return "boss/orderInfo/orderInfoUpdate";
	}

	/**
	 * 修改订单信息
	 * 
	 * @param tmallOrder
	 * @param model
	 * @return
	 */
	@RequiresPermissions("boss:orderInfo:update")
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody OrderInfo orderInfo) {
		orderInfoService.update(orderInfo);
		return "success";
	}

	/**
	 * 删除订单
	 * 
	 * @param id
	 * @return
	 */
	@RequiresPermissions("boss:orderInfo:delete")
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Integer id) {
		orderInfoService.delete(id);
		log.info("orderInfo delete:" + id);
		return "success";
	}

	/**
	 * 根据省份获取城市
	 * 
	 * @param province
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getCity")
	@ResponseBody
	public String[] getCity(String province) throws Exception {
		log.info("getCity province:" + province);
		if (province != "" && province != null) {
			for (String[] str : ParameterListener.cityList) {
				if (province.equals(str[0])) {
					log.info("current province:" + province);
					String[] string = new String[str.length - 1];
					for (int i = 1; i < str.length; i++) {
						string[i - 1] = str[i];
					}
					return string;
				}
			}
		}
		log.info("get city error :" + province);
		return null;
	}

	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("exportExcel")
	public void createExcel(HttpServletRequest request, HttpServletResponse response) throws ParseException {
		@SuppressWarnings("rawtypes")
		Map attribute = (Map) request.getSession().getServletContext().getAttribute("sysParam");
		// Map<Integer, String> orderStatusMap = (Map<Integer, String>)
		// attribute.get("orderStatusMap");
		Map<Integer, CustomerInfo> customerInfoMap = (Map<Integer, CustomerInfo>) attribute.get("customerInputMap");
		Map<Integer, ProviderInfo> providerInfoMap = (Map<Integer, ProviderInfo>) attribute.get("providerInputMap");
		Map<Integer, ProductCategoryInfo> productCategoryInfoMap = (Map<Integer, ProductCategoryInfo>) attribute.get("productCategoryInputMap");
		log.info("excel启动 " + DateUtils.dateFormat(new Date()));
		Page<OrderInfo> page = getPage(request);
		Integer maxNumber = 1000000;
		/*
		 * if (BaseUtil.isLocalTest()) { maxNumber = 700000; }
		 */
		page.setPageNo(1);
		page.setPageSize(maxNumber);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		BaseUtil.testSearch(filters, "receiveTime");
		page = orderInfoService.search(page, filters);
		if (page.getTotalCount() > maxNumber) {
			ExcelUtils.writeErrorMsg(response, page.getTotalCount(), "导出数据不能超过100万条");
			return;
		}
		Long center = System.currentTimeMillis();
		List<OrderInfo> list = page.getResult();// 获取数据
		List<Map<String, Object>> mapList = new ArrayList<>();
		List<ProductCategoryInfo> productCategorys = productCategoryInfoService.getAll();
		Map<Integer, ProductCategoryInfo> productCategoryMap = new HashMap<>();
		for (ProductCategoryInfo productCategoryInfo : productCategorys) {
			productCategoryMap.put(productCategoryInfo.getId(), productCategoryInfo);
		}
		for (OrderInfo orderInfo : list) {
			if (productCategoryMap.containsKey(orderInfo.getCategoryId())) {
				Map<String, Object> beanToMap = ExcelUtils.beanToMap(orderInfo);
				ProductCategoryInfo productCategoryInfo = productCategoryMap.get(orderInfo.getCategoryId());
				beanToMap.put("handleTime", (orderInfo.getStatus() == 3 ? orderInfo.getSuccessTime() : orderInfo.getFailTime()));
				// 耗时
				Date time = (orderInfo.getStatus() == 3 ? orderInfo.getSuccessTime() : orderInfo.getFailTime());
				if (time != null && orderInfo.getReceiveTime() != null) {
					beanToMap.put("useTime", (int) ((time.getTime() - orderInfo.getReceiveTime().getTime()) / 1000));
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
				if (orderInfo.getCost() != null && orderInfo.getPrice() != null && orderInfo.getStatus() == 3) {
					beanToMap.put("earn", orderInfo.getPrice().subtract(orderInfo.getCost()));
				} else
					beanToMap.put("earn", null);
				if (productCategoryInfoMap.get(orderInfo.getCategoryId()) != null) {
					beanToMap.put("categoryName", productCategoryInfoMap.get(orderInfo.getCategoryId()).getCategoryName());
				}
				if (customerInfoMap.get(orderInfo.getCustomerId()) != null) {
					beanToMap.put("customerName", customerInfoMap.get(orderInfo.getCustomerId()).getCustomerName());
				}
				if (providerInfoMap.get(orderInfo.getSuccessId()) != null) {
					beanToMap.put("providerName", providerInfoMap.get(orderInfo.getSuccessId()).getProviderName());
				}
				// 订单状态 1新增2充值中3充值成功4充值失败5等待确认6需手工处理',
				ExcelUtils.showValue(beanToMap, "status", "", "新增", "充值中", "充值成功", "充值失败", "等待确认", "需手工处理");
				mapList.add(beanToMap);
			}
		}
		String title = "订单信息";
		try {
			ExcelUtils.productExcel(response, mapList, title, OrderInfoCmd.inside_hearders, OrderInfoCmd.inside_fields, log);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	/**
	 * 手工重发订单
	 * 
	 * @return
	 */
	@RequestMapping("callback")
	@ResponseBody
	public String callBackOrderByHand(Integer id) {
		OrderInfo orderInfo = orderInfoService.get(id);
		CustomerInfo customerInfo = customerInfoService.get(orderInfo.getCustomerId());
		String orderStatus = orderInfoService.callBackOrder(orderInfo, customerInfo);
		switch (orderStatus) {
		case "errorToken":
			return "Token错误";
		case "success":
			return "重发成功";
		case "error":
			return "重发失败";
		default:
			return "错误参数";
		}
	}

	/**
	 * 更改订单状态
	 * 
	 * @return
	 */
	@RequestMapping("updateOrderStatus")
	@ResponseBody
	public String updateOrderStatus(HttpServletRequest request) {
		String[] ids = request.getParameterValues("ids");
		User user = UserUtil.getCurrentUser();
		for (String id : ids) {
			OrderInfo orderInfo = orderInfoService.get(Integer.parseInt(id));
			orderInfo.setStatus(6);
			orderInfo.setHandleStatus(2);
			update(orderInfo);
			log.info("操作员：" + user.getName() + "修改订单:" + id + ",为手工处理状态");
		}
		return "success";
	}

	/**
	 * 获取黑名单
	 * 
	 * @return
	 */
	@RequestMapping("getBlackList")
	@ResponseBody
	public Map<String, String> getBlackList() {
		// 获取两天内的黑名单及异常数量
		List<AlarmEvent> blackSum = alarmEventService.getBlackCount();
		List<AlarmEvent> exceptionSum = alarmEventService.getExceptionCount();

		// 获取当前用户这两天看过的OrderId
		User user = UserUtil.getCurrentUser();
		List<String> userReadedOrderId = ParameterListener.userReadedBlackOrderMap.get(user.getLoginName());

		boolean flag = false;

		// 若用户没有阅读记录，则直接插入
		if (userReadedOrderId.size() < 1 && (blackSum.size() > 0 || exceptionSum.size() > 0)) {
			for (AlarmEvent alarmEvent : blackSum) {
				addOperationRecord(alarmEvent.getOrderId() + "");
				userReadedOrderId.add(alarmEvent.getOrderId() + "");
				flag = true;
			}
			for (AlarmEvent alarmEvent : exceptionSum) {
				addOperationRecord(alarmEvent.getOrderId() + "");
				userReadedOrderId.add(alarmEvent.getOrderId() + "");
				flag = true;
			}

			// 用户有记录，则用户没有的插入
		} else if (userReadedOrderId.size() > 0 && (blackSum.size() > 0 || exceptionSum.size() > 0)) {

			for (AlarmEvent alarmEvent : blackSum) {
				for (String orderId : userReadedOrderId) {
					if (!alarmEvent.getOrderId().equals(orderId)) {
						addOperationRecord(orderId);
						userReadedOrderId.add(orderId);
						flag = true;
					}
				}
			}

			for (AlarmEvent alarmEvent : exceptionSum) {
				for (String orderId : userReadedOrderId) {
					if (!alarmEvent.getOrderId().equals(orderId)) {
						addOperationRecord(orderId);
						userReadedOrderId.add(orderId);
						flag = true;
					}
				}
			}
		}

		// 刷新缓存
		ParameterListener.userReadedBlackOrderMap.put(user.getLoginName(), userReadedOrderId);

		Map<String, String> map = new HashMap<>();
		if (flag) {
			map.put("state", "true");
		} else {
			map.put("state", "false");
		}
		map.put("black", blackSum.size() + "");
		map.put("exception", exceptionSum.size() + "");
		return map;

	}

	/**
	 * 插入确认操作
	 * 
	 * @return
	 */
	private void addOperationRecord(String orderId) {
		// 记录客服人员的插入操作
		OperationRecords or = new OperationRecords();
		User user = UserUtil.getCurrentUser();
		or.setOtherId(orderId);
		or.setTime(DateUtils.getSysTimestamp());
		or.setType("1");
		or.setUserName(user.getLoginName());
		operationRecordsService.save(or);
	}

	/**
	 * 更改订单状态 手工成功/手工失败/手工重发
	 * 
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("handleOrder")
	@ResponseBody
	public String handleOrder(HttpServletRequest request) throws ParseException {
		String[] ids = request.getParameterValues("ids");
		String action = request.getParameter("action");
		String filter = request.getParameter("filter");
		List<Integer> list = new ArrayList<>();
		List<Integer> noChargeIdList = new ArrayList<>();
		for (int i = 0, len = ids.length; i < len; i++) {
			int orderId = Integer.parseInt(ids[i]);
			list.add(orderId);
			if ("toResend".equals(action)) {
				OrderInfo orderInfo = orderInfoService.get(orderId);
				createOverTimeOrderRecord(orderInfo);
			}
		}
		// 根据查询条件重发所有手工单
		if ("reSendAll".equals(action)) {
			List<PropertyFilter> filters = new ArrayList<>(22);
			PropertyFilter pFilter = null;
			String arrFilter[] = filter.split(",", 0);
			for (int i = 0; i < arrFilter.length; i++) {
				String name = arrFilter[i].toString().split("::", 0)[0];
				String value = arrFilter[i].toString().split("::", 0)[1];
				if (!"none".equals(value)) {
					pFilter = new PropertyFilter(name, value);
					filters.add(pFilter);
				}
			}
			Page<OrderInfo> page = getPage(request);
			BaseUtil.testSearch(filters, "receiveTime");
			page.setPageNo(1);
			page.setPageSize(1_000_000);
			page = orderInfoService.search(page, filters);
			List<OrderInfo> listOrder = page.getResult();
			List<Integer> listOrderId = new ArrayList<>();
			// List<OrderInfo> listOrder = orderInfoService.search("from
			// OrderInfo where `stutas`=6 and bizType=1");
			for (OrderInfo order : listOrder) {
				listOrderId.add(order.getId());
				OrderInfo orderInfo = orderInfoService.get(order.getId());
				createOverTimeOrderRecord(orderInfo);
			}
			String orderIds = JsonUtils.obj2Json(listOrderId);
			orderInfoService.handleOrder("toResend", orderIds);
			return "success";
		}
		if ("toSuccess".equals(action) || "toFail".equals(action)) {
			List<Integer> orderIdlist = new ArrayList<>();
			List<Integer> providerChargeIdlist = new ArrayList<>();
			for (Integer orderId : list) {
				List<Map<String, Object>> bpcList = bossProviderChargeService.query("select id as id from boss_provider_charge where orderId = " + orderId + " and status!=3 and status!=4 order by id desc limit 0,1;");
				if (bpcList.size() >= 1) {
					{
						providerChargeIdlist.add(Integer.parseInt(bpcList.get(0).get("id").toString()));
						orderIdlist.add(orderId);
					}
				} else { // 订单没有供应商
					noChargeIdList.add(orderId);
				}
			}
			Map<String, Object> map = new HashMap<>();
			map.put("orderIds", orderIdlist);
			map.put("chargeIds", providerChargeIdlist);
			map.put("action", action.equals("toSuccess") ? "toSuccessWithChargeId" : "toFailWithChargeId");
			orderInfoService.handleOrder(map);

			if (noChargeIdList.size() > 0) {
				String orderids = JsonUtils.obj2Json(noChargeIdList);
				orderInfoService.handleOrder(action, orderids);
			}
		} else {
			System.out.println(list);
			String orderids = JsonUtils.obj2Json(list);
			orderInfoService.handleOrder(action, orderids);
		}
		return "success";
	}

	/**
	 * 更改订单状态 手工成功/手工失败/手工重发
	 * 
	 * @return
	 */
	@RequestMapping("handleOrderTest")
	@ResponseBody
	public String handleOrderTest(HttpServletRequest request) {
		String[] ids = request.getParameterValues("ids");
		List<Integer> list = new ArrayList<>();
		for (int i = 0, len = ids.length; i < len; i++) {
			list.add(Integer.parseInt(ids[i]));
		}
		String orderids = JsonUtils.obj2Json(list);
		String action = request.getParameter("action");
		if ("toSuccess".equals(action) || "toFail".equals(action)) {
			List<Integer> orderIdlist = new ArrayList<>();
			List<Integer> providerChargeIdlist = new ArrayList<>();
			for (Integer orderId : list) {
				List<Map<String, Object>> bpcList = bossProviderChargeService.query("select id as id from boss_provider_charge where orderId = " + orderId + " order by id desc limit 0,1;");
				if (bpcList.size() >= 1) {
					{
						providerChargeIdlist.add(Integer.parseInt(bpcList.get(0).get("id").toString()));
						orderIdlist.add(orderId);
					}
				}
			}
			Map<String, Object> map = new HashMap<>();
			map.put("orderIds", orderIdlist);
			map.put("chargeIds", ids);
			map.put("action", action.equals("toSuccess") ? "toSuccessWithChargeId" : "toFailWithChargeId");
			orderInfoService.handleOrder(map);
		} else {
			orderInfoService.handleOrder(action, orderids);
		}

		return "success";
	}

	/**
	 * 获取采购商数据分析统计信息
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getCustomerDataAnalyzeCountInfo")
	@ResponseBody
	public Map<String, Object> getCustomerDataAnalyzeCountInfo(HttpServletRequest request) throws Exception {
		Page<OrderInfo> page = getPage(request);
		page.setPageNo(1);
		page.setPageSize(10000000);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		page = orderInfoService.search(page, filters);
		List<OrderInfo> result = page.getResult();
		if (result.size() == 0) {
			return null;
		}
		Map map = OrderUtils.getCountMap(orderInfoService, filters, "boss_order");
		return map;
	}

	/**
	 * 订单充值次数+1
	 */
	@RequestMapping(value = "changeOrder")
	@ResponseBody
	public String changeOrder(HttpServletRequest request) {
		String[] ids = request.getParameterValues("ids");

		for (String id : ids) {
			OrderInfo orderInfo = orderInfoService.get(Integer.parseInt(id));
			orderInfo.setChargeCount(orderInfo.getChargeCount() + 1);
			update(orderInfo);
		}

		return "success";
	}

	// ----------------------------------------电子券------------------------------------------------

	/**
	 * 默认页面
	 */
	@RequestMapping("ycFlowOrderlist")
	public String ycFlowOrderlist(Integer customerId, Integer categoryId, HttpServletRequest request) {
		request.setAttribute("categoryId", categoryId);
		return "boss/yc/ycFlowOrderList";
	}

	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("exportFlowExcel")
	public void createFlowExcel(HttpServletRequest request, HttpServletResponse response) throws ParseException {
		Map attribute = (Map) request.getSession().getServletContext().getAttribute("sysParam");
		Map<Integer, String> orderStatusMap = (Map<Integer, String>) attribute.get("orderStatusMap");
		Map<Integer, ProductCategoryInfo> productCategoryInfoMap = (Map<Integer, ProductCategoryInfo>) attribute.get("productCategoryInputMap");
		log.info("excel启动 " + DateUtils.dateFormat(new Date()));
		Page<OrderInfo> page = getPage(request);
		page.setPageNo(1);
		page.setPageSize(1000000);

		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		BaseUtil.testSearch(filters, "receiveTime");
		page = orderInfoService.search(page, filters);
		Long center = System.currentTimeMillis();
		List<OrderInfo> list = page.getResult();// 获取数据
		List<Map<String, Object>> mapList = new ArrayList<>();
		List<ProductCategoryInfo> productCategorys = productCategoryInfoService.getAll();
		Map<Integer, ProductCategoryInfo> productCategoryMap = new HashMap<>();
		for (ProductCategoryInfo productCategoryInfo : productCategorys) {
			productCategoryMap.put(productCategoryInfo.getId(), productCategoryInfo);
		}
		for (OrderInfo orderInfo : list) {
			if (productCategoryMap.containsKey(orderInfo.getCategoryId())) {
				Map<String, Object> beanToMap = ExcelUtils.beanToMap(orderInfo);
				ProductCategoryInfo productCategoryInfo = productCategoryMap.get(orderInfo.getCategoryId());
				beanToMap.put("handleTime", (orderInfo.getStatus() == 3 ? orderInfo.getSuccessTime() : orderInfo.getFailTime()));
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
				if (orderInfo.getCost() != null && orderInfo.getPrice() != null && orderInfo.getStatus() == 3) {
					BigDecimal standarPrice = new BigDecimal(productCategoryInfo.getStandarPrice());
					beanToMap.put("earn", standarPrice.subtract(orderInfo.getCost()));
				} else
					beanToMap.put("earn", null);
				if (productCategoryInfoMap.get(orderInfo.getCategoryId()) != null) {
					beanToMap.put("categoryName", productCategoryInfoMap.get(orderInfo.getCategoryId()).getCategoryName());
				}
				beanToMap.put("standardPrice", productCategoryInfo.getStandarPrice());
				if (productCategoryInfo.getArea() == 0) {
					beanToMap.put("productArea", "本省");
				} else if (productCategoryInfo.getArea() == 1) {
					beanToMap.put("productArea", "本国");
				} else {
					beanToMap.put("productArea", null);
				}
				// 订单状态 1新增2充值中3充值成功4充值失败5等待确认6需手工处理',
				ExcelUtils.showValue(beanToMap, "status", "", "新增", "充值中", "充值成功", "充值失败", "等待确认", "需手工处理");
				mapList.add(beanToMap);
			}

		}
		String title = "订单信息";
		// 采购商订单号/
		String[] hearders = new String[] { "id", "产品ID", "充值号码", "运营商", "省份", "城市", "使用区域", "产品名称", "产品规格", "电子券金额", "成本价", "利润", "订单状态", "失败原因", "订单提交时间", "订单处理时间" };// 表头数组
		String[] fields = new String[] { "id", "categoryId", "phone", "beyoudOperation", "province", "city", "productArea", "categoryName", "productScale", "standardPrice", "cost", "earn", "status", "failReason", "receiveTime", "handleTime" };// OrderInfo对象属性数组
		try {
			ExcelUtils.productExcel(response, mapList, title, hearders, fields, log);
		} catch (Exception e) {
			log.error(e.getMessage());
		}

	}

	/**
	 * 导出excel话费手工单
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@SuppressWarnings("all")
	@RequestMapping("selectHFExcel")
	public void createHFExcel(HttpServletRequest request, HttpServletResponse response) throws ParseException {

		Map attribute = (Map) request.getSession().getServletContext().getAttribute("sysParam");
		// Map<Integer, String> orderStatusMap = (Map<Integer, String>)
		// attribute.get("orderStatusMap");
		Map<Integer, ProviderInfo> providerInfoMap = (Map<Integer, ProviderInfo>) attribute.get("providerInputMap");
		Map<Integer, String> orderStatusMap = (Map<Integer, String>) attribute.get("orderStatusMap");
		Map<Integer, ProductCategoryInfo> productCategoryInfoMap = (Map<Integer, ProductCategoryInfo>) attribute.get("productCategoryInputMap");
		log.info("excel启动 " + DateUtils.dateFormat(new Date()));
		Page<OrderInfo> page = getPage(request);
		page.setPageNo(1);
		page.setPageSize(100000);// 设置每页页大小
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);// 获取所有的参数
		BaseUtil.testSearch(filters, "receiveTime");
		page = orderInfoService.search(page, filters);
		Long center = System.currentTimeMillis();
		List<OrderInfo> list = page.getResult();// 获取数据
		List<ProductCategoryInfo> productCategorys = productCategoryInfoService.getAll();

		Map<Integer, ProductCategoryInfo> productCategoryMap = new HashMap<>();
		for (ProductCategoryInfo productCategoryInfo : productCategorys) {
			productCategoryMap.put(productCategoryInfo.getId(), productCategoryInfo);
		}

		List<Map<String, Object>> mapList = new ArrayList<>();
		for (OrderInfo orderInfo : list) {
			Integer bizType = orderInfo.getBizType();
			ProductCategoryInfo productCategoryInfo = productCategoryMap.get(orderInfo.getCategoryId());
			if (productCategoryMap.containsKey(orderInfo.getCategoryId())) {
				// 获取话费订单
				if (bizType.intValue() == 2) {

					Map<String, Object> beanToMap = ExcelUtils.beanToMap(orderInfo);

					beanToMap.put("productScale", productCategoryInfo.getProductNum());

					if (providerInfoMap.get(orderInfo.getSuccessId()) != null) {
						beanToMap.put("providerName", providerInfoMap.get(orderInfo.getSuccessId()).getProviderName());
					}

					beanToMap.put("time", DateUtils.getDate(new Date(), "yyyy/MM/dd"));

					mapList.add(beanToMap);
					// 把获取出来的订单都改为等待确认订单,并修改提单时间为现在
					orderInfo.setSubmitTime(new Date());
					orderInfo.setStatus(6);//设置手工
					orderInfo.setHandleStatus(7);//设置为未完成订单转手工的状态
					orderInfoService.update(orderInfo);
				}
			}
		}

		String title = "手工话费待充值订单信息";
		// 采购商订单号/
		String[] hearders = new String[] { "充值号码", "充值金额(元)", "供货商id", "供货商简称", "时间" };// 表头数组
		String[] fields = new String[] { "phone", "productScale", "successId", "providerName", "time" };// OrderInfo对象属性数组
		try {
			ExcelUtils.productExcel(response, mapList, title, hearders, fields, log);
		} catch (Exception e) {
			log.error(e.getMessage());
		}

	}

	/*
	 * 批量修改话费订单信息跳转
	 * 
	 * @param model
	 * 
	 * @return
	 */
	/* @RequiresPermissions("customer:order:add") */
	@RequestMapping(value = "updateHFOrder", method = RequestMethod.GET)
	public String create2(Model model) {
		return "boss/orderInfo/orderInfoHandleUpdateHF";
	}

	public static void main(String[] args) throws ParseException {
		// j
		/*
		 * String phone = "1.32428717620e".trim().substring(0, 12).replace(".",
		 * ""); System.out.println(phone);
		 */
		// 5/20/17 12:55
		/*
		 * String date = "5/20/17 12:55"; SimpleDateFormat simpleDateFormat =
		 * new SimpleDateFormat("MM/dd/yy HH:mm"); Date parse =
		 * simpleDateFormat.parse(date); String date2 = DateUtils.getDate(parse,
		 * "yyyy-MM-dd HH:mm:ss"); System.out.println(date2);
		 * System.out.println(parse);
		 */
		/*String phone = "13622662311";
		String phone1 = "13622662s311";
		String regEx = "^[1]\\d{10}$";
		Pattern compile = Pattern.compile(regEx);
		System.out.println(compile.matcher(phone).matches());
		System.out.println(compile.matcher(phone1).matches());*/
		
		Integer aa=1231313;
		if (aa.equals("1231313")) {
			System.out.println("没有问题");
		}else{
			System.out.println("有问题");
			
		}
	}

	/**
	 * 批量更新话费订单状态
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@SuppressWarnings("all")
	@RequestMapping(value = "batchUpdateHFOrder", method = RequestMethod.POST)
	public String updateHFOrder(HttpServletRequest request, HttpServletResponse response) throws ParseException {
		String contextPath = request.getSession().getServletContext().getRealPath("/");
		// 文件地址
		String version = contextPath + File.separator + request.getParameter("urll");
		// 表名
		//String steet = "手工话费待充值订单信息表1";

		try {
			log.info("------------------开始生成Excel表格-------------------");
			long startDealDate = System.currentTimeMillis();
			//导入excel的路径,选择第一张表
			ExcelReader excelReader = new ExcelReader(version, 1);
			// 行数量
			int rowCount = excelReader.getRowCount();
			// 列数量
			int cellCount = excelReader.getCellCount();

			// 第二行第四列是订单时间
			String orderDate = excelReader.getCellData(2, 5);
			// 5/20/17 12:55
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
			Date date = simpleDateFormat.parse(orderDate);
			String startDate = DateUtils.getDate(date, "yyyy-MM-dd ")+ "00:00:00";

			Date dateAddOneDay = DateUtils.addDate(date, 1);
			String endDate = DateUtils.getDate(dateAddOneDay, "yyyy-MM-dd ")+ "00:00:00";

			// 获取所有的手工话费订单(两天之内)
			ArrayList<PropertyFilter> filterList = new ArrayList<>();
			// 获取所有等待确认的话费订单
			filterList.add(new PropertyFilter("EQI_bizType", "2"));
			filterList.add(new PropertyFilter("EQI_status", "6"));
			filterList.add(new PropertyFilter("EQI_handleStatus", "7"));//获取未完成订单转手工状态的订单

			// String date = DateUtils.getDate(yesterday, "yyyy-MM-dd
			// HH:mm:ss");
			filterList.add(new PropertyFilter("GED_submitTime", startDate));// 大于
			filterList.add(new PropertyFilter("LED_submitTime", endDate));// 小于
			List<OrderInfo> orderInfos = orderInfoService.search(filterList);
			log.info(orderInfos.toString());
			// 获取所有的话费产品
			filterList.clear();
			filterList.add(new PropertyFilter("EQI_productType", "2"));
			List<ProductCategoryInfo> productCategorys = productCategoryInfoService.search(filterList);

			// 跳过表头(第一行不读)
			order: for (int i = 1; i <= rowCount; i++) {
				log.info("下一个订单");
				// 假设第一列是手机13622662311
				String phone = excelReader.getCellData(i, 1).trim();
				String regEx = "^[1]\\d{10}$";
				Pattern compile = Pattern.compile(regEx);
				if (!compile.matcher(phone).matches()) {
					try {
						phone = excelReader.getCellData(i, 1).trim().substring(0, 12).replace(".", "");
					} catch (Exception e) {
						log.info("跳过第"+i+"行的数据,因为该行数据为:"+phone+",不符合格式,也转换失败");
						continue;
					}
				}
				// 假设第二列是金额
				String money = excelReader.getCellData(i, 2).trim();
				// 假设第三列是供货商id
				String providerid = excelReader.getCellData(i, 3).trim();
				// 假设第四列是供货商简称
				String providerName = excelReader.getCellData(i, 4).trim();
				// 假设第五列是时间
				// String time = excelReader.getCellData(i, 5).trim();
				// 假设第六列是状态
				String status = excelReader.getCellData(i, 6).trim();
				if (!StringUtils.isNotBlank(phone)|!StringUtils.isNotBlank(money)|!StringUtils.isNotBlank(providerid)|!StringUtils.isNotBlank(providerName)|!StringUtils.isNotBlank(status)) {
					log.info("第"+i+"行有内容为空,跳过");
					continue;
				}

				log.info("获取订单,手机:" + phone + ",金额:" + money + ",供货商:+"+providerid+providerName+",状态:" + status);

				NumberSegment numberSegment = numberSegmentService.getNumberSegment(phone.substring(0, 7));
				String operator = numberSegment.getOperator();
				String province = numberSegment.getProvince();

				boolean a = false;
				boolean b = false;
				boolean c = true;

				try {
					// 该订单的产品
					product: for (ProductCategoryInfo ProductInfo : productCategorys) {
						if (operator.equals(ProductInfo.getOperator()) && province.equals(ProductInfo.getProvince()) && money.equals(ProductInfo.getProductNum().toString())) {
							log.info("产品匹配成功");
							
							a = true;
							// 该订单的订单
							for (OrderInfo orderInfo : orderInfos) {
//							log.info("手机:"+phone+",匹配手机:"+orderInfo.getPhone()+",结果:"+orderInfo.getPhone().equals(phone));
//							log.info("产品:"+ProductInfo.getId()+",匹配产品:"+orderInfo.getCategoryId()+",结果:"+orderInfo.getCategoryId().equals(ProductInfo.getId()) );
//							log.info("供应商:"+providerid+",匹配供应商:"+orderInfo.getSuccessId()+",结果:"+orderInfo.getSuccessId().toString().equals(providerid));
								
								if (orderInfo.getPhone().equals(phone) && orderInfo.getCategoryId().equals(ProductInfo.getId()) && orderInfo.getSuccessId().toString().equals(providerid)) {
									log.info("订单匹配成功");
									
									b = true;
									// 订单状态1新增2充值中3充值成功4充值失败5等待确认6需要手工处理7平账审核
									switch (status) {
									case "6":
										orderInfo.setCallbackStatus(2);
										orderInfo.setCallbackTime(new Date());
										log.info("Status改为:"+orderInfo.getStatus()+"");
										orderInfoService.update(orderInfo);
										log.info("订单修改提交");
										break;
									case "3":
										orderInfo.setCallbackStatus(2);
										orderInfo.setCallbackTime(new Date());
										orderInfo.setStatus(3);
										log.info("Status改为:"+orderInfo.getStatus()+"");
										orderInfoService.update(orderInfo);
										log.info("订单修改提交");
										break;
									case "4":
										orderInfo.setCallbackStatus(2);
										orderInfo.setCallbackTime(new Date());
										orderInfo.setStatus(4);
										log.info("Status改为:"+orderInfo.getStatus()+"");
										orderInfoService.update(orderInfo);
										log.info("订单修改提交");
										break;
									default:
										c = false;
										break product;
									}
									continue order;// 写入下一个订单状态
								}/*else{
									log.info("		订单不匹配,换下一个订单试试");
								}*/
							}
						}
					}
				} catch (Exception e) {
					log.error("-error-" + e.getMessage(), e);
					log.info("该订单格式或数据不对,手机:" + phone + ",金额:" + money + ",状态:" + status);
					continue;
				}
				if (!a) {
					log.info("该订单的产品不存在,手机:" + phone + ",金额:" + money + ",状态:" + status);
					continue;
				}
				if (!b) {
					log.info("该订单的订单不存在,手机:" + phone + ",金额:" + money + ",状态:" + status);
					continue;
				}
				if (!c) {
					log.info("该订单写入状态有误,手机:" + phone + ",金额:" + money + ",状态:" + status);
					log.info("状态应该为 ,6充值中 ,3充值成功,4充值失败");
					continue;
				}
			}
			long endDealDate = System.currentTimeMillis();
			log.info("------------------生成Excel表格结束-------------------");
			log.info("耗时:" + DateFormatUtils.formatUTC(endDealDate - startDealDate, "HH时mm分ss秒"));
			return "success";
			// 保存
		} catch (Exception e) {
			log.info("-error-" + e.getMessage(), e);
			log.error("-error-" + e.getMessage(), e);
			return "系统出现异常，请联系开发人员";
		}
	}

	/**
	 * 获取统计信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "getFlowCountInfo", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getFlowCountInfo(HttpServletRequest request) {
		Page<OrderInfo> page = getPage(request);
		page.setPageNo(1);
		page.setPageSize(10000000);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		Map<String, Object> map = null;
		try {
			map = OrderUtils.getFlowCountMap(orderInfoService, filters);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	// boolean isAllow = isProviderAllow(providerAllows,
	// customerId);
	// boolean isForbid = isProviderForbid(providerForbids,
	// customerId);

	// boolean isProductAllow = isProviderProductAllow(providerProductAllows,
	// customerId);
	// boolean isProductForbid = isProviderProductForbid(providerProductForbids,
	// customerId);
	// if((isAllow && !isForbid)&&(isProductAllow && !isProductForbid))
	// /**
	// * 判断供货商是否允许
	// *
	// * @param providerAllows
	// * @param customerId
	// * @return
	// */
	// private boolean isProviderAllow(String providerAllows, int customerId) {
	// boolean isAllow = true;
	// String customerIdStr = customerId + "";
	// if (StringUtils.isNotBlank(providerAllows)) {
	// String[] allowArg = StringUtils.split(providerAllows, ",");
	// for (int i = 0, len = allowArg.length; i < len; i++) {
	// if (StringUtils.equals(allowArg[i], customerIdStr)) {
	// isAllow = true;
	// break;
	// } else {
	// isAllow = false;
	// }
	// }
	// }
	// return isAllow;
	// }
	//
	// /**
	// * 判断供货商是否禁止
	// *
	// * @param providerForbids
	// * @param customerId
	// * @return
	// */
	// private boolean isProviderForbid(String providerForbids, int customerId)
	// {
	// boolean isForbid = false;
	// String customerIdStr = customerId + "";
	// if (StringUtils.isNotBlank(providerForbids)) {
	// String[] forbidArg = StringUtils.split(providerForbids, ",");
	// for (int i = 0, len = forbidArg.length; i < len; i++) {
	// if (StringUtils.equals(forbidArg[i], customerIdStr)) {
	// isForbid = true;
	// break;
	// } else {
	// isForbid = false;
	// }
	// }
	// }
	// return isForbid;
	// }

	/**
	 * 手工处理页面
	 */
	@RequestMapping(value = "orderHandleList", method = RequestMethod.GET)
	public String orderHandleList() {
		return "boss/orderInfo/orderHandleList";
	}

	/**
	 * 采购商数据分析页面
	 */
	@RequestMapping(value = "customerDataAnalyzeList", method = RequestMethod.GET)
	public String customerDataAnalyzeList() {
		return "boss/orderInfo/customerDataAnalyzeList";
	}

	/**
	 * 供应商数据分析页面
	 */
	@RequestMapping(value = "providerDataAnalyzeList", method = RequestMethod.GET)
	public String providerDataAnalyzeList() {
		return "boss/orderInfo/providerDataAnalyzeList";
	}

	/**
	 * 黑名单处理页面
	 */
	@RequestMapping(value = "orderBlackPhoneList", method = RequestMethod.GET)
	public String orderBlackPhoneList() {
		return "boss/orderInfo/orderBlackPhoneList";
	}
}
