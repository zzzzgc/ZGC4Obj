package xinxing.boss.admin.boss.other.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xinxing.boss.admin.boss.other.dao.BossCustomerRouteDao;
import xinxing.boss.admin.boss.other.domain.BossCustomerRoute;
import xinxing.boss.admin.common.persistence.HibernateDao;
import xinxing.boss.admin.common.service.BaseService;

@Service
public class BossCustomerRouteService extends BaseService<BossCustomerRoute, Integer> {

	@Autowired
	private BossCustomerRouteDao bossCustomerRouteDao;

	@Override
	public HibernateDao<BossCustomerRoute, Integer> getEntityDao() {
		return bossCustomerRouteDao;
	}


}
