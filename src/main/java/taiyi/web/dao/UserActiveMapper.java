package taiyi.web.dao;

import taiyi.web.model.UserActive;

public interface UserActiveMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_active
     *
     * @mbg.generated Wed Sep 14 16:32:54 CST 2016
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_active
     *
     * @mbg.generated Wed Sep 14 16:32:54 CST 2016
     */
    int insert(UserActive record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_active
     *
     * @mbg.generated Wed Sep 14 16:32:54 CST 2016
     */
    int insertSelective(UserActive record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_active
     *
     * @mbg.generated Wed Sep 14 16:32:54 CST 2016
     */
    UserActive selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_active
     *
     * @mbg.generated Wed Sep 14 16:32:54 CST 2016
     */
    int updateByPrimaryKeySelective(UserActive record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_active
     *
     * @mbg.generated Wed Sep 14 16:32:54 CST 2016
     */
    int updateByPrimaryKey(UserActive record);
}