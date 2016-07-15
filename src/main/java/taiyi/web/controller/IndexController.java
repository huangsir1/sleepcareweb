/**
 * 
 */
package taiyi.web.controller;

import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author <a href="mailto:jason19659@163.com">jason19659</a>
 *
 *         taiyi.web.controller
 *
 *         2016年3月14日
 */
@Controller
public class IndexController extends ExceptionHandlerController {

	@RequestMapping("/index")
	public String index() {
		// TODO:
		// FIXME:
		return "adminLogin";
	}

}
