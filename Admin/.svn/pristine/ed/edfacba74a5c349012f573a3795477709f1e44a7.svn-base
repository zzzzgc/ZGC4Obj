package xinxing.boss.admin.boss.customer.dao;

import java.math.BigDecimal;

import org.springframework.stereotype.Repository;

import xinxing.boss.admin.boss.customer.domain.CustomerProductInfo;
import xinxing.boss.admin.common.persistence.HibernateDao;

/**
 * 用户 数据访问组件
 * @author lgy
 *
 */
@Repository
public class CustomerProductInfoDAO extends HibernateDao<CustomerProductInfo, Integer> {

	public int updateSellPriceByDiscount(int id, BigDecimal sellprice) {
	  	String hql="update CustomerProductInfo cpi set cpi.sellPrice=?0 where cpi.id=?1";
	  	return batchExecute(hql, sellprice,id);
	}

}
