package xinxing.boss.admin.system.user.dao;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.CriteriaSpecification;
import org.springframework.stereotype.Repository;

import xinxing.boss.admin.common.persistence.HibernateDao;
import xinxing.boss.admin.system.user.domain.User;

/**
 * 用户 数据访问组件
 * @author cm
 *
 */
@Repository
public class UserDAO extends HibernateDao<User, Integer> {

	private static Logger log = Logger.getLogger(UserDAO.class);
	/**
	 * 获取正常用户
	 * @param loginName
	 * @return
	 */
	public User getValidUser(String loginName){
		String hql = "select a from User a where a.loginName=?0 and a.state=?1 and a.isCustomer=0";
		return this.findUnique(hql, loginName,1);
	}
	
	/**
	 * 更新用户状态
	 * @param loginName
	 * @param state
	 * @return
	 */
	public int updateUserState(String loginName,int state){
		String hql = "update User a set a.state=?0 where a.loginName=?1";
		return batchExecute(hql, state, loginName);
	}
	
	/**
	 * 更新洋葱ID
	 */
	public int updateUserYangCongEventId(String loginName,String eventId){
		Session session = sessionFactory.openSession();
		log.info("session" + session);
		Transaction tx = session.beginTransaction();
		Query query=session.createQuery("update User a set a.yangEventId=? where a.loginName=?");
		query.setString(0, eventId);
		query.setString(1, loginName);
		query.executeUpdate();
		tx.commit();
		session.close();
		return 1;
	}

	public List<Map<String, Object>> queryUser(String sql) {
		return createSQLQuery(sql).setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP).list();
	}
	
}
