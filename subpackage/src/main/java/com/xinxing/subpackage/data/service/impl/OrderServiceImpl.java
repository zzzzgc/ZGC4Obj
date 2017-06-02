package com.xinxing.subpackage.data.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xinxing.subpackage.core.erorr.NULLOrderIdException;
import com.xinxing.subpackage.core.erorr.RepeatOrdersException;
import com.xinxing.subpackage.core.po.PackInfo;
import com.xinxing.subpackage.data.dao.SubpackageOrderApiMapper;
import com.xinxing.subpackage.data.po.SubpackageOrderApi;
import com.xinxing.subpackage.data.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	SubpackageOrderApiMapper subpackageOrderApiMapper;

	

	@Override
	public boolean addOrderInfo(SubpackageOrderApi order) {
		order.setStarttime(new Date());
		int insert = subpackageOrderApiMapper.insert(order);
		return insert > 0 ? true : false;
	}

	@Override
	public String getOrderId(String sendOrderId) {
		return sendOrderId.split("F")[0];
	}

	/*
	 * @Override public boolean sendOnePack() { return false; }
	 */

	@Override
	public boolean updateCallback(String orderId, String status, String callbackInfo, String CallbackStatus) {
		SubpackageOrderApi api = new SubpackageOrderApi();
		api.setOrderid(orderId);
		api.setStatus(Integer.parseInt(status));
		api.setFeedbackinfo(callbackInfo);
		api.setFeedbackstatus(Integer.parseInt(CallbackStatus));
		api.setBacktime(new Date());
		api.setEndtime(new Date());

		int updateByPrimaryKey = subpackageOrderApiMapper.updateByPrimaryKeySelective(api);
		if (updateByPrimaryKey > 0) {
			return true;
		}
		return false;
	}

	@Override
	public SubpackageOrderApi getOrder(String orderId) {
		return subpackageOrderApiMapper.selectByPrimaryKey(orderId);
	}

	@Override
	public boolean updateOrderInfo(SubpackageOrderApi order) {
		int is = subpackageOrderApiMapper.updateByPrimaryKeySelective(order);
		if (is>0) {
			return true;
		}
		return false;
	}

	@Override
	public boolean deteleOrderInfo(String orderId) {
		int is = subpackageOrderApiMapper.deleteByPrimaryKey(orderId);
		if (is>0) {
			return true;
		}
		return false;
	}

}
