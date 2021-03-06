package com.xinxing.o.boss.business.provider.other.justtest.api;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.util.DateUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.xinxing.boss.business.api.domain.SendOrderInfo;
import com.xinxing.boss.business.api.domain.SendOrderResult;
import com.xinxing.boss.business.api.domain.SendOrderStatus;
import com.xinxing.o.boss.business.provider.api.supplier.api.SendApi;
import com.xinxing.o.boss.business.provider.other.justtest.util.JusttestUtils;
import com.xinxing.o.boss.common.password.PawUtils;
import com.xinxing.o.boss.common.util.CommonUtils;
import com.xinxing.o.boss.common.util.FlowQueryLogUtils;
import com.xinxing.o.boss.common.util.FlowSendLogUtils;
import com.xinxing.o.boss.common.xml.XmlUtils;

public class JusttestSendApiImpl implements SendApi {
	private static Logger log = Logger.getLogger(JusttestSendApiImpl.class);
	public static Boolean isOurId = false;

	/*public static void main(String[] args) throws Exception {
		//PhoneUtils.initLog(log);
		SendOrderInfo sendOrderInfo = PhoneUtils.createSendOrderInfo("1608170test22222", "208511296,1049", "CQFS2017020413402930992210",
				PhoneUtils.guangdong_yd);
		System.out.println("send:" + JsonUtils.obj2Json(new JusttestSendApiImpl().send(sendOrderInfo)));
		//System.out.println("query:" + JsonUtils.obj2Json(new JusttestSendApiImpl().query(sendOrderInfo)));
	}*/
	
	@SuppressWarnings("all")
	@Override
	public SendOrderResult send(SendOrderInfo sendOrderInfo) {
		SendOrderResult result = null;
		String order_orderId = sendOrderInfo.getOrderId();// AA订单id
		// CQFS+8位日期(例如:20151108141558）+8位随机码（例如：12345678）
		String transactionId = "CQFS" + DateUtil.formatDate(new Date(), "yyyyMMddHHmmss") + PawUtils.getRandomNumber(8);
		sendOrderInfo.setSupplierOrderId(transactionId);
		String providerOrderId = transactionId;
		try {
		  /*<?xml version=\"1.0\" encoding=\"UTF-8\" ?>
			<Root> 
	  			<Header> 
	    			<ExchangeId>CQWT00201511091039047171367345</ExchangeId>  
	     			<Code>0000</Code>  
	     			<Message>成功</Message> 
	  			</Header>  
	  			<Results> 
			   		<accept_url>htt://cq.189.cn/test?id=XXXXXXXXXXXXXX</accept_url>
	            	<AcceptId>231511093600479</AcceptId>  
	            	<AcceptedTime>2015-11-09 10:39:07</AcceptedTime>  
	            	<SpecName>1元900M新入网本地流量包_预付费</SpecName>  
	            	<Cust_id>800206713634</Cust_id>  
	            	<ObjectType>00</ObjectType>  
	            	<OrderId>823151109976908</OrderId>  
	            	<ServCodeName>订购</ServCodeName> 
	    		</Results> 
			</Root>*/
			String sendReq = JusttestUtils.getSendReq(sendOrderInfo);
			FlowSendLogUtils.sendReqLog(log, order_orderId, sendReq);
			String sendRes = JusttestUtils.send(sendReq);
			FlowSendLogUtils.sendResLog(log, order_orderId, sendRes);
			if (StringUtils.isNotBlank(sendRes)) {
				String code = XmlUtils.getXmlNoteValue(sendRes, "Root/Header/Code/text()");
				String msg = XmlUtils.getXmlNoteValue(sendRes, "Root/Header/Message/text()");
				// providerOrderId =sendResp.get("respid")!=null?sendResp.get("respid").toString():"";
				if ("0000".equals(code)) {
					result = new SendOrderResult(SendOrderStatus.WAIT_CALLBACK.status, null, providerOrderId);
				}else if ("-10014".equals(code)) { //余额不足转手工
					result = new SendOrderResult(SendOrderStatus.NEED_MANUAL.status,msg, providerOrderId);
				}else {
					result = new SendOrderResult(SendOrderStatus.FAILED.status, code + ":" + msg, providerOrderId);
				}
			} else {
				result = new SendOrderResult(SendOrderStatus.NEED_MANUAL.status, "充值返回为空", providerOrderId);
			}
		} catch (Exception e) {
			result = new SendOrderResult(SendOrderStatus.NEED_MANUAL.status, "系统异常,请找技术人员", providerOrderId);
			FlowSendLogUtils.sendExceptionLog(log, order_orderId, e);
		}
		return result;
	}
	/*  <?xml version="1.0" encoding="UTF-8"?>
	    <Root> 
	  		<Header> 
	    		<ExchangeId>CQWT00201511091039047171367345</ExchangeId>  
	     		<Code>0000</Code>  
	     		<Message>成功</Message> 
	  		</Header>  
	  		<Results> 
			 	<accept_state>1</accept_state>
	          	<crm_state>1</crm_state>  
			 	<desc>已竣工</desc>	
	  		</Results> 
		</Root>  */
	@SuppressWarnings("all")
	@Override
	public Map<String, SendOrderResult> querys(List<SendOrderInfo> sendOrderInfos) {
		Map<String, SendOrderResult> map = new HashMap<>();
		SendOrderResult result = null;
		for (SendOrderInfo sendOrderInfo : sendOrderInfos) {
			String order_orderId = sendOrderInfo.getOrderId();// AA订单id
			String order_providerOrderId = sendOrderInfo.getSupplierOrderId();// 供应商流水号
			try {
				String queryReq = JusttestUtils.getQueryReq(sendOrderInfo);
				FlowQueryLogUtils.queryReqLog(log, order_orderId, queryReq);
				String queryResp = JusttestUtils.query(queryReq);
				FlowQueryLogUtils.queryResLog(log, order_orderId, queryResp);
				
				if (StringUtils.isNotBlank(queryResp)) {
					String code = XmlUtils.getXmlNoteValue(queryResp, "Root/Header/Code/text()");
					String msg = XmlUtils.getXmlNoteValue(queryResp, "Root/Header/Message/text()");
					if ("0000".equals(code)) {
						result = new SendOrderResult(SendOrderStatus.SUCCESS.status, null, order_providerOrderId);
					}else if ("-10014".equals(code)) { //余额不足转手工
						result = new SendOrderResult(SendOrderStatus.NEED_MANUAL.status,JusttestUtils.getErrorMsg(code), order_providerOrderId);
					}else {
						result = new SendOrderResult(SendOrderStatus.FAILED.status, code +":"+JusttestUtils.getErrorMsg(code), order_providerOrderId);
					}
				} else {
					result = new SendOrderResult(SendOrderStatus.WAIT_CALLBACK.status, "查询返回为空", order_providerOrderId);
				}
			} catch (Exception e) {
				result = new SendOrderResult(SendOrderStatus.NEED_MANUAL.status, "系统异常,请找技术人员", order_providerOrderId);
				FlowQueryLogUtils.queryExceptionLog(log, CommonUtils.getLogParamListId(sendOrderInfos), e);
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
