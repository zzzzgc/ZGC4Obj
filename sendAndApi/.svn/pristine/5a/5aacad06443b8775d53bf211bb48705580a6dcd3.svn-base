package com.xinxing.o.boss.business.provider.other.yliang.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xinxing.boss.business.api.domain.SendOrderInfo;
import com.xinxing.boss.business.api.domain.SendOrderResult;
import com.xinxing.boss.business.api.domain.SendOrderStatus;
import com.xinxing.o.boss.business.provider.api.supplier.api.SendApi;
import com.xinxing.o.boss.business.provider.other.yliang.util.YliangTestUtils;
import com.xinxing.o.boss.common.http.HttpUtils;
import com.xinxing.o.boss.common.phone.PhoneUtils;
import com.xinxing.o.boss.common.resource.Constants;
import com.xinxing.o.boss.common.util.FlowQueryLogUtils;
import com.xinxing.o.boss.common.util.FlowSendLogUtils;

public class YliangSendApiImpl implements SendApi {

	private static Logger log = Logger.getLogger(YliangSendApiImpl.class);
	
	public static void main(String[] args) {
		SendOrderInfo sendOrderInfo = PhoneUtils.createSendOrderInfo(
				"170227B0B10427379", "YLIANG_YD", "170227B0B10427379",
				"18820036966");
		System.out.println("query:"+new YliangSendApiImpl().query(sendOrderInfo));
	}

	@Override
	public SendOrderResult send(SendOrderInfo sendOrderInfo) {
		SendOrderResult result = null;

		String order_orderId = sendOrderInfo.getOrderId();

		try {
			String sendReq = YliangTestUtils.getSendReq(sendOrderInfo);
			String sendUrl = Constants.getString("yliang_sendUrl");

			// 发送请求 返回值 {"status":1,"msg":"订购成功"}
			FlowSendLogUtils.sendReqLog(log, order_orderId, sendReq);
			String sendRes = HttpUtils.sendGet(sendUrl + "?" + sendReq, "UTF-8");
			FlowSendLogUtils.sendResLog(log, order_orderId, sendRes);

			if (StringUtils.isNotBlank(sendRes)) {
				JSONObject sendResObject = JSON.parseObject(sendRes);

				String status = sendResObject.getString("status");
				String msg = YliangTestUtils.getSendErrorMsg(status);

				switch (status) {
				case "1":
				case "2":
					result = new SendOrderResult(SendOrderStatus.WAIT_CALLBACK.status, null, order_orderId);
					break;
				case "-3":
					result = new SendOrderResult(SendOrderStatus.FAILED.status, status + ":" + msg, order_orderId);
					break;
				default:
					result = new SendOrderResult(SendOrderStatus.FAILED.status, status + ":" + msg, order_orderId);
				}
			} else {
				result = new SendOrderResult(SendOrderStatus.NEED_MANUAL.status, "充值返回为空,请找技术人员", order_orderId);
			}
		} catch (Exception e) {
			result = new SendOrderResult(SendOrderStatus.NEED_MANUAL.status, "系统异常,请找技术人员", order_orderId);
			FlowSendLogUtils.sendExceptionLog(log, order_orderId, e);
		}
		return result;
	}

	@Override
	public Map<String, SendOrderResult> querys(List<SendOrderInfo> sendOrderInfo) {

		Map<String, SendOrderResult> map = new HashMap<>();
		SendOrderResult result = null;

		for (SendOrderInfo sendOrder : sendOrderInfo) {
			String order_orderId = sendOrder.getOrderId();
			try {
				String queryUrl = Constants.getString("yliang_queryUrl").trim();
				String queryReq = YliangTestUtils.getQueryReq(sendOrder);

				// 发送请求
				FlowQueryLogUtils.queryReqLog(log, order_orderId, queryReq);
				String queryRes = HttpUtils.sendGet(queryUrl + "?" + queryReq, "utf-8");
				FlowQueryLogUtils.queryResLog(log, order_orderId, queryRes);

				if (StringUtils.isNotBlank(queryRes)) {
					JSONObject resObj = JSON.parseObject(queryRes);
					String status = resObj.getString("status");
					String msg = resObj.getString("msg");
					if (status != null) {
						switch (status) {
						case "1":
							result = new SendOrderResult(SendOrderStatus.SUCCESS.status, null, order_orderId);
							break;
						case "2":
							result = new SendOrderResult(SendOrderStatus.FAILED.status, status + ":" + msg,
									order_orderId);
							break;
						default:
							result = new SendOrderResult(SendOrderStatus.WAIT_CALLBACK.status, status + ":" + msg,
									order_orderId);
							break;
						}
					}
				} else {
					result = new SendOrderResult(SendOrderStatus.WAIT_CALLBACK.status, "查询返回为空,请找技术人员", order_orderId);
				}
			} catch (Exception e) {
				e.printStackTrace();
				result = new SendOrderResult(SendOrderStatus.NEED_MANUAL.status, "系统异常,请找技术人员", order_orderId);
				FlowQueryLogUtils.queryExceptionLog(log, order_orderId, e);
			}
			map.put(order_orderId, result);
		}
		return map;
	}
	
	@Override
	public SendOrderResult query(SendOrderInfo sendOrderInfo) {
		List<SendOrderInfo> queryList = new ArrayList<>();
		queryList.add(sendOrderInfo);
		Map<String, SendOrderResult> mapRes = querys(queryList);
		if (mapRes != null) {
			return mapRes.get(sendOrderInfo.getOrderId());
		}
		return null;
	}
}
