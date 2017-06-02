package com.xinxing.flow.log;

import org.apache.log4j.Logger;

public class CallBackOrderLogNorm {

	
	public static void requestLog(String orderId,String param,String who,Logger logger) {
		logger.info("收到"+who+"回调查询:"+param+"");
	}

	
	 public static void responseLog(String orderId,String param,String who,Logger log) {
		log.info("反馈"+who+"回调查询:"+param+"");
	}

	
	 public static void resultLog(String orderId, String status, String msg,String who,Logger log) {
		log.info(""+who+"回调查询概要,ID:"+orderId+",status:"+status+",msg:"+msg);
	}

	
	 public static void ExceptionLog(String param, String orderId,String who,Logger log) {
		log.info(""+who+"回调查询异常:"+param+",ID:"+orderId);
	}

}
