package xinxing.boss.admin.boss.customer.dao;

import org.springframework.stereotype.Repository;

import xinxing.boss.admin.boss.customer.domain.CustomerMoneyRecord;
import xinxing.boss.admin.common.persistence.HibernateDao;

/**
 * 用户 数据访问组件
 * @author lgy
 *
 */
@Repository
public class CustomerMoneyRecordDAO extends HibernateDao<CustomerMoneyRecord, Integer> {

}
