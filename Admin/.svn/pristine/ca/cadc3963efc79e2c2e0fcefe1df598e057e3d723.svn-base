package xinxing.boss.admin.common.zip;

import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.apache.xmlbeans.impl.common.IOUtil;

public class ZipUtil {
	public static Boolean ZipFile(final String filepath, final String zippath) {
		new Thread(new Runnable() {
			public void run() {

				Boolean isSuccess = true;
				InputStream input = null;
				ZipOutputStream zipOut = null;
				try {
					File file = new File(filepath);
					File zipFile = new File(zippath);
					input = new FileInputStream(file);
					zipOut = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(zipFile)));
					zipOut.putNextEntry(new ZipEntry(file.getName()));
					int temp = 0;
					while ((temp = input.read()) != -1) {
						zipOut.write(temp);
					}
					input.close();
					zipOut.close();
				} catch (Exception e) {
					isSuccess = false;
					e.printStackTrace();

				} finally {
					if (input != null) {
						try {
							input.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
						input = null;
					}
					if (zipOut != null) {
						try {
							zipOut.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
						zipOut = null;
					}

				}
				if (isSuccess) {
					File f = new File(filepath);
					if (f.exists())
						f.delete();
				} else
					throw new RuntimeException("生成csv文件成功但压缩成zip失败");

			}
		}).start();
		return true;
	}

	/**
	 * 解压文件
	 * 
	 * @param filePath
	 *            压缩文件路径
	 */
	public static Boolean unzip(String filePath) {
		File source = new File(filePath);
		Boolean isSuccess = true;
		if (source.exists()) {
			ZipInputStream zis = null;
			BufferedOutputStream bos = null;
			try {
				zis = new ZipInputStream(new FileInputStream(source));
				ZipEntry entry = null;
				while ((entry = zis.getNextEntry()) != null && !entry.isDirectory()) {
					File target = new File(source.getParent(), entry.getName());
					if (!target.getParentFile().exists()) {
						// 创建文件父目录
						target.getParentFile().mkdirs();
					}
					// 写入文件
					bos = new BufferedOutputStream(new FileOutputStream(target));
					int read = 0;
					byte[] buffer = new byte[1024 * 10];
					while ((read = zis.read(buffer, 0, buffer.length)) != -1) {
						bos.write(buffer, 0, read);
					}
					bos.flush();
				}
				zis.closeEntry();
			} catch (IOException e) {
				isSuccess = false;
				throw new RuntimeException(e);
			} finally {
				closeQuietly(zis, bos);
			}
		}
		return isSuccess;
	}

	/**
	 * 关闭一个或多个流对象
	 * 
	 * @param closeables
	 *            可关闭的流对象列表
	 */
	public static void closeQuietly(Closeable... closeables) {
		try {
			close(closeables);
		} catch (IOException e) {
			// do nothing
		}
	}

	/**
	 * 关闭一个或多个流对象
	 * 
	 * @param closeables
	 *            可关闭的流对象列表
	 * @throws IOException
	 */
	public static void close(Closeable... closeables) throws IOException {
		if (closeables != null) {
			for (Closeable closeable : closeables) {
				if (closeable != null) {
					closeable.close();
				}
			}
		}
	}
}
