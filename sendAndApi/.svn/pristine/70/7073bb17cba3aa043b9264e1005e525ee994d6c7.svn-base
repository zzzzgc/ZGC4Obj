package com.xinxing.o.boss.common.http;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

public class HttpUtils {

	private static Logger log = Logger.getLogger(HttpUtils.class);

	/**
	 * 获取请求的参数信息
	 * 
	 * @param request
	 * @return
	 */
	public static Map<String, String> getReqParams(HttpServletRequest request) {
		@SuppressWarnings("unchecked")
		Enumeration<String> paramNames = request.getParameterNames();
		if (paramNames.hasMoreElements()) {
			Map<String, String> map = new HashMap<String, String>();
			String paramName = paramNames.nextElement();
			String[] paramValues = request.getParameterValues(paramName);
			if (paramValues.length == 1) {
				String paramValue = paramValues[0];
				if (paramValue.length() != 0) {
					map.put(paramName, paramValue);
				}
			}
			while (paramNames.hasMoreElements()) {
				paramName = paramNames.nextElement();
				paramValues = request.getParameterValues(paramName);
				if (paramValues.length == 1) {
					String paramValue = paramValues[0];
					if (paramValue.length() != 0) {
						map.put(paramName, paramValue);
					}
				}
			}
			return map;
		}
		return null;
	}
	
	/**
	 * 这是适用于mXXXX/data格式请求传参取值的方法.
	 * @param request 请求对象
	 * @return 文件体内容
	 */
	@SuppressWarnings("all")
	public static Map<String,Object> getBodyMap(HttpServletRequest request,Logger log){
		 DiskFileItemFactory factory = new DiskFileItemFactory();
	        ServletFileUpload upload = new ServletFileUpload(factory);
	        log.info("upload:"+upload);
	        List items=null;
	        Map param = new HashMap();
	        try {
	        	List parseRequest = upload.parseRequest(request);
	        	 log.info("parseRequest:"+parseRequest);
	            items = parseRequest;
	            log.info("items:"+items);
	            for(Object object:items){
	                FileItem fileItem = (FileItem) object;
	                if (fileItem.isFormField()) {
	                    try {
	                    	  log.info("fileItem.getFieldName():"+fileItem.getFieldName());
	                    	  log.info("fileItem.getString(utf-8):"+fileItem.getString("utf-8"));
	                        param.put(fileItem.getFieldName(), fileItem.getString("utf-8"));
	                    } catch (Exception e) {
	                        log.error("getBodyMap报错,可能param为空");
	                    }
	                }
	            } 
	        } catch (Exception e) {
	        	log.error("getBodyMap报错");
	        }
	        log.error("MHAN回调成功");
	        return param;
    }


	public static Map<String, String> getReqParams(String reqParams) {
		String[] arge = StringUtils.split(reqParams, "&");
		if (arge.length > 0) {
			Map<String, String> map = new HashMap<>();
			for (String str : arge) {
				String[] param = StringUtils.split(str, "=");
				if (param.length == 2) {
					map.put(param[0], param[1]);
				}
			}
			return map;
		}
		return null;
	}

