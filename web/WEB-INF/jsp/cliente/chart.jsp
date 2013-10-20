<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ include file="/admin/inc/taglibs.jsp" %>
<html>
	<head>
		<title>EA Milestone</title>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/reset.css" />
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/admin.css" />
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/ui.css" />
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/blitzer/jquery-ui-1.8.17.custom.css" />
		
		<script type="text/javascript" language="JavaScript" src="${pageContext.request.contextPath}/js/admin.js"></script>
		<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.6.1/jquery.min.js"></script>
		<script type="text/javascript" language="JavaScript" src="${pageContext.request.contextPath}/js/libs/jquery-ui-1.8.17.custom.min.js"></script>
		<script type="text/javascript" src="../js/chart/highcharts.js"></script>
		<script type="text/javascript" src="../js/chart/modules/exporting.js"></script>
		<script type="text/javascript">
		var chart;
		$(document).ready(function() {
			chart = new Highcharts.Chart({
				chart: {
					renderTo: 'container-chart',
					defaultSeriesType: 'spline'
				},
				title: {
					text: '<c:out value="${projeto.codigo}"/>'
				},
				subtitle: {
					text: 'Engineering Assembly'
				},
				xAxis: {
					categories: [
						<c:forEach var="data" items="${realizadoChartData}">
							'<fmt:formatDate value="${data.point}" pattern="dd/MM/yyyy"/>',
						</c:forEach>
					]
				},
				yAxis: {
					title: {
						text: 'Porcentagem'
					},
					labels: {
						formatter: function() {
							return this.value +'%';
						}
					},
					min:0,
					max:100
					
				},
				tooltip: {
					crosshairs: true,
					shared: true
				},
				plotOptions: {
					spline: {
						marker: {
							radius: 4,
							lineColor: '#666666',
							lineWidth: 1
						}
					}
				},
				series: [{
					name: 'Realizado',
					marker: {
						symbol: 'square'
					},
					data: [
						<c:forEach var="data" items="${realizadoChartData}">
							<c:out value="${data.porcentagem}"/>,
						</c:forEach>
					]
			
				}, {
					name: 'Previsto',
					marker: {
						symbol: 'diamond'
					},
					data: [
						<c:forEach var="data" items="${previstoChartData}">
							<c:out value="${data.porcentagem}"/>,
						</c:forEach>
					]
				}]
			});
			
			
		});
	</script>
	</head>
	<body>
		<div id="container">
			<%@ include file="/admin/inc/header.jsp" %>
			<div id="content">
				<h2>Gráficos GT's</h2>
				<!-- form name="filtro" action="${pageContext.request.contextPath}/admin/projeto/list.jhtml" method="GET"-->
					<input type="hidden" name="pageScreen" value="0"/>
					<div class="split">
						<div class="master" style="width:10%">
							<ol id="selectable">
								<c:forEach var="projeto" items="${projetoList}">
									<c:set var="classe" value=""/>
									<c:if test="${project.id == projeto.id}">
										<c:set var="classe" value=" selected"/>
									</c:if>
									<li id="${projeto.id}" class="ui-widget-content${classe}" style="width:122px">
										<a href="/milestone/cliente/chart.jhtml?idCliente=${idCliente}&idProjeto=${projeto.id}">${projeto.codigo}</a>
									</li>
								</c:forEach>
							</ol>
						</div>
						<div class="detail" style="width:90%">
							<div id="container-chart" style="width: 99%; height: 99%; margin: 0 auto"></div>
						</div>
						<br style="clear:both">
					</div>
				<!-- /form-->
			</div>
			<div class="frame ac">
				<p> </p>
			</div>
			<div id="footer">
			</div>
		</div>
	</body>
</html>