/**
 * 
 */
package taiyi.web.model.dto;

import java.util.Date;

/**
 * @author <a href="mailto:jason19659@163.com">jason19659</a>
 *
 * taiyi.web.model.dto
 *
 * 2016年3月30日
 */
public class ValidateCode {
	private String code;
	private String phone;
	private Date upDate;
	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}
	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}
	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	/**
	 * @return the upDate
	 */
	public Date getUpDate() {
		return upDate;
	}
	/**
	 * @param upDate the upDate to set
	 */
	public void setUpDate(Date upDate) {
		this.upDate = upDate;
	}
	
	
	
}
