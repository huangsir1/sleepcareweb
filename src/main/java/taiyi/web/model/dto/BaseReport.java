/**
 * 
 */
package taiyi.web.model.dto;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;

import taiyi.web.model.BreatheReport;
import taiyi.web.model.SleepReport;
import taiyi.web.model.SubReport;

/**
 * @author <a href="mailto:jason19659@163.com">jason19659</a>
 *
 *         taiyi.web.model.dto
 *
 *         2016年4月18日
 */
public class BaseReport {
	// reportId
	private String id;
	// OxygenReductionIndex
	private Double oxygenReductionIndex;
	// 最长呼吸暂停时长
	private Integer longestApneaSeconds;
	// 最长呼吸暂停发生时间
	private Date longestApneaTime;
	// 最大氧降
	private Integer maxOxygenReduceSeconds;
	// 最大氧降发生时间
	private Date maxOxygenReduceTime;
	// 平均脉率
	private Double averagePulse;
	// 最大脉率
	private Double maxPulse;
	// 最大脉率发生时间
	private Date maxPulseTime;
	// 最小脉率
	private Double minPulse;
	// 最小脉率发生时间
	private Date minPulseTime;
	// 最长氧降时长
	private Integer longestOxygenReduceSeconds;
	// 最长氧降发生时间
	private Date longestOxygenReduceTime;
	// 血氧危害指数
	private Double bloodOxygenHazardIndex;

	// 用户id
	private String userId;
	// 呼吸暂停低通气指数
	private Double apneaHypopneaIndex;
	// 呼吸暂停次数
	private Integer apneaTimes;
	// 低通气次数
	private Integer hypopneaTimes;
	// 最长低通气秒数
	private Integer maxHyponeaSeconds;
	// 低通气总时间秒数
	private Integer totalHyponeaSeconds;
	// 低通气发生时间
	private Date hyponeaHappenDate;
	// 氧减次数
	private Integer reducedOxygenTimes;
	// 平均血氧饱和度
	private Double averageOxygenSaturation;
	// 最高血氧饱和度
	private Double maxOxygenSaturation;
	// 最低血氧饱和度
	private Double minOxygenSaturation;

	private Double odi;
	// 醒时
	private Integer awakeSeconds;
	// 血氧90%-100%的次数
	private Integer oxygenSaturationNinetyToHundredPercentTimes;
	// 血氧90%-100%的持续时间秒数
	private Integer oxygenSaturationNinetyToHundredPercentHyponea;
	// 血氧80%-89%的次数
	private Integer oxygenSaturationEightyToEightyNinePercentTimes;
	// 血氧80%-89%的持续时间秒数
	private Integer oxygenSaturationEightyToEightyNinePercentHyponea;
	// 血氧70%-79%的次数
	private Integer oxygenSaturationSeventyToSeventyNinePercentTimes;
	// 血氧70%-79%的持续时间秒数
	private Integer oxygenSaturationSeventyToSeventyNinePercentHyponea;
	// 血氧60%-69%的次数
	private Integer oxygenSaturationSixtyToSixtyNinePercentTimes;
	// 血氧60%-69%的持续时间秒数
	private Integer oxygenSaturationSixtyToSixtyNinePercentHyponea;
	// 血氧50%-59%的次数
	private Integer oxygenSaturationFiftyToFiftyNinePercentTimes;
	// 血氧50%-59%的持续时间秒数
	private Integer oxygenSaturationFiftyToFiftyNinePercentHyponea;
	// 血氧<50%的次数
	private Integer oxygenSaturationLessthanFiftyPercentTimes;
	// 血氧<50%的持续时间秒数
	private Integer oxygenSaturationLessthanFiftyPercentHyponea;
	// 血氧低于90%的百分比
	private Double oxygenSaturationLessthanNinetyPercent;
	// 呼吸评分
	private Integer brathe_score;
	// 上传日期
	private Date uploadDate;

	private Date startTime;

	private Date endTime;

	private Integer lightSleepSeconds;

	private Integer deepSleepSeconds;

	private Integer totalSeconds;

	private Integer sleep_score;

	private Integer sleep_awake;

	private String macAddress;

	private String advice;

	private Double perfusionIndex;

	// 总呼吸暂停时长
	private Integer totalApneaTimeSeconds;

	// 总低通气时长
	private Integer totalHypoventilationTimeSeconds;
	
