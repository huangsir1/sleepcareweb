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
	src="${basePath}js/g2.js"></script>
<!-- 引入插件的 css 样式 -->
<link rel="stylesheet" type="text/css"
	href="https://os.alipayobjects.com/rmsportal/UBXCMkzNVlaZYNs.css" />
<!-- 引入插件脚本 -->
<script
	src="https://as.alipayobjects.com/g/datavis/g-plugin-range/0.0.9/index.js"></script>


<script language="JavaScript" type="text/javascript"
	src="${basePath}js/util.js"></script>
<script>
	$.getJSON('../getHyponeaDataNew/${baseReport.id}', function(data) {
		var Stat = G2.Stat;
		var chart = new G2.Chart({
			id : 'chart1',
			width : 440,
			height : 175,
			plotCfg : {
				margin : [ 20, 60, 80, 60 ]
			}
		});
		var Frame = G2.Frame;
		var frame = new Frame(data.yangjian);
		frame = Frame.sort(frame, 'name');
		chart.source(frame, {
			'value' : {
				alias : '氧减总时间'
			}
		});
		chart.axis('name', {
			title : null
		});
		chart.axis('value', {
			title : null,
			formatter : function(dimValue) {
				return dimValue + '分';
			}
		});
		chart.interval().position('name*value').color('#e50000');
		chart.render();

		var Stat2 = G2.Stat;
		var chart2 = new G2.Chart({
			id : 'chart2',
			width : 440,
			height : 175,
			plotCfg : {
				margin : [ 20, 60, 80, 70 ]
			}
		});
		var Frame2 = G2.Frame;
		var frame2 = new Frame(data.xueyang);
		frame2 = Frame.sort(frame2, 'name');
		chart2.source(frame2, {
			'value' : {
				alias : '氧减总次数'
			}
		});
		chart2.axis('name', {
			title : null
		});
		chart2.axis('value', {
			title : null,
			formatter : function(dimValue) {
				return dimValue + '次';
			}
		});
		chart2.interval().position('name*value').color('#e50000');
		chart2.render();

	});
	var flowChart;
	var rainChart ;
	var range;
	$.getJSON('../getReportData/${baseReport.id}?percent=0.05',
					function(data) {
						$('<div><br /><br /><br />数据量: <input onclick=\"getDate(0.05)\" type=\"radio\" name=\"percent\" checked >5% <input onclick=\"getDate(0.1)\" type=\"radio\" name=\"percent\" >10% <input onclick=\"getDate(0.2)\" type=\"radio\" name=\"percent\" >20% <input type=\"radio\" onclick=\"getDate(0.3)\"  name=\"percent\" >30% <input type=\"radio\" onclick=\"getDate(0.4)\"  name=\"percent\" >40% <input type=\"radio\" onclick=\"getDate(0.5)\"  name=\"percent\" >50% <input type=\"radio\" name=\"percent\" onclick=\"getDate(0.6)\"  >60% <input type=\"radio\" name=\"percent\" onclick=\"getDate(0.7)\"  >70% <input type=\"radio\" name=\"percent\" onclick=\"getDate(0.8)\"  >80% <input type=\"radio\" name=\"percent\" onclick=\"getDate(0.9)\"  >90% <input type=\"radio\" name=\"percent\" onclick=\"getDate(1)\" >100% </div>')
								.appendTo('#chart3');
						flowChart = new G2.Chart({
							id : 'chart3',
							width : 800,
							height : 250
						});
						flowChart.source(data, {
							time : {
								type : 'time',
								mask : 'HH:MM:ss',
								nice : true
							}
						});
						flowChart.axis('time', {
							title : null,
							labelOffset : 35
						});
						flowChart.axis('frequency', {
							title : null
						});
						flowChart.line().position('time*frequency').color(
								'#B03A5B').size(2);
						flowChart.guide().text([ 'max', 'max' ], '氧减趋势图');
						rainChart = new G2.Chart({
							id : 'chart3',
							width : 800,
							height : 250,
							plotCfg : {
								margin : [ 5, 80, 75 ]
							}
						});
						rainChart.source(data, {
							time : {
								type : 'time',
								mask : 'HH:MM:ss',
								nice : true
							}
						});
						rainChart.axis('time', {
							title : null,
						});
						rainChart.axis('percent', {
							title : null
						});
						//rainChart.coord().reflect('y');
						rainChart.line().position('time*percent').color(
								'#293c55').size(2);
						rainChart.guide().text([ 'max', 'min' ], '血氧趋势图');
						$('<div id="range"></div>').appendTo('#chart3');
						range = new G2.Plugin.range({
							id : 'range', //DOM id
							width : 800,
							height : 26,
							dim : 'time',
						});

						range.source(data);
						range.link([ flowChart, rainChart ]);
						range.render();
					});
