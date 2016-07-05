package taiyi.web.model;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

public class Device {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column device.mac_address
     *
     * @mbggenerated Mon Jul 04 14:15:08 CST 2016
     */
    private String macAddress;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column device.description
     *
     * @mbggenerated Mon Jul 04 14:15:08 CST 2016
     */
    private String description;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column device.reg_date
     *
     * @mbggenerated Mon Jul 04 14:15:08 CST 2016
     */
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date regDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column device.hostipal_id
     *
     * @mbggenerated Mon Jul 04 14:15:08 CST 2016
     */
    private Integer hostipalId;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column device.mac_address
     *
     * @return the value of device.mac_address
     *
     * @mbggenerated Mon Jul 04 14:15:08 CST 2016
     */
    public String getMacAddress() {
        return macAddress;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column device.mac_address
     *
     * @param macAddress the value for device.mac_address
     *
     * @mbggenerated Mon Jul 04 14:15:08 CST 2016
     */
    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column device.description
     *
     * @return the value of device.description
     *
     * @mbggenerated Mon Jul 04 14:15:08 CST 2016
     */
    public String getDescription() {
        return description;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column device.description
     *
     * @param description the value for device.description
     *
     * @mbggenerated Mon Jul 04 14:15:08 CST 2016
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column device.reg_date
     *
     * @return the value of device.reg_date
     *
     * @mbggenerated Mon Jul 04 14:15:08 CST 2016
     */
    public Date getRegDate() {
        return regDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column device.reg_date
     *
     * @param regDate the value for device.reg_date
     *
     * @mbggenerated Mon Jul 04 14:15:08 CST 2016
     */
    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column device.hostipal_id
     *
     * @return the value of device.hostipal_id
     *
     * @mbggenerated Mon Jul 04 14:15:08 CST 2016
     */
    public Integer getHostipalId() {
        return hostipalId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column device.hostipal_id
     *
     * @param hostipalId the value for device.hostipal_id
     *
     * @mbggenerated Mon Jul 04 14:15:08 CST 2016
     */
    public void setHostipalId(Integer hostipalId) {
        this.hostipalId = hostipalId;
    }

	/* 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Device [macAddress=" + macAddress + ", description=" + description + ", regDate=" + regDate
				+ ", hostipalId=" + hostipalId + "]";
	}
    
    
    
}