package com.xinxing.flow.status;

public enum OrderStatus {
	/**
	 * 提单成功
	 */
	SEND_SUCCESS("3"),
	/**
	 * 订单失败
	 */
	NOTHING("221"),
	/**
	 * 查询订单成功
	 */
	OREDR_SUCCESS("801"),
	/**
	 * 查询,订单等待
	 */
	QUERY_WAIT("2"),
	/**
	 * 查询,订单成功
	 */
	QUERY_SUCCESS("1"),
	/**
	 * 查询,订单失败
	 */
	QUERY_ERROR("0"),
	/**
	 * 产品不支持
	 */
	NULLForProduct("103"),
	/**
	 * 系统异常
	 */
	NULLResponse("999"),
	/**
	 * 参数为空
	 */
	NULLResponseException("104"),
	/**
	 * 签名校验失败
	 */
	CheckSignException("108"),
	/**
	 * 订单不存在
	 */
	NULLOrderIdException("101"),
	/**
	 * 回调状态 成功
	 */
	 Callback_SUCCESS("1"),
	 /**
	  * 回调状态 失败
	  */
	 Callback_ERROR("0"),
	 /**
	  * 订单不存在
	  */
	 RepeatOrdersException("219")
	;
	
	public String status;
	private OrderStatus(String status){
		this.status=status;
	}

}
