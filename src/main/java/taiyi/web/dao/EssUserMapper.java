package taiyi.web.dao;

import java.util.List;

import taiyi.web.model.EssUser;

public interface EssUserMapper {
    int deleteByPrimaryKey(Integer id);
    
    int deleteByUserId(String userId);
    
    int insert(EssUser record);

    int insertSelective(EssUser record);

    EssUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(EssUser record);

    int updateByPrimaryKey(EssUser record);

	List<EssUser> selectByUserId(String userId);
}