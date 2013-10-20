<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ include file="/admin/inc/taglibs.jsp" %>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
		<title>Admin</title>
		<%@ include file="/admin/inc/taghead.jsp" %>
	</head>
	<body class="login">
		<div id="container">
			<div id="content">
				<fieldset id="formLogin">
					<c:if test="${!empty msgError}">
						<p class="box_errors"><c:out value="${msgError}"/></p>
					</c:if>
					
					
					<form action="${pageContext.request.contextPath}/admin/login.jhtml" method="post" id="formLogin">
						<p>
							<label>E-mail:</label>
							<input type="text" name="email" class="text" id="txtemail" />
						</p>
						<p>
							<label>Senha:</label>
							<input type="password" name="password" class="text" id="txtsenha" />
						</p>
						<p class="ar">
							<input type="submit" class="button" value="Send" />
						</p>
					</form>
				</fieldset>
			</div>
		</div>
	</body>
</html>