package com.xinxing.transfer.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.xinxing.transfer.po.BossCustomerProduct;
import com.xinxing.transfer.po.BossCustomerProductExample;

public interface BossCustomerProductMapper {
    int countByExample(BossCustomerProductExample example);

    int deleteByExample(BossCustomerProductExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BossCustomerProduct record);

    int insertSelective(BossCustomerProduct record);

    List<BossCustomerProduct> selectByExample(BossCustomerProductExample example);

    BossCustomerProduct selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BossCustomerProduct record, @Param("example") BossCustomerProductExample example);

    int updateByExample(@Param("record") BossCustomerProduct record, @Param("example") BossCustomerProductExample example);

    int updateByPrimaryKeySelective(BossCustomerProduct record);

    int updateByPrimaryKey(BossCustomerProduct record);
}