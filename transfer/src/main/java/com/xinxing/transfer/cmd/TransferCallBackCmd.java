package com.xinxing.transfer.cmd;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.xinxing.flow.core.TransferCallback.Callback_YG_CQ;
import com.xinxing.flow.core.transfer.impl.Transfer_YG_CQ;
import com.xinxing.flow.erorr.NULLOrderException;
import com.xinxing.flow.erorr.NULLParametersException;
import com.xinxing.flow.erorr.TransferSystemException;
import com.xinxing.flow.log.CallBackOrderLogNorm;
import com.xinxing.flow.status.whoId;
import com.xinxing.transfer.common.encry.MD5_HexUtil;
import com.xinxing.transfer.common.http.HttpUtils;
import com.xinxing.transfer.common.resource.Constants;
import com.xinxing.transfer.po.TransferOrdre;
import com.xinxing.transfer.service.TransferOrdreService;

/**
 * 中央控制台中转站(动态回调AND转发站,回调站) CQ专用----华屹开发
 * 2017-03-26
 * @author ZGC 
 * @version 1.0.0V
 */
@Controller
@RequestMapping(value = "/CQExclusive")
public class TransferCallBackCmd {

	@Autowired
	TransferOrdreService TransferOrdreService;

	private static final Logger LOGGER = Logger.getLogger("CQ_TransferProscenium");
	
