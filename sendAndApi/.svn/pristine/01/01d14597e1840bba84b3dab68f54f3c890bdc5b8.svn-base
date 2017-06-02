package com.xinxing.o.boss.business.provider.other.test;

import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.log4j.Logger;

import com.xinxing.boss.business.api.domain.SendOrderInfo;
import com.xinxing.boss.business.api.domain.SendOrderResult;
import com.xinxing.boss.business.api.domain.SendOrderStatus;

public class DemoTestYDApiImpl implements DemoTestYDApi {

	private static final Logger log = Logger.getLogger(DemoTestYDApiImpl.class);
	
	@Override
	public SendOrderResult send(SendOrderInfo sendOrderInfo) {
		String phone = sendOrderInfo.getPhone();
		String productcode = sendOrderInfo.getSupplierCode();
		String orderId = sendOrderInfo.getOrderId();
		Random random = new Random();
		SendOrderResult result = null;
		log.info("phone=" + phone + " productCode=" + productcode + " orderId=" + orderId);
		if (random.nextInt() < 5) {
			result = new SendOrderResult(SendOrderStatus.WAIT_CALLBACK.status,
					null, null);
		} else {
			result = new SendOrderResult(SendOrderStatus.FAILED.status, "随机失败", null);
		}
		return result;
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
