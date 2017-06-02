package xinxing.boss.admin.boss.other.service;

import java.text.MessageFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xinxing.boss.admin.boss.other.dao.FlowPoolInfoDao;
import xinxing.boss.admin.boss.other.domain.FlowPoolInfo;
import xinxing.boss.admin.common.persistence.HibernateDao;
import xinxing.boss.admin.common.service.BaseService;
import xinxing.boss.admin.common.utils.time.DateUtils;

@Service
public class FlowPoolInfoService extends BaseService<FlowPoolInfo, Integer> {

	@Autowired
	private FlowPoolInfoDao flowPoolInfoDao;

	@Override
	public HibernateDao<FlowPoolInfo, Integer> getEntityDao() {
		return flowPoolInfoDao;
	}

	public void save(String sql) {
		flowPoolInfoDao.save(sql);
	}

	public void changeAlarmNum(Integer id, Integer changeAlarmNum) {
		Integer saveOrUpdateByHql = flowPoolInfoDao.saveOrUpdateByHql("update FlowPoolInfo set alarmNum = ?,lastUpdateTime=? where id = ?",
				changeAlarmNum, new Date(), id);

	}

	public void changeReserveNumAndReserveNum(Integer id, Integer changeValue) {
		String upsateSql = "update boss_flow_pool set reserveNum =reserveNum{0},poolRemain = poolRemain{1},lastUpdateTime={3} where id = {2}";

		String reserveNumSql = changeValue >= 0 ? ("+" + changeValue) : ("" + changeValue);

		String poolRemainSql = changeValue >= 0 ? ("-" + changeValue) : ("+" + (changeValue * (-1)));

		upsateSql = MessageFormat.format(upsateSql, reserveNumSql, poolRemainSql, id, "'" + DateUtils.formatDateTime(new Date()) + "'"); // 字符串替换

		flowPoolInfoDao.saveOrUpdate(upsateSql);

	}

	public void changeStatus(Integer id, Integer status) {
		flowPoolInfoDao.saveOrUpdateByHql("update FlowPoolInfo set status = ? where id = ?", status, id);
	}
}
