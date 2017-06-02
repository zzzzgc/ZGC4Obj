package com.xinxing.subpackage.core.erorr;

import com.xinxing.subpackage.core.status.OrderStatus;

/**
 * 上游未响应异�?
 * @author ZGC
 */
//@SuppressWarnings("all")
public class NULLResponseException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5370873603528692679L;

	@Override
	public String getMessage() {
		return OrderStatus.NULLResponseException.status;
	}

}
