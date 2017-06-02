package xinxing.boss.admin.common.utils.aop.log;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.util.ThreadContext;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import xinxing.boss.admin.common.utils.constants.Constants;
import xinxing.boss.admin.common.utils.json.JsonUtils;
import xinxing.boss.admin.system.user.domain.User;
import xinxing.boss.admin.system.user.utils.UserUtil;

/**
 * 系统服务组件Aspect切面Bean
 * 
 * @author Shenghany
 * @date 2013-5-28
 */

// 声明这是一个切面Bean
@Aspect
public class AopAspect {
	private static Logger logger = LoggerFactory.getLogger(AopAspect.class);

	@Pointcut("execution(* xinxing..service..*Service.*(..))")
	public void aspect() {

	}

	/*
	 * 配置前置通知,使用在方法aspect()上注册的切入点 同时接受JoinPoint切入点对象,可以没有该参数
	 */
	@Before("aspect()")
	public void before(JoinPoint joinPoint) {
		StringBuffer sb = new StringBuffer(250);
		if ((ThreadContext.getSecurityManager() != null) && (SecurityUtils.getSubject() != null) && (SecurityUtils.getSubject().getSession() != null)
				&& (SecurityUtils.getSubject().getSession().getAttribute("user") != null)) {
			String method = "";
			if (UserUtil.getCurrentUser() != null)
				sb.append(UserUtil.getCurrentUser().getName()).append(" ");
			if (joinPoint.getSignature() != null) {
				method = joinPoint.getSignature().getName();
				sb.append("方法:").append(method).append(" ");
				Object[] args = joinPoint.getArgs();
				for (Object object : args) {
					sb.append("参数:").append(JsonUtils.obj2Json(object)).append(" ");
				}
			}
			String aopFilterStrs = Constants.getString("aopFilterStrs");
			Boolean isSkip = false;
			if (aopFilterStrs != null && aopFilterStrs != "") {
				String[] strUrls = aopFilterStrs.split(",");
				for (String string : strUrls) {
					if (method.contains(string)) {
						isSkip = true;
						break;
					}
				}
			}
			if (sb.length() <= 666 && method != null && !isSkip) {
				logger.warn(sb.toString());
			}
		}
	}

	// 配置后置通知,使用在方法aspect()上注册的切入点
	// @After("aspect()")
	public void after(JoinPoint joinPoint) {
	}

	// 配置环绕通知,使用在方法aspect()上注册的切入点
	// @Around("aspect()")
	public void around(JoinPoint joinPoint) {
	}

	// 配置后置返回通知,使用在方法aspect()上注册的切入点
	// @AfterReturning("aspect()")
	public void afterReturn(JoinPoint joinPoint) {
	}

	// //配置抛出异常后通知,使用在方法aspect()上注册的切入点
	// @AfterThrowing(pointcut = "aspect()", throwing = "ex")
	public void afterThrow(JoinPoint joinPoint, Exception ex) {
	}

}