	/**
	 * YG-->CQ
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/CQCallback.do", method = RequestMethod.POST)
	public void getCQCallback(HttpServletRequest request, HttpServletResponse response) {
		String ip = request.getRemoteAddr();
		String allowIp = Constants.getString("CQ_allowIp");
		response.setHeader("accept", "*/*");
		//response.setCharacterEncoding("GBK");
		response.setContentType("text/html; charset=gbk");
		response.setHeader("connection", "Keep-Alive");
		response.setHeader("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
		LOGGER.info("请求ip！" + ip);
		if (!allowIp.contains(ip)) {
			LOGGER.info("请求ip未授权！！！" + ip);
			try (PrintWriter pw = response.getWriter();){
				pw.write("请求ip未授权！！！" + ip);
			} catch (IOException e) {
				LOGGER.error(e);
			}
			return;
		}
		LOGGER.info("进入YG->>CQ回调");
		String reqPostString = HttpUtils.getReqPostString(request, LOGGER);
		Map<String, String> reqPostMap = JSON.parseObject(reqPostString, new TypeReference<HashMap<String, String>>(){});
		LOGGER.info("获取YG回调YG参数:"+reqPostString);
		try {
			// request4YG
			Map<String, Object> YGRequestParamMap = Callback_YG_CQ.getCallbackRequestParam(reqPostMap);

			String DownId = (String) YGRequestParamMap.get("FlowKey");
			String status = Callback_YG_CQ.getStatus(YGRequestParamMap);
			String INTECMD = "TZCZ";

			Map<String, Object> CQRequestParamMap = Transfer_YG_CQ.getCallbackResponseParam(INTECMD, status,YGRequestParamMap);
			
			// Add SIGN  to parameters
			String CQRequestParam = HttpUtils.getStrByMapOrderByABC(CQRequestParamMap)+Constants.getString("CQ_Key");
			String SIGN = MD5_HexUtil.md5Hex(CQRequestParam).toLowerCase();
			CQRequestParamMap.put("SIGN", SIGN);
			
			// parameters
			String responserParam = HttpUtils.getStrByMapOrderByABC(CQRequestParamMap);
			LOGGER.info("responserParam:"+responserParam);
			
			// URL
			TransferOrdre orderInfo = TransferOrdreService.getOrderInfo(whoId.DOWNID.status,DownId);
			String callbackUrl;
			if (orderInfo!=null) {
				callbackUrl = orderInfo.getCallbackaddress();
			}else{
				throw new NULLOrderException();
			}
			LOGGER.info("----DownId的回调地址:"+callbackUrl+"-----");
			// callback CQ
			CallBackOrderLogNorm.requestLog(DownId, responserParam, "YG", LOGGER);
			String CQResponserParam = HttpUtils.sendPostEncode(callbackUrl, responserParam, "GBK");
			CallBackOrderLogNorm.responseLog(DownId, "收到CQ反馈:" + CQResponserParam, "YG", LOGGER);

			// CQ feedback
			Map<String, Object> feedback = HttpUtils.getReqParams(CQResponserParam);
			Object result = feedback.get("RESULT");
			if (result != null) {
				String RESULT = (String) result;
				if ("1".equals(RESULT)) {
					LOGGER.info("回调成功");
					TransferOrdre updateOrderInfo = new TransferOrdre();
					updateOrderInfo.setDownid(DownId);
					updateOrderInfo.setCallbackdata(responserParam);
					updateOrderInfo.setStatus(Integer.parseInt(status));
					updateOrderInfo.setCallbacktime(new Date());
					boolean isUpdataOrder = TransferOrdreService.updateOrder(whoId.DOWNID.status,updateOrderInfo);
					if (isUpdataOrder == false) {
						throw new TransferSystemException();
					}
				} else if ("0".equals(RESULT)) {
					LOGGER.info("回调失败");
					//TODO 添加回调无响应表, 时间调度队列调用该表 循环调用,直到该订单被接收为止
				} else {
					throw new RuntimeException("CQ回馈(回调)异常");
				}
			}else{
				throw new RuntimeException("CQ回馈(回调)异常");
			}
			// 设置响应体
			PrintWriter out = null;
			try {
				response.setHeader("accept", "*/*");
				//response.setCharacterEncoding("utf-8");
				response.setContentType("text/html; charset=utf-8");
				response.setHeader("connection", "Keep-Alive");
				response.setHeader("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
				out = response.getWriter();
				out.append("OK");
			} catch (IOException e) {
				e.printStackTrace();
			}finally {
				if (out!=null) {
					out.close();
				}
			}
		} catch (NULLParametersException e) {
			LOGGER.info("YG->CQ回调,有参数为空");
			LOGGER.error(e+":"+e.getMessage());
		} catch (TransferSystemException e) {
			LOGGER.info("YG->CQ,YG回调异常");
			LOGGER.error(e+":"+e.getMessage());
		} catch (NULLOrderException e){
			LOGGER.info("YG->CQ回调,回调订单不存在");
			LOGGER.error(e+":"+e.getMessage());
		}  catch (Exception e) {
			LOGGER.info("YG->CQ,回调异常");
			LOGGER.error(e+":"+e.getMessage());
		}
		
		//TODO 待处理, 回调没响应,需要重复的回调下游
	}

	/**
	 * test callback
	 * @param request
	 * @param response
	 */
	@RequestMapping("/SCQCallback.do")
	public void setCQCallback(HttpServletRequest request, HttpServletResponse response) {
		LOGGER.info("--------------------------回调测试START------------------------");
		String reqPostString = HttpUtils.getReqPostString(request, LOGGER);
		LOGGER.info("获取回调参数:"+reqPostString);
		Map<String, Object> reqParams =HttpUtils.getReqParams(reqPostString);
		
		LOGGER.info("reqParams"+reqParams+"------以下遍历一次");
		
		for (String a : reqParams.keySet()) {
			LOGGER.info(a+"="+reqParams.get(a));
		}
		
		String DownID =request.getParameter("CTMORDID");//取出sign后获取sign
		String SIGN = request.getParameter("SIGN");//取出sign后获取sign

		reqParams.remove("SIGN");//验证签名  需要去掉其中包含的签名
		String signStr = HttpUtils.getStrByMapOrderByABC(reqParams)+Constants.getString("CQ_Key");
		String md5Hex = MD5_HexUtil.md5Hex(signStr);
		LOGGER.info("获取SIGN"+md5Hex);
		if (md5Hex.equals(SIGN)){
			LOGGER.info("SIGN校验没问题");
		}
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.println("RESULT=1");
			LOGGER.info("------------------------------RESULT=1,回调测通----------------------------------");
		} catch (IOException e) {
			out.println("RESULT=0");
			CallBackOrderLogNorm.ExceptionLog(reqPostString, DownID, "CQ", LOGGER);
		}finally {
			if (out!=null) {
				out.close();
			}
		}

		LOGGER.info("CQ完整的收到了回调");
	}

	public static void main(String[] args) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("FlowKey", "58a87bef3efcac13efd6");
		map.put("OrderKey", "58a87bef3efcac13efd6");
		map.put("Phone", "18820036966");
		map.put("OrderStatus", "Success");
		map.put("FailReason", "");

		String strByMapOrderByABC = HttpUtils.getStrByMapOrderByABC(map);
		String VerifyCode = MD5_HexUtil.md5Hex(strByMapOrderByABC).toUpperCase();
		map.put("VerifyCode", VerifyCode);
		String strByMapOrderByABC2 = HttpUtils.getStrByMapOrderByABC(map);
		LOGGER.info("param" + strByMapOrderByABC2);
	}

	
}