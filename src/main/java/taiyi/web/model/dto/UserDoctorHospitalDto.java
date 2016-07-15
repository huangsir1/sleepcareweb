/**
 * 
 */
package taiyi.web.model.dto;

import java.util.UUID;

import taiyi.web.model.Hostipal;
import taiyi.web.model.SystemUser;
import taiyi.web.model.User;

/**
 * @author <a href="mailto:jason19659@163.com">jason19659</a>
 *
 * taiyi.web.model.dto
 *
 * 2016年7月14日
 */
public class UserDoctorHospitalDto {
	private User user;
	private Hostipal hostipal;
	private SystemUser doctor;
	public UserDoctorHospitalDto() {
		
	}
	
	public UserDoctorHospitalDto(User user,String hostipalName,String doctorName) {
		this.user = user;
		hostipal = new Hostipal();
		hostipal.setName(hostipalName);
		doctor = new SystemUser();
		doctor.setName(doctorName);
	}
	
	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}
	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}
	/**
	 * @return the hostipal
	 */
	public Hostipal getHostipal() {
		return hostipal;
	}
	/**
	 * @param hostipal the hostipal to set
	 */
	public void setHostipal(Hostipal hostipal) {
		this.hostipal = hostipal;
	}
	/**
	 * @return the doctor
	 */
	public SystemUser getDoctor() {
		return doctor;
	}
	/**
	 * @param doctor the doctor to set
	 */
	public void setDoctor(SystemUser doctor) {
		this.doctor = doctor;
	}
	
	
}
