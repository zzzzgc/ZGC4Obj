package com.xinxing.subpackage.core.erorr;

import com.xinxing.subpackage.core.status.OrderStatus;

/**
 * 订单不存在或商户(下游)未开通产�?
 * @author ZGC
 *
 */
//@SuppressWarnings("all")
public class NULLForProductException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7320930364516218827L;
	
	@Override
	public String getMessage() {
		return OrderStatus.NULLForProduct.status;
	}

}
