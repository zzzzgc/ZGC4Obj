package xinxing.boss.admin.common.schedule.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import xinxing.boss.admin.boss.customer.domain.CustomerDataAnalyze;
import xinxing.boss.admin.boss.customer.service.CustomerDataAnalyzeService;
import xinxing.boss.admin.boss.order.cmd.OrderInfoCmd;
import xinxing.boss.admin.boss.order.domain.OrderInfo;
import xinxing.boss.admin.boss.order.service.OrderInfoService;
import xinxing.boss.admin.boss.provider.domain.ProviderDataAnalyze;
import xinxing.boss.admin.boss.provider.service.ProviderDataAnalyzeService;
import xinxing.boss.admin.common.persistence.Page;
import xinxing.boss.admin.common.persistence.PropertyFilter;
import xinxing.boss.admin.common.schedule.DataAnalyzeProcessorScheduleService;
import xinxing.boss.admin.common.utils.parameter.ParameterListener;
import xinxing.boss.admin.common.utils.time.DateUtils;

public class DataAnalyzeProcessorScheduleServiceImpl implements DataAnalyzeProcessorScheduleService {
	private static Logger logger = LoggerFactory
			.getLogger(DataAnalyzeProcessorScheduleServiceImpl.class);
	@Autowired
	private ProviderDataAnalyzeService providerDataAnalyzeService;
	@Autowired
	private CustomerDataAnalyzeService customerDataAnalyzeService;
	@Autowired
	private OrderInfoService orderInfoService;

	@Override
	public void doScheduleJob() throws Exception {
		getProviderDataAnalyze();
		getCustomerDataAnalyze();

	}

	public void getProviderDataAnalyze() {
		List<String> sqlList = new ArrayList<>();
		Date date1 = new Date();
		date1 = DateUtils.addDate(date1, -2);
		date1 = DateUtils.getDateStart(date1);
		Date date2 = new Date();
		date2 = DateUtils.addDate(date2, -1);
		date2 = DateUtils.getDateStart(date2);
		List<Date> list = new ArrayList<>();
		list.add(date1);
		list.add(date2);
		for (Date date : list) {
			List<ProviderDataAnalyze> search = providerDataAnalyzeService.search(
					"from ProviderDataAnalyze obj where obj.time = ?0", date);
			if (search.size() != 0) {
				continue;
			}
			String GED_receiveTime = DateUtils.formatDate(date, "yyyy-MM-dd HH:mm:ss");
			date = DateUtils.getDateEnd(date);
			String LED_receiveTime = DateUtils.formatDate(date, "yyyy-MM-dd HH:mm:ss");
			Page<OrderInfo> page = new Page<>();
			page.setPageSize(100000000);
			List<PropertyFilter> filters = new ArrayList<>();
			filters.add(new PropertyFilter("GED_receiveTime", GED_receiveTime));
			filters.add(new PropertyFilter("LED_receiveTime", LED_receiveTime));
			page = orderInfoService.search(page, filters);
			List<OrderInfo> result = page.getResult();
			if (result.size() == 0)
				continue;
			Map<Integer, ProviderDataAnalyze> pdaMap = new HashMap<>();
			for (OrderInfo orderInfo : result) {
				Double cost = 0d;
				Integer providerId = orderInfo.getSuccessId();
				ProviderDataAnalyze cda = null;
				if (pdaMap.get(providerId) == null) {
					cda = new ProviderDataAnalyze();
					cda.setProviderId(providerId);
				} else {
					cda = pdaMap.get(providerId);
				}
				if (orderInfo.getCost() != null) {
					cost = orderInfo.getCost().doubleValue();
					cda.setTotalMoney(cda.getTotalMoney() + orderInfo.getPrice().doubleValue());
					// 状态为成功
					if (orderInfo.getStatus() == 3) {
						cda.setSuccessNum(cda.getSuccessNum() + 1);
						cda.setSuccessMoney(cda.getSuccessMoney() + cost);
					}
				}
				String operation = orderInfo.getBeyoudOperation();
				if ("联通".equals(operation))
					cda.setLiantongNum(cda.getLiantongNum() + 1);
				else if ("移动".equals(operation))
					cda.setYidongNum(cda.getYidongNum() + 1);
				else if ("电信".equals(operation))
					cda.setDianxinNum(cda.getDianxinNum() + 1);
				cda.setTotalNum(cda.getTotalNum() + 1);
				if (orderInfo.getStatus() == 3) {
					Long useTime = (orderInfo.getSuccessTime().getTime() - orderInfo
							.getReceiveTime().getTime()) / 1000;
					cda.setUseTime(cda.getUseTime() + useTime);
				} else if (orderInfo.getStatus() == 4) {
					Long useTime = (orderInfo.getFailTime().getTime() - orderInfo.getReceiveTime()
							.getTime()) / 1000;
					cda.setUseTime(cda.getUseTime() + useTime);
				}
				pdaMap.put(providerId, cda);
			}
			List<ProviderDataAnalyze> cdaList = new ArrayList<>();
			for (Map.Entry<Integer, ProviderDataAnalyze> entry : pdaMap.entrySet()) {
				if (entry.getValue() != null && entry.getValue().getTotalMoney() != 0)
					cdaList.add(entry.getValue().format2decimalPoint());
			}

			StringBuffer sql = new StringBuffer(300000);
			sql.append("INSERT boss_provider_data_analyze(providerId,totalMoney,successMoney,dianxinNum,liantongNum,yidongNum,totalNum,successNum,useTime,time) value");
			if (cdaList.size() > 0) {
				for (ProviderDataAnalyze pda : cdaList) {
					sql.append("(").append(pda.getProviderId()).append(",")
							.append(pda.getTotalMoney()).append(",").append(pda.getSuccessMoney())
							.append(",").append(pda.getDianxinNum()).append(",")
							.append(pda.getLiantongNum()).append(",").append(pda.getYidongNum())
							.append(",").append(pda.getTotalNum()).append(",")
							.append(pda.getSuccessNum()).append(",").append(pda.getUseTime())
							.append(",'").append(GED_receiveTime).append("'),");
				}

				String sql1 = sql.substring(0, sql.length() - 1);
				sqlList.add(sql1);
			}
		}
		providerDataAnalyzeService.save(sqlList);
	}

