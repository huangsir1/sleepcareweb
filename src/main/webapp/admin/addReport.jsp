<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${user.name}的报告</title>
<script language="JavaScript" type="text/javascript"
	src="<%=basePath%>js/jquery.min.js" charset="utf-8"></script>
<link href="<%=basePath%>css/internet.css" rel="stylesheet"
	type="text/css" />
<script language="JavaScript" type="text/javascript"
	src="<%=basePath%>js/echarts.common.min.js"></script>
<script language="JavaScript" type="text/javascript"
	src="<%=basePath%>js/util.js"></script>
<script>
</script>
<style type="text/css">
<!--
.style1 {
	color: #5c8bc7
}
-->
</style>
<style type="text/css">
table {
	font-family: verdana, arial, sans-serif;
	font-size: 11px;
	color: #333333;
	border-width: 1px;
	border-color: #666666;
	border-collapse: collapse;
}

th {
	border-width: 1px;
	padding: 8px;
	border-style: solid;
	border-color: #666666;
	background-color: #dedede;
}

td {
	border-width: 1px;
	padding: 8px;
	border-style: solid;
	border-color: #666666;
	background-color: #ffffff;
}
</style>



</head>

<body>
	<!--begin-->
	<div id="container">
		<div class="searcbar"
			style="font-size: 18px; font-family: monospace; height: 25px;">睡眠呼吸监测报告</div>
