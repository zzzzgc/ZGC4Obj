package com.xinxing.o.boss.api.system.handle.cmd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.TypeReference;
import com.xinxing.boss.business.api.domain.SendOrderInfo;
import com.xinxing.boss.business.api.domain.SendOrderResult;
import com.xinxing.boss.business.worker.domain.SupplierCallbackType;
import com.xinxing.boss.interaction.pojo.customer.OrderInfo;
import com.xinxing.boss.interaction.pojo.provider.ProviderInfo;
import com.xinxing.boss.interaction.pojo.provider.ProviderProductInfo;
import com.xinxing.o.boss.business.provider.api.chinamobile.ChinaMobileSendApi;
import com.xinxing.o.boss.business.provider.api.chinatelecom.TeleComSendApi;
import com.xinxing.o.boss.business.provider.api.chinaunicom.UniComSendApi;
import com.xinxing.o.boss.common.http.HttpUtils;
import com.xinxing.o.boss.common.json.JsonUtils;

/**
 * 处理手工订单接口
 * 
 * @author
 * 
 */
public class CmdHandleManualOrder extends com.xinxing.flow.cmd.a {

	private static final Logger log = Logger.getLogger(CmdHandleManualOrder.class);

	private static final String MANAUAL_ORDER_PAW = "123456";

	@Autowired
	private com.xinxing.boss.business.worker.handle.a flowWorkerService;

	@Autowired
	private ChinaMobileSendApi chinaMobileSendApi;

	@Autowired
	private UniComSendApi uniComSendApi;

	@Autowired
	private TeleComSendApi teleComSendApi;

	@Autowired
	private com.xinxing.boss.interaction.service.order.a flowOrderService;

	@Autowired
	private com.xinxing.boss.interaction.service.provider.a flowProviderService;

	@Autowired
	private com.xinxing.boss.cache.a flowCacheService;

	@Override
	public String b(HttpServletRequest request, HttpServletResponse response){
		String postData = HttpUtils.getReqPostString(request, log);
		Map<Object, Object> map = JSON.parseObject(postData, new TypeReference<Map<Object, Object>>() {
		});
		String paw = (String) map.get("paw");
		Map<Object, Object> res = new HashMap<Object, Object>();
		;
		if (StringUtils.equals(MANAUAL_ORDER_PAW, paw)) {
			String action = (String) map.get("action");
			JSONArray orderArrays = (JSONArray) map.get("orderIds");
			@SuppressWarnings("unchecked")
			List<Integer> orderIds = JSONArray.toJavaObject(orderArrays, List.class);
			switch (action) {
			case "toFail":// 订单修改为失败
				flowWorkerService.a(orderIds);
				break;
			case "toSuccess":// 订单修改为成功
				flowWorkerService.b(orderIds);
				break;
			case "toResend":// 订单重发
				flowWorkerService.d(orderIds);
				break;
			case "toCallback":// 订单回调
				flowWorkerService.c(orderIds);
				break;
			case "getOrderStatus":// 查询广东订单状态信息
				//queryGdChinaMobileOrders(orderIds);
				break;
			case "getOrderStatusAll":// 查询所有订单状态接口
				queryAllOrders(orderIds);
				break;
			case "toProviderSend":// 选择供货商后重发
				// 1、遍历，修改状态为充值中，需检查前后状态
				// 2、校验产品是否向上兼容
				// 3、检测供货商是否冻结
				// 4、获取供货商产品
				// 5、重发到队列
				int providerProductId = (int) map.get("providerProductId");
				flowWorkerService.a(orderIds, providerProductId, false);

				break;
			case "toProviderSendWithNoFinished":// 选择供货商后重发
				// 1、遍历，修改状态为充值中，需检查前后状态
				// 2、校验产品是否向上兼容
				// 3、检测供货商是否冻结
				// 4、获取供货商产品
				// 5、重发到队列
				int providerNoFinishProductId = (int) map.get("providerProductId");
				flowWorkerService.a(orderIds, providerNoFinishProductId, true);
			case "toFailWithChargeId":
				JSONArray chargeArray = (JSONArray) map.get("chargeIds");
				@SuppressWarnings("unchecked")
				List<Integer> chargeIds = JSONArray.toJavaObject(chargeArray, List.class);
				flowWorkerService.a(chargeIds, orderIds);
				break;
			case "toFailOnChargePage":
				JSONArray chargeArray1 = (JSONArray) map.get("chargeIds");
				@SuppressWarnings("unchecked")
				List<Integer> chargeIds1 = JSONArray.toJavaObject(chargeArray1, List.class);
				flowWorkerService.b(chargeIds1, orderIds);
				break;
			case "toSuccessWithChargeId":
				JSONArray chargeArrays = (JSONArray) map.get("chargeIds");
				@SuppressWarnings("unchecked")
				List<Integer> chargeIdss = JSONArray.toJavaObject(chargeArrays, List.class);
				flowWorkerService.c(chargeIdss, orderIds);
				break;
			}

			res.put("state", true);
		} else {
			res = new HashMap<Object, Object>();
			res.put("state", false);
		}
		return a(response, res, "手工处理信息");
	}

