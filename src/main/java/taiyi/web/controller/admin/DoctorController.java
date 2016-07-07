/**
 * 
 */
package taiyi.web.controller.admin;

import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import taiyi.web.controller.ExceptionHandlerController;
import taiyi.web.model.User;
import taiyi.web.model.dto.PageModel;
import taiyi.web.service.BreatheReportService;
import taiyi.web.service.DiseaseHistoryUserService;
import taiyi.web.service.EssUserService;
import taiyi.web.service.SleepReportService;
import taiyi.web.service.SubReportService;
import taiyi.web.service.UserService;
import taiyi.web.service.WebService;

/**
 * @author <a href="mailto:jason19659@163.com">jason19659</a>
 *
 * taiyi.web.controller.admin
 *
 * 2016年6月3日
 */
@Controller
@RequestMapping("/admin")
public class DoctorController extends ExceptionHandlerController{
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
	
	@RequiresPermissions("doctor:view")
	@RequestMapping("/getDoctorUsers")
	@ResponseBody
	public Map<String, Object> getAllUsers(@RequestParam(required = false) Integer page,
			@RequestParam(required = false) Integer rows) {
		if (page != null && rows != null) {
			PageHelper.startPage(page, rows);
		}
		String username =  (String) SecurityUtils.getSubject().getPrincipal();
		List<User> users = userService.selectBySysUsername(username);
		PageInfo<User> pageinfo = new PageInfo<User>(users);
		PageModel pageModel = new PageModel(pageinfo.getTotal(), users);
		return pageModel.get();
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
	@RequiresPermissions("user:insert")//doctor:insert")
	@RequestMapping("addDoctorUser")
	public String addUser() {
		return "doctor/addUser";
	}
	
	@RequiresPermissions(logical=Logical.OR, value={"doctor:update","hospital:update"})
	@RequestMapping("changePasswordUI")
	public String changePassword() {
		return "doctor/changePassword";
	}
	
}
