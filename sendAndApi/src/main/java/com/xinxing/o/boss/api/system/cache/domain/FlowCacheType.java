package com.xinxing.o.boss.api.system.cache.domain;

import org.apache.commons.lang.StringUtils;

public enum FlowCacheType {

	/**
	 * 重新加载产品目录
	 */
	CATEGORY("init_category"),
	/**
	 * 重新加载相同的供应商,用与报警
	 */
	BOSS_PROVIDER_SAME("bossProviderSame"),
	/**
	 * 重新加载供货商
	 */
	PROVIDER("init_provider"),
	/**
	 * 重新加载黑名单
	 */
	BLACK_PHONE("init_backphone"),
	/**
	 * 重新加载运营商开关
	 */
	OPERATOR_GATE("init_operator_gate"),
	
	/**
	 * 运营商地市维护信息配置
	 */
	OPERATOR_CLOSE_CONFIG("init_operator_close_config"),
	
	/**
	 * 初始化配置
	 */
	BOSS_CONFIG("init_boss_config"),
	
	/**
	 * 初始化正常失败原因
	 */
	NORMAL_FAIL("init_fail_reason"),
	/**
	 * 关闭移动发送订单
	 */
	STOP_SEND_MOBILE("stop_send_mobile"),
	/**
	 * 关闭联通发送订单
	 */
	STOP_SEND_UNICOM("stop_send_unicom"),
	/**
	 * 关闭电信发送订单
	 */
	STOP_SEND_TELECOM("stop_send_telecom"),
	/**
	 * 开通移动发送订单
	 */
	START_SEND_MOBILE("start_send_mobile"),
	/**
	 * 开通联通发送订单
	 */
	START_SEND_UNICOM("start_send_unicom"),
	/**
	 * 开通电信发送订单
	 */
	START_SEND_TELECOM("start_send_telecom"),
	
	/**
	 * 获取队列状态
	 */
	QUEUE_STATE("get_queue_state"),
	
	
	;
	
	public String type;

	private FlowCacheType(String type) {
		this.type = type;
	}
	
	/**
	 * 根据传入字符串获取类型
	 * @param type
	 * @return
	 */
	public static FlowCacheType getFlowCacheType(String type){
		for(FlowCacheType flowCacheType : FlowCacheType.values()){
			if(StringUtils.equals(flowCacheType.type, type)){
				return flowCacheType;
			}
		}
		return null;
	}
	
}
