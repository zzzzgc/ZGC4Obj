package com.xinxing.subpackage.data.utils;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;
/**
 * 响应协议封装工具类
 * @author Administrator
 *
 */
public class ResponseUtils {
	/**
	 * 给YG回调的设置
	 * @param response 响应对象
	 * @param responseParam  响应参数
	 */
	public static void responseYGSet(HttpServletResponse response, String responseParam){
		PrintWriter out = null;
		try {
			response.setHeader("accept", "*/*");
			response.setContentType("application/json; charset=utf-8");
			response.setHeader("connection", "Keep-Alive");
			response.setHeader("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			out = response.getWriter();
			out.append(responseParam);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}

}
