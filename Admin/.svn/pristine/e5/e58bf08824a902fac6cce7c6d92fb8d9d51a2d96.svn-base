package xinxing.boss.admin.boss.customer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xinxing.boss.admin.boss.customer.dao.CustomerCallbackRecordDAO;
import xinxing.boss.admin.boss.customer.domain.CustomerCallbackRecord;
import xinxing.boss.admin.common.persistence.HibernateDao;
import xinxing.boss.admin.common.service.BaseService;

@Service
public class CustomerCallbackRecordService extends BaseService<CustomerCallbackRecord, Integer> {

	@Autowired
	private CustomerCallbackRecordDAO customerCallbackRecordDAO;
	
	@Override
	public HibernateDao<CustomerCallbackRecord, Integer> getEntityDao() {
		return customerCallbackRecordDAO;
	}
	
}
