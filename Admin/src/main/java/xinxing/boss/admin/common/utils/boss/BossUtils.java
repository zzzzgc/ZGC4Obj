package xinxing.boss.admin.common.utils.boss;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import xinxing.boss.admin.common.utils.constants.Constants;
import xinxing.boss.admin.common.utils.http.HttpUtils;
import xinxing.boss.admin.common.utils.security.MD5_HexUtil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 发送至boss 工具类
 * @author Zhuan
 *
 */
public class BossUtils {
	private static final Logger log = Logger.getLogger(BossUtils.class);
	
	/**
	 * 京东发送订单  ， 账号为京东
	 * @param orderId
	 * @param productId
	 * @return
	 */
	public static boolean sendOrderToBoss(String orderId,String productId){
		String str="{\"Action\":\"SendOrder\",\"Version\":\"V1.0\",\"MerChant\":\""+Constants.getString("cus_merchant") +"\",\"FlowKey\":\""+orderId+"\",\"Product\":\""+productId+"\",\"Mobile\":\""+Constants.getString("jingdong_phone")+"\"}";
		log.info("订单发送信息:"+str);
		String result=HttpUtils.sendPost(Constants.getString("boss_url"), str,MD5_HexUtil.md5Hex(str).toUpperCase(),getToken());
		log.info("订单返回信息:"+result);
		if(StringUtils.isNotBlank(result)){
			JSONObject obj = JSON.parseObject(result);
			Integer status = obj.getInteger("Type");
			if(status==4){
				return true;
			}
		}
		return false;
	}
/*	
	public static Map<String,Object> queryFromBoss(String orderId,String phone){
		String str="{\"Action\":\"GetCallBack\",\"Version\":\"V1.0\",\"MerChant\":\""+Constants.getString("xinxing_merchant") +"\",\"FlowKey\":\""+orderId+"\",\"Mobile\":\""+phone+"\"}";
		log.info("订单发送信息:"+str);
		String result=HttpUtils.sendPost(Constants.getString("boss_url"), str,MD5_HexUtil.md5Hex(str).toUpperCase(),getToken(Constants.getString("xinxing_merchant"),Constants.getString("xinxing_loginName"),Constants.getString("xinxing_secret")));
		log.info("订单返回信息:"+result);
		
		Map<String,Object> map=null;
		if(StringUtils.isNotBlank(result)){
			map = JsonUtils.json2Obj(result, Map.class);
		}
		return map;
	}
	*/

	/**
	 * 获取Token
	 * @param url 发送地址
	 * @param merChant 采购商ID
	 * @param clientId 采购商登录名
	 * @param clientSeceret  密钥
	 * @return
	 */
	public static String getToken(){
		String string="{\"Action\":\"GetToken\",\"Version\":\"V1.0\",\"MerChant\":\""+Constants.getString("cus_merchant")+"\",\"ClientID\":\""+Constants.getString("cus_loginName")+"\",\"ClientSeceret\":\""+Constants.getString("cus_secret")+"\"}";
		log.info("订单获取token发送信息:"+string);
		String sendStr=HttpUtils.sendPost(Constants.getString("boss_url"), string,MD5_HexUtil.md5Hex(string).toUpperCase(),"");
		log.info("订单获取token返回信息:"+sendStr);
		String token="";
		if(StringUtils.isNotBlank(sendStr)){
			JSONObject obj = JSON.parseObject(sendStr);
			token=(String) obj.get("Msg");
		}
		return token;
	}
	
	/**
	 * 获取Token
	 * @param url 发送地址
	 * @param merChant 采购商ID
	 * @param clientId 采购商登录名
	 * @param clientSeceret  密钥
	 * @return
	 */
	/*public static String getToken(String merChant,String clientId,String secret){
		String string="{\"Action\":\"GetToken\",\"Version\":\"V1.0\",\"MerChant\":\""+merChant+"\",\"ClientID\":\""+clientId+"\",\"ClientSeceret\":\""+secret+"\"}";
		log.info("订单获取token发送信息:"+string);
		String sendStr=HttpUtils.sendPost(Constants.getString("boss_url"), string,MD5_HexUtil.md5Hex(string).toUpperCase(),"");
		log.info("订单获取token返回信息:"+sendStr);
		String token="";
		if(StringUtils.isNotBlank(sendStr)){
			JSONObject obj = JSON.parseObject(sendStr);
			token=(String) obj.get("Msg");
		}
		return token;
	}*/
}
