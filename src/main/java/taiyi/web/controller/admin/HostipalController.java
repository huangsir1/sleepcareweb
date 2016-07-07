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
public class HostipalController extends APIExceptionHandlerController {
	/*
	
	 */
	@Autowired
	private HostipalService hostipalService;
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
	@RequestMapping("admin/hostipal")
	public String hostipal() {
		return "admin/showHostipal";
	}

	@RequiresPermissions("system:view")
	@RequestMapping("admin/getAllHostipal")
	@ResponseBody
	public Map<String, Object> getAllHostipal(@RequestParam(required = false) Integer page,
			@RequestParam(required = false) Integer rows) {
		if (page != null && rows != null) {
			PageHelper.startPage(page, rows);
		}
		List<Hostipal> hostipals = hostipalService.selectAll();
		PageInfo<Hostipal> pageinfo = new PageInfo<Hostipal>(hostipals);
		PageModel pageModel = new PageModel(pageinfo.getTotal(), hostipals);
		return pageModel.get();
	}

	@RequiresPermissions("system:insert")
	@RequestMapping("admin/saveHostipal")
	@ResponseBody
	public Status addHostipal(Hostipal hostipal) {
		hostipal.setId(null);
		hostipal.setRegDate(new Date());
		hostipalService.insert(hostipal);
		return Status.SUCCESSED;
	}

	@RequiresPermissions("system:delete")
	@RequestMapping("/admin/deleteHostipal")
	@ResponseBody
	public Status deleteHostipal(Integer hostipalId) {
		try {
			if (!deviceService.selectByHostipalId(hostipalId).isEmpty()
					|| !systemUserService.selectByHostipal(hostipalId).isEmpty()) {
				return Status.CANNOT_DELETE_WITHOUT_DELETE_ALL_SUBITEM;
			}
			hostipalService.deleteByPrimaryKey(hostipalId);
			return Status.SUCCESSED;
		} catch (Exception e) {
			e.printStackTrace();
			return Status.FAILED;
		}

	}

	@RequiresPermissions("hostipal:view")
	@RequestMapping("/admin/getHostipalUsers")
	@ResponseBody
	public List<User> getHostipalUsers() {
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		SystemUser user = (SystemUser) session.getAttribute("user");
		List<User> users = userService.selectUserByHostipalId(user.getHostipalId());
		return users;
	}

	@RequiresPermissions("hostipal:view")
	@RequestMapping("/admin/getHostipalReport/{userId}")
	@ResponseBody
	public List<ReportPreviewDto> getHostipalReport(@PathVariable String userId) {
		SystemUser user = (SystemUser) SecurityUtils.getSubject().getSession().getAttribute("user");
		List<SleepReport> sleepReports = sleepReportService.selectByHostipalIdAndUserId(userId, user.getHostipalId());
		List<ReportPreviewDto> packagePerviewReportDto = webService.packagePerviewReportDto(sleepReports);
		return packagePerviewReportDto;
	}

	@RequiresPermissions("hostipal:view")
	@RequestMapping("/admin/searchHostipalUsers")
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
	@RequestMapping("/admin/getDeviceByHostapilId/{hostipalId}")
	@ResponseBody
	public List<Device> getDeviceByHostapilId(@PathVariable Integer hostipalId) {
		return deviceService.selectByHostipalId(hostipalId);
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

	@RequiresPermissions("hostipal:view")
	@RequestMapping("/admin/showHostipalUser")
	public String showUser() {
		return "hostipal/showUser";
	}

	@RequiresPermissions("hostipal:view")
	@RequestMapping("/admin/searchHostipalUser")
	public String searchUser() {
		return "hostipal/searchUser";
	}

}