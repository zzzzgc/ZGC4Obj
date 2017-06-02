package xinxing.boss.admin.boss.providerBalance.cmd;

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
import xinxing.boss.admin.boss.provider.service.ProviderInfoService;
import xinxing.boss.admin.boss.providerBalance.domain.ProviderBalanceAudit;
import xinxing.boss.admin.boss.providerBalance.service.ProviderBalanceAuditService;
import xinxing.boss.admin.common.persistence.Page;
import xinxing.boss.admin.common.persistence.PropertyFilter;
import xinxing.boss.admin.common.web.BaseController;
import xinxing.boss.admin.system.user.utils.UserUtil;

@Controller
@RequestMapping(value="boss/providerBalanceRecord")
public class ProviderBalanceRecordCmd extends BaseController {

	private static Logger log = Logger.getLogger(ProviderBalanceRecordCmd.class);
	
	@Autowired
	private ProviderBalanceAuditService providerBalanceAuditService;
	
	@Autowired
	private ProviderInfoService providerInfoService;
	
	
	@RequestMapping(value="list",method = RequestMethod.GET)
	public String list() {
		return "boss/providerFinance/providerBalanceAuditList";
	}
	
	/**
	 * 获取所有订单信息
	 * @return
	 */
	@RequiresPermissions("boss:providerBalanceRecord:view")
	@RequestMapping(value="balanceRecordList",method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getConfigList(HttpServletRequest request){
		Page<ProviderBalanceAudit> page = getPage(request);		
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		page = providerBalanceAuditService.search(page, filters);
		return getEasyUIData(page);
	}
	
	
	/**
	 * 修改跳转
	 */
	@RequestMapping(value = "audit/{id}", method = RequestMethod.GET)
	public String auditForm(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("balance", providerBalanceAuditService.get(id));
		model.addAttribute("action", "audit");
		model.addAttribute("userName",UserUtil.getCurrentUser().getName());
		return "boss/providerFinance/providerBalanceAuditForm";
	}
	/**
	 * 修改跳转到复核页面
	 */
	@RequestMapping(value = "review/{id}", method = RequestMethod.GET)
	public String reviewForm(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("balance", providerBalanceAuditService.get(id));
		model.addAttribute("action", "audit");
		model.addAttribute("userName",UserUtil.getCurrentUser().getName());
		return "boss/providerFinance/providerBalanceReviewForm";
	}
	
	/**
	 * 审核注资
	 * 
	 * @param dict
	 * @param model
	 */
	@RequiresPermissions("boss:providerBalanceRecord:audit")
	@RequestMapping(value = "audit", method = RequestMethod.POST)
	@ResponseBody
	public String audit(@Valid ProviderBalanceAudit providerBalanceAudit, Model model) {
		providerBalanceAuditService.auditProviderBalanceRecord(providerBalanceAudit);
		return "success";
	}
	/**
	 * 复核注资
	 * 
	 * @param dict
	 * @param model
	 */
	@RequiresPermissions("boss:providerBalanceRecord:review")
	@RequestMapping(value = "review", method = RequestMethod.POST)
	@ResponseBody
	public String review(@Valid ProviderBalanceAudit providerBalanceAudit, Model model) {
		providerBalanceAuditService.reviewProviderBalanceRecord(providerBalanceAudit);
		return "success";
	}
	
	/**
	 * 添加供应商注资
	 */
	@RequestMapping(value = "addProvider/{id}", method = RequestMethod.GET)
	public String addProviderForm(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("providerInfo", providerInfoService.get(id));
		model.addAttribute("action", "audit");
		model.addAttribute("userName",UserUtil.getCurrentUser().getName());
		return "boss/providerFinance/providerBalanceAddAudit";
	}
	
	
	/**
	 * 添加注资
	 * 
	 * @param dict
	 * @param model
	 */
	@RequiresPermissions("boss:providerBalanceRecord:add")
	@RequestMapping(value = "addProvider/audit", method = RequestMethod.POST)
	@ResponseBody
	public String addProvider(@Valid ProviderBalanceAudit providerBalanceAudit, Model model) {
		providerBalanceAudit.setAddTime(new Date());
		providerBalanceAuditService.addBalanceRecord(providerBalanceAudit);
		return "success";
	}
}