</script>
<script>
	function getDate(percent) {
		$.getJSON('../getReportData/${baseReport.id}?percent=' + percent,
				function(data) {
					flowChart.changeData(data)
					rainChart.changeData(data)
					flowChart.repaint()
					rainChart.repaint()
					
					range.clear()
					range = new G2.Plugin.range({
						id : 'range', //DOM id
						width : 800,
						height : 26,
						dim : 'time', 
					});

					range.source(data);
					range.link([ flowChart, rainChart ]);
					range.render();
				});

	}
	/*$(function() {
		// 基于准备好的dom，初始化echarts实例
		//第一张图
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
		//第二张图
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

		
		
		init3();
		init4();
	});

	
	$.getJSON('../getHyponeaData/${baseReport.id}').done(
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

	$.getJSON('../getReportData/${baseReport.id}').done(
			function(data) {
				
				// 填入数据
				var dates = data.riqi;
				var dateString = [ dates.length ];
				for (var i = 0; i < dates.length; i++) {
					dateString[i] = getFormatDateByLong(parseInt(dates[i]),
							"hh:mm:ss");
				}
				console.info(dates.length)
				
				
				var myChart3 = echarts.getInstanceByDom(document
						.getElementById('chart3'));
				myChart3.setOption({
					xAxis : {
						data : dateString
					},
					series : [ {
						
						data : data.xueyang
					} ]
				});
				
				
				var myChart4 = echarts.getInstanceByDom(document
						.getElementById('chart4'));
				
				myChart4.setOption({
					xAxis : {
						data : dateString
					},
					series : [ {
						data : data.mailv
					} ]
				});  
				
				console.info(data)
				

			});
	
	function init3() {
		//第三张图
		var myChart3 = echarts.init(document.getElementById('chart3'));
		myChart3option = {
			tooltip : {
				trigger : 'axis',
				position : function(pt) {
					return [ pt[0], '10%' ];
				}
			},
			title : {
				text : '血氧趋势图',
				left : 'center'
			},
			legend : {
				top : 'bottom',
				data : [ '％' ]
			},
			xAxis : {
				type : 'category',
				boundaryGap : false,
			},
			yAxis : {
				type : 'value',
				boundaryGap : [ 0, '100%' ]
			},
			dataZoom : [
					{
						type : 'inside',
						start : 0,
						end : 10
					},
					{
						start : 0,
						end : 10,
						handleIcon : 'M10.7,11.9v-1.3H9.3v1.3c-4.9,0.3-8.8,4.4-8.8,9.4c0,5,3.9,9.1,8.8,9.4v1.3h1.3v-1.3c4.9-0.3,8.8-4.4,8.8-9.4C19.5,16.3,15.6,12.2,10.7,11.9z M13.3,24.4H6.7V23h6.6V24.4z M13.3,19.6H6.7v-1.4h6.6V19.6z',
						handleSize : '80%',
						handleStyle : {
							color : '#fff',
							shadowBlur : 3,
							shadowColor : 'rgba(0, 0, 0, 0.6)',
							shadowOffsetX : 2,
							shadowOffsetY : 2
						}
					} ],
			series : [ {
				name : '％',
				type : 'line',
				smooth : true,
				symbol : 'none',
				sampling : 'average',
			} ]
		};
		myChart3.setOption(myChart3option);
	}
	
	function init4() {
		//第三张图
		var myChart4 = echarts.init(document.getElementById('chart4'));
		myChart4option = {
			tooltip : {
				trigger : 'axis',
				position : function(pt) {
					return [ pt[0], '10%' ];
				}
			},
			title : {
				text : '脉率趋势图',
				left : 'center'
			},
			legend : {
				top : 'bottom',
				data : [ '次/分' ]  
			},
			xAxis : {
				type : 'category',
				boundaryGap : false,
			},
			yAxis : {
				type : 'value',
				boundaryGap : [ 0, '100%' ]
			},
			dataZoom : [
					{
						type : 'inside',
						start : 0,
						end : 10
					},
					{
						start : 0,
						end : 10,
						handleIcon : 'M10.7,11.9v-1.3H9.3v1.3c-4.9,0.3-8.8,4.4-8.8,9.4c0,5,3.9,9.1,8.8,9.4v1.3h1.3v-1.3c4.9-0.3,8.8-4.4,8.8-9.4C19.5,16.3,15.6,12.2,10.7,11.9z M13.3,24.4H6.7V23h6.6V24.4z M13.3,19.6H6.7v-1.4h6.6V19.6z',
						handleSize : '80%',
						handleStyle : {
							color : '#fff',
							shadowBlur : 3,
							shadowColor : 'rgba(0, 0, 0, 0.6)',
							shadowOffsetX : 2,
							shadowOffsetY : 2
						}
					} ],
			series : [ {
				name : '次/分',
				type : 'line',
				smooth : true,
				symbol : 'none',
				sampling : 'average',
			} ]
		};
		myChart4.setOption(myChart4option);
	}*/
