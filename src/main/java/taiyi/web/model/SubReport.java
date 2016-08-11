package taiyi.web.model;

import java.util.Date;

public class SubReport {
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
	// mac地址
	private String macAddress;
	// 医生建议
	private String advice;
	// 平均血流灌注度
	private Double perfusionIndex;
	// 总呼吸暂停时长
	private Integer totalApneaTimeSeconds;

	// 总低通气时长
	private Integer totalHypoventilationTimeSeconds;

	public SubReport() {
	}

	/**
	 * @param id
	 * @param oxygenReductionIndex
	 * @param longestApneaSeconds
	 * @param longestApneaTime
	 * @param maxOxygenReduceSeconds
	 * @param maxOxygenReduceTime
	 * @param averagePulse
	 * @param maxPulse
	 * @param maxPulseTime
	 * @param minPulse
	 * @param minPulseTime
	 * @param longestOxygenReduceSeconds
	 * @param longestOxygenReduceTime
	 * @param bloodOxygenHazardIndex
	 * @param macAddress
	 * @param advice
	 * @param perfusionIndex
	 * @param totalApneaTimeSeconds
	 * @param totalHypoventilationTimeSeconds
	 */
	public SubReport(String id, Double oxygenReductionIndex, Integer longestApneaSeconds, Date longestApneaTime,
			Integer maxOxygenReduceSeconds, Date maxOxygenReduceTime, Double averagePulse, Double maxPulse,
			Date maxPulseTime, Double minPulse, Date minPulseTime, Integer longestOxygenReduceSeconds,
			Date longestOxygenReduceTime, Double bloodOxygenHazardIndex, String macAddress, String advice,
			Double perfusionIndex, Integer totalApneaTimeSeconds, Integer totalHypoventilationTimeSeconds) {
		super();
		this.id = id;
		this.oxygenReductionIndex = oxygenReductionIndex;
		this.longestApneaSeconds = longestApneaSeconds;
		this.longestApneaTime = longestApneaTime;
		this.maxOxygenReduceSeconds = maxOxygenReduceSeconds;
		this.maxOxygenReduceTime = maxOxygenReduceTime;
		this.averagePulse = averagePulse;
		this.maxPulse = maxPulse;
		this.maxPulseTime = maxPulseTime;
		this.minPulse = minPulse;
		this.minPulseTime = minPulseTime;
		this.longestOxygenReduceSeconds = longestOxygenReduceSeconds;
		this.longestOxygenReduceTime = longestOxygenReduceTime;
		this.bloodOxygenHazardIndex = bloodOxygenHazardIndex;
		this.macAddress = macAddress;
		this.advice = advice;
		this.perfusionIndex = perfusionIndex;
		this.totalApneaTimeSeconds = totalApneaTimeSeconds;
		this.totalHypoventilationTimeSeconds = totalHypoventilationTimeSeconds;
	}

