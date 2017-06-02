package xinxing.boss.admin.system.role.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import xinxing.boss.admin.common.persistence.HibernateDao;
import xinxing.boss.admin.system.menu.domain.Menu;
import xinxing.boss.admin.system.role.domain.RoleMenu;

@Repository
public class RoleMenuDAO extends HibernateDao<RoleMenu, Integer> {
	
	/**
	 * 查询角色拥有的权限id
	 * @param roleId
	 * @return 结果集合
	 */
	@SuppressWarnings("unchecked")
	public List<Integer> getMenuIds(Integer roleId){
		String hql="select rp.menu.id from RoleMenu rp where rp.role.id=?0";
		Query query= createQuery(hql, roleId);
		return query.list();
	}

	/**
	 * 删除角色权限
	 * @param roleId
	 * @param permissionId
	 */
	public void delRoleMenu(Integer roleId,Integer permissionId){
		String hql="delete RoleMenu rp where rp.role.id=?0 and rp.menu.id=?1";
		batchExecute(hql, roleId,permissionId);
	}

}
