package com.xinxing.o.boss.business.provider.other.xcheng.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xinxing.boss.business.api.domain.SendOrderInfo;
import com.xinxing.o.boss.common.encry.MD5_HexUtil;
import com.xinxing.o.boss.common.json.JsonUtils;
import com.xinxing.o.boss.common.resource.Constants;
import com.xinxing.o.boss.common.time.TimeUtils;


public class XChengUtil {
	private static Logger log = Logger.getLogger(XChengUtil.class);
	/*
	 * 订单发送请求参数
	 */
	public static String getSendStr(SendOrderInfo sendOrderInfo) throws Exception{
		Map<String,String> map = new HashMap<>();
		String xCheng_key = Constants.getString("xcheng_key");
		String supplierCode = sendOrderInfo.getSupplierCode(); //“123456,233666”表示“产品ID,采购池协议ID”
		String[] split = supplierCode.split(",");
		
		//请求体
		String protocolId = split[1]; 					 //协议ID，采购池协议ID     
		String orderId = sendOrderInfo.getOrderId();     //我方订单号
		String content = "";                             //订单描述,可空时必须传空字符串,下同
		String orderTime = TimeUtils.getNowTime();       //订单时间,格式：yyyy-MM-dd HH:mm:ss 
		String prodId = split[0]; 					     // 产品ID
		String areaLimit = "";    //产品区域限制
		String channelLimit = ""; //产品渠道限制
		String channel = "";      //产品渠道
		String province = "";     //省份编码
		String city = "";         //城市编码        
		String payAmount = "";    //结算金额
		String phoneNumber = sendOrderInfo.getPhone();   //充值号码
		String notifyUrl = Constants.getString("xcheng_sendBack");      //回调地址
		String ext = "";          //扩展字段
		
		map.put("protocolId",protocolId);
		map.put("orderId", orderId);
		map.put("content",content);
		map.put("orderTime",orderTime );
		map.put("prodId", prodId);
		map.put("areaLimit",areaLimit);
		map.put("channelLimit",channelLimit);
		map.put("channel",channel);
		map.put("province",province);
		map.put("city",city);
		map.put("payAmount",payAmount);
		map.put("phoneNumber",phoneNumber );
		map.put("notifyUrl",notifyUrl );
		map.put("ext",ext);
		
		//上游所需字段
		String partyId = Constants.getString("xcheng_id");//身份ID
		String time = TimeUtils.getSSSNowTimeFormate();   //请求时间,格式：yyyyMMddHHmmssSSS
		String data = AESUtil.encrypt(JsonUtils.obj2Json(map),xCheng_key); 	//数据摘要	
		
		map.clear();
		map.put("partyId",partyId);
		map.put("time",time);
		map.put("data",data);	
		
		String sign = getSign(map); //签名
		map.put("sign", sign);
		return JsonUtils.obj2Json(map);
	}
	
	/*
	 * 订单查询请求参数
	 */
	public static String getQueryStr(SendOrderInfo sendOrderInfo) throws Exception{
		Map<String,String> map = new HashMap<>();
		String xCheng_key = Constants.getString("xcheng_key");
		//请求体
		String orderId = sendOrderInfo.getOrderId();     //我方订单号
		String orderTime = TimeUtils.getNowTime();       //订单时间,格式：yyyy-MM-dd HH:mm:ss 
		String channelOrderId = sendOrderInfo.getSupplierOrderId(); //对方订单号
		
		map.put("orderId",orderId);
		map.put("orderTime",orderTime);
		map.put("channelOrderId",channelOrderId);
		
		//上游所需字段
		String partyId = Constants.getString("xcheng_id");//身份ID
		String time = TimeUtils.getSSSNowTimeFormate();   //请求时间,格式：yyyyMMddHHmmssSSS
		String data = AESUtil.encrypt(JsonUtils.obj2Json(map),xCheng_key); //数据摘要
	
		map.clear();
		map.put("partyId",partyId);
		map.put("time",time);
		map.put("data",data);	
		
		String sign = getSign(map); //签名
		map.put("sign", sign);
		return JsonUtils.obj2Json(map);
	}
	
	public static String getSign(Map<String,String> map) {
		String sign = map.get("partyId")+map.get("data")+map.get("time");
		return MD5_HexUtil.md5Hex(sign);
	}
	
	/**
	 * 获取结果码信息
	 * @param codes
	 * @return
	 */
	public static String getErrorMsg(String code){
		Map<String,String> map = new HashMap<>();
		map.put("", "");
		return map.get(code)==null?"":map.get(code);
	}
		
