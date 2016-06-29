package taiyi.web.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import taiyi.web.model.User;

public interface UserMapper {
	
	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table user
	 * @mbggenerated  Thu Jun 02 15:08:20 CST 2016
	 */
	int deleteByPrimaryKey(String id);
	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table user
	 * @mbggenerated  Thu Jun 02 15:08:20 CST 2016
	 */
	int insert(User record);
	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table user
	 * @mbggenerated  Thu Jun 02 15:08:20 CST 2016
	 */
	int insertSelective(User record);
	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table user
	 * @mbggenerated  Thu Jun 02 15:08:20 CST 2016
	 */
	User selectByPrimaryKey(String id);
	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table user
	 * @mbggenerated  Thu Jun 02 15:08:20 CST 2016
	 */
	int updateByPrimaryKeySelective(User record);
	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table user
	 * @mbggenerated  Thu Jun 02 15:08:20 CST 2016
	 */
	int updateByPrimaryKey(User record);
	List<User> selectAllWithDH();
	List<User> getAllUsers();
	User selectWithDH(String userId);
    /**
	 * @param user
	 * @return
	 */
	List<User> searchUsers(User user);
	/**
	 * @param user
	 * @return
	 */
	List<User> searchUsersByPage(@Param("user") User user,@Param("current")int current,@Param("pagesize")int pagesize);
	/**
	 * @param users
	 * @return
	 */
	long searchUsersByPageCount(User user);
	/**
	 * @param userId
	 * @return
	 */
	List<User> selectBySysUserId(String userId);
	/**
	 * @param username
	 * @return
	 */
	List<User> selectBySysUsername(String username);
}