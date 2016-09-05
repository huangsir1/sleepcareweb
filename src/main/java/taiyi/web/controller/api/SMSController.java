/**
 * 
 */
package taiyi.web.controller.api;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import taiyi.web.model.Account;
import taiyi.web.model.dto.SMSDto;
import taiyi.web.model.dto.SMSValidate;
import taiyi.web.service.AccountService;
import taiyi.web.utils.SMSTools;

/**
 * @author <a href="mailto:jason19659@163.com">jason19659</a>
 *
 *         taiyi.web.controller.api
 *
 *         2016年9月5日
 */
@Controller
@RequestMapping("/api/sms")
public class SMSController {
	@Autowired
	private AccountService accountService;

	@RequestMapping(value = "/login", consumes = "application/json")
	@ResponseBody
	public SMSValidate loginOrRegister(SMSDto smsDto) {
		SMSValidate smsValidate = SMSTools.checkSMS(smsDto.getPhone(), smsDto.getCode());
		if (smsValidate.isSuccess()) {
			Account account = accountService.selectByPhone(smsDto.getPhone());
			if (account == null) {
				account = new Account();
				account.setId(UUID.randomUUID().toString());
				account.setPhone(smsDto.getPhone());
				account.setToken(UUID.randomUUID().toString().replaceAll("-", ""));
				account.setRegDate(new Date());
				account.setTokenDate(new Date());
				accountService.insert(account);
			} else {
				account.setToken(UUID.randomUUID().toString().replaceAll("-", ""));
			}
			smsValidate.setMessage(account.getToken());
		}
		return smsValidate;
	}
	
	
}
