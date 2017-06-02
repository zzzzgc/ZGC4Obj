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

import xinxing.boss.admin.boss.provider.domain.BlackPhone;
import xinxing.boss.admin.boss.provider.service.BlackPhoneService;
import xinxing.boss.admin.common.persistence.Page;
import xinxing.boss.admin.common.persistence.PropertyFilter;
import xinxing.boss.admin.common.utils.filter.AjaxFilter;
import xinxing.boss.admin.common.utils.http.HttpUtils;
import xinxing.boss.admin.common.utils.parameter.ParameterListener;
import xinxing.boss.admin.common.web.BaseController;

@Controller
@RequestMapping(value="boss/blackPhone")
public class BlackPhoneCmd extends BaseController {
	private static Logger logger = LoggerFactory.getLogger(BlackPhoneCmd.class);
	
	@Autowired
	private BlackPhoneService blackPhoneService;
	
	
	/**
	 * 默认页面   
	 */
	@RequestMapping(value="list",method = RequestMethod.GET)
	public String list() {
		return "boss/blackPhone/blackPhoneList";
	}
	
	/**
	 * 获取所有订单信息
	 * @return
	 */
	@RequestMapping(value="blackPhoneList",method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getConfigList(HttpServletRequest request){
		Page<BlackPhone> page = getPage(request);		
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		page = blackPhoneService.search(page, filters);
		return getEasyUIData(page);
	}
	
	/**
	 * 添加订单信息跳转
	 * @param model
	 * @return
	 */
	@RequiresPermissions("boss:blackPhone:add")
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String create(Model model) {
		 model.addAttribute("blackPhone", new BlackPhone());
		return "boss/blackPhone/blackPhoneAdd";
	}
	
	/**
	 * 添加订单信息
	 * 
	 * @param dict
	 * @param model
	 */
	@RequiresPermissions("boss:blackPhone:add")
	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ResponseBody
	public String add(@Valid BlackPhone blackPhone, Model model,HttpServletRequest request) {
		String[] phones = request.getParameterValues("phones");
		int length = phones.length;
		System.out.println(phones.length);
		StringBuffer sql = new StringBuffer(300000);
		sql.append("INSERT black_phone(phone) value");
//		
		for (int i = 0; i < length; i++) {
			if(phones[i].length()==11){
				sql.append("(").append(phones[i]).append("),");
			}
		}
		String sql1 = sql.substring(0, sql.length()-1);
		System.out.println("sql1.length()"+sql1.length());
		blackPhoneService.save(sql1);
		new Thread(new Runnable() {
			@Override
			public void run() {
				ParameterListener.flushStatus("init_backphone");
			}
		}).start();
		return "success";
	}

	/**
	 * 修改订单信息跳转
	 */
	@RequiresPermissions("boss:blackPhone:update")
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String update(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("blackPhone", blackPhoneService.get(id));
		return "boss/blackPhone/blackPhoneUpdate";
	}
	
	/**
	 * 修改订单信息
	 * @param tmallOrder
	 * @param model
	 * @return
	 */
	@RequiresPermissions("boss:blackPhone:update")
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody BlackPhone blackPhone,Model model) {
		blackPhoneService.update(blackPhone);
		ParameterListener.flushStatus("init_backphone");
		return "success";
	}
	
	
	/**
	 * 删除订单
	 * 
	 * @param id
	 * @return
	 */
	@RequiresPermissions("boss:blackPhone:delete")
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Integer id) {
		blackPhoneService.delete(id);
		logger.info("blackPhone delete:"+id);
		new Thread(new Runnable() {
			@Override
			public void run() {
				ParameterListener.flushStatus("init_backphone");
			}
		}).start();
		return "success";
	}
	/**
	 * 导如excel
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	@RequestMapping("ImportExcel")
    public void ImportExcel(HttpServletRequest request, HttpServletResponse response) throws ParseException{  
		System.out.println("----------------");
	}
	/**
	 * 导出excel
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	@RequestMapping("exportExcel")
    public void createExcel(HttpServletRequest request, HttpServletResponse response) throws ParseException{  
    }
}
