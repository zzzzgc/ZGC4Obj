package xinxing.boss.admin.boss.other.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xinxing.boss.admin.boss.other.dao.BossProviderChargeDao;
import xinxing.boss.admin.boss.other.domain.BossProviderCharge;
import xinxing.boss.admin.common.persistence.HibernateDao;
import xinxing.boss.admin.common.service.BaseService;

@Service
public class BossProviderChargeService extends BaseService<BossProviderCharge, Integer> {

	@Autowired
	private BossProviderChargeDao bossProviderChargeDao;

	@Override
	public HibernateDao<BossProviderCharge, Integer> getEntityDao() {
		return bossProviderChargeDao;
	}

	public List<BossProviderCharge> getByOrderId(Integer orderId) {
		return search("from BossProviderCharge where orderId = ?0", orderId);
	}


}
