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
				<h2>Fornecedores</h2>
				<c:if test="${sessionScope.usuarioLogado.god || (sessionScope.usuarioLogado.perfil.id ne 2 && sessionScope.usuarioLogado.perfil.id ne 3)}">
					<ul class="menu-category clearfix">
						<li><a href="${pageContext.request.contextPath}/admin/fornecedor/novo.jhtml">Novo</a></li>
					</ul>
				</c:if>
				<form name="filtro" action="${pageContext.request.contextPath}/admin/fornecedor/list.jhtml" method="GET">
					<input type="hidden" name="pageScreen" value="0"/>
					<table class="grid">
						<colgroup>
							<col width="50%">
							<col width="50%">
						</colgroup>
						<tr>
							<th>RAZ&Atilde;O SOCIAL</th>
							<th>CNPJ</th>
						</tr>
						<c:forEach var="fornecedor" items="${fornecedorList}">
							<tr>
								<td>
									<c:choose>
										<c:when test="${sessionScope.usuarioLogado.god || (sessionScope.usuarioLogado.perfil.id ne 2 && sessionScope.usuarioLogado.perfil.id ne 3)}">
											<a href="${pageContext.request.contextPath}/admin/fornecedor/editar.jhtml?idFornecedor=${fornecedor.id}">
												<c:out value="${fornecedor.razaoSocial}"/>
											</a>
										</c:when>
										<c:otherwise>
											<c:out value="${fornecedor.razaoSocial}"/>
										</c:otherwise>
									</c:choose>
								</td>
								<td><c:out value="${fornecedor.cnpj}"/></td>
							</tr>
						</c:forEach>
						</tr>
						<!-- Caso nao tenha registro -->
						<c:if test="${totalPages == 0}">
							<tr>
								<td colspan="2">Nenhum registro encontrado</td>
							</tr>
						</c:if>
					</table>
					<c:if test="${totalPages > 0}">
						<div class="pagination">
							<ul class="clearfix">
								<c:forEach begin="0" end="${(totalPages/15)-1}" var="i">
									<li>
										<c:choose>
											<c:when test="${(param.pageScreen eq null && i eq 0) || param.pageScreen eq i}">
												<a href="#" class="active" onclick="javascript:submete('<c:out value="${i}"/>');" ><c:out value="${i+1}"/></a>
											</c:when>
											<c:otherwise>
												<a href="#" onclick="javascript:submete('<c:out value="${i}"/>');" ><c:out value="${i+1}"/></a>
											</c:otherwise>
										</c:choose>
									</li>
								</c:forEach>
							</ul>
						</div>
					</c:if>
				</form>
			</div>
			<div class="frame ac">
				<p> </p>
			</div>
			<div id="footer">
			</div>
		</div>
	</body>
</html>