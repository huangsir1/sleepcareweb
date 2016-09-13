package taiyi.web.dao;

import taiyi.web.model.UserActive;

public interface UserActiveMapper {
    int deleteByPrimaryKey(String id);

    int insert(UserActive record);

    int insertSelective(UserActive record);

    UserActive selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(UserActive record);

    int updateByPrimaryKey(UserActive record);
}