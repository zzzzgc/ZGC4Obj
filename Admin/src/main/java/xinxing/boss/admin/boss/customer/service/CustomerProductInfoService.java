package xinxing.boss.admin.boss.customer.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xinxing.boss.admin.boss.customer.dao.CustomerProductInfoDAO;
import xinxing.boss.admin.boss.customer.domain.CustomerProductInfo;
import xinxing.boss.admin.boss.provider.domain.ProviderProductInfo;
import xinxing.boss.admin.common.persistence.HibernateDao;
import xinxing.boss.admin.common.service.BaseService;

@Service
public class CustomerProductInfoService extends BaseService<CustomerProductInfo, Integer> {

	@Autowired
	private CustomerProductInfoDAO customerProductInfoDAO;
	
	@Override
	public HibernateDao<CustomerProductInfo, Integer> getEntityDao() {
		return customerProductInfoDAO;
	}

	public int updateSellPriceByDiscount(int id, BigDecimal sellprice) {
		return customerProductInfoDAO.updateSellPriceByDiscount(id,sellprice);
	}

	/**
	 * 根据采购商id获取供应商产品
	 * @param id
	 * @return
	 */
	public List<CustomerProductInfo> getCustomerProductInfoByCustomerId(String id){
		String hql="from CustomerProductInfo p where p.customerId = "+id;
		return customerProductInfoDAO.find(hql);
			
	}
	
	public List<CustomerProductInfo> getCustomerProductInfoByProductId(Integer id){
		String hql="from CustomerProductInfo p where p.categoryId = ?0";
		return customerProductInfoDAO.find(hql,id);
	}
	
}
