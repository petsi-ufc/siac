<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div id="home-content">
	<div id="left-bar">
		<img id="avatar-img" class="img-responsive"
			src="<c:url value='/resources/images/user_avatar.png'/>">

		<!-- 		<div id="modal-config" class="modal fade" role="dialog">
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
		</div> -->

		<div id="box-services">
			<h3>Serviços disponí­veis</h3>
			<ul id="ul-services" class="nav nav-pills nav-stacked" role="tablist">
				<li class="nav-divider"></li>
				<li id="my-calend" class="service active"><a class="link-service" id="0">Meu calendário</a></li>
				<li id="my-consults" class="service"><a class="link-service" id="1">Minhas consultas </a></li>
			</ul>
		</div>
		<div id="calendar-legend">
			<table style='margin-left: 70px'>
				<thead>
					<tr>
						<th colspan="2"><h3>Legenda de Consultas</h3></th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>
							<h4>Disponível</h4>
						</td>
						<td>
							<div class='legend-color' style='background-color: #32CD32'></div>
						</td>
					</tr>

					<tr>
						<td>
							<h4>Agendada</h4>
						</td>
						<td>
							<div class='legend-color' style='background-color: #4682B4'></div>
						</td>
					</tr>

					<tr>
						<td>
							<h4>Realizada</h4>
						</td>
						<td>
							<div class='legend-color' style='background-color: grey'></div>
						</td>
					</tr>

					<tr>
						<td>
							<h4>Reservada</h4>
						</td>
						<td>
							<div class='legend-color' style='background-color: #D9D919'></div>
						</td>
					</tr>

					<tr>
						<td>
							<h4>Cancelada</h4>
						</td>
						<td>
							<div class='legend-color' style='background-color: #FF0000'></div>
						</td>
					</tr>
				</tbody>

			</table>



		</div>
	</div>



	<div id="right-bar">
		<div class="alert alert-danger" id="alert-schedules">Não há
			nenhum horário cadastrado para este serviço!</div>
		<h2 id="my-calendar">Meu calendário</h2>
		
		<div class="content-calendar">
			<div class="calendar" id="calendar-patient"></div>
		</div>
		
		<div id="my-consultations">
			<div id="constutaions-panel" class="panel panel-primary">
				<div class="panel-heading">
					<h3>Suas Consultas</h3>
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
								<th>Status</th>
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
					<!-- <button id="btn-confirm-schedule" type="button"
						class="btn btn-primary" data-dismiss="modal">Confirmar</button> -->
					<button type="button" class="btn btn-default" data-dismiss="modal">Fechar</button>
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
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">Avaliação de Consulta</h4>
				</div>
				<div class="modal-body">
					<p><h4>Comentário</h4></p>
					<input type='hidden' id='input-rating-id' name='idCons'/>
					<textarea id="rating-comment" class="form-control" rows="4"
						placeholder="Deixe seu comentário..." style="overflow:auto;resize:none"></textarea>
					<br />
				
				<!-- Preenchido com Francis/AJAX hehe -->
				
				
					
					<p><h4>Dê uma nota*</h4></p>
					<select id="rating-grade" class="form-control">
						<option>1</option>
						<option>2</option>
						<option>3</option>
						<option>4</option>
						<option>5</option>
					</select>
					
					

				</div>
				<div class="modal-footer">
					<button id="cancel-rating" type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
					<button id="save-rating" type="button" class="btn btn-primary">Salvar</button>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
	<!-- /.modal -->



</div>