package com.xinxing.o.boss.business.provider.other.kdou.util;

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

public class KdouUtils {
	
	/*获取提交流量充值请求参数 
	参数名称	是否必须	    类型	              描述
	cid		必须		string		商户ID
	oid		必须		string(50)	商户自己生成的订单号
	phone	必须		string(20)	充值号码
	nd		必须		int			充值流量，单位M(100,1024,2048..)
	range	必须		int			充值范围。1 :国内漫游; 2 :省内;
	sign	必须		string	签名，值：md5(cid+oid+phone+nd+key) 小写
	
	自家订单信息
	SupplierCode  	流量包编码(格式：【流量范围，流量数】)
					第一个数代表使用范围  全国/本省 ,第二个数代表产品M  "2,10" 
	*/
	public static String getSendStr(SendOrderInfo sendOrderInfo){
		Map<String,Object> map = new HashMap<>();
		//自家订单提取参数
		String ourOrderId = sendOrderInfo.getOrderId();
		String orderPhone = sendOrderInfo.getPhone();
		String supplierCode = sendOrderInfo.getSupplierCode();//
		String[] supplier = supplierCode.split(",");
		//上游所需参数	
		String  cid   = Constants.getString("kdou_id");
		String	oid   = ourOrderId;
		String	phone = orderPhone;
		int		nd    = Integer.parseInt(supplier[1]);
		int		range = Integer.parseInt(supplier[0]);
		map.put("cid",cid);
		map.put("oid",oid);
		map.put("phone",phone);
		map.put("nd",nd);
		map.put("sign",MD5_HexUtil.md5Hex(getSign(map)).toLowerCase());
		map.put("range",range);
		
		return HttpUtils.getStrByMapOrderByABC(map);
	}
	
	/*获取订单查询请求 参数
	 参数名称	是否必须	 类型			描述
	cid		必须		string		商户ID
	oid		必须		string(50)	商户自己生成的订单号
	sign	必须		string		签名，值：md5(oid+cid+key) 小写*/
	public static String getQueryStr(SendOrderInfo sendOrderInfo){
		Map<String,Object> map = new HashMap<>();
		//上游所需参数	
		String  cid  = Constants.getString("kdou_id");
		String  oid = sendOrderInfo.getOrderId();
		map.put("cid",cid);
		map.put("oid",oid);
		map.put("sign",MD5_HexUtil.md5Hex(getSign(map)).toLowerCase());
		
		return HttpUtils.getStrByMapOrderByABC(map);
	}
	
	public static String getSign(Map<String,Object> map){
		String kdou_key = Constants.getString("kdou_key");
		StringBuilder sb = new StringBuilder();
		//区分是提交订单还是查询订单,查询订单不需要phone
		if (StringUtils.isNotBlank((String)map.get("phone"))) {
			sb.append(map.get("cid"));
			sb.append(map.get("oid"));
			sb.append(map.get("phone"));
			sb.append(map.get("nd"));
		}else {
			sb.append(map.get("oid"));
			sb.append(map.get("cid"));
		}
		sb.append(kdou_key);
		return sb.toString();
	}
	@Test
	public void test() throws Exception {
	}
}
