package xinxing.boss.admin.boss.other.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xinxing.boss.admin.boss.other.dao.BossInvoiceAuditDao;
import xinxing.boss.admin.boss.other.domain.BossInvoiceAudit;
import xinxing.boss.admin.common.persistence.HibernateDao;
import xinxing.boss.admin.common.service.BaseService;

@Service
public class BossInvoiceAuditService extends BaseService<BossInvoiceAudit, Integer> {

	@Autowired
	private BossInvoiceAuditDao bossInvoiceAuditDao;

	@Override
	public HibernateDao<BossInvoiceAudit, Integer> getEntityDao() {
		return bossInvoiceAuditDao;
	}


}
