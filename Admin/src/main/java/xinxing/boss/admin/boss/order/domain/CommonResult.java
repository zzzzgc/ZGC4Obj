package xinxing.boss.admin.boss.order.domain;

import com.alibaba.fastjson.annotation.JSONField;

public class CommonResult {
	
/*	private ResultType _Type=ResultType.Default;
	
	public ResultType Type{
		
	}*/
	
	@JSONField(name="Type")
	private int type;
	@JSONField(name="Msg")
	private String msg;
	@JSONField(name="Code")
	private String code;
	@JSONField(name="Data")
	private Object data;
	
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
