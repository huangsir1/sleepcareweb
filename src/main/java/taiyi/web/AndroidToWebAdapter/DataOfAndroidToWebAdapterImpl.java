/**
 *
 */
package taiyi.web.AndroidToWebAdapter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;

import taiyi.web.model.BreatheReport;
import taiyi.web.model.SleepReport;
import taiyi.web.model.SubReport;
import taiyi.web.model.dto.BaseReport;

/**
 * Implement by
 */
public class DataOfAndroidToWebAdapterImpl implements DataOfAndroidToWebAdapter {


	// 数据文件内数据的编码方式
	public static final int DATATYPE_ASCII = 0;
	public static final int DATATYPE_BINARY = 1;
	private int dataType = DATATYPE_ASCII; // 存储在文件中的数据类型

	public final String DATA_SEPARATOR = "@#$%@#$%@#$%@#$%@#$%@#$%@#$%@#$%";

	@Override
	public BaseReport originalFileToBaseReport(File file) {

		Sleep_ApneaDetection mSleep_ApneaDetection = null; // 呼吸指标计算类
		Sleep_stageSeperate mSleep_stageSeperate = null; // 睡眠分期指标计算类
		BaseReport mBaseReport = null;
		
		String filePath = file.getPath();
		long time_last = timeCountTotal(filePath, dataType);
		int line_count = lineCountTotal(filePath, dataType);
		if (time_last / 1000 > 300 && line_count > 300) { // 设定测量最短时间，5分钟
			if (time_last / 1000 > 600 && line_count > 600) { // 如果测量时间大于10分钟，则计算时舍去前5分钟的数据
				mSleep_stageSeperate = new Sleep_stageSeperate();
				mSleep_stageSeperate.calculate(filePath, true, false, DATA_SEPARATOR, dataType);
				mSleep_ApneaDetection = new Sleep_ApneaDetection();
				mSleep_ApneaDetection.calculate(filePath, true, false, DATA_SEPARATOR, dataType);
			} else {
				mSleep_stageSeperate = new Sleep_stageSeperate();
				mSleep_stageSeperate.calculate(filePath, false, false, DATA_SEPARATOR, dataType);
				mSleep_ApneaDetection = new Sleep_ApneaDetection();
				mSleep_ApneaDetection.calculate(filePath, false, false, DATA_SEPARATOR, dataType);
			}
			
			UserSleep userSleep = convertToUserSleep(mSleep_stageSeperate);
			UserIndex userIndex = convertToUserIndex(mSleep_ApneaDetection);
			try {
				mBaseReport = toBaseReport(userSleep, userIndex);
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			System.out.println("数据太短！");
		}
		
		return mBaseReport;
	}

	public BaseReport toBaseReport(UserSleep userSleep, UserIndex userIndex)
			throws IllegalAccessException, InvocationTargetException {
		SubReport srTosr = srTosr(userSleep, userIndex);
		SleepReport userSleepToSleepReport = userSleepToSleepReport(userSleep);
		BreatheReport userIndexToBreatheReport = userIndexToBreatheReport(userIndex);
		srTosr.setId(null);
		userSleepToSleepReport.setId(null);
		userIndexToBreatheReport.setId(null);
		BaseReport baseReport = new BaseReport(userSleepToSleepReport, userIndexToBreatheReport, srTosr);
		return baseReport;
	}

	/**
	 * 附表
	 */
	public SubReport srTosr(UserSleep userSleep, UserIndex userIndex) {
		SubReport sur = new SubReport();
		if (userIndex.getXyodi() != null && userIndex.getXywhzsIndex() != null) {
			// 氧减指数
			sur.setOxygenReductionIndex(Double.parseDouble(userIndex.getXyodi()));
			// 最长呼吸暂停时长
			sur.setLongestApneaSeconds(userIndex.getZchxzttimes());
			// 最长呼吸暂停发生时间
			if (userIndex.getHxhappertimes() != 0) {
				long st1 = (long) userIndex.getHxhappertimes() * 1000 + 1;
				Date h1 = new Date(st1);
				sur.setLongestApneaTime(h1);
			} else {
				sur.setLongestApneaTime(null);
			}
			// sur.setLongestApneaTime(new Date());
			// 最大氧降
			sur.setMaxOxygenReduceSeconds(userIndex.getMaxyjcxhour());
			// 最大氧降发生时间
			if (userIndex.getMaxyjfstimes() != 0) {
				long st2 = (long) userIndex.getMaxyjfstimes() * 1000;
				Date h2 = new Date(st2);
				sur.setMaxOxygenReduceTime(h2);
			} else {
				sur.setMaxOxygenReduceTime(null);
			}

			// sur.setMaxOxygenReduceTime(new Date());
			// 平均脉率
			sur.setAveragePulse(Double.parseDouble(userSleep.getAvgml()));
			// 最大脉率
			sur.setMaxPulse(Double.parseDouble(userSleep.getMaxml()));
			// 最大脉率发生时间
			if (userSleep.getMaxmltimes() != 0) {
				long st3 = (long) userSleep.getMaxmltimes() * 1000;
				Date h3 = new Date(st3);
				sur.setMaxPulseTime(h3);
			} else {
				sur.setMaxPulseTime(null);
			}

			// sur.setMaxPulseTime(new Date());
			// 最小脉率
			sur.setMinPulse(Double.parseDouble(userSleep.getMinml()));
			// 最小脉率发生时间
			if (userSleep.getMinmltimes() != 0) {
				long st4 = (long) userSleep.getMinmltimes() * 1000;
				Date h4 = new Date(st4);
				sur.setMinPulseTime(h4);
			} else {
				sur.setMinPulseTime(null);
			}
			// sur.setMinPulseTime(new Date());
			// 最长氧降时长
			sur.setLongestOxygenReduceSeconds(userIndex.getLongyjcxtimes());
			// 最长氧降发生时间
			if (userIndex.getLongyjfstimes() != 0) {
				long st5 = (long) userIndex.getLongyjfstimes() * 1000;
				Date h5 = new Date(st5);
				sur.setLongestOxygenReduceTime(h5);
			} else {
				sur.setLongestOxygenReduceTime(null);
			}
			// sur.setLongestOxygenReduceTime(new Date());
			// 氧减危害指数
			sur.setBloodOxygenHazardIndex(Double.parseDouble(userIndex.getXywhzsIndex()));
			// mac地址
			sur.setMacAddress(userIndex.getMacaddress());
			try {
				sur.setPerfusionIndex(Double.parseDouble(userIndex.getAvgxlgzd()));
			} catch (Exception e) {
				// TODO: handle exception
			}

		}

		return sur;
	}

	/**
	 * 睡眠
	 */
	public SleepReport userSleepToSleepReport(UserSleep userSleep) {
		SleepReport sr = new SleepReport();
		String uuid = null;
		// SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if (userSleep.getAvgml() != null) {
			sr.setUserId(uuid);
			// 开始睡眠时间
			if (userSleep.getStarttimes() != 0) {
				long st = (long) userSleep.getStarttimes() * 1000;
				Date d1 = new Date(st);
				sr.setStartTime(d1);
			} else {
				sr.setStartTime(null);
			}
			// sr.setStartTime(new Date());
			// 结束睡眠时间
			if (userSleep.getEndtimes() != 0) {
				long et = (long) userSleep.getEndtimes() * 1000;
				Date d2 = new Date(et);
				sr.setEndTime(d2);
			} else {
				sr.setEndTime(null);
			}
			// sr.setEndTime(new Date());
			// 睡眠总时间
			sr.setTotalSeconds(userSleep.getSmsctimes());
			// 清醒
			sr.setAwakeSeconds(userSleep.getQxsctimes());
			// 深睡
			sr.setDeepSleepSeconds(userSleep.getSssctimes());
			// 浅睡
			sr.setLightSleepSeconds(userSleep.getQssctimes());
			// 睡眠评分
			sr.setScore(userSleep.getScoreDate());
		}
		// else {
		// sr.setUserId(uuid);
		// //开始睡眠时间
		// sr.setStartTime(new Date());
		// //结束睡眠时间
		// sr.setEndTime(new Date());
		// //睡眠总时间
		// sr.setTotalSeconds(0);
		// //清醒
		// sr.setAwakeSeconds(0);
		// //深睡
		// sr.setDeepSleepSeconds(0);
		// //浅睡
		// sr.setLightSleepSeconds(0);
		// }
		return sr;
	}

