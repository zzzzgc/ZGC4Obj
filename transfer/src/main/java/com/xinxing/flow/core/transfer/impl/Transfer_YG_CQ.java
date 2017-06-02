package com.xinxing.flow.core.transfer.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.xinxing.flow.core.downstream.status.CQ_Status;
import com.xinxing.flow.core.transfer.Transfer;
import com.xinxing.flow.core.upstream.tools.Transfer4HuaYi_upUtils;
import com.xinxing.flow.erorr.NULLForProductException;
import com.xinxing.flow.erorr.NULLOrderIdException;
import com.xinxing.flow.status.whoId;
import com.xinxing.transfer.common.json.JsonUtils;
import com.xinxing.transfer.common.resource.Constants;
import com.xinxing.transfer.common.time.TimeUtils;
import com.xinxing.transfer.po.BossCustomerBalanceRecord;
import com.xinxing.transfer.po.BossOrder;
import com.xinxing.transfer.po.BossProductCategory;
import com.xinxing.transfer.po.CQ_CapitalReport;
import com.xinxing.transfer.po.TransferOrdre;
import com.xinxing.transfer.service.BossCustomerBalanceRecordService;
import com.xinxing.transfer.service.BossCustomerProductService;
import com.xinxing.transfer.service.BossOrderserService;
import com.xinxing.transfer.service.BossProductCategoryService;
import com.xinxing.transfer.service.TransferOrdreService;

/**
 * 中转-关节(非静态的工具,涉及到注入的类)
 * 
 * @author Administrator
 */
public class Transfer_YG_CQ implements Transfer {

	@Autowired
	private BossProductCategoryService bossProductCategoryService;

	@Autowired
	private BossCustomerProductService bossCustomerProductService;

	@Autowired
	private TransferOrdreService transferOrdreService;

	@Autowired
	private BossOrderserService bossOrderserService;

	@Autowired
	private BossCustomerBalanceRecordService bossCustomerBalanceRecordService;

	@Override
	public String getProduct(String pDTVALUE, String uRPROVID, String iSP, String rOAMING_TYPE) throws NULLForProductException {

		Integer PDTVALUE = Integer.parseInt(pDTVALUE);
		Integer area = Integer.parseInt(rOAMING_TYPE); // 0 省内 1全国
		Integer Productnum = null; // 流量值
		Integer Productunit = null; // 流量类型 1按MB算 2按GB算
		String Operator = Transfer4HuaYi_upUtils.getMobileoperator(iSP); // 运营商
		String Urprovid = Transfer4HuaYi_upUtils.getProvince(uRPROVID);

		if (PDTVALUE < 1000) {
			Productnum = PDTVALUE;
			Productunit = 1; // 流量按MB算
		} else {
			Productnum = PDTVALUE / 1000;
			Productunit = 2; // 流量按GB算
		}

		// 获取匹配的产品
		List<BossProductCategory> list = bossProductCategoryService.getMatchingProducts(area, Productnum, Productunit, Operator, Urprovid);;

		if (list.size() > 0) {
			for (BossProductCategory bossProductCategory : list) {
				Integer Categoryid = bossProductCategory.getId();
				Integer Customerid = Integer.parseInt(Constants.getString("CQ_MerChant"));
				String id = bossCustomerProductService.getDockingProductId(Customerid, Categoryid);
				if (id != null) {
					return id;
				}
			}
		}
		throw new NULLForProductException();
	}

	@SuppressWarnings("unused")
	public String getSendParam(Map<String, Object> param) throws NULLForProductException {
		// 下游订单
		String CTMID = (String) param.get("CTMID");
		String CTMORDID = (String) param.get("CTMORDID");
		String CTMTIME = (String) param.get("CTMTIME");
		String PLAYACC = (String) param.get("PLAYACC");
		String PDTVALUE = (String) param.get("PDTVALUE");
		String CTMRETURL = (String) param.get("CTMRETURL");
		String URPROVID = (String) param.get("URPROVID");
		String ISP = (String) param.get("ISP");
		String ROAMING_TYPE = (String) param.get("ROAMING_TYPE");
		String INTECMD = (String) param.get("INTECMD");
		String SIGN = (String) param.get("SIGN");

		// 上游订单
		String MerChan = CTMID;// 商户唯一标示
		String Product = this.getProduct(PDTVALUE, URPROVID, ISP, ROAMING_TYPE);
		String Mobile = PLAYACC;// 手机号(只能一个)
		String Version = "V1.0";// 固定值：V1.0
		String Action = "SendOrder";// 固定值：SendOrder
		String FlowKey = CTMORDID;// 客户订单标识（唯一）

		// 获取文件体
		HashMap<Object, Object> paramMap = new HashMap<>();
		paramMap.put("MerChant", MerChan);
		paramMap.put("Product", Product);
		paramMap.put("Mobile", Mobile);
		paramMap.put("Version", Version);
		paramMap.put("Action", Action);
		paramMap.put("FlowKey", FlowKey);
		String SendParam = JsonUtils.obj2Json(paramMap);
		return SendParam;
	}

