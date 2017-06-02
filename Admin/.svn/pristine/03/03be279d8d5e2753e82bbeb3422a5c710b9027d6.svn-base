package xinxing.boss.admin.boss.operation.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.alibaba.druid.support.json.JSONUtils;

import xinxing.boss.admin.boss.operation.domain.OperatorConfig;
import xinxing.boss.admin.boss.provider.domain.ProviderProductInfo;
import xinxing.boss.admin.common.persistence.HibernateDao;
import xinxing.boss.admin.common.utils.parameter.ParameterListener;

@Repository
public class OperatorConfigDAO extends HibernateDao<OperatorConfig, Integer> {

	public void updateStateByProvince(String province, Integer state,String operator) {
		createSQLQuery("update boss_operator_gate set type = :state where province = :province and operator = :operator").setParameter("state", state).setParameter("province", province).setParameter("operator", operator).executeUpdate(); 
	}

	public void empty() {
		createSQLQuery("truncate table operator_config").executeUpdate();
	}

	public void initTable() {
		String[] operators = new String[]{"移动", "联通","电信"};
		Map<Integer, String> provinceMap = ParameterListener.provinceMap;
		List<String[]> cityList = ParameterListener.cityList;
		for (int i = 0; i < operators.length; i++) {
			StringBuilder sql = new StringBuilder(300000);
			sql.append("INSERT operator_config(operator,province,city,type) value");
			for (Map.Entry<Integer, String> province : provinceMap.entrySet()) {
				for (int j = 0; j < cityList.size(); j++) {
					if(province.getValue().equals(cityList.get(j)[0])){
						for (int j2 = 1; j2 < cityList.get(j).length; j2++) {
							OperatorConfig config = new OperatorConfig();
							config.setCity(cityList.get(j)[j2]);
							config.setOperator(operators[i]);
							config.setProvince(province.getValue());
							config.setType(1);
							sql.append("('").append(operators[i]).append("','").append(province.getValue()).append("','").append(cityList.get(j)[j2]).append("',1),");
						}
					}
				}
			}
			String sql1 = sql.substring(0, sql.length()-1);
			createSQLQuery(sql1).executeUpdate(); 
		}
	}
	
}
