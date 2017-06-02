package xinxing.boss.admin.boss.other.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xinxing.boss.admin.boss.other.dao.BossTmallCacheDao;
import xinxing.boss.admin.boss.other.domain.BossTmallCache;
import xinxing.boss.admin.common.persistence.HibernateDao;
import xinxing.boss.admin.common.service.BaseService;

@Service
public class BossTmallCacheService extends BaseService<BossTmallCache, Integer> {

	@Autowired
	private BossTmallCacheDao bossTmallCacheDao;

	@Override
	public HibernateDao<BossTmallCache, Integer> getEntityDao() {
		return bossTmallCacheDao;
	}


}
