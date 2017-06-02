package com.xinxing.flow.core.TransferCallback;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.xinxing.flow.erorr.NULLParametersException;
import com.xinxing.flow.status.OrderStatus;
import com.xinxing.transfer.common.encry.MD5_HexUtil;
import com.xinxing.transfer.common.resource.Constants;

public class Callback_YG_CQ {

	public static Logger log = Logger.getLogger("回调中转站");

	public static void main(String[] args) {
		String jsonStr = "{'FlowKey':'27e96519342e93d858de','OrderKey':'153667','Phone':'13582116333','OrderStatus':'Success','FailReason':'null','VerifyCode':'C1F1E2D591EB0540AD109AC5A655B26B'}";
		/*
		 * HashMap<String,String> parseObject = JSON.parseObject(jsonStr, new
		 * TypeReference<HashMap<String,String>>() {});
		 * System.out.println(parseObject.get("FlowKey"));
		 */
	}

	/**
	 * 获取YG回调的请求参数(数据重点校验)
	 * 
	 * @param reqPostMap
	 * @return
	 * @throws NULLParametersException
	 */
	public static Map<String, Object> getCallbackRequestParam(Map<String, String> reqPostMap)
			throws NULLParametersException {
		HashMap<String, Object> YGCallbackMap = new HashMap<>();

		String FlowKey = reqPostMap.get("FlowKey");
		String OrderKey = reqPostMap.get("OrderKey");
		String Phone = reqPostMap.get("Phone");
		String OrderStatus = reqPostMap.get("OrderStatus");
		String FailReason = reqPostMap.get("FailReason") == null ? "" : reqPostMap.get("FailReason");
		String VerifyCode = reqPostMap.get("VerifyCode").replace("\n", "");

		if (FlowKey != null && OrderKey != null && Phone != null && OrderStatus != null && FailReason != null
				&& VerifyCode != null) {
			String ClientSeceret = Constants.getString("CQ_ClientSeceret");

			String jsonStr = "{\"FlowKey\":\"" + FlowKey + "\",\"OrderKey\":\"" + OrderKey + "\",\"Phone\":\"" + Phone
					+ "\",\"OrderStatus\":\"" + OrderStatus + "\",\"ClientSeceret\":\"" + ClientSeceret + "\"}";
			String SING = MD5_HexUtil.md5Hex(jsonStr).toUpperCase();

			if (SING.equals(VerifyCode)) {
				YGCallbackMap.put("FlowKey", FlowKey);
				YGCallbackMap.put("OrderKey", OrderKey);
				YGCallbackMap.put("Phone", Phone);
				YGCallbackMap.put("OrderStatus", OrderStatus);
				YGCallbackMap.put("ClientSeceret", ClientSeceret);
				return YGCallbackMap;
			}
		}

		throw new NULLParametersException();
	}

	/**
	 * 获取订单状态
	 * 
	 * @param yGRequestParamMap
	 * @return
	 * @throws NULLParametersException
	 */
	public static String getStatus(Map<String, Object> yGRequestParamMap) throws NULLParametersException {
		String status = (String) yGRequestParamMap.get("OrderStatus");
		if ("Success".equals(status)) {
			return OrderStatus.Callback_SUCCESS.status;
		} else if ("Error".equals(status)) {
			return OrderStatus.Callback_ERROR.status;
		} else {
			throw new NULLParametersException();
		}
	}

}
