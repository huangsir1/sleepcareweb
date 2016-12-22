/**
 * 
 */
package taiyi.web.controller;

import org.apache.shiro.authz.AuthorizationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author <a href="mailto:jason19659@163.com">jason19659</a>
 *
 *         taiyi.web.controller
 *
 *         2016年3月11日
 */
@Controller
public class ExceptionHandlerController {
											 
	@ExceptionHandler(AuthorizationException.class)
	public String unauthenticatedException(Exception ex) {
		ex.printStackTrace();
		
		return "redirect:/err";
	}
	
	@ExceptionHandler(Exception.class)
	public String operateExp(Exception ex) {
		ex.printStackTrace();
		return "redirect:/err";
	}
	
}
