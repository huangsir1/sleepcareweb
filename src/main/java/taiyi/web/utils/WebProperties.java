/**
 * 
 */
package taiyi.web.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.Locale;
import java.util.Properties;

/**
 * @author <a href="mailto:jason19659@163.com">jason19659</a>
 *
 *         读取配置字段的工具类
 *
 *         taiyi.web.utils
 *
 *         2016年4月27日
 */
public class WebProperties {
	public static String readValue(String key) {
		Properties props = new Properties();
		try {
			InputStream in = new BufferedInputStream(WebProperties.class.getResourceAsStream("/config.properties"));

			props.load(in);
			String value = props.getProperty(key);
//			 System.out.println("key:" + key + " " + "value:" + value);
			in.close();
			return value;
		} catch (Exception e) {
			return null;
		}
	}

	public static String getReportFileName(String userId, String reportId) {
		String fileName = WebProperties.getFilePath() + File.separator + userId + File.separator + reportId
				+ File.separator + "source.txt";
		return fileName;
	}

	public static String getReportPdfName(String userId, String reportId) {
		String fileName = WebProperties.getFilePath() + File.separator + userId + File.separator + reportId
				+ File.separator + "report.pdf";
		return fileName;
	}

	public static String getReportPdfName(String userId, String reportId, Locale locale) {
		String fileName = WebProperties.getFilePath() + File.separator + userId + File.separator + reportId
				+ File.separator + "report_" + locale.getLanguage() + ".pdf";
		return fileName;
	}

	public static String getFilePath() {
		String os = System.getProperty("os.name").toLowerCase();
		String filePath = null;
		if (os.contains("windows")) {
			filePath = readValue("base_win_filePath");
		} else {
			filePath = readValue("base_linux_filePath");
		}
		return filePath;
	}

}
