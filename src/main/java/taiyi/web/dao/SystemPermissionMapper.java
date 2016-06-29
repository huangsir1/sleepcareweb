package taiyi.web.dao;

import java.util.Set;

import taiyi.web.model.SystemPermission;

public interface SystemPermissionMapper {
	
	Set<String> selectPermissionsByUserId(String userId);
	Set<String> selectPermissionsByUsername(String username);
	
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_permission
     *
     * @mbggenerated Tue Jun 07 12:12:02 CST 2016
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_permission
     *
     * @mbggenerated Tue Jun 07 12:12:02 CST 2016
     */
    int insert(SystemPermission record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_permission
     *
     * @mbggenerated Tue Jun 07 12:12:02 CST 2016
     */
    int insertSelective(SystemPermission record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_permission
     *
     * @mbggenerated Tue Jun 07 12:12:02 CST 2016
     */
    SystemPermission selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_permission
     *
     * @mbggenerated Tue Jun 07 12:12:02 CST 2016
     */
    int updateByPrimaryKeySelective(SystemPermission record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_permission
     *
     * @mbggenerated Tue Jun 07 12:12:02 CST 2016
     */
    int updateByPrimaryKey(SystemPermission record);
}