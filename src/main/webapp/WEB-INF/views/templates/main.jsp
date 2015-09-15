<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	
	<title>
		<tiles:getAsString name="title"/>
	</title>
</head>
<body>
	<header>
		<!-- cabeçalho -->
		<tiles:insertAttribute name="header" />
	</header>
	
	<section>
		<!-- conteudo -->
		<tiles:insertAttribute name="body" />
	</section>
	
	<footer>
		<!-- rodapé -->
		<tiles:insertAttribute name="footer"/>
	</footer>
</body>
</html>