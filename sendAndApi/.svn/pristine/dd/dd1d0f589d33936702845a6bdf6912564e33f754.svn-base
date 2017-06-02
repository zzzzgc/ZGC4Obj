package com.xinxing.o.boss.business.provider.other.yliang.util;

import java.util.HashMap;
import java.util.Map;

import com.xinxing.boss.business.api.domain.SendOrderInfo;
import com.xinxing.o.boss.common.encry.MD5_HexUtil;
import com.xinxing.o.boss.common.http.HttpUtils;
import com.xinxing.o.boss.common.resource.Constants;

public class YliangTestUtils {

	/**
	 * 获取发送订单参数 RequestParamBySendParam
	 * 
	 * @param sendOrderInfo
	 * @return
	 */
	public static String getSendReq(SendOrderInfo sendOrderInfo) {
		/**
		 * tel 用户手机号(AAPhone) did 渠道商id(BB提供) bid 产品id(BB提供) efftype
		 * 产品生效方式:2=立即(默认方式),3=下月 dorderid AA订单id timestamp 时间戳 userkey
		 * threeDes(tel+did+timestamp)加密 | md5(tel+did+timestamp+key)带约定key加密
		 * userdata
		 * (不必须)渠道商自有数据(渠道商采用建议采用JSON格式)，如：{"storeid":"11109","productid":
		 * "03937642"} Storeid, productid渠道商根据实现需求提交
		 * 
		 * 
		 * order_phone order_orderId order_supplier
		 */
		String order_phone = sendOrderInfo.getPhone();
		String order_orderId = sendOrderInfo.getOrderId();
		String supplierCode = sendOrderInfo.getSupplierCode();
		// String split_productType=supplierCode.split(",")[0]; //0省内,1全国

		String key = Constants.getString("yliang_key");
		String tel = order_phone;
		String did = Constants.getString("yliang_did"); // 渠道商
		String bid = supplierCode; // 产品id(BB),为产品编号
		String efftype = "2";// 2=立即(默认方式),3=下月
		String dorderid = order_orderId;
		String timestamp = "" + System.currentTimeMillis() / 1000;// 19700101至今的秒数

		String userkey = MD5_HexUtil.md5Hex(tel + did + timestamp + key);
		// String userdata = ;

		Map<String, Object> sendReqMap = new HashMap<>();

		sendReqMap.put("tel", tel);
		sendReqMap.put("did", did);
		sendReqMap.put("bid", bid);
		sendReqMap.put("efftype", efftype);
		sendReqMap.put("dorderid", dorderid);
		sendReqMap.put("timestamp", timestamp);
		sendReqMap.put("userkey", userkey);
		String sendUrlParam = HttpUtils.getStrByMapOrderByABC(sendReqMap);
		return sendUrlParam;

	}

	/**
	 * 获取查询参数 RequestParamByQueryParam
	 * 
	 * @param sendOrderInfo
	 * @return
	 * @throws Exception
	 */
	public static String getQueryReq(SendOrderInfo sendOrderInfo) throws Exception {
		/**
		 * spid 运营商ID，取值: 1-中国电信， 3 -中国联通,4-所有运营商,5-中国移动 tel AAPhone(AA) did
		 * 渠道商id(BB提供) timestamp 时间戳 userkey threeDes(tel+did+timestamp)加密 |
		 * md5(tel+did+timestamp+key)带约定key加密
		 */
		String order_orderId = sendOrderInfo.getOrderId();
		String order_orderPhone = sendOrderInfo.getPhone();

		String key = Constants.getString("yliang_key");
		String tel = order_orderPhone;
		String did = Constants.getString("yliang_did");
		String timestamp = "" + System.currentTimeMillis() / 1000;
		String dorderid = order_orderId;
		String userkey = MD5_HexUtil.md5Hex(tel + did + timestamp + key);

		Map<String, Object> map = new HashMap<>();
		map.put("tel".trim(), tel);
		map.put("did".trim(), did);
		map.put("dorderid".trim(), dorderid);
		map.put("timestamp".trim(), timestamp);
		map.put("userkey".trim(), userkey);
		String queryReq = HttpUtils.getStrByMapOrderByABC(map);
		return queryReq;
	}

	/**
	 * 
	 * 获取提交错误提示代码的信息
	 * 
	 * @param codes
	 * @return
	 */
	public static String getSendErrorMsg(String code) {
		HashMap<String, String> map = new HashMap<>();
		map.put("-3", "提交失败");
		map.put("-7", "参数错误，如手机号码不正确、渠道商id不存在等。");
		map.put("-2", "订单已存在");
		map.put("-21", "你当前帐户余额不足，为保证平台正常使用，请先充值");
		map.put("-22", "充值失败，原因可能是：预付费帐户余额不足、后付费帐户上月未月结等");
		map.put("-23", "你当前帐户余额不足，为保证平台正常使用，请先充值");
		map.put("-101", "认证不通过");
		map.put("-112", "请求已过期");
		map.put("-113", "请求重复");
		map.put("-115", "号码为非广东移动号码");
		return map.get(code) == null ? "" : map.get(code);
	}

	/**
	 * 获取查询错误提示代码的信息
	 * 
	 * @param codes
	 * @return
	 */
	public static String getQueryErrorMsg(String code) {
		HashMap<String, String> map = new HashMap<>();
		map.put("-100", "查询成功,表示平台接收查询,并返回相应数据");
		map.put("-101", "认证未通过");
		map.put("-105", "参数错误，比如渠道商id、运营商id或者手机号码不正确等");
		map.put("-112", "请求已过期");
		map.put("-113", "请求重复");
		return map.get(code) == null ? "" : map.get(code);
	}
}
