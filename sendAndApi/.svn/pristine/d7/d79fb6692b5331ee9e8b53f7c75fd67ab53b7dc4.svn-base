package com.xinxing.o.boss.business.provider.shcedule;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.xinxing.boss.business.api.domain.SendOrderInfo;
import com.xinxing.boss.business.api.domain.SendOrderResult;
import com.xinxing.boss.business.worker.domain.SupplierCallbackType;
import com.xinxing.boss.interaction.pojo.customer.OrderInfo;
import com.xinxing.boss.interaction.pojo.provider.ProviderInfo;
import com.xinxing.boss.interaction.pojo.provider.ProviderProductInfo;
import com.xinxing.o.boss.business.provider.api.chinamobile.ChinaMobileSendApiCode;
import com.xinxing.o.boss.business.provider.api.chinatelecom.TeleComSendApiCode;
import com.xinxing.o.boss.business.provider.api.chinaunicom.UniComSendApiCode;
import com.xinxing.o.boss.business.provider.other.bigbosscx.api.BigbosscxSendApiImpl;
import com.xinxing.o.boss.business.provider.other.bigbosscxhf.api.BigbosscxhfSendApiImpl;
import com.xinxing.o.boss.business.provider.other.cyue.api.CYUESendApiImpl;
import com.xinxing.o.boss.business.provider.other.dli.api.DLiSendApiImpl;
import com.xinxing.o.boss.business.provider.other.fb.api.FBSendAndApiimpl;
import com.xinxing.o.boss.business.provider.other.hquan.api.HquanSendApiImpl;
import com.xinxing.o.boss.business.provider.other.hzycb.api.HzycbSendApiImpl;
import com.xinxing.o.boss.business.provider.other.jnlt.api.JNLTSendApiImpl;
import com.xinxing.o.boss.business.provider.other.josy.api.JOSYSendApiImpl;
import com.xinxing.o.boss.business.provider.other.justtest.api.JusttestSendApiImpl;
import com.xinxing.o.boss.business.provider.other.kdou.api.KdouSendApiImpl;
import com.xinxing.o.boss.business.provider.other.lliu.api.LliuSendApiImpl;
import com.xinxing.o.boss.business.provider.other.mchuan.api.MCHUANSendApiImpl;
import com.xinxing.o.boss.business.provider.other.mhan.api.MHANSendApiImpl;
import com.xinxing.o.boss.business.provider.other.qgdx.api.QGDXSendApiImpl;
import com.xinxing.o.boss.business.provider.other.qws.api.QwsSendApiImpl;
import com.xinxing.o.boss.business.provider.other.test.DemoTestYDApiImpl;
import com.xinxing.o.boss.business.provider.other.testsdlt.api.TestsdltSendApiImpl;
import com.xinxing.o.boss.business.provider.other.txin.api.TXINSendApiImpl;
import com.xinxing.o.boss.business.provider.other.xcheng.api.XChengSendApiImpl;
import com.xinxing.o.boss.business.provider.other.yliang.api.YliangSendApiImpl;
import com.xinxing.o.boss.business.provider.other.yshang.api.YshangSendApiImpl;
import com.xinxing.o.boss.business.provider.other.zhiqutest.api.ZhiQuTestSendApiImpl;
import com.xinxing.o.boss.common.json.JsonUtils;
import com.xinxing.o.boss.common.time.TimeUtils;
public class ScheduleJobApiImpl implements ScheduleJobApi {
	
	private static final Logger log = Logger.getLogger(ScheduleJobApiImpl.class);
	private final static Logger hzycb = Logger.getLogger("hzycb_Job");
	private final static Logger justtest = Logger.getLogger("justtest_Job");
	private final static Logger bigbosscx = Logger.getLogger("bigbosscx_Job");
	private final static Logger testsdlt = Logger.getLogger("testsdlt_Job");
	private final static Logger yliang = Logger.getLogger("yliang_Job");
	private final static Logger zhiqutest = Logger.getLogger("zhiqu_Job");
	private final static Logger yshangLog = Logger.getLogger("yshang_Job");
	private static final Logger logDemo = Logger.getLogger("DEMO_TEST_YD");
	private static final Logger MHANLog = Logger.getLogger("MHAN_Job");
	private static final Logger cyueLog = Logger.getLogger("cyue_Job");
	private static final Logger kdouLog = Logger.getLogger("kdou_Job");
	private static final Logger MCHUANLog = Logger.getLogger("MCHUAN_Job");
	private static final Logger jNLTLog = Logger.getLogger("jNLT_Job");
	private static final Logger txinLog = Logger.getLogger("txin_Job");
	private static final Logger lliuLog = Logger.getLogger("lliu_Job");
	private static final Logger josyLog = Logger.getLogger("josy_Job");
	private static final Logger qgdxLog = Logger.getLogger("qgdx_Job");
	private static final Logger hquanLog = Logger.getLogger("hquan_Job");
	private final static Logger bigbosscxhfLog = Logger.getLogger("bigbosscxhf_Job");
	private static final Logger qwsLog = Logger.getLogger("qws_Job");
	private static final Logger fbLog = Logger.getLogger("fb_Job");
	private static final Logger dliLog = Logger.getLogger("dli_Job");
	private static final Logger xchengLog = Logger.getLogger("xcheng_Job");
	
