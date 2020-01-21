function openOneUser(){
	
	var id = window.location.search.split("=")[1];
	
	$.get("./UserServlet", {id:id}, function(data){
		console.log(data);
		
		var user = data.user;
		
		$("#username").val(data.user.username);
		$("#password").val(user.password);
		$("#repeatedPassword").val(user.password);
		$("#registrationDate").val(new Date(user.registrationDate).toString().substring(4,16));
		$("#role").val(data.user.role.toString());
	});
}

$(document).ready(function(){
	openOneUser();
});