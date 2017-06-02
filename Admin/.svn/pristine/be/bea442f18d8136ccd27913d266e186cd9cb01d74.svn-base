package xinxing.boss.admin.boss.provider.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xinxing.boss.admin.boss.provider.dao.BlackPhoneDao;
import xinxing.boss.admin.boss.provider.domain.BlackPhone;
import xinxing.boss.admin.common.persistence.HibernateDao;
import xinxing.boss.admin.common.service.BaseService;

@Service
public class BlackPhoneService extends BaseService<BlackPhone, Integer> {

	@Autowired
	private BlackPhoneDao blackPhoneDao;
	
	@Override
	public HibernateDao<BlackPhone, Integer> getEntityDao() {
		return blackPhoneDao;
	}

	public void save(String sql) {
		blackPhoneDao.save(sql);
	}


}
