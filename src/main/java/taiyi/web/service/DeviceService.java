/**
 * 
 */
package taiyi.web.service;

import java.util.List;

import taiyi.web.model.Device;

/**
 * @author <a href="mailto:jason19659@163.com">jason19659</a>
 *
 *         taiyi.web.service
 *
 *         2016年7月4日
 */
public interface DeviceService {
	List<Device> selectAll();

	List<Device> selectByHostipalId(int hostipalId);

	int deleteByPrimaryKey(String macAddress);

	int insert(Device record);

	int insertSelective(Device record);

	Device selectByPrimaryKey(String macAddress);

	int updateByPrimaryKeySelective(Device record);

	int updateByPrimaryKey(Device record);
}