	@Autowired
	private DemoTestYDApiImpl demoTestYDApi;
	@Autowired
	private HzycbSendApiImpl hzycbSendApi;
	@Autowired
	private JusttestSendApiImpl justtestSendApi;
	@Autowired
	private BigbosscxSendApiImpl bigbosscxSendApi;
	@Autowired
	private YliangSendApiImpl yliangSendApi;
	@Autowired
	private TestsdltSendApiImpl testsdltSendApi;
	@Autowired
	private YshangSendApiImpl yshangSendApi;
	@Autowired
	private ZhiQuTestSendApiImpl zhiQuTestSendApi;
	@Autowired
	private MHANSendApiImpl mhanSendApiImpl;
	@Autowired
	private CYUESendApiImpl cyueSendApi;
	@Autowired
	private KdouSendApiImpl kdouSendApi;
	@Autowired
	private MCHUANSendApiImpl mchuanSendApi;
	@Autowired
	private JNLTSendApiImpl jNLTSendApi;
	@Autowired
	private TXINSendApiImpl txinSendApi;
	@Autowired
	private LliuSendApiImpl lliuSendApi;
	@Autowired
	private JOSYSendApiImpl josySendApi;
	@Autowired
	private QGDXSendApiImpl qgdxSendApi;
	@Autowired
	private HquanSendApiImpl hquanSendApi;
	@Autowired
	private BigbosscxhfSendApiImpl bigbosscxhfSendApi;
	@Autowired
	private QwsSendApiImpl qwsSendApi;
	@Autowired
	private FBSendAndApiimpl fbSendAndApi;
	@Autowired
	private DLiSendApiImpl dLiSendApi;
	@Autowired
	private XChengSendApiImpl xChengSendApi;
	
	@Autowired
	private com.xinxing.boss.interaction.service.provider.a flowProviderService;
	
	@Autowired
	private com.xinxing.boss.interaction.service.order.a flowOrderService;
	
	@Autowired
	private com.xinxing.boss.cache.a flowCacheService;
	
	@Autowired
	private com.xinxing.boss.business.worker.handle.a flowWorkerService;

	@Override
	public void demoQuery_Job() {
		logDemo.info("-进入Demo订单查询-");
		List<String> aliSuppliers = new ArrayList<>();
		aliSuppliers.add(ChinaMobileSendApiCode.TEST_YD.apiCode);
		String beforeMinutesCallBack = TimeUtils.getNowBeforeMinutes(5);
		String beforeMinutesCharging = TimeUtils.getNowBeforeMinutes(20);
		List<OrderInfo> orderInfoList = getNeed2HandleOrders(aliSuppliers, beforeMinutesCallBack, beforeMinutesCharging);
		if (orderInfoList != null && orderInfoList.size() > 0) {
			List<SendOrderInfo> orderList = buildSendOrders(orderInfoList);
			Map<String, SendOrderResult> mapResult = demoTestYDApi.querys(orderList);
			handleOrders(orderList, mapResult, logDemo);
		}
	}
	@Override
	public void hzycb_Job() {
		logDemo.info("-进入hzycb_Job订单查询-");
		List<String> aliSuppliers = new ArrayList<>();
		aliSuppliers.add(ChinaMobileSendApiCode.HZYCB_YD.apiCode);
		String beforeMinutesCallBack = TimeUtils.getNowBeforeMinutes(5);
		String beforeMinutesCharging = TimeUtils.getNowBeforeMinutes(20);
		List<OrderInfo> orderInfoList = getNeed2HandleOrders(aliSuppliers, beforeMinutesCallBack, beforeMinutesCharging);
		if (orderInfoList != null && orderInfoList.size() > 0) {
			List<SendOrderInfo> orderList = buildSendOrders(orderInfoList);
			Map<String, SendOrderResult> mapResult = hzycbSendApi.querys(orderList);
			handleOrders(orderList, mapResult, hzycb);
		}
	}
	
