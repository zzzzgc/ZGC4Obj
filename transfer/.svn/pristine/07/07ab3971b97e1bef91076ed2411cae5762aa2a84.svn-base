package com.xinxing.transfer.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xinxing.transfer.dao.BossProductCategoryMapper;
import com.xinxing.transfer.po.BossProductCategory;
import com.xinxing.transfer.po.BossProductCategoryExample;
import com.xinxing.transfer.po.BossProductCategoryExample.Criteria;
import com.xinxing.transfer.service.BossProductCategoryService;
@Service
public class BossProductCategoryServiceImpl implements BossProductCategoryService {
	
	@Autowired
	BossProductCategoryMapper bossProductCategoryMapper;

	@Override
	public List<BossProductCategory> getMatchingProducts(Integer area, Integer Productnum, Integer Productunit,
			String Operator,String urprovid) {
		BossProductCategoryExample example = new BossProductCategoryExample();
		Criteria Criteria = example.createCriteria();
		Criteria.andAreaEqualTo(area);
		Criteria.andProductnumEqualTo(Productnum);
		Criteria.andProductunitEqualTo(Productunit);
		Criteria.andOperatorEqualTo(Operator);
		Criteria.andProvinceEqualTo(urprovid);
		return bossProductCategoryMapper.selectByExample(example);
	}

	@Override
	public BossProductCategory getProduct(String categoryid) {
		return bossProductCategoryMapper.selectByPrimaryKey(Integer.parseInt(categoryid));
	}

}
