/**
 * 
 */
package taiyi.web.service;

import java.io.File;

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
	 * 将原始数据文件解析成报告
	 * @param file 数据文件
	 * @return
	 */
	public BaseReport OriginalFileToBaseReport(File file);
}
