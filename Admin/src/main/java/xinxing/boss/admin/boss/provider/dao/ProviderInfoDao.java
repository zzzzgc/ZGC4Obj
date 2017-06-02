package xinxing.boss.admin.boss.provider.dao;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Repository;

import xinxing.boss.admin.boss.customer.domain.CustomerInfo;
import xinxing.boss.admin.boss.provider.domain.ProviderInfo;
import xinxing.boss.admin.common.persistence.HibernateDao;

@Repository
public class ProviderInfoDao extends HibernateDao<ProviderInfo, Integer> {

	
	/**
	 * 通过采购商简称获取采购商
	 * @param simpleName
	 * @return
	 */
	public List<ProviderInfo> checkSimpleName(String simpleName){
		String hql = "from ProviderInfo ci where ci.supplier=?0";
		return find(hql,simpleName);
	}
	
	/**
	 * 增加供应商金额
	 * @param customerId
	 * @param addBalance
	 * @return
	 */
	public int updateProviderBalance(int providerId,BigDecimal addBalance){
		String hql = "update ProviderInfo ci set ci.balance =ci.balance+?0 where ci.id=?1";
		return batchExecute(hql, addBalance, providerId);
	}
}
