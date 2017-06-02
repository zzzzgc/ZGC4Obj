package xinxing.boss.admin.boss.other.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xinxing.boss.admin.boss.other.dao.FailReasonDao;
import xinxing.boss.admin.boss.other.domain.FailReason;
import xinxing.boss.admin.common.persistence.HibernateDao;
import xinxing.boss.admin.common.service.BaseService;

@Service
public class FailReasonService extends BaseService<FailReason, Integer> {

	@Autowired
	private FailReasonDao failReasonDao;
	
	@Override
	public HibernateDao<FailReason, Integer> getEntityDao() {
		return failReasonDao;
	}

	public void save(String sql) {
		failReasonDao.save(sql);
	}

	public Boolean isReapeat(FailReason failReason) {
		return failReasonDao.isReapeat(failReason) ;
	}


}
