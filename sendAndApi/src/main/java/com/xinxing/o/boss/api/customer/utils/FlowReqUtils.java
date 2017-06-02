package com.xinxing.o.boss.api.customer.utils;

import org.apache.commons.lang.StringUtils;

import com.xinxing.o.boss.common.encry.MD5_HexUtil;

public class FlowReqUtils {

	/**
	 * 校验Sign是否合法
	 * @param sign
	 * @param postData
	 * @return
	 */
	public static boolean isSignOk(String sign,String postData){
		String postDataSign = MD5_HexUtil.md5Hex(postData).toUpperCase();
		return StringUtils.equals(sign, postDataSign);
	}
	
}
