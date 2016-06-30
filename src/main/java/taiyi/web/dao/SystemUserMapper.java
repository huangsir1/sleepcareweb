package taiyi.web.dao;

import taiyi.web.model.SystemUser;

public interface SystemUserMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user
     *
     * @mbggenerated Thu Jun 02 15:23:31 CST 2016
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user
     *
     * @mbggenerated Thu Jun 02 15:23:31 CST 2016
     */
    int insert(SystemUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user
     *
     * @mbggenerated Thu Jun 02 15:23:31 CST 2016
     */
    int insertSelective(SystemUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user
     *
     * @mbggenerated Thu Jun 02 15:23:31 CST 2016
     */
    SystemUser selectByPrimaryKey(String id);
    SystemUser selectByUsername(String username);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user
     *
     * @mbggenerated Thu Jun 02 15:23:31 CST 2016
     */
    int updateByPrimaryKeySelective(SystemUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_user
     *
     * @mbggenerated Thu Jun 02 15:23:31 CST 2016
     */
    int updateByPrimaryKey(SystemUser record);
}