package xinxing.boss.admin.boss.other.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xinxing.boss.admin.boss.other.dao.BossConfigDao;
import xinxing.boss.admin.boss.other.domain.BossConfig;
import xinxing.boss.admin.common.persistence.HibernateDao;
import xinxing.boss.admin.common.service.BaseService;
import xinxing.boss.admin.common.utils.json.JsonUtils;

@Service
public class BossConfigService extends BaseService<BossConfig, Integer> {

	@Autowired
	private BossConfigDao bossConfigDao;

	@Override
	public HibernateDao<BossConfig, Integer> getEntityDao() {
		return bossConfigDao;
	}

	public String getContentByName(String name) {
		List<BossConfig> list = bossConfigDao.createQuery("from BossConfig bc where bc.name=?0", name).list();
		return list.size() == 1 ? list.get(0).getContent() : null;
	}

	public BossConfig getByName(String name) {
		List<BossConfig> list = bossConfigDao.createQuery("from BossConfig bc where bc.name=?0", name).list();
		return list.size() == 1 ? list.get(0) : null;
	}

}
