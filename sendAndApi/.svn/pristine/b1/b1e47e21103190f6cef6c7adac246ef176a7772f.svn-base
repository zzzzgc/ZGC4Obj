package com.xinxing.o.boss.api.customer.domain;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 兼容.net版本返回信息
 * @author 
 *
 */
public class FlowRspResult {
	
	@JSONField(name="Type")
	private int type;
	@JSONField(name="Msg")
	private String msg;
	@JSONField(name="Code")
	private String code;
	@JSONField(name="Data")
	private Object data;
	
	public FlowRspResult() {
	}
	
	public FlowRspResult(int type, String msg, String code, Object data) {
		this.type = type;
		this.msg = msg;
		this.code = code;
		this.data = data;
	}

	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
}
