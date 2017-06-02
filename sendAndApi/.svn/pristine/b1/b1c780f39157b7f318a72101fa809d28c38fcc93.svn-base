package com.xinxing.o.boss.business.provider.other.yshang.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.xinxing.boss.business.api.domain.SendOrderInfo;
import com.xinxing.o.boss.common.encry.MD5_HexUtil;
import com.xinxing.o.boss.common.http.HttpUtils;
import com.xinxing.o.boss.common.resource.Constants;

public class YshangUtil {
	/**
	 * 获取提单请求参数
	 * 格式   tbOrderNo=&spuId=&accountVal=&supplierId=&notifyUrl=&sign=
	 * @param sendOrderInfo
	 * @return
	 * @author wuzl
	 */
	public static String getSendParam(SendOrderInfo sendOrderInfo) {
		String tbOrderNo = sendOrderInfo.getOrderId();
		//产品编号
		String spuId = sendOrderInfo.getSupplierCode();
		String accountVal = sendOrderInfo.getPhone();
		//渠道商编号，由YSHANG流量商城分配  从配置文件获取supplierId
		String supplierId = Constants.getString("yshang_supplierId").trim();
		//回调url
		//String notifyUrl = Constants.getString("hy_notify").trim() + "/yshangCallBack.do";
		Map<String, Object> mapKey = new HashMap<String, Object>();
		mapKey.put("tbOrderNo", tbOrderNo);
		mapKey.put("spuId", spuId);
		mapKey.put("accountVal", accountVal);
		mapKey.put("supplierId", supplierId);
		//mapKey.put("notifyUrl", notifyUrl);
		String sign = getSign(mapKey);
		mapKey.put("sign", sign);
		//Map<String, Object> map = new HashMap<String, Object>();
		//map.put("", tbOrderNo);
		return HttpUtils.getStrByMapOrderByABC(mapKey);
	}
	/**
	 * 获取查询请求参数
	 * 格式   tbOrderNo=&supplierId=&sign=
	 * @param sendOrderInfo
	 * @return
	 * @author wuzl
	 */
	public static String getQueryParam(SendOrderInfo sendOrderInfo) {
		String tbOrderNo = sendOrderInfo.getOrderId();
		//从配置文件获取supplierId
		String supplierId = Constants.getString("yshang_supplierId").trim();
		Map<String, Object> mapKey = new HashMap<String, Object>();
		mapKey.put("tbOrderNo", tbOrderNo);
		mapKey.put("supplierId", supplierId);
		String sign = getSign(mapKey);
		mapKey.put("sign", sign);
		return HttpUtils.getStrByMapOrderByABC(mapKey);
	}
	/**
	 * 生产sign
	 * @param mapKey
	 * @return
	 * @author wuzl
	 */
	private static String getSign(Map<String, Object> mapKey) {
		//从配置文件获取密钥
		String PRIVATEKEY = Constants.getString("yshang_key").trim();
		//Key排序
        Object[] arrKey = mapKey.keySet().toArray();
        Arrays.sort(arrKey);
        //拼接排序后的参数
        StringBuffer sb = new StringBuffer(200);
        for(Object key : arrKey) {
            Object value = mapKey.get(key);
            sb.append(key.toString() + value);
        }
        //MD5加密生成签名
        String sign = MD5_HexUtil.md5Hex(sb.toString() + PRIVATEKEY);
        return sign;
	}
}
