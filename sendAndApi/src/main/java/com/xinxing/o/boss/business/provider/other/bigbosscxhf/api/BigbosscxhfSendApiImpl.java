package com.xinxing.o.boss.business.provider.other.bigbosscxhf.api;

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
import com.xinxing.o.boss.business.provider.other.bigbosscx.api.BigbosscxSendApiImpl;
import com.xinxing.o.boss.business.provider.other.bigbosscx.util.BigbosscxUtils;
import com.xinxing.o.boss.business.provider.other.bigbosscxhf.util.BigbosscxhfUtils;
import com.xinxing.o.boss.common.http.HttpUtils;
import com.xinxing.o.boss.common.phone.PhoneUtils;
import com.xinxing.o.boss.common.resource.Constants;
import com.xinxing.o.boss.common.util.FlowQueryLogUtils;
import com.xinxing.o.boss.common.util.FlowSendLogUtils;

public class BigbosscxhfSendApiImpl implements SendApi {

	private static Logger log = Logger.getLogger(BigbosscxhfSendApiImpl.class);
	
	public static void main(String[] args) {
		SendOrderInfo sendOrderInfo = PhoneUtils.createSendOrderInfo("orderId", "supplierCode,30", "supplierOrderId", "phone");
		BigbosscxhfSendApiImpl hf = new BigbosscxhfSendApiImpl();
		SendOrderResult result = hf.send(sendOrderInfo);
		System.out.println(result);
	}
	
	/**
	 * 返回响应数据 {"rspCode":0,"rspMsg":"success","taskId":3209} 响应码为 0
	 * 时表示订单提交成功，其他则表示错误
	 */
	@Override
	public SendOrderResult send(SendOrderInfo sendOrderInfo) {
		SendOrderResult result = null;
		String orderId = sendOrderInfo.getOrderId();
		try {
			String sendUrl = Constants.getString("bigbosscxhf_sendUrl");
			String sendStr = BigbosscxhfUtils.getSendStr(sendOrderInfo);
			
			FlowSendLogUtils.sendReqLog(log,orderId,sendUrl+ "," + sendStr);
			String getResp = HttpUtils.sendPostWithContentType(sendUrl, sendStr, "application/json");
			FlowSendLogUtils.sendResLog(log,orderId, getResp);
			
			if (StringUtils.isNotBlank(getResp)) {
				JSONObject obj = JSON.parseObject(getResp);
				String rspCode = obj.getString("rspCode");
				String msg = BigbosscxhfUtils.getErrorMsg(rspCode);
				if ("0".equals(rspCode)) {
					String theirOrderId = obj.getString("taskId");
					result = new SendOrderResult(SendOrderStatus.WAIT_CALLBACK.status, null, orderId);
				}else if("1009".equals(rspCode)){ //余额不足转人工
					result = new SendOrderResult(SendOrderStatus.NEED_MANUAL.status,msg,orderId);
				}else {
					result = new SendOrderResult(SendOrderStatus.FAILED.status,msg,orderId);
				}
			} else {
				result = new SendOrderResult(SendOrderStatus.WAIT_CALLBACK.status, "充值响应为空",orderId);
			}
		} catch (Exception e) {
			result = new SendOrderResult(SendOrderStatus.NEED_MANUAL.status, "系统异常,请找技术人员", orderId);
			FlowSendLogUtils.sendExceptionLog(log,orderId, e);
		}
		return result;
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
			String ourOrderId = sendOrderInfo.getOrderId();// 我方id
			try {
	
				String queryUrl = Constants.getString("bigbosscxhf_queryUrl");
				String qureyStr = BigbosscxhfUtils.getQueryStr(sendOrderInfo);
				// 获取订单查询日志
				FlowQueryLogUtils.queryReqLog(log, ourOrderId, queryUrl + "," + qureyStr);
				String getResp = HttpUtils.sendPostWithContentType(queryUrl, qureyStr, "application/json");
				FlowQueryLogUtils.queryResLog(log, sendOrderInfo.getOrderId(), getResp);

				if (StringUtils.isNotBlank(getResp)) {
					JSONObject obj = JSON.parseObject(getResp);
					String rspCode = obj.getString("rspCode");
					String msg = BigbosscxhfUtils.getErrorMsg(rspCode);
					String status = obj.getString("status");
					if ("0".equals(rspCode)) {
						if ("4".equals(status)) {
							result = new SendOrderResult(SendOrderStatus.SUCCESS.status, null,ourOrderId);
						} else if ("5".equals(status)) {
							result = new SendOrderResult(SendOrderStatus.FAILED.status,msg,ourOrderId);
						} else {
							result = new SendOrderResult(SendOrderStatus.WAIT_CALLBACK.status,msg,ourOrderId);
						}
					} else {
						result = new SendOrderResult(SendOrderStatus.WAIT_CALLBACK.status,msg,ourOrderId);
					}
				} else {
					result = new SendOrderResult(SendOrderStatus.WAIT_CALLBACK.status,"查询返回为空",ourOrderId);
				}
				map.put(ourOrderId, result);
			} catch (Exception e) {
				result= new SendOrderResult(SendOrderStatus.NEED_MANUAL.status, "系统异常,请找技术人员",ourOrderId);
				FlowSendLogUtils.sendExceptionLog(log,ourOrderId, e);
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
