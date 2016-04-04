<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>



<img id="img-banner" src="<c:url value='/resources/images/banner.jpg'/>">

<nav class="navbar navbar-default">
  <div class="container-fluid">
    <div class="navbar-header">
      <a class="navbar-brand" href="#">SIAC</a>
    </div>
    <div>
      <ul class="nav navbar-nav" id="main-navbar">
        <!-- ESSA NAVBAR É PREENCHIDA DINAMICAMENTE COM JS. -->
      </ul>  
      <ul class="nav navbar-nav navbar-right">
      	<li><a href="/logout"><span class="glyphicon glyphicon-log-out"></span>Sair</a></li>
      </ul>
      
    </div>
  </div>
</nav>
  