	@Override
	public String getQueryParam(Map<String, Object> param) throws NULLForProductException, NULLOrderIdException {
		String CTMID = (String) param.get("CTMID");
		String CTMORDID = (String) param.get("CTMORDID");
		//String INTECMD = (String) param.get("INTECMD");

		// OrderInfoBydownstreamId
		TransferOrdre orderInfo = transferOrdreService.getOrderInfo(whoId.DOWNID.status, CTMORDID);
		if (orderInfo != null) {
			String phone = orderInfo.getPhone();
			HashMap<String, Object> map = new HashMap<>();
			String MerChant = CTMID;// 商户唯一标示
			String Version = "V1.0";// 固定值：V1.0
			String Action = "GetCallBack";// 固定值：GetCallBack
			String Mobile = phone;// 电话号码
			String FlowKey = CTMORDID;// 客户订单标识（唯一）
			map.put("MerChant", MerChant);
			map.put("Version", Version);
			map.put("Action", Action);
			map.put("Mobile", Mobile);
			map.put("FlowKey", FlowKey);
			return JSON.toJSONString(map);
		} else {
			throw new NULLOrderIdException();
		}
	}
	
	@Override
	public String getQueryBalanceParam(Map<String, Object> param) {
		String CTMID = (String) param.get("CTMID");
		
		HashMap<String, Object> map = new HashMap<>(3);
		map.put("MerChant", CTMID);
		map.put("Version", "V1.0");
		map.put("Action", "GetBalance");
		return JSON.toJSONString(map);
	}

