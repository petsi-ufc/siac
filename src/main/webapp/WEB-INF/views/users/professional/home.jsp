<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:useBean id="user" class="br.ufc.petsi.model.Professional"
	scope="session" />
<jsp:setProperty property="*" name="user" />

<div id="home-content" ng-app="siacApp" ng-controller="professionalController">

	<div id="left-bar">

		<div id="modal-schedules-description" class="modal fade" 
			role="dialog">
			<div class="modal-large">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title">
							<strong>Descrição do(s) Horário(s)</strong>
						</h4>
					</div>
					<div class="modal-body">
						<div id="container-details-consultation">
							<table class="table table-bordered table-hover">
								<thead>
									<tr>
										<th>Hora Início</th>
										<th>Hora Fim</th>
										<th>Estado</th>
										<th>Paciente</th>
										<th>Horário</th>
									</tr>
								</thead>
								<tbody id="tbody-schedules-description">
									<!-- Preenchida dinamicamente -->
								</tbody>
							</table>
						</div>
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
		
		<div id="modal-reschedule" class="modal fade" role="dialog">
			<div class="modal-medium">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title">
							<strong>Reagendar Consulta</strong>
						</h4>
					</div>
					<div class="modal-body">
					
						<div id="alert-reschedule" class="alert alert-danger hidden">
  							<div id="alert-text"></div>
						</div>
						
						<div class="form-group form-font-md">
							<div class="col-md-4">
  								<label class="control-label" for="rsch-atualdate">Data Atual</label>
  								<input class="form-control" id="rsch-atualdate" type="text" placeholder="Disabled input here..." disabled="">
  							</div>
  							<div class="col-md-4">
  								<label class="control-label" for="rsch-atual-timeinit">Hora de Início</label>
  								<input class="form-control" id="rsch-atual-timeinit" type="text" disabled="">
  							</div>
  							<div class="col-md-4">
  								<label class="control-label" for="rsch-atual-timeend">Hora de Fim</label>
  								<input class="form-control" id="rsch-atual-timeend" type="text" disabled="">
  							</div>
						</div>
						
						<div class="form-group form-font-md">
							
							<div class="col-md-4">
								<label class="control-label" for="input-dtpckr-reschedule">Nova Data</label>
								<input type='text' class="form-control" id="input-dtpckr-reschedule"/>
							</div>	                   			
								
          						<div class="col-md-4">
									<label class="control-label" for="rch-timeinit">Hora de Início</label>
									<div class="bootstrap-timepicker timepicker">
									<input id="rch-timeinit" type="text" class="input-schedule-info form-control input-small">
									
								</div>
							</div>  	
														
							<div class="col-md-4">
									<label class="control-label" for="rch-timeend">Hora de Fim</label>
									<div class="bootstrap-timepicker timepicker">
									<input id="rch-timeend" type="text" class="input-schedule-info form-control input-small">
								</div>
							</div>
							
							<div class="input-group col-md-12" id="div-email">
								 <label for="textArea" class="col-lg-2 control-label">E-mail:</label>
     								 <div class="col-lg-10">
       								 <textarea class="form-control" rows="3" id="textArea-email-rsch"></textarea>
       								 <span class="help-block">Explique o motivo do reagendamento da consulta.</span>
     								</div>
							</div>
							
						</div>
						
						<div class="modal-footer">
							<button id="btn-confirm-resch" type="button" class="btn btn-primary">Confirmar</button>
							<button type="button" class="btn btn-default" data-dismiss="modal">Voltar</button>
						</div>
					</div>
				</div>
			</div>
		</div>
		


		<img id="avatar-img" class="img-responsive"
			src="<c:url value='/resources/images/user_avatar.png'/>">

		<div id="box-services">
			<div class="box-header">
				<label>Serviços disponí­veis</label>
			</div>
			<ul id="ul-services" class="nav nav-pills nav-stacked" role="tablist">
				<li class="nav-divider"></li>
				<li class="service-item" ng-class="{'active':canShow(0)}" ng-click="setMenuIndex(0)"><a>Meu Calendário</a></li>
				<li class="nav-divider"></li>
				<li class="service-item" ng-class="{'active':canShow(1)}" ng-click="setMenuIndex(1)"><a>Cadastrar Agenda</a></li>
				<li class="service-item" ng-class="{'active':canShow(2)}" ng-click="setMenuIndex(2)"><a>Gerar Relatório</a></li>
				<!--<li class="nav-divider"></li>
				<li class="service-item" id="2"><a>Minhas Consultas</a></li> -->
			</ul>
		</div>
	</div>

	<div id="right-bar">

		<div class="alert alert-message hidden" role="alert">
			<span id="alert-text">Alert de Mensagens</span><span id="alert-icon"></span>
		</div>

		<div id="calendar-container" ng-show="canShow(0)">
			<h2 id="my-calendar">Meu calendário</h2>
			<!--<div id="container-goto-date">
				<div class="input-group">
					<input id="input-goto-date" type="date" class="form-control"
						placeholder="Ir para data..."> <span
						class="input-group-btn">
						<button class="btn btn-default" id="btn-goto-date" type="button">Ir!</button>
					</span>
				</div>
			</div> -->
			
			<div  ui-calendar="uiConfig.calendar" ng-model="eventSources" class="calendar" ></div>

			<div id="calendar-legend">
				<h3>Legenda de Consultas</h3>
				<table id="table-legend">
					<tbody id="tbody-legend">
						<tr>
							<td>
								<div class='legend-color color-black'></div>
							</td>
							<td>
								<h5>Grupo de Consultas</h5>
							</td>

							<td>
								<div class='legend-color color-green'></div>
							</td>
							<td>
								<h4>Disponível</h4>
							</td>

							<td>
								<div class='legend-color color-blue'></div>
							</td>
							<td>
								<h5>Agendada</h5>
							</td>

							<td>
								<div class='legend-color color-grey'></div>
							</td>
							<td>
								<h5>Realizada</h5>
							</td>

							<td>
								<div class='legend-color color-yellow'></div>
							</td>
							<td>
								<h5>Reservada</h5>
							</td>

							<td>
								<div class='legend-color color-red'></div>
							</td>
							<td>
								<h5>Cancelada</h5>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>

		<div class="panel panel-primary margin-right" ng-show="canShow(1)" 
			id="panel-register-schedules">
			<div class="panel-heading">
				<h1 class="panel-title">Cadastrar Agenda</h1>
			</div>
			<div class="panel-body">
				<form class="form-inline">
					<div class="form-group">
						<h5>Frequência:</h5>
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
				</div>
			</div>
		</div>
		
		<div class="panel panel-primary margin-right" 
		id="panel-generate-report" ng-show="canShow(2)">
			<div class="panel-heading">
				<h1 class="panel-title">Gerar Relatórios</h1>
			</div>
			
			<div class="panel-body">
				<form action="relatorio/avaliacao" method="post" target="_blank">
					<div id="date-report" class="form-group">
						<div class="input-group col-md-4" id="date-left">
							<label class="control-label" for="input-dtpckr-start-report">Início</label>
							<input type='text' name="dateBegin" class="form-control" id="input-dtpckr-start-report"/>
						</div>
						<div class="input-group col-md-4" id="date-right">
							<label class="control-label" for="input-dtpckr-end-report">Fim</label>
							<input type='text' name="dateEnd" class="form-control" id="input-dtpckr-end-report"/>
						</div>
					</div>
					<br><br><br><br>
					<button type="submit" class="btn btn-primary" id="button-generate-report" data-dismiss="modal">Gerar</button>
				</form>
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
											<th>Hora Início</th>
											<th>Hora Fim</th>
											<th>Estado</th>
											<th>Detalhes</th>
											<th>Ações</th>
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
			<div class="modal-dialog modal-md">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4>Cancelar Horário</h4>
					</div>
					<div class="modal-title">
						<h4>Deseja realmente cancelar este horário?</h4>
					</div>
					<div class="modal-body">
						<div id="div-send-email">
							<label>Enviar email para o paciente:</label>
							<textarea placeholder="Escrever email..." id="text-area-email"
								class="no-resize form-control" rows="3"></textarea>
						</div>
						<div class="margin-top">
							<button id="btn-cancel-consultation" class="btn btn-danger"
								value="" name="id">Sim, cancelar</button>
							<button class="btn btn-default" data-dismiss="modal">Voltar</button>
						</div>
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
						Horário</h3>
				</div>
				<div class="modal-body">
					<h4 class="modal-description" id="modal-description-body"></h4>
					<label id="label-date-clicked" class="hidden"></label>

					<div class="panel panel-primary">
						<div class="panel-heading">
							<h3 class="panel-title">Gerador de Horários</h3>
						</div>
						<div class="panel-body">
							<form class="form-horizontal">
								<div class="form-group">
									<label for="input-count-vacancy" class="col-md-4 control-label">Quantidade
										de Vagas:</label>
									<div class="col-md-5">
										<input type="number" min="1" ng-model="vacancyAmount"  
											class="form-control input-schedule-info"
											id="input-count-vacancy">
									</div>
								</div>
								<div class="form-group">
									<label for="input-count-time" class="col-md-4 control-label">Tempo
										por Consulta:</label>
									<div class="col-md-5">
										<input type="number" min="1" ng-model="timePerConsult" 
											class="form-control input-schedule-info"
											id="input-count-time" placeholder="Minutos">
									</div>
								</div>
								<div class="form-group">
									<label for="input-count-time-init" class="col-md-4 control-label">Hora de Início:</label>
									<div class="col-md-5 col-sm-offset-4 input-group bootstrap-timepicker timepicker">
										<input id="tmp-init-0" type="text" class="input-schedule-info form-control input-small" ng-model="timeInit">
										<span class="input-group-addon"><i class="glyphicon glyphicon-time"></i></span>
									</div>
								</div>
							</form>
							<div class="col-md-offset-5">
								<button type="button" class="btn btn-primary" ng-disabled="!vacancyAmount || !timePerConsult || !timeInit"
									id="btn-generate-schedules" ng-click="generateSchedules(vacancyAmount, timePerConsult, timeInit)">
									Gerar Horários <i class="glyphicon glyphicon-time"></i>
								</button>
							</div>
						</div>
					</div>

					<div class="panel panel-primary">
						<div class="panel-heading">
							<h3 class="panel-title">Horários</h3>
						</div>
						<div class="panel-body" id="panel-schedules">
							<form class="form-horizontal">
								<div id="row-add-schedules">
									<div class="row row-schedule-id" ng-repeat="sch in generetedSchedules">
										<label class="col-lg-1 control-label">Início</label>
										<div class="col-md-4">
											<div
												class="timepicker-init  margin-left input-group bootstrap-timepicker timepicker">
												<input id="tmp-init-1" type="text" value="{{sch.initTime}}" 
													class="form-control input-small"> <span
													class="input-group-addon"><i
													class="glyphicon glyphicon-time"></i></span>
											</div>
										</div>
	
										<label class="col-lg-1 control-label">Fim</label>
										<div class="col-md-4">
											<div
												class="timepicker-end input-group bootstrap-timepicker timepicker">
												<input id="tmp-end-1" type="text" class="form-control input-small" value="{{sch.endTime}}"> 
												<span class="input-group-addon">
													<i class="glyphicon glyphicon-time"></i>
												</span>
											</div>
										</div>
										<div class="col-md-2">
											<button type="button" class="btn btn-primary add-schedule">
												<span class="glyphicon glyphicon glyphicon-plus"></span>
											</button>
										</div>
									</div>
								</div>
							</form>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="submit" class="btn btn-primary"
						id="btn-confirm-schedules">
						Salvar <i class="glyphicon glyphicon-floppy-saved"></i>
					</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">Voltar</button>
				</div>
			</div>
		</div>
	</div>

</div>