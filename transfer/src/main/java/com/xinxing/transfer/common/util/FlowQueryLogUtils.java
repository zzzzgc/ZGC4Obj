package com.xinxing.transfer.common.util;

import org.apache.log4j.Logger;

public class FlowQueryLogUtils {

	/**
	 * 流量查询发送日志
	 * @param log
	 * @param orderId
	 * @param reqParam
	 */
	public static void queryReqLog(Logger log,String orderId,String reqParam){
		log.info("订单查询发送信息：" + orderId + "--" + reqParam);
	}
	
	/**
	 * 流量查询返回日志
	 * @param log
	 * @param orderId
	 * @param resContent
	 */
	public static void queryResLog(Logger log,String orderId,String resContent){
		log.info("订单查询返回信息：" + orderId + "--" + resContent.replace("/r/n", ""));
	}
	
	/**
	 * 流量查询异常日志
	 * @param log
	 * @param orderId
	 * @param e
	 */
	public static void queryExceptionLog(Logger log,String orderId,Exception e){
		log.info("订单号："+orderId+"订单查询出现异常：返回信息：" + e.getMessage() ,e);
		log.error("订单号："+orderId+"订单查询出现异常：返回信息：" + e.getMessage() ,e);
	}
}
