package xinxing.boss.admin.common.schedule.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import xinxing.boss.admin.boss.customer.cmd.CustomerMoneyRecordCmd;
import xinxing.boss.admin.boss.customer.domain.CustomerInfo;
import xinxing.boss.admin.boss.customer.domain.CustomerMoneyRecord;
import xinxing.boss.admin.boss.customer.service.CustomerInfoService;
import xinxing.boss.admin.boss.customer.service.CustomerMoneyRecordService;
import xinxing.boss.admin.boss.order.cmd.OrderInfoCmd;
import xinxing.boss.admin.boss.order.domain.OrderInfo;
import xinxing.boss.admin.boss.order.service.OrderInfoService;
import xinxing.boss.admin.boss.provider.domain.ProductCategoryInfo;
import xinxing.boss.admin.boss.provider.domain.ProviderInfo;
import xinxing.boss.admin.boss.provider.service.ProductCategoryInfoService;
import xinxing.boss.admin.common.csv.CSVUtils;
import xinxing.boss.admin.common.excel.ExcelUtils;
import xinxing.boss.admin.common.schedule.UploadCSV4DayScheduleService;
import xinxing.boss.admin.common.utils.parameter.ParameterListener;
import xinxing.boss.admin.common.utils.time.DateUtils;

public class UploadCSV4DayScheduleServiceImpl implements UploadCSV4DayScheduleService {
	private static Logger logger = LoggerFactory.getLogger(UploadCSV4DayScheduleServiceImpl.class);
	@Autowired
	private CustomerMoneyRecordService customerMoneyRecordService;
	@Autowired
	private CustomerInfoService customerInfoService;
	@Autowired
	private OrderInfoService orderInfoService;
	@Autowired
	private ProductCategoryInfoService productCategoryInfoService;

	@Override
	public void doScheduleJob() throws Exception {

		Date beginDate = new Date();

		upload4Day(customerMoneyRecordService, productCategoryInfoService, orderInfoService, customerInfoService, beginDate);

	}

	public static void upload4Day(CustomerMoneyRecordService customerMoneyRecordService, ProductCategoryInfoService productCategoryInfoService,
			OrderInfoService orderInfoService, CustomerInfoService customerInfoService, Date date) {
		Date startDate = new Date();
		logger.info("updateCSV每日定时任务启动:" + DateUtils.dateFormat(startDate));
		date = DateUtils.addDays(date, -1);
		Date startTime = DateUtils.getDateByStr(DateUtils.formatDate(date) + " 00:00:00");
		Date endTime = DateUtils.getDateByStr(DateUtils.formatDate(date) + " 23:59:59");
		// ------------------------------------------------CustomerMoneyRecord
		for (int i = 0; i < 2; i++) {
			List<String[]> heardersList = new ArrayList<>();
			List<String[]> fieldsList = new ArrayList<>();
			StringBuilder insideSb = new StringBuilder(200000);
			Map<Integer, StringBuilder> cusSbList = new HashMap<>();
			Class<?> clazz = null;
			if (i == 0) {// customer
				clazz = CustomerMoneyRecord.class;
				heardersList.add(CustomerMoneyRecordCmd.hearders);
				fieldsList.add(CustomerMoneyRecordCmd.fields);
				heardersList.add(CustomerMoneyRecordCmd.inside_hearders);
				fieldsList.add(CustomerMoneyRecordCmd.inside_fields);
				UploadCSV4DayScheduleServiceImpl.getCustomerMoneyRecordDataList(startTime, endTime, heardersList, fieldsList, insideSb, cusSbList,
						customerMoneyRecordService, customerInfoService);

			} else if (i == 1) {// orderInfo
				clazz = OrderInfo.class;
				heardersList.add(OrderInfoCmd.hearders);
				fieldsList.add(OrderInfoCmd.fields);
				heardersList.add(OrderInfoCmd.inside_hearders);
				fieldsList.add(OrderInfoCmd.inside_fields);
				UploadCSV4DayScheduleServiceImpl.getOrderInfoDataList(startTime, endTime, fieldsList, heardersList, insideSb, cusSbList,
						orderInfoService, productCategoryInfoService);
			}
			String fileName = clazz.getSimpleName() + "_" + DateUtils.formatDate(startTime);
			try {
				if (cusSbList.size() > 0) {
					for (Entry<Integer, StringBuilder> entry : cusSbList.entrySet()) {
						CSVUtils.updateByCSV(entry.getValue(), "uploadCSV", entry.getKey() + "_" + fileName + ".csv", true, logger);
					}
				}
				CSVUtils.updateByCSV(insideSb, "uploadCSV", "inside_" + fileName + ".csv", true, logger);
				// CSVUtils.updateByCSV(insideSb, "uploadCSV", "inside_" + fileName + ".csv", true, logger);
			} catch (Exception e) {
				logger.error("执行uploadCSV每日任务出现异常" + e.getMessage(), e);
				//e.printStackTrace();
			}
			heardersList = null;
			fieldsList = null;
			insideSb = null;
			cusSbList = null;
			clazz = null;
			logger.info("updateCSV每日定时任务结束:" + DateUtils.dateFormat(new Date()) + ",耗时:" + (new Date().getTime() - startDate.getTime()) / 1000);
		}
	}

