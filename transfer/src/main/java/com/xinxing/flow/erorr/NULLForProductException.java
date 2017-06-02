package com.xinxing.flow.erorr;

import com.xinxing.flow.core.downstream.status.CQ_Status;

/**
 * 订单不存在或商户(下游)未开通产品
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
		return CQ_Status.NULLForProduct.status;
	}

}