	@Override
	public void justtest_Job() {
		justtest.info("-进入重庆电信订单查询-");
		List<String> aliSuppliers = new ArrayList<>();
		aliSuppliers.add(ChinaMobileSendApiCode.JUSTTEST_YD.apiCode);
		aliSuppliers.add(UniComSendApiCode.JUSTTEST_LT.apiCode);
		aliSuppliers.add(TeleComSendApiCode.JUSTTEST_DX.apiCode);
		String beforeMinutesCallBack = TimeUtils.getNowBeforeMinutes(1); // 多少分钟前的等待回调
		String beforeMinutesCharging = TimeUtils.getNowBeforeMinutes(20);// 多少分钟前就是充值中
		List<OrderInfo> orderInfoList = getNeed2HandleOrders(aliSuppliers, beforeMinutesCallBack, beforeMinutesCharging);
		if (orderInfoList != null && orderInfoList.size() > 0) {
			List<SendOrderInfo> orderList = buildSendOrders(orderInfoList);
			Map<String, SendOrderResult> mapResult = justtestSendApi.querys(orderList);
			handleOrders(orderList, mapResult, justtest);
		}
	}
	
	@Override
	public void bigbosscx_Job() {
		String name = "大boss宸信";
		bigbosscx.info("-进入" + name + "订单查询-");
		List<String> aliSuppliers = new ArrayList<>();
		aliSuppliers.add(ChinaMobileSendApiCode.BIGBOSSCX_YD.apiCode);
		aliSuppliers.add(UniComSendApiCode.BIGBOSSCX_LT.apiCode);
		aliSuppliers.add(TeleComSendApiCode.BIGBOSSCX_DX.apiCode);
		String beforeMinutesCallBack = TimeUtils.getNowBeforeMinutes(3); // 多少分钟前的等待回调
		String beforeMinutesCharging = TimeUtils.getNowBeforeMinutes(20);// 多少分钟前就是充值中
		List<OrderInfo> orderInfoList = getNeed2HandleOrders(aliSuppliers, beforeMinutesCallBack, beforeMinutesCharging);
		if (orderInfoList != null && orderInfoList.size() > 0) {
			List<SendOrderInfo> orderList = buildSendOrders(orderInfoList);
			Map<String, SendOrderResult> mapResult = bigbosscxSendApi.querys(orderList);
			handleOrders(orderList, mapResult, bigbosscx);
		}
	}
	
	@Override
	public void testsdlt_Job() {
		String name = "测试山东联通";
		testsdlt.info("-进入" + name + "订单查询-");
		List<String> aliSuppliers = new ArrayList<>();
		aliSuppliers.add(ChinaMobileSendApiCode.TESTSDLT_YD.apiCode);
		aliSuppliers.add(UniComSendApiCode.TESTSDLT_LT.apiCode);
		aliSuppliers.add(TeleComSendApiCode.TESTSDLT_DX.apiCode);
		String beforeMinutesCallBack = TimeUtils.getNowBeforeMinutes(1); // 多少分钟前的等待回调
		String beforeMinutesCharging = TimeUtils.getNowBeforeMinutes(20);// 多少分钟前就是充值中
		List<OrderInfo> orderInfoList = getNeed2HandleOrders(aliSuppliers, beforeMinutesCallBack, beforeMinutesCharging);
		if (orderInfoList != null && orderInfoList.size() > 0) {
			List<SendOrderInfo> orderList = buildSendOrders(orderInfoList);
			Map<String, SendOrderResult> mapResult = testsdltSendApi.querys(orderList);
			handleOrders(orderList, mapResult, testsdlt);
		}
	}
	/**
	 * 时间调度每3分钟一次
	 * author:zhc
	 */
	@Override
	public void yliang_Job() {
		String name = "yliang供应商";
		yliang.info("-进入" + name + "订单查询-");
		List<String> aliSuppliers = new ArrayList<>();
		aliSuppliers.add(ChinaMobileSendApiCode.YLIANG_YD.apiCode);
		aliSuppliers.add(UniComSendApiCode.YLIANG_LT.apiCode);
		aliSuppliers.add(TeleComSendApiCode.YLIANG_DX.apiCode);
		//供应商数据库读写分离,写入后有一定延迟,需要晚一点才能查询,蛋疼!!我记住了
		String beforeMinutesCallBack = TimeUtils.getNowBeforeMinutes(20); // 多少分钟前的等待回调
		String beforeMinutesCharging = TimeUtils.getNowBeforeMinutes(25);// 多少分钟前就是充值中
		List<OrderInfo> orderInfoList = getNeed2HandleOrders(aliSuppliers, beforeMinutesCallBack, beforeMinutesCharging);
		if (orderInfoList != null && orderInfoList.size() > 0) {
			List<SendOrderInfo> orderList = buildSendOrders(orderInfoList);
			Map<String, SendOrderResult> mapResult = yliangSendApi.querys(orderList);//查询全部订单
			handleOrders(orderList, mapResult, yliang);//获取所有的订单处理信息
		}
	}
	
