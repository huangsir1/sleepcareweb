/**
 * 
 */
package taiyi.web.service.Impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import taiyi.web.dao.SleepReportMapper;
import taiyi.web.model.SleepReport;
import taiyi.web.service.SleepReportService;

/**
 * @author <a href="mailto:jason19659@163.com">jason19659</a>
 *
 * taiyi.web.service.Impl
 *
 * 2016年3月9日
 */
@Service
@Transactional
public class SleepReportServiceImpl implements SleepReportService{
	
	@Autowired
	private SleepReportMapper sleepReportMapper;

	/* 
	 * @see taiyi.web.service.SleepReportService#deleteByPrimaryKey(java.lang.String)
	 */
	@Override
	public int deleteByPrimaryKey(String id) {
		return sleepReportMapper.deleteByPrimaryKey(id);
	}

	/* 
	 * @see taiyi.web.service.SleepReportService#insert(taiyi.web.model.SleepReport)
	 */
	@Override
	public int insert(SleepReport record) {
		return sleepReportMapper.insert(record);
	}

	/* 
	 * @see taiyi.web.service.SleepReportService#insertSelective(taiyi.web.model.SleepReport)
	 */
	@Override
	public int insertSelective(SleepReport record) {
		return sleepReportMapper.insertSelective(record);
	}

	/* 
	 * @see taiyi.web.service.SleepReportService#selectByPrimaryKey(java.lang.String)
	 */
	@Override
	public SleepReport selectByPrimaryKey(String id) {
		return sleepReportMapper.selectByPrimaryKey(id);
	}

	/* 
	 * @see taiyi.web.service.SleepReportService#updateByPrimaryKeySelective(taiyi.web.model.SleepReport)
	 */
	@Override
	public int updateByPrimaryKeySelective(SleepReport record) {
		return sleepReportMapper.updateByPrimaryKeySelective(record);
	}

	/* 
	 * @see taiyi.web.service.SleepReportService#updateByPrimaryKey(taiyi.web.model.SleepReport)
	 */
	@Override
	public int updateByPrimaryKey(SleepReport record) {
		return sleepReportMapper.updateByPrimaryKey(record);
	}

	/* 
	 * @see taiyi.web.service.SleepReportService#selectByUserId(java.lang.String)
	 */
	@Override
	public List<SleepReport> selectByUserId(String userId) {
		return sleepReportMapper.selectByUserId(userId);
	}

	/* 
	 * @see taiyi.web.service.SleepReportService#selectByHostipalIdAndUserId(java.lang.String, java.lang.String)
	 */
	@Override
	public List<SleepReport> selectByHostipalIdAndUserId(String userId, Integer hostipalId) {
		HashMap<String, String> map = new HashMap<String,String>();
		map.put("userId", userId);
		map.put("hostipalId", ""+hostipalId);
		return sleepReportMapper.selectByHostipalIdAndUserId(map);
	}
	
	

}
