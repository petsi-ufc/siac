<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div id="main-container">
	<div id="container-content">
		<img src="<c:url value='/resources/images/logo.png'/>" height="64px" width="200px">
		
		<form action="<c:url value='j_spring_security_check' />" method="post" >
			<div class="form-group">
     			<input type="text" class="form-control" name="j_username" placeholder="CPF" required="required">
				<input type="password" class="form-control" name="j_password" placeholder="Senha" required="required">
				<select name="role" class="form-control">
   					<option>Selecione uma Opção</option>
   					<option>Paciente</option>
   					<option>Profissional</option>
   					<option>Administrador</option>
   				</select>
   			</div>
   			<input type="submit" class="btn btn-info" value="Entrar" id="bt-enter"><br>
			<a href="#" data-toggle="modal" data-target="#modal-forgot-pass">Esqueceu a senha?</a>
			
		</form>

	</div>
</div>
