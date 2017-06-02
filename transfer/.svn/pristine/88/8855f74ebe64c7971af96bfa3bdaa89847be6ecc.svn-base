package com.xinxing.flow.log;

import org.apache.log4j.Logger;

public interface LogNorm {
	/**
	 * 请求日志
	 */
	public void requestLog(String orderId,String param,String who,Logger log);
	
	/**
	 * 响应日志
	 */
	public void responseLog(String orderId,String param,String who,Logger log);
	
	/**
	 * 结果状态
	 * @param param 订单id
	 * @param status 订单状态
	 * @param msg 订单消息
	 */
	public void resultLog(String orderId,String status,String msg,String who,Logger log);
	
	/**
	 * 异常日志
	 */
	public void ExceptionLog(String param,String orderId,String who,Logger log);
}
