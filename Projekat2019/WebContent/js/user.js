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
			$("#updateButton").prop("disabled", false);
		}
		else if(loggedinUser.id === user.id){
			$("#username").val(data.user.username);
			$("#password").val(user.password);
			$("#repeatedPassword").val(user.password);
			$("#registrationDate").val(new Date(user.registrationDate).toString().substring(4,16));
			$("#role").val(data.user.role.toString());
		}
		if(loggedinUser.role == "ADMINISTRATOR"){
			
			$("#password").prop("disabled", true);
			$("#repeatedPassword").prop("disabled", true);
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
								   "<td><a href='Ticket.html?id=" + ticket.id +"'>" + new Date(ticket.dateAndTime).toString().substring(0,21) + "</a></td></tr>");
				
			}
		}else if(data.status == "fail"){
			alert("Something went wrong! Please try again!");
			window.location.reload();
		}	
	});
}


//--------------------------------------------------------------------------------------------------------------------------------



$(document).ready(function(){
	id = window.location.search.split("=")[1];

	if(loggedinUser != null){
		openOneUser();		
		getAllTickets();
		
	}
	
	if(loggedinUser.id == id){
		
		
		$("#updateButton").on("click", function(event){
			var pass = $("#password").val();
			var repPass = $("#repeatedPassword").val();
			
			if(pass === repPass){
			
			params = {
					"user" : "user",
					"id" : id,
					"pass" : pass
			}
		
			$.post("./UpdateUserServlet", params, function(data){
				if(data.status == "success"){
					window.location.reload();
				
				}else if(data.status == "fail"){
					alert("Something went wrong! Please try again!");
					window.location.reload();
				}
			});
			}else{
				alert("Password and repeated password are not same!");
			}
		});
	
		
	}else if(loggedinUser.role == "ADMINISTRATOR"){
			$("#delete").prepend("<button id='deleteButton' type = 'submit'> DELETE USER </button>");
			
			var id = window.location.search.split("=")[1];

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
						window.location.reload();
					
					}else if(data.status == "fail"){
						alert("Something went wrong! Please try again!");
						window.location.reload();
					}
				});
			});	
			
			$("#deleteButton").on("click", function(data){
				
				var answer = window.confirm("Are You sure You want to delete user?");
				
				if(answer){
					
					$.get("./DeleteUserServlet", {id:id}, function(data){
						
						if(data.status == "success"){
							window.location.replace("./users.html");
						}else if(data.status == "fail"){
							alert("Something went wrong! Please try again!");
							window.location.reload();
						}
					});
				}
				
			});
		}		
	});
});