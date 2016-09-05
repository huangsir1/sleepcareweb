/**
 * 
 */
package taiyi.web.controller.api;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
//
//	static {
//		new Thread() {
//			@Override
//			public void run() {
//				for (ValidateCode v : validateCodes.values()) {
//					Date d = new Date();
//					Calendar calendar = Calendar.getInstance();
//					calendar.setTime(v.getUpDate());
//					calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE) + 5);
//					if (d.after(calendar.getTime())) {
//						validateCodes.remove(v.getPhone());
//					}
//					try {
//						Thread.sleep(60_000);
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					}
//				}
//			};
//		}.start();
//	}

//	@RequestMapping(value = "/getCode")
//	@ResponseBody
//	public Status getCode(String phone) {
//		// TODO 增加发送限制
//		ValidateCode validateCode = new ValidateCode();
//		validateCode.setCode(webService.generateValidateCode());
//		validateCode.setPhone(phone);
//		validateCode.setUpDate(new Date());
//		validateCodes.put(phone, validateCode);
//		return new Status(Status.SUCCESSED_CODE, validateCode.getCode());
//	}

	@RequestMapping(value = "/register")
	@ResponseBody
	public Status register(String phone, String code) {
		if (code == validateCodes.get(phone).getCode()) {
			validateCodes.remove(phone);
			Account account = new Account();
			account.setId(UUID.randomUUID().toString());
			account.setPhone(phone);
			account.setToken(UUID.randomUUID().toString());
			accountService.insert(account);
			return new Status(Status.SUCCESSED_CODE, account.getToken());
		} else {
			return Status.VALIDATECODE_ERROR;
		}
	}

	@RequestMapping(value = "/login")
	@ResponseBody
	public Status login(String phone, String code) {
		// TODO
		if (code.equals(phone)) {

		}
		String token = UUID.randomUUID().toString();
		Account account = accountService.selectByPhone(phone);
		account.setToken(token);
		accountService.updateByPrimaryKeySelective(account);
		return new Status(Status.SUCCESSED_CODE, token);
	}
}