	public String getId() {
		return id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column sub_report.id
	 *
	 * @param id
	 *            the value for sub_report.id
	 *
	 * @mbggenerated Mon Aug 08 14:08:43 CST 2016
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column sub_report.oxygen_reduction_index
	 *
	 * @return the value of sub_report.oxygen_reduction_index
	 *
	 * @mbggenerated Mon Aug 08 14:08:43 CST 2016
	 */
	public Double getOxygenReductionIndex() {
		return oxygenReductionIndex;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column sub_report.oxygen_reduction_index
	 *
	 * @param oxygenReductionIndex
	 *            the value for sub_report.oxygen_reduction_index
	 *
	 * @mbggenerated Mon Aug 08 14:08:43 CST 2016
	 */
	public void setOxygenReductionIndex(Double oxygenReductionIndex) {
		this.oxygenReductionIndex = oxygenReductionIndex;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column sub_report.longest_apnea_seconds
	 *
	 * @return the value of sub_report.longest_apnea_seconds
	 *
	 * @mbggenerated Mon Aug 08 14:08:43 CST 2016
	 */
	public Integer getLongestApneaSeconds() {
		return longestApneaSeconds;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column sub_report.longest_apnea_seconds
	 *
	 * @param longestApneaSeconds
	 *            the value for sub_report.longest_apnea_seconds
	 *
	 * @mbggenerated Mon Aug 08 14:08:43 CST 2016
	 */
	public void setLongestApneaSeconds(Integer longestApneaSeconds) {
		this.longestApneaSeconds = longestApneaSeconds;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column sub_report.longest_apnea_time
	 *
	 * @return the value of sub_report.longest_apnea_time
	 *
	 * @mbggenerated Mon Aug 08 14:08:43 CST 2016
	 */
	public Date getLongestApneaTime() {
		return longestApneaTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column sub_report.longest_apnea_time
	 *
	 * @param longestApneaTime
	 *            the value for sub_report.longest_apnea_time
	 *
	 * @mbggenerated Mon Aug 08 14:08:43 CST 2016
	 */
	public void setLongestApneaTime(Date longestApneaTime) {
		this.longestApneaTime = longestApneaTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column sub_report.max_oxygen_reduce_seconds
	 *
	 * @return the value of sub_report.max_oxygen_reduce_seconds
	 *
	 * @mbggenerated Mon Aug 08 14:08:43 CST 2016
	 */
	public Integer getMaxOxygenReduceSeconds() {
		return maxOxygenReduceSeconds;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column sub_report.max_oxygen_reduce_seconds
	 *
	 * @param maxOxygenReduceSeconds
	 *            the value for sub_report.max_oxygen_reduce_seconds
	 *
	 * @mbggenerated Mon Aug 08 14:08:43 CST 2016
	 */
	public void setMaxOxygenReduceSeconds(Integer maxOxygenReduceSeconds) {
		this.maxOxygenReduceSeconds = maxOxygenReduceSeconds;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column sub_report.max_oxygen_reduce_time
	 *
	 * @return the value of sub_report.max_oxygen_reduce_time
	 *
	 * @mbggenerated Mon Aug 08 14:08:43 CST 2016
	 */
	public Date getMaxOxygenReduceTime() {
		return maxOxygenReduceTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column sub_report.max_oxygen_reduce_time
	 *
	 * @param maxOxygenReduceTime
	 *            the value for sub_report.max_oxygen_reduce_time
	 *
	 * @mbggenerated Mon Aug 08 14:08:43 CST 2016
	 */
	public void setMaxOxygenReduceTime(Date maxOxygenReduceTime) {
		this.maxOxygenReduceTime = maxOxygenReduceTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column sub_report.average_pulse
	 *
	 * @return the value of sub_report.average_pulse
	 *
	 * @mbggenerated Mon Aug 08 14:08:43 CST 2016
	 */
	public Double getAveragePulse() {
		return averagePulse;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column sub_report.average_pulse
	 *
	 * @param averagePulse
	 *            the value for sub_report.average_pulse
	 *
	 * @mbggenerated Mon Aug 08 14:08:43 CST 2016
	 */
	public void setAveragePulse(Double averagePulse) {
		this.averagePulse = averagePulse;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column sub_report.max_pulse
	 *
	 * @return the value of sub_report.max_pulse
	 *
	 * @mbggenerated Mon Aug 08 14:08:43 CST 2016
	 */
	public Double getMaxPulse() {
		return maxPulse;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column sub_report.max_pulse
	 *
	 * @param maxPulse
	 *            the value for sub_report.max_pulse
	 *
	 * @mbggenerated Mon Aug 08 14:08:43 CST 2016
	 */
	public void setMaxPulse(Double maxPulse) {
		this.maxPulse = maxPulse;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column sub_report.max_pulse_time
	 *
	 * @return the value of sub_report.max_pulse_time
	 *
	 * @mbggenerated Mon Aug 08 14:08:43 CST 2016
	 */
	public Date getMaxPulseTime() {
		return maxPulseTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column sub_report.max_pulse_time
	 *
	 * @param maxPulseTime
	 *            the value for sub_report.max_pulse_time
	 *
	 * @mbggenerated Mon Aug 08 14:08:43 CST 2016
	 */
	public void setMaxPulseTime(Date maxPulseTime) {
		this.maxPulseTime = maxPulseTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column sub_report.min_pulse
	 *
	 * @return the value of sub_report.min_pulse
	 *
	 * @mbggenerated Mon Aug 08 14:08:43 CST 2016
	 */
	public Double getMinPulse() {
		return minPulse;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column sub_report.min_pulse
	 *
	 * @param minPulse
	 *            the value for sub_report.min_pulse
	 *
	 * @mbggenerated Mon Aug 08 14:08:43 CST 2016
	 */
	public void setMinPulse(Double minPulse) {
		this.minPulse = minPulse;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column sub_report.min_pulse_time
	 *
	 * @return the value of sub_report.min_pulse_time
	 *
	 * @mbggenerated Mon Aug 08 14:08:43 CST 2016
	 */
	public Date getMinPulseTime() {
		return minPulseTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column sub_report.min_pulse_time
	 *
	 * @param minPulseTime
	 *            the value for sub_report.min_pulse_time
	 *
	 * @mbggenerated Mon Aug 08 14:08:43 CST 2016
	 */
	public void setMinPulseTime(Date minPulseTime) {
		this.minPulseTime = minPulseTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column sub_report.longest_oxygen_reduce_seconds
	 *
	 * @return the value of sub_report.longest_oxygen_reduce_seconds
	 *
	 * @mbggenerated Mon Aug 08 14:08:43 CST 2016
	 */
	public Integer getLongestOxygenReduceSeconds() {
		return longestOxygenReduceSeconds;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column sub_report.longest_oxygen_reduce_seconds
	 *
	 * @param longestOxygenReduceSeconds
	 *            the value for sub_report.longest_oxygen_reduce_seconds
	 *
	 * @mbggenerated Mon Aug 08 14:08:43 CST 2016
	 */
	public void setLongestOxygenReduceSeconds(Integer longestOxygenReduceSeconds) {
		this.longestOxygenReduceSeconds = longestOxygenReduceSeconds;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column sub_report.longest_oxygen_reduce_time
	 *
	 * @return the value of sub_report.longest_oxygen_reduce_time
	 *
	 * @mbggenerated Mon Aug 08 14:08:43 CST 2016
	 */
	public Date getLongestOxygenReduceTime() {
		return longestOxygenReduceTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column sub_report.longest_oxygen_reduce_time
	 *
	 * @param longestOxygenReduceTime
	 *            the value for sub_report.longest_oxygen_reduce_time
	 *
	 * @mbggenerated Mon Aug 08 14:08:43 CST 2016
	 */
	public void setLongestOxygenReduceTime(Date longestOxygenReduceTime) {
		this.longestOxygenReduceTime = longestOxygenReduceTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column sub_report.blood_oxygen_hazard_index
	 *
	 * @return the value of sub_report.blood_oxygen_hazard_index
	 *
	 * @mbggenerated Mon Aug 08 14:08:43 CST 2016
	 */
	public Double getBloodOxygenHazardIndex() {
		return bloodOxygenHazardIndex;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column sub_report.blood_oxygen_hazard_index
	 *
	 * @param bloodOxygenHazardIndex
	 *            the value for sub_report.blood_oxygen_hazard_index
	 *
	 * @mbggenerated Mon Aug 08 14:08:43 CST 2016
	 */
	public void setBloodOxygenHazardIndex(Double bloodOxygenHazardIndex) {
		this.bloodOxygenHazardIndex = bloodOxygenHazardIndex;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column sub_report.mac_address
	 *
	 * @return the value of sub_report.mac_address
	 *
	 * @mbggenerated Mon Aug 08 14:08:43 CST 2016
	 */
	public String getMacAddress() {
		return macAddress;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column sub_report.mac_address
	 *
	 * @param macAddress
	 *            the value for sub_report.mac_address
	 *
	 * @mbggenerated Mon Aug 08 14:08:43 CST 2016
	 */
	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column sub_report.advice
	 *
	 * @return the value of sub_report.advice
	 *
	 * @mbggenerated Mon Aug 08 14:08:43 CST 2016
	 */
	public String getAdvice() {
		return advice;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column sub_report.advice
	 *
	 * @param advice
	 *            the value for sub_report.advice
	 *
	 * @mbggenerated Mon Aug 08 14:08:43 CST 2016
	 */
	public void setAdvice(String advice) {
		this.advice = advice;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column sub_report.perfusion_index
	 *
	 * @return the value of sub_report.perfusion_index
	 *
	 * @mbggenerated Mon Aug 08 14:08:43 CST 2016
	 */
	public Double getPerfusionIndex() {
		return perfusionIndex;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column sub_report.perfusion_index
	 *
	 * @param perfusionIndex
	 *            the value for sub_report.perfusion_index
	 *
	 * @mbggenerated Mon Aug 08 14:08:43 CST 2016
	 */
	public void setPerfusionIndex(Double perfusionIndex) {
		this.perfusionIndex = perfusionIndex;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column sub_report.total_apnea_time_seconds
	 *
	 * @return the value of sub_report.total_apnea_time_seconds
	 *
	 * @mbggenerated Mon Aug 08 14:08:43 CST 2016
	 */
	public Integer getTotalApneaTimeSeconds() {
		return totalApneaTimeSeconds == null ? 0 : totalApneaTimeSeconds;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column sub_report.total_apnea_time_seconds
	 *
	 * @param totalApneaTimeSeconds
	 *            the value for sub_report.total_apnea_time_seconds
	 *
	 * @mbggenerated Mon Aug 08 14:08:43 CST 2016
	 */
	public void setTotalApneaTimeSeconds(Integer totalApneaTimeSeconds) {
		this.totalApneaTimeSeconds = totalApneaTimeSeconds;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the
	 * value of the database column
	 * sub_report.total_hypoventilation_time_seconds
	 *
	 * @return the value of sub_report.total_hypoventilation_time_seconds
	 *
	 * @mbggenerated Mon Aug 08 14:08:43 CST 2016
	 */
	public Integer getTotalHypoventilationTimeSeconds() {
		return totalHypoventilationTimeSeconds  == null ? 0 : totalHypoventilationTimeSeconds;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the
	 * value of the database column
	 * sub_report.total_hypoventilation_time_seconds
	 *
	 * @param totalHypoventilationTimeSeconds
	 *            the value for sub_report.total_hypoventilation_time_seconds
	 *
	 * @mbggenerated Mon Aug 08 14:08:43 CST 2016
	 */
	public void setTotalHypoventilationTimeSeconds(Integer totalHypoventilationTimeSeconds) {
		this.totalHypoventilationTimeSeconds = totalHypoventilationTimeSeconds;
	}
}