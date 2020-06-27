$.get("./LoginServlet", function(data){
	var loggedinUser = data.loggedinUser;
	var allUsers;
	
//-------------------------------------------------------------------------------------------------------------------------------
	
	//function for getting all users from db
	
	function getAllUsers(){
		
		$.get("./AllUsersServlet", function(data){
			console.log(data);
			
			if(data.status == "success"){
				allUsers = data.users;
	
				for(user of allUsers){
					$("#table").append("<tr>" + 
									   "<td></td>" +
									   "<td><a href='User.html?id=" + user.id +"'>" + user.username + "</a></td>" + 
									   "<td>" + new Date(user.registrationDate).toString().substring(4,16) + "</td>" + 
									   "<td>" + new Date(user.registrationDate).toString().substring(16,21) + "</td>" +
									   "<td>" + user.role.toString() + "</td>");
				}
			}else if(data.status == "fail"){
				alert("Something went wrong! Please try again!");
				window.location.reload();
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
			}else if(data.status == "fail"){
				alert("Something went wrong! Please try again!");
				window.location.reload();
			}
		});
	}
	
//--------------------------------------------------------------------------------------------------------------------------------
	function searchUsersRole(){
			
			var value = $(this).val();
			params = {
					"value" : value
			}
			$.post("./SearchUserServlet", params, function(data){

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
				}else if(data.status == "fail"){
					alert("Something went wrong! Please try again!");
					window.location.reload();
				}
			});
		}
	
//--------------------------------------------------------------------------------------------------------------------------------
	function sortUsers(){
		
		var criteria = $("#criteria").val();
		var ascDesc  = $("#ascOrDesc").val();
					
		 $("#table").find("tr:gt(0)").remove();
	        switch(criteria){
	            case "name":
	                if(ascDesc == "ASC"){
	                    allUsers.sort(function(a, b){
	                        return(a.username > b.username) ? 1 : -1;
	                    });
	                }else{
	                	allUsers.sort(function(a,b){
	                      return (a.username < b.username) ? 1 : -1;
	                  });
	              }
	              break;
	            case "role":
	            	if(ascDesc =="ASC"){
	            		allUsers.sort(function(a,b){
	            			return(a.role > b.role) ? 1 : -1;
	            		});
	            	}else{
	            		allUsers.sort(function(a,b){
	            			return(a.role < b.role) ? 1 : -1;
	            		});
	            	}
	            	break;
	        default:
	                return;
	        }
	        for(user of allUsers){
				$("#table").append("<tr>" + 
								   "<td></td>" +
								   "<td><a href='User.html?id=" + user.id +"'>" + user.username + "</a></td>" + 
								   "<td>" + new Date(user.registrationDate).toString().substring(4,16) + "</td>" + 
								   "<td>" + new Date(user.registrationDate).toString().substring(16,21) + "</td>" +
								   "<td>" + user.role.toString() + "</td>");
			}
	
	}
	
	$(document).ready(function(){
	 
		if(loggedinUser != null){
			$("#myProfile").click(function(event){
				window.location.replace("./User.html?id=" + loggedinUser.id);
			});
		}
		
		if(loggedinUser != null && loggedinUser.role == "ADMINISTRATOR"){
			getAllUsers();	
				
		
		//open modal for sorting
		document.getElementById("sortUsers").addEventListener("click", function(){
			document.querySelector(".bgg-modal").style.display="flex";
		});
		
		//close modal for sorting
		document.querySelector(".closeSort").addEventListener("click", function(){
			document.querySelector(".bgg-modal").style.display = "none";
		});
		
		//close modal when click on sort button
		document.getElementById("sort").addEventListener("click", function(){
			document.querySelector(".bgg-modal").style.display = "none";
		});
		
		$("#roleSelect").change(searchUsersRole);		
		$("#search").click(searchUsers);
		$("#sort").on("click", sortUsers);
		}
	});
});