package taiyi.web.service;

import taiyi.web.model.DiseaseHistory;

public interface DiseaseHistoryService {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table disease_history
     *
     * @mbggenerated Tue Mar 08 16:09:20 CST 2016
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table disease_history
     *
     * @mbggenerated Tue Mar 08 16:09:20 CST 2016
     */
    int insert(DiseaseHistory record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table disease_history
     *
     * @mbggenerated Tue Mar 08 16:09:20 CST 2016
     */
    int insertSelective(DiseaseHistory record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table disease_history
     *
     * @mbggenerated Tue Mar 08 16:09:20 CST 2016
     */
    DiseaseHistory selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table disease_history
     *
     * @mbggenerated Tue Mar 08 16:09:20 CST 2016
     */
    int updateByPrimaryKeySelective(DiseaseHistory record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table disease_history
     *
     * @mbggenerated Tue Mar 08 16:09:20 CST 2016
     */
    int updateByPrimaryKey(DiseaseHistory record);
}