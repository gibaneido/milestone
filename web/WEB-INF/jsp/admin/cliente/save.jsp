<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ include file="/admin/inc/taglibs.jsp" %>
<html>
	<head>
		<title>Cadastro Cliente</title>
		<%@ include file="/admin/inc/taghead.jsp" %>
	</head>
	<body>
		<div id="container">
			<%@ include file="/admin/inc/header.jsp" %>
			<%@ include file="/admin/inc/nav.jsp" %>
			<div id="content">
				<h2>Novo Cliente</h2>
				<p class="validateTips">Os campos com * são obrigat&oacute;rios.</p>
				<ul class="menu-category clearfix">
					<li><a id="add-cliente" href="#">Salvar</a></li>
				</ul>
				<div class="boxForm">
					<form id="novoCliente" method="post" action="${pageContext.request.contextPath}/admin/cliente/save.jhtml">
						<c:if test="${!empty cliente.id}">
							<input type="hidden" name="id" value="${cliente.id}"/>
						</c:if>
						<c:if test="${empty cliente.id && !empty param.idCliente}">
							<input type="hidden" name="id" value="${param.idCliente}"/>
						</c:if>
						<div class="boxAba">
							<p>
								<label>Raz&atilde;o Social*</label>
								<input type="text" maxlength="45" name="razaoSocial" id="razaoSocial" value="${cliente.razaoSocial}" class="text ui-widget-content ui-corner-all big" />
							</p>
							<p>
								<label>Cnpj*</label>
								<input type="text" maxlength="45" name="cnpj" id="cnpj" value="${cliente.cnpj}" class="text ui-widget-content ui-corner-all medium" />
							</p>
							<p>
								<!-- input type="submit" value="OK"/-->
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
	<script type="text/javascript" language="JavaScript" src="${pageContext.request.contextPath}/js/cliente/functions.js"></script>
</html>