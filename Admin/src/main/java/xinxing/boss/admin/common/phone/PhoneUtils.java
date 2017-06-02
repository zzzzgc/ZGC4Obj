package xinxing.boss.admin.common.phone;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import xinxing.boss.admin.common.utils.json.JsonUtils;

public class PhoneUtils {
	public static Logger log = Logger.getLogger(PhoneUtils.class);
	public static String getOperator(String order_phone) {
		String operator = "";
		String phone7str = "";
		if (order_phone != null && order_phone.length() >= 7) {
			phone7str = order_phone.substring(0, 7);
		} else {
			throw new RuntimeException("手机号异常:" + order_phone);
		}
		PhoneInfo_Juhe juhe = JuHeApi.getJuhe_PhoneInfo(phone7str);
		if (juhe != null && StringUtils.equals(juhe.getResultcode(), "200")) {
			PhoneInfo_Result_Juhe result = juhe.getResult();
			log.info(JsonUtils.obj2Json(result));
			operator = result.getCompany();
			// String city = result.getCity();
			// String province = result.getProvince();
		}
		if (StringUtils.isBlank(operator)) {
			throw new RuntimeException("运营商为空" + order_phone);
		}
		return operator;
	}

	public static String shanghai_dx = "18930915391".trim();
	public static String shanghai_yd = "15102185442".trim();
	public static String shanghai_lt = "13062658756".trim();
	public static String yunnan_dx = "18087490922".trim();
	public static String yunnan_yd = "15812137930".trim();
	public static String yunnan_lt = "18687281541".trim();
	public static String beijing_dx = "15321883673".trim();
	public static String beijing_yd = "15910880351".trim();
	public static String beijing_lt = "18612270443".trim();
	public static String jilin_dx = "13331687175".trim();
	public static String jilin_yd = "13596339097".trim();
	public static String jilin_lt = "18604439077".trim();
	public static String sichuan_dx = "18989172824".trim();
	public static String sichuan_yd = "18782184630".trim();
	public static String sichuan_lt = "15520299889".trim();
	public static String tianjin_dx = "15320110720".trim();
	public static String tianjin_yd = "13622116277".trim();
	public static String tianjin_lt = "13114971636".trim();
	public static String ningxia_dx = "13323546619".trim();
	public static String ningxia_yd = "18209504228".trim();
	public static String ningxia_lt = "15595002205".trim();
	public static String anhui_dx = "17756674397".trim();
	public static String anhui_yd = "13965621656".trim();
	public static String anhui_lt = "15555545845".trim();
	public static String shandong_dx = "15376036560".trim();
	public static String shandong_yd = "15863791812".trim();
	public static String shandong_lt = "13053494151".trim();
	public static String shan1xi_dx = "13383405106".trim();
	public static String shan1xi_yd = "15234127672".trim();
	public static String shan1xi_lt = "18636516636".trim();
	public static String guangdong_dx = "18922219320".trim();
	public static String guangdong_yd = "13692362148".trim();
	public static String guangdong_lt = "15622347260".trim();
	public static String guangxi_dx = "18907886141".trim();
	public static String guangxi_yd = "18776904309".trim();
	public static String guangxi_lt = "18589924034".trim();
	public static String xinjiang_dx = "18999029338".trim();
	public static String xinjiang_yd = "18799760140".trim();
	public static String xinjiang_lt = "13201235423".trim();
	public static String jiangsu_dx = "18921562156".trim();
	public static String jiangsu_yd = "18706184509".trim();
	public static String jiangsu_lt = "13179669608".trim();
	public static String jiangxi_dx = "18174001212".trim();
	public static String jiangxi_yd = "13507094626".trim();
	public static String jiangxi_lt = "13263927809".trim();
	public static String hebei_dx = "15369308170".trim();
	public static String hebei_yd = "18831318262".trim();
	public static String hebei_lt = "15633201889".trim();
	public static String henan_dx = "13333691987".trim();
	public static String henan_yd = "18703866800".trim();
	public static String henan_lt = "13233860298".trim();
	public static String zhejiang_dx = "17767162095".trim();
	public static String zhejiang_yd = "18367882291".trim();
	public static String zhejiang_lt = "18658521030".trim();
	public static String hainan_dx = "18976768557".trim();
	public static String hainan_yd = "13637667296".trim();
	public static String hainan_lt = "13086013748".trim();
	public static String hubei_dx = "18086539908".trim();
	public static String hubei_yd = "15272815764".trim();
	public static String hubei_lt = "18571595952".trim();
	public static String hunan_dx = "18075127335".trim();
	public static String hunan_yd = "18890686989".trim();
	public static String hunan_lt = "15607381289".trim();
	public static String gansu_dx = "15379703012".trim();
	public static String gansu_yd = "13830377240".trim();
	public static String gansu_lt = "15593898365".trim();
	public static String fujian_dx = "18016787000".trim();
	public static String fujian_yd = "13959273092".trim();
	public static String fujian_lt = "18650323975".trim();
	public static String xizang_dx = "18089079521".trim();
	public static String xizang_yd = "15208053979".trim();
	public static String xizang_lt = "15608914666".trim();
	public static String guizhou_dx = "18185123119".trim();
	public static String guizhou_yd = "15985227199".trim();
	public static String guizhou_lt = "15519585060".trim();
	public static String liaoning_dx = "18940920667".trim();
	public static String liaoning_yd = "15204266199".trim();
	public static String liaoning_lt = "13050168726".trim();
	public static String zhongqing_dx = "13340240783".trim();
	public static String zhongqing_yd = "15730034886".trim();
	public static String zhongqing_lt = "13167930058".trim();
	public static String shan3xi_dx = "18091742298".trim();
	public static String shan3xi_yd = "15771610418".trim();
	public static String shan3xi_lt = "18629094238".trim();
	public static String qinghai_dx = "18997005751".trim();
	public static String qinghai_yd = "15003624310".trim();
	public static String qinghai_lt = "18697224080".trim();
	public static String xianggang_lt = "13172067689".trim();
	public static String heilongjiang_dx = "18045171298".trim();
	public static String heilongjiang_yd = "18346248293".trim();
	public static String heilongjiang_lt = "18646248382".trim();
	public static String neimenggu_dx = "18104865399".trim();
	public static String neimenggu_yd = "18847169588".trim();
	public static String neimenggu_lt = "15661187078".trim();

	public static Logger initLog() {
		PropertyConfigurator.configure("G:/log4j/log4j.properties");
		log.info("---------------------------------------------------------");
		return log;
	}
}
