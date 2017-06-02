package xinxing.boss.admin.boss.provider.cmd;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import xinxing.boss.admin.boss.provider.domain.CustomerOrderUnique;
import xinxing.boss.admin.boss.provider.service.CustomerOrderUniqueService;
import xinxing.boss.admin.common.persistence.Page;
import xinxing.boss.admin.common.persistence.PropertyFilter;
import xinxing.boss.admin.common.web.BaseController;

@Controller
@RequestMapping(value="boss/customerOrderUniue")
public class CustomerOrderUniqueCmd extends BaseController{
private static Logger logger = LoggerFactory.getLogger(CustomerOrderUniqueCmd.class);
	
	@Autowired
	private CustomerOrderUniqueService customerOrderUniqueService;
	
	
	/**
	 * 默认页面   
	 */
	@RequestMapping(value="list",method = RequestMethod.GET)
	public String list() {
		return "boss/customerOrderUniue/customerOrderUniueList";
	}
	
	/**
	 * 获取所有订单信息
	 * @return
	 */
	@RequestMapping(value="customerList",method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getConfigList(HttpServletRequest request){
		Page<CustomerOrderUnique> page = getPage(request);		
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		page = customerOrderUniqueService.search(page, filters);
		return getEasyUIData(page);
	}
}
