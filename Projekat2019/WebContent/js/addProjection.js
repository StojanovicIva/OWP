$.get("./LoginServlet", function(data){
	var loggedinUser = data.loggedinUser;

	$(document).ready(function(){
		
		if(loggedinUser!= null && loggedinUser.role == "ADMINISTRATOR"){						
			
			var movieValue;
			var typeValue;
			var hallValue;
			
			var movieName = $("#movieName");
			$.get("./AllMovies", function(data){
				
				var allMovies = data.movies;
				for(movie of allMovies){
					movieName.append("<option value = '" + movie.id + "'>" + movie.name + "</option>");
				}		
				movieName.change(function(){
					movieValue = $(this).val();
					
				});
			});
			var projectType = $("#projectionType");
			$.get("./ProjectionTypeServlet", function(data){
				
				var types = data.projectTypes;
				for(type of types){
					projectType.append("<option value = '" + type.id + "'> + " + type.name +"</option>");
				}
				projectType.change(function(){
					typeValue = $(this).val();
				});
			});
			var hall = $("#hall");
			$.get("./HallServlet", function(data){
				
				var halls = data.halls;
				for(h of halls){
					hall.append("<option value = '" + h.id + "'> + " + h.name +"</option>");
				}
				hall.change(function(){
					hallValue = $(this).val();
				});
			});
			
			
			$("#addProjectionButton").on("click", function(){				
				var params = {
					"movieId": movieValue,
					"dateAndTime" : $("#dateAndTime").val(),
					"projectionType" : typeValue,
					"hall" : hallValue,
					"price" : $("#price").val(),
					"adminsName" : loggedinUser.id
				}
				$.post("./AddProjectionServlet", params, function(data){
					if(data.status == "success"){
						alert("Projection successfully added");
						window.location.replace("./index.html");
					}else if(data.status == "fail"){
						alert("Something went wrong! Please, try again!");
						window.location.reload();
					}
				});
			});

		}		
	});
});	