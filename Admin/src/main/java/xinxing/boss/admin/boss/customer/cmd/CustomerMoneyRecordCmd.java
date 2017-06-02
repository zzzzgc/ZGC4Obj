package xinxing.boss.admin.boss.customer.cmd;

import java.text.ParseException;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import xinxing.boss.admin.boss.customer.domain.CustomerInfo;
import xinxing.boss.admin.boss.customer.domain.CustomerMoneyRecord;
import xinxing.boss.admin.boss.customer.service.CustomerInfoService;
import xinxing.boss.admin.boss.customer.service.CustomerMoneyRecordService;
import xinxing.boss.admin.common.excel.ExcelUtils;
import xinxing.boss.admin.common.persistence.Page;
import xinxing.boss.admin.common.persistence.PropertyFilter;
import xinxing.boss.admin.common.utils.time.DateUtils;
import xinxing.boss.admin.common.web.BaseController;

@Controller
@RequestMapping(value = "boss/customerMoneyRecord")
public class CustomerMoneyRecordCmd extends BaseController {
	private static Logger logger = LoggerFactory.getLogger(CustomerMoneyRecordCmd.class);
	public static String[] hearders = new String[] { "资金余额", "流水号", "充值号码", "收支类型", "单价", "发生前余额", "发生金额", "备注", "时间" };
	public static String[] fields = new String[] { "fundBalance", "orderKey", "phone", "costType", "price", "beforeMoney", "cost", "remark",
			"recordTime" };
	public static String[] inside_hearders = new String[] { "订单号", "采购商名称", "资金余额", "流水号", "充值号码", "收支类型", "单价", "发生前余额", "发生金额", "备注", "时间" };
	public static String[] inside_fields = new String[] { "id", "customerName", "fundBalance", "orderKey", "phone", "costType", "price",
			"beforeMoney", "cost", "remark", "recordTime" };
	@Autowired
	private CustomerMoneyRecordService customerMoneyRecordService;
	@Autowired
	private CustomerInfoService customerInfoService;

	/**
	 * 默认页面
	 */
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list() {
		return "boss/customerMoneyRecord/customerMoneyRecordList";
	}

	/**
	 * 获取所有订单信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "customerMoneyRecordList", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getConfigList(HttpServletRequest request) {
		Page<CustomerMoneyRecord> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		page = customerMoneyRecordService.search(page, filters);
		return getEasyUIData(page);
	}

	/**
	 * 添加订单信息跳转
	 * 
	 * @param model
	 * @return
	 */
	@RequiresPermissions("boss:customerMoneyRecord:add")
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String create(Model model) {
		model.addAttribute("customerMoneyRecord", new CustomerMoneyRecord());
		return "boss/customerMoneyRecord/customerMoneyRecordAdd";
	}

	/**
	 * 添加订单信息
	 * 
	 * @param dict
	 * @param model
	 */
	@RequiresPermissions("boss:customerMoneyRecord:add")
	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ResponseBody
	public String add(@Valid CustomerMoneyRecord customerMoneyRecord, Model model) {
		customerMoneyRecordService.save(customerMoneyRecord);
		logger.info("customerMoneyRecord add:" + customerMoneyRecord.getId());
		return "success";
	}

	/**
	 * 修改订单信息跳转
	 */
	@RequiresPermissions("boss:customerMoneyRecord:update")
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String update(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("customerMoneyRecord", customerMoneyRecordService.get(id));
		return "boss/customerMoneyRecord/customerMoneyRecordUpdate";
	}

	/**
	 * 修改订单信息
	 * 
	 * @param tmallOrder
	 * @param model
	 * @return
	 */
	@RequiresPermissions("boss:customerMoneyRecord:update")
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody CustomerMoneyRecord customerMoneyRecord, Model model) {
		customerMoneyRecordService.update(customerMoneyRecord);
		return "success";
	}

	/**
	 * 删除订单
	 * 
	 * @param id
	 * @return
	 */
	@RequiresPermissions("boss:customerMoneyRecord:delete")
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Integer id) {
		customerMoneyRecordService.delete(id);
		logger.info("customerMoneyRecord delete:" + id);
		return "success";
	}

	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("exportExcel")
	public void createExcel(HttpServletRequest request, HttpServletResponse response) throws ParseException {
		logger.info("excel启动 " + DateUtils.dateFormat(new Date()));
		Page<CustomerMoneyRecord> page = getPage(request);
		page.setPageNo(1);
		page.setPageSize(10000000);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		page = customerMoneyRecordService.search(page, filters);
		List<CustomerMoneyRecord> list = page.getResult();
		// List<CustomerMoneyRecord> list = customerMoneyRecordService.getAll();//获取数据
		List<Map<String, Object>> mapList = new ArrayList<>();
		List<CustomerInfo> customerInfos = customerInfoService.getAll();
		Map<Integer, CustomerInfo> customerInfoMap = new HashMap<>();
		for (CustomerInfo customerInfo : customerInfos) {
			customerInfoMap.put(customerInfo.getId(), customerInfo);
		}
		for (CustomerMoneyRecord customerMoneyRecord : list) {
			if (customerInfoMap.containsKey(customerMoneyRecord.getCustomerId())) {
				Map<String, Object> beanToMap = ExcelUtils.beanToMap(customerMoneyRecord);
				CustomerInfo customerInfo = customerInfoMap.get(customerMoneyRecord.getCustomerId());
				beanToMap.put("customerName", customerInfo.getCustomerName());
				ExcelUtils.showValue(beanToMap, "costType", "", "人工注资", "系统注资", "消费", "失败返还", "人工扣款");
				beanToMap.put("beforeMoney", customerMoneyRecord.getFundBalance().subtract(customerMoneyRecord.getCost()));
				mapList.add(beanToMap);
			}
		}
		System.out.println("mapList.size():" + mapList.size());
		String title = "采购商注资";
		String[] hearders = new String[] { "采购商名称", "发生前资金余额", "发生金额", "资金余额", "收支类型", "备注", "时间" };// 表头数组
		String[] fields = new String[] { "customerName", "beforeMoney", "cost", "fundBalance", "costType", "remark", "recordTime" };// CustomerMoneyRecord对象属性数组
		ExcelUtils.productExcel(response, mapList, title, hearders, fields, logger);
	}
}
