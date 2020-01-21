$.get("./LoginServlet", function(data){
	var loggedinUser = data.loggedinUser;

//------------------------------------------------------------------------------------------------------------------------------
	//FUNCTION FOR GETTING ALL PROJECTIONS From DB!
	
	function getAllProjections(){
		
		var allProjections;
		var table = $("#table");
		
		$.get("./AllProjections", function(data){
			console.log(data);
			
			if(data.status == "success"){
				allProjections = data.projections;
				
				for(Projection of allProjections){
					table.append("<tr>" +
								 "<td><a href='projection.html?id=" + Projection.id + "'>" + Projection.movie.name + "</a></td>" +
								 "<td>" + new Date(Projection.dateAndTime).toString().substring(4,25) + "</td>" + 
								 "<td>" + Projection.type.name + "</td>" + 
								 "<td>" + Projection.hall.name + "</td>" + 
								 "<td>" + Projection.price + "</td>");
				}
			}
		});	
	}


$(document).ready(function(){
	
	getAllProjections();

	//open modal for logging 
	document.getElementById("login").addEventListener("click", function(){
		console.log('upao');
		document.querySelector(".bg-modal").style.display = "flex";
	});
	
	//close modal for logging
	document.querySelector(".close").addEventListener("click", function(){
		document.querySelector(".bg-modal").style.display = "none";
	});

//--------------------------------------------------------------------------------------------------------------------------------
//FUNCTION FOR LOGGING!
	$("#logIn").submit(function(e){
		
		var parameters = {
			username : $('#username').val(),
			password : $('#password').val()
		};
		
		$.post("./LoginServlet", parameters, function(data){
			console.log(data);
			
			if(data.status == "success"){
				window.location.reload();
				console.log(data);
			}
			else if(data.status == "fail"){
				alert("Wrong username or password!");
				window.location.replace("./index.html");
			}
		});
	});
	
//----------------------------------------------------------------------------------------------------------------------------------


	//if loggedinUser is basic user
		
	if(loggedinUser != null ){
		
		//changing login to logout
		$("#login").text("Log out");
		
		$("#login").click(function(event){
			event.preventDefault();
			$.get("LogoutServlet", function(data){
				if(data.status == "unauthenticated"){
					window.location.replace("./index.html");
				}
			});
			return false;
		});
		
		//changing about to myProfile
		$("#about").text("My profile");
			
			$("#about").click(function(event){

				window.location.replace("./User.html?id=" + loggedinUser.id);

			});
			
		//if loggedinUser is admin

		if(loggedinUser.role == "ADMINISTRATOR"){
			
			//add li for all users
			var ul = $("#ul");
			ul.append("<li><a href = './users.html'>All users</a></li>");
			
		}

		}
	
//----------------------------------------------------------------------------------------------------------------------------------


	
//----------------------------------------------------------------------------------------------------------------------------------
//	function onClick(element) {
//		  document.getElementById("img01").src = element.src;
//		  document.getElementById("modal01").style.display = "block";
//		}
	
//----------------------------------------------------------------------------------------------------------------------------------

	//$("#img").on("click", onClick(element));


});

});