package xinxing.boss.admin.boss.customer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import xinxing.boss.admin.boss.customer.dao.CustomerDataAnalyzeDao;
import xinxing.boss.admin.boss.customer.domain.CustomerDataAnalyze;
import xinxing.boss.admin.common.persistence.HibernateDao;
import xinxing.boss.admin.common.service.BaseService;

@Service
public class CustomerDataAnalyzeService extends BaseService<CustomerDataAnalyze, Integer> {

	@Autowired
	private CustomerDataAnalyzeDao customerDataAnalyzeDao;
	
	@Override
	public HibernateDao<CustomerDataAnalyze, Integer> getEntityDao() {
		return customerDataAnalyzeDao;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	public void save(String sql) {
		customerDataAnalyzeDao.save(sql);
	}



}
