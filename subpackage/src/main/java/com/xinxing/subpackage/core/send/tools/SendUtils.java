package com.xinxing.subpackage.core.send.tools;

import java.util.Map;

import com.xinxing.subpackage.data.common.encry.MD5_HexUtil;
import com.xinxing.subpackage.data.common.resource.Constants;

public class SendUtils {

	private static String MerChant = Constants.getString("pack_CustomerId");
	//private static String MerChant = "600012";
	
	/**
	 * 
	 * @param sendOrderInfo
	 * @return 提单请求Map
	 */
	public static Map<String, String> getSendParam(Map<String, String> map) {
		String Product = map.get("Product");
		String Mobile = map.get("Mobile");
		String FlowKey = map.get("FlowKey");
		map.clear();
		map.put("MerChant", MerChant);
		map.put("Product", Product);
		map.put("Mobile", Mobile);
		map.put("Action", "SendOrder");
		map.put("Version", "V1.0");
		map.put("FlowKey", FlowKey);
		String sign = "{\"Action\":\"SendOrder\",\"Version\":\"V1.0\"," + "\"MerChant\":\"" + MerChant
				+ "\",\"Product\":\"" + Product + "\"," + "\"Mobile\":\"" + Mobile
				+ "\",\"FlowKey\":\"" + FlowKey + "\"}";;
		map.clear();
		map.put("sendParam", sign);
		sign = MD5_HexUtil.md5Hex(sign).toUpperCase();
		map.put("sign", sign);
		return map;
	}
	/**
	 * 
	 * @param sendOrderInfo
	 * @return 查询请求Map
	 */
	public static Map<String, String> getQueryParam(Map<String, String> map) {
		String Mobile = map.get("Mobile");
		String FlowKey = map.get("FlowKey");
		map.clear();
		map.put("MerChant", MerChant);
		map.put("Action", "GetCallBack");
		map.put("Version", "V1.0");
		map.put("Mobile", Mobile);
		map.put("FlowKey", FlowKey);
		String sign = "{\"Action\":\"GetCallBack\",\"Version\":\"V1.0\"," + "\"MerChant\":\""
				+ MerChant + "\",\"Mobile\":\"" + Mobile + "\"," + "\"FlowKey\":\"" + FlowKey
				+ "\"}";
		map.clear();
		map.put("queryParam", sign);
		sign = MD5_HexUtil.md5Hex(sign).toUpperCase();
		map.put("sign", sign);
		return map;
	}

}
