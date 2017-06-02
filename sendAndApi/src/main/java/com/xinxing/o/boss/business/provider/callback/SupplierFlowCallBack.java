package com.xinxing.o.boss.business.provider.callback;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.xinxing.boss.business.api.domain.SendOrderResult;
import com.xinxing.boss.business.api.domain.SendOrderStatus;
import com.xinxing.boss.business.worker.domain.SupplierCallbackType;
import com.xinxing.boss.common.db.CommonDao;
import com.xinxing.boss.interaction.pojo.common.NumberSegment;
import com.xinxing.boss.interaction.pojo.common.ProductCategoryInfo;
import com.xinxing.boss.interaction.pojo.customer.OrderInfo;
import com.xinxing.o.boss.business.provider.api.chinamobile.ChinaMobileSendApiCode;
import com.xinxing.o.boss.business.provider.api.chinatelecom.TeleComSendApiCode;
import com.xinxing.o.boss.business.provider.api.chinaunicom.UniComSendApiCode;
import com.xinxing.o.boss.business.provider.other.cyue.util.CYUEUtil;
import com.xinxing.o.boss.business.provider.other.lliu.util.LliuUtils;
import com.xinxing.o.boss.business.provider.other.testsdlt.api.TestsdltSendApiImpl;
import com.xinxing.o.boss.business.provider.other.txin.util.TXINUtils;
import com.xinxing.o.boss.business.provider.other.xcheng.util.AESUtil;
import com.xinxing.o.boss.business.provider.other.zhiqutest.util.ZhiQuTestUtils;
import com.xinxing.o.boss.common.http.HttpUtils;
import com.xinxing.o.boss.common.json.JsonUtils;
import com.xinxing.o.boss.common.resource.Constants;
import com.xinxing.o.boss.common.util.FlowUtils;
import com.xinxing.o.boss.common.xml.XmlUtils;

/**
 * 供货商回调 业务处理
 * 
 * @author Administrator
 * 
 */
@SuppressWarnings("all")
@Controller
public class SupplierFlowCallBack {
	private static final Logger hzycbLog = Logger.getLogger("hzycbBack");
	private static final Logger justtestLog = Logger.getLogger("justtestBack");
	private static final Logger bigbosscxLog = Logger.getLogger("bigbosscxBack");
	private static final Logger testLog = Logger.getLogger("testBack");
	private static final Logger testsdltLog = Logger.getLogger("testsdltBack");
	private static final Logger yliangLog = Logger.getLogger("yliangBack");
	private static final Logger zhiquLog = Logger.getLogger("zhiqutestBack");
	private static final Logger MHANLog = Logger.getLogger("MHANBack");
	private static final Logger kdouLog = Logger.getLogger("kdouBack");
	private static final Logger cyueLog = Logger.getLogger("cyueBack");
	private static final Logger MCHUANLog = Logger.getLogger("MCHUANCallBack");
	private static final Logger txinLog = Logger.getLogger("txinCallBack");
	private static final Logger lliuLog = Logger.getLogger("lliuCallBack");
	private static final Logger josyLog = Logger.getLogger("josyCallBack");
	private static final Logger qgdxLog = Logger.getLogger("qgdxCallBack");
	private static final Logger hquanLog = Logger.getLogger("hquanCallBack");
	private static final Logger fbLog = Logger.getLogger("fbCallBack");
	private static final Logger bigbosscxhfLog = Logger.getLogger("bigbosscxhfBack");
	private static final Logger qwsLog = Logger.getLogger("qwsCallBack");
	private static final Logger dliLog = Logger.getLogger("dliCallBack");
	private static final Logger xchengLog = Logger.getLogger("xchengCallBack");
	
	@Autowired
	private com.xinxing.boss.business.worker.handle.a flowWorkerService;

	@Autowired
	private com.xinxing.boss.interaction.service.common.a flowCommonService;

	@Autowired
	private com.xinxing.boss.interaction.service.order.a flowOrderService;

	@Autowired
	private com.xinxing.boss.interaction.service.provider.a flowProviderService;

	@Autowired
	private CommonDao commonDao;

