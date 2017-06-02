package com.xinxing.transfer.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xinxing.transfer.dao.BossCustomerBalanceRecordMapper;
import com.xinxing.transfer.po.BossCustomerBalanceRecord;
import com.xinxing.transfer.po.BossCustomerBalanceRecordExample;
import com.xinxing.transfer.service.BossCustomerBalanceRecordService;
@Service
public class BossCustomerBalanceRecordServiceImpl implements BossCustomerBalanceRecordService {

	@Autowired
	BossCustomerBalanceRecordMapper bossCustomerBalanceRecordMapper;
	
	@Override
	public List<BossCustomerBalanceRecord> getRecord(int id,Date startTime,Date endTime) {
		BossCustomerBalanceRecordExample bossCustomerBalanceRecordExample = new BossCustomerBalanceRecordExample();
		bossCustomerBalanceRecordExample.createCriteria().andCustomeridEqualTo(id).andRecordtimeBetween(startTime, endTime);
		return bossCustomerBalanceRecordMapper.selectByExample(bossCustomerBalanceRecordExample);
	}
	@Override
	public List<BossCustomerBalanceRecord> findOneGT(int id, String orderby, int pageStart, int pageEnd, Date time) {
		BossCustomerBalanceRecordExample bossCustomerBalanceRecordExample = new BossCustomerBalanceRecordExample();
		bossCustomerBalanceRecordExample.setOrderByClause(orderby);
		bossCustomerBalanceRecordExample.setPageStart(pageStart);
		bossCustomerBalanceRecordExample.setPageEnd(pageEnd);
		bossCustomerBalanceRecordExample.createCriteria().andCustomeridEqualTo(id).andRecordtimeGreaterThanOrEqualTo(time);
		return bossCustomerBalanceRecordMapper.selectByExample(bossCustomerBalanceRecordExample);
	}
	@Override
	public List<BossCustomerBalanceRecord> findOneLT(int id, String orderby, int pageStart, int pageEnd, Date time) {
		BossCustomerBalanceRecordExample bossCustomerBalanceRecordExample = new BossCustomerBalanceRecordExample();
		bossCustomerBalanceRecordExample.setOrderByClause(orderby);
		bossCustomerBalanceRecordExample.setPageStart(pageStart);
		bossCustomerBalanceRecordExample.setPageEnd(pageEnd);
		bossCustomerBalanceRecordExample.createCriteria().andCustomeridEqualTo(id).andRecordtimeLessThanOrEqualTo(time);
		return bossCustomerBalanceRecordMapper.selectByExample(bossCustomerBalanceRecordExample);
	}

}
