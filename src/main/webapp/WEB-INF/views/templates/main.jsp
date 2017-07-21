<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html ng-app="siacApp">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
	
	<link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/bootstrap.min.css'/>">
	<link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/fullcalendar.css'/>">
	<link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/datepicker.css'/>">
	<link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/bootstrap-timepicker.min.css'/>">
	<link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/style.css'/>">
	<link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/bootstrap-stars.css'/>">
	<link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/bootstrap-datetimepicker.min.css'/>">
	<link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/jquery.datetimepicker.css'/>">

	<script type="text/javascript" src="<c:url value='/resources/js/angular.min.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/resources/js/jquery-2.1.3.min.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/resources/js/bootstrap.min.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/resources/js/moment.min.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/resources/js/calendar.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/resources/js/fullcalendar.min.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/resources/js/pt-br.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/resources/js/bootstrap-datepicker.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/resources/js/bootstrap-timepicker.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/resources/js/siac-funcs.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/resources/js/schedule-classes.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/resources/js/bootstrap-datetimepicker.min.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/resources/js/jquery.datetimepicker.full.js'/>"></script>
	
		
	<script type="text/javascript" src="<c:url value='/resources/js/app.js'/>"></script>
	
	<%-- <script type="text/javascript" src="<c:url value='/resources/js/qrating-1.0.0.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/resources/js/qrating-1.0.0.min.js'/>"></script> --%>  
	<script type="text/javascript" src="<c:url value='/resources/js/jquery.barrating.min.js'/>"></script>
	
	
	<tiles:useAttribute id='src' name='javascriptsrc'/>
	
	<c:forEach var="item" items="${src}">
		<script type="text/javascript" src="<c:url value='${item}'/>"></script>
	</c:forEach>
	
	<title>
		<tiles:getAsString name="title" />
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
	
	<script type="text/javascript" src="<c:url value='/resources/js/config-datatimepicker.js'/>"></script>
	
</body>
</html>