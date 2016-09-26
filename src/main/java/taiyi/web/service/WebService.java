/**
 * 
 */
package taiyi.web.service;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import taiyi.web.model.SleepReport;
import taiyi.web.model.dto.BaseReport;
import taiyi.web.model.dto.ImageModel;
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
	/**
	 * 获取报告所需前段数据
	 * 
	 * @param reportId
	 * @return
	 */
	Map<String, Integer[]> getReportNumber(String reportId);

	/**
	 * 生成验证码
	 * 
	 * @return
	 */
	String generateValidateCode();

	void insertReport(BaseReport report);

	void updateReport(BaseReport report);

	void deleteReport(String id);

	/**
	 * 判断是否生成报告的所有条件都准备好
	 * 
	 * @param reportId
	 *            报告id
	 * @return
	 */
	boolean isReportAllReady(String reportId);

	/**
	 * 生成报告pdf
	 * 
	 * @param reportId
	 *            报告id
	 * @param basePath
	 *            服务器的路径
	 * @param servletRailPath
	 *            真实路径
	 * @return
	 * @throws Exception
	 */
	String generatePdfByReportId(String reportId, String basePath, String servletRailPath) throws Exception;

	/**
	 * 输出pdf文件
	 * 
	 * @param request
	 * @param response
	 * @param reportId
	 *            报告id
	 * @param locale
	 *            语言偏好
	 * @throws IOException
	 */
	void flushPdf(HttpServletRequest request, HttpServletResponse response, String reportId, Locale locale)
			throws IOException;

	/**
	 * 判断是否存在该报告
	 * 
	 * @param reportId
	 *            报告id
	 * @return
	 */
	Locale isReportPdfExist(String reportId);

	/**
	 * 根据用户生成报告预览数据
	 * 
	 * @param userId
	 *            用户id
	 * @return
	 */
	List<ReportPreviewDto> packagePerviewReportDtoByUserId(String userId);

	/**
	 * 根据报告生成报告预览数据
	 * 
	 * @param sleepReports
	 *            报告
	 * @return
	 */
	List<ReportPreviewDto> packagePerviewReportDto(List<SleepReport> sleepReports);

	BaseReport selectById(String reportId) throws IllegalAccessException, InvocationTargetException;

	/**
	 * 输出报告文件
	 * 
	 * @param request
	 * @param response
	 * @param reportId
	 * @throws IOException
	 */
	void flushFile(HttpServletRequest request, HttpServletResponse response, String reportId) throws IOException;

	/**
	 * 根据报告id生成pdf
	 * 
	 * @param reportId
	 *            报告id
	 * @param basePath
	 *            服务器路径
	 * @param servletRailPath
	 *            真实路径
	 * @param locale
	 *            语言偏好
	 * @return
	 * @throws Exception
	 */
	public String generatePdfByReportId(String reportId, String basePath, String servletRailPath, Locale locale)
			throws Exception;

	/**
	 * 判断报告pdf是否存在
	 * 
	 * @param reportId
	 *            报告id
	 * @param locale
	 *            语言
	 * @return
	 */
	boolean isReportPdfFileExist(String reportId, Locale locale);

	/**
	 * 删除报告
	 * 
	 * @param reportId
	 *            报告id
	 */
	void deletePdf(String reportId);

	/**
	 * 根据token查出用户，疾病，ess数据
	 * @param token token
	 * @return
	 */
	List<UserEssAndDHDto> selectUserEssAndDHByToken(String token);
	
	public Set<ImageModel> getReportData(String reportId,double percent);
}
