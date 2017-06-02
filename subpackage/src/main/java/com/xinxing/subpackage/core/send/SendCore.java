package com.xinxing.subpackage.core.send;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xinxing.subpackage.core.erorr.DIYException;
import com.xinxing.subpackage.core.erorr.NULLOrderIdException;
import com.xinxing.subpackage.core.erorr.RepeatOrdersException;
import com.xinxing.subpackage.core.erorr.SystemException;
import com.xinxing.subpackage.core.halt.Halt;
import com.xinxing.subpackage.core.log.QueryOrderLogNorm;
import com.xinxing.subpackage.core.log.sendOrderLogNorm;
import com.xinxing.subpackage.core.po.OrderInfo;
import com.xinxing.subpackage.core.po.OrderResult;
import com.xinxing.subpackage.core.po.PackInfo;
import com.xinxing.subpackage.core.send.tools.SendUtils;
import com.xinxing.subpackage.core.status.OrderStatus;
import com.xinxing.subpackage.data.common.encry.MD5_HexUtil;
import com.xinxing.subpackage.data.common.http.HttpUtils;
import com.xinxing.subpackage.data.common.json.JsonUtils;
import com.xinxing.subpackage.data.common.resource.Constants;
import com.xinxing.subpackage.data.po.SubpackageOrderSend;
import com.xinxing.subpackage.data.service.OrderService;
import com.xinxing.subpackage.data.service.PackOrderService;

/**
 * 发单接口
 * 
 * @author Administrator
 *
 */
public class SendCore {
	private static Logger log = Logger.getLogger("subpackage-out");
	@Autowired
	OrderService orderService;
	@Autowired
	PackOrderService packOrderService;
	private static String url = Constants.getString("pack_sendUrl");
	// 商户唯一标示
	private static String MerChant = Constants.getString("pack_CustomerId");
	// 客户ID
	private static String ClientID = Constants.getString("pack_CustomerUser");
	// 客户秘钥
	private static String ClientSeceret = Constants.getString("pack_CustomerKey");
	// token缓存
	private static Map<String, String> tokenMap = new ConcurrentHashMap<String, String>(1);
	// token时间缓存
	private static Map<String, Date> tokenTimeMap = new ConcurrentHashMap<String, Date>(1);
	public static void main(String args[]) {
		/*
		 * OrderInfo info = PhoneUtils.createSendOrderInfo("testMaxF1",
		 * "10118a", "testMaxF1", "13622662311"); System.out.println(new
		 * SendCore().query(info));
		 */
		/*
		 * OrderInfo info = PhoneUtils.createSendOrderInfo("20170426003",
		 * "10118a", "20170426003", "15633201889"); System.out.println(new
		 * SendCore().query(info));
		 */
		/*
		 * OrderInfo info = PhoneUtils.createSendOrderInfo("20170426003",
		 * "10118a", "20170426003", "15633201889"); System.out.println(new
		 * SendCore().query(info));
		 */
	}
	/*
	 * 提交订单后返回数据 成功：{"Code":"2","Msg":"开始充值","Type":4}
	 * 失败：{"Code":"2","Msg":"重复提交订单","Type":1}
	 */
	public OrderResult send(OrderInfo orderInfo) throws RepeatOrdersException, NULLOrderIdException, SystemException, DIYException {
		// log.info ("分包提单 (分包出口)");
		log.info("subpackage send");
		OrderResult result = null;
		String orderId = orderInfo.getOrderId();
		String sendOrderId = orderInfo.getSendOrderId();// 赋值分包信息
		String sendProduct = orderInfo.getSendProductInfo();// 赋值分包信息
		PackInfo nextPack = null;
		SubpackageOrderSend packOrderInfo = null;
		if (sendOrderId == null) {// 没有分包信息的时候就去取(订单第一次分包提单,查询成功后和回调成功后充值的下一笔订单)
			log.info("不该出现这一段句子的,请注意,看到请联系开发!!!");
			try {
				nextPack = packOrderService.nextPack(orderId, null);
			} catch (DIYException e) {
				String[] codeAndMsg = e.getMessage().split(",");
				int code = Integer.parseInt(codeAndMsg[0]);
				String msg = codeAndMsg[0];
				if (code == 4) {
					log.info(orderId + "的" + sendOrderId + "分包订单:" + msg);
				}
			}
			sendProduct = nextPack.getPackProductId();
			sendOrderId = nextPack.getPackId();
		}
		
		//限制提单叫停校验
		if (!Halt.isCanSend()) {
			result=new OrderResult(OrderStatus.FAILURE.status, "连续失败多次,该供货商已叫停", sendOrderId, orderId, orderInfo.getPhone());
		}
		

		if (sendOrderId != null && sendProduct != null) {
			String token = getToken();
			if (StringUtils.isNotBlank(token)) {
				packOrderInfo = new SubpackageOrderSend();
				packOrderInfo.setOrderid(orderId);
				packOrderInfo.setSendorderid(sendOrderId);
				String phone = orderInfo.getPhone();
				Map<String, String> mapSend = new HashMap<>(6);
				mapSend.put("Product", sendProduct);
				mapSend.put("Mobile", phone);
				mapSend.put("FlowKey", sendOrderId);
				Map<String, String> sendMap = SendUtils.getSendParam(mapSend);
				String sendParam = sendMap.get("sendParam");
				String sign = sendMap.get("sign");
				sendMap.clear();
				sendMap.put("Token", token);
				sendMap.put("Sign", sign);
				int whichpack = Integer.parseInt(sendOrderId.split("F")[1]);
				
				sendOrderLogNorm.requestLog(sendOrderId, sendParam + "第" + whichpack + "包", MerChant, log);
				String respSend = HttpUtils.sendPostAndEn(url, sendParam, sendMap, "utf-8");
				sendOrderLogNorm.responseLog(sendOrderId, respSend + "第" + whichpack + "包", MerChant, log);

				if (StringUtils.isNotBlank(respSend)) {
					JSONObject obj = JSON.parseObject(respSend);
					String msg = obj.getString("Msg");
					String type = obj.getString("Type");
					if ("4".equals(type)) {
						result = new OrderResult(OrderStatus.WAIT.status, null, sendOrderId, orderId, phone);
					} else {
						if ("流水号重复".equals(msg)) {
							throw new RepeatOrdersException();
						}
						result = new OrderResult(OrderStatus.FAILURE.status, type + ":" + msg, sendOrderId, orderId, phone);
					}
				} else {
					result = new OrderResult(OrderStatus.NULLResponseException.status, "提交订单返回为空", sendOrderId, orderId, phone);
				}
			}
		} else {
			throw new DIYException(999, "系统异常或完结订单进入提单");
		}

		return result;
	}

