package com.xinxing.o.boss.business.provider.api.supplier.utils;

import com.xinxing.o.boss.business.provider.api.supplier.api.SendApi;

public abstract class SupplierFactory {
	public abstract SendApi createChinaMobileSendApi(String supplier);
	public abstract SendApi createTeleComSendApi(String supplier);
    public abstract SendApi createUniComSendApi(String supplier);
}
