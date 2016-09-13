/**
 * 
 */
package taiyi.web.model.dto;

import java.util.Arrays;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author <a href="mailto:jason19659@163.com">jason19659</a>
 *
 * taiyi.web.model.dto
 *
 * 2016年3月15日
 */
public class DiseaseHistoryDto {
	/**
	 * 失眠
	 */
	
	public static final int INSOMNIA = 1;
	/**
	 * 糖尿病
	 */
	
	public static final int DIABETES = 2;
	/**
	 * 高血压
	 */
	
	public static final int HYPERTENSION = 3;
	/**
	 * 冠心病
	 */
	
	public static final int CORONARY_HEART_DISEASE = 4;
	/**
	 * 心力衰竭
	 */
	
	public static final int HEART_FAILURE = 5;
	/**
	 * 心律失常
	 */
	
	public static final int ARRHYTHMIA = 6;
	/**
	 * 鼻腔阻塞
	 */
	
	public static final int NASAL_OBSTRUCTION = 7;
	/**
	 * 长期吸烟
	 */
	
	public static final int LONG_TERM_SMOKING = 8;
	/**
	 * 悬雍垂粗大
	 */
	
	public static final int BULKY_UVULA = 9;
	/**
	 * OSAHS的家族史
	 */
	
	public static final int FAMILY_HISTORY_OF_OSAHS = 10;
	/**
	 * 脑血管疾病
	 */
	
	public static final int CEREBRAL_VASCULAR_DISEASE = 11;
	/**
	 * 肾功能损害
	 */
	
	public static final int RENAL_FUNCTION_DAMAGE = 12;
	/**
	 * 用镇静剂／药物
	 */
	
	public static final int TO_USE_A_SEDATIVE_OR_DRUG = 13;
	/**
	 * 长期大量饮酒
	 */
	
	public static final int LONG_TERM_HEAVY_DRINKING = 14;
	
	/**
	 * 是否绝经
	 */
	public final static int IS_MENOPAUSE = 15;
	
	public DiseaseHistoryDto() {
		
	}
	
	public DiseaseHistoryDto(String userId,Integer... diseaseHistoryIds) {
		this.diseaseHistoryIds = diseaseHistoryIds;
		this.userId = userId;
	}
	
	public DiseaseHistoryDto(Integer... diseaseHistoryIds) {
		this.diseaseHistoryIds = diseaseHistoryIds;
	}
	
	private String userId;
	private Integer diseaseHistoryIds[];
	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * @return the diseaseHistoryIds
	 */
	public Integer[] getDiseaseHistoryIds() {
		return diseaseHistoryIds;
	}
	/**
	 * @param diseaseHistoryIds the diseaseHistoryIds to set
	 */
	public void setDiseaseHistoryIds(Integer[] diseaseHistoryIds) {
		this.diseaseHistoryIds = diseaseHistoryIds;
	}

	/* 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "DiseaseHistoryDto [userId=" + userId + ", diseaseHistoryIds=" + Arrays.toString(diseaseHistoryIds)
				+ "]";
	}
	
	
	
}
