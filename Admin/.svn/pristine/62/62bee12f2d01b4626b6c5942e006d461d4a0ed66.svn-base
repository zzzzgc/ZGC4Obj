package xinxing.boss.admin.system.user.service;


import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.CriteriaSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import xinxing.boss.admin.common.persistence.HibernateDao;
import xinxing.boss.admin.common.service.BaseService;
import xinxing.boss.admin.common.utils.security.Digests;
import xinxing.boss.admin.common.utils.security.Encodes;
import xinxing.boss.admin.common.utils.time.DateUtils;
import xinxing.boss.admin.system.user.dao.UserDAO;
import xinxing.boss.admin.system.user.domain.User;

@Service
public class UserService extends BaseService<User, Integer> {
	
	/**加密方法*/
	public static final String HASH_ALGORITHM = "SHA-1";
	public static final int HASH_INTERATIONS = 1024;
	private static final int SALT_SIZE = 8;	//盐长度

	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public HibernateDao<User, Integer> getEntityDao() {
		return userDAO;
	}
	
	/**
	 * 保存用户
	 * @param user
	 */
	@Transactional(readOnly=false)
	public void save(User user) {
		entryptPassword(user);
		user.setTime(DateUtils.getSysTimestamp());
		userDAO.save(user);
	}
	
	/**
	 * 修改密码
	 * @param user
	 */
	@Transactional(readOnly=false)
	public void updatePwd(User user) {
		entryptPassword(user);
		userDAO.save(user);
	}
	
	public int updateYangCongEventId(String loginName,String eventId){
		return userDAO.updateUserYangCongEventId(loginName, eventId);
	}
	
	/**
	 * 修改其它信息
	 * @param user
	 */
	@Transactional(readOnly=false)
	public void updateUserNotPwd(User user){
		userDAO.save(user);
	}

	/**
	 * 按登录名查询用户
	 * @param loginName
	 * @return 用户对象
	 */
	public User getUser(String loginName) {
		return userDAO.findUniqueBy("loginName", loginName);
	}
	
	/**
	 * 更新用户状态
	 * @param loginName
	 * @param state
	 * @return
	 */
	public int updateUserState(String loginName,int state){
		return userDAO.updateUserState(loginName, state);
	}
	
	/**
	 * 获取正常状态的用户
	 * @param loginName
	 * @return
	 */
	public User getValidUser(String loginName){
		return userDAO.getValidUser(loginName);
	}
	
	/**
	 * 根据洋葱事件查询用户
	 * @param yangeventid
	 * @return
	 */
	public User getUserByYangCongEventId(String yangeventid) {
		return userDAO.findUniqueBy("yangEventId", yangeventid);
	}
	
	/**
	 * 设定安全的密码，生成随机的salt并经过1024次 sha-1 hash
	 */
	private void entryptPassword(User user) {
		byte[] salt = Digests.generateSalt(SALT_SIZE);
		user.setSalt(Encodes.encodeHex(salt));

		byte[] hashPassword = Digests.sha1(user.getPlainPassword().getBytes(),salt, HASH_INTERATIONS);
		user.setPassword(Encodes.encodeHex(hashPassword));
	}
	
	/**
	 * 验证原密码是否正确
	 * @param user
	 * @param oldPwd
	 * @return
	 */
	public boolean checkPassword(User user,String oldPassword){
		System.out.println("----------------------------------------------------------");
		byte[] salt =Encodes.decodeHex(user.getSalt()) ;
		byte[] hashPassword = Digests.sha1(oldPassword.getBytes(),salt, HASH_INTERATIONS);
		if(user.getPassword().equals(Encodes.encodeHex(hashPassword))){
			return true;
		}else{
			return false;
		}
	}

	public List<Map<String, Object>> queryUser(String sql) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		List<Map<String,Object>> list = session.createSQLQuery(sql).setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP).list();
		tx.commit();
		session.close();
		return list;
	}
	
}
