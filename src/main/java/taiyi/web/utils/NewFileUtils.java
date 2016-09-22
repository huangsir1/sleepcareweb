/**
 * 
 */
package taiyi.web.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 处理文件工具类
 * 
 * @author <a href="mailto:jason19659@163.com">jason19659</a>
 *
 *         taiyi.web.utils
 *
 *         2016年5月16日
 */
public class NewFileUtils {

	public static String generateDataFile(String filePath) throws Exception {
		StringBuilder sb = new StringBuilder("Ml,Xy,RR,NumTime,PI\n");
		String readFile = FileOperateUtils.readFile(filePath);
		String regex = "t:(\\d+.\\d+)[\\s\\S]*?s:\\[(\\d+), (\\d+), (\\d+), (\\d+), (\\d+), (\\d+), (\\d+), (\\d+), (\\d+), (\\d+)\\][\\s\\S]{3}k:\\[(\\d+), (\\d+), (\\d+), (\\d+), (\\d+), (\\d+), (\\d+), (\\d+), (\\d+), (\\d+)\\]";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(readFile);
		while (matcher.find()) {
			long time = Long.parseLong(matcher.group(1).replace(".", ""));
			int xueyang = 0, mailv = 0;
			for (int i = 2; i <= 11; i++) {
				xueyang += Integer.parseInt(matcher.group(i));
			}
			for (int i = 12; i <= 21; i++) {
				mailv += Integer.parseInt(matcher.group(i));
			}
			xueyang /= 10;
			mailv /= 10;
			int RR = mailv == 0 ? 0 : 6000 / mailv;
			sb.append(mailv + "," + xueyang + "," + RR + "," + time + ",1\n");
		}
		return sb.toString();
	}

	public static void generateNewDataFile(String filePath) throws Exception {
		StringBuilder sb = new StringBuilder("Ml,Xy,PI,Status,RR,Head1,Head2,Battery,Index,NumTime,CheckSum\n");
		String readFile = FileOperateUtils.readFile(filePath);
		String regex = "t:(\\d+.\\d+)[\\s\\S]*?s:\\[(\\d+), (\\d+), (\\d+), (\\d+), (\\d+), (\\d+), (\\d+), (\\d+), (\\d+), (\\d+)\\][\\s\\S]{3}k:\\[(\\d+), (\\d+), (\\d+), (\\d+), (\\d+), (\\d+), (\\d+), (\\d+), (\\d+), (\\d+)\\]";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(readFile);
		while (matcher.find()) {
			long time = Long.parseLong(matcher.group(1).replace(".", ""));
			int xueyang = 0, mailv = 0;
			for (int i = 2; i <= 11; i++) {
				xueyang += Integer.parseInt(matcher.group(i));
			}
			for (int i = 12; i <= 21; i++) {
				mailv += Integer.parseInt(matcher.group(i));
			}
			xueyang /= 10;
			mailv /= 10;
			int RR = mailv == 0 ? 0 : 6000 / mailv;
			sb.append(mailv + "," + xueyang + "," + 0 + ",0," + RR + ",0,0,0,0," + time + ",0\n");
		}
		FileOperateUtils.writeFileByNIO(filePath + "_upload.txt", sb.toString());
	}

}
