/**
 * 
 */
package taiyi.web.service;

import java.util.List;

import taiyi.web.model.News;

/**
 * @author <a href="mailto:jason19659@163.com">jason19659</a>
 *
 * taiyi.web.service
 *
 * 2016年3月30日
 */
public interface NewsService {
	  int deleteByPrimaryKey(Integer id);

	    int insert(News record);

	    int insertSelective(News record);

	    News selectByPrimaryKey(Integer id);

	    int updateByPrimaryKeySelective(News record);

	    int updateByPrimaryKeyWithBLOBs(News record);

	    int updateByPrimaryKey(News record);

		List<News> selectAll();
}
