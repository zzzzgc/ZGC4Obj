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
public class HuaYiCallBack {
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
	
	@RequestMapping(value = "/huayitele_callback.do") //问题1
	public void teleComCallBack(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String status = "0";
		String msg = "充值成功";
		String orderNumber="HY0001111";
		String phoneNumber="18820036966";
		String sign="HY000111118820036966FXT0000 ";
		
		SendOrderResult result = null;
/*		String bossId = "A123456" ;//上游 系统订单号
		String orderId = "123456" ;//自有系统订单号
*/		/*if (StringUtils.isNotBlank(postData)) {*/
			// TODO 电信成功后业务处理
			if (status.equals("0")) {// 充值成功
				result = new SendOrderResult(SendOrderStatus.SUCCESS.status, null, orderNumber);
			} else {
				String failReason = "回调错误";
				result = new SendOrderResult(SendOrderStatus.FAILED.status, failReason, orderNumber);
			}
			flowWorkerService.a(result, orderNumber, status, SupplierCallbackType.OUR_ID);
			PrintWriter pw = response.getWriter();
			pw.print("0");
			pw.flush();
			pw.close();
	}

}
