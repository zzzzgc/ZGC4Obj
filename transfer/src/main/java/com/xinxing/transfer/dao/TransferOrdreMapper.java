package com.xinxing.transfer.dao;

import com.xinxing.transfer.po.TransferOrdre;
import com.xinxing.transfer.po.TransferOrdreExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TransferOrdreMapper {
    int countByExample(TransferOrdreExample example);

    int deleteByExample(TransferOrdreExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TransferOrdre record);

    int insertSelective(TransferOrdre record);

    List<TransferOrdre> selectByExample(TransferOrdreExample example);

    TransferOrdre selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TransferOrdre record, @Param("example") TransferOrdreExample example);

    int updateByExample(@Param("record") TransferOrdre record, @Param("example") TransferOrdreExample example);

    int updateByPrimaryKeySelective(TransferOrdre record);

    int updateByPrimaryKey(TransferOrdre record);
}