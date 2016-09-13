/**
 * 
 */
package taiyi.web.utils;

import java.awt.Color;
import java.awt.Font;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.ResourceBundle;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.axis.StandardTickUnitSource;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.TextAnchor;

import taiyi.web.constant.Internationalization;

/**
 * @author <a href="mailto:jason19659@163.com">jason19659</a>
 *
 *         test
 *
 *         2016年3月23日
 */
public class BarChartImageUtil {
	public static void main(String[] args) throws IOException {
		 generateSecondsOfReducedOxygenImage(new Integer[] { 9, 9, 9,
		 9, 9, 9},"分","/Users/jason/Desktop/bar2.png");
		 generateTimesOfReducedOxygenImage(new
		 Integer[]{0,0,0,0,0,0},"/Users/jason/Desktop/bar1.png");
//		generateLineChartImage(new Integer[] { 600, 220, 5130, 1231, 123, 11 }, "次数", "氧减总次数", "百分比", "次数",
//				"/Users/jason/Desktop/bar3.png");
	}

	public static void generateBarChartToImage(Integer[] yValue, String xRowKey, String title, String xLabel,
			String yLabel, String imagePathNameAndExt) throws IOException {
		Font baseFont = new Font("微软雅黑", Font.PLAIN, 10);
		Font baseBigFont = new Font("微软雅黑", Font.BOLD, 10);
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		dataset.addValue(yValue[0], xRowKey, "0-50%");
		dataset.addValue(yValue[1], xRowKey, "50-59%");
		dataset.addValue(yValue[2], xRowKey, "60-69%");
		dataset.addValue(yValue[3], xRowKey, "70-79%");
		dataset.addValue(yValue[4], xRowKey, "80-89%");
		dataset.addValue(yValue[5], xRowKey, "90-100%");
		StandardChartTheme standardChartTheme = new StandardChartTheme("CN");
		ChartFactory.setChartTheme(standardChartTheme);
		JFreeChart chart = ChartFactory.createBarChart(title, xLabel, yLabel, dataset, PlotOrientation.VERTICAL, false,
				false, false);

		CategoryPlot plot = chart.getCategoryPlot();
		plot.setBackgroundPaint(Color.white);
		// 设置网格竖线颜色
		plot.setDomainGridlinePaint(new Color(194, 53, 49));
		// 设置网格横线颜色
		plot.setRangeGridlinePaint(Color.pink);
		plot.setNoDataMessage("NO DATA 没有数据");
		BarRenderer customBarRenderer = (BarRenderer) plot.getRenderer();
		ItemLabelPosition itemLabelPosition = new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12,
				TextAnchor.BASELINE_CENTER);
		customBarRenderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		customBarRenderer.setBaseItemLabelsVisible(true);
		TextTitle textTitle = chart.getTitle();
		textTitle.setFont(baseBigFont);

		customBarRenderer.setBasePositiveItemLabelPosition(itemLabelPosition);
		customBarRenderer.setItemLabelAnchorOffset(10D);
		customBarRenderer.setShadowVisible(false);
		customBarRenderer.setSeriesPaint(0, new Color(194, 53, 49));
		customBarRenderer.setSeriesPaint(1, new Color(47, 69, 84));

		CategoryAxis domainAxis = plot.getDomainAxis(); // 水平底部列表
		domainAxis.setTickLabelFont(baseFont); // x轴下标
		domainAxis.setLabelFont(baseBigFont);

