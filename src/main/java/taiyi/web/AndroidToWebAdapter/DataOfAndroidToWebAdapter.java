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
	
	public  BaseReport originalFileToBaseReport(File file) ;
	public  BaseReport binaryFileToBaseReport(File file) ;
}
