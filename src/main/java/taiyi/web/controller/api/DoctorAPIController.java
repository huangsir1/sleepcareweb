/**
 * 
 */
package taiyi.web.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import taiyi.web.model.Hostipal;
import taiyi.web.model.SystemUser;
import taiyi.web.model.User;
import taiyi.web.model.dto.Status;
import taiyi.web.model.dto.UserDoctorAndHosptial;
import taiyi.web.service.DoctorAndHosptialService;

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
	@Autowired
	private DoctorAndHosptialService doctorAndHosptialService;
	
	@RequestMapping(value = "/uploadUserAndDoctor", consumes = "application/json")
	@ResponseBody
	public Status uploadUserAndDoctor(@RequestBody  UserDoctorAndHosptial userDoctorAndHosptial) {
		SystemUser doctor = userDoctorAndHosptial.getDoctor();
		User user = userDoctorAndHosptial.getUser();
		Hostipal hostipal = userDoctorAndHosptial.getHostipal();
		boolean success = doctorAndHosptialService.register(user,doctor,hostipal);
		if (success) {
			return Status.SUCCESSED;
		}
		return Status.FAILED;
		
	}
}