	/**
	 * ZHIQU
	 * @author tjm
	 */
	@Override
	public void zhiqu_Job() {
		zhiqutest.info("-进入zhiqu订单查询-");
		List<String> aliSuppliers = new ArrayList<>();
		aliSuppliers.add(ChinaMobileSendApiCode.ZHIQU_YD.apiCode);
		aliSuppliers.add(UniComSendApiCode.ZHIQU_LT.apiCode);
		aliSuppliers.add(TeleComSendApiCode.ZHIQU_DX.apiCode);
		String beforeMinutesCallBack = TimeUtils.getNowBeforeMinutes(3); // 多少分钟前的等待回调
		String beforeMinutesCharging = TimeUtils.getNowBeforeMinutes(20);// 多少分钟前就是充值中
		List<OrderInfo> orderInfoList = getNeed2HandleOrders(aliSuppliers, beforeMinutesCallBack, beforeMinutesCharging);
		if (orderInfoList != null && orderInfoList.size() > 0) {
			List<SendOrderInfo> orderList = buildSendOrders(orderInfoList);
			Map<String, SendOrderResult> mapResult = zhiQuTestSendApi.querys(orderList);
			handleOrders(orderList, mapResult, zhiqutest);
		}
	}
	/*
	 * yshang
	 * @see com.xinxing.o.boss.business.provider.shcedule.ScheduleJobApi#yshang_Job()
	 * @author wuzl
	 */
	@Override
	public void yshang_Job() {
		yshangLog.info("进入YSHANG 订单查询");
		List<String> allSupplier = new ArrayList<String>();
		allSupplier.add(ChinaMobileSendApiCode.YSHANG_YD.apiCode);
		allSupplier.add(UniComSendApiCode.YSHANG_LT.apiCode);
		allSupplier.add(TeleComSendApiCode.YSHANG_DX.apiCode);
		String beforeMinutesCallBack = TimeUtils.getNowBeforeMinutes(1); // 多少分钟前的等待回调
		String beforeMinutesCharging = TimeUtils.getNowBeforeMinutes(20);// 多少分钟前就是充值中
		List<OrderInfo> listOrder = getNeed2HandleOrders(allSupplier, beforeMinutesCallBack, beforeMinutesCharging);
		if(listOrder != null && listOrder.size() > 0) {
			List<SendOrderInfo> listSendOrder = buildSendOrders(listOrder);
			Map<String, SendOrderResult> mapResult = yshangSendApi.querys(listSendOrder);
			//处理订单
			handleOrders(listSendOrder, mapResult, yshangLog);
		}
	}
	/**
	 * author:zhc
	 */
	@Override
	public void MHAN_Job() {
		String name = "MHAN供应商";
		MHANLog.info("-进入" + name + "订单查询-");
		List<String> aliSuppliers = new ArrayList<>();
		aliSuppliers.add(ChinaMobileSendApiCode.MHAN_YD.apiCode);
		aliSuppliers.add(UniComSendApiCode.MHAN_LT.apiCode);
		aliSuppliers.add(TeleComSendApiCode.MHAN_DX.apiCode);
		String beforeMinutesCallBack = TimeUtils.getNowBeforeMinutes(1); // 多少分钟前的等待回调
		String beforeMinutesCharging = TimeUtils.getNowBeforeMinutes(20);// 多少分钟前就是充值中
		List<OrderInfo> orderInfoList = getNeed2HandleOrders(aliSuppliers, beforeMinutesCallBack, beforeMinutesCharging);
		if (orderInfoList != null && orderInfoList.size() > 0) {
			List<SendOrderInfo> orderList = buildSendOrders(orderInfoList);
			Map<String, SendOrderResult> mapResult = mhanSendApiImpl.querys(orderList);
			handleOrders(orderList, mapResult, MHANLog);
		}
	}
	/**
	 * CYUE
	 * @author tjm
	 */
	@Override
	public void cyue_Job() {
		cyueLog.info("-进入cyue订单查询-");
		List<String> aliSuppliers = new ArrayList<>();
		aliSuppliers.add(ChinaMobileSendApiCode.CYUE_YD.apiCode);
		aliSuppliers.add(UniComSendApiCode.CYUE_LT.apiCode);
		aliSuppliers.add(TeleComSendApiCode.CYUE_DX.apiCode);
		//文档要求提单后10分钟再去查询
		String beforeMinutesCallBack = TimeUtils.getNowBeforeMinutes(10); // 多少分钟前的等待回调
		String beforeMinutesCharging = TimeUtils.getNowBeforeMinutes(20); // 多少分钟前就是充值中
		List<OrderInfo> orderInfoList = getNeed2HandleOrders(aliSuppliers, beforeMinutesCallBack, beforeMinutesCharging);
		if (orderInfoList != null && orderInfoList.size() > 0) {
			List<SendOrderInfo> orderList = buildSendOrders(orderInfoList);
			Map<String, SendOrderResult> mapResult = cyueSendApi.querys(orderList);
			handleOrders(orderList, mapResult, cyueLog);
		}
	}

