/**
 * 
 */
package taiyi.web.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import taiyi.web.dao.BreatheReportMapper;
import taiyi.web.model.BreatheReport;
import taiyi.web.service.BreatheReportService;

/**
 * @author <a href="mailto:jason19659@163.com">jason19659</a>
 *
 * taiyi.web.service.Impl
 *
 * 2016年3月9日
 */
@Service
@Transactional
public class BreatheReportServiceImpl implements BreatheReportService{
	@Autowired
	private BreatheReportMapper breatheReportMapper;
	

	/* 
	 * @see taiyi.web.service.BreatheReportService#deleteByPrimaryKey(java.lang.String)
	 */
	@Override
	public int deleteByPrimaryKey(String id) {
		return breatheReportMapper.deleteByPrimaryKey(id);
	}

	/* 
	 * @see taiyi.web.service.BreatheReportService#insert(taiyi.web.model.BreatheReport)
	 */
	@Override
	public int insert(BreatheReport record) {
		return breatheReportMapper.insert(record);
	}

	/* 
	 * @see taiyi.web.service.BreatheReportService#insertSelective(taiyi.web.model.BreatheReport)
	 */
	@Override
	public int insertSelective(BreatheReport record) {
		return breatheReportMapper.insertSelective(record);
	}

	/* 
	 * @see taiyi.web.service.BreatheReportService#selectByPrimaryKey(java.lang.String)
	 */
	@Override
	public BreatheReport selectByPrimaryKey(String id) {
		return breatheReportMapper.selectByPrimaryKey(id);
	}

	/* 
	 * @see taiyi.web.service.BreatheReportService#updateByPrimaryKeySelective(taiyi.web.model.BreatheReport)
	 */
	@Override
	public int updateByPrimaryKeySelective(BreatheReport record) {
		return breatheReportMapper.updateByPrimaryKeySelective(record);
	}

	/* 
	 * @see taiyi.web.service.BreatheReportService#updateByPrimaryKey(taiyi.web.model.BreatheReport)
	 */
	@Override
	public int updateByPrimaryKey(BreatheReport record) {
		return breatheReportMapper.updateByPrimaryKey(record);
	}

}
