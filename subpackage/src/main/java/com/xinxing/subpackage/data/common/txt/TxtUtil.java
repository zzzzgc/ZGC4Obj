package com.xinxing.subpackage.data.common.txt;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.log4j.Logger;

public class TxtUtil {
	
	/**生成txt文件
	 * @param sb
	 * @param path
	 * @param fileName
	 * @param yesterday 
	 * @param logger
	 */
	public static void createTXT(StringBuilder sb, String path, String fileName, Date yesterday, Logger logger) {
		Date startDate = new Date();
		
		String dateUrl = DateFormatUtils.format(yesterday, "yyyy"+File.separator+"MM"+File.separator+"dd");
		
		path+=File.separator+dateUrl;
		
		File file = new File(path);
		
		boolean setWritable = file.setWritable(true, false);
		logger.info("获取写入权限"+(setWritable==true?"成功":"失败"));
		
		//没有该目录则生成该目录
		if (!file.exists() && !file.isDirectory()) {
			file.mkdirs();
		}
		
		file = new File(path +File.separator+ fileName);
		
		logger.info("txt开始生成,路径>>>>" + path  +File.separator+ fileName);
		boolean isSuccess = TxtUtil.writeTxt(file, sb, logger);
		
		String result = isSuccess == true ? "成功" : "失败";
		
		
		Date endDate = new Date();
		logger.info("生成txt:" + path + fileName + ",耗时:" + (startDate.getTime() - endDate.getTime()) / 1000 + "秒" + "结果:" + result);
	}
	/**
	 * 写入txt文件
	 * @param file
	 * @param sb
	 * @param logger
	 * @return
	 */
	public static boolean writeTxt(File file, StringBuilder sb, Logger logger) {
		boolean isSucess = false;
		//FileOutputStream out = null;
		//OutputStreamWriter osw = null;
		//BufferedWriter bw = null;
		try (FileOutputStream out = new FileOutputStream(file);) {
			// out = new FileOutputStream(file);
			out.write(sb.toString().getBytes("GBK"));
			isSucess = true;
		} catch (Exception e) {
			isSucess = false;
			logger.warn("writeTxt出现异常",e);
		}
		return isSucess;
	}
}
