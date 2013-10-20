<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ include file="/admin/inc/taglibs.jsp" %>
<html>
	<head>
		<title>Cadastro GT</title>
		<%@ include file="/admin/inc/taghead.jsp" %>
	</head>
	<body>
		<div id="container">
			<%@ include file="/admin/inc/header.jsp" %>
			<%@ include file="/admin/inc/nav.jsp" %>
			<div id="content">
				<h2>Nova GT</h2>
				<p style="display:none" class="box_errors">
					<label>Verifique os campos em vermelho para cadastrar uma nova GT</label>
				</p>
				<p class="validateTips">Os campos com * são obrigat&oacute;rios.</p>
				<ul class="menu-category clearfix">
					<li><a id="create-gt" href="#">Salvar</a></li>
				</ul>
				<div class="boxForm">
					<form id="novoProjeto" method="post" action="${pageContext.request.contextPath}/admin/projeto/save.jhtml">
						<c:if test="${!empty projeto.id}">
							<input type="hidden" name="id" value="${projeto.id}"/>
							<input type="hidden" name="codigo" value="${projeto.codigo}"/>
						</c:if>
						<c:if test="${empty projeto.id && !empty param.idProjeto}">
							<input type="hidden" name="id" value="${param.idProjeto}"/>
						</c:if>
						<div class="boxAba">
							<p>
								<label>Descri&ccedil;&atilde;o*</label>
								<input type="text" maxlength="45" name="descricao" id="descricao" value="${projeto.descricao}" class="text ui-widget-content ui-corner-all big" />
							</p>
							<p>
								<label for="cliente"><span></span>Cliente Final*</label>
								<select id="idCliente" name="idCliente">
									<option value="0">Selecione o Cliente</option>
									<c:forEach var="cliente" items="${clientList}">
										<option value="<c:out value="${cliente.id}"/>" <c:if test="${cliente.id eq projeto.cliente.id}">selected="selected"</c:if>><c:out value="${cliente.razaoSocial}"/></option>
									</c:forEach>
								</select>
							</p>
							<p>
								<label for="clienteFinal"><span></span>Cliente Final</label>
								<select id="idClienteFinal" name="idClienteFinal">
									<option value="0">Selecione o Cliente</option>
									<c:forEach var="cliente" items="${clientList}">
										<option value="<c:out value="${cliente.id}"/>" <c:if test="${cliente.id eq projeto.clienteFinal.id}">selected="selected"</c:if>><c:out value="${cliente.razaoSocial}"/></option>
									</c:forEach>
								</select>
							</p>
							<p>
								<label>Data In&iacute;cio</label>
								<input type="text" maxlength="10" name="dataInicio" value="<fmt:formatDate pattern="dd/MM/yyyy" value="${projeto.dataInicio}"/>" class="text ui-widget-content ui-corner-all medium datepicker" />
							</p>
							<p>
								<label>Data Fim</label>
								<input type="text" maxlength="10" name="dataFim" value="<fmt:formatDate pattern="dd/MM/yyyy" value="${projeto.dataFim}"/>" class="text ui-widget-content ui-corner-all medium datepicker" />
							</p>
							
							<p>
								<label>Etapas</label><br/>
								<c:forEach var="etapa" items="${etapaList}">
									<c:forEach var="etapaProjeto" items="${projeto.etapas}">
										<c:if test="${etapa.id eq etapaProjeto.id}">
											<c:set var="checked" value="checked=\"checked\""/>
										</c:if>
									</c:forEach>
									<input type="checkbox" name="etapaIds" value="${etapa.id}" ${checked} />${etapa.descricao}<br/>
									<c:set var="checked" value=""/>
								</c:forEach>
							</p>
						</div>
					</form>
				</div>
			</div>
			<div class="frame ac">
			</div>
			<div id="footer">
			</div>
		</div>
	</body>
	<script type="text/javascript" language="JavaScript" src="${pageContext.request.contextPath}/js/projeto/functions.js"></script>
</html>