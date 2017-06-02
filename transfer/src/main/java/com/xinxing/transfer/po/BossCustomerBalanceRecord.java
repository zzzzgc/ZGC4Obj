package com.xinxing.transfer.po;

import java.math.BigDecimal;
import java.util.Date;

public class BossCustomerBalanceRecord {
    private Integer id;

    private Integer customerid;

    private BigDecimal fundbalance;

    private Integer costtype;

    private String remark;

    private BigDecimal cost;

    private Date recordtime;

    private BigDecimal price;

    private String phone;

    private String orderkey;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCustomerid() {
        return customerid;
    }

    public void setCustomerid(Integer customerid) {
        this.customerid = customerid;
    }

    public BigDecimal getFundbalance() {
        return fundbalance;
    }

    public void setFundbalance(BigDecimal fundbalance) {
        this.fundbalance = fundbalance;
    }

    public Integer getCosttype() {
        return costtype;
    }

    public void setCosttype(Integer costtype) {
        this.costtype = costtype;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public Date getRecordtime() {
        return recordtime;
    }

    public void setRecordtime(Date recordtime) {
        this.recordtime = recordtime;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getOrderkey() {
        return orderkey;
    }

    public void setOrderkey(String orderkey) {
        this.orderkey = orderkey == null ? null : orderkey.trim();
    }

	@Override
	public String toString() {
		return "BossCustomerBalanceRecord [id=" + id + ", customerid=" + customerid + ", fundbalance=" + fundbalance
				+ ", costtype=" + costtype + ", remark=" + remark + ", cost=" + cost + ", recordtime=" + recordtime
				+ ", price=" + price + ", phone=" + phone + ", orderkey=" + orderkey + "]";
	}
    
    
}