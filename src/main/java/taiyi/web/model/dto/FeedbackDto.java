/**
 * 
 */
package taiyi.web.model.dto;

import taiyi.web.model.Feedback;

/**
 * @author <a href="mailto:jason19659@163.com">jason19659</a>
 *
 * taiyi.web.model.dto
 *
 * 2016年3月30日
 */
public class FeedbackDto extends Feedback{
	private String token;

	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * @param token the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}
	
	public Feedback covertToFeedBack() {
		Feedback feedback = new Feedback();
		feedback.setAccountId(getAccountId());
		feedback.setContact(getContact());
		feedback.setContent(getContent());
		feedback.setId(getId());
		feedback.setUploadDate(getUploadDate());
		return feedback;
	}
}
