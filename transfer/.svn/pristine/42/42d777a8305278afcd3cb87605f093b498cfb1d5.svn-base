package com.xinxing.transfer.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.xinxing.transfer.po.BossProductCategory;
import com.xinxing.transfer.po.BossProductCategoryExample;

public interface BossProductCategoryMapper {
	
	
    int countByExample(BossProductCategoryExample example);

    int deleteByExample(BossProductCategoryExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BossProductCategory record);

    int insertSelective(BossProductCategory record);

    List<BossProductCategory> selectByExample(BossProductCategoryExample example);

    BossProductCategory selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BossProductCategory record, @Param("example") BossProductCategoryExample example);

    int updateByExample(@Param("record") BossProductCategory record, @Param("example") BossProductCategoryExample example);

    int updateByPrimaryKeySelective(BossProductCategory record);

    int updateByPrimaryKey(BossProductCategory record);
}