package com.xinxing.o.boss.business.provider.other.testsdlt.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.util.DateUtil;

import com.xinxing.boss.business.api.domain.SendOrderInfo;
import com.xinxing.o.boss.common.encry.MD5_HexUtil;
import com.xinxing.o.boss.common.http.HttpUtils;
import com.xinxing.o.boss.common.resource.Constants;

public class TestsdltUtils {

	public static String getSendReq(SendOrderInfo sendOrderInfo) throws Exception {
		String order_orderId = sendOrderInfo.getOrderId();// AA订单id
		String order_productCode = sendOrderInfo.getSupplierCode();// 流量包编码
		String order_phone = sendOrderInfo.getPhone();// 手机号
		String[] split_productCode = order_productCode.split(",");//第一个数代表全国/本省 ,第二个数代表产品M
		String  key       = Constants.getString("testsdlt_key".trim()).trim().toUpperCase();//		String  = Constants.getString("testsdlt_".trim()).trim();
		String  userid    = Constants.getString("testsdlt_userid".trim()).trim();
		String  pwd       = Constants.getString("testsdlt_pwd".trim()).trim().toUpperCase();
		String  orderid   = order_orderId;
		String  account   = order_phone;
		String  gprs      = split_productCode[1];//充值流量值	单位：MB（具体流量值请咨询商务）
		String  area      = split_productCode[0];//0 全国流量，1 省内流量
		String  effecttime= "0";
		String  validity  = "0";
		String  times     = DateUtil.formatDate(new Date(), "yyyyMMddhhmmss");
		String  userkey   = MD5_HexUtil.md5Hex("userid"+userid+"pwd"+pwd+"orderid"+orderid+"account"+account+"gprs"+gprs+"area"+area+"effecttime"+effecttime+"validity"+validity+"times"+times+key);

		Map sendReqMap = new HashMap<>();
		sendReqMap.put("userid    ".trim(),userid        );
		sendReqMap.put("pwd       ".trim(),pwd        );
		sendReqMap.put("orderid   ".trim(),orderid    );
		sendReqMap.put("account   ".trim(),account    );
		sendReqMap.put("gprs      ".trim(),gprs       );
		sendReqMap.put("area      ".trim(),area       );
		sendReqMap.put("effecttime".trim(),effecttime );
		sendReqMap.put("validity  ".trim(),validity   );
		sendReqMap.put("times     ".trim(),times      );
		sendReqMap.put("userkey   ".trim(),userkey    );
		String sendReq =  HttpUtils.getStrByMapOrderByABC(sendReqMap);
		
		return sendReq;
	}

	public static String getQueryReq(SendOrderInfo sendOrderInfo) throws Exception {
		String order_providerOrderId = sendOrderInfo.getSupplierOrderId();// 供应商流水号
		String  key       = Constants.getString("testsdlt_key".trim()).trim().toUpperCase();
//	    String  = Constants.getString("testsdlt_".trim()).trim();
	    String userid   =  Constants.getString("testsdlt_userid".trim()).trim();
	    String pwd      =   Constants.getString("testsdlt_pwd".trim()).trim().toUpperCase();
	    String orderid  = order_providerOrderId;
	    String userkey  = MD5_HexUtil.md5Hex("userid"+userid+"pwd"+pwd+"orderid"+orderid+key);
		Map queryReqMap = new HashMap<>();
		queryReqMap.put("userid  ".trim(), userid  );
		queryReqMap.put("pwd     ".trim(), pwd     );
		queryReqMap.put("orderid ".trim(), orderid );
		queryReqMap.put("userkey ".trim(), userkey );
		String queryReq =  HttpUtils.getStrByMapOrderByABC(queryReqMap);
//		String queryReq =  JsonUtils.obj2Json(queryReqMap);
		return queryReq;
	}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	public static String getErrorMsg(String code) {
		Map<String, String> map = new HashMap<>();
		map.put("0	".trim(), "无错误	请求已被后台接收                                            ".trim()); 
		map.put("1003".trim(),"用户ID或接口密码错误	充值时可失败，查询时需人工处理                             ".trim()); 
		map.put("1004".trim(),"用户IP错误	充值时可失败，查询时需人工处理                                 ".trim()); 
		map.put("1005".trim(),"用户接口已关闭	充值时可失败，查询时需人工处理                                 ".trim()); 
		map.put("1006".trim(),"加密结果错误	充值时可失败，查询时需人工处理                                 ".trim()); 
		map.put("1007".trim(),"订单号不存在	需在该订单提交10分钟后方可处理失败                              ".trim()); 
		map.put("1011".trim(),"号码归属地未知	充值时可失败，查询时需人工处理                                 ".trim()); 
		map.put("1013".trim(),"手机对应的商品有误或者没有上架	充值时可失败，查询时需人工处理                         ".trim()); 
		map.put("1014".trim(),"无法找到手机归属地	充值时可失败，查询时需人工处理                             ".trim()); 
		map.put("1015".trim(),"余额不足	充值时可失败，查询时需人工处理                                 ".trim()); 
		map.put("1017".trim(),"产品未分配用户，联系商务	充值时可失败                                  ".trim()); 
		map.put("1018".trim(),"订单生成失败	充值时可失败                                          ".trim()); 
		map.put("1019".trim(),"充值号码与产品不匹配	充值时可失败                                      ".trim()); 
		map.put("1020".trim(),"号码运营商未知	充值时可失败                                          ".trim()); 
		map.put("9998".trim(),"参数有误	充值时可失败                                          ".trim()); 
		map.put("9999".trim(),"系统错误	人工处理                                            ".trim()); 
		return map.get(code) == null ? "" : map.get(code);

	}
	
	
}
