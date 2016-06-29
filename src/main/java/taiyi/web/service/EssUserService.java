/**
 * 
 */
package taiyi.web.service;

import taiyi.web.model.EssUser;
import taiyi.web.model.dto.EssDto;

/**
 * @author <a href="mailto:jason19659@163.com">jason19659</a>
 *
 *         taiyi.web.service
 *
 *         2016年4月26日
 */
public interface EssUserService {
	int deleteByPrimaryKey(Integer id);

	int insert(EssUser record);

	int insertSelective(EssUser record);

	EssUser selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(EssUser record);

	int updateByPrimaryKey(EssUser record);

	/**
	 * @param essDto
	 */
	void insertOrUpdate(EssDto essDto);

	/**
	 * @param userId
	 */
	void deleteByUserId(String userId);
}
