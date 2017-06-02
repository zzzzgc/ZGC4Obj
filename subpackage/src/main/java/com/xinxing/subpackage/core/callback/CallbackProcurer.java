package com.xinxing.subpackage.core.callback;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.xinxing.subpackage.core.po.OrderResult;
import com.xinxing.subpackage.data.common.encry.MD5_HexUtil;
import com.xinxing.subpackage.data.common.http.HttpUtils;
import com.xinxing.subpackage.data.common.resource.Constants;
import com.xinxing.subpackage.data.service.OrderService;
import com.xinxing.subpackage.data.service.PackOrderService;

public class CallbackProcurer {
	private static Logger log=Logger.getLogger("init Pack->callback out");
	
	@Autowired
	OrderService orderService;
	@Autowired
	PackOrderService packOrderService;
	
	/**
	 * YG callback methods
	 * @param sendOrderResult
	 */
	public void CallbackYG(OrderResult result) {
		//log.info("回调原包(原包出口)");
		String orderId = result.getOrderId();
		String sendOrderId = result.getSendOrderId();
		String status = result.getStatus();
		String phone = result.getPhone();
		String msg = result.getFailReason();
		String key = Constants.getString("pack_CustomerKey");

		String sign = "{\"phone\":\"" + phone + "\",\"orderId\":\"" + orderId + "\",\"status\":\"" + status + "\",\"key\":\"" + key + "\",\"msg\":\"" + msg + "\"}";
		String SIGN = MD5_HexUtil.md5Hex(sign);
		String json = "{\"phone\":\"" + phone + "\",\"orderId\":\"" + orderId + "\",\"status\":\"" + status + "\",\"key\":\"" + key + "\",\"msg\":\"" + msg + "\",\"SIGN\":\"" + SIGN + "\"}";

		String callbackUrl = Constants.getString("pack_callbackUrl");
		
		log.info("回调YG 参数:"+json);
		String sendPost = HttpUtils.sendPost(callbackUrl, json, "application/json");
		log.info("回调YG YG返回参数:"+sendPost);
		
		//是否收到
		if ("OK".equals(sendPost)) {
			log.info("callback the order "+orderId+" SUCCESS!");
		}else{
			log.info("callback the order "+orderId+" FAILURE");
		}
		
		//TODO 如果没收到就   改数据库 加时间调度并校验出没回调的订单 回调它    直到收到为止
		
		//修改数据库
		if(orderService.updateCallback(orderId,status, json, status)&&packOrderService.updateCallbackOrderInfo(sendOrderId, status)){
			log.info("Save the callback order "+orderId+" SUCCESS!");
		}else{
			log.info("Save the callback order "+orderId+" FAILURE!");
		}
		
	}

}
