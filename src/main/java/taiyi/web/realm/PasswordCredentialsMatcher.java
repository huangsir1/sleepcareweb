/**
 * 
 */
package taiyi.web.realm;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;

import taiyi.web.utils.EncryptUtils;

/**
 * @author <a href="mailto:jason19659@163.com">jason19659</a>
 *
 *         taiyi.web.realm
 *
 *         2016年6月6日
 */
public class PasswordCredentialsMatcher extends SimpleCredentialsMatcher{
	@Override
	public boolean doCredentialsMatch(AuthenticationToken authcToken, AuthenticationInfo info) {
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		String inputPassword =new String( token.getPassword());
		String dbPassword = info.getCredentials().toString();
		return EncryptUtils.checkOriginalTaiirPassword(inputPassword, dbPassword);
	}
}
