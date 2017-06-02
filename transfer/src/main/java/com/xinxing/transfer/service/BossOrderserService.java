package com.xinxing.transfer.service;

import com.xinxing.transfer.po.BossOrder;

public interface BossOrderserService {

	/**
	 * 根据订单Id获取订单信息
	 * @param downid 谁的Id
	 * @param customerid Id
	 * @return 订单信息
	 */
	BossOrder selectOrder(String downid, String key);

}
