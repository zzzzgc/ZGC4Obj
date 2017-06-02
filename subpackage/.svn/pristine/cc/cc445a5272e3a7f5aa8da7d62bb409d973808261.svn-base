package com.xinxing.subpackage.data.service;

import com.xinxing.subpackage.data.po.SubpackageOrderApi;

public interface OrderService {
	
	/**
	 * C
	 * @param order
	 * @return
	 */
	 boolean addOrderInfo(SubpackageOrderApi order);
	 
	 /**
	  * R
	  * @param sendOrderId
	  * @return
	  */
	 String getOrderId(String sendOrderId);
	 
	 /**
	  * U
	  * @return
	  */
	 boolean updateOrderInfo(SubpackageOrderApi order);
	 
	 /**
	  * D
	  * @return
	  */
	 boolean deteleOrderInfo(String orderId);
	 
	 
	 
	 
	/**
	 *回调YG信息保存
	 * @return
	 */
	boolean updateCallback(String orderId, String status, String callbackInfo, String CallbackStatus);
	

	/**
	 * 根据Id获取手机
	 * @param orderId
	 * @return
	 */
	SubpackageOrderApi getOrder(String orderId);


	
	

	
	/**
	 * 添加一个包
	 * @return
	 */
	/* boolean sendOnePack();*/
}
