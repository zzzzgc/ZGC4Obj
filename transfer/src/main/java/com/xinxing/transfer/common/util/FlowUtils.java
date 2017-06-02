package com.xinxing.transfer.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.xinxing.transfer.common.resource.Constants;

public class FlowUtils {

	/**
	 * 获取本地订单号
	 * 
	 * @param orderIdFromSupplier
	 *            供货商中记录我们这边的流水号信息
	 * @return
	 */
	public static String getOrderId(String orderIdFromSupplier) {
		return orderIdFromSupplier.split("B")[2];
	}

	/**
	 * 构建发送流水号
	 * 
	 * @param receiveTime
	 * @param chargeCount
	 * @param orderId
	 * @return
	 */
	public static String setOrderId4Supplier(String receiveTime,
			int chargeCount, String orderId) {
		String from = Constants.getString("boss_order_tag");
		return receiveTime + from + "B" + chargeCount + "B" + orderId;
	}

	/**
	 * 获取简单年月日
	 * 
	 * @param date
	 * @return
	 */
	public static String setOrderFormateTime(Date date) {
		String format = "yyMMdd";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}

}
