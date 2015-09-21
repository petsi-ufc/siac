<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div id="left-bar">
	<img id="avatar-img" class="img-responsive" src="<c:url value='/resources/images/user_avatar.png'/>">
	
	<div id="modal-config" class="modal fade" role="dialog">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">X</button>
					<h4 class="modal-title">Seus Dados</h4>
				</div>
				<div class="modal-body">
					<h4>BODY MODAL</h4>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Fechar</button>
				</div>
			</div>
		</div>
	</div>
	
	<div id="box-services">
		<h3>Serviços disponí­veis</h3>
		<ul class="nav nav-pills nav-stacked" role="tablist">
			<li class="nav-divider"></li>
			<li><a href="#">Nutrição</a></li>
		   	<li class="nav-divider"></li>
		   	<li><a href="#">Odontologia</a></li>
		   	<li class="nav-divider"></li>
		   	<li><a href="#">Psicologia</a></li>
		</ul>
	</div>
	<div id="calendar-legend">
		<h3>LEGENDA AQUI</h3>
	</div>
</div>

<div id="right-bar">
	<h2 id="my-calendar">Meu calendário</h2>
	<div id="calendar"></div>	
</div>