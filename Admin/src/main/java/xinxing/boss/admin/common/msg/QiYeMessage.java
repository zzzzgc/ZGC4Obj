package xinxing.boss.admin.common.msg;

import xinxing.boss.admin.common.utils.http.HttpUtils;

public class QiYeMessage {

	public static String QIYE_CONTINUE_FAIL = "【%s】系统，供货商%s的产品已经连续失败%s笔，请登录后台管理系统处理";
	
	public static String QIYE_SYSTEM_PILED_ORDER = "【%s】系统，供货商%s卡单%s笔。新增%s笔，充值失败%s笔，等待确认%s笔，手工处理%s笔，请即时处理";
	
	public static String QIYE_TMALL_PILED_ORDER = "【%s】系统，天猫直充已经卡单超过%s笔，请登录后台处理";
	
	public static String QIYE_MINUS_PROFIT_ORDER = "【%s】系统，采购商%s销售价格低于供货商%s成本价格，请及时处理。";
	
	
	/**
	 * 获取连续失败信息
	 * @param boss
	 * @param provider
	 * @param failCnt
	 * @return
	 */
	public static String getContinueFailMessage(String boss,String provider,int failCnt){
		return String.format(QIYE_CONTINUE_FAIL, boss,provider,failCnt);
	}
	
	/**
	 * 系统订单状态信息
	 * @param boss
	 * @param provider
	 * @param failCnt
	 * @return
	 */
	public static String getSystemPiledMessage(String boss,String provider,int totalCnt,int newCnt,int failCnt,int waitCnt,int handCnt){
		return String.format(QIYE_SYSTEM_PILED_ORDER, boss,provider,totalCnt,newCnt,failCnt,waitCnt,handCnt);
	}
	
	/**
	 * 天猫卡单信息
	 * @param boss
	 * @param pipledCnt
	 * @return
	 */
	public static String getTmallPiledMessage(String boss,int pipledCnt){
		return String.format(QIYE_TMALL_PILED_ORDER, boss, pipledCnt);
	}
	
	/**
	 * 发送负利润警报
	 * @param boss
	 * @param customer
	 * @param provider
	 * @return
	 */
	public static String getMinusProfitMessage(String boss,String customer,String provider){
		return String.format(QIYE_MINUS_PROFIT_ORDER, boss, customer,provider);
	}
	
	/**
	 * 发送信息
	 * @param content
	 * @param user
	 */
	public static void sendQiYeMessage(String content,String user){
		String msg = "{\"user\":\""+user+"\",\"content\":\""+content+"\"}";
		HttpUtils.sendPost("http://message.llczxt.com/call.do?cmd=qiyemessage", msg, "application/json");
	}
	
	/*public static void main(String[] args) {
		sendQiYeMessage("测试", "seventeen_cm");
	}*/
}
