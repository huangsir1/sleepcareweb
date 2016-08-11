	/**
 * 
 */
package taiyi.web.controller.admin;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import taiyi.web.controller.api.APIExceptionHandlerController;
import taiyi.web.model.Device;
import taiyi.web.model.Hostipal;
import taiyi.web.model.SleepReport;
import taiyi.web.model.SystemUser;
import taiyi.web.model.User;
import taiyi.web.model.dto.PageModel;
import taiyi.web.model.dto.ReportPreviewDto;
import taiyi.web.model.dto.Status;
import taiyi.web.service.DeviceService;
import taiyi.web.service.HostipalService;
import taiyi.web.service.SleepReportService;
import taiyi.web.service.SystemUserService;
import taiyi.web.service.UserService;
import taiyi.web.service.WebService;

/**
 * @author <a href="mailto:jason19659@163.com">jason19659</a>
 *
 *         taiyi.web.controller.admin
 *
 *         2016年7月5日
 */
@Controller
public class HospitalController extends APIExceptionHandlerController {
	/*
	
	 */
	@Autowired
	private HostipalService hospitalService;
	@Autowired
	private DeviceService deviceService;
	@Autowired
	private SystemUserService systemUserService;
	@Autowired
	private UserService userService;
	@Autowired
	private WebService webService;
	@Autowired
	private SleepReportService sleepReportService;

	@RequiresPermissions("system:view")
	@RequestMapping("admin/hospital")
	public String hospital() {
		return "admin/showHospital";
	}

	@RequiresPermissions("system:view")
	@RequestMapping("admin/getAllHospital")
	@ResponseBody
	public Map<String, Object> getAllHospital(@RequestParam(required = false) Integer page,
			@RequestParam(required = false) Integer rows) {
		if (page != null && rows != null) {
			PageHelper.startPage(page, rows);
		}
		List<Hostipal> hospitals = hospitalService.selectAll();
		PageInfo<Hostipal> pageinfo = new PageInfo<Hostipal>(hospitals);
		PageModel pageModel = new PageModel(pageinfo.getTotal(), hospitals);
		return pageModel.get();
	}

	@RequiresPermissions("system:insert")
	@RequestMapping("admin/saveHospital")
	@ResponseBody
	public Status addHospital(Hostipal hospital) {
		hospital.setId(null);
		hospital.setRegDate(new Date());
		hospitalService.insert(hospital);
		return Status.SUCCESSED;
	}
	
	@RequiresPermissions("system:update")
	@RequestMapping("admin/editHospital")
	@ResponseBody
	public Status editHospital(Hostipal hospital) {
		hospitalService.updateByPrimaryKeySelective(hospital);
		return Status.SUCCESSED;
	}

	@RequiresPermissions("system:delete")
	@RequestMapping("/admin/deleteHospital")
	@ResponseBody
	public Status deleteHospital(Integer hospitalId) {
		try {
			if (!deviceService.selectByHostipalId(hospitalId).isEmpty()
					|| !systemUserService.selectByHostipal(hospitalId).isEmpty()) {
				return Status.CANNOT_DELETE_WITHOUT_DELETE_ALL_SUBITEM;
			}
			hospitalService.deleteByPrimaryKey(hospitalId);
			return Status.SUCCESSED;
		} catch (Exception e) {
			e.printStackTrace();
			return Status.FAILED;
		}

	}
	
	@RequiresPermissions("system:view")
	@RequestMapping("/admin/getReportByHospitalAndUser/{hospitalId}/{userId}")
	@ResponseBody
	public HashMap<String, Object> getUserByHospitalAndUserId(@RequestParam(required = false) Integer page,
			@RequestParam(required = false) Integer rows,@PathVariable Integer hospitalId,@PathVariable String userId) {
		if (page != null && rows != null) {
			PageHelper.startPage(page, rows);
		}
		List<SleepReport> sleepReports = sleepReportService.selectByHostipalIdAndUserId(userId, hospitalId);
		PageInfo<SleepReport> pageinfo = new PageInfo<SleepReport>(sleepReports);
		List<ReportPreviewDto> packagePerviewReportDto = webService.packagePerviewReportDto(sleepReports);
		PageModel pageModel = new PageModel(pageinfo.getTotal(), packagePerviewReportDto);
		return pageModel.get();
	}
	
