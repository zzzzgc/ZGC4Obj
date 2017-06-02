package xinxing.boss.admin.boss.operation.dao;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import xinxing.boss.admin.boss.operation.domain.AlarmEvent;
import xinxing.boss.admin.common.persistence.HibernateDao;
import xinxing.boss.admin.common.utils.time.DateUtils;

@Repository
public class AlarmEventDAO extends HibernateDao<AlarmEvent, Integer> {
	
	
	public List<AlarmEvent> getBlackCount(){
		//获取前两天时间
		String start = DateUtils.getDate(DateUtils.addDate(new Date(), -2),"yyyy-MM-dd")+" 00:00:00";
		String end=DateUtils.getDate()+" 23:59:59";
		String hql="from AlarmEvent a where a.type=1 and a.time>='"+start+"' and a.time<='"+end+"'";
		List<AlarmEvent> alarmEvents = find(hql);
		return alarmEvents;
	}
	
	public List<AlarmEvent> getExceptionCount(){
		String start = DateUtils.getDate(DateUtils.addDate(new Date(), -2),"yyyy-MM-dd")+" 00:00:00";
		String end=DateUtils.getDate()+" 23:59:59";
		String hql="from AlarmEvent a where a.type=2 and a.time>='"+start+"' and a.time<='"+end+"'";
		List<AlarmEvent> alarmEvents = find(hql);
		return alarmEvents;
	}
}
