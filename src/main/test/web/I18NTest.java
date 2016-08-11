/**
 * 
 */
package web;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author <a href="mailto:jason19659@163.com">jason19659</a>
 *
 * web
 *
 * 2016年8月9日
 */
public class I18NTest {
	private I18NTest(){}
	public static void main(String[] args) {
		System.out.println(I18NTest.class.getResource("/"));
        ResourceBundle res=ResourceBundle.getBundle("messages",Locale.CHINA);
        System.out.println(res.getString("hello"));
        System.out.println(MessageFormat.format(res.getString("hello"), "世界"));; 
	}
}
