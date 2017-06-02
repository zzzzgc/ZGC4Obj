package xinxing.boss.admin.boss.other.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class TestInterceptor extends HandlerInterceptorAdapter  {
	private static Logger log=Logger.getLogger("执行效率监听器");
	private long startTime;
	private long endTime;
	/**
	 * 请求开始
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		// TODO Auto-generated method stub
		startTime = System.currentTimeMillis();
		return super.preHandle(request, response, handler);
	}

	/**
	 * 处理器请求结束
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		super.postHandle(request, response, handler, modelAndView);
	}
	
	/**
	 * 请求结束
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		// TODO Auto-generated method stub
		super.afterCompletion(request, response, handler, ex);
		endTime = System.currentTimeMillis();
		log.info(request.getRequestURL()+"的执行时间为:"+(endTime-startTime)/1000+"s,"+(endTime-startTime)+"ms");
	}
	

}
