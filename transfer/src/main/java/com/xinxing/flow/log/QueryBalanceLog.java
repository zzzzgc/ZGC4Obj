package com.xinxing.flow.log;

import java.util.Map;

import org.apache.log4j.Logger;

public class QueryBalanceLog {

	public static void requestLog(String param, String customer, Logger log) {
		log.info("收到" + customer + "余额查询>> " + param + "");
	}

	public static void responseLog(String param, String customer, Logger log) {
		log.info("反馈" + customer + "余额查询>> " + param + "");
	}

	public static void resultLog(String status, String msg, String customer, Logger log) {
		log.info("" + customer + "余额查询概要>> status:" + status + ",msg:" + msg);
	}

	public static void ExceptionLog(String param, String customer, Logger log, String msg) {
		log.info("" + customer + "余额查询异常:" + param + ",msg:" + msg);
	}

	public static String upError(Map<String, Object> queryMap, String resKeyValue, String CTMID,
			Logger log, Exception e) {
		String error = e.getMessage();
		log.error(e.getMessage() + e);
		ExceptionLog("下游->" + queryMap + ",上游->" + resKeyValue, CTMID, log, error);
		return error;
	}

}
