package xinxing.boss.admin.boss.operation.cmd;

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

import xinxing.boss.admin.boss.operation.domain.OperatorConfig;
import xinxing.boss.admin.boss.operation.service.OperatorConfigService;
import xinxing.boss.admin.common.persistence.Page;
import xinxing.boss.admin.common.persistence.PropertyFilter;
import xinxing.boss.admin.common.utils.json.JsonUtils;
import xinxing.boss.admin.common.utils.parameter.ParameterListener;
import xinxing.boss.admin.common.web.BaseController;

@Controller
@RequestMapping(value="boss/operatorConfig")
public class OperatorConfigCmd extends BaseController {
	private static Logger logger = LoggerFactory.getLogger(OperatorConfigCmd.class);
	@Autowired
	private OperatorConfigService operatorConfigService;
	
	
	/**
	 * 默认页面   
	 */
	@RequestMapping(value="list",method = RequestMethod.GET)
	public String list() {
		return "boss/operatorConfig/operatorConfigList";
	}
	
	/**
	 * 获取所有订单信息
	 * @return
	 */
	@RequestMapping(value="operatorConfigList",method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getConfigList(HttpServletRequest request){
		Page<OperatorConfig> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		page = operatorConfigService.search(page, filters);
		return getEasyUIData(page);
	}
	
	/**
	 * 添加订单信息跳转
	 * @param model
	 * @return
	 */
	@RequiresPermissions("boss:operatorConfig:add")
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String create(Model model) {
		 model.addAttribute("operatorConfig", new OperatorConfig());
		return "boss/operatorConfig/operatorConfigAdd";
	}
	
	/**
	 * 添加订单信息
	 * 
	 * @param dict
	 * @param model
	 */
	@RequiresPermissions("boss:operatorConfig:add")
	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ResponseBody
	public String add(@Valid OperatorConfig operatorConfig, Model model) {
		operatorConfigService.save(operatorConfig);
		logger.info("operatorConfig add:"+operatorConfig.getId());
		ParameterListener.flushStatus("init_operator_gate");
		return "success";
	}

	/**
	 * 修改订单信息跳转
	 */
	@RequiresPermissions("boss:operatorConfig:update")
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String update(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("operatorConfig", operatorConfigService.get(id));
		return "boss/operatorConfig/operatorConfigUpdate";
	}
	
	/**
	 * 修改订单信息
	 * @param tmallOrder
	 * @param model
	 * @return
	 */
	@RequiresPermissions("boss:operatorConfig:update")
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody OperatorConfig operatorConfig,Model model) {
		operatorConfigService.update(operatorConfig);
		ParameterListener.flushStatus("init_operator_gate");
		return "success";
	}
	
	
	/**
	 * 删除订单
	 * 
	 * @param id
	 * @return
	 */
	@RequiresPermissions("boss:operatorConfig:delete")
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Integer id) {
		operatorConfigService.delete(id);
		logger.info("operatorConfig delete:"+id);
		ParameterListener.flushStatus("init_operator_gate");
		return "success";
	}
	
	/**
	 * 批量开通/冻结
	 * @param operatorConfig
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "updateStatus", method = RequestMethod.POST)
	@ResponseBody
	public String updateStatus(HttpServletRequest request){
		String[] ids = request.getParameterValues("ids");
		String state=request.getParameter("state");
		if(ids==null || StringUtils.isBlank(state)){
			return "error";
		}
		int stateInt = Integer.parseInt(state);
		List<OperatorConfig> list=new ArrayList<OperatorConfig>();
		for (String id : ids) {
			OperatorConfig operatorConfig = operatorConfigService.get(Integer.parseInt(id));
			operatorConfig.setType(stateInt);
			list.add(operatorConfig);
		}
		for (OperatorConfig operatorConfig : list) {
			operatorConfigService.save(operatorConfig);
		}
		ParameterListener.flushStatus("init_operator_gate");
		return "success";
	}
	/**
	 * 批量开通/冻结
	 * @param operatorConfig
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "updateStateByProvince", method = RequestMethod.POST)
	@ResponseBody
	public boolean updateStateByProvince(HttpServletRequest request){
		String province = request.getParameter("province");
		String operator = request.getParameter("operator");
		String state=request.getParameter("state");
		if(province==null || StringUtils.isBlank(state)){
			return false;
		}
		int stateInt = Integer.parseInt(state);
		operatorConfigService.updateStateByProvince(province,stateInt,operator);
		ParameterListener.flushStatus("init_operator_gate");
		return true;
	}
	@RequestMapping(value = "initTable")
	@ResponseBody
	public String initTable(HttpServletRequest req,HttpServletResponse resp) {
		logger.info("initTable");
		List<OperatorConfig> all = operatorConfigService.getAll();
		if(all.size()==0){
			operatorConfigService.initTable();
			logger.info("初始化表operatorConfig!");
			return "success";
		}
		else return "表已存在内容，请先清空表";
	}
}
