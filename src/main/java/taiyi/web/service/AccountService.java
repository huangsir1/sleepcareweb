/**
 * 
 */
package taiyi.web.service;

import taiyi.web.model.Account;

/**
 * @author <a href="mailto:jason19659@163.com">jason19659</a>
 *
 *         taiyi.web.service
 *
 *         2016年3月29日
 */
public interface AccountService {
	int deleteByPrimaryKey(String id);

	int insert(Account record);

	int insertSelective(Account record);

	Account selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(Account record);

	int updateByPrimaryKey(Account record);
	
	Account selectByPhone(String phone);

	Account selectByToken(String token);
	
}
