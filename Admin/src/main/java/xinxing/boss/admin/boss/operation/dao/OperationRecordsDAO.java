package xinxing.boss.admin.boss.operation.dao;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import xinxing.boss.admin.boss.operation.domain.OperationRecords;
import xinxing.boss.admin.common.persistence.HibernateDao;
import xinxing.boss.admin.common.utils.time.DateUtils;

@Repository
public class OperationRecordsDAO extends HibernateDao<OperationRecords, Integer> {

	
	public List<OperationRecords> getReadedOrders(){
		//获取前两天时间
		String start = DateUtils.getDate(DateUtils.addDate(new Date(), -2),"yyyy-MM-dd")+" 00:00:00";
		String end=DateUtils.getDate()+" 23:59:59";
		String hql="from OperationRecords op where op.time>=?0 and op.time<=?1";
		List<OperationRecords> list = find(hql,Timestamp.valueOf(start),Timestamp.valueOf(end));
		return list;
	}
}
