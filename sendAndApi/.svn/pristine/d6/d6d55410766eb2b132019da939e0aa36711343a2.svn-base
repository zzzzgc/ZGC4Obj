package com.xinxing.o.boss.business.provider.other.dli.api;

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
import com.xinxing.o.boss.business.provider.other.dli.util.DLiUtil;
import com.xinxing.o.boss.common.http.HttpUtils;
import com.xinxing.o.boss.common.phone.PhoneUtils;
import com.xinxing.o.boss.common.resource.Constants;
import com.xinxing.o.boss.common.util.FlowQueryLogUtils;
import com.xinxing.o.boss.common.util.FlowSendLogUtils;

public class DLiSendApiImpl implements SendApi {
	private static Logger log = Logger.getLogger(DLiSendApiImpl.class);
	/*public static void main(String[] args) {
		SendOrderInfo sendOrderInfo = PhoneUtils.createSendOrderInfo("testId", "0,50", "providerId", "15002908527");
		DLiSendApiImpl dli = new DLiSendApiImpl();
		//SendOrderResult result = dli.send(sendOrderInfo);
		SendOrderResult query = dli.query(sendOrderInfo);
	}*/

	/*
	 * 发送订单返回参数
	 * 错误：  {"code":"5001","msg":"用户名不能为空" }
	 * 成功：  {"code":"2000","msg":"提交成功","order_number":"xxxx"}
	 */
	@Override
	public SendOrderResult send(SendOrderInfo sendOrderInfo) {
		SendOrderResult result = null;
		String orderId = sendOrderInfo.getOrderId();
		try {
			String sendStr = DLiUtil.getSendStr(sendOrderInfo);
			String sendUrl = Constants.getString("dli_sendUrl").trim();
			//获取订单发送日志
			FlowSendLogUtils.sendReqLog(log, orderId, sendStr);
			//发送get请求后获取返回信息
			String getResp = HttpUtils.sendGet(sendUrl+"?"+sendStr,"utf-8");
			//获取订单返回日志
			FlowSendLogUtils.sendResLog(log, orderId, getResp);
			if (StringUtils.isNotBlank(getResp)) {
				JSONObject obj = JSON.parseObject(getResp);
				String msg = obj.getString("msg");
				String code = obj.getString("code");
				
				if ("2000".equals(code)) {
					String theirOrderId = obj.getString("order_number");
					result = new SendOrderResult(SendOrderStatus.WAIT_CALLBACK.status,null,theirOrderId);
				}else if ("5202".equals(code)) {   //余额不足转手工
					result = new SendOrderResult(SendOrderStatus.NEED_MANUAL.status,msg,orderId);
				}else if ("5110".equals(code)) {
					result = new SendOrderResult(SendOrderStatus.NEED_MANUAL.status,msg,orderId);
				}else {
					result = new SendOrderResult(SendOrderStatus.FAILED.status,msg,orderId); 
				}
			}else { 
				result = new SendOrderResult(SendOrderStatus.WAIT_CALLBACK.status,"提交订单返回为空",orderId); 
			}
		} catch (Exception e) {
			result = new SendOrderResult(SendOrderStatus.NEED_MANUAL.status, "系统异常,请找技术人员", orderId);
			FlowSendLogUtils.sendExceptionLog(log,orderId, e);
		}
		return result;
	}

	/*
	 * 查询返回数据 (json格式)
	 *  code			状态码	String(5)	是	响应状态码
	    msg				结果描述	String(500)	是	响应结果描述
		data			数据集合	Object		是	
		user_ordernum	商户订单号	String(255)	是	唯一的订单号，商户提供
		order_number	平台订单号	String(255)	是	DLI平台生成的订单号
		order_status	充值状态	String(5)	是	0：未提交，1：充值中，2：已充值，-1：失败
		mobile			手机号码	String(18)	是	手机号码
		ctime			下单时间	Int			是	时间戳
		
		{"code":2000,"msg":"\u83b7\u53d6\u8ba2\u5355\u4fe1\u606f\u6210\u529f",
		 "data":{
			 	  "user_ordernum":"170519B0B154034",
			 	  "order_number":"BBECE2D84C8841AAA175BB890EBDE9E7",
			 	  "order_status":"1",
			 	  "mobile":"13622662311",
			 	  "ctime":"1495178924"
		 	     }
	    }
	 */
	@Override
	public Map<String, SendOrderResult> querys(List<SendOrderInfo> sendOrderInfos) {
		Map<String,SendOrderResult> resultMap = new HashMap<>();
		SendOrderResult result = null;
		for (SendOrderInfo sendOrderInfo : sendOrderInfos) {
			String orderId = sendOrderInfo.getOrderId();
			String supplierOrderId = sendOrderInfo.getSupplierOrderId();
			try {
				String queryStr = DLiUtil.getQueryStr(sendOrderInfo);
				String queryUrl = Constants.getString("dli_queryUrl").trim();
				//获取查询订单日志
				FlowQueryLogUtils.queryReqLog(log, orderId, queryUrl+","+queryStr);
				String getResp = HttpUtils.sendGet(queryUrl+"?"+queryStr,"utf-8");
				//获取查询返回日志
				FlowQueryLogUtils.queryResLog(log, orderId, getResp);
				if (StringUtils.isNotBlank(getResp)) {
					JSONObject obj = JSON.parseObject(getResp);
					String msg = obj.getString("msg");
					String code = obj.getString("code");
					if ("2000".equals(code)) {
						String status = obj.getJSONObject("data").getString("order_status");
						switch (status) {  // 0：未提交，1：充值中，2：已充值，-1：失败
						case "0":
							result = new SendOrderResult(SendOrderStatus.FAILED.status,"未提交订单",orderId);
							break;
						case "1":
							result = new SendOrderResult(SendOrderStatus.WAIT_CALLBACK.status,null,supplierOrderId);
							break;
						case "2":
							result = new SendOrderResult(SendOrderStatus.SUCCESS.status,null,supplierOrderId);
							break;
						case "-1":
							result = new SendOrderResult(SendOrderStatus.FAILED.status,"充值失败",supplierOrderId);
							break;
						default:
							result = new SendOrderResult(SendOrderStatus.WAIT_CALLBACK.status,null,supplierOrderId);
							break;
						}
					}else if ("5202".equals(code)) { //余额不足转手工
						result = new SendOrderResult(SendOrderStatus.NEED_MANUAL.status,msg,orderId);
					}else {
						result = new SendOrderResult(SendOrderStatus.FAILED.status,msg,supplierOrderId);
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
