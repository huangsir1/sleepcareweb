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
	$('#dlg').dialog('open').dialog('setTitle','修改密码');
	$('#fm').form('clear');
	$.extend($.fn.validatebox.defaults.rules, {    
	    /*必须和某个字段相等*/  
	    equals: {  
	        validator:function(value,param){  
	            return $(param[0]).val() == value;  
	        },  
	        message:'新密码不相同'  
	    }  
	             
	});  
});


function changePassword(){
	$('#fm').form('submit',{
		url: '<%=basePath%>admin/changePassword?' + Math.random(),
		onSubmit : function() {
			return $(this).form('validate');
		},
		success : function(result) {
			console.info(result + " ")  
			var result = eval("("+result  +")");
			console.info(result)
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
					msg : "密码修改成功"
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
		<div class="ftitle">修改密码</div><br />
		<form id="fm" method="post">
			<div class="fitem">
				<label>原密码:</label> <input type="password" name="original" class="easyui-validatebox" data-options="required:true"><br />
				<label>新密码:</label> <input id="newp" type="password" name="newp" class="easyui-validatebox" data-options="required:true"><br />
				<label>确认密码:</label> <input id="confirm" type="password" name="confirm"  class="easyui-validatebox" data-options="required:true" validType="equals['#newp']"><br />
			</div>
			
		</form>
	</div>
		<div id="dlg-buttons">
		<a href="#" class="easyui-linkbutton" iconCls="icon-ok"
			onclick="changePassword()">修改</a> <a href="#" class="easyui-linkbutton"
			iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">取消</a>
	</div>
</body>
</html>