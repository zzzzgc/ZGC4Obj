package junitTest;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import xinxing.boss.admin.boss.order.domain.OrderInfo;
import xinxing.boss.admin.boss.order.service.OrderInfoService;

public class JunitTestDemo extends CommonTest {
	
	private static final Logger log = Logger.getLogger(JunitTestDemo.class);
	
	@Autowired
	private OrderInfoService orderInfoService;
	
	@Test
	public void test() {
		log.info("进来了");
		OrderInfo orderInfo = orderInfoService.get(154001);
		System.out.println(orderInfo.getPhone());
//		orderInfoService.update(o);
	}


}
