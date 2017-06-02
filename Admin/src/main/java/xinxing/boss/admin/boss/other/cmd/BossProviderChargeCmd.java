package xinxing.boss.admin.boss.other.cmd;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import xinxing.boss.admin.boss.order.service.OrderInfoService;
import xinxing.boss.admin.boss.other.domain.BossProviderCharge;
import xinxing.boss.admin.boss.other.service.BossProviderChargeService;
import xinxing.boss.admin.boss.provider.domain.ProductCategoryInfo;
import xinxing.boss.admin.boss.provider.domain.ProviderInfo;
import xinxing.boss.admin.boss.provider.domain.ProviderProductInfo;
import xinxing.boss.admin.common.excel.ExcelUtils;
import xinxing.boss.admin.common.persistence.Page;
import xinxing.boss.admin.common.persistence.PropertyFilter;
import xinxing.boss.admin.common.utils.base.BaseUtil;
import xinxing.boss.admin.common.utils.http.HttpUtils;
import xinxing.boss.admin.common.utils.json.JsonUtils;
import xinxing.boss.admin.common.utils.parameter.ParameterListener;
import xinxing.boss.admin.common.utils.string.StringUtils;
import xinxing.boss.admin.common.utils.time.DateUtils;
import xinxing.boss.admin.common.web.BaseController;

@Controller
@RequestMapping(value = "boss/bossProviderCharge")
public class BossProviderChargeCmd extends BaseController {
	private static Logger log = LoggerFactory.getLogger(BossProviderChargeCmd.class);
	@Autowired
	private BossProviderChargeService bossProviderChargeService;
	@Autowired
	private OrderInfoService orderInfoService;

	/**
	 * 订单统计
	 * 
	 * @return
	 */
	@RequestMapping(value = "getCountInfo")
	@ResponseBody
	public Map<String, Object> getCountInfo(HttpServletRequest request) {
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		Map<String, Object> returnMap = new HashMap<>();
		try {
			StringBuilder sql = new StringBuilder(200);
			sql.append("select status as status,count(status) as statusNum,sum(cost) as cost from boss_provider_charge ")
					.append(BaseUtil.getSqlConditionByFilters(filters, log)).append(" group by status ");
			List<Map<String, Object>> query = orderInfoService.query(sql.toString().replaceAll("beyoudOperation", "operator"));
			if (query.size() == 0) {
				returnMap.put("failReason", "数据不能为空");
				return returnMap;
			}
			Double successTime = 0d;
			Double failTime = 0d;
			Integer totalNum = 0;
			Integer failNum = 0;
			Integer chargeNum = 0;
			Integer waitNum = 0;
			Integer handleNum = 0;
			Integer newNum = 0;
			Double totalCost = 0d;
			Double successCost = 0d;
			Double failMoney = 0d;
			Double chargeMoney = 0d;
			Double waitMoney = 0d;
			Double handleMoney = 0d;
			Double earnMoney = 0d;
			for (Map<String, Object> map : query) {
				Integer state = (Integer) map.get("status");
				Integer stateNum = (BigInteger) map.get("statusNum") != null ? ((BigInteger) map.get("statusNum")).intValue() : 0;
				Object costObj = map.get("cost");
				Double cost = costObj != null ? Double.valueOf(costObj.toString()) : 0;
				state = state != null ? state : 0;
				totalCost += cost;
				totalNum += stateNum;
				switch (state) {
				case 1:
				case 2:
					// newNum += stateNum;
					// break;
					chargeNum += stateNum;
					chargeMoney += cost;
					break;
				case 3:
					successCost += cost;
					break;
				case 4:
					failNum += stateNum;
					failMoney += cost;
					break;
				case 5:
					waitNum += stateNum;
					waitMoney += cost;
					break;
				case 6:
					handleNum += stateNum;
					handleMoney += cost;
					break;
				default:
					break;
				}
			}
			returnMap.put("chargeMoney", chargeMoney);
			returnMap.put("chargeNum", chargeNum);
			returnMap.put("successTime", successTime);
			returnMap.put("successCost", successCost);
			returnMap.put("failTime", failTime);
			returnMap.put("failMoney", failMoney);
			returnMap.put("failNum", failNum);
			returnMap.put("waitMoney", waitMoney);
			returnMap.put("waitNum", waitNum);
			returnMap.put("handleMoney", handleMoney);
			returnMap.put("handleNum", handleNum);
			returnMap.put("totalNum", totalNum);
			returnMap.put("totalMoney", totalCost);
			returnMap.put("earnMoney", earnMoney);

		} catch (Exception e) {
			returnMap.put("failReason", "统计异常");
		}
		return returnMap;
	}

