package xinxing.boss.admin.boss.customer.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xinxing.boss.admin.boss.customer.dao.CustomerMoneyRecordDAO;
import xinxing.boss.admin.boss.customer.domain.CustomerMoneyRecord;
import xinxing.boss.admin.common.persistence.HibernateDao;
import xinxing.boss.admin.common.service.BaseService;

@Service
public class CustomerMoneyRecordService extends BaseService<CustomerMoneyRecord, Integer> {

	@Autowired
	private CustomerMoneyRecordDAO customerMoneyRecordDAO;
	@Autowired
	SessionFactory sessionFactory;

	@Override
	public HibernateDao<CustomerMoneyRecord, Integer> getEntityDao() {
		return customerMoneyRecordDAO;
	}

	public void save(List<Map<String,Object>> list,Map<Integer,List<Integer>> m) {
		
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		int size = 0;
		for (int i = 0; i < list.size(); i++) {
				List<Integer> list2 = m.get(list.get(i).get("id"));
				for (Integer cmrId : list2) {
				list.get(i).put("cmrId", cmrId);
				String orderKey = (String) list.get(i).get("orderKey");
				BigDecimal price = (BigDecimal) list.get(i).get("price");
				String phone = (String) list.get(i).get("phone");
				String hql = "update CustomerMoneyRecord obj set obj.orderKey =:orderKey,obj.phone=:phone,obj.price = :price where obj.id= :id";
				session.createQuery(hql).setString("orderKey", orderKey).setBigDecimal("price", price)
						.setString("phone", phone).setInteger("id", cmrId).executeUpdate();
				}
			size++;
			if (size % 50 == 0) { // 20, same as the JDBC batch size
				session.flush();
				session.clear();
			}
		}
		tx.commit();
		session.close();

	}

}
