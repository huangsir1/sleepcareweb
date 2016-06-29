<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%
    	String inputA = request.getParameter("wordA");
    String inputB = request.getParameter("wordB");
    	String result = "";
    	if(inputA != null && inputB != null){
    		result = inputA.equals(inputB) ? "字符串相等" : inputA.length() >= inputB.length() ?  inputA : inputB;
    	}
    %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1><%=result %></h1><br />
	<form action="test2.jsp" method="post">
		a:<input type="text" name="wordA"><br />
		b:<input type="text" name="wordB"><br />
		<input type="submit">
	</form>
</body>
</html>