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
function add() {
	$('#dlg').dialog('open').dialog('setTitle','增加设备');
	$('#fm').form('clear');
}
	$(function(){
		$('#dg').datagrid({
			url: '<%=basePath%>admin/getDeviceByHostapilId/'+ getQueryString("id"),
		});
	});
	function del(){
		var row = $('#dg').datagrid('getSelected');
		if (row){
			$.messager.confirm('确认','真的要删除该报告嘛?',function(r){
				if (r){
					$.post('<%=basePath%>admin/deleteDevice', {
						macAddress : row.macAddress
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
	
	function saveDevice(){
		$('#fm').form('submit',{
			url: '<%=basePath%>admin/addDevice?random=' + Math.random(),
			onSubmit : function() {
				$('#hostipalId').attr("value",getQueryString("id"))
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
			},
			error: function(result) {
				$.messager.show({
					title : 'Error',
					msg : "失败"
				});
			}
		});
	}
</script>
<!--汉化-->

<script>
	
</script>
<body>
	<table id="dg" title="报告概览(双击查看报告)" class="easyui-datagrid" style=""
		toolbar="#toolbar" rownumbers="true" fitColumns="true"
		singleSelect="true">
		<thead>
			<tr>
				<th field="macAddress">mac地址</th>
				<th field="description">备注</th> 
				<th field="regDate">上传日期</th>
				<!--  <th field="macAddress" >mac地址</th>-->
			</tr>
		</thead>
	</table>
	<div id="toolbar">
		<a href="#" class="easyui-linkbutton" iconCls="icon-add"  plain="true"
			onclick="add()">增加设备</a> 
		<a href="#" class="easyui-linkbutton" iconCls="icon-remove"  plain="true"
			onclick="del()">删除设备</a> 
		<a href="#" class="easyui-linkbutton"
			iconCls="icon-back" plain="true" onclick="history.go(-1)">返回</a>
	</div>


<div id="dlg" class="easyui-dialog"
		style="width: 250px; height: 150px; padding: 10px 20px" closed="true"
		buttons="#dlg-buttons">
		<form id="fm" method="post">
			<div class="fitem">
				<label>mac地址:</label> <input name="macAddress">
			</div>
			<div class="fitem">
				<label>简介:</label> <input name="description">
			</div>
			 <input  id="hostipalId" type="hidden" name="hostipalId">
		</form>
	</div>
	<div id="dlg-buttons">
		<a href="#" class="easyui-linkbutton" iconCls="icon-ok"
			onclick="saveDevice()">保存</a> <a href="#" class="easyui-linkbutton"
			iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">取消</a>
	</div>
</body>
</html>