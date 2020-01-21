$.get("./LoginServlet", function(data){
	var loggedinUser = data.loggedinUser;

	
//-------------------------------------------------------------------------------------------------------------------------------
	
	//function for getting all users from db
	
	function getAllUsers(){
		$.get("./AllUsersServlet", function(data){
			console.log(data);
			
			if(data.status == "success"){
				var allUsers = data.users;
	
				for(user of allUsers){
					$("#table").append("<tr>" + 
									   "<td></td>" +
									   "<td><a href='User.html?id=" + user.id +"'>" + user.username + "</a></td>" + 
									   "<td>" + new Date(user.registrationDate).toString().substring(4,16) + "</td>" + 
									   "<td>" + new Date(user.registrationDate).toString().substring(16,21) + "</td>" +
									   "<td>" + user.role.toString() + "</td>");
				}
			}
			
		});
	}

	
//--------------------------------------------------------------------------------------------------------------------------------

	function searchUsers(){
		
		var username = $("#findUserInput").val();
		
		var param = {
				"username" : username
		}
		
		$.get("./SearchUserServlet", param, function(data){
			
			if(data.status == "success"){
				users = data.users;
				
				$("#table").find("tr:gt(0)").remove();
				for(User of users){
					$("#table").append("<tr>" + 
							   "<td></td>" +
							   "<td><a href='User.html?id=" + User.id +"'>" + User.username + "</a></td>" + 
							   "<td>" + new Date(User.registrationDate).toString().substring(4,16) + "</td>" + 
							   "<td>" + new Date(User.registrationDate).toString().substring(16,21) + "</td>" +
							   "<td>" + User.role.toString() + "</td>");
				}
			}
		});
	}
	
//--------------------------------------------------------------------------------------------------------------------------------

	$(document).ready(function(){
		
	 
		if(loggedinUser != null){
			$("#myProfile").click(function(event){
				window.location.replace("./User.html?id=" + loggedinUser.id);

			});
		}
		
		getAllUsers();
		$("#search").on("click", searchUsers);

	});
	
});