package xinxing.boss.admin.boss.provider.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.criterion.CriteriaSpecification;
import org.springframework.stereotype.Repository;

import xinxing.boss.admin.boss.provider.domain.ProviderDataAnalyze;
import xinxing.boss.admin.common.persistence.HibernateDao;

@Repository
public class ProviderDataAnalyzeDao extends HibernateDao<ProviderDataAnalyze, Integer>{

	public void save(String sql) {
		createSQLQuery(sql).executeUpdate();
	}

	public List<Map<String,Object>> query(String sql) {
		List<Map<String,Object>> list = createSQLQuery(sql).setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP).list();
		return list;
	}


}
