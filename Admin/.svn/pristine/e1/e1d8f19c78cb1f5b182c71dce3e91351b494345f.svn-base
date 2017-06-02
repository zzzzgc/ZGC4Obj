package xinxing.boss.admin.boss.customer.cmd;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
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
import xinxing.boss.admin.boss.customer.service.CustomerInfoService;
import xinxing.boss.admin.boss.customer.service.CustomerMoneyRecordService;
import xinxing.boss.admin.common.csv.CSVUtils;
import xinxing.boss.admin.common.download.DownLoadUtil;
import xinxing.boss.admin.common.excel.ExcelUtils;
import xinxing.boss.admin.common.persistence.Page;
import xinxing.boss.admin.common.persistence.PropertyFilter;
import xinxing.boss.admin.common.utils.json.JsonUtils;
import xinxing.boss.admin.common.utils.parameter.ParameterListener;
import xinxing.boss.admin.common.utils.security.PawUtils;
import xinxing.boss.admin.common.utils.time.DateUtils;
import xinxing.boss.admin.common.web.BaseController;
import xinxing.boss.admin.common.zip.ZipUtil;
import xinxing.boss.admin.system.user.domain.User;
import xinxing.boss.admin.system.user.service.UserService;
import xinxing.boss.admin.system.user.utils.UserUtil;

@Controller
@RequestMapping(value = "boss/customerInfo")
public class CustomerInfoCmd extends BaseController {
	private static Logger log = LoggerFactory.getLogger(CustomerInfoCmd.class);

	@Autowired
	private CustomerMoneyRecordService customerMoneyRecordService;
	@Autowired
	private CustomerInfoService customerInfoService;

	@Autowired
	private UserService userService;

	/**
	 * 默认页面
	 */
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list() {
		return "boss/customerInfo/customerInfoList";
	}

	/**
	 * 获取所有订单信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "customerInfoList", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getConfigList(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		String tableName = (String) request.getAttribute("tableName");
		if (tableName == null) {
			Page<CustomerInfo> page = getPage(request);
			List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
			page = customerInfoService.search(page, filters);
			map = getEasyUIData(page);
		} else {
			
		}
		return map;
	}

	/**
	 * 添加订单信息跳转
	 * 
	 * @param model
	 * @return
	 */
	@RequiresPermissions("boss:customerInfo:add")
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String create(Model model) {
		model.addAttribute("customerInfo", new CustomerInfo());
		return "boss/customerInfo/customerInfoAdd";
	}

	/**
	 * 添加采购商
	 * 
	 * @param dict
	 * @param model
	 */
	@RequiresPermissions("boss:customerInfo:add")
	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ResponseBody
	public String add(@Valid CustomerInfo customerInfo, Model model) {
		String pwd = PawUtils.getRandomNumber(6);
		customerInfo.setDevKey(PawUtils.getRandomLetter(15));
		customerInfo.setLoginPaw(pwd);
		customerInfo.setBalance(new BigDecimal(0));
		customerInfoService.save(customerInfo);
		log.info("customerInfo add:" + customerInfo.getId());
		ParameterListener.refresh("customer");
		// 添加采购商登录账号密码
		customerInfoService.addCustomerLoginAccount(customerInfo);
		return "新增成功，登录密码为：" + pwd;
	}

	/**
	 * 修改采购商信息跳转
	 */
	@RequiresPermissions("boss:customerInfo:update")
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String update(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("customerInfo", customerInfoService.get(id));
		return "boss/customerInfo/customerInfoUpdate";
	}