	/**
	 * 订单信息 获得mapList heardersList fieldsList
	 * 
	 * @param start
	 * @param end
	 * @param heardersList
	 * @param orderSb
	 */
	@SuppressWarnings("unchecked")
	public static void getOrderInfoDataList(Date start, Date end, List<String[]> fieldsList, List<String[]> heardersList, StringBuilder orderSb,
			Map<Integer, StringBuilder> cusIdSBList, OrderInfoService orderInfoService, ProductCategoryInfoService productCategoryInfoService) {
		Long beginTime = new Date().getTime();
		List<OrderInfo> list = orderInfoService.search("from OrderInfo where successTime >=?0 and successTime<=?1 and status = 3 ", start, end);
		logger.info("OrderInfo------查完数据,耗时" + (new Date().getTime() - beginTime) / 1000 + ",数量:" + list.size() + ",start:"
				+ DateUtils.formatDateTime(start) + " end:" + DateUtils.formatDateTime(end));
		Map<String, Object> attribute = ParameterListener.sysParamMap;
		Map<Integer, CustomerInfo> customerInfoMap = (Map<Integer, CustomerInfo>) attribute.get("customerInputMap");
		Map<Integer, ProviderInfo> providerInfoMap = (Map<Integer, ProviderInfo>) attribute.get("providerInputMap");
		Map<Integer, ProductCategoryInfo> productCategoryInfoMap = (Map<Integer, ProductCategoryInfo>) attribute.get("productCategoryInputMap");
		List<ProductCategoryInfo> productCategorys = productCategoryInfoService.getAll();
		Map<Integer, ProductCategoryInfo> productCategoryMap = new HashMap<>();
		beginTime = new Date().getTime();
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
				if (orderSb.length() == 0) {
					for (String hearder : heardersList.get(1)) {
						orderSb.append(hearder).append(",");
					}
					orderSb.append("\r");
				}
				getSbByFileName(orderSb, fieldsList.get(1), beanToMap);
				Integer customerId = orderInfo.getCustomerId();
				StringBuilder cidSB = cusIdSBList.get(customerId);
				if (cidSB == null) {
					cidSB = new StringBuilder(100000);
					for (String hearder : heardersList.get(0)) {
						cidSB.append(hearder).append(",");
					}
					cidSB.append("\r");
					cusIdSBList.put(customerId, cidSB);
				}
				getSbByFileName(cidSB, fieldsList.get(0), beanToMap);
			}
		}
		//System.out.println("OrderInfosb一天结束,耗时" + Double.valueOf((beginTime - new Date().getTime()) + "") / 1000);
		logger.info("OrderInfo------查询一天 start:" + DateUtils.formatDateTime(start) + " end:" + DateUtils.formatDateTime(end) + "耗时:" + (new Date().getTime() - beginTime) /1000 + ",sb的长度" + orderSb.length());
	}

	/**
	 * 采购商注资 获得mapList heardersList fieldsList
	 * 
	 * @param start
	 * @param end
	 * @param heardersList
	 * @param orderSb
	 */
	public static void getCustomerMoneyRecordDataList(Date start, Date end, List<String[]> heardersList, List<String[]> fieldsList,
			StringBuilder orderSb, Map<Integer, StringBuilder> cusIdMapList, CustomerMoneyRecordService customerMoneyRecordService,
			CustomerInfoService customerInfoService) {
		Long beginTime = new Date().getTime();
		List<CustomerMoneyRecord> list = customerMoneyRecordService.search("from CustomerMoneyRecord where recordTime >=?0 and recordTime<=?1",
				start, end);
		logger.info("CustomerMoneyRecord------查完数据,耗时" + (new Date().getTime() - beginTime) / 1000 + ",数量:" + list.size() + ",start:"
				+ DateUtils.formatDateTime(start) + " end:" + DateUtils.formatDateTime(end));
		List<CustomerInfo> customerInfos = customerInfoService.getAll();
		Map<Integer, CustomerInfo> customerInfoMap = new HashMap<>();
		beginTime = new Date().getTime();
		for (CustomerInfo customerInfo : customerInfos) {
			customerInfoMap.put(customerInfo.getId(), customerInfo);
		}
		for (CustomerMoneyRecord customerMoneyRecord : list) {
			Map<String, Object> beanToMap = ExcelUtils.beanToMap(customerMoneyRecord);
			CustomerInfo customerInfo = customerInfoMap.get(customerMoneyRecord.getCustomerId());
			if (customerInfo != null) {
				beanToMap.put("customerName", customerInfo.getCustomerName());
			}

			if (orderSb.length() == 0) {
				for (String hearder : heardersList.get(1)) {
					orderSb.append(hearder).append(",");
				}
				orderSb.append("\r");
			}

			ExcelUtils.showValue(beanToMap, "costType", "", "人工注资", "系统注资", "消费", "失败返还", "人工扣款");
			beanToMap.put("beforeMoney", customerMoneyRecord.getFundBalance().subtract(customerMoneyRecord.getCost()));
			getSbByFileName(orderSb, fieldsList.get(1), beanToMap);
			Integer customerId = customerMoneyRecord.getCustomerId();
			StringBuilder cidSb = cusIdMapList.get(customerId);
			if (cidSb == null) {
				cidSb = new StringBuilder(100000);

				for (String hearder : heardersList.get(0)) {
					cidSb.append(hearder).append(",");
				}
				cidSb.append("\r");
				cusIdMapList.put(customerId, cidSb);
			}
			getSbByFileName(cidSb, fieldsList.get(0), beanToMap);
		}
		logger.info("CustomerMoneyRecord------查询一天 start:" + DateUtils.formatDateTime(start) + " end:" + DateUtils.formatDateTime(end) + "耗时:" + (new Date().getTime() - beginTime) /1000 + ",sb的长度" + orderSb.length());
		list = null;
		customerInfos = null;
	}

	public static void getSbByFileName(StringBuilder sb, String[] fields, Map<String, Object> beanToMap) {
		for (String fieldName : fields) {
	 		Object object = beanToMap.get(fieldName) != null ? beanToMap.get(fieldName) : "";
			if (object.getClass() == Date.class) {
				object = DateUtils.formatDateTime((Date) object);
			}
			sb.append(object.toString()).append(",");
		}
		sb.append("\r");
	}
}
