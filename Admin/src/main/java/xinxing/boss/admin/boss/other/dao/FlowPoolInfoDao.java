package xinxing.boss.admin.boss.other.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import xinxing.boss.admin.boss.other.domain.FlowPoolInfo;
import xinxing.boss.admin.common.persistence.HibernateDao;

@Repository
public class FlowPoolInfoDao extends HibernateDao<FlowPoolInfo, Integer>{

	public void save(String sql) {
		createSQLQuery(sql).executeUpdate();
	}


}
