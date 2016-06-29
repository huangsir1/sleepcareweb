package taiyi.web.dao;

import taiyi.web.model.Ess;

public interface EssMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Ess record);

    int insertSelective(Ess record);

    Ess selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Ess record);

    int updateByPrimaryKey(Ess record);
}