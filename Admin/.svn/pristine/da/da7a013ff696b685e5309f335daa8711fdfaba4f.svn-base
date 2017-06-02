package xinxing.boss.admin.boss.provider.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import xinxing.boss.admin.boss.provider.domain.BlackPhone;
import xinxing.boss.admin.common.persistence.HibernateDao;

@Repository
public class BlackPhoneDao extends HibernateDao<BlackPhone, Integer>{

	public void save(String sql) {
		createSQLQuery(sql).executeUpdate();
	}

}
