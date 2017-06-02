package xinxing.boss.admin.boss.balance.dao;

import org.springframework.stereotype.Repository;

import xinxing.boss.admin.boss.balance.domain.BossCustomerBalanceAudit;
import xinxing.boss.admin.common.persistence.HibernateDao;

@Repository
public class BossCustomerBalanceAuditDAO extends HibernateDao<BossCustomerBalanceAudit, Integer> {

	public int auditBalanceRecord(BossCustomerBalanceAudit record){
		String hql="update BossCustomerBalanceAudit br set br.status=?0, br.auditUser=?1, br.auditTime=now(), br.remark=?2 where br.id=?3 and br.status=?4";
		return batchExecute(hql, record.getStatus(),record.getAuditUser(), record.getRemark(),record.getId(),0);
	}
}
