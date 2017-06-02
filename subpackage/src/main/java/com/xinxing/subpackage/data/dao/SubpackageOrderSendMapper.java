package com.xinxing.subpackage.data.dao;

import com.xinxing.subpackage.data.po.SubpackageOrderSend;
import com.xinxing.subpackage.data.po.SubpackageOrderSendExample;
import com.xinxing.subpackage.data.po.SubpackageOrderSendKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SubpackageOrderSendMapper {
    int countByExample(SubpackageOrderSendExample example);

    int deleteByExample(SubpackageOrderSendExample example);

    int deleteByPrimaryKey(SubpackageOrderSendKey key);

    int insert(SubpackageOrderSend record);

    int insertSelective(SubpackageOrderSend record);

    List<SubpackageOrderSend> selectByExample(SubpackageOrderSendExample example);

    SubpackageOrderSend selectByPrimaryKey(SubpackageOrderSendKey key);

    int updateByExampleSelective(@Param("record") SubpackageOrderSend record, @Param("example") SubpackageOrderSendExample example);

    int updateByExample(@Param("record") SubpackageOrderSend record, @Param("example") SubpackageOrderSendExample example);

    int updateByPrimaryKeySelective(SubpackageOrderSend record);

    int updateByPrimaryKey(SubpackageOrderSend record);
}