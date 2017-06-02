package xinxing.boss.admin.boss.other.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xinxing.boss.admin.boss.other.dao.DiscountChangeDao;
import xinxing.boss.admin.boss.other.domain.DiscountChange;
import xinxing.boss.admin.common.persistence.HibernateDao;
import xinxing.boss.admin.common.service.BaseService;

@Service
public class DiscountChangeService extends BaseService<DiscountChange, Integer> {

	@Autowired
	private DiscountChangeDao discountChangeDao;

	@Override
	public HibernateDao<DiscountChange, Integer> getEntityDao() {
		return discountChangeDao;
	}


}
