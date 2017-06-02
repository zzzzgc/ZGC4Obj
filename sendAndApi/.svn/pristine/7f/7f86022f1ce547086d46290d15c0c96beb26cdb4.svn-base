package com.xinxing.o.boss.business.provider.other.mhan.utils;

import java.util.HashMap;
import java.util.Map;

import com.xinxing.boss.business.api.domain.SendOrderInfo;
import com.xinxing.o.boss.business.provider.other.mhan.api.MHANSendApiImpl;
import com.xinxing.o.boss.common.encry.MD5_HexUtil;
import com.xinxing.o.boss.common.http.HttpUtils;
import com.xinxing.o.boss.common.resource.Constants;

public class MHANUtils {
	
	/**
	 * 获取请求体中的参数
	 * @param sendOrderInfo
	 * @return
	 */
	public static String getSendReq(SendOrderInfo sendOrderInfo) {
		String phone = sendOrderInfo.getPhone();
		String product_Id = sendOrderInfo.getSupplierCode();
		String callback_url = Constants.getString("MHAN_callback");
		
		Map<String, Object> map =new HashMap<>();
		
		map.put("phone_number", phone);
		map.put("product_id", product_Id); // 产品id
		map.put("notify_url", callback_url);
		
		String resParam = HttpUtils.getStrByMapOrderByABC(map);
		return resParam;
	}
	public static Map<String, String> getSendHead(SendOrderInfo sendOrderInfo) {
		Map<String, String> map = new HashMap<>();

		String key = Constants.getString("MHAN_key");

		String user_name = Constants.getString("MHAN_userName");
		String call_name = "OrderCreate";
		String timestamp = System.currentTimeMillis() / 1000L + "";
		String signature = MD5_HexUtil.md5Hex(timestamp + key);

		map.put("API-USER-NAME", user_name);
		map.put("API-NAME", call_name);
		map.put("API-TIMESTAMP", timestamp);
		map.put("API-SIGNATURE", signature);
		map.put("Content-Type", "application/x-www-form-urlencoded");

		return map;
	}

	public static Map<String, String> getQueryHead(SendOrderInfo sendOrderInfo) {
		Map<String, String> map = new HashMap<>();

		String key = Constants.getString("MHAN_key");

		String user_name = Constants.getString("MHAN_userName");
		String call_name = "OrderQuery";
		String timestamp = System.currentTimeMillis() / 1000L + "";
		String signature = MD5_HexUtil.md5Hex(timestamp + key);

		map.put("API-USER-NAME", user_name);
		map.put("API-NAME", call_name);
		map.put("API-TIMESTAMP", timestamp);
		map.put("API-SIGNATURE", signature);
		map.put("Content-Type", "application/x-www-form-urlencoded");

		return map;
	}

	/**
	 * 获取查询参数
	 * @param sendOrderInfo
	 * @return
	 * @throws Exception
	 */
	public static String getQueryReq(SendOrderInfo sendOrderInfo) throws Exception {
		String order_orderId = sendOrderInfo.getOrderId(); // aaId
		String supplise_id = sendOrderInfo.getSupplierOrderId(); // BBid

		String order_Id = MHANSendApiImpl.isOurId ? order_orderId : supplise_id;

		Map<String, Object> map =new HashMap<>();

		map.put("order_number", order_Id);

		String resParam = HttpUtils.getStrByMapOrderByABC(map);
		return resParam;
	}

}
