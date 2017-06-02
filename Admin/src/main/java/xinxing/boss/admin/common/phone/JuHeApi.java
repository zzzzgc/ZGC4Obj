package xinxing.boss.admin.common.phone;

import org.apache.commons.lang.StringUtils;

import xinxing.boss.admin.common.utils.http.HttpUtils;
import xinxing.boss.admin.common.utils.json.JsonUtils;

public class JuHeApi {

	/**
	 * 从聚合API中获取手机号段信息
	 * TODO 需要增加另外一个号段API信息
	 * 
	 * @param phone
	 * @return
	 */
	public static PhoneInfo_Juhe getJuhe_PhoneInfo(String phone) {
		String url = "http://apis.juhe.cn/mobile/get?phone=" + phone + "&key=f646d25eaf77a89be74190dbfe650644";
		String result = HttpUtils.sendGet(url, "UTF-8");
		PhoneInfo_Juhe info = JsonUtils.json2Obj(result, PhoneInfo_Juhe.class);
		return info;
	}


}
