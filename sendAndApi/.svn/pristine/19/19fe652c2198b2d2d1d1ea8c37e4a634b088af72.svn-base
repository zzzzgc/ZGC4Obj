package com.xinxing.o.boss.business.provider.other.yshang.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.xinxing.boss.business.api.domain.SendOrderInfo;
import com.xinxing.boss.business.api.domain.SendOrderResult;
import com.xinxing.boss.business.api.domain.SendOrderStatus;
import com.xinxing.o.boss.business.provider.api.supplier.api.SendApi;
import com.xinxing.o.boss.business.provider.other.yshang.util.YshangUtil;
import com.xinxing.o.boss.common.http.HttpUtils;
import com.xinxing.o.boss.common.resource.Constants;
import com.xinxing.o.boss.common.util.FlowQueryLogUtils;
import com.xinxing.o.boss.common.util.FlowSendLogUtils;
import com.xinxing.o.boss.common.xml.XmlUtils;

public class YshangSendApiImpl implements SendApi {

	private static final Logger logger = Logger.getLogger(YshangSendApiImpl.class); 
	/* 处理提单请求
	 * 返回
	  <?xml version="1.0" encoding="GBK"?>
		<response>
		<tbOrderNo>xxx</tbOrderNo>
		<coopOrderStatus>xxx</coopOrderStatus>
		<coopOrderNo>xxx</coopOrderNo>
		<errorCode>xxx</errorCode> 当 coopOrderStatus 为 FAILED时该值为必选，其它情况可以为空。
		<errorDesc>xxx</errorDesc> 当 coopOrderStatus 为 FAILED时该值为必选，其它情况可以为空。
		</response>
	 * @see com.xinxing.o.boss.business.provider.api.supplier.api.SendApi#send(com.xinxing.boss.business.api.domain.SendOrderInfo)
	 * @author Administrator
	 */
	@Override
	public SendOrderResult send(SendOrderInfo sendOrderInfo) {
		String tbOrderNo = sendOrderInfo.getOrderId();
		String sendUrl = Constants.getString("yshang_sendUrl").trim();
		String sendParam = YshangUtil.getSendParam(sendOrderInfo);
		//流量订购发送日志
		FlowSendLogUtils.sendReqLog(logger, tbOrderNo, sendParam);
		SendOrderResult result = null;
		try {
			//流量订单请求结果
			String respResult = HttpUtils.sendGet(sendUrl + "?" + sendParam, "utf-8");
			//流量订购返回日志
			FlowSendLogUtils.sendResLog(logger, tbOrderNo, respResult);
			if(StringUtils.isNotBlank(respResult)) {
				String coopOrderStatus = XmlUtils.getXmlNoteValue(respResult, "response/coopOrderStatus/text()");
				String coopOrderNo = XmlUtils.getXmlNoteValue(respResult, "response/coopOrderNo/text()");
				String errorCode = XmlUtils.getXmlNoteValue(respResult, "response/errorCode/text()");
				String errorDesc = XmlUtils.getXmlNoteValue(respResult, "response/errorDesc/text()");
				switch (coopOrderStatus) {
				case "SUCCESS":
				case "UNDERWAY":
					result = new SendOrderResult(SendOrderStatus.WAIT_CALLBACK.status, null, coopOrderNo);
					break;
				case "FAILED":
					if("2016".equals(errorCode)) {
						result = new SendOrderResult(SendOrderStatus.NEED_MANUAL.status, errorCode + ":" + errorDesc, coopOrderNo);
						break;
					}
					result = new SendOrderResult(SendOrderStatus.FAILED.status, errorCode + ":" + errorDesc, coopOrderNo);
					break;
				}
			} else {
				result = new SendOrderResult(SendOrderStatus.NEED_MANUAL.status, "充值结果为空", tbOrderNo);
			}
		} catch (Exception e) {
			result = new SendOrderResult(SendOrderStatus.NEED_MANUAL.status, "充值发生异常", tbOrderNo);
			//流量订购异常日志
			FlowSendLogUtils.sendExceptionLog(logger, tbOrderNo, e);
		}
		return result;
	}
	/* 处理查询请求
	 * 返回
	 * <?xml version="1.0" encoding="GBK"?>
		<response>
		<tbOrderNo>xxx</tbOrderNo>
		<coopOrderStatus>xxx</coopOrderStatus>
		<coopOrderNo>xxx</coopOrderNo>
		<errorCode>xxx</errorCode> 当 coopOrderStatus 为 FAILED时该值为必选，其它情况可以为空。
		<errorDesc>xxx</errorDesc> 当 coopOrderStatus 为 FAILED时该值为必选，其它情况可以为空。
		</response>
	 * @see com.xinxing.o.boss.business.provider.api.supplier.api.SendApi#querys(java.util.List)
	 * @author Administrator
	 */
	@Override
	public Map<String, SendOrderResult> querys(List<SendOrderInfo> sendOrderInfo) {
		Map<String, SendOrderResult> map = new HashMap<String, SendOrderResult>();
		SendOrderResult result = null;
		String tbOrderNo = null;
		String queryUrl = Constants.getString("yshang_queryUrl").trim();
		for(SendOrderInfo orderInfo : sendOrderInfo) {
			try {
				tbOrderNo = orderInfo.getOrderId();
				String queryParm = YshangUtil.getQueryParam(orderInfo);
				FlowQueryLogUtils.queryReqLog(logger, tbOrderNo, queryParm);
				//查询请求结果
				String respResult = HttpUtils.sendGet(queryUrl + "?" + queryParm, "utf-8");
				FlowQueryLogUtils.queryResLog(logger, tbOrderNo, respResult);
				if(StringUtils.isNotBlank(respResult)) {
					String coopOrderStatus = XmlUtils.getXmlNoteValue(respResult, "response/coopOrderStatus/text()");
					String coopOrderNo = XmlUtils.getXmlNoteValue(respResult, "response/coopOrderNo/text()");
					String errorCode = XmlUtils.getXmlNoteValue(respResult, "response/errorCode/text()");
					String errorDesc = XmlUtils.getXmlNoteValue(respResult, "response/errorDesc/text()");
					switch (coopOrderStatus) {
					case "SUCCESS":
						result = new SendOrderResult(SendOrderStatus.SUCCESS.status, null, coopOrderNo);
						break;
					case "FAILED":
						if("2016".equals(errorCode)) {
							result = new SendOrderResult(SendOrderStatus.NEED_MANUAL.status, errorCode + ":" + errorDesc, coopOrderNo);
							break;
						}
						result = new SendOrderResult(SendOrderStatus.FAILED.status, errorCode + ":" + errorDesc, coopOrderNo);
						break;
					case "UNDERWAY":
						result = new SendOrderResult(SendOrderStatus.WAIT_CALLBACK.status, null, coopOrderNo);
						break;
					default:
						result = new SendOrderResult(SendOrderStatus.WAIT_CALLBACK.status, null, coopOrderNo);
						break;
					}
				} else {
					result = new SendOrderResult(SendOrderStatus.WAIT_CALLBACK.status,"查询结果为空", null);
				}
			} catch (Exception e) {
				result = new SendOrderResult(SendOrderStatus.NEED_MANUAL.status, "查询出现异常", null);
				//查询异常日志
				FlowQueryLogUtils.queryExceptionLog(logger, tbOrderNo, e);
			}
			map.put(tbOrderNo, result);
		}
		return map;
	}

	@Override
	public SendOrderResult query(SendOrderInfo sendOrderInfo) {
		SendOrderResult result = null;
		List<SendOrderInfo> list = new ArrayList<SendOrderInfo>();
		list.add(sendOrderInfo);
		Map<String, SendOrderResult> map = querys(list);
		if(map != null) {
			result = map.get(sendOrderInfo.getOrderId());
		}
		return result;
	}

}
