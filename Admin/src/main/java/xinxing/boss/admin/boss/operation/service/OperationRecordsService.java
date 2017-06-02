package xinxing.boss.admin.boss.operation.service;

import java.util.List;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import xinxing.boss.admin.boss.operation.dao.OperationRecordsDAO;
import xinxing.boss.admin.boss.operation.domain.OperationRecords;
import xinxing.boss.admin.common.persistence.HibernateDao;
import xinxing.boss.admin.common.service.BaseService;

@Service
public class OperationRecordsService extends BaseService<OperationRecords, Integer> {
	
	@Autowired
	private OperationRecordsDAO operationRecordsDAO;
	
	
	@Override
	public HibernateDao<OperationRecords, Integer> getEntityDao() {
		return operationRecordsDAO;
	}
	
	/**
	 * 获取两天内的操作记录数
	 * @return
	 */
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class) 
	public List<OperationRecords> getReadedOrders(){
		return operationRecordsDAO.getReadedOrders();
	}
}
