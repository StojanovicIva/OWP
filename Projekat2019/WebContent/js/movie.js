$.get("./LoginServlet", function(data){
	var loggedinUser = data.loggedinUser;
	var id = window.location.search.split("=")[1];

	function openOneMovie(){		
		
		$.get("./MovieServlet", {id:id}, function(data){
			
			if(data.status == "success"){
				console.log(data);
				var movie = data.movie;
				
				$("#name").val(data.movie.name);
				$("#director").val(data.movie.director);
				$("#actors").val(data.movie.actors);
				$("#style").val(data.movie.style);
				$("#duration").val(data.movie.duration);
				$("#distributor").val(data.movie.distributor);
				$("#country").val(data.movie.country);
				$("#year").val(data.movie.year);
				$("#description").val(data.movie.description);
			
			}else if(data.status == "fail"){
				alert("Something went wrong! Please try again!");
				window.location.reload();
			}
		});
	}

$(document).ready(function(){
	openOneMovie();
	
	if(loggedinUser != null && loggedinUser.role == "USER"){
		
		var id = window.location.search.split("=")[1];
				
		$.get("./MovieServlet", {id:id}, function(data){
			
			if(data.status == "success"){
				
				var name = data.movie.id;
				var params = {
						"id" : id
				}
				$.post("./ProjectionIsInFuture", params, function(data){
					
					if(data.status == "success" && data.projections.length != 0 &&  data.numberOfFreeSeats > 0 && loggedinUser.role === "USER"){
						$("#ul").append("<li id='getTicketLi'><button class='blinking' id='getTicketButton' type='submit'>CLICK HERE AND GET TICKET</button></li>");
						
						$("#getTicketButton").on("click", function(event){							
							window.location.replace("./BuyingATicket.html?movieId=" + id );
							
						});
				
					}else if(data.status == "fail"){
						alert("Something went wrong! Please try again!");
						window.location.replace("./Movies.html");
					}				
				});
			}
		});			
	}
		
		else if(loggedinUser != null && loggedinUser.role == "ADMINISTRATOR"){
			$("#button").prepend("<button id='updateButton' type='submit'>UPDATE</button>");
			$("#button").prepend("<button id='deleteButton' type= 'submit'> DELETE MOVIE</button>");
			
			var id = window.location.search.split("=")[1];
			
			$("#name").prop("disabled", false);
			$("#director").prop("disabled", false);
			$("#actors").prop("disabled", false);
			$("#style").prop("disabled", false);
			$("#duration").prop("disabled", false);
			$("#distributor").prop("disabled", false);
			$("#country").prop("disabled", false);
			$("#year").prop("disabled", false);
			$("#description").prop("disabled", false);
			
			$("#updateButton").on("click", function(event){
				params = {
						"id" : id,
						"name" : $("#name").val(),
						"director" : $("#director").val(),
						"actors" : $("#actors").val(),
						"style" : $("#style").val(),
						"duration" : $("#duration").val(),
						"distributor" : $("#distributor").val(),
						"country" : $("#country").val(),
						"year" : $("#year").val(),
						"description" : $("#description").val()					
				}
				
				$.get("./UpdateMovieServlet", params, function(data){
					if (data.status == "success"){
							window.location.reload();
					}else if(data.status == "fail"){
						alert("Something went wrong! Please try again!");
						window.location.reload();
					}
				});
			});
			
			$("#deleteButton").on("click", function(data){
				
				var answer = window.confirm("Are You sure You want to delete movie?");
				
				if(answer){
			
					$.get("./DeleteMovieServlet" ,  {id:id}, function(data){
						
						if(data.status == "success"){
							window.location.replace("./Movies.html");
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