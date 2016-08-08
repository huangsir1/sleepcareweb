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
<title>医院查看</title>



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

<script language="JavaScript" type="text/javascript"
	src="${basePath}js/easyui-lang-zh_CN.js"></script>
<script type="text/javascript">
	var url;
	function showDevice() {
		var row = $('#dg').datagrid('getSelected');
		if (row) {
			window.location.href = "${basePath}admin/showDevice.jsp?id="
					+ row.id;
		}

	}
	
	function showAdmin() {
		var row = $('#dg').datagrid('getSelected');
		if (row) {
			window.location.href = "${basePath}admin/showHosptialAdmin.jsp?id="
					+ row.id;
		}

	}

	$(function() {
		$('#dg').datagrid({
			url : '${basePath}admin/getAllHospital',
			onDblClickRow : function(rowIndex, rowData) {
				showReport() ;
			},
			pageSize : 20
		})
	});

	function showReport() {
		var row = $('#dg').datagrid('getSelected');
		if (row) {
			window.location.href = "${basePath}admin/showHosptialUser.jsp?id="+ row.id;
		}

	}

	function destroyHospital() {
		var row = $('#dg').datagrid('getSelected');
		if (row) {
			$.messager.confirm('确认', '真的要删除该医院嘛?', function(r) {
				if (r) {
					$.post('${basePath}admin/deleteHospital', {
						hospitalId : row.id
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

	function addHospital() {
		$('#dlg').dialog('open').dialog('setTitle', '增加医院');
		$('#fm').form('clear');
		url = '${basePath}admin/saveHospital?' + Math.random();
	}

	function saveHospital() {
		$('#fm').form('submit', {
			url : url,
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

	function editHospital() {
		var row = $('#dg').datagrid('getSelected');
		if (row) {
			$('#dlg').dialog('open').dialog('setTitle', '修改医院');
			$('#fm').form('load', row);
			url = '${basePath}admin/editHospital?id=' + row.id + '&rnd='
					+ Math.random();
		}
	}
</script>

<script>
	
</script>
<body>
	<table id="dg" title="用户查看(双击查看详情)" class="easyui-datagrid" style=""
		toolbar="#toolbar" rownumbers="true" fitColumns="true"
		singleSelect="true" rownumbers="true" pagination="true">
		<thead>
			<tr>
				<th hidden="true" field="id">id</th>
				<th field="name">名称</th>
				<th field="phone">电话</th>
				<th field="address">地址</th>
				<th field="alias">别名</th>
				<th field="regDate">注册日期</th>
			</tr>
		</thead>
	</table>
	<div id="toolbar">
		<a href="#" class="easyui-linkbutton" iconcls="icon-tip" plain="true"
			onclick="showReport()">查看报告</a> 
			<a href="#" class="easyui-linkbutton" iconCls="icon-tip" plain="true" onclick="showDevice()">查看设备</a>
		    <a href="#" class="easyui-linkbutton" iconCls="icon-tip" plain="true" onclick="showAdmin()"></a>
			 <a
			href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true"
			onclick="editHospital()">修改医院</a> <a href="#"
			class="easyui-linkbutton" iconCls="icon-add" plain="true"
			onclick="addHospital()">增加医院</a> <a href="#"
			class="easyui-linkbutton" iconCls="icon-remove" plain="true"
			onclick="destroyHospital()">删除</a> <a href="#"
			class="easyui-linkbutton" iconCls="icon-back" plain="true"
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

	<div id="dlg" class="easyui-dialog"
		style="width: 250px; height: 250px; padding: 10px 20px" closed="true"
		buttons="#dlg-buttons">
		<div class="ftitle">增加医院</div>
		<br />
		<form id="fm" method="post">
			<div class="fitem">
				<label>名称:</label> <input name="name">
			</div>
			<br />
			<div class="fitem">
				<label>别名:</label> <input name="alias">
			</div>
			<br />
			<div class="fitem">
				<label>地址:</label> <input name="address">
			</div>
			<br />
			<div class="fitem">
				<label>电话:</label> <input name="phone">
			</div>
		</form>
	</div>
	<div id="dlg-buttons">
		<a href="#" class="easyui-linkbutton" iconCls="icon-ok"
			onclick="saveHospital()">保存</a> <a href="#" class="easyui-linkbutton"
			iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">取消</a>
	</div>




</body>
</html>