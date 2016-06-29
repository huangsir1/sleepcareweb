package taiyi.web.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class BreatheReport {
	public BreatheReport() {
	}

	/**
	 * @param id
	 * @param userId
	 * @param apneaHypopneaIndex
	 * @param apneaTimes
	 * @param hypopneaTimes
	 * @param maxHyponeaSeconds
	 * @param totalHyponeaSeconds
	 * @param hyponeaHappenDate
	 * @param reducedOxygenTimes
	 * @param averageOxygenSaturation
	 * @param maxOxygenSaturation
	 * @param minOxygenSaturation
	 * @param odi
	 * @param awakeSeconds
	 * @param oxygenSaturationNinetyToHundredPercentTimes
	 * @param oxygenSaturationNinetyToHundredPercentHyponea
	 * @param oxygenSaturationEightyToEightyNinePercentTimes
	 * @param oxygenSaturationEightyToEightyNinePercentHyponea
	 * @param oxygenSaturationSeventyToSeventyNinePercentTimes
	 * @param oxygenSaturationSeventyToSeventyNinePercentHyponea
	 * @param oxygenSaturationSixtyToSixtyNinePercentTimes
	 * @param oxygenSaturationSixtyToSixtyNinePercentHyponea
	 * @param oxygenSaturationFiftyToFiftyNinePercentTimes
	 * @param oxygenSaturationFiftyToFiftyNinePercentHyponea
	 * @param oxygenSaturationLessthanFiftyPercentTimes
	 * @param oxygenSaturationLessthanFiftyPercentHyponea
	 * @param oxygenSaturationLessthanNinetyPercent
	 * @param score
	 * @param uploadDate
	 */
	public BreatheReport(String id, String userId, Double apneaHypopneaIndex, Integer apneaTimes, Integer hypopneaTimes,
			Integer maxHyponeaSeconds, Integer totalHyponeaSeconds, Date hyponeaHappenDate, Integer reducedOxygenTimes,
			Double averageOxygenSaturation, Double maxOxygenSaturation, Double minOxygenSaturation, Double odi,
			Integer awakeSeconds, Integer oxygenSaturationNinetyToHundredPercentTimes,
			Integer oxygenSaturationNinetyToHundredPercentHyponea,
			Integer oxygenSaturationEightyToEightyNinePercentTimes,
			Integer oxygenSaturationEightyToEightyNinePercentHyponea,
			Integer oxygenSaturationSeventyToSeventyNinePercentTimes,
			Integer oxygenSaturationSeventyToSeventyNinePercentHyponea,
			Integer oxygenSaturationSixtyToSixtyNinePercentTimes,
			Integer oxygenSaturationSixtyToSixtyNinePercentHyponea,
			Integer oxygenSaturationFiftyToFiftyNinePercentTimes,
			Integer oxygenSaturationFiftyToFiftyNinePercentHyponea, Integer oxygenSaturationLessthanFiftyPercentTimes,
			Integer oxygenSaturationLessthanFiftyPercentHyponea, Double oxygenSaturationLessthanNinetyPercent,
			Integer score, Date uploadDate) {
		super();
		this.id = id;
		this.userId = userId;
		this.apneaHypopneaIndex = apneaHypopneaIndex;
		this.apneaTimes = apneaTimes;
		this.hypopneaTimes = hypopneaTimes;
		this.maxHyponeaSeconds = maxHyponeaSeconds;
		this.totalHyponeaSeconds = totalHyponeaSeconds;
		this.hyponeaHappenDate = hyponeaHappenDate;
		this.reducedOxygenTimes = reducedOxygenTimes;
		this.averageOxygenSaturation = averageOxygenSaturation;
		this.maxOxygenSaturation = maxOxygenSaturation;
		this.minOxygenSaturation = minOxygenSaturation;
		this.odi = odi;
		this.awakeSeconds = awakeSeconds;
		this.oxygenSaturationNinetyToHundredPercentTimes = oxygenSaturationNinetyToHundredPercentTimes;
		this.oxygenSaturationNinetyToHundredPercentHyponea = oxygenSaturationNinetyToHundredPercentHyponea;
		this.oxygenSaturationEightyToEightyNinePercentTimes = oxygenSaturationEightyToEightyNinePercentTimes;
		this.oxygenSaturationEightyToEightyNinePercentHyponea = oxygenSaturationEightyToEightyNinePercentHyponea;
		this.oxygenSaturationSeventyToSeventyNinePercentTimes = oxygenSaturationSeventyToSeventyNinePercentTimes;
		this.oxygenSaturationSeventyToSeventyNinePercentHyponea = oxygenSaturationSeventyToSeventyNinePercentHyponea;
		this.oxygenSaturationSixtyToSixtyNinePercentTimes = oxygenSaturationSixtyToSixtyNinePercentTimes;
		this.oxygenSaturationSixtyToSixtyNinePercentHyponea = oxygenSaturationSixtyToSixtyNinePercentHyponea;
		this.oxygenSaturationFiftyToFiftyNinePercentTimes = oxygenSaturationFiftyToFiftyNinePercentTimes;
		this.oxygenSaturationFiftyToFiftyNinePercentHyponea = oxygenSaturationFiftyToFiftyNinePercentHyponea;
		this.oxygenSaturationLessthanFiftyPercentTimes = oxygenSaturationLessthanFiftyPercentTimes;
		this.oxygenSaturationLessthanFiftyPercentHyponea = oxygenSaturationLessthanFiftyPercentHyponea;
		this.oxygenSaturationLessthanNinetyPercent = oxygenSaturationLessthanNinetyPercent;
		this.score = score;
		this.uploadDate = uploadDate;
	}

	// 呼吸id
	private String id;
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

	private Integer score;

	private Date uploadDate;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Double getApneaHypopneaIndex() {
		return apneaHypopneaIndex;
	}

	public void setApneaHypopneaIndex(Double apneaHypopneaIndex) {
		this.apneaHypopneaIndex = apneaHypopneaIndex;
	}

	public Integer getApneaTimes() {
		return apneaTimes;
	}

	public void setApneaTimes(Integer apneaTimes) {
		this.apneaTimes = apneaTimes;
	}

	public Integer getHypopneaTimes() {
		return hypopneaTimes;
	}

	public void setHypopneaTimes(Integer hypopneaTimes) {
		this.hypopneaTimes = hypopneaTimes;
	}

	public Integer getMaxHyponeaSeconds() {
		return maxHyponeaSeconds;
	}

	public void setMaxHyponeaSeconds(Integer maxHyponeaSeconds) {
		this.maxHyponeaSeconds = maxHyponeaSeconds;
	}

	public Integer getTotalHyponeaSeconds() {
		return totalHyponeaSeconds;
	}

	public void setTotalHyponeaSeconds(Integer totalHyponeaSeconds) {
		this.totalHyponeaSeconds = totalHyponeaSeconds;
	}

	@JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getHyponeaHappenDate() {
		return hyponeaHappenDate;
	}

	public void setHyponeaHappenDate(Date hyponeaHappenDate) {
		this.hyponeaHappenDate = hyponeaHappenDate;
	}

	public Integer getReducedOxygenTimes() {
		return reducedOxygenTimes;
	}

	public void setReducedOxygenTimes(Integer reducedOxygenTimes) {
		this.reducedOxygenTimes = reducedOxygenTimes;
	}

	public Double getAverageOxygenSaturation() {
		return averageOxygenSaturation;
	}

	public void setAverageOxygenSaturation(Double averageOxygenSaturation) {
		this.averageOxygenSaturation = averageOxygenSaturation;
	}

	public Double getMaxOxygenSaturation() {
		return maxOxygenSaturation;
	}

	public void setMaxOxygenSaturation(Double maxOxygenSaturation) {
		this.maxOxygenSaturation = maxOxygenSaturation;
	}

	public Double getMinOxygenSaturation() {
		return minOxygenSaturation;
	}

	public void setMinOxygenSaturation(Double minOxygenSaturation) {
		this.minOxygenSaturation = minOxygenSaturation;
	}

	public Double getOdi() {
		return odi;
	}

	public void setOdi(Double odi) {
		this.odi = odi;
	}

	public Integer getAwakeSeconds() {
		return awakeSeconds;
	}

	public void setAwakeSeconds(Integer awakeSeconds) {
		this.awakeSeconds = awakeSeconds;
	}

	public Integer getOxygenSaturationNinetyToHundredPercentTimes() {
		return oxygenSaturationNinetyToHundredPercentTimes;
	}

	public void setOxygenSaturationNinetyToHundredPercentTimes(Integer oxygenSaturationNinetyToHundredPercentTimes) {
		this.oxygenSaturationNinetyToHundredPercentTimes = oxygenSaturationNinetyToHundredPercentTimes;
	}

	public Integer getOxygenSaturationNinetyToHundredPercentHyponea() {
		return oxygenSaturationNinetyToHundredPercentHyponea;
	}

	public void setOxygenSaturationNinetyToHundredPercentHyponea(
			Integer oxygenSaturationNinetyToHundredPercentHyponea) {
		this.oxygenSaturationNinetyToHundredPercentHyponea = oxygenSaturationNinetyToHundredPercentHyponea;
	}

	public Integer getOxygenSaturationEightyToEightyNinePercentTimes() {
		return oxygenSaturationEightyToEightyNinePercentTimes;
	}

	public void setOxygenSaturationEightyToEightyNinePercentTimes(
			Integer oxygenSaturationEightyToEightyNinePercentTimes) {
		this.oxygenSaturationEightyToEightyNinePercentTimes = oxygenSaturationEightyToEightyNinePercentTimes;
	}

	public Integer getOxygenSaturationEightyToEightyNinePercentHyponea() {
		return oxygenSaturationEightyToEightyNinePercentHyponea;
	}

	public void setOxygenSaturationEightyToEightyNinePercentHyponea(
			Integer oxygenSaturationEightyToEightyNinePercentHyponea) {
		this.oxygenSaturationEightyToEightyNinePercentHyponea = oxygenSaturationEightyToEightyNinePercentHyponea;
	}

	public Integer getOxygenSaturationSeventyToSeventyNinePercentTimes() {
		return oxygenSaturationSeventyToSeventyNinePercentTimes;
	}

	public void setOxygenSaturationSeventyToSeventyNinePercentTimes(
			Integer oxygenSaturationSeventyToSeventyNinePercentTimes) {
		this.oxygenSaturationSeventyToSeventyNinePercentTimes = oxygenSaturationSeventyToSeventyNinePercentTimes;
	}

	public Integer getOxygenSaturationSeventyToSeventyNinePercentHyponea() {
		return oxygenSaturationSeventyToSeventyNinePercentHyponea;
	}

	public void setOxygenSaturationSeventyToSeventyNinePercentHyponea(
			Integer oxygenSaturationSeventyToSeventyNinePercentHyponea) {
		this.oxygenSaturationSeventyToSeventyNinePercentHyponea = oxygenSaturationSeventyToSeventyNinePercentHyponea;
	}

	public Integer getOxygenSaturationSixtyToSixtyNinePercentTimes() {
		return oxygenSaturationSixtyToSixtyNinePercentTimes;
	}

	public void setOxygenSaturationSixtyToSixtyNinePercentTimes(Integer oxygenSaturationSixtyToSixtyNinePercentTimes) {
		this.oxygenSaturationSixtyToSixtyNinePercentTimes = oxygenSaturationSixtyToSixtyNinePercentTimes;
	}

	public Integer getOxygenSaturationSixtyToSixtyNinePercentHyponea() {
		return oxygenSaturationSixtyToSixtyNinePercentHyponea;
	}

	public void setOxygenSaturationSixtyToSixtyNinePercentHyponea(
			Integer oxygenSaturationSixtyToSixtyNinePercentHyponea) {
		this.oxygenSaturationSixtyToSixtyNinePercentHyponea = oxygenSaturationSixtyToSixtyNinePercentHyponea;
	}

	public Integer getOxygenSaturationFiftyToFiftyNinePercentTimes() {
		return oxygenSaturationFiftyToFiftyNinePercentTimes;
	}

	public void setOxygenSaturationFiftyToFiftyNinePercentTimes(Integer oxygenSaturationFiftyToFiftyNinePercentTimes) {
		this.oxygenSaturationFiftyToFiftyNinePercentTimes = oxygenSaturationFiftyToFiftyNinePercentTimes;
	}

	public Integer getOxygenSaturationFiftyToFiftyNinePercentHyponea() {
		return oxygenSaturationFiftyToFiftyNinePercentHyponea;
	}

	public void setOxygenSaturationFiftyToFiftyNinePercentHyponea(
			Integer oxygenSaturationFiftyToFiftyNinePercentHyponea) {
		this.oxygenSaturationFiftyToFiftyNinePercentHyponea = oxygenSaturationFiftyToFiftyNinePercentHyponea;
	}

	public Integer getOxygenSaturationLessthanFiftyPercentTimes() {
		return oxygenSaturationLessthanFiftyPercentTimes;
	}

	public void setOxygenSaturationLessthanFiftyPercentTimes(Integer oxygenSaturationLessthanFiftyPercentTimes) {
		this.oxygenSaturationLessthanFiftyPercentTimes = oxygenSaturationLessthanFiftyPercentTimes;
	}

	public Integer getOxygenSaturationLessthanFiftyPercentHyponea() {
		return oxygenSaturationLessthanFiftyPercentHyponea;
	}

	public void setOxygenSaturationLessthanFiftyPercentHyponea(Integer oxygenSaturationLessthanFiftyPercentHyponea) {
		this.oxygenSaturationLessthanFiftyPercentHyponea = oxygenSaturationLessthanFiftyPercentHyponea;
	}

	public Double getOxygenSaturationLessthanNinetyPercent() {
		return oxygenSaturationLessthanNinetyPercent;
	}

	public void setOxygenSaturationLessthanNinetyPercent(Double oxygenSaturationLessthanNinetyPercent) {
		this.oxygenSaturationLessthanNinetyPercent = oxygenSaturationLessthanNinetyPercent;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	@JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}
}