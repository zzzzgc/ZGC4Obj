package com.xinxing.transfer.common.enums;

public enum CodeAndReasonType {
	
	SUCCESS("0000",""),
	SIZE_NULL("1000","面值为空"),
	PRODUCT_ERROR("1001","请输入正确的产品编码"),
	STOCK_NULL("1002","提卡失败"),//库存不足，返回对方为提卡失败
	PRODUCT_SIZE_ERROR("1003","面值与产品不符"),
	ORDER_ID_REPEAT("1004","订单号不允许重复"),
	ORDER_NOT_FIND("1005","没有查到该订单的充值记录，可重新提交"),//用于充值时异常，对方无法确定我们是否充值的情况下
	SYSTEM_ERROR("1006","系统错误"),
	MONEY_NOT_ENOUGH("1007","余额不足"),
	PARAM_ERROR("1008","参数错误"),
	USER_ERROR("1009","该用户还未开通"),
	IP_ERROR("1010","鉴权失败"),
	ORDER_NULL("1011","订单号不允许为空"),
	SIGN_ERROR("1012","加密错误");
	
	public String code;
	public  String msg;
	
	private CodeAndReasonType(String code,String msg){
		this.code=code;
		this.msg=msg;
	}
	
}
