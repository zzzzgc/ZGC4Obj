package xinxing.boss.admin.system.login.cmd;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import xinxing.boss.admin.common.utils.constants.Constants;
import xinxing.boss.admin.system.user.domain.User;
import xinxing.boss.admin.system.user.service.UserRoleService;
import xinxing.boss.admin.system.user.utils.UserUtil;


/**
 * 登录controller
 * @author ty
 * @date 2015年1月14日
 */
@Controller
@RequestMapping(value = "{adminPath}")
public class LoginController{
	
	@Autowired
	private UserRoleService UserRoleService;
	/**
	 * 默认页面
	 * @return
	 */
	@RequestMapping(value="login",method = RequestMethod.GET)
	public String login() {
		Subject subject = SecurityUtils.getSubject();
		if(subject.isAuthenticated()||subject.isRemembered()){
			Session session = subject.getSession();
			User user = (User) session.getAttribute("user");
			if(user.getBind()==0){
			//	return "system/bindYC";
			}
			
			//把用户的角色Id保存到session域中用于前台的角色判断,只有一个角色Id
			Integer userId = UserUtil.getCurrentUser().getId();
			List<Integer> userRoleId = UserRoleService.getUserRoles(userId);
			session.setAttribute("takeMsg", userRoleId.get(0).toString());
			
			
			return "redirect:"+Constants.getAdminPath();
		} 
		return "system/login";
	}
	
	/**
	 * 登录失败
	 * @param userName
	 * @param model
	 * @return
	 */
	@RequestMapping(value="login",method = RequestMethod.POST)
	public String fail(@RequestParam(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM) String userName, Model model) {
		model.addAttribute(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM, userName);
		return "system/login";
	}

	/**
	 * 登出
	 * @param userName
	 * @param model
	 * @return
	 */
	@RequestMapping(value="logout")
	public String logout(Model model) {
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
		return "system/login";
	}
	
}
