package com.xinxing.subpackage.core.halt;

import org.apache.log4j.Logger;

import com.xinxing.subpackage.data.common.resource.Constants;

/**
 * 分包业务叫停模块
 * @author ZGC
 */
public class Halt {
	//TODO
	private static Logger log=Logger.getLogger("Pack Halt");
	//连续三次失败
	private static int continuous=0;
	//是否叫停
	//private static boolean isHalt=true;
	//一天有10次失败
	private static int count=0;
	private static int erorr=0;
	private static boolean halt=false;
	
	private static int countNum=Constants.getInteger("pack_cont");
	private static int continuousNum=Constants.getInteger("pack_continuous");
	private static int erorrNum=Constants.getInteger("pack_erorr");
	
	
	/**
	 * 判断是否叫停
	 * @return 是否叫停
	 */
	public static boolean isCanSend(){
		return halt;
	}
	
	/**
	 * 除了第一单以外的订单状态修改
	 * @param status 订单状态
	 */
	public static void addStatus(String sendOrderId,Integer status){
		switch (status) {
			case 11 :
				continuous=0;
				break;
			case 2 :
				count++;
				continuous++;
				break;
			default :
				erorr++;
				break;
		}
		log.info("添加"+sendOrderId+"订单状态:"+status+"到叫停模块,现在 持续多次失败叫停计数为:"+continuous+"次,一天多次失败叫停叫停为:"+count+"次.");
		if (count<countNum&&continuous<continuousNum&&erorr<erorrNum) {
			halt=false;
		}else{
			halt=true;
		}
	}
	
	//定时每日清零
	public void pastDay(Integer status){
		count=0;
	}
}
	
