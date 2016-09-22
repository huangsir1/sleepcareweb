/**
 * 
 */
package taiyi.web.controller.api;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import taiyi.web.constant.Constant;
import taiyi.web.model.Account;
import taiyi.web.model.dto.Status;
import taiyi.web.model.dto.ValidateCode;
import taiyi.web.service.AccountService;
import taiyi.web.service.WebService;

/**
 * @author <a href="mailto:jason19659@163.com">jason19659</a>
 *
 *         taiyi.web.controller.api
 *
 *         2016年3月29日
 */
@Controller
@RequestMapping("/api/account")
public class AccountAPIController extends APIExceptionHandlerController {
	public static Map<String, ValidateCode> validateCodes = new HashMap<String, ValidateCode>();
	@Autowired
	private AccountService accountService;
	@Autowired
	private WebService webService;

	/**
	 * 检查token有效性
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/testToken")
	@ResponseBody
	public Status isTokenValidate(HttpServletRequest request) {
		String token = request.getHeader(Constant.TOKEN);
		Account account = accountService.selectByToken(token);
		if (account != null) {
			return Status.getSuccess(account.getPhone());
		}
		return Status.getFailed("无效token");
	}

}
