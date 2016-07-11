/**
 * 
 */
package web;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.TreeSet;

import taiyi.web.model.dto.Status;

/**
 * @author <a href="mailto:jason19659@163.com">jason19659</a>
 *
 *         web
 *
 *         2016年3月9日
 */
public class Test {
	public static void main(String[] args)
			throws ClassNotFoundException, IllegalArgumentException, IllegalAccessException {
		TreeSet<Status> treeSet = new TreeSet<>();
		Class<?> sourceClass = Class.forName("taiyi.web.model.dto.Status");
		Field[] fields = sourceClass.getFields();
		for (Field field : fields) {
			try {
				Status status = (Status) field.get(sourceClass);
				treeSet.add(status);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		for (Status status : treeSet) {
			System.out.println("|" + status.getCode() + "|" + status.getMessage() + "|");
		}
	}
	// public static void main(String[] args) {
	// SleepReport sleepReport = new SleepReport(UUID.randomUUID().toString(),
	// "1",
	// new Date(), new Date(), 900, 900,
	// 900, 2700, 999, new Date());
	// System.out.println(JSON.toJSON(sleepReport));
	// User user = new User();
	// // user.setAccountId(0);
	// user.setBirthday(new Date( 94, 1, 23));
	// user.setAddress("");
	// user.setEssRank(0);
	// user.setHeight(175);
	// user.setName("test");
	// user.setWeight(66.0);
	// user.setId(UUID.randomUUID().toString());
	// System.out.println(JSON.toJSON(user));
	// BreatheReport breatheReport = new
	// BreatheReport(UUID.randomUUID().toString(),
	// "1", 44.4,100,100,100,100,new
	// Date(),1,12.3,22.2,22.3,55.0,100,1,1,1,1,1,1,1,1,1,1,1,1,(double)
	// 12,100,new
	// Date());
	// System.out.println(JSON.toJSONString(breatheReport,true));
	// System.out.println(JSON.toJSON(Status.FAILED));
	// }
}
