package com.xinxing.transfer.service;

import com.xinxing.transfer.po.TransferOrdre;

public interface TransferOrdreService {

	/**
	 * 获取订单信息  通过
	 * @param who 谁的订单id  
	 * @param downId  订单Id
	 * @return 返回订单实体 ,需要校验是否为null
	 */
	TransferOrdre getOrderInfo(String who, String id);

	/**
	 * 添加订单
	 * @param transferOrdre  订单实体
	 */
	void addOredr(TransferOrdre transferOrdre);

	/**
	 * 更新订单状态
	 * @param updateOrderInfo  订单实体
	 * @return 是否更新成功
	 */
	boolean updateOrder(String who,TransferOrdre transferOrdre);

	

}
