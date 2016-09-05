/**
 * 
 */
package taiyi.web.thread;

import java.util.Locale;

import net.sf.cglib.core.Local;
import taiyi.web.service.WebService;

/**
 * @author <a href="mailto:jason19659@163.com">jason19659</a>
 *
 * taiyi.web.thread
 *
 * 2016年4月27日
 */
public class GenerateReportThread extends Thread {
	private String reportId;
	private String basePath;
	private String servletRailPath;
	private WebService webService;
	private Locale locale;
	

	/**
	 * @param reportId
	 * @param basePath
	 * @param servletRailPath
	 */
	public GenerateReportThread( WebService webService,String reportId, String basePath, String servletRailPath) {
		this.webService = webService;
		this.reportId = reportId;
		this.basePath = basePath;
		this.servletRailPath = servletRailPath;
		locale = Locale.CHINA;
	}

	public GenerateReportThread( WebService webService,String reportId, String basePath, String servletRailPath,Locale locale) {
		this.webService = webService;
		this.reportId = reportId;
		this.basePath = basePath;
		this.servletRailPath = servletRailPath;
		this.locale = locale;
	}


	/* 
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		try {
			webService.generatePdfByReportId(reportId, basePath,servletRailPath,locale);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
