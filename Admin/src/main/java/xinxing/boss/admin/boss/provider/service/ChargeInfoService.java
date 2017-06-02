package xinxing.boss.admin.boss.provider.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xinxing.boss.admin.boss.provider.dao.ChargeInfoDao;
import xinxing.boss.admin.boss.provider.domain.ChargeInfo;
import xinxing.boss.admin.common.persistence.HibernateDao;
import xinxing.boss.admin.common.service.BaseService;

@Service
public class ChargeInfoService extends BaseService<ChargeInfo, Integer> {

	@Autowired
	private ChargeInfoDao chargeInfoDao;
	
	@Override
	public HibernateDao<ChargeInfo, Integer> getEntityDao() {
		return chargeInfoDao;
	}

}
