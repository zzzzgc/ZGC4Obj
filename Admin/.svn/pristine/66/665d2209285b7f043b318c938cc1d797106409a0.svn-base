package xinxing.boss.admin.boss.other.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xinxing.boss.admin.boss.other.dao.BossOvertimeOrderRecordDao;
import xinxing.boss.admin.boss.other.domain.BossOvertimeOrderRecord;
import xinxing.boss.admin.common.persistence.HibernateDao;
import xinxing.boss.admin.common.service.BaseService;

@Service
public class BossOvertimeOrderRecordService extends BaseService<BossOvertimeOrderRecord, Integer> {

	@Autowired
	private BossOvertimeOrderRecordDao bossOvertimeOrderRecordDao;

	@Override
	public HibernateDao<BossOvertimeOrderRecord, Integer> getEntityDao() {
		return bossOvertimeOrderRecordDao;
	}


}