	/**
	 * 获取CQ的回调请求参数
	 * 
	 * @param INTECMD
	 * @param status
	 * @param YGCallbackMap
	 * @return
	 */
	public static Map<String, Object> getCallbackResponseParam(String INTECMD, String status, Map<String, Object> YGCallbackMap) {
		String FlowKey = (String) YGCallbackMap.get("FlowKey");
		String OrderKey = (String) YGCallbackMap.get("OrderKey");
		String Phone = (String) YGCallbackMap.get("Phone");
		String OrderStatus = (String) YGCallbackMap.get("OrderStatus");
		String FailReason = (String) YGCallbackMap.get("FailReason");

		HashMap<String, Object> map = new HashMap<>();

		if (!"1".equals(status)) {
			map.put("TRADEERROR", status); // 交易错误码 TODO 待处理,返回指定错误的状态
		} else {
			// map.put("TRDACTVALUE", value);// 接入方实际完成充值面值
		}
		map.put("INTECMD", INTECMD);// 文件头中存在INTECMD(借口套接字)
		map.put("TRADESTATUS", status);// 交易状态码
		map.put("CTMID", Constants.getString("CQ_MerChant")); // 商户编号
		map.put("CTMORDID", FlowKey); // 商户交易流水号
		map.put("TRDSN", OrderKey); // 接入方流水号
		map.put("TRDREQTIME", DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:dd")); // 接入方完成时间

		return map;
	}

	public void addOrder(String status, Map<String, Object> sendMap) {
		int orderStatus = 0;
		TransferOrdre obj = new TransferOrdre();

		String CTMORDID = (String) sendMap.get("CTMORDID"); // 商户交易流水
		String PLAYACC = (String) sendMap.get("PLAYACC"); // 手机号码
		String CTMRETURL = (String) sendMap.get("CTMRETURL"); // 订单结果回调地址

		StringBuilder sb = new StringBuilder();
		String PDTVALUE = (String) sendMap.get("PDTVALUE"); // 充值面额
		String URPROVID = getProvince((String) sendMap.get("URPROVID")); // 用户归属省份
																			// ID
		String ROAMING_TYPE = (String) sendMap.get("ROAMING_TYPE"); // 漫游类型 0省内
																	// 1全国
		String ISP = (String) sendMap.get("ISP"); // 运营商 YD LT DX
		sb.append(PDTVALUE + "," + URPROVID + "," + ROAMING_TYPE + "," + ISP);

		obj.setTypeflow(sb.toString());
		obj.setDownid(CTMORDID);
		obj.setPhone(PLAYACC);
		obj.setCallbackaddress(CTMRETURL);
		obj.setStarttime(new Date());

		if (CQ_Status.SEND_SUCCESS.status.equals(status)) {
			orderStatus = 3;
		} else if (CQ_Status.OREDR_SUCCESS.status.equals(status)) {
			orderStatus = 1;
		} else {
			orderStatus = 2;
		}
		obj.setStatus(orderStatus);

		transferOrdreService.addOredr(obj);
	}

	public boolean queryUpdateOredr(String status, Map<String, Object> queryMap) {
		String downId = (String) queryMap.get("CTMORDID");
		Date Endtime = new Date();
		int STATUS = Integer.parseInt(status);

		TransferOrdre transferOrdre = new TransferOrdre();
		transferOrdre.setDownid(downId);
		transferOrdre.setStatus(STATUS);
		transferOrdre.setEndtime(Endtime);

		boolean updateOrder = transferOrdreService.updateOrder(whoId.DOWNID.status, transferOrdre);
		return updateOrder;
	}

	/**
	 * 通过省份id获取省份名称
	 * 
	 * @param URPROVID
	 *            省份id
	 * @return 省份
	 */
	public static String getProvince(String URPROVID) {
		String product = null;
		URPROVID = URPROVID.replace("\n", "");
		int a = new Integer(URPROVID);
		switch (a) {
			case 0 :
				product = "全国";
				break;
			case 1 :
				product = "北京";
				break;
			case 2 :
				product = "上海";
				break;
			case 3 :
				product = "天津";
				break;
			case 4 :
				product = "重庆";
				break;
			case 5 :
				product = "河北";
				break;
			case 6 :
				product = "山西";
				break;
			case 7 :
				product = "内蒙古";
				break;
			case 8 :
				product = "辽宁";
				break;
			case 9 :
				product = "吉林";
				break;
			case 10 :
				product = "黑龙江";
				break;
			case 11 :
				product = "江苏";
				break;
			case 12 :
				product = "浙江";
				break;
			case 13 :
				product = "安徽";
				break;
			case 14 :
				product = "福建";
				break;
			case 15 :
				product = "江西";
				break;
			case 16 :
				product = "山东";
				break;
			case 17 :
				product = "河南";
				break;
			case 18 :
				product = "湖北";
				break;
			case 19 :
				product = "湖南";
				break;
			case 20 :
				product = "广东";
				break;
			case 21 :
				product = "广西";
				break;
			case 22 :
				product = "海南";
				break;
			case 23 :
				product = "四川";
				break;
			case 24 :
				product = "贵州";
				break;
			case 25 :
				product = "云南";
				break;
			case 26 :
				product = "西藏";
				break;
			case 27 :
				product = "陕西";
				break;
			case 28 :
				product = "甘肃";
				break;
			case 29 :
				product = "青海";
				break;
			case 30 :
				product = "宁夏";
				break;
			case 31 :
				product = "新疆";
				break;
			default :
				product = null;
				break;
		}
		return product;
	}

	/**
	 * 把数据库中的订单流水转换成CQ需要的订单流水格式
	 * 
	 * @param endTime
	 * 
	 * @param dDAccount
	 * @return
	 */
	public StringBuffer YG_DDA2CQ_DDA(int CustomerName, Date startTime, Date endTime) {

		List<BossCustomerBalanceRecord> DDAccount = bossCustomerBalanceRecordService.getRecord(Integer.parseInt(Constants.getString("CQ_MerChant")), startTime, endTime);

		List<CQ_CapitalReport> CapitalReportList = new ArrayList<>();
		for (BossCustomerBalanceRecord bossCustomerBalanceRecord : DDAccount) {
			// 发生金额
			String cost = bossCustomerBalanceRecord.getCost().toString();
			// 收支类型 1人工注资2系统注资 3消费4失败返还5人工扣款
			String costtype = bossCustomerBalanceRecord.getCosttype().toString();
			// 资金余额
			String fundbalance = bossCustomerBalanceRecord.getFundbalance().toString();
			// 手机号
			String phone = bossCustomerBalanceRecord.getPhone();
			// 发生时间
			String recordtime = DateFormatUtils.format(bossCustomerBalanceRecord.getRecordtime(), "yyyy-MM-dd HH:mm:ss");
			// 采购商流水号
			String orderkey = bossCustomerBalanceRecord.getOrderkey();

			switch (costtype) {
				case "1" :
				case "2" :
				case "5" :
					continue;
				default :
					break;
			}

			BossOrder order = bossOrderserService.selectOrder(whoId.DOWNID.status, orderkey);
			String categoryid = order.getCategoryid().toString();// 产品分类id
			String biztype = order.getBiztype().toString();// 业务类型
			String operator = order.getOperator();// 运营商
			
			String providerkey = order.getProviderkey()==null?"":order.getProviderkey().toString();// 上游(供应商,非YG的Id)Id
			String province = order.getProvince();// 省份

			BossProductCategory Product = bossProductCategoryService.getProduct(categoryid);
			String standarPrice = Product.getStandarprice().toString();// 标准价
			String productNum = Product.getProductnum().toString();// 数量
			/*
			 * 运营商 省份 面值 基准价 发货流水号 上游流水号 充值号码 交易类型 变动金额 账户余额 资金变动时间 业务类型
			 */
			CQ_CapitalReport report = new CQ_CapitalReport();
			report.setOperator(operator);// 运营商
			report.setProvince(province);// 省份
			
			report.setDenomination(productNum);// 面值
			
			standarPrice = standarPrice.substring(0, standarPrice.indexOf("."));
			report.setStandardPrice(standarPrice);// 基准价
			
			report.setDownSerialNumber(orderkey);// 发货流水号
			report.setUpSerialNumber(providerkey);// 上游流水号
			report.setRechargePhone(phone);// 充值号码

			String type = null;
			switch (costtype) {
				case "1" :// 人工注资
					type = "加款";
					break;
				case "2" :// 系统注资
					type = "加款";
					break;
				case "3" :// 消费
					type = "扣款";
					break;
				case "4" :// 失败返还
					type = "退款";
					break;
				case "5" :// 人工扣款
					type = "人工扣款";
					break;
				default :
					break;
			}
			report.setType(type);// 交易类型

			cost = cost.substring(0, cost.indexOf("."));
			report.setChangesMoney(cost);// 变动金额

			fundbalance = fundbalance.substring(0, fundbalance.indexOf("."));
			report.setBalance(fundbalance);// 账户余额

			report.setBalanceChangesDate(recordtime);// 资金变动时间
			switch (biztype) {
				case "1" :
					biztype = "流量业务";
					break;
				case "2" :
					biztype = "话费业务";
					break;
				case "0" :
					biztype = "----";
					break;
				default :
					biztype = "****";
					break;
			}
			report.setServiceType(biztype);// 业务类型

			CapitalReportList.add(report);
		}

		StringBuffer sb = new StringBuffer();
		sb.append("运营商,省份,面值,基准价,发货流水号,上游流水号,充值号码,交易类型,变动金额,账户余额,资金变动时间,业务类型" + System.getProperty("line.separator"));
		for (CQ_CapitalReport c : CapitalReportList) {
			sb.append(c.getOperator()).append(",").append(c.getProvince()).append(",").append(c.getDenomination()).append(",").append(c.getStandardPrice()).append(",").append(c.getDownSerialNumber()).append(",").append(c.getUpSerialNumber()).append(",").append(c.getRechargePhone()).append(",")
					.append(c.getType()).append(",").append(c.getChangesMoney()).append(",").append(c.getBalance()).append(",").append(c.getBalanceChangesDate()).append(",").append(c.getServiceType()).append(System.getProperty("line.separator"));
		}

		return sb;
	}

	/**
	 * 每天生成资金流水记录 账户名称: 商户账户在接入商系统中的中文名称 凌晨余额: 商户账户在账单日 0 点时的余额 今天第一笔订单记录之前的余额
	 * 次日凌晨余额: 商户账户在账单次日 0 点时的余额 今天最后一笔订单。。 加款金额: 账单日加款总金额（单位元） 注资 cosType收支类型
	 * 1人工注资2系统注资 提现金额: 账单日提现总金额（单位元） 扣款金额: 账单日充值扣款总金额和（单位元） 3消费5人工扣款 退款金额:
	 * 账单日充值退款总金额和（单位元） 4失败返还 当天交易金额: 扣款金额-退款金额: （单位元） "customerId"
	 * "fundBalance" "costType" "remark" "cost" "recordTime" "price" "phone"
	 * "orderkey" "600002" "99994.000" "3" "为订单号153595充值，消费3.0元" "-3.000"
	 * "2017-03-22 16:14:30" "3.000" "13622662311" "20170322161430113"
	 * @param date2 
	 */
	public StringBuilder bossCustomerBalanceRecord4Day(BossCustomerBalanceRecordService bossCustomerBalanceRecordService, String customerName, Integer customerId, Date date) {
		StringBuilder sb = new StringBuilder(60);
		sb.append("账户名称：").append(customerName).append("\r\n");
		// 加款金额
		Integer sumAddfund = 0;
		// 扣款金额
		Integer sumDebitfund = 0;
		// 退款金额
		Integer sumRefund = 0;
		Integer fundBalance = 0;
		Integer costType = 0;
		BigDecimal cost = new BigDecimal(0);
		// 昨天
		date = TimeUtils.addDayForNow(date, -1);
		Date startTime = TimeUtils.getDateByStr(TimeUtils.date2FormateStr(date) + " 00:00:00");
		List<BossCustomerBalanceRecord> listBalanceRecord = bossCustomerBalanceRecordService.findOneGT(customerId, "recordTime DESC", 0, 1, startTime);
		if (listBalanceRecord == null || listBalanceRecord.size() == 0) {
			listBalanceRecord = bossCustomerBalanceRecordService.findOneLT(customerId, "recordTime DESC", 0, 1, startTime);
		}
		fundBalance = listBalanceRecord.get(0).getFundbalance().intValue();
		sb.append("凌晨余额：").append(fundBalance).append("\r\n");
		date = TimeUtils.addDayForNow(date, -1);
		startTime = TimeUtils.getDateByStr(TimeUtils.date2FormateStr(date) + " 00:00:00");
		listBalanceRecord = bossCustomerBalanceRecordService.findOneGT(customerId, "recordTime DESC", 0, 1, startTime);
		if (listBalanceRecord == null || listBalanceRecord.size() == 0) {
			listBalanceRecord = bossCustomerBalanceRecordService.findOneLT(customerId, "recordTime DESC", 0, 1, startTime);
		}
		fundBalance = listBalanceRecord.get(0).getFundbalance().intValue();
		sb.append("次日凌晨余额：").append(fundBalance).append("\r\n");
		date = TimeUtils.addDayForNow(date, 1);
		startTime = TimeUtils.getDateByStr(TimeUtils.date2FormateStr(date) + " 00:00:00");
		Date endTime = TimeUtils.getDateByStr(TimeUtils.date2FormateStr(date) + " 23:59:59");
		listBalanceRecord = bossCustomerBalanceRecordService.getRecord(customerId, startTime, endTime);
		for (BossCustomerBalanceRecord balanceRecord : listBalanceRecord) {
			costType = balanceRecord.getCosttype();
			cost = balanceRecord.getCost();
			switch (costType) {
				case 1 :
				case 2 :
					sumAddfund += cost.intValue();
					break;
				case 3 :
				case 5 :
					sumDebitfund += cost.intValue();
					break;
				default :
					sumRefund += cost.intValue();
					break;
			}
		}
		sb.append("加款金额：").append(sumAddfund).append("\r\n").append("提现金额：").append("0\r\n")
				.append("扣款金额：").append(Math.abs(sumDebitfund)).append("\r\n").append("退款金额：")
				.append(sumRefund).append("\r\n").append("当天交易金额：").append(Math.abs(sumDebitfund)- sumRefund)
				.append("\r\n");
		return sb;
	}
}
