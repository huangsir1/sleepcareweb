	/**
 * 
 */
package taiyi.web.controller.admin;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
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
import taiyi.web.model.DiseaseHistory;
import taiyi.web.model.Hostipal;
import taiyi.web.model.SleepReport;
import taiyi.web.model.SystemUser;
import taiyi.web.model.User;
import taiyi.web.model.dto.BaseReport;
import taiyi.web.model.dto.BaseReportDto;
import taiyi.web.model.dto.PageModel;
import taiyi.web.model.dto.ReportPreviewDto;
import taiyi.web.model.dto.Status;
import taiyi.web.service.DeviceService;
import taiyi.web.service.HostipalService;
import taiyi.web.service.SleepReportService;
import taiyi.web.service.SystemUserService;
import taiyi.web.service.UserService;
import taiyi.web.service.WebService;
import taiyi.web.utils.WebProperties;

/**
 * @author <a href="mailto:jason19659@163.com">jason19659</a>
 *
 *         taiyi.web.controller.admin
 *
 *         2016年7月5日
 */
@Controller
public class HospitalController extends APIExceptionHandlerController {
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
	
	@RequiresPermissions(logical = Logical.OR, value = { "user:view", "doctor:view", "hostipal:view" })
	@RequestMapping("hostipal/showReport/{id}")
	public String showReport(@PathVariable String id, HttpServletRequest request) throws IllegalAccessException, InvocationTargetException {
		BaseReport baseReport = webService.selectById(id);
		BaseReportDto baseReportDto = new BaseReportDto();
		baseReportDto.copy(baseReport);
		 
		String fileName = WebProperties.getReportFileName(baseReport.getUserId(), baseReport.getId());
		User user = userService.selectWithDH(baseReport.getUserId());
		ResourceBundle resourceBundle = ResourceBundle.getBundle("messages", Locale.CHINA);
		for (DiseaseHistory d: user.getDiseaseHistories()) {
			d.setName(resourceBundle.getString(d.getName()));
		}
		
		request.setAttribute("baseReport", baseReportDto);
		request.setAttribute("user", user);
		request.setAttribute("file", new File(fileName).exists());
		return "hospital/report"; 
	}

	@RequiresPermissions("system:view")
	@RequestMapping("admin/hospital")
	public String hospital() {
		return "admin/showHospital";
	}

	/**
	 * 获取所有医院
	 * @param page 页数
	 * @param rows 行数
	 * @return
	 */
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

	/**
	 * 增加医院
	 * @param hospital 医院
	 * @return 结果
	 */
	@RequiresPermissions("system:insert")
	@RequestMapping("admin/saveHospital")
	@ResponseBody
	public Status addHospital(Hostipal hospital) {
		hospital.setId(null);
		hospital.setRegDate(new Date());
		hospitalService.insert(hospital);
		return Status.SUCCESSED;
	}
	
	/**
	 * 修改医院
	 * @param hospital 医院
	 * @return 结果
	 */
	@RequiresPermissions("system:update")
	@RequestMapping("admin/editHospital")
	@ResponseBody
	public Status editHospital(Hostipal hospital) {
		hospitalService.updateByPrimaryKeySelective(hospital);
		return Status.SUCCESSED;
	}

	/**
	 * 删除医院
	 * @param hospitalId 医院id
	 * @return
	 */
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
	
	/**
	 * 获取该医院下该用户的报告
	 * @param page 页数
	 * @param rows 行数
	 * @param hospitalId 医院id
	 * @param userId 用户id
	 * @return
	 */
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
	
	/**
	 * 获取该医院下的所有注册用户
	 * @param page 页数
	 * @param rows 行数
	 * @param hospitalId 医院id
	 * @return
	 */
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

	/**
	 * 获取登录用户所属医院下的所有用户
	 * @param page 页数
	 * @param rows 行数
	 * @return
	 */
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

	/**
	 * 获取登录用户所属医院下指定用户的报告列表
	 * @param userId 用户id
	 * @return
	 */
	@RequiresPermissions("hospital:view")
	@RequestMapping("/admin/getHospitalReport/{userId}")
	@ResponseBody
	public List<ReportPreviewDto> getHospitalReport(@PathVariable String userId) {
		SystemUser user = (SystemUser) SecurityUtils.getSubject().getSession().getAttribute("user");
		List<SleepReport> sleepReports = sleepReportService.selectByHostipalIdAndUserId(userId, user.getHostipalId());
		List<ReportPreviewDto> packagePerviewReportDto = webService.packagePerviewReportDto(sleepReports);
		return packagePerviewReportDto;
	}

	/**
	 * 搜索该医院下用户
	 * @param user
	 * @param birthdays
	 * @param page
	 * @param rows
	 * @return
	 */
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

	/**
	 * 获取该医院下设备
	 * @param hospitalId 医院id
	 * @return
	 */
	@RequiresPermissions("system:view")
	@RequestMapping("/admin/getDeviceByHostapilId/{hospitalId}")
	@ResponseBody
	public List<Device> getDeviceByHostapilId(@PathVariable Integer hospitalId) {
		return deviceService.selectByHostipalId(hospitalId);
	}

	/**
	 * 删除设备
	 * @param macAddress macAddress
	 * @return
	 */
	@RequiresPermissions("system:delete")
	@RequestMapping("/admin/deleteDevice")
	@ResponseBody
	public Status deleteDevice(String macAddress) {
		deviceService.deleteByPrimaryKey(macAddress);
		return Status.SUCCESSED;
	}

	/**
	 * 增加设备
	 * @param device 设备
	 * @return
	 */
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