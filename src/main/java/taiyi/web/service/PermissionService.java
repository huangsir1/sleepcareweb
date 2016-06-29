/**
 * 
 */
package taiyi.web.service;

import java.util.Set;

/**
 * @author <a href="mailto:jason19659@163.com">jason19659</a>
 *
 *         taiyi.web.service
 *
 *         2016年6月7日
 */

public interface PermissionService {

	Set<String> selectRoleStringsByUserId(String id);

	Set<String> selectPermissionsByUserId(String id);
	
	Set<String> selectRoleStringsByUsername(String username);

	Set<String> selectPermissionsByUsername(String username);

}
