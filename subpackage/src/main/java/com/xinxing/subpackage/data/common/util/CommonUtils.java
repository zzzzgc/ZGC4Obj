package com.xinxing.subpackage.data.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.xinxing.boss.business.api.domain.SendOrderInfo;
import com.xinxing.boss.interaction.pojo.customer.OrderInfo;

public class CommonUtils {
	
	/**
	 * 获取查询中打日志时参数为list的情况，获取里面的id来打印，简洁日志
	 * @return
	 */
	public static String getLogParamListId(List<SendOrderInfo> list){
		List<String> nList=new ArrayList<>();
		if(list!=null&&list.size()>0){
			for (SendOrderInfo order : list) {
				nList.add(order.getOrderId());
			}
		}
		return nList.toString();
	}
	
	/**
	 * 获取查询中打日志时参数为list的情况，获取里面的id来打印，简洁日志
	 * @return
	 */
	public static String getLogParamList(List<OrderInfo> list){
		List<Integer> nList=new ArrayList<>();
		if(list!=null&&list.size()>0){
			for (OrderInfo order : list) {
				nList.add(order.getId());
			}
		}
		return nList.toString();
	}
	
	/**
	 * 排序成 key=value
	 * @param map
	 * @return
	 */
	public static String sort(Map<String,String> map){
		StringBuffer sb=new StringBuffer();
		for (Map.Entry<String, String> entry : map.entrySet()) {
			sb.append("&"+entry.getKey()+"="+entry.getValue());
		}
		return sb.toString().substring(1);
	}
	
}	