	/**
	 * 获取Post数据
	 * 
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String getReqPostString(HttpServletRequest request, Logger log) {
		StringBuilder reqData = new StringBuilder();
		String line = null;
		BufferedReader reader = null;
		try {
			request.setCharacterEncoding("utf8");// 设置编码
			reader = request.getReader();
			while ((line = reader.readLine()) != null) {
				reqData.append(line);
			}
			if (reqData.length() <= 0) {
				return null;
			}
			String strJson = reqData.toString();
			reader.close();
			log.info("获取post数据: strJson=" + strJson);
			return strJson;
		} catch (IOException e) {
			log.error("获取post数据错误:" + e.getMessage(), e);
		}
		return null;
	}

	/**
	 * 获取Post数据
	 * 
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String getReqPostStringByEncode(HttpServletRequest request, Logger log) {
		StringBuilder reqData = new StringBuilder();
		String line = null;
		BufferedReader reader = null;
		try {
			request.setCharacterEncoding("gbk");
			reader = request.getReader();
			while ((line = reader.readLine()) != null) {
				reqData.append(line);
			}
			if (reqData.length() <= 0) {
				return null;
			}
			String strJson = reqData.toString();
			reader.close();
			log.info("获取post数据: strJson=" + strJson);
			return strJson;
		} catch (IOException e) {
			log.error("获取post数据错误:" + e.getMessage(), e);
		}
		return null;
	}

	public static String sendGet(String url, String encode) {
		String result = "";
		BufferedReader in = null;
		try {
			String urlNameString = url;
			URL realUrl = new URL(urlNameString);
			// 打开和URL之间的连接
			URLConnection connection = realUrl.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			connection.setConnectTimeout(50000);
			connection.setReadTimeout(50000);
			// 建立实际的连接
			connection.connect();

			in = new BufferedReader(new InputStreamReader(connection.getInputStream(), encode));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			log.error("发送的url：" + url + ","+e.getMessage(), e);
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception ex) {
				log.error(ex.getMessage(), ex);
			}
		}
		return result;
	}

	/**
	 * 向指定 URL 发送POST方法的请求
	 * 
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 * 
	 * @return 所代表远程资源的响应结果
	 */
	public static String sendPost(String url, String param, String sign, String token) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			conn.setRequestProperty("Sign", sign);
			conn.setRequestProperty("Token", token);

