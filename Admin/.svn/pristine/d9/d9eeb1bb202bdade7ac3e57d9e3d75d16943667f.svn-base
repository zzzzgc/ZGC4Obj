package xinxing.boss.admin.boss.other.cmd;

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

import xinxing.boss.admin.boss.operation.domain.OperatorConfig;
import xinxing.boss.admin.boss.other.domain.FailReason;
import xinxing.boss.admin.boss.other.service.FailReasonService;
import xinxing.boss.admin.common.persistence.Page;
import xinxing.boss.admin.common.persistence.PropertyFilter;
import xinxing.boss.admin.common.utils.parameter.ParameterListener;
import xinxing.boss.admin.common.web.BaseController;

@Controller
@RequestMapping(value="boss/failReason")
public class FailReasonCmd extends BaseController {
	private static Logger logger = LoggerFactory.getLogger(FailReasonCmd.class);
	@Autowired
	private FailReasonService failReasonService;
	
	
	/**
	 * 默认页面   
	 */
	@RequestMapping(value="list",method = RequestMethod.GET)
	public String list() {
		return "boss/failReason/failReasonList";
	}
	/**
	 * 获取所有订单信息
	 * @return
	 */
	@RequestMapping(value="failReasonList",method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getConfigList(HttpServletRequest request){
		Page<FailReason> page = getPage(request);		
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		page = failReasonService.search(page, filters);
		return getEasyUIData(page);
	}
	/**
	 * 添加订单信息跳转
	 * @param model
	 * @return
	 */
	@RequiresPermissions("boss:failReason:add")
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String create(Model model) {
		 model.addAttribute("failReason", new FailReason());
		return "boss/failReason/failReasonAdd";
	}
	
	/**
	 * 添加订单信息
	 * 
	 * @param dict
	 * @param model
	 */
	@RequiresPermissions("boss:failReason:add")
	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ResponseBody
	public String add(@Valid FailReason failReason, Model model) {
		Boolean isReapeat = failReasonService.isReapeat(failReason);
		if(isReapeat){
			return "false";
		}
		failReasonService.save(failReason);
		ParameterListener.flushStatus("init_fail_reason");
		logger.info("failReason add:"+failReason.getId());
		return "success";
	}

	/**
	 * 修改订单信息跳转
	 */
	@RequiresPermissions("boss:failReason:update")
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String update(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("failReason", failReasonService.get(id));
		return "boss/failReason/failReasonUpdate";
	}
	
	/**
	 * 修改订单信息
	 * @param tmallOrder
	 * @param model
	 * @return
	 */
	@RequiresPermissions("boss:failReason:update")
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody FailReason failReason,Model model) {
		Boolean isReapeat = failReasonService.isReapeat(failReason);
		if(isReapeat){
			return "false";
		}
		failReasonService.update(failReason);
		ParameterListener.flushStatus("init_fail_reason");
		return "success";
	}
	
	
	/**
	 * 删除订单
	 * 
	 * @param id
	 * @return
	 */
	@RequiresPermissions("boss:failReason:delete")
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Integer id) {
		failReasonService.delete(id);
		logger.info("failReason delete:"+id);
		ParameterListener.flushStatus("init_fail_reason");
		return "success";
	}
	
}
