package com.xinxing.o.boss.business.provider.other.jnlt.api;

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
import com.xinxing.o.boss.business.provider.other.jnlt.util.JNLTUtil;
import com.xinxing.o.boss.common.http.HttpUtils;
import com.xinxing.o.boss.common.resource.Constants;
import com.xinxing.o.boss.common.util.FlowQueryLogUtils;
import com.xinxing.o.boss.common.util.FlowSendLogUtils;
import com.xinxing.o.boss.common.xml.XmlUtils;
/**
 * JNLT 发送请求实现类
 * @author wuzl
 *
 */
public class JNLTSendApiImpl implements SendApi {

	private static final Logger log = Logger.getLogger(JNLTSendApiImpl.class);
	/* 发送提单请求
	 * 返回
	 * <?xml version="1.0" encoding="GBK"?>
		<response>
		<tbOrderNo>xxx</tbOrderNo>
		<coopOrderStatus>xxx</coopOrderStatus>
		<coopOrderNo>xxx</coopOrderNo>
		<errorCode>xxx</errorCode> 当 coopOrderStatus 为 FAILED时该值为必选，其它情况可以为空。
		<errorDesc>xxx</errorDesc> 当 coopOrderStatus 为 FAILED时该值为必选，其它情况可以为空。
		</response>
	 * @see com.xinxing.o.boss.business.provider.api.supplier.api.SendApi#send(com.xinxing.boss.business.api.domain.SendOrderInfo)
	 * @author wuzl
	 */
	@Override
	public SendOrderResult send(SendOrderInfo sendOrderInfo) {
		String tbOrderNo = sendOrderInfo.getOrderId();
		String sendUrl = Constants.getString("JNLT_sendUrl").trim();
		String sendParam = JNLTUtil.getSendParam(sendOrderInfo);
		FlowSendLogUtils.sendReqLog(log, tbOrderNo, sendParam);
		SendOrderResult result = null;
		try {
			String respResult = HttpUtils.sendGet(sendUrl + "?" + sendParam, "utf-8");
			FlowSendLogUtils.sendResLog(log, tbOrderNo, respResult);
			if(StringUtils.isNotBlank(respResult)) {
				String coopOrderStatus = XmlUtils.getXmlNoteValue(respResult, "response/coopOrderStatus/text()");
				String coopOrderNo = XmlUtils.getXmlNoteValue(respResult, "response/coopOrderNo/text()");
				String errorCode = XmlUtils.getXmlNoteValue(respResult, "response/errorCode/text()");
				String errorDesc = XmlUtils.getXmlNoteValue(respResult, "response/errorDesc/text()");
				switch (coopOrderStatus) {
				case "SUCCESS":
				case "UNDERWAY":
					//成功和进行中
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
			result = new SendOrderResult(SendOrderStatus.NEED_MANUAL.status, "充值出现异常", tbOrderNo);
			FlowSendLogUtils.sendExceptionLog(log, tbOrderNo, e);
		}
		return result;
	}
	/* 发送查询请求
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
	 * @author wuzl
	 */
	@Override
	public Map<String, SendOrderResult> querys(List<SendOrderInfo> sendOrderInfo) {
		Map<String, SendOrderResult> map = new HashMap<String, SendOrderResult>();
		String tbOrderNo = null;
		SendOrderResult result = null;
		String queryUrl = Constants.getString("JNLT_queryUrl").trim();
		for(SendOrderInfo orderInfo : sendOrderInfo) {
			try {
				tbOrderNo = orderInfo.getOrderId();
				String queryParam = JNLTUtil.getQueryParam(orderInfo);
				FlowQueryLogUtils.queryReqLog(log, tbOrderNo, queryParam);
				String respResult = HttpUtils.sendGet(queryUrl + "?" + queryParam, "utf-8");
				FlowQueryLogUtils.queryResLog(log, tbOrderNo, queryParam);
				if(StringUtils.isNotBlank(respResult)) {
					String coopOrderStatus = XmlUtils.getXmlNoteValue(respResult, "response/coopOrderStatus/text()");
					String coopOrderNo = XmlUtils.getXmlNoteValue(respResult, "response/coopOrderNo/text()");
					String errorCode = XmlUtils.getXmlNoteValue(respResult, "response/errorCode/text()");
					String errorDesc = XmlUtils.getXmlNoteValue(respResult, "response/errorDesc/text()");
					switch (coopOrderStatus) {
					case "SUCCESS":
						result = new SendOrderResult(SendOrderStatus.SUCCESS.status, null, coopOrderNo);
						break;
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
					case "ORDER_NOT_EXIST":
						result = new SendOrderResult(SendOrderStatus.FAILED.status, errorCode + ":" + errorDesc, coopOrderNo);
						break;
					default:
						result = new SendOrderResult(SendOrderStatus.WAIT_CALLBACK.status, null, coopOrderNo);
						break;
					}
				} else {
					result = new SendOrderResult(SendOrderStatus.WAIT_CALLBACK.status, "查询结果为空", tbOrderNo);
				}
			} catch (Exception e) {
				result = new SendOrderResult(SendOrderStatus.NEED_MANUAL.status, "查询出现异常", tbOrderNo);
				FlowQueryLogUtils.queryExceptionLog(log, tbOrderNo, e);
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
