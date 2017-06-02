package com.xinxing.o.boss.business.provider.other.mchuan.utils;

import java.util.HashMap;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xinxing.boss.business.api.domain.SendOrderInfo;
import com.xinxing.o.boss.common.http.HttpUtils;
import com.xinxing.o.boss.common.resource.Constants;

public class MCHUANUtils {
	public static String getSendReq(SendOrderInfo sendOrderInfo) {
		HashMap<String, Object> map = new HashMap<>();

		String[] split_produdtCode = sendOrderInfo.getSupplierCode().split(",");

		String action = "charge";// 固定
		String userid = Constants.getString("MCHUAN_userId");// :客户名
		String password = Constants.getString("MCHUAN_password");// :密码
		String phone = sendOrderInfo.getPhone();
		String mbytes = split_produdtCode[1]; // 流量充值
		String mytype = split_produdtCode[0];// 0全国漫游,1省内漫游

		map.put("action", action);
		map.put("userid", userid);
		map.put("password", password);
		map.put("phone", phone);
		map.put("mbytes", mbytes);
		map.put("mytype", mytype);

		String reqParam = HttpUtils.getStrByMapOrderByABC(map);

		return reqParam;
	}

	public static void main(String[] args) {
		String json = "" + "{\"ack\": \"success\"," + "\"message\": \"\"," + "\"order\": {"
				+ "\"charge_amount\": \"1.00\"," + "\"create_time\": \"2017-03-03 17:30:10\","
				+ "\"order_number\": \"MH42KES32MTSJ\"," + "\"phone_number\": \"15989030678\","
				+ "\"product_id\": \"5\"," + "\"refund_flag\": \"0\"," + "\"refund_flag_desc\": \"无退款\","
				+ "\"shipping_status\": \"4\"," + "\"shipping_status_desc\": \"已充值\","
				+ "\"shipping_status_message\": \"\"" + "}" + "}";
		JSONObject jsonObject = JSON.parseObject(json);

		System.out.println(jsonObject.getString("order"));

		String string = JSON.parseObject(jsonObject.getString("order")).getString("shipping_status_desc");

		System.out.println(string);

	}

	public static String getQueryReq(SendOrderInfo sendOrderInfo) {

		HashMap<String, Object> map = new HashMap<>();
		String action = "querystatus";// 固定
		String userid = Constants.getString("MCHUAN_userId");// :客户名
		String password = Constants.getString("MCHUAN_password");// :密码
		String taskid = sendOrderInfo.getSupplierOrderId(); // bbId

		map.put("action", action);
		map.put("userid", userid);
		map.put("password", password);
		map.put("taskid", taskid);

		String reqParam = HttpUtils.getStrByMapOrderByABC(map);

		return reqParam;
	}

}
