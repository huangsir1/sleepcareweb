<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<style type="text/css">
.datasource {
	float: left;
	width: 33%;
	line-height: 20px;
}
</style>
<div id="container">
	<div class="searcbar"
		style="font-size: 18px; font-family: monospace; height: 25px;">睡眠呼吸监测报告</div>

	<div class="topbar"
		style="font-size: 12px; font-family: initial; text-align: inherit; height: 145px; width: 800px">
		<div class="titlebar">
			<h1>用户信息</h1>
		</div>
		﻿
		<div class="datasource">姓名:${user.name}</div>
		<div class="datasource">性别:${user.gender}</div>
		<div class="datasource">
			生日:
			<fmt:formatDate value="${user.birthday}" pattern="yyyy-MM-dd" />
		</div>
		<div class="datasource">体重:${user.weight}kg</div>
		<br />
		<div class="datasource">身高:${user.height}cm</div>
		<div class="datasource">ess总分:${user.essRank}</div>
		<div class="datasource">
			BMI:
			<fmt:formatNumber
				value="${10000 * user.weight / user.height / user.height}"
				pattern="##.##" minFractionDigits="2"></fmt:formatNumber>
		</div>
		<div class="datasource">电话:${user.phone}</div>
		<br />
		<div class="datasource">住址:${user.address}</div>
		<br /> 疾病史:
		<c:forEach items="${user. diseaseHistories}" var="d">	${d.name}</c:forEach>
		<br />
		<div class="datasource">
			开始时间:
			<fmt:formatDate value="${baseReport.startTime}"
				pattern="yyyy-MM-dd HH:mm:ss" />
		</div>
		<div class="datasource">
			结束时间：
			<fmt:formatDate value="${baseReport.endTime}"
				pattern="yyyy-MM-dd HH:mm:ss" />
		</div>
		<div class="datasource">
			监测时长:${baseReport.monitoringDurationString }</div>

	</div>

	<div class="column" style="width: 800px; height: 120px;">
		<div class="titlebar">
			<h1>呼吸统计</h1>
		</div>
		<div class="content-inner">
			﻿
			<div class="datasource">呼吸紊乱指数 :
				${baseReport.apneaHypopneaIndex}</div>
			<div class="datasource">呼吸暂停次数 : ${baseReport.apneaTimes}次</div>
			<div class="datasource">最长呼吸暂停 :
				${baseReport.longestApneaSecondsString}</div>
			<div class="datasource">
				发生于
				<fmt:formatDate value="${baseReport.longestApneaTime}"
					pattern="HH:mm:ss" />
			</div>
			<div class="datasource">呼吸暂停总时长 :
				${baseReport.totalApneaTimeSecondsString}</div>
			<br />
			<div class="datasource">低通气次数 : ${baseReport.hypopneaTimes}次</div>
			<div class="datasource">最长低通气 :
				${baseReport.maxHyponeaSecondsString }</div>
			<div class="datasource">
				发生于
				<fmt:formatDate value="${baseReport.hyponeaHappenDate}"
					pattern="HH:mm:ss" />
			</div>
			<div class="datasource">低通气总时长 :
				${baseReport.totalHypoventilationTimeSecondsString }</div>
			<div class="datasource">呼吸质量评分 : ${baseReport.brathe_score}</div>

		</div>
	</div>

	<div id="contentarea">
		<div class="column" style="width: 800px; height: 950px;">
			<div class="titlebar">
				<h1>血氧饱和度分析</h1>
			</div>
			<div class="content-inner">
				﻿
				<div class="datasource">平均血氧饱和度:
					${baseReport.averageOxygenSaturation} %</div>
				<div class="datasource">最低血氧饱和度 :
					${baseReport.minOxygenSaturation} %</div>
				<div class="datasource">血氧饱和度低于90% :
					${baseReport.oxygenSaturationLessthanNinetyPercent} %</div>
				<div class="datasource">氧减指数 :
					${baseReport.oxygenReductionIndex}</div>
				<div class="datasource">最大氧降 :
					${baseReport.maxOxygenReduceSecondsString}</div>
				,
				<div class="datasource">
					发生于
					<fmt:formatDate value="${baseReport.maxOxygenReduceTime}"
						pattern="HH:mm:ss" />
				</div>
				<br />
				<div class="datasource">最长氧降 时长 :
					${baseReport.longestOxygenReduceSecondsString}</div>
				,
				<div class="datasource">
					发生于
					<fmt:formatDate value="${baseReport.longestOxygenReduceTime}"
						pattern="HH:mm:ss" />
				</div>
				<div class="datasource">氧减危害指数 :
					${baseReport.bloodOxygenHazardIndex}</div>

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
				﻿
				<div class="datasource">平均脉率 : ${baseReport.averagePulse}次/分钟</div>
				<div class="datasource">
					最低脉率 : ${baseReport.minPulse}次/分钟, 发生于
					<fmt:formatDate value="${baseReport.minPulseTime}"
						pattern="HH:mm:ss" />
				</div>
				<div class="datasource">
					最高脉率 : ${baseReport.maxPulse}次/分钟, 发生于
					<fmt:formatDate value="${baseReport.maxPulseTime}"
						pattern="HH:mm:ss" />
				</div>

			</div>
		</div>
		<div class="column" style="width: 800px; height: 85px">
			<div class="titlebar">
				<h1>睡眠分析</h1>
			</div>
			<div class="content-inner">
				﻿
				<div class="datasource">睡眠时间 :
					${baseReport.totalSecondsString}</div>
				<div class="datasource">深睡时长 :
					${baseReport.deepSleepSecondsString}</div>
				<div class="datasource">
					深睡眠占比 :
					<fmt:formatNumber
						value="${baseReport.deepSleepSeconds * 100 / baseReport.totalSeconds}"
						maxFractionDigits="2" />
					%
				</div>
				<div class="datasource">浅睡时长 :
					${baseReport.lightSleepSecondsString}</div>
				<div class="datasource">
					浅睡眠占比 :
					<fmt:formatNumber
						value="${baseReport.lightSleepSeconds * 100 / baseReport.totalSeconds}"
						maxFractionDigits="2" />
					%
				</div>
				<br />
				<div class="datasource">睡眠质量评分 : ${baseReport.sleep_score}</div>

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

