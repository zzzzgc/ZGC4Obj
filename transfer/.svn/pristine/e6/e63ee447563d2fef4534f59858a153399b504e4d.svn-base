package com.xinxing.transfer.service;

import java.util.Date;
import java.util.List;

import com.xinxing.transfer.po.BossCustomerBalanceRecord;

public interface BossCustomerBalanceRecordService {

	/**
	 * 根据时间获取采购商账户流水信息
	 * @param id
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	List<BossCustomerBalanceRecord> getRecord(int id, Date startTime, Date endTime);

	List<BossCustomerBalanceRecord> findOneGT(int id, String orderby, int pageStart, int pageEnd,
			Date time);

	List<BossCustomerBalanceRecord> findOneLT(int id, String orderby, int pageStart, int pageEnd,
			Date time);
	

}
