<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>增加用户</title>
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
<script>
$(function(){
	$('#dlg').dialog('open').dialog('setTitle','增加用户');
	$('#fm').form('clear');
});

function saveUser(){
	$('#fm').form('submit',{
		url: '<%=basePath%>admin/saveUser?random=' + Math.random(), 
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
<script language="JavaScript" type="text/javascript"
	src="<%=basePath%>js/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<div id="dlg" class="easyui-dialog"
		style="width: 400px; height: 350px; padding: 10px 20px" closed="true"
		buttons="#dlg-buttons">
		<div class="ftitle">个人信息</div>
		<form id="fm" method="post">
			<div class="fitem">
				<label>姓名:</label> <input name="name">
			</div>
			<div class="fitem">
				<label>生日yyyy-MM-DD:</label> <input name="birth">
			</div>
			<div class="fitem">
				<label>身高:</label> <input name="height">
			</div>
			<div class="fitem">
				<label>体重:</label> <input name="weight">
			</div>
			<div class="fitem">
				<label>性别:</label> <input name="gender">
			</div>
			<div class="fitem">
				<label>电话:</label> <input name="phone">
			</div>
			<div class="fitem">
				<label>地址:</label> <input name="address">
			</div>
			<div class="fitem">
				<label>ess:</label> <input name="essRank">
			</div>
			<div>
				<input type="checkbox" name="qx" value="1" />失眠 <input
					type="checkbox" name="qx" value="2" />糖尿病 <input type="checkbox"
					name="qx" value="3" />高血压 <input type="checkbox" name="qx"
					value="4" />冠心病 <input type="checkbox" name="qx" value="5" />心力衰竭
				<input type="checkbox" name="qx" value="6" />心律失常 <input
					type="checkbox" name="qx" value="7" />鼻腔阻塞 <input type="checkbox"
					name="qx" value="8" />长期吸烟 <input type="checkbox" name="qx"
					value="9" />悬雍垂粗大 <input type="checkbox" name="qx" value="10" />OSAHS的家族史
				<input type="checkbox" name="qx" value="11" />脑血管疾病 <input
					type="checkbox" name="qx" value="12" />肾功能损害 <input
					type="checkbox" name="qx" value="13" />用镇静剂／药物 <input
					type="checkbox" name="qx" value="14" />长期大量饮酒

			</div>
		</form>
	</div>
		<div id="dlg-buttons">
		<a href="#" class="easyui-linkbutton" iconCls="icon-ok"
			onclick="saveUser()">保存</a> <a href="#" class="easyui-linkbutton"
			iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">取消</a>
	</div>
</body>
</html>