	/**
	 * 指标
	 */
	public BreatheReport userIndexToBreatheReport(UserIndex userIndex) {
		BreatheReport br = new BreatheReport();
		// SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String uuid = null;
		if (userIndex.getAhiIndex() != null) {
			br.setUserId(uuid);
			// 呼吸暂停低通气指数
			br.setApneaHypopneaIndex(Double.parseDouble(userIndex.getAhiIndex()));
			// 呼吸暂停次数
			br.setApneaTimes(Integer.parseInt(userIndex.getHxztIndex()));
			// 低通气次数
			br.setHypopneaTimes(Integer.parseInt(userIndex.getDtqIndex()));
			// 最长低通气秒数
			br.setMaxHyponeaSeconds(userIndex.getZcdtqtimes());
			// 低通气总时间秒数
			br.setTotalHyponeaSeconds(userIndex.getDtqzsjtimes());
			// 低通气发生时间
			if (userIndex.getDtqhappertimes() != 0) {
				long l1 = (long) userIndex.getDtqhappertimes() * 1000;
				Date d3 = new Date(l1);
				br.setHyponeaHappenDate(d3);
			} else {
				br.setHyponeaHappenDate(null);
			}
			// br.setHyponeaHappenDate(new Date());
			// 氧减次数
			br.setReducedOxygenTimes(Integer.parseInt(userIndex.getYjzcsIndex()));
			// 平均血氧饱和度
			br.setAverageOxygenSaturation(Double.parseDouble(userIndex.getPjxybhdIndex()));
			// 最低血氧饱和度
			br.setMinOxygenSaturation(Double.parseDouble(userIndex.getZdxybhdIndex()));
			// odi
			br.setOdi(Double.parseDouble(userIndex.getXyodi()));
			// 醒时
			br.setAwakeSeconds(0);
			br.setOxygenSaturationLessthanNinetyPercent(Double.parseDouble(userIndex.getXybhdzbIndex()));
			// 总分
			br.setScore(userIndex.getScoreHxzb());
			// 血氧低于90%的百分比 ，
			br.setOxygenSaturationNinetyToHundredPercentHyponea(userIndex.getAxyfbzsjtimes());
			br.setOxygenSaturationNinetyToHundredPercentTimes(Integer.parseInt(userIndex.getAyjzcsnum()));
			// 血氧80%-89%的持续时间秒数
			br.setOxygenSaturationEightyToEightyNinePercentHyponea(userIndex.getBxyfbzsjtimes());
			br.setOxygenSaturationEightyToEightyNinePercentTimes(Integer.parseInt(userIndex.getByjzcsnum()));
			// 血氧70%-79%的持续时间秒数
			br.setOxygenSaturationSeventyToSeventyNinePercentHyponea(userIndex.getCxyfbzsjtimes());
			br.setOxygenSaturationSeventyToSeventyNinePercentTimes(Integer.parseInt(userIndex.getCyjzcsnum()));
			// 血氧60%-69%的持续时间秒数
			br.setOxygenSaturationSixtyToSixtyNinePercentHyponea(userIndex.getDxyfbzsjtimes());
			br.setOxygenSaturationSixtyToSixtyNinePercentTimes(Integer.parseInt(userIndex.getDyjzcsnum()));
			// 血氧50%-59%的持续时间秒数
			br.setOxygenSaturationFiftyToFiftyNinePercentHyponea(userIndex.getExyfbzsjtimes());
			br.setOxygenSaturationFiftyToFiftyNinePercentTimes(Integer.parseInt(userIndex.getEyjzcsnum()));
			// 血氧<50%的持续时间秒数
			br.setOxygenSaturationLessthanFiftyPercentHyponea(userIndex.getFxyfbzsjtimes());
			br.setOxygenSaturationLessthanFiftyPercentTimes(Integer.parseInt(userIndex.getFyjzcsnum()));

			// //睡眠报告的UUID客户端生成
			// String uuids=UUID.randomUUID().toString();
			// br.setId(uuids);
		}

		return br;
	}

