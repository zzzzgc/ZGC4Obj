package xinxing.boss.admin.boss.other.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import xinxing.boss.admin.boss.other.dao.AlarmLinkmanDao;
import xinxing.boss.admin.boss.other.domain.AlarmLinkman;
import xinxing.boss.admin.common.persistence.HibernateDao;
import xinxing.boss.admin.common.service.BaseService;

@Service
public class AlarmLinkmanService extends BaseService<AlarmLinkman, Integer> {

	@Autowired
	private AlarmLinkmanDao alarmLinkmanDao;

	@Override
	public HibernateDao<AlarmLinkman, Integer> getEntityDao() {
		return alarmLinkmanDao;
	}
//	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
//	public List<AlarmLinkman> getAlarmLinkman() {
//		alarmLinkmanDao.query(sql);
//		return null;
//	}


}