	// 弹出框的方法
	@RequestMapping(value = "alertWindow")
	@ResponseBody
	public String alertWindow(HttpServletRequest req, BossProviderCharge bossProviderCharge, Integer[] ids, String method) {
		Map<String, String> reqParams = HttpUtils.getReqParams(req);
		if (reqParams == null) {
			reqParams = new HashMap<String, String>();
		}
		log.info("reqParams:" + JsonUtils.obj2Json(reqParams));
		return "success";
	}

	// 额外的方法
	@RequestMapping(value = "updateStatus")
	@ResponseBody
	public String updateStatus(HttpServletRequest req, Integer[] ids, Integer status) {
		Map<String, String> reqParams = HttpUtils.getReqParams(req);
		if (reqParams == null) {
			reqParams = new HashMap<String, String>();
		}
		log.info("reqParams:" + JsonUtils.obj2Json(reqParams));

		if (status != null && status >= 0 && status <= 2) {
			String action = "";
			if (status == 0) {
				action = "toFailOnChargePage";
			} else if (status == 1) {
				action = "toSuccessWithChargeId";
			}
			List<Integer> orderIdlist = new ArrayList<>();
			for (Integer id : ids) {
				BossProviderCharge bossProviderCharge = bossProviderChargeService.get(id);
				if (bossProviderCharge == null || bossProviderCharge.getOrderId() == null) {
					String errorMsg = "id:" + id + "没有对应的订单号";
					log.error(errorMsg);
					return errorMsg;
				} else {
					orderIdlist.add(bossProviderCharge.getOrderId());
				}
			}
			Map<String, Object> map = new HashMap<>();
			map.put("orderIds", orderIdlist);
			map.put("chargeIds", ids);
			map.put("action", action);
			orderInfoService.handleOrder(map);

		}
		return "success";
	}

	// 获取数据到页面
	@RequestMapping(value = "bossProviderChargeList")
	@ResponseBody
	public Map<String, Object> getConfigList(HttpServletRequest req) {
		Page<BossProviderCharge> page = getPage(req);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(req);

		String orderInfo_status = req.getParameter("orderInfo_status");
		if (StringUtils.isNotBlank(orderInfo_status)) {//有订单状态
			String sql = "select b.*,o.status as orderStatus,o.failReason as orderReason from boss_provider_charge b, boss_order o  where b.orderId = o.id ";
			List<String> conditions = BaseUtil.getConditions(filters, log);
			for (String condition : conditions) {
				sql += " and b." + condition;
			}
			sql+=" and o.status = "+orderInfo_status;
			sql += " order by b.id desc limit " + (page.getFirst() - 1) + "," + page.getPageSize();
			List<Map<String, Object>> query = bossProviderChargeService.query(sql);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("rows", query);
			map.put("total", query.size());
			log.info("sql::" + sql);
			return map;
		}
		// select b.*,o.status as chargeState, from boss_provider_charge b, boss_order o where b.id in(1,2) and o.id = 7969912 and b.orderId = o.id;

		page = bossProviderChargeService.search(page, filters);
		List<BossProviderCharge> rs = page.getResult();
		if (rs != null && !rs.isEmpty()) {
			List<Integer> orderIds = new ArrayList<>();
			for (int i = 0, len = rs.size(); i < len; i++) {
				orderIds.add(rs.get(i).getOrderId());
			}
			String sql = "select * from boss_order where id in(" + StringUtils.join(orderIds.toArray(), ",") + ")";
			List rst = orderInfoService.query(sql);
			Map<Integer, Object> orderMap = new HashMap<>();
			for (int j = 0; j < rst.size(); j++) {
				orderMap.put((Integer) ((Map) rst.get(j)).get("id"), rst.get(j));
			}
			for (int i = 0, len = rs.size(); i < len; i++) {
				BossProviderCharge bpc = rs.get(i);
				Map<String, Object> orderInfoMap = (Map<String, Object>) orderMap.get(bpc.getOrderId());
				if (orderInfoMap != null) {
					bpc.setOrderStatus((Integer) orderInfoMap.get("status"));
					bpc.setOrderReason((String) orderInfoMap.get("failReason"));
				}
			}

		}
		return getEasyUIData(page);
	}

