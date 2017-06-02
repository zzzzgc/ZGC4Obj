package com.xinxing.o.boss.business.provider.other.dli.util;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xinxing.boss.business.api.domain.SendOrderInfo;
import com.xinxing.o.boss.common.encry.MD5_HexUtil;
import com.xinxing.o.boss.common.http.HttpUtils;
import com.xinxing.o.boss.common.resource.Constants;

public class DLiUtil {
	/**
	 * @author tjm
	 * 获取发送订单的参数
	 */
	public static String getSendStr(SendOrderInfo sendOrderInfo) throws Exception{
		// "D44Y100"   D代表移动,44代表广东,Y代表漫游,100代表100M 
		String supplierCode = sendOrderInfo.getSupplierCode();
		String url = Constants.getString("dli_callback");
		
		//上游所需参数
		String account = Constants.getString("dli_account").trim() ;   //用户名
		String flowCode = supplierCode; //所充流量的产品编码
		String mobile = sendOrderInfo.getPhone();	//充值号码	
		String orderNumber = sendOrderInfo.getOrderId();		//唯一的订单号，商户提供
		String callbackURL = URLEncoder.encode(url,"utf-8");//回调地址用UrlEncode编码（否则可能会导致一些问题），不传将不会回调
		
		Map<String,Object> map = new HashMap<>();
		map.put("account",account);
		map.put("flowCode",flowCode);
		map.put("mobile",mobile);
		map.put("orderNumber",orderNumber);
		map.put("callbackURL",callbackURL);
	
		String sign = getSign(map);
		map.put("sign",sign);
		return HttpUtils.getStrByMapOrderByABC(map);
	}
	/**
	 * @author tjm
	 * 获取查询订单的参数
	 */
	public static String getQueryStr(SendOrderInfo sendOrderInfo){
		Map<String,Object> map = new HashMap<>();
		String account = Constants.getString("dli_account").trim() ;   //用户名
		String user_ordernum = sendOrderInfo.getOrderId();//商户订单号，即我方订单号

		map.put("account",account);
		map.put("user_ordernum",user_ordernum);

		String sign = getSign(map);
		map.put("sign", sign);
		return HttpUtils.getStrByMapOrderByABC(map);
	}
	/**
	 * @author tjm  
	 *  发单加密： 大写( md5( md5(密码)+手机号码+产品编码+商户订单号+秘钥 ) )
	 *  查询加密： 大写( md5( md5(密码)+ 商户订单号/平台订单号+秘钥 ) )
	 */
	public static String getSign(Map<String, Object> map){
		
		String md5Password = MD5_HexUtil.md5Hex(Constants.getString("dli_password").trim()); //md5(密码)
		String dli_key = Constants.getString("dli_key").trim(); //秘钥
		StringBuilder sb = new StringBuilder();
		
		sb.append(md5Password);
		if (StringUtils.isNotBlank((String) map.get("flowCode"))) { //提交订单
			sb.append(map.get("mobile"));
			sb.append(map.get("flowCode"));
			sb.append(map.get("orderNumber"));
		}else { 						 
			sb.append(map.get("user_ordernum"));		//查询订单
		}
		sb.append(dli_key);
		return MD5_HexUtil.md5Hex(sb.toString()).toUpperCase();
	}
	
	public static String getErrorMsg(String code){
		Map<String,String> map = new HashMap<>();
		map.put("2000","充值成功");
		map.put("5000","系统错误");
		map.put("5001","用户名不能为空");
		map.put("5002","订单号不能为空");
		map.put("5003","签名不能为空");
		map.put("5004","产品编码不能为空");
		map.put("5005","手机号码为空或者格式错误");
		map.put("5006","请传入平台订单号或商户订单号");
		map.put("5101","用户名不存在");
		map.put("5102","用户已被禁用");
		map.put("5103","签名错误");
		map.put("5104","手机号码无效");
		map.put("5105","该手机号码被列入黑名单");
		map.put("5106","产品编码不存在");
		map.put("5107","产品编码的省份与手机所属省份不对应");
		map.put("5108","产品编码的运营商与手机所属运营商不对应");
		map.put("5109","IP地址未授权 ");
		map.put("5110","订单号已存在，请提供新订单号");
		map.put("5201","没有合适的流量产品");
		map.put("5202","用户余额不足");
		map.put("5301","订单生成失败");
		map.put("5302","扣除用户余额失败");
		map.put("5303","订单设置失败");
		map.put("5304","未配置供应商接口参数");
		map.put("5305","供应商接口，充值失败 ");
		map.put("6001","订单不存在");
		return map.get(code)==null?"":map.get(code);
	}

	@Test
	public void test() throws Exception {
		String test = "http://106.14.18.108:28083/dli_callback.ddo";
		String newStr = URLEncoder.encode(test,"utf-8");
		System.out.println(newStr);
		System.out.println(URLDecoder.decode(test,"gbk"));
		System.out.println("-------"+"\u63d0\u4ea4\u6210\u529f");
		String json = "{'code':2000,'msg':'\u83b7\u53d6\u8ba2\u5355\u4fe1\u606f\u6210\u529f',"
				+ "		'data':{"
				+ "				'user_ordernum':'170519B0B154034',"
				+ "				'order_number':'BBECE2D84C8841AAA175BB890EBDE9E7',"
				+ "				'order_status':'1',"
				+ "				'mobile':'13622662311',"
				+ "				'ctime':'1495178924'"
				+ "				}"
				+ "	   }";
		JSONObject obj = JSON.parseObject(json);
		String code = obj.getString("code");
		String param = obj.getJSONObject("data").getString("mobile");
		System.out.println("code:"+code);
		System.out.println("param:"+param);
	}
}
