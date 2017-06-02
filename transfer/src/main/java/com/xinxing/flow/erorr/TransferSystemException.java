package com.xinxing.flow.erorr;

import com.xinxing.flow.core.downstream.status.CQ_Status;

/**
 * 系统异常
 * @author ZGC
 *
 */
//@SuppressWarnings("all")
public class TransferSystemException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7320930364516218827L;
	
	@Override
	public String getMessage() {
		return CQ_Status.TransferSystemException.status;
	}

}
