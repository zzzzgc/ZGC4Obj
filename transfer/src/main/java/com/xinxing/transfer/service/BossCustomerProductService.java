package com.xinxing.transfer.service;

public interface BossCustomerProductService {
	/**
	 * 校验是否是可用产品
	 * @param Customerid  商户id
	 * @param Productid  分类id
	 * @return 对接产品id
	 */
	public String getDockingProductId(Integer Customerid,Integer Productid);
	

}
