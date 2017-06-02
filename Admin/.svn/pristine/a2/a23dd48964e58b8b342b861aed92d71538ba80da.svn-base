package xinxing.boss.admin.system.menu.cmd;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import xinxing.boss.admin.boss.other.cmd.BossCustomerRouteCmd;
import xinxing.boss.admin.common.utils.json.JsonUtils;
import xinxing.boss.admin.common.web.BaseController;
import xinxing.boss.admin.system.menu.domain.Menu;
import xinxing.boss.admin.system.menu.service.MenuService;
import xinxing.boss.admin.system.role.domain.Role;
import xinxing.boss.admin.system.role.service.RoleMenuService;
import xinxing.boss.admin.system.role.service.RoleService;
import xinxing.boss.admin.system.user.domain.User;
import xinxing.boss.admin.system.user.service.UserService;
import xinxing.boss.admin.system.user.utils.UserUtil;

@Controller
@RequestMapping("system/menu")
public class MenuController extends BaseController {
	private static Logger log = LoggerFactory.getLogger(MenuController.class);
	@Autowired
	private MenuService menuService;
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private RoleMenuService roleMenuService;

	/**
	 * 默认页面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "system/menu/menuList";
	}

	/**
	 * 菜单集合(JSON)
	 */
	@RequiresPermissions("sys:perm:menu:view")
	@RequestMapping(value = "menu/json", method = RequestMethod.GET)
	@ResponseBody
	public List<Map<String, Object>> menuDate() {
		List<Menu> menuList = new ArrayList<Menu>();
		menuList = menuService.getMenus();

		List<Integer> pidList = new ArrayList<Integer>();
		for (Menu menu : menuList) {
			if (menu.getPid() != null)
				pidList.add(menu.getPid());
		}
		List<Map<String, Object>> mList = new ArrayList<Map<String, Object>>();
		for (Menu menu : menuList) {
			Map<String, Object> json2Obj = JsonUtils.json2Obj(JsonUtils.obj2Json(menu), Map.class);
			Integer id = (Integer) json2Obj.get("id");
			Boolean hasSun = false;
			for (Integer pid : pidList) {
				if (pid.toString().equals(id.toString())) {
					hasSun = true;
					break;
				}
			}
			json2Obj.put("state_", json2Obj.get("state"));
			if (hasSun) {
				json2Obj.put("state", "closed");
			}
			mList.add(json2Obj);
		}
		return mList;
	}

	/**
	 * 添加菜单跳转
	 */
	@RequestMapping(value = "menu/create", method = RequestMethod.GET)
	public String menuCreateForm(Model model) {
		model.addAttribute("menu", new Menu());
		model.addAttribute("action", "create");
		return "system/menu/menuForm";
	}

