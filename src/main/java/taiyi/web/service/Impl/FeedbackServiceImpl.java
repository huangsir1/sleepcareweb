/**
 * 
 */
package taiyi.web.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import taiyi.web.dao.FeedbackMapper;
import taiyi.web.model.Feedback;
import taiyi.web.service.FeedbackService;

/**
 * @author <a href="mailto:jason19659@163.com">jason19659</a>
 *
 * taiyi.web.service.Impl
 *
 * 2016年3月30日
 */
@Service
@Transactional
public class FeedbackServiceImpl implements FeedbackService {
	@Autowired
	private FeedbackMapper feedbackMapper;

	/* 
	 * @see taiyi.web.service.FeedbackService#deleteByPrimaryKey(java.lang.String)
	 */
	@Override
	public int deleteByPrimaryKey(Integer id) {
		return feedbackMapper.deleteByPrimaryKey(id);
	}

	/* 
	 * @see taiyi.web.service.FeedbackService#insert(taiyi.web.model.Feedback)
	 */
	@Override
	public int insert(Feedback record) {
		return feedbackMapper.insert(record);
	}

	/* 
	 * @see taiyi.web.service.FeedbackService#insertSelective(taiyi.web.model.Feedback)
	 */
	@Override
	public int insertSelective(Feedback record) {
		return feedbackMapper.insertSelective(record);
	}

	/* 
	 * @see taiyi.web.service.FeedbackService#selectByPrimaryKey(java.lang.String)
	 */
	@Override
	public Feedback selectByPrimaryKey(Integer id) {
		return feedbackMapper.selectByPrimaryKey(id);
	}

	/* 
	 * @see taiyi.web.service.FeedbackService#updateByPrimaryKeySelective(taiyi.web.model.Feedback)
	 */
	@Override
	public int updateByPrimaryKeySelective(Feedback record) {
		return feedbackMapper.updateByPrimaryKeySelective(record);
	}

	/* 
	 * @see taiyi.web.service.FeedbackService#updateByPrimaryKey(taiyi.web.model.Feedback)
	 */
	@Override
	public int updateByPrimaryKey(Feedback record) {
		return feedbackMapper.updateByPrimaryKey(record);
	}

}
