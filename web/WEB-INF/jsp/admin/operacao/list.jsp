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
				<h2>Opera&ccedil;&otilde;es</h2>
				<c:if test="${sessionScope.usuarioLogado.god || (sessionScope.usuarioLogado.perfil.id ne 2 && sessionScope.usuarioLogado.perfil.id ne 3)}">
					<ul class="menu-category clearfix">
						<li><a id="apontar" href="#">% Evolu&ccedil;&atilde;o</a></li>
						<li><a id="add-operacao" href="#">Novo</a></li>
					</ul>
				</c:if>
				<form name="filtro" action="${pageContext.request.contextPath}/admin/operacao/list.jhtml" method="GET">
					<input type="hidden" name="pageScreen" value="0"/>
					<fieldset class="filter">
						<ul class="clearfix">
							<li>
								<label>
									Projeto :
									<select name="idProjeto" id="idProjeto" >
										<option value="">Selecione o Projeto</option>
										<c:forEach var="projeto" items="${projetoList}">
											<option value="<c:out value="${projeto.id}"/>" <c:if test="${projeto.id eq idProjeto}">selected="selected"</c:if>><c:out value="${projeto.codigo}"/></option>
										</c:forEach>
									</select>
								</label>
							</li>
							<li>
								<!-- input type="submit" value="filtrar" /-->
							</li>
						</ul>
					</fieldset>
					<div class="split">
						<div class="master">
							<ol id="selectable">
								<c:forEach var="produto" items="${produtoList}">
									<li id="${produto.id}" class="ui-widget-content">${produto.descricao}</li>
								</c:forEach>
							</ol>
						</div>
						<div class="detail">
							<table class="grid" id="grid">
						<colgroup>
							<col width="10%">
							<col width="30%">
							<col width="20%">
							<col width="10%">
							<col width="10%">
							<col width="10%">
							<col width="10%">
						</colgroup>
								<thead>
									<tr>
										<th class="cinza">OP</th>
										<th>DESCRI&Ccedil;&Atilde;O</th>
										<th>N&Uacute;MERO</th>
										<th>ESQUERDA</th>
										<th>DIREITA</th>
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
			<jsp:param name="nivel" value="operacao"/>
			<jsp:param name="nivelTitle" value="OPERAÇÃO"/>
		</jsp:include>
		<div id="dialog-confirm" title="Alerta" style="display:none">
			<p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>Tem certeza que deseja remover a opera&ccedil;&atilde;o?</p>
		</div>
	</body>
	<script type="text/javascript" language="JavaScript" src="${pageContext.request.contextPath}/js/operacao/functions.js"></script>
	<script>
		setProjetoApontamento(${idProjeto});	
	</script>
</html>