package xinxing.boss.admin.boss.order.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import xinxing.boss.admin.boss.order.cmd.OrderInfoCmd;
import xinxing.boss.admin.boss.order.service.OrderInfoService;
import xinxing.boss.admin.common.persistence.PropertyFilter;
import xinxing.boss.admin.common.persistence.PropertyFilter.MatchType;
import xinxing.boss.admin.common.utils.base.BaseUtil;
import xinxing.boss.admin.common.utils.time.DateUtils;

public class OrderUtils {
	private static Logger log = LoggerFactory.getLogger(OrderInfoCmd.class);

	/**
	 * 将需要的统计信息存成map,没有统计新增订单
	 * 
	 * @param result
	 * @param map
	 */
	public static Map getCountMap(OrderInfoService orderInfoService, List<PropertyFilter> filters, String tableName) throws Exception {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		String ORDER_STATUS = "status";
		if ("boss_order_resend_record".equals(tableName)) {
			ORDER_STATUS = "finalStatus";
		}
		BaseUtil.testSearch(filters, "receiveTime");
		StringBuffer sql = new StringBuffer(200);
		sql.append("select sum((UNIX_TIMESTAMP(successTime) - UNIX_TIMESTAMP(receiveTime)))/count(" + ORDER_STATUS
				+ ") as successTime,sum((UNIX_TIMESTAMP(failTime) - UNIX_TIMESTAMP(receiveTime)))/count(" + ORDER_STATUS + ") as failTime,count("
				+ ORDER_STATUS + ") as stateNum," + ORDER_STATUS + " as state,sum(cost) as cost,sum(price) as price from " + tableName);
		// sql.append("select sum(dealTime) as useTime,count(chargeState) as stateNum,chargeState as state,sum(sprice) as cost,sum(unitPrice) as price,sum(divideMoney) as divideMoney  from tmall_order");

		StringBuffer whereSql = new StringBuffer(100);
		for (int i = 0, len = filters.size(); i < len; i++) {
			Class<?> cls = filters.get(i).getPropertyClass();
			MatchType matchType = filters.get(i).getMatchType();
			String bijiao = "";
			List<Object> matchValueList = filters.get(i).getMatchValue();
			if (matchValueList.size() > 0) {
				String matchValue = matchValueList.get(0).toString();
				Class<? extends Object> cls1 = matchValueList.get(0).getClass();
				if (cls1.equals(String.class) || cls1.equals(Date.class)) {
					if (cls1.equals(Date.class)) {
						log.info(new Date(matchValue).toString());
						SimpleDateFormat sdf1 = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK);
						matchValue = DateUtils.formatDate(sdf1.parse(matchValue), "yyyy-MM-dd HH:mm:ss");
					}
					matchValue = "'" + matchValue + "'";
					log.info(matchValue);
				}
				switch (matchType) {
				case EQ:
					bijiao = "=" + matchValue;
					break;
				case LIKE:
					bijiao = "like(%" + matchValue + "%)";
					break;
				case LT:
					bijiao = "<" + matchValue;
					break;
				case GT:
					bijiao = ">" + matchValue;
					break;
				case GE:
					bijiao = ">=" + matchValue;
					break;
				case LE:
					bijiao = "<=" + matchValue;
					break;
				case IN:
					String str = "";
					if (matchValueList.size() > 0) {
						for (Object object : matchValueList) {
							if (object.getClass().equals(String.class) || object.getClass().equals(Date.class))
								str += "'" + object.toString() + "',";
							else {
								str += object.toString() + ",";
							}
						}
						str = str.substring(0, str.length() - 1);
					}
					bijiao = " in (" + str + ")";
					break;
				default:
					break;
				}
			}
			if (i == 0) {
				whereSql.append(" where ");
			} else
				whereSql.append(" and ");
			String propertyName = filters.get(i).getPropertyName();
			if ("successId".equals(propertyName)) {
				propertyName = "providerId";
			}
			whereSql.append(propertyName + bijiao);
		}
		sql.append(whereSql.toString()).append(" GROUP BY " + ORDER_STATUS + "");
		LoggerFactory.getLogger("countSql:").info(sql.toString());
		List<Map<String, Object>> query = orderInfoService.query(sql.toString().replaceAll("beyoudOperation", "operator"));
		if (query.size() == 0){
			returnMap.put("failReason","数据不能为空");
			return null;
		}
		Double successTime = 0d;
		Double failTime = 0d;
		Integer totalNum = 0;
		Integer successNum = 0;
		Integer failNum = 0;
		Integer chargeNum = 0;
		Integer waitNum = 0;
		Integer handleNum = 0;
		Integer newNum = 0;
		Double totalMoney = 0d;
		Double successMoney = 0d;
		Double successCost = 0d;
		Double failMoney = 0d;
		Double chargeMoney = 0d;
		Double waitMoney = 0d;
		Double handleMoney = 0d;
		Double newMoney = 0d;
		Double earnMoney = 0d;
		for (Map<String, Object> map : query) {
			Double decimalSuccessTime = map.get("successTime") != null ? Double.valueOf(map.get("successTime").toString()) : 0d;
			Double decimalFailTime = map.get("failTime") != null ? Double.valueOf(map.get("failTime").toString()) : 0d;
			Integer state = (Integer) map.get("state");
			Integer stateNum = (BigInteger) map.get("stateNum") != null ? ((BigInteger) map.get("stateNum")).intValue() : 0;
			BigDecimal decimalCost = (BigDecimal) map.get("cost");
			BigDecimal decimalPrice = (BigDecimal) map.get("price");
			Double cost = decimalCost != null ? decimalCost.doubleValue() : 0;
			Double price = decimalPrice != null ? decimalPrice.doubleValue() : 0;
			state = state != null ? state : 0;
			totalMoney += price;
			totalNum += stateNum;
			switch (state) {
			case 1:
				newNum += stateNum;
				newMoney += price;
				break;
			case 2:
				chargeNum += stateNum;
				chargeMoney += price;
				break;
			case 3:
				successTime += decimalSuccessTime;
				successNum += stateNum;
				successMoney += price;
				successCost += cost;
				break;
			case 4:
				failTime += decimalFailTime;
				failNum += stateNum;
				failMoney += price;
				break;
			case 5:
				waitNum += stateNum;
				waitMoney += price;
				break;
			case 6:
				handleNum += stateNum;
				handleMoney += price;
				break;
			default:
				break;
			}
		}
		earnMoney = successMoney - successCost;
		returnMap.put("chargeMoney", chargeMoney);
		returnMap.put("chargeNum", chargeNum);
		returnMap.put("successTime", successTime);
		returnMap.put("successMoney", successMoney);
		returnMap.put("successNum", successNum);
		returnMap.put("successCost", successCost);
		returnMap.put("failTime", failTime);
		returnMap.put("failMoney", failMoney);
		returnMap.put("failNum", failNum);
		returnMap.put("waitMoney", waitMoney);
		returnMap.put("waitNum", waitNum);
		returnMap.put("handleMoney", handleMoney);
		returnMap.put("handleNum", handleNum);
		returnMap.put("totalNum", totalNum);
		returnMap.put("totalMoney", totalMoney);
		returnMap.put("earnMoney", earnMoney);
		return returnMap;
	}

	/**
	 * 将需要的统计信息存成map,没有统计新增订单
	 * 
	 * @param result
	 * @param map
	 */
	public static Map getFlowCountMap(OrderInfoService orderInfoService, List<PropertyFilter> filters) throws Exception {
//		BaseUtil.testSearch(filters, "receiveTime");
		StringBuffer sql = new StringBuffer(200);
		sql.append("select sum(successTime-receiveTime)/count(status) as successTime,sum(failTime-receiveTime)/sum(status) as failTime,count(status) as stateNum,status as state,"
				+ "sum(cost) as cost,sum((select standarprice from boss_product_category where boss_product_category.id = boss_order.categoryId)) AS price from boss_order");
		for (int i = 0, len = filters.size(); i < len; i++) {
			Class<?> cls = filters.get(i).getPropertyClass();
			MatchType matchType = filters.get(i).getMatchType();
			String bijiao = "";
			List<Object> matchValueList = filters.get(i).getMatchValue();
			if (matchValueList.size() > 0) {
				String matchValue = matchValueList.get(0).toString();
				Class<? extends Object> cls1 = matchValueList.get(0).getClass();
				if (cls1.equals(String.class) || cls1.equals(Date.class)) {
					if (cls1.equals(Date.class)) {
						log.info(new Date(matchValue).toString());
						SimpleDateFormat sdf1 = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK);
						matchValue = DateUtils.formatDate(sdf1.parse(matchValue), "yyyy-MM-dd HH:mm:ss");
					}
					matchValue = "'" + matchValue + "'";
					log.info(matchValue);
				}
				switch (matchType) {
				case EQ:
					bijiao = "=" + matchValue;
					break;
				case LIKE:
					bijiao = "like(%" + matchValue + "%)";
					break;
				case LT:
					bijiao = "<" + matchValue;
					break;
				case GT:
					bijiao = ">" + matchValue;
					break;
				case GE:
					bijiao = ">=" + matchValue;
					break;
				case LE:
					bijiao = "<=" + matchValue;
					break;
				case IN:
					String str = "";
					if (matchValueList.size() > 0) {
						for (Object object : matchValueList) {
							if (object.getClass().equals(String.class) || object.getClass().equals(Date.class))
								str += "'" + object.toString() + "',";
							else {
								str += object.toString() + ",";
							}
						}
						str = str.substring(0, str.length() - 1);
					}
					bijiao = " in (" + str + ")";
					break;
				default:
					break;
				}
			}
			if (i == 0) {
				sql.append(" where ");
			} else
				sql.append(" and ");
			String propertyName = filters.get(i).getPropertyName();
			if ("successId".equals(propertyName)) {
				propertyName = "providerId";
			}
			sql.append(propertyName + bijiao);
		}
		sql.append(" GROUP BY status");
		LoggerFactory.getLogger("countSql:").info(sql.toString());
		List<Map<String, Object>> query = orderInfoService.query(sql.toString());
		if (query.size() == 0)
			return null;
		Double successTime = 0d;
		Double failTime = 0d;
		Integer totalNum = 0;
		Integer successNum = 0;
		Integer failNum = 0;
		Integer chargeNum = 0;
		Integer waitNum = 0;
		Integer handleNum = 0;
		Integer newNum = 0;
		Double totalMoney = 0d;
		Double successMoney = 0d;
		Double successCost = 0d;
		Double failMoney = 0d;
		Double chargeMoney = 0d;
		Double waitMoney = 0d;
		Double handleMoney = 0d;
		Double newMoney = 0d;
		Double earnMoney = 0d;
		for (Map<String, Object> map : query) {
			Integer state = (Integer) map.get("state");
			Integer stateNum = map.get("stateNum") != null ? Integer.parseInt(map.get("stateNum").toString()) : 0;
			BigDecimal decimalCost = map.get("cost") != null ? (new BigDecimal(map.get("cost").toString())) : new BigDecimal(0);
			BigDecimal decimalPrice = map.get("price") != null ? (new BigDecimal(map.get("price").toString())) : new BigDecimal(0);
			Double cost = decimalCost.doubleValue();
			Double price = decimalPrice.doubleValue();
			state = state != null ? state : 0;
			totalMoney += price;
			totalNum += stateNum;
			switch (state) {
			case 1:
				newNum += stateNum;
				newMoney += price;
				break;
			case 2:
				chargeNum += stateNum;
				chargeMoney += price;
				break;
			case 3:
				successNum += stateNum;
				successMoney += price;
				successCost += cost;
				break;
			case 4:
				failNum += stateNum;
				failMoney += price;
				break;
			case 5:
				waitNum += stateNum;
				waitMoney += price;
				break;
			case 6:
				handleNum += stateNum;
				handleMoney += price;
				break;
			default:
				break;
			}
		}
		earnMoney = successMoney - successCost;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("chargeMoney", chargeMoney);
		map.put("chargeNum", chargeNum);
		map.put("successTime", successTime);
		map.put("successMoney", successMoney);
		map.put("successNum", successNum);
		map.put("successCost", successCost);
		map.put("failTime", failTime);
		map.put("failMoney", failMoney);
		map.put("failNum", failNum);
		map.put("waitMoney", waitMoney);
		map.put("waitNum", waitNum);
		map.put("handleMoney", handleMoney);
		map.put("handleNum", handleNum);
		map.put("totalNum", totalNum);
		map.put("totalMoney", totalMoney);
		map.put("earnMoney", earnMoney);
		return map;
	}
	public static Map<String, Object> getOldDataCountMap(OrderInfoService orderInfoService, List<PropertyFilter> filters, String ORDER_STATUS,
			String tableName) {
		String whereSql = BaseUtil.getSqlConditionByFilters(filters, log);
		String countSql = "select count(id) as count from " + tableName + " " + whereSql;
		List<Map<String, Object>> countList = orderInfoService.query(countSql);
		if (countList.size() == 0) {
			return null;
		}
		Object countObject = countList.get(0).get("count");
		Integer count = countObject != null ? Integer.parseInt(countObject.toString()) : 0;
		int countNum = 1600000;
		if (count > countNum) {
			Map map = new HashMap<>();
			map.put("failReason", "统计数量不能超过"+countNum+"条");
			return map;
		}
		StringBuffer sql = new StringBuffer(200);
		sql.append("select count(" + ORDER_STATUS + ") as stateNum," + ORDER_STATUS + " as state,sum(cost) as cost,sum(price) as price from "
				+ tableName);
		sql.append(" where " + ORDER_STATUS + " in (3) ").append(whereSql.replaceAll("where", "and")).append("  GROUP BY status");
		Integer totalNum = 0;
		Integer successNum = 0;
		// Integer failNum = 0;
		Double totalMoney = 0d;
		Double successMoney = 0d;
		Double successCost = 0d;
		// Double failMoney = 0d;
		Double earnMoney = 0d;
		List<Map<String, Object>> query = orderInfoService.query(sql.toString());
		for (Map<String, Object> map : query) {
			Integer state = (Integer) map.get("state");
			Integer stateNum = map.get("stateNum") != null ? Integer.parseInt(map.get("stateNum").toString()) : 0;
			BigDecimal decimalCost = map.get("cost") != null ? (new BigDecimal(map.get("cost").toString())) : new BigDecimal(0);
			BigDecimal decimalPrice = map.get("price") != null ? (new BigDecimal(map.get("price").toString())) : new BigDecimal(0);
			Double cost = decimalCost.doubleValue();
			Double price = decimalPrice.doubleValue();
			state = state != null ? state : 0;
			totalMoney += price;
			totalNum += stateNum;
			switch (state) {
			case 3:
				successNum += stateNum;
				successMoney += price;
				successCost += cost;
				break;
			default:
				break;
			}
		}
		earnMoney = successMoney - successCost;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("successMoney", successMoney);
		map.put("successNum", successNum);
		map.put("successCost", successCost);
		map.put("totalNum", totalNum);
		map.put("totalMoney", totalMoney);
		map.put("earnMoney", earnMoney);
		return map;

	}

}
