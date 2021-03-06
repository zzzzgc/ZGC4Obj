package com.xinxing.o.boss.business.provider.other.qws.util;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.junit.Test;

import com.xinxing.boss.business.api.domain.SendOrderInfo;
import com.xinxing.o.boss.business.provider.other.qws.api.QwsSendApiImpl;
import com.xinxing.o.boss.common.encry.MD5_HexUtil;
import com.xinxing.o.boss.common.http.HttpUtils;
import com.xinxing.o.boss.common.resource.Constants;
import com.xinxing.o.boss.common.time.TimeUtils;

public class QwsUtil {
	private static Logger log = Logger.getLogger(QwsSendApiImpl.class);

	public static String getSendStr(SendOrderInfo sendOrderInfo){
		Map<String, Object> map = new HashMap<>();
		String supplierCode = sendOrderInfo.getSupplierCode();//供货商产品编码
		
		//上游所需字段
		String customerOrderId = sendOrderInfo.getOrderId();//客户订单号
		String enterpriseCode = Constants.getString("qws_enterpriseCode");//企业代码
		String productCode = supplierCode; //产品代码
		String mobile = sendOrderInfo.getPhone(); //手机号
		String orderTime = TimeUtils.getNowTimeFormate(); //yyyyMMddHHmmss格式时间戳
		
		map.put("enterpriseCode",enterpriseCode);
		map.put("productCode",productCode);
		map.put("mobile",mobile);
		map.put("orderTime",orderTime);
		map.put("sign",MD5_HexUtil.md5Hex(getSign(map,"send")));
		
		map.put("customerOrderId",customerOrderId);

		return HttpUtils.getStrByMapOrderByABC(map);
	}

	public static String getQueryStr(SendOrderInfo sendOrderInfo){
		Map<String, Object> map = new HashMap<>();
		String supplierOrderId = sendOrderInfo.getSupplierOrderId();
		
		String enterpriseCode = Constants.getString("qws_enterpriseCode");//企业代码
	//	String orderId = supplierOrderId;				     //供货商订单号
		String customerOrderId = sendOrderInfo.getOrderId(); //客户订单号
		
		map.put("enterpriseCode",enterpriseCode);
	//	map.put("orderId",orderId);
		map.put("sign",MD5_HexUtil.md5Hex(getSign(map,"query")));
		
		map.put("customerOrderId",customerOrderId);
		
		return HttpUtils.getStrByMapOrderByABC(map);
		
	}
	
	public static String getSign(Map<String, Object> map,String mark){
		String password	= Constants.getString("qws_password");
		StringBuilder sb = new StringBuilder();
		
		sb.append("enterpriseCode"+"="+map.get("enterpriseCode")+"&");
		if ("send".equals(mark)) {
			sb.append("productCode"+"="+map.get("productCode")+"&");
			sb.append("mobile"+"="+map.get("mobile")+"&");
			sb.append("orderTime"+"="+map.get("orderTime")+"&");
		}else {
			sb.append("orderId=&");
		}
		sb.append("password"+"="+password);
		return sb.toString();
	}

	/**
	 * 获取错误提示代码的信息
	 * @param codes
	 * @return
	 */
	public static String getErrorMsg(String code){
		Map<String,String> map = new HashMap<>();
		return map.get(code)==null?"":map.get(code);
	}
	
	@Test
	public void testName() throws Exception {
		String test = "%E8%AE%A2%E8%B4%AD%E6%88%90%E5%8A%9F";
		String result =URLDecoder.decode(test);
		System.out.println(result);
		String string = "sign=e7b2905cb6f42b61dea40f46a422081f&reason=666&status=1&customerOrderId=170516B0B436989&orderId=PO20170516175609726238";
		Map<String,String> map = HttpUtils.getReqParams(string);
		System.out.println(map.get("reason"));
		if (StringUtils.isNotBlank(map.get("reason"))) {
			String reason = URLDecoder.decode(map.get("reason"),"utf-8");
		}

		
	}
}
