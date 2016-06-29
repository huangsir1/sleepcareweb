/**
 * 
 */
package taiyi.web.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import taiyi.web.dao.DiseaseHistoryMapper;
import taiyi.web.model.DiseaseHistory;
import taiyi.web.service.DiseaseHistoryService;

/**
 * @author <a href="mailto:jason19659@163.com">jason19659</a>
 *
 * taiyi.web.service.Impl
 *
 * 2016年3月15日
 */
@Service
@Transactional
public class DiseaseHistoryServiceImpl implements DiseaseHistoryService {
	@Autowired
	private DiseaseHistoryMapper diseaseHistoryMapper;
	/* 
	 * @see taiyi.web.service.DiseaseHistoryService#deleteByPrimaryKey(java.lang.Integer)
	 */
	@Override
	public int deleteByPrimaryKey(Integer id) {
		return diseaseHistoryMapper.deleteByPrimaryKey(id);
	}

	/* 
	 * @see taiyi.web.service.DiseaseHistoryService#insert(taiyi.web.model.DiseaseHistory)
	 */
	@Override
	public int insert(DiseaseHistory record) {
		return diseaseHistoryMapper.insert(record);
	}

	/* 
	 * @see taiyi.web.service.DiseaseHistoryService#insertSelective(taiyi.web.model.DiseaseHistory)
	 */
	@Override
	public int insertSelective(DiseaseHistory record) {
		return diseaseHistoryMapper.insertSelective(record);
	}

	/* 
	 * @see taiyi.web.service.DiseaseHistoryService#selectByPrimaryKey(java.lang.Integer)
	 */
	@Override
	public DiseaseHistory selectByPrimaryKey(Integer id) {
		return diseaseHistoryMapper.selectByPrimaryKey(id);
	}

	/* 
	 * @see taiyi.web.service.DiseaseHistoryService#updateByPrimaryKeySelective(taiyi.web.model.DiseaseHistory)
	 */
	@Override
	public int updateByPrimaryKeySelective(DiseaseHistory record) {
		return diseaseHistoryMapper.updateByPrimaryKeySelective(record);
	}

	/* 
	 * @see taiyi.web.service.DiseaseHistoryService#updateByPrimaryKey(taiyi.web.model.DiseaseHistory)
	 */
	@Override
	public int updateByPrimaryKey(DiseaseHistory record) {
		return diseaseHistoryMapper.updateByPrimaryKey(record);
	}

}