	/**
	 * KDOU
	 * @author tjm
	 */
	@Override
	public void kdou_Job() {
		kdouLog.info("-进入kdou订单查询-");
		List<String> aliSuppliers = new ArrayList<>();
		aliSuppliers.add(ChinaMobileSendApiCode.KDOU_YD.apiCode);
		aliSuppliers.add(UniComSendApiCode.KDOU_LT.apiCode);
		aliSuppliers.add(TeleComSendApiCode.KDOU_DX.apiCode);
		String beforeMinutesCallBack = TimeUtils.getNowBeforeMinutes(3); // 多少分钟前的等待回调
		String beforeMinutesCharging = TimeUtils.getNowBeforeMinutes(20);// 多少分钟前就是充值中
		List<OrderInfo> orderInfoList = getNeed2HandleOrders(aliSuppliers, beforeMinutesCallBack, beforeMinutesCharging);
		if (orderInfoList != null && orderInfoList.size() > 0) {
			List<SendOrderInfo> orderList = buildSendOrders(orderInfoList);
			Map<String, SendOrderResult> mapResult = kdouSendApi.querys(orderList);
			handleOrders(orderList, mapResult, kdouLog);
		}
	}
	
	/**
	 * MCHUAN
	 * @author zgc
	 */
	@Override
	public void MCHUAN_Job() {
		String name = "MCHUAN_Job";
		MCHUANLog.info("-进入" + name + "订单查询-");
		List<String> aliSuppliers = new ArrayList<>();
		aliSuppliers.add(ChinaMobileSendApiCode.MCHUAN_YD.apiCode);
		aliSuppliers.add(UniComSendApiCode.MCHUAN_LT.apiCode);
		aliSuppliers.add(TeleComSendApiCode.MCHUAN_DX.apiCode);
		String beforeMinutesCallBack = TimeUtils.getNowBeforeMinutes(1); // 多少分钟前的等待回调
		String beforeMinutesCharging = TimeUtils.getNowBeforeMinutes(20);// 多少分钟前就是充值中
		List<OrderInfo> orderInfoList = getNeed2HandleOrders(aliSuppliers, beforeMinutesCallBack, beforeMinutesCharging);
		if (orderInfoList != null && orderInfoList.size() > 0) {
			List<SendOrderInfo> orderList = buildSendOrders(orderInfoList);
			Map<String, SendOrderResult> mapResult = mchuanSendApi.querys(orderList);
			handleOrders(orderList, mapResult, MCHUANLog);
		}
	}
	/*
	 * JNLT
	 * @see com.xinxing.o.boss.business.provider.shcedule.ScheduleJobApi#jNLT_Job()
	 * @author wuzl
	 */
	@Override
	public void jNLT_Job() {
		jNLTLog.info("进入JNLT订单查询>>");
		List<String> allSupplier = new ArrayList<String>();
		allSupplier.add(ChinaMobileSendApiCode.JNLT_YD.apiCode);
		allSupplier.add(UniComSendApiCode.JNLT_LT.apiCode);
		allSupplier.add(TeleComSendApiCode.JNLT_DX.apiCode);
		String beforeMinutesCallBack = TimeUtils.getNowBeforeMinutes(1); // 多少分钟前的等待回调
		String beforeMinutesCharging = TimeUtils.getNowBeforeMinutes(20);// 多少分钟前就是充值中
		List<OrderInfo> listOrder = getNeed2HandleOrders(allSupplier, beforeMinutesCallBack, beforeMinutesCharging);
		if(listOrder != null && listOrder.size() > 0) {
			List<SendOrderInfo> listSendOrder = buildSendOrders(listOrder);
			Map<String, SendOrderResult> mapResult = jNLTSendApi.querys(listSendOrder);
			//处理订单
			handleOrders(listSendOrder, mapResult, jNLTLog);
		}
	}
	/**
	 * TXIN
	 * @author tjm
	 */
	@Override
	public void txin_Job() {
		txinLog.info("-进入txin订单查询-");
		List<String> aliSuppliers = new ArrayList<>();
		aliSuppliers.add(ChinaMobileSendApiCode.TXIN_YD.apiCode);
		aliSuppliers.add(UniComSendApiCode.TXIN_LT.apiCode);
		aliSuppliers.add(TeleComSendApiCode.TXIN_DX.apiCode);
		String beforeMinutesCallBack = TimeUtils.getNowBeforeMinutes(3); 
		String beforeMinutesCharging = TimeUtils.getNowBeforeMinutes(20);
		List<OrderInfo> orderInfoList = getNeed2HandleOrders(aliSuppliers, beforeMinutesCallBack, beforeMinutesCharging);
		if (orderInfoList != null && orderInfoList.size() > 0) {
			List<SendOrderInfo> orderList = buildSendOrders(orderInfoList);
			Map<String, SendOrderResult> mapResult = txinSendApi.querys(orderList);
			handleOrders(orderList, mapResult, txinLog);
		}
	}

