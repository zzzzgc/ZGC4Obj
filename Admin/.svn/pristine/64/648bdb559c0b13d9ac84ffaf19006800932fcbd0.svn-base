package xinxing.boss.admin.common.download;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;

import xinxing.boss.admin.common.utils.json.JsonUtils;
import xinxing.boss.admin.common.utils.time.DateUtils;
import xinxing.boss.admin.system.user.domain.User;
import xinxing.boss.admin.system.user.utils.UserUtil;

public class DownLoadUtil {
	public static void downLoad(HttpServletResponse response, String fileName, String realUrl,
			Logger logger) throws UnsupportedEncodingException, FileNotFoundException, IOException {
		logger.info("下载地址:" + realUrl);
		String ohterUrl = "F:/Users/admin/Workspaces/MyEclipse Professional 2014/.metadata/.me_tcat/webapps/XinXingBossAdmin/WEB-INF/uploadCSV/CustomerInfo_2016-05-13.zip";
		List<String> list = new ArrayList<>();
		list.add(realUrl);
		list.add(ohterUrl);
		response.setContentType("multipart/form-data");
		response.addHeader("Content-Disposition",
				"attachment;filename=" + new String(fileName.getBytes("UTF-8"), "ISO8859-1"));
		FileInputStream in = null;
		OutputStream out = response.getOutputStream();
		try {
			for (String url : list) {
				in = new FileInputStream(url);
				int b = 0;
				byte[] buffer = new byte[512]; 
				while (b != -1) {
					b = in.read(buffer);
					//4.写到输出流(out)中  
					out.write(buffer, 0, b);
				}
				in.close();
			}

			out.close();
			out.flush();

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
					in = null;
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
	}
	
	public static void downloadZip(HttpServletResponse response, List<String> fileUrls, String zipName,Logger logger)
            throws IOException {
        logger.info("下载zip开始时间:"+DateUtils.formatDateTime(new Date()));
        User user = UserUtil.getCurrentUser();
        if(user!=null){
            logger.info("下载zip的人:"+user.getLoginName()+",id为"+user.getId()+"下载的文件:"+JsonUtils.obj2Json(fileUrls));
        }
        logger.info("下载的数据地址:");
        for (String string : fileUrls) {
            logger.info(string);
        }
        response.setContentType("multipart/form-data");
//        response.setContentType("text/plain");   
        response.addHeader("Content-Disposition",
                "attachment;filename=" + new String(zipName.getBytes("UTF-8"), "ISO8859-1"));
        OutputStream os =new BufferedOutputStream(response.getOutputStream());
        ZipOutputStream out = new ZipOutputStream(os);
        
        BufferedInputStream in = null; 
        try {
            int len;
            for (int i = 0; i < fileUrls.size(); i++) {
                File file = new File(fileUrls.get(i));
                if (!file.isFile())
                    continue;
                ZipEntry ze = new ZipEntry(file.getName());
                out.putNextEntry(ze);
                in = new BufferedInputStream(new FileInputStream(file));
                byte[] buf = new byte[8192];
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
            }
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
//          out.closeEntry();
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        logger.info("下载zip结束时间:"+DateUtils.formatDateTime(new Date()));
    }
}
