package hy.test;
import java.io.FileNotFoundException;

import org.apache.log4j.Logger;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Log4jConfigurer;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"file:src/main/webapp/WEB-INF/config/spring/common_Context_junit.xml",
		"file:src/main/webapp/WEB-INF/config/spring/flowCharge_Context.xml",
		"file:src/main/webapp/WEB-INF/config/spring/supplierApi_Context.xml",
		"file:src/main/webapp/WEB-INF/config/spring/sendService_Context.xml",
		"file:src/main/webapp/WEB-INF/config/spring/scheduleJob_Context_local.xml",
		})
public class test {
	protected static final Logger log=Logger.getLogger(test.class);
	
	static{
		try {
			Log4jConfigurer.initLogging("file:src/main/webapp/WEB-INF/config/log4j/log4j_junit.properties");
//			Log4jConfigurer.initLogging("classpath:propertise/log4j_junit.properties");
		} catch (FileNotFoundException ex) {
			System.err.println("Cannot Initialize log4j"+ex);
		}
	}

}
