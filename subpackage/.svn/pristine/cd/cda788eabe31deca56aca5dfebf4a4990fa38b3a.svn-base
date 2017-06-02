package com.xinxing.subpackage.data.dao;

import com.xinxing.subpackage.data.po.SubpackageOrderApi;
import com.xinxing.subpackage.data.po.SubpackageOrderApiExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SubpackageOrderApiMapper {
    int countByExample(SubpackageOrderApiExample example);

    int deleteByExample(SubpackageOrderApiExample example);

    int deleteByPrimaryKey(String orderid);

    int insert(SubpackageOrderApi record);

    int insertSelective(SubpackageOrderApi record);

    List<SubpackageOrderApi> selectByExample(SubpackageOrderApiExample example);

    SubpackageOrderApi selectByPrimaryKey(String orderid);

    int updateByExampleSelective(@Param("record") SubpackageOrderApi record, @Param("example") SubpackageOrderApiExample example);

    int updateByExample(@Param("record") SubpackageOrderApi record, @Param("example") SubpackageOrderApiExample example);

    int updateByPrimaryKeySelective(SubpackageOrderApi record);

    int updateByPrimaryKey(SubpackageOrderApi record);
}