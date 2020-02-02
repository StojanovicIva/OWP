$.get("./LoginServlet", function(data){
	var loggedinUser = data.loggedinUser;
	var user;
	var id;
	var allTickets;

function openOneUser(){
	
	id = window.location.search.split("=")[1];
	
	$.get("./UserServlet", {id:id}, function(data){
		console.log(data);
		
		user = data.user;
		
		if(loggedinUser.id != user.id){
			$("#username").val(data.user.username);
			$("#registrationDate").val(new Date(user.registrationDate).toString().substring(4,16));
			$("#role").val(data.user.role.toString());			
		}
		else if(loggedinUser.id === user.id){
			$("#username").val(data.user.username);
			$("#password").val(user.password);
			$("#repeatedPassword").val(user.password);
			$("#registrationDate").val(new Date(user.registrationDate).toString().substring(4,16));
			$("#role").val(data.user.role.toString());
		}
		else if(loggedinUser != null && loggedinUser.role == "ADMINISTRATOR"){
			$("#role").prop("disabled", false);
		}
	
	});
}

//-------------------------------------------------------------------------------------------------------------

function getAllTickets(){
	$.get("./AllTicketsServlet", {id:id}, function(data){
		console.log(data);
		
		if(data.status == "success"){
			allTickets = data.tickets;

			for(ticket of allTickets){
				$("#tableOfTickets").append("<tr>" + 
								   "<td></td>" +
								   "<td><a href='Ticket.html?id=" + ticket.id +"'>" + new Date(ticket.dateAndTime).toString().substring(0,21) + "</a></td>");
			}
		}
		
	});
}


//--------------------------------------------------------------------------------------------------------------------------------



$(document).ready(function(){
	openOneUser();
	
	if(loggedinUser != null){
		
		getAllTickets();
		
	}
	
	else if(loggedinUser.id == id){
		

		

		
			$("#updateButton").on("click", function(event){
				
				var pass = $("#password").val();
				var repPass = $("#repeatedPassword").val();
				
				if(pass === repPass){
					alert("iste");
				
				alert("klik");
				params = {
						"user" : "user",
						"id" : id,
						"pass" : pass
				}
			
				$.post("./UpdateUserServlet", params, function(data){
					alert("otiso u servlet");
					if(data.status == "success"){
						window.location.reload();
					}
				});
				}else{
					alert("Password and repeated password are not same!");
				}
			});
		
		
	}else if(loggedinUser.role == "ADMINISTRATOR"){
			
			$("#role").prop("disabled", false);
			var role = $("#role").val();
			
			
			$("#updateButton").on("click", function(event){

				params = {
						"user" : "admin",
						"id" : id,
						"role" : role
				}
			
				$.post("./UpdateUserServlet", params, function(data){
					
					if(data.status == "success"){
						alert("success");
						window.location.reload();
					}
				});
				});
		
	}
	
});
});