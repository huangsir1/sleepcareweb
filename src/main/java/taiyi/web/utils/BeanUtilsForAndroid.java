/**
 * 
 */
package taiyi.web.utils;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.UUID;
import java.util.logging.Logger;

import taiyi.web.model.BreatheReport;
import taiyi.web.model.SleepReport;
import taiyi.web.model.SubReport;
import taiyi.web.model.dto.BaseReport;

/**
 * @author <a href="mailto:jason19659@163.com">jason19659</a>
 *
 * taiyi.web.jason
 *
 * 2016年4月21日
 */
public class BeanUtilsForAndroid {
	public static void main(String[] args) {
		SubReport subReport = new SubReport("12312313", 11.0, 11,new Date(),
				12, new Date(123213123l),11.1, 12.1,
				new Date(), 11.111, new Date(), 13,
				new Date(), 12.1,"1230-12123213","sb");
		SleepReport sleepReport = new SleepReport(UUID.randomUUID().toString(), "1", new Date(), new Date(), 900, 900,
				900, 2700, 999, new Date());
		sleepReport.setUserId("123");
		BreatheReport br = new BreatheReport();
		br.setUserId("123");
		BaseReport baseReport = new BaseReport();
		copy(sleepReport, baseReport);
		copy(subReport, baseReport);
		copy(br, baseReport);
		System.out.println(baseReport);
	
	}
	
	
	public static void copy(Object source,Object destination) {
		Class<?> sourceClass = source.getClass();
		Method[] sourceMethods = sourceClass.getMethods();
		for (Method method : sourceMethods) {
			String methodName = method.getName();
			if ((methodName.startsWith("get") || methodName.startsWith("is")) && !methodName.equals("getClass")) {
				try{
					Class<?> returnType = method.getReturnType();
					Object value = method.invoke(source);
					Class<? extends Object> br = destination.getClass();
					Method destMethod;
					if (methodName.startsWith("g")) {
						destMethod = br.getDeclaredMethod(methodName.replaceFirst("get", "set"),returnType);
					} else {
						destMethod = br.getDeclaredMethod(methodName.replaceFirst("is", "set"),returnType);
					}
					destMethod.invoke(destination, value);
				} catch(Exception e){
					Logger logger = Logger.getLogger("logger");
					logger.warning("BeanUtilsForAndroid : "+ methodName + " 不存在相对应的setter或者getter");
				}
				
			}

		}
	}
}
