package xinxing.boss.admin.boss.other.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xinxing.boss.admin.boss.other.dao.BossBackTmallCacheDao;
import xinxing.boss.admin.boss.other.domain.BossBackTmallCache;
import xinxing.boss.admin.common.persistence.HibernateDao;
import xinxing.boss.admin.common.service.BaseService;

@Service
public class BossBackTmallCacheService extends BaseService<BossBackTmallCache, Integer> {

	@Autowired
	private BossBackTmallCacheDao bossBackTmallCacheDao;

	@Override
	public HibernateDao<BossBackTmallCache, Integer> getEntityDao() {
		return bossBackTmallCacheDao;
	}


}
