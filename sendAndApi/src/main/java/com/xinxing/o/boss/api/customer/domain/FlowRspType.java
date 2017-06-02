package com.xinxing.o.boss.api.customer.domain;

public enum FlowRspType {
	DEFAULT(0),
	/**
	 * 错误信息
	 */
	ERROR(1),	
	OBJECT(3),
	STRING(4),
	;
	public int type;

	private FlowRspType(int type) {
		this.type = type;
	}
	
}
