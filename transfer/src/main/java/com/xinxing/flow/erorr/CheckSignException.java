package com.xinxing.flow.erorr;

import com.xinxing.flow.status.OrderStatus;

/**
 * SIGN签名校验不匹配
 * @author ZGC
 *
 */
//@SuppressWarnings("all")
public class CheckSignException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5370873603528692679L;

	@Override
	public String getMessage() {
		return OrderStatus.CheckSignException.status;
	}

}
