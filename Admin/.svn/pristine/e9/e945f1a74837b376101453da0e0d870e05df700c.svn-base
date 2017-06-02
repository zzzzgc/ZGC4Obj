package xinxing.boss.admin.boss.operation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import xinxing.boss.admin.boss.operation.dao.OperatorConfigDAO;
import xinxing.boss.admin.boss.operation.domain.OperatorConfig;
import xinxing.boss.admin.common.persistence.HibernateDao;
import xinxing.boss.admin.common.service.BaseService;

@Repository
public class OperatorConfigService extends BaseService<OperatorConfig, Integer> {
	
	@Autowired
	private OperatorConfigDAO operatorConfigDAO;
	
	
	@Override
	public HibernateDao<OperatorConfig, Integer> getEntityDao() {
		return operatorConfigDAO;
	}


	public void updateStateByProvince(String province, Integer state,String operator) {
		operatorConfigDAO.updateStateByProvince(province, state,operator);
	}


	public void empty() {
		operatorConfigDAO.empty();
	}

	public void initTable() {
		operatorConfigDAO.initTable();
	}

}
