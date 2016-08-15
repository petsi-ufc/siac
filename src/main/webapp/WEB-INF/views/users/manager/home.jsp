<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

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


	</div>


	<div id="right-bar">
	
		<div class="alert alert-message hidden" role="alert">
			<span id="alert-text">Alert de Mensagens</span><span id="alert-icon"></span>
		</div>

		<div class="alert alert-danger" id="alert-schedules">Não há
			nenhum horário cadastrado!</div>

		<div id="generate-report">
			<h2>Gerar Relatórios</h2>
			<br /> <br /> <br />

			<form action="relatorio/geral" method="post" target="_blank" id="form-report">
				<div class="form-group">
					<label>Escolha o tipo de relatório</label> 
					<select class="form-control type-report" id="select-report-type" >
						<option>Escolha o tipo</option>
						<option id="general-report" value="general">Geral</option>
						<option value="by-type">Por serviço</option>
					</select>
				</div>
				<div class="form-group" id="select-servico">
					<label>Escolha o serviço</label> 
					<select class="form-control select-service" id="select-service-type" name="serviceId">
						<option value="option-deafult">Escolha o serviço</option>
					</select>
				</div>
				<div class="form-group" id="select-professional">
					<label>Escolha o profissional</label> 
					<select class="form-control select-professional" id="select-professional-id" name="professionalId">
						<option value="option-default">Escolha o profissional</option>
					</select>
				</div>
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
			<button type="submit" class="btn btn-primary" id="button-generate-report" data-dismiss="modal" onclick="return onGenerateReportButtonClicked();">Gerar</button>
			</form>
		</div>

		<div id="set-professional">
			<h2>Gerenciar Profissionais</h2>
			<br /> <br /> <br />

			<ul class="nav nav-tabs" role="tablist">
				<li role="presentation" class="active"><a
					href="#professional-registered"
					aria-controls="professional-registered" role="tab"
					data-toggle="tab">Profissionais cadastrados</a></li>
				<li role="presentation"><a href="#add-professional"
					aria-controls="add-professional" role="tab" data-toggle="tab">Adicionar
						profissional</a></li>
			</ul>

			<div class="tab-content">
				<div role="tabpanel" class="tab-pane active"
					id="professional-registered">
					<br>
					<table id="table-professional"
						class="table table-hover table-bordered">
						<thead>
							<tr>
								<th>Nome</th>
								<th>Email</th>
								<th>Ativar / Desativar</th>
							</tr>
						</thead>
					</table>
				</div>
				<div role="tabpanel" class="tab-pane" id="add-professional">
					<div class="form-group">
						<br>

						<div class="row">
							<div class="col-xs-8">
								<input type="text" class="form-control"
									id="field-search-professional">
							</div>
							<div class="col-xs-4">
								<button type="submit" class="btn btn-primary pull-left"
									id="button-search-professional">Pesquisar</button>
							</div>
						</div>

						<br>
						<div class="alert alert-danger" id="alert-search-professional">
							Digite, no mínimo, <strong>três caracteres</strong> para
							pesquisar!
						</div>

					</div>
				</div>
			</div>


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
			<button type="submit" class="btn btn-primary pull-left"
				data-toggle="modal" data-target="#modal-add-service">Adicionar
				serviço</button>


			<!-- Modal usado para cadastrar um serviço -->
			<div class="modal fade" id="modal-add-service" tabindex="-1"
				role="dialog" aria-labelledby="modal-service">
				<div class="modal-dialog" role="document">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<h4 class="modal-title" id="modal-service">Adicionar Serviço</h4>
						</div>
						<div class="modal-body">
							<div class="form-group">
								<label>Nome do serviço: </label> <input type="text"
									class="form-control" name="name" id="name-register-service" />
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-default"
									data-dismiss="modal">Fechar</button>
								<button type="button"
									class="btn btn-primary save-register-service">Salvar
									Serviço</button>
							</div>

						</div>

					</div>
				</div>
			</div>
			<!-- Fim da div #modal-add-service -->

			<!-- Modal usado para editar um serviço -->
			<div class="modal fade" id="modal-edit-service" tabindex="-1"
				role="dialog" aria-labelledby="modal-service">
				<div class="modal-dialog" role="document">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<h4 class="modal-title" id="modal-service">Editar Serviço</h4>
						</div>
						<div class="modal-body">

							<div class="form-group">
								<label>Nome do serviço: </label> <input type="text"
									class="form-control" name="name" id="name-edit-service" />
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-default"
									data-dismiss="modal">Fechar</button>
								<button type="button" class="btn btn-primary save-edit-service">Salvar
									Serviço</button>
							</div>

						</div>

					</div>
				</div>
			</div>
			<!-- Fim da dvi #modal-edit-service -->

			<!-- Modal -->
			<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
				aria-labelledby="myModalLabel">
				<div class="modal-dialog" role="document">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<h4 class="modal-title" id="myModalLabel">Modal title</h4>
						</div>
						<div class="modal-body">...</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-default"
								data-dismiss="modal">Close</button>
							<button type="button" class="btn btn-primary">Save
								changes</button>
						</div>
					</div>
				</div>
			</div>

		</div>
	</div>
</div>