package com.xinxing.subpackage.data.po;

import java.util.Date;

public class SubpackageOrderSend extends SubpackageOrderSendKey {
    private Integer flowpacksize;

    private Integer whichpack;

    private Integer status;

    private Date starttime;

    private Date endtime;

    public Integer getFlowpacksize() {
        return flowpacksize;
    }

    public void setFlowpacksize(Integer flowpacksize) {
        this.flowpacksize = flowpacksize;
    }

    public Integer getWhichpack() {
        return whichpack;
    }

    public void setWhichpack(Integer whichpack) {
        this.whichpack = whichpack;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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