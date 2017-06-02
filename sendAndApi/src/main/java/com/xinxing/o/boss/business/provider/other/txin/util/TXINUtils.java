package com.xinxing.o.boss.business.provider.other.txin.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.w3c.dom.NodeList;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xinxing.boss.business.api.domain.SendOrderInfo;
import com.xinxing.boss.business.api.domain.SendOrderResult;
import com.xinxing.boss.business.api.domain.SendOrderStatus;
import com.xinxing.o.boss.common.encry.MD5_HexUtil;
import com.xinxing.o.boss.common.http.HttpUtils;
import com.xinxing.o.boss.common.resource.Constants;
import com.xinxing.o.boss.common.time.TimeUtils;
import com.xinxing.o.boss.common.util.FlowSendLogUtils;
import com.xinxing.o.boss.common.xml.XmlUtils;

public class TXINUtils {
	/**
	 * @date   2017年3月15日下午3:05:35
	 * @author 唐镜茗
	 * @param sendOrderInfo
	 * @return
	 * 发送订单请求参数
	 */
	public static String getSendStr(SendOrderInfo sendOrderInfo){
		String supplierCode = sendOrderInfo.getSupplierCode();//"0,50" 0为漫游，1为本地，50代表50M
		String[] splitCode = supplierCode.split(","); 
		//上游所需参数
		String oid = sendOrderInfo.getOrderId();   //商户系统自己生成的订单号，限制 32 位以内。 
		String cid = Constants.getString("txin_id").trim(); //商家编号 
		String pr = splitCode[1];				   //单位商品面值(流量单位为M,如 50M)
		String nb = "1";						   //充值的商品数量，暂时只支持数值 1
		String fm = String.valueOf(Integer.parseInt(pr)*Integer.parseInt(nb));//充值总面值=商品面值*商品数量
		String pn = sendOrderInfo.getPhone(); 	   //充值号码
		String ru = Constants.getString("txin_sendBack").trim();//通知回调地址
		String tsp = TimeUtils.getNowTimeFormate();//请求时间戳，格式 yyyyMMddHHmmss
		String bt = "1";						   //待充值产品的业务类型：话费业务为 0，流量业务为 1
		String ut = splitCode[0];				   //可空，待充值产品的使用类型：0 为漫游，1 为本地  (默认为 0)
		Map<String,Object> map = new HashMap<>();
		map.put("oid",oid);
		map.put("cid",cid);
		map.put("pr",pr);
		map.put("nb",nb);
		map.put("fm",fm);
		map.put("pn",pn);
		map.put("ru",ru);
		map.put("tsp",tsp);
		map.put("bt",bt);
		String sign = MD5_HexUtil.md5Hex(getSign(map));//MD5(oid+cid+pr+nb+fm+pn+ru+tsp+bt+key)小写
		map.put("sign",sign);
		map.put("ut",ut);
		return HttpUtils.getStrByMapOrderByABC(map);
	}
	/**
	 * @date   2017年3月15日下午3:06:02
	 * @author 唐镜茗
	 * @param sendOrderInfo
	 * @return
	 * 查询订单请求参数
	 */
	public static String getQueryStr(SendOrderInfo sendOrderInfo){
		Map<String,Object> map = new HashMap<>();
		String oid = sendOrderInfo.getOrderId();   //商户系统自己生成的订单号，限制 32 位以内。 
		String cid = Constants.getString("txin_id").trim(); //商家编号 
		String tsp = TimeUtils.getNowTimeFormate();//请求时间戳，格式 yyyyMMddHHmmss
		map.put("oid", oid);
		map.put("cid", cid);
		map.put("tsp", tsp);
		String sign = MD5_HexUtil.md5Hex(getSign(map));//MD5 (oid+cid+tsp+key)不区分大写,即小写
		map.put("sign", sign);
		return HttpUtils.getStrByMapOrderByABC(map);
	}
	
	
	public static String getSign(Map<String, Object> map){
		String txin_key = Constants.getString("txin_key").trim();
		StringBuilder sb = new StringBuilder();
		sb.append(map.get("oid"));
		sb.append(map.get("cid"));
		if (StringUtils.isNotBlank((String) map.get("pr"))) { //提交订单
			sb.append(map.get("pr"));
			sb.append(map.get("nb"));
			sb.append(map.get("fm"));
			sb.append(map.get("pn"));
			sb.append(map.get("ru"));
			sb.append(map.get("tsp"));
			sb.append(map.get("bt"));
		}else { 						 
			sb.append(map.get("tsp"));		//查询订单
		}
		sb.append(txin_key);
		return sb.toString();
	}
	
