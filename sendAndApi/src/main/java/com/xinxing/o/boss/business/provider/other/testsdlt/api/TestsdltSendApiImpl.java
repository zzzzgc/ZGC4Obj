package com.xinxing.o.boss.business.provider.other.testsdlt.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.xinxing.boss.business.api.domain.SendOrderInfo;
import com.xinxing.boss.business.api.domain.SendOrderResult;
import com.xinxing.boss.business.api.domain.SendOrderStatus;
import com.xinxing.o.boss.business.provider.api.supplier.api.SendApi;
import com.xinxing.o.boss.business.provider.other.testsdlt.util.TestsdltUtils;
import com.xinxing.o.boss.common.http.HttpUtils;
import com.xinxing.o.boss.common.json.JsonUtils;
import com.xinxing.o.boss.common.phone.PhoneUtils;
import com.xinxing.o.boss.common.resource.Constants;
import com.xinxing.o.boss.common.util.CommonUtils;
import com.xinxing.o.boss.common.util.FlowQueryLogUtils;
import com.xinxing.o.boss.common.util.FlowSendLogUtils;
import com.xinxing.o.boss.common.xml.XmlUtils;

public class TestsdltSendApiImpl implements SendApi {
	private static Logger log = Logger.getLogger(TestsdltSendApiImpl.class);
	public static Boolean isOurId = true;

	public static void main(String[] args) throws Exception {
		PhoneUtils.initLog(log);
		SendOrderInfo sendOrderInfo = PhoneUtils.createSendOrderInfo("1608170test222223", "0,30", "1608170test222223","1866013726800");
//		SendOrderInfo sendOrderInfo = PhoneUtils.createSendOrderInfo("1608170test22222", "0,10", "1608170test22222","18660137268" PhoneUtils.shandong_lt);
//		 System.out.println("send:" + JsonUtils.obj2Json(new TestsdltSendApiImpl().send(sendOrderInfo)));
		 System.out.println("query:" + JsonUtils.obj2Json(new TestsdltSendApiImpl().query(sendOrderInfo)));
	}

	@SuppressWarnings("all")
	@Override
	public SendOrderResult send(SendOrderInfo sendOrderInfo) {
		SendOrderResult result = null;
		String order_orderId = sendOrderInfo.getOrderId();// AA订单id     xxxxxB0Bxxxx
		String providerOrderId = isOurId ? order_orderId : null;
		try {
			String sendReq = TestsdltUtils.getSendReq(sendOrderInfo); 
			String sendUrl = Constants.getString("testsdlt_sendUrl").trim();
			FlowSendLogUtils.sendReqLog(log, order_orderId, sendReq);
			// String sendRes = HttpUtils.sendPost(sendUrl, sendReq, "application/json");
			// String sendRes = HttpUtils.sendPostByNameValue(sendUrl, sendReq);
			String sendRes = HttpUtils.sendGet(sendUrl + "?" + sendReq, "utf-8");
			FlowSendLogUtils.sendResLog(log, order_orderId, sendRes);
			if (StringUtils.isNotBlank(sendRes)) {
				String code =XmlUtils.getXmlNoteValue(sendRes, "response/error/text()");
				String msg = TestsdltUtils.getErrorMsg(code);
				// providerOrderId =sendResp.get("respid")!=null?sendResp.get("respid").toString():"";
//				<?xml version="1.0" encoding="UTF-8" standalone="yes"?><response><userid>18820036966</userid><orderid>1608170test22222</orderid><Porderid>75229901</Porderid><account>18660137268</account><state>0</state><starttime>2017-02-04 03:30:57</starttime><endtime>2017-02-04 03:30:57</endtime><error>0</error><userprice>3.0</userprice><gprs>30</gprs><area>0</area><effecttime>0</effecttime><validity>0</validity></response>
				if ("0".equals(code)) {
					result = new SendOrderResult(SendOrderStatus.WAIT_CALLBACK.status, null, providerOrderId);
				} else {
					result = new SendOrderResult(SendOrderStatus.FAILED.status, code + ":" + msg, providerOrderId);
				}
			} else {
				result = new SendOrderResult(SendOrderStatus.NEED_MANUAL.status, "充值返回为空,请找技术人员", providerOrderId);
			}
		} catch (Exception e) {
			result = new SendOrderResult(SendOrderStatus.NEED_MANUAL.status, "系统异常,请找技术人员", providerOrderId);
			FlowSendLogUtils.sendExceptionLog(log, order_orderId, e);
		}
		return result;
	}
//	<?xml version="1.0" encoding="UTF-8"?>
//	-<response>
//	<userid> xxx</userid> //用户编号
//	<Porderid>xxx</Porderid> //山东联通平台订单号
//	<orderid> xxx</orderid> //用户订单号
//	<account>xxx</account> //需要充值的手机号码
//	<face> xxx</face> //充值面值
//	<amount>1</amount> //购买数量
//	<starttime>xxx</starttime>//开始时间
//	<state> xxx</state> //订单状态
//	<endtime>xxx</endtime>//结束时间
//	<error> xxx</error> //错误提示（详见附录3.2）
//	</response>
	
//	8	等待扣款
//	0	充值中
//	1	充值成功
//	2	充值失败
	@SuppressWarnings("all")
	@Override
	public Map<String, SendOrderResult> querys(List<SendOrderInfo> sendOrderInfos) {
		Map<String, SendOrderResult> map = new HashMap<>();
		SendOrderResult result = null;
		for (SendOrderInfo sendOrderInfo : sendOrderInfos) {
			String order_orderId = sendOrderInfo.getOrderId();// AA订单id
			String order_providerOrderId = sendOrderInfo.getSupplierOrderId();// 供应商流水号
			try {
				String queryUrl = Constants.getString("testsdlt_queryUrl").trim();
				String queryReq = TestsdltUtils.getQueryReq(sendOrderInfo);
				FlowQueryLogUtils.queryReqLog(log, order_orderId, queryReq);
//				String queryResp = HttpUtils.sendPost(queryUrl, queryReq, "application/json");
				// String queryResp = HttpUtils.sendPostByNameValue(queryUrl, queryReq);
				 String queryResp = HttpUtils.sendGet(queryUrl+"?"+queryReq,"utf-8");
				FlowQueryLogUtils.queryResLog(log, order_orderId, queryResp);
				if (StringUtils.isNotBlank(queryResp)) {
					String code = XmlUtils.getXmlNoteValue(queryResp, "response/state/text()");
					String msg = XmlUtils.getXmlNoteValue(queryResp, "response/error/text()");
					if("0".equals(msg)){
						if ("1".equals(code)) {
							result = new SendOrderResult(SendOrderStatus.SUCCESS.status, null, order_providerOrderId);
						} else if ("0".equals(code)||"8".equals(code)) {
							result = new SendOrderResult(SendOrderStatus.WAIT_CALLBACK.status, null, order_providerOrderId);
						} else if ("2".equals(code)) {
							result = new SendOrderResult(SendOrderStatus.FAILED.status, code + ":" + msg, order_providerOrderId);
						} else {
							result = new SendOrderResult(SendOrderStatus.NEED_MANUAL.status, "查询返回异常:" + code + ":" + msg, order_providerOrderId);
						}
					}else {
						result = new SendOrderResult(SendOrderStatus.NEED_MANUAL.status, "查询返回异常:" + code + ":" + msg, order_providerOrderId);
					}
				} else {
					result = new SendOrderResult(SendOrderStatus.NEED_MANUAL.status, "查询返回为空", order_providerOrderId);
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
