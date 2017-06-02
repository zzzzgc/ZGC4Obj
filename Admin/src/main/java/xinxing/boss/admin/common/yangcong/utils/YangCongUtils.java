package xinxing.boss.admin.common.yangcong.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import xinxing.boss.admin.common.utils.constants.Constants;
import xinxing.boss.admin.common.utils.json.JsonUtils;
import xinxing.boss.admin.common.utils.security.MD5_HexUtil;

public class YangCongUtils {

	/**
	 * 获取用户绑定的二维码
	 * @return
	 */
	public static YangCongRes getQrcode4Binding(){
		String url = Constants.getString("yangcong_bingding_url");
		String app_id = Constants.getString("yangcong_appid");
		String key = Constants.getString("yangcong_appkey");
		String callback_url = Constants.getString("yangcong_callback_url");
		String callback = null;
		try {
			callback = URLEncoder.encode(callback_url, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} 
		String signature = MD5_HexUtil.md5Hex("app_id=" + app_id + "callback="+ callback + key);
		String result=null;
		try {
			result = HttpsUtil.sendGet(url + "?app_id=" + app_id+ "&callback=" + URLEncoder.encode(callback,"UTF-8") + "&signature=" + signature,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		YangCongRes req=JsonUtils.json2Obj(result,YangCongRes.class);
		return req;
	}
	
	public static boolean isCodeOk(String uid,String securityCode) {
		String url = Constants.getString("yangcong_offline_authorization_url");
		String app_id = Constants.getString("yangcong_appid");
		String key = Constants.getString("yangcong_appkey");
		String signature = MD5_HexUtil.md5Hex("app_id=" + app_id + "dynamic_code="+ securityCode +"uid="+uid + key);
		try {
			uid = URLEncoder.encode(uid,"UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		String content = "app_id=" + app_id+"&dynamic_code="+ securityCode +"&uid="+uid+ "&signature=" + signature;
		byte[] resultByte = null;
		try {
			resultByte = HttpsUtil.post(url, content, "UTF-8");
		} catch (KeyManagementException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String result = new String(resultByte);
		YangCongOfflineRes req = JsonUtils.json2Obj(result, YangCongOfflineRes.class);
		return req.getStatus()==200;
	}
	
	public static void main(String[] args) throws UnsupportedEncodingException {
		String securityCode = "123462";
		String uid = "e+f2iJHkScjvo6GHZKVmaA==";
		

		String url =("https://api.yangcong.com/v2/offline_authorization");
		String app_id = ("EcAPTRzNWcwEm7iGWgmdKldHkADiyfQK");
		String key = ("pPKJU7GGhZai6deqW18N");
		String signature = MD5_HexUtil.md5Hex("app_id=" + app_id + "dynamic_code="+ securityCode +"uid="+uid + key);
		
		String content = "app_id=" + app_id+"&dynamic_code="+ securityCode +"&uid="+uid+ "&signature=" + signature;
		byte[] resultByte = null;
		try {
			resultByte = HttpsUtil.post(url, content, "UTF-8");
		} catch (KeyManagementException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String result = new String(resultByte);
		YangCongOfflineRes req = JsonUtils.json2Obj(result, YangCongOfflineRes.class);
		System.out.println(JsonUtils.obj2Json(req));
	}
	
	/**
	 * 判断签名是否合法
	 * @param resMap
	 * @return
	 */
	public static boolean isSignOk(Map<String,String> resMap){
		List<String> keyList = getAscKey(resMap.keySet());
		StringBuilder sBuilder = new StringBuilder();
		String appKey = Constants.getString("yangcong_appkey");
		for(int i=0,len=keyList.size();i<len;i++){
			String key = keyList.get(i);
			String value = resMap.get(key);
			if(StringUtils.equals("signature", key)){
				continue;
			}
			sBuilder.append(key).append("=").append(value);
		}
		sBuilder.append(appKey);
		String sign = MD5_HexUtil.md5Hex(sBuilder.toString());
		return StringUtils.equals(resMap.get("signature"), sign);
	}
	
	
	/**
	 * 升序排序
	 * @param keyset
	 * @return
	 */
	private final static List<String> getAscKey(Collection<String> keyset){
	     List<String> list = new ArrayList<String>(keyset);  
	     Collections.sort(list);  
	     return list;
	}
	
	
}
