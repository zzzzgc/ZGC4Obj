package com.xinxing.transfer.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xinxing.transfer.dao.BossOrderMapper;
import com.xinxing.transfer.po.BossOrder;
import com.xinxing.transfer.po.BossOrderExample;
import com.xinxing.transfer.service.BossOrderserService;

@Service
public class BossOrderserServiceImpl implements BossOrderserService{
	
	@Autowired
	BossOrderMapper bossOrderMapper;

	@Override
	public BossOrder selectOrder(String downid, String key) {
		if ("DOWNID".equals(downid)) {
			BossOrderExample bossOrderExample = new BossOrderExample();
			bossOrderExample.createCriteria().andOrderkeyEqualTo(key);
			List<BossOrder> selectByExample = bossOrderMapper.selectByExample(bossOrderExample);
			return selectByExample.get(0);
		}else{
			BossOrderExample bossOrderExample = new BossOrderExample();
			bossOrderExample.createCriteria().andProviderkeyEqualTo(key);
			List<BossOrder> selectByExample = bossOrderMapper.selectByExample(bossOrderExample);
			return selectByExample.get(0);
		}
	}
	
}