	/**
	 * 中国电信回调处理(广东)
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/telecom_callback.do")
	public void teleComCallBack(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String supplier = null;
		String postData = "回调参数";
		SendOrderResult result = null;
		String bossId = "A123456";// 上游 系统订单号
		String orderId = "123456";// 自有系统订单号
		if (StringUtils.isNotBlank(postData)) {
			// TODO 电信成功后业务处理
			if (StringUtils.equals("00000", "00000")) {// 充值成功
				result = new SendOrderResult(SendOrderStatus.SUCCESS.status, null, bossId);
			} else {
				String failReason = "回调错误";
				result = new SendOrderResult(SendOrderStatus.FAILED.status, failReason, bossId);
			}
			flowWorkerService.a(result, orderId, supplier, SupplierCallbackType.OUR_ID);
			PrintWriter pw = response.getWriter();
			pw.print("1");
			pw.flush();
			pw.close();
		}
	}

	/**
	 * 测试的时候接收回调
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/test_callback.do")
	public void testCallBack(HttpServletRequest request, HttpServletResponse response) throws IOException {
		testLog.info("进入测试回调");
		Map<String, String> reqParams = HttpUtils.getReqParams(request);
		testLog.info("getReqParams::" + JsonUtils.obj2Json(reqParams));
		testLog.info("getReqParams_encode::" + URLEncoder.encode(JsonUtils.obj2Json(reqParams), "UTF-8"));
		String postData = HttpUtils.getReqPostString(request, testLog);
		testLog.info("getReqPostString:" + postData);
		testLog.info("postData_encode::" + URLEncoder.encode(JsonUtils.obj2Json(postData), "UTF-8"));
		PrintWriter pw = response.getWriter();
		pw.print("testOk");
		pw.flush();
		pw.close();

	}

	/**
	 * 杭州易充宝
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/hzycb_callback.do")
	public void hzycbCallBack(HttpServletRequest request, HttpServletResponse response) throws IOException {
		hzycbLog.info("进入杭州易充宝回调");
		try {
		} catch (Exception e) {
			hzycbLog.info(e.getMessage(), e);
		}
	}

	/**
	 * 重庆电信
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/justtest_callback.do")
	public void justtestCallBack(HttpServletRequest request, HttpServletResponse response) throws IOException {
		justtestLog.info("进入重庆电信回调");
		String postData = HttpUtils.getReqPostString(request, justtestLog);
		justtestLog.info("重庆电信 参数:" + postData);
	}

	/**
	 * 大BOSS宸信 根据我们的订单号
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/bigbosscx_callback.do")
	public void bigbosscxCallBack(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String string = HttpUtils.getReqPostString(request, bigbosscxLog);
		bigbosscxLog.info("进入大BOSS宸信回调 :" + string);
		JSONObject obj = null;
		if (StringUtils.isNotBlank(string)) {
			obj = JSON.parseObject(string);
		}
		try {
			int status = obj.getInteger("status"); // 状态
			String orderId = obj.getString("outTradeNo");
			int myOrderId = Integer.parseInt(FlowUtils.getOrderId(orderId));// OrderIdBySupplierOrderId
																			// to
																			// getOrderId
			OrderInfo orderInfo = flowOrderService.j(myOrderId);// orderInfoByOrderId
																// to j
																// 根据订单ID查订单
																// CRUD
			String phone = orderInfo.getPhone();
			NumberSegment segment = flowCommonService.h(phone);// segmentByPhone
																// to h
			String operator = segment.getOperator();
			String supplier = "";
			switch (operator) {
			case "移动":
				supplier = ChinaMobileSendApiCode.BIGBOSSCX_YD.apiCode;
				break;
			case "联通":
				supplier = UniComSendApiCode.BIGBOSSCX_LT.apiCode;
				break;
			case "电信":
				supplier = TeleComSendApiCode.BIGBOSSCX_DX.apiCode;
				break;
			}

			SendOrderResult result = null;
			if (status == 4) {
				result = new SendOrderResult(SendOrderStatus.SUCCESS.status, null, null);
			} else {
				result = new SendOrderResult(SendOrderStatus.FAILED.status, status + ":" + obj.getString("failReason"),
						null);
			}
			flowWorkerService.a(result, orderId, supplier, SupplierCallbackType.OUR_ID);
			PrintWriter pw = response.getWriter();
			pw.print("OK");
			pw.flush();
			pw.close();
		} catch (Exception e) {
			bigbosscxLog.info(e.getMessage(), e);
		}
	}

	/**
	 * 测试山东联通
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	// userid=xxx&orderid=xxx&state=xxx&account=xxx&userkey=xxx
	@RequestMapping(value = "/testsdlt_callback.do")
	public void testsdltCallBack(HttpServletRequest request, HttpServletResponse response) throws IOException {
		testsdltLog.info("进入测试山东联通回调");
		Map<String, String> reqParams = HttpUtils.getReqParams(request);
		// String postData = HttpUtils.getReqPostString(request, testsdltLog);
		String postData = JsonUtils.obj2Json(reqParams);
		testsdltLog.info("测试山东联通  参数:" + postData);
		try {
			if (StringUtils.isNotBlank(postData)) {
				// Map reqParams = JsonUtils.json2Obj(postData, Map.class);
				String code = reqParams.get("state") != null ? reqParams.get("state").toString() : "";
				String msg = reqParams.get("msg") != null ? reqParams.get("msg").toString() : "";
				String providerOrderId = reqParams.get("orderid") != null ? reqParams.get("orderid").toString() : "";
				String orderId = FlowUtils.getOrderId(providerOrderId);
				OrderInfo orderInfo = flowOrderService.j(Integer.parseInt(orderId));// 如果是我们的订单号(
																					// 11111BxxxBxxx)
																					// j是根据订单id获取订单
				// int haoying_yd = 100159;// 供货商Id
				// int haoying_dx = 100054;// 供货商Id
				// int haoying_lt = 100163;// 联通
				// OrderInfo orderInfo = flowOrderService.c(haoying_yd,
				// providerOrderId); //如果是对方的订单号,并且回调数据没有手机号, 就需要使用供应商id和 对方的订单号
				String phone = orderInfo.getPhone();
				NumberSegment segment = flowCommonService.h(phone);// 根据手机号获取运营商
				String operator = segment.getOperator();
				String supplier = "";
				switch (operator) {
				case "移动":
					supplier = ChinaMobileSendApiCode.TESTSDLT_YD.apiCode;
					break;
				case "联通":
					supplier = UniComSendApiCode.TESTSDLT_LT.apiCode;
					break;
				case "电信":
					supplier = TeleComSendApiCode.TESTSDLT_DX.apiCode;
					break;
				}
				SendOrderResult result = null;
				testsdltLog.info(providerOrderId + "--状态：" + code);
				if (StringUtils.equals(code, "1")) {
					result = new SendOrderResult(SendOrderStatus.SUCCESS.status, null, providerOrderId);
				} else if (StringUtils.equals(code, "2")) {
					result = new SendOrderResult(SendOrderStatus.FAILED.status, code + ":" + msg, providerOrderId);
				} else {
					result = new SendOrderResult(SendOrderStatus.NEED_MANUAL.status, code + ":" + msg, providerOrderId);
				}
				SupplierCallbackType backType = TestsdltSendApiImpl.isOurId ? SupplierCallbackType.OUR_ID
						: SupplierCallbackType.THEIR_ID;
				flowWorkerService.a(result, providerOrderId, supplier, backType);
				PrintWriter pw = response.getWriter();
				pw.print("OK");
				pw.flush();
				pw.close();
			}
		} catch (Exception e) {
			testsdltLog.info(postData + "错误" + e.getMessage(), e);
		}
	}

	/**
	 * yliang
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	// {"did":"123456","mobile":"13454567676","dorderid":"123456","status":1,"msg":"订购成功"}
	@RequestMapping(value = "/yliang_callback.do")
	public void yliangCallBack(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String reqPost = HttpUtils.getReqPostString(request, yliangLog);
		yliangLog.info("进入yliang的回调");
		JSONObject resparam = JSON.parseObject(reqPost);
		yliangLog.info("yliang 的回调参数:" + reqPost);

		try {
			String mobile = resparam.getString("mobile") != null ? resparam.getString("mobile").trim() : "";// 手机
			String dorderid = resparam.getString("dorderid") != null ? resparam.getString("dorderid").trim() : "";// AAid
			String status = resparam.getInteger("status") != null ? resparam.getString("status").trim() : "";// 状态
			String msg = resparam.getString("msg") != null ? resparam.getString("msg").trim() : "";// 信息

			String orderId = FlowUtils.getOrderId(dorderid);
			OrderInfo orderInfo = flowOrderService.j(Integer.parseInt(orderId));

			yliangLog.info(dorderid + "--状态：" + status + "--");

			// 获取运营商
			String phone = orderInfo.getPhone();
			NumberSegment segment = flowCommonService.h(phone);
			String operator = segment.getOperator();
			/**
			 * OrderInfo orderInfo =
			 * flowOrderService.j(myOrderId);//orderInfoByOrderId to j String
			 * phone = orderInfo.getPhone(); NumberSegment segment =
			 * flowCommonService.h(phone);//segmentByPhone to h
			 */

			String supplier = "";
			switch (operator) {
			case "移动":
				supplier = ChinaMobileSendApiCode.YLIANG_YD.apiCode;
				break;
			case "联通":
				supplier = UniComSendApiCode.YLIANG_LT.apiCode;
				break;
			case "电信":
				supplier = TeleComSendApiCode.YLIANG_DX.apiCode;
				break;
			}

			SendOrderResult result = null;

			switch (status) {
			case "1":
				result = new SendOrderResult(SendOrderStatus.SUCCESS.status, null, dorderid);
				break;
			case "2":
				result = new SendOrderResult(SendOrderStatus.FAILED.status, msg, dorderid);
				break;
			default:
				result = new SendOrderResult(SendOrderStatus.NEED_MANUAL.status, "返回状态异常,请找开发人员调整系统", dorderid);
			}
			// 修改数据库中订单的状态
			flowWorkerService.a(result, dorderid, supplier, SupplierCallbackType.OUR_ID);

