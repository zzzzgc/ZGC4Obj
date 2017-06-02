package com.xinxing.transfer.common.enums;

public enum StatusType {
	
	SUCCESS("4"),
	ERROR("1");
	
	public String opt;
	private StatusType(String opt){
		this.opt=opt;
	}
}
