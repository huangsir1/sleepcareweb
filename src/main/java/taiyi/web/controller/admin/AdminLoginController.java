/**
 * 
 */
package taiyi.web.controller.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import taiyi.web.controller.ExceptionHandlerController;
import taiyi.web.model.SystemUser;
import taiyi.web.service.SystemUserService;

/**
 * @author <a href="mailto:jason19659@163.com">jason19659</a>
 *
 *         登录部分
 *
 *         taiyi.web.controller.admin
 *
 *         2016年3月14日
 */
@Controller
@RequestMapping("/")
public class AdminLoginController extends ExceptionHandlerController {
	Logger logger = Logger.getLogger(AdminLoginController.class);
	@Autowired
	private SystemUserService systemUserService;

	@RequestMapping("/")
	public String login() {
		return "adminLogin";
	}

	@RequestMapping("admin/login")
	public String login2() {
		return "adminLogin";
	}

	@RequiresPermissions("user:view")
	@RequestMapping("/admin")
	public String a() {
		return "admin/adminIndex";
	}

	// @RequestMapping("/admin2")
	// public String b() {
	// return "admin/adminIndex2";
	// }

	@RequestMapping("/doctor")
	public String c() {
		return "doctor/adminIndex";
	}

	@RequestMapping("/hospital")
	public String hostipal() {
		return "hospital/adminIndex";
	}

	/**
	 * 登录
	 * @param username 用户名
	 * @param password 密码
	 * @param request
	 */
	@RequestMapping("/login")
	public String login(String username, String password, HttpServletRequest request) {

		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		try {
			if (systemUserService.selectByUsername(username) == null) {
				throw new UnknownAccountException();
			}
			subject.login(token);

		} catch (UnknownAccountException uae) {

			logger.info(username + " :username wasn't in the system.");
			request.setAttribute("msg", "用户名不存在");
			return "forward:admin/login";

		} catch (IncorrectCredentialsException ice) {

			logger.info(username + " :password didn't match.");
			request.setAttribute("msg", "密码不匹配");
			return "forward:admin/login";

		} catch (LockedAccountException lae) {

			logger.info(username + " :account for that username is locked - can't login.");
			request.setAttribute("msg", "用户被锁定");
			return "forward:admin/login";

		} catch (AuthenticationException ae) {
			logger.info(username + " :unexpected condition.");
			request.setAttribute("msg", "登录失败");
			return "forward:admin/login";
		}
		Session session = subject.getSession();
		SystemUser systemUser = systemUserService.selectByUsername(username);
		if (!systemUser.getIsValid()) {
			request.setAttribute("msg", "用户被锁定");
			return "forward:admin/login";
		}
		session.setAttribute("user", systemUser);
		request.getSession().setAttribute("trueName", systemUser.getName());
		if (subject.hasRole("admin")) {
			return "redirect:/admin";
		} else if (subject.hasRole("hospital")) {
			return "redirect:/hospital";
		} else if (subject.hasRole("doctor")) {
			return "redirect:/doctor";
		}
		// SystemUser systemUser = systemUserService.selectByUsername(username);
		// if (systemUser != null) {
		// if (EncryptUtils.checkOriginalTaiirPassword(password,
		// systemUser.getPassword())) {
		// session.setAttribute("login", "true");
		// if ("admin".equalsIgnoreCase(username)) {
		// session.setAttribute("user", systemUser);
		// return "redirect:/admin";
		// } else {
		// session.setAttribute("user", systemUser);
		// return "redirect:doctor";
		// }
		// }
		// }
		request.setAttribute("msg", "登陆失败");
		logger.info(username + " 登录失败");
		return "forward:admin/login";
	}

	/**
	 * 注销
	 * @param request
	 * @return
	 */
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request) {
		// session.removeAttribute("login");
		// session.removeAttribute("user");
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
		request.setAttribute("msg", "已经登出");
		return "forward:/admin";
	}

	@RequestMapping("/err")
	public String err() {
		return "exception";
	}
}
