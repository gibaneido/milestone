<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ include file="/admin/inc/taglibs.jsp" %>
<html>
	<head>
		<title>EA Milestone</title>
		
		<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.6.1/jquery.min.js"></script>
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
					text: 'GT-0001'
				},
				subtitle: {
					text: 'Engenering Assembly'
				},
				xAxis: {
					categories: ['01/2012', '02/2012', '03/2012', '04/2012', '05/2012', '06/2012', 
						'07/2012', '08/2012', '09/2012', '10/2012', '11/2012', '12/2012']
				},
				yAxis: {
					title: {
						text: 'Porcentagem'
					},
					labels: {
						formatter: function() {
							return this.value +'%'
						}
					}
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
					name: 'Previsto',
					marker: {
						symbol: 'square'
					},
					data: [7.0, 6.9, 19.5, 24.5, 28.2, 41.5, 55.2, {
						y: 56.5,
						marker: {
							symbol: 'url(../graphics/sun.png)'
						}
					}, 68.3, 78.3, 83.9, 100]
			
				}, {
					name: 'Realizado',
					marker: {
						symbol: 'diamond'
					},
					data: [{
						y: 3.9,
						marker: {
							symbol: 'url(../graphics/snow.png)'
						}
					}, 4.2, 5.7, 8.5, 11.9, 15.2, 17.0, 16.6, 14.2, 10.3, 6.6, 4.8]
				}]
			});
			
			
		});
	</script>
	</head>
	<body>
		<div id="container">
			<%@ include file="/admin/inc/header.jsp" %>
			<%@ include file="/admin/inc/nav.jsp" %>
			<div id="content">
				<h2>Etapas GT's</h2>
				<ul class="menu-category clearfix">
					<li><a id="save" href="#">Salvar</a></li>
				</ul>
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
										<a href="${pageContext.request.contextPath}/admin/projeto-etapa/list.jhtml?idProjeto=${projeto.id}">${projeto.codigo}</a>
									</li>
								</c:forEach>
							</ol>
						</div>
						<div class="detail" style="width:90%">
							<div id="container-chart" style="width: 800px; height: 400px; margin: 0 auto"></div>
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
	<script type="text/javascript" language="JavaScript" src="${pageContext.request.contextPath}/js/projeto-etapa/functions.js"></script>
</html>