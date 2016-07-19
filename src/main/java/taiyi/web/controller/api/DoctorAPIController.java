/**
 * 
 */
package taiyi.web.controller.api;

import java.util.Date;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import taiyi.web.model.Hostipal;
import taiyi.web.model.SystemUser;
import taiyi.web.model.User;
import taiyi.web.model.dto.Status;
import taiyi.web.model.dto.UserDoctorHospitalDto;
import taiyi.web.service.DoctorAndHosptialService;
import taiyi.web.service.HostipalService;
import taiyi.web.service.SystemUserService;
import taiyi.web.service.UserService;

/**
 * @author <a href="mailto:jason19659@163.com">jason19659</a>
 *
 * taiyi.web.controller.api
 *
 * 2016年7月7日
 */
@Controller
@RequestMapping("/api")
public class DoctorAPIController extends APIExceptionHandlerController{
	Logger logger = Logger.getLogger(getClass());
//	@Autowired
//	private DoctorAndHosptialService doctorAndHosptialService;
	@Autowired
	private UserService userService;
	@Autowired
	private HostipalService hostipalService;
	@Autowired
	private SystemUserService doctorService;

	
	@RequestMapping(value="user/registerUserWithDoctor",consumes = "application/json")
	@ResponseBody
	public Status register(@RequestBody UserDoctorHospitalDto userDoctorHospitalDto) {
		try {
			User user = userDoctorHospitalDto.getUser();
			Hostipal hostipal = userDoctorHospitalDto.getHostipal();
			SystemUser doctor = userDoctorHospitalDto.getDoctor();
			
			if (hostipalService.selectByName(hostipal.getName()) == null) {
				hostipal.setAddress("-");
				hostipal.setPhone("-");
				hostipal.setRegDate(new Date());
				hostipalService.insert(hostipal);
				logger.info("注册医院 " + hostipal.getName());
			}
			
			
			hostipal = hostipalService.selectByName(hostipal.getName());
			SystemUser selectedDoctor = doctorService.selectByTrueName(doctor.getName());
			if (selectedDoctor == null) {
				doctor.setHostipalId(hostipal.getId());
				doctor.setIsValid(false);
				doctor.setId(UUID.randomUUID().toString());
				doctor.setPassword("NOPASSWORD");
				doctor.setUsername(doctor.getId().replaceAll("-", ""));
				doctorService.insert(doctor);
				logger.info("注册医生 " + doctor.getName());
			} else {
				doctor = selectedDoctor;
			}
//			if (false) {
//				return Status.PARAM_ERROR;
//			}
			user.setId(UUID.randomUUID().toString());
			user.setRegisterDate(new Date());
			user.setLastestDate(new Date());
			user.setDoctorId(doctor.getId());
			userService.insert(user);
			logger.info("注册用户 " + user.getName());
			Status status = new Status(Status.SUCCESSED_CODE,user.getId()); 
			return status;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Status.FAILED;
	}
}
