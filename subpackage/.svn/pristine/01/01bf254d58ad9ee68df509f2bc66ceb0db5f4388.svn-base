package com.xinxing.subpackage.core.api.tools;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.xinxing.subpackage.core.erorr.CheckSignException;
import com.xinxing.subpackage.core.po.OrderInfo;
import com.xinxing.subpackage.data.common.encry.MD5_HexUtil;
import com.xinxing.subpackage.data.common.http.HttpUtils;
import com.xinxing.subpackage.data.common.resource.Constants;
import com.xinxing.subpackage.data.service.PackOrderService;

public class ApiUtils {
	private static Logger log = Logger.getLogger("ApiUtils");
	/**
	 * 取参并校验数据
	 * 
	 * @param request
	 * @return
	 * @throws CheckSignException
	 */
	public static OrderInfo sendCheckout(HttpServletRequest request) throws CheckSignException {
		OrderInfo info=null;
		String reqPostString = HttpUtils.getReqPostString(request, log);// UTF-8

		HashMap<String, Object> reqMap = JSON.parseObject(reqPostString, new TypeReference<HashMap<String, Object>>() {
		});

		Object ORDERID = reqMap.get("orderId");
		Object PHONE = reqMap.get("phone");
		Object PRODUCTINFO = reqMap.get("productInfo");
		Object SIGN1 = reqMap.get("SIGN");
		reqMap.remove("SIGN");

		if (ORDERID != null && PHONE != null && PRODUCTINFO != null && SIGN1 != null) {
			String orderId = (String) ORDERID;
			String phone = (String) PHONE;
			String productInfo = (String) PRODUCTINFO;
			String SIGN = (String) SIGN1;

			String param = HttpUtils.getStrByMapOrderByABC(reqMap) + Constants.getString("pack_SignKey");

			// sign
			String sign = MD5_HexUtil.md5Hex(param);
			if (!SIGN.equals(sign)) {
				throw new CheckSignException();
			}
			info = new OrderInfo(productInfo,phone, orderId+"F1", productInfo.split(",")[0],orderId );
		}


		return info;
	}
	
	@Autowired
	private static PackOrderService packOrderService;
	/**
	 * 取参并校验数据
	 * 
	 * @param request
	 * @return
	 * @throws CheckSignException 
	 */
	public static OrderInfo queryCheckout(HttpServletRequest request){
		OrderInfo info=null;
		String reqPostString = HttpUtils.getReqPostString(request, log);// UTF-8

		HashMap<String, Object> reqMap = JSON.parseObject(reqPostString, new TypeReference<HashMap<String, Object>>() {
		});
		
		Object ORDERID = reqMap.get("orderId");

		if (ORDERID != null) {
			String orderId = (String) ORDERID;
			info = new OrderInfo(null,null, null, null, orderId);
		}


		return info;
	}

}
