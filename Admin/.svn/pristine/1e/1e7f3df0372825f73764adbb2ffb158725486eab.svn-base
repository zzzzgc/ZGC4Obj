package xinxing.boss.admin.boss.other.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xinxing.boss.admin.boss.other.dao.BossOrderResendRecordDao;
import xinxing.boss.admin.boss.other.domain.BossOrderResendRecord;
import xinxing.boss.admin.common.persistence.HibernateDao;
import xinxing.boss.admin.common.service.BaseService;

@Service
public class BossOrderResendRecordService extends BaseService<BossOrderResendRecord, Integer> {

	@Autowired
	private BossOrderResendRecordDao bossOrderResendRecordDao;

	@Override
	public HibernateDao<BossOrderResendRecord, Integer> getEntityDao() {
		return bossOrderResendRecordDao;
	}


}
