/**
 * 
 */
package web;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.github.pagehelper.PageHelper;

import taiyi.web.dao.DeviceMapper;
import taiyi.web.dao.SleepReportMapper;
import taiyi.web.dao.SystemPermissionMapper;
import taiyi.web.dao.SystemRoleMapper;
import taiyi.web.dao.UserMapper;
import taiyi.web.model.BreatheReport;
import taiyi.web.model.SleepReport;
import taiyi.web.model.SubReport;
import taiyi.web.model.User;
import taiyi.web.model.dto.BaseReport;
import taiyi.web.service.BreatheReportService;
import taiyi.web.service.PermissionService;
import taiyi.web.service.SleepReportService;
import taiyi.web.service.SubReportService;
import taiyi.web.service.UserService;
import taiyi.web.service.Impl.BreatheReportServiceImpl;
import taiyi.web.service.Impl.PermissionServiceImpl;
import taiyi.web.service.Impl.SleepReportServiceImpl;
import taiyi.web.service.Impl.SubReportServiceImpl;
import taiyi.web.service.Impl.UserServiceImpl;

/**
 * @author <a href="mailto:jason19659@163.com">jason19659</a>
 *
 *         web
 *
 *         2016年3月10日
 */
public class TestService {
	private static ApplicationContext context;
	private static UserMapper userMapper;
	private static UserService userService;
	private static SleepReportService sleepReportService;
	private static BreatheReportService breatheReportService;
	private static SubReportService subReportService;
	private static SystemRoleMapper sysmapper;
	private static SystemPermissionMapper systemPermissionMapper;
	private static PermissionService permissionService;
	private static DeviceMapper deviceMapper;
	private static SleepReportMapper sleepReportMapper;

	@BeforeClass
	public static void a() {
		context = new ClassPathXmlApplicationContext(new String[] { "classpath:spring-base.xml",
				"classpath:spring-mvc.xml", "classpath:spring-mybatis.xml" });
		userMapper = context.getBean(UserMapper.class);
		userService = context.getBean(UserServiceImpl.class);
		sleepReportService = context.getBean(SleepReportServiceImpl.class);
		breatheReportService = context.getBean(BreatheReportServiceImpl.class);
		subReportService = context.getBean(SubReportServiceImpl.class);
		sysmapper = context.getBean(SystemRoleMapper.class);
		systemPermissionMapper = context.getBean(SystemPermissionMapper.class);
		permissionService = context.getBean(PermissionServiceImpl.class);
		deviceMapper = context.getBean(DeviceMapper.class);
		sleepReportMapper = context.getBean(SleepReportMapper.class);
	}

	@Test
	public void testttt() {
		User user = new User();
		user.setName("王%");
		System.out.println(userMapper.countHospitalUsers(user, 1));
		;
		
	}

	@Test
	public void testMapper() {
		String id = "00000000-0000-0000-0000-000000000000";
		String username = "admin";
		System.out.println(permissionService.selectRoleStringsByUserId(id));
		System.out.println(permissionService.selectPermissionsByUserId(id));
		System.err.println(sysmapper.selectRoleStringsByUsername(username));
		System.err.println(systemPermissionMapper.selectPermissionsByUsername(username));

	}

	@Test
	public void testReport() throws IllegalAccessException, InvocationTargetException {
		String id = "2e87291b-2394-4ea5-ae68-79c487e8690b";
		SleepReport sleepReport = sleepReportService.selectByPrimaryKey(id);
		BreatheReport breatheReport = breatheReportService.selectByPrimaryKey(id);
		SubReport subReport = subReportService.selectByPrimaryKey(id);
		BaseReport baseReport = new BaseReport(sleepReport, breatheReport, subReport);
		System.out.println(JSON.toJSON(baseReport));
	}

	@Test
	public void test3() {
		PageHelper.startPage(1, 1);
		List<User> user = userService.selectAllWIthDH();
		System.out.println(user.get(0));
	}

	@Test
	public void test() {
		List<User> users = userMapper.selectAllWithDH();
		for (User u : users) {
			System.out.println(u);
		}
	}

	@Test
	public void test2() {
		System.out.println(userMapper.getAllUsers());
	}

	@Test
	public void test4() {
		SleepReport sr = new SleepReport();
		sr.setUserId("1");
		sr.setId("1231222ss31313");
		sr.setStartTime(new Date(10000));
		sleepReportService.insert(sr);
	}

	public static void main(String[] args) {
		User user2 = JSON.parseObject("{\"id\":\"1231231\"}", User.class,Feature.InitStringFieldAsEmpty);
		System.out.println(user2);
	}
}
