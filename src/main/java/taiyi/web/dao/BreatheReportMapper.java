package taiyi.web.dao;

import taiyi.web.model.BreatheReport;

public interface BreatheReportMapper {
    int deleteByPrimaryKey(String id);

    int insert(BreatheReport record);

    int insertSelective(BreatheReport record);

    BreatheReport selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(BreatheReport record);

    int updateByPrimaryKey(BreatheReport record);
}