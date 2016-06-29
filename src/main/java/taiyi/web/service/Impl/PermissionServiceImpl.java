/**
 * 
 */
package taiyi.web.service.Impl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import taiyi.web.dao.SystemPermissionMapper;
import taiyi.web.dao.SystemRoleMapper;
import taiyi.web.service.PermissionService;

/**
 * @author <a href="mailto:jason19659@163.com">jason19659</a>
 *
 * taiyi.web.service.Impl
 *
 * 2016年6月7日
 */
@Service
@Transactional
public class PermissionServiceImpl implements PermissionService {
	@Autowired
	private  SystemRoleMapper systemRoleMapper;
	@Autowired
	private  SystemPermissionMapper systemPermissionMapper;

	/* 
	 * @see taiyi.web.service.PermissionService#selectRoleStringsByUserId(java.lang.String)
	 */
	@Override
	public Set<String> selectRoleStringsByUserId(String userId) {
		return systemRoleMapper.selectRoleStringsByUserId(userId);
	}

	/* 
	 * @see taiyi.web.service.PermissionService#selectPermissionsByUserId(java.lang.String)
	 */
	@Override
	public Set<String> selectPermissionsByUserId(String userId) {
		return systemPermissionMapper.selectPermissionsByUserId(userId);
	}

	/* 
	 * @see taiyi.web.service.PermissionService#selectRoleStringsByUsername(java.lang.String)
	 */
	@Override
	public Set<String> selectRoleStringsByUsername(String username) {
		return systemRoleMapper.selectRoleStringsByUsername(username);
	}

	/* 
	 * @see taiyi.web.service.PermissionService#selectPermissionsByUsername(java.lang.String)
	 */
	@Override
	public Set<String> selectPermissionsByUsername(String username) {
		return systemPermissionMapper.selectPermissionsByUsername(username);
	}

}
