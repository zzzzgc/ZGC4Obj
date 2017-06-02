package xinxing.boss.admin.boss.other.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xinxing.boss.admin.boss.other.dao.BossOperatorCloseConfigDao;
import xinxing.boss.admin.boss.other.domain.BossOperatorCloseConfig;
import xinxing.boss.admin.common.persistence.HibernateDao;
import xinxing.boss.admin.common.service.BaseService;

@Service
public class BossOperatorCloseConfigService extends BaseService<BossOperatorCloseConfig, Integer> {

	@Autowired
	private BossOperatorCloseConfigDao bossOperatorCloseConfigDao;

	@Override
	public HibernateDao<BossOperatorCloseConfig, Integer> getEntityDao() {
		return bossOperatorCloseConfigDao;
	}


}
