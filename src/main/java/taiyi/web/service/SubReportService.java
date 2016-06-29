package taiyi.web.service;

import taiyi.web.model.SubReport;

public interface SubReportService {
    int deleteByPrimaryKey(String id);

    int insert(SubReport record);

    int insertSelective(SubReport record);

    SubReport selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SubReport record);

    int updateByPrimaryKey(SubReport record);
}