<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	pageContext.setAttribute("basePath", basePath);

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${user.name}的报告</title>
<script language="JavaScript" type="text/javascript"
	src="${basePath}js/jquery.min.js" charset="utf-8"></script>
<link href="${basePath}css/internet.css" rel="stylesheet"
	type="text/css" />
<script language="JavaScript" type="text/javascript"
	src="${basePath}js/echarts.common.min.js"></script>
<script language="JavaScript" type="text/javascript"
	src="${basePath}js/util.js"></script>
<script>


	$(function() {
		// 基于准备好的dom，初始化echarts实例
		var myChart = echarts.init(document.getElementById('chart1'));
		var baseOption = {
			tooltip : {
				trigger : 'axis',
				axisPointer : { // 坐标轴指示器，坐标轴触发有效
					type : 'shadow' // 默认为直线，可选为：'line' | 'shadow'
				}
			},

			grid : {
				left : '3%',
				right : '4%',
				bottom : '10%',
				containLabel : true
			},
			xAxis : [ {
				type : 'category',
				data : [ "0-50%", "50-59%", "60-69%", "70-79%", "80-89%",
						"90-100%" ],
				axisLabel : {
					interval : 0
				}
			} ],
			yAxis : [ {
				type : 'value',
				show : 'true',
				nameLocation : 'start'
			} ],
			series : [

			{
				name : '氧减总次数',
				type : 'bar',
				barWidth : 20,
				data : [ 0, 0, 0, 0, 0 ]
			}

			]
		};
		// 指定图表的配置项和数据

		// 使用刚指定的配置项和数据显示图表。
		myChart.setOption(baseOption);

		// 基于准备好的dom，初始化echarts实例
		var myChart2 = echarts.init(document.getElementById('chart2'));

		// 指定图表的配置项和数据
		var baseOption2 = {
			tooltip : {
				trigger : 'axis',
				axisPointer : { // 坐标轴指示器，坐标轴触发有效
					type : 'shadow' // 默认为直线，可选为：'line' | 'shadow'
				},
				formatter : function(params) {

					return params[0].name + params[0].seriesName + ' : '
							+ params[0].value + ' (分)<br/>'

				},
			},

			grid : {
				left : '3%',
				right : '4%',
				bottom : '10%',
				containLabel : true
			},
			xAxis : [ {
				type : 'category',
				data : [ "0-50%", "50-59%", "60-69%", "70-79%", "80-89%",
						"90-100%" ],
				axisLabel : {
					interval : 0
				}
			} ],
			yAxis : [ {
				type : 'value',
			} ],
			series : [ {
				name : '氧减总时间(秒)',
				type : 'bar',
				barWidth : 20,
				data : [ 0, 0, 0, 0, 0, 0 ]
			} ]
		};

		// 使用刚指定的配置项和数据显示图表。
		myChart2.setOption(baseOption2);
		myChart.showLoading();
		myChart2.showLoading();
	});
	$.get('../getHyponeaData/${sleepReport.id}').done(
			function(data) {
				var myChart1 = echarts.getInstanceByDom(document
						.getElementById('chart1'))
				var myChart2 = echarts.getInstanceByDom(document
						.getElementById('chart2'))
				// 填入数据
				myChart1.hideLoading();
				myChart2.hideLoading();
				myChart1.setOption({
					series : [ {
						data : data.times
					} ]
				});
				myChart2.setOption({
					series : [ {
						data : data.seconds
					} ]
				});

			});
