package com.xinxing.o.boss.business.provider.other.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.apache.log4j.Logger;

import com.xinxing.boss.business.api.domain.SendOrderInfo;
import com.xinxing.boss.business.api.domain.SendOrderResult;
import com.xinxing.boss.business.api.domain.SendOrderStatus;
import com.xinxing.o.boss.common.encry.MD5_HexUtil;

public class HuaYiTestApiImpl  implements HuaYiTestApi{
	
 private static final Logger log = Logger.getLogger(HuaYiTestApiImpl.class);
	
	@Override
	public SendOrderResult send(SendOrderInfo sendOrderInfo) {
		com.xinxing.flow.utils.resource.a.getString("sys_boss_key");
		
		Map<String, Object> map = new HashMap<>();
		String phoneNumberList="18820036966";
		/*String phoneNumberList = sendOrderInfo.getPhone();*/
		String productCode = "HY00001";
		String key = "18820036966HY00001FXT0000";
		String distributorCode = "FXT0000"; //商品编码 修改
		
		map.put("phoneNumberList", phoneNumberList);
		map.put("productCode", productCode);
		map.put("key", key);
		map.put("distributorCode", distributorCode);

		System.out.println("计算签名(MD5):"+MD5_HexUtil.md5Hex(getSign(map)).toUpperCase()); //大写

		Random random = new Random();
		SendOrderResult result = null;
		/*String bossId = null;  //如果没有指定值 这个就为null
*/		log.info("phoneNumberList=" + phoneNumberList + "productCode=" + productCode + "key=" + " distributorCode=" + distributorCode);
		if (random.nextInt() < 5) {
			result = new SendOrderResult(SendOrderStatus.SUCCESS.status,
					null, null);
		} else {
			result = new SendOrderResult(SendOrderStatus.FAILED.status, "随机失败", null);
		}
		return result;
	}
	
	public static String getSign(Map<String, Object> map) {

		Set<String> keys = map.keySet();
		List<String> list = new ArrayList<String>(keys);
		Collections.sort(list);

		StringBuffer sb = new StringBuffer();

		for (String key : list) {
			sb.append(key + map.get(key));
		}
		sb.append("dvVAgnBzpoUILzm");
		return sb.toString();
	}

	@Override
	public Map<String, SendOrderResult> querys(List<SendOrderInfo> sendOrderInfo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SendOrderResult query(SendOrderInfo sendOrderInfo) {
		// TODO Auto-generated method stub
		return null;
	}
	public static void main(String[] args) {
//		String url="http://183.3.136.5:18089/services/OrderTest";
//		Service service = new Service();
//		String returnXml = "";
//		String phoneNumberList="18820036966";
//		/*String phoneNumberList = sendOrderInfo.getPhone();*/
//		String productCode = "HY00001";
//		String key = "18820036966HY00001FXT0000";
//		String distributorCode = "FXT0000"; //商品编码 修改
//		try {
//			Call call = (Call) service.createCall();
//			call.setTargetEndpointAddress(url);
//			call.setOperationName(new QName("http://webservice.util.llhuayi.sharera.com/","orderSubmit")); // 设置要调用哪个方法
//			call.addParameter(new QName("http://webservice.util.llhuayi.sharera.com/","phoneNumberList"), // 设置要传递的参数
//					org.apache.axis.encoding.XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN);
//			call.addParameter(new QName("http://webservice.util.llhuayi.sharera.com/","productCode"), // 设置要传递的参数
//					org.apache.axis.encoding.XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN);
//			call.addParameter(new QName("http://webservice.util.llhuayi.sharera.com/","key"), // 设置要传递的参数
//					org.apache.axis.encoding.XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN);
//			call.addParameter(new QName("http://webservice.util.llhuayi.sharera.com/","distributorCode"), // 设置要传递的参数
//					org.apache.axis.encoding.XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN);
//			// call.setReturnType(new QName(soapaction, "excute"),
//			// Vector.class); // 要返回的数据类型（自定义类型）
//
//			call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);// （标准的类型）
//
//			//call.setUseSOAPAction(true);
//			call.setSOAPActionURI("http://webservice.util.llhuayi.sharera.com/");
//
//			// Vector v = (Vector) call.invoke(new Object[] { xml});// 调用方法并传递参数
//
//			returnXml = (String) call.invoke(new Object[] {phoneNumberList,productCode,key,distributorCode});// 调用方法并传递参数
////			logger.debug("returnXml==" + returnXml);
//			System.out.println("returnXml:"+returnXml);
//		} catch (Exception ex) { 
//			ex.printStackTrace();
//		}
	}
}
