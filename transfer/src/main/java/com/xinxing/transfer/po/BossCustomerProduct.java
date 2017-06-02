package com.xinxing.transfer.po;

import java.math.BigDecimal;

public class BossCustomerProduct {
    private Integer id;

    private Integer customerid;

    private Integer categoryid;

    private String productname;

    private BigDecimal sellprice;

    private Integer status;

    private String mobileoperator;

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

    public Integer getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(Integer categoryid) {
        this.categoryid = categoryid;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname == null ? null : productname.trim();
    }

    public BigDecimal getSellprice() {
        return sellprice;
    }

    public void setSellprice(BigDecimal sellprice) {
        this.sellprice = sellprice;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMobileoperator() {
        return mobileoperator;
    }

    public void setMobileoperator(String mobileoperator) {
        this.mobileoperator = mobileoperator == null ? null : mobileoperator.trim();
    }
}