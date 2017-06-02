package com.xinxing.o.boss.business.provider.api.supplier.utils;

import org.springframework.beans.factory.annotation.Autowired;

import com.xinxing.o.boss.business.provider.api.chinamobile.ChinaMobileSendApiCode;
import com.xinxing.o.boss.business.provider.api.chinatelecom.TeleComSendApiCode;
import com.xinxing.o.boss.business.provider.api.chinaunicom.UniComSendApiCode;
import com.xinxing.o.boss.business.provider.api.supplier.api.SendApi;
import com.xinxing.o.boss.business.provider.other.bigbosscx.api.BigbosscxSendApiImpl;
import com.xinxing.o.boss.business.provider.other.hzycb.api.HzycbSendApiImpl;
import com.xinxing.o.boss.business.provider.other.justtest.api.JusttestSendApiImpl;
import com.xinxing.o.boss.business.provider.other.mhan.api.MHANSendApiImpl;
import com.xinxing.o.boss.business.provider.other.test.DemoTestYDApiImpl;
import com.xinxing.o.boss.business.provider.other.yliang.api.YliangSendApiImpl;
import com.xinxing.o.boss.business.provider.other.zhiqutest.api.ZhiQuTestSendApiImpl;

public class SendApiFactory extends SupplierFactory {
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
	private ZhiQuTestSendApiImpl zhiQuTestSendApi;
	@Autowired
	private MHANSendApiImpl mhanSendApiImpl;

	@Override
	public SendApi createChinaMobileSendApi(String supplier) {
		ChinaMobileSendApiCode apiCode = Enum.valueOf(ChinaMobileSendApiCode.class, supplier);
		SendApi sendApi = null;
		switch (apiCode) {
		case TEST_YD:
			sendApi = (SendApi) demoTestYDApi;
			break;
		case HZYCB_YD:
			sendApi = hzycbSendApi;
			break;
		case JUSTTEST_YD:
			sendApi = justtestSendApi;
			break;
		case BIGBOSSCX_YD:
			sendApi = bigbosscxSendApi;
			break;
		case YLIANG_YD:
			sendApi = yliangSendApi;
			break;
		case MHAN_YD:
			sendApi = mhanSendApiImpl;
			break;
		case ZHIQU_YD:
			sendApi = zhiQuTestSendApi;
			break;
		}
		return sendApi;
	}

	@Override
	public SendApi createTeleComSendApi(String supplier) {
		TeleComSendApiCode apiCode = Enum.valueOf(TeleComSendApiCode.class, supplier);
		SendApi sendApi = null;
		switch (apiCode) {
		case HZYCB_DX:
			sendApi = hzycbSendApi;
			break;
		case JUSTTEST_DX:
			sendApi = justtestSendApi;
			break;
		case BIGBOSSCX_DX:
			sendApi = bigbosscxSendApi;
			break;
		case YLIANG_DX:
			sendApi = yliangSendApi;
			break;
		case MHAN_DX:
			sendApi = mhanSendApiImpl;
			break;
		case ZHIQU_DX:
			sendApi = zhiQuTestSendApi;
			break;
		}
		return sendApi;
	}

	@Override
	public SendApi createUniComSendApi(String supplier) {
		UniComSendApiCode apiCode = Enum.valueOf(UniComSendApiCode.class, supplier);
		SendApi sendApi = null;
		switch (apiCode) {
		case HZYCB_LT:
			sendApi = hzycbSendApi;
			break;
		case JUSTTEST_LT:
			sendApi = justtestSendApi;
			break;
		case BIGBOSSCX_LT:
			sendApi = bigbosscxSendApi;
			break;
		case YLIANG_LT:
			sendApi = yliangSendApi;
			break;
		case MHAN_LT:
			sendApi = mhanSendApiImpl;
			break;
		case ZHIQU_LT :
			sendApi = zhiQuTestSendApi;
			break;
		}
		return sendApi;
	}

}
