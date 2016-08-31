/**
 * 
 */
package web;

import java.util.Locale;

import org.junit.Test;

/**
 * @author <a href="mailto:jason19659@163.com">jason19659</a>
 *
 * web
 *
 * 2016年8月9日
 */
public class I18NTest extends Thread {
	public static void main(String[] args) {
//		System.out.println(I18NTest.class.getResource("/"));
//        ResourceBundle res=ResourceBundle.getBundle("messages",Locale.CHINA);
//        System.out.println(res.getString("hello"));
//        System.out.println(MessageFormat.format(res.getString("hello"), "世界"));; 
        
        Locale china = Locale.PRC;
		System.out.println(china.toString());
		Locale locale = new Locale(china.getLanguage());
		System.out.println(locale.getDisplayName());
	}
	
	@Test
	public void test() {
		Locale china = Locale.CHINA;
		System.out.println(china.getVariant());
	}
}
