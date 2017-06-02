package xinxing.boss.admin.boss.provider.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import xinxing.boss.admin.boss.provider.domain.NumberSegment;
import xinxing.boss.admin.common.persistence.HibernateDao;

@Repository
public class NumberSegmentDao extends HibernateDao<NumberSegment, String>{

	public NumberSegment getNumberSegment(String phone){
		String hql="from NumberSegment ns where ns.number =?0";
		List<NumberSegment> list = find(hql,phone);
		if(list.size()<1){
			return null;
		}
		return list.get(0);
	}
	
}
