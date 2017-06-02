package com.xinxing.flow.core.upstream.tools;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xinxing.flow.erorr.TransferSystemException;
import com.xinxing.transfer.common.encry.MD5_HexUtil;
import com.xinxing.transfer.common.http.HttpUtils;
import com.xinxing.transfer.common.json.JsonUtils;
import com.xinxing.transfer.common.resource.Constants;

public class Transfer4HuaYi_upUtils {
	private static final Logger log = Logger.getLogger("TransferCore_Transfer_YG_up");

	/**
	 * 获取YG的token
	 * 
	 * @param request
	 * @return
	 * @throws TransferSystemException 
	 */
	public static String getToken() throws TransferSystemException {

		String token = null;
		String kenUrl = null;
		String tokenParam = null;
		String responsejson = null;
		kenUrl = Constants.getString("YG_RequestTokenUrl");
		tokenParam = getTokenParam();

		Map<String, String> map = new TreeMap<>();
		String md5Hex = MD5_HexUtil.md5Hex(tokenParam).toUpperCase();
		String Sign = md5Hex;
		map.put("Sign", Sign);

		// {"Code":"600012","Msg":"eae56e896829145ad90306a9112ca22f","Type":4}

		// 获取响应
		log.info("获取优狗token,提交参数,文件头:" + md5Hex + ",参数=" + tokenParam);
		responsejson = HttpUtils.sendPost(kenUrl, tokenParam, map);
		log.info("获取优狗token,返回参数=" + responsejson);

		// 获取Token
		JSONObject resJsonObj = JSON.parseObject(responsejson);
		String Type = resJsonObj.getString("Type");
		if ("4".equals(Type)) {
			token = resJsonObj.getString("Msg");
		} else {
			log.info("Token获取失败");
			throw new TransferSystemException();
		}

		return token;
	}

	/**
	 * 配置用于获取Token的参数
	 * 
	 * @return
	 */
	public static String getTokenParam() {

		Map<String, Object> map = new HashMap<>();
		// YG获取token
		String MerChant = Constants.getString("CQ_MerChant"); // 商户唯一标示
		String ClientID = Constants.getString("CQ_ClientID"); // 客户ID
		String ClientSeceret = Constants.getString("CQ_ClientSeceret"); // 客户秘钥
		String Version = "V1.0"; // 固定值：V1.0
		String Action = "GetToken"; // 固定值：GetToken

		map.put("Action", Action);
		map.put("Version", Version);
		map.put("MerChant", MerChant);
		map.put("ClientID", ClientID);
		map.put("ClientSeceret", ClientSeceret);

		return JsonUtils.obj2Json(map);
	}

	/**
	 * 通过 运营商简称获取运营商
	 * 
	 * @param iSP
	 *            运营商简称
	 * @return 供应商
	 */
	public static String getMobileoperator(String iSP) {
		switch (iSP) {
		case "YD":
			return "移动";
		case "LT":
			return "联通";
		case "DX":
			return "电信";
		default:
			return "未知运营商";
		}
	}

	/**
	 * 通过省份id获取省份名称
	 * 
	 * @param URPROVID
	 *            省份id
	 * @return 省份
	 */
	public static String getProvince(String URPROVID) {
		String product = null;
		URPROVID = URPROVID.replace("\n", "");
		int a = new Integer(URPROVID);
		switch (a) {
		case 0:
			product = "全国";
			break;
		case 1:
			product = "北京";
			break;
		case 2:
			product = "上海";
			break;
		case 3:
			product = "天津";
			break;
		case 4:
			product = "重庆";
			break;
		case 5:
			product = "河北";
			break;
		case 6:
			product = "山西";
			break;
		case 7:
			product = "内蒙古";
			break;
		case 8:
			product = "辽宁";
			break;
		case 9:
			product = "吉林";
			break;
		case 10:
			product = "黑龙江";
			break;
		case 11:
			product = "江苏";
			break;
		case 12:
			product = "浙江";
			break;
		case 13:
			product = "安徽";
			break;
		case 14:
			product = "福建";
			break;
		case 15:
			product = "江西";
			break;
		case 16:
			product = "山东";
			break;
		case 17:
			product = "河南";
			break;
		case 18:
			product = "湖北";
			break;
		case 19:
			product = "湖南";
			break;
		case 20:
			product = "广东";
			break;
		case 21:
			product = "广西";
			break;
		case 22:
			product = "海南";
			break;
		case 23:
			product = "四川";
			break;
		case 24:
			product = "贵州";
			break;
		case 25:
			product = "云南";
			break;
		case 26:
			product = "西藏";
			break;
		case 27:
			product = "陕西";
			break;
		case 28:
			product = "甘肃";
			break;
		case 29:
			product = "青海";
			break;
		case 30:
			product = "宁夏";
			break;
		case 31:
			product = "新疆";
			break;
		default:
			product = null;
			break;
		}
		return product;
	}

}