	/**
	 * 构建发送订单
	 * 
	 * @param orders
	 * @return
	 */
	private List<SendOrderInfo> buildSendOrders(List<OrderInfo> orderInfoList) {
		List<SendOrderInfo> orderList = null;
		if (orderInfoList != null) {
			orderList = new ArrayList<>();
			for (int i = 0, len = orderInfoList.size(); i < len; i++) {
				OrderInfo order = orderInfoList.get(i);
				ProviderInfo providerInfo = flowProviderService.o(order.getProviderId());
				SendOrderInfo sendOrderInfo = new SendOrderInfo(order.getOperator(), order.getId() + "", null, order.getPhone(),
						providerInfo.getSupplier(), order.getReceiveTime(), order.getChargeCount(), order.getProviderKey());
				sendOrderInfo.setProviderId(providerInfo.getId());
				orderList.add(sendOrderInfo);
			}
		}
		return orderList;
	}

	/**
	 * 单个查询订单接口
	 * 
	 * @param orderIds
	 */
	private void queryAllOrders(List<Integer> orderIds) {
		List<OrderInfo> orderInfos = flowOrderService.e(orderIds);
		if (orderInfos != null && orderInfos.size() > 0) {
			Map<Integer, SendOrderInfo> sendOrderMaps = buildSendOrders4Map(orderInfos);
			for (OrderInfo info : orderInfos) {
				SendOrderInfo sendOrderInfo = sendOrderMaps.get(info.getId());
				SendOrderResult result = null;
				switch (info.getOperator()) {
				case "移动":
					result = chinaMobileSendApi.query(sendOrderInfo);
					break;
				case "联通":
					result = uniComSendApi.query(sendOrderInfo);
					break;
				case "电信":
					result = teleComSendApi.query(sendOrderInfo);
					break;
				}
				if (result != null) {
					ProviderInfo providerInfo = flowProviderService.o(info.getProviderId());
					flowWorkerService.a(result, sendOrderInfo.getOrderId(), providerInfo.getSupplier(), SupplierCallbackType.OUR_ID);
				} else {
					flowOrderService.a("查询不到订单状态", info.getId());
				}
			}
		}
	}

	/**
	 * 构造发送订单Map
	 * 
	 * @param orderInfoList
	 * @return
	 */
	private Map<Integer, SendOrderInfo> buildSendOrders4Map(List<OrderInfo> orderInfoList) {
		Map<Integer, SendOrderInfo> map = null;
		if (orderInfoList != null) {
			map = new HashMap<>();
			for (int i = 0, len = orderInfoList.size(); i < len; i++) {
				OrderInfo order = orderInfoList.get(i);
				ProviderInfo providerInfo = flowProviderService.o(order.getProviderId());

				ProviderProductInfo providerProductInfo = null;
				String productCode = null;
				try {
					providerProductInfo = flowCacheService.a(order.getProviderCategoryId(), providerInfo.getId());
					if (providerProductInfo != null) {
						productCode = providerProductInfo.getProductCode();
					} else {
						log.info("----" + order.getId() + "-------供货商产品为空");
					}
				} catch (Exception e) {
					log.info("构建发送订单异常:" + order.getId() + ":" + JsonUtils.obj2Json(providerProductInfo) + e.getMessage(), e);
					log.error("构建发送订单异常:" + order.getId() + e.getMessage(), e);
				}

				SendOrderInfo sendOrderInfo = new SendOrderInfo(order.getOperator(), order.getId() + "", productCode, order.getPhone(),
						providerInfo.getSupplier(), order.getReceiveTime(), order.getChargeCount(), order.getProviderKey());
				sendOrderInfo.setProviderId(providerInfo.getId());
				map.put(order.getId(), sendOrderInfo);
			}
		}
		return map;
	}

}
