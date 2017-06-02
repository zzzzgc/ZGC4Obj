package com.xinxing.o.boss.business.provider.other.txin.api;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.xinxing.boss.business.api.domain.SendOrderInfo;
import com.xinxing.boss.business.api.domain.SendOrderResult;
import com.xinxing.boss.business.api.domain.SendOrderStatus;
import com.xinxing.boss.interaction.pojo.customer.OrderInfo;
import com.xinxing.o.boss.business.provider.api.supplier.api.SendApi;
import com.xinxing.o.boss.business.provider.other.txin.util.TXINUtils;
import com.xinxing.o.boss.common.http.HttpUtils;
import com.xinxing.o.boss.common.resource.Constants;
import com.xinxing.o.boss.common.util.FlowQueryLogUtils;
import com.xinxing.o.boss.common.util.FlowSendLogUtils;
import com.xinxing.o.boss.common.util.FlowUtils;
import com.xinxing.o.boss.common.xml.XmlUtils;

public class TXINSendApiImpl implements SendApi {
	private static Logger log = Logger.getLogger(TXINSendApiImpl.class);
	/*public static void main(String[] args) {
		SendOrderInfo sendOrderInfo = PhoneUtils.createSendOrderInfo("testId", "0,50", "providerId", "1500290852766");
		System.out.println(sendOrderInfo.toString());
		TXINSendApiImpl txin = new TXINSendApiImpl();
		SendOrderResult result = txin.send(sendOrderInfo);
		System.out.println(result.getOrderId());
	}*/
	@Autowired
	private com.xinxing.boss.interaction.service.order.a flowOrderService;
	/*提交订单成功返回数据
	 成功：
	<?xml version="1.0"?>
	<response>
		<result>true</result>
		<code>100</code>
		<msg>恭喜，提交成功</msg>
		<data>
			<sid>RO201312232232127348</sid>
		</data>
	</response>
	失败 ：
	<?xml version="1.0"?>
	<response>
		<result>false</result>
		<code>250</code>
		<msg>参数有误</msg>
	</response>
	*/
	
	@Override
	public SendOrderResult send(SendOrderInfo sendOrderInfo) {
		SendOrderResult result = null;
		String orderid = sendOrderInfo.getOrderId();
		try {
			String sendStr = TXINUtils.getSendStr(sendOrderInfo);
			String sendUrl = Constants.getString("txin_sendUrl").trim();
			System.out.println(sendUrl);
			//获取订单发送日 志
			FlowSendLogUtils.sendReqLog(log, orderid, sendStr);
			//发送get请求后获取返回信息
			String getResp = HttpUtils.sendGet(sendUrl+"?"+sendStr,"GBK");
			//获取订单返回日志
			FlowSendLogUtils.sendResLog(log, orderid, getResp);
			if (StringUtils.isNotBlank(getResp)) {
				String providerResult = XmlUtils.getXmlNoteValue(getResp, "response/result/text()");
				String code = XmlUtils.getXmlNoteValue(getResp, "response/code/text()");
				String msg = XmlUtils.getXmlNoteValue(getResp, "response/msg/text()");
				String theirOrderId = XmlUtils.getXmlNoteValue(getResp, "response/data/sid/text()");
				//订单失败判断条件，providerResult不为空且为false 或     code不为空且不等于100
				boolean mark = (StringUtils.isNotBlank(providerResult) &&"false".equals(providerResult)) 
								|| (StringUtils.isNotBlank(code)&&(!"100".equals(code)));
				if (("true".equals(providerResult) && "100".equals(code)) || "999".equals(code)) { //999未知异常暂时视为成功
					result = new SendOrderResult(SendOrderStatus.WAIT_CALLBACK.status,null,theirOrderId);
				}else if ("601".equals(code)) {  //扣款失败，也符合失败，应先于失败判断,转人工
					result = new SendOrderResult(SendOrderStatus.NEED_MANUAL.status,code+":"+msg,theirOrderId);
				}else if (mark) {
					result = new SendOrderResult(SendOrderStatus.FAILED.status,code+":"+msg,theirOrderId);
				}else {
					result = new SendOrderResult(SendOrderStatus.NEED_MANUAL.status,code+":"+msg,theirOrderId); 
				}
			}else { //请求超时，接口文档建议暂时视为成功,最后看回调或查询
				result = new SendOrderResult(SendOrderStatus.WAIT_CALLBACK.status,"提交订单返回为空",orderid); 
			}
		} catch (Exception e) {
			result = new SendOrderResult(SendOrderStatus.NEED_MANUAL.status, "系统异常,请找技术人员", orderid);
			FlowSendLogUtils.sendExceptionLog(log, sendOrderInfo.getOrderId(), e);
		}
		return result;
	}

