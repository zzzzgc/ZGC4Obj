package com.xinxing.subpackage.core.po;

import com.xinxing.subpackage.core.halt.Halt;

public class OrderResult {

	/**
	 * 
	 */
	private String status;
	private String failReason;
	// 分包订单id
	private String sendOrderId;
	// 原始订单id
	private String orderId;
	private String phone;
	
	
	
	public OrderResult(){}
	public OrderResult(String status, String failReason, String sendOrderId, String orderId, String phone) {
		this.status = status;
		this.failReason = failReason;
		this.sendOrderId = sendOrderId;
		this.orderId = orderId;
		this.phone = phone;
		//订单状态记录,叫停模块
		Halt.addStatus(sendOrderId, Integer.parseInt(status));
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getFailReason() {
		return failReason;
	}
	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}
	public String getSendOrderId() {
		return sendOrderId;
	}
	public void setSendOrderId(String sendOrderId) {
		this.sendOrderId = sendOrderId;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
}
