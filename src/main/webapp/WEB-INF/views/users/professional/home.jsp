<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div id="home-content">
	<div id="left-bar">
		<img id="avatar-img" class="img-responsive" src="<c:url value='/resources/images/user_avatar.png'/>">
	</div>


	<div id="right-bar">
		<h2 id="my-calendar">Meu calendário</h2>
		<div id="calendar_professional" class="calendar">
		
			<div id="modal-day" class="modal fade" role="dialog">
				<div class="modal-dialog">
					<div class="modal-content">
						<div id = "title-header" class="modal-header">
							<button type="button" class="close" data-dismiss="modal">X</button>
							<h4 class="modal-title"></h4>
						</div>
						<div class="modal-body">
							<h4>Como chamo a agenda do dia???</h4>
						</div>
						<div class="modal-footer">
							<p align="left">Adicionar novos horários?</p>
							<button type="button" class="btn btn-default" data-dismiss="modal">Não</button> 
    						<button type="button" class="btn btn-primary">Sim</button>
    						
						</div>
					</div>
				</div>
			</div>
		
		</div>	
	</div>
</div>