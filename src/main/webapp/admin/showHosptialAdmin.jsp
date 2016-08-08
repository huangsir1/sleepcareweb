<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	pageContext.setAttribute("basePath", basePath);
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>用户查看</title>



</head>
<script language="JavaScript" type="text/javascript"
	src="${basePath}js/jquery.min.js" charset="utf-8"></script>
<!--(指定编码方式，防止出现乱码)引入EasyUI中使用的Jquery版本-->
<script language="JavaScript" type="text/javascript"
	src="${basePath}js/jquery.easyui.min.js" charset="utf-8"></script>
<!--(指定编码方式，防止出现乱码)引入EasyUi文件-->

<link rel="stylesheet" type="text/css" href="${basePath}css/easyui.css">
<!--引入CSS样式-->
<link rel="stylesheet" type="text/css" href="${basePath}css/icon.css">
<!--Icon引入-->

<script type="text/javascript" src="${basePath}js/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${basePath}js/util.js"></script>
<script type="text/javascript">
	function jump() {

	}

	$(function() {
		$('#dg').datagrid({
			url : '${basePath}admin/getHospitalAdmin/' + getQueryString("id"),
			pageSize : 20,
			onDblClickRow : function(rowIndex, rowData) {
				 jump() ;
			}
		})
	});

	function destroyUser() {
		var row = $('#dg').datagrid('getSelected');
		if (row) {
			$.messager.confirm('确认', '真的要删除该用户嘛?', function(r) {
				if (r) {
					$.post('${basePath}admin/deleteUser', {
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
</script>

<script>
	
</script>
<body>
	<table id="dg" title="用户查看(双击查看详情)" class="easyui-datagrid" style=""
		toolbar="#toolbar" rownumbers="true" fitColumns="true"
		singleSelect="true" rownumbers="true">
		<thead>
			<tr>
				<th hidden="true" field="id">id</th>
				<th field="name">名称</th>
				<th field="username">用户名</th>
				<th field="systemRoles">权限</th>
				<th field="isValid">是否有效</th>
			</tr>
		</thead>
	</table>
	<div id="toolbar">
	<a href="#" class="easyui-linkbutton" iconCls="icon-add"plain="true" onclick="jump()">增加</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-edit"plain="true" onclick="jump()">编辑</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true"
			onclick="">修改密码</a> <a href="#" class="easyui-linkbutton"
			iconCls="icon-remove" plain="true" onclick="">删除</a> <a
			href="#" class="easyui-linkbutton" iconCls="icon-back" plain="true"
			onclick="history.go(-1)">返回</a>
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