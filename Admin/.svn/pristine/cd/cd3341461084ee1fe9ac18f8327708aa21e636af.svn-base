package xinxing.boss.admin.boss.provider.dao;

import java.util.List;

import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import xinxing.boss.admin.boss.provider.domain.ProviderProductInfo;
import xinxing.boss.admin.common.persistence.HibernateDao;

@Repository
public class ProviderProductInfoDao extends HibernateDao<ProviderProductInfo, Integer> {
	
	public void updateSellPriceByDiscount(ProviderProductInfo info) {
	  	save(info);
	  	flush();
	}
	

}