	public void getCustomerDataAnalyze() {
		Date date1 = new Date();
		date1 = DateUtils.addDate(date1, -2);
		date1 = DateUtils.getDateStart(date1);
		Date date2 = new Date();
		date2 = DateUtils.setDays(date2, -1);
		date2 = DateUtils.getDateStart(date2);
		List<Date> list = new ArrayList<>();
		list.add(date1);
		list.add(date2);
		for (Date date : list) {
			List<CustomerDataAnalyze> search = customerDataAnalyzeService.search(
					"from CustomerDataAnalyze obj where obj.time = ?0", date);
			if (search.size() != 0) {
				continue;
			}
			String GED_receiveTime = DateUtils.formatDate(date, "yyyy-MM-dd HH:mm:ss");
			date = DateUtils.getDateEnd(date);
			String LED_receiveTime = DateUtils.formatDate(date, "yyyy-MM-dd HH:mm:ss");
			Page<OrderInfo> page = new Page<>();
			page.setPageSize(100000000);
			List<PropertyFilter> filters = new ArrayList<>();
			filters.add(new PropertyFilter("GED_receiveTime", GED_receiveTime));
			filters.add(new PropertyFilter("LED_receiveTime", LED_receiveTime));
			page = orderInfoService.search(page, filters);
			List<OrderInfo> result = page.getResult();
			if (result.size() == 0)
				continue;
			Map<Integer, CustomerDataAnalyze> cdaMap = new HashMap<>();
			for (OrderInfo orderInfo : result) {
				Integer customerId = orderInfo.getCustomerId();
				CustomerDataAnalyze cda = null;
				if (cdaMap.get(customerId) == null) {
					cda = new CustomerDataAnalyze();
					cda.setCustomerId(customerId);
				} else {
					cda = cdaMap.get(customerId);
				}
				cda.setTotalMoney(cda.getTotalMoney() + orderInfo.getPrice().doubleValue());
				// 状态为成功
				if (orderInfo.getStatus() == 3) {
					cda.setSuccessCost(cda.getSuccessCost() + orderInfo.getCost().doubleValue());
					cda.setSuccessMoney(cda.getSuccessMoney() + orderInfo.getPrice().doubleValue());
					cda.setSuccessNum(cda.getSuccessNum() + 1);
				}
				String operation = orderInfo.getBeyoudOperation();
				if ("联通".equals(operation))
					cda.setLiantongNum(cda.getLiantongNum() + 1);
				else if ("移动".equals(operation))
					cda.setYidongNum(cda.getYidongNum() + 1);
				else if ("电信".equals(operation))
					cda.setDianxinNum(cda.getDianxinNum() + 1);
				cda.setTotalNum(cda.getTotalNum() + 1);
				if (orderInfo.getStatus() == 3) {
					Long useTime = (orderInfo.getSuccessTime().getTime() - orderInfo
							.getReceiveTime().getTime()) / 1000;
					cda.setUseTime(cda.getUseTime() + useTime);
				} else if (orderInfo.getStatus() == 4) {
					Long useTime = (orderInfo.getFailTime().getTime() - orderInfo.getReceiveTime()
							.getTime()) / 1000;
					cda.setUseTime(cda.getUseTime() + useTime);
				}
				cdaMap.put(customerId, cda);
			}
			List<CustomerDataAnalyze> cdaList = new ArrayList<>();
			for (Map.Entry<Integer, CustomerDataAnalyze> entry : cdaMap.entrySet()) {
				cdaList.add(entry.getValue());
			}

			StringBuffer sql = new StringBuffer(300000);
			sql.append("INSERT boss_customer_data_analyze(customerId,totalMoney,successMoney,successCost,earn,dianxinNum,liantongNum,yidongNum,totalNum,successNum,useTime,time) value");
			if (cdaList.size() > 0) {
				for (CustomerDataAnalyze pda : cdaList) {
					sql.append("(").append(pda.getCustomerId()).append(",")
							.append(pda.getTotalMoney()).append(",").append(pda.getSuccessMoney())
							.append(",").append(pda.getSuccessCost()).append(",")
							.append(pda.getEarn()).append(",").append(pda.getDianxinNum())
							.append(",").append(pda.getLiantongNum()).append(",")
							.append(pda.getYidongNum()).append(",").append(pda.getTotalNum())
							.append(",").append(pda.getSuccessNum()).append(",")
							.append(pda.getUseTime()).append(",'").append(GED_receiveTime)
							.append("'),");
				}

				String sql1 = sql.substring(0, sql.length() - 1);
				customerDataAnalyzeService.save(sql1);
				logger.info("将订单当天数据保存" + GED_receiveTime + "," + LED_receiveTime
						+ "------------------------" + result.size());
			}
		}

	}

}
