package xinxing.boss.admin.common.schedule.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xinxing.boss.admin.boss.customer.domain.CustomerInfo;
import xinxing.boss.admin.boss.customer.domain.CustomerProductInfo;
import xinxing.boss.admin.boss.customer.service.CustomerInfoService;
import xinxing.boss.admin.boss.customer.service.CustomerProductInfoService;
import xinxing.boss.admin.boss.order.domain.OrderInfo;
import xinxing.boss.admin.boss.order.service.OrderInfoService;
import xinxing.boss.admin.boss.other.domain.BossBackTmallCache;
import xinxing.boss.admin.boss.other.domain.BossGdDataAnalyze;
import xinxing.boss.admin.boss.other.domain.BossScheduleChange;
import xinxing.boss.admin.boss.other.domain.BossTmallCache;
import xinxing.boss.admin.boss.other.domain.DiscountChange;
import xinxing.boss.admin.boss.other.service.BossBackTmallCacheService;
import xinxing.boss.admin.boss.other.service.BossGdDataAnalyzeService;
import xinxing.boss.admin.boss.other.service.BossScheduleChangeService;
import xinxing.boss.admin.boss.other.service.BossTmallCacheService;
import xinxing.boss.admin.boss.other.service.DiscountChangeService;
import xinxing.boss.admin.boss.provider.domain.ProductCategoryInfo;
import xinxing.boss.admin.boss.provider.domain.ProviderInfo;
import xinxing.boss.admin.boss.provider.domain.ProviderProductInfo;
import xinxing.boss.admin.boss.provider.service.ProviderInfoService;
import xinxing.boss.admin.boss.provider.service.ProviderProductInfoService;
import xinxing.boss.admin.common.schedule.TotalScheduleService;
import xinxing.boss.admin.common.utils.base.BaseUtil;
import xinxing.boss.admin.common.utils.constants.Constants;
import xinxing.boss.admin.common.utils.json.JsonUtils;
import xinxing.boss.admin.common.utils.parameter.ParameterListener;
import xinxing.boss.admin.common.utils.time.DateUtils;

@Service
public class TotalScheduleServiceImpl implements TotalScheduleService {
	private static Logger changeByTimeLog = LoggerFactory.getLogger("changeByTimeLog");
	private static Logger cygdcdLog = LoggerFactory.getLogger("昨日广东城市统计");
	private static Logger ctcLog = LoggerFactory.getLogger("天猫缓存 10分钟一次");
	@Autowired
	private BossScheduleChangeService bossScheduleChangeService;
	@Autowired
	private CustomerInfoService customerInfoService;
	@Autowired
	private ProviderInfoService providerInfoService;
	@Autowired
	private CustomerProductInfoService customerProductInfoService;
	@Autowired
	private ProviderProductInfoService providerProductInfoService;
	@Autowired
	private DiscountChangeService discountChangeService;
	@Autowired
	private OrderInfoService orderInfoService;
	@Autowired
	BossGdDataAnalyzeService bossGdDataAnalyzeService;
	@Autowired
	BossTmallCacheService bossTmallCacheService;
	@Autowired
	BossBackTmallCacheService bossBackTmallCacheService;

	@Override
	public void changeByTime_Job() throws Exception {
		changeByTime_Job(bossScheduleChangeService, customerInfoService, providerInfoService, customerProductInfoService, providerProductInfoService,
				discountChangeService);
	}