	@Test
	public void testName() throws Exception {
		String teString = "897598946682D1ro2R5425rOVsAwYSJLfgy32/7Ok+0ezShiCNBieq4+Qyv2s1ulDIKO7OIHGHM+KPR4IoBtNyZxC7d0d9ptZwYhmHk65axhsEgvYogtXUyGFdHwE0SNB1RZkeTt88IHAlrlCh1GwNjg5G24HDTGd2FCuyQvx+/PLzmUSGr1ovfoxaDlf7bdXkLlimrawPWlgIjFiaHRtHxWNLn/KuVD13wZQyhKtIWUMhIDsLDvJBCGRGItoayg/rm2JNEulMYhbv74Eyp/9hsLIkpp9081wXJXbBcyxLwnIi+RBzftH8gCE3uqrg75MHDGShW0WEmgZQu/nAP1jW0YBH1ztPopbbYcoX/lTJTc5z49iU2rCbXHheMkkYtnw4yFEwXUJQXyQ7Qud+84IRGnvxmrzU9JrgvLGwjF8iWkJc7fT8899aA=20170525112314481";
		System.out.println(MD5Util.getSignAndMD5(teString));
		System.out.println(MD5_HexUtil.md5Hex("tjm454"));
		/*
		
		String key ="b138b0e67f26b8a25c39256af801b8dc";
		String jsonArr = "[{"
				+ "			'status':'1', "
				+ "			'resultCode':'00000', "
				+ "			'resultDesc':'233',"
				+ "			'orderId':'10000000000001',"
				+ "			'channelOrderId':'60000000000001',"
				+ "			},"
				+ "			{"
				+ "			'status':'2', "
				+ "			'resultCode':'00002', "
				+ "			'resultDesc':'666',"
				+ "			'orderId':'10000000000002',"
				+ "			'channelOrderId':'60000000000002',"
				+ "			}"			
				+ "		  ]";
		JSONArray test=  JSON.parseArray(jsonArr);
		System.out.println(test);
		JSONObject obj = null;
		for (int i = 0; i < 100; i++) {
			try {
				obj = test.getJSONObject(i);
				String result = obj.getString("resultDesc");
				System.out.println("resultDesc--"+i+"--"+result);
			} catch (Exception e) {
				break;
			}
		}
		System.out.println("已打印");
		
		String callback = "GGH5os2TU5sFc0LvJgS7sG9cyqJX3/dFc8YJhZ/8YxEBLEHybAkcGsVSDkjac9CkuQ53IfUDgmRXZG/Wv8LB5LGqGjJoYArdmrxDAB9sFyFaM1bwKuJ06b5Pv2PSoy8m+do2uhrGoiBzEy6XRA9tA5ZUbMWdzZLpxklpdSRVBCRa0J23cli1bW22D1XjumBhPjuErBMlNniuuZv5NjKgUuiHr8Y8QRBj2rfXnApeT5TNCLTwbyFhED/wEc5a8Va82VCRLnwNY2taU8qHngHItxEg7DwcL2c+PZe6ewDDnUns8QT9XVP796MeVjd2bdO5X7vXsMdF1rX+7/ChWy5F7FnzXYjYouu7JJcVpCz/LB7LE2p7pronSRVL3Qhi4xr/ynPBYbShvSDhToS3PKG8N8vkcTmpoz4lKVCPnqJNk1f/Eat6EhOtbaG35XSg4RIsHO01EM3EU6rwb/QFPNBzLQ==";
		String send = "D1ro2R5425rOVsAwYSJLfjab0NRioXbSGw4N2IxhGe5fmvqd/lKY1uQVscgDKR2JKPR4IoBtNyZxC7d0d9ptZ8TQGD+cf+ta6g1+sn0tVg4PiV2Jpa4pyMYqg2BkpGDrAlrlCh1GwNjg5G24HDTGd2FCuyQvx+/PLzmUSGr1ovfoxaDlf7bdXkLlimrawPWlgIjFiaHRtHxWNLn/KuVD13wZQyhKtIWUMhIDsLDvJBCGRGItoayg/rm2JNEulMYhbv74Eyp/9hsLIkpp9081wXJXbBcyxLwnIi+RBzftH8gCE3uqrg75MHDGShW0WEmgZQu/nAP1jW0YBH1ztPopbbYcoX/lTJTc5z49iU2rCbXHheMkkYtnw4yFEwXUJQXyQ7Qud+84IRGnvxmrzU9JrtfP57ZXP1q9zIEgkiFN0wk=";
		String query = "lWsM+9hr1eknpSRg68BikW+yyHha2fhtze9koAlVFDiSNxBbDTZ2oYPm1lh8wBnkjXfyRvBCqv1gs+x4Tdsj+2nnSYcUfwaPc1sv8ZhY5rY+Ik7mlK1NSvE+L4roPOs5loW5xk+gqNbu6UnJfL5zUg==";
		System.out.println();
		System.out.println("解密callback："+AESUtil.decrypt(callback,"94228f220c3520d531eb124a902b0f62"));
		System.out.println("解密send："+AESUtil.decrypt(send,"94228f220c3520d531eb124a902b0f62"));
		System.out.println("解密query："+AESUtil.decrypt(query,"94228f220c3520d531eb124a902b0f62"));
		 */
	
	}
}
