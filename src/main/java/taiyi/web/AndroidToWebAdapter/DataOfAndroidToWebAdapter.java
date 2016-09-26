/**
 * 
 */
package taiyi.web.AndroidToWebAdapter;

import java.io.File;

import org.apache.log4j.chainsaw.Main;

import taiyi.web.model.dto.BaseReport;

/**
 * @author <a href="mailto:jason19659@163.com">jason19659</a>
 *
 * taiyi.web.service
 *
 * 2016年7月8日
 */
public interface DataOfAndroidToWebAdapter {
	/**
	 * 原始数据文件转换成报告
	 * @param file 文件
	 * @return
	 */
	public  BaseReport originalFileToBaseReport(File file) ;
	/**
	 * 二进制文件转换报告
	 * @param file 文件
	 * @return
	 */
	public  BaseReport binaryFileToBaseReport(File file) ;
}

