package com.xinxing.o.boss.business.customer.callback;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.xinxing.boss.interaction.pojo.customer.CustomerCallbackRecord;

/**
 * 回调采购商定时任务
 * @author Administrator
 *
 */
public class CustomerCallBackScheduleServiceImpl implements CustomerCallBackScheduleService {
	
	private static final Logger log = Logger.getLogger(CustomerCallBackScheduleServiceImpl.class);
	 
	@Autowired
	private com.xinxing.boss.interaction.service.customer.a flowCustomerService;
	
	@Autowired
	private com.xinxing.boss.interaction.service.order.a flowOrderService;
	
	@Override
	public void doCallBackRequest(){
		log.info("回调采购商开始");
		List<CustomerCallbackRecord> records = flowCustomerService.getCustomerCallbackRecords();
		if(records!=null){
			if(records.size()<1){
				log.info("没有采购商需要回调");
				return;
			}
			for (CustomerCallbackRecord customer : records) {
				CustomerCallBackThread aoc=new CustomerCallBackThread(customer,flowCustomerService,flowOrderService);
				Thread thread=new Thread(aoc);
				thread.start();
			}
		}
		log.info("回调任务执行中");
	}
	
	
}
