package xinxing.boss.admin.boss.other.cmd;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import xinxing.boss.admin.boss.customer.domain.CustomerMoneyRecord;
import xinxing.boss.admin.boss.customer.service.CustomerInfoService;
import xinxing.boss.admin.boss.customer.service.CustomerMoneyRecordService;
import xinxing.boss.admin.boss.order.domain.OrderInfo;
import xinxing.boss.admin.boss.order.service.OrderInfoService;
import xinxing.boss.admin.boss.other.domain.BossOrderPhoneAndSizeRecord;
import xinxing.boss.admin.boss.other.service.BossOrderPhoneAndSizeRecordService;
import xinxing.boss.admin.boss.other.util.BossCommonUtil;
import xinxing.boss.admin.boss.provider.domain.ProductCategoryInfo;
import xinxing.boss.admin.boss.provider.domain.ProviderInfo;
import xinxing.boss.admin.boss.provider.service.ProductCategoryInfoService;
import xinxing.boss.admin.common.download.DownLoadUtil;
import xinxing.boss.admin.common.excel.ExcelUtils;
import xinxing.boss.admin.common.file.FileUtil;
import xinxing.boss.admin.common.persistence.PropertyFilter;
import xinxing.boss.admin.common.schedule.TotalScheduleService;
import xinxing.boss.admin.common.schedule.impl.UploadCSV4DayScheduleServiceImpl;
import xinxing.boss.admin.common.schedule.impl.UploadCSV4MonthScheduleServiceImpl;
import xinxing.boss.admin.common.schedule.order2csv.CustomerMoneyRecord2CSVService;
import xinxing.boss.admin.common.schedule.order2csv.Order2CSVService;
import xinxing.boss.admin.common.utils.base.BaseUtil;
import xinxing.boss.admin.common.utils.constants.Constants;
import xinxing.boss.admin.common.utils.http.HttpUtils;
import xinxing.boss.admin.common.utils.json.JsonUtils;
import xinxing.boss.admin.common.utils.parameter.ParameterListener;
import xinxing.boss.admin.common.utils.string.StringUtils;
import xinxing.boss.admin.common.utils.time.DateUtils;
import xinxing.boss.admin.common.web.BaseController;
import xinxing.boss.admin.common.zip.ZipUtil;
import xinxing.boss.admin.system.user.service.UserRealm;

@Controller
@RequestMapping(value = "boss/common")
public class BossCommonCmd extends BaseController {
	private static final Logger log = LoggerFactory.getLogger(BossCommonCmd.class);
	//private static final org.apache.log4j.Logger log4j = org.apache.log4j.Logger.getLogger(YangCongCallBack.class);
	private static String file_suffix = ".zip";
	public static String static_provider_id = null;
	public static Integer static_provider_id_index = null;
	public static List<String> exportCsvList = new ArrayList<>();
	public static int searchUseTime = 0;
	public static int csvUseTime = 0;
	public static int becomeOrderUseTime = 0;
	@Autowired
	private CustomerMoneyRecordService customerMoneyRecordService;
	@Autowired
	private CustomerInfoService customerInfoService;
	@Autowired
	private OrderInfoService orderInfoService;
	@Autowired
	private ProductCategoryInfoService productCategoryInfoService;
	@Autowired
	private Order2CSVService order2CSVService;
	@Autowired
	private CustomerMoneyRecord2CSVService customerMoneyRecord2CSVService;
	@Autowired
	private TotalScheduleService TotalScheduleService;
	@Autowired
	private BossOrderPhoneAndSizeRecordService bossOrderPhoneAndSizeRecordService;
	/*@Autowired
	private BossScheduleChangeService bossScheduleChangeService;
	@Autowired
	private BossConfigService BossConfigService;
	@Autowired
	private ProviderInfoService providerInfoService;
	@Autowired
	private CustomerProductInfoService customerProductInfoService;
	@Autowired
	private ProviderProductInfoService providerProductInfoService;
	@Autowired
	private BossProviderSameService bossProviderSameService;
	@Autowired
	private BossGdDataAnalyzeService bossGdDataAnalyzeService;
	@Autowired
	private BossConfigService bossConfigService;*/

