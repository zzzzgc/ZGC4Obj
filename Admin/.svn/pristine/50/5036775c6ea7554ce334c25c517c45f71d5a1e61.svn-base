package xinxing.boss.admin.boss.other.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xinxing.boss.admin.boss.other.dao.BossGdDataAnalyzeDao;
import xinxing.boss.admin.boss.other.domain.BossGdDataAnalyze;
import xinxing.boss.admin.common.persistence.HibernateDao;
import xinxing.boss.admin.common.service.BaseService;

@Service
public class BossGdDataAnalyzeService extends BaseService<BossGdDataAnalyze, Integer> {

	@Autowired
	private BossGdDataAnalyzeDao bossGdDataAnalyzeDao;

	@Override
	public HibernateDao<BossGdDataAnalyze, Integer> getEntityDao() {
		return bossGdDataAnalyzeDao;
	}


}
