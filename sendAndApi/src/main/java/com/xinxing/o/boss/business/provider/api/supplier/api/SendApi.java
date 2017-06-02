package com.xinxing.o.boss.business.provider.api.supplier.api;

import java.util.List;
import java.util.Map;

import com.xinxing.boss.business.api.domain.SendOrderInfo;
import com.xinxing.boss.business.api.domain.SendOrderResult;

public interface SendApi {
	/**
	 * 鍙戦�璁㈠崟淇℃伅
	 * 
	 * @param sendOrderInfo
	 * @return
	 */
	public SendOrderResult send(SendOrderInfo sendOrderInfo);

	/**
	 * 鏌ヨ澶氫釜璁㈠崟淇℃伅-闇�鎸囧畾渚涜揣鍟�
	 * 
	 * @param sendOrderInfo
	 * @return
	 */
	public Map<String, SendOrderResult> querys(List<SendOrderInfo> sendOrderInfo);

	/**
	 * 鏌ヨ鍗曚釜璁㈠崟鐘舵�
	 * 
	 * @param sendOrderInfo
	 * @return
	 */
	public SendOrderResult query(SendOrderInfo sendOrderInfo);
}
