package taiyi.web.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;

import taiyi.web.constant.Internationalization;
import taiyi.web.model.BreatheReport;
import taiyi.web.model.DiseaseHistory;
import taiyi.web.model.SleepReport;
import taiyi.web.model.SubReport;
import taiyi.web.model.User;

/**
 * 生成pdf的工具类
 * 
 * @author <a href="mailto:jason19659@163.com">jason19659</a>
 *
 *         taiyi.web.utils
 *
 *         2016年9月22日
 */
public class PDFUtils {

	public final String CHINESE_FONT;
	public final BaseFont BASE_FONT;
	public final Font fontBigTitle;
	public final Font fontSmallTitle;
	public final Font fontText;
	public final Font fontTextUnderLine;
	public final Font fontTextBold;
	public final java.text.DecimalFormat decimalFormat = new java.text.DecimalFormat("0.00");
	public final java.text.DecimalFormat timeFormat = new java.text.DecimalFormat("00");
	public final SimpleDateFormat yyyyMMDDFormat = new SimpleDateFormat("yyyy-MM-dd");
	public final SimpleDateFormat yyyyMMDDHHmmssFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public final SimpleDateFormat HHmmssFormat = new SimpleDateFormat("HH:mm:ss");
	public Image logo;
	public Logger logger = Logger.getLogger(PDFUtils.class);
	private Locale local = Locale.CHINA;
	private ResourceBundle resourceBundle = ResourceBundle.getBundle("messages", local);

