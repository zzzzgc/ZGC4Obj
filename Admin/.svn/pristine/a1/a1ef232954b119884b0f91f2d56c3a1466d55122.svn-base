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

import xinxing.boss.admin.boss.customer.domain.CustomerInfo;
import xinxing.boss.admin.boss.customer.domain.CustomerMoneyRecord;
import xinxing.boss.admin.boss.customer.service.CustomerInfoService;
import xinxing.boss.admin.boss.customer.service.CustomerMoneyRecordService;
import xinxing.boss.admin.boss.order.domain.OrderInfo;
import xinxing.boss.admin.boss.order.service.OrderInfoService;
import xinxing.boss.admin.boss.other.cmd.BossCommonCmd;
import xinxing.boss.admin.boss.other.util.BossCommonUtil;
import xinxing.boss.admin.boss.provider.domain.ProductCategoryInfo;
import xinxing.boss.admin.boss.provider.domain.ProviderInfo;
import xinxing.boss.admin.boss.provider.service.ProductCategoryInfoService;
import xinxing.boss.admin.common.csv.CSVUtils;
import xinxing.boss.admin.common.excel.ExcelUtils;
import xinxing.boss.admin.common.schedule.UploadCSV4MonthScheduleService;
import xinxing.boss.admin.common.utils.base.BaseUtil;
import xinxing.boss.admin.common.utils.parameter.ParameterListener;
import xinxing.boss.admin.common.utils.time.DateUtils;

