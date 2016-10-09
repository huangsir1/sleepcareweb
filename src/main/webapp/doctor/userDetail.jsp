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
<title>用户详情</title>



</head>

<script language="JavaScript" type="text/javascript"
	src="<%=basePath%>js/jquery.min.js" charset="utf-8"></script>
<!--(指定编码方式，防止出现乱码)引入EasyUI中使用的Jquery版本-->
<script language="JavaScript" type="text/javascript"
	src="<%=basePath%>js/jquery.easyui.min.js" charset="utf-8"></script>
<!--(指定编码方式，防止出现乱码)引入EasyUi文件-->

<link rel="stylesheet" type="text/css" href="<%=basePath%>css/easyui.css">
<!--引入CSS样式-->
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/icon.css">
<!--Icon引入-->

<script language="JavaScript" type="text/javascript"
	src="<%=basePath%>js/easyui-lang-zh_CN.js"></script>
<script language="JavaScript" type="text/javascript"
	src="<%=basePath%>js/util.js"></script>
<script type="text/javascript">
	var url = 
	function jump() {
		var row = $('#dg').datagrid('getSelected');
	if (row){
		 window.location.href = "<%=basePath%>dmin/doctor/showReport/" + row.id; 
		
	}

	}
	$(function(){
		$('#dg').datagrid({
			url: '<%=basePath%>admin/getReportByUserId/'+ getQueryString("id"),
			onDblClickRow: function (rowIndex, rowData) {
				window.location.href="<%=basePath%>admin/doctor/showReport/" + rowData.id; 
			}
		});
	});
	function del(){
		var row = $('#dg').datagrid('getSelected'); 
		if (row){
			$.messager.confirm('确认','真的要删除该报告嘛?',function(r){
				if (r){
					$.post('<%=basePath%>admin/deleteReport', {
						id : row.id
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
<!--汉化-->

<script> 
	
</script>
<body>
	<table id="dg" title="报告概览(双击查看报告)" class="easyui-datagrid"
		style="width: 650px;"
		toolbar="#toolbar" rownumbers="true" fitColumns="true"
		singleSelect="true">
		<thead>
			<tr>
				<th  field="id">id</th>
				<th field="startTime">开始时间</th>
				<th field="endTime">结束时间</th> 
				<th field="uploadDate">上传日期</th>
				  
			</tr>
		</thead>
	</table>  
	<div id="toolbar">
		<a href="#" class="easyui-linkbutton" plain="true"
			onclick="jump()">查看报告</a>  
		<!--  <a href="#" class="easyui-linkbutton" iconCls="icon-remove"  plain="true"
			onclick="del()">删除报告</a>   -->
		<a href="#" class="easyui-linkbutton"
			iconCls="icon-back" plain="true" onclick="history.go(-1)">返回</a>
	</div>



</body>
</html>