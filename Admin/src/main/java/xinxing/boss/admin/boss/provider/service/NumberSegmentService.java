package xinxing.boss.admin.boss.provider.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xinxing.boss.admin.boss.provider.dao.NumberSegmentDao;
import xinxing.boss.admin.boss.provider.domain.NumberSegment;
import xinxing.boss.admin.common.persistence.HibernateDao;
import xinxing.boss.admin.common.service.BaseService;

@Service
public class NumberSegmentService extends BaseService<NumberSegment, String> {

	@Autowired
	private NumberSegmentDao numberSegmentDao;
	
	
	@Override
	public HibernateDao<NumberSegment, String> getEntityDao() {
		return numberSegmentDao;
	}

	public NumberSegment getNumberSegment(String phone){
		return numberSegmentDao.getNumberSegment(phone);
	}

}
