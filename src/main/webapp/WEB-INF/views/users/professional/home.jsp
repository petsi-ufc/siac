<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<jsp:useBean id="user" class="br.ufc.petsi.model.Professional"
	scope="session" />
<jsp:setProperty property="*" name="user" />


<div id="home-content">
	<div id="left-bar">

		<div id="modal-schedules-description" class="modal fade" tabindex="-1"
			role="dialog">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title">
							<strong>Descri��o do(s) Hor�rio(s)</strong>
						</h4>
					</div>
					<div class="modal-body">
						<table class="table table-bordered table-hover">
							<thead>
								<tr>
									<th>Hora In�cio</th>
									<th>Hora Fim</th>
									<th>Estado</th>
									<th>Nota</th>
								</tr>
							</thead>
							<tbody id="tbody-schedules-description">
							</tbody>
						</table>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">Voltar</button>
					</div>
				</div>
				<!-- /.modal-content -->
			</div>
			<!-- /.modal-dialog -->
		</div>
		<!-- /.modal -->


		<img id="avatar-img" class="img-responsive"
			src="<c:url value='/resources/images/user_avatar.png'/>">

		<div id="box-services">
			<h3>Servi�os dispon�veis</h3>
			<ul id="ul-services" class="nav nav-pills nav-stacked" role="tablist">
				<li class="nav-divider"></li>
				<li class="service-item active" id="0"><a>Meu Calend�rio</a></li>
				<li class="nav-divider"></li>
				<li class="service-item" id="1"><a>Cadastrar Agenda</a></li>
				<li class="nav-divider"></li>
				<li class="service-item" id="2"><a>Minhas Consultas</a></li>
			</ul>
		</div>
	</div>

	<div id="right-bar">

		<div class="alert alert-danger alert-message hidden" role="alert">
			<span id="alert-text">Alert de Mensagens</span><span id="alert-icon"></span>
		</div>

		<h2 id="my-calendar">Meu calend�rio</h2>

		<div id="calendar_professional" class="calendar"></div>

		<div id="calendar-legend">
			<h3>Legenda de Consultas</h3>
			<table id="table-legend">
				<tbody id="tbody-legend">
					<tr>
						<td>
							<div class='legend-color color-black'></div>
						</td>
						<td>
							<h4>Grupo de Consultas</h4>
						</td>

						<td>
							<div class='legend-color color-green'></div>
						</td>
						<td>
							<h4>Dispon�vel</h4>
						</td>

						<td>
							<div class='legend-color color-blue'></div>
						</td>
						<td>
							<h4>Agendada</h4>
						</td>

						<td>
							<div class='legend-color color-grey'></div>
						</td>
						<td>
							<h4>Realizada</h4>
						</td>

						<td>
							<div class='legend-color color-yellow'></div>
						</td>
						<td>
							<h4>Reservada</h4>
						</td>

						<td>
							<div class='legend-color color-red'></div>
						</td>
						<td>
							<h4>Cancelada</h4>
						</td>
					</tr>
				</tbody>
			</table>
		</div>

		<div class="panel panel-primary margin-right hidden"
			id="panel-register-schedules">
			<div class="panel-heading">
				<h1 class="panel-title">Cadastrar Agenda</h1>
			</div>
			<div class="panel-body">
				<form class="form-inline">
					<div class="form-group">
						<h4>Frequ�ncia:</h4>
					</div>
					<div class="form-group">
						<select class="form-control" id="select-repeat-schedule">
							<option value="weekly">Semanalmente</option>
							<option value="monthly">Mensalmente</option>
						</select>
					</div>

					<div class="form-group">
						<input id="input-frequenci" type="number" min="0"
							class="form-control" placeholder="Quantidade de Semanas">
					</div>
				</form>
				<div id="div-days-week" class="margin-top">
					<div class="row">
						<div id="days-buttons">
							<button type="button" class="btn btn-default btn-day"
								value="Monday">Segunda</button>
							<button type="button" class="btn btn-default btn-day"
								value="Tuesday">Ter�a</button>
							<button type="button" class="btn btn-default btn-day"
								value="Wednesday">Quarta</button>
							<button type="button" class="btn btn-default btn-day"
								value="Thursday">Quinta</button>
							<button type="button" class="btn btn-default btn-day"
								value="Friday">Sexta</button>
							<button type="button" class="btn btn-default btn-day"
								value="Saturday">S�bado</button>
							<button type="button" class="btn btn-default btn-day"
								value="Sunday">Domingo</button>
						</div>
					</div>
					<div class="row margin-top" id="div-table-days">
						<div class="col-md-10 col-md-offset-1">
							<table class="table table-bordered table-hover">
								<thead>
									<tr>
										<th>Dia</th>
										<th>Data</th>
										<th>Hor�rios</th>
										<th>Remover</th>
									</tr>
								</thead>
								<tbody id="tbody-table-days">
									<!-- Preenchida dinamicamente -->
								</tbody>
							</table>
						</div>
					</div>
					<button type="button" id="btn-register-schedules"
						class="disabled btn btn-lg btn-primary">
						Cadastrar <i class="glyphicon glyphicon-floppy-saved"></i>
					</button>
				</div>
			</div>
		</div>

		<div class="panel panel-primary margin-right hidden"
			id="panel-my-consultations">
			<div class="panel-heading">
				<h1 class="panel-title">Minhas Consultas</h1>
			</div>
			<div class="panel-body">
				<div class="panel-group" id="collapse-panel-group">
					<div id="message-no-cosultations">
						<h4>Nenhuma consulta cadastrada!</h4>
					</div>
					<div id="fixed-panel-collapse"
						class="panel panel-default my-collapse-panel hidden">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a class="collapse-header" data-toggle="collapse"
									href="#collapse-id">Data das Consultas</a> <span
									class="collapse-icon glyphicon glyphicon-plus"></span>
							</h4>
						</div>
						<div id="collapse-id" class="panel-collapse collapse">
							<div class="panel-body">
								<table class="table table-bordered table-hover">
									<thead>
										<tr>
											<th>Hora In�cio</th>
											<th>Hora Fim</th>
											<th>Estado</th>
											<th>Detalhes</th>
											<th>A��es</th>
										</tr>
									</thead>
									<tbody class="collapse-tbody">
										<!-- Preenchida dinamicamente -->
									</tbody>
								</table>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>

		<div class="modal fade" role="dialog" id="modal-cancel-consultation">
			<div class="modal-dialog modal-sm">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4>Cancelar Hor�rio</h4>
					</div>
					<div class="modal-title">Deseja realmente cancelar esse
						hor�rio?</div>
					<div class="modal-body">
						<button id="btn-cancel-consultation" class="btn btn-danger"
							value="" name="id">Sim, cancelar</button>
						<button class="btn btn-default" value="">Voltar</button>
					</div>
				</div>
			</div>
		</div>

		<div class="modal fade" role="dialog" id="modal-consultation-details">
			<div class="modal-dialog modal-sm">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4>Detalhes da Consulta</h4>
					</div>
					<div class="modal-body">
						<form>
							<div class="form-group">
								<label>Coment�rio:</label> 
								<textarea id="textarea-comment" class="form-control" disabled="disabled" rows="3">Sem coment�rios cadastrados!</textarea>
							</div>
							<div class="form-group">
								<label>Nota:</label> 
								<div id="div-rating"></div>
							</div>
						</form>
						
						<button class="btn btn-default" data-dismiss="modal">Voltar</button>
					</div>
				</div>
			</div>
		</div>

	</div>

	<div id="modal-day" class="modal fade" role="dialog">
		<div class="modal-dialog">
			<div class="modal-content">
				<div id="title-header" class="modal-header">
					<button type="button" class="close" data-dismiss="modal">X</button>
					<h3 id="modal-schedule-title" class="modal-title">Cadastrar
						Hor�rio</h3>
				</div>
				<div class="modal-body">
					<h4 id="modal-description-body"></h4>
					<label id="label-date-clicked" class="hidden"></label>

					<div class="panel panel-primary">
						<div class="panel-heading">
							<h3 class="panel-title">Informa��es da Consulta</h3>
						</div>
						<div class="panel-body">
							<form class="form-horizontal">
								<div class="form-group">
									<label for="input-count-vacancy" class="col-md-4 control-label">Quantidade
										de Vagas:</label>
									<div class="col-md-5">
										<input type="number" min="1"
											class="form-control input-schedule-info"
											id="input-count-vacancy">
									</div>
								</div>
								<div class="form-group">
									<label for="input-count-time" class="col-md-4 control-label">Tempo
										por Consulta:</label>
									<div class="col-md-5">
										<input type="number" min="1"
											class="form-control input-schedule-info"
											id="input-count-time" placeholder="Minutos">
									</div>
								</div>
								<div class="form-group">
									<label for="input-count-time-init"
										class="col-md-4 control-label">Hora de In�cio:</label>
									<div
										class="col-md-5 col-sm-offset-4 input-group bootstrap-timepicker timepicker">
										<input id="tmp-init-0" type="text"
											class="input-schedule-info form-control input-small">
										<span class="input-group-addon"><i
											class="glyphicon glyphicon-time"></i></span>
									</div>
								</div>
							</form>
							<div class="col-md-offset-5">
								<button type="button" class="btn btn-primary"
									id="btn-generate-schedules">
									Gerar Hor�rios <i class="glyphicon glyphicon-time"></i>
								</button>
							</div>
						</div>
					</div>

					<div class="panel panel-primary">
						<div class="panel-heading">
							<h3 class="panel-title">Hor�rios</h3>
						</div>
						<div class="panel-body" id="panel-schedules">
							<div class="row" id="row-add-schedules">

								<div class="col-md-1">
									<h4>In�cio:</h4>
								</div>
								<div class="col-md-4">
									<div
										class="timepicker-init  margin-left input-group bootstrap-timepicker timepicker">
										<input id="tmp-init-1" type="text"
											class="form-control input-small"> <span
											class="input-group-addon"><i
											class="glyphicon glyphicon-time"></i></span>
									</div>
								</div>

								<div class="col-md-1">
									<h4>Fim:</h4>
								</div>
								<div class="col-md-4">
									<div
										class="timepicker-end input-group bootstrap-timepicker timepicker">
										<input id="tmp-end-1" type="text"
											class="form-control input-small"> <span
											class="input-group-addon"><i
											class="glyphicon glyphicon-time"></i></span>
									</div>
								</div>
								<div class="col-md-2">
									<button type="button" class="btn btn-primary add-schedule">
										<span class="glyphicon glyphicon glyphicon-plus"></span>
									</button>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-primary"
						id="btn-confirm-schedules" data-dismiss="modal">Confirmar</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">Voltar</button>
				</div>
			</div>
		</div>
	</div>

</div>