	private String appVersion;
	
	

	/**
	 * @return the appVersion
	 */
	public String getAppVersion() {
		return appVersion;
	}

	/**
	 * @param appVersion the appVersion to set
	 */
	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}

	public BaseReport() {

	}

	public BaseReport(SleepReport sleepReport, BreatheReport breatheReport, SubReport subReport)
			throws IllegalAccessException, InvocationTargetException {
		ConvertUtils.register(new org.apache.commons.beanutils.converters.DateConverter(null), java.util.Date.class);
		BeanUtils.copyProperties(this, sleepReport);
		BeanUtils.copyProperties(this, breatheReport);
		BeanUtils.copyProperties(this, subReport);
		this.awakeSeconds = breatheReport.getAwakeSeconds();
		this.sleep_awake = sleepReport.getAwakeSeconds();
		this.sleep_score = sleepReport.getScore();
		this.brathe_score = breatheReport.getScore();
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the oxygenReductionIndex
	 */
	public Double getOxygenReductionIndex() {
		return oxygenReductionIndex;
	}

	/**
	 * @param oxygenReductionIndex
	 *            the oxygenReductionIndex to set
	 */
	public void setOxygenReductionIndex(Double oxygenReductionIndex) {
		this.oxygenReductionIndex = oxygenReductionIndex;
	}

	/**
	 * @return the longestApneaSeconds
	 */
	public Integer getLongestApneaSeconds() {
		return longestApneaSeconds;
	}

	/**
	 * @param longestApneaSeconds
	 *            the longestApneaSeconds to set
	 */
	public void setLongestApneaSeconds(Integer longestApneaSeconds) {
		this.longestApneaSeconds = longestApneaSeconds;
	}

	/**
	 * @return the longestApneaTime
	 */
	public Date getLongestApneaTime() {
		return longestApneaTime;
	}

	/**
	 * @param longestApneaTime
	 *            the longestApneaTime to set
	 */
	public void setLongestApneaTime(Date longestApneaTime) {
		this.longestApneaTime = longestApneaTime;
	}

	/**
	 * @return the maxOxygenReduceSeconds
	 */
	public Integer getMaxOxygenReduceSeconds() {
		return maxOxygenReduceSeconds;
	}

	/**
	 * @param maxOxygenReduceSeconds
	 *            the maxOxygenReduceSeconds to set
	 */
	public void setMaxOxygenReduceSeconds(Integer maxOxygenReduceSeconds) {
		this.maxOxygenReduceSeconds = maxOxygenReduceSeconds;
	}

	/**
	 * @return the maxOxygenReduceTime
	 */
	public Date getMaxOxygenReduceTime() {
		return maxOxygenReduceTime;
	}

	/**
	 * @param maxOxygenReduceTime
	 *            the maxOxygenReduceTime to set
	 */
	public void setMaxOxygenReduceTime(Date maxOxygenReduceTime) {
		this.maxOxygenReduceTime = maxOxygenReduceTime;
	}

	/**
	 * @return the averagePulse
	 */
	public Double getAveragePulse() {
		return averagePulse;
	}

	/**
	 * @param averagePulse
	 *            the averagePulse to set
	 */
	public void setAveragePulse(Double averagePulse) {
		this.averagePulse = averagePulse;
	}

	/**
	 * @return the maxPulse
	 */
	public Double getMaxPulse() {
		return maxPulse;
	}

	/**
	 * @param maxPulse
	 *            the maxPulse to set
	 */
	public void setMaxPulse(Double maxPulse) {
		this.maxPulse = maxPulse;
	}

	/**
	 * @return the maxPulseTime
	 */
	public Date getMaxPulseTime() {
		return maxPulseTime;
	}

	/**
	 * @param maxPulseTime
	 *            the maxPulseTime to set
	 */
	public void setMaxPulseTime(Date maxPulseTime) {
		this.maxPulseTime = maxPulseTime;
	}

	/**
	 * @return the minPulse
	 */
	public Double getMinPulse() {
		return minPulse;
	}

	/**
	 * @param minPulse
	 *            the minPulse to set
	 */
	public void setMinPulse(Double minPulse) {
		this.minPulse = minPulse;
	}

	/**
	 * @return the minPulseTime
	 */
	public Date getMinPulseTime() {
		return minPulseTime;
	}

	/**
	 * @param minPulseTime
	 *            the minPulseTime to set
	 */
	public void setMinPulseTime(Date minPulseTime) {
		this.minPulseTime = minPulseTime;
	}

	/**
	 * @return the longestOxygenReduceSeconds
	 */
	public Integer getLongestOxygenReduceSeconds() {
		return longestOxygenReduceSeconds;
	}

	/**
	 * @param longestOxygenReduceSeconds
	 *            the longestOxygenReduceSeconds to set
	 */
	public void setLongestOxygenReduceSeconds(Integer longestOxygenReduceSeconds) {
		this.longestOxygenReduceSeconds = longestOxygenReduceSeconds;
	}

	/**
	 * @return the longestOxygenReduceTime
	 */
	public Date getLongestOxygenReduceTime() {
		return longestOxygenReduceTime;
	}

	/**
	 * @param longestOxygenReduceTime
	 *            the longestOxygenReduceTime to set
	 */
	public void setLongestOxygenReduceTime(Date longestOxygenReduceTime) {
		this.longestOxygenReduceTime = longestOxygenReduceTime;
	}

	/**
	 * @return the bloodOxygenHazardIndex
	 */
	public Double getBloodOxygenHazardIndex() {
		return bloodOxygenHazardIndex;
	}

	/**
	 * @param bloodOxygenHazardIndex
	 *            the bloodOxygenHazardIndex to set
	 */
	public void setBloodOxygenHazardIndex(Double bloodOxygenHazardIndex) {
		this.bloodOxygenHazardIndex = bloodOxygenHazardIndex;
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return the apneaHypopneaIndex
	 */
	public Double getApneaHypopneaIndex() {
		return apneaHypopneaIndex;
	}

	/**
	 * @param apneaHypopneaIndex
	 *            the apneaHypopneaIndex to set
	 */
	public void setApneaHypopneaIndex(Double apneaHypopneaIndex) {
		this.apneaHypopneaIndex = apneaHypopneaIndex;
	}

	/**
	 * @return the apneaTimes
	 */
	public Integer getApneaTimes() {
		return apneaTimes;
	}

	/**
	 * @param apneaTimes
	 *            the apneaTimes to set
	 */
	public void setApneaTimes(Integer apneaTimes) {
		this.apneaTimes = apneaTimes;
	}

	/**
	 * @return the hypopneaTimes
	 */
	public Integer getHypopneaTimes() {
		return hypopneaTimes;
	}

	/**
	 * @param hypopneaTimes
	 *            the hypopneaTimes to set
	 */
	public void setHypopneaTimes(Integer hypopneaTimes) {
		this.hypopneaTimes = hypopneaTimes;
	}

	/**
	 * @return the maxHyponeaSeconds
	 */
	public Integer getMaxHyponeaSeconds() {
		return maxHyponeaSeconds;
	}

	/**
	 * @param maxHyponeaSeconds
	 *            the maxHyponeaSeconds to set
	 */
	public void setMaxHyponeaSeconds(Integer maxHyponeaSeconds) {
		this.maxHyponeaSeconds = maxHyponeaSeconds;
	}

	/**
	 * @return the totalHyponeaSeconds
	 */
	public Integer getTotalHyponeaSeconds() {
		return totalHyponeaSeconds;
	}

	/**
	 * @param totalHyponeaSeconds
	 *            the totalHyponeaSeconds to set
	 */
	public void setTotalHyponeaSeconds(Integer totalHyponeaSeconds) {
		this.totalHyponeaSeconds = totalHyponeaSeconds;
	}

	/**
	 * @return the hyponeaHappenDate
	 */
	public Date getHyponeaHappenDate() {
		return hyponeaHappenDate;
	}

	/**
	 * @param hyponeaHappenDate
	 *            the hyponeaHappenDate to set
	 */
	public void setHyponeaHappenDate(Date hyponeaHappenDate) {
		this.hyponeaHappenDate = hyponeaHappenDate;
	}

	/**
	 * @return the reducedOxygenTimes
	 */
	public Integer getReducedOxygenTimes() {
		return reducedOxygenTimes;
	}

	/**
	 * @param reducedOxygenTimes
	 *            the reducedOxygenTimes to set
	 */
	public void setReducedOxygenTimes(Integer reducedOxygenTimes) {
		this.reducedOxygenTimes = reducedOxygenTimes;
	}

	/**
	 * @return the averageOxygenSaturation
	 */
	public Double getAverageOxygenSaturation() {
		return averageOxygenSaturation;
	}

	/**
	 * @param averageOxygenSaturation
	 *            the averageOxygenSaturation to set
	 */
	public void setAverageOxygenSaturation(Double averageOxygenSaturation) {
		this.averageOxygenSaturation = averageOxygenSaturation;
	}

	/**
	 * @return the maxOxygenSaturation
	 */
	public Double getMaxOxygenSaturation() {
		return maxOxygenSaturation;
	}

	/**
	 * @param maxOxygenSaturation
	 *            the maxOxygenSaturation to set
	 */
	public void setMaxOxygenSaturation(Double maxOxygenSaturation) {
		this.maxOxygenSaturation = maxOxygenSaturation;
	}

	/**
	 * @return the minOxygenSaturation
	 */
	public Double getMinOxygenSaturation() {
		return minOxygenSaturation;
	}

	/**
	 * @param minOxygenSaturation
	 *            the minOxygenSaturation to set
	 */
	public void setMinOxygenSaturation(Double minOxygenSaturation) {
		this.minOxygenSaturation = minOxygenSaturation;
	}

	/**
	 * @return the odi
	 */
	public Double getOdi() {
		return odi;
	}

	/**
	 * @param odi
	 *            the odi to set
	 */
	public void setOdi(Double odi) {
		this.odi = odi;
	}

	/**
	 * @return the awakeSeconds
	 */
	public Integer getAwakeSeconds() {
		return awakeSeconds;
	}

	/**
	 * @param awakeSeconds
	 *            the awakeSeconds to set
	 */
	public void setAwakeSeconds(Integer awakeSeconds) {
		this.awakeSeconds = awakeSeconds;
	}

	/**
	 * @return the oxygenSaturationNinetyToHundredPercentTimes
	 */
	public Integer getOxygenSaturationNinetyToHundredPercentTimes() {
		return oxygenSaturationNinetyToHundredPercentTimes;
	}

	/**
	 * @param oxygenSaturationNinetyToHundredPercentTimes
	 *            the oxygenSaturationNinetyToHundredPercentTimes to set
	 */
	public void setOxygenSaturationNinetyToHundredPercentTimes(Integer oxygenSaturationNinetyToHundredPercentTimes) {
		this.oxygenSaturationNinetyToHundredPercentTimes = oxygenSaturationNinetyToHundredPercentTimes;
	}

	/**
	 * @return the oxygenSaturationNinetyToHundredPercentHyponea
	 */
	public Integer getOxygenSaturationNinetyToHundredPercentHyponea() {
		return oxygenSaturationNinetyToHundredPercentHyponea;
	}

	/**
	 * @param oxygenSaturationNinetyToHundredPercentHyponea
	 *            the oxygenSaturationNinetyToHundredPercentHyponea to set
	 */
	public void setOxygenSaturationNinetyToHundredPercentHyponea(
			Integer oxygenSaturationNinetyToHundredPercentHyponea) {
		this.oxygenSaturationNinetyToHundredPercentHyponea = oxygenSaturationNinetyToHundredPercentHyponea;
	}

	/**
	 * @return the oxygenSaturationEightyToEightyNinePercentTimes
	 */
	public Integer getOxygenSaturationEightyToEightyNinePercentTimes() {
		return oxygenSaturationEightyToEightyNinePercentTimes;
	}

	/**
	 * @param oxygenSaturationEightyToEightyNinePercentTimes
	 *            the oxygenSaturationEightyToEightyNinePercentTimes to set
	 */
	public void setOxygenSaturationEightyToEightyNinePercentTimes(
			Integer oxygenSaturationEightyToEightyNinePercentTimes) {
		this.oxygenSaturationEightyToEightyNinePercentTimes = oxygenSaturationEightyToEightyNinePercentTimes;
	}

	/**
	 * @return the oxygenSaturationEightyToEightyNinePercentHyponea
	 */
	public Integer getOxygenSaturationEightyToEightyNinePercentHyponea() {
		return oxygenSaturationEightyToEightyNinePercentHyponea;
	}

	/**
	 * @param oxygenSaturationEightyToEightyNinePercentHyponea
	 *            the oxygenSaturationEightyToEightyNinePercentHyponea to set
	 */
	public void setOxygenSaturationEightyToEightyNinePercentHyponea(
			Integer oxygenSaturationEightyToEightyNinePercentHyponea) {
		this.oxygenSaturationEightyToEightyNinePercentHyponea = oxygenSaturationEightyToEightyNinePercentHyponea;
	}

	/**
	 * @return the oxygenSaturationSeventyToSeventyNinePercentTimes
	 */
	public Integer getOxygenSaturationSeventyToSeventyNinePercentTimes() {
		return oxygenSaturationSeventyToSeventyNinePercentTimes;
	}

	/**
	 * @param oxygenSaturationSeventyToSeventyNinePercentTimes
	 *            the oxygenSaturationSeventyToSeventyNinePercentTimes to set
	 */
	public void setOxygenSaturationSeventyToSeventyNinePercentTimes(
			Integer oxygenSaturationSeventyToSeventyNinePercentTimes) {
		this.oxygenSaturationSeventyToSeventyNinePercentTimes = oxygenSaturationSeventyToSeventyNinePercentTimes;
	}

	/**
	 * @return the oxygenSaturationSeventyToSeventyNinePercentHyponea
	 */
	public Integer getOxygenSaturationSeventyToSeventyNinePercentHyponea() {
		return oxygenSaturationSeventyToSeventyNinePercentHyponea;
	}

	/**
	 * @param oxygenSaturationSeventyToSeventyNinePercentHyponea
	 *            the oxygenSaturationSeventyToSeventyNinePercentHyponea to set
	 */
	public void setOxygenSaturationSeventyToSeventyNinePercentHyponea(
			Integer oxygenSaturationSeventyToSeventyNinePercentHyponea) {
		this.oxygenSaturationSeventyToSeventyNinePercentHyponea = oxygenSaturationSeventyToSeventyNinePercentHyponea;
	}

	/**
	 * @return the oxygenSaturationSixtyToSixtyNinePercentTimes
	 */
	public Integer getOxygenSaturationSixtyToSixtyNinePercentTimes() {
		return oxygenSaturationSixtyToSixtyNinePercentTimes;
	}

	/**
	 * @param oxygenSaturationSixtyToSixtyNinePercentTimes
	 *            the oxygenSaturationSixtyToSixtyNinePercentTimes to set
	 */
	public void setOxygenSaturationSixtyToSixtyNinePercentTimes(Integer oxygenSaturationSixtyToSixtyNinePercentTimes) {
		this.oxygenSaturationSixtyToSixtyNinePercentTimes = oxygenSaturationSixtyToSixtyNinePercentTimes;
	}

	/**
	 * @return the oxygenSaturationSixtyToSixtyNinePercentHyponea
	 */
	public Integer getOxygenSaturationSixtyToSixtyNinePercentHyponea() {
		return oxygenSaturationSixtyToSixtyNinePercentHyponea;
	}

	/**
	 * @param oxygenSaturationSixtyToSixtyNinePercentHyponea
	 *            the oxygenSaturationSixtyToSixtyNinePercentHyponea to set
	 */
	public void setOxygenSaturationSixtyToSixtyNinePercentHyponea(
			Integer oxygenSaturationSixtyToSixtyNinePercentHyponea) {
		this.oxygenSaturationSixtyToSixtyNinePercentHyponea = oxygenSaturationSixtyToSixtyNinePercentHyponea;
	}

	/**
	 * @return the oxygenSaturationFiftyToFiftyNinePercentTimes
	 */
	public Integer getOxygenSaturationFiftyToFiftyNinePercentTimes() {
		return oxygenSaturationFiftyToFiftyNinePercentTimes;
	}

	/**
	 * @param oxygenSaturationFiftyToFiftyNinePercentTimes
	 *            the oxygenSaturationFiftyToFiftyNinePercentTimes to set
	 */
	public void setOxygenSaturationFiftyToFiftyNinePercentTimes(Integer oxygenSaturationFiftyToFiftyNinePercentTimes) {
		this.oxygenSaturationFiftyToFiftyNinePercentTimes = oxygenSaturationFiftyToFiftyNinePercentTimes;
	}

	/**
	 * @return the oxygenSaturationFiftyToFiftyNinePercentHyponea
	 */
	public Integer getOxygenSaturationFiftyToFiftyNinePercentHyponea() {
		return oxygenSaturationFiftyToFiftyNinePercentHyponea;
	}

	/**
	 * @param oxygenSaturationFiftyToFiftyNinePercentHyponea
	 *            the oxygenSaturationFiftyToFiftyNinePercentHyponea to set
	 */
	public void setOxygenSaturationFiftyToFiftyNinePercentHyponea(
			Integer oxygenSaturationFiftyToFiftyNinePercentHyponea) {
		this.oxygenSaturationFiftyToFiftyNinePercentHyponea = oxygenSaturationFiftyToFiftyNinePercentHyponea;
	}

	/**
	 * @return the oxygenSaturationLessthanFiftyPercentTimes
	 */
	public Integer getOxygenSaturationLessthanFiftyPercentTimes() {
		return oxygenSaturationLessthanFiftyPercentTimes;
	}

	/**
	 * @param oxygenSaturationLessthanFiftyPercentTimes
	 *            the oxygenSaturationLessthanFiftyPercentTimes to set
	 */
	public void setOxygenSaturationLessthanFiftyPercentTimes(Integer oxygenSaturationLessthanFiftyPercentTimes) {
		this.oxygenSaturationLessthanFiftyPercentTimes = oxygenSaturationLessthanFiftyPercentTimes;
	}

	/**
	 * @return the oxygenSaturationLessthanFiftyPercentHyponea
	 */
	public Integer getOxygenSaturationLessthanFiftyPercentHyponea() {
		return oxygenSaturationLessthanFiftyPercentHyponea;
	}

	/**
	 * @param oxygenSaturationLessthanFiftyPercentHyponea
	 *            the oxygenSaturationLessthanFiftyPercentHyponea to set
	 */
	public void setOxygenSaturationLessthanFiftyPercentHyponea(Integer oxygenSaturationLessthanFiftyPercentHyponea) {
		this.oxygenSaturationLessthanFiftyPercentHyponea = oxygenSaturationLessthanFiftyPercentHyponea;
	}

	/**
	 * @return the oxygenSaturationLessthanNinetyPercent
	 */
	public Double getOxygenSaturationLessthanNinetyPercent() {
		return oxygenSaturationLessthanNinetyPercent;
	}

	/**
	 * @param oxygenSaturationLessthanNinetyPercent
	 *            the oxygenSaturationLessthanNinetyPercent to set
	 */
	public void setOxygenSaturationLessthanNinetyPercent(Double oxygenSaturationLessthanNinetyPercent) {
		this.oxygenSaturationLessthanNinetyPercent = oxygenSaturationLessthanNinetyPercent;
	}

	/**
	 * @return the brathe_score
	 */
	public Integer getBrathe_score() {
		return brathe_score;
	}

	/**
	 * @param brathe_score
	 *            the brathe_score to set
	 */
	public void setBrathe_score(Integer brathe_score) {
		this.brathe_score = brathe_score;
	}

	/**
	 * @return the uploadDate
	 */
	public Date getUploadDate() {
		return uploadDate;
	}

	/**
	 * @param uploadDate
	 *            the uploadDate to set
	 */
	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}

	/**
	 * @return the startTime
	 */
	public Date getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime
	 *            the startTime to set
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return the endTime
	 */
	public Date getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime
	 *            the endTime to set
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	/**
	 * @return the lightSleepSeconds
	 */
	public Integer getLightSleepSeconds() {
		return lightSleepSeconds;
	}

	/**
	 * @param lightSleepSeconds
	 *            the lightSleepSeconds to set
	 */
	public void setLightSleepSeconds(Integer lightSleepSeconds) {
		this.lightSleepSeconds = lightSleepSeconds;
	}

	/**
	 * @return the deepSleepSeconds
	 */
	public Integer getDeepSleepSeconds() {
		return deepSleepSeconds;
	}

	/**
	 * @param deepSleepSeconds
	 *            the deepSleepSeconds to set
	 */
	public void setDeepSleepSeconds(Integer deepSleepSeconds) {
		this.deepSleepSeconds = deepSleepSeconds;
	}

	/**
	 * @return the totalSeconds
	 */
	public Integer getTotalSeconds() {
		return totalSeconds;
	}

	/**
	 * @param totalSeconds
	 *            the totalSeconds to set
	 */
	public void setTotalSeconds(Integer totalSeconds) {
		this.totalSeconds = totalSeconds;
	}

	/**
	 * @return the sleep_score
	 */
	public Integer getSleep_score() {
		return sleep_score;
	}

	/**
	 * @param sleep_score
	 *            the sleep_score to set
	 */
	public void setSleep_score(Integer sleep_score) {
		this.sleep_score = sleep_score;
	}

	@JsonIgnore
	@JSONField(serialize = false)
	public SleepReport getSleepReport() {
		return new SleepReport(id, userId, startTime, endTime, lightSleepSeconds, deepSleepSeconds, sleep_awake,
				totalSeconds, sleep_score, uploadDate);
	}

	@JsonIgnore
	@JSONField(serialize = false)
	public BreatheReport getBreatheReport() {
		return new BreatheReport(id, userId, apneaHypopneaIndex, apneaTimes, hypopneaTimes, maxHyponeaSeconds,
				totalHyponeaSeconds, hyponeaHappenDate, reducedOxygenTimes, averageOxygenSaturation,
				maxOxygenSaturation, minOxygenSaturation, odi, awakeSeconds,
				oxygenSaturationNinetyToHundredPercentTimes, oxygenSaturationNinetyToHundredPercentHyponea,
				oxygenSaturationEightyToEightyNinePercentTimes, oxygenSaturationEightyToEightyNinePercentHyponea,
				oxygenSaturationSeventyToSeventyNinePercentTimes, oxygenSaturationSeventyToSeventyNinePercentHyponea,
				oxygenSaturationSixtyToSixtyNinePercentTimes, oxygenSaturationSixtyToSixtyNinePercentHyponea,
				oxygenSaturationFiftyToFiftyNinePercentTimes, oxygenSaturationFiftyToFiftyNinePercentHyponea,
				oxygenSaturationLessthanFiftyPercentTimes, oxygenSaturationLessthanFiftyPercentHyponea,
				oxygenSaturationLessthanNinetyPercent, brathe_score, uploadDate);
	}

	@JsonIgnore
	@JSONField(serialize = false)
	public SubReport getSubReport() {
		return new SubReport(id, oxygenReductionIndex, longestApneaSeconds, longestApneaTime, maxOxygenReduceSeconds,
				maxOxygenReduceTime, averagePulse, maxPulse, maxPulseTime, minPulse, minPulseTime,
				longestOxygenReduceSeconds, longestOxygenReduceTime, bloodOxygenHazardIndex, macAddress, advice,
				perfusionIndex,totalApneaTimeSeconds,totalHypoventilationTimeSeconds,appVersion);
	}

	/**
	 * @return the sleep_awake
	 */
	public Integer getSleep_awake() {
		return sleep_awake;
	}

	/**
	 * @param sleep_awake
	 *            the sleep_awake to set
	 */
	public void setSleep_awake(Integer sleep_awake) {
		this.sleep_awake = sleep_awake;
	}

	/**
	 * @return the macAddress
	 */
	public String getMacAddress() {
		return macAddress;
	}

	/**
	 * @param macAddress
	 *            the macAddress to set
	 */
	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}

	/**
	 * @return the advice
	 */
	public String getAdvice() {
		return advice;
	}

	/**
	 * @param advice
	 *            the advice to set
	 */
	public void setAdvice(String advice) {
		this.advice = advice;
	}


	/**
	 * @return the totalApneaTimeSeconds
	 */
	public Integer getTotalApneaTimeSeconds() {
		return totalApneaTimeSeconds;
	}

	/**
	 * @param totalApneaTimeSeconds
	 *            the totalApneaTimeSeconds to set
	 */
	public void setTotalApneaTimeSeconds(Integer totalApneaTimeSeconds) {
		this.totalApneaTimeSeconds = totalApneaTimeSeconds;
	}

	

	/**
	 * @return the totalHypoventilationTimeSeconds
	 */
	public Integer getTotalHypoventilationTimeSeconds() {
		return totalHypoventilationTimeSeconds;
	}

	/**
	 * @param totalHypoventilationTimeSeconds the totalHypoventilationTimeSeconds to set
	 */
	public void setTotalHypoventilationTimeSeconds(Integer totalHypoventilationTimeSeconds) {
		this.totalHypoventilationTimeSeconds = totalHypoventilationTimeSeconds;
	}

	/**
	 * @return the perfusionIndex
	 */
	public Double getPerfusionIndex() {
		return perfusionIndex;
	}

	/**
	 * @param perfusionIndex
	 *            the perfusionIndex to set
	 */
	public void setPerfusionIndex(Double perfusionIndex) {
		this.perfusionIndex = perfusionIndex;
	}

	/* 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "BaseReport [id=" + id + ", oxygenReductionIndex=" + oxygenReductionIndex + ", longestApneaSeconds="
				+ longestApneaSeconds + ", longestApneaTime=" + longestApneaTime + ", maxOxygenReduceSeconds="
				+ maxOxygenReduceSeconds + ", maxOxygenReduceTime=" + maxOxygenReduceTime + ", averagePulse="
				+ averagePulse + ", maxPulse=" + maxPulse + ", maxPulseTime=" + maxPulseTime + ", minPulse=" + minPulse
				+ ", minPulseTime=" + minPulseTime + ", longestOxygenReduceSeconds=" + longestOxygenReduceSeconds
				+ ", longestOxygenReduceTime=" + longestOxygenReduceTime + ", bloodOxygenHazardIndex="
				+ bloodOxygenHazardIndex + ", userId=" + userId + ", apneaHypopneaIndex=" + apneaHypopneaIndex
				+ ", apneaTimes=" + apneaTimes + ", hypopneaTimes=" + hypopneaTimes + ", maxHyponeaSeconds="
				+ maxHyponeaSeconds + ", totalHyponeaSeconds=" + totalHyponeaSeconds + ", hyponeaHappenDate="
				+ hyponeaHappenDate + ", reducedOxygenTimes=" + reducedOxygenTimes + ", averageOxygenSaturation="
				+ averageOxygenSaturation + ", maxOxygenSaturation=" + maxOxygenSaturation + ", minOxygenSaturation="
				+ minOxygenSaturation + ", odi=" + odi + ", awakeSeconds=" + awakeSeconds
				+ ", oxygenSaturationNinetyToHundredPercentTimes=" + oxygenSaturationNinetyToHundredPercentTimes
				+ ", oxygenSaturationNinetyToHundredPercentHyponea=" + oxygenSaturationNinetyToHundredPercentHyponea
				+ ", oxygenSaturationEightyToEightyNinePercentTimes=" + oxygenSaturationEightyToEightyNinePercentTimes
				+ ", oxygenSaturationEightyToEightyNinePercentHyponea="
				+ oxygenSaturationEightyToEightyNinePercentHyponea
				+ ", oxygenSaturationSeventyToSeventyNinePercentTimes="
				+ oxygenSaturationSeventyToSeventyNinePercentTimes
				+ ", oxygenSaturationSeventyToSeventyNinePercentHyponea="
				+ oxygenSaturationSeventyToSeventyNinePercentHyponea + ", oxygenSaturationSixtyToSixtyNinePercentTimes="
				+ oxygenSaturationSixtyToSixtyNinePercentTimes + ", oxygenSaturationSixtyToSixtyNinePercentHyponea="
				+ oxygenSaturationSixtyToSixtyNinePercentHyponea + ", oxygenSaturationFiftyToFiftyNinePercentTimes="
				+ oxygenSaturationFiftyToFiftyNinePercentTimes + ", oxygenSaturationFiftyToFiftyNinePercentHyponea="
				+ oxygenSaturationFiftyToFiftyNinePercentHyponea + ", oxygenSaturationLessthanFiftyPercentTimes="
				+ oxygenSaturationLessthanFiftyPercentTimes + ", oxygenSaturationLessthanFiftyPercentHyponea="
				+ oxygenSaturationLessthanFiftyPercentHyponea + ", oxygenSaturationLessthanNinetyPercent="
				+ oxygenSaturationLessthanNinetyPercent + ", brathe_score=" + brathe_score + ", uploadDate="
				+ uploadDate + ", startTime=" + startTime + ", endTime=" + endTime + ", lightSleepSeconds="
				+ lightSleepSeconds + ", deepSleepSeconds=" + deepSleepSeconds + ", totalSeconds=" + totalSeconds
				+ ", sleep_score=" + sleep_score + ", sleep_awake=" + sleep_awake + ", macAddress=" + macAddress
				+ ", advice=" + advice + ", perfusionIndex=" + perfusionIndex + ", totalApneaTimeSeconds="
				+ totalApneaTimeSeconds + ", toralHypoventilationTimeSeconds=" + totalHypoventilationTimeSeconds + "]";
	}
	
	

}