			conn.setConnectTimeout(30000);
			conn.setReadTimeout(30000);
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			log.error("发送的url：" + url + ","+e.getMessage(), e);
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				log.error(ex.getMessage(), ex);
			}
		}
		return result;
	}

	/**
	 * 向指定 URL 发送POST方法的请求
	 * 
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 * 
	 * @return 所代表远程资源的响应结果
	 */
	public static String sendPost(String url, String param, Map<String, String> map) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");

			for (Map.Entry<String, String> entry : map.entrySet()) {
				conn.setRequestProperty(entry.getKey(), entry.getValue());
			}

			conn.setConnectTimeout(30000);
			conn.setReadTimeout(30000);
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			log.error("发送的url：" + url + ","+e.getMessage(), e);
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				log.error(ex.getMessage(), ex);
			}
		}
		return result;
	}

	/**
	 * 向指定 URL 发送POST方法的请求 自己填多少ms
	 * 
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 * 
	 * @return 所代表远程资源的响应结果
	 */
	public static String sendPost(String url, String param, Map<String, String> map, Integer ms) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");

			for (Map.Entry<String, String> entry : map.entrySet()) {
				conn.setRequestProperty(entry.getKey(), entry.getValue());
			}

			conn.setConnectTimeout(ms);
			conn.setReadTimeout(ms);
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			log.error("发送的url：" + url + ","+e.getMessage(), e);
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				log.error(ex.getMessage(), ex);
			}
		}
		return result;
	}

	/**
	 * 向指定 URL 发送POST方法的请求
	 * 
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 * 
	 * @return 所代表远程资源的响应结果
	 */
	public static String sendPost(String url, String param) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");

			conn.setConnectTimeout(30000);
			conn.setReadTimeout(30000);
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			log.error("发送的url：" + url + ","+e.getMessage(), e);
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				log.error(ex.getMessage(), ex);
			}
		}
		return result;
	}

	/**
	 * 向指定 URL 发送POST方法的请求
	 * 
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 * 
	 * @return 所代表远程资源的响应结果
	 */
	public static String sendPostWithContentType(String url, String param, String content_type) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("Content-Type", content_type);
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");

			conn.setConnectTimeout(30000);
			conn.setReadTimeout(30000);
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			log.error("发送的url：" + url + ","+e.getMessage(), e);
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				log.error(ex.getMessage(), ex);
			}
		}
		return result;
	}

	/**
	 * 向指定 URL 发送POST方法的请求
	 * 
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 * 
	 * @param encode
	 *            接收结果时的编码
	 * 
	 * @return 所代表远程资源的响应结果
	 */
	public static String sendPostEncode(String url, String param, String encode) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");

			conn.setConnectTimeout(30000);
			conn.setReadTimeout(30000);
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream(), encode));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			log.error("发送的url：" + url + ","+e.getMessage(), e);
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				log.error(ex.getMessage(), ex);
			}
		}
		return result;
	}

	/**
	 * 向指定 URL 发送POST方法的请求
	 * 
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 * 
	 * @return 所代表远程资源的响应结果
	 */
	public static String sendPost(String url, String param, String contentType) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("Content-Type", contentType);
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");

			conn.setConnectTimeout(30000);
			conn.setReadTimeout(30000);
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			log.error("发送的url：" + url + ","+e.getMessage(), e);
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				log.error(ex.getMessage(), ex);
			}
		}
		return result;
	}
	
	/**
	 * 发送post对象请求
	 * 
	 * @param post HttpPost 对象
	 * @return
	 */
	public static String sendPost(HttpPost post) {
		String resJson = null;
		try {
			HttpClient client = HttpClients.createDefault();
			HttpResponse respone = client.execute(post);
			HttpEntity entity = respone.getEntity();
			resJson = EntityUtils.toString(entity, "UTF-8");
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
		}
		return resJson;
	}

	/**
	 * 向指定 URL 发送POST方法的请求
	 * 
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 * 
	 * @return 所代表远程资源的响应结果
	 */
	public static String sendPost1(String url, String param, String contentType, String returnEncode) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("Content-Type", contentType);
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");

			conn.setConnectTimeout(50000);
			conn.setReadTimeout(50000);
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream(), returnEncode));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			log.error("发送的url：" + url + ","+e.getMessage(), e);
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				log.error(ex.getMessage(), ex);
			}
		}
		return result;
	}

	public static String sendPostMap(String url, String param, Map<String, String> headMap) {
		return sendPostTemplate(url, param, headMap, "UTF-8", 50000);
	}

	/**
	 * 
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 * 
	 * @return 所代表远程资源的响应结果
	 */
	public static String sendPostTemplate(String url, String param, Map<String, String> headMap, String returnEncode, Integer timeOut) {
		if (StringUtils.isEmpty(returnEncode)) {
			returnEncode = "UTF-8";
		}
		if (!headMap.containsKey("accept")) {
			headMap.put("accept", "*/*");
		}
		if (!headMap.containsKey("Content-Type")) {
			headMap.put("Content-Type", "application/json");
		}
		if (timeOut == null) {
			timeOut = 50000;
		}

		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("connection", "Keep-Alive");
			for (Entry<String, String> entry : headMap.entrySet()) {
				conn.setRequestProperty(entry.getKey(), entry.getValue());
			}
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			conn.setConnectTimeout(timeOut);
			conn.setReadTimeout(timeOut);
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream(), returnEncode));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			log.error("发送的url：" + url + ","+e.getMessage(), e);
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				log.error(ex.getMessage(), ex);
			}
		}
		return result;
	}

	/**
	 * 获得对应的xx=yy&&aa==bb 字符串 字典排序
	 * 
	 * @param sendReqMap
	 * @return
	 */
	public static String getStrByMapOrderByABC(Map<String, Object> sendReqMap) {
		String sign = "";
		Object[] key_arr = sendReqMap.keySet().toArray();
		Arrays.sort(key_arr);
		for (Object k : key_arr) {
			Object value = sendReqMap.get(k.toString());
			sign += "&" + k + "=" + value;
		}
		if (sign.length() > 0) {
			sign = sign.substring(1, sign.length());
		}
		return sign;
	}

	/**
	 * 由于ip限制 输出出来打印到后台执行
	 * 
	 * @param sendUrl
	 * @param sendReq
	 * @return
	 */
	public static String writePostStr(String sendUrl, String sendReq) {
		System.out.println("--------");
		System.out.println("curl -l -H \"Content-type: application/json\" -X POST -d '" + sendReq + "' \"" + sendUrl + "\" ");
		System.out.println("--------");
		return "";
	}

	/**
	 * 向指定 URL 发送POST方法的请求 原方法sendPost有时不能成功发送参数,就使用这个方法
	 * 
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return 所代表远程资源的响应结果
	 */
	public static String sendPostByNameValue(String url, String param) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			
			conn.setConnectTimeout(120000);
			conn.setReadTimeout(120000);
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			log.error("发送的url：" + url + ","+e.getMessage(), e);
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				log.error(ex.getMessage(), ex);
			}
		}
		return result;
	}


}
