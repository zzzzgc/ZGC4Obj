package com.xinxing.o.boss.business.provider.other.jnlt.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.xinxing.boss.business.api.domain.SendOrderInfo;
import com.xinxing.o.boss.common.encry.MD5_HexUtil;
import com.xinxing.o.boss.common.http.HttpUtils;
import com.xinxing.o.boss.common.resource.Constants;

/**
 * JNLT 请求工具类
 * @author wuzl
 *
 */
public class JNLTUtil {

	/**
	 * 获取提单参数
	 * tbOrderNo=&spuId=&accountVal=&supplierId=&notifyUrl=&sign=&encoding=&promotion=
	 * @param orderInfo
	 * @return
	 * 2017年3月10日
	 * @author wuzl
	 */
	public static String getSendParam(SendOrderInfo orderInfo) {
		String tbOrderNo = orderInfo.getOrderId();
		//产品编号
		String spuId = orderInfo.getSupplierCode();
		String accountVal = orderInfo.getPhone();
		//渠道商编号
		String supplierId = Constants.getString("JNLT_supplierId").trim();
		//客户经理编号
		String encoding = Constants.getString("JNLT_encoding").trim();
		//活动编号
		String promotion = Constants.getString("JNLT_promotion").trim();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("accountVal", accountVal);
		map.put("encoding", encoding);
		map.put("promotion", promotion);
		map.put("supplierId", supplierId);
		map.put("spuId", spuId);
		map.put("tbOrderNo", tbOrderNo);
		String sign = getSign(map);
		map.put("sign", sign);
		return HttpUtils.getStrByMapOrderByABC(map);
	}
	/**
	 * 获取查询参数
	 * ?tbOrderNo=&supplierId=&sign=
	 * @param orderInfo
	 * @return
	 * @date 2017年3月10日
	 * @author wuzl
	 */
	public static String getQueryParam(SendOrderInfo orderInfo) {
		String tbOrderNo = orderInfo.getOrderId();
		String supplierId = Constants.getString("JNLT_supplierId").trim();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("supplierId", supplierId);
		map.put("tbOrderNo", tbOrderNo);
		String sign = getSign(map);
		map.put("sign", sign);
		return HttpUtils.getStrByMapOrderByABC(map);
	}
	/**
	 * 生成sign
	 * @param map
	 * @return
	 * @date 2017年3月10日
	 * @author wuzl
	 */
	private static String getSign(Map<String, Object> map) {
		//密钥
		String PRIVATEKEY = Constants.getString("JNLT_key").trim();
		//Key排序
        Object[] arrKey = map.keySet().toArray();
        Arrays.sort(arrKey);
        //拼接排序后的参数
        StringBuffer sb = new StringBuffer(200);
        for(Object key : arrKey) {
            Object value = map.get(key);
            sb.append(key.toString() + value);
        }
        //MD5加密生成签名
        String sign = MD5_HexUtil.md5Hex(sb.toString() + PRIVATEKEY);
        return sign;
	}
}
