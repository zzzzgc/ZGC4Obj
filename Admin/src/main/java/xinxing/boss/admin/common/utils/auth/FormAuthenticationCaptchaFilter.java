package xinxing.boss.admin.common.utils.auth;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

/**
 * 扩展认证默认过滤
 * @author ty
 * @date 2014年12月2日 下午10:47:09
 */
public class FormAuthenticationCaptchaFilter extends FormAuthenticationFilter {

	public static final String DEFAULT_CAPTCHA_PARAM = "captcha";
	public static final String DEFAULT_SECURITYCODE_PARAM = "securitycode"; //洋葱安全码
	private String captchaParam = DEFAULT_CAPTCHA_PARAM;

	public String getCaptchaParam() {
		return captchaParam;
	}

	protected String getCaptcha(ServletRequest request) {
		return WebUtils.getCleanParam(request, getCaptchaParam());
	}

	protected String getSecuritycode(ServletRequest request) {
		return WebUtils.getCleanParam(request, DEFAULT_SECURITYCODE_PARAM);
	}

	@Override
	protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
		String username = getUsername(request);
		String password = getPassword(request);
		String captcha = getCaptcha(request);
		String securitycode = getSecuritycode(request);
		boolean rememberMe = isRememberMe(request);
		String host = getHost(request);
		if(StringUtils.isNotBlank(password)){
			return new UsernamePasswordCaptchaToken(username,password.toCharArray(), rememberMe, host, captcha,securitycode);
		}
		return null;
	}
	
	
	

}