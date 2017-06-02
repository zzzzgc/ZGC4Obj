package com.xinxing.o.boss.common.pkg;

import java.util.HashMap;
import java.util.Map;

public class PackageMsg {
	
	private static final String STATE = "state";
	private static final String RESULT = "result";
	private static final String MSG = "msg";

	public static Map<Object, Object> getErrorMsg(String falseMsg) {
		Map<Object, Object> mapCode = new HashMap<>();
		mapCode.put(STATE, false);
		mapCode.put(MSG, falseMsg);
		return mapCode;
	}

	public static Map<Object, Object> getError(int code, String falseMsg) {
		Map<Object, Object> mapCode = new HashMap<>();
		mapCode.put(STATE, false);
		mapCode.put(RESULT, code);
		mapCode.put(MSG, falseMsg);
		return mapCode;
	}

	public static Map<Object, Object> getRightOperCode(Object obj) {
		Map<Object, Object> mapCode = new HashMap<>();
		mapCode.put(STATE, true);
		mapCode.put(RESULT, obj);
		return mapCode;
	}
}