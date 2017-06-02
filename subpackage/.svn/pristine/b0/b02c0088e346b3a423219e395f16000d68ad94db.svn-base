package com.xinxing.subpackage.data.service;

import java.util.List;

import com.xinxing.subpackage.core.erorr.DIYException;
import com.xinxing.subpackage.core.erorr.NULLOrderIdException;
import com.xinxing.subpackage.core.erorr.RepeatOrdersException;
import com.xinxing.subpackage.core.po.PackInfo;
import com.xinxing.subpackage.data.po.SubpackageOrderSend;

public interface PackOrderService {
	
	/**
	 * 重点核心方法
	 * 获取即将充值的包的必要信息
	 * 获取下一个可以充值的产品Id和可用的订单Id
	 * 
	 * @param orderId 订单Id
	 * @param receivedSendOrderId  当前获取到的分包Id(因为哪个分包订单成功才充值的那个分包id)
	 * 第一单的话   receivedSendOrderId可以为null
	 * @return 下一个需要充值的订单
	 * @throws RepeatOrdersException
	 * @throws NULLOrderIdException
	 * @throws DIYException 
	 */
	PackInfo nextPack(String orderId, String receivedSendOrderId) throws RepeatOrdersException, NULLOrderIdException, DIYException;

	
	/**
	 * 获取这是第几个包, 如果返回0,说明没有该订单.
	 * 确认
	 * @param orderId
	 * @param sendOrderId
	 * @return
	 */
	int isWhichPack(String orderId, String sendOrderId);

	/**
	 * 添加分包信息
	 * 充值
	 * @param PackOrderInfo
	 * @return
	 */
	boolean addPackOrderInfo(SubpackageOrderSend PackOrderInfo);
	
	/**
	 * 根据orderId和sendorderid,修改分包信息
	 * 充值
	 * @param PackOrderInfo
	 * @return
	 */
	boolean updatePackOrderInfo(SubpackageOrderSend PackOrderInfo);

	/**
	 * 获取当前正在充值的分包Id
	 * 查询,回调
	 * @param orderId
	 * @return
	 * @throws NULLOrderIdException 
	 */
	String getpackId(String orderId) throws NULLOrderIdException;
	
	/**
	 * 获取指定原包的所有分包
	 * 查询(间接)
	 * @param orderId
	 * @return
	 */
	List<SubpackageOrderSend> getAllPack(String orderId);
	
	/**
	 * YG回调数据保存
	 * @param sendOrderId
	 * @param status
	 * @param feedbackStatus
	 * @param feedbackInfo
	 */
	boolean updateCallbackOrderInfo(String sendOrderId, String status);

	/**
	 * 获取 SubpackageOrderSend
	 * @param sendOrderId
	 * @return
	 */
	SubpackageOrderSend getSubpackageOrder(String sendOrderId);




	
}
