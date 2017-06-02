package com.xinxing.o.boss.business.provider.callback;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xinxing.boss.business.api.domain.SendOrderResult;
import com.xinxing.boss.business.api.domain.SendOrderStatus;
import com.xinxing.boss.business.worker.domain.SupplierCallbackType;
import com.xinxing.boss.common.db.CommonDao;

/**
 * 供货商回调 业务处理
 * 
 * @author Administrator
 * 
 */
@Controller
public class MiPuCallBack {

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
	 * 回调处理
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/telecom2_callback.do")
	public void teleComCallBack(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String order_number = ""; // 成功后返回订单号
		String shipping_status = "充值成功"; // 结束状态对应值，参考
		String shipping_status_desc = "充值成功";// 对应shipping_status的文字描述
		String shipping_status_message = "充值成功";// 发货错误的描述
		/*
		 * if (StringUtils.isNotBlank(postData)) { // TODO 电信成功后业务处理 if
		 * (StringUtils.equals("00000", "00000")) {// 充值成功 result = new
		 * SendOrderResult(SendOrderStatus.SUCCESS.status, null, bossId); } else
		 * { String failReason = "回调错误"; result = new
		 * SendOrderResult(SendOrderStatus.FAILED.status, failReason, bossId); }
		 * flowWorkerService.a(result, bossId, supplier,
		 * SupplierCallbackType.THEIR_ID); PrintWriter pw =
		 * response.getWriter(); pw.print("OK"); pw.flush(); pw.close(); }
		 */
	}
}
