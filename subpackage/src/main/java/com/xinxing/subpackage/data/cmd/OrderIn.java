package com.xinxing.subpackage.data.cmd;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface OrderIn {
	public void query(HttpServletRequest request, HttpServletResponse response);
	public void send(HttpServletRequest request, HttpServletResponse response);
}
