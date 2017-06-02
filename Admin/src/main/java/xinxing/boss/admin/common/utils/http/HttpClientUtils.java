package xinxing.boss.admin.common.utils.http;

import org.apache.log4j.Logger;

public class HttpClientUtils {

	private static final Logger log = Logger.getLogger(HttpClientUtils.class);

//	/**
//	 * HttpClient 发送 Post请求
//	 * 
//	 * @param url
//	 * @param content
//	 */
//	public static String doPost(String url, String content) {
//		String result = null;
//		CloseableHttpClient client = null;
//		try {
//			client = HttpClientBuilder.create().build();
//			RequestConfig requestConfig = RequestConfig.custom()
//					.setConnectionRequestTimeout(50000)
//					.setConnectTimeout(50000).setSocketTimeout(50000).build();
//			HttpPost request = new HttpPost(url);
//			request.addHeader("Content-type", "application/json; charset=utf-8");
//			request.setHeader("Accept", "application/json");
//			request.setEntity(new StringEntity(content, Charset
//					.forName("UTF-8")));
//			request.setConfig(requestConfig);
//
//			CloseableHttpResponse response = client.execute(request);
//			try {
//				HttpEntity entity = response.getEntity();
//				if (entity != null) {
//					result = EntityUtils.toString(entity);
//					EntityUtils.consume(entity);
//				}
//			} finally {
//				response.close();
//			}
//		} catch (Exception e) {
//			log.info("send conternt:"+content+"   ,"+e.getMessage(),e);
//			log.error("send conternt:"+content+"   ,"+e.getMessage(), e);
//		} finally {
//			try {
//				client.close();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//		return result;
//	}

}
