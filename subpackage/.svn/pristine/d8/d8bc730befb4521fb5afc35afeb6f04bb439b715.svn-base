package test;
import java.io.FileNotFoundException;

import org.apache.log4j.Logger;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Log4jConfigurer;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/config/spring/common_Context_junit.xml",
		"file:src/main/webapp/WEB-INF/config/spring/subpackage_servlet.xml",
		"file:src/main/webapp/WEB-INF/config/spring/apiAndSend.xml"
		})
public class test {
	protected static final Logger log=Logger.getLogger(test.class);
	
	static{
		try {
			Log4jConfigurer.initLogging("file:src/main/webapp/WEB-INF/config/propertise/log4j_local.properties");
//			Log4jConfigurer.initLogging("classpath:propertise/log4j_junit.properties");
		} catch (FileNotFoundException ex) {
			System.err.println("Cannot Initialize log4j"+ex);
		}
	}

}
