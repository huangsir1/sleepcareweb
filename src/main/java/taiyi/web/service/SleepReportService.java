package taiyi.web.service;

import java.util.List;

import taiyi.web.model.SleepReport;
import taiyi.web.model.User;

public interface SleepReportService {
	List<SleepReport> selectByUserId(String userId);
	List<SleepReport> selectByHostipalIdAndUserId(String userId,Integer hostipalId);
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sleep_report
     *
     * @mbggenerated Tue Mar 08 16:09:20 CST 2016
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sleep_report
     *
     * @mbggenerated Tue Mar 08 16:09:20 CST 2016
     */
    int insert(SleepReport record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sleep_report
     *
     * @mbggenerated Tue Mar 08 16:09:20 CST 2016
     */
    int insertSelective(SleepReport record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sleep_report
     *
     * @mbggenerated Tue Mar 08 16:09:20 CST 2016
     */
    SleepReport selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sleep_report
     *
     * @mbggenerated Tue Mar 08 16:09:20 CST 2016
     */
    int updateByPrimaryKeySelective(SleepReport record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sleep_report
     *
     * @mbggenerated Tue Mar 08 16:09:20 CST 2016
     */
    int updateByPrimaryKey(SleepReport record);
}