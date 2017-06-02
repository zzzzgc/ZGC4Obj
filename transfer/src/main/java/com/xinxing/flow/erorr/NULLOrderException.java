package com.xinxing.flow.erorr;

/**
 * 没有该订单
 * @author ZGC
 *
 */
//@SuppressWarnings("all")
public class NULLOrderException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5089588670422987049L;

	/**
	 * 
	 */
	
	@Override
	public String getMessage() {
		return "HuaYitransfer 中没有该订单";
	}

}
