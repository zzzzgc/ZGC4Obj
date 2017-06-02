package com.xinxing.transfer.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xinxing.transfer.dao.BossCustomerProductMapper;
import com.xinxing.transfer.po.BossCustomerProduct;
import com.xinxing.transfer.po.BossCustomerProductExample;
import com.xinxing.transfer.po.BossCustomerProductExample.Criteria;
import com.xinxing.transfer.service.BossCustomerProductService;

@Service
public class BossCustomerProductServiceImpl implements BossCustomerProductService {
	@Autowired
	BossCustomerProductMapper bossCustomerProductMapper;

	@Override
	public String getDockingProductId(Integer Customerid, Integer Categoryid) {
		BossCustomerProductExample bossCustomerProductExample = new BossCustomerProductExample();
		@SuppressWarnings("unused")
		Criteria criteria = bossCustomerProductExample
				.createCriteria()
				.andCustomeridEqualTo(Customerid)
				.andCategoryidEqualTo(Categoryid);
		List<BossCustomerProduct> selectByExample = bossCustomerProductMapper
				.selectByExample(bossCustomerProductExample);
		if (selectByExample != null && selectByExample.size() > 0) {
			return selectByExample.get(0).getId() + "";
		}
		return null;
	}

}