	/**
	 * LLIU
	 * @author tjm
	 */
	@Override
	public void lliu_Job() {
		lliuLog.info("-进入lliu订单查询-");
		List<String> aliSuppliers = new ArrayList<>();
		aliSuppliers.add(ChinaMobileSendApiCode.LLIU_YD.apiCode);
		aliSuppliers.add(UniComSendApiCode.LLIU_LT.apiCode);
		aliSuppliers.add(TeleComSendApiCode.LLIU_DX.apiCode);
		String beforeMinutesCallBack = TimeUtils.getNowBeforeMinutes(3); // 多少分钟前的等待回调
		String beforeMinutesCharging = TimeUtils.getNowBeforeMinutes(20);// 多少分钟前就是充值中
		List<OrderInfo> orderInfoList = getNeed2HandleOrders(aliSuppliers, beforeMinutesCallBack, beforeMinutesCharging);
		if (orderInfoList != null && orderInfoList.size() > 0) {
			List<SendOrderInfo> orderList = buildSendOrders(orderInfoList);
			Map<String, SendOrderResult> mapResult = lliuSendApi.querys(orderList);
			handleOrders(orderList, mapResult, lliuLog);
		}
	}
	
	/**
	 * JOSY
	 * @author tjm
	 */
	@Override
	public void josy_Job() {
		josyLog.info("-进入josy订单查询-");
		List<String> aliSuppliers = new ArrayList<>();
		aliSuppliers.add(ChinaMobileSendApiCode.JOSY_YD.apiCode);
		aliSuppliers.add(UniComSendApiCode.JOSY_LT.apiCode);
		aliSuppliers.add(TeleComSendApiCode.JOSY_DX.apiCode);
		String beforeMinutesCallBack = TimeUtils.getNowBeforeMinutes(3); // 多少分钟前的等待回调
		String beforeMinutesCharging = TimeUtils.getNowBeforeMinutes(20);// 多少分钟前就是充值中
		List<OrderInfo> orderInfoList = getNeed2HandleOrders(aliSuppliers, beforeMinutesCallBack, beforeMinutesCharging);
		if (orderInfoList != null && orderInfoList.size() > 0) {
			List<SendOrderInfo> orderList = buildSendOrders(orderInfoList);
			Map<String, SendOrderResult> mapResult = josySendApi.querys(orderList);
			handleOrders(orderList, mapResult, josyLog);
		}
	}
	/**
	 * QGDX
	 * @author tjm
	 */
	@Override
	public void qgdx_Job() {
		qgdxLog.info("-进入qgdx订单查询-");
		List<String> allSuppliers = new ArrayList<>();
		allSuppliers.add(ChinaMobileSendApiCode.QGDX_YD.apiCode);
		allSuppliers.add(UniComSendApiCode.QGDX_LT.apiCode);
		allSuppliers.add(TeleComSendApiCode.QGDX_DX.apiCode);
		String beforeMinutesCallBack = TimeUtils.getNowBeforeMinutes(3); // 多少分钟前的等待回调
		String beforeMinutesCharging = TimeUtils.getNowBeforeMinutes(20);// 多少分钟前就是充值中
		List<OrderInfo> orderInfoList = getNeed2HandleOrders(allSuppliers, beforeMinutesCallBack, beforeMinutesCharging);
		if (orderInfoList != null && orderInfoList.size() > 0) {
			List<SendOrderInfo> orderList = buildSendOrders(orderInfoList);
			Map<String, SendOrderResult> mapResult = qgdxSendApi.querys(orderList);
			handleOrders(orderList, mapResult, qgdxLog);
		}
	}
	/**
	 * HQUAN
	 * @author tjm
	 */
	@Override
	public void hquan_Job() {
		hquanLog.info("-进入hquan订单查询-");
		List<String> allSuppliers = new ArrayList<>();
		allSuppliers.add(ChinaMobileSendApiCode.HQUAN_YD.apiCode);
		allSuppliers.add(UniComSendApiCode.HQUAN_LT.apiCode);
		allSuppliers.add(TeleComSendApiCode.HQUAN_DX.apiCode);
		String beforeMinutesCallBack = TimeUtils.getNowBeforeMinutes(3); // 多少分钟前的等待回调
		String beforeMinutesCharging = TimeUtils.getNowBeforeMinutes(20);// 多少分钟前就是充值中
		List<OrderInfo> orderInfoList = getNeed2HandleOrders(allSuppliers, beforeMinutesCallBack, beforeMinutesCharging);
		if (orderInfoList != null && orderInfoList.size() > 0) {
			List<SendOrderInfo> orderList = buildSendOrders(orderInfoList);
			Map<String, SendOrderResult> mapResult = hquanSendApi.querys(orderList);
			handleOrders(orderList, mapResult, hquanLog);
		}
	}
	/**
	 * bigbosscxhf
	 * @author tjm
	 */
	@Override
	public void bigbosscxhf_Job() {
		bigbosscxhfLog.info("-进入bigbosscxhf订单查询-");
		List<String> allSuppliers = new ArrayList<>();
		allSuppliers.add(ChinaMobileSendApiCode.BIGBOSSCXHF_YD.apiCode);
		allSuppliers.add(UniComSendApiCode.BIGBOSSCXHF_LT.apiCode);
		allSuppliers.add(TeleComSendApiCode.BIGBOSSCXHF_DX.apiCode);
		String beforeMinutesCallBack = TimeUtils.getNowBeforeMinutes(3); // 多少分钟前的等待回调
		String beforeMinutesCharging = TimeUtils.getNowBeforeMinutes(20);// 多少分钟前就是充值中
		List<OrderInfo> orderInfoList = getNeed2HandleOrders(allSuppliers, beforeMinutesCallBack, beforeMinutesCharging);
		if (orderInfoList != null && orderInfoList.size() > 0) {
			List<SendOrderInfo> orderList = buildSendOrders(orderInfoList);
			Map<String, SendOrderResult> mapResult = bigbosscxhfSendApi.querys(orderList);
			handleOrders(orderList, mapResult, bigbosscxhfLog);
		}
	}
	/**
	 * qws
	 * @author tjm
	 */
	@Override
	public void qws_Job() {
		qwsLog.info("-进入qws订单查询-");
		List<String> allSuppliers = new ArrayList<>();
		allSuppliers.add(ChinaMobileSendApiCode.QWS_YD.apiCode);
		allSuppliers.add(UniComSendApiCode.QWS_LT.apiCode);
		allSuppliers.add(TeleComSendApiCode.QWS_DX.apiCode);
		//这个接口时间特殊设置
		String beforeMinutesCallBack = TimeUtils.getNowBeforeMinutes(30); // 多少分钟前的等待回调
		String beforeMinutesCharging = TimeUtils.getNowBeforeMinutes(50);// 多少分钟前就是充值中
		List<OrderInfo> orderInfoList = getNeed2HandleOrders(allSuppliers, beforeMinutesCallBack, beforeMinutesCharging);
		if (orderInfoList != null && orderInfoList.size() > 0) {
			List<SendOrderInfo> orderList = buildSendOrders(orderInfoList);
			Map<String, SendOrderResult> mapResult = qwsSendApi.querys(orderList);
			handleOrders(orderList, mapResult, qwsLog);
		}
	}
	
