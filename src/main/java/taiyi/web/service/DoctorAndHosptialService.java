/**
 * 
 */
package taiyi.web.service;

import taiyi.web.model.Hostipal;
import taiyi.web.model.SystemUser;
import taiyi.web.model.User;

/**
 * @author <a href="mailto:jason19659@163.com">jason19659</a>
 *
 * taiyi.web.service
 *
 * 2016年7月7日
 */
public interface DoctorAndHosptialService {

	boolean register(User user, SystemUser doctor, Hostipal hostipal);

}
