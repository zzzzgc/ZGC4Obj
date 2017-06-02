package xinxing.boss.admin.system.role.service;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import xinxing.boss.admin.common.persistence.HibernateDao;
import xinxing.boss.admin.common.service.BaseService;
import xinxing.boss.admin.system.menu.domain.Menu;
import xinxing.boss.admin.system.role.dao.RoleMenuDAO;
import xinxing.boss.admin.system.role.domain.Role;
import xinxing.boss.admin.system.role.domain.RoleMenu;
import xinxing.boss.admin.system.user.service.UserRealm;

@Service
@Transactional(readOnly=true)
public class RoleMenuService extends BaseService<RoleMenu, Integer> {

	@Autowired
	private RoleMenuDAO roleMenuDAO;
	
	@Override
	public HibernateDao<RoleMenu, Integer> getEntityDao() {
		return roleMenuDAO;
	}
	
	/**
	 * 获取角色权限id集合
	 * @param id
	 * @return List
	 */
	public List<Integer> getMenuIds(Integer roleId){
		return roleMenuDAO.getMenuIds(roleId);
	}
	
	
	/**
	 * 修改角色权限
	 * @param id
	 * @param oldList
	 * @param newList
	 */
	@Transactional(readOnly = false)
	public void updateRoleMenu(Integer id,List<Integer> oldList,List<Integer> newList){
		//是否删除
		for(int i=0,j=oldList.size();i<j;i++){
			if(!newList.contains(oldList.get(i))){
				roleMenuDAO.delRoleMenu(id,oldList.get(i));
			}
		}
		
		//是否添加
		for(int i=0,j=newList.size();i<j;i++){
			if(!oldList.contains(newList.get(i))){
				roleMenuDAO.save(getRoleMenu(id,newList.get(i)));
			}
		}
	}
	
	/**
	 * 构造角色权限对象
	 * @param roleId
	 * @param permissionId
	 * @return RolePermission
	 */
	private RoleMenu getRoleMenu(Integer roleId,Integer permissionId){
		RoleMenu rp=new RoleMenu();
		rp.setRole(new Role(roleId));
		rp.setMenu(new Menu(permissionId));
		return rp;
	}
	
	/**
	 * 清空该角色用户的权限缓存
	 */
	public void clearUserPermCache(PrincipalCollection pc){
		RealmSecurityManager securityManager =  (RealmSecurityManager) SecurityUtils.getSecurityManager();
		UserRealm userRealm = (UserRealm) securityManager.getRealms().iterator().next();
	    userRealm.clearCachedAuthorizationInfo(pc);
	}


}