			PrintWriter writer = response.getWriter();
			writer.print("0");
			writer.flush();
			writer.close();
		} catch (Exception e) {
			yliangLog.info(reqPost + "yliang回调错误,请找技术:" + e.getMessage(), e);
		}
	}

	/**
	 * zhiqu测试流量订购回调接口
	 * @author tjm
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	/**
	 * 回调时接受的数据 ,post传参，request中参数为a=A&b=B格式
	 * sign 		签名 		签名，32位MD5大写的字符串。
	 * status 		返回状态码   0为成功，其他为失败。
	 * phoneNumber  手机号码 	本次充值手机号码 
	 * msg 			返回信息	 对返回状态的描述。当返回状态为失败时，该字段应返回详细的错误描述
	 * outOrderNum  外部订单号 	原样返回。
	 * orderNumber 	订单号 	原样返回。
	 */
	@RequestMapping(value = "/zhiqu_callback.do")
	public void zhiquCallback(HttpServletRequest request, HttpServletResponse response) throws IOException {
		zhiquLog.info("进入zhiqu的回调");
		String temp = HttpUtils.getReqPostString(request, zhiquLog);
		zhiquLog.info("测试zhiqu的回调参数 ：" + temp);
		Map<String, String> tempMap = HttpUtils.getReqParams(temp);
		SendOrderResult result = null;
		String msg = "";
		String phone = "";
		String status = "";
		String theirOrderId = "";
		String ourOrderId = "";
		String supplier = "";
		// 上游所需参数
		String respStatus = "";
		String respMsg = "";
		try {
			if (!tempMap.isEmpty()) {
				msg = tempMap.get("msg");
				phone = tempMap.get("phoneNumber");
				status = tempMap.get("status");
				ourOrderId = tempMap.get("outOrderNum");
				theirOrderId = tempMap.get("orderNumber");
				zhiquLog.info(ourOrderId + " 状态 ：" + status);
				NumberSegment segment = flowCommonService.h(phone);// 根据手机号获取运营商
				String operator = segment.getOperator();
				switch (operator) {
				case "移动":
					supplier = ChinaMobileSendApiCode.ZHIQU_YD.apiCode;
					break;
				case "联通":
					supplier = UniComSendApiCode.ZHIQU_LT.apiCode;
					break;
				case "电信":
					supplier = TeleComSendApiCode.ZHIQU_DX.apiCode;
					break;
				}
				if ("0".equals(status)) {
					result = new SendOrderResult(SendOrderStatus.SUCCESS.status, null, theirOrderId);
					// 返回给上游的数据
					respStatus = "0";
					respMsg = "成功";
				} else if ("315".equals(status) || "313".equals(status)) { // 余额不足转人工
					result = new SendOrderResult(SendOrderStatus.NEED_MANUAL.status, ZhiQuTestUtils.getErrorMsg(status),
							theirOrderId);
					respStatus = "1";
					respMsg = "失败";
				} else {
					result = new SendOrderResult(SendOrderStatus.FAILED.status, "status:" + status + "," + msg, theirOrderId);
					respStatus = "2";
					respMsg = "失败";
				}
				flowWorkerService.a(result, ourOrderId, supplier, SupplierCallbackType.OUR_ID);
				String tempJson = "{\"status\":\"" + respStatus + "\",\"message\":\"" + respMsg + "\"}";
				String respJson = new String(tempJson.getBytes(), "utf-8");
				PrintWriter pw = response.getWriter();
				pw.print(respJson);
				pw.flush();
				pw.close();
			}
		} catch (Exception e) {
			zhiquLog.info(temp + "zhiqu回调错误,请找技术人员:" + e.getMessage(), e);
		}
	}

	/**
	 * MHAN
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	/*
	 * 请求头参数: API-SIGNATURE: $SIGNATURE
	 */
	@SuppressWarnings("all")
	@RequestMapping(value = "/MHAN_callback.do")
	public void MHANBack(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map reqParams = HttpUtils.getReqParams(request);
	    System.out.println(reqParams);
	    MHANLog.info("reqParams:" + reqParams);

	    String order_number = (String)reqParams.get("order_number");
	    String shipping_status = (String)reqParams.get("shipping_status");
	    String shipping_status_desc = (String)reqParams.get("shipping_status_desc");
	    String shipping_status_message = (String)reqParams.get("shipping_status_message");
	    Map map = null;
	    try {
	      MHANLog.info("进入MHAN的回调");
	      
	      map = new HashMap();
	      map.put("order_number", order_number);
	      map.put("shipping_status", shipping_status);
	      map.put("shipping_status_desc", shipping_status_desc);
	      map.put("shipping_status_message", shipping_status_message);

	      MHANLog.info("MHAN的回调参数:" + map);
	    } catch (Exception e) {
	      MHANLog.error("MHAN回调异常:" + e.getMessage() + ",异常:" + e);
	    }


		String status = null;
		String msg = null;
		String supplier_id = null;

		if (map != null && map.size() > 0) {
			status = (String) map.get("shipping_status");
			msg = (String) map.get("shipping_status_desc");
			supplier_id = (String) map.get("order_number");

			try {
				// String orderId = FlowUtils.getOrderId(supplier_id);

				int yd_supplier_Id = 100012;
				int lt_supplier_Id = 100014;
				int dx_supplier_Id = 100013;

				String supplier = null;
				OrderInfo orderInfo = this.flowOrderService.c(yd_supplier_Id, supplier_id);
				if (orderInfo != null) {
					supplier = ChinaMobileSendApiCode.MHAN_YD.apiCode;
				} else {
					orderInfo = this.flowOrderService.c(lt_supplier_Id, supplier_id);
					if (orderInfo != null) {
						supplier = UniComSendApiCode.MHAN_LT.apiCode;
					} else {
						orderInfo = this.flowOrderService.c(dx_supplier_Id, supplier_id);
						if (orderInfo != null)
							supplier = TeleComSendApiCode.MHAN_DX.apiCode;
						else {
							new Exception("------------------------------回调,查无此单!!!订单id:" + supplier_id
									+ "------------------------------");
						}
					}
				}

				SendOrderResult result = null;

				switch (status) {
				case "4":
					result = new SendOrderResult(SendOrderStatus.SUCCESS.status, null, supplier_id);
					break;
				case "5":
					result = new SendOrderResult(SendOrderStatus.FAILED.status, status + ":" + msg, supplier_id);
					break;
				default:
					result = new SendOrderResult(SendOrderStatus.WAIT_CALLBACK.status, status + ":" + msg, supplier_id);
					break;
				}
				// 修改数据库中订单的状态,返回状态,订单ID ,运营商,id类型
				flowWorkerService.a(result, supplier_id, supplier, SupplierCallbackType.THEIR_ID);
				PrintWriter pw = response.getWriter();
				pw.print("received");
				pw.flush();
				pw.close();
			} catch (Exception e) {
				MHANLog.info(map + "HMIA回调错误,请找技术:" + e.getMessage(), e);
			}
		}
	}

	/**
	 * kdou流量订购回调接口
	 * @author tjm
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	/**
	 * 回调时接受的数据, 接受get传参
	 * ,示例：http://127.0.0.1/result?oid=orderid&phone=13800000000&code=200
	 *  oid 	string 	商户的订单号
	 *  phone 	string 	充值号码
	 *  code 	int 	充值结果    200：成功，400：失败
	 * 需返回给上游的数据： success （表示收到回调）
	 */
	@RequestMapping(value = "/kdou_callback.do")
	public void kdouCallback(HttpServletRequest request, HttpServletResponse response) throws IOException {
		kdouLog.info("进入kdou的回调");
		Map<String, String> reqMap = HttpUtils.getReqParams(request);
		kdouLog.info("kdou的回调参数 ：" + reqMap);
		SendOrderResult result = null;
		String ourOrderId = "";
		String phone = "";
		int code = 0;
		String supplier = "";
		try {
			if (!reqMap.isEmpty()) {
				ourOrderId = reqMap.get("oid");
				phone = reqMap.get("phone");
				code = Integer.parseInt(reqMap.get("code"));
				NumberSegment segment = flowCommonService.h(phone);// 根据手机号获取运营商
				String operator = segment.getOperator();
				switch (operator) {
				case "移动":
					supplier = ChinaMobileSendApiCode.KDOU_YD.apiCode;
					break;
				case "联通":
					supplier = UniComSendApiCode.KDOU_LT.apiCode;
					break;
				case "电信":
					supplier = TeleComSendApiCode.KDOU_DX.apiCode;
					break;
				}
				if (code == 200) {
					result = new SendOrderResult(SendOrderStatus.SUCCESS.status, null, null);
				} else {
					result = new SendOrderResult(SendOrderStatus.FAILED.status, "code:" + code, null);
				}
				flowWorkerService.a(result, ourOrderId, supplier, SupplierCallbackType.OUR_ID);
				PrintWriter pw = response.getWriter();
				pw.print("success");
				pw.flush();
				pw.close();
			}
		} catch (Exception e) {
			kdouLog.info(reqMap + "kdou测试回调错误,请找技术人员:" + e.getMessage(), e);
		}
	}

	/**cyue回调接口
	 * @author tjm
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	/**
	 * 回调时接受的数据 ,加密!post传参，请求参数为a=A&b=B格式
	 *  字段 	      类型
	 *  param String 
	 *  字段 类型 说明 
	 *  order_id 		 string 订单ID 
	 *  out_order_id 	 string 接入方订单ID 
	 *  status			 string 订单状态0-成功，3-失败
	 *  completion_time  string 订单完成时间，格式yyyyMMddHHmmss 
	 *  err_desc 		 string 错误描述
	 * 
	 * 需返回给上游的数据： "0" （表示收到回调）
	 */
	@RequestMapping(value = "/cyue_callback.do")
	public void cyueCallback(HttpServletRequest request, HttpServletResponse response) throws IOException {
		cyueLog.info("进入cyue的回调,回调参数为：");
		String temp = HttpUtils.getReqPostString(request, cyueLog);
		Map<String, String> paramMap = HttpUtils.getReqParams(temp);
		String cyue_key = Constants.getString("cyue_key");
		String cyue_key_all = Constants.getString("cyue_key_all");
		// param="xzWzgwzOsS6mx2I9RuzQ.........."(需解密)
		String param = paramMap.get("param");
		String order_id = paramMap.get("order_id");
		SendOrderResult result = null;
		String msg = "";
		String status = "";
		String supplier = "";
		String ourOrderId = "";
		String theriOrderId = "";
		try {
			if (StringUtils.isNotBlank(param) || StringUtils.isNotBlank(order_id)) {
				if (StringUtils.isNotBlank(param) ) {
					// 对param进行解密获取json字符串
					String jsonStr;
					jsonStr = CYUEUtil.decrypt(URLDecoder.decode(URLDecoder.decode(param)), cyue_key);
					JSONObject obj = JSON.parseObject(jsonStr);
					msg = obj.getString("err_desc");
					status = obj.getString("status");
					ourOrderId = obj.getString("out_order_id");
					theriOrderId = obj.getString("order_id");
				}else { //全国账号不加密，不需要解密
					msg = paramMap.get("err_desc");
					status = paramMap.get("status");
					ourOrderId = paramMap.get("out_order_id");
					theriOrderId = paramMap.get("order_id");
				}
				
				// 根据我方订单id获取我方订单信息
				OrderInfo orderInfo = flowOrderService.j(Integer.parseInt(FlowUtils.getOrderId(ourOrderId)));
				NumberSegment segment = flowCommonService.h(orderInfo.getPhone());// 根据手机号获取运营商
				String operator = segment.getOperator();
				switch (operator) {
				case "移动":
					supplier = ChinaMobileSendApiCode.CYUE_YD.apiCode;
					break;
				case "联通":
					supplier = UniComSendApiCode.CYUE_LT.apiCode;
					break;
				case "电信":
					supplier = TeleComSendApiCode.CYUE_DX.apiCode;
					break;
				}
				if ("0".equals(status)) {
					result = new SendOrderResult(SendOrderStatus.SUCCESS.status, null, theriOrderId);
				} else {
					result = new SendOrderResult(SendOrderStatus.FAILED.status,"充值失败", theriOrderId);
				}
				flowWorkerService.a(result, theriOrderId, supplier, SupplierCallbackType.THEIR_ID);
				PrintWriter pw = response.getWriter();
				pw.print("0");
				pw.flush();
				pw.close();
			}
		} catch (Exception e) {
			cyueLog.info(temp + "cyue回调错误,请找技术人员:" + e.getMessage(), e);
		}
	}

	/**
	 * MCHUAN
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@SuppressWarnings("all")
	@RequestMapping(value = "/MCHUAN_callback.do")
	public void MCHUANBack(HttpServletRequest request, HttpServletResponse response) throws IOException {

		MCHUANLog.info("进入MCHUAN的回调");
		String reqPostString = HttpUtils.getReqPostString(request, MCHUANLog);

		try {
			if (StringUtils.isNotBlank(reqPostString)) {
				// <root><status taskid="1703131223014021840" code="9999"
				// message="失败" time="2017-03-13 13:48:51"/></root>
				// <root><status taskid="1703141507144614340" code="0"
				// message="success" time="2017-03-14 15:08:19"/></root>
				/*NamedNodeMap attributes = XmlUtils.getXmlNoteList(reqPostString, "root/status").item(0)
						.getAttributes();*/
				NodeList xmlNoteList = XmlUtils.getXmlNoteList(reqPostString, "root/status");
				for (int i = 0; i < xmlNoteList.getLength(); i++) {
					Node item = xmlNoteList.item(i);
					NamedNodeMap attributes = item.getAttributes();
					String status = attributes.getNamedItem("code").getNodeValue();
					String msg = attributes.getNamedItem("message").getNodeValue();
					String supplier_id = attributes.getNamedItem("taskid").getNodeValue(); // aaID
					// 正式站的配置
					int yd_supplier_Id = 100018;
					int lt_supplier_Id = 100019;
					int dx_supplier_Id = 100020;
					// 测试站的配置
					/*
					 * int yd_supplier_Id=100220; int lt_supplier_Id=100221; int
					 * dx_supplier_Id=100217;
					 */

					MCHUANLog.info(supplier_id + "--状态：" + status + "--");

					// 获取充值手机运营商简称
					String supplier = null;
					OrderInfo orderInfo = flowOrderService.c(yd_supplier_Id, supplier_id); // yd
					if (orderInfo != null) {
						supplier = ChinaMobileSendApiCode.MCHUAN_YD.apiCode;
					} else {

						orderInfo = flowOrderService.c(lt_supplier_Id, supplier_id); // lt
						if (orderInfo != null) {
							supplier = UniComSendApiCode.MCHUAN_LT.apiCode;
						} else {

							orderInfo = flowOrderService.c(dx_supplier_Id, supplier_id); // lt
							if (orderInfo != null) {
								supplier = TeleComSendApiCode.MCHUAN_DX.apiCode;
							} else {
								new Exception("------------------------------回调,查无此单!!!订单id:" + supplier_id
										+ "------------------------------");
							}
						}
					}
					SendOrderResult result = null;
					switch (status) {
					case "0":
						result = new SendOrderResult(SendOrderStatus.SUCCESS.status, null, supplier_id);
						break;
					default:
						result = new SendOrderResult(SendOrderStatus.FAILED.status,
								"充值失败,crde:(" + status + "),msg->" + msg, supplier_id);
						break;
					}
					
					/**
					 * 第一个参数是 订单状态 ,供应商简称,上游id,用谁的id(id类型)
					 */
					flowWorkerService.a(result, supplier_id, supplier, SupplierCallbackType.THEIR_ID);
					
				}
				PrintWriter out = response.getWriter();
				out.print("success");
				out.flush();
				out.close();
				
				
			}
		} catch (Exception e) {
			MCHUANLog.info(reqPostString + "MCHUAN回调错误,请找技术:" + e.getMessage(), e);
		}
	}

	/**
	 * TXIN回调
	 * @author tjm
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	/*
	 * 回调举例：
	 * http://127.0.0.1/Test/UrlReturnTest.jsp?sid=RO201312051123455645&...
	 */
	@RequestMapping(value = "/txin_callback.do")
	public void txinCallback(HttpServletRequest request, HttpServletResponse response) throws IOException {
		txinLog.info("进入txin的回调");
		Map<String, String> reqMap = HttpUtils.getReqParams(request);
		txinLog.info("txin的回调参数 ：" + reqMap);
		SendOrderResult result = null;
		String ste = "";
		String temp = "";
		String phone = "";
		String supplier = "";
		String ourOrderId = "";
		String theirOrderId = "";
		try {
			if (!reqMap.isEmpty()) {
				ste = reqMap.get("ste");
				phone = reqMap.get("pn");
				ourOrderId = reqMap.get("oid");
				theirOrderId = reqMap.get("sid");

				NumberSegment segment = flowCommonService.h(phone);// 根据手机号获取运营商
				String operator = segment.getOperator();
				switch (operator) {
				case "移动":
					supplier = ChinaMobileSendApiCode.TXIN_YD.apiCode;
					break;
				case "联通":
					supplier = UniComSendApiCode.TXIN_LT.apiCode;
					break;
				case "电信":
					supplier = TeleComSendApiCode.TXIN_DX.apiCode;
					break;
				}
				// 对sign进行md5校验
				if (TXINUtils.checkoutMD5(reqMap)) {
					if ("0".equals(ste)) {
						result = new SendOrderResult(SendOrderStatus.SUCCESS.status, null, theirOrderId);
						temp = "success";
					} else {
						result = new SendOrderResult(SendOrderStatus.FAILED.status, "ste:" + ste, theirOrderId);
						temp = "failed";
					}
				} else {
					result = new SendOrderResult(SendOrderStatus.FAILED.status, "MD5校验不正确", theirOrderId);
					temp = "failed";
				}
				flowWorkerService.a(result, theirOrderId, supplier, SupplierCallbackType.THEIR_ID);
				String sendStr = new String(temp.getBytes(), "GBK");
				PrintWriter pw = response.getWriter();
				pw.print(sendStr);
				pw.flush();
				pw.close();
			}
		} catch (Exception e) {
			txinLog.info(reqMap + "txin测试回调错误,请找技术人员:" + e.getMessage(), e);
		}
	}

	/** LLIU回调
	 * @author tjm
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	/*
	 * { "orderstatus": "finish", "phone_id": "18137817158", "ordertime":
	 * "2014-11-17 13:36:18", "result_code": "00000", "order_id":
	 * "P2014111713333700000228", "plat_offer_id": "TBC00005000A",
	 * "transactionid": “Q2014111713333710000269”, “result_code”: “成功” }
	 */
	@RequestMapping(value = "/lliu_callback.do")
	public void lliuCallback(HttpServletRequest request, HttpServletResponse response) throws IOException {
		lliuLog.info("进入lliu的回调");
		String reqJson = HttpUtils.getReqPostString(request, lliuLog);
		lliuLog.info("lliu的回调参数 ：" + reqJson);
		SendOrderResult result = null;
		String phone = "";
		String supplier = "";
		String ourOrderId = "";
		String orderstatus = "";
		String result_code = "";
		String theirOrderId = "";
		try {
			if (StringUtils.isNotBlank(reqJson)) {
				JSONObject obj = JSON.parseObject(reqJson);
				phone = obj.getString("phone_id");
				ourOrderId = obj.getString("order_id");
				orderstatus = obj.getString("orderstatus");
				result_code = obj.getString("result_code");
				theirOrderId = obj.getString("transactionid");
				String msg = LliuUtils.getErrorMsg(result_code);

				NumberSegment segment = flowCommonService.h(phone);// 根据手机号获取运营商
				String operator = segment.getOperator();
				switch (operator) {
				case "移动":
					supplier = ChinaMobileSendApiCode.LLIU_YD.apiCode;
					break;
				case "联通":
					supplier = UniComSendApiCode.LLIU_LT.apiCode;
					break;
				case "电信":
					supplier = TeleComSendApiCode.LLIU_DX.apiCode;
					break;
				}

				switch (orderstatus) {
				case "finish":
					result = new SendOrderResult(SendOrderStatus.SUCCESS.status, null, theirOrderId);
					break;
				case "processing":
					result = new SendOrderResult(SendOrderStatus.WAIT_CALLBACK.status, null, theirOrderId);
					break;
				case "fail":
					if ("60002".equals(result_code)) { // 余额不足
						result = new SendOrderResult(SendOrderStatus.NEED_MANUAL.status, msg, theirOrderId);
					} else {
						result = new SendOrderResult(SendOrderStatus.FAILED.status, msg, theirOrderId);
					}
					break;
				}
				flowWorkerService.a(result, ourOrderId, supplier, SupplierCallbackType.OUR_ID);
				PrintWriter pw = response.getWriter();
				pw.print("1");
				pw.flush();
				pw.close();
			}
		} catch (Exception e) {
			lliuLog.info(reqJson + "lliu测试回调错误,请找技术人员:" + e.getMessage(), e);
		}
	}

	/**
	 * JOSY回调
	 * @author tjm
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	/*
	 * tradeno=170401B0B153632&mobile=13622662311&status=1 tradeno 充值时渠道提交的订单号
	 * mobile 手机号 status 1：充值成功 2或3：充值失败
	 */
	@RequestMapping(value = "/josy_callback.do")
	public void josyCallback(HttpServletRequest request, HttpServletResponse response) throws IOException {
		josyLog.info("进入josy的回调");
		String temp = HttpUtils.getReqPostString(request, josyLog);
		Map<String, String> map = HttpUtils.getReqParams(temp);
		SendOrderResult result = null;
		String status = "";
		String mobile = "";
		String supplier = "";
		String ourOrderId = "";
		try {
			if (!map.isEmpty()) {
				mobile = map.get("mobile");
				status = map.get("status");
				ourOrderId = map.get("tradeno");

				NumberSegment segment = flowCommonService.h(mobile);// 根据手机号获取运营商
				String operator = segment.getOperator();
				switch (operator) {
				case "移动":
					supplier = ChinaMobileSendApiCode.JOSY_YD.apiCode;
					break;
				case "联通":
					supplier = UniComSendApiCode.JOSY_LT.apiCode;
					break;
				case "电信":
					supplier = TeleComSendApiCode.JOSY_DX.apiCode;
					break;
				}
				if ("1".equals(status)) {
					result = new SendOrderResult(SendOrderStatus.SUCCESS.status, null, null);
				} else if ("2".equals(status) || "3".equals(status)) {
					result = new SendOrderResult(SendOrderStatus.FAILED.status, "status:" + status, null);
				} else {
					result = new SendOrderResult(SendOrderStatus.WAIT_CALLBACK.status, null, null);
				}
				flowWorkerService.a(result, ourOrderId, supplier, SupplierCallbackType.OUR_ID);
				PrintWriter pw = response.getWriter();
				pw.print("ok");
				pw.flush();
				pw.close();
			}
		} catch (Exception e) {
			josyLog.info(temp + "josy测试回调错误,请找技术人员:" + e.getMessage(), e);
		}
	}
	/**  QGDX接口
	 * @author tjm
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	/*回调返回样例：  {   "failReason": "充值失败",
				 	"outTradeNo": "us0pt4lw5w0dtj8i3x4dx71hej79",
				 	"sign": "7061b61b933cc2777b5b6649be2cdbb9",
				 	"status": 5,
				 	"ts": 1472181871485 }  */
	@RequestMapping(value = "/qgdx_callback.do")
	public void qgdxCallback(HttpServletRequest request, HttpServletResponse response) throws IOException {
		qgdxLog.info("进入qgdx的回调");
		qgdxLog.info("qgdx的回调参数：");
		String reqJson = HttpUtils.getReqPostString(request, qgdxLog);
		SendOrderResult result = null;
		String ts = "";
		String sign = "";
		String status = "";
		String failReason = "";
		String ourOrderId = "";
		
		String supplier = "";
		try {
			if (StringUtils.isNotBlank(reqJson)) {
				JSONObject obj = JSON.parseObject(reqJson);
				ts = obj.getString("ts");
				sign = obj.getString("sign");
				status = obj.getString("status");
				failReason = obj.getString("failReason");
				ourOrderId = obj.getString("outTradeNo");
				String msg = failReason;
				//根据我方id获取我方订单信息
				int myOrderId = Integer.parseInt(FlowUtils.getOrderId(ourOrderId));
				OrderInfo orderInfo = flowOrderService.j(myOrderId);

				NumberSegment segment = flowCommonService.h(orderInfo.getPhone());// 根据手机号获取运营商
				String operator = segment.getOperator();
				switch (operator) {
				case "移动":
					supplier = ChinaMobileSendApiCode.QGDX_YD.apiCode;
					break;
				case "联通":
					supplier = UniComSendApiCode.QGDX_LT.apiCode;
					break;
				case "电信":
					supplier = TeleComSendApiCode.QGDX_DX.apiCode;
					break;
				}	
				boolean mark = false;
				if (failReason != null) {
					mark = failReason.contains("余额不足");
				}
				if ("4".equals(status)) {
					result = new SendOrderResult(SendOrderStatus.SUCCESS.status, null, null);
				}else if (mark) { //余额不足转人工
					result = new SendOrderResult(SendOrderStatus.NEED_MANUAL.status,failReason,null);
				}else{
					result = new SendOrderResult(SendOrderStatus.FAILED.status, failReason, null);
				}
				flowWorkerService.a(result, ourOrderId, supplier, SupplierCallbackType.OUR_ID);
				PrintWriter pw = response.getWriter();
				pw.print("OK");
				pw.flush();
				pw.close();
			}
		} catch (Exception e) {
			qgdxLog.info(reqJson + "qgdx回调错误,请找技术人员:" + e.getMessage(), e);
		}
	}
	
	/**
	 * HQUAN回调
	 * @author tjm
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	/*  post传参，格式 A=a&B=b&...
	 *  orderId 	    string	HQUAN流量平台订单号
		customerOrderId	string	客户订单号
		phoneNo			string	手机号
		spec			int	流量规格 (整数, 单位: MB)
		scope			string	范围 (国内:nation, 省内:province)
		status			string	状态(充值成功：success, 充值失败：fail)
		signature		string	签名*/
	@RequestMapping(value = "/hquan_callback.do")
	public void hquanCallback(HttpServletRequest request, HttpServletResponse response) throws IOException {
		hquanLog.info("进入hquan的回调");
		hquanLog.info("hquan的回调参数 ：");
		String temp = HttpUtils.getReqPostString(request, hquanLog);
		Map<String, String> tempMap = HttpUtils.getReqParams(temp);
		SendOrderResult result = null;
		String phone = "";
		String status = "";
		String ourOrderId = "";
		String theirOrderId = "";
		String supplier = "";
		// 上游所需参数
		String sendBackStr = "";
		try {
			if (!tempMap.isEmpty()) {
				phone = tempMap.get("phoneNo");
				status = tempMap.get("status");
				ourOrderId = tempMap.get("customerOrderId");
				theirOrderId = tempMap.get("orderId");
				
				NumberSegment segment = flowCommonService.h(phone);// 根据手机号获取运营商
				String operator = segment.getOperator();
				switch (operator) {
				case "移动":
					supplier = ChinaMobileSendApiCode.HQUAN_YD.apiCode;
					break;
				case "联通":
					supplier = UniComSendApiCode.HQUAN_LT.apiCode;
					break;
				case "电信":
					supplier = TeleComSendApiCode.HQUAN_DX.apiCode;
					break;
				}
				if ("success".equals(status)) {
					result = new SendOrderResult(SendOrderStatus.SUCCESS.status, null, ourOrderId);
					// 返回给上游的数据
					sendBackStr = "success";
				} else {
					result = new SendOrderResult(SendOrderStatus.FAILED.status,"充值失败",ourOrderId);
					sendBackStr = "fail";
				}
				flowWorkerService.a(result,ourOrderId, supplier, SupplierCallbackType.OUR_ID);
				
				String respCode = new String(sendBackStr.getBytes(), "utf-8");
				PrintWriter pw = response.getWriter();
				pw.print(respCode);
				pw.flush();
				pw.close();
			}
		} catch (Exception e) {
			hquanLog.info("hquan回调错误,请找技术人员:" + e.getMessage(),e);
		}
	}
	
	
	/**
	 * 分包供应商
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/fb_callback.do")
	public void fbCallBack(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String reqPost = HttpUtils.getReqPostString(request, fbLog);
		fbLog.info("进入fb的回调");
		HashMap<String,String> callbackMap = JSON.parseObject(reqPost, new TypeReference<HashMap<String, String>>(){});
		
		fbLog.info("fb 的回调参数:" + reqPost);

		try {
			String Phone=callbackMap.get("phone");
			String supplierOrderId=callbackMap.get("orderId");
			String status=callbackMap.get("status");
			String msg=callbackMap.get("msg");
			
			// 获取运营商
			String phone = Phone;
			NumberSegment segment = flowCommonService.h(phone);
			String operator = segment.getOperator();
			/**
			 * OrderInfo orderInfo =
			 * flowOrderService.j(myOrderId);//orderInfoByOrderId to j String
			 * phone = orderInfo.getPhone(); NumberSegment segment =
			 * flowCommonService.h(phone);//segmentByPhone to h
			 */

			String supplier = "";
			switch (operator) {
			case "移动":
				supplier = ChinaMobileSendApiCode.YLIANG_YD.apiCode;
				break;
			case "联通":
				supplier = UniComSendApiCode.YLIANG_LT.apiCode;
				break;
			case "电信":
				supplier = TeleComSendApiCode.YLIANG_DX.apiCode;
				break;
			}

			SendOrderResult result = null;

			switch (status) {
			case "11":
				result = new SendOrderResult(SendOrderStatus.SUCCESS.status, null, supplierOrderId);
				break;
			case "2":
				result = new SendOrderResult(SendOrderStatus.FAILED.status, msg, supplierOrderId);
				break;
			default:
				result = new SendOrderResult(SendOrderStatus.NEED_MANUAL.status, "返回状态异常,请找开发", supplierOrderId);
			}
			// 修改数据库中订单的状态
			flowWorkerService.a(result, supplierOrderId, supplier, SupplierCallbackType.THEIR_ID);

			PrintWriter writer = response.getWriter();
			writer.print("OK");
			writer.flush();
			writer.close();
		} catch (Exception e) {
			fbLog.info(reqPost + "分包业务回调错误,请找技术:" + e.getMessage(), e);
		}
	}
	
	/**bigbosscxhf回调
	 * @author tjm
	 */
    /* 请求方法：post  参数格式 json
     * { 
		  "failReason": "充值失败", 
		  "outTradeNo": "us0pt4lw5w0dtj8i3x4dx71hej79", 
		  "sign": "7061b61b933cc2777b5b6649be2cdbb9", 
		  "status": 5, 
		  "ts": 1472181871485 
		}  */
	@RequestMapping(value = "/bigbosscxhf_callback.do")
	public void bigbosscxhfCallback(HttpServletRequest request, HttpServletResponse response) throws IOException {
		bigbosscxhfLog.info("进入bigbosscxhf的回调");
		bigbosscxhfLog.info("bigbosscxhf的回调参数 ：");
		String reqJson = HttpUtils.getReqPostString(request,bigbosscxhfLog);
		SendOrderResult result = null;
		String status = "";
		String ourOrderId = "";
		String supplier = "";

		try {
			if (StringUtils.isNotBlank(reqJson)) {
				JSONObject obj = JSON.parseObject(reqJson);
				status = obj.getString("status");
				ourOrderId = obj.getString("outTradeNo");
				
				// 根据我方订单id获取我方订单信息
				OrderInfo orderInfo = flowOrderService.j(Integer.parseInt(FlowUtils.getOrderId(ourOrderId)));
				NumberSegment segment = flowCommonService.h(orderInfo.getPhone());// 根据手机号获取运营商
				String operator = segment.getOperator();
				switch (operator) {
				case "移动":
					supplier = ChinaMobileSendApiCode.BIGBOSSCXHF_YD.apiCode;
					break;
				case "联通":
					supplier = UniComSendApiCode.BIGBOSSCXHF_LT.apiCode;
					break;
				case "电信":
					supplier = TeleComSendApiCode.BIGBOSSCXHF_DX.apiCode;
					break;
				}
				if ("4".equals(status)) {
					result = new SendOrderResult(SendOrderStatus.SUCCESS.status, null, ourOrderId);
				} else {
					result = new SendOrderResult(SendOrderStatus.FAILED.status,"充值失败",ourOrderId);
				}
				flowWorkerService.a(result,ourOrderId, supplier, SupplierCallbackType.OUR_ID);
				PrintWriter pw = response.getWriter();
				pw.print("OK");
				pw.flush();
				pw.close();
			}
		} catch (Exception e) {
			bigbosscxhfLog.info("bigbosscxhf回调错误,请找技术人员:" + e.getMessage(),e);
		}
	}
	/**qws回调
	 * @author tjm
	 */
	/* orderId			订单号
	   customerOrderId	客户订单号
	   status			订单状态 1 成功，2 失败
	   reason			status=2时，本字段返回失败原因
	   sign				签名 md5(orderId=PO20150601024900&status=1&password=123) */
	@RequestMapping(value = "/qws_callback.do")
	public void qwsCallback(HttpServletRequest request, HttpServletResponse response) throws IOException {
		qwsLog.info("进入qws的回调");
		qwsLog.info("qws的回调参数 ：");
		String temp = HttpUtils.getReqPostString(request,qwsLog);
		Map<String, String> map = HttpUtils.getReqParams(temp);
		SendOrderResult result = null;
		String status = "";
		String reason = "";
		String ourOrderId = "";
		String theirOrderId = ""; 
		String supplier = "";

		//返回给上游的信息
		String respJson = "";
		try {
			if (!map.isEmpty()) {
				status = map.get("status");
				
				if (StringUtils.isNotBlank(map.get("reason"))) {
					reason = URLDecoder.decode(map.get("reason"),"utf-8");
				}
				ourOrderId = map.get("customerOrderId");
				theirOrderId = map.get("orderId");
				// 根据我方订单id获取我方订单信息
				OrderInfo orderInfo = flowOrderService.j(Integer.parseInt(FlowUtils.getOrderId(ourOrderId)));
				NumberSegment segment = flowCommonService.h(orderInfo.getPhone());// 根据手机号获取运营商
				String operator = segment.getOperator();
				
				switch (operator) {
				case "移动":
					supplier = ChinaMobileSendApiCode.QWS_YD.apiCode;
					break;
				case "联通":
					supplier = UniComSendApiCode.QWS_LT.apiCode;
					break;
				case "电信":
					supplier = TeleComSendApiCode.QWS_DX.apiCode;
					break;
				}
				if ("1".equals(status)) {
					result = new SendOrderResult(SendOrderStatus.SUCCESS.status,null,ourOrderId);
					respJson = "{\"success\":true}";
				} else {
					result = new SendOrderResult(SendOrderStatus.FAILED.status,reason,ourOrderId);
					respJson = "{\"success\":false}";
				}
				flowWorkerService.a(result,ourOrderId, supplier, SupplierCallbackType.OUR_ID);
				String respStr = new String(respJson.getBytes(),"utf-8");
				
				PrintWriter pw = response.getWriter();
				pw.print(respStr);
				pw.flush();
				pw.close();
			}
		} catch (Exception e) {
			qwsLog.info("qws回调错误,请找技术人员:" + e.getMessage(),e);
		}
	}
	/**dli回调  
	 * @author tjm
	 */
	/**
	 * 参数为POST JSON，超时时间为10秒
	 * user_ordernum	商户订单号	String(255)	是	唯一的订单号，商户提供
	 * order_number		平台订单号	String(255)	是	DLI平台生成的订单号
	 * order_status		充值状态	String(5)	是	0：未提交，1：充值中，2：已充值，-1：失败    根据该值判断订单是否充值成功
	 * mobile			手机号码	String(18)	是	手机号码
	 * ctime			下单时间	Int			是	时间戳
	 * msg				描述		String		否	回调描述	
	 * 
	 * {"user_ordernum":"170519B0B154034",   
	 *  "order_number":"BBECE2D84C8841AAA175BB890EBDE9E7",  
	 *  "order_status":"2",					
	 *  "mobile":"13622662311",				 
	 *  "ctime":"1495178924" 				 
	 *  }	
	 */
	@RequestMapping(value = "/dli_callback.do")
	public void dliCallback(HttpServletRequest request, HttpServletResponse response) throws IOException {
		dliLog.info("进入dli的回调");
		dliLog.info("dli的回调参数 ：");
		String reqJson = HttpUtils.getReqPostString(request,dliLog);
		SendOrderResult result = null;
		String msg = "";
		String mobile = "";
		String status = "";
		String supplier = "";
		String ourOrderId = "";
		String theirOrderId = ""; 

		try {
			if (StringUtils.isNotBlank(reqJson)) {
				JSONObject obj = JSON.parseObject(reqJson);
				msg = obj.getString("msg");          
				mobile = obj.getString("mobile");
				status = obj.getString("order_status");       
				ourOrderId = obj.getString("user_ordernum");   
				theirOrderId = obj.getString("order_number"); 
				
				NumberSegment segment = flowCommonService.h(mobile);// 根据手机号获取运营商
				String operator = segment.getOperator();
				switch (operator) {
				case "移动":
					supplier = ChinaMobileSendApiCode.DLI_YD.apiCode;
					break;
				case "联通":
					supplier = UniComSendApiCode.DLI_LT.apiCode;
					break;
				case "电信":
					supplier = TeleComSendApiCode.DLI_DX.apiCode;
					break;
				}
				switch (status) {  //0：未提交，1：充值中，2：已充值，-1
				case "0":
					result = new SendOrderResult(SendOrderStatus.FAILED.status,"未提交订单",theirOrderId);
					break;
				case "1":
					result = new SendOrderResult(SendOrderStatus.WAIT_CALLBACK.status,null,theirOrderId);
					break;
				case "2":
					result = new SendOrderResult(SendOrderStatus.SUCCESS.status,null,theirOrderId);
					break;
				case "-1":
					result = new SendOrderResult(SendOrderStatus.FAILED.status,"充值失败",theirOrderId);
					break;
				default:
					result = new SendOrderResult(SendOrderStatus.WAIT_CALLBACK.status,null,theirOrderId);
					break;
				}
				flowWorkerService.a(result,theirOrderId, supplier, SupplierCallbackType.THEIR_ID);
				
				PrintWriter pw = response.getWriter();
				pw.print("success");
				pw.flush();
				pw.close();
			}
		} catch (Exception e) {
			dliLog.info("dli回调错误,请找技术人员:" + e.getMessage(),e);
		}
	}
	
	/**xcheng回调
	 * @author tjm
	 * {
		"partyId":"1001",
		"data":"HJKLTBNMKUU2345678FGHJ567890BNMHJKLGHJK",
		"sign":"ESFGJFGHJN3456GGH",
		"time":"20151029103010000"
		}		 */
	@RequestMapping(value = "/xcheng_callback.do")
	public void xchengCallback(HttpServletRequest request, HttpServletResponse response) throws IOException {
		xchengLog.info("进入xcheng的回调");
		xchengLog.info("xcheng的回调参数 ：");
		String tempJson = HttpUtils.getReqPostString(request,xchengLog);
		String xcheng_key = Constants.getString("xcheng_key");
		SendOrderResult result = null;
		String phone = "";
		String status = "";
		String resultCode = "";
		String resultDesc = "";
		String ourOrderId = "";
		String theirOrderId = ""; 
		String supplier = "";

		try {
			if (StringUtils.isNotBlank(tempJson)) {
				JSONObject obj = JSON.parseObject(tempJson);
				String data = obj.getString("data");
				if (StringUtils.isNotBlank(data)) {
					//解密获取请求体
					String reqBody = AESUtil.decrypt(data,xcheng_key);
					/*
					 * 请求体解密后的数据格式,为一个字符串类型的数组，一次可发送多组充值结果数据，最大100组
					 * "[{
							"status":"1", 
							"resultCode":"00000", 
							"resultDesc":"",
							"orderId":"10000000000001",
							"channelOrderId":"60000000000001",
							"phoneNumber":"13500000000",
							"prodId":"1100030",
							"prodName":"中国移动30M流量 ",
							"channel":"",
						    "province":"",
						    "city":"",
						    "validBeginDate":"",
						    "validEndDate":"",
						    "validBeginTime":"",
						    "validEndTime":"",
							"payAmount":"3500",
							"ext":"扩展字段",
							"extDesc":""
						  },{},{},.....]"
				      */	
					JSONArray jsonArr = JSON.parseArray(reqBody);
					for (int i = 0; i < 100; i++) {
						try {
							obj = jsonArr.getJSONObject(i);
						} catch (Exception e) {
							break;
						}
						phone = obj.getString("phoneNumber");
						status = obj.getString("status");
						resultCode = obj.getString("resultCode");
						resultDesc = "00000".equals(resultCode) ? "":obj.getString("resultDesc");
						theirOrderId = obj.getString("channelOrderId");
						
						NumberSegment segment = flowCommonService.h(phone);// 根据手机号获取运营商
						String operator = segment.getOperator();
						switch (operator) {
						case "移动":
							supplier = ChinaMobileSendApiCode.XCHENG_YD.apiCode;
							break;
						case "联通":
							supplier = UniComSendApiCode.XCHENG_LT.apiCode;
							break;
						case "电信":
							supplier = TeleComSendApiCode.XCHENG_DX.apiCode;
							break;
						}
						if ("1".equals(status)) {  //0订购失败  1订购成功
							result = new SendOrderResult(SendOrderStatus.SUCCESS.status,null,theirOrderId);
						}else {
							result = new SendOrderResult(SendOrderStatus.FAILED.status,resultDesc,theirOrderId);
						}
						flowWorkerService.a(result,theirOrderId, supplier, SupplierCallbackType.THEIR_ID);
					}
				}
				//返回给上游的信息
				String respJson = "{\"status\":\"1\",\"resultCode\":\"00000\",\"resultDesc\":\"\"}";
				String respStr = new String(respJson.getBytes(),"utf-8");
				
				PrintWriter pw = response.getWriter();
				pw.print(respStr);
				pw.flush();
				pw.close();
			}
		} catch (Exception e) {
			xchengLog.info("xcheng回调错误,请找技术人员:" + e.getMessage(),e);
		}
	}

}