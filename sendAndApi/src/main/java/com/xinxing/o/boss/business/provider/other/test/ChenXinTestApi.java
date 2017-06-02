package com.xinxing.o.boss.business.provider.other.test;

import java.util.List;
import java.util.Map;

import com.xinxing.boss.business.api.domain.SendOrderInfo;
import com.xinxing.boss.business.api.domain.SendOrderResult;

public interface ChenXinTestApi {
	/**
	 * 发送订单信息
	 * @param sendOrderInfo
	 * @return
	 */
	public SendOrderResult send(SendOrderInfo sendOrderInfo);

	/**
	 * 查询多个订单信息-需要指定供货商
	 * @param sendOrderInfo
	 * @return
	 */
	public Map<String,SendOrderResult> querys(List<SendOrderInfo> sendOrderInfo);
	
	/**
	 * 查询单个订单状态
	 * @param sendOrderInfo
	 * @return
	 */
	public SendOrderResult query(SendOrderInfo sendOrderInfo);

}
