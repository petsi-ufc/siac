<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<fieldset>
	<legend>Login</legend>
	
	<form action="<c:url value='j_spring_security_check' />" method="post">
		
		<label>Usuário</label>
		<input name="j_username" type="text">
		<br/>
		<label>Senha</label>
		<input name="j_password" type="password">
		<br/>
		<input type="submit" value="Entrar">
			
	</form>
</fieldset>