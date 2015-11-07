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
			<h3>Ações disponí­veis</h3>
			<ul class="nav nav-pills nav-stacked" role="tablist">
				<li class="nav-divider"></li>
				<li class="action"><a class="link-action" id="0">Gerar
						Relatórios</a></li>
				<li class="nav-divider"></li>
				<li class="action"><a class="link-action" id="1">Gerenciar
						Profissionais</a></li>
				<li class="nav-divider"></li>
				<li class="action"><a class="link-action" id="2">Gerenciar
						Serviços</a></li>
			</ul>
		</div>

		<div id="box-services">
			<h3>Serviços disponí­veis</h3>
			<ul class="nav nav-pills nav-stacked" role="tablist">
				<li class="nav-divider"></li>
				<li class="service active"><a class="link-service" id="0">Seu calendário</a></li>
				<li class="nav-divider"></li>
				<li class="service"><a class="link-service" id="1">Nutrição</a></li>
				<li class="nav-divider"></li>
				<li class="service"><a class="link-service" id="2">Odontologia</a></li>
				<li class="nav-divider"></li>
				<li class="service"><a class="link-service" id="3">Psicologia</a></li>
			</ul>
		</div>
		<div id="calendar-legend">
			<h3>LEGENDA AQUI</h3>
		</div>
	</div>


	<div id="right-bar">
	
		<div class="alert alert-danger" id="alert-schedules">Não há nenhum horário cadastrado!</div>
		
		<h2 id="my-calendar">Meu calendário</h2>
		<div class="calendar" id="calendar_patient"></div>

		<div id="generate-report">
			<h2>Gerar Relatórios</h2>
			<br /> <br /> <br />

			<form action="" method="post">
				<div class="form-group">
					<label>Escolha o tipo de relatório</label>
					
					<select class="form-control type-report">
						<option>Escolha o tipo</option>
						<option id="general-report" value="general">Geral</option>
						<option value="by-type">Por serviço</option>
					</select>
				</div>
				<div class="form-group">
					<label>Escolha o serviço</label> 
					<select class="form-control select-service" disabled="disabled">
						<option>Escolha o serviço</option>
					</select>
				</div>
			</form>

		</div>

		<div id="set-professional">
			<h2>Definir Profissionais</h2>
		</div>

		<div id="add-service">
			<h2>Gerenciar Serviços</h2>
			<br /> <br /> <br />
			<h4>Serviços cadastrados</h4>
			<br />
			<table id="table-services" class="table table-hover table-bordered">
				<thead>
					<tr>
						<th>Nome</th>
						<th>Ativo</th>
						<th>Editar</th>
						<th>Ativar / Desativar</th>
					</tr>
				</thead>
			</table>
			<button type="submit" class="btn btn-primary pull-left" data-toggle="modal" data-target="#modal-add-service">Adicionar serviço</button>
			
			
			<!-- Modal usado para cadastrar um serviço -->
			<div class="modal fade" id="modal-add-service" tabindex="-1" role="dialog" aria-labelledby="modal-service">
				<div class="modal-dialog" role="document">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal" aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<h4 class="modal-title" id="modal-service">Adicionar Serviço</h4>
						</div>
						<div class="modal-body">
							
								<div class="form-group">
									<label>Nome do serviço: </label>
									<input type="text" class="form-control" name="name" id="name-register-service"/>
								</div>
								<div class="modal-footer">
									<button type="button" class="btn btn-default" data-dismiss="modal">Fechar</button>
									<button type="button" class="btn btn-primary save-register-service" >Salvar Serviço</button>
								</div>
						
						</div>
						
					</div>
				</div>
			</div><!-- Fim da dvi #modal-add-service -->
			
			<!-- Modal usado para editar um serviço -->
			<div class="modal fade" id="modal-edit-service" tabindex="-1" role="dialog" aria-labelledby="modal-service">
				<div class="modal-dialog" role="document">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal" aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<h4 class="modal-title" id="modal-service">Editar Serviço</h4>
						</div>
						<div class="modal-body">
							
								<div class="form-group">
									<label>Nome do serviço: </label>
									<input type="text" class="form-control" name="name" id="name-edit-service"/>
								</div>
								<div class="modal-footer">
									<button type="button" class="btn btn-default" data-dismiss="modal">Fechar</button>
									<button type="button" class="btn btn-primary save-edit-service">Salvar Serviço</button>
								</div>
						
						</div>
						
					</div>
				</div>
			</div><!-- Fim da dvi #modal-edit-service -->
			
		</div>
	</div>

	<div id="modal-schedules" class="modal fade" role="dialog">
		<div class="modal-dialog modal-sm">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">X</button>
					<h4 class="modal-title" id="modal-title-schedule">Horários dia
						xx</h4>
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