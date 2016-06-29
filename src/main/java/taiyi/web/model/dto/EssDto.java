/**
 * 
 */
package taiyi.web.model.dto;

import java.util.ArrayList;
import java.util.List;

import taiyi.web.model.EssUser;

/**
 * @author <a href="mailto:jason19659@163.com">jason19659</a>
 *
 * taiyi.web.model.dto
 *
 * 2016年4月26日
 */
public class EssDto {
	private List<EssUser> essUsers;
	/**
	 * 坐着阅读时
	 */
	public static final int SITTING_READING = 1;
	/**
	 * 看电视时
	 */
	public static final int WATCHING_TV = 2;
	/**
	 * 在公共场所坐着不动时( 如在剧场或开会)
	 */
	public static final int WHEN_SITTING_IN_A_PUBLIC_PLACE = 3;
	/**
	 * 长时间坐车时中间不休息( 超过 ! 5)
	 */
	public static final int LONG_RIDE_WITHOUT_A_BREAK = 4;
	/**
	 * 坐着与人谈话时
	 */
	public static final int WHEN_PEOPLE_SIT_AND_TALK = 5;
	/**
	 * 饭后休息时( 未饮酒时)
	 */
	public static final int WHEN_RESTING_AFTER_A_MEAL = 6;
	/**
	 * 开车等红绿灯时
	 */
	public static final int DRIVING_WHILE_WAITING_FOR_TRAFFIC_LIGHTS = 7;
	/**
	 * 下午静卧休息时
	 */
	public  static final int PM_SUPINE_REST = 8;
	
	public EssDto() {
		
	}
	
	public EssDto(String userId,int SITTING_READING_RANK,
			int WATCHING_TV_RANK,
			int WHEN_SITTING_IN_A_PUBLIC_PLACE_RANK,
			int LONG_RIDE_WITHOUT_A_BREAK_RANK,
			int WHEN_PEOPLE_SIT_AND_TALK_RANK,
			int WHEN_RESTING_AFTER_A_MEAL_RANK,
			int DRIVING_WHILE_WAITING_FOR_TRAFFIC_LIGHTS_RANK,
			int PM_SUPINE_REST_RANK) {
		essUsers = new ArrayList<EssUser>();
		essUsers.add(new EssUser(userId,SITTING_READING, SITTING_READING_RANK));
		essUsers.add(new EssUser(userId,WATCHING_TV, WATCHING_TV_RANK));
		essUsers.add(new EssUser(userId,WHEN_SITTING_IN_A_PUBLIC_PLACE, WHEN_SITTING_IN_A_PUBLIC_PLACE_RANK));
		essUsers.add(new EssUser(userId,LONG_RIDE_WITHOUT_A_BREAK, LONG_RIDE_WITHOUT_A_BREAK_RANK));
		essUsers.add(new EssUser(userId,WHEN_PEOPLE_SIT_AND_TALK, WHEN_PEOPLE_SIT_AND_TALK_RANK));
		essUsers.add(new EssUser(userId,WHEN_RESTING_AFTER_A_MEAL, WHEN_RESTING_AFTER_A_MEAL_RANK));
		essUsers.add(new EssUser(userId,DRIVING_WHILE_WAITING_FOR_TRAFFIC_LIGHTS, DRIVING_WHILE_WAITING_FOR_TRAFFIC_LIGHTS_RANK));
		essUsers.add(new EssUser(userId,PM_SUPINE_REST, PM_SUPINE_REST_RANK));
	}


	/**
	 * @return the essUsers
	 */
	public List<EssUser> getEssUsers() {
		return essUsers;
	}


	/**
	 * @param essUsers the essUsers to set
	 */
	public void setEssUsers(List<EssUser> essUsers) {
		this.essUsers = essUsers;
	}

	/* 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "EssDto [essUsers=" + essUsers + "]";
	}



}
