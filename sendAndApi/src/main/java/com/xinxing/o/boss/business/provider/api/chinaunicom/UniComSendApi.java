package com.xinxing.o.boss.business.provider.api.chinaunicom;

import com.xinxing.boss.business.api.domain.SendOrderInfo;
import com.xinxing.boss.business.api.domain.SendOrderResult;

public interface UniComSendApi {
	/**
	 * 中国联通订单发送总Api入口
	 */
	public SendOrderResult send(SendOrderInfo sendOrderInfo);
	
	/**
	 * 查询订单API总入口
	 * @param sendOrderInfo
	 * @return
	 */
	public SendOrderResult query(SendOrderInfo sendOrderInfo);
}
