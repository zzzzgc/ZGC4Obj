package xinxing.boss.admin.boss.order.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.criterion.CriteriaSpecification;
import org.springframework.stereotype.Repository;

import xinxing.boss.admin.boss.order.domain.OrderInfo;
import xinxing.boss.admin.common.persistence.HibernateDao;

/**
 * 用户 数据访问组件
 * @author lgy
 *
 */
@Repository
public class OrderInfoDAO extends HibernateDao<OrderInfo, Integer> {

	public List<Map<String, Object>> query(String sql) {
		List<Map<String,Object>> list = createSQLQuery(sql).setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP).list();
		return list;
	}

	
	public List<Map<String, Object>> getManualWeiXinOrders(){
		String sql="select * from boss_order where customerId=600008 and status=6 and weiXinPrice is null order by receiveTime asc limit 0,30";
		List<Map<String, Object>> list = createSQLQuery(sql).setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP).list();
		return list;
	}
	public List<Map<String, Object>> getManualTmallOrders(){
		String sql="select * from boss_order where customerId=600008 and status=6 and tmallPrice is null order by receiveTime asc limit 0,30";
		List<Map<String, Object>> list = createSQLQuery(sql).setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP).list();
		return list;
	}
	
}
