package com.xinxing.o.boss.business.provider.other.kdou.api;

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
import com.xinxing.o.boss.business.provider.other.kdou.util.KdouUtils;
import com.xinxing.o.boss.common.http.HttpUtils;
import com.xinxing.o.boss.common.phone.PhoneUtils;
import com.xinxing.o.boss.common.resource.Constants;
import com.xinxing.o.boss.common.util.FlowQueryLogUtils;
import com.xinxing.o.boss.common.util.FlowSendLogUtils;

public class KdouSendApiImpl implements SendApi {
	public static Logger log = Logger.getLogger(KdouSendApiImpl.class);
	/*提交流量充值请求返回数据
	 返回参数  {"result":1,"msg":"\u8bf7\u6c42\u6210\u529f","sid":"KDSnhFcEzs1469088607"}
	参数名称		 描述
	result	   查询代码，1为成功，其他为失败，
	msg		   结果描述
	sid		   请求成功后，平台生成的订单号，用于追踪，和商户的订单号不同
	*/
	@Override
	public SendOrderResult send(SendOrderInfo sendOrderInfo) {
		SendOrderResult result = null;
		String orderid = sendOrderInfo.getOrderId();
		try {
			String sendStr = KdouUtils.getSendStr(sendOrderInfo);
			String sendUrl = Constants.getString("kdou_sendUrl");
			//获取订单发送日志
			FlowSendLogUtils.sendReqLog(log, orderid, sendStr);
			//发送get请求后获取返回信息
			String jsonStr = HttpUtils.sendGet(sendUrl+"?"+sendStr,"UTF-8");
			//获取订单返回日志
			FlowSendLogUtils.sendResLog(log, orderid, jsonStr);
			if (StringUtils.isNotBlank(jsonStr)) {
				JSONObject obj = JSON.parseObject(jsonStr);
				String provider_result = obj.getString("result");
				String msg = obj.getString("msg");
				String theirOrderId = obj.getString("sid");
				switch (provider_result) {
				case "1":
					result = new SendOrderResult(SendOrderStatus.WAIT_CALLBACK.status,null,theirOrderId);
					break;
				case "-9": //余额不足转人工
					result = new SendOrderResult(SendOrderStatus.NEED_MANUAL.status,msg,theirOrderId);
					break;
				default:
					result = new SendOrderResult(SendOrderStatus.FAILED .status,msg,theirOrderId);
					break;
				}
			}else {
				result = new SendOrderResult(SendOrderStatus.NEED_MANUAL.status,"订单返回为空",orderid);
			}	
		} catch (Exception e) {
				result = new SendOrderResult(SendOrderStatus.NEED_MANUAL.status, "系统异常,请找技术人员", orderid);
				FlowSendLogUtils.sendExceptionLog(log, sendOrderInfo.getOrderId(), e);
		}
		return result;
		
	}

	/*提交订单查询请求返回数据
	 示例： {“result":1,"phone":"13800000000","oid":"orderid","code":200}
	 参数名称	描述
	result	查询代码，1为成功，其他为失败，
	phone	充值号码
	oid		商户订单号
	code	充值结果，100：充值中，200：成功，400：失败
	*/
	@Override
	public Map<String, SendOrderResult> querys(List<SendOrderInfo> sendOrderInfos) {
		Map<String,SendOrderResult> resultMap = new HashMap<>();
		SendOrderResult result = null;
		for (SendOrderInfo sendOrderInfo : sendOrderInfos) {
			String orderId = sendOrderInfo.getOrderId();
			String theirOrderId = sendOrderInfo.getSupplierOrderId();
			try {
				String queryStr = KdouUtils.getQueryStr(sendOrderInfo);
				String queryUrl = Constants.getString("kdou_queryUrl");
				//获取订单查询日志
				FlowQueryLogUtils.queryReqLog(log, orderId, queryUrl+","+queryStr);
				String jsonStr = HttpUtils.sendGet(queryUrl+"?"+queryStr,"UTF-8");
				//获取订单返回日志
				FlowQueryLogUtils.queryResLog(log, orderId, jsonStr);
				if (StringUtils.isNotBlank(jsonStr)) {
					JSONObject obj = JSON.parseObject(jsonStr);
					int provider_result = obj.getIntValue("result");
					int code = obj.getIntValue("code");
					if (provider_result == 1) {
						switch (code) {
							case 100:
								result = new SendOrderResult(SendOrderStatus.WAIT_CALLBACK.status,null,theirOrderId);
								break;
							case 200:
								result = new SendOrderResult(SendOrderStatus.SUCCESS.status,null,theirOrderId);
								break;
							case 400:
								result = new SendOrderResult(SendOrderStatus.FAILED.status,"code:"+code,theirOrderId);
								break;
						}
					}else if (provider_result == -9) {
						result = new SendOrderResult(SendOrderStatus.NEED_MANUAL.status,"余额不足",null);
					}else {
						result = new SendOrderResult(SendOrderStatus.WAIT_CALLBACK.status,"result:"+provider_result,theirOrderId);
					}
				}else {
					result = new SendOrderResult(SendOrderStatus.WAIT_CALLBACK .status,"查询返回为空",theirOrderId);
				}
			} catch (Exception e) {
				result= new SendOrderResult(SendOrderStatus.NEED_MANUAL.status, "系统异常,请找技术人员", theirOrderId);
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
