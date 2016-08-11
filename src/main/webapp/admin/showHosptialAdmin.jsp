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
		$.extend($.fn.validatebox.defaults.rules, {
			/*必须和某个字段相等*/
			equals : {
				validator : function(value, param) {
					return $(param[0]).val() == value;
				},
				message : '新密码不相同'
			}

		});
		$('#dg').datagrid({
			url : '${basePath}admin/getHospitalAdmin/' + getQueryString("id"),
			pageSize : 20,
			onDblClickRow : function(rowIndex, rowData) {
				jump();
			}
		});

	});

	function saveHospitalAdmin() {
		$('#hospitalId').val(getQueryString("id"));
		$('#fm2').form('submit', {
			url : '${basePath}admin/saveHospitalAdmin?' + Math.random(),
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
					$('#dlg2').dialog('close'); // close the dialog
					$('#dg').datagrid('reload'); // reload the user data
					$.messager.show({ // show error message
						title : "成功",
						msg : "成功"
					});
				}
			}
		});
	}

	function addHospitalAdmin() {
		$('#dlg2').dialog('open').dialog('setTitle', '增加管理员');
		$('#fm2').form('clear');
		//url = '${basePath}admin/editHospital?id=' + row.id + '&rnd='+ Math.random();
	}

	function editHospitalAdmin() {
		var row = $('#dg').datagrid('getSelected');
		if (row) {
			$('#dlg').dialog('open').dialog('setTitle', '修改管理员信息');
			$('#fm').form('load', row);
			//url = '${basePath}admin/editHospital?id=' + row.id + '&rnd='+ Math.random();
		}
	}
	function changeHospitalAdminPassword() {
		var row = $('#dg').datagrid('getSelected');
		if (row) {
			$('#dlgpass').dialog('open').dialog('setTitle', '修改密码');
		}
	}

	function alterHospitalAdmin() {
		var row = $('#dg').datagrid('getSelected');
		if (row) {
			$('#fm').form('submit', {
				url : '${basePath}admin/editHospitalAdmin?' + Math.random(),
				onSubmit : function(param) {
					param.id = row.id;
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

	}

	function saveHospitalAdminPassword() {
		var row = $('#dg').datagrid('getSelected');
		if (row) {
			$('#fmpass').form(
					'submit',
					{
						url : '${basePath}admin/updateHospitalAdminPassword?'
								+ Math.random(),
						onSubmit : function(param) {
							param.id = row.id;
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
								$('#dlgpass').dialog('close'); // close the dialog
								$('#dg').datagrid('reload'); // reload the user data
								$.messager.show({ // show error message
									title : "成功",
									msg : "成功"
								});
							}
						}
					});
		}

	}

	function deleteHospitalAdmin() {
		var row = $('#dg').datagrid('getSelected');
		if (row) {
			$.messager.confirm('确认', '真的要删除该管理员嘛?', function(r) {
				if (r) {
					$.post('${basePath}admin/deleteHospitalAdmin', {
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
		<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true"
			onclick="addHospitalAdmin()">增加</a> <a href="#"
			class="easyui-linkbutton" iconCls="icon-edit" plain="true"
			onclick="editHospitalAdmin()">编辑</a> <a href="#"
			class="easyui-linkbutton" iconCls="icon-edit" plain="true"
			onclick="changeHospitalAdminPassword()">修改密码</a> <a href="#"
			class="easyui-linkbutton" iconCls="icon-remove"
			onclick="deleteHospitalAdmin()" plain="true" onclick="">删除</a> <a
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

	<div id="dlg" class="easyui-dialog"
		style="width: 250px; height: 250px; padding: 10px 20px" closed="true"
		buttons="#dlg-buttons">
		<br />
		<form id="fm" method="post">
			<div class="fitem">
				<label>名称:</label> <input name="name">
			</div>
			<br />
			<div class="fitem">
				<label>用户名:</label> <input name="username">
			</div>
			<br />
			<div class="fitem">
				<label>权限:</label> <input name="systemRoles" type="checkbox"
					value="doctor" />医生<input name="systemRoles" type="checkbox"
					value="hospital" />医院
			</div>
			<br />
			<div class="fitem">
				<label>账号是否有效:</label> <input type="radio" value="true"
					name="isValid"> 有效<input type="radio" value="false"
					name="isValid"> 无效
			</div>
		</form>
	</div>
	<div id="dlg-buttons">
		<a href="#" class="easyui-linkbutton" iconCls="icon-ok"
			onclick="alterHospitalAdmin()">保存</a> <a href="#"
			class="easyui-linkbutton" iconCls="icon-cancel"
			onclick="javascript:$('#dlg').dialog('close')">取消</a>
	</div>


	<div id="dlg2" class="easyui-dialog"
		style="width: 300px; height: 300px; padding: 10px 20px" closed="true"
		buttons="#dlg-buttons">
		<br />
		<form id="fm2" method="post">
			<input type="hidden" name="hospitalId" id="hospitalId">
			<div class="fitem">
				<label>名称:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label> <input
					name="name">
			</div>
			<br />
			<div class="fitem">
				<label>用户名:&nbsp;&nbsp;&nbsp;</label> <input name="username">
			</div>
			<br />
			<div class="fitem">
				<label>密码:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label> <input
					id="newp" type="password" name="password"
					class="easyui-validatebox" data-options="required:true"><br />
			</div>
			<br />
			<div class="fitem">
				<label>确认密码:</label> <input id="confirm" type="password"
					name="confirm" class="easyui-validatebox"
					data-options="required:true" validType="equals['#newp']"><br />
			</div>
			<br />
			<div class="fitem">
				<label>权限:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
				<input name="systemRoles" type="checkbox" value="doctor" />医生<input
					name="systemRoles" type="checkbox" value="hospital" />医院
			</div>
			<br />
			<div class="fitem">
				<label>账号是否有效:</label> <input type="radio" value="true"
					name="isValid"> 有效<input type="radio" value="false"
					name="isValid"> 无效
			</div>
		</form>
	</div>
	<div id="dlg-buttons">
		<a href="#" class="easyui-linkbutton" iconCls="icon-ok"
			onclick="saveHospitalAdmin()">保存</a> <a href="#"
			class="easyui-linkbutton" iconCls="icon-cancel"
			onclick="javascript:$('#dlg2').dialog('close')">取消</a>
	</div>


	<div id="dlgpass" class="easyui-dialog"
		style="width: 300px; height: 200px; padding: 10px 20px" closed="true"
		buttons="#dlg-buttons">
		<br />
		<form id="fmpass" method="post">
			<div class="fitem">
				<label>密码:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label> <input
					id="adminp" type="password" name="password"
					class="easyui-validatebox" data-options="required:true"><br />
			</div>
			<br />
			<div class="fitem">
				<label>确认密码:</label> <input id="adminnp" type="password"
					name="confirm" class="easyui-validatebox"
					data-options="required:true" validType="equals['#adminp']"><br />
			</div>
		</form>
	</div>
	<div id="dlg-buttons">
		<a href="#" class="easyui-linkbutton" iconCls="icon-ok"
			onclick="saveHospitalAdminPassword()">保存</a> <a href="#"
			class="easyui-linkbutton" iconCls="icon-cancel"
			onclick="javascript:$('#dlgpass').dialog('close')">取消</a>
	</div>



</body>
</html>