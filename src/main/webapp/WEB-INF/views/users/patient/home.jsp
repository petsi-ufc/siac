<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<div id="home-content">
	<div id="left-bar">
		<img id="avatar-img" class="img-responsive"
			src="<c:url value='/resources/images/user_avatar.png'/>">
		<center><h5><c:out value="${userLogged.name}" /></h5><center/>

		<div id="box-services">
			<h3>Serviços <br>disponí­veis</h3>
			<ul id="ul-services" class="nav nav-pills nav-stacked" role="tablist">
				<li class="nav-divider"></li>
				<li id="my-calend" class="service active service-item" value="meu"><a
					class="link-service" id="0">Meu calendário</a></li>
				<li class="service service-item"><a class="link-service"
					id="my-consults">Minhas consultas </a></li>
				<li class="service service-item"><a class="link-service"
					id="my-groups">Grupos</a></li>
			</ul>
		</div>
	</div>


	<div id="right-bar">
<div id="calendar-legend" style="margin-left: 135px">
				<table style="margin-left: 0px; margin-bottom: 10px">
					<thead>
						<tr>
							<th colspan="12"><h3 style="text-align: center">Legenda
									de Consultas</h3></th>
						</tr>
					</thead>
					<tbody>
						<tr>

							<td>
								<div class='legend-color'
									style="background-color: #32CD32; margin-left: 12px; margin-right: 12px"></div>
							</td>
							<td>
								<h5>Disponível</h5>
							</td>



							<td>
								<div class='legend-color'
									style="background-color: #4682B4; margin-left: 12px; margin-right: 12px"></div>
							</td>

							<td>
								<h5>Agendada</h5>
							</td>


							<td>
								<div class='legend-color'
									style='background-color: grey; margin-left: 12px; margin-right: 12px'></div>
							</td>

							<td>
								<h5>Realizada</h5>
							</td>

						</tr>
						<tr>
							<td>
								<div class='legend-color'
									style='background-color: #D9D919; margin-left: 12px; margin-right: 12px'></div>
							</td>

							<td>
								<h5>Em Espera</h5>
							</td>


							<td>
								<div class='legend-color'
									style='background-color: #FF0000; margin-left: 12px; margin-right: 12px'></div>
							</td>

							<td>
								<h5>Cancelada</h5>
							</td>

							<td>
								<div class='legend-color'
									style='background-color: #FF7F00; margin-left: 12px; margin-right: 12px'></div>
							</td>

							<td>
								<h5>Ocupada</h5>
							</td>
						</tr>
					</tbody>

				</table>

			</div>
		<div class="alert alert-danger" id="alert-schedules">Não há
			nenhum horário cadastrado para este serviço!</div>
		


		<div class="content-calendar">
			<h2 id="my-calendar-title">Meu Calendário</h2>
			<div class="alert alert-message hidden" role="alert">
				<span id="alert-text">Alert de Mensagens</span><span id="alert-icon"></span>
			</div>
			
			<div class="calendar" id="calendar-patient"></div>
			
			
		</div>

		<div id="my-consultations">
			<h2 id="my-calendar-title">Histórico de Consultas</h2>
			<div class="alert alert-danger alert-message hidden" role="alert">
				<span id="alert-text">Alert de Mensagens</span><span id="alert-icon"></span>
			</div>

			<div id="constutaions-panel" class="panel panel-primary">
				<div class="panel-heading">
					<h3 class="panel-title">Minhas Consultas</h3>

				</div>
				<table id="my-consultations-table" class="table table-hover">
					<thead>
						<tr>
							<th>Serviço</th>
							<th>Data</th>
							<th>Horário</th>
							<th>Status</th>
							<th colspan="2">Opções</th>
						</tr>
					</thead>
					<tbody>

					</tbody>
				</table>
			</div>
		</div>
		
		<div class="panel panel-primary margin-right" id="div-my-groups" style="display:none;" ng-show="true">
			<div class="panel-heading">
				<h1 class="panel-title">Gerenciador de Grupos</h1>
			</div>
			
			<div class="panel-body">
				<h3>Meus Grupos</h3>
				<table id="table-my-groups" class="table table-bordered table-hover">
					<thead>
						<tr>
							<th>Nome</th>
							<th>Ações</th>
						</tr>
					</thead>
					<tbody id="corpo-mygrupo">
						<!-- Preenchido dinâmicamente -->
					</tbody>
				</table>
				<br>
				<h3>Grupos disponíveis</h3>
				<br>
				<table id="table-groups" class="table table-bordered table-hover">
					<thead>
						<tr>
							<th>Nome</th>
							<th>Ações</th>
						</tr>
					</thead>
					<tbody id="corpo-grupo-disponivel">
						<!-- Preenchido dinâmicamente -->
					</tbody>
				</table>
			</div>
		</div>

	</div>

	<div id="modal-event" class="modal fade" role="dialog">
		<div class="modal-dialog modal-md">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">X</button>
					<h4 class="modal-title" id="modal-title-schedule"></h4>
				</div>
				<div class="modal-body">
					<input type="hidden" id="id-service-temp"/>
					<table id="table-schedule" class="table table-hover">
						<thead>
							<tr>

								<th>Horário</th>
								<th>Status</th>
								<th>Ação</th>
							</tr>
						</thead>
						<tbody id="body-table-event">

						</tbody>
					</table>
				</div>
				<div class="modal-footer">
					<!-- <button id="btn-confirm-schedule" type="button"
						class="btn btn-primary" data-dismiss="modal">Confirmar</button> -->
					<button type="button" class="btn btn-default" data-dismiss="modal">Fechar</button>
				</div>
			</div>
		</div>
	</div>
	
	
	<!-- 	Método aproveitado da página do profissional, mudando apenas algumas coisas -->
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
							<label>Motivo do cancelamento da consulta: </label>
							<textarea ng-model="message_cancel" placeholder="Escrever email..." id="text-area-email-cancel"
								class="no-resize form-control" rows="3"></textarea>
						</div>
						<div class="margin-top">
							<button id="cancel-consultation" class="btn btn-danger"
								value="" name="id">Sim, cancelar</button>
							<button class="btn btn-default" data-dismiss="modal">Voltar</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	
	
	<!-- 	Método aproveitado da página do profissional, mudando apenas algumas coisas -->
	<div class="modal fade" role="dialog" id="modal-schedule-consultation">
		<div class="modal-dialog modal-md">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4>Agendar Horário</h4>
				</div>
				<div class="modal-title">
					<h4>Qual o motivo da consulta?</h4>
				</div>
				<div class="modal-body">
					<div id="div-send-email">
						<label>Motivo da Consulta:</label>
						<textarea placeholder="Escrever motivo..." id="text-area-email-schedule" class="no-resize form-control" rows="3"></textarea>
					</div>
					<div class="margin-top">
						<button id="schedule-consultation" class="btn btn-primary"
						value="" name="id">Sim, agendar</button>
						<button class="btn btn-default" data-dismiss="modal">Voltar</button>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div id="modal-rating" class="modal fade" tabindex="-1" role="dialog">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title">Avaliação de Consulta</h4>
				</div>
				<div class="modal-body">
					<p>
					<h5>Comentário</h5>
					</p>
					<input type='hidden' id='input-rating-id' name='idCons' />
					<textarea id="rating-comment" class="form-control" rows="4"
						placeholder="Deixe seu comentário..."
						style="overflow: auto; resize: none"></textarea>
					<br />

					<!-- Preenchido com AJAX -->

					<p>
					<h5>Dê uma nota: </h5>
					</p>
					<select id="rating-grade">
						<option value="1">1</option>
						<option value="2">2</option>
						<option value="3">3</option>
						<option value="4">4</option>
						<option value="5">5</option>
					</select>



				</div>
				<div class="modal-footer">
					<button id="cancel-rating" type="button" class="btn btn-default"
						data-dismiss="modal">Cancelar</button>
					<button id="save-rating" type="button" class="btn btn-primary">Salvar</button>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
	<!-- /.modal -->


	<div id="modal-read-rating" class="modal fade" tabindex="-1"
		role="dialog">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">×</span>
					</button>
					<h5 class="modal-title">Avaliação da Consulta</h5>
				</div>
				<div class="modal-body modal-body-rating">

					<div id="content-rating"></div>
					<!-- Preenchido com AJAX-->

				</div>
				<div class="modal-footer">
					<button id="cancel-rating" type="button" class="btn btn-default"
						data-dismiss="modal">Fechar</button>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
	<!-- /.modal -->
		<script>
		// Check browser support
		if (typeof(Storage) !== "undefined") {
		    // Store
		  var cpf = window.localStorage['userCPF'];
		    console.log(window.localStorage['userCPF']);
		    // Retrieve

		} else {
		    console.log( "Sorry, your browser does not support Web Storage...");
		}
			
		</script>
<div id="snackbar"></div>
</div>