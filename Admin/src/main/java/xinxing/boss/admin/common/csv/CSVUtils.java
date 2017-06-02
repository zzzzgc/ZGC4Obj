package xinxing.boss.admin.common.csv;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;

import xinxing.boss.admin.common.utils.base.BaseUtil;
import xinxing.boss.admin.common.zip.ZipUtil;

public class CSVUtils {
	
	/**
	 * 导出
	 * 
	 * @param file
	 *            csv文件(路径+文件名)，csv文件不存在会自动创建
	 * @param dataList
	 *            数据
	 * @return
	 */
	public static boolean exportCsv(File file, StringBuilder sb, Logger logger) {
		boolean isSucess = false;
		FileOutputStream out = null;
		OutputStreamWriter osw = null;
		BufferedWriter bw = null;
		try {
			out = new FileOutputStream(file);
			out.write(sb.toString().getBytes("GBK")); //输出GBK 格式的字节，用于适应excel 打开会乱码的问题
			/*osw = new OutputStreamWriter(out);
			
			bw = new BufferedWriter(osw);
			bw.append(new String(sb.toString().getBytes(),"GBK"));*/
			isSucess = true;
		} catch (Exception e) {
			isSucess = false;
		} finally {
			if (bw != null) {
				try {
					bw.close();
					bw = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (osw != null) {
				try {
					osw.close();
					osw = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (out != null) {
				try {
					out.close();
					out = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return isSucess;
	}

	/**
	 * 导入
	 * 
	 * @param file
	 *            csv文件(路径+文件)
	 * @return
	 */
	public static List<String> importCsv(File file) {
		List<String> dataList = new ArrayList<String>();

		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(file));
			String line = "";
			while ((line = br.readLine()) != null) {
				dataList.add(line);
			}
		} catch (Exception e) {
		} finally {
			if (br != null) {
				try {
					br.close();
					br = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return dataList;
	}

	/**
	 * 
	 * @param sb
	 * @param csvUrl
	 * @param fileName
	 * @param needZip
	 * @param logger
	 */
	public static void updateByCSV(StringBuilder sb, String csvUrl, String fileName, Boolean needZip, Logger logger) {
		Date startDate = new Date();
		String parthArg = BaseUtil.getBackUrl();
		parthArg = parthArg + "/" + csvUrl + "/";// linux
		File file = new File(parthArg);
		if (!file.exists() && !file.isDirectory()) {
			file.mkdirs();
		}
		file = new File(parthArg + fileName);
		logger.info("导出csv开始,路径:::::" + parthArg + fileName);
		boolean isSuccess = CSVUtils.exportCsv(file, sb, logger);
		Boolean isZipSuccess = true;
		if (isSuccess && needZip) {
			// 将csv压缩成zip再删除
			isZipSuccess = ZipUtil.ZipFile(parthArg + fileName, parthArg + fileName.replaceAll("\\.csv", "") + ".zip");

		}
		String result = isZipSuccess == true ? "成功" : "失败";
		Date endDate = new Date();
		logger.info("导出CSV:" + parthArg + fileName + ",耗时:" + (startDate.getTime() - endDate.getTime()) / 1000 + "秒" + "导出csv:" + result);
	}
	/**
	 * 
	 * @param path 文件地址
	 * @param content  追加文件内容
	 */
	public  static void appendCsv(String path,String content) {
		FileOutputStream fos = null;
		FileChannel fc_out = null;
		try {
			fos = new FileOutputStream(path, true);
			fc_out = fos.getChannel();
			// 解决乱码问题
			ByteBuffer buf = ByteBuffer.wrap(content.getBytes("GBK"));
			buf.put(content.getBytes("GBK"));
			buf.flip();
			fc_out.write(buf);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != fc_out) {
				try {
					fc_out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (null != fos) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}