<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ include file="/admin/inc/taglibs.jsp" %>
<html>
	<head>
		<title>Cadastro Usuário</title>
		<%@ include file="/admin/inc/taghead.jsp" %>
	</head>
	<body>
		<div id="container">
			<%@ include file="/admin/inc/header.jsp" %>
			<%@ include file="/admin/inc/nav.jsp" %>
			<div id="content">
				<h2>Novo Usuario</h2>
				<p class="validateTips">Os campos com * são obrigat&oacute;rios.</p>
				<ul class="menu-category clearfix">
					<li><a id="add-usuario" href="#">Salvar</a></li>
				</ul>
				<div class="boxForm">
					<p class="box_errors"><label></label></p>
					<form id="novoUsuario" method="post" action="${pageContext.request.contextPath}/admin/usuario/save.jhtml">
						<c:if test="${!empty usuario.id}">
							<input type="hidden" name="id" value="${usuario.id}"/>
						</c:if>
						<c:if test="${empty usuario.id && !empty param.idUsuario}">
							<input type="hidden" name="id" value="${param.idUsuario}"/>
						</c:if>
						<div class="boxAba">
							<p>
								<label>Nome*</label>
								<input type="text" maxlength="45" name="nome" id="nome" value="${usuario.nome}" class="text ui-widget-content ui-corner-all medium" />
							</p>							
							<p>
								<label>Email*</label>
								<input type="text" maxlength="100" name="email" id="email" value="${usuario.email}" class="text ui-widget-content ui-corner-all medium" />
							</p>
							<p>
								<label>Senha*</label>
								<input type="password" maxlength="100" name="senha" id="senha" class="text ui-widget-content ui-corner-all medium" />
							</p>							
							<p>
								<label for="perfil"><span></span>Perfil*</label>
								<select id="idPerfil" name="idPerfil">
									<option value="0">Selecione o Perfil</option>
									<c:forEach var="perfil" items="${perfilList}">
										<option value="<c:out value="${perfil.id}"/>" <c:if test="${perfil.id eq usuario.perfil.id}">selected="selected"</c:if>><c:out value="${perfil.descricao}"/></option>
									</c:forEach>
								</select>
							</p>
							<p>
								<label for="empreendimento"><span></span>Empreendimento*</label>
								<select id="idEmpreendimento" name="idEmpreendimento">
									<option value="0">Selecione o Empreendimento</option>
									<c:forEach var="empreendimento" items="${empreendimentoList}">
										<option value="<c:out value="${empreendimento.id}"/>" <c:if test="${empreendimento.id eq usuario.empreendimento.id}">selected="selected"</c:if>><c:out value="${empreendimento.descricao}"/></option>
									</c:forEach>
								</select>
							</p>
							<p>
								<!--  input type="submit" value="OK"/-->
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
	<script type="text/javascript" language="JavaScript" src="${pageContext.request.contextPath}/js/usuario/functions.js"></script>
</html>