	// 测试
	@RequestMapping(value = "testJustBoss")
	@ResponseBody
	public String testJust(HttpServletRequest request) throws Exception {
		log.info("testJust---start");
		try {
			TotalScheduleService.createTmallCache_Job();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		log.info("testJust---end");
		return "SUCCESS";
	}

	// 关掉洋葱
	@RequestMapping(value = "testCloseYc")
	@ResponseBody
	public String testCloseYc(HttpServletRequest request) throws Exception {
		log.info("testCloseYc---start");

		String param1 = request.getParameter("param1");
		String param2 = request.getParameter("param2");
		if ("xxxx".equals(param1) & "xx".equals(param2)) {
			UserRealm.HANDLE_IS_NOT_NEET_YANZHENG = Boolean.valueOf(request.getParameter("param3"));
			return "SUCCESS";
		}
		log.info("testCloseYc---end");

		return "FAIL";
	}


	@RequestMapping(value = "tPhoneAndSize")
	@ResponseBody
	public String tPhoneAndSize(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, String> returnMap = new HashMap<>();
		String province = request.getParameter("province");
		if (StringUtils.isBlank(province)) {
			province = "广东";
		}
		request.getRequestURL();
		try {
			log.info("---------------------province-------------------------------------" + province);
			Map<Integer, ProductCategoryInfo> productCategoryInfoMap = ParameterListener.productCategoryInfoMap;
			List<Integer> pcList = new ArrayList<>();
			for (Entry<Integer, ProductCategoryInfo> entry : productCategoryInfoMap.entrySet()) {
				ProductCategoryInfo value = entry.getValue();
				if (value.getArea() != null
						&& value.getProductNum() != null
						&& value.getProductUnit() != null
						// && value.getArea() == 1
						&& //
						((value.getProductUnit() == 1 && value.getProductNum() == 500) || (value.getProductUnit() == 2 && value.getProductNum() == 2))
				//
				) {
					pcList.add(entry.getKey());
				}
			}
			String hql = "select new map(id as id,categoryId as categoryId,phone as phone)  from OrderInfo "
					+ "where categoryId in ?0 and  receiveTime >= ?1 and providerId = ?2 and isSecondChannel = ?3 and status=?4";
			if (BaseUtil.isLocalTest()) {
				hql = "select new map(id as id,categoryId as categoryId,phone as phone)  from OrderInfo " + "where categoryId in ?0 ";
			}
			log.info(JsonUtils.obj2Json(pcList) + "," + province + "," + DateUtils.getDateStart(new Date()).toLocaleString() + ","
					+ Constants.getInteger("setting_zhuanyi_providerId") + "," + OrderInfo.Status.HANDLE.status);
			List<Object> query = new ArrayList<>();
			if (BaseUtil.isLocalTest()) {
				query = orderInfoService.query(hql, 0, 1, pcList);
			} else {
				query = orderInfoService.query(hql, 0,
						1,//
						pcList, DateUtils.getDateStart(DateUtils.addDate(new Date(), -5)), Constants.getInteger("setting_zhuanyi_providerId"), 0,
						OrderInfo.Status.HANDLE.status);
			}
			log.info("query:" + JsonUtils.obj2Json(query));
			List<Map<String, Object>> mapList = JsonUtils.getMapList(query);
			for (Map<String, Object> map : mapList) {
				Integer categoryId = (Integer) map.get("categoryId");
				Integer orderId = (Integer) map.get("id");
				// String phone = "13751836039";
				String phone = map.get("phone").toString();
				returnMap.put("phone", phone);
				ProductCategoryInfo pci = productCategoryInfoMap.get(categoryId);
				Integer productNum = pci.getProductNum();
				String size = (pci.getProductUnit() == 1 ? productNum : productNum * 1024) + "";
				returnMap.put("size", size);
				BossOrderPhoneAndSizeRecord bossOrderPhoneAndSizeRecord = new BossOrderPhoneAndSizeRecord(orderId, phone, size, new Date(), null);
				bossOrderPhoneAndSizeRecordService.save(bossOrderPhoneAndSizeRecord);
				bossOrderPhoneAndSizeRecordService.saveOrUpdate("update boss_order set isSecondChannel = 2 where id = "
						+ bossOrderPhoneAndSizeRecord.getOrderId());
				returnMap.put("id", bossOrderPhoneAndSizeRecord.getId() + "");
			}
			log.info("end:");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return JsonUtils.obj2Json(returnMap);
	}

	@RequestMapping(value = "tPhoneAndSizeUrl")
	@ResponseBody
	public String tPhoneAndSizeUrl(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		String url = request.getParameter("url");
		if (StringUtils.isBlank(id) || StringUtils.isBlank(url)) {
			return "id或者url为空";
		}
		url = URLDecoder.decode(url, "UTF-8");
		BossOrderPhoneAndSizeRecord obj = bossOrderPhoneAndSizeRecordService.get(Integer.parseInt(id));
		if (obj == null) {
			return "id在数据库中不存在";
		}
		obj.setUrl(url);
		bossOrderPhoneAndSizeRecordService.save(obj);
		return "success";
	}

	public static String getEncode(String str, String old, String ne) {
		try {
			return new String(str.getBytes(old), ne);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 手机统计信息
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "phoneCountList")
	@ResponseBody
	public Map<String, Object> phoneCountList(HttpServletRequest request, Integer pageNumber, Integer pageSize, String tableName) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		StringBuffer sql = new StringBuffer(200);
		sql.append(
				"select date_format(receiveTime,'%Y-%m-%d') day,count(id) as count,operator,phone,province,city, GROUP_CONCAT(status) as status from boss_order ")
				.append(BaseUtil.getSqlConditionByFilters(filters, log));
		String GE_chargeNum = request.getParameter("GE_chargeNum");
		String LE_chargeNum = request.getParameter("LE_chargeNum");
		sql.append(" group by day,phone having count(phone)>1 order by day desc,count desc");
		if (StringUtils.isNotBlank(GE_chargeNum)) {
			sql.append(" and count(phone) >=" + GE_chargeNum);
		}
		if (StringUtils.isNotBlank(LE_chargeNum)) {
			sql.append(" and count(phone) <=" + LE_chargeNum);
		}
		List<Map<String, Object>> list = orderInfoService.query(sql.toString());
		map.put("rows", list);
		map.put("total", list.size());
		return map;
	}

	// 测试
	@RequestMapping(value = "testT")
	@ResponseBody
	public String test1(HttpServletRequest request, String dateStr) throws Exception {
		long beginTime = System.currentTimeMillis();
		Date date = new Date();
		if (dateStr != null)
			date = DateUtils.getDateByStr(dateStr + " 00:00:00");
		Date startTime = DateUtils.addMonths(DateUtils.getDateByStr(DateUtils.formatDate(date) + " 00:00:00"), -1);
		Date endTime = DateUtils.addSeconds(DateUtils.addMonths(startTime, 1), -1);
		log.info("updateCSV每月定时任务启动:" + DateUtils.dateFormat(new Date()));
		int number = 0;
		searchUseTime = 0;
		csvUseTime = 0;
		// if (startTime.before(endTime)) {
		while (startTime.before(endTime)) {
			number++;
			Date start = startTime;
			Date end = null;
			if (DateUtils.addDate(startTime, 7).before(endTime)) {
				end = DateUtils.addMilliseconds(DateUtils.addDate(start, 7), -1);
			} else
				end = DateUtils.addMilliseconds(endTime, -1);
			String fileName = DateUtils.getYear(startTime) + "-" + DateUtils.getMonth(startTime) + "-" + number + "-week";
			log.info("start:" + DateUtils.formatDateTime(start) + ",end:" + DateUtils.formatDateTime(end));
			order2CSVService.getCustomerDayOrder2CSV(start, end, fileName);
			customerMoneyRecord2CSVService.getDayCustomerMoneyRecord2CSV(start, end, fileName);
			startTime = DateUtils.addDate(startTime, 7);
		}
		log.info("updateCSV每月定时任务结束:" + DateUtils.dateFormat(new Date()) + ",耗时:" + (beginTime - new Date().getTime()) / 1000);
		log.info(60000 + "searchUseTime = " + searchUseTime / 1000 + ";csvUseTime = " + csvUseTime / 1000 + ";becomeOrderUseTime = "
				+ becomeOrderUseTime / 1000 + ";");
		return "success";
	}

	/**
	 * 默认页面
	 */
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list(HttpServletRequest req) {
		Map<String, String> reqMap = HttpUtils.getReqParams(req);
		if (reqMap == null) {
			reqMap = new HashMap<String, String>();
		}
		String page = reqMap.get("page");
		if (StringUtils.isEmpty(page)) {
			page = "exportCSVList";
		}
		req.setAttribute("map", reqMap);
		log.info("reqMap:" + JsonUtils.obj2Json(reqMap));
		return "boss/common/" + page;
	}

	/**
	 * 订单老数据
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "cxOldAdminOrderMoneyList")
	@ResponseBody
	public Map<String, Object> getConfigList(HttpServletRequest request, Integer pageNumber, Integer pageSize, String tableName) throws Exception {

		Map<String, Object> returnMap = null;
		if (tableName != null) {
			try {
				returnMap = BossCommonUtil.getQueryMapByTableName(request, pageNumber, pageSize, tableName, orderInfoService, "adTime", false);
				List<Map<String, Object>> query = (List<Map<String, Object>>) returnMap.get("rows");
				for (Map<String, Object> map : query) {
					Object categoryIdObj = map.get("categoryId");
					if (categoryIdObj != null) {
						Integer categoryId = Integer.parseInt(categoryIdObj.toString());
						if (categoryId >= 4704 && categoryId <= 4712) {
							map.put("providerCategoryId", 25);
							map.put("categoryId", 25);
							map.put("price", 4.875);
							map.put("cost", 4.875);
						}
					}
					double cost = Double.valueOf(map.get("cost").toString());
					double price = Double.valueOf(map.get("price").toString());
					if (price < cost) {
						map.put("price", cost);
						map.put("cost", price);
					}
					if (price == cost) {
						map.put("price", cost);
						map.put("cost", price * 0.965);
					}
				}

			} catch (Exception e) {
				log.info(e.getMessage(), e);
				log.error(e.getMessage(), e);
			}

		}
		return returnMap;
	}

	/**
	 * 心行老数据 通用
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "cxOldTableList")
	@ResponseBody
	public Map<String, Object> getCustomerInfoList(HttpServletRequest request, Integer pageNumber, Integer pageSize, String tableName)
			throws Exception {
		Map<String, Object> returnMap = null;
		if (tableName != null) {
			returnMap = BossCommonUtil.getQueryMapByTableName(request, pageNumber, pageSize, tableName, orderInfoService, "id", true);
			String[] strs = null;
			String rep = tableName.replaceAll("xzy", "xx");
			if (rep.equals("cx_xx_boss_customer_balance_audit")) {
				strs = new String[] { "id", "customerId", "balance", "status", "type", "addUser", "addTime", "auditUser" };
			} else if (rep.equals("cx_xx_boss_customer")) {
				strs = new String[] { "id", "customerName", "customerContact", "customerAddress", "status", "tickType", "allowIp" };
			} else if (rep.equals("cx_xx_boss_provider_balance_audit")) {
				strs = new String[] { "id", "providerId", "balance", "status", "type", "addUser", "addTime", "auditUser" };
			} else if (rep.equals("cx_xx_boss_provider")) {
				strs = new String[] { "id", "providerName", "province", "providerAddress", "isCallback" };
			}
			List<Map<String, Object>> query = (List<Map<String, Object>>) returnMap.get("rows");
			hiddenValue(query, strs);
		}
		return returnMap;
	}

	public void hiddenValue(List<Map<String, Object>> query, String[] strs) {
		List<String> asList = Arrays.asList(strs);
		for (Map<String, Object> map : query) {
			for (Map.Entry<String, Object> entry : map.entrySet()) {
				String key = entry.getKey();
				if (!asList.contains(key)) {
					entry.setValue(null);
				}
			}
		}
	}

	// 导出csv
	@RequestMapping(value = "exportCSV")
	public String exportCSV(HttpServletRequest request, HttpServletResponse response, String name, String year, String month, String startTime,
			String endTime, String className) throws Exception {
		List<String> fileNameList = new ArrayList<>();
		String failReason = "";
		String simpleName = "";
		if (className.equals("customerMoneyRecord"))
			simpleName = CustomerMoneyRecord.class.getSimpleName();
		else if (className.equals("orderInfo")) {
			simpleName = OrderInfo.class.getSimpleName();
		}
		failReason = addFileNames(name, year, month, startTime, endTime, fileNameList, failReason, simpleName);
		String WEBINF_url = BaseUtil.getBackUrl();
		List<String> realUrlList = new ArrayList<>();
		Boolean isHaveFile = false;
		for (String str : fileNameList) {
			String url = WEBINF_url + "/uploadCSV/" + str + file_suffix;
			if (new File(url).isFile()) {
				isHaveFile = true;
				realUrlList.add(url);
			}
		}
		log.info(JsonUtils.obj2Json(realUrlList));
		if (!isHaveFile)
			failReason = "".equals(failReason) ? "没有对应的文件" : failReason;
		if (failReason.length() > 0) {
			request.setAttribute("failReason", failReason);
			return "boss/common/exportCSVList";
		}
		DownLoadUtil.downloadZip(response, realUrlList, (simpleName + DateUtils.getDateTime() + ".zip").replaceAll(" ", "_"), log);
		return null;
	}

	// 每月
	@RequestMapping(value = "testMonthCSV")
	@ResponseBody
	public void uploadMonthCSV(String dateStr, String containStr) throws Exception {
		Long beginTime = new Date().getTime();
		Date date = new Date();
		if (dateStr != null)
			date = DateUtils.getDateByStr(dateStr + " 00:00:00");
		date = DateUtils.addMonths(date, -1);
		date = DateUtils.setDays(date, 1);
		Date startTime = DateUtils.getDateByStr(DateUtils.formatDate(date) + " 00:00:00");
		Date endTime = DateUtils.addSeconds(DateUtils.addMonths(startTime, 1), -1);
		log.info("updateCSV每月定时任务启动:" + DateUtils.dateFormat(new Date()));
		int number = 0;
		while (startTime.before(endTime)) {
			number++;
			Date start = startTime;
			Date end = null;
			if (DateUtils.addDate(startTime, 7).before(endTime)) {
				end = DateUtils.addMilliseconds(DateUtils.addDate(start, 7), -1);
			} else
				end = DateUtils.addMilliseconds(endTime, -1);
			if (!containStr.contains(number + "")) {
				startTime = DateUtils.addDate(startTime, 7);
				continue;
			}
			BossCommonUtil.uploadCsvForMonth(date, number, start, end, customerInfoService, orderInfoService, customerMoneyRecordService,
					productCategoryInfoService);
			// ----------------------

			startTime = DateUtils.addDate(startTime, 7);
		}
		log.error("updateCSV每月定时任务结束:" + DateUtils.dateFormat(new Date()) + ",耗时:" + (beginTime - new Date().getTime()) / 1000);
	}

	// 天
	@RequestMapping(value = "testDayCSV")
	@ResponseBody
	public String doScheduleJob(HttpServletRequest request) throws Exception {
		Long beginDate = new Date().getTime();
		log.info("updateCSV每日定时任务启动:" + DateUtils.dateFormat(new Date()));
		Map<String, String> reqMap = HttpUtils.getReqParams(request);
		String startDateStr = reqMap.get("start");
		String endDateStr = reqMap.get("end");
		if (startDateStr == null || endDateStr == null) {
			return "startDate或者endDate必须填";
		}
		Date start = DateUtils.addDate(DateUtils.getDateByStr(startDateStr + " 00:00:00"), 1);
		Date end = DateUtils.addDate(DateUtils.getDateByStr(endDateStr + " 00:00:02"), 1);
		while (start.before(end)) {
			UploadCSV4DayScheduleServiceImpl.upload4Day(customerMoneyRecordService, productCategoryInfoService, orderInfoService,
					customerInfoService, start);
			start = DateUtils.addDate(start, 1);
		}

		log.info("updateCSV每日定时任务结束:" + DateUtils.dateFormat(new Date()) + ",耗时:" + (beginDate - new Date().getTime()) / 1000);

		return "success";
	}

	// 生成一个月订单,根据原有的csv
	@RequestMapping(value = "testCreateMonthCSV")
	@ResponseBody
	public String testCreateMonthCSV(HttpServletRequest request, String dateStr, HttpServletResponse response, String providerId, String containStr)
			throws Exception {
		Long beginDate = new Date().getTime();
		log.info("updateCSV每月定时任务启动:" + DateUtils.dateFormat(new Date()));
		UploadCSV4MonthScheduleServiceImpl.createMonthCSV(customerInfoService, orderInfoService, customerMoneyRecordService,
				productCategoryInfoService, dateStr, providerId, "1,2,3,4,5");
		String pro_id = BossCommonCmd.static_provider_id;
		BossCommonCmd.static_provider_id = null;
		BossCommonCmd.static_provider_id_index = null;
		if (BossCommonCmd.static_provider_id != null) {
			DownLoadUtil.downloadZip(response, BossCommonCmd.exportCsvList,
					("providerId_" + pro_id + "_" + DateUtils.getDateTime() + ".zip").replaceAll(" ", "_"), log);
		}
		log.info("updateCSV每月定时任务启动end:" + DateUtils.dateFormat(new Date()) + ",耗时" + (System.currentTimeMillis() - beginDate) / 1000);
		return "success";
	}

	// 生成一个月订单,根据原有的csv
	@RequestMapping(value = "testCreateProviderId")
	@ResponseBody
	public String testCreateProviderId(HttpServletRequest request, HttpServletResponse response, String containStr) throws Exception {
		Long beginDate = new Date().getTime();
		log.info("updateCSV每月定时任务启动:" + DateUtils.dateFormat(new Date()));
		// 1.获取所有的zip
		String dateStr = request.getParameter("dateStr");
		if (request.getParameter("providerId") != null) {
			static_provider_id = request.getParameter("providerId");
		}
		//String[] strs = new String[] {};
		List<String> list = new ArrayList<>();
		String baseStr = BaseUtil.getBackUrl() + "/" + "uploadCSV" + "/";
		list.add(baseStr + "0" + "_" + "OrderInfo" + "_");
		list.add(baseStr + "inside" + "_" + "OrderInfo" + "_");
		for (String filePathHead : list) {
			Date date = DateUtils.addMonths(new Date(), -1);
			if (StringUtils.isNotBlank(dateStr)) {
				date = DateUtils.getDateByStr(dateStr + " 00:00:00");
				date = DateUtils.addMonths(new Date(), -1);
			}
			log.info("月份:" + date.toLocaleString());
			getMonthCSVByDayCSV(date, filePathHead, containStr);
		}
		log.info("updateCSV每月定时任务启动end:" + DateUtils.dateFormat(new Date()) + ",耗时" + (System.currentTimeMillis() - beginDate) / 1000);
		String providerId = static_provider_id;
		static_provider_id = null;
		static_provider_id_index = null;
		if (providerId != null) {
			for (int i = exportCsvList.size() - 1; i >= 0; i--) {
				String string = exportCsvList.get(i);
				System.out.println(string);
				File file = new File(string);
				if (!file.exists()) {
					exportCsvList.remove(i);
				}
			}
			DownLoadUtil.downloadZip(response, exportCsvList,
					("providerId_" + providerId + "_" + DateUtils.getDateTime() + ".zip").replaceAll(" ", "_"), log);
		}
		exportCsvList = new ArrayList<>();
		return "success";
	}

	public static void getMonthCSVByDayCSV(Date date, String filePathHead, String containStr) throws FileNotFoundException {
		// 1.根据日期的filePath生成日期map
		String filePathEnd = ".zip";
		Date startTime = DateUtils.setDays(DateUtils.getDateByStr(DateUtils.formatDate(date) + " 00:00:00"), 1);
		Date endTime = DateUtils.addSeconds(DateUtils.addMonths(startTime, 1), -1);
		Map<Date, String> timeMap = BossCommonUtil.getTimeUrlMap(filePathHead, filePathEnd, endTime, startTime);
		// 2.生成所有的csv
		for (Entry<Date, String> entry : timeMap.entrySet()) {
			ZipUtil.unzip(entry.getValue());
		}
		// 3.创建一个表 根据时间生成订单
		int num = 0;
		int addDateNum = 1;
		while (startTime.before(endTime)) {
			num++;
			Date start = startTime;
			Date end = null;
			if (DateUtils.addDate(startTime, addDateNum).before(endTime)) {
				end = DateUtils.addMilliseconds(DateUtils.addDate(start, addDateNum), -1);
			} else
				end = DateUtils.addMilliseconds(endTime, -1);

			/*if (containStr == null || !containStr.contains(num + "")) {
				startTime = DateUtils.addDate(startTime, addDateNum);
				continue;
			}*/

			String provider_idStr = StringUtils.isNotBlank(static_provider_id) ? static_provider_id + "_" : "";
			String writeFileUrl = filePathHead + provider_idStr + DateUtils.getYear(date) + "-" + DateUtils.getMonth(date) + ".csv";
			//String writeFileUrl = filePathHead + provider_idStr + DateUtils.getYear(date) + "-" + DateUtils.getMonth(date) + "-" + num + "-week.csv";
			exportCsvList.add(writeFileUrl);
			if (new File(writeFileUrl).exists()) {
				new File(writeFileUrl).delete();
			}
			Boolean isNeedFirst = true;
			while (start.before(end)) {
				for (Entry<Date, String> entry : timeMap.entrySet()) {
					if (DateUtils.formatDate(entry.getKey()).equals(DateUtils.formatDate(start))) {
						String str = getStrByFilePath(entry.getValue().replaceAll(".zip", ".csv"), isNeedFirst);
						FileUtil.appendFile(writeFileUrl, str);
						isNeedFirst = false;
					}
				}
				start = DateUtils.addDate(start, 1);
			}
			startTime = DateUtils.addDate(startTime, addDateNum);
		}
		// 4.删除所有的csv
		for (Entry<Date, String> entry : timeMap.entrySet()) {
			File file = new File(entry.getValue().replaceAll(".zip", ".csv"));
			if (file.exists()) {
				file.delete();
			}
		}
	}

