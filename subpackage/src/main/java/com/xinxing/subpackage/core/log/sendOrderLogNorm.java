package com.xinxing.subpackage.core.log;

import java.util.Map;

import org.apache.log4j.Logger;

public class sendOrderLogNorm{

	public static void requestLog(String orderId,String param,String who,Logger log) {
		log.info("收到"+who+"请求订单:"+param+",订单ID"+orderId);
	}

	public static void responseLog(String orderId,String param,String who,Logger log) {
		log.info("反馈"+who+"请求订单:"+param+",订单ID"+orderId);
	}

	public static void resultLog(String orderId, String status, String msg,String who,Logger log) {
		log.info(""+who+"订单请求概要,订单ID:"+orderId+",status:"+status+",msg:"+msg);
	}

	public static void ExceptionLog(String param, String orderId,String who,Logger log) {
		log.info(""+who+"请求订单失败:"+param+",订单ID:"+orderId);
	}
	
	public static String upEorror(Map<String, Object> sendMap, String resKeyValue, String CTMORDID,Logger log, Exception e) {
		String erorr;
		erorr = e.getMessage();
		log.error(e.getMessage() + e);
		sendOrderLogNorm.ExceptionLog("下游->" + sendMap + ",上游->" + resKeyValue, CTMORDID, "CQ", log);
		return erorr;
	}

}
