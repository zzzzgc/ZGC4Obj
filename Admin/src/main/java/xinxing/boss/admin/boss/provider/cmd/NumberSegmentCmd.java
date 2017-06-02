package xinxing.boss.admin.boss.provider.cmd;

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

import xinxing.boss.admin.boss.provider.domain.NumberSegment;
import xinxing.boss.admin.boss.provider.service.NumberSegmentService;
import xinxing.boss.admin.common.persistence.Page;
import xinxing.boss.admin.common.persistence.PropertyFilter;
import xinxing.boss.admin.common.utils.parameter.ParameterListener;
import xinxing.boss.admin.common.web.BaseController;

@Controller
@RequestMapping(value="boss/numberSegment")
public class NumberSegmentCmd extends BaseController{
	private static Logger logger = LoggerFactory.getLogger(NumberSegmentCmd.class);
	@Autowired
	private NumberSegmentService numberSegmentService;
	
	
	/**
	 * 默认页面   
	 */
	@RequestMapping(value="list",method = RequestMethod.GET)
	public String list() {
		return "boss/numberSegment/numberSegmentList";
	}
	
	/**
	 * 获取所有订单信息
	 * @return
	 */
	@RequestMapping(value="numberSegmentList",method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getConfigList(HttpServletRequest request){
		Page<NumberSegment> page = getPage(request);		
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		page = numberSegmentService.search(page, filters);
		return getEasyUIData(page);
	}
	
	
	/**
	 * 添加订单信息跳转
	 * @param model
	 * @return
	 */
	@RequiresPermissions("boss:numberSegment:add")
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String create(Model model) {
		 model.addAttribute("numberSegment", new NumberSegment());
		return "boss/numberSegment/numberSegmentAdd";
	}
	
	/**
	 * 添加订单信息
	 * 
	 * @param dict
	 * @param model
	 */
	@RequiresPermissions("boss:numberSegment:add")
	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ResponseBody
	public String add(@Valid NumberSegment numberSegment, Model model) {
		numberSegmentService.save(numberSegment);
		logger.info("numberSegment add:"+numberSegment.getNumber());
		return "success";
	}

	/**
	 * 修改订单信息跳转
	 */
	@RequiresPermissions("boss:numberSegment:update")
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String update(@PathVariable("id") String number, Model model) {
		model.addAttribute("numberSegment", numberSegmentService.get(number));
		return "boss/numberSegment/numberSegmentUpdate";
	}
	
	/**
	 * 修改订单信息
	 * @param tmallOrder
	 * @param model
	 * @return
	 */
	@RequiresPermissions("boss:numberSegment:update")
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody NumberSegment numberSegment,Model model) {
		numberSegmentService.update(numberSegment);
		return "success";
	}
	
	
	/**
	 * 删除订单
	 * 
	 * @param id
	 * @return
	 */
	@RequiresPermissions("boss:numberSegment:delete")
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") String id) {
		numberSegmentService.delete(id);
		logger.info("numberSegment delete:"+id);
		return "success";
	}
	
	/**
	 * 根据省份获取城市
	 * @param province
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="getCity")
	@ResponseBody
	public String[] getCity(String province) throws Exception{
		logger.info("getCity province:"+province);
		logger.info("getCity change province:"+province);
		if(province!=""&&province!=null){
			for (String[] str : ParameterListener.cityList) {
				if(province.equals(str[0])){
					logger.info("current province:"+province);
					String[] string=new String[str.length-1];
					for(int i=1;i<str.length;i++){
						string[i-1]=str[i];
					}
					return string;
				}
			}
		}
		logger.info("get city error :"+province);
		return null;
	}
	
}
