<a href="#" data-toggle="modal" data-target="#login-modal">Login</a>

<div class="modal fade" id="login-modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="display: none;">
    	  
  	<div class="modal-dialog">
		<div class="loginmodal-container">
			<h1>Entre na sua Conta</h1><br>
		  <form action="http://127.0.0.1"  method="get" id="loginForm">
				<input type="text" placeholder="Email" id="userEmail" required>
				<input type="password" placeholder="Senha" id="userPassword" required>
				<input type="submit" class="login loginmodal-submit" value="Login">
		  </form>
			
		  <div class="login-help">
			<a onclick="location.href = 'pages/userPageLogOn.html';" data-toggle="modal">Cadastrar</a>
		  </div>
		</div>
	</div>
</div>