	private UserSleep convertToUserSleep(Sleep_stageSeperate mSleep_stageSeperate) {
		UserSleep us = new UserSleep();
		/**
		 * 睡眠
		 */
		// 起始时间
		us.setStarthour(mSleep_stageSeperate.getTime_start()[0]);
		us.setStartminute(mSleep_stageSeperate.getTime_start()[1]);
		us.setStarttimes((int) (mSleep_stageSeperate.getTime_start2() / 1000));
		// Log.d("TAG", "-------------------" + (int)
		// (mSleep_stageSeperate.getTime_start2() / 1000));
		// 结束时间
		us.setEndhour(mSleep_stageSeperate.getTime_end()[0]);
		us.setEndminute(mSleep_stageSeperate.getTime_end()[1]);
		us.setEndtimes((int) (mSleep_stageSeperate.getTime_end2() / 1000));
		// Log.d("TAG", "-------------------" + (int)
		// (mSleep_stageSeperate.getTime_end2() / 1000));

		// 持续时间
		us.setSmschour(mSleep_stageSeperate.getTime_last()[0]);
		us.setSmscminute(mSleep_stageSeperate.getTime_last()[1]);
		us.setSmsctimes((int) (mSleep_stageSeperate.getTime_last2() / 1000));

		// 浅睡
		us.setQshour(mSleep_stageSeperate.getTime_light()[0]);
		us.setQsminute(mSleep_stageSeperate.getTime_light()[1]);
		us.setQssctimes((int) (mSleep_stageSeperate.getTime_light2() / 1000));

		// 深睡
		us.setSshour(mSleep_stageSeperate.getTime_deep()[0]);
		us.setSsminute(mSleep_stageSeperate.getTime_deep()[1]);
		us.setSssctimes((int) (mSleep_stageSeperate.getTime_deep2() / 1000));

		// 清醒
		us.setQxhour(mSleep_stageSeperate.getTime_wake()[0]);
		us.setQxminute(mSleep_stageSeperate.getTime_wake()[1]);
		us.setQxsctimes((int) (mSleep_stageSeperate.getTime_wake2() / 1000));

		/** 脉率 */
		// 最高脉率
		us.setMaxml(String.valueOf(mSleep_stageSeperate.getPr_max()));
		// 发生于 时
		us.setMaxmlhour(mSleep_stageSeperate.getPr_max_timePoint()[0]);
		// 发生于 分
		us.setMaxmlminute(mSleep_stageSeperate.getPr_max_timePoint()[1]);
		// 发生于 秒
		us.setMaxmlsec(mSleep_stageSeperate.getPr_max_timePoint()[2]);
		us.setMaxmltimes((int) (mSleep_stageSeperate.getPr_max_timePoint2() / 1000));
		// 最低脉率
		us.setMinml(String.valueOf(mSleep_stageSeperate.getPr_min()));
		// 发生于 时
		us.setMinmlhour(mSleep_stageSeperate.getPr_min_timePoint()[0]);
		// 发生于 分
		us.setMinmlminute(mSleep_stageSeperate.getPr_min_timePoint()[1]);
		// 发生于 秒
		us.setMinmlsec(mSleep_stageSeperate.getPr_min_timePoint()[2]);
		us.setMinmltimes((int) (mSleep_stageSeperate.getPr_min_timePoint2() / 1000));
		// 平均脉率
		us.setAvgml(String.valueOf(formFloat(mSleep_stageSeperate.getPr_average(), 2)));

		// 睡眠评分
		int qs = mSleep_stageSeperate.getTime_light()[0] * 60 + mSleep_stageSeperate.getTime_light()[1];
		int ss = mSleep_stageSeperate.getTime_deep()[0] * 60 + mSleep_stageSeperate.getTime_deep()[1];
		int qx = mSleep_stageSeperate.getTime_wake()[0] * 60 + mSleep_stageSeperate.getTime_wake()[1];
		int ss_rate = ss * 100 / (qs + ss + qx);
		if (ss_rate > 35) {
			us.setScoreDate(95 + (int) (Math.random() * 5));
		} else if (ss_rate > 25) {
			us.setScoreDate(90 + (int) (Math.random() * 5));
		} else if (ss_rate > 20) {
			us.setScoreDate(85 + (int) (Math.random() * 5));
		} else if (ss_rate > 15) {
			us.setScoreDate(80 + (int) (Math.random() * 5));
		} else if (ss_rate > 10) {
			us.setScoreDate(70 + (int) (Math.random() * 10));
		} else if (ss_rate > 5) {
			us.setScoreDate(60 + (int) (Math.random() * 10));
		} else {
			us.setScoreDate(50 + (int) (Math.random() * 10));
		}
		return us;
	}

