package test;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test111 {
	
	@Test
	public void test1(){
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/common_Context.xml");
		System.out.println(applicationContext.getBean("bossCustomerProductMapper").toString());
		
		/*BossCustomerProductExample a=new BossCustomerProductExample();
		
		List<BossCustomerProduct> selectByExample = bossCustomerProductMapper.selectByExample(a);
		for (BossCustomerProduct bossCustomerProduct : selectByExample) {
			System.out.println(bossCustomerProduct.getId());
		}*/
	}

}
