<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ include file="/admin/inc/taglibs.jsp" %>
<html>
	<head>
		<title>EA Milestone</title>
		<%@ include file="/admin/inc/taghead.jsp" %>
	</head>
	<body>
		<div id="container">
			<%@ include file="/admin/inc/header.jsp" %>
			<%@ include file="/admin/inc/nav.jsp" %>
			<div id="content">
				<h2>Produtos</h2>
				<c:if test="${sessionScope.usuarioLogado.god || (sessionScope.usuarioLogado.perfil.id ne 2 && sessionScope.usuarioLogado.perfil.id ne 3)}">
					<ul class="menu-category clearfix">
						<li><a id="apontar" href="#">% Evolu&ccedil;&atilde;o</a></li>
						<li><a id="add-produto" href="#">Novo</a></li>
					</ul>
				</c:if>
				<!-- form name="filtro" action="${pageContext.request.contextPath}/admin/projeto/list.jhtml" method="GET"-->
					<input type="hidden" name="pageScreen" value="0"/>
					<div class="split">
						<div class="master">
							<ol id="selectable">
								<c:forEach var="projeto" items="${projetoList}">
									<li id="${projeto.id}" class="ui-widget-content">${projeto.codigo}</li>
								</c:forEach>
							</ol>
						</div>
						<div class="detail">
							<table class="grid" id="grid">
								<thead>
									<tr>
										<th class="cinza">DESCRI&Ccedil;&Atilde;O</th>
										<th>N&Uacute;MERO</th>
										<th>PORCENTAGEM</th>
										<th>&nbsp;</th>
									</tr>
								</thead>
								<tbody>
								</tbody>
							</table>
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
		<%@ include file="save.jsp" %>
		<jsp:include page="/inc/apontamento.jsp">
			<jsp:param name="nivel" value="produto"/>
			<jsp:param name="nivelTitle" value="PRODUTO"/>
		</jsp:include>
		<div id="dialog-confirm" title="Alerta" style="display:none">
			<p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>Tem certeza que deseja remover o produto?</p>
		</div>
	</body>
	<script type="text/javascript" language="JavaScript" src="${pageContext.request.contextPath}/js/produto/functions.js"></script>
</html>