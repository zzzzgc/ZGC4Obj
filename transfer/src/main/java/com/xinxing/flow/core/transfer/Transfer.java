package com.xinxing.flow.core.transfer;

import java.util.HashMap;
import java.util.Map;

import com.xinxing.flow.erorr.NULLForProductException;
import com.xinxing.flow.erorr.NULLOrderIdException;

public interface Transfer {
	
	/**
	 * 通过上游给定的参数获取给手机充值的 优狗产品编号
	 * 
	 * @param pDTVALUE
	 *            下游充值面额
	 * @param uRPROVID
	 *            下游充值省份
	 * @param iSP
	 *            下游运营商 YD 移动 , DX 电信 , LT 联通
	 * @param rOAMING_TYPE
	 *            下游漫游类型 0 省内漫游 , 1 全国漫游
	 * @return 优狗产品编号
	 * @throws NULLForProductException
	 */
	String getProduct(String pDTVALUE, String uRPROVID, String iSP, String rOAMING_TYPE) throws NULLForProductException;
	
	/**
	 * 获取BB订单参数
	 * @param sendMap AA订单参数
	 * @return 可提交给YG的提单JSON字符串
	 * @throws NULLForProductException 
	 */
	String getSendParam(Map<String, Object> sendMap) throws NULLForProductException;

	/**
	 * 获取BB查询参数
	 * @param sendMap AA订单参数
	 * @return 可提交给YG的提单JSON字符串
	 * @throws NULLForProductException 
	 * @throws NULLOrderIdException 
	 */
	String getQueryParam(Map<String, Object> param) throws NULLForProductException, NULLOrderIdException;

	/**
	 * 获取余额查询参数
	 * @param map
	 * @return
	 * @throws NULLForProductException
	 * @throws NULLOrderIdException
	 */
	String getQueryBalanceParam(Map<String, Object> map);

}