	public PDFUtils(String font) throws DocumentException, IOException {
		CHINESE_FONT = font;
		BASE_FONT = BaseFont.createFont(CHINESE_FONT, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
		fontBigTitle = new Font(BASE_FONT, 22, Font.BOLD);
		fontSmallTitle = new Font(BASE_FONT, 10, Font.BOLD);
		fontText = new Font(BASE_FONT, 8, Font.NORMAL);
		fontTextUnderLine = new Font(BASE_FONT, 8, Font.UNDERLINE | Font.NORMAL);
		fontTextBold = new Font(BASE_FONT, 8, Font.BOLD);
	}

	public PDFUtils(String font, String logo) throws DocumentException, IOException {
		this(font);
		this.logo = Image.getInstance(logo);
	}

	public void createPdf(String dest, String imagePathROSeconds, String imagePathROTimes, String imagePathROml,
			String imagePathROxy, User user, SleepReport sleepReport, BreatheReport breatheReport, SubReport subReport)
			throws Exception {
		createPdf(dest, imagePathROSeconds, imagePathROTimes, imagePathROml, imagePathROxy, user, sleepReport,
				breatheReport, subReport, "");
	}

	/**
	 * @param dest
	 * @param imagePathROSeconds
	 * @param imagePathROTimes
	 * @param imagePathROml
	 * @param imagePathROxy
	 * @param user
	 * @param sleepReport
	 * @param breatheReport
	 * @param pulse
	 * @param result
	 * @param advice
	 * @throws Exception
	 */
	public void createPdf(String dest, String imagePathROSeconds, String imagePathROTimes, String imagePathROml,
			String imagePathROxy, User user, SleepReport sleepReport, BreatheReport breatheReport, SubReport subReport,
			String header) throws Exception {
		try {
			Document document = new Document(PageSize.A4);
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(dest));
			document.open();
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < resourceBundle.getString(Internationalization.SLEEP_APNEA_SYNDROME).length(); i++) {
				sb.append(" ");
			}
			String result = "1. " + resourceBundle.getString(Internationalization.SLEEP_APNEA_SYNDROME) + "："
					+ getOSAHSResult(breatheReport, user) + "\n" + sb.toString() + "2. "
					+ resourceBundle.getString(Internationalization.SLEEP_HYPOXEMIA) + "："
					+ getSleepHypoxiaResult(breatheReport);

			Image image = Image.getInstance(imagePathROSeconds);
			Image image2 = Image.getInstance(imagePathROTimes);
			Image image3 = Image.getInstance(imagePathROml);
			Image image4 = Image.getInstance(imagePathROxy);

			image.setAbsolutePosition(15, 465);
			image.scaleToFit(270, 150);
			image2.setAbsolutePosition(300, 465);
			image2.scaleToFit(270, 150);
			image3.setAbsolutePosition(15, 160);
			image3.scaleToFit(555, 150);
			image4.setAbsolutePosition(15, 335);
			image4.scaleToFit(555, 150);
			try {
				if (logo != null) {
					logo.scaleToFit(62, 16);
					logo.setAbsolutePosition(515, 810);
					document.add(logo);
					logger.info("add logo to pdf");
				}
			} catch (Exception e) {
				logger.info("logo not exist");
			}

			document.add(image);
			document.add(image2);
			document.add(image3);
			document.add(image4);

			// drawAndSetRetangle(430, 585, writer);
			Paragraph t = new Paragraph(resourceBundle.getString(Internationalization.SLEEP_REPORT), fontBigTitle);
			t.setAlignment(Element.ALIGN_CENTER);
			document.add(t);
			drawDIYHeader(writer, header);
			if (!"noname".equals(user.getName())) {
				drawPersonalInfomationPart(writer, user, sleepReport);
			} else {
				drawNonamePart(writer, user, sleepReport);
			}
			// drawSleepReportPart(writer, sleepReport);
			drawBreatheReportPart(writer, breatheReport, subReport);
			drawSpO2AnalysisPart(writer, breatheReport, subReport);
			darwPulseRateAnalysisPart(writer, subReport);
			drawSleepAnalysisPart(writer, sleepReport);
			//
			String advice = subReport.getAdvice() == null
					? resourceBundle.getString(Internationalization.RESULT_REFERENCE_CLINICAL) + " "
					: subReport.getAdvice();
			drawAnalysisResultPart(writer, result, advice);

			// Rectangle rectangle = drawAndSetRetangle(750, 600, writer);

			document.close();
		} catch (Exception e) {
			File file = new File(dest);
			if (file.exists()) {
				file.delete();
			}
			throw new Exception(e);
		}
	}

	public void drawDIYHeader(PdfWriter writer, String header) throws DocumentException {
		if (!StringUtils.isEmpty(header)) {
			Rectangle rectangle = new Rectangle(25, 810, 575, 790);
			ColumnText ct = new ColumnText(writer.getDirectContent());
			ct.setSimpleColumn(rectangle);
			Paragraph paragraph = new Paragraph();
			paragraph.setAlignment(Element.ALIGN_CENTER);
			paragraph.setLeading(12f);
			paragraph.setFont(fontBigTitle);
			paragraph.add(header);
			ct.addElement(paragraph);
			ct.go();
		}

	}

	public void drawAnalysisResultPart(PdfWriter writer, String result, String advice) throws DocumentException {
		Rectangle rectangle = drawAndSetRetangle(110, 20, writer);
		ColumnText ct = new ColumnText(writer.getDirectContent());
		ct.setSimpleColumn(rectangle);
		Paragraph paragraph = new Paragraph();
		paragraph.setLeading(12f);
		paragraph.setFont(fontSmallTitle);
		paragraph.add(resourceBundle.getString(Internationalization.RESULTS_OF_THE_ANALYSIS) + " \n");
		addBoldText(paragraph, resourceBundle.getString(Internationalization.CONCLUSION) + " : ");
		addNormalText(paragraph, result);
		addNormalText(paragraph, "\n");
		addBoldText(paragraph, resourceBundle.getString(Internationalization.SUGGESTION) + " : ");
		addNormalText(paragraph, advice);
		addDefaultSpace(paragraph);
		ct.addElement(paragraph);
		ct.go();
	}

	public void drawAnalysisResultPart(PdfWriter writer) {

	}

	public void drawSleepAnalysisPart(PdfWriter writer, SleepReport sleepReport) throws DocumentException {
		Rectangle rectangle = drawAndSetRetangle(155, 115, writer);
		ColumnText ct = new ColumnText(writer.getDirectContent());
		ct.setSimpleColumn(rectangle);
		Paragraph paragraph = new Paragraph();
		paragraph.setLeading(12f);
		paragraph.setFont(fontSmallTitle);
		paragraph.add(resourceBundle.getString(Internationalization.SLEEP_ANALYSIS) + " \n");
		addBoldText(paragraph, resourceBundle.getString(Internationalization.SLEEP_TIME) + " : ");
		addNormalText(paragraph, secondsToHHmmss(sleepReport.getTotalSeconds()));
		addDefaultSpace(paragraph);
		addBoldText(paragraph, resourceBundle.getString(Internationalization.DEEP_SLEEP_DURATION) + " : ");
		addNormalText(paragraph, secondsToHHmmss(sleepReport.getDeepSleepSeconds()));
		addDefaultSpace(paragraph);
		addBoldText(paragraph, resourceBundle.getString(Internationalization.THE_PROPORTION_OF_DEEP_SLEEP) + " : ");
		addNormalText(paragraph,
				decimalFormat.format((double) sleepReport.getDeepSleepSeconds() * 100 / sleepReport.getTotalSeconds())
						+ "%");
		addDefaultSpace(paragraph);
		addBoldText(paragraph, resourceBundle.getString(Internationalization.LIGHT_SLEEP_DURATION) + " : ");
		addNormalText(paragraph, secondsToHHmmss(sleepReport.getLightSleepSeconds()));
		addDefaultSpace(paragraph);
		addBoldText(paragraph, resourceBundle.getString(Internationalization.THE_PROPORTION_OF_LIGHT_SLEEP) + " : ");
		addNormalText(paragraph,
				decimalFormat.format((double) sleepReport.getLightSleepSeconds() * 100 / sleepReport.getTotalSeconds())
						+ "%");
		addNormalText(paragraph, "\n");
		addBoldText(paragraph, resourceBundle.getString(Internationalization.SCORE_OF_SLEEP_QUALITY) + " : ");
		addNormalText(paragraph, "" + sleepReport.getScore());
		addDefaultSpace(paragraph);
		ct.addElement(paragraph);
		ct.go();
	}

	public void darwPulseRateAnalysisPart(PdfWriter writer, SubReport pulse) throws DocumentException {
		Rectangle rectangle = drawAndSetRetangle(330, 160, writer);
		ColumnText ct = new ColumnText(writer.getDirectContent());
		ct.setSimpleColumn(rectangle);
		Paragraph paragraph = new Paragraph();
		paragraph.setLeading(12f);
		paragraph.setFont(fontSmallTitle);
		paragraph.add(resourceBundle.getString(Internationalization.PR_ANALYSIS) + " \n");
		addBoldText(paragraph, resourceBundle.getString(Internationalization.MEAN_PR) + " : ");
		addNormalText(paragraph, decimalFormat.format(pulse.getAveragePulse()) + " "
				+ resourceBundle.getString(Internationalization.FREQUENCY_PER_MIN));
		addDefaultSpace(paragraph);
		addBoldText(paragraph, resourceBundle.getString(Internationalization.MIN_PR) + " : ");
		addNormalText(paragraph, decimalFormat.format(pulse.getMinPulse()));
		if (pulse.getMinPulseTime() != null) {
			addNormalText(paragraph,
					" " + resourceBundle.getString(Internationalization.FREQUENCY_PER_MIN) + ", "
							+ resourceBundle.getString(Internationalization.OCCUR) + " "
							+ HHmmssFormat.format(pulse.getMinPulseTime()));
		}

		addDefaultSpace(paragraph);
		addBoldText(paragraph, resourceBundle.getString(Internationalization.MAX_PR) + " : ");
		addNormalText(paragraph, decimalFormat.format(pulse.getMaxPulse()));
		if (pulse.getMaxPulseTime() != null) {
			addNormalText(paragraph,
					" " + resourceBundle.getString(Internationalization.FREQUENCY_PER_MIN) + ", "
							+ resourceBundle.getString(Internationalization.OCCUR) + " "
							+ HHmmssFormat.format(pulse.getMaxPulseTime()));
		}
		addDefaultSpace(paragraph);
		// 脉率趋势图
		ct.addElement(paragraph);
		ct.go();
	}

	public void drawSpO2AnalysisPart(PdfWriter writer, BreatheReport breatheReport, SubReport subReport)
			throws DocumentException {
		// 595,842
		Rectangle rectangle = drawAndSetRetangle(645, 335, writer);
		ColumnText ct = new ColumnText(writer.getDirectContent());
		ct.setSimpleColumn(rectangle);
		Paragraph paragraph = new Paragraph();
		paragraph.setLeading(12f);
		paragraph.setFont(fontSmallTitle);
		paragraph.add(resourceBundle.getString(Internationalization.SPO2_ANALYSIS) + " \n");
		addBoldText(paragraph, resourceBundle.getString(Internationalization.MEAN_SPO2) + ": ");
		addNormalText(paragraph, decimalFormat.format(breatheReport.getAverageOxygenSaturation()) + "%");
		addDefaultSpace(paragraph);
		addBoldText(paragraph, resourceBundle.getString(Internationalization.MIN_SPO2) + " : ");
		addNormalText(paragraph, decimalFormat.format(breatheReport.getMinOxygenSaturation()) + "%");
		addDefaultSpace(paragraph);
		addBoldText(paragraph, resourceBundle.getString(Internationalization.T90_SPO2_90) + " : ");
		addNormalText(paragraph, decimalFormat.format(breatheReport.getOxygenSaturationLessthanNinetyPercent()) + "%");
		addDefaultSpace(paragraph);
		addBoldText(paragraph, resourceBundle.getString(Internationalization.OXYGEN_DESATURATION) + " : ");
		addNormalText(paragraph, decimalFormat.format(subReport.getOxygenReductionIndex()));
		addDefaultSpace(paragraph);
		addBoldText(paragraph, resourceBundle.getString(Internationalization.MAX_OXYGEN_DESATURATION) + " : ");
		addNormalText(paragraph, secondsToHHmmss(subReport.getMaxOxygenReduceSeconds()));
		if (subReport.getMaxOxygenReduceTime() != null) {
			addNormalText(paragraph, ", " + resourceBundle.getString(Internationalization.OCCUR) + " "
					+ HHmmssFormat.format(subReport.getMaxOxygenReduceTime()));
		}
		addNormalText(paragraph, "   ");
		addBoldText(paragraph, resourceBundle.getString(Internationalization.LONGEST_OXYGEN_DESATURATION) + " : ");
		addNormalText(paragraph, secondsToHHmmss(subReport.getLongestOxygenReduceSeconds()));
		if (subReport.getLongestOxygenReduceTime() != null) {
			addNormalText(paragraph, ", " + resourceBundle.getString(Internationalization.OCCUR) + " "
					+ HHmmssFormat.format(subReport.getLongestOxygenReduceTime()));
		}
		addDefaultSpace(paragraph);
		addBoldText(paragraph, resourceBundle.getString(Internationalization.ODRI) + " : ");
		addNormalText(paragraph, decimalFormat.format(subReport.getBloodOxygenHazardIndex()));
		addDefaultSpace(paragraph);
		/*
		 * 血氧分布图、氧减分布图 血氧趋势图
		 */

		ct.addElement(paragraph);
		ct.go();
	}

	public void drawBreatheReportPart(PdfWriter writer, BreatheReport breatheReport, SubReport subReport)
			throws DocumentException {
		// 595,842
		Rectangle rectangle = drawAndSetRetangle(695, 650, writer);
		ColumnText ct = new ColumnText(writer.getDirectContent());
		ct.setSimpleColumn(rectangle);
		Paragraph paragraph = new Paragraph();
		paragraph.setLeading(12f);
		paragraph.setFont(fontSmallTitle);
		paragraph.add(resourceBundle.getString(Internationalization.BREATHING_STATISTICS) + " \n");
		addBoldText(paragraph, resourceBundle.getString(Internationalization.RESPIRATORY_DISTURBANCE_INDEX) + " : ");
		try {
			addNormalText(paragraph, decimalFormat.format(breatheReport.getApneaHypopneaIndex()));
		} catch (Exception e) {
			addNormalText(paragraph, "0");
		}
		addDefaultSpace(paragraph);
		addBoldText(paragraph, resourceBundle.getString(Internationalization.APNEAS) + " : ");
		addNormalText(paragraph,
				breatheReport.getApneaTimes() + resourceBundle.getString(Internationalization.FREQUENCY_NO));
		addDefaultSpace(paragraph);
		addBoldText(paragraph, resourceBundle.getString(Internationalization.LONGEST_APNEA) + " : ");
		addNormalText(paragraph, secondsToHHmmss(subReport.getLongestApneaSeconds()));
		if (subReport.getLongestApneaTime() != null) {
			addNormalText(paragraph, ", " + resourceBundle.getString(Internationalization.OCCUR) + " "
					+ HHmmssFormat.format(subReport.getLongestApneaTime()));
		}
		addDefaultSpace(paragraph);
		addBoldText(paragraph, resourceBundle.getString(Internationalization.TOTAL_DURATION_OF_APNEA_EVENTS) + " : ");
		addNormalText(paragraph, secondsToHHmmss(subReport.getTotalApneaTimeSeconds()));
		addNormalText(paragraph, "\n");
		addBoldText(paragraph, resourceBundle.getString(Internationalization.HYPOPNEAS) + " : ");
		addNormalText(paragraph,
				breatheReport.getHypopneaTimes() + resourceBundle.getString(Internationalization.FREQUENCY_NO));
		addDefaultSpace(paragraph);
		addBoldText(paragraph, resourceBundle.getString(Internationalization.LONGEST_HYPOPNEAS) + " : ");
		addNormalText(paragraph, secondsToHHmmss(breatheReport.getMaxHyponeaSeconds()));
		if (breatheReport.getHyponeaHappenDate() != null) {
			addNormalText(paragraph, ", " + resourceBundle.getString(Internationalization.OCCUR) + " "
					+ HHmmssFormat.format(breatheReport.getHyponeaHappenDate()));
		}
		addDefaultSpace(paragraph);
		// addBoldText(paragraph, "氧减次数 : ");
		// addNormalText(paragraph, breatheReport.getReducedOxygenTimes() +
		// "次");
		// addDefaultSpace(paragraph);
		// addBoldText(paragraph, "odi : ");
		// addNormalText(paragraph, "" + breatheReport.getOdi());
		// addNormalText(paragraph, "\n");
		// addBoldText(paragraph, "醒时 : ");-
		// addNormalText(paragraph, "" + breatheReport.getAwakeSeconds());
		// addDefaultSpace(paragraph);
		// addBoldText(paragraph, "血氧饱和度低于90%占比 : ");
		// addNormalText(paragraph,
		// breatheReport.getOxygenSaturationLessthanNinetyPercent() + "%");
		// addDefaultSpace(paragraph);
		addBoldText(paragraph,
				resourceBundle.getString(Internationalization.TOTAL_DURATION_OF_HYPOPNEA_EVENTS) + " : ");
		addNormalText(paragraph, secondsToHHmmss(subReport.getTotalHypoventilationTimeSeconds()));
		addDefaultSpace(paragraph);

		addBoldText(paragraph, resourceBundle.getString(Internationalization.SCORE_OF_RESPIRATORY_QUALITY) + " : ");
		addNormalText(paragraph, "" + breatheReport.getScore());
		addDefaultSpace(paragraph);
		// addBoldText(paragraph, "上传日期 : ");
		// try {
		// addNormalText(paragraph,
		// yyyyMMDDFormat.format(breatheReport.getUploadDate()));
		// } catch (Exception e) {
		// addNormalText(paragraph, "无数据");
		// }
		// addDefaultSpace(paragraph);
		ct.addElement(paragraph);
		ct.go();
	}

	public void drawSleepReportPart(PdfWriter writer, SleepReport sleepReport) throws DocumentException {
		Rectangle rectangle = drawAndSetRetangle(705, 650, writer);
		ColumnText ct = new ColumnText(writer.getDirectContent());
		ct.setSimpleColumn(rectangle);
		Paragraph paragraph = new Paragraph();
		paragraph.setLeading(12f);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		paragraph.setFont(fontSmallTitle);
		paragraph.add(resourceBundle.getString(Internationalization.SLEEP_REPORT) + " \n");
		addBoldText(paragraph, resourceBundle.getString(Internationalization.STARTING_TIME) + " : ");
		try {
			addNormalText(paragraph, simpleDateFormat.format(sleepReport.getStartTime()));
		} catch (Exception e) {
			addNormalText(paragraph, resourceBundle.getString(Internationalization.NO_DATA));
		}
		addDefaultSpace(paragraph);
		addBoldText(paragraph, resourceBundle.getString(Internationalization.END_TIME) + " : ");
		try {
			addNormalText(paragraph, simpleDateFormat.format(sleepReport.getEndTime()));
		} catch (Exception e) {
			addNormalText(paragraph, resourceBundle.getString(Internationalization.NO_DATA) + "");
		}
		addNormalText(paragraph, "\n");
		addBoldText(paragraph, resourceBundle.getString(Internationalization.LIGHT_SLEEP_DURATION) + ": ");
		try {
			addNormalText(paragraph, secondsToHHmmss(sleepReport.getLightSleepSeconds()));
		} catch (Exception e) {
			addNormalText(paragraph, resourceBundle.getString(Internationalization.NO_DATA));
		}
		addDefaultSpace(paragraph);
		addBoldText(paragraph, resourceBundle.getString(Internationalization.DEEP_SLEEP_DURATION) + " : ");
		try {
			addNormalText(paragraph, secondsToHHmmss(sleepReport.getDeepSleepSeconds()));
		} catch (Exception e) {
			addNormalText(paragraph, resourceBundle.getString(Internationalization.NO_DATA));
		}
		addDefaultSpace(paragraph);
		addBoldText(paragraph, resourceBundle.getString(Internationalization.WAKE_DURATION) + " : ");
		try {
			addNormalText(paragraph, secondsToHHmmss(sleepReport.getAwakeSeconds()));
		} catch (Exception e) {
			addNormalText(paragraph, resourceBundle.getString(Internationalization.NO_DATA));
		}
		addDefaultSpace(paragraph);
		addBoldText(paragraph, resourceBundle.getString(Internationalization.TOTAL) + " : ");
		try {
			addNormalText(paragraph, secondsToHHmmss(sleepReport.getTotalSeconds()));
		} catch (Exception e) {
			addNormalText(paragraph, resourceBundle.getString(Internationalization.NO_DATA));
		}
		addNormalText(paragraph, "\n");
		addBoldText(paragraph, resourceBundle.getString(Internationalization.SCORE) + " : ");
		addNormalText(paragraph, "" + sleepReport.getScore());
		addDefaultSpace(paragraph);
		addBoldText(paragraph, resourceBundle.getString(Internationalization.UPLOAD_DATE) + " : ");
		try {
			addNormalText(paragraph, yyyyMMDDFormat.format(sleepReport.getUploadDate()));
		} catch (Exception e) {
			addNormalText(paragraph, resourceBundle.getString(Internationalization.NO_DATA) + "");
		}

		ct.addElement(paragraph);
		ct.go();
	}

	public void drawNonamePart(PdfWriter writer, User user, SleepReport sleepReport) throws DocumentException {
		Rectangle rectangle = drawAndSetRetangle(700, 760, writer);
		ColumnText ct = new ColumnText(writer.getDirectContent());
		ct.setSimpleColumn(rectangle);
		Paragraph paragraph = new Paragraph();
		paragraph.setLeading(12f);
		paragraph.setFont(fontSmallTitle);
		paragraph.add(resourceBundle.getString(Internationalization.USER_INFO) + " \n");
		addBoldText(paragraph, resourceBundle.getString(Internationalization.NAME) + ": ");
		addTextAndSpaceWithUnderLine(paragraph, "", 20);
		addDefaultSpace(paragraph);
		addBoldText(paragraph, resourceBundle.getString(Internationalization.AGE) + " : ");
		addTextAndSpaceWithUnderLine(paragraph, "", 10);
		addDefaultSpace(paragraph);
		addBoldText(paragraph, resourceBundle.getString(Internationalization.GENDER) + " : ");
		addTextAndSpaceWithUnderLine(paragraph, "", 5);
		addDefaultSpace(paragraph);
		// addBoldText(paragraph, "生日 : ");
		// SimpleDateFormat simpleDateFormat = new
		// SimpleDateFormat("yyyy-MM-DD");
		// try {
		// addTextAndSpaceWithUnderLine(paragraph,
		// simpleDateFormat.format(user.getBirthday()), 40);
		// } catch (Exception e) {
		// addTextAndSpaceWithUnderLine(paragraph, "无数据", 40);
		// }
		// addDefaultSpace(paragraph);
		addBoldText(paragraph, resourceBundle.getString(Internationalization.HEIGHT) + " : ");
		addTextAndSpaceWithUnderLine(paragraph, "", 12);
		addDefaultSpace(paragraph);
		addBoldText(paragraph, resourceBundle.getString(Internationalization.WEIGHT) + " : ");
		addTextAndSpaceWithUnderLine(paragraph, "", 20);
		addDefaultSpace(paragraph);
		addBoldText(paragraph, resourceBundle.getString(Internationalization.PHONE) + " : ");
		addTextAndSpaceWithUnderLine(paragraph, "", 30);
		addDefaultSpace(paragraph);
		addBoldText(paragraph, resourceBundle.getString(Internationalization.ESS_SCORE) + " : ");
		addTextAndSpaceWithUnderLine(paragraph, "", 25);
		addDefaultSpace(paragraph);
		addNormalText(paragraph, "\n");
		addBoldText(paragraph, resourceBundle.getString(Internationalization.BMI_INDEX) + " : ");
		addTextAndSpaceWithUnderLine(paragraph, "", 10);

		addDefaultSpace(paragraph);
		addBoldText(paragraph, resourceBundle.getString(Internationalization.MEDICAL_HISTORY) + " : ");

		addSpaceWithUnderLine(paragraph, 200);

		addNormalText(paragraph, "\n");
		// 监测日期、开始时间、结束时间、监测时长
		addBoldText(paragraph, resourceBundle.getString(Internationalization.MONITORING_DATE) + " : ");
		try {
			addTextAndSpaceWithUnderLine(paragraph, yyyyMMDDFormat.format(sleepReport.getStartTime()), 30);
		} catch (Exception e) {
			addTextAndSpaceWithUnderLine(paragraph, "", 30);
		}
		addDefaultSpace(paragraph);
		addBoldText(paragraph, resourceBundle.getString(Internationalization.STARTING_TIME) + " : ");
		try {
			addTextAndSpaceWithUnderLine(paragraph, yyyyMMDDHHmmssFormat.format(sleepReport.getStartTime()), 30);
		} catch (Exception e) {
			addTextAndSpaceWithUnderLine(paragraph, resourceBundle.getString(Internationalization.NO_DATA), 30);
		}
		addDefaultSpace(paragraph);
		addBoldText(paragraph, resourceBundle.getString(Internationalization.END_TIME) + ": ");
		try {
			addTextAndSpaceWithUnderLine(paragraph, yyyyMMDDHHmmssFormat.format(sleepReport.getEndTime()), 30);
		} catch (Exception e) {
			addTextAndSpaceWithUnderLine(paragraph, resourceBundle.getString(Internationalization.NO_DATA) + "", 30);
		}
		addDefaultSpace(paragraph);
		addBoldText(paragraph, resourceBundle.getString(Internationalization.MONITORING_DURATION) + " : ");
		try {
			addTextAndSpaceWithUnderLine(paragraph,
					secondsToHHmmss(
							sleepReport.getEndTime().getTime() / 1000 - sleepReport.getStartTime().getTime() / 1000),
					30);
		} catch (Exception e) {
			addTextAndSpaceWithUnderLine(paragraph, resourceBundle.getString(Internationalization.NO_DATA) + "", 30);
		}
		addDefaultSpace(paragraph);
		ct.addElement(paragraph);
		ct.go();
	}

	public void drawPersonalInfomationPart(PdfWriter writer, User user, SleepReport sleepReport)
			throws DocumentException {
		Rectangle rectangle = drawAndSetRetangle(700, 760, writer);
		ColumnText ct = new ColumnText(writer.getDirectContent());
		ct.setSimpleColumn(rectangle);
		Paragraph paragraph = new Paragraph();
		paragraph.setLeading(12f);
		paragraph.setFont(fontSmallTitle);
		paragraph.add(resourceBundle.getString(Internationalization.USER_INFO) + " \n");
		addBoldText(paragraph, resourceBundle.getString(Internationalization.NAME) + " : ");
		addTextAndSpaceWithUnderLine(paragraph, user.getName(), 20);
		addDefaultSpace(paragraph);
		addBoldText(paragraph, resourceBundle.getString(Internationalization.AGE) + " : ");
		addTextAndSpaceWithUnderLine(paragraph,
				getYear(user.getBirthday(), new Date()) + " " + resourceBundle.getString(Internationalization.YEAR_OLD),
				10);
		addDefaultSpace(paragraph);
		addBoldText(paragraph, resourceBundle.getString(Internationalization.GENDER) + " : ");
		addTextAndSpaceWithUnderLine(paragraph, resourceBundle.getString(user.getGender(true)), 5);
		addDefaultSpace(paragraph);
		// addBoldText(paragraph, "生日 : ");
		// SimpleDateFormat simpleDateFormat = new
		// SimpleDateFormat("yyyy-MM-DD");
		// try {
		// addTextAndSpaceWithUnderLine(paragraph,
		// simpleDateFormat.format(user.getBirthday()), 40);
		// } catch (Exception e) {
		// addTextAndSpaceWithUnderLine(paragraph, "无数据", 40);
		// }
		// addDefaultSpace(paragraph);
		addBoldText(paragraph, resourceBundle.getString(Internationalization.HEIGHT) + " : ");
		addTextAndSpaceWithUnderLine(paragraph, user.getHeight() + "cm", 12);
		addDefaultSpace(paragraph);
		addBoldText(paragraph, resourceBundle.getString(Internationalization.WEIGHT) + " : ");
		addTextAndSpaceWithUnderLine(paragraph, user.getWeight() + "kg", 20);
		addDefaultSpace(paragraph);
		addBoldText(paragraph, resourceBundle.getString(Internationalization.PHONE) + " : ");
		addTextAndSpaceWithUnderLine(paragraph, user.getPhone(), 30);
		addDefaultSpace(paragraph);
		addBoldText(paragraph, resourceBundle.getString(Internationalization.ESS_SCORE) + " : ");
		addTextAndSpaceWithUnderLine(paragraph,
				user.getEssRank() + "(" + resourceBundle.getString(user.getEssResult()) + ")", 25);
		addDefaultSpace(paragraph);
		addNormalText(paragraph, "\n");
		addBoldText(paragraph, resourceBundle.getString(Internationalization.BMI_INDEX) + " : ");
		try {
			addTextAndSpaceWithUnderLine(
					paragraph, decimalFormat.format(user.getWeight() * 10000 / user.getHeight() / user.getHeight())
							+ "(" + getBMIResult(user.getWeight() * 10000 / user.getHeight() / user.getHeight()) + ")",
					30);
		} catch (Exception e) {
			addTextAndSpaceWithUnderLine(paragraph, resourceBundle.getString(Internationalization.NO_DATA), 10);
		}

		addDefaultSpace(paragraph);
		addBoldText(paragraph, resourceBundle.getString(Internationalization.MEDICAL_HISTORY) + " : ");
		StringBuilder sb = new StringBuilder();
		if (user.getDiseaseHistories().size() <= 5) {
			for (DiseaseHistory dh : user.getDiseaseHistories()) {
				sb.append(resourceBundle.getString(dh.getName()) + "、");
			}
		} else {
			for (int i = 0; i < 5; i++) {
				sb.append(resourceBundle.getString(user.getDiseaseHistories().get(i).getName()) + "、");
			}
			sb.append(resourceBundle.getString(Internationalization.ETC) + "、");
		}

		try {
			addTextAndSpaceWithUnderLine(paragraph, sb.substring(0, sb.length() - 1), 170);
		} catch (Exception e) {
			addSpaceWithUnderLine(paragraph, 200);
		}

		addNormalText(paragraph, "\n");
		// 监测日期、开始时间、结束时间、监测时长
		addBoldText(paragraph, resourceBundle.getString(Internationalization.MONITORING_DATE) + " : ");
		try {
			addTextAndSpaceWithUnderLine(paragraph, yyyyMMDDFormat.format(sleepReport.getStartTime()), 30);
		} catch (Exception e) {
			addTextAndSpaceWithUnderLine(paragraph, resourceBundle.getString(Internationalization.NO_DATA), 30);
		}
		addDefaultSpace(paragraph);
		addBoldText(paragraph, resourceBundle.getString(Internationalization.STARTING_TIME) + " : ");
		try {
			addTextAndSpaceWithUnderLine(paragraph, yyyyMMDDHHmmssFormat.format(sleepReport.getStartTime()), 30);
		} catch (Exception e) {
			addTextAndSpaceWithUnderLine(paragraph, resourceBundle.getString(Internationalization.NO_DATA), 30);
		}
		addDefaultSpace(paragraph);
		addBoldText(paragraph, resourceBundle.getString(Internationalization.END_TIME) + " : ");
		try {
			addTextAndSpaceWithUnderLine(paragraph, yyyyMMDDHHmmssFormat.format(sleepReport.getEndTime()), 30);
		} catch (Exception e) {
			addTextAndSpaceWithUnderLine(paragraph, resourceBundle.getString(Internationalization.NO_DATA) + "", 30);
		}
		addDefaultSpace(paragraph);
		addBoldText(paragraph, resourceBundle.getString(Internationalization.MONITORING_DURATION) + " : ");
		try {
			addTextAndSpaceWithUnderLine(paragraph,
					secondsToHHmmss(
							sleepReport.getEndTime().getTime() / 1000 - sleepReport.getStartTime().getTime() / 1000),
					30);
		} catch (Exception e) {
			addTextAndSpaceWithUnderLine(paragraph, resourceBundle.getString(Internationalization.NO_DATA), 30);
		}
		addDefaultSpace(paragraph);
		ct.addElement(paragraph);
		ct.go();
	}

	/**
	 * @param d
	 * @return
	 */
	private String getBMIResult(double fat) {
		String result = null;
		if (fat < 18.5) {
			result = resourceBundle.getString(Internationalization.UNDERWEIGHT);
		} else if (fat >= 18.5 && fat <= 24.99) {
			result = resourceBundle.getString(Internationalization.NORMAL);
		} else if (fat > 25 && fat < 28) {
			result = resourceBundle.getString(Internationalization.OVERWEIGHT);
		} else if (fat > 28 && fat < 32) {
			result = resourceBundle.getString(Internationalization.OBESE);
		} else if (fat > 32) {
			result = resourceBundle.getString(Internationalization.SEVERELY_OBESE);
		}
		return result;
	}

	/**
	 * @param paragraph
	 */
	public void addDefaultSpace(Paragraph paragraph) {
		addSpace(paragraph, 5);
	}

	public void addTextAndSpaceWithUnderLine(Paragraph paragraph, String text, int length) {
		int textLength = 0;
		try {
			for (int i = 0; i < text.length(); i++) {
				char temp = text.charAt(i);
				if ((temp >= '0' && temp <= '9') || (temp >= 'a' && temp <= 'z') || (temp >= 'A' && temp <= 'Z')) {
					textLength += 2;
				} else {
					textLength += 4;
				}
			}
			length -= textLength;
			addUnderLineText(paragraph, text);
			addSpaceWithUnderLine(paragraph, length);
		} catch (Exception e) {
			addSpaceWithUnderLine(paragraph, length);
		}

	}

	public void addBoldText(Paragraph paragraph, String text) {
		paragraph.setFont(fontTextBold);
		paragraph.add(text);
		paragraph.setFont(fontText);
	}

	public void addNormalText(Paragraph paragraph, String text) {
		paragraph.setFont(fontText);
		paragraph.add(text);
		paragraph.setFont(fontText);
	}

	public void addUnderLineText(Paragraph paragraph, String text) {
		paragraph.setFont(fontTextUnderLine);
		paragraph.add(text);
		paragraph.setFont(fontText);
	}

	public void addSpaceWithUnderLine(Paragraph paragraph, int amonut) {
		paragraph.setFont(fontTextUnderLine);
		for (int i = 0; i < amonut; i++) {
			paragraph.add("\u00a0");
		}
		paragraph.setFont(fontText);
	}

	public void addSpace(Paragraph paragraph, int amonut) {
		paragraph.setFont(fontText);
		for (int i = 0; i < amonut; i++) {
			paragraph.add("\u00a0");
		}
		paragraph.setFont(fontText);
	}

	public void drawRectangle(int startY, int endY, PdfWriter writer) {
		PdfContentByte canvas = writer.getDirectContent();
		canvas.setColorFill(BaseColor.BLACK);
		canvas.moveTo(20, startY);
		canvas.lineTo(20, endY);
		canvas.lineTo(575, endY);
		canvas.lineTo(575, startY);
		canvas.lineTo(20, startY);
		canvas.closePathStroke();
	}

	public Rectangle setRectangle(int startY, int endY, PdfWriter writer) {
		Rectangle r = new Rectangle(25, startY, 575, endY);
		PdfContentByte canvas = writer.getDirectContent();
		canvas.setColorFill(BaseColor.BLACK);
		canvas.rectangle(r);
		canvas.stroke();
		return r;
	}

	public Rectangle drawAndSetRetangle(int startY, int endY, PdfWriter writer) {
		drawRectangle(startY, endY, writer);
		return setRectangle(startY, endY, writer);
	}

	public String secondsToHHmmss(long seconds) {
		try {
			long hour = seconds / 3600;
			long minute = seconds % 3600 / 60;
			long second = seconds % 60;
			return timeFormat.format(hour) + resourceBundle.getString(Internationalization.HR)
					+ timeFormat.format(minute) + resourceBundle.getString(Internationalization.MIN)
					+ timeFormat.format(second) + resourceBundle.getString(Internationalization.SEC);
		} catch (Exception e) {

		}
		return resourceBundle.getString(Internationalization.NO_DATA);
	}

	public int getYear(Date before, Date after) {
		int day = (int) (((double) after.getTime() - before.getTime()) / 1000 / 60 / 60 / 24 / 365.25);
		return Math.abs(day);
	}

	/**
	 * @return the local
	 */
	public Locale getLocal() {
		return local;
	}

	/**
	 * @param local
	 *            the local to set
	 */
	public void setLocal(Locale local) {
		this.local = local;
		resourceBundle = ResourceBundle.getBundle("messages", local);
	}

	public String getOSAHSResult(BreatheReport breatheReport, User user) {
		// List<DiseaseHistory> diseaseHistories = user.getDiseaseHistories();
		String result = resourceBundle.getString(Internationalization.NO);
		double AHI = breatheReport.getApneaHypopneaIndex();
		// boolean isDisease = false;
		// boolean isOSAHS = false;
		// for (DiseaseHistory d : diseaseHistories) {
		// if("失 眠".equals(d.getName()) || "高血压".equals(d.getName()) ||
		// "冠心病".equals(d.getName()) || "脑血管疾病".equals(d.getName()) ||
		// "糖尿病".equals(d.getName())) {
		// isDisease = true;
		// }
		// }
		//
		// if((essscore >= 9 && AHI > 5) || (essscore < 9 && AHI >= 10) || (AHI
		// > 5 && isDisease)){
		// isOSAHS = true;
		// }
		if (AHI <= 5 && breatheReport.getMinOxygenSaturation() >= 90)
			result = resourceBundle.getString(Internationalization.NO);
		if (AHI > 30) {
			result = resourceBundle.getString(Internationalization.SEVERE);
		} else if (AHI > 15) {
			result = resourceBundle.getString(Internationalization.MODERATE);
		} else if (AHI > 5) {
			result = resourceBundle.getString(Internationalization.SLIGHT);
		}
		return result;
	}

	public String getSleepHypoxiaResult(BreatheReport breatheReport) {
		Double lspo2 = breatheReport.getMinOxygenSaturation();
		String result = resourceBundle.getString(Internationalization.NO);
		if (lspo2 < 80) {
			result = resourceBundle.getString(Internationalization.SEVERE);
		} else if (lspo2 < 85) {
			result = resourceBundle.getString(Internationalization.MODERATE);
		} else if (lspo2 < 90) {
			result = resourceBundle.getString(Internationalization.SLIGHT);
		}
		return result;
	}
}