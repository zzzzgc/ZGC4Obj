package com.xinxing.transfer.common.resource;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;


/**
 * 取Properties数据
 * 
 * @author AIXIANG
 * 
 */
public class Constants {
	@Autowired
	private static ReloadableResourceBundleMessageSource messageSource;

	private static Locale zh_CN = new Locale("zh", "CN");
	private static Boolean isLocalTest = null;

	public static String testgetString(String key) {
		String value = "";
		Properties pps = new Properties();
		String before = key.split("_")[0];
		String file1 = "F:/Users/admin/Workspaces/space_eclipse2/XinXingBoss Maven Webapp/src/main/webapp/WEB-INF/config/properties/provider"
				+ "/" + before + "_setting.properties";
		String file2 = "E:/TestProperties/"+ before + "_setting.properties";
		String filePath = null;
		List<String> filePathList = new ArrayList<String>();
		filePathList.add(file1);
		filePathList.add(file2);
		for (String file : filePathList) {
			if (new File(file).isFile()) {
				filePath = file;
				break;
			}
		}
		
		if ( !(new File(filePath).isFile())) {
			return getString(key);
		} else {
			try {
				InputStream in = new BufferedInputStream(new FileInputStream(
						filePath));
				pps.load(in);
				value = pps.getProperty(key);
			} catch (IOException e) {
			}
		}
		return value;
	}

	public static boolean isLocalTest() {
		try {
			if (isLocalTest == null) {
				String filePath = "F:/isLocalTest.txt";
				if ((new File(filePath).isFile())) {
					isLocalTest = true;
				} else
					isLocalTest = false;
			}
		} catch (Exception e) {
			isLocalTest = false;
		}
		return isLocalTest;
	}

	public static String getString(String key) {
		return messageSource.getMessage(key, null, null, zh_CN);
	}

	public static Integer getInteger(String key) {
		Integer result = null;
		try {
			String msg = messageSource.getMessage(key, null, null, zh_CN);
			result = Integer.parseInt(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static Double getDouble(String key) {
		Double result = null;
		try {
			String msg = messageSource.getMessage(key, null, null, zh_CN);
			result = Double.parseDouble(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static Boolean getBoolean(String key) {
		Boolean result = null;
		try {
			String msg = messageSource.getMessage(key, null, null, zh_CN);
			result = Boolean.parseBoolean(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public ReloadableResourceBundleMessageSource getMessageSource() {
		return messageSource;
	}

	public void setMessageSource(
			ReloadableResourceBundleMessageSource messageSource) {
		Constants.messageSource = messageSource;
	}

}
