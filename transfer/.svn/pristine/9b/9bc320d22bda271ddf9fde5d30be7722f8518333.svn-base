package com.xinxing.transfer.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xinxing.transfer.dao.TransferOrdreMapper;
import com.xinxing.transfer.po.TransferOrdre;
import com.xinxing.transfer.po.TransferOrdreExample;
import com.xinxing.transfer.service.TransferOrdreService;
@Service
public class TransferOrdreServiceImpl implements TransferOrdreService {

	@Autowired
	TransferOrdreMapper transferOrdreMapper;

	@Override
	public TransferOrdre getOrderInfo(String who, String id) {
		List<TransferOrdre> selectByExample = null;
		if ("DOWNID".equals(who)) {
			TransferOrdreExample example = new TransferOrdreExample();
			example.createCriteria().andDownidEqualTo(id);
			selectByExample = transferOrdreMapper.selectByExample(example);
		}else if ("SUPPLIERID".equals(who)){
			TransferOrdreExample example = new TransferOrdreExample();
			example.createCriteria().andSupplieridEqualTo(id);
			selectByExample = transferOrdreMapper.selectByExample(example);
		}else{
			return null;
		}
		
		if(selectByExample!=null&&selectByExample.size()>0){
			return selectByExample.get(0);
		}
		return null;
	}

	@Override
	public void addOredr(TransferOrdre transferOrdre) {
		transferOrdreMapper.insert(transferOrdre);
	}

	@Override
	public boolean updateOrder(String who,TransferOrdre transferOrdre) {
		String id =null;
		TransferOrdreExample example=null;
		if ("DOWNID".equals(who)) {
			id=transferOrdre.getDownid();
			example = new TransferOrdreExample();
			example.createCriteria().andDownidEqualTo(id);
		}else if ("SUPPLIERID".equals(who)){
			example = new TransferOrdreExample();
			example.createCriteria().andSupplieridEqualTo(id);
		}else{
			return false;
		}
		int updateByExampleSelective = transferOrdreMapper.updateByExampleSelective(transferOrdre, example);
		if (updateByExampleSelective!=0) {
			return true;
		}
		return false;
	}

}