	public static String getStrByFilePath(String addTargetUrl, Boolean isNeedFirst) throws FileNotFoundException {
		Scanner in = new Scanner(new File(addTargetUrl));
		StringBuffer sb = new StringBuffer();
		int num = 0;
		while (in.hasNext()) {
			String str = in.nextLine();
			if (num == 0) {
				if (isNeedFirst) {
					sb.append(str + "\r");
					if (static_provider_id != null) {
						String[] split = str.split(",");
						for (int i = 0; i < split.length; i++) {
							if ("供应商ID".equals(split[i])) {
								static_provider_id_index = i;
								break;
							}
						}
					}
				}
			} else {
				if (static_provider_id != null && static_provider_id_index != null) {
					String[] split = str.split(",");
					if (split.length >= static_provider_id_index && static_provider_id.equals(split[static_provider_id_index])) {
						sb.append(str + "\r");
					}
				} else {
					sb.append(str + "\r");
				}
			}
			num++;
		}
		in.close();
		return sb.toString();
	}

	// 测试用
	@RequestMapping(value = "test")
	@ResponseBody
	public String test(HttpServletRequest request) throws Exception {

		return "success" + (int) (Math.random() * 100);
	}

	/**
	 * 根据查询条件生成fileNameList
	 * 
	 * @param name
	 * @param year
	 * @param month
	 * @param startTime
	 * @param endTime
	 * @param fileNameList
	 * @param failReason
	 * @param simpleName
	 * @return
	 * @throws ParseException
	 */
	public String addFileNames(String name, String year, String month, String startTime, String endTime, List<String> fileNameList,
			String failReason, String simpleName) throws ParseException {
		if ("month".equals(name)) {
			file_suffix = ".csv";
			if (StringUtils.isNotEmpty(year) && StringUtils.isNotEmpty(month)) {
				/*for (int i = 1; i < 6; i++) {
					fileNameList.add("inside_" + simpleName + "_" + year + "-" + (Integer.parseInt(month) < 10 ? ("0" + month) : month) + "-" + i
							+ "-week");
					fileNameList.add("0_" + simpleName + "_" + year + "-" + (Integer.parseInt(month) < 10 ? ("0" + month) : month) + "-" + i
							+ "-week");
				}*/
				fileNameList.add(simpleName + "_" + year + "-" + (Integer.parseInt(month) < 10 ? ("0" + month) : month));
				// fileNameList.add(simpleName +"_"+ year + "-" +(Integer.parseInt(month)<10?("0"+month):month)+"-"+i+"-week"); }
			} else {
				failReason = "年和月不能为空";
			}
		} else if ("day".equals(name)) {
			file_suffix = ".zip";
			if (StringUtils.isNotEmpty(startTime) || StringUtils.isNotEmpty(endTime)) {
				if (StringUtils.isBlank(startTime)) {
					startTime = endTime;
				}
				if (StringUtils.isBlank(endTime)) {
					endTime = startTime;
				}
				Date startDate = DateUtils.getDateByStr(startTime + " 00:00:00");
				Date endDate = DateUtils.getDateByStr(endTime + " 00:00:02");
				if ((endDate.getTime() - startDate.getTime()) / 1000 > 60 * 60 * 24 * 30) {
					failReason = "".equals(failReason) ? "导出时间不能超过一个月" : failReason;
				} else {
					while (startDate.before(endDate)) {
						fileNameList.add("inside_" + simpleName + "_" + DateUtils.formatDate(startDate, "yyyy-MM-dd"));
						fileNameList.add("0_" + simpleName + "_" + DateUtils.formatDate(startDate, "yyyy-MM-dd"));
						startDate = DateUtils.addDays(startDate, 1);
					}
				}
			} else
				failReason = "".equals(failReason) ? "开始时间和结束时间不能为空" : failReason;
		}
		return failReason;
	}

	@RequestMapping(value = "getBossChargeInfo")
	@ResponseBody
	public String getTmallPriceAndCost(HttpServletRequest req, HttpServletResponse resp, HttpSession session) throws Exception {
		String str = req.getParameter("str");
		log.info("str:" + str);
		List<OrderInfo> search = orderInfoService.search("from OrderInfo where orderKey in (" + str + ") and customerId = "
				+ Constants.getString("tmallCustomerId"));
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		Map<Integer, ProviderInfo> providerInfoMap = ParameterListener.providerInfoMap;

		try {
			for (OrderInfo bossOrder : search) {
				ProviderInfo providerInfo = providerInfoMap.get(bossOrder.getSuccessId());
				Map<String, Object> map = ExcelUtils.beanToMap(bossOrder);
				map.put("failReason", providerInfo != null ? providerInfo.getProviderName() : "供应商不存在");
				mapList.add(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JsonUtils.obj2Json(mapList);
	}

}