	/**验证回调方法中的 sign是否正确
	 * @date   2017年3月17日下午4:26:34
	 * @author 唐镜茗
	 * @param map
	 * @return 
	 */
	 /*sign=94RJ490RU405493TUI4304039049
	   sign= MD5(sid+ste+cid+pid+oid+pn+tf+fm+key)大写 
	 */
	public static boolean checkoutMD5(Map<String,String> map){
		String txin_key = Constants.getString("txin_key").trim();
		StringBuilder sb = new StringBuilder();
		sb.append(map.get("sid"));
		sb.append(map.get("ste"));
		sb.append(map.get("cid"));
		sb.append(map.get("pid"));
		sb.append(map.get("oid"));
		sb.append(map.get("pn"));
		sb.append(map.get("tf"));
		sb.append(map.get("fm"));
		sb.append(txin_key);
		String newMd5 = MD5_HexUtil.md5Hex(sb.toString()).toUpperCase();
		if (newMd5.equals(map.get("sign"))) {
			return true;
		}
		return false;
	}
	
	public static String getErrorMsg(String code){
		Map<String,String> map = new HashMap<>();
		map.put("250","必须参数为空");
		map.put("251","签名错误");
		map.put("252","订单已存在 ");
		map.put("253","商家不存在或未启用");
		map.put("254","订单金额错误");
		map.put("255","卡规则错误 ");
		map.put("256","渠道产品未开通");
		map.put("257","解密失败");
		map.put("258","订单不存在");
		map.put("259","渠道订单规则未设置");
		map.put("260","系统不支持该产品");
		map.put("261","手机号码鉴权失败");
		map.put("271","发送订单频率过快");
		map.put("272","提交订单总金额超过限制金额");
		map.put("600","商户账户未开启 ");
		map.put("601","扣款失败 ");
		map.put("999","未知异常，请进一步核实 ");
		return map.get(code)==null?"":map.get(code);
	}

	@Test
	public void test() throws Exception {
		String before30Minutes = TimeUtils.getNowBeforeMinutes(30);
		System.out.println(before30Minutes);
		Date date30 = TimeUtils.getDateByStr(before30Minutes);
		String testStr = "2017-04-11 15:58:00";
		Date test = TimeUtils.getDateByStr(testStr);
		boolean mark = test.before(date30);
		System.out.println(mark);
		String xmlStr = "<?xml version=\"1.0\"?>"
				        +"<all>"  
						+   "<response>"
						+ 		"<result>true</result>"
						+ 		"<code>100</code>"
						+ 		"<msg>恭喜，提交成功</msg>"
						+	    "<data>"
						+ 			"<sid>RO201312232232127348</sid>"
						+	    "</data>"
						+   "</response>"
						+   "<response>"
						+ 		"<result>双击666</result>"
						+   "</response>"
						+"</all>"  ;
		System.out.println("1--"+XmlUtils.getXMLNoteValue("msg",xmlStr));
		System.out.println("2--"+XmlUtils.getXmlNoteValue(xmlStr,"all/response/data/sid/text()"));
		NodeList response = XmlUtils.getXmlNoteList(xmlStr,"all");
		System.out.println("3--"+response.item(0).getTextContent());
		NodeList code = XmlUtils.getXmlNoteList(xmlStr,"all/response/code");
		System.out.println("4--"+code.item(0).getTextContent());
	}
}
