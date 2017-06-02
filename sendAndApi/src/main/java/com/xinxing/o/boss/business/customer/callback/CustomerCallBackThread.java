package com.xinxing.o.boss.business.customer.callback;

import org.apache.log4j.Logger;

import com.xinxing.boss.interaction.pojo.customer.CustomerCallbackRecord;
import com.xinxing.o.boss.common.http.HttpClientUtils;
import com.xinxing.o.boss.common.json.JsonUtils;
import com.xinxing.o.boss.common.time.TimeUtils;

/**
 * 回调采购商
 * @author Administrator
 *
 */
public class CustomerCallBackThread implements Runnable{
	private static final Logger log = Logger.getLogger(CustomerCallBackThread.class);
	
	private com.xinxing.boss.interaction.service.customer.a flowCustomerService; 
	
	private com.xinxing.boss.interaction.service.order.a flowOrderService;
	
	private CustomerCallbackRecord customer;
	
	
	public CustomerCallBackThread(CustomerCallbackRecord customer,com.xinxing.boss.interaction.service.customer.a flowCustomerService, com.xinxing.boss.interaction.service.order.a flowOrderService){
		this.flowCustomerService=flowCustomerService;
		this.customer=customer;
		this.flowOrderService = flowOrderService;
	}
	
	@Override
	public void run() {
			log.info("回调任务执行");			
			String param = customer.getCallbackData();
			//回调次数+1
			customer.setCallbackTimes(customer.getCallbackTimes()+1);
			customer.setCallbackTime(TimeUtils.getNowTime());
			
			flowCustomerService.a(customer);
			String string = HttpClientUtils.doPost(customer.getCallbackAddress(), param);
			log.info("回调参数:"+param+",url:"+JsonUtils.obj2Json(customer));
			
			log.info(param+",回调后接收到的信息:"+string);
			if("OK".equals(string)){
				//回调成功
				log.info("回调成功  customer_id:"+customer.getId());
				customer.setStatus(1);
				flowOrderService.e(customer.getOrderId(), 2);
				flowCustomerService.a(customer);
			}
		
	}

}
