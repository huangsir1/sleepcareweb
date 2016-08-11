package taiyi.web.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

import taiyi.web.model.BreatheReport;
import taiyi.web.model.DiseaseHistory;
import taiyi.web.model.SleepReport;
import taiyi.web.model.SubReport;
import taiyi.web.model.User;

public class PDFUtils {

	public final String CHINESE_FONT;
	public final BaseFont BASE_FONT;
	public final Font fontBigTitle;
	public final Font fontSmallTitle;
	public final Font fontText;
	public final Font fontTextUnderLine;
	public final Font fontTextBold;
	public final java.text.DecimalFormat decimalFormat = new java.text.DecimalFormat("0.00");
	public final SimpleDateFormat yyyyMMDDFormat = new SimpleDateFormat("yyyy-MM-dd");
	public final SimpleDateFormat yyyyMMDDHHmmssFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public final SimpleDateFormat HHmmssFormat = new SimpleDateFormat("HH:mm:ss");
	public Image logo;
	public Logger logger = Logger.getLogger(PDFUtils.class);

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
			String imagePathROxy, User user, SleepReport sleepReport, BreatheReport breatheReport, String result,
			SubReport subReport) throws Exception {
		createPdf(dest, imagePathROSeconds, imagePathROTimes, imagePathROml, imagePathROxy, user, sleepReport, breatheReport, result, subReport, ""); 
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
			String imagePathROxy, User user, SleepReport sleepReport, BreatheReport breatheReport, String result,
			SubReport subReport,String header) throws Exception {
		try {
			Document document = new Document(PageSize.A4);
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(dest));
			document.open();

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
			Paragraph t = new Paragraph("睡眠监测报告", fontBigTitle);
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
			String advice = subReport.getAdvice() == null ? "结果请结合临床。 " : subReport.getAdvice();
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
	public void drawDIYHeader(PdfWriter writer,String header) throws DocumentException {
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
		paragraph.add("分析结果 \n");
		addBoldText(paragraph, "结论 : ");
		addNormalText(paragraph, result);
		addNormalText(paragraph, "\n");
		addBoldText(paragraph, "建议 : ");
		addNormalText(paragraph, advice);
		addDefaultSpace(paragraph);
		ct.addElement(paragraph);
		ct.go();
	}

	public void drawSleepAnalysisPart(PdfWriter writer, SleepReport sleepReport) throws DocumentException {
		Rectangle rectangle = drawAndSetRetangle(155, 115, writer);
		ColumnText ct = new ColumnText(writer.getDirectContent());
		ct.setSimpleColumn(rectangle);
		Paragraph paragraph = new Paragraph();
		paragraph.setLeading(12f);
		paragraph.setFont(fontSmallTitle);
		paragraph.add("睡眠分析 \n");
		addBoldText(paragraph, "睡眠时间 : ");
		addNormalText(paragraph, secondsToHHmmss(sleepReport.getTotalSeconds()));
		addDefaultSpace(paragraph);
		addBoldText(paragraph, "深睡眠时长 : ");
		addNormalText(paragraph, secondsToHHmmss(sleepReport.getDeepSleepSeconds()));
		addDefaultSpace(paragraph);
		addBoldText(paragraph, "深睡眠占比 : ");
		addNormalText(paragraph,
				decimalFormat.format((double) sleepReport.getDeepSleepSeconds() * 100 / sleepReport.getTotalSeconds())
						+ "%");
		addDefaultSpace(paragraph);
		addBoldText(paragraph, "浅睡眠时长 : ");
		addNormalText(paragraph, secondsToHHmmss(sleepReport.getLightSleepSeconds()));
		addDefaultSpace(paragraph);
		addBoldText(paragraph, "浅睡眠占比 : ");
		addNormalText(paragraph,
				decimalFormat.format((double) sleepReport.getLightSleepSeconds() * 100 / sleepReport.getTotalSeconds())
						+ "%");
		addNormalText(paragraph, "\n");
		addBoldText(paragraph, "睡眠质量评分 : ");
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
		paragraph.add("脉率分析 \n");
		addBoldText(paragraph, "平均脉率 : ");
		addNormalText(paragraph, decimalFormat.format(pulse.getAveragePulse()) + "次/分钟");
		addDefaultSpace(paragraph);
		addBoldText(paragraph, "最低脉率 : ");
		addNormalText(paragraph, decimalFormat.format(pulse.getMinPulse()));
		if (pulse.getMinPulseTime() != null) {
			addNormalText(paragraph, "次/分钟， 发生于 " + HHmmssFormat.format(pulse.getMinPulseTime()));
		}

		addDefaultSpace(paragraph);
		addBoldText(paragraph, "最高脉率 : ");
		addNormalText(paragraph, decimalFormat.format(pulse.getMaxPulse()));
		if (pulse.getMaxPulseTime() != null) {
			addNormalText(paragraph, "次/分钟， 发生于 " + HHmmssFormat.format(pulse.getMaxPulseTime()));
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
		paragraph.add("血氧饱和度分析 \n");
		addBoldText(paragraph, "平均血氧饱和度 : ");
		addNormalText(paragraph, decimalFormat.format(breatheReport.getAverageOxygenSaturation()) + "%");
		addDefaultSpace(paragraph);
		addBoldText(paragraph, "最低血氧饱和度 : ");
		addNormalText(paragraph, decimalFormat.format(breatheReport.getMinOxygenSaturation()) + "%");
		addDefaultSpace(paragraph);
		addBoldText(paragraph, "血氧饱和度低于90%占比 : ");
		addNormalText(paragraph, decimalFormat.format(breatheReport.getOxygenSaturationLessthanNinetyPercent()) + "%");
		addDefaultSpace(paragraph);
		addBoldText(paragraph, "氧减指数 : ");
		addNormalText(paragraph, decimalFormat.format(subReport.getOxygenReductionIndex()));
		addDefaultSpace(paragraph);
		addBoldText(paragraph, "最大氧降 : ");
		addNormalText(paragraph, secondsToHHmmss(subReport.getMaxOxygenReduceSeconds()));
		if (subReport.getMaxOxygenReduceTime() != null) {
			addNormalText(paragraph, "， 发生于 " + HHmmssFormat.format(subReport.getMaxOxygenReduceTime()));
		}
		addNormalText(paragraph, "\n");
		addBoldText(paragraph, "最长氧降时长 : ");
		addNormalText(paragraph, secondsToHHmmss(subReport.getLongestOxygenReduceSeconds()));
		if (subReport.getLongestOxygenReduceTime() != null) {
			addNormalText(paragraph, "， 发生于 " + HHmmssFormat.format(subReport.getLongestOxygenReduceTime()));
		}
		addDefaultSpace(paragraph);
		addBoldText(paragraph, "氧减危害指数 : ");
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
		paragraph.add("呼吸统计 \n");
		addBoldText(paragraph, "呼吸紊乱指数 : ");
		try {
			addNormalText(paragraph, decimalFormat.format(breatheReport.getApneaHypopneaIndex()));
		} catch (Exception e) {
			addNormalText(paragraph, "0");
		}
		addDefaultSpace(paragraph);
		addBoldText(paragraph, "呼吸暂停次数 : ");
		addNormalText(paragraph, breatheReport.getApneaTimes() + "次");
		addDefaultSpace(paragraph);
		addBoldText(paragraph, "最长呼吸暂停 : ");
		addNormalText(paragraph, secondsToHHmmss(subReport.getLongestApneaSeconds()));
		if (subReport.getLongestApneaTime() != null) {
			addNormalText(paragraph, "， 发生于 " + HHmmssFormat.format(subReport.getLongestApneaTime()));
		}
		addDefaultSpace(paragraph);
		addBoldText(paragraph, "呼吸暂停总时长 : ");
		addNormalText(paragraph, secondsToHHmmss(subReport.getTotalApneaTimeSeconds()));
		addNormalText(paragraph, "\n");
		addBoldText(paragraph, "低通气次数 : ");
		addNormalText(paragraph, breatheReport.getHypopneaTimes() + "次");
		addDefaultSpace(paragraph);
		addBoldText(paragraph, "最长低通气 : ");
		addNormalText(paragraph, secondsToHHmmss(breatheReport.getMaxHyponeaSeconds()));
		if (breatheReport.getHyponeaHappenDate() != null) {
			addNormalText(paragraph, "， 发生于 " + HHmmssFormat.format(breatheReport.getHyponeaHappenDate()));
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
		addBoldText(paragraph, "低通气总时长 : ");
		addNormalText(paragraph, secondsToHHmmss(subReport.getTotalHypoventilationTimeSeconds()));
		addDefaultSpace(paragraph); 
		
		addBoldText(paragraph, "呼吸质量评分 : ");
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
		paragraph.add("睡眠报告 \n");
		addBoldText(paragraph, "开始时间 : ");
		try {
			addNormalText(paragraph, simpleDateFormat.format(sleepReport.getStartTime()));
		} catch (Exception e) {
			addNormalText(paragraph, "无数据");
		}
		addDefaultSpace(paragraph);
		addBoldText(paragraph, "结束时间 : ");
		try {
			addNormalText(paragraph, simpleDateFormat.format(sleepReport.getEndTime()));
		} catch (Exception e) {
			addNormalText(paragraph, "无数据");
		}
		addNormalText(paragraph, "\n");
		addBoldText(paragraph, "浅睡时长 : ");
		try {
			addNormalText(paragraph, secondsToHHmmss(sleepReport.getLightSleepSeconds()));
		} catch (Exception e) {
			addNormalText(paragraph, "无数据");
		}
		addDefaultSpace(paragraph);
		addBoldText(paragraph, "深睡时长 : ");
		try {
			addNormalText(paragraph, secondsToHHmmss(sleepReport.getDeepSleepSeconds()));
		} catch (Exception e) {
			addNormalText(paragraph, "无数据");
		}
		addDefaultSpace(paragraph);
		addBoldText(paragraph, "清醒时长 : ");
		try {
			addNormalText(paragraph, secondsToHHmmss(sleepReport.getAwakeSeconds()));
		} catch (Exception e) {
			addNormalText(paragraph, "无数据");
		}
		addDefaultSpace(paragraph);
		addBoldText(paragraph, "总计 : ");
		try {
			addNormalText(paragraph, secondsToHHmmss(sleepReport.getTotalSeconds()));
		} catch (Exception e) {
			addNormalText(paragraph, "无数据");
		}
		addNormalText(paragraph, "\n");
		addBoldText(paragraph, "评分 : ");
		addNormalText(paragraph, "" + sleepReport.getScore());
		addDefaultSpace(paragraph);
		addBoldText(paragraph, "上传日期 : ");
		try {
			addNormalText(paragraph, yyyyMMDDFormat.format(sleepReport.getUploadDate()));
		} catch (Exception e) {
			addNormalText(paragraph, "无数据");
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
		paragraph.add("用户信息 \n");
		addBoldText(paragraph, "姓名 : ");
		addTextAndSpaceWithUnderLine(paragraph, "", 20);
		addDefaultSpace(paragraph);
		addBoldText(paragraph, "年龄 : ");
		addTextAndSpaceWithUnderLine(paragraph, "", 10);
		addDefaultSpace(paragraph);
		addBoldText(paragraph, "性别 : ");
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
		addBoldText(paragraph, "身高 : ");
		addTextAndSpaceWithUnderLine(paragraph, "", 12);
		addDefaultSpace(paragraph);
		addBoldText(paragraph, "体重 : ");
		addTextAndSpaceWithUnderLine(paragraph, "", 20);
		addDefaultSpace(paragraph);
		addBoldText(paragraph, "电话 : ");
		addTextAndSpaceWithUnderLine(paragraph, "", 30);
		addDefaultSpace(paragraph);
		addBoldText(paragraph, "ESS评分 : ");
		addTextAndSpaceWithUnderLine(paragraph, "", 50);
		addDefaultSpace(paragraph);
		addNormalText(paragraph, "\n");
		addBoldText(paragraph, "BMI指数 : ");
		addTextAndSpaceWithUnderLine(paragraph, "", 10);

		addDefaultSpace(paragraph);
		addBoldText(paragraph, "病史 : ");

		addSpaceWithUnderLine(paragraph, 200);

		addNormalText(paragraph, "\n");
		// 监测日期、开始时间、结束时间、监测时长
		addBoldText(paragraph, "监测日期 : ");
		try {
			addTextAndSpaceWithUnderLine(paragraph, yyyyMMDDFormat.format(sleepReport.getStartTime()), 30);
		} catch (Exception e) {
			addTextAndSpaceWithUnderLine(paragraph, "", 30);
		}
		addDefaultSpace(paragraph);
		addBoldText(paragraph, "开始时间 : ");
		try {
			addTextAndSpaceWithUnderLine(paragraph, yyyyMMDDHHmmssFormat.format(sleepReport.getStartTime()), 30);
		} catch (Exception e) {
			addTextAndSpaceWithUnderLine(paragraph, " 无数据", 30);
		}
		addDefaultSpace(paragraph);
		addBoldText(paragraph, "结束时间 : ");
		try {
			addTextAndSpaceWithUnderLine(paragraph, yyyyMMDDHHmmssFormat.format(sleepReport.getEndTime()), 30);
		} catch (Exception e) {
			addTextAndSpaceWithUnderLine(paragraph, " 无数据", 30);
		}
		addDefaultSpace(paragraph);
		addBoldText(paragraph, "监测时长 : ");
		try {
			addTextAndSpaceWithUnderLine(paragraph,
					secondsToHHmmss(
							sleepReport.getEndTime().getTime() / 1000 - sleepReport.getStartTime().getTime() / 1000),
					30);
		} catch (Exception e) {
			addTextAndSpaceWithUnderLine(paragraph, " 无数据", 30);
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
		paragraph.add("用户信息 \n");
		addBoldText(paragraph, "姓名 : ");
		addTextAndSpaceWithUnderLine(paragraph, user.getName(), 20);
		addDefaultSpace(paragraph);
		addBoldText(paragraph, "年龄 : ");
		addTextAndSpaceWithUnderLine(paragraph, getYear(user.getBirthday(), new Date()) + "岁", 10);
		addDefaultSpace(paragraph);
		addBoldText(paragraph, "性别 : ");
		addTextAndSpaceWithUnderLine(paragraph, user.getGender(), 5);
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
		addBoldText(paragraph, "身高 : ");
		addTextAndSpaceWithUnderLine(paragraph, user.getHeight() + "cm", 12);
		addDefaultSpace(paragraph);
		addBoldText(paragraph, "体重 : ");
		addTextAndSpaceWithUnderLine(paragraph, user.getWeight() + "kg", 20);
		addDefaultSpace(paragraph);
		addBoldText(paragraph, "电话 : ");
		addTextAndSpaceWithUnderLine(paragraph, user.getPhone(), 30);
		addDefaultSpace(paragraph);
		addBoldText(paragraph, "ESS评分 : ");
		addTextAndSpaceWithUnderLine(paragraph, user.getEssRank() + "(" + user.getEssResult() + ")", 50);
		addDefaultSpace(paragraph);
		addNormalText(paragraph, "\n");
		addBoldText(paragraph, "BMI指数 : ");
		try {
			addTextAndSpaceWithUnderLine(
					paragraph, decimalFormat.format(user.getWeight() * 10000 / user.getHeight() / user.getHeight())
							+ "(" + getBMIResult(user.getWeight() * 10000 / user.getHeight() / user.getHeight()) + ")",
					30);
		} catch (Exception e) {
			addTextAndSpaceWithUnderLine(paragraph, "无数据", 10);
		}

		addDefaultSpace(paragraph);
		addBoldText(paragraph, "病史 : ");
		StringBuilder sb = new StringBuilder();
		if (user.getDiseaseHistories().size() <= 8) {
			for (DiseaseHistory dh : user.getDiseaseHistories()) {
				sb.append(dh.getName() + "、");
			}
		} else {
			for (int i = 0; i < 8; i++) {
				sb.append(user.getDiseaseHistories().get(i).getName() + "、");
			}
			sb.append("等、");
		}

		try {
			addTextAndSpaceWithUnderLine(paragraph, sb.substring(0, sb.length() - 1), 200);
		} catch (Exception e) {
			addSpaceWithUnderLine(paragraph, 200);
		}

		addNormalText(paragraph, "\n");
		// 监测日期、开始时间、结束时间、监测时长
		addBoldText(paragraph, "监测日期 : ");
		try {
			addTextAndSpaceWithUnderLine(paragraph, yyyyMMDDFormat.format(sleepReport.getStartTime()), 30);
		} catch (Exception e) {
			addTextAndSpaceWithUnderLine(paragraph, " 无数据", 30);
		}
		addDefaultSpace(paragraph);
		addBoldText(paragraph, "开始时间 : ");
		try {
			addTextAndSpaceWithUnderLine(paragraph, yyyyMMDDHHmmssFormat.format(sleepReport.getStartTime()), 30);
		} catch (Exception e) {
			addTextAndSpaceWithUnderLine(paragraph, " 无数据", 30);
		}
		addDefaultSpace(paragraph);
		addBoldText(paragraph, "结束时间 : ");
		try {
			addTextAndSpaceWithUnderLine(paragraph, yyyyMMDDHHmmssFormat.format(sleepReport.getEndTime()), 30);
		} catch (Exception e) {
			addTextAndSpaceWithUnderLine(paragraph, " 无数据", 30);
		}
		addDefaultSpace(paragraph);
		addBoldText(paragraph, "监测时长 : ");
		try {
			addTextAndSpaceWithUnderLine(paragraph,
					secondsToHHmmss(
							sleepReport.getEndTime().getTime() / 1000 - sleepReport.getStartTime().getTime() / 1000),
					30);
		} catch (Exception e) {
			addTextAndSpaceWithUnderLine(paragraph, " 无数据", 30);
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
			result = "过轻";
		} else if (fat >= 18.5 && fat <= 24.99) {
			result = "正常";
		} else if (fat > 25 && fat < 28) {
			result = "过重";
		} else if (fat > 28 && fat < 32) {
			result = "肥胖";
		} else if (fat > 32) {
			result = "非常肥胖";
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
			return hour + "小时" + minute + "分" + second + "秒";
		} catch (Exception e) {

		}
		return "无数据";
	}

	public int getYear(Date before, Date after) {
		int day = (int) (((double)after.getTime() - before.getTime()) / 1000 / 60 / 60 / 24 / 365.25);
		return Math.abs(day);
	}
	
}