	/**
	 * 修改采购商信息
	 * 
	 * @param customerInfo
	 * @param model
	 * @return
	 */
	@RequiresPermissions("boss:customerInfo:update")
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody CustomerInfo customerInfo, HttpServletRequest request) {
		String pwd = PawUtils.getRandomNumber(6);
		/*
		 * customerInfo.setDevKey(PawUtils.getRandomLetter(15));
		 * customerInfo.setLoginPaw(pwd);
		 */
		String loginName = request.getParameter("uloginName");
		User user = userService.getUser(loginName);
		customerInfoService.save(customerInfo);
		user.setLoginName(customerInfo.getLoginName());
		user.setName(customerInfo.getCustomerName());
		userService.updateUserNotPwd(user);
		log.info("customerInfo update:" + customerInfo.getId());
		ParameterListener.refresh("customer");
		return "success";
	}

	/**
	 * 批量修改采购商 开通/冻结
	 * 
	 * @param customerInfo
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "updateStatus", method = RequestMethod.POST)
	@ResponseBody
	public String updateStatus(HttpServletRequest request) {
		String[] ids = request.getParameterValues("ids");
		String state = request.getParameter("state");
		if (ids == null || StringUtils.isBlank(state)) {
			return "error";
		}
		int stateInt = Integer.parseInt(state);
		List<CustomerInfo> list = new ArrayList<CustomerInfo>();
		log.info(UserUtil.getCurrentUser().getLoginName() + ",updateStatus更新状态:ids" + ids + ",status:" + state);
		for (String id : ids) {
			CustomerInfo customerInfo = customerInfoService.get(Integer.parseInt(id));
			log.info(UserUtil.getCurrentUser().getId() + "-" + UserUtil.getCurrentUser().getName() + "修改前:" + customerInfo.getStatus() + ",修改后:"
					+ state + "---" + JsonUtils.obj2Json(customerInfo));
			customerInfo.setStatus(stateInt);
			list.add(customerInfo);
		}

		for (CustomerInfo customerInfo : list) {
			customerInfoService.save(customerInfo);
			userService.updateUserState(customerInfo.getLoginName(), stateInt);
		}

		ParameterListener.refresh("customer");
		return "success";
	}

	/**
	 * 删除订单
	 * 
	 * @param id
	 * @return
	 */
	@RequiresPermissions("boss:customerInfo:delete")
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Integer id) {
		customerInfoService.delete(id);
		log.info("customerInfo delete:" + id);
		ParameterListener.refresh("customer");
		return "success";

	}

	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 * @throws InterruptedException
	 * @throws Exception
	 */
	@RequestMapping("exportExcel")
	public void createExcel(HttpServletRequest request, HttpServletResponse response) throws Exception, InterruptedException {
		List<CustomerInfo> list = customerInfoService.getAll();// 获取数据
		List<Map<String, Object>> mapList = new ArrayList<>();
		for (CustomerInfo customerInfo : list) {
			Map<String, Object> beanToMap = ExcelUtils.beanToMap(customerInfo);
			ExcelUtils.showValue(beanToMap, "status", "冻结", "正常");
			ExcelUtils.showValue(beanToMap, "tickType", "", "增值税发票", "普通发票");
			ExcelUtils.showValue(beanToMap, "isBackCost", "不回调成本价", "回调成本价");
			mapList.add(beanToMap);
		}
		String title = "采购商信息";
		String[] hearders = new String[] { "编号", "采购商名称", "采购商联系号码", "采购商联系人", "资金余额", "添加时间", "备注", "采购商回调地址", "开发者密钥", "采购商联系地址", "登录用户名", "登录密码",
				"状态", "发票类型", "是否发送短信", "允许ip", "优先级", "是否回调", "采购商简称" };// 表头数组
		String[] fields = new String[] { "id", "customerName", "customerNumber", "customerContact", "balance", "addTime", "remark",
				"callbackAddress", "devKey", "customerAddress", "loginName", "loginPaw", "status", "tickType", "isSendMsg", "allowIp", "priority", "isBackCost",
				"cusSimpleName" };// CustomerInfo对象属性数组
		ExcelUtils.productExcel(response, mapList, title, hearders, fields, log);
	}

	/**
	 * 发送邮件及短信给采购商
	 * 
	 * @param customerId
	 * @return
	 */
	@RequestMapping("sendEmail")
	public String sendCusEmail(String customerId) {
		if (StringUtils.isNotBlank(customerId)) {
			customerInfoService.sendEmail(customerId);
		}
		return "success";
	}

	/**
	 * 检查采购商简称
	 * 
	 * @param simpleName
	 * @return
	 */
	@RequestMapping("checkSimpleName")
	@ResponseBody
	public Map<String, String> checkSimpleName(String simpleName) {
		return customerInfoService.checkSimpleName(simpleName);
	}

	/**
	 * 修改采购商密码
	 * 
	 * @param simpleName
	 * @return
	 */
	@RequestMapping("updatePaw")
	@ResponseBody
	public String updatePaw(String customerInfo) {
		String pasWord = "";
		try {

			log.info("customerInfo:" + customerInfo);
			customerInfo = customerInfo.replace("&quot;", "\"");

			log.info("customerInfo:" + customerInfo);
			CustomerInfo customer = JsonUtils.json2Obj(customerInfo, CustomerInfo.class);
			pasWord = PawUtils.getRandomNumber(6);
			customer.setLoginPaw(pasWord);
			customerInfoService.update(customer);
			User user = userService.getUser(customer.getLoginName());
			user.setPlainPassword(pasWord);
			userService.updatePwd(user);
			return pasWord;
		} catch (Exception e) {
			log.info(e.getMessage(), e);
			log.error(e.getMessage(), e);
		}
		log.info("pasWord:" + pasWord);
		return pasWord;
	}
}
