package xinxing.boss.admin.boss.other.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xinxing.boss.admin.boss.other.dao.BossOrderPhoneAndSizeRecordDao;
import xinxing.boss.admin.boss.other.domain.BossOrderPhoneAndSizeRecord;
import xinxing.boss.admin.common.persistence.HibernateDao;
import xinxing.boss.admin.common.service.BaseService;

@Service
public class BossOrderPhoneAndSizeRecordService extends BaseService<BossOrderPhoneAndSizeRecord, Integer> {

	@Autowired
	private BossOrderPhoneAndSizeRecordDao bossOrderPhoneAndSizeRecordDao;

	@Override
	public HibernateDao<BossOrderPhoneAndSizeRecord, Integer> getEntityDao() {
		return bossOrderPhoneAndSizeRecordDao;
	}


}
