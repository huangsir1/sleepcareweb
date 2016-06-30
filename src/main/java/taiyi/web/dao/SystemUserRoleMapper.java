package taiyi.web.dao;

import java.util.List;

import taiyi.web.model.SystemUserRoleKey;

public interface SystemUserRoleMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user_role
     *
     * @mbggenerated Tue Jun 07 11:38:58 CST 2016
     */
    int deleteByPrimaryKey(SystemUserRoleKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user_role
     *
     * @mbggenerated Tue Jun 07 11:38:58 CST 2016
     */
    int insert(SystemUserRoleKey record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user_role
     *
     * @mbggenerated Tue Jun 07 11:38:58 CST 2016
     */
    int insertSelective(SystemUserRoleKey record);
    
    
    List<SystemUserRoleKey> selectByUserId(String userId);
    List<SystemUserRoleKey> selectByRoleId(int roleId);

}