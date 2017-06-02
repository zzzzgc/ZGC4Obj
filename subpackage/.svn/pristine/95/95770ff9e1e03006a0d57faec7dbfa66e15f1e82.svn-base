package com.xinxing.subpackage.core.erorr;

public class DIYException extends Exception {
	/**
	 * 自定义异常类
	 */
	private static final long serialVersionUID = 5370873603528692679L;
	
	private String msg =null;
	private int coed =0;
	
	/**
	 * 实例化对象时初始化Message返回信息 ,格式( 错误代码,信息 )
	 * @param msg 错误消息
	 * @param coed  错误代码
	 */
	public DIYException(int coed,String msg) {
		this.coed=coed;
		this.msg=msg;
	}

	@Override
	public String getMessage() {
		return coed+","+msg;
	}
}
