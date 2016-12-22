/**
 * 
 */
package taiyi.web.model.dto;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;

/**
 * @author <a href="mailto:jason19659@163.com">jason19659</a>
 *
 *         taiyi.web.model.dto
 *
 *         2016年9月22日
 */
public class BaseReportDto extends BaseReport {
	private final java.text.DecimalFormat timeFormat = new java.text.DecimalFormat("00");

	private String monitoringDurationString;
	private String maxOxygenReduceSecondsString;
	private String longestOxygenReduceSecondsString;
	private String longestApneaSecondsString;
	private String totalApneaTimeSecondsString;
	private String maxHyponeaSecondsString;
	private String totalHypoventilationTimeSecondsString;
	private String lightSleepSecondsString;
	private String deepSleepSecondsString;
	private String awakeSecondsString;
	private String totalSecondsString;
	private String sleepResult;
	private String spO2Result;
	

	/**
	 * @return the sleepResult
	 */
	public String getSleepResult() {
		return sleepResult;
	}

	/**
	 * @return the spO2Result
	 */
	public String getSpO2Result() {
		return spO2Result;
	}

	public void copy(BaseReport baseReport) {
		ConvertUtils.register(new org.apache.commons.beanutils.converters.DateConverter(null), java.util.Date.class);
		try {
			BeanUtils.copyProperties(this, baseReport);
		} catch (IllegalAccessException|InvocationTargetException e1) {
			e1.printStackTrace();
		}

		try {
			monitoringDurationString = secondsToHHmmss(getEndTime().getTime() / 1000 - getStartTime().getTime() / 1000);
		} catch (Exception e) {
		}
		monitoringDurationString = secondsToHHmmss(getEndTime().getTime() / 1000 - getStartTime().getTime() / 1000);
		maxOxygenReduceSecondsString = secondsToHHmmss(getMaxOxygenReduceSeconds());
		longestOxygenReduceSecondsString = secondsToHHmmss(getLongestOxygenReduceSeconds());
		longestApneaSecondsString = secondsToHHmmss(getLongestApneaSeconds());
		totalApneaTimeSecondsString = secondsToHHmmss(getTotalApneaTimeSeconds());
		maxHyponeaSecondsString = secondsToHHmmss(getMaxHyponeaSeconds());
		totalHypoventilationTimeSecondsString = secondsToHHmmss(getTotalHypoventilationTimeSeconds());
		lightSleepSecondsString = secondsToHHmmss(getLightSleepSeconds());
		deepSleepSecondsString = secondsToHHmmss(getDeepSleepSeconds());
		awakeSecondsString = secondsToHHmmss(getAwakeSeconds());
		totalSecondsString = secondsToHHmmss(getTotalSeconds());
		sleepResult = getOSAHSResult();
		spO2Result = getSleepHypoxiaResult();
	}

	public String secondsToHHmmss(Integer seconds) {
		try {
			int hour = seconds / 3600;
			int minute = seconds % 3600 / 60;
			int second = seconds % 60;
			return timeFormat.format(hour) + "小时" + timeFormat.format(minute) + "分钟" + timeFormat.format(second) + "秒";
		} catch (Exception e) {

		}
		return "无数据";
	}

	public String secondsToHHmmss(Long seconds) {
		try {
			long hour = seconds / 3600;
			long minute = seconds % 3600 / 60;
			long second = seconds % 60;
			return timeFormat.format(hour) + "小时" + timeFormat.format(minute) + "分钟" + timeFormat.format(second) + "秒";
		} catch (Exception e) {

		}
		return "无数据";
	}

	/**
	 * @return the monitoringDurationString
	 */
	public String getMonitoringDurationString() {
		return monitoringDurationString;
	}

	/**
	 * @return the maxOxygenReduceSecondsString
	 */
	public String getMaxOxygenReduceSecondsString() {
		return maxOxygenReduceSecondsString;
	}

	/**
	 * @return the longestOxygenReduceSecondsString
	 */
	public String getLongestOxygenReduceSecondsString() {
		return longestOxygenReduceSecondsString;
	}

	/**
	 * @return the longestApneaSecondsString
	 */
	public String getLongestApneaSecondsString() {
		return longestApneaSecondsString;
	}

	/**
	 * @return the totalApneaTimeSecondsString
	 */
	public String getTotalApneaTimeSecondsString() {
		return totalApneaTimeSecondsString;
	}

	/**
	 * @return the maxHyponeaSecondsString
	 */
	public String getMaxHyponeaSecondsString() {
		return maxHyponeaSecondsString;
	}

	/**
	 * @return the totalHypoventilationTimeSecondsString
	 */
	public String getTotalHypoventilationTimeSecondsString() {
		return totalHypoventilationTimeSecondsString;
	}

	/**
	 * @return the lightSleepSecondsString
	 */
	public String getLightSleepSecondsString() {
		return lightSleepSecondsString;
	}

	/**
	 * @return the deepSleepSecondsString
	 */
	public String getDeepSleepSecondsString() {
		return deepSleepSecondsString;
	}

	/**
	 * @return the awakeSecondsString
	 */
	public String getAwakeSecondsString() {
		return awakeSecondsString;
	}

	/**
	 * @return the totalSecondsString
	 */
	public String getTotalSecondsString() {
		return totalSecondsString;
	}

	
	public String getOSAHSResult() {
		// List<DiseaseHistory> diseaseHistories = user.getDiseaseHistories();
		String result = "无";
		double AHI = getApneaHypopneaIndex();
		if (AHI <= 5 && getMinOxygenSaturation() >= 90)
			result = "无";
		if (AHI > 30) {
			result = "重度";
		} else if (AHI > 15) {
			result = "中度";
		} else if (AHI > 5) {
			result = "轻度";
		}
		return result;
	}
	
	public String getSleepHypoxiaResult() {
		Double lspo2 = getMinOxygenSaturation();
		String result = "无";
		if (lspo2 < 80) {
			result = "重度";
		} else if (lspo2 < 85) {
			result = "中度";
		} else if (lspo2 < 90) {
			result = "轻度";
		}
		return result;
	}
	
	
}
