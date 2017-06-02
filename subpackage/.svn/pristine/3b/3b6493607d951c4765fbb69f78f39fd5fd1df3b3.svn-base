package com.xinxing.subpackage.core.send.callback;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xinxing.subpackage.core.callback.CallbackProcurer;
import com.xinxing.subpackage.core.erorr.DIYException;
import com.xinxing.subpackage.core.erorr.NULLOrderIdException;
import com.xinxing.subpackage.core.erorr.RepeatOrdersException;
import com.xinxing.subpackage.core.erorr.SystemException;
import com.xinxing.subpackage.core.halt.Halt;
import com.xinxing.subpackage.core.po.OrderInfo;
import com.xinxing.subpackage.core.po.OrderResult;
import com.xinxing.subpackage.core.po.PackInfo;
import com.xinxing.subpackage.core.send.SendCore;
import com.xinxing.subpackage.core.status.OrderStatus;
import com.xinxing.subpackage.data.common.http.HttpUtils;
import com.xinxing.subpackage.data.po.SubpackageOrderSend;
import com.xinxing.subpackage.data.service.OrderService;
import com.xinxing.subpackage.data.service.PackOrderService;

@Controller
public class CallBack {

	private static final Logger log = Logger.getLogger("Subpackage->callback enter");
	@Autowired
	OrderService orderService;
	@Autowired
	PackOrderService packOrderService;
	@Autowired
	SendCore sendCore;
	@Autowired
	CallbackProcurer callbackProcurer;

	@RequestMapping("/fzgcallback.do")
	public void callBack(HttpServletRequest request, HttpServletResponse response) {
		// log.info("进入分包回调(分包入口)");
		String respJson = HttpUtils.getReqPostString(request, log);
		JSONObject obj = null;
		OrderResult orderResult = null;
		OrderResult newOrderResult = null;
		String orderStatus = null;
		String sendOrderId = null;
		int newOrderStatus = 0;
		String failReason = null;
		String orderId = null;
		SubpackageOrderSend newSendOrder=null;
		SubpackageOrderSend sendOrder=null;
		String phone = null;
		int status=0;

		try {
			if (StringUtils.isNotBlank(respJson)) {
				obj = JSON.parseObject(respJson);
				phone = obj.getString("Phone");
				sendOrderId = obj.getString("FlowKey");
				failReason = obj.getString("FailReason");
				orderStatus = obj.getString("OrderStatus");
				orderId = orderService.getOrderId(sendOrderId);
				if ("Success".equals(orderStatus)) {
					PackInfo nextPack = packOrderService.nextPack(orderId, sendOrderId);
					if (nextPack == null) {// 如果没有下一个可以充值的分包,说明充值成功，所有分包成功
						orderResult = new OrderResult(OrderStatus.SUCCEED.status, null, sendOrderId, orderId, phone);
						callbackProcurer.CallbackYG(orderResult);
					} else {
						orderResult = new OrderResult(OrderStatus.PACKSUCCEED.status, null, sendOrderId, orderId, phone);
						OrderInfo orderInfo = new OrderInfo(null, phone, nextPack.getPackId(), nextPack.getPackProductId(), orderId);
						SubpackageOrderSend orderSend = packOrderService.getSubpackageOrder(nextPack.getPackId());
						if (orderSend == null) {
							OrderResult result2 = sendCore.send(orderInfo);
							newOrderStatus = Integer.parseInt(result2.getStatus());
							newSendOrder= new SubpackageOrderSend();
							int whichpack = Integer.parseInt(nextPack.getPackId().split("F")[1]);
							newSendOrder.setStatus(newOrderStatus);
							newSendOrder.setSendorderid(nextPack.getPackId());
							newSendOrder.setOrderid(orderId);
							newSendOrder.setFlowpacksize(null);// 包数量
							newSendOrder.setWhichpack(whichpack);// 分包名次
							newSendOrder.setStarttime(new Date());
							if (newOrderStatus == 2) {// 提单失败直接结束订单,并改原包结束时间
								newSendOrder.setEndtime(new Date());
								if (whichpack>1) {
									Halt.addStatus(nextPack.getPackId(), 2);
								}
							}
							// 保存订单信息
							boolean isSendAddOrder = packOrderService.addPackOrderInfo(newSendOrder);
							if (newOrderStatus == 2) {// 必要的校验,提单失败直接回调YG,
								newOrderResult = new OrderResult(OrderStatus.FAILURE.status, null, nextPack.getPackId(), orderId, phone);
								callbackProcurer.CallbackYG(newOrderResult);
							}

							if (!isSendAddOrder) {
								log.info("分包处理模块出现 " + "数据保存失败异常" + "请联系开发");
							}
						}
					}

				} else if ("Error".equals(orderStatus)) {
					orderResult = new OrderResult(OrderStatus.FAILURE.status, failReason, sendOrderId, orderId, phone);
					callbackProcurer.CallbackYG(orderResult);
				}
				sendOrder = new SubpackageOrderSend();
				sendOrder.setOrderid(orderId);
				switch (orderStatus) {
					case "Success" :
						status=1;
						break;
					case "Error" :
						status=2;
						break;
					default :
						break;
				}
				sendOrder.setStatus(status);
				sendOrder.setEndtime(new Date());
				packOrderService.updatePackOrderInfo(sendOrder);
				
			}else{
				log.info("回调为空");
			}

		} catch (RepeatOrdersException e) {
			log.info("订单" + sendOrderId + "回调:" + e.getMessage());
		} catch (NULLOrderIdException e) {
			log.info("订单" + sendOrderId + "回调:" + e.getMessage());
		} catch (DIYException e) {
			String[] split = e.getMessage().split(",");
			if ("4".equals(split[0])) {
				log.info(split[1]);
			}
		} catch (SystemException e) {
			log.info("订单" + sendOrderId + "回调:" + e.getMessage());
		}

		// 成功并且没有下一个包要充值,回调YG

		PrintWriter pw;
		try {
			pw = response.getWriter();
			pw.print("OK");
			pw.flush();
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	public static void main(String[] args) {
		int a = 02;
		String bString = "02";
		String b = a + "," + bString;
		String[] split = b.split(",");
		if (split[0].equals("02")) {
			System.out.println("ok");
		} else {
			System.out.println(a);
			System.out.println(b);
			System.out.println(split);
			System.out.println("Notok");

		}
	}
}
