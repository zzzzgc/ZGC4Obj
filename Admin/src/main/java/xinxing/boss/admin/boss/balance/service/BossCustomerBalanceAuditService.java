package xinxing.boss.admin.boss.balance.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import xinxing.boss.admin.boss.balance.dao.BossCustomerBalanceAuditDAO;
import xinxing.boss.admin.boss.balance.domain.BossCustomerBalanceAudit;
import xinxing.boss.admin.boss.customer.dao.CustomerInfoDAO;
import xinxing.boss.admin.boss.customer.dao.CustomerMoneyRecordDAO;
import xinxing.boss.admin.boss.customer.domain.CustomerInfo;
import xinxing.boss.admin.boss.customer.domain.CustomerMoneyRecord;
import xinxing.boss.admin.common.persistence.HibernateDao;
import xinxing.boss.admin.common.service.BaseService;

@Service
public class BossCustomerBalanceAuditService extends BaseService<BossCustomerBalanceAudit, Integer> {

	@Autowired
	private BossCustomerBalanceAuditDAO bossCustomerBalanceAuditDAO; 

	@Autowired
	private CustomerInfoDAO customerInfoDAO;

	@Autowired
	private CustomerMoneyRecordDAO customerMoneyRecordDAO;

	@Override
	public HibernateDao<BossCustomerBalanceAudit, Integer> getEntityDao() {
		return bossCustomerBalanceAuditDAO;
	}

	@Transactional(readOnly = false)
	public void auditBalanceRecord(BossCustomerBalanceAudit balanceRecord) {
		int res = bossCustomerBalanceAuditDAO.auditBalanceRecord(balanceRecord);
//		if(balanceRecord.getType()==6){
//			return;
//		}
		if (res > 0 && balanceRecord.getStatus() == 1) { // 审核通过
			BossCustomerBalanceAudit record = bossCustomerBalanceAuditDAO.find(balanceRecord.getId());
			int customerId = record.getCustomerId();
			BigDecimal balance = record.getBalance();
			int resAdd = customerInfoDAO.updateCustomerBalance(customerId, balance);
			if (resAdd > 0) {
				CustomerMoneyRecord reMoneyRecord = new CustomerMoneyRecord();
				reMoneyRecord.setCost(balance);
				reMoneyRecord.setCostType(balanceRecord.getType());
				reMoneyRecord.setCustomerId(customerId);
				CustomerInfo info = customerInfoDAO.find(customerId);
				BigDecimal decimal = info.getBalance();
				String remark = "注资" + record.getBalance() + "元";
				reMoneyRecord.setRemark(remark);
				reMoneyRecord.setFundBalance(decimal);
				customerMoneyRecordDAO.save(reMoneyRecord);
			} else {
				throw new RuntimeException();
			}
		}
	}

	public void addBalanceRecord(BossCustomerBalanceAudit balanceRecord) {
		bossCustomerBalanceAuditDAO.save(balanceRecord);
	}

}
