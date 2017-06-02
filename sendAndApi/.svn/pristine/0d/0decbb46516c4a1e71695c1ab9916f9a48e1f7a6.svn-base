package com.xinxing.o.boss.business.provider.other.bigbosscxhf.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import com.xinxing.boss.business.api.domain.SendOrderInfo;
import com.xinxing.o.boss.common.encry.MD5_HexUtil;
import com.xinxing.o.boss.common.json.JsonUtils;
import com.xinxing.o.boss.common.resource.Constants;

public class BigbosscxhfUtils {
	public static String getSendStr(SendOrderInfo sendOrderInfo) {
		Map<String, Object> map = new HashMap<>();
		
		String accountVal = sendOrderInfo.getPhone(); //手机号码
		int merchant = Integer.parseInt(Constants.getString("bigbosscxhf_merchant")); //商户号
		int clientId = Integer.parseInt(Constants.getString("bigbosscxhf_clientId")); //服务接口编号
		int product = Integer.parseInt(sendOrderInfo.getSupplierCode()); //充值产品编号
		String outTradeNo = sendOrderInfo.getOrderId();  //商户本地订单号
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
		int merchant = Integer.parseInt(Constants.getString("bigbosscxhf_merchant")); //商户号
		int clientId = Integer.parseInt(Constants.getString("bigbosscxhf_clientId")); //服务接口编号
		String outTradeNo = sendOrderInfo.getOrderId();  //商户本地订单号
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
		sb.append(Constants.getString("bigbosscxhf_key"));
		return sb.toString();
	}
	
	/**
	 * 获取结果码信息
	 * @param codes
	 * @return
	 */
	public static String getErrorMsg(String code){
		Map<String,String> map = new HashMap<>();
		map.put("1000","验签不正确");
		map.put("1001","时间戳相差过大");
		map.put("1002","充值产品代码错误");
		map.put("1003","请求参数的商户号或者接口服务号错误");
		map.put("1004","系统充值繁忙");
		map.put("1005","充值请求IP非法");
		map.put("1006","手机号段错误，联系管理员添加号段信息");
		map.put("1007","请求版本号错误");
		map.put("1008","流水号重复/流水号超过最大位数 s");
		map.put("1009","余额不足");
		map.put("1010","订单不存在");
		return map.get(code)==null?"":map.get(code);
    }

	@Test
	public void testName() throws Exception {
		
	}
}
