package com.xinxing.subpackage.data.common.resource;

import org.apache.log4j.Logger;

public class LoggerPrint {

	public static void printLog(Logger logger,Exception e){
		logger.info(e.toString());
		StackTraceElement[] stackTraceElements = e.getStackTrace();
		for (int i = 0; i < stackTraceElements.length; i++) {
			logger.info(stackTraceElements[i].toString());
			logger.error(stackTraceElements[i].toString());
		}
	}
	public static void printLog(String header,Logger logger,Exception e){
		logger.info(header+e.toString());
		StackTraceElement[] stackTraceElements = e.getStackTrace();
		for (int i = 0; i < stackTraceElements.length; i++) {
			logger.info(header+stackTraceElements[i].toString());
			logger.error(header+stackTraceElements[i].toString());
		}
	}
}
