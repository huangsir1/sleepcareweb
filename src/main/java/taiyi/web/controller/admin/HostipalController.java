/**
 * 
 */
package taiyi.web.controller.admin;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.itextpdf.text.DocumentException;

import taiyi.web.controller.api.APIExceptionHandlerController;
import taiyi.web.model.Device;
import taiyi.web.model.Hostipal;
import taiyi.web.model.SystemUser;
import taiyi.web.model.dto.PageModel;
import taiyi.web.model.dto.Status;
import taiyi.web.service.DeviceService;
import taiyi.web.service.HostipalService;
import taiyi.web.service.SystemUserService;

/**
 * @author <a href="mailto:jason19659@163.com">jason19659</a>
 *
 * taiyi.web.controller.admin
 *
 * 2016年7月5日
 */
@Controller
public class HostipalController extends APIExceptionHandlerController{
	/*
	
	 */
	@Autowired
	private HostipalService hostipalService;
	@Autowired
	private DeviceService deviceService;
	@Autowired
	private SystemUserService systemUserService;

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
			if (!deviceService.selectByHostipalId(hostipalId).isEmpty() || !systemUserService.selectByHostipal(hostipalId).isEmpty()) {
				return Status.CANNOT_DELETE_WITHOUT_DELETE_ALL_SUBITEM;
			}
			hostipalService.deleteByPrimaryKey(hostipalId);
			return Status.SUCCESSED;
		} catch (Exception e) {
			e.printStackTrace();
			return Status.FAILED;
		}

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
	public Status deleteDevice( String macAddress) {
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
	
}

