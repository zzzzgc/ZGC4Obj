package xinxing.boss.admin.system.role.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xinxing.boss.admin.common.persistence.HibernateDao;
import xinxing.boss.admin.common.service.BaseService;
import xinxing.boss.admin.system.role.dao.RoleDAO;
import xinxing.boss.admin.system.role.domain.Role;

@Service
public class RoleService extends BaseService<Role, Integer> {

	@Autowired
	private RoleDAO roleDAO;

	@Override
	public HibernateDao<Role, Integer> getEntityDao() {
		return roleDAO;
	}

	public Role getRoleByName(String name) {
		List<Object> find = roleDAO.find("from Role where name = '" + name + "'");
		if (find == null || find.get(0) == null)
			return null;
		return (Role) find.get(0);
	}

}