		customBarRenderer.setBarPainter(new StandardBarPainter());
		customBarRenderer.setItemMargin(-0.01);
		plot.setRenderer(customBarRenderer);
		// y轴设置
		ValueAxis rangeAxis = plot.getRangeAxis();
		rangeAxis.setLabelFont(baseBigFont);
		rangeAxis.setTickLabelFont(baseBigFont);
		// 设置最高的一个 Item 与图片顶端的距离
		rangeAxis.setUpperMargin(0.4);
		// 设置最低的一个 Item 与图片底端的距离
		rangeAxis.setLowerMargin(0.1);
		rangeAxis.setAutoRange(true);
		boolean isZero = true;
		for (int i = 0; i < yValue.length; i++) {
			if (yValue[i] != 0 ) {
				isZero = false;
			}
		}
		if (isZero) {
			rangeAxis.setRange(0,100);
		}
		plot.setRangeAxis(rangeAxis);
		FileOutputStream fos = null;
		fos = new FileOutputStream(imagePathNameAndExt);
		ChartUtilities.writeChartAsPNG(fos, chart, 435, 225);
		fos.close();
	}

	public static void generateLineChartImage(Integer[] yValue, String xRowKey, String title, String xLabel,
			String yLabel, String imagePathNameAndExt) throws IOException {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for (int i = 0; i < 10; i++) {
			dataset.addValue(yValue[0], xRowKey, "0-50%" + i);
			dataset.addValue(yValue[1], xRowKey, "50-59%" + i);
			dataset.addValue(yValue[2], xRowKey, "60-69%" + i);
			dataset.addValue(yValue[3], xRowKey, "70-79%" + i);
			dataset.addValue(yValue[4], xRowKey, "80-89%" + i);
			dataset.addValue(yValue[5], xRowKey, "90-100%" + i);
		}

		JFreeChart chart = ChartFactory.createLineChart(title, xLabel, yLabel, dataset, PlotOrientation.VERTICAL, false,
				false, false);
		CategoryPlot plot = chart.getCategoryPlot();
		plot.setBackgroundPaint(Color.white);
		// 设置网格竖线颜色
		plot.setDomainGridlinePaint(new Color(194, 53, 49));
		// 设置网格横线颜色
		plot.setRangeGridlinePaint(Color.pink);
		// 数据轴属性部分
		NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		rangeAxis.setAutoRangeIncludesZero(true); // 自动生成
		rangeAxis.setUpperMargin(0.20);
		rangeAxis.setLabelAngle(Math.PI / 2.0);
		rangeAxis.setAutoRange(true);
		
		CategoryAxis categoryaxis = plot.getDomainAxis();
		categoryaxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
		categoryaxis.setMaximumCategoryLabelWidthRatio(5.0f);
		categoryaxis.setMaximumCategoryLabelLines(1);
		categoryaxis.setTickLabelsVisible(true);
		chart.setBackgroundImageAlpha(0.01F);
		categoryaxis.setTickMarksVisible(true);
		categoryaxis.setCategoryLabelPositionOffset(20);
		categoryaxis.setCategoryMargin(0.5);

		LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
		renderer.setBaseItemLabelsVisible(true);
		renderer.setSeriesPaint(0, Color.black); // 设置折线的颜色
		renderer.setBaseShapesFilled(true);
		renderer.setBaseItemLabelsVisible(false);
		renderer.setBasePositiveItemLabelPosition(
				new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_LEFT));
		renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		renderer.setBaseItemLabelFont(new Font("Dialog", 1, 14));

		plot.setRenderer(renderer);

		FileOutputStream fos = null;
		fos = new FileOutputStream(imagePathNameAndExt);
		ChartUtilities.writeChartAsPNG(fos, chart, 435, 225);
		fos.close();
	}

	public static void generateTimesOfReducedOxygenImage(Integer[] yValue, String imagePathNameAndExt)
			throws IOException {
		generateTimesOfReducedOxygenImage(yValue, imagePathNameAndExt,Locale.CHINA);
	}

	public static void generateSecondsOfReducedOxygenImage(Integer[] yValue, String xLabel, String imagePathNameAndExt)
			throws IOException {
		generateSecondsOfReducedOxygenImage(yValue, xLabel, imagePathNameAndExt,Locale.CHINA);
	}
	
	public static void generateTimesOfReducedOxygenImage(Integer[] yValue, String imagePathNameAndExt,Locale locale)
			throws IOException {
		ResourceBundle bundle = ResourceBundle.getBundle("messages", locale);
		generateBarChartToImage(yValue, bundle.getString(Internationalization.FREQUENCY), bundle.getString(Internationalization.OXYGEN_DISTRIBUTION), bundle.getString(Internationalization.PERCENTAGE), bundle.getString(Internationalization.FREQUENCY), imagePathNameAndExt);
	}

	public static void generateSecondsOfReducedOxygenImage(Integer[] yValue, String xLabel, String imagePathNameAndExt,Locale locale)
			throws IOException {
		ResourceBundle bundle = ResourceBundle.getBundle("messages", locale);
		generateBarChartToImage(yValue, bundle.getString(Internationalization.FREQUENCY), bundle.getString(Internationalization.OXYGEN_DESATURATION_DISTRIBUTION), bundle.getString(Internationalization.PERCENTAGE), bundle.getString(Internationalization.IMAGE_MIN), imagePathNameAndExt);
	}
	
	
	
}

class CustomTickUnit extends NumberTickUnit {
	private boolean display = false;

	public CustomTickUnit(double size) {
		super(size);

	}

	public CustomTickUnit(double size, boolean display, NumberFormat formatter) {
		super(size, formatter);
		this.display = display;
		System.out.println("display value is " + display);
	}

	/**
	 * Converts a value to a string.
	 * 
	 * @param value
	 *            the value.
	 * 
	 * @return The formatted string.
	 */
	@Override
	public String valueToString(double value) {
		System.out.println("get value is " + value + display);
		if (((int) value) % 10 == 0) {
			return super.valueToString(value);
		} else {
			return "";
		}
	}

	public void setDisplay(boolean display) {
		this.display = display;
	}

	public boolean isDisplay() {
		return display;
	}
}
