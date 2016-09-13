/**
 * 
 */
package taiyi.web.service;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import taiyi.web.model.SleepReport;
import taiyi.web.model.dto.BaseReport;
import taiyi.web.model.dto.ReportPreviewDto;
import taiyi.web.model.dto.UserEssAndDHDto;

/**
 * @author <a href="mailto:jason19659@163.com">jason19659</a>
 *
 *         taiyi.web.service
 *
 *         2016年3月30日
 */
public interface WebService {
	Map<String, Integer[]> getReportNumber(String reportId);

	String generateValidateCode();

	void insertReport(BaseReport report);
	
	void updateReport(BaseReport report);

	void deleteReport(String id);

	boolean isReportAllReady(String reportId);

	String generatePdfByReportId(String reportId, String basePath, String servletRailPath) throws Exception;

	void flushPdf(HttpServletRequest request, HttpServletResponse response, String reportId,Locale locale) throws IOException;

	Locale isReportPdfExist(String reportId);

	List<ReportPreviewDto> packagePerviewReportDtoByUserId(String userId);
	
	List<ReportPreviewDto> packagePerviewReportDto(List<SleepReport> sleepReports);

	BaseReport selectById(String reportId) throws IllegalAccessException, InvocationTargetException;

	void flushFile(HttpServletRequest request, HttpServletResponse response, String reportId) throws IOException;

	public String generatePdfByReportId(String reportId, String basePath, String servletRailPath, Locale locale)
			throws Exception;


	boolean isReportPdfFileExist(String reportId, Locale locale);

	void deletePdf(String reportId);

	List<UserEssAndDHDto> selectUserEssAndDHByToken(String token);
}
