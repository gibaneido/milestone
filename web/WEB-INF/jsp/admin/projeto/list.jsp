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
				<h2>GT's</h2>
				<c:if test="${sessionScope.usuarioLogado.god || (sessionScope.usuarioLogado.empreendimento.id eq 1 || sessionScope.usuarioLogado.empreendimento.id eq 8)}">
					<ul class="menu-category clearfix">
						<li><a href="${pageContext.request.contextPath}/admin/projeto/novo.jhtml">Novo</a></li>
					</ul>
				</c:if>
				<form name="filtro" action="${pageContext.request.contextPath}/admin/projeto/list.jhtml" method="GET">
					<input type="hidden" name="pageScreen" value="0"/>
					<!--  fieldset class="filter">
						<ul class="clearfix">
							<li>
								<label>
									Filter :
									<select name="status">
										<option value="E" <c:if test="${param.status eq 'E'}">selected</c:if> >Ativas</option>
										<option value="D" <c:if test="${param.status eq 'D'}">selected</c:if> >Inativas</option>
									</select>
								</label>
							</li>
							<li>
								<input type="submit" value="filtrar" />
							</li>
						</ul>
					</fieldset> -->
					<table class="grid">
						<colgroup>
							<col width="20%">
							<col width="30%">
							<col width="20%">
							<col width="30%">
						</colgroup>
						<tr>
							<th>GT</th>
							<th>DESCRI&Ccedil;&Atilde;O</th>
							<th>CLIENTE</th>
							<th>DATA FIM</th>
						</tr>
						<c:forEach var="projeto" items="${projetoList}">
							<tr>
								<td>
									<c:choose>
										<c:when test="${sessionScope.usuarioLogado.god || (sessionScope.usuarioLogado.empreendimento.id eq 1 || sessionScope.usuarioLogado.empreendimento.id eq 8)}">
											<a href="${pageContext.request.contextPath}/admin/projeto/editar.jhtml?idProjeto=${projeto.id}"><c:out value="${projeto.codigo}"/></a>	
										</c:when>
										<c:otherwise>
											<c:out value="${projeto.codigo}"/>
										</c:otherwise>
									</c:choose>
								</td>
								<td><c:out value="${projeto.descricao}"/></td>
								<td><c:out value="${projeto.cliente.razaoSocial}"/></td>
								<td><fmt:formatDate pattern="dd/MM/yyyy" value="${projeto.dataFim}"/></td>
							</tr>
						</c:forEach>
						</tr>
						<!-- Caso nao tenha registro -->
						<c:if test="${totalPages == 0}">
							<tr>
								<td colspan="4">Nenhum registro encontrado</td>
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