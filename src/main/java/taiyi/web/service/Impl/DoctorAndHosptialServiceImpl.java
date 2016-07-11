/**
 * 
 */
package taiyi.web.service.Impl;

import org.springframework.stereotype.Service;

import taiyi.web.model.Hostipal;
import taiyi.web.model.SystemUser;
import taiyi.web.model.User;
import taiyi.web.service.DoctorAndHosptialService;

/**
 * @author <a href="mailto:jason19659@163.com">jason19659</a>
 *
 * taiyi.web.service
 *
 * 2016年7月7日
 */
@Service
public class DoctorAndHosptialServiceImpl implements DoctorAndHosptialService{

	/* 
	 * @see taiyi.web.service.DoctorAndHosptialService#register(taiyi.web.model.User, taiyi.web.model.SystemUser, taiyi.web.model.Hostipal)
	 */
	@Override
	public boolean register(User user, SystemUser doctor, Hostipal hostipal) {
		// TODO Auto-generated method stub
		return false;
	}


}
