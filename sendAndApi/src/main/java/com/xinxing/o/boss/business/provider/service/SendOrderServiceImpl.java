package com.xinxing.o.boss.business.provider.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.xinxing.boss.business.api.domain.SendOrderInfo;
import com.xinxing.boss.business.api.domain.SendOrderModel;
import com.xinxing.boss.business.api.domain.SendOrderResult;
import com.xinxing.boss.business.api.service.SendOrderService;
import com.xinxing.o.boss.business.provider.api.chinamobile.ChinaMobileSendApi;
import com.xinxing.o.boss.business.provider.api.chinatelecom.TeleComSendApi;
import com.xinxing.o.boss.business.provider.api.chinaunicom.UniComSendApi;

public class SendOrderServiceImpl implements SendOrderService {

	@Autowired
	private ChinaMobileSendApi chinaMobileSendApi;
	
	@Autowired
	private TeleComSendApi teleComSendApi;
	
	@Autowired
	private UniComSendApi uniComSendApi;
	
	@Override
	public SendOrderResult sendOrder(SendOrderInfo sendOrderInfo) {
		String model = sendOrderInfo.getModel();
		SendOrderModel modelType = SendOrderModel.getSendOrderModel(model);
		SendOrderResult result = null;
		switch (modelType) {
		case CHINAMOBILE:
			result = chinaMobileSendApi.send(sendOrderInfo);
			break;
		case TELECOM:
			result = teleComSendApi.send(sendOrderInfo);
			break;
		case CHINAUNICOM:
			result = uniComSendApi.send(sendOrderInfo);
			break;
		}
		return result;
	}


}
