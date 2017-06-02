package com.xinxing.o.boss.business.provider.other.zhiqutest.util;

import static org.junit.Assert.*;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.junit.Test;

import com.xinxing.boss.business.api.domain.SendOrderInfo;
import com.xinxing.o.boss.business.provider.callback.SupplierFlowCallBack;
import com.xinxing.o.boss.common.encry.BASE64Util;
import com.xinxing.o.boss.common.encry.MD5_HexUtil;
import com.xinxing.o.boss.common.http.HttpUtils;
import com.xinxing.o.boss.common.json.JsonUtils;
import com.xinxing.o.boss.common.resource.Constants;
import com.xinxing.o.boss.common.time.TimeUtils;

public class ZhiQuTestUtils {
	private static Logger log = Logger.getLogger(ZhiQuTestUtils.class);
	
	
	/*上游接口提交订单字段
	 * 字段			名称			类型长度		必填		说明
	service			接口标识		String(32)	Y		llsk.order.create
	partnerKey		商户密匙		String(64)	Y		ZHIQU平台分配给商户的密匙
	partnerCode		商户编码		String(32)	Y		ZHIQU平台分配给商户的编码
	signType		签名方法		String(16)	Y		签名方法，默认填写MD5
	sign			签名			tring(64)	Y		签名，32位MD5大写的字符串。签名方法请参考6附录章节
	notifyUrl		异步通知URL	String(512)	Y		下游接入商用于接收异步通知消息的URL地址
	outOrderNum		外部订单号		String(32)	Y		接入商用于标识本次下单的订单流水号，外部订单必须遵循唯一性、幂等性约束。
	product			产品编码		String(32)	Y		要订购流量包产品的编码。请参考产品查询接口响应结果。
	phoneNumbers	手机号码		String(256)	Y		填写要充值的目标手机号码。多个请用英文半角逗号分隔，不允许重复。单张订单一次最多充值200个号码。
													若多个手机号码充值，ZHIQU平台将对充值号码逐个进行充值结果异步通知。
	price			销售价		String(16)	N		可选，接入商当前产品的销售价格。
	tag				自定义标识		String(64)	N		可选，允许接入商根据业务需求给本次订购添加自定义标记，通常用于业务分类。
	detail1			预留字段1		String(256)	N		预留字段，不清楚请留空
	detail2			预留字段2		String(256)	N		预留字段，不清楚请留空
	detail3			预留字段3		String(256)	N		预留字段，不清楚请留空
	detail4			预留字段4		String(256)	N		预留字段，不清楚请留空
	detail5			预留字段5		String(256)	N		预留字段，不清楚请留空
	
	自家sendOrderInfo提取的字段                                                                                                                                                                                                            
	ourOrderId     	自家平台订单id                                                                                                                                                                                     
	SupplierCode  	流量包编码(格式：【流量范围，流量数】)                                                                                                                                                                          
	phone    		手机号                                                                                                                               
	*/
	/**
	 * 获取流量包订购接口传递的参数
	 * @param sendOrderInfo
	 * @return  获得对应的xx=yy&&aa=bb 字符串 字典排序
	 */
	public static String getSendStr(SendOrderInfo sendOrderInfo){
		Map<String, Object> map = new HashMap<>();
		//自家sendOrderInfo提取出来的字段
		String ourOrderId = sendOrderInfo.getOrderId();
		String phone = sendOrderInfo.getPhone();
		String supplierCode = sendOrderInfo.getSupplierCode();//供应商产品编码 (广东移动10M): LL00193
		//上游所需字段
		String service = Constants.getString("zhiqu_order_create").trim();
		String partnerCode = Constants.getString("zhiqu_partnerCode").trim();
		String partnerKey = Constants.getString("zhiqu_partnerKey").trim();
		String signType = "MD5";
		String notifyUrl = Constants.getString("zhiqu_sendBack")+"/zhiqu_callback.do";
		String outOrderNum = ourOrderId;
		String product = supplierCode; 
		String phoneNumbers =phone;
		//非必填项
		/*String price = "";
		String tag = "";
		String detail1 = "";
		String detail2 = "";
		String detail3 = "";
		String detail4 = "";
		String detail5 = "";*/
		map.put("service",service);
		map.put("notifyUrl",notifyUrl);
		map.put("outOrderNum",outOrderNum);
		map.put("product",product);
		map.put("phoneNumbers",phoneNumbers);
		/*map.put("price",price);
		map.put("tag",tag);
		map.put("detail1",detail1);
		map.put("detail2",detail2);
		map.put("detail3",detail3);
		map.put("detail4",detail4);
		map.put("detail5",detail5);*/
		map.put("sign",MD5_HexUtil.md5Hex(getSign(map)).toUpperCase());
		map.put("partnerCode",partnerCode);
		map.put("partnerKey",partnerKey);
		map.put("signType",signType);
		return HttpUtils.getStrByMapOrderByABC(map);
	}
	
	
	/*zhiqu流量包查询接口
	 * 		字段	 	     名称	     类型长度	         必填		说明
	 *  service		  接口标识	String(32)	Y	llsk.order.query
		partnerKey	  商户密匙	String(64)	Y	ZHIQU平台分配给商户的密匙
		partnerCode	  商户编码	String(32)	Y	ZHIQU平台分配给商户的编码
		signType	  签名方法	String(16)	Y	签名方法，默认填写MD5
		sign		  签名		String(64)	Y	签名，32位MD5大写的字符串。签名方法请参考6附录章节
		outOrderNum	外部订单号	String(32)	Y	接入商已经成功下单的订单流水号，外部订单必须遵循唯一性、幂等性约束。
	/**
	 * 
	 * 订单状态查询接口获取传递的参数
	 * @param sendOrderInfo
	 * @return  对应的xx=yy&&aa==bb 字符串 字典排序
	 */
	public static String getQueryStr(SendOrderInfo sendOrderInfo){
		Map<String, Object> map = new HashMap<>();
		
		String SupplierCode = sendOrderInfo.getSupplierCode();// 流量包编码
		String[] productCode = SupplierCode.split(",");//第一个数代表使用范围  全国/本省 ,第二个数代表产品M    0,10
		
		String service = Constants.getString("zhiqu_order_query").trim();
		String partnerCode = Constants.getString("zhiqu_partnerCode").trim();
		String partnerKey = Constants.getString("zhiqu_partnerKey").trim();
		String signType = "MD5";
		String outOrderNum = sendOrderInfo.getOrderId();
		
		map.put("service",service);
		map.put("outOrderNum",outOrderNum);
		map.put("sign",MD5_HexUtil.md5Hex(getSign(map)).toUpperCase());
		map.put("partnerCode",partnerCode );
		map.put("partnerKey", partnerKey);
		map.put("signType",signType);
		return HttpUtils.getStrByMapOrderByABC(map);
		
	}
	/**
	 * 获取签名方式  ：除了partnerCode,partnerKey 参数外,按a-z排序，第一个相同则看第二个
	 * service  =llsk.product.query,
	 * partner  =100001,
	 * signType =MD5,
	 * sign		=,
	 * provider =1,
	 * specific =1,
	 * scope    =0
	 * @param map
	 * @return	加密前最终字符串 		 10000110llsk.product.queryMD51 + partnerCode + partnerKey
	 */
	public static String getSign(Map<String, Object> map){
		String partnerCode	= Constants.getString("zhiqu_partnerCode").trim();
		String partnerKey   = Constants.getString("zhiqu_partnerKey").trim();
		//map按a-z排序后取值
		Set<String> keys = map.keySet();
		List<String> list = new ArrayList<String>(keys);
		Collections.sort(list);
		StringBuffer sb = new StringBuffer();
		
		for (String key : list) {
			sb.append(map.get(key));
		}
		sb.append(partnerCode);
		sb.append(partnerKey);
		return sb.toString();
	}

