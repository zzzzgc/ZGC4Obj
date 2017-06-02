package hy.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.xinxing.boss.business.api.domain.SendOrderInfo;
import com.xinxing.boss.business.api.domain.SendOrderResult;
import com.xinxing.boss.business.api.domain.SendOrderStatus;
import com.xinxing.o.boss.business.provider.other.fb.util.FBUtils;
import com.xinxing.o.boss.common.http.HttpUtils;
import com.xinxing.o.boss.common.resource.Constants;
import com.xinxing.o.boss.common.util.FlowQueryLogUtils;
import com.xinxing.o.boss.common.util.FlowSendLogUtils;

public class Demo extends test {
	private static final Logger log = Logger.getLogger(Demo.class);
	SendOrderInfo sendOrderInfo = new SendOrderInfo("YG", "AAA", "10123,10122,10123", "13622662311", "FB_YD", new Date(), 1, "170504B1BAAA");
	@Test
	public void send() {
		SendOrderResult result = null;
		

		String order_orderId = sendOrderInfo.getOrderId();

		try {
			String sendReq = FBUtils.getSendReq(sendOrderInfo);
			String sendUrl = Constants.getString("fb_sendUrl");

			FlowSendLogUtils.sendReqLog(log, order_orderId, sendReq);
			String sendRes = HttpUtils.sendPost1(sendUrl, sendReq, "application/json", "UTF-8");
			FlowSendLogUtils.sendResLog(log, order_orderId, sendRes);

			if (StringUtils.isNotBlank(sendRes)) {
				HashMap<String, String> sendMap = JSON.parseObject(sendRes, new TypeReference<HashMap<String, String>>() {
				});
				String status = sendMap.get("status");
				String msg = sendMap.get("msg");
				// String orderId = sendMap.get("orderId");

				switch (status) {
					case "1" :
					case "3" :
						result = new SendOrderResult(SendOrderStatus.WAIT_CALLBACK.status, null, order_orderId);
						break;
					case "2" :
						result = new SendOrderResult(SendOrderStatus.FAILED.status, status + ":" + msg, order_orderId);
						break;
					case "11" :
						result = new SendOrderResult(SendOrderStatus.NEED_MANUAL.status, status + ":" + msg, order_orderId);
						break;
					default :
						result = new SendOrderResult(SendOrderStatus.FAILED.status, status + ":" + msg, order_orderId);
				}
			} else {
				result = new SendOrderResult(SendOrderStatus.NEED_MANUAL.status, "充值返回为空,请找技术人员", order_orderId);
			}
		} catch (Exception e) {
			result = new SendOrderResult(SendOrderStatus.NEED_MANUAL.status, "系统异常,请找技术人员", order_orderId);
			FlowSendLogUtils.sendExceptionLog(log, order_orderId, e);
		}
		System.out.println(result);
	}
	
	@Test
	public void query() {
		List<SendOrderInfo> queryList = new ArrayList<>();
		queryList.add(sendOrderInfo);
		Map<String, SendOrderResult> mapRes = querys(queryList);
		if (mapRes != null) {
			System.out.println(mapRes.get(sendOrderInfo.getOrderId()));
		}
	}
	
	public Map<String, SendOrderResult> querys(List<SendOrderInfo> sendOrderInfo) {

		Map<String, SendOrderResult> map = new HashMap<>();
		SendOrderResult result = null;
		String supplierOrderId = null;
		for (SendOrderInfo sendOrder : sendOrderInfo) {
			String order_orderId = sendOrder.getOrderId();
			try {
				String queryUrl = Constants.getString("fb_queryUrl");
				String queryReq = FBUtils.getQueryReq(sendOrder);

				// 发送请求
				FlowQueryLogUtils.queryReqLog(log, order_orderId, queryReq);
				String queryRes = HttpUtils.sendPost1(queryUrl, queryReq, "application/json", "utf-8");
				FlowQueryLogUtils.queryResLog(log, order_orderId, queryRes);

				if (StringUtils.isNotBlank(queryRes)) {
					HashMap<String, String> sendMap = JSON.parseObject(queryRes, new TypeReference<HashMap<String, String>>() {
					});
					String status = sendMap.get("status");
					String msg = sendMap.get("msg");
					supplierOrderId = sendMap.get("orderId");
					if (status != null) {
						switch (status) {
							case "11" :
								result = new SendOrderResult(SendOrderStatus.SUCCESS.status, null, supplierOrderId);
								break;
							case "2" :
								result = new SendOrderResult(SendOrderStatus.FAILED.status, status + ":" + msg, supplierOrderId);
								break;
							default :
								result = new SendOrderResult(SendOrderStatus.WAIT_CALLBACK.status, status + ":" + msg, supplierOrderId);
								break;
						}
					}
				} else {
					result = new SendOrderResult(SendOrderStatus.WAIT_CALLBACK.status, "查询返回为空,请找技术人员", supplierOrderId);
				}
			} catch (Exception e) {
				e.printStackTrace();
				result = new SendOrderResult(SendOrderStatus.NEED_MANUAL.status, "系统异常,请找技术人员", supplierOrderId);
				FlowQueryLogUtils.queryExceptionLog(log, order_orderId, e);
			}
			map.put(order_orderId, result);
		}
		return map;
	}

}