	/*
	成功案例： 
		<?xml version="1.0"?>
		<response>
			<result>true</result>
			<code>100</code>
			<msg>恭喜，提交成功</msg>
			<data>
				<sid>RO201312230112334563</sid>
				<ste>0</ste>
				<cid>test</cid>
				<pid>YDHUN01001</pid>
				<oid>TOPUP10000013610</oid>
				<pn>13898789685</pn>
				<fm>30.00</fm>
				<ft>2014-01-15 09:44:55</ft>
			</data>
		</response>
	失败案例：
		<?xml version="1.0"?>
		<response>
			<result>false</result>
			<code>250</code>
			<msg>参数有误</msg>
		</response>
	*/
	@Override
	public Map<String, SendOrderResult> querys(List<SendOrderInfo> sendOrderInfos) {
		Map<String,SendOrderResult> resultMap = new HashMap<>();
		SendOrderResult result = null;
		for (SendOrderInfo sendOrderInfo : sendOrderInfos) {
			String orderId = sendOrderInfo.getOrderId();
			String supplierOrderId = sendOrderInfo.getSupplierOrderId();
			try {
				String queryStr = TXINUtils.getQueryStr(sendOrderInfo);
				String queryUrl = Constants.getString("txin_queryUrl").trim();
				FlowQueryLogUtils.queryReqLog(log, orderId, queryUrl+","+queryStr);
				String getResp = HttpUtils.sendGet(queryUrl+"?"+queryStr,"GBK");
				//获取订单返回日志
				FlowQueryLogUtils.queryResLog(log, orderId, getResp);
				if (StringUtils.isNotBlank(getResp)) {
					String msg = XmlUtils.getXmlNoteValue(getResp, "response/msg/text()");
					String ste = XmlUtils.getXmlNoteValue(getResp, "response/data/ste/text()");
					String code = XmlUtils.getXmlNoteValue(getResp, "response/code/text()");
					String queryTime = XmlUtils.getXmlNoteValue(getResp, "response/data/ft/text()");
					String ourOrderId =  XmlUtils.getXmlNoteValue(getResp, "response/data/oid/text()");
					String theirOrderId = XmlUtils.getXmlNoteValue(getResp, "response/data/sid/text()");
					String providerResult = XmlUtils.getXmlNoteValue(getResp, "response/result/text()");
					if ("100".equals(code) ||"101".equals(code) ||"102".equals(code) ||"103".equals(code)) {
						switch (ste) { 	//0  充值成功          1 or 2  充值中	  3  充值失败
						case "0":
							result = new SendOrderResult(SendOrderStatus.SUCCESS.status,null,theirOrderId);
							break;
						case "1":
							result = new SendOrderResult(SendOrderStatus.WAIT_CALLBACK.status,null,theirOrderId);
							break;
						case "2":
							result = new SendOrderResult(SendOrderStatus.WAIT_CALLBACK.status,null,theirOrderId);
							break;
						case "3":
							result = new SendOrderResult(SendOrderStatus.FAILED.status,"ste:"+ste+","+msg,theirOrderId);
							break;
						default:
							result = new SendOrderResult(SendOrderStatus.WAIT_CALLBACK.status,null,theirOrderId);
							break;
						}
					}else if("258".equals(code)){  //10分钟前 code=258  则继续查询，10分钟后  code=258  则为失败
						//根据我方id获取我方订单信息
						int myOrderId = Integer.parseInt(FlowUtils.getOrderId(ourOrderId));
						OrderInfo orderInfo = flowOrderService.j(myOrderId);
						
						long createOrderTime = orderInfo.getSubmitTime().getTime();
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						long queryOrderTime =  sdf.parse(queryTime).getTime();
						long ten = 610000L;   //10分钟的毫秒数    (订单生成时间稍微延迟于提交订单时发送给对方的时间，额外加10秒的余量)
						long tempTime = queryOrderTime -createOrderTime; //查询订单时间与提交订单时间相差的毫秒数
						
						if (tempTime < ten || tempTime == ten) {  
							result = new SendOrderResult(SendOrderStatus.WAIT_CALLBACK.status,null,theirOrderId);
						}else { 
							result = new SendOrderResult(SendOrderStatus.FAILED.status,code+":"+msg,theirOrderId);
						}
					}else if ("601".equals(code)) { //扣款失败
							result= new SendOrderResult(SendOrderStatus.NEED_MANUAL.status,code+":"+msg, theirOrderId);
					}else { //其他code值不处理
							result = new SendOrderResult(SendOrderStatus.WAIT_CALLBACK.status,code+":"+msg,theirOrderId);
					}
				}else {
					result = new SendOrderResult(SendOrderStatus.WAIT_CALLBACK.status,"查询返回为空",supplierOrderId);
				}
			} catch (Exception e) {
				result= new SendOrderResult(SendOrderStatus.NEED_MANUAL.status, "系统异常,请找技术人员",supplierOrderId);
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