	public static void changeByTime_Job(BossScheduleChangeService bossScheduleChangeService, CustomerInfoService customerInfoService,
			ProviderInfoService providerInfoService, CustomerProductInfoService customerProductInfoService,
			ProviderProductInfoService providerProductInfoService, DiscountChangeService discountChangeService) throws Exception {
		changeByTimeLog.info("定时任务启动:" + DateUtils.formatDateTime(new Date()));
		List<BossScheduleChange> search = bossScheduleChangeService
				.search("from BossScheduleChange where status=1 and changeTime>=now() and timestampdiff(SECOND,now(),changeTime)<=60");
		// .search("from BossScheduleChange where status=1 and timestampdiff(SECOND,now(),changeTime)<=1");
		for (BossScheduleChange bossScheduleChange : search) {
			changeByTimeLog.info("id:" + bossScheduleChange.getId());
		}
		changeByTimeLog.info("----------------------------------------------------------");
		// if (true)
		// return;
		Map<String, String> refreshMap = new HashMap<>();
		for (BossScheduleChange bossScheduleChange : search) {
			Integer tableId = bossScheduleChange.getTableId();
			Integer tableType = bossScheduleChange.getTableType();
			Integer type = bossScheduleChange.getType();
			if (tableId == null || tableType == null || type == null) {
				changeByTimeLog.error("存在值为空:" + JsonUtils.obj2Json(bossScheduleChange));
				continue;
			}
			if (type == 0 || type == 1) {// 冻结/开通
				switch (tableType) {
				case 1:
					CustomerInfo customerInfo = ParameterListener.customerInfoMap.get(tableId);
					System.out.println(JsonUtils.obj2Json(customerInfo));
					if (customerInfo != null) {
						customerInfo.setStatus(type);
						customerInfoService.update(customerInfo);
						refreshMap.put("customer", null);
					}
					break;
				case 2:
					ProviderInfo providerInfo = ParameterListener.providerInfoMap.get(tableId);
					if (providerInfo != null) {
						providerInfo.setStatus(type);
						providerInfoService.update(providerInfo);
						refreshMap.put("provider", "init_provider");
					}
					break;
				case 3:
					CustomerProductInfo customerProductInfo = ParameterListener.customerProductInfoMap.get(tableId);
					if (customerProductInfo != null) {
						customerProductInfo.setStatus(type);
						customerProductInfoService.update(customerProductInfo);
						refreshMap.put("customerProductInfo", null);
					}
					break;
				case 4: // 状态 1正常/2冻结
					ProviderProductInfo providerProductInfo = ParameterListener.providerProductMap.get(tableId);
					Integer realType = (type == 0 ? 2 : type);
					if (providerProductInfo != null) {
						providerProductInfo.setStatus(realType);
						providerProductInfoService.update(providerProductInfo);
						refreshMap.put("providerProduct", "init_provider");
					}
					break;
				default:
					break;
				}
			} else {// 修改折扣
				BigDecimal changeNum = bossScheduleChange.getChangeNum();
				switch (tableType) {
				case 3:
					CustomerProductInfo cpi = ParameterListener.customerProductInfoMap.get(tableId);
					ProductCategoryInfo productCategoryInfo = ParameterListener.productCategoryInfoMap.get(cpi.getCategoryId());
					if (cpi != null) {

						try {
							CustomerInfo pi = ParameterListener.customerInfoMap.get(cpi.getCustomerId());
							BigDecimal beforeDiscount = cpi.getSellPrice().divide(new BigDecimal(productCategoryInfo.getStandarPrice()),5);
							DiscountChange disc = new DiscountChange(pi.getCustomerName(), "定时修改折扣", new Date(), bossScheduleChange.getAddUser(),
									cpi.getCategoryId(), beforeDiscount, changeNum, bossScheduleChange.getAddUser() + "", 0);
							discountChangeService.save(disc);
						} catch (Exception e) {
							changeByTimeLog.error(e.getMessage(), e);
						}

						cpi.setSellPrice(new BigDecimal(productCategoryInfo.getStandarPrice()).multiply(changeNum));
						customerProductInfoService.update(cpi);
						refreshMap.put("customerProductInfo", null);

					}
					break;
				case 4:
					//changeByTimeLog.info("进入修改折扣,tableType="+tableType);
					ProviderProductInfo ppi = ParameterListener.providerProductMap.get(tableId);
					ProductCategoryInfo pci = ParameterListener.productCategoryInfoMap.get(ppi.getCategoryId());
					ProviderInfo pi = ParameterListener.providerInfoMap.get(ppi.getProviderId());
					if (ppi != null) {
						try {
							BigDecimal beforeDiscount = new BigDecimal(ppi.getCostPrice()).divide(new BigDecimal(pci.getStandarPrice()),5);
							changeByTimeLog.info("beforeDiscount="+beforeDiscount);
							DiscountChange disc = new DiscountChange(pi.getProviderName(), "定时修改折扣", new Date(), bossScheduleChange.getAddUser(),
									ppi.getCategoryId(), beforeDiscount, changeNum, bossScheduleChange.getAddUser() + "", 1);
							discountChangeService.save(disc);
						} catch (Exception e) {
							changeByTimeLog.info(e.getMessage(), e);
						}
						ppi.setCostPrice((new BigDecimal(pci.getStandarPrice()).multiply(changeNum)).doubleValue());
						providerProductInfoService.update(ppi);
						refreshMap.put("providerProduct", "init_provider");
					}
					break;
				default:
					break;
				}
			}
			bossScheduleChange.setStatus(2);// 已生效
			bossScheduleChangeService.update(bossScheduleChange);
		}
		for (Entry<String, String> entry : refreshMap.entrySet()) {
			ParameterListener.refresh(entry.getKey());
			if (entry.getValue() != null) {
				ParameterListener.flushStatus(entry.getValue());
			}
		}
		changeByTimeLog.info("定时任务结束:" + DateUtils.formatDateTime(new Date()));
	}

