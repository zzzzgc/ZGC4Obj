package xinxing.boss.admin.common.msg;

import org.apache.log4j.Logger;

import xinxing.boss.admin.common.utils.constants.Constants;
import xinxing.boss.admin.common.utils.http.HttpUtils;
import xinxing.boss.admin.common.utils.security.MD5_HexUtil;
import xinxing.boss.admin.common.utils.time.DateUtils;

public class MsgUtils {
	private static final Logger log = Logger.getLogger(MsgUtils.class);

	public static String sendMsg(String phone, int count) {
		String sid = "585232b0d956ddf175e40c1919e11355";
		String appId = "9a6994598e8e49d5a455ebf711e8ce8e";
		String to = phone;
		String time = DateUtils.getDateRandom();
		String templateId = "20056";// 天猫直充警报
		String sign = MD5_HexUtil.md5Hex(sid + time + "4738834cb3c22fe26ce55042a2c22ee6").toLowerCase();
		String url = "http://www.ucpaas.com/maap/sms/code";
		String result = HttpUtils.sendGet(url + "?sid=" + sid + "&appId=" + appId + "&time=" + time + "&sign=" + sign + "&to=" + to + "&templateId="
				+ templateId + "&param=" + count, "UTF-8");
		return result;
	}

	/**
	 * 发送报警短信
	 * 
	 * @param phone
	 * @param supplier
	 * @param count
	 */
	public static void sendAlarmMsg(String phone, String supplier, int count) {
		String bossTag = "[" + Constants.getString("sys_boss_name") + "]";
		String res = sendYunZhiXunMessage(phone, "26479", bossTag + "," + supplier + "," + count);
		log.info("-云之讯报警返回-phone=" + phone + "res=" + res);
	}

	/**
	 * 云之讯短信平台
	 * 
	 * @param phone
	 *            发送手机号
	 * @param templateId
	 *            模板ID
	 * @param param
	 *            参数信息
	 * @return
	 */
	public static String sendYunZhiXunMessage(String phone, String templateId, String param) {
		String sid = "585232b0d956ddf175e40c1919e11355";
		String appId = "9a6994598e8e49d5a455ebf711e8ce8e";
		String token = "4738834cb3c22fe26ce55042a2c22ee6";
		String to = phone;
		String time = DateUtils.getNowTimeStamp();
		String sign = MD5_HexUtil.md5Hex(sid + time + token).toLowerCase();
		String url = "http://www.ucpaas.com/maap/sms/code";
		String result = HttpUtils.sendGet(url + "?sid=" + sid + "&appId=" + appId + "&time=" + time + "&sign=" + sign + "&to=" + to + "&templateId="
				+ templateId + "&param=" + param, "UTF-8");
		return result;
	}

}