	/**
	 * FB---i
	 * @author ZGC
	 */
	@Override
	public void fb_Job() {
		fbLog.info("-进入FB订单查询-");
		List<String> allSuppliers = new ArrayList<>();
		allSuppliers.add(ChinaMobileSendApiCode.FB_YD.apiCode);
		allSuppliers.add(UniComSendApiCode.FB_LT.apiCode);
		allSuppliers.add(TeleComSendApiCode.FB_DX.apiCode);
		String beforeMinutesCallBack = TimeUtils.getNowBeforeMinutes(1); // 多少分钟前的等待回调
		String beforeMinutesCharging = TimeUtils.getNowBeforeMinutes(20);// 多少分钟前就是充值中
		List<OrderInfo> orderInfoList = getNeed2HandleOrders(allSuppliers, beforeMinutesCallBack, beforeMinutesCharging);
		if (orderInfoList != null && orderInfoList.size() > 0) {
			List<SendOrderInfo> orderList = buildSendOrders(orderInfoList);
			Map<String, SendOrderResult> mapResult = fbSendAndApi.querys(orderList);
			handleOrders(orderList, mapResult, fbLog);
		}
	}
	
	/**dli
	 * @author tjm
	 */
	@Override
	public void dli_Job() {
		dliLog.info("-进入dli订单查询-");
		List<String> allSuppliers = new ArrayList<>();
		allSuppliers.add(ChinaMobileSendApiCode.DLI_YD.apiCode);
		allSuppliers.add(UniComSendApiCode.DLI_LT.apiCode);
		allSuppliers.add(TeleComSendApiCode.DLI_DX.apiCode);
																		 //文档建议发单5分钟后开始查询
		String beforeMinutesCallBack = TimeUtils.getNowBeforeMinutes(5); // 多少分钟前的等待回调
		String beforeMinutesCharging = TimeUtils.getNowBeforeMinutes(20);// 多少分钟前就是充值中
		List<OrderInfo> orderInfoList = getNeed2HandleOrders(allSuppliers, beforeMinutesCallBack, beforeMinutesCharging);
		if (orderInfoList != null && orderInfoList.size() > 0) {
			List<SendOrderInfo> orderList = buildSendOrders(orderInfoList);
			Map<String, SendOrderResult> mapResult = dLiSendApi.querys(orderList);
			handleOrders(orderList, mapResult, dliLog);
		}
	}
	/**xcheng
	 * @author tjm
	 */
	@Override
	public void xcheng_Job() {
		xchengLog.info("-进入xcheng订单查询-");
		List<String> allSuppliers = new ArrayList<>();
		allSuppliers.add(ChinaMobileSendApiCode.XCHENG_YD.apiCode);
		allSuppliers.add(UniComSendApiCode.XCHENG_LT.apiCode);
		allSuppliers.add(TeleComSendApiCode.XCHENG_DX.apiCode);
		String beforeMinutesCallBack = TimeUtils.getNowBeforeMinutes(3); // 多少分钟前的等待回调
		String beforeMinutesCharging = TimeUtils.getNowBeforeMinutes(20);// 多少分钟前就是充值中
		List<OrderInfo> orderInfoList = getNeed2HandleOrders(allSuppliers, beforeMinutesCallBack, beforeMinutesCharging);
		if (orderInfoList != null && orderInfoList.size() > 0) {
			List<SendOrderInfo> orderList = buildSendOrders(orderInfoList);
			Map<String, SendOrderResult> mapResult = xChengSendApi.querys(orderList);
			handleOrders(orderList, mapResult, xchengLog);
		}
	}
	
