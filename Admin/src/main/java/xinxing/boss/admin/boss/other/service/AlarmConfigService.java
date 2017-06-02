package xinxing.boss.admin.boss.other.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xinxing.boss.admin.boss.other.dao.AlarmConfigDao;
import xinxing.boss.admin.boss.other.domain.AlarmConfig;
import xinxing.boss.admin.common.persistence.HibernateDao;
import xinxing.boss.admin.common.service.BaseService;

@Service
public class AlarmConfigService extends BaseService<AlarmConfig, Integer> {

	@Autowired
	private AlarmConfigDao blarmConfigDao;

	@Override
	public HibernateDao<AlarmConfig, Integer> getEntityDao() {
		return blarmConfigDao;
	}


}
