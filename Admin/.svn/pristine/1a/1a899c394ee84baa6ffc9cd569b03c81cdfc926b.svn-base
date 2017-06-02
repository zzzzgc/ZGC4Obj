package xinxing.boss.admin.boss.other.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import xinxing.boss.admin.boss.other.domain.FailReason;
import xinxing.boss.admin.common.persistence.HibernateDao;

@Repository
public class FailReasonDao extends HibernateDao<FailReason, Integer>{

	public void save(String sql) {
		createSQLQuery(sql).executeUpdate();
	}

	public Boolean isReapeat(FailReason failReason) {
		List list = new ArrayList<>();
		list = createSQLQuery("select id from fail_reason where operator = ?0 and failreasonkey = ?1",failReason.getOperator(),failReason.getFailReasonKey()).list();
		if(list.size()==0){
			return false;
		}
		//更新
		if(failReason.getId()!=null){
			if(list.get(0)==failReason.getId()){
				return false;
			}
		}
		return true;
	}

}
