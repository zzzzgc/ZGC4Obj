package xinxing.boss.admin.common.utils.filter;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import xinxing.boss.admin.common.utils.constants.Constants;
import xinxing.boss.admin.common.utils.http.HttpUtils;
import xinxing.boss.admin.system.user.cmd.UserController;
import xinxing.boss.admin.system.user.domain.User;
import xinxing.boss.admin.system.user.service.UserRoleService;
import xinxing.boss.admin.system.user.utils.UserUtil;

public class LoginFilter implements Filter {
	private static Logger logger = LoggerFactory.getLogger(LoginFilter.class);

	@Autowired
	private UserRoleService userRoleService;
	
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	public void doFilter(ServletRequest servletRequestt, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequestt;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		String uri = request.getRequestURI();
		Boolean isSplit = false;
		if ("GET".equals(request.getMethod().toUpperCase()) || uri.contains("boss/orderInfo/orderInfoList")) {
			Map<String, String> reqParams = HttpUtils.getReqParams(request);
			if (reqParams != null && reqParams.get("createMenu") != null && reqParams.get("createMenu").toString().equals("110")) {
				isSplit = true;
			}
		}
		// 过滤条件逗号隔开
		if (!isSplit) {
			String loginFilterStrs = Constants.getString("loginFilterStrs");
			String[] strUrls = loginFilterStrs.split(",");
			for (String string : strUrls) {
				if (uri.contains(string)) {
					isSplit = true;
					break;
				}
			}
		}
		if (isSplit) {
			chain.doFilter(servletRequestt, servletResponse);
			return;
		} else {
			Subject subject = SecurityUtils.getSubject();
			if (subject.isAuthenticated() || subject.isRemembered()) {
				Session session = subject.getSession();
				User user = (User) session.getAttribute("user");
				String userName = UserUtil.getCurrentUser().getName();
				logger.warn(userName + " 地址:" + uri.replaceAll("/XinXingBossAdmin/boss/", ""));

				chain.doFilter(request, response);
				return;
			}
			request.getRequestDispatcher("/login/logout").forward(request, response);
			return;
		}
	}

	public void destroy() {

	}
}
