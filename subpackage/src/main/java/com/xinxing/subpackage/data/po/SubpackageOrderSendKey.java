package com.xinxing.subpackage.data.po;

public class SubpackageOrderSendKey {
    private String orderid;

    private String sendorderid;

    public SubpackageOrderSendKey() {
		super();
	}

	public SubpackageOrderSendKey(String orderid, String sendorderid) {
		super();
		this.orderid = orderid;
		this.sendorderid = sendorderid;
	}

	public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid == null ? null : orderid.trim();
    }

    public String getSendorderid() {
        return sendorderid;
    }

    public void setSendorderid(String sendorderid) {
        this.sendorderid = sendorderid == null ? null : sendorderid.trim();
    }
}