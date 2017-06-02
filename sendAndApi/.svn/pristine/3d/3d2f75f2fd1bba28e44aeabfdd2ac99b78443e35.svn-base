package com.xinxing.o.boss.business.provider.other.justtest.util;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.rpc.ParameterMode;
import javax.xml.rpc.ServiceException;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;
import org.apache.log4j.Logger;
import org.junit.Test;

import com.xinxing.boss.business.api.domain.SendOrderInfo;
import com.xinxing.o.boss.business.provider.other.justtest.api.JusttestSendApiImpl;
import com.xinxing.o.boss.common.resource.Constants;

public class JusttestUtils {
	private static Logger log = Logger.getLogger(JusttestSendApiImpl.class);

	/**
	 * 获取提交请求（封装订单，用于给供应商）
	 * @param sendOrderInfo
	 * @return
	 * @throws Exception
	 */
	public static String getSendReq(SendOrderInfo sendOrderInfo) throws Exception {
		String order_orderId = sendOrderInfo.getOrderId();// AA订单id
		String supplierOrderId = sendOrderInfo.getSupplierOrderId();
		String order_productCode = sendOrderInfo.getSupplierCode();// 流量包编码
		String phone = sendOrderInfo.getPhone();// 手机号
		String[] split_productCode = order_productCode.split(",");// 第一个数代表全国/本省 ,第二个数代表产品M
		// String = Constants.getString("justtest_".trim()).trim();
		//上游所需数据
		String acc_nbr = phone;
		String c_product_id = split_productCode[0]; // 产品类型
		String buss_id = Constants.getString("justtest_buss_id");
		String transactionId = supplierOrderId;// 请求流水号:CQFS+8位日期(例如:20151108141558）+8位随机码（例如：12345678）
		String con_id = Constants.getString("justtest_con_id");
		String sale_type = "A";// A 订购 D 退订
		// 1 商户API接口 
		// 2 WAP
		// 3 商户WEB单卡充值    
		// 31 商户WEB批量充值
		// 4 用户WAP自助充值
		// 0 其他
		String channel_id = "1";//
		String offerCompId = split_productCode[1];// 商品id
		String newFlag = "F";// T新用户 F老用户
		String stimeFlag = "0"; //"0"立即生效，"1"次月生效
		// 1 可选包 可用
		// 2 附属产品 不可用（后期扩展）
		// 3 国际漫游 不可用（后期扩展）
		// 4 增值业务 不可用 （后期扩展）
		String accept_type = "1";
		// 1 直接受理 1
		// 2 红包受理 2
		String accept_mode = "1";
		String password = Constants.getString("justtest_password".trim()).trim();
		String sendReq = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>" + "<Root><Header><transactionId>" + transactionId
				+ "</transactionId><channel_id>" + channel_id + "</channel_id><password>" + password + "</password></Header><DataParam><acc_nbr>"
				+ acc_nbr + "</acc_nbr><c_product_id>" + c_product_id + "</c_product_id><buss_id>" + buss_id + "</buss_id><con_id>" + con_id
				+ "</con_id><sale_type>" + sale_type + "</sale_type><offerCompId>" + offerCompId + "</offerCompId><newFlag>" + newFlag
				+ "</newFlag><accept_type>" + accept_type + "</accept_type><accept_mode>" + accept_mode + "</accept_mode><stimeFlag>" + stimeFlag
				+ "</stimeFlag></DataParam></Root>";

		return sendReq;
	}

	public static String send(String sendReq) throws Exception {
		return excu(sendReq, "exchage");
	}

	public static String query(String queryReq) throws Exception {
		return excu(queryReq, "getOrderState");
	}

	public static String excu(String sendReq, String method) throws Exception {
		// String sendReq =
		// "<?xml version=\"1.0\" encoding=\"UTF-8\" ?><Root><Header><transactionId>CQFS2017020413402930992210</transactionId><channel_id>1</channel_id><password>3700CE886E8627F41E79</password></Header><DataParam><acc_nbr>18182338041</acc_nbr><c_product_id>208511296</c_product_id><buss_id>10023</buss_id><con_id>1124</con_id><sale_type>A</sale_type><offerCompId>1049</offerCompId><newFlag>F</newFlag><accept_type>1</accept_type><stimeFlag>0</stimeFlag><accept_mode>1</accept_mode></DataParam></Root>";
		String endpoint = "http://dls.cq.ct10000.com/external/services/TrafficAcceptService";
		String result = "";
		Service service = new Service();
		Call call;
		Object[] object = new Object[1];
		object[0] = sendReq;// Object是用来存储方法的参数
		try {
			call = (Call) service.createCall();
			call.setTargetEndpointAddress(endpoint);// 远程调用路径
			// call.setOperationName("getOrderState");// 调用的方法名 查询
			call.setOperationName(method);// 调用的方法名 受理
			// 设置参数名:
			call.addParameter("in0", // 参数名
					XMLType.XSD_STRING,// 参数类型:String
					ParameterMode.IN);// 参数模式：'IN' or 'OUT'
			// 设置返回值类型：
			call.setReturnType(XMLType.XSD_STRING);// 返回值类型：String
			call.setTimeout(20000);
			result = (String) call.invoke(object);// 远程调用
		} catch (ServiceException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static String getQueryReq(SendOrderInfo sendOrderInfo) throws Exception {
		String order_providerOrderId = sendOrderInfo.getSupplierOrderId();// 供应商流水号
		// String = Constants.getString("justtest_".trim()).trim();
		String transactionId = order_providerOrderId;
		String channel_id = "1";//
		String password = Constants.getString("justtest_password".trim()).trim();
		String buss_id = Constants.getString("justtest_buss_id".trim()).trim();
		String queryReq = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>" + "<Root><transactionId>" + transactionId + "</transactionId><channel_id>"
				+ channel_id + "</channel_id ><buss_id>" + buss_id + "</buss_id ><password>" + password + "</password></Root>";
		return queryReq;
	}

	public static String getErrorMsg(String code) {
		Map<String, String> map = new HashMap<>();
		map.put("-10000", "密码错误");
		map.put("-10001", "渠道传入错误");
		map.put("-10002", "请求流水号不能为空");
		map.put("-10003", "请求参数有误，不能为空");
		map.put("-10004", "未查询到对应数据");
		map.put("-10005", "未查询到商品对应的CRM产品ID");
		map.put("-10006", "请使用电信号码");
		map.put("-10007", "库存不足");
		map.put("-10008", "CRM受理成功，状态更新失败");
		map.put("-10009", "CRM受理失败");
		map.put("-10010", "系统忙，请稍后再试");
		map.put("-10011", "直接受理类型，号码和产品类型不能为空");
		map.put("-10012", "CRM受理超时");
		map.put("-10013", "请求流水号不能重复");
		map.put("-10014", "合同可使用金额不足");
		map.put("-10020", "系统正在升级，请稍后再试！");
		return map.get(code) == null ? "" : map.get(code);

	}

	@Test
	public void test() throws Exception {
	}
}

