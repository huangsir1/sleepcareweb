/**
 * 
 */
package taiyi.web.controller.api;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

import taiyi.web.constant.Constant;
import taiyi.web.model.Account;
import taiyi.web.model.SubReport;
import taiyi.web.model.User;
import taiyi.web.model.dto.DiseaseHistoryDto;
import taiyi.web.model.dto.ESSAndDisEaseHistoryDto;
import taiyi.web.model.dto.EssDto;
import taiyi.web.model.dto.Status;
import taiyi.web.model.dto.Token;
import taiyi.web.model.dto.UserEssAndDHDto;
import taiyi.web.service.AccountService;
import taiyi.web.service.DiseaseHistoryUserService;
import taiyi.web.service.EssUserService;
import taiyi.web.service.UserService;
import taiyi.web.service.WebService;

/**
 * @author <a href="mailto:jason19659@163.com">jason19659</a>
 *
 * taiyi.web.controller.api
 *
 * 2016年3月9日
 */
@Controller
@RequestMapping("/api/user" )
public class UserAPIController extends APIExceptionHandlerController {
	@Autowired
	private UserService userService;
	@Autowired 
	private DiseaseHistoryUserService diseaseHistoryUserService;
	@Autowired
	private EssUserService essUserService;
	@Autowired
	private AccountService accountService;
	@Autowired
	private WebService webService;
	
	@RequestMapping(value="/getUserByToken")
	@ResponseBody
	public List<UserEssAndDHDto> getUserActiveUserByToken(HttpServletRequest request) {
		String token = request.getHeader(Constant.TOKEN);
		List<UserEssAndDHDto> users = webService.selectUserEssAndDHByToken(token);
		return users;
	}
	
	@RequestMapping(value="/deleteUser/{userId}")
	@ResponseBody
	public Status unActiveUser(HttpServletRequest request,@PathVariable String userId) {
		String token = request.getHeader(Constant.TOKEN);
		Status result = null;
		if (userService.unActiveUser(token,userId)) {
			result = Status.getSuccess();
		} else {
			result = Status.getFailed("删除失败");
		}
		return result;
	}
	
	@RequestMapping(value="/register",consumes = "application/json")
	@ResponseBody
	public Status register(@RequestBody User user,HttpServletRequest request) {
		String token = request.getHeader(Constant.TOKEN);
		if (!StringUtils.isEmpty(token)) {
			System.out.println(token);
			Account account = accountService.selectByToken(token);
			//TODO 失效注册失败
			if (account != null) {
				user.setAccountId(account.getId());
				user.setPhone(account.getPhone());
			}
		}
		try {
//			if (false) {
//				return Status.PARAM_ERROR;
//			}
			if (!"男".equals(user.getGender()) && !"女".equals(user.getGender())) {
				return new Status(Status.FAILED_CODE, "性别参数错误!!!");
			}
			user.setId(UUID.randomUUID().toString());
			user.setRegisterDate(new Date());
			user.setLastestDate(new Date());
			//
			if (user.getDoctorId() == null) {
				user.setDoctorId("00000000-0000-0000-0000-000000000000");
			}
			userService.insert(user);
			Status status = new Status(Status.SUCCESSED_CODE,user.getId()); 
			return status;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Status.FAILED;
	}
	
	
	@RequestMapping(value="/update",consumes = "application/json")
	@ResponseBody
	public Status update(@RequestBody User user) {
		if (StringUtils.isEmpty(user.getId())) {
			return Status.getFailed("被修改用户的id不能为\""+user.getId()+"\"");
		}
		try {
			user.setLastestDate(new Date());
			userService.updateByPrimaryKeySelective(user);
			Status status = new Status(Status.SUCCESSED_CODE,"Id : " + user.getId() + "的用户修改成功"); 
			return status;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Status.getFailed();
	}
	
	@RequestMapping(value="/isUserRegister",consumes = "application/json")
	@ResponseBody
	public Status isUserRegister(@RequestBody User user) {
		if (userService.selectByPrimaryKey(user.getId()) != null) {
			return Status.USER_EXIST;
		}
		return Status.USER_UNREGISTER;
	}
	
	@RequestMapping(value="/uploadDiseaseHistory",consumes = "application/json")
	@ResponseBody
	public Status uploadDiseaseHistory(@RequestBody  DiseaseHistoryDto diseaseHistoryIds) {
		diseaseHistoryUserService.deleteByUserId(diseaseHistoryIds.getUserId());
		diseaseHistoryUserService.insertDHUs(diseaseHistoryIds);
		return Status.SUCCESSED;
	}
	
	
	@RequestMapping(value="/uploadEss",consumes = "application/json")
	@ResponseBody 
	public Status uploadEss(@RequestBody EssDto essDto) {
		essUserService.insertOrUpdate(essDto);
		return Status.SUCCESSED;
	}
	
	@RequestMapping(value="/uploadEssAndDiseaseHistory",consumes = "application/json")
	@ResponseBody 
	public Status uploadEss(@RequestBody ESSAndDisEaseHistoryDto essAndDisEaseHistoryDto) {
		essUserService.insertOrUpdate(essAndDisEaseHistoryDto.getEssDto());
		diseaseHistoryUserService.deleteByUserId(essAndDisEaseHistoryDto.getDiseaseHistoryDto().getUserId());
		diseaseHistoryUserService.insertDHUs(essAndDisEaseHistoryDto.getDiseaseHistoryDto().getDiseaseHistoryIds(),essAndDisEaseHistoryDto.getDiseaseHistoryDto().getUserId());
		return Status.SUCCESSED;
	}
	
	
}
