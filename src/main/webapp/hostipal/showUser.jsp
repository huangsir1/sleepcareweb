<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>用户查看</title>



</head>
<script language="JavaScript" type="text/javascript"
	src="<%=basePath%>js/jquery.min.js" charset="utf-8"></script>
<!--(指定编码方式，防止出现乱码)引入EasyUI中使用的Jquery版本-->
<script language="JavaScript" type="text/javascript"
	src="<%=basePath%>js/jquery.easyui.min.js" charset="utf-8"></script>
<!--(指定编码方式，防止出现乱码)引入EasyUi文件-->

<link rel="stylesheet" type="text/css"
	href="<%=basePath%>css/easyui.css">
<!--引入CSS样式-->
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/icon.css">
<!--Icon引入-->

<script language="JavaScript" type="text/javascript"
	src="<%=basePath%>js/easyui-lang-zh_CN.js"></script>
<script type="text/javascript">
	function jump() {
		var row = $('#dg').datagrid('getSelected');
		if (row) {
			window.location.href = "<%=basePath%>hostipal/userDetail.jsp?id=" + row.id;
		}
 
	}
	 
	$(function(){
		$('#dg').datagrid({
			url: '<%=basePath%>admin/getHostipalUsers',
			pageSize:20,
			onDblClickRow: function (rowIndex, rowData) {
				window.location.href = "<%=basePath%>hostipal/userDetail.jsp?id=" + rowData.id
			}
		})
	});
	
	function destroyUser(){
		var row = $('#dg').datagrid('getSelected');
		if (row){
			$.messager.confirm('确认','真的要删除该用户嘛?',function(r){
				if (r){
					$.post('<%=basePath%>admin/deleteUser', {
						userId : row.id
					}, function(result) {
						$('#dg').datagrid('reload'); // reload the user data
						var title = "";
						if (result.code == 2000) {
							title = "成功"
						} else {
							title = "失败"
						}
						$.messager.show({ // show error message
							title : title,
							msg : result.message
						});
					}, 'json');
				}
			});
		}
	}

	function newUser(){
		$('#dlg').dialog('open').dialog('setTitle','增加用户');
		$('#fm').form('clear');
	}
	
	function saveUser(){
		$('#fm').form('submit',{
			url: '<%=basePath%>admin/saveUser?' + Math.random(),
			onSubmit : function() {
				return $(this).form('validate');
			},
			success : function(result) {
				var result = eval('(' + result + ')');
				if (result.code != 2000) {
					$.messager.show({
						title : 'Error',
						msg : result.message
					});
				} else {
					$('#dlg').dialog('close'); // close the dialog
					$('#dg').datagrid('reload'); // reload the user data
					$.messager.show({ // show error message
						title : "成功",
						msg : "成功"
					});
				}
			}
		});
	}
</script>

<script>
	
</script>
<body>
	<table id="dg" title="用户查看(双击查看详情)" class="easyui-datagrid"
		style="" toolbar="#toolbar" rownumbers="true"
		fitColumns="true" singleSelect="true" rownumbers="true"
		pagination="true">
		<thead>
			<tr>
				<th hidden="true" field="id">id</th>
				<th field="name">姓名</th>
				<th field="birthday">生日</th>
				<th field="weight">体重(kg)</th>
				<th field="height">身高(cm)</th>
				<th field="essRank">ess评分</th>
				<th field="gender">性别</th>
				<th field="phone">电话</th>
				<th field="address">地址</th>
			</tr>
		</thead>
	</table>
	<div id="toolbar">
		<a href="#" class="easyui-linkbutton" plain="true" onclick="jump()">查看详情</a>
		<!--  <a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true"
			onclick="newUser()">增加用户</a> -->
			<!--  <a href="#" class="easyui-linkbutton"
			iconCls="icon-remove" plain="true" onclick="destroyUser()">删除</a>-->
		<a href="#" class="easyui-linkbutton"
			iconCls="icon-back" plain="true" onclick="history.go(-1)">返回</a>
		<!--   <br /> <span>姓名:</span>
		<input id="name" style="width: 50px; border: 1px solid #ccc">
		<span>体重:</span> <input id="weight"
			style="width: 30px; border: 1px solid #ccc"> <span>身高:</span>
		<input id="height" style="width: 30px; border: 1px solid #ccc">
		<span>性别:</span> <input id="gender"
			style="width: 30px; border: 1px solid #ccc"> <span>电话:</span>
		<input id="phone" style="width: 120px; border: 1px solid #ccc">
		<a href="#" class="easyui-linkbutton" plain="true"
			onclick="doSearch()">搜索</a>-->
	</div>





</body>
</html>