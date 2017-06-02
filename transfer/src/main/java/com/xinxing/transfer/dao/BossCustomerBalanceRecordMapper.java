package com.xinxing.transfer.dao;

import com.xinxing.transfer.po.BossCustomerBalanceRecord;
import com.xinxing.transfer.po.BossCustomerBalanceRecordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BossCustomerBalanceRecordMapper {
    int countByExample(BossCustomerBalanceRecordExample example);

    int deleteByExample(BossCustomerBalanceRecordExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BossCustomerBalanceRecord record);

    int insertSelective(BossCustomerBalanceRecord record);

    List<BossCustomerBalanceRecord> selectByExample(BossCustomerBalanceRecordExample example);

    BossCustomerBalanceRecord selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BossCustomerBalanceRecord record, @Param("example") BossCustomerBalanceRecordExample example);

    int updateByExample(@Param("record") BossCustomerBalanceRecord record, @Param("example") BossCustomerBalanceRecordExample example);

    int updateByPrimaryKeySelective(BossCustomerBalanceRecord record);

    int updateByPrimaryKey(BossCustomerBalanceRecord record);
}