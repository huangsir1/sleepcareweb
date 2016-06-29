/**
 * 
 */
package taiyi.web.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import taiyi.web.dao.DiseaseHistoryUserMapper;
import taiyi.web.model.DiseaseHistory;
import taiyi.web.model.DiseaseHistoryUser;
import taiyi.web.model.dto.DiseaseHistoryDto;
import taiyi.web.service.DiseaseHistoryUserService;

/**
 * @author <a href="mailto:jason19659@163.com">jason19659</a>
 *
 * taiyi.web.service.Impl
 *
 * 2016年3月15日
 */
@Service
@Transactional
public class DiseaseHistoryUserServiceImpl implements DiseaseHistoryUserService{
	@Autowired
	private DiseaseHistoryUserMapper diseaseHistoryUserMapper;

	/* 
	 * @see taiyi.web.service.DiseaseHistoryUserService#insertDHUs(taiyi.web.model.dto.DiseaseHistoryDto)
	 */
	@Override
	public int insertDHUs(DiseaseHistoryDto diseaseHistoryDto) {
		int count = 0;
		for (Integer dids : diseaseHistoryDto.getDiseaseHistoryIds()) {
			DiseaseHistoryUser d = new DiseaseHistoryUser();
			d.setUserId(diseaseHistoryDto.getUserId());
			d.setDiseaseHistoryId(dids);
			count += diseaseHistoryUserMapper.insert(d);
		}
		return count;
	}

	
	@Override
	public int insertDHUs(Integer[] diseaseHistoryIds,String userId) {
		int count = 0;
		for (Integer dids : diseaseHistoryIds) {
			DiseaseHistoryUser d = new DiseaseHistoryUser();
			d.setUserId(userId);
			d.setDiseaseHistoryId(dids);
			count += diseaseHistoryUserMapper.insert(d);
		}
		return count;
	}
	/* 
	 * @see taiyi.web.service.DiseaseHistoryUserService#deleteByUserId(java.lang.String)
	 */
	@Override
	public int deleteByUserId(String userId) {
		return diseaseHistoryUserMapper.deleteByUserId(userId);
	}

}
