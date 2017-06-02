package com.xinxing.o.boss.business.provider.other.mhan.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xinxing.boss.business.api.domain.SendOrderInfo;
import com.xinxing.boss.business.api.domain.SendOrderResult;
import com.xinxing.boss.business.api.domain.SendOrderStatus;
import com.xinxing.o.boss.business.provider.api.supplier.api.SendApi;
import com.xinxing.o.boss.business.provider.other.mhan.utils.MHANUtils;
import com.xinxing.o.boss.common.http.HttpUtils;
import com.xinxing.o.boss.common.phone.PhoneUtils;
import com.xinxing.o.boss.common.resource.Constants;
import com.xinxing.o.boss.common.util.FlowQueryLogUtils;
import com.xinxing.o.boss.common.util.FlowSendLogUtils;

public class MHANSendApiImpl implements SendApi {
	private static Logger log = Logger.getLogger(MHANSendApiImpl.class);
	public static Boolean isOurId = false;

	public static void main(String[] args) {	
		SendOrderInfo info = PhoneUtils.createSendOrderInfo("123456MyId", "4", "123456youId", "132428717621");
		System.out.println(new MHANSendApiImpl().query(info));
	}

	@Override
	public SendOrderResult send(SendOrderInfo sendOrderInfo) {
		String order_orderId = sendOrderInfo.getOrderId();
		SendOrderResult result = null;
		String supplier_orderId = isOurId ? order_orderId : null;

		String param = MHANUtils.getSendReq(sendOrderInfo);
		String url = Constants.getString("MHAN_Url");
		Map<String, String> headMap = MHANUtils.getSendHead(sendOrderInfo);
		try {
			// 提交订单
			FlowSendLogUtils.sendReqLog(log, order_orderId, "请求参数:" + param + ",请求头:" + headMap);
			String resJson = HttpUtils.sendPostTemplate(url, param, headMap, "UTF-8", 50000);
			FlowSendLogUtils.sendResLog(log, order_orderId, resJson);
	
			if (StringUtils.isNotBlank(resJson)) {
	
				JSONObject resParam = JSON.parseObject(resJson);
				String ack = resParam.getString("ack"); // 提交状态
				String msg = resParam.getString("message");
				supplier_orderId = resParam.getString("order_number");
				/**
				 * { "ack": "success", 接受状态 "message": "", 接受消息 "order_number":
				 * "LLW41.....", aa订单 "charge_amount": "1.00", 版本号
				 * "shipping_status": 1, 订单状态 "shipping_status_desc": "未充值" 订单消息 }
				 */
				if ("success".equals(ack)) {
					String status = resParam.getString("shipping_status"); // 提交订单状态
					msg = resParam.getString("shipping_status_desc");
					switch (status) {
					case "1":
					case "2":
						// 未充值和充值成功
						result = new SendOrderResult(SendOrderStatus.WAIT_CALLBACK.status, null, supplier_orderId);
						break;
					default:
						result = new SendOrderResult(SendOrderStatus.FAILED.status, "订单提交失败,core:" + status + ":" + msg,
								supplier_orderId);
						break;
					}
				} else {//余额不足
					result = new SendOrderResult(SendOrderStatus.NEED_MANUAL.status, "提交失败!,msg:" + msg, supplier_orderId);
				}
			} else {
				result = new SendOrderResult(SendOrderStatus.NEED_MANUAL.status, "提交失败,返回为空", null);
			}
		} catch (Exception e) {
			result = new SendOrderResult(SendOrderStatus.NEED_MANUAL.status, "系统异常,请找技术人员", supplier_orderId);
			FlowSendLogUtils.sendExceptionLog(log, order_orderId, e);
		}
		return result;
	}

	@Override
	public Map<String, SendOrderResult> querys(List<SendOrderInfo> sendOrderInfos) {
		Map<String, SendOrderResult> map = new HashMap<String, SendOrderResult>();
		for (SendOrderInfo sendOrderInfo : sendOrderInfos) {

			String order_orderId = sendOrderInfo.getOrderId(); // aa
			String supplise_orderId = sendOrderInfo.getSupplierOrderId(); // bb
			String orderId = isOurId ? order_orderId : supplise_orderId; // aa||bb

			SendOrderResult result = null;
			try {
				String param = MHANUtils.getQueryReq(sendOrderInfo);

				String url = Constants.getString("MHAN_Url");

				Map<String, String> headMap = MHANUtils.getQueryHead(sendOrderInfo);

				// 获取查询信息
				FlowQueryLogUtils.queryReqLog(log, order_orderId, "请求参数:" + param + ",请求头:" + headMap);
				String resJson = HttpUtils.sendPostMap(url, param, headMap);
				FlowQueryLogUtils.queryResLog(log, order_orderId, resJson);

				if (StringUtils.isNotBlank(resJson)) {
					JSONObject jsonObj = JSON.parseObject(resJson);
					String ack = jsonObj.getString("ack");
					String msg = jsonObj.getString("message");
					if ("success".equals(ack)) {
						String order_json = jsonObj.getString("order");
						JSONObject query_Json = JSON.parseObject(order_json);
						msg = query_Json.getString("shipping_status_desc");
						String status = query_Json.getString("shipping_status");
						switch (status) {
						case "4":
							result = new SendOrderResult(SendOrderStatus.SUCCESS.status, null, orderId);
							break;
						case "5":
							result = new SendOrderResult(SendOrderStatus.FAILED.status, status + ":" + msg, orderId);
							break;
						default:
							result = new SendOrderResult(SendOrderStatus.WAIT_CALLBACK.status, status + ":" + msg,
									orderId);
							break;
						}
					} else {
						result = new SendOrderResult(SendOrderStatus.NEED_MANUAL.status, "查询失败", orderId);
					}
				}else {
					result = new SendOrderResult(SendOrderStatus.WAIT_CALLBACK.status,"查询返回为空", orderId);
				}
			} catch (Exception e) {
				result = new SendOrderResult(SendOrderStatus.NEED_MANUAL.status, "系统异常,请找技术人员", orderId);
				FlowQueryLogUtils.queryExceptionLog(log, order_orderId, e);
			}
			map.put(order_orderId, result);
		}
		return map;
	}

	@Override
	public SendOrderResult query(SendOrderInfo sendOrderInfo) {
		List<SendOrderInfo> queryList = new ArrayList<>();
		queryList.add(sendOrderInfo);
		Map<String, SendOrderResult> mapRes = querys(queryList);
		if (mapRes != null) {
			return mapRes.get(sendOrderInfo.getOrderId());
		}
		return null;
	}

}