	@Override
	public void createYesterdayGuangDongCityData_Job() throws Exception {
		cygdcdLog.info("定时任务开始");
		Date date = DateUtils.addDate(new Date(), -1);
		createYesterdayGdData(date, orderInfoService, bossGdDataAnalyzeService);
		cygdcdLog.info("定时任务结束");
	}

	public static void createYesterdayGdData(Date date, OrderInfoService orderInfoService, BossGdDataAnalyzeService bossGdDataAnalyzeService) {
		String startTime = DateUtils.getDate(date, "yyyy-MM-dd") + " 00:00:00";
		String endTime = DateUtils.getDate(date, "yyyy-MM-dd") + " 23:59:59";

		bossGdDataAnalyzeService.saveOrUpdate("delete from boss_gd_data_analyze where addTime >= '" + startTime + "' and addTime <= '" + endTime
				+ "'");
		String guangdongSql = " select city,cost,status,categoryId from boss_order where province='广东' and successTime>='" + startTime
				+ "' and successTime <='" + endTime + "'";
		cygdcdLog.info("guangdongSql:" + guangdongSql);
		List<Map<String, Object>> query = bossGdDataAnalyzeService.query(guangdongSql);
		Map<String, BossGdDataAnalyze> objMap = new HashMap<>();
		BigDecimal sumTotalCost = new BigDecimal(0);
		int sumTotalNum = 0;
		BigDecimal sumSuccessCost = new BigDecimal(0);
		int sumSuccessNum = 0;

		Map<Integer, ProductCategoryInfo> productCategoryInfoMap = ParameterListener.productCategoryInfoMap;

		for (Map<String, Object> map : query) {
			Integer categoryId = (Integer) map.get("categoryId");
			Integer status = (Integer) map.get("status");
			String city = (String) map.get("city");
			if (StringUtils.isNotBlank(city) && city.substring(city.length() - 1, city.length()).contains("市")) {
				city = city.replaceAll("市", "");
			}
			BigDecimal cost = new BigDecimal(map.get("cost").toString());
			sumTotalNum++;
			sumTotalCost = sumTotalCost.add(cost);
			ProductCategoryInfo pci = productCategoryInfoMap.get(categoryId);
			if (categoryId == null || StringUtils.isBlank(city) || pci == null) {
				continue;
			}
			if (status == OrderInfo.Status.SUCCESS.status) {
				sumSuccessNum++;
				sumSuccessCost = sumSuccessCost.add(cost);
			}
			Integer area = pci.getArea(); // 0本省1全国
			String key = city + "_" + area;
			BossGdDataAnalyze obj = objMap.get(key);
			if (obj == null) {
				obj = new BossGdDataAnalyze();
				obj.setArea(area);
				obj.setCity(city);
				objMap.put(key, obj);
			}
			obj.add(status, cost);
		}

		for (Entry<String, BossGdDataAnalyze> entry : objMap.entrySet()) {
			BossGdDataAnalyze obj = entry.getValue();
			obj.createDayData(sumTotalNum, sumTotalCost, sumSuccessNum, sumSuccessCost);
			obj.setAddTime(date);
			bossGdDataAnalyzeService.save(obj);
		}
	}

