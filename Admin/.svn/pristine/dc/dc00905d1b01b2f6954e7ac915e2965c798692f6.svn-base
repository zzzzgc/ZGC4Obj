package xinxing.boss.admin.common.mapper;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/*解决异常:
 * Could not write content: 
 * No serializer found for class org.hibernate.proxy.pojo.javassist.JavassistLazyInitializer 
 * and no properties discovered to create BeanSerializer 
 * 
 *  
 *解决方法：将 SpringMVC里默认序列化使用的 com.fasterxml.jackson.databind.ObjectMapper 
 * 的属性SerializationFeature.FAIL_ON_EMPTY_BEANS 为false     
 */

public class CustomMapper extends ObjectMapper{
	public CustomMapper(){
		this.setSerializationInclusion(Include.NON_NULL);
		this.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS,false);
	}
}