	private UserIndex convertToUserIndex(Sleep_ApneaDetection mSleep_ApneaDetection) {
		UserIndex ui = new UserIndex();
		/**
		 * 指标
		 */
		// AHI
		ui.setAhiIndex(String.valueOf(formFloat(mSleep_ApneaDetection.getAHI(), 2)));
		// 呼吸暂停次数
		ui.setHxztIndex(String.valueOf(mSleep_ApneaDetection.getAI_count()));
		// 低通气次数
		ui.setDtqIndex(String.valueOf(mSleep_ApneaDetection.getHI_count()));
		// 氧减次数
		ui.setYjzcsIndex(String.valueOf(mSleep_ApneaDetection.getODI4_count()));
		// 平均血氧饱和度
		ui.setPjxybhdIndex(String.valueOf(formFloat(mSleep_ApneaDetection.getMSPO2(), 2)));
		// 最低血氧饱和度
		ui.setZdxybhdIndex(String.valueOf(mSleep_ApneaDetection.getLSPO2()));
		// 血氧低于90%占比
		ui.setXybhdzbIndex(String.valueOf(formFloat(mSleep_ApneaDetection.getTS90(), 2)));
		/** 氧降 */
		// 最大氧降 持续时
		ui.setMaxyjcxhour(mSleep_ApneaDetection.getODI4_largest_duration()[0]);
		// 最大氧降 持续分
		ui.setMaxyjcxminute(mSleep_ApneaDetection.getODI4_largest_duration()[1]);
		// 最大氧降 持续秒
		ui.setMaxyjcxsec(mSleep_ApneaDetection.getODI4_largest_duration()[2]);
		ui.setMaxyjcxhour((int) (mSleep_ApneaDetection.getODI4_largest_duration2() / 1000));
		// 最大氧降 发生时
		ui.setMaxyjfshour(mSleep_ApneaDetection.getODI4_largest_timePoint()[0]);
		// 最大氧降 发生分
		ui.setMaxyjfsminute(mSleep_ApneaDetection.getODI4_largest_timePoint()[1]);
		// 最大氧降 发生秒
		ui.setMaxyjfssec(mSleep_ApneaDetection.getODI4_largest_timePoint()[2]);
		ui.setMaxyjfstimes((int) (mSleep_ApneaDetection.getODI4_largest_timePoint2() / 1000));
		// 最长氧降 持续时
		ui.setLongyjcxhour(mSleep_ApneaDetection.getODI4_longest_duration()[0]);
		// 最长氧降 持续分
		ui.setLongyjcxminute(mSleep_ApneaDetection.getODI4_longest_duration()[1]);
		// 最长氧降 持续秒
		ui.setLongyjcxsec(mSleep_ApneaDetection.getODI4_longest_duration()[2]);
		ui.setLongyjcxtimes((int) (mSleep_ApneaDetection.getODI4_longest_duration2() / 1000));
		// 最长氧降 发生时
		ui.setLongyjfshour(mSleep_ApneaDetection.getODI4_longest_timePoint()[0]);
		// 最长氧降 发生分
		ui.setLongyjfsminute(mSleep_ApneaDetection.getODI4_longest_timePoint()[1]);
		// 最长氧降 发生秒
		ui.setLongyjfssec(mSleep_ApneaDetection.getODI4_longest_timePoint()[2]);
		ui.setLongyjfstimes((int) (mSleep_ApneaDetection.getODI4_longest_timePoint2() / 1000));
		// 氧减危害指数
		ui.setXywhzsIndex(String.valueOf(formFloat(mSleep_ApneaDetection.getODHI(), 2)));
		// 平均血流灌注度
		ui.setAvgxlgzd(String.valueOf(formFloat(mSleep_ApneaDetection.getPI(), 2)));
		// 评分
		int score;
		if (mSleep_ApneaDetection.getAHI() % 1 < 0.5) {
			score = 100 - (int) mSleep_ApneaDetection.getAHI();
		} else {
			score = 99 - (int) mSleep_ApneaDetection.getAHI();
		}
		if (score < 0)
			score = 0;
		ui.setScoreHxzb(score);
		// 最长呼吸暂停时长
		ui.setZchxzthour(mSleep_ApneaDetection.getAI_longest_duration()[0]);
		ui.setZchxztminute(mSleep_ApneaDetection.getAI_longest_duration()[1]);
		ui.setZchxztsec(mSleep_ApneaDetection.getAI_longest_duration()[2]);
		ui.setZchxzttimes((int) (mSleep_ApneaDetection.getAI_longest_duration2() / 1000));
		// 发生于
		ui.setHxhapperhour(mSleep_ApneaDetection.getAI_longest_timePoint()[0]);
		ui.setHxhapperminute(mSleep_ApneaDetection.getAI_longest_timePoint()[1]);
		ui.setHxhappersec(mSleep_ApneaDetection.getAI_longest_timePoint()[2]);
		ui.setHxhappertimes((int) (mSleep_ApneaDetection.getAI_longest_timePoint2() / 1000));
		// 总时间
		ui.setHxztzsjhour(mSleep_ApneaDetection.getAI_time_sum()[0]);
		ui.setHxztzsjminute(mSleep_ApneaDetection.getAI_time_sum()[1]);
		ui.setHxztzsjsec(mSleep_ApneaDetection.getAI_time_sum()[2]);
		ui.setHxztzsjtimes((int) (mSleep_ApneaDetection.getAI_time_sum2() / 1000));
		// 最长低通气时长
		ui.setZcdtqhour(mSleep_ApneaDetection.getHI_longest_duration()[0]);
		ui.setZcdtqminute(mSleep_ApneaDetection.getHI_longest_duration()[1]);
		ui.setZcdtqsec(mSleep_ApneaDetection.getHI_longest_duration()[2]);
		ui.setZcdtqtimes((int) (mSleep_ApneaDetection.getHI_longest_duration2() / 1000));
		// 发生于
		ui.setDtqhapperhour(mSleep_ApneaDetection.getHI_longest_timePoint()[0]);
		ui.setDtqhapperminute(mSleep_ApneaDetection.getHI_longest_timePoint()[1]);
		ui.setDtqhappersec(mSleep_ApneaDetection.getHI_longest_timePoint()[2]);
		ui.setDtqhappertimes((int) (mSleep_ApneaDetection.getHI_longest_timePoint2() / 1000));
		// 总时间
		ui.setDtqzsjhour(mSleep_ApneaDetection.getHI_time_sum()[0]);
		ui.setDtqzsjminute(mSleep_ApneaDetection.getHI_time_sum()[1]);
		ui.setDtqzsjsec(mSleep_ApneaDetection.getHI_time_sum()[2]);
		ui.setDtqzsjtimes((int) (mSleep_ApneaDetection.getHI_time_sum2() / 1000));
		// ODI
		ui.setXyodi(String.valueOf(formFloat(mSleep_ApneaDetection.getODI4_index(), 2)));
		// 醒时
		// 最高
		ui.setSmmax(String.valueOf(mSleep_ApneaDetection.getHSPO2()));

		// 90-100总时间
		ui.setAxyfbzsjhour(mSleep_ApneaDetection.getDistribution_90_100()[0]);
		ui.setAxyfbzsjminute(mSleep_ApneaDetection.getDistribution_90_100()[1]);
		ui.setAxyfbzsjsec(mSleep_ApneaDetection.getDistribution_90_100()[2]);
		ui.setAxyfbzsjtimes((int) mSleep_ApneaDetection.getDistribution_90_1002()[0] / 1000);
		// 90-100氧减总次数
		ui.setAyjzcsnum(String.valueOf(mSleep_ApneaDetection.getDistribution_90_100()[3]));
		// 80-90
		ui.setBxyfbzsjhour(mSleep_ApneaDetection.getDistribution_80_90()[0]);
		ui.setBxyfbzsjminute(mSleep_ApneaDetection.getDistribution_80_90()[1]);
		ui.setBxyfbzsjsec(mSleep_ApneaDetection.getDistribution_80_90()[2]);
		ui.setBxyfbzsjtimes((int) mSleep_ApneaDetection.getDistribution_80_902()[0] / 1000);
		ui.setByjzcsnum(String.valueOf(mSleep_ApneaDetection.getDistribution_80_90()[3]));
		// 70-80
		ui.setCxyfbzsjhour(mSleep_ApneaDetection.getDistribution_70_80()[0]);
		ui.setCxyfbzsjminute(mSleep_ApneaDetection.getDistribution_70_80()[1]);
		ui.setCxyfbzsjsec(mSleep_ApneaDetection.getDistribution_70_80()[2]);
		ui.setCxyfbzsjtimes((int) mSleep_ApneaDetection.getDistribution_70_802()[0] / 1000);
		ui.setCyjzcsnum(String.valueOf(mSleep_ApneaDetection.getDistribution_70_80()[3]));
		// 60-70
		ui.setDxyfbzsjhour(mSleep_ApneaDetection.getDistribution_60_70()[0]);
		ui.setDxyfbzsjminute(mSleep_ApneaDetection.getDistribution_60_70()[1]);
		ui.setDxyfbzsjsec(mSleep_ApneaDetection.getDistribution_60_70()[2]);
		ui.setDxyfbzsjtimes((int) mSleep_ApneaDetection.getDistribution_60_702()[0] / 1000);
		ui.setDyjzcsnum(String.valueOf(mSleep_ApneaDetection.getDistribution_60_70()[3]));
		// 50-60
		ui.setExyfbzsjhour(mSleep_ApneaDetection.getDistribution_50_60()[0]);
		ui.setExyfbzsjminute(mSleep_ApneaDetection.getDistribution_50_60()[1]);
		ui.setExyfbzsjsec(mSleep_ApneaDetection.getDistribution_50_60()[2]);
		ui.setExyfbzsjtimes((int) mSleep_ApneaDetection.getDistribution_50_602()[0] / 1000);
		ui.setEyjzcsnum(String.valueOf(mSleep_ApneaDetection.getDistribution_50_60()[3]));
		// 0-50
		ui.setFxyfbzsjhour(mSleep_ApneaDetection.getDistribution_0_50()[0]);
		ui.setFxyfbzsjminute(mSleep_ApneaDetection.getDistribution_0_50()[1]);
		ui.setFxyfbzsjsec(mSleep_ApneaDetection.getDistribution_0_50()[2]);
		ui.setFxyfbzsjtimes((int) mSleep_ApneaDetection.getDistribution_0_502()[0] / 1000);
		ui.setFyjzcsnum(String.valueOf(mSleep_ApneaDetection.getDistribution_0_50()[3]));

		float AHI = mSleep_ApneaDetection.getAHI();
		int lspo2 = mSleep_ApneaDetection.getLSPO2();
		// 判断是否患OSAHS
		if (AHI > 5) {
			ui.setUdegree("OSAHS");
		} else {
			ui.setUdegree(null);
		}
		// OSAHS
		if ("OSAHS".equals(ui.getUdegree())) {
			if (AHI <= 5 && lspo2 >= 90)
				ui.setUdegree("无");
			if (AHI > 30) {
				ui.setUdegree("重度OSAHS");
			} else if (AHI > 15) {
				ui.setUdegree("中度OSAHS");
			} else if (AHI > 5) {
				ui.setUdegree("轻度OSAHS");
			}
		}
		if (ui.getUdegree() != null) {
			if (lspo2 < 80) {
				ui.setUdegree(ui.getUdegree() + "且重度低氧血症");
			} else if (lspo2 < 85) {
				ui.setUdegree(ui.getUdegree() + "且中度低氧血症");
			} else if (lspo2 < 90) {
				ui.setUdegree(ui.getUdegree() + "且轻度低氧血症");
			}
		} else {
			if (lspo2 < 80) {
				ui.setUdegree("重度低氧血症");
			} else if (lspo2 < 85) {
				ui.setUdegree("中度低氧血症");
			} else if (lspo2 < 90) {
				ui.setUdegree("轻度低氧血症");
			}
		}
		// OSAHS程度
		if (AHI > 30) {
			ui.setOsahsdegree("重度");
		} else if (AHI > 15) {
			ui.setOsahsdegree("中度");
		} else if (AHI > 5) {
			ui.setOsahsdegree("轻度");
		} else {
			ui.setOsahsdegree("无");
		}
		// 低血氧症程度
		if (lspo2 < 80) {
			ui.setDyxzdegree("重度");
		} else if (lspo2 < 85) {
			ui.setDyxzdegree("中度");
		} else if (lspo2 < 90) {
			ui.setDyxzdegree("轻度");
		} else {
			ui.setDyxzdegree("无");
		}
		return ui;
	}

