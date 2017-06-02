package com.xinxing.o.boss.business.provider.other.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.apache.log4j.Logger;

import com.xinxing.boss.business.api.domain.SendOrderInfo;
import com.xinxing.boss.business.api.domain.SendOrderResult;
import com.xinxing.boss.business.api.domain.SendOrderStatus;
import com.xinxing.o.boss.common.encry.MD5_HexUtil;
import com.xinxing.o.boss.common.json.JsonUtils;

public class ChenXinTestApiImpl implements ChenXinTestApi {
 
	private static final Logger log = Logger.getLogger(ChenXinTestApiImpl.class);

	@Override
	public SendOrderResult send(SendOrderInfo sendOrderInfo) {
		com.xinxing.flow.utils.resource.a.getString("boneCP.acquireIncrement");
		
		Map<String, Object> map = new HashMap<>();
		String accountVal = sendOrderInfo.getPhone();
		int merchant = 10151;
		int clientId = 10104;
		int product = sendOrderInfo.getProviderId(); //是不是产品id??
		String outTradeNo = System.currentTimeMillis()+"";
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
		Random random = new Random();   //???干嘛用的
		SendOrderResult result = null;
		//return JsonUtils.obj2Json(map);
		log.info("accountVal=" + sendOrderInfo.getPhone() + " merchant=10151+clientId=10104+product" + sendOrderInfo.getProviderId()+ "outTradeNo=" + System.currentTimeMillis()+""+"version=V100+ts="+ts+"sign"+MD5_HexUtil.md5Hex(getSign(map)).toLowerCase());
		if (random.nextInt() < 5) {
			result = new SendOrderResult(SendOrderStatus.WAIT_CALLBACK.status,
					null, null);
		} else {
			result = new SendOrderResult(SendOrderStatus.FAILED.status, "随机失败", null);
		}
		return result;
	}
	
	public static String getSign(Map<String, Object> map) {

		Set<String> keys = map.keySet();
		List<String> list = new ArrayList<String>(keys);
		Collections.sort(list);

		StringBuffer sb = new StringBuffer();

		for (String key : list) {
			sb.append(key + map.get(key));
		}
		sb.append("dvVAgnBzpoUILzm");
		return sb.toString();

	}
	@Override
	public Map<String, SendOrderResult> querys(List<SendOrderInfo> sendOrderInfo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SendOrderResult query(SendOrderInfo sendOrderInfo) {
		// TODO Auto-generated method stub
		return null;
	}
	

}
