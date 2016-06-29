/**
 * 
 */
package taiyi.web.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import taiyi.web.model.News;
import taiyi.web.service.NewsService;

/**
 * @author <a href="mailto:jason19659@163.com">jason19659</a>
 *
 * taiyi.web.controller.api
 *
 * 2016年3月30日
 */
@Controller
@RequestMapping("/api/news")
public class NewsAPIController extends APIExceptionHandlerController{
	@Autowired
	private NewsService newsService;
	
	@RequestMapping("/getAll")
	@ResponseBody
	public List<News>getAll() {
		return newsService.selectAll();
	}
}
