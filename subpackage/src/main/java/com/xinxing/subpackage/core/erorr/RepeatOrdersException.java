package com.xinxing.subpackage.core.erorr;

import com.xinxing.subpackage.core.status.OrderStatus;

/**
 * 重复提交的时�?
 * @author ZGC
 *
 */
//@SuppressWarnings("all")
public class RepeatOrdersException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7320930364516218827L;
	
	@Override
	public String getMessage() {
		return OrderStatus.RepeatOrdersException.status;
	}

}
