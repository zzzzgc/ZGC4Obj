package xinxing.boss.admin.common.fileupload;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@Controller
public class CmdFileUpload {
	
	private Logger logger = Logger.getLogger(CmdFileUpload.class);

	@RequestMapping("/file_upload.do")
	public String upload2(HttpServletRequest request,HttpServletResponse response) throws IllegalStateException,IOException {
		// 创建一个通用的多部分解析器
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		// 判断 request 是否有文件上传,即多部分请求
		if (multipartResolver.isMultipart(request)) {
			// 转换成多部分request
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			StringBuilder fileNameBuilder = new StringBuilder();
			// 取得request中的所有文件名
			Iterator<String> iter = multiRequest.getFileNames();
			while (iter.hasNext()) {				
				// 记录上传过程起始时的时间，用来计算上传时间
				long start =  System.currentTimeMillis();
				// 取得上传文件
				MultipartFile file = multiRequest.getFile(iter.next());
				
				String fileRelativePath = null;
				
				if (file != null) {
					// 取得当前上传文件的文件名称
					String myFileName = file.getOriginalFilename();
					// 如果名称不为,说明该文件存在，否则说明该文件不存在
					if (StringUtils.isNotBlank(myFileName)) {
						// 重命名上传后的文件名
						DateTime nowDateTime = new DateTime();
						String timeStr = nowDateTime.toString("yyyyMMddHHmmssSSS");
						int index = myFileName.lastIndexOf(".");
						String fileName = null;
						if(index>0){
							String suffix = myFileName.substring(index);
							//String name = myFileName.substring(0,index);
							fileName = timeStr + suffix;
						}else{
							fileName = myFileName;
						}
						// 定义上传路径
						String parthArg = CmdFileUpload.class.getResource("").toString();
						// 将%20换成空格（如果文件夹的名称带有空格的话，会在取得的字符串上变成%20）
						parthArg=parthArg.substring(5,parthArg.length()).replaceAll("%20", " "); //如果是window请使用 parthArg=parthArg.substring(6,parthArg.length()).replaceAll("%20", "");  
						// 查找WEB-INF在该字符串的位置
						int num = parthArg.indexOf("WEB-INF");
						// 截取
						parthArg = parthArg.substring(0, num)+"tick/upload/";
						logger.info(parthArg);
						File dir = new File(parthArg);
						if(!dir.exists()){
							dir.mkdirs();
						}
						String path = parthArg + fileName;
						fileRelativePath = "/tick/upload/" + fileName;
						File localFile = new File(path);
						file.transferTo(localFile);	
						fileNameBuilder.append(path);
						if(iter.hasNext()){
							fileNameBuilder.append(",");
						}
					}
				}
				// 记录上传该文件后的时间
				long end =  System.currentTimeMillis();				
				String fileNames = fileRelativePath;//fileNameBuilder.toString();				
				logger.info("-----upload-time----" + (end- start));
				 PrintWriter pw = response.getWriter();
				 response.setHeader("Content-type", "application/json;charset=UTF-8");
				 response.setHeader("Content-Length",fileNames.getBytes("utf-8").length + "");
				 pw.print(fileRelativePath);
	             pw.flush();	             
	             pw.close();
			}

		}
		return null;
	}
}
