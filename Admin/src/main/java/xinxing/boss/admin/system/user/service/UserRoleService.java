package xinxing.boss.admin.system.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import xinxing.boss.admin.common.persistence.HibernateDao;
import xinxing.boss.admin.common.service.BaseService;
import xinxing.boss.admin.system.role.domain.Role;
import xinxing.boss.admin.system.user.dao.UserRoleDAO;
import xinxing.boss.admin.system.user.domain.User;
import xinxing.boss.admin.system.user.domain.UserRole;

@Service
@Transactional(readOnly=true)
public class UserRoleService extends BaseService<UserRole, Integer> {

	@Autowired
	private UserRoleDAO userRoleDAO;
	
	@Override
	public HibernateDao<UserRole, Integer> getEntityDao() {
		return userRoleDAO;
	}
	/**
	 * 添加用户角色
	 */
	@Transactional(readOnly = false)
	public void add(Integer userId, Integer roleId ) {
		userRoleDAO.save(getUserRole(userId, roleId));
	}
	/**
	 * 添加修改用户角色
	 * 
	 * @param id
	 * @param oldList
	 * @param newList
	 */
	@Transactional(readOnly = false)
	public void updateUserRole(Integer userId, List<Integer> oldList,List<Integer> newList) {
		// 是否删除
		for (int i = 0, j = oldList.size(); i < j; i++) {
			if (!newList.contains(oldList.get(i))) {
				userRoleDAO.delUserRole(userId, oldList.get(i));
			}
		}

		// 是否添加
		for (int m = 0, n = newList.size(); m < n; m++) {
			if (!oldList.contains(newList.get(m))) {
				userRoleDAO.save(getUserRole(userId, newList.get(m)));
			}
		}
	}

	/**
	 * 构建UserRole
	 * 
	 * @param userId
	 * @param roleId
	 * @return UserRole
	 */
	private UserRole getUserRole(Integer userId, Integer roleId) {
		UserRole ur = new UserRole();
		ur.setUser(new User(userId));
		ur.setRole(new Role(roleId));
		return ur;
	}

	/**
	 * 获取用户拥有角色id集合
	 * 
	 * @param userId
	 * @return 结果集合
	 */
	public List<Integer> getUserRoles(Integer userId) {
		return userRoleDAO.getUserRoles(userId);
	}
}
