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

	/**
	 * 注册医生，患者，和医院
	 * @param user 患者
	 * @param doctor 医生
	 * @param hostipal 医院
	 * @return
	 */
	boolean register(User user, SystemUser doctor, Hostipal hostipal);

}
