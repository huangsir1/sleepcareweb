/**
 * 
 */
package taiyi.web.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import taiyi.web.dao.NewsMapper;
import taiyi.web.model.News;
import taiyi.web.service.NewsService;

/**
 * @author <a href="mailto:jason19659@163.com">jason19659</a>
 *
 * taiyi.web.service.Impl
 *
 * 2016年3月30日
 */
@Service
@Transactional
public class NewsServiceImpl implements NewsService {
	@Autowired
	private NewsMapper newsMapper;

	/* 
	 * @see taiyi.web.service.NewsService#deleteByPrimaryKey(java.lang.String)
	 */
	@Override
	public int deleteByPrimaryKey(Integer id) {
		return newsMapper.deleteByPrimaryKey(id);
	}

	/* 
	 * @see taiyi.web.service.NewsService#insert(taiyi.web.model.News)
	 */
	@Override
	public int insert(News record) {
		return newsMapper.insert(record);
	}

	/* 
	 * @see taiyi.web.service.NewsService#insertSelective(taiyi.web.model.News)
	 */
	@Override
	public int insertSelective(News record) {
		return newsMapper.insertSelective(record);
	}

	/* 
	 * @see taiyi.web.service.NewsService#selectByPrimaryKey(java.lang.String)
	 */
	@Override
	public News selectByPrimaryKey(Integer id) {
		return newsMapper.selectByPrimaryKey(id);
	}

	/* 
	 * @see taiyi.web.service.NewsService#updateByPrimaryKeySelective(taiyi.web.model.News)
	 */
	@Override
	public int updateByPrimaryKeySelective(News record) {
		return newsMapper.updateByPrimaryKeySelective(record);
	}

	/* 
	 * @see taiyi.web.service.NewsService#updateByPrimaryKey(taiyi.web.model.News)
	 */
	@Override
	public int updateByPrimaryKey(News record) {
		return newsMapper.updateByPrimaryKey(record);
	}

	/* 
	 * @see taiyi.web.service.NewsService#updateByPrimaryKeyWithBLOBs(taiyi.web.model.News)
	 */
	@Override
	public int updateByPrimaryKeyWithBLOBs(News record) {
		return newsMapper.updateByPrimaryKeyWithBLOBs(record);
	}

	/* 
	 * @see taiyi.web.service.NewsService#selectAll()
	 */
	@Override
	public List<News> selectAll() {
		return newsMapper.selectAll();
	}

}
