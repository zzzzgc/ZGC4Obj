package xinxing.boss.admin.boss.other.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xinxing.boss.admin.boss.other.dao.BossChargeAlarmDao;
import xinxing.boss.admin.boss.other.domain.BossChargeAlarm;
import xinxing.boss.admin.common.persistence.HibernateDao;
import xinxing.boss.admin.common.service.BaseService;

@Service
public class BossChargeAlarmService extends BaseService<BossChargeAlarm, Integer> {

	@Autowired
	private BossChargeAlarmDao bossChargeAlarmDao;

	@Override
	public HibernateDao<BossChargeAlarm, Integer> getEntityDao() {
		return bossChargeAlarmDao;
	}


}
