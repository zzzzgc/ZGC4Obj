package xinxing.boss.admin.boss.balance.cmd;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import xinxing.boss.admin.boss.balance.domain.BossCustomerBalanceAudit;
import xinxing.boss.admin.boss.balance.service.BossCustomerBalanceAuditService;
import xinxing.boss.admin.boss.customer.domain.CustomerInfo;
import xinxing.boss.admin.boss.customer.service.CustomerInfoService;
import xinxing.boss.admin.common.persistence.Page;
import xinxing.boss.admin.common.persistence.PropertyFilter;
import xinxing.boss.admin.common.utils.time.DateUtils;
import xinxing.boss.admin.common.web.BaseController;
import xinxing.boss.admin.system.user.utils.UserUtil;

@Controller
@RequestMapping(value = "boss/balanceRecord")
public class BossCustomerBalanceAuditCmd extends BaseController {

	private static Logger log = Logger.getLogger(BossCustomerBalanceAuditCmd.class);

	@Autowired
	private BossCustomerBalanceAuditService bossCustomerBalanceAuditService;

	@Autowired
	private CustomerInfoService customerInfoService;

	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list() {
		return "boss/finance/balanceAuditList";
	}

	/**
	 * 获取所有订单信息
	 * 
	 * @return
	 */
	@RequiresPermissions("boss:balanceRecord:view")
	@RequestMapping(value = "balanceRecordList", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getConfigList(HttpServletRequest request) {
		Page<BossCustomerBalanceAudit> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		page = bossCustomerBalanceAuditService.search(page, filters);
		return getEasyUIData(page);
	}

	/**
	 * 修改跳转
	 */
	@RequestMapping(value = "audit/{id}", method = RequestMethod.GET)
	public String auditForm(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("balance", bossCustomerBalanceAuditService.get(id));
		model.addAttribute("action", "audit");
		model.addAttribute("userName", UserUtil.getCurrentUser().getName());
		return "boss/finance/balanceAuditForm";
	}

	/**
	 * 审核注资
	 * 
	 * @param dict
	 * @param model
	 */
	@RequiresPermissions("boss:balanceRecord:audit")
	@RequestMapping(value = "audit", method = RequestMethod.POST)
	@ResponseBody
	public String audit(@Valid BossCustomerBalanceAudit balanceRecord, Model model) {
		bossCustomerBalanceAuditService.auditBalanceRecord(balanceRecord);
		return "success";
	}
	@RequestMapping(value = "shouXin")
	@ResponseBody
	public String shouXin(int customerId){
		BigDecimal sum = new BigDecimal(0);
		List<BossCustomerBalanceAudit> search = bossCustomerBalanceAuditService.search(
				"from BossCustomerBalanceAudit where customerId=?3 and addTime>?0 and type = ?1 and status = ?2", DateUtils.addMonths(new Date(), -1), 6, 1,customerId);
		if (search.size() > 0) {
			for (BossCustomerBalanceAudit bossCustomerBalanceAudit : search) {
				sum = sum.add(bossCustomerBalanceAudit.getBalance());
				bossCustomerBalanceAudit.setStatus(3);
				bossCustomerBalanceAuditService.update(bossCustomerBalanceAudit);
			}
		}
		return sum.toString();
	}
	
	/**
	 * 添加注资
	 */
	@RequestMapping(value = "add/{id}", method = RequestMethod.GET)
	public String addForm(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("customerInfo", customerInfoService.get(id));
		model.addAttribute("action", "audit");
		model.addAttribute("userName", UserUtil.getCurrentUser().getName());
				return "boss/finance/balanceAddAudit";
	}

	/**
	 * 添加注资
	 * 
	 * @param dict
	 * @param model
	 */
	@RequiresPermissions("boss:balanceRecord:add")
	@RequestMapping(value = "add/audit", method = RequestMethod.POST)
	@ResponseBody
	public String add(@Valid BossCustomerBalanceAudit balanceRecord, Model model) {
		balanceRecord.setAddTime(new Date());
		bossCustomerBalanceAuditService.addBalanceRecord(balanceRecord);
		return "success";
	}

}
