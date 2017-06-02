package com.xinxing.o.boss.business.provider.other.zhiqutest.api;

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
import com.xinxing.o.boss.business.provider.other.zhiqutest.util.ZhiQuTestUtils;
import com.xinxing.o.boss.common.http.HttpUtils;
import com.xinxing.o.boss.common.resource.Constants;
import com.xinxing.o.boss.common.util.FlowQueryLogUtils;
import com.xinxing.o.boss.common.util.FlowSendLogUtils;

public class ZhiQuTestSendApiImpl implements SendApi {
	private static Logger log = Logger.getLogger(ZhiQuTestSendApiImpl.class);

	/*返回数据：json格式
		     字段		                 名称				说明
		status			返回状态码		0为成功，其他为失败。详情请参考5.4附录章节
		msg				返回信息		对返回状态的描述，当返回状态为失败时，该字段将返回详细的错误描述
		outOrderNum		外部订单号		与请求中的外部订单号一致，原样返回。
		orderNumber		订单号		ZHIQU平台接收流量包订购请求成功，订单成功落地后产生的订单号。该订单编号仅说明订单已经生成，并不表示流量包充值成功。
	*/
	@Override
	public SendOrderResult send(SendOrderInfo sendOrderInfo) {
		SendOrderResult result = null;
		String orderid = sendOrderInfo.getOrderId();
		try {
			String sendStr = ZhiQuTestUtils.getSendStr(sendOrderInfo);
			String sendUrl = Constants.getString("zhiqu_Url");
			//获取订单发送日志
			FlowSendLogUtils.sendReqLog(log, orderid, sendStr);
			//发送post请求后获取返回信息
			String getResp = HttpUtils.sendPostByNameValue(sendUrl,sendStr);
			//获取订单返回日志
			FlowSendLogUtils.sendResLog(log, orderid, getResp);
			if (StringUtils.isNotBlank(getResp)) {
				JSONObject obj = JSON.parseObject(getResp);
				String status = obj.getString("status");
				String msg = obj.getString("msg");
				String theirOrderId = obj.getString("orderNumber");
				switch (status) {
				case "0":
					result = new SendOrderResult(SendOrderStatus.WAIT_CALLBACK.status,null,theirOrderId);
					break;
				case "315": //余额不足转人工
					result = new SendOrderResult(SendOrderStatus.NEED_MANUAL .status,ZhiQuTestUtils.getErrorMsg("315"),theirOrderId);
					break;
				case "313": //当前账号额度已用完
					result = new SendOrderResult(SendOrderStatus.NEED_MANUAL .status,ZhiQuTestUtils.getErrorMsg("313"),theirOrderId);
					break;
				default:
					result = new SendOrderResult(SendOrderStatus.FAILED.status,msg,theirOrderId);
					break;
				}
			}else {
				result = new SendOrderResult(SendOrderStatus.NEED_MANUAL.status,"订单返回为空",orderid);
			}
		} catch (Exception e) {
			result = new SendOrderResult(SendOrderStatus.NEED_MANUAL.status, "系统异常,请找技术人员", orderid);
			FlowSendLogUtils.sendExceptionLog(log, sendOrderInfo.getOrderId(), e);
		}
		return result;
	}

	/*返回数据格式(json格式)
	   字段		  	 名称			        说明
	status		返回状态码		0为成功，其他为失败。详情请参考5.4附录章节
	msg			返回信息		对返回状态的描述，当返回状态为失败时，该字段将返回详细的错误描述
	partnerCode	商户编码		与请求中的商户编码一致，原样返回。
	outOrderNum	外部订单号		与请求中的外部订单号一致，原样返回。
	orderlist	订单列表		Jsonarray格式（如一张订单有多个号码则多个号码状态返回）
	queryTime	查询时间		请求查询的时间
	phoneNumber	手机号		orderlist里的子字段之一，手机号
	orderstatus	订单状态		orderlist里的子字段之一，订单状态（1：成功；0：失败；-1：处理中；-2：不确定）当状态为不确定的时候请联系客服人工处理。
	 */
	@Override
	public Map<String, SendOrderResult> querys(List<SendOrderInfo> sendOrderInfos) {
		Map<String,SendOrderResult> resultMap = new HashMap<>();
		SendOrderResult result = null;
		for (SendOrderInfo sendOrderInfo : sendOrderInfos) {
			String orderId = sendOrderInfo.getOrderId();
			String theirOrderId = sendOrderInfo.getSupplierOrderId();
			try {
				String queryStr = ZhiQuTestUtils.getQueryStr(sendOrderInfo);
				String queryUrl = Constants.getString("zhiqu_Url").trim();
				//获取订单查询日志
				FlowQueryLogUtils.queryReqLog(log, orderId, queryUrl+","+queryStr);
				//发送post请求后获取返回信息例子：
				/*{'msg':'订单查询成功',
				   'orderlist':[
				                {'orderstatus':0,'phoneNumber':'13719242157'}
				               ],  订单状态（1：成功；0：失败；-1：处理中；-2：不确定）当状态为不确定的时候请联系客服人工处理。
				   'outOrderNum':'LSL111',
				   'partnerCode':'FXXXXX',
				   'queryTime':'2016-07-23 17:08:46',
				   'status':'0'
			     }*/
				String getResp = HttpUtils.sendPostByNameValue(queryUrl,queryStr);
				//获取订单返回日志
				FlowQueryLogUtils.queryResLog(log, orderId, getResp);
				if (StringUtils.isNotBlank(getResp)) {
					JSONObject obj = JSON.parseObject(getResp);
					String status = obj.getString("status");
					String msg = obj.getString("msg");
					if ("0".equals(status)) {
						String orderstatus = obj.getJSONArray("orderlist").getJSONObject(0).getString("orderstatus");
						switch (orderstatus ) {
							case "1":
								result = new SendOrderResult(SendOrderStatus.SUCCESS.status,null,theirOrderId);
								break;
							case "0":
								result = new SendOrderResult(SendOrderStatus.FAILED.status,msg,theirOrderId);
								break;
							case "-1":
								result = new SendOrderResult(SendOrderStatus.WAIT_CALLBACK.status,null,theirOrderId);
								break;
							default:   // 最终结果，如-2就一直查下去
								result = new SendOrderResult(SendOrderStatus.WAIT_CALLBACK.status,null,theirOrderId);
								break;
						}
					}else if ("315".equals(status) || "313".equals(status) ) { //余额不足
						result = new SendOrderResult(SendOrderStatus.NEED_MANUAL.status,ZhiQuTestUtils.getErrorMsg(status),theirOrderId);
					}else {
						result = new SendOrderResult(SendOrderStatus.FAILED.status,msg,theirOrderId);
					}
				}else {
					result = new SendOrderResult(SendOrderStatus.WAIT_CALLBACK.status,"查询返回为空",theirOrderId);
				}
			} catch (Exception e) {
				result= new SendOrderResult(SendOrderStatus.NEED_MANUAL.status, "系统异常,请找技术人员", theirOrderId);
				FlowQueryLogUtils.queryExceptionLog(log,orderId, e);
			}
			
			resultMap.put(orderId,result);
		}
		return resultMap;
	}

	@Override
	public SendOrderResult query(SendOrderInfo sendOrderInfo) {
		List<SendOrderInfo> queryList = new ArrayList<>();
		queryList.add(sendOrderInfo);
		Map<String, SendOrderResult> resultMap = querys(queryList);
		if (resultMap != null) {
			return resultMap.get(sendOrderInfo.getOrderId());
		}
		return null;
	}

}
