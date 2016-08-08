/**
 * 
 */
package taiyi.web.controller.api;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

import taiyi.web.model.SubReport;
import taiyi.web.model.User;
import taiyi.web.model.dto.DiseaseHistoryDto;
import taiyi.web.model.dto.ESSAndDisEaseHistoryDto;
import taiyi.web.model.dto.EssDto;
import taiyi.web.model.dto.Status;
import taiyi.web.service.DiseaseHistoryUserService;
import taiyi.web.service.EssUserService;
import taiyi.web.service.UserService;

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
	
	@RequestMapping(value="/register",consumes = "application/json")
	@ResponseBody
	public Status register(@RequestBody User user) {
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
	
	public static void main(String[] args) {
		SubReport subReport = new SubReport();
		subReport.setId("id");
		subReport.setAdvice("advice");
		System.out.println(JSON.toJSON(subReport));
	}
	
	@RequestMapping(value="/update",consumes = "application/json")
	@ResponseBody
	public Status update(@RequestBody User user) {
		try {
			user.setLastestDate(new Date());
			userService.updateByPrimaryKeySelective(user);
			Status status = new Status(Status.SUCCESSED_CODE,"Id : " + user.getId() + "的用户修改成功"); 
			return status;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Status.FAILED;
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
