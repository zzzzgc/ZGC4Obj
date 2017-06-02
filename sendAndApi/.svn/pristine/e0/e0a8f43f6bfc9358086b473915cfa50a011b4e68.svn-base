package com.xinxing.o.boss.business.provider.other.xcheng.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xinxing.boss.business.api.domain.SendOrderInfo;
import com.xinxing.boss.business.api.domain.SendOrderResult;
import com.xinxing.boss.business.api.domain.SendOrderStatus;
import com.xinxing.boss.interaction.pojo.common.ProductCategoryInfo;
import com.xinxing.boss.interaction.pojo.customer.OrderInfo;
import com.xinxing.o.boss.business.provider.api.supplier.api.SendApi;
import com.xinxing.o.boss.business.provider.other.cyue.api.CYUESendApiImpl;
import com.xinxing.o.boss.business.provider.other.cyue.util.CYUEUtil;
import com.xinxing.o.boss.business.provider.other.josy.util.JOSYUtils;
import com.xinxing.o.boss.business.provider.other.lliu.util.LliuUtils;
import com.xinxing.o.boss.business.provider.other.xcheng.util.XChengUtil;
import com.xinxing.o.boss.common.http.HttpUtils;
import com.xinxing.o.boss.common.resource.Constants;
import com.xinxing.o.boss.common.util.FlowQueryLogUtils;
import com.xinxing.o.boss.common.util.FlowSendLogUtils;
import com.xinxing.o.boss.common.util.FlowUtils;

public class XChengSendApiImpl implements SendApi {
	private static Logger log = Logger.getLogger(XChengSendApiImpl.class);

	/*返回的数据样例
	 {  "status":"1",
		"resultCode":"00000",
		"resultDesc":"成功",    (该属性描述失败原因，status=0时必填)
	 	"data":{
				  "orderId":"10000000000001",
				  "channelOrderId":"60000000000001"   (成功的时候必填)
				}
	  }  
	 */
	@Override
	public SendOrderResult send(SendOrderInfo sendOrderInfo) {
		SendOrderResult result = null;
		String orderId = sendOrderInfo.getOrderId();
		String sendUrl = Constants.getString("xcheng_sendUrl");
		try {
			String sendStr = XChengUtil.getSendStr(sendOrderInfo);
			//获取订单发送日志
			FlowSendLogUtils.sendReqLog(log, orderId, sendStr);
			//发送订单
			String getResp = HttpUtils.sendPost1(sendUrl, sendStr, "application/json", "utf-8");
			//获取订单返回日志
			FlowSendLogUtils.sendResLog(log, orderId, getResp);
			
			if (StringUtils.isNotBlank(getResp)) {
				JSONObject obj = JSON.parseObject(getResp);
				String resultCode = obj.getString("resultCode");
				String msg = obj.getString("resultDesc");
				if ("00000".equals(resultCode)) {
					String theirOrderId = obj.getJSONObject("data").getString("channelOrderId");
					result = new SendOrderResult(SendOrderStatus.WAIT_CALLBACK.status,null,theirOrderId);
				}else if ("11004".equals(resultCode)) { //余额不足转手工
					result = new SendOrderResult(SendOrderStatus.NEED_MANUAL.status,msg,orderId);
				}else {
					result = new SendOrderResult(SendOrderStatus.FAILED.status,msg,orderId);
				}
			}else {
				result = new SendOrderResult(SendOrderStatus.NEED_MANUAL.status,"充值响应结果为空",orderId);
			}
		} catch (Exception e) {
			result = new SendOrderResult(SendOrderStatus.NEED_MANUAL.status,"系统异常,请找技术人员",orderId);
			FlowSendLogUtils.sendExceptionLog(log, sendOrderInfo.getOrderId(), e);
		}
		return result;
	}
	
	/*查询返回数据样例
	 * {"data":
			{"channel":"移动",
			 "channelOrderId":"1705251001330000005",
			 "city":"441900","orderId":"170525B0B154138",
			 "orderStatus":"0",
			 "orderTime":"2017-05-25 12:20:58",
			 "payAmount":"0",
			 "phoneNumber":"13622662311",
			 "prodId":"1400010",
			 "prodName":"中国移动10M流量包",
			 "prodSource":"1",
			 "province":"44",
			 "resultCode":"20001",
			 "resultDesc":"请求失败，没有与订购范围匹配的产品",
			 "validBeginDate":"",
			 "validBeginTime":"",
			 "validEndDate":"",
			 "validEndTime":""
			},
		"resultCode":"00000",
		"resultDesc":"请求成功",
		"status":"1"
	    }

	 */
	@Override
	public Map<String, SendOrderResult> querys(List<SendOrderInfo> sendOrderInfos) {
		Map<String,SendOrderResult> resultMap = new HashMap<>();
		SendOrderResult result = null;
		for (SendOrderInfo sendOrderInfo : sendOrderInfos) {
			String orderId = sendOrderInfo.getOrderId();
			String theirOrderId = sendOrderInfo.getSupplierOrderId();
			try {
				String queryStr = XChengUtil.getQueryStr(sendOrderInfo);
				String queryUrl = Constants.getString("xcheng_queryUrl");
				//获取订单查询 发送 日志
				FlowQueryLogUtils.queryReqLog(log, orderId, queryUrl+";"+queryStr);
				//查询订单
				String getResp = HttpUtils.sendPost1(queryUrl, queryStr,"application/json","utf-8");
				//获取订单查询返回 日志
				FlowQueryLogUtils.queryResLog(log, orderId, getResp);
				
				if (StringUtils.isNotBlank(getResp)) {
					JSONObject obj = JSON.parseObject(getResp);
					String resultCode = obj.getString("resultCode");
					String msg = obj.getString("resultDesc");
					if ("00000".equals(resultCode)) {
						String status = obj.getJSONObject("data").getString("orderStatus");
						//0订购失败    1订购成功   2订购中
						switch (status) {
						case "0":
							String failReason = obj.getJSONObject("data").getString("resultDesc");
							result = new SendOrderResult(SendOrderStatus.FAILED.status,failReason,theirOrderId);
							break;
						case "1":
							result = new SendOrderResult(SendOrderStatus.SUCCESS.status,null,theirOrderId);
							break;
						case "2":
							result = new SendOrderResult(SendOrderStatus.WAIT_CALLBACK.status,null,theirOrderId);
							break;
						default:
							result = new SendOrderResult(SendOrderStatus.WAIT_CALLBACK.status,null,theirOrderId);
							break;
						}
					}else if ("11004".equals(resultCode)) { //余额不足转人工
						result = new SendOrderResult(SendOrderStatus.NEED_MANUAL.status,msg,theirOrderId);
					}else {
						result = new SendOrderResult(SendOrderStatus.FAILED.status,msg,theirOrderId);
					}
				}else {
					result = new SendOrderResult(SendOrderStatus.WAIT_CALLBACK.status,"查询返回为空",theirOrderId);
				}
			} catch (Exception e) {
				result= new SendOrderResult(SendOrderStatus.NEED_MANUAL.status, "系统异常,请找技术人员",theirOrderId);
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
