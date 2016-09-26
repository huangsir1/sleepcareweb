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
 <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <%--<meta http-equiv="X-UA-Compatible" content="IE=7"></meta>--%>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
<title>${user.name}的报告</title>
<script language="JavaScript" type="text/javascript"
	src="${basePath}js/jquery1.9.js" charset="utf-8"></script>
<link href="${basePath}css/internet.css" rel="stylesheet"
	type="text/css" />
<link href="${basePath}css/g2.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" type="text/javascript"
	src="${basePath}js/g2.js"></script>
<script language="JavaScript" type="text/javascript"
	src="${basePath}js/g2plugin.js"></script>
<script language="JavaScript" type="text/javascript"
	src="${basePath}js/util.js"></script>
<script>
	$.getJSON('${basePath}/admin/getHyponeaDataNew/18688bac-65e3-4e40-886e-bc2a2edaf617', function(data) {
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

	$.getJSON('${basePath}/admin/getReportData/18688bac-65e3-4e40-886e-bc2a2edaf617', function(data) {
		var flowChart = new G2.Chart({
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
		flowChart.line().position('time*frequency').color('#B03A5B').size(2);
		flowChart.guide().text([ 'min', 'max' ], ' ');
		var rainChart = new G2.Chart({
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
			labels : null
		});
		rainChart.axis('percent', {
			title : null
		});
		rainChart.coord().reflect('y');
		rainChart.line().position('time*percent').color('#293c55').size(2);
		rainChart.guide().text([ 'min', 'max' ], ' ');
		$('<div id="range"></div>').appendTo('#chart3');
		var range = new G2.Plugin.range({
			id : 'range', //DOM id
			width : 800,
			height : 26,
			dim : 'time'
		});

		range.source(data);
		range.link([ flowChart, rainChart ]);
		range.render();
	});
</script>
<script>
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
	<!--begin-->

	<div class="content-inner" id="chart1"
		style="width: 390px; height: 175px; float: left">
		<h4 style="text-align: center; margin-bottom: 5px;">氧减分布图</h4>
	</div>
	<div class="content-inner" id="chart2"
		style="width: 390px; height: 175px; float: left">
		<h4 style="text-align: center; margin-bottom: 5px;">血氧分布图</h4>
	</div>
	<div class="content-inner" id="chart3"
		style="width: 1000px; height: 800px; float: left"></div>
	</div>

</body>
</html>
