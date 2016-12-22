<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	pageContext.setAttribute("basePath", basePath);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>钛铱Taiir睡眠监测系统 管理员登陆</title>
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="this is my page">
<script>
function getTopWinow(){  
   var p = window;    
   while(p != p.parent){  
       p = p.parent;   
   }  
   return p;  
}
</script> 

<script>
function reload(){  
	//调用：
	console.info(window);
	console.info(top);
	console.info(top == window)
	var top = getTopWinow(); //获取当前页面的顶层窗口对象
	if(top != window) {
		 top.location.href = '${basePath}admin/login'; //跳转到登陆页面 
	}
}

window.onload=function(){
	reload();
}
</script> 
<style type="text/css">
body {
	margin: 0;
	padding: 0;
	background: #38b4ea
}

.bottom {
	width: 1400px;
	height: 138px;
	background: url(${basePath}images/login-background.png);
	position: absolute;
	top: 50%;
	left: 50%;
	margin: -69px 0 0 -700px
}

.taiir {
	position: absolute;
	width: 380px;
	height: 317px;
	top: 50%;
	left: 50%;
	margin: -159px 0 0 -190px
}

.logo {
	position: relative;
	width: 120;
	height: 30;
	left: 250;
	top: 0;
	z-index: 10;
}

.title {
	color: ffffff;
	position: absolute;
	left: 110;
	top: 15;
	z-index: 5;
	position: absolute;
}

.main {
	position: relative;
	width: 380;
	height: 287;
	z-index: 8;
}

.form {
	position: absolute;
	left: 40;
	top: 120;
	z-index: 10;
	position: absolute;
}

.div-username {
	position: relative;
}

.div-password {
	position: relative;
}

.icon-user {
	position: absolute;
	left: 14;
	z-index: 10;
	top: 10;
	background-image: url(${basePath}images/login-user01.png);
	background-repeat: no-repeat;
	background-position: 0px 0px;
	width: 20px;
	height: 20px;
}

.icon-pass {
	position: absolute;
	left: 14;
	z-index: 10;
	top: 10;
	background-image: url(${basePath}images/login-key01.png);
	background-repeat: no-repeat;
	background-position: 0px 0px;
	width: 20px;
	height: 20px;
}

.user, .pass  {
	padding-left: 34px;
	width: 300px;
	height: 40px
}
  
  
.submit:hover {
	background-color: #1ea8e4;
	cursor:pointer;
}
.submit:active {
	background-color:#0f98d3;
}  

.submit {
	background-color: 0b9cdb;
	border-radius: 8px;
	border: 0px;
	padding: 0px;
	color: ffffff;
	width: 300px;
	height: 40px;
}
</style>
</head>

<body>
	<div class="bottom"></div>
	<div class="taiir">
		<img class="logo" src="${basePath}images/taiir-logo.png"
			style="right: 0px; left: 0px; float: right;"> <img calss="main"
			src="${basePath}images/login-input.png">
		<h1 class="title"
			style="text-align: right; float: inherit; right: 0px; left: 0px;">钛铱睡眠监测系统</h1>
		<form class="form"
			action="${basePath}login"
			align="center" method="post">
			<div class="div-username">
				<i class="icon-user"></i> <input class="user" name="username"
					required="required" type="text" placeholder="请输入用户名">
			</div>
			<p></p>
			<div class="div-password">
				<i class="icon-pass"></i> <input class="pass" name="password"
					required="required" type="password" placeholder="请输入密码"> 
			</div>
			<p>
				<input type="submit" value="登      录" class="submit" >
			</p>
		</form>
		<h4 style="color: red;text-align: center;">${msg}</h4>
	</div>
</body>

</html>