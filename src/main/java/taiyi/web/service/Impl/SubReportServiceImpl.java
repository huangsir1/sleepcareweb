/**
 * 
 */
package taiyi.web.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import taiyi.web.dao.SubReportMapper;
import taiyi.web.model.SubReport;
import taiyi.web.service.SubReportService;

/**
 * @author <a href="mailto:jason19659@163.com">jason19659</a>
 *
 * taiyi.web.service.Impl
 *
 * 2016年4月12日
 */
@Service
@Transactional
public class SubReportServiceImpl implements SubReportService {

	@Autowired
	private SubReportMapper subReportMapper;
	/* 
	 * @see taiyi.web.service.SubReportService#deleteByPrimaryKey(java.lang.String)
	 */
	@Override
	public int deleteByPrimaryKey(String id) {
		return subReportMapper.deleteByPrimaryKey(id);
	}

	/* 
	 * @see taiyi.web.service.SubReportService#insert(taiyi.web.model.SubReport)
	 */
	@Override
	public int insert(SubReport record) {
		return subReportMapper.insert(record);
	}

	/* 
	 * @see taiyi.web.service.SubReportService#insertSelective(taiyi.web.model.SubReport)
	 */
	@Override
	public int insertSelective(SubReport record) {
		return subReportMapper.insertSelective(record);
	}

	/* 
	 * @see taiyi.web.service.SubReportService#selectByPrimaryKey(java.lang.String)
	 */
	@Override
	public SubReport selectByPrimaryKey(String id) {
		return subReportMapper.selectByPrimaryKey(id);
	}

	/* 
	 * @see taiyi.web.service.SubReportService#updateByPrimaryKeySelective(taiyi.web.model.SubReport)
	 */
	@Override
	public int updateByPrimaryKeySelective(SubReport record) {
		return subReportMapper.updateByPrimaryKeySelective(record);
	}

	/* 
	 * @see taiyi.web.service.SubReportService#updateByPrimaryKey(taiyi.web.model.SubReport)
	 */
	@Override
	public int updateByPrimaryKey(SubReport record) {
		return subReportMapper.updateByPrimaryKey(record);
	}

}