	@Override
	public void createTmallCache_Job() throws Exception {
		ctcLog.info("start");
		Date successTime = DateUtils.addMinutes(new Date(), -11);// 10分钟执行一次
		// Date successTime = DateUtils.addMonths(new Date(), 5);// 10分钟执行一次
		List<OrderInfo> search = orderInfoService.search("from OrderInfo where (successTime >=?0 or failTime >=?0) and customerId=?1 order by id asc", successTime,
				Constants.getInteger("tmallCustomerId"));
		List<BossTmallCache> btcList = bossTmallCacheService.getAll();
		Map<String, BossTmallCache> orderId_btcMap = new HashMap<>();
		Map<String, BossTmallCache> pid_catIdMap = new HashMap<>();
		for (BossTmallCache bossTmallCache : btcList) {
			String orderIds = bossTmallCache.getOrderIds();
			if (StringUtils.isNotBlank(orderIds)) {
				String[] split = orderIds.split(",");
				for (String str : split) {
					orderId_btcMap.put(str, bossTmallCache);
				}
			}
			pid_catIdMap.put(bossTmallCache.getProviderId() + "_" + bossTmallCache.getCategoryId(), bossTmallCache);
		}
		Map<String, Map<String, Object>> oldMap = (Map<String, Map<String, Object>>) JsonUtils.json2Obj(JsonUtils.obj2Json(pid_catIdMap), Map.class);
		Date now = new Date();
		Set<String>  pid_catSet = new HashSet<>();
		if (search.size() > 0) {
			for (OrderInfo orderInfo : search) {
				
				Boolean isKadan = isKadan(orderInfo);
				String orderId = orderInfo.getId().toString();
				Integer categoryId = orderInfo.getProviderCategoryId();
				Integer providerId = orderInfo.getSuccessId();
				String appendOrderId = orderId + ",";
				if (providerId == null || providerId == 0 || categoryId == null || categoryId == 0) {
					continue;
				}
				BossTmallCache bossTmallCache = orderId_btcMap.get(orderId);
				Boolean hasOrderInfo = bossTmallCache != null;// 订单id是否记录
				ctcLog.info(orderInfo.getId() + "_status:" + orderInfo.getStatus() + "_修改前:" + JsonUtils.obj2Json(bossTmallCache));
				String key = providerId + "_" + categoryId;
				pid_catSet.add(key);
				if (hasOrderInfo) {
					Boolean isResend = !(categoryId.toString().equals(bossTmallCache.getCategoryId() + "") && providerId.toString().equals(
							bossTmallCache.getProviderId() + ""));// 是否是重发的订单
					if (isResend) {
						String orderIds = bossTmallCache.getOrderIds();
						bossTmallCache.setOrderIds(orderIds.replaceAll(appendOrderId, ""));
						bossTmallCache = addBtc(orderId_btcMap, pid_catIdMap, now, orderId, categoryId, providerId, appendOrderId, key);
					}
				} else {
					bossTmallCache = addBtc(orderId_btcMap, pid_catIdMap, now, orderId, categoryId, providerId, appendOrderId, key);
				}
				if (!isKadan) {
					bossTmallCache.setIsCache(0);
					bossTmallCache.setBeginTime(now);
					String[] split = bossTmallCache.getOrderIds().split(",");
					for (String oid : split) {
						if (StringUtils.isNotBlank(oid)) {
							orderId_btcMap.remove(oid);
						}
					}
					bossTmallCache.setOrderIds("");
				}
				ctcLog.info(orderInfo.getId() + "_修改后:" + orderInfo.getStatus() + "_" + JsonUtils.obj2Json(bossTmallCache));
			}
		}
		// 修改过值的
		List<BossTmallCache> saveList = new ArrayList<>();
		List<BossTmallCache> updateList = new ArrayList<>();
		for (Entry<String, BossTmallCache> entry : pid_catIdMap.entrySet()) {
			String key = entry.getKey();
			BossTmallCache _new = entry.getValue();
			if (DateUtils.addMinutes(_new.getBeginTime(), 30).before(now) && StringUtils.isNotBlank(_new.getOrderIds())) {
				// if (DateUtils.addHours(_new.getBeginTime(), 2).before(now) && StringUtils.isNotBlank(_new.getOrderIds())) {
				_new.setIsCache(1);
			}
			Map<String, Object> old = oldMap.get(key);
			if (old != null) {
				if (!_new.valueEquals(old)) {
					updateList.add(_new);
				}
			} else {
				saveList.add(_new);
			}
		}
		ctcLog.info("search:" + JsonUtils.obj2Json(search));
		ctcLog.info("pid_catIdMap:" + JsonUtils.obj2Json(pid_catIdMap));
		ctcLog.info("oldMap:" + JsonUtils.obj2Json(oldMap));
		ctcLog.info("saveList:" + JsonUtils.obj2Json(saveList));
		ctcLog.info("updateList:" + JsonUtils.obj2Json(updateList));

		List<BossBackTmallCache> backSaveList = new ArrayList<>();

		for (BossTmallCache bossTmallCache : saveList) {
			BossBackTmallCache bbtc = JsonUtils.json2Obj(JsonUtils.obj2Json(bossTmallCache), BossBackTmallCache.class);
			bbtc.setAddTime(now);
			bbtc.setId(null);
			backSaveList.add(bbtc);
		}
		for (BossTmallCache bossTmallCache : updateList) {
			BossBackTmallCache bbtc = JsonUtils.json2Obj(JsonUtils.obj2Json(bossTmallCache), BossBackTmallCache.class);
			bbtc.setAddTime(now);
			bbtc.setId(null);
			backSaveList.add(bbtc);
		}
		bossTmallCacheService.saveBatch(saveList);
		bossTmallCacheService.updateBatch(updateList);
		bossBackTmallCacheService.saveBatch(backSaveList);
		ctcLog.info("end");
	}

