/**
 * 
 */
package taiyi.web.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import taiyi.web.model.Account;
import taiyi.web.model.dto.FeedbackDto;
import taiyi.web.model.dto.Status;
import taiyi.web.service.AccountService;
import taiyi.web.service.FeedbackService;

/**
 * @author <a href="mailto:jason19659@163.com">jason19659</a>
 *
 * taiyi.web.controller.api
 *
 * 2016年3月30日
 */
@Controller
@RequestMapping("/api/feedback")
public class FeedBackController  extends APIExceptionHandlerController {
	@Autowired
	private FeedbackService feedBackService;
	@Autowired
	private AccountService accountService;
	
	@RequestMapping(value="upload", consumes = "application/json")
	@ResponseBody
	public Status upload(@RequestBody  FeedbackDto feedback) {
		Account account = accountService.selectByToken(feedback.getToken());
		feedback.setAccountId(account.getId());
		feedBackService.insert(feedback.covertToFeedBack());
		return Status.SUCCESSED;
	}
	
}

