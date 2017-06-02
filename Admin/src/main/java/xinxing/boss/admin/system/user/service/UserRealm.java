package xinxing.boss.admin.system.user.service;

import java.io.Serializable;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xinxing.boss.admin.common.utils.auth.UsernamePasswordCaptchaToken;
import xinxing.boss.admin.common.utils.base.BaseUtil;
import xinxing.boss.admin.common.utils.constants.Constants;
import xinxing.boss.admin.common.utils.exception.CaptchaException;
import xinxing.boss.admin.common.utils.exception.YangCongException;
import xinxing.boss.admin.common.utils.security.Encodes;
import xinxing.boss.admin.common.yangcong.utils.YangCongUtils;
import xinxing.boss.admin.system.menu.domain.Menu;
import xinxing.boss.admin.system.menu.service.MenuService;
import xinxing.boss.admin.system.user.domain.User;
import xinxing.boss.admin.system.user.domain.UserRole;

import com.google.common.base.Objects;

/**
 * 用户登录授权service(shrioRealm)
 * 
 * @author ty
 * @date 2015年1月14日
 */
@Service
public class UserRealm extends AuthorizingRealm { 
	public static Boolean HANDLE_IS_NOT_NEET_YANZHENG = false;
	@Autowired
	private UserService userService;

	@Autowired
	private MenuService menuService;

	/**
	 * 认证回调函数,登录时调用.
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
		UsernamePasswordCaptchaToken token = (UsernamePasswordCaptchaToken) authcToken;
		User user = userService.getValidUser(token.getUsername());
		if (user != null) {
			if (doSecurityCodeValidate(token, user)) {
				byte[] salt = Encodes.decodeHex(user.getSalt());
				ShiroUser shiroUser = new ShiroUser(user.getId(), user.getLoginName(), user.getName());
				Session session = SecurityUtils.getSubject().getSession();
				session.setAttribute("user", user);
				return new SimpleAuthenticationInfo(shiroUser, user.getPassword(), ByteSource.Util.bytes(salt), getName());
			}
			return null;
		} else {
			return null;
		}
	}

	/**
	 * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用.
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		ShiroUser shiroUser = (ShiroUser) principals.getPrimaryPrincipal();
		User user = userService.getUser(shiroUser.loginName);

		// 把principals放session中 key=userId value=principals
		SecurityUtils.getSubject().getSession().setAttribute(String.valueOf(user.getId()), SecurityUtils.getSubject().getPrincipals());

		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		// 赋予角色
		for (UserRole userRole : user.getUserRoles()) {
			info.addRole(userRole.getRole().getName());
		}
		// 赋予权限
		for (Menu permission : menuService.getRoleMenus(user.getId())) {
			if (StringUtils.isNotBlank(permission.getUrl()) && permission.getType() == 1)
				info.addStringPermission(permission.getUrl());
		}
		/*
		 * //设置登录次数、时间
		 * userService.updateUserLogin(user);
		 */
		return info;
	}

	/**
	 * 验证码校验
	 * 
	 * @param token
	 * @return boolean
	 */
	protected boolean doCaptchaValidate(UsernamePasswordCaptchaToken token) {
		if (StringUtils.isBlank(token.getCaptcha())) {
			return false;
		}
		if (StringUtils.length(token.getCaptcha()) < 5) {
			return false;
		}
		String captcha = (String) SecurityUtils.getSubject().getSession().getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
		if (captcha != null && !captcha.equalsIgnoreCase(token.getCaptcha())) {
			throw new CaptchaException("验证码错误！");
		}
		return true;
	}

	/**
	 * 洋葱安全码校验
	 * 
	 * @param token
	 * @return boolean
	 */
	protected boolean doSecurityCodeValidate(UsernamePasswordCaptchaToken token, User user) {
		return true;
	/*	if (BaseUtil.isNotNeedYanzheng()) {// 本地测试的时候用的
			return true;
		}
		if (HANDLE_IS_NOT_NEET_YANZHENG) {
			return true;
		}
		if (user.getBind() == 0) {// 未绑定
			return true;
		}
		if (YangCongUtils.isCodeOk(user.getYangId(), token.getSecuritycode())) {
			return true;
		} else {
			throw new YangCongException("动态码错误！");
		}*/
	}

	/**
	 * 设定Password校验的Hash算法与迭代次数.
	 */
	@SuppressWarnings("static-access")
	@PostConstruct
	public void initCredentialsMatcher() {
		HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(userService.HASH_ALGORITHM);
		matcher.setHashIterations(userService.HASH_INTERATIONS);
		setCredentialsMatcher(matcher);
	}

	/**
	 * 自定义Authentication对象，使得Subject除了携带用户的登录名外还可以携带更多信息.
	 */
	public static class ShiroUser implements Serializable {
		private static final long serialVersionUID = -1373760761780840081L;
		public Integer id;
		public String loginName;
		public String name;

		public ShiroUser(Integer id, String loginName, String name) {
			this.id = id;
			this.loginName = loginName;
			this.name = name;
		}

		public Integer getId() {
			return id;
		}

		public String getName() {
			return name;
		}

		/**
		 * 本函数输出将作为默认的<shiro:principal/>输出.
		 */
		@Override
		public String toString() {
			return loginName;
		}

		/**
		 * 重载hashCode,只计算loginName;
		 */
		@Override
		public int hashCode() {
			return Objects.hashCode(loginName);
		}

		/**
		 * 重载equals,只计算loginName;
		 */
		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			ShiroUser other = (ShiroUser) obj;
			if (loginName == null) {
				if (other.loginName != null) {
					return false;
				}
			} else if (!loginName.equals(other.loginName)) {
				return false;
			}
			return true;
		}
	}

	@Override
	public void clearCachedAuthorizationInfo(PrincipalCollection principals) {
		super.clearCachedAuthorizationInfo(principals);
	}

	@Override
	public void clearCachedAuthenticationInfo(PrincipalCollection principals) {
		super.clearCachedAuthenticationInfo(principals);
	}

	@Override
	public void clearCache(PrincipalCollection principals) {
		super.clearCache(principals);
	}

	public void clearAllCachedAuthorizationInfo() {
		getAuthorizationCache().clear();
	}

	public void clearAllCachedAuthenticationInfo() {
		getAuthenticationCache().clear();
	}

	public void clearAllCache() {
		clearAllCachedAuthenticationInfo();
		clearAllCachedAuthorizationInfo();
	}

}