<form action="<%=basePath%>admin/postReport" method="post" enctype="multipart/form-data" id="fm">
		<div class="topbar"
			style="font-size: 12px; font-family: initial; text-align: inherit; height: 100px; width: 800px">
			<div class="titlebar">
				<h1>个人资料</h1> 
			</div>
			姓名:${user.name}&nbsp;&nbsp;&nbsp;性别:${user.gender}&nbsp;&nbsp;&nbsp;生日:
			<fmt:formatDate value="${user.birthday}" pattern="yyyy-MM-dd" />
			&nbsp;&nbsp;&nbsp;体重:${user.weight}kg<br />
			身高:${user.height}cm&nbsp;&nbsp;&nbsp;&nbsp;ess总分:${user.essRank}&nbsp;&nbsp;&nbsp;BMI:
			<fmt:formatNumber
				value="${10000 * user.weight / user.height / user.height}" 
				pattern="##.##" minFractionDigits="2"></fmt:formatNumber>  
			&nbsp;&nbsp;&nbsp;&nbsp;电话:${user.phone}<br /> 疾病史:
			<c:forEach items="${user. diseaseHistories}" var="d">	${d.name}</c:forEach> 
			&nbsp;&nbsp;&nbsp;&nbsp;住址:${user.address} <br /> 
			开始时间yyyy-MM-dd HH:mm:ss : <input type="text" name="kaishishijian"/> 结束时间 : <input type="text" name="jieshushij"/> 
		</div>

		<div id="contentarea">
			<div class="column" style="width: 800px;">
				<div class="titlebar">
					<h1>呼吸统计</h1>
				</div>
				<input type="hidden" name="userId" value="${user.id}" />
				<div class="content-inner"> 呼吸紊乱指数(AHI) : <input type="text" name="apneaHypopneaIndex"/> 呼吸暂停次数
					: <input type="text" name="apneaTimes"/>次 最长呼吸暂停 : <input type="text" name="maxhuxizantingH" size="2"/>小时 <input type="text" name="maxhuxizantingM"size="2"/>分 <input type="text" name="maxhuxizantingS"size="2"/>秒,发生于  <input type="text" name="maxhuxizantingDateString"/> 低通气次数 : <input type="text" name="hypopneaTimes"/> 次 最长低通气 :  <input type="text" name="maxHyponeaSecondsH" size="2"/>小时 <input type="text" name="maxHyponeaSecondsM"size="2"/>分 <input type="text" name="maxHyponeaSecondsS"size="2"/>秒,发生于  <input type="text" name="hyponeaHappenDateString"/>
					呼吸质量评分 :  <input type="text" name="breathe_score"/></div>
				
			</div> 
			<div class="column" style="width: 800px;">  
				<div class="titlebar">
					<h1>血氧饱和度分析</h1>
				</div>
				<div class="content-inner">平均血氧饱和度 :  <input type="text" name="averageOxygenSaturation"/>% 最低血氧饱和度 :  <input type="text" name="minOxygenSaturation"/>%
					血氧饱和度低于90%占比 :  <input type="text" name="oxygenSaturationLessthanNinetyPercent"/>% 氧减指数 :  <input type="text" name="yangjianzhishu"/>
					最大氧降 : <input type="text" name="maxYangjiangtimesH" size="2"/>小时 <input type="text" name="maxYangjiangtimesM"size="2"/>分 <input type="text" name="maxYangjiangtimesS"size="2"/>秒,发生于<input type="text" name="maxYangjiangDateString"/>
					最长氧降时长 :<input type="text" name="longestYangjiangtimesH" size="2"/>小时 <input type="text" name="longestYangjiangtimesM"size="2"/>分 <input type="text" name="longestYangjiangtimesS"size="2"/>秒,发生于 <input type="text" name="longestYangjiangDateString"/>
					
					血氧危害指数 : <input type="text" name="xueyangweihaizhishu"/></div>
					 血氧饱和度0%-50%<input type="text" name="oxygenSaturationLessthanFiftyPercentTimes"size="2"/>次 时间:<input type="text" name="l050H"size="2"/>小时<input type="text" name="l050M"size="2"/>分<input type="text" name="l050S"size="2"/>秒<br />
					 血氧饱和度50%-60%<input type="text" name="oxygenSaturationFiftyToFiftyNinePercentTimes"size="2"/>次 时间:<input type="text" name="l5060H"size="2"/>小时<input type="text" name="l5060M"size="2"/>分<input type="text" name="l5060S"size="2"/>秒<br />
					 血氧饱和度60%-70%<input type="text" name="oxygenSaturationSixtyToSixtyNinePercentTimes"size="2"/>次 时间:<input type="text" name="l6070H"size="2"/>小时<input type="text" name="l6070M"size="2"/>分<input type="text" name="l6070S"size="2"/>秒<br />
					 血氧饱和度70%-80%<input type="text" name="oxygenSaturationSeventyToSeventyNinePercentTimes"size="2"/>次 时间:<input type="text" name="l7080H"size="2"/>小时<input type="text" name="l7080M"size="2"/>分<input type="text" name="l7080S"size="2"/>秒<br />
					 血氧饱和度80%-90%<input type="text" name="oxygenSaturationEightyToEightyNinePercentTimes"size="2"/>次 时间:<input type="text" name="l8090H"size="2"/>小时<input type="text" name="l8090M"size="2"/>分<input type="text" name="l8090S"size="2"/>秒<br />
					 血氧饱和度90%-100%<input type="text" name="oxygenSaturationNinetyToHundredPercentTimes"size="2"/>次 时间:<input type="text" name="l90100H"size="2"/>小时<input type="text" name="l90100M"size="2"/>分<input type="text" name="l90100S"size="2"/>秒<br />
					 
			</div>
			<div class="column" style="width: 800px;">
				<div class="titlebar">
					<h1>脉率分析</h1>
				</div>
				平均脉率 : <input type="text" name="aveagePulse"/>次/分钟 最低脉率 : <input type="text" name="minPulse"/>次/分钟，发生于 <input type="text" name="minPulseDate"/> 最高脉率 : <input type="text" name="maxPulse"/>次/分钟，发生于 <input type="text" name="maxPulseDate"/>
				 <input type="file" name="dataText"/>
			</div>
			<div class="column" style="width: 800px;">
				<div class="titlebar">
					<h1>睡眠分析</h1>
				</div>
				<div class="content-inner">睡眠时间 :  <input type="text" name="totalSecondsH"size="2"/>小时 <input type="text" name="totalSecondsM"size="2"/>分 <input type="text" name="totalSecondsD"size="2"/>秒 深睡眠时长 :  <input type="text" name="deepSleepSecondsH"size="2"/>小时 <input type="text" name="deepSleepSecondsM"size="2"/>分 <input type="text" name="deepSleepSecondsS"size="2"/>秒
					浅睡眠时长 :  <input type="text" name="lightSleepSecondsH"size="2"/>小时 <input type="text" name="lightSleepSecondsM"size="2"/>分<input type="text" name="lightSleepSecondsS"size="2"/>秒  睡眠质量评分 :  <input type="text" name="sleep_score"/></div>
				<input type="submit" name="保存"></input>
			</div>
		</div>
		</form>
		
	</div>
</body>
</html>
