package com.xinxing.subpackage.core.erorr;

import com.xinxing.subpackage.core.status.OrderStatus;

/**
 * 有参数为�?或参数有误异�?
 * @author ZGC
 */
//@SuppressWarnings("all")
public class NULLParametersException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5370873603528692679L;

	@Override
	public String getMessage() {
		return OrderStatus.NULLForProduct.status;
	}

}