public class UploadCSV4MonthScheduleServiceImpl implements UploadCSV4MonthScheduleService {
	private static final Logger logger = LoggerFactory.getLogger(UploadCSV4MonthScheduleServiceImpl.class);
	
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
		logger.info("updateCSV每月定时任务启动:" + DateUtils.dateFormat(beginDate));
		try {
			createMonthCSV(customerInfoService, orderInfoService, customerMoneyRecordService,
					productCategoryInfoService, null, null, "1,2,3,4,5");
		} catch (Exception e) {
			logger.error("执行updateCSV每月任务出现异常:" + e.getMessage(), e);
		}
		logger.info("updateCSV每月定时任务结束:" + DateUtils.dateFormat(new Date()) + ",耗时:" + (new Date().getTime() - beginDate.getTime()) / 1000);
	}

	public static String createMonthCSV(CustomerInfoService customerInfoService, OrderInfoService orderInfoService,
			CustomerMoneyRecordService customerMoneyRecordService,
			ProductCategoryInfoService productCategoryInfoService, String dateStr, String providerId, String containStr)
			throws Exception {
		// 1.获取所有的zip
		/*if (providerId != null) {
			BossCommonCmd.static_provider_id = providerId;
		}*/
		Map<Integer, CustomerInfo> customerInfoMap = ParameterListener.customerInfoMap;
		String[] strs = { "CustomerMoneyRecord", "OrderInfo" };
		List<String> list = new ArrayList<>();
		String baseStr = BaseUtil.getBackUrl() + "/" + "uploadCSV" + "/";
		for (int i = 0; i < strs.length; i++) {
			list.add(baseStr + "0" + "_" + strs[i] + "_");
			list.add(baseStr + "inside" + "_" + strs[i] + "_");
			for (Entry<Integer, CustomerInfo> entry : customerInfoMap.entrySet()) {
				list.add(baseStr + entry.getKey() + "_" + strs[i] + "_");
			}
		}
		Date date = new Date();
		for (String filePathHead : list) {
			Date date1 = DateUtils.addMonths(date, -1);
			/*if (StringUtils.isNotBlank(dateStr)) {
				date = DateUtils.getDateByStr(dateStr + " 00:00:00");
				date = DateUtils.addMonths(date, -1);
			}*/
			logger.info("月份:" + DateUtils.getYear(date1) +DateUtils.getMonth(date1));
			BossCommonCmd.getMonthCSVByDayCSV(date1, filePathHead, containStr);
		}
		// 生成每月CSV add S
		/*if (dateStr != null) {
			now = DateUtils.getDateByStr(dateStr + " 00:00:00");
		}*/
		date = DateUtils.addMonths(date, -1);
		date = DateUtils.setDays(date, 1);
		Date startDate = DateUtils.getDateByStr(DateUtils.formatDate(date) + " 00:00:00");
		Date endDate = DateUtils.addSeconds(DateUtils.addMonths(startDate, 1), -1);
//		int number = 0;
//		while (startDate.before(endDate)) {
//			number++;
//			Date start = startDate;
//			Date end = null;
//			if (DateUtils.addDate(startDate, 1).before(endDate)) {
//				end = DateUtils.addMilliseconds(DateUtils.addDate(start, 1), -1);
//			} else {
//				end = DateUtils.addMilliseconds(endDate, -1);
//			}
			/*if (!containStr.contains(number + "")) {
				startDate = DateUtils.addDate(startDate, 1);
				continue;
			}*/
			BossCommonUtil.uploadCsvForMonth(date, 0, startDate, endDate, customerInfoService, orderInfoService, customerMoneyRecordService, productCategoryInfoService);
			startDate = DateUtils.addDate(startDate, 1);
		//}
		// add E
		return "success";
	}
	/**
	 * 采购商注资 获得mapList heardersList fieldsList 月用
	 * 
	 * @param start
	 * @param end
	 * @param heardersList
	 * @param orderSb
	 */
	public static void getCustomerMoneyRecordDataList(Date start, Date end, List<String[]> fieldsList, Map<Integer, String> cusIdPathMap,
			Map<Integer, StringBuilder> cusIdSbMap, CustomerMoneyRecordService customerMoneyRecordService, CustomerInfoService customerInfoService) {
		String simpleName = "CustomerMoneyRecord------";
		Long beginTime = new Date().getTime();
		List<CustomerMoneyRecord> list = customerMoneyRecordService.search("from CustomerMoneyRecord where recordTime >=?0 and recordTime<=?1",
				start, end);
		logger.info(simpleName + "查完数据,耗时" + (new Date().getTime() - beginTime) / 1000 + ",数量:"
				+ list.size() + ",start:" + DateUtils.formatDateTime(start) + " end:" + DateUtils.formatDateTime(end));
		beginTime = new Date().getTime();
		List<CustomerInfo> customerInfos = customerInfoService.getAll();
		Map<Integer, CustomerInfo> customerInfoMap = new HashMap<>();
		for (CustomerInfo customerInfo : customerInfos) {
			customerInfoMap.put(customerInfo.getId(), customerInfo);
		}
		for (CustomerMoneyRecord customerMoneyRecord : list) {
			Map<String, Object> beanToMap = ExcelUtils.beanToMap(customerMoneyRecord);
			CustomerInfo customerInfo = customerInfoMap.get(customerMoneyRecord.getCustomerId());
			if (customerInfo != null) {
				beanToMap.put("customerName", customerInfo.getCustomerName());
			}
			ExcelUtils.showValue(beanToMap, "costType", "", "人工注资", "系统注资", "消费", "失败返还", "人工扣款");
			beanToMap.put("beforeMoney", customerMoneyRecord.getFundBalance().subtract(customerMoneyRecord.getCost()));
			getSbByFileName(cusIdPathMap.get(0), fieldsList.get(1), beanToMap, cusIdSbMap.get(0));
			Integer customerId = customerMoneyRecord.getCustomerId();
			getSbByFileName(cusIdPathMap.get(customerId), fieldsList.get(0), beanToMap, cusIdSbMap.get(customerId));
		}

		for (Entry<Integer, String> entry : cusIdPathMap.entrySet()) {
			String path = cusIdPathMap.get(entry.getKey());
			StringBuilder sb = cusIdSbMap.get(entry.getKey());
			CSVUtils.appendCsv(path, sb.toString()); // 追加到文件中
			sb.delete(0, sb.length());
		}

		logger.info(simpleName + "循环结束,耗时" + (new Date().getTime() - beginTime) / 1000);
		list = null;
		customerInfos = null;
	}

	/**
	 * 订单信息 获得mapList heardersList fieldsList 月用
	 * 
	 * @param start
	 * @param end
	 * @param heardersList
	 * @param orderSb
	 */
	@SuppressWarnings("unchecked")
	public static void getOrderInfoDataList(Date start, Date end, List<String[]> fieldsList, Map<Integer, String> cusIdPathMap,
			Map<Integer, StringBuilder> cusIdSbMap, OrderInfoService orderInfoService, ProductCategoryInfoService productCategoryInfoService) {
		String simpleName = "orderInfo------";
		Long beginTime = new Date().getTime();
		List<OrderInfo> list = orderInfoService.search("from OrderInfo where successTime >=?0 and successTime<=?1 and status = 3", start, end);
		// orderInfoService.search("from OrderInfo where successTime >=?0 and successTime<=?1 and status = 3  and customerId = 600042", start, end);
		logger.info(simpleName + "查完数据,耗时" + (new Date().getTime() - beginTime) / 1000 + ",数量:"
				+ list.size() + ",start:" + DateUtils.formatDateTime(start) + " end:" + DateUtils.formatDateTime(end));
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
				getSbByFileName(cusIdPathMap.get(0), fieldsList.get(1), beanToMap, cusIdSbMap.get(0));
				Integer customerId = orderInfo.getCustomerId();
				getSbByFileName(cusIdPathMap.get(customerId), fieldsList.get(0), beanToMap, cusIdSbMap.get(customerId));
			}
		}
		for (Entry<Integer, String> entry : cusIdPathMap.entrySet()) {
			String path = cusIdPathMap.get(entry.getKey());
			StringBuilder sb = cusIdSbMap.get(entry.getKey());
			CSVUtils.appendCsv(path, sb.toString()); // 追加到文件中
			sb.delete(0, sb.length());
		}

		logger.info(simpleName + "循环结束,耗时" + (new Date().getTime() - beginTime) / 1000);
		list = null;
	}

	public static void getSbByFileName(String path, String[] fields, Map<String, Object> beanToMap, StringBuilder sb) {
		for (String fieldName : fields) {
			Object object = beanToMap.get(fieldName) != null ? beanToMap.get(fieldName) : "";
			if (object.getClass() == Date.class) {
				object = DateUtils.formatDateTime((Date) object);
			}
			sb.append(object.toString()).append(",");
		}
		sb.append("\r");
		if (sb.length() > 50000) {
			CSVUtils.appendCsv(path, sb.toString()); // 追加到文件中
			sb.delete(0, sb.length());
		}
	}
}
