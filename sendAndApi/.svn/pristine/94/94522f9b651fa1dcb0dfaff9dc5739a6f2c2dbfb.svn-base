package com.xinxing.o.boss.business.provider.other.hzycb.api;

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
import com.xinxing.o.boss.business.provider.api.supplier.api.SendApi;
import com.xinxing.o.boss.common.encry.MD5_HexUtil;
import com.xinxing.o.boss.common.json.JsonUtils;

public class HzycbSendApiImpl implements SendApi {

	private static final Logger log = Logger.getLogger(HzycbSendApiImpl.class);

	@Override
	public SendOrderResult send(SendOrderInfo sendOrderInfo) {
		return null;
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
		return null;
	}

	@Override
	public SendOrderResult query(SendOrderInfo sendOrderInfo) {
		return null;
	}

}