	// 跳转页面
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list(HttpServletRequest req, Integer id, BossProviderCharge bossProviderCharge, Model model) {
		Map<String, String> reqMap = HttpUtils.getReqParams(req);
		if (reqMap == null) {
			reqMap = new HashMap<String, String>();
		}
		String page = reqMap.get("page");
		if (StringUtils.isEmpty(page)) {
			page = "List";
		}
		if (id != null) {
			bossProviderCharge = bossProviderChargeService.get(id);
		}
		Date yesterDay = DateUtils.addDate(new Date(), -1);
		reqMap.put("yestodayStart", DateUtils.getDate(yesterDay, "yyyy-MM-dd") + " 00:00:00");
		reqMap.put("todayEnd", DateUtils.getDate() + " 23:59:59");
		model.addAttribute("reqMap", reqMap);
		model.addAttribute("action", (id != null ? "update" : "add"));
		model.addAttribute("bossProviderCharge", bossProviderCharge);
		return "boss/bossProviderCharge/bossProviderCharge" + page;
	}

	// 跳转页面
	@RequestMapping(value = "handleOrderPage")
	public String handleOrderPage(Integer status, String ids, Model model) {
		Map<Integer, ProviderInfo> providerInfoMap = ParameterListener.providerInfoMap;
		List<Map<String, Object>> modelList;
		List<BossProviderCharge> list = bossProviderChargeService.search(" from BossProviderCharge where id in (" + ids + ")");
		modelList = JsonUtils.json2Obj(JsonUtils.obj2Json(list), List.class);
		for (Map<String, Object> map : modelList) {
			Object providerId = map.get("providerId");
			String providerName = "";
			if (providerId != null) {
				ProviderInfo providerInfo = providerInfoMap.get((Integer) providerId);
				if (providerInfo != null) {
					providerName = providerInfo.getProviderName();
				}
			}
			map.put("providerName", providerName);
		}
		model.addAttribute("status", status);
		model.addAttribute("list", modelList);
		return "boss/bossProviderCharge/bossProviderChargeHandleProvider";
	}

	// 添加
	@RequiresPermissions("boss:bossProviderCharge:add")
	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ResponseBody
	public String add(@Valid BossProviderCharge bossProviderCharge, Model model, String batchs, HttpServletRequest req) {
		String addIds = "";
		if (StringUtils.isNotEmpty(batchs)) {// 批量添加
			String[] split = batchs.split(",");
			for (String change : split) {
				BossProviderCharge obj = JsonUtils.json2Obj(JsonUtils.obj2Json(bossProviderCharge), BossProviderCharge.class);
				// change内容修改
				obj.setId(null);
				bossProviderChargeService.save(obj);
				addIds = obj.getId() + " ";
			}
		} else {// 单个添加
			bossProviderChargeService.save(bossProviderCharge);
			addIds = bossProviderCharge.getId() + " ";
		}
		ParameterListener.refresh("bossProviderCharge");
		// ParameterListener.flushStatus("bossProviderCharge");
		log.info("bossProviderCharge add:" + addIds);
		return "success";
	}

