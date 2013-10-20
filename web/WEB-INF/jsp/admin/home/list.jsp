<%@page import="br.com.projeto.util.ApplicationProperties"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:if test="${empty sessionScope.moderator}">
	<jsp:forward page="/moderator/login.jhtml" /> 
</c:if>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>Nivea AngelStar</title>
		<link rel="stylesheet" type="text/css" href="css/reset.css" />
		<link rel="stylesheet" type="text/css" href="css/admin.css" />
	</head>
	<body>
		<div id="container">
			<div class="frame ar">
				<p>Bem-vindo, <strong><c:out value="${sessionScope.moderator.name}"/></strong>. <a href="/moderator/logout.jhtml">Sair</a></p>
			</div>
			<div id="header">
				<h1></h1>
			</div>
			<div id="content">
				<h2>Lista de Pages</h2>
				<form name="filtro" action="/moderator/list-pages.jhtml" method="GET">
					<input type="hidden" name="pageScreen" value="0"/>
					<fieldset class="filter">
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
					</fieldset>
					<table class="grid">
						<colgroup>
							<col width="10%">
							<col width="20%">
							<col width="50%">
							<col width="20%">
						</colgroup>
						<tr>
							<th>PAGE ID</th>
							<th>USER</th>
							<th>FOTOS</th>
							<th>STATUS</th>
						</tr>
						<c:forEach var="page" items="${pageList}">
							<tr>
								<td><c:out value="${page.id}"/></td>
								<td><a href="http://apps.facebook.com/<%=ApplicationProperties.get("facebook.appName")%>/<c:out value="${page.user.pageCode}"/>" target="_blank"><c:out value="${page.user.name}"/></a></td>
								<td>
									<img width="211" src="/fotos/<c:out value="${page.user.facebookId}"/>/feature.jpg">
									<img width="100" src="/fotos/<c:out value="${page.user.facebookId}"/>/avatar.jpg">
								</td>
								<td>
									<c:if test="${empty param.status || param.status eq 'E'}">
										<a href="#" onclick="javascript:setStatus('<c:out value="${page.id}"/>','D');" class="button">Inativar</a>
									</c:if>
									<c:if test="${param.status eq 'D'}">
										Inativada
									</c:if>
								</td>
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
				<form name="filtro" action="/moderator/set-status.jhtml" method="GET">
					<input type="hidden" name="pageId"/>
					<input type="hidden" name="status"/>
				</form>
			</div>
			<div class="frame ac">
				<p> </p>
			</div>
			<div id="footer">
			</div>
		</div>
	<script type="text/javascript" language="JavaScript" src="js/libs/jquery-1.5.2.min.js"></script>
	<script type="text/javascript" language="JavaScript" src="js/admin.js"></script>
	<script type="text/javascript" language="JavaScript" src="js/validation.js"></script>
	<script>
		function submete(pagina){
			document.forms[0].pageScreen.value = pagina;
			document.forms[0].submit();
		}
		function setStatus(pageId,status){
			if(status == 'D'){
				if(confirm("Tem certeza que deseja Inativar?")){
					document.forms[1].pageId.value = pageId;
					document.forms[1].status.value = 'D';
					document.forms[1].submit();
				}
			}
		}
		<c:if test="${!empty returnMessage}">
			alert('<c:out value="${returnMessage}"/>');
		</c:if>
	</script>
	</body>
</html>