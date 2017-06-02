package com.xinxing.o.boss.business.provider.other.bigbosscx.api;

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
import com.xinxing.o.boss.business.provider.other.bigbosscx.util.BigbosscxUtils;
import com.xinxing.o.boss.common.http.HttpUtils;
import com.xinxing.o.boss.common.phone.PhoneUtils;
import com.xinxing.o.boss.common.resource.Constants;
import com.xinxing.o.boss.common.util.FlowQueryLogUtils;
import com.xinxing.o.boss.common.util.FlowSendLogUtils;

public class BigbosscxSendApiImpl implements SendApi {
	private static Logger log = Logger.getLogger(BigbosscxSendApiImpl.class);

	/*
	 * public static void main(String[] args) { PhoneUtils.initLog(log); 10179
	 * SendOrderInfo sendOrderInfo = PhoneUtils.createSendOrderInfo(
	 * "170227B0B10427379", "YLIANG_YD", "170227B0B10427379", "18820036966");
	 * System.out.println("send:" + JsonUtils.obj2Json(new
	 * BigbosscxSendApiImpl().send(sendOrderInfo))); System.out.println("query:"
	 * + JsonUtils.obj2Json(new BigbosscxSendApiImpl().query(sendOrderInfo))); }
	 */

	/**
	 * 返回响应数据 {"rspCode":0,"rspMsg":"success","taskId":3209} 响应码为 0
	 * 时表示订单提交成功，其他则表示错误
	 */
	@Override
	public SendOrderResult send(SendOrderInfo sendOrderInfo) {
		SendOrderResult result = null;
		try {
			String ourOrderId = sendOrderInfo.getOrderId(); 
			String sendStr = BigbosscxUtils.getSendStr(sendOrderInfo);
			String url = Constants.getString("bigbosscx_url");
			FlowSendLogUtils.sendReqLog(log, sendOrderInfo.getOrderId(), url + "," + sendStr);
			String sendRes = HttpUtils.sendPostWithContentType(url, sendStr, "application/json");
			FlowSendLogUtils.sendResLog(log, sendOrderInfo.getOrderId(), sendRes);
			if (StringUtils.isNotBlank(sendRes)) {
				JSONObject obj = JSON.parseObject(sendRes);
				String rspMsg = obj.getString("rspMsg");
				String status = obj.getString("rspCode");
				if ("0".equals(status)) {
					String theirOrderId = obj.getString("taskId");
					result = new SendOrderResult(SendOrderStatus.WAIT_CALLBACK.status, null, theirOrderId);
				} else if ("1009".equals(status)) { //余额不足转人工
					result = new SendOrderResult(SendOrderStatus.NEED_MANUAL.status,"余额不足",ourOrderId);
				}else {
					result = new SendOrderResult(SendOrderStatus.FAILED.status, "status="+status+",msg="+rspMsg, null);
				}
			} else {
				result = new SendOrderResult(SendOrderStatus.FAILED.status, "无法确认供货商是否收到订单，请查询不存在后重发", null);
			}
		} catch (Exception e) {
			FlowSendLogUtils.sendExceptionLog(log, sendOrderInfo.getOrderId(), e);
		}
		return result;
	}

	public static void main(String[] args) {
		SendOrderInfo info = PhoneUtils.createSendOrderInfo("asdasd", "12123", "adadasdasdasd", "13242871762");
		System.out.println(new BigbosscxSendApiImpl().query(info));
	}

	/**
	 * 返回响应数据 {"rspCode":0,"rspMsg":"success","status":2}
	 * rspCode为0表示查询到订单状态，订单最终成功或失败，查看status(4 成功，5 失败，2 充值中)
	 */
	@Override
	public Map<String, SendOrderResult> querys(List<SendOrderInfo> sendOrderInfos) {
		Map<String, SendOrderResult> map = new HashMap<>();
		SendOrderResult result = null;
		for (SendOrderInfo sendOrderInfo : sendOrderInfos) {
			try {
				String ourOrderId = sendOrderInfo.getOrderId();// AA订单id
				// String providerOrderId =
				// sendOrderInfo.getSupplierOrderId();// 供应商流水号
				String queryUrl = Constants.getString("bigbosscx_queryUrl");
				String qurerStr = BigbosscxUtils.getQueryStr(sendOrderInfo);
				// 获取订单查询日志
				FlowQueryLogUtils.queryReqLog(log, ourOrderId, queryUrl + "," + qurerStr);
				String getResp = HttpUtils.sendPostWithContentType(queryUrl, qurerStr, "application/json");
				FlowQueryLogUtils.queryResLog(log, sendOrderInfo.getOrderId(), getResp);

				if (StringUtils.isNotBlank(getResp)) {
					JSONObject obj = JSON.parseObject(getResp);
					String rspCode = obj.getString("rspCode");
					String rspMsg = obj.getString("rspMsg");
					String status = obj.getString("status");
					if ("0".equals(rspCode)) {
						if ("4".equals(status)) {
							result = new SendOrderResult(SendOrderStatus.SUCCESS.status, null, null);
						} else if ("5".equals(status)) {
							result = new SendOrderResult(SendOrderStatus.FAILED.status,  "status="+status+",msg="+rspMsg, null);
						} else {
							result = new SendOrderResult(SendOrderStatus.WAIT_CALLBACK.status,  "status="+status+",msg="+rspMsg, null);
						}
					} else {
						result = new SendOrderResult(SendOrderStatus.FAILED.status, "查询失败,请求码:" + rspCode + ",请找技术",
								null);
					}
				} else {
					result = new SendOrderResult(SendOrderStatus.NEED_MANUAL.status, "查询数据为空,请找技术", null);
				}
				map.put(ourOrderId, result);
			} catch (Exception e) {
				FlowSendLogUtils.sendExceptionLog(log, sendOrderInfo.getOrderId(), e);
			}
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
