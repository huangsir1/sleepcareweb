package taiyi.web.utils;

import java.awt.Color;
import java.awt.Font;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.TimeZone;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.time.Minute;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;

import taiyi.web.constant.Internationalization;

public class LineChartImageUtil {

	private static XYDataset createDataset(String[] riqi, String data[]) throws Exception {
		TimeSeriesCollection dataset = new TimeSeriesCollection();
		TimeSeries timeSeries1 = new TimeSeries("data");
		DateFormat in = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
		for (int i = 0; i < riqi.length; i++) {
			Date parse = null;
			try {
				parse = new Date(Long.parseLong(riqi[i]));
			} catch (Exception e) {
				parse = in.parse(riqi[i]);
			}
			timeSeries1.addOrUpdate(new Minute(parse,TimeZone.getTimeZone("Asia/Shanghai"),Locale.CHINA), Double.parseDouble(data[i]));
		}
		dataset.addSeries(timeSeries1);
		return dataset; 
	}

	public static void create(String[] time, String[] data, String title, String xLabel, String yLabel,
			String imagePathNameAndExt) throws Exception {
		XYDataset xyDataset = createDataset(time, data);
		StandardChartTheme standardChartTheme = new StandardChartTheme("CN");
		ChartFactory.setChartTheme(standardChartTheme);
		JFreeChart chart = ChartFactory.createTimeSeriesChart(title, xLabel, yLabel, xyDataset, false, false, false);
		
		Font baseFont = new Font("微软雅黑", Font.PLAIN, 10);
		Font baseBigFont = new Font("微软雅黑", Font.BOLD, 10);
		XYPlot plot = chart.getXYPlot();
		TextTitle textTitle = chart.getTitle();
		textTitle.setFont(baseBigFont);

		ValueAxis domainAxis = plot.getDomainAxis(); // 水平底部列表
		domainAxis.setTickLabelFont(baseFont); // x轴下标
		domainAxis.setLabelFont(baseBigFont);

		// y轴设置
		ValueAxis rangeAxis = plot.getRangeAxis();
		rangeAxis.setLabelFont(baseBigFont);
		rangeAxis.setTickLabelFont(baseBigFont);
		rangeAxis.setAutoRange(true);
		rangeAxis.setAutoRangeMinimumSize(5);  
		
		chart.setBackgroundPaint(Color.WHITE); 

		plot.setBackgroundPaint(Color.white);
		// 设置网格竖线颜色
		plot.setDomainGridlinePaint(new Color(194, 53, 49));
		// 设置网格横线颜色 
		plot.setRangeGridlinePaint(Color.pink);
		plot.setNoDataMessage("NO DATA 没有数据");
		plot.setNoDataMessageFont(new Font("微软雅黑", Font.BOLD, 22));
		XYItemRenderer renderer = plot.getRenderer();
		renderer.setSeriesPaint(0, new Color(194, 53, 49));
		FileOutputStream fos = null;
		fos = new FileOutputStream(imagePathNameAndExt);
		ChartUtilities.writeChartAsPNG(fos, chart, 870, 225);
		fos.close();
	}

	public static void generateMailvImage(String[] riqi, String[] mailv, String imagePathAndExt) throws Exception {
		generateMailvImage(riqi, mailv, imagePathAndExt,Locale.CHINA);
	}

	public static void generateXueyangImage(String[] riqi, String[] xueyang, String imagePathAndExt) throws Exception {
		generateXueyangImage(riqi, xueyang, imagePathAndExt,Locale.CHINA);
	}
	
	public static void generateMailvImage(String[] riqi, String[] mailv, String imagePathAndExt,Locale locale) throws Exception {
		ResourceBundle bundle = ResourceBundle.getBundle("messages", locale);
		create(riqi, mailv, bundle.getString(Internationalization.PR_TREND), null, bundle.getString(Internationalization.FREQUENCY_PER_MIN), imagePathAndExt);
	}

	public static void generateXueyangImage(String[] riqi, String[] xueyang, String imagePathAndExt,Locale locale) throws Exception {
		ResourceBundle bundle = ResourceBundle.getBundle("messages", locale);
		create(riqi, xueyang, bundle.getString(Internationalization.SPO2_TREND), null, bundle.getString(Internationalization.PERCENTAGE), imagePathAndExt);
	}


	public static JFreeChart createTimeSeriesChart(JFreeChart timeSeriesChart) throws Exception {
		ChartFrame frame = new ChartFrame("TestPieChart", timeSeriesChart);
		frame.pack();
		frame.setVisible(true);
		return timeSeriesChart;
	}

	public static void main(String[] args) throws Exception {
//		generateMailvImage(readAsMap.get("riqi"), readAsMap.get("mailv"),"/Users/jason/Desktop/test/mailv.png");
//		generateXueyangImage(readAsMap.get("riqi"), readAsMap.get("xueyang"), "/Users/jason/Desktop/test/xueyang.png");
	}

}