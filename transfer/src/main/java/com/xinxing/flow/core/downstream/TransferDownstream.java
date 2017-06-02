package com.xinxing.flow.core.downstream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xinxing.flow.erorr.RepeatOrdersException;
/**
 * 下游接口
 * @author ZGC
 *
 */
public interface TransferDownstream {
	
	/**
	 * 下游提交订单
	 * AA->BB
	 * @param request
	 * @param response
	 */
	public void sendOrder(HttpServletRequest request,HttpServletResponse response);

	/**
	 * 下游查询订单
	 * AA->BB
	 * @param request
	 * @param response
	 * @throws RepeatOrdersException 
	 */
	public void queryOrder(HttpServletRequest request,HttpServletResponse response);


	/**
	 * 下游查询资金
	 * @param request
	 * @param response
	 */
	/*public void download(HttpServletRequest request, HttpServletResponse response);
*/
	/**
	 * 余额查询
	 * @param request
	 * @param response
	 */
	public void queryBalance(HttpServletRequest request, HttpServletResponse response);
	

}
