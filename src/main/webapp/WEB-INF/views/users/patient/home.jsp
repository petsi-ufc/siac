<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div id="home-content">
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
				<li><a id="service_0" class="service" href="#">Seu calendário</a></li>
				<li class="nav-divider"></li>
				<li><a id="service_1" class="service" href="#">Nutrição</a></li>
			   	<li class="nav-divider"></li>
			   	<li><a id="service_2" class="service" href="#">Odontologia</a></li>
			   	<li class="nav-divider"></li>
			   	<li><a id="service_3" class="service" href="#">Psicologia</a></li>
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
	
	<div id="modal-schedules" class="modal fade" role="dialog">
			<div class="modal-dialog modal-sm">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">X</button>
						<h4 class="modal-title" id="modal-title-schedule">Horários dia xx</h4>
					</div>
					<div class="modal-body">
						<table id="table-schedule" class="table table-bordered table-hover">
							<thead>
								<tr>
									<th>Horário</th>
									<th id="table-status">Status</th>
								</tr>
							</thead>
							<tbody>
								<tr>
								 	<!-- ESSA TABELA É PREENCHIDA DINAMICAMENTE COM AJAX -->
								</tr>
							</tbody>
						</table>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">Fechar</button>
					</div>
				</div>
			</div>
		</div>
	
</div>