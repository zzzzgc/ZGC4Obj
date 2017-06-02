package com.xinxing.o.boss.common.phone;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.xinxing.boss.business.api.domain.SendOrderInfo;

public class PhoneUtils {

	public static String shanghai_dx = "13331960906".trim();
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
	public static String guangdong_lt = "13726902126".trim();
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
	public static String chongqing_dx = "13340240783".trim();
	public static String chongqing_yd = "15730034886".trim();
	public static String chongqing_lt = "13167930058".trim();
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

	public static Map<String, String> map = new HashMap<>();
	static {
		map.put("全国_电信".trim(), " 17717615554".trim());
		map.put("全国_移动".trim(), " 13816187947".trim());
		map.put("全国_联通".trim(), " 13262622726".trim());
		map.put("上海_电信".trim(), " 17717615554".trim());
		map.put("上海_移动".trim(), " 13816187947".trim());
		map.put("上海_联通".trim(), " 13262622726".trim());
		map.put("云南_电信".trim(), " 18988392115".trim());
		map.put("云南_移动".trim(), " 18387870868".trim());
		map.put("云南_联通".trim(), " 18687033863".trim());
		map.put("北京_电信".trim(), " 18146508098".trim());
		map.put("北京_移动".trim(), " 15801281895".trim());
		map.put("北京_联通".trim(), " 18516812873".trim());
		map.put("吉林_电信".trim(), " 18043152224".trim());
		map.put("吉林_移动".trim(), " 15844004850".trim());
		map.put("吉林_联通".trim(), " 18504492286".trim());
		map.put("四川_电信".trim(), " 13309093183".trim());
		map.put("四川_移动".trim(), " 15883890906".trim());
		map.put("四川_联通".trim(), " 15508113646".trim());
		map.put("天津_电信".trim(), " 15320128023".trim());
		map.put("天津_移动".trim(), " 14722261351".trim());
		map.put("天津_联通".trim(), " 18630947721".trim());
		map.put("宁夏_电信".trim(), " 18009508866".trim());
		map.put("宁夏_移动".trim(), " 15769547531".trim());
		map.put("宁夏_联通".trim(), " 13259696145".trim());
		map.put("安徽_电信".trim(), " 18156266717".trim());
		map.put("安徽_移动".trim(), " 18356873571".trim());
		map.put("安徽_联通".trim(), " 13030683467".trim());
		map.put("山东_电信".trim(), " 15318056063".trim());
		map.put("山东_移动".trim(), " 13723985656".trim());
		map.put("山东_联通".trim(), " 15564418062".trim());
		map.put("山西_电信".trim(), " 15333666799".trim());
		map.put("山西_移动".trim(), " 18335403732".trim());
		map.put("山西_联通".trim(), " 13073507996".trim());
		map.put("广东_电信".trim(), " 18922219320".trim());
		map.put("广东_移动".trim(), " 13726902126".trim());
		map.put("广东_联通".trim(), " 18620119950".trim());
		map.put("广西_电信".trim(), " 13393620662".trim());
		map.put("广西_移动".trim(), " 18277730053".trim());
		map.put("广西_联通".trim(), " 15678508050".trim());
		map.put("新疆_电信".trim(), " 18196308958".trim());
		map.put("新疆_移动".trim(), " 15894154905".trim());
		map.put("新疆_联通".trim(), " 18699954461".trim());
		map.put("江苏_电信".trim(), " 13327865161".trim());
		map.put("江苏_移动".trim(), " 15162949030".trim());
		map.put("江苏_联通".trim(), " 13115060769".trim());
		map.put("江西_电信".trim(), " 18107928358".trim());
		map.put("江西_移动".trim(), " 15797636398".trim());
		map.put("江西_联通".trim(), " 13026265558".trim());
		map.put("河北_电信".trim(), " 17732682773".trim());
		map.put("河北_移动".trim(), " 15132026169".trim());
		map.put("河北_联通".trim(), " 18631201232".trim());
		map.put("河南_电信".trim(), " 13303938891".trim());
		map.put("河南_移动".trim(), " 18240739574".trim());
		map.put("河南_联通".trim(), " 15538399005".trim());
		map.put("浙江_电信".trim(), " 13362168969".trim());
		map.put("浙江_移动".trim(), " 13567478500".trim());
		map.put("浙江_联通".trim(), " 18657513887".trim());
		map.put("海南_电信".trim(), " 18089860050".trim());
		map.put("海南_移动".trim(), " 13637667296".trim());
		map.put("海南_联通".trim(), " 18689808126".trim());
		map.put("湖北_电信".trim(), " 18971734745".trim());
		map.put("湖北_移动".trim(), " 13997725800".trim());
		map.put("湖北_联通".trim(), " 18627846381".trim());
		map.put("湖南_电信".trim(), " 15386355563".trim());
		map.put("湖南_移动".trim(), " 18807395052".trim());
		map.put("湖南_联通".trim(), " 18674525210".trim());
		map.put("甘肃_电信".trim(), " 15393120217".trim());
		map.put("甘肃_移动".trim(), " 18894430330".trim());
		map.put("甘肃_联通".trim(), " 15593782230".trim());
		map.put("福建_电信".trim(), " 18159312396".trim());
		map.put("福建_移动".trim(), " 15260003016".trim());
		map.put("福建_联通".trim(), " 13115979419".trim());
		map.put("西藏_电信".trim(), " 17711926657".trim());
		map.put("西藏_移动".trim(), " 18308052070".trim());
		map.put("西藏_联通".trim(), " 13298975113".trim());
		map.put("贵州_电信".trim(), " 15338521811".trim());
		map.put("贵州_移动".trim(), " 18275180767".trim());
		map.put("贵州_联通".trim(), " 18685270245".trim());
		map.put("辽宁_电信".trim(), " 18041951185".trim());
		map.put("辽宁_移动".trim(), " 18802436952".trim());
		map.put("辽宁_联通".trim(), " 15642221391".trim());
		map.put("重庆_电信".trim(), " 15320443682".trim());
		map.put("重庆_移动".trim(), " 18290237882".trim());
		map.put("重庆_联通".trim(), " 18623489035".trim());
		map.put("陕西_电信".trim(), " 13379279563".trim());
		map.put("陕西_移动".trim(), " 18709141490".trim());
		map.put("陕西_联通".trim(), " 13227779839".trim());
		map.put("青海_电信".trim(), " 18997125264".trim());
		map.put("青海_移动".trim(), " 18409783948".trim());
		map.put("青海_联通".trim(), " 13109715506".trim());
		map.put("香港_联通".trim(), " 13143920000".trim());
		map.put("黑龙江_电信 ".trim(), "13351764025 ".trim());
		map.put("黑龙江_移动 ".trim(), "18724431651 ".trim());
		map.put("黑龙江_联通 ".trim(), "18646669313 ".trim());
		map.put("内蒙古_电信 ".trim(), "13304733218 ".trim());
		map.put("内蒙古_移动 ".trim(), "13847476009 ".trim());
		map.put("内蒙古_联通 ".trim(), "15648111670 ".trim());
	}

