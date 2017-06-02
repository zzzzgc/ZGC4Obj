package com.xinxing.subpackage.core.status;
/**
 * 订单异常
 * @author Administrator
 */
public enum OrderStatus {
	/**
	 * (分包)成功 ---分包订单成功
	 */
	PACKSUCCEED("1"),
	/**
	 * 失败 ---订单失败
	 */
	FAILURE("2"),
	/**
	 * 等待 ---订单提交成功
	 */
	WAIT("3"),
	/**
	 * (原包)成功 ---所有分包都成功了,或分包成功
	 */
	SUCCEED("11"),
	/**
	 * 参数有误
	 */
	NULLForProduct("103,参数有误"),
	/**
	 * 其他异常
	 */
	SystemException("999,其他异常"),
	/**
	 * 没响应异常
	 */
	NULLResponseException("104,没响应异常"),
	/**
	 * 签名校验失败异常
	 */
	CheckSignException("108,签名校验失败异常"),
	/**
	 * 没有该订单异常
	 */
	NULLOrderIdException("101,没有该订单异常"),
	/**
	 * 订单重复异常
	 */
	RepeatOrdersException("219,订单重复异常");

	public String status;
	private OrderStatus(String status) {
		this.status = status;
	}

}
