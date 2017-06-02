package xinxing.boss.admin.common.yangcong.cmd;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import xinxing.boss.admin.common.yangcong.utils.YangCongRes;
import xinxing.boss.admin.common.yangcong.utils.YangCongUtils;
import xinxing.boss.admin.system.user.domain.User;
import xinxing.boss.admin.system.user.service.UserService;

@Controller
@RequestMapping("yc/bind")
public class YangCongController{
	
	private static final Logger log = Logger.getLogger(YangCongController.class);
	
	@Autowired
	UserService userService;

	//@RequiresPermissions("123456")
	@RequestMapping(value="bind" , method=RequestMethod.GET)
	@ResponseBody
	public Map exeCommand() {
		Session session =SecurityUtils.getSubject().getSession();
		User user = null;
		try {
			user = (User) session.getAttribute("user");
		} catch (Exception e) {
			log.error("-error-1" + e.getMessage(),e);
		}
		
		
		Map<Object,Object> resultMap = new HashMap<>();
		YangCongRes yangCongRes = YangCongUtils.getQrcode4Binding();
		if(yangCongRes!=null && user!=null){
			try {
				userService.updateYangCongEventId(user.getLoginName(), yangCongRes.getEvent_id());
			} catch (Exception e) {
				log.info("-error-2" + e.getMessage(),e);
				log.error("-error-3" + e.getMessage(),e);
			}
		}
		resultMap.put("url", yangCongRes.getQrcode_url());
		resultMap.put("state", true);
		return resultMap;
	}

}
