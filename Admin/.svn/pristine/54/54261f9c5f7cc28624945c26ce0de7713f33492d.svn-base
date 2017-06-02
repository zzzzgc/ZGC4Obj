package xinxing.boss.admin.boss.other.cmd;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
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

import xinxing.boss.admin.boss.other.domain.AlarmLinkman;
import xinxing.boss.admin.boss.other.service.AlarmLinkmanService;
import xinxing.boss.admin.common.persistence.Page;
import xinxing.boss.admin.common.persistence.PropertyFilter;
import xinxing.boss.admin.common.utils.parameter.ParameterListener;
import xinxing.boss.admin.common.web.BaseController;
@Controller
@RequestMapping(value = "boss/alarmLinkman")
public class AlarmLinkmanCmd extends BaseController {
	private static Logger logger = LoggerFactory.getLogger(AlarmLinkmanCmd.class);
	@Autowired
	private AlarmLinkmanService alarmLinkmanService;

	/**
	 * 默认页面
	 */
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list() {
		return "boss/alarmLinkman/alarmLinkmanList";
	}

	/**
	 * 获取所有订单信息
	 *
	 * @return
	 */
	@RequestMapping(value = "alarmLinkmanList", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getConfigList(HttpServletRequest request) {
		Page<AlarmLinkman> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		page = alarmLinkmanService.search(page, filters);
		return getEasyUIData(page);
	}

	/**
	 * 添加订单信息跳转
	 * 
	 * @param model
	 * @return
	 */
	@RequiresPermissions("boss:alarmLinkman:add")
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String create(Model model) {
		model.addAttribute("alarmLinkman", new AlarmLinkman());
		return "boss/alarmLinkman/alarmLinkmanAdd";
	}

	/**
	 * 添加订单信息
	 * 
	 * @param dict
	 * @param model
	 */
	@RequiresPermissions("boss:alarmLinkman:add")
	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ResponseBody
	public String add(@Valid AlarmLinkman alarmLinkman, Model model) {
		alarmLinkmanService.save(alarmLinkman);
		//		ParameterListener.refresh("alarmLinkman");
		//		ParameterListener.flushStatus("init_boss_config");
		logger.info("alarmLinkman add:" + alarmLinkman.getId());
		return "success";
	}

	/**
	 * 修改订单信息跳转
	 */
	@RequiresPermissions("boss:alarmLinkman:update")
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String update(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("alarmLinkman", alarmLinkmanService.get(id));
		return "boss/alarmLinkman/alarmLinkmanUpdate";
	}

	/**
	 * 修改订单信息
	 * 
	 * @param tmallOrder
	 * @param model
	 * @return
	 */
	@RequiresPermissions("boss:alarmLinkman:update")
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody AlarmLinkman alarmLinkman) {
		alarmLinkmanService.update(alarmLinkman);
		//		ParameterListener.refresh("alarmLinkman");
		//		ParameterListener.flushStatus("init_boss_config");
		return "success";
	}

	/**
	 * 删除订单
	 * 
	 * @param id
	 * @return
	 */
	@RequiresPermissions("boss:alarmLinkman:delete")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(Integer[] ids) {
		if (ids != null && ids.length > 0) {
			for (Integer id : ids) {
				alarmLinkmanService.delete(id);
				logger.info("alarmLinkman delete:" + id);
			}
			//		ParameterListener.refresh("alarmLinkman");
			//		ParameterListener.flushStatus("init_boss_config");
		}
		return "success";
	}
	
	
	/**
	 * 批量更新/冻结
	 * 
	 * @param tmallOrder
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
		for (String id : ids) {
			 AlarmLinkman alarmLinkman = alarmLinkmanService.get(Integer.parseInt(id));
			 alarmLinkman.setStatus(Integer.parseInt(state));
			 update(alarmLinkman);
		}
		ParameterListener.flushStatus("init_provider");
		return "success";
	}

}
