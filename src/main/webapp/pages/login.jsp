<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/form.css" />
<title>Login</title>
</head>

<body>
	<div>
		<h1>
			BIENVENIDO A <i>AIRECYD</i>
		</h1>
	</div>
	<form method="post" action="Login">
		<fieldset class="fieldsetlogin">
			<div>
				<label>Username <input class="inputdata" type="text"
					name="usernameLogin" placeholder="USUARIO" required></label>
			</div>
			<div>
				<label>Password <input class="inputdata" type="password"
					name="passwordLogin" required></label>
			</div>

		</fieldset>
		<div>
			<input class="button" id=buttonLogin type="submit">
		</div>
		<div>
			<button class="button"
				onclick="location.href='${pageContext.request.contextPath}/pages/index.html#!/registrar'">Register
				for free!</button>
		</div>
	</form>
	<c:if test="${error eq true}"> Usuario y/o contrasenya erroneos </c:if>

</body>

</html>
