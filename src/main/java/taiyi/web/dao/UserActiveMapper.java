package taiyi.web.dao;

import taiyi.web.model.userActive;

public interface UserActiveMapper {
    int deleteByPrimaryKey(String id);

    int insert(userActive record);

    int insertSelective(userActive record);

    userActive selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(userActive record);

    int updateByPrimaryKey(userActive record);
}