	/*
	 * 查询返回信息 获取成功 {"Code":"SUCCESS","Msg":"充值成功","Type":4}
	 * {"Code":"WAIT","Msg":"充值中","Type":4}
	 * {"Code":"ERROR","Data":"订购异常","Msg":"充值失败","Type":4} 订单不存在
	 * {"Code":"2","Msg":"订单不存在","Type":1} Code为开发者ID
	 */
	public Map<String, OrderResult> querys(List<OrderInfo> list) throws SystemException, RepeatOrdersException, NULLOrderIdException, DIYException {
		// log.info("分包查单(分包出口)");
		log.info("subpackage query");
		Map<String, OrderResult> resultMap = new HashMap<>();
		Map<String, String> map = new HashMap<>();
		OrderResult result = null;
		int currPackage = 0;
		for (OrderInfo orderInfo : list) {
			String orderId = orderInfo.getOrderId();
			String sendOrderId = orderInfo.getSendOrderId();
			String phone = orderInfo.getPhone();
			String token = getToken();
			if (StringUtils.isNotBlank(token)) {
				Map<String, String> mapQuery = new HashMap<>(5);
				mapQuery.put("Mobile", phone);
				mapQuery.put("FlowKey", sendOrderId);
				map = SendUtils.getQueryParam(mapQuery);
				String queryParam = map.get("queryParam");
				String sign = map.get("sign");
				map.clear();
				map.put("Token", token);
				map.put("Sign", sign);
				currPackage = Integer.parseInt(sendOrderId.split("F")[1]);
				QueryOrderLogNorm.requestLog(sendOrderId, queryParam + "第" + currPackage + "包", MerChant, log);
				String response = HttpUtils.sendPostAndEn(url, queryParam, map, "utf-8");
				QueryOrderLogNorm.responseLog(sendOrderId, response + "第" + currPackage + "包", MerChant, log);
				if (StringUtils.isNotBlank(response)) {
					JSONObject obj = JSON.parseObject(response);
					String msg = obj.getString("Msg");
					String code = obj.getString("Code");
					switch (code) {
						case "SUCCESS" :
							PackInfo nextPack = null;
							try {
								nextPack = packOrderService.nextPack(orderId, sendOrderId);
							} catch (DIYException e) {
								String[] erorr = e.getMessage().split(",");
								if (Integer.parseInt(erorr[0]) == 02) {
									result = new OrderResult(OrderStatus.PACKSUCCEED.status, null, sendOrderId, orderId, phone);
									break;// 由于订单重复获取状态,所以就只更新分包信息
									// TODO 后期优化 :订单重复获取状态后不更新分包信息
								}
							}
							// 充值成功，分包结束
							if (nextPack == null) {
								result = new OrderResult(OrderStatus.SUCCEED.status, null, sendOrderId, orderId, phone);
								break;
							} else {
								result = new OrderResult(OrderStatus.PACKSUCCEED.status, null, sendOrderId, orderId, phone);
								// 查询充值成功开始充值下一个流量包,重点
								OrderInfo sendOrderInfo = new OrderInfo(null, nextPack.getPackProductId(), phone, nextPack.getPackId(), orderId);
								SubpackageOrderSend orderSend = packOrderService.getSubpackageOrder(nextPack.getPackId());
								if (orderSend == null) {
									OrderResult result2 = send(sendOrderInfo);
									int status = Integer.parseInt(result2.getStatus());
									SubpackageOrderSend sendOrder = new SubpackageOrderSend();
									int whichpack = Integer.parseInt(nextPack.getPackId().split("F")[1]);
									sendOrder.setStatus(status);
									sendOrder.setSendorderid(nextPack.getPackId());
									sendOrder.setOrderid(orderId);
									sendOrder.setFlowpacksize(orderInfo.getProductInfo().split(",").length);// 包数量
									sendOrder.setWhichpack(whichpack);// 分包名次
									sendOrder.setStarttime(new Date());
									if (status == 2) {
										sendOrder.setEndtime(new Date());
										if(whichpack>1){
											Halt.addStatus(nextPack.getPackId(), 2);
										}
									}
									boolean isSendAddOrder = packOrderService.addPackOrderInfo(sendOrder);

									if (!isSendAddOrder) {
										log.info("分包处理模块出现 " + "数据保存失败异常" + "请联系开发");
									}
								}
							}

							break;
						case "WAIT" :
							
							result = new OrderResult(OrderStatus.WAIT.status, null, sendOrderId, orderId, phone);
							break;
						case "ERROR" :
							result = new OrderResult(OrderStatus.FAILURE.status, msg, sendOrderId, orderId, phone);
							break;
						default :
							result = new OrderResult(OrderStatus.SystemException.status, msg, sendOrderId, orderId, phone);
							break;
					}
					if ("订单不存在".equals(msg)) {
						result.setStatus(OrderStatus.NULLOrderIdException.status);
					}
				} else {
					result = new OrderResult(OrderStatus.NULLResponseException.status, "查询返回为空", sendOrderId, orderId, phone);
				}
			}
			resultMap.put(orderId, result);
		}
		return resultMap;
	}
	public OrderResult query(OrderInfo sendOrderInfo) throws SystemException, RepeatOrdersException, NULLOrderIdException, DIYException {
		List<OrderInfo> queryList = new ArrayList<>();
		queryList.add(sendOrderInfo);
		Map<String, OrderResult> resultMap = querys(queryList);
		if (resultMap != null) {
			return resultMap.get(sendOrderInfo.getOrderId());
		}
		return null;
	}
	/**
	 * 获取YG的token
	 * 
	 * @param request
	 * @return
	 * @throws TransferSystemException
	 */
	public static String getToken() throws SystemException {
		String token = tokenMap.get(MerChant);
		String tokenParam = getTokenParam();
		Map<String, String> map = new HashMap<>(1);
		String Sign = MD5_HexUtil.md5Hex(tokenParam).toUpperCase();
		map.put("Sign", Sign);
		// 获取响应6
		log.info("获取优狗token,提交参数,文件头:" + Sign + ",参数=" + tokenParam);
		if (StringUtils.isBlank(token)) {
			String response = HttpUtils.sendPost(url, tokenParam, map);
			log.info("获取优狗token,返回参数=" + response);
			// 获取Token
			JSONObject resJsonObj = JSON.parseObject(response);
			String Type = resJsonObj.getString("Type");
			if ("4".equals(Type)) {
				token = resJsonObj.getString("Msg");
				Date time = DateUtils.addHours(new Date(), 2);
				time = DateUtils.addMinutes(time, -10);
				tokenMap.put(MerChant, token);
				tokenTimeMap.put(MerChant, time);
			} else {
				log.info("Token获取失败");
				throw new SystemException();
			}
		}
		if (tokenTimeMap.get(MerChant).before(new Date())) {
			String response = HttpUtils.sendPost(url, tokenParam, map);
			log.info("获取优狗token,返回参数=" + response);
			// 获取Token
			JSONObject resJsonObj = JSON.parseObject(response);
			String Type = resJsonObj.getString("Type");
			if ("4".equals(Type)) {
				token = resJsonObj.getString("Msg");
				Date time = DateUtils.addHours(new Date(), 2);
				time = DateUtils.addMinutes(time, -10);
				tokenMap.put(MerChant, token);
				tokenTimeMap.put(MerChant, time);
			} else {
				log.info("Token获取失败");
				throw new SystemException();
			}
		}
		return token;
	}

	/**
	 * 配置用于获取Token的参数
	 * 
	 * @return
	 */
	public static String getTokenParam() {
		Map<String, Object> map = new HashMap<>();
		// YG获取token
		map.put("Action", "GetToken");
		map.put("Version", "V1.0");
		map.put("MerChant", MerChant);
		map.put("ClientID", ClientID);
		map.put("ClientSeceret", ClientSeceret);
		return JsonUtils.obj2Json(map);
	}
}