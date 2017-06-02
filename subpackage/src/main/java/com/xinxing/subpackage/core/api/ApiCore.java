package com.xinxing.subpackage.core.api;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.xinxing.subpackage.core.api.tools.ApiUtils;
import com.xinxing.subpackage.core.callback.CallbackProcurer;
import com.xinxing.subpackage.core.erorr.CheckSignException;
import com.xinxing.subpackage.core.erorr.DIYException;
import com.xinxing.subpackage.core.erorr.NULLOrderIdException;
import com.xinxing.subpackage.core.erorr.RepeatOrdersException;
import com.xinxing.subpackage.core.erorr.SystemException;
import com.xinxing.subpackage.core.po.OrderInfo;
import com.xinxing.subpackage.core.po.OrderResult;
import com.xinxing.subpackage.core.send.SendCore;
import com.xinxing.subpackage.data.po.SubpackageOrderApi;
import com.xinxing.subpackage.data.po.SubpackageOrderSend;
import com.xinxing.subpackage.data.service.OrderService;
import com.xinxing.subpackage.data.service.PackOrderService;
/**
 * 收单分包接口
 * 
 * @author Administrator
 *
 */
public class ApiCore {

	@Autowired
	SendCore sendCore;
	@Autowired
	OrderService orderService;
	@Autowired
	PackOrderService packOrderService;
	@Autowired
	CallbackProcurer callback;

	private static Logger log = Logger.getLogger("ApiCore");

	// consecutive defeat
	private static int CD = 0;
	// total failure
	private static int TF = 0;

	/**
	 * 分包并提交第一个包,并记录返回的订单信息 TODO 调用分包表 调用订单表
	 * 
	 * @param sendMap
	 *            订单提交参数
	 * @return
	 * @throws CheckSignException
	 * @throws RepeatOrdersException
	 * @throws NULLOrderIdException
	 * @throws DIYException 
	 * @throws SystemException 
	 */
	public OrderResult sendApi(HttpServletRequest request) throws CheckSignException, RepeatOrdersException, NULLOrderIdException, SystemException, DIYException {
		OrderResult resule = null;
		int status = 0;

		// 取参
		OrderInfo info = ApiUtils.sendCheckout(request);

		// 分包提单(第一单)
		resule = sendCore.send(info);
		
		status = Integer.parseInt(resule.getStatus());
		String orderId = resule.getOrderId();
		String sendOrderId = resule.getSendOrderId();
		String Productinfo = info.getProductInfo();
		String phone = info.getPhone();
		int Packnum = Productinfo.split(",").length;

		// 收单记录
		SubpackageOrderApi order = new SubpackageOrderApi();
		order.setStatus(status);
		order.setOrderid(orderId);
		order.setProductinfo(Productinfo);
		order.setPacknum(Packnum);
		order.setWhichpack(0);// 提单多少包(初始化为0)
		order.setStarttime(new Date());
		order.setPhone(phone);
		if (status == 2) {// 第一单失败直接结束订单
			order.setEndtime(new Date());
		}
		boolean isAddOrder = orderService.addOrderInfo(order);

		// 发单记录(第一单),决定成败
		SubpackageOrderSend sendOrder = new SubpackageOrderSend();
		sendOrder.setStatus(status);
		sendOrder.setSendorderid(sendOrderId);
		sendOrder.setOrderid(orderId);
		sendOrder.setFlowpacksize(Packnum);// 包数量
		sendOrder.setWhichpack(1);// 分包名次
		sendOrder.setStarttime(new Date());
		if (status != 3 && status < 11) {// 当订单不为等待且不为异常[status>11]则视为该分包订单结束
			sendOrder.setEndtime(new Date());
		}
		boolean isSendAddOrder = packOrderService.addPackOrderInfo(sendOrder);

		if (!isAddOrder && !isSendAddOrder) {
			log.info("分包处理模块出现 " + "数据保存失败异常" + "请联系开发");
		}

		// 提单失败反馈
		if ("2".equals(status)) { // 回调YG
			callback.CallbackYG(resule);
		}

		return resule;

	}

	public OrderResult queryApi(HttpServletRequest request) throws NULLOrderIdException, SystemException, RepeatOrdersException, DIYException {
		OrderResult resule = null;
		int status = 0;
		// 取参
		OrderInfo info = ApiUtils.queryCheckout(request);
		String orderId = info.getOrderId();
		String packId = packOrderService.getpackId(orderId);
		SubpackageOrderApi api = orderService.getOrder(orderId);
		// 追加参数
		info.setSendOrderId(packId);
		info.setPhone(api.getPhone());
		info.setProductInfo(api.getProductinfo());

		// 分包查询
		resule = sendCore.query(info);

		status = Integer.parseInt(resule.getStatus().split(",")[0]);
		String[] statusAndMsg = resule.getStatus().split(",");
		if (statusAndMsg.length < 2) {
			resule.setFailReason("");
		} else {
			resule.setFailReason(statusAndMsg[1]);
		}
		resule.setStatus(status + "");
		String sendOrderId = resule.getSendOrderId();
		int whichPack = Integer.parseInt(sendOrderId.split("F")[1]);// XXXXXXXXF1
																	// XXXXXXXXF2
																	// XXXXXXXXF3

		SubpackageOrderSend pack = new SubpackageOrderSend();
		pack.setOrderid(orderId);
		pack.setSendorderid(sendOrderId);
		pack.setStarttime(new Date());
		pack.setFlowpacksize(0);// 包数量不好获取,获取到会影响性能,性能至上
		pack.setStatus(status);
		// 状态不为等待状态不为异常视为该分包业务结束[status>11都是是状态异常的状态码]]
		if (status != 3 || status > 11) {
			pack.setEndtime(new Date());
		}
		pack.setWhichpack(whichPack);
		boolean isAddOrder = packOrderService.updatePackOrderInfo(pack);

		if (!isAddOrder) {
			log.info("分包处理模块出现 " + "数据保存失败异常" + "请联系开发");
		}
		// 只有总成功和总失败的时候才回调YG
		switch (status) {
			case 2 :
			case 11 :
				callback.CallbackYG(resule);
				break;
			default :
				break;
		}

		return resule;
	}

}
