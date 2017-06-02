package com.xinxing.transfer.common.util;

import org.apache.log4j.Logger;

public class FlowSendLogUtils {

	/**
	 * 流量订购发送日志
	 * @param log
	 * @param orderId
	 * @param reqParam
	 */
	public static void sendReqLog(Logger log,String orderId,String reqParam){
		log.info("订单订购发送信息：" + orderId + "--" + reqParam);
	}
	
	/**
	 * 流量订购返回日志
	 * @param log
	 * @param orderId
	 * @param resContent
	 */
	public static void sendResLog(Logger log,String orderId,String resContent){
		log.info("订单订购返回信息：" + orderId + "--" + resContent);
	}
	
	/**
	 * 流量订购异常日志
	 * @param log
	 * @param orderId
	 * @param e
	 */
	public static void sendExceptionLog(Logger log,String orderId,Exception e){
		log.info("订单号："+orderId+"订单订购 出现异常：返回信息：" + e.getMessage() ,e);
		log.error("订单号："+orderId+"订单订购 出现异常：返回信息：" + e.getMessage() ,e);
	}
}
