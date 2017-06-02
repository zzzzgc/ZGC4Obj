package xinxing.boss.admin.boss.provider.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import xinxing.boss.admin.boss.provider.dao.ProviderDataAnalyzeDao;
import xinxing.boss.admin.boss.provider.domain.ProviderDataAnalyze;
import xinxing.boss.admin.common.persistence.HibernateDao;
import xinxing.boss.admin.common.service.BaseService;

@Service
public class ProviderDataAnalyzeService extends BaseService<ProviderDataAnalyze, Integer> {

	@Autowired
	private ProviderDataAnalyzeDao providerDataAnalyzeDao;
	
	@Override
	public HibernateDao<ProviderDataAnalyze, Integer> getEntityDao() {
		return providerDataAnalyzeDao;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	public void save(String sql) {
		providerDataAnalyzeDao.save(sql);
	}
	public List<Map<String,Object>> query(String sql){
		return providerDataAnalyzeDao.query(sql);
	}


}
