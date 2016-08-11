/**
 * 
 */
package taiyi.web.model.dto;

/**
 * @author <a href="mailto:jason19659@163.com">jason19659</a>
 *
 *         taiyi.web.model.dto
 *
 *         2016年3月9日
 */

public class Status implements Comparable<Status>{
	private static Status status;
	public final static int SUCCESSED_CODE = 2000;
	public final static int SUCCESSED_UPLOAD_REPORT_CDOE = 2001;
	public final static int SUCCESSED_UPLOAD_REPORT_AND_GENERATE_IT_CODE = 2002;
	public final static int SUCCESSED_UPLOAD_FILE_CODE = 2003;
	public static final int SUCCESSED_UPLOAD_FILE__AND_GENERATE_IT_CODE = 2004;
	public static final int SUCCESSED_REPORT_NOT_EXIST_BUT_GENERATE_IT_CODE = 2005;
	public static final int SUCCESSED_RECOVER_REPORT_CDOE = 2006;
	
	public final static int FAILED_CODE = 5000;
	public final static int USER_UNREGISTER_CODE = 5001;
	public final static int USER_EXIST_CODE = 5002;
	public final static int PARAM_ERROR_CODE = 5003;
	public final static int CANNOT_DELETE_USER_CODE = 5004;
	public final static int CANNOT_DELETE_WITHOUT_DELETE_ALL_SUBITEM_CODE = 5005;
	public final static int VALIDATECODE_ERROR_CODE = 5100;
	public final static int USER_REGISTER_SUCCESS_CODE = 5200;
	
	public final static int REPORT_EXSIT_CODE = 6001;
	public final static int REPORT_CONDITION_NOT_EXIST_CODE = 6002;
	public final static int CANNOT_DELETE_REPORT_CODE = 6004;
	public static final int REPORT_NOT_EXSIT_CODE = 6003;
	
	public static final int PASSWORD_NOT_MATCH_CODE = 7000;
	public static final int PASSWORD_SAME_CODE = 7001;
	
	

	public final static Status SUCCESSED = new Status(SUCCESSED_CODE, "成功");
	public final static Status FAILED = new Status(FAILED_CODE, "未知错误");

	public final static Status USER_UNREGISTER = new Status(USER_UNREGISTER_CODE, "用户未注册");
	public final static Status USER_EXIST = new Status(USER_EXIST_CODE, "用户已存在");
	public final static Status PARAM_ERROR = new Status(PARAM_ERROR_CODE, "参数错误");
	public final static Status USER_REGISTER_SUCCESS = new Status(USER_REGISTER_SUCCESS_CODE, "用户注册成功");
	public final static Status REPORT_EXSIT = new Status(REPORT_EXSIT_CODE, "报告已存在");
	
	public final static Status REPORT_NOT_EXSIT = new Status(REPORT_NOT_EXSIT_CODE, "报告不存在");
	
	public final static Status CANNOT_DELETE_REPORT = new Status(CANNOT_DELETE_REPORT_CODE, "无法删除报告");
	public final static Status CANNOT_DELETE_USER = new Status(CANNOT_DELETE_USER_CODE, "无法删除用户");
	public final static Status VALIDATECODE_ERROR = new Status(VALIDATECODE_ERROR_CODE, "验证码错误");
	public final static Status FILE_UPLOAD_SUCCESSED = new Status(SUCCESSED_UPLOAD_FILE_CODE, "文件上传成功");
	public static final Status FAILD_TO_MKDIR = new Status(FAILED_CODE, "创建文件夹失败");
	public final static Status SUCCESSED_UPLOAD_REPORT = new Status(SUCCESSED_UPLOAD_REPORT_CDOE, "报告上传成功");
	public final static Status SUCCESSED_RECOVER_REPORT = new Status(SUCCESSED_RECOVER_REPORT_CDOE, "报告覆盖成功");
	public final static Status SUCCESSED_UPLOAD_REPORT_AND_GENERATE_IT = new Status(
			SUCCESSED_UPLOAD_REPORT_AND_GENERATE_IT_CODE, "报告上传成功，并且已经开始生成报告");
	public final static Status FILE_UPLOAD_SUCCESSED_AND_GENERATE_IT = new Status(
			SUCCESSED_UPLOAD_FILE__AND_GENERATE_IT_CODE, "文件上传成功，并且已经开始生成报告");
	public final static Status REPORT_CONDITION_NOT_EXIST = new Status(
			REPORT_CONDITION_NOT_EXIST_CODE, "生成报告的条件不齐全");
	public final static Status SUCCESSED_REPORT_NOT_EXIST_BUT_GENERATE_IT = new Status(
			SUCCESSED_REPORT_NOT_EXIST_BUT_GENERATE_IT_CODE, "请求显示报告，但是报告文件不存在，正在生成");
	
	public static final Status PASSWORD_NOT_MATCH = new Status(PASSWORD_NOT_MATCH_CODE,"密码不匹配");

	public static final Status PASSWORD_SAME =  new Status(PASSWORD_SAME_CODE,"新密码与原密码相同");
	public static final Status CANNOT_DELETE_WITHOUT_DELETE_ALL_SUBITEM =  new Status(CANNOT_DELETE_WITHOUT_DELETE_ALL_SUBITEM_CODE,"在该条目的所有子条目删除之前，无法删除该条目。");
	
	
	private int code = FAILED_CODE;
	private String message = "no info";
	
	public synchronized static Status getSuccess() {
		if (status == null) {
			status = Status.SUCCESSED;
		} else {
			status.setCode(Status.SUCCESSED_CODE);
			status.setMessage("成功");
		}
		return status;
	}
	
	public synchronized static Status getSuccess(String msg) {
		if (status == null) {
			status = Status.SUCCESSED;
		}
		status.setCode(Status.SUCCESSED_CODE);
		status.setMessage(msg);
		return status;
	}
	
	public synchronized static Status getFailed(String msg) {
		if (status == null) {
			status = Status.FAILED;
		}
		status.setCode(Status.FAILED_CODE);
		status.setMessage(msg);
		return status;
	}
	
	public synchronized static Status getFailed() {
		if (status == null) {
			status = Status.FAILED;
		} else {
			status.setCode(Status.FAILED_CODE);
			status.setMessage("失败");
		}
		return status;
	}

	public Status() {

	}
	
	public Status(int code) {
		this.code = code;
	}

	public Status(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	/*
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Status [message=" + message + ", code=" + code + "]";
	}
	
	public static boolean isSuccess(int statusCode) {
		if (statusCode >= 2000 && statusCode < 3000) {
			return true;
		}
		return false;
	}
	
	public static boolean isFailed(int statusCode) {
		return !isSuccess(statusCode);
	}
	
	public static boolean isFailed(Status status) {
		return !isSuccess(status);
	}

	public static boolean isSuccess(Status status) {
		return isSuccess(status.getCode());
	}

	/* 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Status o) {
		if (code == o.getCode()) {
			return 0;
		} else if(code < o.getCode()){
			return -1;
		}
		return 1;
	}

}
