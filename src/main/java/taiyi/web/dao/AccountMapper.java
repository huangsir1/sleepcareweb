package taiyi.web.dao;

import taiyi.web.model.Account;

public interface AccountMapper {
    int deleteByPrimaryKey(String id);

    int insert(Account record);

    int insertSelective(Account record);

    Account selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Account record);

    int updateByPrimaryKey(Account record);

	Account selectByPhone(String phone);


	Account selectByToken(String token);
}