/**
 * 
 */
package taiyi.web.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.deser.Deserializers.Base;

import taiyi.web.model.SleepReport;
import taiyi.web.model.dto.BaseReport;
import taiyi.web.model.dto.ReportPreviewDto;

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

	void flushPdf(HttpServletRequest request, HttpServletResponse response, String reportId) throws IOException;

	boolean isReportPdfExist(String reportId);

	List<ReportPreviewDto> packagePerviewReportDtoByUserId(String userId);
	
	List<ReportPreviewDto> packagePerviewReportDto(List<SleepReport> sleepReports);
}
