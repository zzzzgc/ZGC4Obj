package test;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import com.alibaba.fastjson.JSON;
import com.xinxing.subpackage.core.erorr.DIYException;
import com.xinxing.subpackage.core.erorr.NULLOrderIdException;
import com.xinxing.subpackage.core.erorr.RepeatOrdersException;
import com.xinxing.subpackage.core.po.PackInfo;
import com.xinxing.subpackage.data.common.encry.MD5_HexUtil;
import com.xinxing.subpackage.data.common.http.HttpUtils;
import com.xinxing.subpackage.data.common.resource.Constants;
import com.xinxing.subpackage.data.dao.SubpackageOrderApiMapper;
import com.xinxing.subpackage.data.dao.SubpackageOrderSendMapper;
import com.xinxing.subpackage.data.po.SubpackageOrderApi;
import com.xinxing.subpackage.data.po.SubpackageOrderApiExample;
import com.xinxing.subpackage.data.po.SubpackageOrderSend;
import com.xinxing.subpackage.data.po.SubpackageOrderSendKey;
import com.xinxing.subpackage.data.service.OrderService;
import com.xinxing.subpackage.data.service.PackOrderService;

public class Demo extends test {
	private static final Logger log = Logger.getLogger(Demo.class);
	@Autowired
	PackOrderService packOrderService;
	@Autowired
	OrderService orderService;

	@Autowired
	SubpackageOrderApiMapper subpackageOrderApiMapper;

	@Test
	public void sendTest() {
		System.out.println("-----------------" + Constants.getString("pack_SignKey"));
		HashMap<String, Object> hashMap = new HashMap<>();

		String orderId = "testMax";
		String phone = "13622662311";
		String productInfo = "10123,10122,10123,10122,10123";
		String sign = null;
		String SIGN = null;
		hashMap.put("orderId", orderId);
		hashMap.put("phone", phone);
		hashMap.put("productInfo", productInfo);

		sign = HttpUtils.getStrByMapOrderByABC(hashMap) + Constants.getString("pack_SignKey");
		SIGN = MD5_HexUtil.md5Hex(sign);
		hashMap.put("SIGN", SIGN);

		String json = JSON.toJSONString(hashMap);

		System.out.println(json);
		System.out.println("SIGN:----------------" + SIGN + "----------");

	}

	@Autowired
	private static ReloadableResourceBundleMessageSource messageSource;

	private static Locale zh_CN = new Locale("zh", "CN");
	@Test
	public void Testtt() {
		System.out.println(Constants.getInteger("pack_continuous"));
		/*System.out.println(Constants.getString("pack_CustomerName"));
		System.out.println(messageSource.getMessage("pack_CustomerName", null, null, zh_CN));
		System.out.println(Constants.getString("pack_CustomerUser"));
		System.out.println(Constants.getString("pack_CustomerId"));
		System.out.println(Constants.getString("pack_CustomerKey"));
		System.out.println(Constants.getString("pack_SignKey"));
		System.out.println(Constants.getString("pack_SignKey"));*/

	}

	@Test
	public void send() throws RepeatOrdersException, NULLOrderIdException, DIYException {

		/*
		 * List<SubpackageOrderSend> allPack =
		 * packOrderService.getAllPack("123456"); for (SubpackageOrderSend
		 * subpackageOrderSend : allPack) {
		 * System.out.println(subpackageOrderSend); }
		 */
		/*
		 * String getpackId = packOrderService.getpackId("123Test");
		 * System.out.println(getpackId);
		 */

		System.out.println("123");
		String orderId = "123Test";
		SubpackageOrderApi selectByPrimaryKey = subpackageOrderApiMapper.selectByPrimaryKey(orderId);
		System.out.println(selectByPrimaryKey);

		SubpackageOrderApiExample example = new SubpackageOrderApiExample();
		example.createCriteria().andOrderidEqualTo(orderId);
		List<SubpackageOrderApi> list = subpackageOrderApiMapper.selectByExample(example);

		PackInfo nextPack = packOrderService.nextPack("123Test","123TestF1");
		System.out.println(nextPack == null ? "失败" : "成功");
		System.out.println(nextPack.getPackId());
		System.out.println(nextPack.getPackProductId());
	}
	@Autowired
	SubpackageOrderSendMapper subpackageOrderSendMapper;
	@Test
	public void demo() throws RepeatOrdersException {
		SubpackageOrderSend a = null;
		for (int i = 0; i < 9; i++) {
			a = new SubpackageOrderSend();
			a.setStatus(i);
			a.setOrderid("test6");
			a.setSendorderid(i + "");
			if (i % 2 == 0) {
				a.setFlowpacksize(20);
			} else {
				a.setFlowpacksize(10);

			}

			subpackageOrderSendMapper.insert(a);
		}
	}
	@Test
	public void demo111() throws RepeatOrdersException {
		SubpackageOrderSendKey key = new SubpackageOrderSendKey();
		key.setSendorderid("123324B2B247856F1");
		key.setOrderid("123324B2B247856");
		SubpackageOrderSend selectByPrimaryKey = subpackageOrderSendMapper.selectByPrimaryKey(key);
		System.out.println(selectByPrimaryKey);
	}

	@Test
	public void demo2() {
		/*
		 * 原有的数据没有设置的话会消失 SubpackageOrderSend s = new SubpackageOrderSend();
		 * s.setOrderid("test6"); s.setSendorderid("2"); s.setStarttime(new
		 * Date()); subpackageOrderSendMapper.updateByPrimaryKey(s);
		 */

		/*
		 * 原有的数据没有设置的话不会消失 SubpackageOrderSendExample example = new
		 * SubpackageOrderSendExample();
		 * example.createCriteria().andOrderidEqualTo("test6").
		 * andSendorderidEqualTo("3"); SubpackageOrderSend s = new
		 * SubpackageOrderSend(); s.setOrderid("test6"); s.setSendorderid("3");
		 * s.setStarttime(new Date()); int updateByExampleSelective =
		 * subpackageOrderSendMapper.updateByExampleSelective(s, example);
		 */
		// 原有的数据没有设置的话不会消失
		SubpackageOrderSend s = new SubpackageOrderSend();
		s.setOrderid("test6");
		s.setSendorderid("4");
		s.setStarttime(new Date());
		subpackageOrderSendMapper.updateByPrimaryKeySelective(s);
	}

}
