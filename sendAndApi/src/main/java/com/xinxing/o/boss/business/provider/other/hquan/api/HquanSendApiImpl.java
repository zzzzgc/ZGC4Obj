package com.xinxing.o.boss.business.provider.other.hquan.api;

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
import com.xinxing.o.boss.business.provider.other.hquan.util.HquanUtils;
import com.xinxing.o.boss.common.http.HttpUtils;
import com.xinxing.o.boss.common.phone.PhoneUtils;
import com.xinxing.o.boss.common.resource.Constants;
import com.xinxing.o.boss.common.util.FlowQueryLogUtils;
import com.xinxing.o.boss.common.util.FlowSendLogUtils;
import com.xinxing.o.boss.common.util.FlowUtils;

public class HquanSendApiImpl implements SendApi {
    private static Logger log = Logger.getLogger(HquanSendApiImpl.class);
    @Autowired
	private com.xinxing.boss.interaction.service.order.a flowOrderService;
	@Autowired
	private com.xinxing.boss.interaction.service.common.a flowCommonService;
    
	/*public static void main(String[] args) {
		SendOrderInfo sendOrderInfo = PhoneUtils.createSendOrderInfo("orderId", "supplierCode,30", "supplierOrderId", "phone");
		HquanSendApiImpl hquan = new HquanSendApiImpl();
		SendOrderResult result = hquan.send(sendOrderInfo);
		System.out.println(result);
	}*/
    
    /* 响应示例：{ "code":0, "desc":"success","data":""}   */
	@Override
	public SendOrderResult send(SendOrderInfo sendOrderInfo) {
		SendOrderResult result = null;
		String orderId = sendOrderInfo.getOrderId();
		String systemOrderId = FlowUtils.getOrderId(orderId);
	
		//根据系统订单获取我方的订单信息
		OrderInfo orderInfo = flowOrderService.j(Integer.parseInt(systemOrderId));
		ProductCategoryInfo categoryInfo = null;
		if (orderInfo != null) {
			categoryInfo = flowCommonService.g(orderInfo.getProviderCategoryId());
		}
		// area 0本省 1全国
		int area = categoryInfo.getArea();
		String categoryName = categoryInfo.getCategoryName();
		
		try {
			String sendUrl = Constants.getString("hquan_sendUrl");
			String sendStr = HquanUtils.getSendStr(sendOrderInfo,area,categoryName);
			//获取订单发送日志
			FlowSendLogUtils.sendReqLog(log, orderId,sendUrl+","+sendStr);
			//发送get请求后获取返回信息
			String jsonStr = HttpUtils.sendGet(sendUrl+"?"+sendStr,"UTF-8");
			//获取订单返回日志
			FlowSendLogUtils.sendResLog(log, orderId, jsonStr);
			
			if (StringUtils.isNotBlank(jsonStr)) {
				JSONObject obj = JSON.parseObject(jsonStr);
				String code = obj.getString("code");
				String desc = obj.getString("desc");
				if ("0".equals(code)) {
					result = new SendOrderResult(SendOrderStatus.WAIT_CALLBACK.status,null,orderId);
				}else if ("4".equals(code)) { //余额不足转人工
					result = new SendOrderResult(SendOrderStatus.NEED_MANUAL.status,HquanUtils.getErrorMsg(code),orderId);
				}else {
					result = new SendOrderResult(SendOrderStatus.FAILED.status,desc,orderId);
				}
			}else { //文档建议read time out或其它网络异常时，使用查询功能
				result = new SendOrderResult(SendOrderStatus.WAIT_CALLBACK.status,"订单充值响应为空",orderId);
			}	
		} catch (Exception e) {
				result = new SendOrderResult(SendOrderStatus.NEED_MANUAL.status, "系统异常,请找技术人员", orderId);
				FlowSendLogUtils.sendExceptionLog(log,orderId, e);
		}
		return result;
	}
	/* 响应示例：
		{
		    "code": 0, 
		    "desc": "success", 
		    "data": {
				        "orderId": "128", 
				        "customerOrderId": "201702101420303000001", 
				        "phoneNo": "18513941330", 
				        "spec": 10, 
				        "province": "北京市", 
				        "scope": "nation", 
				        "mo": "ChinaNet", 
				        "callbackUrl": "http://127.0.0.1/callback", 
				        "officialPrice": 300, 
				        "actualPrice": 240, 
				        "discount": 0.8, 
				        "createdTime": "2017-02-10 15:33:19", 
				        "status": "success", 
				        "updateTime": "2017-02-10 15:25:00"
		             }
		 }   */
	@Override
	public Map<String, SendOrderResult> querys(List<SendOrderInfo> sendOrderInfos) {
		Map<String,SendOrderResult> resultMap = new HashMap<>();
		SendOrderResult result = null;
		for (SendOrderInfo sendOrderInfo : sendOrderInfos) {
			String orderId = sendOrderInfo.getOrderId();
			String systemOrderId = FlowUtils.getOrderId(orderId);
			//根据系统订单获取我方的订单信息
			OrderInfo orderInfo = flowOrderService.j(Integer.parseInt(systemOrderId));
			ProductCategoryInfo categoryInfo = null;
			if (orderInfo != null) {
				categoryInfo = flowCommonService.g(orderInfo.getProviderCategoryId());
			}
			// area 0本省 1全国
			int area = categoryInfo.getArea();
			String categoryName = categoryInfo.getCategoryName();
			
			try {
				String queryUrl = Constants.getString("hquan_queryUrl");
				String queryStr = HquanUtils.getQueryStr(sendOrderInfo,area,categoryName);

				//获取订单查询日志
				FlowQueryLogUtils.queryReqLog(log, orderId, queryUrl+","+queryStr);
				//发送get请求后获取返回信息
				String jsonStr = HttpUtils.sendGet(queryUrl+"?"+queryStr,"UTF-8");
				//获取订单返回日志
				FlowQueryLogUtils.queryResLog(log, orderId, jsonStr);
				
				if (StringUtils.isNotBlank(jsonStr)) {
					JSONObject obj = JSON.parseObject(jsonStr);
					String code = obj.getString("code");
					String desc = obj.getString("desc");
					if ("0".equals(code)) {
						String status = obj.getJSONObject("data").getString("status");
						String providerOrderId = obj.getJSONObject("data").getString("orderId");
						switch (status) {
						case "success":
							result = new SendOrderResult(SendOrderStatus.SUCCESS.status,null,orderId);
							break;
						case "fail":
							result = new SendOrderResult(SendOrderStatus.FAILED.status,"充值失败",orderId);
							break;
						case "init":
						default:
							result = new SendOrderResult(SendOrderStatus.WAIT_CALLBACK.status,null,orderId);
							break;
						}
						
					}else if ("4".equals(code)) { //余额不足转手工
						result = new SendOrderResult(SendOrderStatus.NEED_MANUAL.status,HquanUtils.getErrorMsg(code),orderId);
					}else {
						result = new SendOrderResult(SendOrderStatus.WAIT_CALLBACK.status,HquanUtils.getErrorMsg(code),orderId);
					}
				}else {
					result = new SendOrderResult(SendOrderStatus.WAIT_CALLBACK.status,null,orderId);
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
