/**
 * 
 */
package taiyi.web.service.Impl;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import taiyi.web.dao.EssMapper;
import taiyi.web.dao.EssUserMapper;
import taiyi.web.dao.UserMapper;
import taiyi.web.model.Ess;
import taiyi.web.model.EssUser;
import taiyi.web.model.User;
import taiyi.web.model.dto.EssDto;
import taiyi.web.service.EssUserService;

/**
 * @author <a href="mailto:jason19659@163.com">jason19659</a>
 *
 * taiyi.web.service.Impl
 *
 * 2016年4月26日
 */
@Transactional
@Service
public class EssUserSerciceImpl implements EssUserService{

	@Autowired
	private EssUserMapper essMapper;
	@Autowired
	private UserMapper userMapper;
	/* 
	 * @see taiyi.web.service.EssUserService#deleteByPrimaryKey(java.lang.Integer)
	 */
	@Override
	public int deleteByPrimaryKey(Integer id) {
		return essMapper.deleteByPrimaryKey(id);
	}

	/* 
	 * @see taiyi.web.service.EssUserService#insert(taiyi.web.model.EssUser)
	 */
	@Override
	public int insert(EssUser record) {
		return essMapper.insert(record);
	}

	/* 
	 * @see taiyi.web.service.EssUserService#insertSelective(taiyi.web.model.EssUser)
	 */
	@Override
	public int insertSelective(EssUser record) {
		return essMapper.insertSelective(record);
	}

	/* 
	 * @see taiyi.web.service.EssUserService#selectByPrimaryKey(java.lang.Integer)
	 */
	@Override
	public EssUser selectByPrimaryKey(Integer id) {
		return essMapper.selectByPrimaryKey(id);
	}

	/* 
	 * @see taiyi.web.service.EssUserService#updateByPrimaryKeySelective(taiyi.web.model.EssUser)
	 */
	@Override
	public int updateByPrimaryKeySelective(EssUser record) {
		return essMapper.updateByPrimaryKeySelective(record);
	}

	/* 
	 * @see taiyi.web.service.EssUserService#updateByPrimaryKey(taiyi.web.model.EssUser)
	 */
	@Override
	public int updateByPrimaryKey(EssUser record) {
		return essMapper.updateByPrimaryKey(record);
	}

	/* 
	 * @see taiyi.web.service.EssUserService#insertOrUpdate(taiyi.web.model.dto.EssDto)
	 */
	@Override
	public void insertOrUpdate(EssDto essDto) {
		int sum = 0;
		if (essDto.getEssUsers().size() > 0) {
			essMapper.deleteByUserId(essDto.getEssUsers().get(0).getUserId());
		}
		for (EssUser ess : essDto.getEssUsers()) {
			essMapper.insert(ess);
			sum += ess.getRank();
		}
		if (essDto.getEssUsers().size() > 0) {
			User user = new User();
			user.setId(essDto.getEssUsers().get(0).getUserId());
			user.setEssRank(sum);
			userMapper.updateByPrimaryKeySelective(user);
		}
		
	}

	/* 
	 * @see taiyi.web.service.EssUserService#deleteByUserId(java.lang.String)
	 */
	@Override
	public void deleteByUserId(String userId) {
		essMapper.deleteByUserId(userId);
	}

	/* 
	 * @see taiyi.web.service.EssUserService#selectByUserId(java.lang.String)
	 */
	@Override
	public List<EssUser>  selectByUserId(String userId) {
		return essMapper.selectByUserId(userId);
	}

}
