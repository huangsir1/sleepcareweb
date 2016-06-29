/**
 * 
 */
package taiyi.web.model.dto;

/**
 * @author <a href="mailto:jason19659@163.com">jason19659</a>
 *
 *         taiyi.web.model.dto
 *
 *         2016年4月28日
 */
public class ESSAndDisEaseHistoryDto {
	private DiseaseHistoryDto diseaseHistoryDto;
	private EssDto essDto;

	public ESSAndDisEaseHistoryDto() {

	}

	public ESSAndDisEaseHistoryDto(String userId, int SITTING_READING_RANK, int WATCHING_TV_RANK,
			int WHEN_SITTING_IN_A_PUBLIC_PLACE_RANK, int LONG_RIDE_WITHOUT_A_BREAK_RANK,
			int WHEN_PEOPLE_SIT_AND_TALK_RANK, int WHEN_RESTING_AFTER_A_MEAL_RANK,
			int DRIVING_WHILE_WAITING_FOR_TRAFFIC_LIGHTS_RANK, int PM_SUPINE_REST_RANK, Integer ...diseaseHistoryIds) {
		this.diseaseHistoryDto = new DiseaseHistoryDto(userId, diseaseHistoryIds);
		this.essDto = new EssDto(userId, SITTING_READING_RANK, WATCHING_TV_RANK, WHEN_SITTING_IN_A_PUBLIC_PLACE_RANK, LONG_RIDE_WITHOUT_A_BREAK_RANK, WHEN_PEOPLE_SIT_AND_TALK_RANK, WHEN_RESTING_AFTER_A_MEAL_RANK, DRIVING_WHILE_WAITING_FOR_TRAFFIC_LIGHTS_RANK, PM_SUPINE_REST_RANK);
				

	}

	/**
	 * @return the diseaseHistoryDto
	 */
	public DiseaseHistoryDto getDiseaseHistoryDto() {
		return diseaseHistoryDto;
	}

	/**
	 * @param diseaseHistoryDto
	 *            the diseaseHistoryDto to set
	 */
	public void setDiseaseHistoryDto(DiseaseHistoryDto diseaseHistoryDto) {
		this.diseaseHistoryDto = diseaseHistoryDto;
	}

	/**
	 * @return the essDto
	 */
	public EssDto getEssDto() {
		return essDto;
	}

	/**
	 * @param essDto
	 *            the essDto to set
	 */
	public void setEssDto(EssDto essDto) {
		this.essDto = essDto;
	}

	/* 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ESSAndDisEaseHistoryDto [diseaseHistoryDto=" + diseaseHistoryDto + ", essDto=" + essDto + "]";
	}
	
	

}
