package xinxing.boss.admin.boss.provider.cmd;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
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

import xinxing.boss.admin.boss.other.domain.BossProviderSame;
import xinxing.boss.admin.boss.other.service.BossProviderSameService;
import xinxing.boss.admin.boss.provider.domain.ProviderInfo;
import xinxing.boss.admin.boss.provider.service.ProviderInfoService;
import xinxing.boss.admin.common.excel.ExcelUtils;
import xinxing.boss.admin.common.persistence.Page;
import xinxing.boss.admin.common.persistence.PropertyFilter;
import xinxing.boss.admin.common.utils.json.JsonUtils;
import xinxing.boss.admin.common.utils.parameter.ParameterListener;
import xinxing.boss.admin.common.web.BaseController;
import xinxing.boss.admin.system.user.utils.UserUtil;

@Controller
@RequestMapping(value = "boss/providerInfo")
public class ProviderInfoCmd extends BaseController {
	private static Logger log = LoggerFactory.getLogger(ProviderInfoCmd.class);

	@Autowired
	private ProviderInfoService providerInfoService;
	@Autowired
	private BossProviderSameService bossProviderSameService;

	/**
	 * 默认页面
	 */
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list(Integer id, HttpServletRequest request) {
		if (id != null) {
			request.setAttribute("flag", id);
		}

		return "boss/providerInfo/providerInfoList";
	}

	/**
	 * 获取所有订单信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "providerInfoList")
	@ResponseBody
	public Map<String, Object> getConfigList(HttpServletRequest request) {
		Page<ProviderInfo> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		page = providerInfoService.search(page, filters);
		return getEasyUIData(page);
	}

	/**
	 * 
	 * 添加供货商信息跳转
	 * 
	 * @param model
	 * @return
	 */
	@RequiresPermissions("boss:providerInfo:add")
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String create(Model model) {
		model.addAttribute("providerInfo", new ProviderInfo());
		return "boss/providerInfo/providerInfoAdd";
	}

	/**
	 * 添加供应商信息
	 * 
	 * @param dict
	 * @param model
	 */
	@RequiresPermissions("boss:providerInfo:add")
	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ResponseBody
	public String add(@Valid ProviderInfo providerInfo, Model model) {
		log.info("providerInfo add:" + ",--内容:"+JsonUtils.obj2Json(providerInfo));
		String supplier = providerInfo.getSupplier();
		
		providerInfo.setBalance(new BigDecimal(0));
		providerInfoService.save(providerInfo);
		log.info("providerInfo add:" + providerInfo.getId());
		ParameterListener.refresh("provider");
		ParameterListener.flushStatus("init_provider");

		if (supplier != null && supplier.contains("_")) {
			String[] split = supplier.split("_");
			if (split.length > 1) {
				supplier = "";
				for (int i = 0; i < split.length - 1; i++) {
					supplier += "_" + split[i];
				}
				if (!split[split.length - 1].equalsIgnoreCase("YD") && !split[split.length - 1].equalsIgnoreCase("LT")
						&& !split[split.length - 1].equalsIgnoreCase("DX")) {
					supplier += "_" + split[split.length - 1];
				}
				supplier = supplier.substring(1);
			}
		}
		List<BossProviderSame> search = bossProviderSameService.search("from BossProviderSame where supplier = ?0", supplier);
		if (search.size() == 0) {
			BossProviderSame bps = new BossProviderSame(providerInfo.getId() + "", new BigDecimal("0"), supplier);
			bossProviderSameService.save(bps);
		} else {
			BossProviderSame bps = search.get(0);
			bps.setProviderIds(bps.getProviderIds() + "," + providerInfo.getId());
			bossProviderSameService.update(bps);
		}
		ParameterListener.refresh("bossProviderSame");
		ParameterListener.flushStatus("bossProviderSame");
		return "success";
	}

	/**
	 * 修改订单信息跳转
	 */
	@RequiresPermissions("boss:providerInfo:update")
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String update(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("providerInfo", providerInfoService.get(id));
		return "boss/providerInfo/providerInfoUpdate";
	}

	/**
	 * 修改供货商信息
	 * 
	 * @param tmallOrder
	 * @param model
	 * @return
	 */
	@RequiresPermissions("boss:providerInfo:update")
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody ProviderInfo providerInfo) {
		providerInfoService.update(providerInfo);
		ParameterListener.refresh("provider");
		ParameterListener.flushStatus("init_provider");
		return "success";
	}

	/**
	 * 批量修改供货商状态 开通/冻结
	 * 
	 * @param tmallOrder
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
		List<ProviderInfo> list = new ArrayList<ProviderInfo>();
		log.info(UserUtil.getCurrentUser().getLoginName() + ",updateStatus更新状态:ids" + ids + ",status:" + state);
		for (String id : ids) {
			ProviderInfo providerInfo = providerInfoService.get(Integer.parseInt(id));
			log.info(UserUtil.getCurrentUser().getId() + "-" + UserUtil.getCurrentUser().getName() + "修改前:" + providerInfo.getStatus() + ",修改后:"
					+ state + "---" + JsonUtils.obj2Json(providerInfo));
			providerInfo.setStatus(Integer.parseInt(state));
			list.add(providerInfo);
		}

		for (ProviderInfo providerInfo : list) {
			update(providerInfo);
		}
		ParameterListener.flushStatus("init_provider");
		return "success";
	}

	/**
	 * 删除供应商
	 * 
	 * @param id
	 * @return
	 */
	@RequiresPermissions("boss:providerInfo:delete")
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Integer id) {
		providerInfoService.delete(id);
		log.info("providerInfo delete:" + id);
		ParameterListener.refresh("provider");
		ParameterListener.flushStatus("init_provider");
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
		List<ProviderInfo> list = providerInfoService.getAll();// 获取数据
		List<Map<String, Object>> mapList = new ArrayList<>();
		for (ProviderInfo providerInfo : list) {
			Map<String, Object> beanToMap = ExcelUtils.beanToMap(providerInfo);
			ExcelUtils.showValue(beanToMap, "status", "冻结", "正常");
			ExcelUtils.showValue(beanToMap, "isCallback", "会", "不会");
			mapList.add(beanToMap);
		}
		String title = "供应商信息";
		String[] hearders = new String[] { "编号", "供应商名称", "供应商联系号码", "供应商联系人", "供应商联系地址", "成本折扣系数", "供应商所在省份", "添加时间", "备注", "是否回调", "状态", "剩余可用余额",
				"供应商简称", "禁止采购商", "允许采购商" };// 表头数组
		String[] fields = new String[] { "id", "providerName", "providerNumber", "providerContact", "providerAddress", "discount", "province",
				"addTime", "remark", "isCallback", "status", "balance", "supplier", "forbinCustomer", "allowCustomer" };// ProviderInfo对象属性数组
		ExcelUtils.productExcel(response, mapList, title, hearders, fields, log);
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
		return providerInfoService.checkSimpleName(simpleName);
	}
	
}
