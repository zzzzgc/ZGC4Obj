package xinxing.boss.admin.boss.provider.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xinxing.boss.admin.boss.provider.dao.CustomerOrderUniqueDao;
import xinxing.boss.admin.boss.provider.domain.CustomerOrderKey;
import xinxing.boss.admin.boss.provider.domain.CustomerOrderUnique;
import xinxing.boss.admin.common.persistence.HibernateDao;
import xinxing.boss.admin.common.service.BaseService;

@Service
public class CustomerOrderUniqueService extends BaseService<CustomerOrderUnique, CustomerOrderKey> {

	@Autowired
	private CustomerOrderUniqueDao customerOrderUniqueDao;
	
	@Override
	public HibernateDao<CustomerOrderUnique, CustomerOrderKey> getEntityDao() {
		return customerOrderUniqueDao;
	}

}
