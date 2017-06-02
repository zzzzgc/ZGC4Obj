package xinxing.boss.admin.boss.other.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xinxing.boss.admin.boss.other.dao.BossScheduleChangeDao;
import xinxing.boss.admin.boss.other.domain.BossScheduleChange;
import xinxing.boss.admin.common.persistence.HibernateDao;
import xinxing.boss.admin.common.service.BaseService;

@Service
public class BossScheduleChangeService extends BaseService<BossScheduleChange, Integer> {

	@Autowired
	private BossScheduleChangeDao bossScheduleChangeDao;

	@Override
	public HibernateDao<BossScheduleChange, Integer> getEntityDao() {
		return bossScheduleChangeDao;
	}


}
