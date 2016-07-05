/**
 * 
 */
package taiyi.web.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import taiyi.web.dao.DeviceMapper;
import taiyi.web.model.Device;
import taiyi.web.service.DeviceService;

/**
 * @author <a href="mailto:jason19659@163.com">jason19659</a>
 *
 * taiyi.web.service.Impl
 *
 * 2016年7月4日
 */
@Service
public class DeviceServiceImpl implements DeviceService{
	@Autowired
	private DeviceMapper deviceMapper;
	/* 
	 * @see taiyi.web.service.DeviceService#selectAll()
	 */
	@Override
	public List<Device> selectAll() {
		return deviceMapper.selectAll();
	}

	/* 
	 * @see taiyi.web.service.DeviceService#selectByHostipalId(int)
	 */
	@Override
	public List<Device> selectByHostipalId(int hostipalId) {
		return deviceMapper.selectByHostipalId(hostipalId);
	}

	/* 
	 * @see taiyi.web.service.DeviceService#deleteByPrimaryKey(java.lang.String)
	 */
	@Override
	public int deleteByPrimaryKey(String macAddress) {
		return deviceMapper.deleteByPrimaryKey(macAddress);
	}

	/* 
	 * @see taiyi.web.service.DeviceService#insert(taiyi.web.model.Device)
	 */
	@Override
	public int insert(Device record) {
		return deviceMapper.insert(record);
	}

	/* 
	 * @see taiyi.web.service.DeviceService#insertSelective(taiyi.web.model.Device)
	 */
	@Override
	public int insertSelective(Device record) {
		return deviceMapper.insertSelective(record);
	}

	/* 
	 * @see taiyi.web.service.DeviceService#selectByPrimaryKey(java.lang.String)
	 */
	@Override
	public Device selectByPrimaryKey(String macAddress) {
		return deviceMapper.selectByPrimaryKey(macAddress);
	}

	/* 
	 * @see taiyi.web.service.DeviceService#updateByPrimaryKeySelective(taiyi.web.model.Device)
	 */
	@Override
	public int updateByPrimaryKeySelective(Device record) {
		return deviceMapper.updateByPrimaryKeySelective(record);
	}

	/* 
	 * @see taiyi.web.service.DeviceService#updateByPrimaryKey(taiyi.web.model.Device)
	 */
	@Override
	public int updateByPrimaryKey(Device record) {
		return deviceMapper.updateByPrimaryKey(record);
	}

}