	/**
	 * 处理订单信息
	 * 
	 * @param orderInfoList
	 * @param mapResult
	 */
	private void handleOrders(List<SendOrderInfo> sendOrderInfos, Map<String, SendOrderResult> mapResult, Logger log) {
		if (mapResult != null && mapResult.size() > 0) {
			for (int i = 0, len = sendOrderInfos.size(); i < len; i++) {
				SendOrderInfo info = sendOrderInfos.get(i);
				SendOrderResult result = mapResult.get(info.getOrderId());  
				if (result != null) {
					ProviderInfo providerInfo = flowProviderService.o(info.getProviderId());
					flowWorkerService.a(result, info.getOrderId(), providerInfo.getSupplier(), SupplierCallbackType.OUR_ID);
				} else {
					String orderIdStr = com.xinxing.boss.common.utils.a.f(info.getOrderId());
					int id = Integer.parseInt(orderIdStr);
					flowOrderService.d(id, "在对方系统查不到订单");
				}				

			}
		} else{
			log.info("返回错误,订单返回信息错误,请找技术");
		}
	}
	
	/**
	 * 获取需要定时任务处理的订单
	 * 
	 * @param supplers
	 *            供货商
	 * @param beforeMinutesCallBack
	 *            多少分钟前就等待回调
	 * @param beforeMinutesCharging
	 *            多少分钟前就是充值中
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<OrderInfo> getNeed2HandleOrders(List<String> supplers, String beforeMinutesCallBack, String beforeMinutesCharging) {
		List<OrderInfo> orderInfoListCallBack = flowOrderService.a(supplers, beforeMinutesCallBack, false);
		List<OrderInfo> orderInfoListCharging = flowOrderService.a(supplers, beforeMinutesCharging, true);
		List<OrderInfo> orderInfoList = null;
		if (orderInfoListCallBack != null && orderInfoListCallBack.size() > 0) {
			orderInfoList = new ArrayList<>();
			orderInfoList.addAll(orderInfoListCallBack);
		}
		if (orderInfoListCharging != null && orderInfoListCharging.size() > 0) {
			if (orderInfoList == null) {
				orderInfoList = new ArrayList<>();
			}
			orderInfoList.addAll(orderInfoListCharging);
		}
		return orderInfoList;
	}

	/**
	 * 构建发送订单
	 * 
	 * @param orders
	 * @return
	 */
	private List<SendOrderInfo> buildSendOrders(List<OrderInfo> orderInfoList) {
		List<SendOrderInfo> orderList = new ArrayList<>();
		for (int i = 0, len = orderInfoList.size(); i < len; i++) {
			OrderInfo order = orderInfoList.get(i);
			ProviderInfo providerInfo = flowProviderService.o(order.getProviderId());
			ProviderProductInfo providerProductInfo = null;
			try {
				providerProductInfo = flowCacheService.a(order.getProviderCategoryId(), providerInfo.getId());
			} catch (Exception e) {
				log.info("构建发送订单异常:" + order.getId() + ":" + JsonUtils.obj2Json(providerProductInfo) + e.getMessage(), e);
				log.error("构建发送订单异常:" + order.getId() + e.getMessage(), e);
			}
			String productCode = "";
			if (providerProductInfo != null) {
				productCode = providerProductInfo.getProductCode();
			} else {
				log.info("----" + order.getId() + "-------供货商产品为空");
			}
			SendOrderInfo sendOrderInfo = new SendOrderInfo(order.getOperator(), order.getId() + "", null, order.getPhone(),
					providerInfo.getSupplier(), order.getReceiveTime(), order.getChargeCount(), order.getProviderKey(), productCode);
			sendOrderInfo.setProviderId(providerInfo.getId());
			orderList.add(sendOrderInfo);
		}
		return orderList;
	}
}
