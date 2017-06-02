package com.xinxing.flow.core.downstream.status;

public enum CQ_Status {

	/**
	 * 提单,提单成功
	 */
	SEND_SUCCESS("1"),
	/**
	 * 订单失败
	 */
	NOTHING("221"),
	/**
	 * 提单,订单成功
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
	TransferSystemException("999"),
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
	 * 订单重复
	 */
	RepeatOrdersException("219");

	public String status;

	private CQ_Status(String status) {
		this.status = status;
	}

	public static void main(String[] args) {
		String status2 = CQ_Status.CheckSignException.status;
		System.out.println(status2);
	}
}
