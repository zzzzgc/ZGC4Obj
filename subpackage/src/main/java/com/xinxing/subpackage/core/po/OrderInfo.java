package com.xinxing.subpackage.core.po;

public class OrderInfo {

	/**
	 * 产品编码
	 */
	private String productInfo;
	/**
	 * 手机号码
	 */
	private String phone;
	/**
	 * 运营商
	 */
	private String model;
	/**
	 * 分包提单id
	 */
	private String sendOrderId;
	/**
	 * 分包产品编码
	 */
	private String sendProductInfo;
	/**
	 * 提单id
	 */
	private String orderId;
	
	public OrderInfo() {
	}

	public OrderInfo(String productInfo, String phone, String sendOrderId, String sendProductInfo, String orderId) {
		super();
		this.productInfo = productInfo;
		this.phone = phone;
		this.sendOrderId = sendOrderId;
		this.sendProductInfo = sendProductInfo;
		this.orderId = orderId;
	}

	public String getProductInfo() {
		return productInfo;
	}

	public void setProductInfo(String productInfo) {
		this.productInfo = productInfo;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getSendOrderId() {
		return sendOrderId;
	}

	public void setSendOrderId(String sendOrderId) {
		this.sendOrderId = sendOrderId;
	}

	public String getSendProductInfo() {
		return sendProductInfo;
	}

	public void setSendProductInfo(String sendProductInfo) {
		this.sendProductInfo = sendProductInfo;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	
	
	
	
	
}
