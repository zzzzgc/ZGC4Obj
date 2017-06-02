package com.xinxing.flow.core.upstream.impl;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.TypeReference;
import com.xinxing.flow.core.downstream.TransferDownstream;
import com.xinxing.flow.core.transfer.Transfer;
import com.xinxing.flow.core.transfer.impl.Transfer_YG_CQ;
import com.xinxing.flow.core.upstream.TransferUpstream;
import com.xinxing.flow.core.upstream.tools.Transfer4HuaYi_upUtils;
import com.xinxing.flow.erorr.NULLForProductException;
import com.xinxing.flow.erorr.NULLOrderIdException;
import com.xinxing.flow.erorr.NULLResponseException;
import com.xinxing.flow.erorr.TransferSystemException;
import com.xinxing.flow.log.QueryBalanceLog;
import com.xinxing.flow.log.QueryOrderLogNorm;
import com.xinxing.flow.log.sendOrderLogNorm;
import com.xinxing.transfer.common.encry.MD5_HexUtil;
import com.xinxing.transfer.common.http.HttpUtils;
import com.xinxing.transfer.common.json.JsonUtils;
import com.xinxing.transfer.common.resource.Constants;

@SuppressWarnings("all")
public class Transfer4HuaYi_up implements TransferUpstream {
	private static Logger log = Logger.getLogger("TransferCore_Transfer4YG_up");

	@Autowired
	private Transfer transfer_YG_CQ;

	@Override
	public Map<String, String> sendOrder(Map<String, Object> sendMap) throws NULLForProductException, NULLResponseException, TransferSystemException {
		System.out.println("进入华屹sendOrder");

		Map<String, String> resJsonMap = null;
		String sendPost = null;

		String SendParam = transfer_YG_CQ.getSendParam(sendMap);

		String Sign = MD5_HexUtil.md5Hex(SendParam).toUpperCase();// 签名：
		String Token = Transfer4HuaYi_upUtils.getToken();// Token信息放在请求头中

		// 获取文件头
		HashMap<String, Object> sendRequestHead = new HashMap<>();
		sendRequestHead.put("Sign", Sign);
		sendRequestHead.put("Token", Token);

		// 获取URL
		String sendOredrUrl = Constants.getString("YG_RequestSendUrl");

		log.info("获取优狗sendOredr,提交参数,文件头:Sign->" + Sign + "----Token->" + Token + ",参数=" + SendParam);
		// 提交订单
		sendOrderLogNorm.requestLog("FlowKey", SendParam, "YG", log);
		sendPost = HttpUtils.sendPostMap1(sendOredrUrl, SendParam, sendRequestHead, "UTF-8");
		sendOrderLogNorm.responseLog("FlowKey", sendPost, "YG", log);
		if (StringUtils.isNotBlank(sendPost)) {
			return resJsonMap = JSON.parseObject(sendPost, new TypeReference<Map<String, String>>() {
			});
		}
		throw new NULLResponseException();
	}

	@Override
	public Map<String, String> queryOrder(Map<String, Object> param) throws NULLForProductException, NULLResponseException, NULLOrderIdException, TransferSystemException {
		System.out.println("进入华屹queryOrder");
		
		String sendPost=null;
		Map<String, String> resJsonMap=null;
		String QueryParam = transfer_YG_CQ.getQueryParam(param);

		String Sign = MD5_HexUtil.md5Hex(QueryParam).toUpperCase();// 签名：
		String Token = Transfer4HuaYi_upUtils.getToken();// Token信息放在请求头中

		// 获取文件头
		HashMap<String, Object> sendRequestHead = new HashMap<>();
		sendRequestHead.put("Sign", Sign);
		sendRequestHead.put("Token", Token);

		// 获取URL
		String sendOredrUrl = Constants.getString("YG_RequestQueryUrl");

		log.info("获取优狗sendOredr,提交参数,文件头:Sign->" + Sign + "----Token->" + Token + ",参数=" + QueryParam);
		// 提交订单
		QueryOrderLogNorm.requestLog("FlowKey", QueryParam, "YG", log);
		sendPost = HttpUtils.sendPostMap1(sendOredrUrl, QueryParam, sendRequestHead, "UTF-8");
		QueryOrderLogNorm.responseLog("FlowKey", sendPost, "YG", log);
		
		if (StringUtils.isNotBlank(sendPost)) {
			return resJsonMap = JSON.parseObject(sendPost, new TypeReference<Map<String, String>>(){});
		}
		throw new NULLResponseException();
	}

	@Override
	public Map<String, String> callBack(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("进入华屹callBack");
		return null;
	}

	@Override
	public Map<String, String> queryBalence(Map<String, Object> queryMap) throws TransferSystemException, NULLResponseException {
		String sendPost=null;
		Map<String, String> resJsonMap = null;
		String QueryParam = transfer_YG_CQ.getQueryBalanceParam(queryMap);

		String Sign = MD5_HexUtil.md5Hex(QueryParam).toUpperCase();// 签名：
		String Token = Transfer4HuaYi_upUtils.getToken();// Token信息放在请求头中

		// 获取请求头
		HashMap<String, Object> sendRequestHead = new HashMap<>();
		sendRequestHead.put("Sign", Sign);
		sendRequestHead.put("Token", Token);

		// 获取URL
		String sendOredrUrl = Constants.getString("YG_RequestQueryUrl");

		log.info("余额查询,请求头:Sign->" + Sign + "----Token->" + Token + ",参数=" + QueryParam);
		QueryBalanceLog.requestLog(QueryParam, "YG", log);
		sendPost = HttpUtils.sendPostMap1(sendOredrUrl, QueryParam, sendRequestHead, "UTF-8");
		QueryBalanceLog.responseLog(sendPost, "YG", log);
		
		if (StringUtils.isNotBlank(sendPost)) {
			return resJsonMap = JSON.parseObject(sendPost, new TypeReference<Map<String, String>>(){});
		}
		throw new NULLResponseException();
	}

}
