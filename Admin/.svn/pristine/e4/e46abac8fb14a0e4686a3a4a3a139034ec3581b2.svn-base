package xinxing.boss.admin.boss.provider.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xinxing.boss.admin.boss.provider.dao.ProviderInfoDao;
import xinxing.boss.admin.boss.provider.domain.ProviderInfo;
import xinxing.boss.admin.common.persistence.HibernateDao;
import xinxing.boss.admin.common.service.BaseService;

@Service
public class ProviderInfoService extends BaseService<ProviderInfo, Integer> {

	@Autowired
	private ProviderInfoDao providerInfoDao;

	@Override
	public HibernateDao<ProviderInfo, Integer> getEntityDao() {
		return providerInfoDao;
	};
	
	/**
	 * 通过采购商简称获取采购商
	 * @param simpleName
	 * @return
	 */
	public Map<String,String> checkSimpleName(String simpleName){
		Map<String,String> map=new HashMap<String,String>();
		if(StringUtils.isBlank(simpleName)){
			map.put("msg", "请填写简称");
		}
		List<ProviderInfo> providerInfos = providerInfoDao.checkSimpleName(simpleName);
		if(providerInfos.size()<1){
			 map.put("msg", "success");
			 return map;
		}
		ProviderInfo providerInfo=providerInfos.get(0);
		map.put("msg", "重复的 采购商 id:"+providerInfo.getId()+"  名称:"+providerInfo.getProviderName()+"  简称是:"+providerInfo.getSupplier());
		return map;
	}
}
