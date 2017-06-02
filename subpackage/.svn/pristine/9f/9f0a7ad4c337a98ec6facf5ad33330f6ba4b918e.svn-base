package com.xinxing.subpackage.data.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xinxing.subpackage.core.erorr.DIYException;
import com.xinxing.subpackage.core.erorr.NULLOrderIdException;
import com.xinxing.subpackage.core.erorr.RepeatOrdersException;
import com.xinxing.subpackage.core.po.PackInfo;
import com.xinxing.subpackage.data.dao.SubpackageOrderApiMapper;
import com.xinxing.subpackage.data.dao.SubpackageOrderSendMapper;
import com.xinxing.subpackage.data.po.SubpackageOrderApi;
import com.xinxing.subpackage.data.po.SubpackageOrderSend;
import com.xinxing.subpackage.data.po.SubpackageOrderSendExample;
import com.xinxing.subpackage.data.po.SubpackageOrderSendKey;
import com.xinxing.subpackage.data.service.PackOrderService;

@Service
public class PackOrderServiceImpl implements PackOrderService {

	@Autowired
	SubpackageOrderSendMapper subpackageOrderSendMapper;

	@Autowired
	SubpackageOrderApiMapper subpackageOrderApiMapper;
	
	@Override
	public PackInfo nextPack(String orderId,String receivedSendOrderId) throws RepeatOrdersException, NULLOrderIdException, DIYException {
		PackInfo info = new PackInfo();
		SubpackageOrderApi api = subpackageOrderApiMapper.selectByPrimaryKey(orderId);
		if (api != null) {
			/*if (api.getStatus() != null && 2 == api.getStatus()) {
				// TODO 校验 是否是重复订单 测试备忘
				throw new RepeatOrdersException();
			}*/
			// 所有分包的产品Id   [0,1,2,3,4]
			String[] Product = api.getProductinfo().split(",");
			// 分包总数
			int num = api.getPacknum();
			// 即将要充值的包('1',2,3,4,5)
			Integer whichpack = api.getWhichpack() + 1;
			
			if (receivedSendOrderId!=null) {
				//是否存在重复提单(由于回调和查询都起效时候会重复提交,而不是迭代充值成功后才提单)
				if (Integer.parseInt(receivedSendOrderId.split("F")[1]) == whichpack - 1) {
					if (num >= whichpack) {
						// WHICHPACK='1',2,3,4,5
						String packId = orderId + "F" + whichpack;
						SubpackageOrderSend packOrder = subpackageOrderSendMapper.selectByPrimaryKey(new SubpackageOrderSendKey(orderId, packId));
						if (packOrder == null) {
							info.setPackId(packId);
							// (Product[0,1,2,3,4])
							info.setPackProductId(Product[whichpack - 1]);
							return info;
						} else {
							throw new DIYException(4, "重复提交相同的分包订单");
						}
					}
				} else {
					throw new DIYException(4, "重复处理相同分包订单状态for 例如:回调(F1)  查询(F1)  提单(F2&F3) 现在是'F3'包在提单 如今 跳出并记录");
				} 
			}/*else{//第一次提单的时候,但是我们排除了.
				info.setPackId(orderId+"F"+1);
				info.setPackProductId(Product[0]);
				return info;
			}*/
		} else {
			throw new NULLOrderIdException();
		}
		return null;
	}
	
	@Override
	public int isWhichPack(String orderId, String sendOrderId) {
		SubpackageOrderSendExample send = new SubpackageOrderSendExample();
		send.createCriteria().andOrderidEqualTo(orderId).andSendorderidEqualTo(sendOrderId);
		List<SubpackageOrderSend> list = subpackageOrderSendMapper.selectByExample(send);
		if (list.size() > 0) {
			return list.get(0).getWhichpack();
		}
		return 0;
	}

	@Override
	public boolean addPackOrderInfo(SubpackageOrderSend PackOrderInfo) {
		//添加原包记录充值分包数+1
		String orderid = PackOrderInfo.getOrderid();
		SubpackageOrderApi api = subpackageOrderApiMapper.selectByPrimaryKey(orderid);
		api.setWhichpack(api.getWhichpack() + 1);
		int isOk = subpackageOrderApiMapper.updateByPrimaryKeySelective(api);

		int insert = subpackageOrderSendMapper.insert(PackOrderInfo);
		if (insert > 0 && isOk > 0) {
			return true;
		}
		return false;
	}

	@Override
	public String getpackId(String orderId) throws NULLOrderIdException {
		List<SubpackageOrderSend> allPack = getAllPack(orderId);
		if (allPack!=null&&allPack.size()>0) {
			return allPack.get(allPack.size()-1).getSendorderid();
		}
		throw new NULLOrderIdException();
	}

	@Override
	public List<SubpackageOrderSend> getAllPack(String orderId) {
		SubpackageOrderSend ById = new SubpackageOrderSend();
		ById.setOrderid(orderId);
		SubpackageOrderSendExample example = new SubpackageOrderSendExample();
		example.createCriteria().andOrderidEqualTo(orderId);
		List<SubpackageOrderSend> selectByExample = subpackageOrderSendMapper.selectByExample(example);
		return selectByExample;
	}

	@Override
	public boolean updateCallbackOrderInfo(String sendOrderId, String status) {
		//TODO 
		SubpackageOrderSend pack = new SubpackageOrderSend();
		String orderId = sendOrderId.split("F")[0];
		pack.setOrderid(orderId);
		pack.setSendorderid(sendOrderId);
		pack.setStatus(Integer.parseInt(status));
		pack.setEndtime(new Date());
		
		int updateByPrimaryKey = subpackageOrderSendMapper.updateByPrimaryKeySelective(pack);
		if (updateByPrimaryKey>0) {
			return true;
		}
		return false;
		
	}

	@Override
	public boolean updatePackOrderInfo(SubpackageOrderSend PackOrderInfo) {
		int is = subpackageOrderSendMapper.updateByPrimaryKeySelective(PackOrderInfo);
		if (is>0) {
			return true;
		}
		return false;
	}

	@Override
	public SubpackageOrderSend getSubpackageOrder(String sendOrderId) {
		SubpackageOrderSendExample example = new SubpackageOrderSendExample();
		example.createCriteria().andSendorderidEqualTo(sendOrderId);
		
		List<SubpackageOrderSend> list = subpackageOrderSendMapper.selectByExample(example);
		
		if (list!=null&&list.size()>0) {
			return list.get(0);
		}
		return null;
	}
}
