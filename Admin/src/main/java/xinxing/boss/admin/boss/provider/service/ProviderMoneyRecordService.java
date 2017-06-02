package xinxing.boss.admin.boss.provider.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xinxing.boss.admin.boss.provider.dao.ProviderMoneyRecordDAO;
import xinxing.boss.admin.boss.provider.domain.ProviderMoneyRecord;
import xinxing.boss.admin.common.persistence.HibernateDao;
import xinxing.boss.admin.common.service.BaseService;

@Service
public class ProviderMoneyRecordService extends BaseService<ProviderMoneyRecord, Integer> {

	@Autowired
	private ProviderMoneyRecordDAO providererMoneyRecordDAO;
	
	@Override
	public HibernateDao<ProviderMoneyRecord, Integer> getEntityDao() {
		return providererMoneyRecordDAO;
	}
	
	
}
