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

import xinxing.boss.admin.boss.other.domain.BossConfig;
import xinxing.boss.admin.boss.other.service.BossConfigService;
import xinxing.boss.admin.common.persistence.Page;
import xinxing.boss.admin.common.persistence.PropertyFilter;
import xinxing.boss.admin.common.utils.parameter.ParameterListener;
import xinxing.boss.admin.common.web.BaseController;

@Controller
@RequestMapping(value="boss/bossConfig")
public class BossConfigCmd extends BaseController {
	private static Logger logger = LoggerFactory.getLogger(BossConfigCmd.class);
	@Autowired
	private BossConfigService bossConfigService;
	
	
	/**
	 * 默认页面   
	 */
	@RequestMapping(value="list",method = RequestMethod.GET)
	public String list() {
		return "boss/bossConfig/bossConfigList";
	}
	/**
	 * 获取所有订单信息
	 * @return
	 */
	@RequestMapping(value="bossConfigList",method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getConfigList(HttpServletRequest request){
		Page<BossConfig> page = getPage(request);		
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		page = bossConfigService.search(page, filters);
		return getEasyUIData(page);
	}
	/**
	 * 添加订单信息跳转
	 * @param model
	 * @return
	 */
	@RequiresPermissions("boss:bossConfig:add")
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String create(Model model) {
		model.addAttribute("bossConfig", new BossConfig());
		return "boss/bossConfig/bossConfigAdd";
	}
	
	/**
	 * 添加订单信息
	 * 
	 * @param dict
	 * @param model
	 */
	@RequiresPermissions("boss:bossConfig:add")
	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ResponseBody
	public String add(@Valid BossConfig bossConfig, Model model) {
		bossConfigService.save(bossConfig);
		ParameterListener.refresh("bossConfig");
		ParameterListener.flushStatus("init_boss_config");
		logger.info("bossConfig add:"+bossConfig.getId());
		return "success";
	}

	/**
	 * 修改订单信息跳转
	 */
	@RequiresPermissions("boss:bossConfig:update")
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String update(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("bossConfig", bossConfigService.get(id));
		return "boss/bossConfig/bossConfigUpdate";
	}
	
	/**
	 * 修改订单信息
	 * @param tmallOrder
	 * @param model
	 * @return
	 */
	@RequiresPermissions("boss:bossConfig:update")
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody BossConfig bossConfig,Model model) {
		bossConfigService.update(bossConfig);
		ParameterListener.refresh("bossConfig");
		ParameterListener.flushStatus("init_boss_config");
		return "success";
	}
	
	
	/**
	 * 删除订单
	 * 
	 * @param id
	 * @return
	 */
	@RequiresPermissions("boss:bossConfig:delete")
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Integer id) {
		System.out.println("------------------------华丽的分割线------------------------");
		bossConfigService.delete(id);
		ParameterListener.refresh("bossConfig");
		ParameterListener.flushStatus("init_boss_config");
		logger.info("bossConfig delete:"+id);
		return "success";
	}
	
}