	/**
	 * 获取错误提示代码的信息
	 * @param codes
	 * @return
	 */
	public static String getErrorMsg(String code){
		Map<String,String> map = new HashMap<>();
		map.put("100","接口维护,暂停使用");
		map.put("101","接口异常,请稍后再试");
		map.put("102","接口入参不全");
		map.put("200","单次充值不能超过200个号码");
		map.put("201","不是同一运营商的号码列表或号码列表中有非11位数字");
		map.put("202","提供的充值号码和产品的运营商类型不匹配,不匹配的号码列表");
		map.put("300","账号不存在");
		map.put("301","账号状态不正常");
		map.put("302","签名验证不通过");
		map.put("303","当前账户下单过于频繁:");
		map.put("310","当前账户被停用");
		map.put("311","当前账户审核不通过");
		map.put("312","当前账户为待审核状态");
		map.put("313","当前账号额度已用完");
		map.put("314","账号信息有误");
		map.put("315","账号余额不足,请及时充值");
		map.put("316","账户余额扣减失败:");
		map.put("400","该产品已经下架");
		map.put("401","产品运营商类型不能为空");
		map.put("402","获取产品列表失败");
		map.put("500","异步通知接口地址不能为空");
		map.put("501","接口标识不能为空");
		map.put("502","商户键值不能为空");
		map.put("503","商户编码不能为空");
		map.put("504","签名方法不能为空 ");
		map.put("505","签名不能为空");
		map.put("600","商户余额查询失败");
		map.put("601","商户订单查询失败");
		map.put("602","商户订单查询超过规定次数");
		map.put("603","商户余额查询超过规定次数");
		map.put("604","查询外部订单号为空");
		return map.get(code)==null?"":map.get(code);
	}
	
	@Test
	public void testName() throws Exception {
	}
}



