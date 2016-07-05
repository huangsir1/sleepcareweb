package taiyi.web.dao;

import java.util.List;

import taiyi.web.model.Device;

public interface DeviceMapper {
	
	List<Device> selectAll();
	
	List<Device> selectByHostipalId(int hostipalId);
    int deleteByPrimaryKey(String macAddress);

    int insert(Device record);

    int insertSelective(Device record);

    Device selectByPrimaryKey(String macAddress);

    int updateByPrimaryKeySelective(Device record);

    int updateByPrimaryKey(Device record);
}