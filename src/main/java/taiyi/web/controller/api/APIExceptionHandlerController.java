/**
 * 
 */
package taiyi.web.controller.api;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import taiyi.web.model.dto.Status;

/**
 * @author <a href="mailto:jason19659@163.com">jason19659</a>
 *
 * taiyi.web.controller.api
 *
 * 2016年3月11日
 */
@Controller
public class APIExceptionHandlerController  {
	/**
	 * api接口异常统一处理
	 * @param ex
	 * @param request
	 * @return
	 */
	@ExceptionHandler(Exception.class)  
	@ResponseBody
    public Status operateExp(Exception ex,HttpServletRequest request){  
        ex.printStackTrace();
        Status status = new Status(Status.FAILED_CODE,ex.getMessage());
        return status;  
    }  
	
	
}
