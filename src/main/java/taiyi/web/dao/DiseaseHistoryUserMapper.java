package taiyi.web.dao;

import taiyi.web.model.DiseaseHistoryUser;

public interface DiseaseHistoryUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DiseaseHistoryUser record);

    int insertSelective(DiseaseHistoryUser record);

    DiseaseHistoryUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DiseaseHistoryUser record);

    int updateByPrimaryKey(DiseaseHistoryUser record);
    
    int deleteByUserId(String userId);
}