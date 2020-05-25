$(document).ready(function(){	
	
	$("#register").submit(function(e){
		
		var parameters = {
			username :  $("#username").val(),
			password : $("#password").val(),
			repeatedPassword : $("#repeatedPassword").val()
		};
		
		$.post("./RegistrationServlet", parameters, function(data){
			console.log(data);
			
			if(data.status == "success"){
				window.location.replace("index.html");
			
			}else if(data.status == "fail"){
				alert("Username already exists! Try with another one!");
				window.location.reload();
			}
		});
	});
	
});