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
			<h3>A��es dispon�veis</h3>
			<ul class="nav nav-pills nav-stacked" role="tablist">
				<li class="nav-divider"></li>
				<li class="action"><a class="link-action" id="0">Gerar Relat�rios</a></li>
				<li class="nav-divider"></li>
				<li class="action"><a class="link-action" id="1">Definir Profissionais</a></li>
				<li class="nav-divider"></li>
				<li class="action"><a class="link-action" id="2">Adicionar Servi�o</a></li>
			</ul>
		</div>
		
		<div id="box-services">
			<h3>Servi�os dispon�veis</h3>
			<ul class="nav nav-pills nav-stacked" role="tablist">
				<li class="nav-divider"></li>
				<li class="service active"><a class="link-service" id="0">Seu calend�rio</a></li>
				<li class="nav-divider"></li>
				<li class="service"><a class="link-service" id="1">Nutri��o</a></li>
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
		<div class="alert alert-danger" id="alert-schedules">N�o h� nenhum hor�rio cadastrado!</div>
		<h2 id="my-calendar">Meu calend�rio</h2>
		<div class="calendar" id="calendar_patient"></div>
		
		<div id="generate-report">
			<h2>Gerar Relat�rios</h2>
			<br />
			<br />
			<br />
			
			<form action="" method="post">
				<div class="form-group">
					<label>Escolha o tipo de relat�rio</label>
					<select class="form-control type-report">
						<option>Escolha o tipo</option>
						<option id="general-report" value="general">Geral</option>
						<option value="by-type">Por servi�o</option>
					</select>
				</div>
				<div class="form-group">
					<label>Escolha o servi�o</label>
					<select class="form-control select-service">
						<option>Escolha o servi�o</option>
						<option>Nutri��o</option>
						<option>Odontologia</option>
						<option>Psicologia</option>
					</select>
				</div>
			</form>
		
		</div>
		
		<div id="set-professional">
			<h2>Definir Profissionais</h2>
		</div>
		
		<div id="add-service">
			<h2>Adicionar Servi�o</h2>
		</div>
	</div>
	
	<div id="modal-schedules" class="modal fade" role="dialog">
			<div class="modal-dialog modal-sm">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">X</button>
						<h4 class="modal-title" id="modal-title-schedule">Hor�rios dia xx</h4>
					</div>
					<div class="modal-body">
						<table id="table-schedule" class="table table-bordered table-hover">
							<thead>
								<tr>
									<th>Hor�rio</th>
									<th id="table-status">Status</th>
								</tr>
							</thead>
							<tbody>
								<tr>
								 	<!-- ESSA TABELA � PREENCHIDA DINAMICAMENTE COM AJAX -->
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