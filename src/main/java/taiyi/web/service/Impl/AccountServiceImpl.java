/**
 * 
 */
package taiyi.web.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import taiyi.web.dao.AccountMapper;
import taiyi.web.model.Account;
import taiyi.web.service.AccountService;

/**
 * @author <a href="mailto:jason19659@163.com">jason19659</a>
 *
 * taiyi.web.service.Impl
 *
 * 2016年3月29日
 */
@Transactional
@Service
public class AccountServiceImpl implements AccountService {
	
	@Autowired
	private AccountMapper accountMapper;
	/* 
	 * @see taiyi.web.service.AccountService#deleteByPrimaryKey(java.lang.String)
	 */
	@Override
	public int deleteByPrimaryKey(String id) {
		return accountMapper.deleteByPrimaryKey(id);
	}

	/* 
	 * @see taiyi.web.service.AccountService#insert(taiyi.web.model.Account)
	 */
	@Override
	public int insert(Account record) {
		return accountMapper.insert(record);
	}

	/* 
	 * @see taiyi.web.service.AccountService#insertSelective(taiyi.web.model.Account)
	 */
	@Override
	public int insertSelective(Account record) {
		return accountMapper.insertSelective(record);
	}

	/* 
	 * @see taiyi.web.service.AccountService#selectByPrimaryKey(java.lang.String)
	 */
	@Override
	public Account selectByPrimaryKey(String id) {
		return accountMapper.selectByPrimaryKey(id);
	}

	/* 
	 * @see taiyi.web.service.AccountService#updateByPrimaryKeySelective(taiyi.web.model.Account)
	 */
	@Override
	public int updateByPrimaryKeySelective(Account record) {
		return accountMapper.updateByPrimaryKeySelective(record);
	}

	/* 
	 * @see taiyi.web.service.AccountService#updateByPrimaryKey(taiyi.web.model.Account)
	 */
	@Override
	public int updateByPrimaryKey(Account record) {
		return accountMapper.updateByPrimaryKey(record);
	}

	/* 
	 * @see taiyi.web.service.AccountService#selectByPhone(java.lang.String)
	 */
	@Override
	public Account selectByPhone(String phone) {
		return accountMapper.selectByPhone(phone);
	}

	/* 
	 * @see taiyi.web.service.AccountService#selectByToken(java.lang.String)
	 */
	@Override
	public Account selectByToken(String token) {
		return accountMapper.selectByToken(token);
	}

}
