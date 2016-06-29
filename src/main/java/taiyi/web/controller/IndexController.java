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

	public static void main(String[] args) {
		double a = 0.4;
		System.out.println(66.0 / 210);
		System.out.println(57.0 / 201);
		System.out.println(84.0 / 210);
	}
	
	
}
