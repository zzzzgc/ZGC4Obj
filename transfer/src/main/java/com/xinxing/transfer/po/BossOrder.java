package com.xinxing.transfer.po;

import java.math.BigDecimal;
import java.util.Date;

public class BossOrder {
    private Integer id;

    private Integer customerid;

    private String orderkey;

    private String phone;

    private Integer categoryid;

    private Integer providercategoryid;

    private Date submittime;

    private Integer status;

    private String failreason;

    private Date failtime;

    private Date successtime;

    private BigDecimal price;

    private BigDecimal cost;

    private Integer providerid;

    private Integer callbackstatus;

    private String province;

    private String city;

    private Integer issecondchannel;

    private Date receivetime;

    private Date callbacktime;

    private String operator;

    private String providerkey;

    private Integer chargecount;

    private Integer priority;

    private Integer isblack;

    private Integer handlestatus;

    private Byte searchnum;

    private Double weixinprice;

    private Integer biztype;

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

    public String getOrderkey() {
        return orderkey;
    }

    public void setOrderkey(String orderkey) {
        this.orderkey = orderkey == null ? null : orderkey.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public Integer getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(Integer categoryid) {
        this.categoryid = categoryid;
    }

    public Integer getProvidercategoryid() {
        return providercategoryid;
    }

    public void setProvidercategoryid(Integer providercategoryid) {
        this.providercategoryid = providercategoryid;
    }

    public Date getSubmittime() {
        return submittime;
    }

    public void setSubmittime(Date submittime) {
        this.submittime = submittime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getFailreason() {
        return failreason;
    }

    public void setFailreason(String failreason) {
        this.failreason = failreason == null ? null : failreason.trim();
    }

    public Date getFailtime() {
        return failtime;
    }

    public void setFailtime(Date failtime) {
        this.failtime = failtime;
    }

    public Date getSuccesstime() {
        return successtime;
    }

    public void setSuccesstime(Date successtime) {
        this.successtime = successtime;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public Integer getProviderid() {
        return providerid;
    }

    public void setProviderid(Integer providerid) {
        this.providerid = providerid;
    }

    public Integer getCallbackstatus() {
        return callbackstatus;
    }

    public void setCallbackstatus(Integer callbackstatus) {
        this.callbackstatus = callbackstatus;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public Integer getIssecondchannel() {
        return issecondchannel;
    }

    public void setIssecondchannel(Integer issecondchannel) {
        this.issecondchannel = issecondchannel;
    }

    public Date getReceivetime() {
        return receivetime;
    }

    public void setReceivetime(Date receivetime) {
        this.receivetime = receivetime;
    }

    public Date getCallbacktime() {
        return callbacktime;
    }

    public void setCallbacktime(Date callbacktime) {
        this.callbacktime = callbacktime;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator == null ? null : operator.trim();
    }

    public String getProviderkey() {
        return providerkey;
    }

    public void setProviderkey(String providerkey) {
        this.providerkey = providerkey == null ? null : providerkey.trim();
    }

    public Integer getChargecount() {
        return chargecount;
    }

    public void setChargecount(Integer chargecount) {
        this.chargecount = chargecount;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Integer getIsblack() {
        return isblack;
    }

    public void setIsblack(Integer isblack) {
        this.isblack = isblack;
    }

    public Integer getHandlestatus() {
        return handlestatus;
    }

    public void setHandlestatus(Integer handlestatus) {
        this.handlestatus = handlestatus;
    }

    public Byte getSearchnum() {
        return searchnum;
    }

    public void setSearchnum(Byte searchnum) {
        this.searchnum = searchnum;
    }

    public Double getWeixinprice() {
        return weixinprice;
    }

    public void setWeixinprice(Double weixinprice) {
        this.weixinprice = weixinprice;
    }

    public Integer getBiztype() {
        return biztype;
    }

    public void setBiztype(Integer biztype) {
        this.biztype = biztype;
    }
}