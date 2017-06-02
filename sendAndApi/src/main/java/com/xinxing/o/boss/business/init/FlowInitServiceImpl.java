package com.xinxing.o.boss.business.init;

import org.springframework.beans.factory.annotation.Autowired;

import com.xinxing.flow.api.ChinaMobileThread;
import com.xinxing.flow.api.ChinaTelecomThread;
import com.xinxing.flow.api.ChinaUnicomThread;
import com.xinxing.flow.api.ManualThread;

public class FlowInitServiceImpl implements FlowInitService {


	@Autowired
	private com.xinxing.boss.cache.a a;
	
	@Autowired
	private ChinaMobileThread chinaMobileThread;
	@Autowired
	private ChinaTelecomThread chinaTelecomThread;
	@Autowired
	private ChinaUnicomThread chinaUnicomThread;
	@Autowired
	private ManualThread manualThread;
	
	@Override
	public void initApiService() {
		a.b();
		a.g();
		a.d();
		a.h();
	}
	
	@Override
	public void initSendService() {
		a.c();
		a.a();
		a.b();
		a.d();
		a.g();
		a.e();
		a.h();
		chinaMobileThread.initChinaMobileThread();
		chinaTelecomThread.initChinaTelecomThread();
		chinaUnicomThread.initChinaUnicomThread();
		manualThread.initManualThread();
	}

}
