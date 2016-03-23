<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div id="home-content">
	<div id="left-bar">
		<img id="avatar-img" class="img-responsive"
			src="<c:url value='/resources/images/user_avatar.png'/>">

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
			<ul id="ul-services" class="nav nav-pills nav-stacked"
				role="tablist">
				<li class="nav-divider"></li>
				<li class="service active"><a class="link-service" id="0">Meu
						calendário</a></li>
			</ul>
		</div>
		<div id="calendar-legend">
			<h3>LEGENDA AQUI</h3>
			
		</div>		
	</div>
	
	

	<div id="right-bar">
		<div class="alert alert-danger" id="alert-schedules">Não há
			nenhum horário cadastrado para este serviço!</div>
		<h2 id="my-calendar">Meu calendário</h2>
	
		<div class="calendar" id="calendar-patient"></div>

		<div id="my-consultations">
			<div id="constutaions-panel" class="panel panel-primary">
				<div class="panel-heading"><h3>Suas Consultas</h3></div>
				<table class="table table-hover">
					<thead>
						<tr>
							<th>Serviço</th>
							<th>Data</th>
							<th>Horário</th>
							<th>Status</th>
							<th>Opções</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>Odontologia</td>
							<td>15/12/2015</td>
							<td>09:30</td>
							<td>Agendada</td>
							<td>
								<div class="btn-group">
									<button type="button" class="btn btn-warning dropdown-toggle"
										data-toggle="dropdown" aria-haspopup="true"
										aria-expanded="false">
										Mais <span class="caret"></span>
									</button>
									<ul class="dropdown-menu">
										<li><a href="#">Avaliar</a></li>
										<li><a href="#">Cancelar</a></li>
									</ul>
								</div>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>

	</div>

	<div id="modal-day" class="modal fade" role="dialog">
		<div class="modal-dialog modal-sm">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">X</button>
					<h4 class="modal-title" id="modal-title-schedule"></h4>
				</div>
				<div class="modal-body">
					<table id="table-schedule" class="table table-bordered table-hover">
						<thead>
							<tr>
								
								<th>Horário</th>
								<th> Status </th>
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
					<button id="btn-confirm-schedule" type="button"
						class="btn btn-primary" data-dismiss="modal">Confirmar</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">Fechar</button>
				</div>
			</div>
		</div>
	</div>

</div>