package com.xinxing.transfer.dao;

import com.xinxing.transfer.po.BossOrder;
import com.xinxing.transfer.po.BossOrderExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BossOrderMapper {
    int countByExample(BossOrderExample example);

    int deleteByExample(BossOrderExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BossOrder record);

    int insertSelective(BossOrder record);

    List<BossOrder> selectByExample(BossOrderExample example);

    BossOrder selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BossOrder record, @Param("example") BossOrderExample example);

    int updateByExample(@Param("record") BossOrder record, @Param("example") BossOrderExample example);

    int updateByPrimaryKeySelective(BossOrder record);

    int updateByPrimaryKey(BossOrder record);
}