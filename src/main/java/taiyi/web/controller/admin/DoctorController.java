/**
 * 
 */
package taiyi.web.controller.admin;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import taiyi.web.controller.ExceptionHandlerController;
import taiyi.web.model.DiseaseHistory;
import taiyi.web.model.User;
import taiyi.web.model.dto.BaseReport;
import taiyi.web.model.dto.BaseReportDto;
import taiyi.web.model.dto.PageModel;
import taiyi.web.service.BreatheReportService;
import taiyi.web.service.DiseaseHistoryUserService;
import taiyi.web.service.EssUserService;
import taiyi.web.service.SleepReportService;
import taiyi.web.service.SubReportService;
import taiyi.web.service.UserService;
import taiyi.web.service.WebService;
import taiyi.web.utils.WebProperties;

/**
 * @author <a href="mailto:jason19659@163.com">jason19659</a>
 *
 *         医生相关
 *
 *         taiyi.web.controller.admin
 *
 *         2016年6月3日
 */
@Controller
@RequestMapping("/admin")
public class DoctorController extends ExceptionHandlerController {
	@Autowired
	private UserService userService;
	@Autowired
	private SleepReportService sleepReportService;
	@Autowired
	private BreatheReportService breatheReportService;
	@Autowired
	private SubReportService subReportService;
	@Autowired
	private WebService webService;
	@Autowired
	private DiseaseHistoryUserService diseaseHistoryUserService;
	@Autowired
	private EssUserService essUesrService;

	/**
	 * 获取该登录医生下的所有用户
	 * @param page 页数
	 * @param rows 行数
	 * @return
	 */
	@RequiresPermissions("doctor:view")
	@RequestMapping("/getDoctorUsers")
	@ResponseBody
	public Map<String, Object> getAllUsers(@RequestParam(required = false) Integer page,
			@RequestParam(required = false) Integer rows) {
		if (page != null && rows != null) {
			PageHelper.startPage(page, rows);
		}
		String username = (String) SecurityUtils.getSubject().getPrincipal();
		List<User> users = userService.selectBySysUsername(username);
		PageInfo<User> pageinfo = new PageInfo<User>(users);
		PageModel pageModel = new PageModel(pageinfo.getTotal(), users);
		return pageModel.get();
	}
	
	@RequiresPermissions(logical = Logical.OR, value = { "user:view", "doctor:view", "hostipal:view" })
	@RequestMapping("doctor/showReport/{id}")
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
		return "doctor/report";
	}

	@RequiresPermissions("doctor:view")
	@RequestMapping("showDoctorUser")
	public String showUser() {
		return "doctor/showUser";
	}

	@RequiresPermissions("doctor:view")
	@RequestMapping("searchDoctorUser")
	public String searchUser() {
		return "doctor/searchUser";
	}

	@RequiresPermissions("user:insert") // doctor:insert")
	@RequestMapping("addDoctorUser")
	public String addUser() {
		return "doctor/addUser";
	}

	@RequiresPermissions(logical = Logical.OR, value = { "doctor:update", "hospital:update" })
	@RequestMapping("changePasswordUI")
	public String changePassword() {
		return "doctor/changePassword";
	}

}