</script>
<script>
	function printit() {
		//var bdhtml = window.document.body.innerHTML;//获取当前页的html代码    
		//var sprnstr = "<!--begin-->";//设置打印开始区域    
		//var eprnstr = "<!--end-->";//设置打印结束区域    
		////var prnhtml = bdhtml.substring(bdhtml.indexOf(sprnstr)); //从开始代码向后取html    
		//var prnhtml = prnhtml.substring(0, prnhtml.indexOf(eprnstr));//从结束代码向前取html    
		//	window.document.body.innerHTML = prnhtml;
		//var myChart1 = echarts.getInstanceByDom(document.getElementById('chart1'))
		//myChart1.setOption(baseOption);
		//	var myChart2 = echarts.getInstanceByDom(document.getElementById('chart2'))
		//myChart2.resize();

		document.getElementById("print").style.display = "none";
		window.print();
		document.getElementById('print').style.display = "";
		//window.document.body.innerHTML = bdhtml;
	}

	function generatePdf() {
		$.getJSON('../generatePdf/${sleepReport.id}').done(function(data) {
			console.info(data.code)
			if (data.code == 2000) {
				window.location.href = "../showPdf/${sleepReport.id}";
			}
		});
	}

	function showpdf() {
		//$.get('../showPdf/${sleepReport.id}').done(function(data) {
			//if (data.code == 2000) {
				window.location.href = "../showPdf/${sleepReport.id}";
		//	}
	//	});
	}
	function generateFullPdf() {
		$.get('../generatePdf/${sleepReport.id}').done(function(data) {
			if (data.code == 2000) {
				window.open(data.message);
			}
		});
	}
	
	function save(){
		 var d=$("#form").serialize();
	     $.post("${basePath}admin/generateFullPdf/${sleepReport.id}", 
	    		d, 
	    	function (result) { 
	    		 if (result.code == 2000) {
					window.location.href = result.message;  
				}
	    	 }, "json" ); 	
	}
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
	<button id="print" onclick="generatePdf()">重新生成pdf</button><button id="print" onclick="showpdf()">查看pdf</button><button id="print" onclick="history.go(-1)">返回</button>
	<c:if test="${!file }"><form enctype="multipart/form-data" action="${basePath}admin/uploadFile" method="post" ><input type="file" name="file"/> <input type="submit" value="上传文件" /><input type="hidden" value="${ sleepReport.id}" name="id" /></form> 
	</c:if>
	<c:if test="${file }">
	文件已存在！！！
	<a href="${basePath}admin/showFile/${ sleepReport.id}"><input type="button" value="下载数据文件"></input></a>
	<form enctype="multipart/form-data" action="${basePath}admin/uploadFile" method="post" ><input type="file" name="file"/> <input type="submit" value="上传文件" /><input type="hidden" value="${ sleepReport.id}" name="id" /></form> 
	</c:if>
	<!--begin-->
	<div id="container">
		<div class="searcbar"
			style="font-size: 18px; font-family: monospace; height: 25px;">睡眠呼吸监测报告</div>

		<div class="topbar"
			style="font-size: 12px; font-family: initial; text-align: inherit; height: 80px; width: 800px">
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
			&nbsp;&nbsp;&nbsp;&nbsp;住址:${user.address}
		</div>

		<div id="contentarea">
			<div class="column" style="width: 800px;">
				<div class="titlebar">
					<h1>睡眠报告</h1>
				</div>
				<div class="content-inner">
					开始时间:
					<fmt:formatDate value="${sleepReport.startTime}"
						pattern="yyyy-MM-dd HH:mm:ss" />
					, 结束时间：
					<fmt:formatDate value="${sleepReport.endTime}"
						pattern="yyyy-MM-dd HH:mm:ss" />
					, <br />潜睡时长:
					<fmt:formatNumber type="number"
						value="${(sleepReport.lightSleepSeconds - sleepReport.lightSleepSeconds % 3600 )/ 3600}"
						maxFractionDigits="0" />
					小时
					<fmt:formatNumber type="number"
						value="${(sleepReport.lightSleepSeconds % 3600- sleepReport.lightSleepSeconds %3600 % 60 )/ 60}"
						maxFractionDigits="0" />
					分 ${sleepReport.lightSleepSeconds % 60}秒 , <br />深睡时长:
					<fmt:formatNumber type="number"
						value="${(sleepReport.deepSleepSeconds - sleepReport.deepSleepSeconds % 3600 )/ 3600}"
						maxFractionDigits="0" />
					小时
					<fmt:formatNumber type="number"
						value="${(sleepReport.deepSleepSeconds % 3600- sleepReport.deepSleepSeconds %3600 % 60 )/ 60}"
						maxFractionDigits="0" />
					分 ${sleepReport.deepSleepSeconds % 60}秒 , <br />清醒时长:
					<fmt:formatNumber type="number"
						value="${(sleepReport.awakeSeconds - sleepReport.awakeSeconds % 3600 )/ 3600}"
						maxFractionDigits="0" />
					小时
					<fmt:formatNumber type="number"
						value="${(sleepReport.awakeSeconds % 3600- sleepReport.awakeSeconds %3600 % 60 )/ 60}"
						maxFractionDigits="0" />
					分 ${sleepReport.awakeSeconds % 60}秒 , <br />总计:
					<fmt:formatNumber type="number"
						value="${(sleepReport.totalSeconds - sleepReport.totalSeconds % 3600 )/ 3600}"
						maxFractionDigits="0" />
					小时
					<fmt:formatNumber type="number"
						value="${(sleepReport.totalSeconds % 3600- sleepReport.totalSeconds %3600 % 60 )/ 60}"
						maxFractionDigits="0" />
					分 ${sleepReport.totalSeconds % 60}秒 , 评分：${sleepReport.score},
					上传日期:
					<fmt:formatDate value="${sleepReport.uploadDate}"
						pattern="yyyy-MM-dd" />
				</div>
			</div>
			<div class="column" style="width: 800px;">
				<div class="titlebar">
					<h1>呼吸报告</h1>
				</div>
				<div class="content-inner">
					呼吸暂停低通气指数:${breatheReport.apneaHypopneaIndex},
					呼吸暂停次数:${breatheReport.apneaTimes},
					低通气次数:${breatheReport.hypopneaTimes},
					氧减次数:${breatheReport.reducedOxygenTimes}, odi:${breatheReport.odi},
					醒时:
					<fmt:formatNumber type="number"
						value="${(breatheReport.awakeSeconds - breatheReport.awakeSeconds % 60 )/ 60}"
						maxFractionDigits="0" />
					分 ${breatheReport.awakeSeconds % 60}秒, <br />血氧饱和度低于90%占比:${breatheReport.oxygenSaturationLessthanNinetyPercent}%,
					评分:${breatheReport.score}， 上传日期:
					<fmt:formatDate value="${breatheReport.uploadDate}"
						pattern="yyyy-MM-dd" />
				</div>
			</div>

			<%-- <div class="column">
					<div class="titlebar">
						<h1>血氧饱和度</h1>
					</div>
					<div class="content-inner">
						<table>
							<tr>
								<th>血氧分布</th>
								<th>90%-100%</th>
								<th>80%-89%</th>
								<th>70%-79%</th>
								<th>60%-69%</th>
								<th>50%-59%</th>
								<th>0%-50%</th>
							</tr>
							<tr>
								<th>总时间</th>
								<td><fmt:formatNumber type="number"
										value="${(breatheReport.oxygenSaturationNinetyToHundredPercentHyponea - breatheReport.oxygenSaturationNinetyToHundredPercentHyponea % 3600 )/ 3600}"
										maxFractionDigits="0" /> 小时 <fmt:formatNumber type="number"
										value="${(breatheReport.oxygenSaturationNinetyToHundredPercentHyponea % 3600- breatheReport.oxygenSaturationNinetyToHundredPercentHyponea %3600 % 60 )/ 60}"
										maxFractionDigits="0" /> 分
									${breatheReport.oxygenSaturationNinetyToHundredPercentHyponea % 60}秒</td>
								<td><fmt:formatNumber type="number"
										value="${(breatheReport.oxygenSaturationEightyToEightyNinePercentHyponea - breatheReport.oxygenSaturationEightyToEightyNinePercentHyponea % 3600 )/ 3600}"
										maxFractionDigits="0" /> 小时 <fmt:formatNumber type="number"
										value="${(breatheReport.oxygenSaturationEightyToEightyNinePercentHyponea % 3600- breatheReport.oxygenSaturationEightyToEightyNinePercentHyponea %3600 % 60 )/ 60}"
										maxFractionDigits="0" /> 分
									${breatheReport.oxygenSaturationEightyToEightyNinePercentHyponea % 60}秒</td>
								<td><fmt:formatNumber type="number"
										value="${(breatheReport.oxygenSaturationSeventyToSeventyNinePercentHyponea - breatheReport.oxygenSaturationSeventyToSeventyNinePercentHyponea % 3600 )/ 3600}"
										maxFractionDigits="0" /> 小时 <fmt:formatNumber type="number"
										value="${(breatheReport.oxygenSaturationSeventyToSeventyNinePercentHyponea % 3600- breatheReport.oxygenSaturationSeventyToSeventyNinePercentHyponea %3600 % 60 )/ 60}"
										maxFractionDigits="0" /> 分
									${breatheReport.oxygenSaturationSeventyToSeventyNinePercentHyponea % 60}秒</td>
								<td><fmt:formatNumber type="number"
										value="${(breatheReport.oxygenSaturationSixtyToSixtyNinePercentHyponea - breatheReport.oxygenSaturationSixtyToSixtyNinePercentHyponea % 3600 )/ 3600}"
										maxFractionDigits="0" /> 小时 <fmt:formatNumber type="number"
										value="${(breatheReport.oxygenSaturationSixtyToSixtyNinePercentHyponea % 3600- breatheReport.oxygenSaturationSixtyToSixtyNinePercentHyponea %3600 % 60 )/ 60}"
										maxFractionDigits="0" /> 分
									${breatheReport.oxygenSaturationSixtyToSixtyNinePercentHyponea % 60}秒</td>
								<td><fmt:formatNumber type="number"
										value="${(breatheReport.oxygenSaturationFiftyToFiftyNinePercentHyponea - breatheReport.oxygenSaturationFiftyToFiftyNinePercentHyponea % 3600 )/ 3600}"
										maxFractionDigits="0" /> 小时 <fmt:formatNumber type="number"
										value="${(breatheReport.oxygenSaturationFiftyToFiftyNinePercentHyponea % 3600- breatheReport.oxygenSaturationFiftyToFiftyNinePercentHyponea %3600 % 60 )/ 60}"
										maxFractionDigits="0" /> 分
									${breatheReport.oxygenSaturationFiftyToFiftyNinePercentHyponea % 60}秒</td>
								<td><fmt:formatNumber type="number"
										value="${(breatheReport.oxygenSaturationLessthanFiftyPercentHyponea - breatheReport.oxygenSaturationLessthanFiftyPercentHyponea % 3600 )/ 3600}"
										maxFractionDigits="0" /> 小时 <fmt:formatNumber type="number"
										value="${(breatheReport.oxygenSaturationLessthanFiftyPercentHyponea % 3600- breatheReport.oxygenSaturationLessthanFiftyPercentHyponea %3600 % 60 )/ 60}"
										maxFractionDigits="0" /> 分
									${breatheReport.oxygenSaturationLessthanFiftyPercentHyponea % 60}秒</td>
							</tr>
							<tr>
								<th>氧减总次数</th>
								<td>${breatheReport.oxygenSaturationNinetyToHundredPercentTimes}</td>
								<td>${breatheReport.oxygenSaturationEightyToEightyNinePercentTimes}</td>
								<td>${breatheReport.oxygenSaturationSeventyToSeventyNinePercentTimes}</td>
								<td>${breatheReport.oxygenSaturationSixtyToSixtyNinePercentTimes}</td>
								<td>${breatheReport.oxygenSaturationFiftyToFiftyNinePercentTimes}</td>
								<td>${breatheReport.oxygenSaturationLessthanFiftyPercentTimes}</td>
							</tr>
						</table>
					</div>    
				</div>--%>
			<div class="column" style="height: 200px; width: 800px;">
				<div class="titlebar">
					<h1>血氧饱和度-氧减总次数</h1>
				</div>
				<div class="content-inner" id="chart1"
					style="width: 390px; height: 175px; float: left"></div>
				<div class="content-inner" id="chart2"
					style="width: 390px; height: 175px; float: left"></div>
			</div>
			<div class="column" style="width: 800px;">
				<div class="titlebar">
					<h1>其他信息</h1>
				</div>
				<div class="content-inner">
					<form id="form">
						最大呼吸暂停：<input type="text" name="maxhuxizanting" /> 最大呼吸时间：<input
							type="text" name="maxhuxizantingDate" /> 最大氧减次数：<input
							type="text" name="maxYangjiangtimes" /> 最大氧减少时间：<input
							type="text" name="maxYangjiangDate" /> 血氧危害指数:<input type="text"
							name="xueyangweihaizhishu" /> 结论:<input type="text"
							name="result"
							value=" 1. 睡眠呼吸暂停低通气综合症：无/轻度/中度/重度 \n 2. 睡眠低氧血症：无/轻度/中度/重度" />
						建议:<input type="text" name="advice" value="结果请结合临床。" />
					</form>
					<button value="生成" onclick="save()" onsubmit="return false">生成</button>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
