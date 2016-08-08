/**
 * 
 */
package taiyi.web.service.Impl;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

import taiyi.web.dao.SystemRoleMapper;
import taiyi.web.dao.SystemUserMapper;
import taiyi.web.model.SystemUser;
import taiyi.web.model.dto.SystemUserRoleDto;
import taiyi.web.utils.BeanUtilsForAndroid;

/**
 * @author <a href="mailto:jason19659@163.com">jason19659</a>
 *
 * taiyi.web.service.Impl
 *
 * 2016年6月2日
 */
@Service
@Transactional
public class SystemUserServiceImpl implements taiyi.web.service.SystemUserService {
	@Autowired
	private SystemUserMapper systemUserMapper;
	@Autowired
	private SystemRoleMapper systemRoleMapper;
	

	/* 
	 * @see taiyi.web.service.SystemUserService#deleteByPrimaryKey(java.lang.String)
	 */
	@Override
	public int deleteByPrimaryKey(String id) {
		return systemUserMapper.deleteByPrimaryKey(id);
	}

	/* 
	 * @see taiyi.web.service.SystemUserService#insert(taiyi.web.model.SystemUser)
	 */
	@Override
	public int insert(SystemUser record) {
		return systemUserMapper.insert(record);
	}

	/* 
	 * @see taiyi.web.service.SystemUserService#insertSelective(taiyi.web.model.SystemUser)
	 */
	@Override
	public int insertSelective(SystemUser record) {
		return systemUserMapper.insertSelective(record);
	}

	/* 
	 * @see taiyi.web.service.SystemUserService#selectByPrimaryKey(java.lang.String)
	 */
	@Override
	public SystemUser selectByPrimaryKey(String id) {
		return systemUserMapper.selectByPrimaryKey(id);
	}

	/* 
	 * @see taiyi.web.service.SystemUserService#selectByUsername(java.lang.String)
	 */
	@Override
	public SystemUser selectByUsername(String username) {
		return systemUserMapper.selectByUsername(username);
	}

	/* 
	 * @see taiyi.web.service.Sys-temUserService#updateByPrimaryKeySelective(taiyi.web.model.SystemUser)
	 */
	@Override
	public int updateByPrimaryKeySelective(SystemUser record) {
		return systemUserMapper.updateByPrimaryKeySelective(record);
	}

	/* 
	 * @see taiyi.web.service.SystemUserService#updateByPrimaryKey(taiyi.web.model.SystemUser)
	 */
	@Override
	public int updateByPrimaryKey(SystemUser record) {
		return systemUserMapper.updateByPrimaryKey(record);
	}

	/* 
	 * @see taiyi.web.service.SystemUserService#selectByHostipal(java.lang.Integer)
	 */
	@Override
	public List<SystemUser> selectByHostipal(Integer HostipalId) {
		return systemUserMapper.selectByHostipal(HostipalId);
	}

	/* 
	 * @see taiyi.web.service.SystemUserService#selectByTrueName(java.lang.String)
	 */
	@Override
	public SystemUser selectByTrueName(String name) {
		return systemUserMapper.selectByTrueName(name);
	}

	/* 
	 * @see taiyi.web.service.SystemUserService#selectWithRoleByHostipal(java.lang.Integer)
	 */
	@Override
	public List<SystemUserRoleDto> selectWithRoleByHostipal(Integer HostipalId) {
		List<SystemUser> systemUsers = systemUserMapper.selectByHostipal(HostipalId);
		List<SystemUserRoleDto> systemUserRoleDtos = Lists.newArrayListWithCapacity(systemUsers.size());
		for(SystemUser systemUser : systemUsers) {
			SystemUserRoleDto systemUserRoleDto = new SystemUserRoleDto();
			BeanUtilsForAndroid.copy(systemUser, systemUserRoleDto);
			Set<String> selectRoleStringsByUserId = systemRoleMapper.selectRoleStringsByUserId(systemUser.getId());
			systemUserRoleDto.setSystemRoles(new TreeSet<String>(selectRoleStringsByUserId));
			systemUserRoleDtos.add(systemUserRoleDto);
		}
		return systemUserRoleDtos;
	}


}
