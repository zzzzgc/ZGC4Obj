package com.xinxing.o.boss.api.customer.domain;

import org.apache.commons.lang.StringUtils;


public enum FlowReqType {
	GET_TOKEN("GetToken"),
	/**
	 * 获取手机可充值产品
	 */
	GET_MOBILE_PRODUCT("GetMobileProduct"),
	GET_CALLBACK("GetCallBack"),
	SEND_ORDER("SendOrder"),
	GET_BALANCE("GetBalance"),
	GET_PRODUCT("GetView"),//就是获取用户产品，兼容.net版本信息
	;
	public String type;
		
	private FlowReqType(String type) {
		this.type = type;
	}
	
	/**
	 * 获取请求类型
	 * @param action
	 * @return
	 */
	public static FlowReqType getFlowType(String action){
		for(FlowReqType type:FlowReqType.values()){
			if(StringUtils.equals(type.type, action)){
				return type;
			}
		}
		return null;
	}
	
}
