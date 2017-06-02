package xinxing.boss.admin.boss.operation.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xinxing.boss.admin.boss.operation.dao.AlarmEventDAO;
import xinxing.boss.admin.boss.operation.domain.AlarmEvent;
import xinxing.boss.admin.common.persistence.HibernateDao;
import xinxing.boss.admin.common.service.BaseService;

@Service
public class AlarmEventService extends BaseService<AlarmEvent, Integer> {
	
	@Autowired
	private AlarmEventDAO alarmEventDAO;
	
	@Override
	public HibernateDao<AlarmEvent, Integer> getEntityDao() {
		return alarmEventDAO;
	}
	
	/**
	 * 获取当天内的黑名单数量
	 * @return
	 */
	public List<AlarmEvent> getBlackCount(){
		return alarmEventDAO.getBlackCount();
	}
	
	/**
	 * 获取当天内的异常数量
	 * @return
	 */
	public List<AlarmEvent> getExceptionCount(){
		return alarmEventDAO.getExceptionCount();
	}
}