	/**
	 * 添加权限/菜单
	 */
	@RequiresPermissions("sys:perm:add")
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@Valid Menu menu, Model model) {
		menuService.save(menu);
		List<Map<String, Object>> query = userService.query("select id from sys_role where name = 'admin'");
		// 自动将添加的菜单放到admin的权限中
		if (query.size() == 1) {
			Object object = query.get(0).get("id");
			if (object != null) {
				System.out.println(object);
				System.out.println(menu.getId());
				if (menu.getId() != null)
					userService.saveOrUpdate("insert sys_role_menu(menu_id,role_id) values(" + menu.getId() + "," + object.toString() + ")");
			}
		}
		return "success";
	}

	/**
	 * 删除菜单
	 */
	@RequiresPermissions("sys:perm:delete")
	@RequestMapping(value = "delete/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Integer id) {
		menuService.delete(id);
		return "success";
	}

	/**
	 * 修改菜单跳转
	 */
	@RequestMapping(value = "menu/update/{id}", method = RequestMethod.GET)
	public String updateMenuForm(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("menu", menuService.get(id));
		model.addAttribute("action", "update");
		return "system/menu/menuForm";
	}

	/**
	 * 修改权限/菜单
	 */
	@RequiresPermissions("sys:perm:update")
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	public String update(@Valid @ModelAttribute("menu") Menu permission, Model model) {
		menuService.save(permission);
		return "success";
	}

	/**
	 * 当前登录用户的权限集合
	 */
	@RequestMapping("i/json")
	@ResponseBody
	public List<Menu> myPermissionDate() {
		List<Menu> menuList = menuService.getRoleMenus(UserUtil.getCurrentUser().getId());
		return menuList;
	}

	@RequestMapping(value = "createMenu")
	@ResponseBody
	public String createForm(HttpServletRequest req, HttpServletResponse resp, HttpSession session) throws Exception {
		try {
			String className = req.getParameter("className");
			String menuName = req.getParameter("menuName");
			String pName = req.getParameter("pName");
			log.info("createMenu------className:" + className + ",menuName:" + menuName);
			// String menuName = new String(req.getParameter("menuName").getBytes("iso8859-1"),"utf-8");
			if (className == null || className.equals(""))
				return "get语句错误";
			Class<?> cls = Class.forName(className);
			if (menuName == null || menuName.equals("")) {
				menuName = cls.getSimpleName();
			}
			Boolean success = createPermission(cls, menuName, pName, session);
			if (!success)
				return "fail";
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return e.getMessage();
		}
		String random = RandomStringUtils.random(5);
		return "success" + random;
	}

	@RequestMapping(value = "createMenu2")
	@ResponseBody
	public String createMenu2(HttpServletRequest req, HttpServletResponse resp, HttpSession session, String name, String url, String parentName,
			String roleNames) throws Exception {
		String returnStr = "";
		try {
			Menu pMenu = menuService.getIdByNameAndPid(parentName, 0);
			Integer pid = pMenu.getId();
			Menu menu = new Menu(pid, name, 0, 10, url, "", 1, null);
			menuService.save(menu);

			String[] split = roleNames.split(",");
			Integer menuId = menu.getId();
			for (String roleName : split) {
				Role role = roleService.getRoleByName(roleName);
				if (role != null) {
					Integer roleId = role.getId();
					roleService.saveOrUpdate("INSERT INTO sys_role_menu (role_id,menu_id) VALUES (" + roleId + ", " + menuId + ")");
				} else
					returnStr = "----" + roleName;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "错误:" + e.getMessage();
		}
		String random = RandomStringUtils.random(5);
		return "success" + returnStr + random;
	}

	private Boolean createPermission(Class cls, String menuName, String pName, HttpSession session) {
		// 添加权限
		Method[] declaredMethods = cls.getDeclaredMethods();
		List<String> list = new ArrayList<>();
		for (Method method : declaredMethods) {
			RequiresPermissions annotation2 = method.getAnnotation(RequiresPermissions.class);
			if (annotation2 != null && !list.contains(annotation2.value()[0])) {
				list.add(annotation2.value()[0]);
			}
		}
		RequestMapping annotation = (RequestMapping) cls.getAnnotation(RequestMapping.class);
		if (StringUtils.isBlank(pName)) {
			pName = "其它";
		}
		Menu pMenu = menuService.getIdByNameAndPid(pName, 0);

		int maxIndex = 0;
		List<Menu> find = menuService.search("from Menu as menu where menu.pid = ?0", pMenu.getId());
		for (Menu menu : find) {
			Integer curindex = menu.getIndex();
			if (curindex != null && curindex > maxIndex) {
				maxIndex = curindex;
			}
		}

		if (pMenu == null)
			return false;
		Menu menu = new Menu();
		menu.setPid(pMenu.getId());
		menu.setName(menuName);
		menu.setType(0);
		menu.setUrl(annotation.value()[0] + "/list");
		menu.setState(1);
		menu.setIndex(maxIndex);
		menuService.save(menu);
		log.info(JsonUtils.obj2Json(list));
		Collections.sort(list);
		Map<String, String[]> nameIconMap = new HashMap<String, String[]>();
		nameIconMap.put("add", new String[] { "增加", "icon-hamburg-up" });
		nameIconMap.put("delete", new String[] { "删除", "icon-hamburg-stop" });
		nameIconMap.put("exportExcel", new String[] { "导出excel", "icon-standard-page-excel" });
		nameIconMap.put("update", new String[] { "更新", "icon-hamburg-publish" });
		List nameIconList = new ArrayList<>();
		if (menu.getId() != null) {
			for (int i = 0; i < list.size(); i++) {
				Menu m = new Menu();
				String url = list.get(i);
				for (Entry<String, String[]> entry : nameIconMap.entrySet()) {
					if (url.contains(entry.getKey())) {
						String[] value = entry.getValue();
						m.setName(value[0]);
						m.setIcon(value[1]);
					}
				}
				m.setPid(menu.getId());
				m.setType(1);
				m.setUrl(url);
				m.setState(1);
				List<Map<String, Object>> query = menuService.query("select max(`index`) from sys_menu where pid = " + menu.getId());
				Object object = query.get(0).get("index");
				m.setIndex(object != null ? Integer.valueOf(object.toString()) : 0);
				menuService.save(m);
			}
		}

		// 将菜单添加到admin的权限
		Role role = roleService.getRoleByName("admin");
		if (role == null)
			return false;
		Integer id = role.getId();
		List<Integer> oldRoleIdList = roleMenuService.getMenuIds(role.getId());
		List<Integer> newRoleIdList = new ArrayList<>();
		List<Menu> all = menuService.getAll();
		for (Menu forMenu : all) {
			newRoleIdList.add(forMenu.getId());
		}
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
				if (user != null && user.getId() == id) {
					pc = (PrincipalCollection) s.getAttribute(String.valueOf(id));
					// 清空该用户权限缓存
					roleMenuService.clearUserPermCache(pc);
					s.removeAttribute(String.valueOf(id));
					break;
				}
			}
		}

		roleMenuService.updateRoleMenu(id, oldRoleIdList, newRoleIdList);
		return true;
	}
}
