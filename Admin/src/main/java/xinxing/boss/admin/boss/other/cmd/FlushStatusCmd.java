package xinxing.boss.admin.boss.other.cmd;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
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
@RequestMapping(value="boss/flushStatus")
public class FlushStatusCmd extends BaseController {
	private static Logger logger = LoggerFactory.getLogger(FlushStatusCmd.class);
	/**
	 * 默认页面   
	 */
	@RequestMapping(value="list",method = RequestMethod.GET)
	public String list() {
		return "boss/flushStatus/flushStatusList";
	}
	/**
	 * 获取所有订单信息
	 * @return
	 */
	@RequestMapping(value="flushStatusList",method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getConfigList(HttpServletRequest request){
		Page<BossConfig> page = getPage(request);		
		return getEasyUIData(page);
	}
	/**
	 * 默认页面   
	 */
	@RequestMapping(value="flushStatus",method = RequestMethod.GET)
	@ResponseBody
	public boolean flushStatus(HttpServletRequest req) {
		String name = req.getParameter("name");
		if(name!=null){
			ParameterListener.flushStatus(name);
			return true;
		}
		return  false;
	}
	
}
