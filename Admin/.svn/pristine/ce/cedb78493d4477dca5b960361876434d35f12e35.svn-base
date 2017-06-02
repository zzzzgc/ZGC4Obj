package xinxing.boss.admin.boss.providerBalance.service;


import java.math.BigDecimal;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import xinxing.boss.admin.boss.balance.domain.BossCustomerBalanceAudit;
import xinxing.boss.admin.boss.customer.domain.CustomerInfo;
import xinxing.boss.admin.boss.customer.domain.CustomerMoneyRecord;
import xinxing.boss.admin.boss.operation.dao.OperatorConfigDAO;
import xinxing.boss.admin.boss.provider.dao.ProviderInfoDao;
import xinxing.boss.admin.boss.provider.dao.ProviderMoneyRecordDAO;
import xinxing.boss.admin.boss.provider.domain.ProviderInfo;
import xinxing.boss.admin.boss.provider.domain.ProviderMoneyRecord;
import xinxing.boss.admin.boss.providerBalance.cmd.ProviderBalanceRecordCmd;
import xinxing.boss.admin.boss.providerBalance.dao.ProviderBalanceAuditDAO;
import xinxing.boss.admin.boss.providerBalance.domain.ProviderBalanceAudit;
import xinxing.boss.admin.common.persistence.HibernateDao;
import xinxing.boss.admin.common.service.BaseService;

@Service
public class ProviderBalanceAuditService extends BaseService<ProviderBalanceAudit, Integer> {
	private static Logger log = Logger.getLogger(ProviderBalanceAuditService.class);
	@Autowired
	private ProviderBalanceAuditDAO providerBalanceRecordDAO;
	@Autowired
	private ProviderMoneyRecordDAO providerMoneyRecordDAO;
	@Autowired
	private ProviderInfoDao providerInfoDao;
	@Autowired
	private OperatorConfigDAO operatorConfigDAO;
	
	@Override
	public HibernateDao<ProviderBalanceAudit, Integer> getEntityDao() {
		return providerBalanceRecordDAO;
	}
	
	

	public void addBalanceRecord(ProviderBalanceAudit providerBalanceAudit) {
		providerBalanceRecordDAO.save(providerBalanceAudit);
	}
	//审核注资
	@Transactional(readOnly=false)
	public void auditProviderBalanceRecord(ProviderBalanceAudit providerBalanceRecord) {
		int res = 0;
		if(providerBalanceRecord.getStatus() == 1){
			res = providerBalanceRecordDAO.reviewProviderBalanceRecord(providerBalanceRecord);
		}else{
			res = providerBalanceRecordDAO.auditProviderBalanceRecord(providerBalanceRecord);
		}
		if(res>0 && providerBalanceRecord.getStatus()==1){ //审核通过
			ProviderBalanceAudit record = providerBalanceRecordDAO.find(providerBalanceRecord.getId());
			int providerId = record.getProviderId();
			BigDecimal balance = record.getBalance();
			int resAdd = providerInfoDao.updateProviderBalance(providerId, balance);
			if(resAdd>0){
				ProviderMoneyRecord reMoneyRecord = new ProviderMoneyRecord();
				reMoneyRecord.setCost(balance);
				reMoneyRecord.setCostType(providerBalanceRecord.getType());
				reMoneyRecord.setProviderId(providerId);
				ProviderInfo info = providerInfoDao.find(providerId);
				BigDecimal decimal = info.getBalance();
				String remark = "注资" + record.getBalance() + "元";
				reMoneyRecord.setRemark(remark);
				reMoneyRecord.setFundBalance(decimal);
				providerMoneyRecordDAO.save(reMoneyRecord);
				if(info.getBalance().doubleValue()>info.getAlarmBalance()){
					try {
						Integer i = operatorConfigDAO.saveOrUpdate("update boss_operate_record set status=1 where otherId="+info.getId()+" and type=3");//更改为要发预警状态
						log.info("供货商:"+info.getId()+",出发余额预警:余额:"+info.getBalance().doubleValue()+",预警金额:"+info.getAlarmBalance()+",是否更改成功:"+i);
						
					} catch (Exception e) {
						log.info("更改状态失败:"+info.getId()+",名字:"+info.getProviderName());
					}
				}
				
			}else{
				throw new RuntimeException();
			}
		}
	}
	//复核注资
	@Transactional(readOnly=false)
	public void reviewProviderBalanceRecord(ProviderBalanceAudit providerBalanceRecord) {
		int res = providerBalanceRecordDAO.auditProviderBalanceRecord(providerBalanceRecord);
		if(res>0 && providerBalanceRecord.getStatus()==1){ //审核通过
			ProviderBalanceAudit record = providerBalanceRecordDAO.find(providerBalanceRecord.getId());
			int providerId = record.getProviderId();
			BigDecimal balance = record.getBalance();
			int resAdd = providerInfoDao.updateProviderBalance(providerId, balance);
			if(resAdd>0){
				ProviderMoneyRecord reMoneyRecord = new ProviderMoneyRecord();
				reMoneyRecord.setCost(balance);
				reMoneyRecord.setCostType(providerBalanceRecord.getType());
				reMoneyRecord.setProviderId(providerId);
				ProviderInfo info = providerInfoDao.find(providerId);
				BigDecimal decimal = info.getBalance();
				String remark = "注资" + record.getBalance() + "元";
				reMoneyRecord.setRemark(remark);
				reMoneyRecord.setFundBalance(decimal);
				providerMoneyRecordDAO.save(reMoneyRecord);
				if(info.getBalance().doubleValue()>info.getAlarmBalance()){
					try {
						Integer i = operatorConfigDAO.saveOrUpdate("update boss_operate_record set status=1 where otherId="+info.getId()+" and type=3");//更改为要发预警状态
						log.info("供货商:"+info.getId()+",出发余额预警:余额:"+info.getBalance().doubleValue()+",预警金额:"+info.getAlarmBalance()+",是否更改成功:"+i);
						
					} catch (Exception e) {
						log.info("更改状态失败:"+info.getId()+",名字:"+info.getProviderName());
					}
				}
				
			}else{
				throw new RuntimeException();
			}
		}
	}
}
