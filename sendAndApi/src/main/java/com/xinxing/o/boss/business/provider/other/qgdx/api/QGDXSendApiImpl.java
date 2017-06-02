package com.xinxing.o.boss.business.provider.other.qgdx.api;

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
import com.xinxing.o.boss.business.provider.other.josy.util.JOSYUtils;
import com.xinxing.o.boss.business.provider.other.qgdx.util.QGDXUtils;
import com.xinxing.o.boss.common.http.HttpUtils;
import com.xinxing.o.boss.common.phone.PhoneUtils;
import com.xinxing.o.boss.common.resource.Constants;
import com.xinxing.o.boss.common.util.FlowQueryLogUtils;
import com.xinxing.o.boss.common.util.FlowSendLogUtils;

public class QGDXSendApiImpl implements SendApi {
	private static Logger log = Logger.getLogger(QGDXSendApiImpl.class);
	/*public static void main(String[] args) {
		SendOrderInfo sendOrderInfo = PhoneUtils.createSendOrderInfo("orderId", "supplierCode", "supplierOrderId", "phone");
		System.out.println(sendOrderInfo.toString());
		QGDXSendApiImpl qgdx = new QGDXSendApiImpl();
		SendOrderResult result = qgdx.query(sendOrderInfo);
		System.out.println(result);
	}*/
	
	/* 返回数据
	 * {"rspCode":0,"rspMsg":"success","taskId":3209}    */
	@Override
	public SendOrderResult send(SendOrderInfo sendOrderInfo) {
		SendOrderResult result = null;
		String orderId = sendOrderInfo.getOrderId();
		String sendUrl = Constants.getString("qgdx_sendUrl");
		try {
			String sendStr = QGDXUtils.getSendStr(sendOrderInfo);
			//获取订单发送(传递/返回)日志
			FlowSendLogUtils.sendReqLog(log, orderId,sendUrl+","+sendStr);
			String getResp = HttpUtils.sendPost1(sendUrl,sendStr, "application/json","utf-8");
			FlowSendLogUtils.sendResLog(log, orderId, getResp);
			
			if (StringUtils.isNotBlank(getResp)) {
				JSONObject obj = JSON.parseObject(getResp);
				String rspCode = obj.getString("rspCode");
				String theirOrderId = obj.getString("taskId");
				String rspMsg = obj.getString("rspMsg");
				String msg = QGDXUtils.getErrorMsg(rspCode);
				
				if ("0".equals(rspCode)) { // 0 成功，其他失败 
					result = new SendOrderResult(SendOrderStatus.WAIT_CALLBACK.status,null,theirOrderId);
				}else if("1009".equals(rspCode)){  //余额不足转人工
					result = new SendOrderResult(SendOrderStatus.NEED_MANUAL.status,msg,orderId);
				}else if ("1004".equals(rspCode)) {
					result = new SendOrderResult(SendOrderStatus.NEED_MANUAL.status,msg,orderId);
				}else {
					result = new SendOrderResult(SendOrderStatus.FAILED.status,rspMsg,orderId);
				}
			}else { //直接查询
				result = new SendOrderResult(SendOrderStatus.WAIT_CALLBACK.status,"充值响应结果为空",orderId);
			}
		} catch (Exception e) {
			result = new SendOrderResult(SendOrderStatus.NEED_MANUAL.status,"系统异常,请找技术人员",orderId);
			FlowSendLogUtils.sendExceptionLog(log, sendOrderInfo.getOrderId(), e);
		}
		return result;
	}

	/*返回数据
	 * 成功：{"rspCode":0,"status":2} 
	 * 错误： {"rspCode":1010,"rspMsg":"order_not_exist","status":0}*/
	@Override
	public Map<String, SendOrderResult> querys(List<SendOrderInfo> sendOrderInfos) {
		Map<String,SendOrderResult> resultMap = new HashMap<>();
		SendOrderResult result = null;
		for (SendOrderInfo sendOrderInfo : sendOrderInfos) {
			String orderId = sendOrderInfo.getOrderId();
			String supplierOrderId = sendOrderInfo.getSupplierOrderId();
			String providerOrderId = supplierOrderId == null ? orderId :supplierOrderId;
			try {
				String queryStr = QGDXUtils.getQueryStr(sendOrderInfo);
				String queryUrl = Constants.getString("qgdx_queryUrl").trim();
				//获取订单查询(传递/返回)日志
				FlowQueryLogUtils.queryReqLog(log, orderId, queryUrl+","+queryStr);
				String getResp = HttpUtils.sendPost1(queryUrl,queryStr, "application/json","utf-8");
				FlowQueryLogUtils.queryResLog(log, orderId, getResp);
				
				if (StringUtils.isNotBlank(getResp)) {
					JSONObject obj = JSON.parseObject(getResp);
					String status = obj.getString("status");
					String rspCode = obj.getString("rspCode");
					String msg = QGDXUtils.getErrorMsg(rspCode);
					if ("0".equals(rspCode)) { //表示查询到了订单状态  
						switch (status) {
						case "2":   //充值中
							result = new SendOrderResult(SendOrderStatus.WAIT_CALLBACK.status,null,providerOrderId);
							break;
						case "4":   //充值成功
							result = new SendOrderResult(SendOrderStatus.SUCCESS.status,null,providerOrderId);
							break;
						case "5":   //充值失败
							result = new SendOrderResult(SendOrderStatus.FAILED.status,msg,providerOrderId);
							break;
						default:
							result = new SendOrderResult(SendOrderStatus.WAIT_CALLBACK.status,null,providerOrderId);
							break;
						}
					}else if("1010".equals(rspCode)){   //订单不存在
						result = new SendOrderResult(SendOrderStatus.FAILED.status,msg,providerOrderId);
					}else if("1009".equals(rspCode)){   //余额不足
						result = new SendOrderResult(SendOrderStatus.NEED_MANUAL.status,msg,providerOrderId);
					}else {
						result = new SendOrderResult(SendOrderStatus.WAIT_CALLBACK.status,null,providerOrderId);
					}
				}else {  
					result = new SendOrderResult(SendOrderStatus.WAIT_CALLBACK.status,"查询返回为空",providerOrderId);
				}
			} catch (Exception e) {
				result= new SendOrderResult(SendOrderStatus.NEED_MANUAL.status, "系统异常,请找技术人员",orderId);
				FlowQueryLogUtils.queryExceptionLog(log,orderId, e);
			}
			
			resultMap.put(orderId,result);
		}
		return resultMap;
	}

	@Override
	public SendOrderResult query(SendOrderInfo sendOrderInfo) {
		List<SendOrderInfo> queryList = new ArrayList<>();
		queryList.add(sendOrderInfo);
		Map<String, SendOrderResult> resultMap = querys(queryList);
		if (resultMap != null) {
			return resultMap.get(sendOrderInfo.getOrderId());
		}
		return null;
	}

}
