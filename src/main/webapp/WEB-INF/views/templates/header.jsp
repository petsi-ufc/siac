<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<nav class="navbar navbar-default">
  <div class="container-fluid">
    <div class="navbar-header">
      <a class="navbar-brand" href="#"><img id="img-logo" width="100" src="<c:url value='/resources/images/logotipo.png'/>"></a>
    </div>
    <div>
      <ul class="nav navbar-nav" id="main-navbar">
        <!-- ESSA NAVBAR É PREENCHIDA DINAMICAMENTE COM JS. -->
      </ul>  
      <ul class="nav navbar-nav navbar-right">
      	<li><a href="<c:url value="/j_spring_security_logout" />"><span class="glyphicon glyphicon-log-out"></span> SAIR</a></li>
      </ul>
      
    </div>
  </div>
</nav>
  