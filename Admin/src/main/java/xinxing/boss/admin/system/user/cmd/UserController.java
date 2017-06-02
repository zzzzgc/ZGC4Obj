package xinxing.boss.admin.system.user.cmd;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import xinxing.boss.admin.common.persistence.Page;
import xinxing.boss.admin.common.persistence.PropertyFilter;
import xinxing.boss.admin.common.utils.base.BaseUtil;
import xinxing.boss.admin.common.utils.json.JsonUtils;
import xinxing.boss.admin.common.utils.security.Digests;
import xinxing.boss.admin.common.utils.security.Encodes;
import xinxing.boss.admin.common.utils.string.StringUtils;
import xinxing.boss.admin.common.web.BaseController;
import xinxing.boss.admin.system.role.domain.Role;
import xinxing.boss.admin.system.role.service.RoleService;
import xinxing.boss.admin.system.user.domain.User;
import xinxing.boss.admin.system.user.service.UserRoleService;
import xinxing.boss.admin.system.user.service.UserService;

@Controller
@RequestMapping("system/user")
public class UserController extends BaseController {

	private static final String User = null;

	@Autowired
	private UserService userService;

	@Autowired
	private UserRoleService userRoleService;
	@Autowired
	private RoleService roleService;

	@RequestMapping(value = "updateStatus")
	@ResponseBody
	public String updateStatus(HttpServletRequest request, Integer[] ids, Integer status) {
		if (status == 5) {// 修改密码为123456
			String idsStr = BaseUtil.getStrByArraysOrList(ids);
			userService
					.saveOrUpdateByHql("update User set password = 'c50374c788c1109637838a9af8d5017362ea257b',salt='f37e76d168a47ba0' where id in ("
							+ idsStr + ")");
		} else if (status == 0 || status == 1) {// 开通,冻结
			userService.saveOrUpdateByHql("update User set state = " + status + " where id in(" + StringUtils.join(ids, ",") + ")");
		}
		return "success";
	}

	/**
	 * 默认页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "system/user/userList";
	}

	/**
	 * 获取用户json
	 */
	@RequiresPermissions("sys:user:view")
	@RequestMapping(value = "json", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getData(HttpServletRequest request) {
		Page<User> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		PropertyFilter filter = new PropertyFilter("EQI_isCustomer", "0");// 搜索不是客户的信息
		filters.add(filter);
		page = userService.search(page, filters);
		return getEasyUIData(page);
	}

	/**
	 * 添加用户跳转
	 * 
	 * @param model
	 */
	@RequiresPermissions("sys:user:add")
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		model.addAttribute("user", new User());
		model.addAttribute("action", "create");
		List<Role> all = roleService.getAll();
		Map<Integer, String> roleMap = new HashMap<Integer, String>();
		for (Role role : all) {
			roleMap.put(role.getId(), role.getName());
		}
		model.addAttribute("roleMap", roleMap);
		return "system/user/userForm";
	}

	/**
	 * 添加用户
	 * 
	 * @param user
	 * @param model
	 */
	@RequiresPermissions("sys:user:add")
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid User user, Model model, HttpServletRequest req, Integer roleId) {
		userService.save(user);
		userRoleService.add(user.getId(), roleId);
		return "success";
	}

	/**
	 * 修改用户跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequiresPermissions("sys:user:update")
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("user", userService.get(id));
		model.addAttribute("action", "update");
		return "system/user/userForm";
	}

	/**
	 * 修改用户
	 * 
	 * @param user
	 * @param model
	 * @return
	 */
	@RequiresPermissions("sys:user:update")
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute @RequestBody User user, Model model) {
		userService.update(user);
		return "success";
	}

	/**
	 * 删除用户
	 * 
	 * @param id
	 * @return
	 */
	@RequiresPermissions("sys:user:delete")
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Integer id) {
		userService.delete(id);
		return "success";
	}

	/**
	 * 默认页面
	 */
	@RequestMapping(value = "userInfo", method = RequestMethod.GET)
	public String updatePwdPage() {
		return "system/user/userInfo";
	}

	/**
	 * 修改密码跳转
	 */
	@RequestMapping(value = "updatePwd", method = RequestMethod.GET)
	public String updatePwdForm(Model model, HttpSession session) {
		model.addAttribute("user", (User) session.getAttribute("user"));
		return "system/user/updatePwd";
	}

	/**
	 * 修改密码
	 */
	@RequestMapping(value = "updatePwd", method = RequestMethod.POST)
	@ResponseBody
	public String updatePwd(String oldPassword, @Valid @ModelAttribute @RequestBody User user, HttpSession session) {
		try {
			User u = (User) session.getAttribute("user");
			System.out.println(userService);
			byte[] salt =Encodes.decodeHex(user.getSalt()) ;
			byte[] hashPassword = Digests.sha1(oldPassword.getBytes(),salt, 1024);
			
			if (u.getPassword().equals(Encodes.encodeHex(hashPassword))) {
				System.out.println(JsonUtils.obj2Json(user));
				userService.updatePwd(user);
				session.setAttribute("user", user);
				return "success";
			} else {
				return "原密码错误！";
			}
		} catch (Exception e) {
			return "原密码错误！";
		}

	}

	/**
	 * 弹窗页-用户拥有的角色
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequiresPermissions("sys:user:roleView")
	@RequestMapping(value = "{userId}/userRole")
	public String getUserRole(@PathVariable("userId") Integer id, Model model) {
		model.addAttribute("userId", id);
		return "system/user/userRoleList";
	}

	/**
	 * 获取用户拥有的角色ID集合
	 * 
	 * @param id
	 * @return
	 */
	@RequiresPermissions("sys:user:roleView")
	@RequestMapping(value = "{id}/role")
	@ResponseBody
	public List<Integer> getRoleIdList(@PathVariable("id") Integer id) {
		return userRoleService.getUserRoles(id);
	}

	/**
	 * 修改用户拥有的角色
	 * 
	 * @param id
	 * @param newRoleList
	 * @return
	 */
	@RequiresPermissions("sys:user:roleUpd")
	@RequestMapping(value = "{id}/updateRole")
	@ResponseBody
	public String updateUserRole(@PathVariable("id") Integer id, @RequestBody List<Integer> newRoleList) {
		userRoleService.updateUserRole(id, userRoleService.getUserRoles(id), newRoleList);
		return "success";
	}

	/**
	 * 所有RequestMapping方法调用前的Model准备方法, 实现Struts2
	 * Preparable二次部分绑定的效果,先根据form的id从数据库查出Task对象,再把Form提交的内容绑定到该对象上。
	 * 因为仅update()方法的form中有id属性，因此仅在update时实际执行.
	 */
	@ModelAttribute
	public void getUser(@RequestParam(value = "id", defaultValue = "-1") Integer id, Model model) {
		if (id != -1) {
			model.addAttribute("user", userService.get(id));
		}
	}

}
