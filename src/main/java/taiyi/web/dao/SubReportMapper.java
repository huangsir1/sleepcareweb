package taiyi.web.dao;

import taiyi.web.model.SubReport;

public interface SubReportMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sub_report
     *
     * @mbggenerated Fri Jun 24 15:28:49 CST 2016
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sub_report
     *
     * @mbggenerated Fri Jun 24 15:28:49 CST 2016
     */
    int insert(SubReport record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sub_report
     *
     * @mbggenerated Fri Jun 24 15:28:49 CST 2016
     */
    int insertSelective(SubReport record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sub_report
     *
     * @mbggenerated Fri Jun 24 15:28:49 CST 2016
     */
    SubReport selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sub_report
     *
     * @mbggenerated Fri Jun 24 15:28:49 CST 2016
     */
    int updateByPrimaryKeySelective(SubReport record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sub_report
     *
     * @mbggenerated Fri Jun 24 15:28:49 CST 2016
     */
    int updateByPrimaryKey(SubReport record);
}