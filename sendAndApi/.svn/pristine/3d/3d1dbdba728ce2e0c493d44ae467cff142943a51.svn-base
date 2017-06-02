package com.xinxing.o.boss.business.provider.other.josy.util;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xinxing.boss.business.api.domain.SendOrderInfo;
import com.xinxing.o.boss.common.encry.MD5_HexUtil;
import com.xinxing.o.boss.common.http.HttpUtils;
import com.xinxing.o.boss.common.json.JsonUtils;
import com.xinxing.o.boss.common.resource.Constants;

public class JOSYUtils {
	public static String getSendStr(SendOrderInfo sendOrderInfo) throws Exception{
			
			Map<String,Object> map = new HashMap<>();
			/*
			 自家sendOrderInfo提取的字段                                                                                                                                                                                                            
			ourOrderId     	自家平台订单id                                                                                                                                                                                     
			SupplierCode  	流量包编码                                                                                                                                                                        
			phone    		手机号 
			 */
			String phone = sendOrderInfo.getPhone();
			String ourOrderId = sendOrderInfo.getOrderId();
			String supplierCode = sendOrderInfo.getSupplierCode();//可输入对方提供的销售品 ID
			String[] split = supplierCode.split(","); 
			//提供给上游字段
			String	uname = Constants.getString("josy_id");  //用户帐号
			String  type = "flow";  	   //产品类型 flow 为 流量  money 为 话费 
			String  spec = split[1]; 	   //产品规格 flow 时1G 或 money 时 10 （元）
			String	mobile = phone;   	   //手机号码
			String	scope = split[0];	   //0 是 省网可漫游  1是全网   2省网不可漫游 话费充值时省略
			String  tradeno  = ourOrderId; //渠道订单号（32位之内）
			
			map.put("uname",uname);
			map.put("type",type);
			map.put("spec",spec);
			map.put("mobile", mobile);
			map.put("scope",scope);	
			map.put("tradeno",tradeno);
			
			String sign = MD5_HexUtil.md5Hex(getSign(map));
			map.put("sign",sign);
			return HttpUtils.getStrByMapOrderByABC(map);
		}
		
		public static String getQueryStr(SendOrderInfo sendOrderInfo){
			Map<String, Object> map = new HashMap<>();
			
			String tradenos = sendOrderInfo.getOrderId();
			map.put("tradenos",tradenos);
			return HttpUtils.getStrByMapOrderByABC(map);
		}
	
		public static String getSign(Map<String,Object> map){
			String josy_key = Constants.getString("josy_key");
			StringBuilder sb = new StringBuilder();
			Set<String> keys = map.keySet();
			List<String> list = new ArrayList<>(keys);
			Collections.sort(list);
			for (String key : list) {
				sb.append(key+"="+map.get(key)+"&");
			}
			sb.append(josy_key);
			return sb.toString();
		}
		/**
		 * 获取结果码信息
		 * @param codes
		 * @return
		 */
		public static String getErrorMsg(String code){
			Map<String,String> map = new HashMap<>();
			map.put("","");
			return map.get(code)==null?"":map.get(code);
	    }
		
		@Test
		public void testName() throws Exception {
			String  test = "{'result':'true','data':[{'tradeno':'1','state':'2','statdesc':'fail'}]}";
			//String  test = "{'result':'true','data':[]}";
			JSONObject obj = JSON.parseObject(test);
			String get = obj.getJSONArray("data").getJSONObject(0).getString("state");
			System.out.println(get);
			
			String  array = "{'result':'true','data':{'tradeno':'1','state':'2','statdesc':'fail'}}";
			JSONObject obj2 = JSON.parseObject(array);
			String get2 = obj2.getJSONObject("data").getString("statdesc");
			System.out.println(get2);
			String count = "您的账户余额不足";
			System.out.println("\u63d0\u4ea4\u6210\u529f");
		}
}
