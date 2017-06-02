package xinxing.boss.admin.boss.customer.cmd;

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

import xinxing.boss.admin.boss.customer.domain.CustomerCallbackRecord;
import xinxing.boss.admin.boss.customer.service.CustomerCallbackRecordService;
import xinxing.boss.admin.common.persistence.Page;
import xinxing.boss.admin.common.persistence.PropertyFilter;
import xinxing.boss.admin.common.web.BaseController;

@Controller
@RequestMapping(value="boss/customerCallbackRecord")
public class CustomerCallbackRecordCmd extends BaseController {
	private static Logger logger = LoggerFactory.getLogger(CustomerCallbackRecordCmd.class);
	
	@Autowired
	private CustomerCallbackRecordService customerCallbackRecordService;
	
	
	/**
	 * 默认页面   
	 */
	@RequestMapping(value="list",method = RequestMethod.GET)
	public String list() {
		return "boss/customerCallbackRecord/customerCallbackRecordList";
	}
	
	/**
	 * 获取所有订单信息
	 * @return
	 */
	@RequestMapping(value="customerCallbackRecordList",method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getConfigList(HttpServletRequest request){
		Page<CustomerCallbackRecord> page = getPage(request);		
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		page = customerCallbackRecordService.search(page, filters);
		return getEasyUIData(page);
	}
	
	/**
	 * 添加订单信息跳转
	 * @param model
	 * @return
	 */
	@RequiresPermissions("boss:customerCallbackRecord:add")
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String create(Model model) {
		 model.addAttribute("customerCallbackRecord", new CustomerCallbackRecord());
		return "boss/customerCallbackRecord/customerCallbackRecordAdd";
	}
	
	/**
	 * 添加订单信息
	 * 
	 * @param dict
	 * @param model
	 */
	@RequiresPermissions("boss:customerCallbackRecord:add")
	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ResponseBody
	public String add(@Valid CustomerCallbackRecord customerCallbackRecord, Model model) {
		customerCallbackRecordService.save(customerCallbackRecord);
		logger.info("customerCallbackRecord add:"+customerCallbackRecord.getId());
		return "success";
	}

	/**
	 * 修改订单信息跳转
	 */
	@RequiresPermissions("boss:customerCallbackRecord:update")
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String update(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("customerCallbackRecord", customerCallbackRecordService.get(id));
		return "boss/customerCallbackRecord/customerCallbackRecordUpdate";
	}
	
	/**
	 * 修改订单信息
	 * @param tmallOrder
	 * @param model
	 * @return
	 */
	@RequiresPermissions("boss:customerCallbackRecord:update")
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody CustomerCallbackRecord customerCallbackRecord,Model model) {
		customerCallbackRecordService.update(customerCallbackRecord);
		return "success";
	}
	
	
	/**
	 * 删除订单
	 * 
	 * @param id
	 * @return
	 */
	@RequiresPermissions("boss:customerCallbackRecord:delete")
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Integer id) {
		customerCallbackRecordService.delete(id);
		logger.info("customerCallbackRecord delete:"+id);
		return "success";
	}
}