</script>
<script>
	function printit() {

		document.getElementById("print").style.display = "none";
		window.print();
		document.getElementById('print').style.display = "";
	}

	function generatePdf() {
		$.getJSON('../generatePdf/${baseReport.id}').done(function(data) {
			console.info(data.code)
			if (data.code == 2000) {
				window.location.href = "../showPdf/${baseReport.id}";
			}
		});
	}

	function showpdf() {
		window.location.href = "../showPdf/${baseReport.id}";
	}
	function showpdfZH() {
		window.location.href = "../showPdf/${baseReport.id}/zh";
	}
	function showpdfEN() {
		window.location.href = "../showPdf/${baseReport.id}/en";
	}

	function generateFullPdf() {
		$.get('../generatePdf/${baseReport.id}').done(function(data) {
			if (data.code == 2000) {
				window.open(data.message);
			}
		});
	}

	function save() {
		var d = $("#form").serialize();
		$.post("${basePath}admin/generateFullPdf/${baseReport.id}", d,
				function(result) {
					if (result.code == 2000) {
						window.location.href = result.message;
					}
				}, "json");
	}

	function deletepdf() {
		$.getJSON('../deleteFile/${baseReport.id}').done(function(data) {
			console.info(data)
			console.info(data.code)
			if (data.code == 2000) {
				alert("删除成功")
			}
		});
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
	<button id="print" onclick="deletepdf()">删除已生成的pdf</button>
	<button id="print" onclick="showpdfZH()">查看中文pdf</button>
	<button id="print" onclick="showpdfEN()">查看英文pdf</button>
	<button id="print" onclick="history.go(-1)">返回</button>
	<c:if test="${!file }">
		<form enctype="multipart/form-data"
			action="${basePath}admin/uploadFile" method="post">
			<input type="file" name="file" /> <input type="submit" value="上传文件" /><input
				type="hidden" value="${baseReport.id}" name="id" />
		</form>
	</c:if>
	<c:if test="${file }">
	文件已存在！！！
	<a href="${basePath}admin/showFile/${baseReport.id}"><input
			type="button" value="下载数据文件"></input></a>
		<form enctype="multipart/form-data"
			action="${basePath}admin/uploadFile" method="post">
			<input type="file" name="file" /> <input type="submit" value="上传文件" /><input
				type="hidden" value="${baseReport.id}" name="id" />
		</form>
	</c:if>
	<!--begin-->
	<div id="container">
		<div class="searcbar"
			style="font-size: 18px; font-family: monospace; height: 25px;">睡眠呼吸监测报告</div>

		<div class="topbar"
			style="font-size: 12px; font-family: initial; text-align: inherit; height: 120px; width: 800px">
			<div class="titlebar">
				<h1>用户信息</h1>
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
			&nbsp;&nbsp;&nbsp;&nbsp;住址:${user.address} <br /> 开始时间:
			<fmt:formatDate value="${baseReport.startTime}"
				pattern="yyyy-MM-dd HH:mm:ss" />
			, 结束时间：
			<fmt:formatDate value="${baseReport.endTime}"
				pattern="yyyy-MM-dd HH:mm:ss" />
			,监测时长:${baseReport.monitoringDurationString }
		</div>

		<div class="column" style="width: 800px;">
			<div class="titlebar">
				<h1>呼吸统计</h1>
			</div>
			<div class="content-inner">
				呼吸紊乱指数 : ${baseReport.apneaHypopneaIndex} 呼吸暂停次数 :
				${baseReport.apneaTimes}次 最长呼吸暂停 :
				${baseReport.longestApneaSecondsString}, 发生于
				<fmt:formatDate value="${baseReport.longestApneaTime}"
					pattern="HH:mm:ss" />
				呼吸暂停总时长 : ${baseReport.totalApneaTimeSecondsString} <br /> 低通气次数 :
				${baseReport.hypopneaTimes}次 最长低通气 :
				${baseReport.maxHyponeaSecondsString }, 发生于
				<fmt:formatDate value="${baseReport.hyponeaHappenDate}"
					pattern="HH:mm:ss" />
				低通气总时长 : ${baseReport.totalHypoventilationTimeSecondsString } 呼吸质量评分
				: ${baseReport.brathe_score}
			</div>
		</div>

		<div id="contentarea">
			<div class="column" style="width: 800px; height: 850px;">
				<div class="titlebar">
					<h1>血氧饱和度分析</h1>
				</div>
				<div class="content-inner">
					平均血氧饱和度: ${baseReport.averageOxygenSaturation}% 最低血氧饱和度 :
					${baseReport.minOxygenSaturation}% 血氧饱和度低于90% :
					${baseReport.oxygenSaturationLessthanNinetyPercent}% 氧减指数 :
					${baseReport.oxygenReductionIndex} 最大氧降 :
					${baseReport.maxOxygenReduceSecondsString}, 发生于
					<fmt:formatDate value="${baseReport.maxOxygenReduceTime}"
						pattern="HH:mm:ss" />
					<br /> 最长氧降 时长 : ${baseReport.longestOxygenReduceSecondsString},
					发生于
					<fmt:formatDate value="${baseReport.longestOxygenReduceTime}"
						pattern="HH:mm:ss" />
					氧减危害指数 : ${baseReport.bloodOxygenHazardIndex}
				</div>



				<div class="content-inner" id="chart1"
					style="width: 390px; height: 175px; float: left">
					<h4 style="text-align: center; margin-bottom: 5px;">氧减分布图</h4>
				</div>
				<div class="content-inner" id="chart2"
					style="width: 390px; height: 175px; float: left">
					<h4 style="text-align: center; margin-bottom: 5px;">血氧分布图</h4>
				</div>
				<div class="content-inner" id="chart3"
					style="width: 800px; height: 500px; float: left"></div>
			</div>
			<div class="column" style="width: 800px; height: 75px">
				<div class="titlebar">
					<h1>脉率分析</h1>
				</div>
				<div class="content-inner">
					平均脉率 : ${baseReport.averagePulse} 次/分钟 最低脉率 :
					${baseReport.minPulse} 次/分钟, 发生于
					<fmt:formatDate value="${baseReport.minPulseTime}"
						pattern="HH:mm:ss" />
					最高脉率 : ${baseReport.maxPulse} 次/分钟, 发生于
					<fmt:formatDate value="${baseReport.maxPulseTime}"
						pattern="HH:mm:ss" />
				</div>
			</div>
			<div class="column" style="width: 800px;">
				<div class="titlebar">
					<h1>睡眠分析</h1>
				</div>
				<div class="content-inner">
					睡眠时间 : ${baseReport.totalSecondsString} 深睡时长 :
					${baseReport.deepSleepSecondsString} 深睡眠占比 :
					<fmt:formatNumber
						value="${baseReport.deepSleepSeconds * 100 / baseReport.totalSeconds}"
						maxFractionDigits="2" />
					% 浅睡时长 : ${baseReport.lightSleepSecondsString} 浅睡眠占比 :
					<fmt:formatNumber
						value="${baseReport.lightSleepSeconds * 100 / baseReport.totalSeconds}"
						maxFractionDigits="2" />
					% <br /> 睡眠质量评分 : ${baseReport.sleep_score}
				</div>
			</div>
			<div class="column" style="width: 800px;">
				<div class="titlebar">
					<h1>分析结果</h1>
				</div>
				<div class="content-inner">
					结论 : 1. 睡眠呼吸暂停低通气综合症：${baseReport.sleepResult }<br />
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2.
					睡眠低氧血症：${baseReport.spO2Result }<br /> 建议 : 结果请结合临床。
				</div>
			</div>
		</div>
	</div>
</body>
</html>
