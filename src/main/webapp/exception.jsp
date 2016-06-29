<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title> . </title>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%> 
<script type="text/javascript">
/** 
 * 在页面中任何嵌套层次的窗口中获取顶层窗口 
 * @return 当前页面的顶层窗口对象 
 */
function getTopWinow(){  
   var p = window;    
   while(p != p.parent){  
       p = p.parent;   
   }  
   return p;  
}
</script>
<base href="<%=basePath%>">
</head>
 
<body> 
${pageScope.basePath} 
<script>
//调用：
console.info(window);
var top = getTopWinow(); //获取当前页面的顶层窗口对象
 top.location.href = ${pageScope.basePath}'admin/login'; //跳转到登陆页面
</script>
</body>
</html>