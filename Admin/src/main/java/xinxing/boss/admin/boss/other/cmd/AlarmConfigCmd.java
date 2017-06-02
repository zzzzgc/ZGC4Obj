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

import xinxing.boss.admin.boss.other.domain.AlarmConfig;
import xinxing.boss.admin.boss.other.service.AlarmConfigService;
import xinxing.boss.admin.common.persistence.Page;
import xinxing.boss.admin.common.persistence.PropertyFilter;
import xinxing.boss.admin.common.web.BaseController;

@Controller
@RequestMapping(value = "boss/alarmConfig")
public class AlarmConfigCmd extends BaseController {
	private static Logger logger = LoggerFactory.getLogger(AlarmConfigCmd.class);
	@Autowired
	private AlarmConfigService alarmConfigService;

	/**
	 * 默认页面
	 */
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list() {
		return "boss/alarmConfig/alarmConfigList";
	}

	/**
	 * 获取所有订单信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "alarmConfigList", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getConfigList(HttpServletRequest request) {
		Page<AlarmConfig> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		page = alarmConfigService.search(page, filters);
		return getEasyUIData(page);
	}

	/**
	 * 添加订单信息跳转
	 * 
	 * @param model
	 * @return
	 */
	@RequiresPermissions("boss:alarmConfig:add")
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String create(Model model) {
		model.addAttribute("alarmConfig", new AlarmConfig());
		return "boss/alarmConfig/alarmConfigAdd";
	}

	/**
	 * 添加订单信息
	 * 
	 * @param dict
	 * @param model
	 */
	@RequiresPermissions("boss:alarmConfig:add")
	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ResponseBody
	public String add(@Valid AlarmConfig alarmConfig, Model model) {
		alarmConfigService.save(alarmConfig);
		//		ParameterListener.refresh("alarmConfig");
		//		ParameterListener.flushStatus("init_boss_config");
		logger.info("alarmConfig add:" + alarmConfig.getId());
		return "success";
	}

	/**
	 * 修改订单信息跳转
	 */
	@RequiresPermissions("boss:alarmConfig:update")
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String update(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("alarmConfig", alarmConfigService.get(id));
		return "boss/alarmConfig/alarmConfigUpdate";
	}

	/**
	 * 修改订单信息
	 * 
	 * @param tmallOrder
	 * @param model
	 * @return
	 */
	@RequiresPermissions("boss:alarmConfig:update")
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody AlarmConfig alarmConfig, Model model) {
		alarmConfigService.update(alarmConfig);
		//		ParameterListener.refresh("alarmConfig");
		//		ParameterListener.flushStatus("init_boss_config");
		return "success";
	}

	/**
	 * 删除订单
	 * 
	 * @param id
	 * @return
	 */
	@RequiresPermissions("boss:alarmConfig:delete")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(Integer[] ids) {
		if (ids != null && ids.length > 0) {
			for (Integer id : ids) {
				alarmConfigService.delete(id);
				logger.info("alarmConfig delete:" + id);
			}
			//		ParameterListener.refresh("alarmConfig");
			//		ParameterListener.flushStatus("init_boss_config");
		}
		return "success";
	}

}
