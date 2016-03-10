<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div id="home-content">
	<div id="left-bar">
		<img id="avatar-img" class="img-responsive"
			src="<c:url value='/resources/images/user_avatar.png'/>">

		<div id="box-services">
			<h3>Serviços disponí­veis</h3>
			<ul id="ul-services" class="nav nav-pills nav-stacked" role="tablist">
				<li class="nav-divider"></li>
				<li class="service-item" id="0"><a>Meu Calendário</a></li>
				<li class="nav-divider"></li>
				<li class="service-item" id="1"><a>Cadastrar Agenda</a></li>
			</ul>
		</div>
	</div>

	<div id="right-bar">
		<h2 id="my-calendar">Meu calendário</h2>
		<div id="calendar_professional" class="calendar"></div>

		<div class="panel panel-primary margin-right hidden" id="panel-register-schedules">
			<div class="panel-heading">
				<h1 class="panel-title">Cadastrar Agenda</h1>
			</div>
			<div class="panel-body">
				<form class="form-inline">
					<div class="form-group">
						<h4>Frequência:</h4>
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
								value="Tuesday">Terça</button>
							<button type="button" class="btn btn-default btn-day"
								value="Wednesday">Quarta</button>
							<button type="button" class="btn btn-default btn-day"
								value="Thursday">Quinta</button>
							<button type="button" class="btn btn-default btn-day"
								value="Friday">Sexta</button>
							<button type="button" class="btn btn-default btn-day"
								value="Saturday">Sábado</button>
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
										<th>Horários</th>
										<th>Remover</th>
									</tr>
								</thead>
								<tbody id="tbody-table-days">
									<!-- Preenchida dinamicamente -->
								</tbody>
							</table>
						</div>
					</div>
					<button type="button" id="btn-register-schedules" class="btn btn-lg btn-primary">Cadastrar <i class="glyphicon glyphicon-floppy-saved"></i></button>
				</div>
			</div>
		</div>
	</div>

	<div id="modal-day" class="modal fade" role="dialog">
		<div class="modal-dialog">
			<div class="modal-content">
				<div id="title-header" class="modal-header">
					<button type="button" class="close" data-dismiss="modal">X</button>
					<h3 class="modal-title">Cadastrar Horário</h3>
				</div>
				<div class="modal-body">
					<h4 id="modal-description-body"></h4>

					<div class="panel panel-primary">
						<div class="panel-heading">
							<h3 class="panel-title">Horários</h3>
						</div>
						<div class="panel-body" id="panel-schedules">
							<div class="row" id="row-add-schedules">

								<div class="col-md-1">
									<h4>Início:</h4>
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
					<button type="button" class="btn btn-primary" data-dismiss="modal">Confirmar</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">Voltar</button>
				</div>
			</div>
		</div>
	</div>


</div>