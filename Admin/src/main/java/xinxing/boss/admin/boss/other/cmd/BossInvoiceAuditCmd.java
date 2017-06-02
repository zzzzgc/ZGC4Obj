package xinxing.boss.admin.boss.other.cmd;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
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

import xinxing.boss.admin.boss.order.domain.OrderInfo;
import xinxing.boss.admin.boss.order.service.OrderInfoService;
import xinxing.boss.admin.boss.other.domain.BossInvoiceAudit;
import xinxing.boss.admin.boss.other.service.BossInvoiceAuditService;
import xinxing.boss.admin.common.excel.ExcelUtils;
import xinxing.boss.admin.common.persistence.Page;
import xinxing.boss.admin.common.persistence.PropertyFilter;
import xinxing.boss.admin.common.utils.parameter.ParameterListener;
import xinxing.boss.admin.common.web.BaseController;
import xinxing.boss.admin.system.user.utils.UserUtil;

@Controller
@RequestMapping(value = "boss/bossInvoiceAudit")
public class BossInvoiceAuditCmd extends BaseController {
	private static Logger logger = LoggerFactory.getLogger(BossInvoiceAuditCmd.class);
	@Autowired
	private BossInvoiceAuditService bossInvoiceAuditService;
	@Autowired
	private OrderInfoService orderInfoService;

	/**
	 * 额外的方法
	 * 
	 * @param tmallOrder
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "updateStatus", method = RequestMethod.POST)
	@ResponseBody
	public String updateStatus(HttpServletRequest request, Integer[] ids, Integer status) {
		if (ids == null) {
			return "没有id";
		}
		if (status != null && status >= 0 && status <= 2) {
			for (Integer id : ids) {
				BossInvoiceAudit bossInvoiceAudit = bossInvoiceAuditService.get(id);
				OrderInfo orderInfo = orderInfoService.get(bossInvoiceAudit.getOrderId());
				if (status == 1) {// 审核通过
					orderInfo.setStatus(OrderInfo.Status.SUCCESS.status);
				} else if (status == 2) {// 审核不通过
					orderInfo.setStatus(OrderInfo.Status.FAIL.status);
				}
				orderInfo.setCallbackTime(new Date());
				orderInfoService.update(orderInfo);
				bossInvoiceAudit.setAuditStatus(status);
				update(bossInvoiceAudit);
			}
		}
		return "success";
	}

	/**
	 * 默认页面
	 */
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list() {
		return "boss/bossInvoiceAudit/bossInvoiceAuditList";
	}

	/**
	 * 获取所有订单信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "bossInvoiceAuditList", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getConfigList(HttpServletRequest request) {
		Page<BossInvoiceAudit> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		page = bossInvoiceAuditService.search(page, filters);
		List<BossInvoiceAudit> result = page.getResult();
		List rows = new ArrayList<>();
		for (BossInvoiceAudit bossInvoiceAudit : result) {
			Integer orderId = bossInvoiceAudit.getOrderId();
			List<OrderInfo> search = orderInfoService.search("from OrderInfo where id = ?0", orderId);
			Map<String, Object> beanToMap = ExcelUtils.beanToMap(bossInvoiceAudit);
			Map<String, Object> beanToMap2 = ExcelUtils.beanToMap(search.get(0));
			beanToMap2.putAll(beanToMap);
			rows.add(beanToMap2);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", rows);
		map.put("total", page.getTotalCount());
		return map;
	}

	/**
	 * 添加订单信息跳转
	 * 
	 * @param model
	 * @return
	 */
	@RequiresPermissions("boss:bossInvoiceAudit:add")
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String create(Model model) {
		model.addAttribute("bossInvoiceAudit", new BossInvoiceAudit());
		return "boss/bossInvoiceAudit/bossInvoiceAuditAdd";
	}

	/**
	 * 添加订单信息
	 * 
	 * @param dict
	 * @param model
	 */
	@RequiresPermissions("boss:bossInvoiceAudit:add")
	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ResponseBody
	public String add(@Valid BossInvoiceAudit bossInvoiceAudit, Model model) {
		Date date = new Date();
		Integer userId = UserUtil.getCurrentUser() != null ? UserUtil.getCurrentUser().getId() : null;
		bossInvoiceAudit.setAddTime(date);
		bossInvoiceAudit.setAddUser(userId);
		bossInvoiceAudit.setUpdateTime(date);
		bossInvoiceAudit.setUpdateUser(userId);
		bossInvoiceAuditService.save(bossInvoiceAudit);
		ParameterListener.refresh("bossInvoiceAudit");
		// ParameterListener.flushStatus("bossInvoiceAudit");
		logger.info("bossInvoiceAudit add:" + bossInvoiceAudit.getId());
		return "success";
	}

	/**
	 * 修改订单信息跳转
	 */
	@RequiresPermissions("boss:bossInvoiceAudit:update")
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String update(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("bossInvoiceAudit", bossInvoiceAuditService.get(id));
		return "boss/bossInvoiceAudit/bossInvoiceAuditUpdate";
	}

	/**
	 * 修改订单信息
	 * 
	 * @param tmallOrder
	 * @param model
	 * @return
	 */
	@RequiresPermissions("boss:bossInvoiceAudit:update")
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody BossInvoiceAudit bossInvoiceAudit) {
		Date date = new Date();
		Integer userId = UserUtil.getCurrentUser() != null ? UserUtil.getCurrentUser().getId() : null;
		bossInvoiceAudit.setUpdateTime(date);
		bossInvoiceAudit.setUpdateUser(userId);
		bossInvoiceAudit.setAuditTime(new Date());
		bossInvoiceAudit.setAuditUser(userId);
		bossInvoiceAuditService.update(bossInvoiceAudit);
		ParameterListener.refresh("bossInvoiceAudit");
		// ParameterListener.flushStatus("bossInvoiceAudit");
		return "success";
	}

	/**
	 * 删除订单
	 * 
	 * @param id
	 * @return
	 */
	@RequiresPermissions("boss:bossInvoiceAudit:delete")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(Integer[] ids) {
		if (ids != null && ids.length > 0) {
			for (Integer id : ids) {
				bossInvoiceAuditService.delete(id);
				logger.info("bossInvoiceAudit delete:" + id);
			}
			ParameterListener.refresh("bossInvoiceAudit");
			// ParameterListener.flushStatus("bossInvoiceAudit");
		}
		return "success";
	}
	
	

}
