package com.xinxing.subpackage.core.erorr;

import com.xinxing.subpackage.core.status.OrderStatus;

/**
 * 不存在订�?
 * @author ZGC
 *
 */
//@SuppressWarnings("all")
public class NULLOrderIdException extends Exception {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8406076958432633319L;

	@Override
	public String getMessage() {
		return OrderStatus.NULLOrderIdException.status;
	}

}
