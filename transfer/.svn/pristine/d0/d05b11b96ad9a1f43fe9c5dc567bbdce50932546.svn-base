package com.xinxing.transfer.common.ftp;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.log4j.Logger;

/**
 * class name:FtpUtil <BR>
 * class description: please write your description <BR>
 * Remark: <BR>
 * @version 1.00 2011-8-9
 */
public class FtpUtil {
	private Logger log = Logger.getLogger("FTP-Service");

	private String ftpPath;
	private String ftpName;
	private String ftpPassword;
	private String ftpServerIP;

	public FtpUtil(String ftpPath, String ftpName, String ftpPassword, String ftpServerIP) {
		this.ftpPath = ftpPath;
		this.ftpName = ftpName;
		this.ftpPassword = ftpPassword;
		this.ftpServerIP = ftpServerIP;
	}

	/**
	 * Method name: saveInFTP <BR>
	 * Description: 把文件存储在FTP上 <BR>
	 * Remark: <BR>
	 * 
	 * @param FolderName
	 *            文件路径 示例"xxx/xxx/"
	 * @param FileName
	 *            文件名 示例"thefilename"
	 * @param data
	 *            写入的数据 byte[]数组
	 * @return boolean<BR>
	 */
	public boolean saveInFTP(String FolderName, String FileName, byte[] data) {
		boolean flag = false;

		// 创建FTP客户端
		FTPClient FTPConnect = new FTPClient();

		// 输入流用于读取文件
		ByteArrayInputStream bis = null;

		try {
			if (StringUtils.isNotBlank(FolderName) && StringUtils.isNotBlank(FileName)) {

				// 连接
				FTPConnect.connect(this.ftpServerIP);

				// 登录
				if (FTPConnect.login(this.ftpName, this.ftpPassword)) {
					// 切换目录
					if (FTPConnect.changeWorkingDirectory(FolderName)) {

						bis = new ByteArrayInputStream(data);

						// 缓冲设置
						FTPConnect.setBufferSize(1024);

						// 设置文件类型(二进制类型)
						if (FTPConnect.setFileType(FTPClient.BINARY_FILE_TYPE)) {
							// 写入文件并给上文件名
							flag = FTPConnect.storeFile(FileName, bis);
						}
					}else{
						log.info("没有该文件路径");
					}
				} else {
					log.info("登录失败");
				}
			}
		} catch (SocketException e) {
			e.printStackTrace();
			flag = false;
		} catch (IOException e) {
			e.printStackTrace();
			flag = false;
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		} finally {
			try {
				// 关闭输入流
				IOUtils.closeQuietly(bis);
				// 关闭连接
				FTPConnect.disconnect();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return flag;
	}

	/**
	 * Method name: getFromFTP <BR>
	 * Description: 从FTP上读取文件 <BR>
	 * Remark: <BR>
	 * 
	 * @return boolean<BR>
	 */
	public boolean getFromFTP() {
		boolean flag = false;

		// 创建FTP客户端
		FTPClient ftpClient = new FTPClient();
		// 输出流用于输出文件
		FileOutputStream fos = null;

		try {
			// 建立FTP连接
			ftpClient.connect(this.ftpServerIP);
			// 如果登录成功后, 才进行创建输出流
			if (ftpClient.login(this.ftpName, this.ftpPassword)) {
				// FTP文件
				String distinationFile = "/name/xxx/xxx/xxx文件";
				// 实例化输出流
				fos = new FileOutputStream("C:/ParseXML_InFTP.xml");

				// 设置缓冲
				ftpClient.setBufferSize(1024);

				// 设置文件类型(二进制类型)
				if (ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE)) {
					ftpClient.retrieveFile(distinationFile, fos);
					flag = true;
				}
			}
		} catch (SocketException e) {
			e.printStackTrace();
			flag = false;
		} catch (IOException e) {
			e.printStackTrace();
			flag = false;
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		} finally {
			try {
				// 关闭输出流
				IOUtils.closeQuietly(fos);
				// 关闭连接
				ftpClient.disconnect();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return flag;
	}

	public boolean createDirectory() {
		boolean flag = false;

		// 创建FTP客户端
		FTPClient ftpClient = new FTPClient();

		try {
			// 建立FTP连接
			ftpClient.connect(this.ftpServerIP);
			// 如果登录成功后, 才进行操作
			if (ftpClient.login(this.ftpName, this.ftpPassword)) {

				// 切换文件路径, 到FTP上的"NNDD3"文件夹下
				if (this.ftpPath != null && this.ftpPath.compareTo("") != 0 && ftpClient.changeWorkingDirectory(this.ftpPath)) {
					SimpleDateFormat f = new SimpleDateFormat("yyyyMMdd");
					String time = f.format(new Date());

					String FolderName = time + "_ReTransmit";
					ftpClient.makeDirectory(FolderName);
					flag = true;
				}
			}
		} catch (SocketException e) {
			e.printStackTrace();
			flag = false;
		} catch (IOException e) {
			e.printStackTrace();
			flag = false;
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		} finally {
			try {
				// 关闭连接
				ftpClient.disconnect();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return flag;
	}

	public String[] getAllFolderNames() {
		// 创建FTP客户端
		FTPClient ftpClient = new FTPClient();

		try {
			// 建立FTP连接
			ftpClient.connect(this.ftpServerIP);

			// 如果登录成功后, 才进行操作
			if (ftpClient.login(this.ftpName, this.ftpPassword)) {

				// 切换文件路径, 到FTP上的"NNDD3"文件夹下
				if (this.ftpPath != null && this.ftpPath.compareTo("") != 0 && ftpClient.changeWorkingDirectory(this.ftpPath)) {
					// 将当前时间减去2天, 删除的是前两天的数据包
					String time = minusTime();

					String[] allNames = ftpClient.listNames();

					String[] temp = new String[allNames.length];
					// 初始化数组
					for (int j = 0; j < allNames.length; j++) {
						temp[j] = "";
					}

					// 找出要删除文件夹的名称
					for (int i = 0; i < allNames.length; i++) {
						if (allNames[i].substring(0, 8).compareTo(time) <= 0) {
							temp[i] = allNames[i];
						}
					}

					return temp;
				}
			}
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				// 关闭连接
				ftpClient.disconnect();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return null;
	}

	/**
	 * 
	 * Method name: minusTime <BR>
	 * Description: 获取钱两天的时间,如2011-8-1的前两天就是2011-7-30 <BR>
	 * Remark: <BR>
	 * 
	 * @return String<BR>
	 */
	private String minusTime() {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		Date d = new Date();
		String timeMinus2 = df.format(new Date(d.getTime() - 2 * 24 * 60 * 60 * 1000));
		return timeMinus2;
	}

	public static void main(String[] args) {
		FtpUtil ftp = new FtpUtil("/123", "cq", "TaS12Bb", "106.14.18.108");

		String aString = "这是写入的文件";
		byte[] data = aString.getBytes();
		boolean saveInFTP = ftp.saveInFTP("/", "a.txt", data);
		if (saveInFTP) {
			System.out.println("ok");
		} else {
			System.out.println("oooo");
		}
		// FtpUtil.deleteFoldersInFTP();

		// boolean flag = FtpUtil.createDirectory();
		// if (flag) {
		// System.out.println("****** FTP文件夹创建成功 ******");
		// }

		// String FolderName = FtpUtil.ftpPath + "20110809_ReTransmit/";
		// byte[] data = new byte[1024];
		// for (int i = 0; i < data.length; i ++) {
		// data[i] = 'a';
		// }
		// boolean flag = FtpUtil.saveInFTP(FolderName, "2011080912345678"
		// ,data);
		// if (flag) {
		// System.out.println("****** FTP文件夹创建成功 ******");
		// }
	}
}
