package xinxing.boss.admin.system.menu.dao;

import java.util.List;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import xinxing.boss.admin.common.persistence.HibernateDao;
import xinxing.boss.admin.system.menu.domain.Menu;

@Repository
public class MenuDAO extends HibernateDao<Menu, Integer> {

	public List<Menu> getRoleMenus(Integer userId) {
		StringBuffer sb=new StringBuffer();
		sb.append("select m.* from sys_menu m ");
		sb.append("INNER JOIN sys_role_menu rm on m.ID=rm.menu_id ");
		sb.append("INNER JOIN sys_role r ON  r.id=rm.role_id ");
		sb.append("INNER JOIN sys_user_role  ur ON ur.role_id =rm.role_id ");
		sb.append("INNER JOIN sys_user u ON u.id = ur.user_id ");
		sb.append("where u.id=?0 order by m.index");
		SQLQuery sqlQuery=createSQLQuery(sb.toString(), userId);
		sqlQuery.addEntity(Menu.class);
		return sqlQuery.list();
	}


}
