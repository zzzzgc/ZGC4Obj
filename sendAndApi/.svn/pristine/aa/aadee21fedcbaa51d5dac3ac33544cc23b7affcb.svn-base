package com.xinxing.o.boss.business.provider.other.bigbosscx.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.xinxing.boss.business.api.domain.SendOrderInfo;
import com.xinxing.o.boss.common.encry.MD5_HexUtil;
import com.xinxing.o.boss.common.json.JsonUtils;
import com.xinxing.o.boss.common.resource.Constants;

public class BigbosscxUtils {

	public static String getSendStr(SendOrderInfo orderInfo) {
		Map<String, Object> map = new HashMap<>();
		String accountVal = orderInfo.getPhone();
		int merchant = Integer.parseInt(Constants.getString("bigbosscx_merchant"));
		int clientId = Integer.parseInt(Constants.getString("bigbosscx_clientId"));
		int product = Integer.parseInt(orderInfo.getSupplierCode());
		String outTradeNo = orderInfo.getOrderId();
		String version = "V100";
		Long ts = System.currentTimeMillis();

		map.put("accountVal", accountVal);
		map.put("merchant", merchant);
		map.put("clientId", clientId);
		map.put("product", product);
		map.put("outTradeNo", outTradeNo);
		map.put("version", version);
		map.put("ts", ts);
		map.put("sign", MD5_HexUtil.md5Hex(getSign(map)).toLowerCase());

		return JsonUtils.obj2Json(map);
	}

	public static String getQueryStr(SendOrderInfo sendOrderInfo){
		Map<String, Object> map = new HashMap<>();
		int merchant = Integer.parseInt(Constants.getString("bigbosscx_merchant"));
		int clientId = Integer.parseInt(Constants.getString("bigbosscx_clientId"));
		String outTradeNo = sendOrderInfo.getOrderId();
		String version = "V100";
		Long ts = System.currentTimeMillis();
		
		map.put("merchant", merchant);
		map.put("clientId", clientId);
		map.put("outTradeNo", outTradeNo);
		map.put("version", version);
		map.put("ts", ts);
		map.put("sign", MD5_HexUtil.md5Hex(getSign(map)).toLowerCase());
		
		return JsonUtils.obj2Json(map);
	}
	
	
	public static String getSign(Map<String, Object> map) {

		Set<String> keys = map.keySet();
		List<String> list = new ArrayList<String>(keys);
		Collections.sort(list);

		StringBuffer sb = new StringBuffer();

		for (String key : list) {
			sb.append(key + map.get(key));
		}
		sb.append(Constants.getString("bigbosscx_key"));
		return sb.toString();

	}
}
