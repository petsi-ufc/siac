<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div id="home-content">

	<div id="left-bar"></div>

	<div id="right-bar">
		<div id="calendar_schedule" class="calendar">

			<div id="modal-schedule" class="modal fade" role="dialog">
				<div class="modal-dialog">
					<div class="modal-content">
						<div id="title-header" class="modal-header">
							<button type="button" class="close" data-dismiss="modal">X</button>
							<h4 class="modal-title"></h4>
						</div>
						<div class="modal-body">
						
							<div id="modal-content-left">
								<div id="initial-date">
									<div class="input-append date" id="dp3" data-date="12-02-2012"
										data-date-format="dd-mm-yyyy">
										<input id="input-calendar" class="span2 form-control" size="16"
											type="text" value="12-02-2012"> <span class="add-on">
											<i class="icon-th"></i>
										</span>
									</div>
								</div>
								
								<select id="select-repeat" class="form-control">
									<option>Não repetir</option>
									<option>Semanalmente</option>
									<option>Mensalmente</option>
								</select>
								
							</div>
							
							<div id="modal-content-right">
								<table id="table-new-time" class="table table-striped">
									<thead>
										<tr>
											<th>
												<h6>Horario Inicial</h6>
											</th>
											<th>
												<h6>Horario Final</h6>
											</th>
										</tr>
									</thead>
									<tbody id="tbody-add-time">
										<tr>
											<td>
												<div class="timepicker-schedule">
													<div class="input-group bootstrap-timepicker timepicker">
														<input id="timepicker1" type="text"
															class="form-control input-small"> <span
															class="input-group-addon"><i
															class="glyphicon glyphicon-time"></i></span>
													</div>
												</div>
											</td>

											<td>
												<div class="timepicker-schedule">
													<div class="input-group bootstrap-timepicker timepicker">
														<input id="timepicker2" type="text"
															class="form-control input-small"> <span
															class="input-group-addon"><i
															class="glyphicon glyphicon-time"></i></span>
													</div>
												</div>
											</td>
										</tr>

									</tbody>
								</table>
								<button id="add-time-button" onclick="addTuple()	" type="button" class="btn btn-default btn-lg">Novo Horário
									<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
								</button>
							</div>
							<div class="clear"></div>
						</div>
						
						<div class="modal-footer">
							<p align="left">Salvar alterações?</p>
							<button type="button" class="btn btn-default"
								data-dismiss="modal">Não</button>
							<button type="button" class="btn btn-primary">Sim</button>

						</div>
					</div>
				</div>
			</div>
		</div>

	</div>
</div>
</div>