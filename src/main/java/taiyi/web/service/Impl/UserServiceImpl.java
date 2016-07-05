/**
 * 
 */
package taiyi.web.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import taiyi.web.dao.UserMapper;
import taiyi.web.model.User;
import taiyi.web.model.dto.PageModel;
import taiyi.web.service.UserService;

/**
 * @author <a href="mailto:jason19659@163.com">jason19659</a>
 *
 * taiyi.web.service.Impl
 *
 * 2016年3月8日
 */
@Service
@Transactional
public class UserServiceImpl implements UserService{

	@Autowired
	private UserMapper userMapper;
	/* 
	 * @see taiyi.web.service.UserService#deleteByPrimaryKey(java.lang.String)
	 */
	@Override
	public int deleteByPrimaryKey(String id) {
		return userMapper.deleteByPrimaryKey(id);
	}

	/* 
	 * @see taiyi.web.service.UserService#insert(taiyi.web.model.User)
	 */
	@Override
	public int insert(User record) {
		return userMapper.insert(record);
	}

	/* 
	 * @see taiyi.web.service.UserService#insertSelective(taiyi.web.model.User)
	 */
	@Override
	public int insertSelective(User record) {
		return userMapper.insertSelective(record);
	}

	/* 
	 * @see taiyi.web.service.UserService#selectByPrimaryKey(java.lang.String)
	 */
	@Override
	public User selectByPrimaryKey(String id) {
		return userMapper.selectByPrimaryKey(id);
	}

	/* 
	 * @see taiyi.web.service.UserService#updateByPrimaryKeySelective(taiyi.web.model.User)
	 */
	@Override
	public int updateByPrimaryKeySelective(User record) {
		return userMapper.updateByPrimaryKeySelective(record);
	}

	/* 
	 * @see taiyi.web.service.UserService#updateByPrimaryKey(taiyi.web.model.User)
	 */
	@Override
	public int updateByPrimaryKey(User record) {
		return userMapper.updateByPrimaryKey(record);
	}

	/* 
	 * @see taiyi.web.service.UserService#selectWithPackages(java.lang.String)
	 */
	@Override
	public User selectWithDH(String userId) {
		return userMapper.selectWithDH(userId);
	}

	/* 
	 * @see taiyi.web.service.UserService#selectAllWIthDH()
	 */
	@Override
	public List<User> selectAllWIthDH() {
		return userMapper.selectAllWithDH();
	}

	/* 
	 * @see taiyi.web.service.UserService#getAllUsers()
	 */
	@Override
	public List<User> getAllUsers() {
		return userMapper.getAllUsers();
	}

	/* 
	 * @see taiyi.web.service.UserService#searchUsers(taiyi.web.model.User)
	 */
	@Override
	public List<User> searchUsers(User user) {
		return userMapper.searchUsers(user);
	}

	@Override
	public PageModel searchUsersByPage(User user,Integer page,Integer pagesize) {
		PageModel pageModel = new PageModel();
		if (page == null || pagesize == null) {
			List<User> searchUsers = searchUsers(user);
			pageModel.setTotal(searchUsers.size());
			pageModel.setRows(searchUsers);
		} else {
//			Map<String,Object> maps = new HashMap<String,Object>();
			List<User> users = userMapper.searchUsersByPage(user,(page - 1) * pagesize,pagesize);
//			long count = userMapper.searchUsersByPageCount(user);
			pageModel.setTotal(users.size());
			pageModel.setRows(users);
		}
		return pageModel;
	}

	/* 
	 * @see taiyi.web.service.UserService#selectBySysUserId(java.lang.String)
	 */
	@Override
	public List<User> selectBySysUserId(String userId) {
		return userMapper.selectBySysUserId(userId);
	}

	/* 
	 * @see taiyi.web.service.UserService#selectBySysUsername(java.lang.String)
	 */
	@Override
	public List<User> selectBySysUsername(String username) {
		return userMapper.selectBySysUsername(username);
	}


}
