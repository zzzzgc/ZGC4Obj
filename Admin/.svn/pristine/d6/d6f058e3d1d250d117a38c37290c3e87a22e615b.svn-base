package xinxing.boss.admin.boss.provider.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xinxing.boss.admin.boss.provider.dao.ProviderProductInfoDao;
import xinxing.boss.admin.boss.provider.domain.ProviderProductInfo;
import xinxing.boss.admin.common.persistence.HibernateDao;
import xinxing.boss.admin.common.service.BaseService;

@Service
public class ProviderProductInfoService extends BaseService<ProviderProductInfo, Integer> {

	@Autowired
	private ProviderProductInfoDao ProviderProductInfoDao;
	
	@Override
	public HibernateDao<ProviderProductInfo, Integer> getEntityDao() {
		return ProviderProductInfoDao;
	}
	
	public void updateSellPriceByDiscount(int id, double costPrice) {
		ProviderProductInfo providerProductInfo = ProviderProductInfoDao.get(id);
		if(providerProductInfo!=null){
			providerProductInfo.setCostPrice(costPrice);
			ProviderProductInfoDao.updateSellPriceByDiscount(providerProductInfo);
		}
	}
	
	
	/**
	 * 根据供应商id获取供应商产品
	 * @param id
	 * @return
	 */
	public List<ProviderProductInfo> getProviderProductInfoByProviderId(String id){
		String hql="from ProviderProductInfo p where p.providerId = "+id;
		return ProviderProductInfoDao.find(hql);
			
	}
	
	/**
	 * 根据分类id获取供应商产品
	 * @param id
	 * @return
	 */
	public List<ProviderProductInfo> getProviderProductInfoByProductId(Integer id){
		String hql="from ProviderProductInfo p where p.categoryId=?0";
		return ProviderProductInfoDao.find(hql,id);
	}
}
