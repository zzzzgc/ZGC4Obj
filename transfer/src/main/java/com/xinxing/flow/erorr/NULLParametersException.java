package com.xinxing.flow.erorr;

import com.xinxing.flow.core.downstream.status.CQ_Status;

/**
 * 有参数为空,或参数有误异常
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
		return CQ_Status.NULLResponseException.status;
	}

}
