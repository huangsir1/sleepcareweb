/**
 * 
 */
package taiyi.web.service;

import taiyi.web.model.dto.DiseaseHistoryDto;

/**
 * @author <a href="mailto:jason19659@163.com">jason19659</a>
 *
 *         taiyi.web.service
 *
 *         2016年3月15日
 */
public interface DiseaseHistoryUserService {
	int insertDHUs(DiseaseHistoryDto diseaseHistoryDto);

	int deleteByUserId(String userId);

	int insertDHUs(Integer[] diseaseHistoryIds, String userId);
}
