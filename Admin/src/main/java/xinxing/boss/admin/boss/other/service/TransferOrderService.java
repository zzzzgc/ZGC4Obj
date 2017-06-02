package xinxing.boss.admin.boss.other.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xinxing.boss.admin.boss.other.dao.TransferOrderDao;
import xinxing.boss.admin.boss.other.domain.TransferOrder;
import xinxing.boss.admin.common.persistence.HibernateDao;
import xinxing.boss.admin.common.service.BaseService;

@Service
public class TransferOrderService extends BaseService<TransferOrder, Integer>{

	@Autowired
	private TransferOrderDao transferOrderDao;
	
	@Override
	public HibernateDao<TransferOrder, Integer> getEntityDao() {
		return transferOrderDao;
	}

}
