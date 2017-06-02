package xinxing.boss.admin.boss.other.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xinxing.boss.admin.boss.other.dao.BossProviderSameDao;
import xinxing.boss.admin.boss.other.domain.BossProviderSame;
import xinxing.boss.admin.common.persistence.HibernateDao;
import xinxing.boss.admin.common.service.BaseService;

@Service
public class BossProviderSameService extends BaseService<BossProviderSame, Integer> {

	@Autowired
	private BossProviderSameDao bossProviderSameDao;

	@Override
	public HibernateDao<BossProviderSame, Integer> getEntityDao() {
		return bossProviderSameDao;
	}


}
