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
				<h2>Sub-Posi&ccedil;&otilde;es</h2>
				<c:if test="${sessionScope.usuarioLogado.god || (sessionScope.usuarioLogado.perfil.id ne 2 && sessionScope.usuarioLogado.perfil.id ne 3)}">
					<ul class="menu-category clearfix">
						<li><a id="apontar" href="#">% Evolu&ccedil;&atilde;o</a></li>
						<li><a id="add-sub-posicao" href="#">Novo</a></li>
					</ul>
				</c:if>
				<form name="filtro" action="${pageContext.request.contextPath}/admin/sub-posicao/list.jhtml" method="GET">
					<fieldset class="filter">
						<ul class="clearfix">
							<li>
								<label>
									Projeto :
									<select name="idProjeto" id="idProjeto">
										<option value="">Selecione o Projeto</option>
										<c:forEach var="projeto" items="${projetoList}">
											<option value="<c:out value="${projeto.id}"/>" <c:if test="${projeto.id eq idProjeto}">selected="selected"</c:if>><c:out value="${projeto.codigo}"/></option>
										</c:forEach>
									</select>
								</label>
							</li>
							<li>
								<label>
									Produto :
									<select name="idProduto" id="idProduto">
										<option value="">Selecione o Produto</option>
										<c:forEach var="produto" items="${produtoList}">
											<option value="<c:out value="${produto.id}"/>" <c:if test="${produto.id eq idProduto}">selected="selected"</c:if>><c:out value="${produto.descricao}"/></option>
										</c:forEach>
									</select>
								</label>
							</li>
							<li>
								<label>
									Opera&ccedil;&atilde;o :
									<select name="idOperacao" id="idOperacao">
										<option value="">Selecione a Opera&ccedil;&atilde;o</option>
										<c:forEach var="operacao" items="${operacaoList}">
											<option value="<c:out value="${operacao.id}"/>" <c:if test="${operacao.id eq idOperacao}">selected="selected"</c:if>>${operacao.op}-${operacao.descricao}</option>
										</c:forEach>
									</select>
								</label>
							</li>
							<li>
								<label>
									Unidade :
									<select name="idUnidade" id="idUnidade">
										<option value="">Selecione a Unidade</option>
										<c:forEach var="unidade" items="${unidadeList}">
											<option value="<c:out value="${unidade.id}"/>" <c:if test="${unidade.id eq idUnidade}">selected="selected"</c:if>><c:out value="${unidade.numero}"/></option>
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
								<c:forEach var="posicao" items="${posicaoList}">
									<li id="${posicao.id}" class="ui-widget-content">${posicao.posicao}</li>
								</c:forEach>
							</ol>
						</div>
						<div class="detail">
							<table class="grid" id="grid">
								<colgroup>
									<col width="30%">
									<col width="30%">
									<col width="10%">
									<col width="10%">
									<col width="10%">
									<col width="10%">
								</colgroup>
								<thead>
									<tr>
										<th class="cinza">SUB-POSI&Ccedil;&Atilde;O</th>
										<th>DESCRI&Ccedil;&Atilde;O</th>
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
			<jsp:param name="nivel" value="sub-posicao"/>
			<jsp:param name="nivelTitle" value="SUB-POSIÇÃO"/>
		</jsp:include>
		<div id="dialog-confirm" title="Alerta" style="display:none">
			<p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>Tem certeza que deseja remover a sub-posi&ccedil;&atilde;o?</p>
		</div>
	</body>
	<script type="text/javascript" language="JavaScript" src="${pageContext.request.contextPath}/js/sub-posicao/functions.js"></script>
	<script>
		setProjetoApontamento(${idProjeto});
	</script>
</html>