	public static String getOperaotr(String phone) {
		Class<?> cls = PhoneUtils.class;
		Field[] fields = cls.getDeclaredFields();
		for (Field field : fields) {
			// 所有的参数均需要Str 结尾
			field.setAccessible(true);
			if (String.class.equals(field.getType())) {
				try {
					String name = field.getName();
					String str = (String) field.get(name);
					int last = name.lastIndexOf("_");
					if (str.equals(phone)) {
						String houzui = name.substring(last + 1, name.length());
						return houzui.equals("yd") ? "移动"
								: houzui.equals("lt") ? "联通" : houzui
										.equals("dx") ? "电信" : "错误的运营商";
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return "错误的运营商";
	}

	public static Logger initLog(Logger... logs) {
		Logger log = (logs != null && logs.length > 0) ? logs[0] : Logger
				.getLogger(PhoneUtils.class);
		PropertyConfigurator.configure("F:/log4j/log4j.properties");
		log.info("---------------------------------------------------------");
		return log;
	}

	public static SendOrderInfo createSendOrderInfo(String orderId,
			String supplierCode, String supplierOrderId, String phone) {
		SendOrderInfo sendOrderInfo = new SendOrderInfo();
		sendOrderInfo.setOrderId(orderId);
		sendOrderInfo.setSupplierCode(supplierCode);
		sendOrderInfo.setSupplierOrderId(supplierOrderId);
		sendOrderInfo.setPhone(phone);
		// System.out.println(getOperaotr(phone));
		sendOrderInfo.setModel(getOperaotr(phone));
		return sendOrderInfo;
	}

}