	// 修改
	@RequiresPermissions("boss:bossProviderCharge:update")
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody BossProviderCharge bossProviderCharge) {
		bossProviderChargeService.update(bossProviderCharge);
		ParameterListener.refresh("bossProviderCharge");
		// ParameterListener.flushStatus("bossProviderCharge");
		return "success";
	}

	// 批量删除
	@RequiresPermissions("boss:bossProviderCharge:delete")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(Integer[] ids) {
		if (ids != null && ids.length > 0) {
			for (Integer id : ids) {
				bossProviderChargeService.delete(id);
				log.info("bossProviderCharge delete:" + id);
			}
			ParameterListener.refresh("bossProviderCharge");
			// ParameterListener.flushStatus("bossProviderCharge");
		}
		return "success";
	}

	// 导出excel
	@RequiresPermissions("boss:bossProviderCharge:exportExcel")
	@RequestMapping("exportExcel")
	public void createExcel(HttpServletRequest req, HttpServletResponse resp) throws Exception, InterruptedException {
		System.out.println("-----------");
		try {
			Integer maxNumber = 400000;
			Page<BossProviderCharge> page = getPage(req);
			page.setPageNo(1);
			page.setPageSize(maxNumber);
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(req);
			page = bossProviderChargeService.exportSearch(page, filters, maxNumber);
			if (page.getTotalCount() > maxNumber) {
				ExcelUtils.writeErrorMsg(resp, page.getTotalCount(), "导出数据不能超过10万条");
				return;
			}
			List<BossProviderCharge> list = page.getResult();// 获取数据
			List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
			// 把orderId放进去
			Map<Integer, Integer> queryMap = getOrderMap(maxNumber, list);
			log.info("queryMap:" + queryMap.size());
			Map<Integer, ProviderInfo> providerInfoMap = ParameterListener.providerInfoMap;
			Map<Integer, ProductCategoryInfo> productCategoryInfoMap = ParameterListener.productCategoryInfoMap;
			Map<Integer, ProviderProductInfo> providerProductMap = ParameterListener.providerProductMap;
			ProviderInfo providerInfo = null;
			ProductCategoryInfo pci = null;
			ProviderProductInfo ppi = null;
			for (BossProviderCharge bossProviderCharge : list) {
				Map<String, Object> beanToMap = ExcelUtils.beanToMap(bossProviderCharge);
				beanToMap.put("orderStatus", queryMap.get(bossProviderCharge.getOrderId()));
				providerInfo = providerInfoMap.get(bossProviderCharge.getProviderId());
				if (providerInfo != null) {
					beanToMap.put("providerName", providerInfo.getProviderName());
					providerInfo = null;
				}
				pci = productCategoryInfoMap.get(bossProviderCharge.getCategoryId());
				if (pci != null) {
					beanToMap.put("categoryName", pci.getCategoryName());
					pci = null;
				}
				ppi = providerProductMap.get(bossProviderCharge.getProductId());
				if (ppi != null) {
					beanToMap.put("providerProductName", ppi.getProductName());
					ppi = null;
				}

				ExcelUtils.showValue(beanToMap, "status", ParameterListener.orderStatusStr);
				ExcelUtils.showValue(beanToMap, "orderStatus", ParameterListener.orderStatusStr);
				mapList.add(beanToMap);
			}

			String title = "供应商充值记录";

			String[] hearders = new String[] { "订单id", "供应商产品id", "供应商产品名称", "供应商id", "供应商名称",//
					"手机号", "成本", "产品id", "产品名称",//
					"回调时间", "供应商状态", "订单状态", "供货商流水号",//
					"失败原因", //
			};
			String[] fields = new String[] { "orderId", "productId", "providerProductName", "providerId", "providerName",//
					"phone", "cost", "categoryId", "categoryName",//
					"callTime", "status", "orderStatus", "orderCode",//
					"failReason", //
			};
			ExcelUtils.productExcel(resp, mapList, title, hearders, fields, log);
		} catch (Exception e) {
			log.info(e.getMessage(), e);
		}
	}

	public Map<Integer, Integer> getOrderMap(Integer maxNumber, List<BossProviderCharge> list) {
		List<Integer> orderIds = new ArrayList<>();
		Map<Integer, Integer> queryMap = new HashMap<>();
		for (BossProviderCharge bossProviderCharge : list) {
			orderIds.add(bossProviderCharge.getOrderId());
		}
		List query = orderInfoService.query("select new map(id as id,status as orderStatus) from OrderInfo where id in ?0", 0, maxNumber + 1,
				orderIds);
		List<Map<String, Object>> mapList2 = JsonUtils.getMapList(query);
		for (Map<String, Object> map : mapList2) {
			queryMap.put((Integer) map.get("id"), (Integer) map.get("orderStatus"));
		}
		return queryMap;
	}
}