	public BossTmallCache addBtc(Map<String, BossTmallCache> btcMap, Map<String, BossTmallCache> pid_catIdMap, Date now, String orderId,
			Integer categoryId, Integer providerId, String appendOrderId, String key) {
		if (pid_catIdMap.get(key) != null) {
			BossTmallCache realCache = pid_catIdMap.get(key);
			if (StringUtils.isBlank(realCache.getOrderIds())) {// 订单都没有 说明相当于新添加
				realCache.setBeginTime(now);
				realCache.setIsCache(0);
			}
			realCache.setOrderIds(realCache.getOrderIds() + appendOrderId);
			return realCache;
		} else {
			BossTmallCache btc = new BossTmallCache(providerId, categoryId, appendOrderId, now, 0);
			pid_catIdMap.put(key, btc);
			btcMap.put(orderId, btc);
			return btc;
		}
	}

	private Boolean isKadan(OrderInfo orderInfo) {
		Boolean isKadan = true;
		Integer status = orderInfo.getStatus();
		if (status == OrderInfo.Status.SUCCESS.status || status == OrderInfo.Status.FAIL.status//
				|| (status == OrderInfo.Status.HANDLE.status && orderInfo.getHandleStatus() == OrderInfo.HANDLE_FAIL)//
				|| (status == OrderInfo.Status.HANDLE.status && orderInfo.getSuccessId() == 0)) {
			isKadan = false;
		}
		return isKadan;
	}

}
