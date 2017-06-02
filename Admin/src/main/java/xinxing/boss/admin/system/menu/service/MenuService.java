package xinxing.boss.admin.system.menu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xinxing.boss.admin.common.persistence.HibernateDao;
import xinxing.boss.admin.common.service.BaseService;
import xinxing.boss.admin.system.menu.dao.MenuDAO;
import xinxing.boss.admin.system.menu.domain.Menu;

@Service
public class MenuService extends BaseService<Menu, Integer> {

	@Autowired
	private MenuDAO menuDao;
	
	@Override
	public HibernateDao<Menu, Integer> getEntityDao() {
		return menuDao;
	}

	public List<Menu> getMenus() {
		return menuDao.findAll();
	}
	
	
	/**
	 * 获取用户菜单集合
	 * @param userId
	 * @return 结果集合
	 */
	public List<Menu> getRoleMenus(Integer userId){
		return menuDao.getRoleMenus(userId);
	}

	public Menu getIdByNameAndPid(String string, int i) {
		List<Object> find = menuDao.find("from Menu as menu where menu.name like ?0 and menu.pid = ?1", string,i);
		return (Menu) (find.get(0)!=null?find.get(0):null);
	}
	
	
}
