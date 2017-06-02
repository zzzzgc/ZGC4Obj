package com.xinxing.transfer.po;

import java.util.Date;

public class TransferOrdre {
    private Integer id;

    private String downid;

    private String supplierid;

    private String phone;

    private Integer status;

    private String typeflow;

    private String callbackaddress;

    private String callbackdata;

    private Date callbacktime;

    private Date starttime;

    private Date endtime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDownid() {
        return downid;
    }

    public void setDownid(String downid) {
        this.downid = downid == null ? null : downid.trim();
    }

    public String getSupplierid() {
        return supplierid;
    }

    public void setSupplierid(String supplierid) {
        this.supplierid = supplierid == null ? null : supplierid.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getTypeflow() {
        return typeflow;
    }

    public void setTypeflow(String typeflow) {
        this.typeflow = typeflow == null ? null : typeflow.trim();
    }

    public String getCallbackaddress() {
        return callbackaddress;
    }

    public void setCallbackaddress(String callbackaddress) {
        this.callbackaddress = callbackaddress == null ? null : callbackaddress.trim();
    }

    public String getCallbackdata() {
        return callbackdata;
    }

    public void setCallbackdata(String callbackdata) {
        this.callbackdata = callbackdata == null ? null : callbackdata.trim();
    }

    public Date getCallbacktime() {
        return callbacktime;
    }

    public void setCallbacktime(Date callbacktime) {
        this.callbacktime = callbacktime;
    }

    public Date getStarttime() {
        return starttime;
    }

    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }
}