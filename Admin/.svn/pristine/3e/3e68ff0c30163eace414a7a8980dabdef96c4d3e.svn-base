package xinxing.boss.admin.boss.providerBalance.dao;

import org.springframework.stereotype.Repository;

import xinxing.boss.admin.boss.balance.domain.BossCustomerBalanceAudit;
import xinxing.boss.admin.boss.providerBalance.domain.ProviderBalanceAudit;
import xinxing.boss.admin.common.persistence.HibernateDao;

@Repository
public class ProviderBalanceAuditDAO extends HibernateDao<ProviderBalanceAudit, Integer> {
	
	public int auditProviderBalanceRecord(ProviderBalanceAudit record){
		String hql="update ProviderBalanceAudit br set br.status=?0, br.auditUser=?1, br.auditTime=now(), br.remark=?2 where br.id=?3 and br.status=?4";
		return batchExecute(hql, record.getStatus(),record.getAuditUser(), record.getRemark(),record.getId(),0);
	}
	public int reviewProviderBalanceRecord(ProviderBalanceAudit record){
		String hql="update ProviderBalanceAudit br set br.status=?0, br.reviewName=?1, br.reviewTime=now()  where br.id=?2 and br.status=?3";
		return batchExecute(hql, record.getStatus(),record.getAuditUser(), record.getId(),3);
	}
	
}