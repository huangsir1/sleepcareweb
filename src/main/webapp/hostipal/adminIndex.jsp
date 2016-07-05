<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head id="Head1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>钛铱Taiir睡眠监测系统</title>
<!--  link href="<%=basePath%>css/default.css" rel="stylesheet"
	type="text/css" />-->
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>css/easyui.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/icon.css" />
<style>
* {
	font-size: 12px;
	font-family: Tahoma, Verdana, 微软雅黑, 新宋体
}

body {
	background: #D2E0F2;
}

a {
	color: Black;
	text-decoration: none;
}

a:hover {
	color: Red;
	text-decoration: underline;
}

.textbox03 {
	border: #878787 1px solid;
	padding: 4px 3px;
	font: Verdana, Geneva, sans-serif, 宋体;
	line-height: 14px;
	background-color: #fff;
	height: auto;
	font-size: 14px;
	font-weight: bold;
	width: 190px;
}

.txt01 {
	font: Verdana, Geneva, sans-serif, 宋体;
	padding: 3px 2px 2px 2px;
	border-width: 1px;
	border-color: #ddd;
	color: #000;
}

.txt {
	border: #878787 1px solid;
	padding: 4px 3px;
	font: Verdana, Geneva, sans-serif, 宋体;
	line-height: 14px;
	background-color: #fff;
	height: auto;
	font-size: 14px;
}

.footer {
	text-align: center;
	color: #4C4C4C;
	margin: 0px;
	padding: 0px;
	line-height: 23px;
	font-weight: bold;
}

.head a {
	color: White;
	text-decoration: underline;
}

.easyui-accordion ul {
	list-style-type: none;
	margin: 0px;
	padding: 10px;
}

.easyui-accordion ul li {
	padding: 0px;
}

.easyui-accordion ul li a {
	line-height: 24px;
}

.easyui-accordion ul li div {
	margin: 2px 0px;
	padding-left: 10px;
	padding-top: 2px;
}

.easyui-accordion ul li div.hover {
	border: 1px dashed #99BBE8;
	background: #E0ECFF;
	cursor: pointer;
}

.easyui-accordion ul li div.hover a {
	color: #416AA3;
}

.easyui-accordion ul li div.selected {
	border: 1px solid #99BBE8;
	background: #E0ECFF;
	cursor: default;
}

.easyui-accordion ul li div.selected a {
	color: #416AA3;
	font-weight: bold;
}

.icon {
	background: url(<%=basePath%>css/images/tabicons.png) no-repeat;
	width: 18px;
	line-height: 18px;
	display: inline-block;
}

.icon-sys {
	background-position: 0px -200px;
}

.icon-set {
	background-position: -380px -200px;
}

.icon-add {
	background-position: -20px 0px;
}

.icon-add1 {
	background: url('<%=basePath%>css/icons/edit_add.png') no-repeat;
}

.icon-nav {
	background-position: -100px -20px;
}

.icon-users {
	background-position: -100px -480px;
}

.icon-role {
	background-position: -360px -200px;
}

.icon-set {
	background-position: -380px -200px;
}

.icon-log {
	background-position: -380px -80px;
}

.icon-delete16 {
	background: url('icon/delete.gif') no-repeat;
	width: 18px;
	line-height: 18px;
	display: inline-block;
}

.icon-delete {
	background-position: -140px -120px;
}

.icon-edit {
	background-position: -380px -320px;
}

.icon-magic {
	background-position: 0px -500px;
}

.icon-database {
	background-position: -20px -140px;
}

.icon-expand {
	background: url('<%=basePath%>css/images/coll2.gif') no-repeat;
}

.icon-collapse {
	background: url('<%=basePath%>css/images/coll3.gif') no-repeat;
}
</style>
<script type="text/javascript" src="<%=basePath%>js/jquery.min.js"></script>
<script type="text/javascript"
	src="<%=basePath%>js/jquery.easyui.min.js"></script>
<script type="text/javascript" src='<%=basePath%>js/outlook.js'>
	
</script>

<script type="text/javascript">
	var _menus = {
		basic : [ {
			"menuid" : "10", 
			"icon" : "icon-sys",
			"menuname" : "管理",
			"menus" : [ {
				"menuid" : "111",
				"menuname" : "查看报告",
				"icon" : "icon-nav",
				"url" : "<%=basePath%>admin/showHostipalUser" 
			}, {
				"menuid" : "113",
				"menuname" : "搜索用户",
				"icon" : "icon-nav",
				"url" : "<%=basePath%>admin/searchHostipalUser"
			}, 
			{
				"menuid" : "116",
				"menuname" : "修改密码", 
				"icon" : "icon-nav",
				"url" : "<%=basePath%>admin/changePasswordUI"
			}]  
		}]
	};
 
	//关闭登录窗口
	function closePwd() {
		$('#w').window('close');
	}

	

</script>
<style>
#css3menu li {
	float: left;
	list-style-type: none;
}

#css3menu li a {
	color: #fff;
	padding-right: 20px;
}

#css3menu li a.active {
	color: yellow;
}
</style>
</head>
<body class="easyui-layout" style="overflow-y: hidden" scroll="no">
	<noscript>
		<div
			style="position: absolute; z-index: 100000; height: 2046px; top: 0px; left: 0px; width: 100%; background: white; text-align: center;">
			<img src="images/noscript.gif" alt='抱歉，请开启javascript！' />
		</div> 
	</noscript>
	<div region="north" split="true" border="false"
		style="overflow: hidden; height: 30px;
    	        background: url(<%=basePath%>css/images/layout-browser-hd-bg.gif) #3fb4e7 repeat-x center 50%;
    	        line-height: 20px;color: #fff; font-family: Verdana, 微软雅黑,黑体">
		<span style="padding-left: 10px; font-size: 16px; float: left;"><img
			src="<%=basePath%>css/images/icon.png" width="20" height="20"
			align="absmiddle" />钛铱Taiir睡眠监测系统</span>
		<ul id="css3menu" 
			style="padding: 0px; margin: 0px; list-type: none; float: left; margin-left: 40px;">
			<li><a class="active" name="basic" href="javascript:;"
				title=""></a></li>

		</ul>
	</div>  
	<div region="south" split="true"
		style="height: 30px; background: #3fb4e7;s">
		<span><a style="padding-left: 10px;font-size: 16px;float: left;color: white;" href="<%=basePath%>logout">注销</a></span>
		<div class="footer"></div> 
		
	</div>
	<div region="west" hide="true" split="true" title="菜单"
		style="width: 180px;" id="west">
		<div id='wnav' class="easyui-accordion" fit="true" border="false">
			<!--  导航内容 -->

		</div>

	</div>
	<div id="mainPanle" region="center" 
		style="background: #eee; overflow-y: hidden">
		<div id="tabs" class="easyui-tabs" fit="true" border="false"> 
			<div title="欢迎使用" style="padding: 20px; overflow: hidden;" id="home">

				<h1>欢迎 ${trueName }</h1>

			</div>
		</div>
	</div>


	
	<div id="mm" class="easyui-menu" style="width: 150px;">
		<div id="mm-tabupdate">刷新</div>
		<div class="menu-sep"></div>
		<div id="mm-tabclose">关闭</div>
		<div id="mm-tabcloseall">全部关闭</div>
		<div id="mm-tabcloseother">除此之外全部关闭</div>
		<div class="menu-sep"></div>
		<div id="mm-tabcloseright">当前页右侧全部关闭</div>
		<div id="mm-tabcloseleft">当前页左侧全部关闭</div>
		<div class="menu-sep"></div>
		<div id="mm-exit">退出</div>
	</div>


</body>
</html>