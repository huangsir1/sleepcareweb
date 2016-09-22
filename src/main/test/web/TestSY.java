/**
 * 
 */
package web;

import org.apache.log4j.chainsaw.Main;

/**
 * @author <a href="mailto:jason19659@163.com">jason19659</a>
 *
 * web
 *
 * 2016年9月21日
 */
public class TestSY {
	public static void main(String[] args) {
		new Thread() {
			/* 
			 * @see java.lang.Thread#run()
			 */
			@Override
			public void run() {
				new TestSY().test();
			}
		}.start();
		new Thread() {
			/* 
			 * @see java.lang.Thread#run()
			 */
			@Override
			public void run() {
				new TestSY().test();
			}
		}.start();
		new Thread() {
			/* 
			 * @see java.lang.Thread#run()
			 */
			@Override
			public void run() {
				new TestSY().test();
			}
		}.start();
	}
	
	public synchronized  static void test() {
		System.out.println(123);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
}
