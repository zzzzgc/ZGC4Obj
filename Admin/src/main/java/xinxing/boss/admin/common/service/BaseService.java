package xinxing.boss.admin.common.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import xinxing.boss.admin.common.persistence.HibernateDao;
import xinxing.boss.admin.common.persistence.Page;
import xinxing.boss.admin.common.persistence.PropertyFilter;

/**
 * 基础service 所有service继承该类 提供基础的实体操作方法
 * 使用HibernateDao<T,PK>进行业务对象的CRUD操作,子类需重载getEntityDao()函数提供该DAO.
 * 
 * @author ty
 * @date 2014年8月20日 上午11:21:14
 * @param <T>
 * @param <PK>
 */
public abstract class BaseService<T, PK extends Serializable> {
	private static Logger logger = LoggerFactory.getLogger("用户操作日志");
	@Autowired
	SessionFactory sessionFactory;

	/**
	 * 子类需要实现该方法，提供注入的dao
	 * 
	 * @return
	 */
	public abstract HibernateDao<T, PK> getEntityDao();

	@Transactional(readOnly = true)
	public T get(final PK id) {
		return getEntityDao().find(id);
	}

	@Transactional(readOnly = false)
	public void save(final T entity) {

		getEntityDao().save(entity);
	}

	@Transactional(readOnly = false)
	public void update(final T entity) {
		getEntityDao().save(entity);
	}

	@Transactional(readOnly = false)
	public void delete(final T entity) {
		getEntityDao().delete(entity);
	}

	@Transactional(readOnly = false)
	public void delete(final PK id) {
		getEntityDao().delete(id);
	}

	@Transactional(readOnly = true)
	public List<T> getAll() {
		return getEntityDao().findAll();
	}

	// @Transactional(readOnly = true)
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
	public List<T> getAll4user() {
		List<T> findAll = getEntityDao().findAll();
		return findAll;
	}

	@Transactional(readOnly = true)
	public List<T> getAll(Boolean isCache) {
		return getEntityDao().findAll(isCache);
	}

	public List<T> search(final List<PropertyFilter> filters) {
		return getEntityDao().find(filters);
	}

	@Transactional(readOnly = true)
	public Page<T> search(final Page<T> page, final List<PropertyFilter> filters) {
		return getEntityDao().findPage(page, filters);
	}

	@Transactional(readOnly = true)
	public Page<T> exportSearch(final Page<T> page, final List<PropertyFilter> filters, Integer maxNumber) {
		return getEntityDao().findPage(page, filters, maxNumber);
	}

	/*
	 * @Transactional(readOnly = true) public Page<T> search(final Page<T> page,
	 * final String hql,final Map<String, ?> values) { return
	 * getEntityDao().findPage(page, hql, values); }
	 */
	@Transactional(readOnly = true)
	public List<T> search(final String hql, final Object... values) {
		return getEntityDao().find(hql, values);
	}

	@Transactional(readOnly = false)
	public void flush() {
		getEntityDao().flush();
	}

	@Transactional(readOnly = false)
	public int saveOrUpdate(String sql) {
		return getEntityDao().saveOrUpdate(sql);
	}

	public void save(List<String> sqlList) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		int size = 0;
		for (int i = 0, length = sqlList.size(); i < length; i++) {
			String sql = sqlList.get(i);
			session.createSQLQuery(sql).executeUpdate();
			size++;
			if (size % 50 == 0) {
				session.flush();
				session.clear();
			}
		}
		tx.commit();
		session.close();

	}

	/**
	 * 自己写hql saveOrUpdateByHql
	 * 
	 * @param hql
	 */
	@Transactional(readOnly = false)
	public Integer saveOrUpdateByHql(String hql, Object... objects) {
		Integer saveOrUpdateByHql = getEntityDao().saveOrUpdateByHql(hql, objects);
		return saveOrUpdateByHql;
	}

	/**
	 * 自己写sql saveOrUpdate 使用sessionFactory的session
	 * 
	 * @param sql
	 */
	@Transactional(readOnly = false)
	public Integer saveOrUpdateForSession(String sql) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		int saveOrUpdate = session.createSQLQuery(sql).executeUpdate();
		tx.commit();
		session.close();
		return saveOrUpdate;
	}

	/**
	 * 自己写hql saveOrUpdate 使用sessionFactory的session
	 * 
	 * @param hql
	 */
	public Integer saveOrUpdateByHqlForSession(String hql, Object... objects) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		Query createQuery = session.createQuery(hql);
		for (int i = 0; i < objects.length; i++) {
			createQuery.setParameter(i, objects[i]);
		}
		int executeUpdate = createQuery.executeUpdate();
		// query.setCacheMode(CacheMode.IGNORE);
		session.flush();
		session.clear();
		tx.commit();
		session.close();
		return executeUpdate;
	}

	/**
	 * 自己写sql 查询
	 * 
	 * @param sql
	 */
	@Transactional(readOnly = true)
	public List<Map<String, Object>> query(String sql) {
		return getEntityDao().query(sql);
	}

	public void saveBatch(List<T> search) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		int size = 0;
		for (int i = 0, length = search.size(); i < length; i++) {
			T t = search.get(i);
			session.save(t);
			size++;
			if (size % 50 == 0) {
				session.flush();
				session.clear();
			}
		}
		tx.commit();
		session.close();
	}

	public void updateBatch(List<T> search) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		int size = 0;
		for (int i = 0, length = search.size(); i < length; i++) {
			T t = search.get(i);
			session.update(t);
			size++;
			if (size % 50 == 0) {
				session.flush();
				session.clear();
			}
		}
		tx.commit();
		session.close();
	}
}
