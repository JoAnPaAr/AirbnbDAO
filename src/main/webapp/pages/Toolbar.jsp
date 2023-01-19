<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/toolbar.css" />
</head>

<body>
	<header>
		<div id="headerToolbar">
			<ul class="navToolbar">
				<li><a href=ListaCasas>Todas Las Casas</a></li>
				<li><a href="Buscar">Buscar</a></li>
				<li>Hola, ${currentSessionUser.username}
					<ul>
						<li><a href=Profile>Editar perfil</a></li>
						<li><a href=ListaUser>Tus casas</a></li>
						<li><a href=Logout>Cerrar sesion</a></li>
					</ul>
				</li>
			</ul>
		</div>
	</header>
</body>

</html>
