package com.xinxing.transfer.service;

import java.util.List;

import com.xinxing.transfer.po.BossProductCategory;

public interface BossProductCategoryService {
	
	/**
	 * 获取匹配的产品分类id(产品id计算器)
	 * @param area  下游漫游类型  0 省内漫游 , 1 全国漫游
	 * @param Productnum   流量值  
	 * @param Productunit  流量类型   1按M算   2按G算
	 * @param Operator  下游运营商 YD 移动 , DX 电信 , LT 联通
	 * @param urprovid  省份   全国  广东  北京  上海   杭州  
	 * @return 匹配的产品分类id
	 */
	public List<BossProductCategory> getMatchingProducts(Integer area,Integer Productnum,Integer Productunit ,String Operator,String urprovid);

	/**
	 * 获取产品分类对象
	 * @param categoryid   产品分类id
	 * @return 产品分类对象
	 */
	public BossProductCategory getProduct(String categoryid);
	
	
	
}
