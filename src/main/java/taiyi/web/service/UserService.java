package taiyi.web.service;

import java.util.List;

import taiyi.web.model.User;
import taiyi.web.model.dto.PageModel;

public interface UserService {
	List<User> selectAllWIthDH();
	List<User> getAllUsers();
	List<User> searchUsers(User user,Integer hospitalId);
	
	User selectWithDH(String userId);
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user
     *
     * @mbggenerated Tue Mar 08 16:09:20 CST 2016
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user
     *
     * @mbggenerated Tue Mar 08 16:09:20 CST 2016
     */
    int insert(User record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user
     *
     * @mbggenerated Tue Mar 08 16:09:20 CST 2016
     */
    int insertSelective(User record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user
     *
     * @mbggenerated Tue Mar 08 16:09:20 CST 2016
     */
    User selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user
     *
     * @mbggenerated Tue Mar 08 16:09:20 CST 2016
     */
    int updateByPrimaryKeySelective(User record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user
     *
     * @mbggenerated Tue Mar 08 16:09:20 CST 2016
     */
    int updateByPrimaryKey(User record);
	/**
	 * @param user
	 * @return
	 */
	List<User> searchUsers(User user);

    PageModel searchUsersByPage(User user,Integer current,Integer pagesize);
    
    List<User> selectBySysUserId(String userId);
    
    List<User> selectBySysUsername(String username);
    
    List<User> selectUserByHostipalId(Integer hostipalId);
    
    void updateLatestDateToNow(String userId);
	/**
	 * @param user
	 * @param hospitalId
	 * @param page
	 * @param pagesize
	 * @return
	 */
	PageModel searchUsersByPage(User user, Integer hospitalId, Integer page, Integer pagesize);
}