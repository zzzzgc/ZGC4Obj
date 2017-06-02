package xinxing.boss.admin.boss.provider.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xinxing.boss.admin.boss.provider.dao.ProductCategoryInfoDao;
import xinxing.boss.admin.boss.provider.domain.ProductCategoryInfo;
import xinxing.boss.admin.common.persistence.HibernateDao;
import xinxing.boss.admin.common.service.BaseService;

@Service
public class ProductCategoryInfoService extends BaseService<ProductCategoryInfo, Integer> {
	private static Logger logger = LoggerFactory.getLogger(ProductCategoryInfoService.class);
	@Autowired
	private ProductCategoryInfoDao productCategoryInfoDao;
	@Autowired
	SessionFactory sessionFactory;

	@Override
	public HibernateDao<ProductCategoryInfo, Integer> getEntityDao() {
		return productCategoryInfoDao;
	}

	public List<ProductCategoryInfo> getList() {
		String hql = "from ProductCategoryInfo as product where product.pid is null";
		List<ProductCategoryInfo> list = productCategoryInfoDao.find(hql);
		logger.info("list.size:" + list.size());
		return list;
	}

	public List<ProductCategoryInfo> getMenu() {
		String hql = "from ProductCategoryInfo as product where product.pid is not null";
		List<ProductCategoryInfo> list = productCategoryInfoDao.find(hql);
		logger.info("menu.size:" + list.size());
		return list;
	}

	public Map<String, Integer> getPidMap() {
		String hql = "from ProductCategoryInfo as product where product.pid = 0";
		Map<String, Integer> pidMap = new HashMap<String, Integer>();
		List<ProductCategoryInfo> list = productCategoryInfoDao.find(hql);
		for (ProductCategoryInfo productCategoryInfo : list) {
			pidMap.put(productCategoryInfo.getCategoryName(), productCategoryInfo.getId());
		}
		return pidMap;
	}

	public ProductCategoryInfo getByOperatorAndCategoryName(String operator, String province, Integer area) {
		String hql1 = "from ProductCategoryInfo as product where product.categoryName = ?0";
		List<ProductCategoryInfo> list1 = productCategoryInfoDao.find(hql1, operator);
		String hql2 = "from ProductCategoryInfo as product where product.pid = ?0 and product.categoryName = ?1";
		List<ProductCategoryInfo> list = productCategoryInfoDao.find(hql2, list1.get(0).getId(), province);
		if (list.size() > 0) {
			String hql3 = "from ProductCategoryInfo as product where product.pid = ?0 and product.categoryName = ?1";
			List<ProductCategoryInfo> list3 = productCategoryInfoDao.find(hql3, list.get(0).getId(), area == 1 ? "全国" : "本省");
			if (list3.size() > 0) {
				return null;
			}
		}
		return list != null ? list.get(0) : null;
	}

	public List<ProductCategoryInfo> getOperator(String operator, String province, String useArea, String name) {
		int i = 0;
		if ("全国".equals(useArea.trim())) {
			i = 1;
		}

		String hql = "from ProductCategoryInfo as product where product.operator=?0 and product.province=?1 and product.area=?2";
		List<ProductCategoryInfo> list = productCategoryInfoDao.find(hql, operator, province, i);
		return list;
	}

	public List<ProductCategoryInfo> getOperator(String operator) {
		String hql = "from ProductCategoryInfo as product where product.operator=?0 ";
		List<ProductCategoryInfo> list = productCategoryInfoDao.find(hql, operator);
		return list;
	}

	public List<Integer> getMenuId(String operator, String province, Integer area) {
		List<Integer> ids = new ArrayList<>();
		String hql = "from ProductCategoryInfo as product where product.operator = ?0 and product.province = ?1 and product.area = ?2";
		List<ProductCategoryInfo> list = productCategoryInfoDao.find(hql, operator, province, area);
		// 判断是否需要删除全国/本省
		if (list.size() == 0) {
			String hql1 = "from ProductCategoryInfo as product where product.categoryName = ?0";
			List<ProductCategoryInfo> list1 = productCategoryInfoDao.find(hql1, operator);
			String hql2 = "from ProductCategoryInfo as product where product.pid = ?0 and product.categoryName = ?1";
			List<ProductCategoryInfo> list2 = productCategoryInfoDao.find(hql2, list1.get(0).getId(), province);
			String hql3 = "from ProductCategoryInfo as product where product.pid = ?0 and product.categoryName = ?1";
			List<ProductCategoryInfo> list3 = productCategoryInfoDao.find(hql3, list2.get(0).getId(), area == 1 ? "全国" : "本省");
			ids.add(list3.size() > 0 ? list3.get(0).getId() : null);
			if (list3.size() > 0) {
				String hql4 = "from ProductCategoryInfo as product where product.pid = ?0";
				List<ProductCategoryInfo> list4 = productCategoryInfoDao.find(hql4, list2.get(0).getId());
				if (list4.size() == 1) {
					ids.add(list2.size() > 0 ? list2.get(0).getId() : null);
				}
			}
		}
		return ids;

	}

//	public void saveBatch(List<ProductCategoryInfo> search) {
//		Session session = sessionFactory.openSession();
//		Transaction tx = session.beginTransaction();
//		int size = 0;
//		for (int i = 0, length = search.size(); i < length; i++) {
//			ProductCategoryInfo productCategoryInfo = search.get(i);
//			save(productCategoryInfo);
//			size++;
//			if (size % 50 == 0) {
//				session.flush();
//				session.clear();
//			}
//		}
//		tx.commit();
//		session.close();
//	}
}
