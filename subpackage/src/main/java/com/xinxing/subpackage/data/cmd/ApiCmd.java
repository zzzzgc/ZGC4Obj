package com.xinxing.subpackage.data.cmd;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.xinxing.subpackage.core.api.ApiCore;
import com.xinxing.subpackage.core.erorr.CheckSignException;
import com.xinxing.subpackage.core.erorr.DIYException;
import com.xinxing.subpackage.core.erorr.NULLOrderIdException;
import com.xinxing.subpackage.core.erorr.RepeatOrdersException;
import com.xinxing.subpackage.core.erorr.SystemException;
import com.xinxing.subpackage.core.po.OrderResult;
import com.xinxing.subpackage.data.utils.ResponseUtils;
/**
 * 接单接口
 * 
 * @author Administrator
 */
@Controller
@RequestMapping(value = "/api/a")
public class ApiCmd implements OrderIn {
	private static Logger log = Logger.getLogger("init Pack-enter");

	@Autowired
	ApiCore apiCore;

	@Override
	@RequestMapping(value = "/send.do", method = RequestMethod.POST)
	public void send(HttpServletRequest request, HttpServletResponse response) {
		log.info("init Pack send");
		// 对方收到空字符串说明我们出问题了,收到null为通讯问题
		String responseParam = "";
		String status = null;
		String msg = null;
		String orderId = null;
		String phone = null;
		String erorr = null;
		Map<String, String> responseMap = null;
		try {
			
			// 获取结果
			OrderResult result=null;
			result = apiCore.sendApi(request);
			
			status = result.getStatus();
			orderId = result.getOrderId();
			msg = result.getFailReason();
			if ("1".equals(status)) {
				msg = "订单提交成功";
			} else if ("2".equals(status)) {
				msg = msg == null ? "订单提交失败" : msg;
			} else {
				msg = msg == null ? "" : msg;
			}
			phone = result.getPhone();

			responseMap = new HashMap<>();
			responseMap.put("status", status);// 1成功2失败3等待11总成功
			responseMap.put("msg", msg);
			responseMap.put("orderId", orderId);
			responseParam = JSON.toJSONString(responseMap);
		} catch (CheckSignException e) {
			String[] split = e.getMessage().split(",");
			erorr = split[1];
			status =split[0];
			log.info("订单"+orderId+"异常:" + orderId+"-->" + e.getMessage());
		} catch (RepeatOrdersException e) {
			String[] split = e.getMessage().split(",");
			erorr = split[1];
			status =split[0];
			log.info("订单"+orderId+"异常:" + orderId+"-->" + e.getMessage());
		} catch (NULLOrderIdException e) {
			String[] split = e.getMessage().split(",");
			erorr = split[1];
			status =split[0];
			log.info("订单"+orderId+"异常:" + orderId+"-->" + e.getMessage());
		} catch (SystemException e) {
			String[] split = e.getMessage().split(",");
			erorr = split[1];
			status =split[0];
			log.info("订单"+orderId+"异常:" + orderId+"-->" + e.getMessage());
		} catch (DIYException e) {
			String[] split = e.getMessage().split(",");
			erorr = split[1];
			status =split[0];
			log.info("订单"+orderId+"异常:" + orderId+"-->" + e.getMessage());
		}  catch (Exception e) {
			erorr =new SystemException().getMessage();
			log.info(e);
			log.info(e.getMessage());
		}
		
		if (erorr != null) {
			responseMap = new HashMap<>();
			responseMap.put("status", status==null?"2":status);// 1成功2失败3等待11总成功
			responseMap.put("msg", erorr);
			responseMap.put("orderId", orderId);
			responseParam = JSON.toJSONString(responseMap);
			log.info("YG提单结果返回:"+responseParam);
			ResponseUtils.responseYGSet(response,responseParam);
			return;
		}
		log.info("YG提单结果返回:"+responseParam);
		ResponseUtils.responseYGSet(response,responseParam);
		
	}

	@RequestMapping(value = "/query.do", method = RequestMethod.POST)
	public void query(HttpServletRequest request, HttpServletResponse response) {
		log.info("init Pack query");
		String responseParam = "";
		String erorr = null;
		String status = null;
		String orderId = null;
		String msg = null;
		String phone = null;

		Map<String, String> responseMap = null;
		try {
			// 获取结果
			OrderResult result = apiCore.queryApi(request);
			status = result.getStatus();
			orderId = result.getOrderId();
			msg = result.getFailReason();
			phone = result.getPhone();

			Map<String, String> map = new HashMap<>();
			map.put("status", status);
			map.put("msg", msg);
			map.put("orderId", orderId);
			map.put("phone", phone);
			responseParam = JSON.toJSONString(map);
			
		} catch (NULLOrderIdException e) {
			String[] split = e.getMessage().split(",");
			erorr = split[1];
			status =split[0]; 
			log.info("订单"+orderId+"异常:" + orderId+"-->" + e.getMessage());
		} catch (SystemException e) {
			String[] split = e.getMessage().split(",");
			erorr = split[1];
			status =split[0]; 
			log.info("订单"+orderId+"异常:" + orderId+"-->" + e.getMessage());
		} catch (RepeatOrdersException e) {
			String[] split = e.getMessage().split(",");
			erorr = split[1];
			status =split[0]; 
			log.info("订单"+orderId+"异常:" + orderId+"-->" + e.getMessage());
		} catch (DIYException e) {
			String[] split = e.getMessage().split(",");
			erorr = split[1];
			status =split[0]; 
			log.info("订单"+orderId+"异常:" + orderId+"-->" + e.getMessage());
		} catch (Exception e) {
			log.info(e);
			log.info(e.getMessage());
		}

		if (erorr != null) {
			responseMap = new HashMap<>();
			responseMap.put("status", status==null?"2":status);// 1成功2失败3等待11总成功
			responseMap.put("msg", erorr);
			responseMap.put("orderId", orderId==null?"":orderId);
			responseParam = JSON.toJSONString(responseMap);
			log.info("YG查单结果返回:"+responseParam);
			ResponseUtils.responseYGSet(response,responseParam);
			return;
		}

		log.info("YG查单结果返回:"+responseParam);
		ResponseUtils.responseYGSet(response,responseParam);
	}

}
