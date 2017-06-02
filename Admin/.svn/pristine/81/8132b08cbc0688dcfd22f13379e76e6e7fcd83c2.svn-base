package xinxing.boss.admin.boss.provider.cmd;

import java.text.ParseException;
import java.util.ArrayList;
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

import xinxing.boss.admin.boss.order.domain.OrderInfo;
import xinxing.boss.admin.boss.provider.domain.ProviderInfo;
import xinxing.boss.admin.boss.provider.domain.ProviderMoneyRecord;
import xinxing.boss.admin.boss.provider.service.ProviderInfoService;
import xinxing.boss.admin.boss.provider.service.ProviderMoneyRecordService;
import xinxing.boss.admin.common.excel.ExcelUtils;
import xinxing.boss.admin.common.persistence.Page;
import xinxing.boss.admin.common.persistence.PropertyFilter;
import xinxing.boss.admin.common.utils.base.BaseUtil;
import xinxing.boss.admin.common.web.BaseController;

@Controller
@RequestMapping(value = "boss/providerMoneyRecord")
public class ProviderMoneyRecordCmd extends BaseController {
	private static Logger logger = LoggerFactory.getLogger(ProviderMoneyRecordCmd.class);

	@Autowired
	private ProviderMoneyRecordService providerMoneyRecordService;
	@Autowired
	private ProviderInfoService providerInfoService;

	/**
	 * 默认页面
	 */
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list() {
		return "boss/providerMoneyRecord/providerMoneyRecordList";
	}

	/**
	 * 获取所有订单信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "providerMoneyRecordList", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getConfigList(HttpServletRequest request) {
		Page<ProviderMoneyRecord> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		page = providerMoneyRecordService.search(page, filters);
		return getEasyUIData(page);
	}

	/**
	 * 添加订单信息跳转
	 * 
	 * @param model
	 * @return
	 */
	@RequiresPermissions("boss:providerMoneyRecord:add")
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String create(Model model) {
		model.addAttribute("providerMoneyRecord", new ProviderMoneyRecord());
		return "boss/providerMoneyRecord/providerMoneyRecordAdd";
	}

	/**
	 * 添加订单信息
	 * 
	 * @param dict
	 * @param model
	 */
	@RequiresPermissions("boss:providerMoneyRecord:add")
	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ResponseBody
	public String add(@Valid ProviderMoneyRecord providerMoneyRecord, Model model) {
		providerMoneyRecordService.save(providerMoneyRecord);
		logger.info("providerMoneyRecord add:" + providerMoneyRecord.getId());
		return "success";
	}

	/**
	 * 修改订单信息跳转
	 */
	@RequiresPermissions("boss:providerMoneyRecord:update")
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String update(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("providerMoneyRecord", providerMoneyRecordService.get(id));
		return "boss/providerMoneyRecord/providerMoneyRecordUpdate";
	}

	/**
	 * 修改订单信息
	 * 
	 * @param tmallOrder
	 * @param model
	 * @return
	 */
	@RequiresPermissions("boss:providerMoneyRecord:update")
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody ProviderMoneyRecord providerMoneyRecord, Model model) {
		providerMoneyRecordService.update(providerMoneyRecord);
		return "success";
	}

	/**
	 * 删除订单
	 * 
	 * @param id
	 * @return
	 */
	@RequiresPermissions("boss:providerMoneyRecord:delete")
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Integer id) {
		providerMoneyRecordService.delete(id);
		logger.info("providerMoneyRecord delete:" + id);
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
		List<Map<String, Object>> mapList = new ArrayList<>();

		Page<ProviderMoneyRecord> page = getPage(request);
		page.setPageNo(1);
		page.setPageSize(1000000);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
//		BaseUtil.testSearch(filters, "receiveTime");
		page = providerMoneyRecordService.search(page, filters);
		List<ProviderMoneyRecord> list = page.getResult();// 获取数据

		// List<ProviderMoneyRecord> list = providerMoneyRecordService.getAll();//获取数据
		List<ProviderInfo> providerInfos = providerInfoService.getAll();
		Map<Integer, ProviderInfo> providerInfoMap = new HashMap<>();
		for (ProviderInfo providerInfo : providerInfos) {
			providerInfoMap.put(providerInfo.getId(), providerInfo);
		}
		for (ProviderMoneyRecord providerMoneyRecord : list) {
			if (providerInfoMap.containsKey(providerMoneyRecord.getProviderId())) {
				ProviderInfo providerInfo = providerInfoMap.get(providerMoneyRecord.getProviderId());
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("providerName", providerInfo.getProviderName());
				map.put("beforeMoney", providerMoneyRecord.getFundBalance().subtract(providerMoneyRecord.getCost()));
				map.put("cost", providerMoneyRecord.getCost());
				map.put("fundBalance", providerMoneyRecord.getFundBalance());
				map.put("costtype", providerMoneyRecord.getCostType());
				ExcelUtils.showValue(map, "costtype", "", "人工注资", "系统注资", "消费", "失败返还", "人工扣款");
				map.put("remark", providerMoneyRecord.getRemark());
				map.put("recordTime", providerMoneyRecord.getRecordTime());
				mapList.add(map);
			}
		}
		System.out.println("mapList.size():" + mapList.size());
		String title = "采购商注资";
		String[] hearders = new String[] { "采购商名称", "发生前资金余额", "发生金额", "资金余额", "收支类型", "备注", "时间" };// 表头数组
		String[] fields = new String[] { "providerName", "beforeMoney", "cost", "fundBalance", "costtype", "remark", "recordTime" };// providerMoneyRecord对象属性数组
		ExcelUtils.productExcel(response, mapList, title, hearders, fields, logger);
	}
}
