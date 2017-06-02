package xinxing.boss.admin.system.role.cmd;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

import xinxing.boss.admin.common.persistence.Page;
import xinxing.boss.admin.common.persistence.PropertyFilter;
import xinxing.boss.admin.common.utils.json.JsonUtils;
import xinxing.boss.admin.common.web.BaseController;
import xinxing.boss.admin.system.role.domain.Role;
import xinxing.boss.admin.system.role.service.RoleMenuService;
import xinxing.boss.admin.system.role.service.RoleService;
import xinxing.boss.admin.system.user.domain.User;

@Controller
@RequestMapping("system/role")
public class RoleController extends BaseController {

	@Autowired
	private RoleService roleService;

	@Autowired
	private RoleMenuService roleMenuService;

	/**
	 * 默认页面
	 * 
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "system/role/roleList";
	}

	/**
	 * 角色集合(JSON)
	 * 获取业务角色信息
	 */
	@RequiresPermissions("sys:role:view")
	@RequestMapping(value = "json", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getData(HttpServletRequest request) {
		Page<Role> page = getPage(request);
		List<PropertyFilter> filters = PropertyFilter.buildFromHttpRequest(request);
		page = roleService.search(page, filters);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", JsonUtils.json2Obj(JsonUtils.obj2Json(page.getResult()), List.class));
		map.put("total", page.getTotalCount());
		return map;
	}

	/**
	 * 添加角色跳转
	 * 
	 * @param model
	 * @return
	 */
	@RequiresPermissions("sys:role:add")
	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		model.addAttribute("role", new Role());
		model.addAttribute("action", "create");
		return "system/role/roleForm";
	}

	/**
	 * 添加角色
	 * 
	 * @param role
	 * @param model
	 * @return
	 */
	@RequiresPermissions("sys:role:add")
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid Role role, Model model) {
		roleService.save(role);
		return "success";
	}

	/**
	 * 修改角色跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequiresPermissions("sys:role:update")
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("role", roleService.get(id));
		model.addAttribute("action", "update");
		return "system/role/roleForm";
	}

	/**
	 * 修改角色
	 * 
	 * @param role
	 * @param model
	 * @return
	 */
	@RequiresPermissions("sys:role:update")
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute("role") Role role, Model model) {
		roleService.save(role);
		return "success";
	}

	/**
	 * 删除角色
	 * 
	 * @param id
	 * @return
	 */
	@RequiresPermissions("sys:role:delete")
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Integer id) {
		roleService.delete(id);
		return "success";
	}

	/**
	 * 获取角色拥有的权限ID集合
	 * 
	 * @param id
	 * @return
	 */
	@RequiresPermissions("sys:role:permView")
	@RequestMapping("{id}/json")
	@ResponseBody
	public List<Integer> getRoleMenus(@PathVariable("id") Integer id) {
		List<Integer> menuIdList = roleMenuService.getMenuIds(id);
		return menuIdList;
	}

	/**
	 * 修改角色权限
	 * 
	 * @param id
	 * @param newRoleList
	 * @return
	 */
	@RequiresPermissions("sys:role:permUpd")
	@RequestMapping(value = "{id}/updateMenu")
	@ResponseBody
	public String updateRolePermission(@PathVariable("id") Integer id, @RequestBody List<Integer> newRoleIdList, HttpSession session) {
		List<Integer> oldRoleIdList = roleMenuService.getMenuIds(id);

		// 获取application中的sessions
		@SuppressWarnings("rawtypes")
		HashSet sessions = (HashSet) session.getServletContext().getAttribute("sessions");
		if (null != sessions) {// 当前如果有正在使用的用户，需要更新正在使用的用户的权限

			@SuppressWarnings("unchecked")
			Iterator<Session> iterator = sessions.iterator();
			PrincipalCollection pc = null;

			// 遍历sessions
			while (iterator.hasNext()) {
				HttpSession s = (HttpSession) iterator.next();
				User user = (User) s.getAttribute("user");
				if (user.getId() == id) {
					pc = (PrincipalCollection) s.getAttribute(String.valueOf(id));
					// 清空该用户权限缓存
					roleMenuService.clearUserPermCache(pc);
					s.removeAttribute(String.valueOf(id));
					break;
				}
			}
		}

		roleMenuService.updateRoleMenu(id, oldRoleIdList, newRoleIdList);

		return "success";
	}
}
