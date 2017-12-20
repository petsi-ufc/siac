<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<jsp:useBean id="user" class="br.ufc.petsi.model.Professional"
	scope="session" />
<jsp:setProperty property="*" name="user" />

<div id="home-content" ng-controller="professionalController">

	<div id="left-bar">

		<div id="modal-schedules-description" class="modal fade" role="dialog">
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
						<div id="container-details-consultation" style="padding: 20px;">
							<table class="table table-bordered table-hover">
								<thead>
									<tr>
										<th>Hora Início</th>
										<th>Hora Fim</th>
										<th>Estado</th>
										<th>Paciente/Grupo</th>
										<th>Horário</th>
										<th>Consulta</th>
									</tr>
								</thead>
								<tbody id="tbody-schedules-description">
									<tr ng-repeat="e in eventsForDay">
										<td>{{e.start}}</td>
										<td>{{e.end}}</td>
										<td>{{e.color}}</td>
										<td>{{e.title}}</td>
										<td>
											<button type="button" value="{{e.id}}"
												class="btn btn btn-success action-register-consultation"
												ng-disabled="e.state == 'CD' || e.state == 'RD'"
												ng-click="registerConsultation(e.id, e.date)">
												Registrar<span class="glyphicon glyphicon-ok"></span>
											</button>
											<button type="button" value="{{e.id}}"
												class="btn btn btn-danger action-cancel-consultation"
												ng-disabled="e.state == 'CD' || e.state == 'RD'"
												ng-click="cancelConsultation(e.id)">
												Cancelar<span class="glyphicon glyphicon-remove-circle"></span>
											</button>
											<button type="button" value="{{e.id}}"
												class="btn btn btn-warning action-reschedule-consultation margin-left"
												ng-disabled="e.state == 'CD' || e.state == 'RD'"
												ng-click="reschedulingConsultation(e)">
												Reagendar<span class="glyphicon glyphicon-time"></span>
											</button>
										</td>
										<td>
											<button type="button" value="{{e.id}}"
												class="btn btn btn-info" ng-disabled="e.state != 'RD'"
												ng-click="viewComment(e.comment, e.id)" title="Comentário">
												<span class="glyphicon glyphicon-list-alt"></span>
											</button>
										</td>
									</tr>
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

		<div id="modal-comment" class="modal fade" role="dialog">
			<div class="modal-dialog modal-md">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<center>
							<h4 class="modal-title">
								<strong>Comentário da Consulta</strong>
							</h4>
						</center>
						<div>
							<textarea ng-model="comentario" placeholder="Comentário..."
								class="no-resize form-control" rows="3"></textarea>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default"
							ng-click="registerComment(comentario); reload();"
							data-dismiss="modal">Comentar</button>
						<button type="button" class="btn btn-default" data-dismiss="modal">Voltar</button>
					</div>
				</div>
			</div>
		</div>


		<div id="modal-consultation-group" class="modal fade" role="dialog">
			<div class="modal-dialog modal-md">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title">
							<strong>Consultas do {{groupVisibleConsultation.title}}</strong>
						</h4>
					</div>
					<div class="modal-body">
						<div id="container-details-consultation">
							<table class="table table-bordered table-hover">
								<thead>
									<tr>
										<th>Data da Consulta</th>
										<th>Visualizar Frequência</th>
									</tr>
								</thead>
								<tbody id="tbody-frequency-list">
									<tr
										ng-repeat="consultation in groupVisibleConsultation.listConsultations">
										<td>{{consultation.dateInit | date:"dd/MM/yyyy 'às'
											h:mma"}}</td>
										<td><button type="button" class="btn btn-primary"
												ng-click="getFrequencyList(consultation.id)"
												data-dismiss="modal">Lista de Frequência</button></td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">Voltar</button>
					</div>
				</div>
			</div>
		</div>


		<div id="modal-view-frequency-group" class="modal fade" role="dialog">
			<div class="modal-dialog modal-md">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title">
							<strong>Frequência do dia
								{{frequencyListOfGroup.frequencyList[0].consultation.dateInit |
								date:"dd/MM/yyyy 'às' h:mma"}}</strong>
						</h4>
					</div>
					<div class="modal-body">
						<div id="container-details-consultation">
							<table class="table table-bordered table-hover">
								<thead>
									<tr>
										<th>Presente</th>
										<th>Nome</th>
									</tr>
								</thead>
								<tbody id="tbody-frequency-list">
									<tr ng-repeat="frequency in frequencyListOfGroup.frequencyList">
										<td><span class="label label-success"
											ng-if="frequency.presence == true">Presente</span><span
											class="label label-danger"
											ng-if="frequency.presence == false">Ausente</span></td>
										<td>{{frequency.patient.name}}</td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">Voltar</button>
					</div>
				</div>
			</div>
		</div>


		<div id="modal-reschedule" class="modal fade" role="dialog">
			<div class="modal-medium">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
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
								<label class="control-label" for="rsch-atualdate">Data
									Atual</label> <input class="form-control" ng-model="consultation.date"
									id="rsch-atualdate" type="text"
									placeholder="Disabled input here..." disabled="">
							</div>
							<div class="col-md-4">
								<label class="control-label" for="rsch-atual-timeinit">Hora
									de Início</label> <input class="form-control"
									ng-model="consultation.start" id="rsch-atual-timeinit"
									type="text" disabled="">
							</div>
							<div class="col-md-4">
								<label class="control-label" for="rsch-atual-timeend">Hora
									de Fim</label> <input class="form-control" ng-model="consultation.end"
									id="rsch-atual-timeend" type="text" disabled="">
							</div>
						</div>

						<div class="form-group form-font-md">

							<div class="col-md-4">
								<label class="control-label" for="input-dtpckr-reschedule">Nova
									Data</label> <input type='text' ng-model="newDate"
									class="form-control datetimepicker2"
									id="input-dtpckr-reschedule" />
							</div>

							<div class="col-md-4">
								<label class="control-label" for="rch-timeinit">Hora de
									Início</label> <input id="rch-timeinit" ng-model="newStarHour"
									type="text"
									class="input-schedule-info form-control input-small datetimepicker1">
							</div>

							<div class="col-md-4">
								<label class="control-label" for="rch-timeend">Hora de
									Fim</label> <input id="rch-timeend" ng-model="newEndHour" type="text"
									class="input-schedule-info form-control input-small datetimepicker1">
							</div>

							<div class="input-group col-md-12" id="div-email">
								<label for="textArea" class="col-lg-2 control-label">E-mail:</label>
								<div class="col-lg-10">
									<textarea ng-model="reason" class="form-control" rows="3"
										id="textArea-email-rsch"></textarea>
									<span class="help-block">Explique o motivo do
										reagendamento da consulta.</span>
								</div>
							</div>

						</div>

						<div class="modal-footer">
							<button id="btn-confirm-resch"
								ng-click="registerReschedulingConsultation(consultation.id, reason)"
								ng-disabled="reason == null" type="button"
								class="btn btn-primary">Confirmar</button>
							<button type="button" class="btn btn-default"
								data-dismiss="modal">Voltar</button>
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
							<textarea ng-model="message_cancel"
								placeholder="Escrever email..." id="text-area-email"
								class="no-resize form-control" rows="3"></textarea>
						</div>
						<div class="margin-top">
							<button id="btn-cancel-consultation" class="btn btn-danger"
								value="" name="id"
								ng-click="registerCancelConsultation(conToCancel,message_cancel)">Sim,
								cancelar</button>
							<button class="btn btn-default" data-dismiss="modal">Voltar</button>
						</div>
					</div>
				</div>
			</div>
		</div>

		<img id="avatar-img" class="img-responsive"
			src="<c:url value='/resources/images/user_avatar.png'/>">
		<center>
			<h5>
				<c:out value="${userLogged.name}" />
			</h5>
			<center />

			<div id="box-services">
				<div class="box-header">
					<label>Serviços disponí­veis</label>
				</div>
				<ul id="ul-services" class="nav nav-pills nav-stacked"
					role="tablist">
					<li class="nav-divider"></li>
					<li class="service-item" ng-class="{'active':canShow(0)}"
						ng-click="setMenuIndex(0)"><a>Meu Calendário</a></li>
					<li class="nav-divider"></li>
					<li class="service-item" ng-class="{'active':canShow(1)}"
						ng-click="setMenuIndex(1)"><a>Cadastrar Agenda</a></li>
					<li class="service-item" ng-class="{'active':canShow(3)}"
						ng-click="setMenuIndex(3); group=0; update=false;"><a>Grupos</a></li>
					<li class="service-item" ng-class="{'active':canShow(2)}"
						ng-click="setMenuIndex(2)"><a>Gerar Relatório</a></li>
					<!--<li class="nav-divider"></li>
				<li class="service-item" id="2"><a>Minhas Consultas</a></li> -->
				</ul>
			</div>
	</div>

	<div id="right-bar">

		<div class="alert alert-message hidden" role="alert">
			<span id="alert-text">Alert de Mensagens</span><span id="alert-icon"></span>
		</div>



		<div ng-show="canShow(3)">
			<div>
				<div class="">
					<div class="">
						<div id="title-header" class="">
							<h3 id="modal-schedule-title" class="modal-title text-center">Gerenciador
								de Grupos</h3>
						</div>
						<div class="modal-body">

							<div class="panel panel-primary">
								<div class="panel-heading">
									<h3 class="panel-title">Informações do Grupo</h3>
								</div>
								<div class="panel-body">
									<div ng-show="group == 0">
										<h3>Meus Grupos</h3>
										<table class="table table-bordered table-hover">
											<thead>
												<tr>
													<th>Nome</th>
													<th>Tipo de Grupo</th>
													<th>Ações</th>
												</tr>
											</thead>
											<tbody id="tbody-schedules-description">
												<tr ng-repeat="group in groups">
													<td>{{group.title}}</td>
													<td><span class="label label-success"
														ng-if="group.openGroup == true">Aberto</span><span
														class="label label-danger"
														ng-if="group.openGroup == false">Fechado</span></td>
													<td>
														<button class="btn btn-primary"
															ng-click="updateGroup(group)">Editar</button>
														<button class="btn btn-default"
															ng-click="showConsultationOfGroup(group)">Ver
															Frequências</button>
													</td>
												</tr>
											</tbody>
										</table>
										<button class="btn btn-primary" ng-show="group == 0"
											ng-click="group = 1">Cadastrar Grupo</button>

									</div>

									<form class="form-horizontal" ng-show="group == 1">
										<div id="row-and-schedules">
											<div id="row-add-schedules">
												<div class="row">
													<div class="col-xs-12">
														<input type="text" ng-required="true"
															ng-model="grupo.title" class="form-control"
															placeholder="Título do Grupo">
													</div>
												</div>

												<div ng-init="grupo.openGroup = true" class="row">
													<br>
													<h5 style="text-align: left; margin-left: 15px;">Tipo
														de Grupo:</h5>
													<div class="form-check form-check-inline col-xs-1">
														<label class="form-check-label"> <input
															class="form-check-input" ng-model="tipo" type="radio"
															ng-click="grupo.openGroup = true" ng-required="!tipo"
															name="inlineRadioOptions" id="inlineRadio1"
															value="option1"> Aberto
														</label>
													</div>

													<div class="form-check form-check-inline col-xs-1">
														<label class="form-check-label"> <input
															class="form-check-input" ng-model="tipo" type="radio"
															ng-click="grupo.openGroup = false" ng-required="!tipo"
															name="inlineRadioOptions" id="inlineRadio2"
															value="option2"> Fechado
														</label>
													</div>
													<br>
												</div>

												<div class="row">
													<div class="col-xs-12">
														<input type="text" ng-required="true"
															ng-model="grupo.patientLimit" class="form-control"
															placeholder="Número de Participantes">
													</div>
												</div>
												<h5>Participantes</h5>
												<table class="table table-bordered table-hover">
													<thead>
														<tr>
															<th>Nome</th>
															<th>CPF</th>
															<th>Remover</th>
														</tr>
													</thead>
													<tbody id="tbody-schedules-description">
														<tr ng-repeat="patient in grupo.patients">
															<td>{{patient.name}}</td>
															<td>{{patient.cpf}}</td>
															<td><button class="btn btn-danger"
																	ng-click="removePatient($index)">
																	<span class="glyphicon glyphicon-minus-sign"
																		aria-hidden="true" style="font-size: 20px"></span>
																</button></td>
														</tr>
													</tbody>
												</table>
												<div class="row">
													<br> <br>
													<h5>Seleção de Participantes</h5>
													<div class="col-xs-12">
														<input type="text" ng-model="buscarPaciente"
															class="form-control" placeholder="Nome do Paciente">
													</div>
												</div>
											</div>
										</div>
										<br>
										<table class="table table-bordered table-hover">
											<thead>
												<tr>
													<th>Nome</th>
													<th>CPF</th>
													<th>Adicionar</th>
												</tr>
											</thead>
											<tbody id="tbody-schedules-description"
												ng-if="buscarPaciente != ''">
												<tr
													ng-repeat="patient in patients | filter: {name: buscarPaciente}">
													<td>{{patient.name}}</td>
													<td>{{patient.cpf}}</td>
													<td><button class="btn btn-primary"
															ng-click="addPatient(patient)">
															<span class="glyphicon glyphicon-plus-sign"
																aria-hidden="true" style="font-size: 20px"></span>
														</button></td>
												</tr>
											</tbody>
										</table>
									</form>
									<br>
									<div ng-if="group == 2">
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
									<button type="button" class="btn btn-primary"
										data-dismiss="modal" ng-show="group == 1 && update == true"
										ng-disabled="!grupo.title || !grupo.patientLimit"
										ng-click="saveUpdateGroup()">Salvar Modificações</button>
									<button type="button" class="btn btn-primary"
										data-dismiss="modal"
										ng-show="(group == 1 || group == 2) && update == false"
										ng-disabled="!grupo.title || !grupo.patientLimit || !tipo"
										ng-click="createGroup()">Cadastrar</button>
									<!-- <button type="button" class="btn btn-primary" data-dismiss="modal" ng-show="group == 1 && update == false" ng-disabled="!grupo.title || !grupo.patientLimit || !tipo" ng-click="group = 2">Incluir Horários</button>-->
									<button type="button" class="btn btn-default"
										data-dismiss="modal" ng-show="group == 1 || group == 2"
										ng-click="group = 0; update=false">Voltar</button>
								</div>
							</div>


							<div class="panel panel-primary"
								ng-show="isFreeConsultation == true">
								<div class="panel-heading">
									<h3 class="panel-title">Cadastrar Horários</h3>
								</div>
								<div class="panel-body" id="panel-schedules">
									<form class="form-horizontal">
										<div id="row-add-schedules">
											<div class="row row-schedule-id">
												<label class="col-lg-1 control-label">Início</label>
												<div class="col-md-4">
													<div
														class="timepicker-init margin-left input-group bootstrap-timepicker timepicker">
														<input id="tmp-init-1" type="text"
															class="form-control input-small" data-template="modal"
															data-provide="timepicker" data-modal-backdrop="true">
														<span class="input-group-addon"> <i
															class="glyphicon glyphicon-time"></i>
														</span>
													</div>
												</div>

												<label class="col-lg-1 control-label">Fim</label>
												<div class="col-md-4">
													<div
														class="timepicker-end input-group bootstrap-timepicker timepicker">
														<input id="tmp-end-1" type="text"
															class="form-control input-small" data-template="modal"
															data-modal-backdrop="true" data-provide="timepicker">
														<span class="input-group-addon"> <i
															class="glyphicon glyphicon-time"></i>
														</span>
													</div>
												</div>
												<div class="col-md-2">
													<button ng-click="addTempSchedule()" type="button"
														class="btn btn-primary add-schedule">
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
								ng-if="isPacientConsultation == true"
								ng-click="saveConsultations(generetedSchedules)">
								Salvar <i class="glyphicon glyphicon-floppy-saved"></i>
							</button>
							<button type="submit" class="btn btn-primary"
								ng-if="isGroupConsultation == true"
								ng-disabled="generetedSchedules.length == 0 && grp == null"
								ng-click="saveConsultations(generetedSchedules)">
								Salvar <i class="glyphicon glyphicon-floppy-saved"></i>
							</button>
							<button type="submit" class="btn btn-primary"
								ng-if="isFreeConsultation == true"
								ng-disabled="generetedSchedules.length == 0"
								id="btn-confirm-schedules"
								ng-click="saveFreeConsultations(generetedSchedules)">
								Salvar <i class="glyphicon glyphicon-floppy-saved"></i>
							</button>
						</div>
					</div>
				</div>
			</div>
		</div>

		<div id="calendar-container" ng-show="canShow(0)">
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
								<h5>Disponível</h5>
							</td>

							<td>
								<div class='legend-color color-blue'></div>
							</td>
							<td>
								<h5>Agendada</h5>
							</td>
						</tr>
						<tr>
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
								<h5>Em Espera</h5>
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
			<div ui-calendar="uiConfig.calendar" config="uiConfig.calendar"
				ng-model="eventSources" class="calendar" calendar="calendar"
				id="calendar"></div>
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
					<br />
					<div class="form-group">
						<h5>Padrões:</h5>
					</div>
					<div class="form-group">
						<div class="margin-left input-group timepicker">
							<input id="tmp-init-hour" type="text"
								class="form-control input-small datetimepicker1"
								placeholder="Horário de início"
								title="Horário padrão para iniciar as consulta de um determinado dia" />
						</div>
					</div>
					<div class="form-group">
						<input id="input-number-vacancy" type="number" min="0"
							class="form-control" placeholder="Quantidade de vagas"
							title="Quantidade padrão de vagas por consulta">
					</div>
					<div class="form-group">
						<input id="input-time-consultation" type="number" min="5"
							class="form-control" placeholder="Tempo por consulta"
							title="Tempo padrão para cada consulta">
					</div>

				</form>
				<div id="div-days-week" class="margin-top">
					<div class="row">
						<div id="days-buttons">
							<button type="button" class="btn btn-default btn-day"
								value="Monday" ng-click="addScheduler($event)">Segunda</button>
							<button type="button" class="btn btn-default btn-day"
								value="Tuesday" ng-click="addScheduler($event)">Terça</button>
							<button type="button" class="btn btn-default btn-day"
								value="Wednesday" ng-click="addScheduler($event)">Quarta</button>
							<button type="button" class="btn btn-default btn-day"
								value="Thursday" ng-click="addScheduler($event)">Quinta</button>
							<button type="button" class="btn btn-default btn-day"
								value="Friday" ng-click="addScheduler($event)">Sexta</button>
							<button type="button" class="btn btn-default btn-day"
								value="Saturday" ng-click="addScheduler($event)">Sábado</button>
							<button type="button" class="btn btn-default btn-day"
								value="Sunday" ng-click="addScheduler($event)">Domingo</button>
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
									<tr ng-repeat="d in scheduler">
										<td>{{d.day}}</td>
										<td>{{d.date}}</td>
										<td><button value={{d.date}}
												class="action-day add-schedule btn btn-primary"
												ng-click="onShowSchedulers(d)">
												Editar <i class="glyphicon glyphicon-time"></i>
											</button></td>
										<td><button value={{d.date}}
												class="btn btn-danger action-day remove-day"
												ng-click="removeDate(d)">
												<i class="glyphicon glyphicon-trash">
											</button> </i></td>
									</tr>
								</tbody>
							</table>
						</div>
						<button class="btn btn-success" ng-click="consolidateAgenda()">Consolidar
							Horários</button>
					</div>
				</div>
			</div>
		</div>

		<div class="panel panel-primary margin-right"
			id="panel-generate-report" ng-show="canShow(2)">
			<div class="panel-heading">
				<h1 class="panel-title">Gerar Relatórios</h1>
			</div>
			<ul class="pager">
				<li><a ng-click="showReport = true" style="cursor: pointer">Por
						Período</a></li>
				<li><a ng-click="showReport = false" style="cursor: pointer">Por
						Mês</a></li>
			</ul>
			<div class="panel-body">
				<form action="relatorio/avaliacao" method="post" target="_blank"
					ng-show="showReport">
					<div id="date-report" class="form-group">
						<div class="input-group col-md-4" id="date-left">
							<label class="control-label" for="input-dtpckr-start-report">Início</label>
							<input ng-required="true" type="text" placeholder="Date"
								name="dateBegin" class="form-control datetimepicker2"
								id="input-dtpckr-start-report" />
						</div>
						<div class="input-group col-md-4" id="date-right"
							layout-gt-md="row" layout="column">
							<label class="control-label" for="input-dtpckr-end-report">Fim</label>
							<input ng-required="true" type="text" placeholder="Date"
								name="dateEnd" class="form-control datetimepicker2"
								id="input-dtpckr-end-report" />
						</div>
					</div>
					<br> <br> <br> <br>
					<button type="submit" class="btn btn-primary"
						id="button-generate-report" data-dismiss="modal">Gerar</button>
				</form>
				<form action="relatorio/avaliacaoMes" method="post" target="_blank"
					ng-show="!showReport">
					<div id="date-report" class="form-group">
						<div class="input-group col-md-4" id="date-left">
							<label class="control-label">Mês</label><br>
							<!-- <input type="text" placeholder="Date" name="dateBegin" class="form-control" id="input-dtpckr-start-report"/>-->
							<select name="month" class="form-control">
								<option value="1">Janeiro</option>
								<option value="2">Fevereiro</option>
								<option value="3">Março</option>
								<option value="4">Abril</option>
								<option value="5">Maio</option>
								<option value="6">Junho</option>
								<option value="7">Julho</option>
								<option value="8">Agosto</option>
								<option value="9">Setembro</option>
								<option value="10">Outubro</option>
								<option value="11">Novembro</option>
								<option value="12">Dezembro</option>
							</select>
						</div>
						<div class="input-group col-md-4" id="date-right"
							layout-gt-md="row" layout="column">
							<label class="control-label" for="input-year-report">Ano</label><br>
							<input type="text" placeholder="Ano" name="year"
								id="input-year-report" class="form-control" />
						</div>
					</div>
					<br> <br> <br> <br>
					<button type="submit" class="btn btn-primary"
						id="button-generate-report" data-dismiss="modal">Gerar</button>
				</form>
			</div>
			<br>
		</div>
		<div class="panel panel-primary margin-right"
			id="panel-frequency-comment" ng-show="canShow(4)">
			<div class="panel-heading">
				<h1 class="panel-title">Registrar Consulta</h1>
			</div>

			<div class="panel-body">
				<center>
					<h4 class="modal-title">
						<strong>Comentário da Consulta</strong>
					</h4>
				</center>
				<div>
					<textarea ng-model="comment" placeholder="Comentário..."
						class="no-resize form-control" rows="3"></textarea>
				</div>

				<div class="modal-header">
					<h4 class="modal-title">
						<strong>Lista de Frequência</strong>
					</h4>
					<h5 align="left">
						Grupo <strong> {{tempConsultation.group.title}}</strong> <br>
						<br> Consulta Realizada em <strong>{{tempConsultation.date}}</strong>
					</h5>
				</div>
				<div class="modal-body">
					<center>
						<h4>Participantes</h4>
					</center>
					<div id="container-details-consultation">
						<table class="table table-bordered table-hover">
							<thead>
								<tr>
									<th><input type="checkbox" ng-model="modelCheck"
										ng-click="addFrequencyListAll();">Presença</th>
									<th>Nome</th>
								</tr>
							</thead>
							<tbody id="tbody-frequency-list">
								<tr ng-repeat="patient in tempConsultation.group.patients">
									<td><input type="checkbox" ng-checked="modelCheck"
										ng-click="addFrequencyList($index);" /></td>
									<td>{{patient.name}}</td>
								</tr>
							</tbody>
						</table>
					</div>
					<div class="modal-body">
						<center><h4>Participantes</h4></center>
						<div id="container-details-consultation">	
							<table class="table table-bordered table-hover">
								<thead>
									<tr>
										<th><input type="checkbox" ng-model="modelCheck" ng-click="addFrequencyListAll();">Presença</th>
										<th>Nome</th>
									</tr>
								</thead>
								<tbody id="tbody-frequency-list">
									<tr ng-repeat="patient in tempConsultation.group.patients">
										<td><input type="checkbox" ng-checked="modelCheck" ng-click="addFrequencyList($index);"/></td>
										<td>{{patient.name}}</td>
									</tr>								
								</tbody>
							</table>
						</div>
					</div>
					<button class="btn btn-primary" ng-click="saveRegister()">Salvar <i class="glyphicon glyphicon-floppy-saved"></i></button>
					<button class="btn btn-default" data-dismiss="modal" ng-click="isPacientConsultation = false; isFreeConsultation = false;">Limpar</button>
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

		</div>

		<div id="modal-day" class="modal fade" role="dialog">
			<div id="modal-horario">
				<div class="alert alert-message hidden" role="alert">
					<span id="alert-text">Alert de Mensagens</span><span
						id="alert-icon"></span>
				</div>
			</div>
			<div class="modal-dialog">
				<div class="modal-content">
					<div id="title-header" class="modal-header">
						<button type="button" class="close" data-dismiss="modal">x</button>
						<h3 id="modal-schedule-title" class="modal-title text-center">Gerenciar
							Horários</h3>
					</div>
					<div class="modal-body">
						<h4 class="modal-description" id="modal-description-body">Horários
							para o dia {{selectedDay.format("DD/MM/YYYY")}}</h4>
						<div ng-show="showConsultationButtons">
							<div class="row">
								<div class="col-xs-6">
									<button class="btn btn-primary text-center btn-block"
										style="height: 200px" ng-click="setPacientConsultation()">
										<span class="glyphicon glyphicon-user" aria-hidden="true"
											style="font-size: 20px"></span>
										<p style="font-size: 20px">
											Cadastrar consulta <br>com paciente
										</p>
									</button>
								</div>

								<div class="col-xs-6">
									<button class="btn btn-primary btn-block" style="height: 200px"
										ng-click="setFreeConsultation()">
										<span class="glyphicon glyphicon-time" aria-hidden="true"
											style="font-size: 20px"></span>
										<p style="font-size: 20px">
											Cadastrar <br>consulta livre
										</p>
									</button>
								</div>
								<br>
								<div class="col-xs-6">
									<br>
									<button class="btn btn-primary btn-block" style="height: 200px"
										ng-click="setGroupConsultation()">
										<!-- <span class="glyphicon glyphicon-group" aria-hidden="true" style="font-size:20px"></span>-->
										<img src="<c:url value='/resources/images/icon-group.png'/>">
										<p style="font-size: 20px">
											Cadastrar <br> consulta com grupo
										</p>
									</button>
								</div>
								<div class="col-xs-6">
									<br>
									<button class="btn btn-danger btn-block" style="height: 200px"
										ng-click="cancelAllConsultation(selectedDay)">
										<span class="glyphicon glyphicon-trash" aria-hidden="true"
											style="font-size: 20px"></span>
										<p style="font-size: 20px">
											Cancelar <br> todas as Consultas <br> do Dia
										</p>
									</button>
								</div>
							</div>
						</div>

						<div class="modal fade" role="dialog"
							id="modal-all-cancel-consultation">
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
										<h4>Deseja realmente cancelar todos os horários?</h4>
									</div>
									<div class="modal-body">
										<div id="div-send-email">
											<label>Enviar email para o paciente:</label>
											<textarea ng-model="message_cancel"
												placeholder="Escrever email..." id="text-area-email"
												class="no-resize form-control" rows="3"></textarea>
										</div>
										<div class="margin-top">
											<button id="btn-cancel-consultation" class="btn btn-danger"
												value="" name="id"
												ng-click="registerAllCancelConsultation(allConsToCancel,message_cancel)">Sim,
												cancelar</button>
											<button class="btn btn-default" data-dismiss="modal">Voltar</button>
										</div>
									</div>
								</div>
							</div>
						</div>

						<!-- INÍCIO - TESTE  -->
						<!-- <div class="panel panel-primary">
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
					</div>-->
						<!-- FIM - TESTE  -->

						<div class="panel panel-primary"
							ng-show="isGroupConsultation == true">
							<div class="panel-heading">
								<h3 class="panel-title">Cadastrar com grupo</h3>
							</div>
							<div class="panel-body">
								<form class="form-horizontal center-block">
									<div id="row-add-schedules">
										<div class="row row-schedule-id">
											<label class="col-lg-1 control-label">Início</label>
											<div class="col-md-5">
												<div class="margin-left input-group date">
													<input id="grupoInicio" type="text"
														ng-model="initGrpSchTemp"
														class="form-control input-small datetimepicker1">
												</div>
											</div>
											<label class="col-lg-1 control-label">Fim</label>
											<div class="col-md-5">
												<div class="input-group date">
													<input id="grupoFim" type="text" ng-model="endGrpSchTemp"
														class="form-control input-small datetimepicker1">
												</div>
											</div>
										</div>
									</div>
								</form>
								<form class="form-horizontal">
									<br>
									<table class="table table-bordered table-hover">
										<thead>
											<tr>
												<th>Título</th>
												<th>Remover</th>
											</tr>
										</thead>
										<tbody id="tbody-schedules-description">
											<tr ng-show="grp != null">
												<td>{{grp.title}}</td>
												<td><button class="btn btn-danger"
														ng-click="removeGroup()">
														<span class="glyphicon glyphicon-minus-sign"
															aria-hidden="true" style="font-size: 15px"></span>
													</button></td>
											</tr>
										</tbody>
									</table>

									<div id="row-and-schedules">
										<div id="row-add-schedules">
											<div class="row">
												<br>
												<div class="col-xs-12">
													<input type="text" class="form-control"
														ng-model="buscarGrupo" placeholder="Pesquisar Grupo">
												</div>
											</div>
										</div>
									</div>
									<br>
									<table class="table table-bordered table-hover">
										<thead>
											<tr>
												<th>Título</th>
												<th>Selecionar</th>
											</tr>
										</thead>
										<tbody id="tbody-schedules-description">
											<tr
												ng-repeat="group in groups | filter: {title: buscarGrupo}">
												<td>{{group.title}}</td>
												<td><button class="btn btn-primary"
														ng-click="addGroup(group)">
														<span class="glyphicon glyphicon-plus-sign"
															aria-hidden="true" style="font-size: 15px"></span>
													</button></td>
											</tr>
										</tbody>
									</table>
								</form>
								<br>
							</div>
						</div>

						<div class="panel panel-primary"
							ng-show="isPacientConsultation == true">
							<div class="panel-heading">
								<h3 class="panel-title">Cadastrar com paciente</h3>
							</div>
							<div class="panel-body">
								<form class="form-horizontal center-block">
									<div id="row-add-schedules">
										<div class="row row-schedule-id">
											<label class="col-lg-1 control-label">Início</label>
											<div class="col-md-5">
												<div
													class="timepicker-init margin-left input-group timepicker">
													<input id="pacienteInicio" type="text"
														ng-model="initSchTemp"
														class="form-control input-small datetimepicker1">
												</div>
											</div>

											<label class="col-lg-1 control-label">Fim</label>
											<div class="col-md-5">
												<div class="timepicker-end input-group timepicker">
													<input id="pacienteFim" type="text"
														class="form-control input-small datetimepicker1"
														ng-model="endSchTemp">
												</div>
											</div>
										</div>
										<br>
										<table class="table table-bordered table-hover">
											<thead>
												<tr>
													<th>Nome</th>
													<th>CPF</th>
													<th>Remover</th>
												</tr>
											</thead>
											<tbody id="tbody-schedules-description">
												<tr ng-show="ptt != null">
													<td>{{ptt.name}}</td>
													<td>{{ptt.cpf}}</td>
													<td><button class="btn btn-danger"
															ng-click="removeConPatient()">
															<span class="glyphicon glyphicon-minus-sign"
																aria-hidden="true" style="font-size: 15px"></span>
														</button></td>
												</tr>
											</tbody>
										</table>
									</div>
								</form>
								<br>
								<form class="form-horizontal">
									<div id="row-and-schedules">
										<div id="row-add-schedules">
											<div class="row">
												<div class="col-xs-12">
													<input type="text" class="form-control"
														ng-model="buscarPacientePeloNome"
														placeholder="Nome do Paciente">
												</div>
											</div>
										</div>
									</div>
									<br>
									<table class="table table-bordered table-hover">
										<thead>
											<tr>
												<th>Nome</th>
												<th>CPF</th>
												<th>Selecionar</th>
											</tr>
										</thead>
										<tbody id="tbody-schedules-description"
											ng-if="buscarPacientePeloNome != ''">
											<tr
												ng-repeat="p in patients | filter: {name: buscarPacientePeloNome}">
												<td>{{p.name}}</td>
												<td>{{p.cpf}}</td>
												<td><button class="btn btn-primary"
														ng-click="addConPatient(p)">
														<span class="glyphicon glyphicon-plus-sign"
															aria-hidden="true" style="font-size: 20px"></span>
													</button></td>
											</tr>
										</tbody>
									</table>
								</form>
								<br>

							</div>
						</div>


						<div class="panel panel-primary"
							ng-show="isFreeConsultation == true">
							<div class="panel-heading">
								<h3 class="panel-title">Cadastrar Horários</h3>
							</div>
							<div class="panel-body" id="panel-schedules">
								<form class="form-horizontal">
									<div id="row-add-schedules">
										<div class="row row-schedule-id">
											<label class="col-lg-1 control-label">Início</label>
											<div class="col-md-4">
												<div class="margin-left input-group timepicker">
													<input id="livreInicio" type="text"
														class="form-control input-small datetimepicker1">
												</div>
											</div>

											<label class="col-lg-1 control-label">Fim</label>
											<div class="col-md-4">
												<div class="input-group timepicker">
													<input id="livreFim" type="text"
														class="form-control input-small datetimepicker1">
												</div>
											</div>
											<div class="col-md-2">
												<button ng-click="addTempSchedule()" type="button"
													class="btn btn-primary add-schedule">
													<span class="glyphicon glyphicon glyphicon-plus"></span>
												</button>
											</div>
										</div>
									</div>
								</form>
							</div>
						</div>

						<div class="panel panel-primary"
							ng-show="isFreeConsultation == true">
							<div class="panel-heading">
								<h3 class="panel-title">Horários</h3>
							</div>
							<div class="panel-body" id="panel-schedules">
								<form class="form-horizontal">
									<div id="row-add-schedules">

										<h5 ng-show="generetedSchedules.length == 0">Nenhum
											horário cadastrado</h5>

										<div class="row row-schedule-id"
											ng-repeat="sch in generetedSchedules">
											<label class="col-lg-1 control-label">Início</label>
											<div class="col-md-4">
												<div class="margin-left input-group timepicker">
													<input type="text"
														value="{{sch.schedule.dateInit.format('HH:mm')}}"
														class="form-control input-small" disabled="disabled">
													<span class="input-group-addon"><i
														class="glyphicon glyphicon-time"></i></span>
												</div>
											</div>

											<label class="col-lg-1 control-label">Fim</label>
											<div class="col-md-4">
												<div class="input-group timepicker">
													<input type="text" class="form-control input-small"
														value="{{sch.schedule.dateEnd.format('HH:mm')}}"
														disabled="disabled"> <span
														class="input-group-addon"> <i
														class="glyphicon glyphicon-time"></i>
													</span>
												</div>
											</div>
											<div class="col-md-2">
												<button type="button" class="btn btn-danger add-schedule"
													ng-click="removeSchedule($index)">
													<span class="glyphicon glyphicon glyphicon-minus"> </span>
												</button>
											</div>
										</div>
									</div>
								</form>
							</div>
						</div>

						<div class="panel panel-primary"
							ng-show="isRemoveAllConsultation == true">
							<div class="panel-heading">
								<h3 class="panel-title">Deseja Realmente Cancelar todas as
									Consultas deste Dia? (Disponível, Agendada, Em Espera)</h3>
							</div>
							<div class="panel-body" id="panel-schedules">
								<form class="form-horizontal">
									<div id="row-add-schedules">

										<h5 ng-show="generetedSchedules.length == 0">Nenhum
											horário cadastrado</h5>

										<div class="row row-schedule-id"
											ng-repeat="sch in generetedSchedules">
											<label class="col-lg-1 control-label">Início</label>
											<div class="col-md-4">
												<div class="margin-left input-group timepicker">
													<input type="text"
														value="{{sch.schedule.dateInit.format('HH:mm')}}"
														class="form-control input-small"> <span
														class="input-group-addon"><i
														class="glyphicon glyphicon-time"></i></span>
												</div>
											</div>

											<label class="col-lg-1 control-label">Fim</label>
											<div class="col-md-4">
												<div class="input-group timepicker">
													<input type="text" class="form-control input-small"
														value="{{sch.schedule.dateEnd.format('HH:mm')}}">
													<span class="input-group-addon"> <i
														class="glyphicon glyphicon-time"></i>
													</span>
												</div>
											</div>
											<div class="col-md-2">
												<button type="button" class="btn btn-danger add-schedule"
													ng-click="removeSchedule($index)">
													<span class="glyphicon glyphicon glyphicon-minus"> </span>
												</button>
											</div>
										</div>
									</div>
								</form>
							</div>
						</div>



					</div>
					<div class="modal-footer" ng-init = "modal = 1">
						<button type="submit" class="btn btn-primary"
							ng-if="isPacientConsultation == true" ng-disabled="ptt == null"
							ng-click="saveConsultations(ptt,selectedDay,modal)">
							Salvar <i class="glyphicon glyphicon-floppy-saved"></i>
						</button>
						<button type="submit" class="btn btn-primary"
							ng-if="isGroupConsultation == true" ng-disabled="grp == null"
							ng-click="saveGroupConsultation(grp,selectedDay,modal)">
							Salvar <i class="glyphicon glyphicon-floppy-saved"></i>
						</button>
						<button type="submit" class="btn btn-primary"
							ng-if="isFreeConsultation == true"
							ng-disabled="generetedSchedules.length == 0"
							id="btn-confirm-schedules"
							ng-click="saveFreeConsultations(generetedSchedules,modal)">
							Salvar <i class="glyphicon glyphicon-floppy-saved"></i>
						</button>
						<button type="button" class="btn btn-default" data-dismiss="modal"
							ng-click="isPacientConsultation = false; isFreeConsultation = false;">Voltar</button>
					</div>
				</div>
			</div>
		</div>

		<!-- VERIFICAR AS FUNCIONALIDADES DESTA MODAL COM BASE NA MASTER DO GIT (da versão anteior)  -->
		<div id="modal-day-scheduler" class="modal fade" role="dialog">
			<div class="modal-dialog">
				<div class="modal-content">
					<div id="title-header" class="modal-header">
						<button type="button" class="close" data-dismiss="modal">x</button>
						<h3 id="modal-schedule-title" class="modal-title text-center">Cadastrar
							Horário</h3>
					</div>
					<div class="modal-body">
						<h4 class="modal-description" id="modal-description-body"></h4>
						<label id="label-date-clicked" class="hidden"></label>
						<!--<div class="panel panel-primary">
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
								<button type="button" class="btn btn-primary" id="btn-generate-schedules">
									Gerar Horários <i class="glyphicon glyphicon-time"></i>
								</button>
							</div>
						</div>
					</div>-->
						<div class="panel panel-primary">
							<div class="panel-heading">
								<h3 class="panel-title">Gerenciador de Horários</h3>
							</div>
							<div class="panel-body" id="panel-schedules-hours">
								<form class="form-horizontal">
									<div id="row-add-schedules-hours">
										<div class="row row-schedule-id">
											<label class="col-lg-1 control-label">Início</label>
											<div class="col-md-4">
												<div class="margin-left input-group">
													<div class="input-group timepicker">
														<input id="tmp-init-plus-1" type="text"
															class="form-control input-small datetimepicker1">
													</div>
												</div>
											</div>

											<label class="col-lg-1 control-label">Fim</label>
											<div class="col-md-4">
												<div class="input-group">
													<div class="input-group timepicker">
														<input id="tmp-end-plus-1" type="text"
															class="form-control input-small datetimepicker1">
													</div>
												</div>
											</div>
											<div class="col-md-2">
												<button type="button" class="btn btn-primary add-schedule"
													ng-click="plusScheculer()">
													<span class="glyphicon glyphicon glyphicon-plus"></span>
												</button>
											</div>
										</div>
										<!-- TABELAS DE HORÁRIOS GERADOS AUTOMÁTICAMENTE -->
										<br />
										<table class="table table-bordered table-hover">
											<thead>
												<tr>
													<th>Início</th>
													<th>Fim</th>
													<th>Remover</th>
												</tr>
											</thead>
											<tbody id="tbody-table-days">
												<!-- Preenchida dinamicamente -->
												<tr ng-repeat="d in schedulerOfDay.schedules">
													<td>{{d.timeInit}}</td>
													<td>{{d.timeEnd}}</td>
													<td><button value={{d.idSchedule}}
															class="btn btn-danger action-day remove-day"
															ng-click="removeScheduler(d)">
															<i class="glyphicon glyphicon-trash"></i>
														</button></td>
												</tr>
											</tbody>
										</table>
									</div>
								</form>
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<button type="submit" class="btn btn-primary"
							id="btn-confirm-schedules-hours" ng-click="confirmeSchduler()">
							Salvar <i class="glyphicon glyphicon-floppy-saved"></i>
						</button>
						<button type="button" class="btn btn-default" data-dismiss="modal">Voltar</button>
					</div>
				</div>
			</div>
		</div>

	</div>

	<div id="snackbar"></div>