	/**
	 * 计算文件内所有数据的行数，并返回该数值
	 *
	 * @param filepath
	 *            文件的路径
	 * @return 所有数据的行数
	 */
	public int lineCountTotal(String filepath, int dataType) {
		int count = 0; // 用于统计行数，从0开始

		if (dataType == DATATYPE_ASCII) {
			String strbuff = null;
			try {
				BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filepath)));
				br.readLine(); // 去掉第一行
				while ((strbuff = br.readLine()) != null) { // readLine()方法是按行读的，返回值是这行的内容
					if (!DATA_SEPARATOR.equals(strbuff))
						count++;
				}
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (dataType == DATATYPE_BINARY) {
			// binary
			final int data_length = 21; // 1+1+1+1+4+1+1+1+1+8+1 = 21
			RandomAccessFile raf = null;
			try {
				raf = new RandomAccessFile(filepath, "r");
				count = (int) (raf.length() / data_length);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return count;
	}

	/**
	 * 计算文件内所有数据的测量总时间，并返回该数值
	 *
	 * @param filepath
	 *            文件的路径
	 * @return 测量总时间
	 */
	public long timeCountTotal(String filepath, int dataType) {
		long timeCount = 0; // 测量总时间

		if (dataType == DATATYPE_ASCII) {
			String strbuff_new = null; // 最新读取到的数据
			String strbuff_start = null; // 开始时间
			String strbuff_end = null; // 结束时间
			try {
				BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filepath)));
				strbuff_new = br.readLine(); // 去掉第一行
				while ((strbuff_new = br.readLine()) != null) { // readLine()方法是按行读的，返回值是这行的内容
					if (!DATA_SEPARATOR.equals(strbuff_new)) {
						if (strbuff_start == null) {
							strbuff_start = strbuff_new;
						} else {
							strbuff_end = strbuff_new;
						}
					} else {
						System.out.println("*********************strbuff_new == DATA_SEPARATOR*************");
					}
				}
				if (strbuff_start != null && strbuff_end != null)
					// try {
					// timeCount = in.parse(strbuff_end.split(",")[3]).getTime()
					// - in.parse(strbuff_start.split(",")[3]).getTime();
					// } catch (ParseException e) {
					// // TODO Auto-generated catch block
					// e.printStackTrace();
					// }
					timeCount = Long.valueOf(strbuff_end.split(",")[9]) - Long.valueOf(strbuff_start.split(",")[9]);
				br.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (dataType == DATATYPE_BINARY) {
			// binary
			final int data_length = 21; // 1+1+1+1+4+1+1+1+1+8+1 = 21
			RandomAccessFile raf = null;
			int len = 0;
			try {
				raf = new RandomAccessFile(filepath, "r");
				len = (int) (raf.length() / data_length);

				long start = 0, end = 0;
				raf.seek(0 * data_length);
				raf.readByte();
				raf.readByte();
				raf.readByte();
				raf.readByte();
				raf.readInt();
				raf.readByte();
				raf.readByte();
				raf.readByte();
				raf.readByte();
				start = raf.readLong();
				raf.readByte();

				raf.seek((len - 1) * data_length);
				raf.readByte();
				raf.readByte();
				raf.readByte();
				raf.readByte();
				raf.readInt();
				raf.readByte();
				raf.readByte();
				raf.readByte();
				raf.readByte();
				end = raf.readLong();
				raf.readByte();

				timeCount = end - start;
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return timeCount;
	}

	/**
	 * 将float型的数值保留若干位小数
	 *
	 * @param param
	 *            需要处理的数值
	 * @param power
	 *            保留的小数位数
	 * @return 保留小数后的float型数值
	 */
	public static float formFloat(float param, int power) {
		return (int) (param * 100) / 100f;
	}

	/*
	 * @see taiyi.web.AndroidToWebAdapter.DataOfAndroidToWebAdapter#
	 * binaryFileToBaseReport(java.io.File)
	 */
	@Override
	public BaseReport binaryFileToBaseReport(File file) {
		this.dataType = DATATYPE_BINARY;
		return originalFileToBaseReport(file);
	}
}
