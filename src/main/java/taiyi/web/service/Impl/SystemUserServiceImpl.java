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
import taiyi.web.dao.SystemUserRoleMapper;
import taiyi.web.model.SystemRole;
import taiyi.web.model.SystemUser;
import taiyi.web.model.SystemUserRoleKey;
import taiyi.web.model.User;
import taiyi.web.model.dto.Status;
import taiyi.web.model.dto.SystemUserRoleDto;
import taiyi.web.service.UserService;
import taiyi.web.utils.BeanUtilsForAndroid;
import taiyi.web.utils.EncryptUtils;

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
	@Autowired
	private SystemUserRoleMapper systemUserRoleMapper;
	@Autowired
	private UserService userService;

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
	
	@Override
	public void saveHospitalAdmin(SystemUser systemUser, String[] systemRoles, String password) {
		
		// save user
		systemUser.setPassword(EncryptUtils.encryptOriginalTaiirPassword(password));
		this.insert(systemUser);
		// save permission
		for (String role : systemRoles) {
			switch (role) {
			case "doctor":
			case "hospital":
				SystemRole selectByRoleName = systemRoleMapper.selectByRoleName(role);
				SystemUserRoleKey s = new SystemUserRoleKey();
				s.setRoleId(selectByRoleName.getId());
				s.setUserId(systemUser.getId());
				systemUserRoleMapper.insert(s);
				break;
			default:
				break;
			}
		}
	}
	
	@Override
	public void editHospitalAdmin(SystemUser systemUser, String[] systemRoles) {
		
		// edit user
		this.updateByPrimaryKeySelective(systemUser);
		// edit permission
		systemUserRoleMapper.deleteByPrimaryUserId(systemUser.getId());
		for (String role : systemRoles) {
			switch (role) {
			case "doctor":
			case "hospital":
				SystemRole selectByRoleName = systemRoleMapper.selectByRoleName(role);
				SystemUserRoleKey s = new SystemUserRoleKey();
				s.setRoleId(selectByRoleName.getId());
				s.setUserId(systemUser.getId());
				systemUserRoleMapper.insert(s);
				break;
			default:
				break;
			}
		}
	}

	/* 
	 * @see taiyi.web.service.SystemUserService#deleteSystemUser(java.lang.String)
	 */
	@Override
	public Status deleteSystemUser(String id) {
		List<User> users = userService.selectBySysUsername(id);
		if (users != null) {
			if (users.size() != 0) {
				return Status.getFailed("无法删除,该账户下存在普通用户！");
			}
		}
		systemUserRoleMapper.deleteByPrimaryUserId(id);
		systemUserMapper.deleteByPrimaryKey(id);
		return Status.getSuccess();
	}

}
