package xinxing.boss.admin.boss.customer.dao;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Repository;

import xinxing.boss.admin.boss.customer.domain.CustomerInfo;
import xinxing.boss.admin.common.persistence.HibernateDao;

/**
 * 用户 数据访问组件
 * @author lgy
 *
 */
@Repository
public class CustomerInfoDAO extends HibernateDao<CustomerInfo, Integer> {

	/**
	 * 增加采购商金额
	 * @param customerId
	 * @param addBalance
	 * @return
	 */
	public int updateCustomerBalance(int customerId,BigDecimal addBalance){
		String hql = "update CustomerInfo ci set ci.balance =ci.balance+?0 where ci.id=?1";
		return batchExecute(hql, addBalance, customerId);
	}
	
	/**
	 * 通过采购商简称获取采购商
	 * @param simpleName
	 * @return
	 */
	public List<CustomerInfo> checkSimpleName(String simpleName){
		String hql = "from CustomerInfo ci where ci.cusSimpleName=?0";
		return find(hql,simpleName);
	}
}
