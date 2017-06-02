package xinxing.boss.admin.boss.order.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xinxing.boss.admin.boss.customer.domain.CustomerInfo;
import xinxing.boss.admin.boss.order.dao.OrderInfoDAO;
import xinxing.boss.admin.boss.order.domain.CommonResult;
import xinxing.boss.admin.boss.order.domain.OrderInfo;
import xinxing.boss.admin.common.persistence.HibernateDao;
import xinxing.boss.admin.common.service.BaseService;
import xinxing.boss.admin.common.utils.base.BaseUtil;
import xinxing.boss.admin.common.utils.constants.Constants;
import xinxing.boss.admin.common.utils.http.HttpUtils;
import xinxing.boss.admin.common.utils.json.JsonUtils;
import xinxing.boss.admin.common.utils.security.MD5_HexUtil;

@Service
public class OrderInfoService extends BaseService<OrderInfo, Integer> {
	private static Logger logger = LoggerFactory.getLogger(OrderInfoService.class);
	@Autowired
	private OrderInfoDAO orderInfoDAO;

	@Override
	public HibernateDao<OrderInfo, Integer> getEntityDao() {
		return orderInfoDAO;
	}

	/**
	 * 刷新状态
	 * 
	 * @param type
	 * @return
	 */
	public String flushStatus(String type) {
		String str = "{\"type\":\"" + type + "\"}";
		String reqUrl = Constants.getString("bossFreshUrl_send");
		String string = HttpUtils.sendPost(reqUrl, str, "UTF-8");

		reqUrl = Constants.getString("bossFreshUrl_api");
		string = HttpUtils.sendPost(reqUrl, str, "UTF-8");
		return string;
	}

	public void handleOrder(String action, String orderids) {
		try {
			String str = "{\"paw\":\"123456\",\"action\":\"" + action + "\",\"orderIds\":" + orderids + "}";
			String reqUrl = Constants.getString("bossManualUrl");
			String sendPost = HttpUtils.sendPost(reqUrl, str, "UTF-8");
			logger.info("手工请求::" + str + " reqUrl =" + reqUrl + " ,返回信息" + sendPost);
		} catch (Exception e) {
			logger.info("orderids:" + orderids + e.getMessage(), e);
			logger.error("orderids:" + orderids + e.getMessage(), e);
		}
	}

	public void handleOrder(String action, String orderids, String providerProductId) {
		try {
			String str = "{\"paw\":\"123456\",\"action\":\"" + action + "\",\"providerProductId\":" + providerProductId + ",\"orderIds\":"
					+ orderids + "}";
			String reqUrl = Constants.getString("bossManualUrl");
			System.out.println(str);
			String sendPost = HttpUtils.sendPost(reqUrl, str, "UTF-8");
			logger.info("手工请求:" + str + " reqUrl =" + reqUrl + ",返回信息" + sendPost);
		} catch (Exception e) {
			logger.info("orderids:" + orderids + e.getMessage(), e);
			logger.error("orderids:" + orderids + e.getMessage(), e);
		}
	}

	public void handleOrder(Map<String, Object> map) {
		try {
			map.put("paw", "123456");
			String str = JsonUtils.obj2Json(map);
			String reqUrl = Constants.getString("bossManualUrl");
			String sendPost = "";
			if (BaseUtil.isLocalTest()) {
				sendPost = "{\"state\":true}";
			} else {
				HttpUtils.sendPost(reqUrl, str, "UTF-8");
			}
			logger.info("手工请求:" + str + ",reqUrl =" + reqUrl + ",返回信息" + sendPost);
		} catch (Exception e) {
			logger.error("orderids:" + JsonUtils.obj2Json(map.get("orderids")) + e.getMessage(), e);
		}
	}

	public String callBackOrder(OrderInfo orderInfo, CustomerInfo customerInfo) {
		// 获取Token
		String url = Constants.getString("bossReqUrl");
		CommonResult commonResult = getToken(url, customerInfo);
		if (commonResult.getType() == 2) {
			return "errorToken";
		}
		// 发送订单
		String token = commonResult.getMsg();
		String str = "{\"Action\":\"CallBackCustomer\",\"Version\":\"V1.0\",\"MerChant\":\"" + customerInfo.getId() + "\",\"FlowKey\":"
				+ customerInfo.getDevKey() + "\"}";
		logger.info("请求接口:CallBackCustomer 发送订单信息:" + str);
		String returnPost = HttpUtils.sendPost(url, str, MD5_HexUtil.md5Hex(str).toUpperCase(), token);
		logger.info("Boss返回信息:" + returnPost);
		CommonResult result = JsonUtils.json2Obj(returnPost, CommonResult.class);
		if (result.getType() == 4) {
			return "success";
		}
		return "error";

	}

	private CommonResult getToken(String url, CustomerInfo customerInfo) {
		String string = "{\"Action\":\"GetToken\",\"Version\":\"V1.0\",\"MerChant\":\"" + customerInfo.getId() + "\",\"ClientID\":\""
				+ customerInfo.getLoginName() + "\",\"ClientSeceret\":\"" + customerInfo.getDevKey() + "\"}";
		String token = HttpUtils.sendPost(url, string, MD5_HexUtil.md5Hex(string).toUpperCase(), "");
		logger.info("获取Token信息:" + token);
		return JsonUtils.json2Obj(token, CommonResult.class);
	}

	public List<Map<String, Object>> query(String sql) {
		return orderInfoDAO.query(sql);
	}

	/**
	 * 获取微信的需要手工处理的订单，最前面的30条
	 * 
	 * @return
	 */
	public List<Map<String, Object>> getManualWeiXinOrders() {
		return orderInfoDAO.getManualWeiXinOrders();
	}

	/**
	 * 获取tmall的需要手工处理的订单，最前面的30条
	 * 
	 * @return
	 */
	public List<Map<String, Object>> getManualTmallOrders() {
		return orderInfoDAO.getManualTmallOrders();
	}

	public List<Object> query(final String hql, Integer start, Integer pageSize, final Object... values) {
		Query query = getEntityDao().getSession().createQuery(hql);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				System.out.println("----------------------------------------------------------");
				if (values[i] instanceof Collection || values[i] instanceof Arrays) {
					query.setParameterList(String.valueOf(i), (Collection) values[i]);
				} else {
					query.setParameter(String.valueOf(i), values[i]);
				}

			}
		}
		if (start != null && pageSize != null) {
			query.setFirstResult(start);
			query.setMaxResults(pageSize);
		}
		List<Object> list = query.list();
		return list;
	}
}
