package xinxing.boss.admin.boss.other.cmd;


import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import xinxing.boss.admin.boss.other.domain.TransferOrder;
import xinxing.boss.admin.boss.other.service.TransferOrderService;
import xinxing.boss.admin.boss.other.util.BossCommonUtil;
import xinxing.boss.admin.common.excel.ExcelUtils;
import xinxing.boss.admin.common.persistence.Page;
import xinxing.boss.admin.common.persistence.PropertyFilter;
import xinxing.boss.admin.common.utils.http.HttpUtils;
import xinxing.boss.admin.common.utils.string.StringUtils;
import xinxing.boss.admin.common.utils.time.DateUtils;
import xinxing.boss.admin.common.web.BaseController;

@Controller
@RequestMapping(value = "boss/transferOrder")
public class TransferOrderCmd extends BaseController{
	private static Logger log = LoggerFactory.getLogger(TransferOrderCmd.class);
	@Autowired
	private TransferOrderService transferOrderService;
	
	/**
	 * 默认页面
	 */
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list(HttpServletRequest req, TransferOrder transferOrder) {
		Map<String, String> reqMap = HttpUtils.getReqParams(req);
		if (reqMap == null) {
			reqMap = new HashMap<String, String>();
		}
		String page = reqMap.get("page");
		if (StringUtils.isEmpty(page)) {
			page = "List";
		}
		reqMap.put("todayStart", DateUtils.getDate() + " 00:00:00");
		reqMap.put("todayEnd", DateUtils.getDate() + " 23:59:59");
		req.setAttribute("reqMap", reqMap);
		return "boss/transferOrderInfo/transferOrderInfo" + page;
	}
	
	
	/**
	 * 获取所有订单信息
	 * 
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = "transferOrderList")
	@ResponseBody
	public Map<String, Object> getTransferOrderList(HttpServletRequest request) throws ParseException {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Page<TransferOrder> page = getPage(request);
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
			//BaseUtil.testSearch(filters, "receiveTime");
			page = transferOrderService.search(page, filters);
			List<TransferOrder> result = page.getResult();

			map.put("rows", page.getResult());
			map.put("total", page.getTotalCount());
		} catch (Exception e) {
			log.info(e.getMessage(), e);
		}
		return map;
	}
	
	/**
	 * 订单统计
	 * 
	 * @return
	 */
	@RequestMapping(value = "getCountInfo")
	@ResponseBody
	public Map<String, Object> getCountInfo(HttpServletRequest request) {
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		Map<String, Object> map = new HashMap<>();
		try {
			map = BossCommonUtil.getCountMap(transferOrderService, filters, "transfer_ordre");
		} catch (Exception e) {
			map.put("failReason", "统计异常");
		}
		return map;
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("exportExcel")
	public void createExcel(HttpServletRequest req, HttpServletResponse resp) throws Exception, InterruptedException {
		Integer maxNumber = 1000000;
		Page<TransferOrder> page = getPage(req);
		page.setPageNo(1);
		page.setPageSize(maxNumber);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(req);
		page = transferOrderService.exportSearch(page, filters, maxNumber);
		if (page.getTotalCount() > maxNumber) {
			ExcelUtils.writeErrorMsg(resp, page.getTotalCount(), "导出数据不能超过100万条");
			return;
		}
		List<TransferOrder> list = page.getResult();// 获取数据
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		for (TransferOrder transferOrder : list) {
			Map<String, Object> beanToMap = ExcelUtils.beanToMap(transferOrder);
			//ExcelUtils.showValue(beanToMap, "status","查询失败","成功","失败","等待");
			showValue(beanToMap, "status","失败","成功","等待","异常");
			changeTimeFormat(beanToMap, "callbackTime");
			changeTimeFormat(beanToMap, "startTime");
			changeTimeFormat(beanToMap, "endTime");
			changeValue(beanToMap,"typeFlow","移动","联通","电信","省内","全国");
			mapList.add(beanToMap);
		}
		String title = "中转平台订单信息";

		String[] hearders = new String[] { "供货商订单号", "采购商订单号", "电话号码",
				"订单状态", "采购商产品名称", "回调地址","回调数据", 
				"回调结束时间","开始充值时间","充值结束时间 "};
		String[] fields = new String[] { "downId", "supplierId", "phone",
				"status", "typeFlow", "callbackAddress","callbackData",
				"callbackTime", "startTime","endTime"};
		ExcelUtils.productExcel(resp, mapList, title, hearders, fields, log);
	}

	/* 针对中转平台的流量类型进行转换
	 * 如把           10,全国,0,YD   转为--->   全国移动10M省内包
	 * 或把     1024,全国,0,YD   转为--->   全国移动1G省内包
	 */
	public static void changeValue(Map<String,Object> beanToMap,String name,String... values){
		String flowType = (String) beanToMap.get(name);
		if (flowType != null) {
			String[] split = flowType.split(",");
			String operator = "YD".equals(split[3]) ? 
								values[0]: ("LT".equals(split[3]) ? values[1] : values[2]);
			String area = "0".equals(split[2]) ? values[3] : values[4] ;
			
			String num = split[0]; //导出的excel中可以转换到11G
			if("1024".equals(num)) num = "1";
			if("2048".equals(num)) num = "2";
			if("3072".equals(num)) num = "3";
			if("4096".equals(num)) num = "4";
			if("5120".equals(num)) num = "5";
			if("6144".equals(num)) num = "6";
			if("7168".equals(num)) num = "7";
			if("8192".equals(num)) num = "8";
			if("9216".equals(num)) num = "9";
			if("10240".equals(num)) num = "10";
			if("11264".equals(num)) num = "11";
			if (split[0].length() == 4 || split[0].length() == 5 ) {
				beanToMap.put(name, split[1] + operator + num + "G" + area + "包"); 
			}else {
				beanToMap.put(name, split[1] + operator + split[0] + "M" + area + "包"); 
			}
		}
	}
	/* 把导出的时间格式化
	 * 2017-05-16 18:41:18.0   变为   2017-05-16 18:41:18
	 */
	public static void changeTimeFormat(Map<String,Object> beanToMap,String name){
		String time = (String) beanToMap.get(name);
		if (time != null) {
			beanToMap.put(name,time.substring(0, 19));
		}
	}
	
	/*
	 * 从0开始将值转为字符串 例如 0失败 1成功 2等待 3异常  values={0,1,2,3};  最后一位是不符合前面选项时的统一选择
	 */
	public static void showValue(Map<String, Object> beanToMap, String name,String... values) {
		if (beanToMap.get(name) != null) {
				switch ((Integer) beanToMap.get(name)) {
				case 0:
					beanToMap.put(name, values[0]);
					break;
				case 1:
					if (beanToMap.get("endTime") == null) {
						beanToMap.put(name,"提交成功");
					}else {
						beanToMap.put(name, "充值成功");
					}
					break;
				case 2:
					beanToMap.put(name, values[2]);
					break;
				default:
					beanToMap.put(name, values[3]);
					break;
			}
		} else
			beanToMap.put(name, null);
	}
	
}
