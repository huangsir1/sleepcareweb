/**
 * 
 */
package taiyi.web.realm;

import java.util.Date;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import taiyi.web.service.PermissionService;
import taiyi.web.service.SystemUserService;

/**
 * @author <a href="mailto:jason19659@163.com">jason19659</a>
 *
 *         pw.jason19659.laosiji.service
 *
 *         2016年5月31日
 */
@Component("loginRealm")
public class LoginRealm extends AuthorizingRealm {
	Logger logger = Logger.getLogger(getClass());
	@Autowired
	private SystemUserService sysUserService;
	@Autowired
	private PermissionService permissionService;

	/*
	 * @see
	 * org.apache.shiro.realm.AuthorizingRealm#doGetAuthorizationInfo(org.apache
	 * .shiro.subject.PrincipalCollection)
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
		String username = (String) (getAvailablePrincipal(principalCollection));
		Set<String> permissions = permissionService.selectPermissionsByUsername(username);
		Set<String> roles = permissionService.selectRoleStringsByUsername(username);
		
		logger.info(username + "在" + new Date() + "授予" + permissions +"权限" + roles );
		
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		authorizationInfo.setRoles(roles);
		authorizationInfo.setStringPermissions(permissions);
		return authorizationInfo;
	}

	/*
	 * @see
	 * org.apache.shiro.realm.AuthenticatingRealm#doGetAuthenticationInfo(org.
	 * apache.shiro.authc.AuthenticationToken)
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) {
		String username = (String) authenticationToken.getPrincipal();
		// 交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配，如果觉得人家的不好可以自定义实现
		SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(username, // 用户名
				sysUserService.selectByUsername(username).getPassword(), // 密码
				getName() // realm name
		);
		return authenticationInfo;
	}

	@PostConstruct
	public void initCredentialsMatcher() {
		// 该句作用是重写shiro的密码验证，让shiro用我自己的验证
		setCredentialsMatcher(new PasswordCredentialsMatcher());

	}

}