	@RequiresPermissions("system:view")
	@RequestMapping("/admin/getUserByHospital/{hospitalId}")
	@ResponseBody
	public HashMap<String, Object> getUserByHospitalId(@RequestParam(required = false) Integer page,
			@RequestParam(required = false) Integer rows,@PathVariable Integer hospitalId) {
		if (page != null && rows != null) {
			PageHelper.startPage(page, rows);
		}
		List<User> users = userService.selectUserByHostipalId(hospitalId);
		PageInfo<User> pageinfo = new PageInfo<User>(users);
		PageModel pageModel = new PageModel(pageinfo.getTotal(), users);
		return pageModel.get();
	}

	@RequiresPermissions("hospital:view")
	@RequestMapping("/admin/getHospitalUsers")
	@ResponseBody
	public HashMap<String, Object> getHospitalUsers(@RequestParam(required = false) Integer page,
			@RequestParam(required = false) Integer rows) {
		if (page != null && rows != null) {
			PageHelper.startPage(page, rows);
		}
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		SystemUser user = (SystemUser) session.getAttribute("user");
		List<User> users = userService.selectUserByHostipalId(user.getHostipalId());
		PageInfo<User> pageinfo = new PageInfo<User>(users);
		PageModel pageModel = new PageModel(pageinfo.getTotal(), users);
		return pageModel.get();
	}

	@RequiresPermissions("hospital:view")
	@RequestMapping("/admin/getHospitalReport/{userId}")
	@ResponseBody
	public List<ReportPreviewDto> getHospitalReport(@PathVariable String userId) {
		SystemUser user = (SystemUser) SecurityUtils.getSubject().getSession().getAttribute("user");
		List<SleepReport> sleepReports = sleepReportService.selectByHostipalIdAndUserId(userId, user.getHostipalId());
		List<ReportPreviewDto> packagePerviewReportDto = webService.packagePerviewReportDto(sleepReports);
		return packagePerviewReportDto;
	}

	@RequiresPermissions("hospital:view")
	@RequestMapping("/admin/searchHospitalUsers")
	@ResponseBody
	public HashMap<String, Object> searchUsers(User user, @RequestParam(required = false) String birthdays,
			@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer rows) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			user.setBirthday(simpleDateFormat.parse(birthdays));
		} catch (Exception e) {
			// TODO: handle exception
		}
		if (!StringUtils.isEmpty(user.getName())) {
			user.setName(user.getName() + "%");
		}
		if (!StringUtils.isEmpty(user.getGender())) {
			user.setGender(user.getGender() + "%");
		}
		if (!StringUtils.isEmpty(user.getPhone())) {
			user.setPhone(user.getPhone() + "%");
		}
		if (!StringUtils.isEmpty(user.getAddress())) {
			user.setAddress(user.getAddress() + "%");
		}

		Session session = SecurityUtils.getSubject().getSession();
		SystemUser systemUser = (SystemUser) session.getAttribute("user");

		PageModel pageModel = userService.searchUsersByPage(user,systemUser.getHostipalId(), page, rows);
		return pageModel.get();

	}

	@RequiresPermissions("system:view")
	@RequestMapping("/admin/getDeviceByHostapilId/{hospitalId}")
	@ResponseBody
	public List<Device> getDeviceByHostapilId(@PathVariable Integer hospitalId) {
		return deviceService.selectByHostipalId(hospitalId);
	}

	@RequiresPermissions("system:delete")
	@RequestMapping("/admin/deleteDevice")
	@ResponseBody
	public Status deleteDevice(String macAddress) {
		deviceService.deleteByPrimaryKey(macAddress);
		return Status.SUCCESSED;
	}

	@RequiresPermissions("system:insert")
	@RequestMapping("/admin/addDevice")
	@ResponseBody
	public Status addDevice(Device device) {
		device.setRegDate(new Date());
		deviceService.insert(device);
		return Status.SUCCESSED;
	}

	@RequiresPermissions("hospital:view")
	@RequestMapping("/admin/showHospitalUser")
	public String showUser() {
		return "hospital/showUser";
	}

	@RequiresPermissions("hospital:view")
	@RequestMapping("/admin/searchHospitalUser")
	public String searchUser() {
		return "hospital/searchUser";
	}

}