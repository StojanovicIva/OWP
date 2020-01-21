function getAllMovies(){
	
	var allMovies;
	var table = $("#table");
	
	$.get("./AllMovies",function(data){
		
		console.log(data);
		
		if(data.status == "success"){
			allMovies = data.movies;
			
			for(Movie of allMovies){
				table.append("<tr>" + 
							 "<td><a href ='movie.html?id=" + Movie.id + "' > " + Movie.name + "</a></td>" +
							 "<td>" + Movie.style + "</td>" +
							 "<td>" + Movie.duration + "</td>" +
							 "<td>" + Movie.distributor + "</td>" + 
							 "<td>" + Movie.country + "</td>" + 
							 "<td>" + Movie.year + "</td>"
						    );
			}
		}
		
	});
}

//----------------------------------------------------------------------------------------------------------------

	function searchMovies(){
		
		var option = $("#criterium").val();
		var input  = $("#searchInput").val();
		
		var params = {
				"criterium" : option,
				"input" : input
		}
		$.get("./SearchMovieServlet", params, function(data){
			alert("wow");
			if(data.status == "success"){
				movies = data.movies;
				alert(data.status);
				$("#table").find("tr:gt(0)").remove();
				for(Movie of movies){
					$("#table").append("<tr>" + 
							 "<td><a href ='movie.html?id=" + Movie.id + "' > " + Movie.name + "</a></td>" +
							 "<td>" + Movie.style + "</td>" +
							 "<td>" + Movie.duration + "</td>" +
							 "<td>" + Movie.distributor + "</td>" + 
							 "<td>" + Movie.country + "</td>" + 
							 "<td>" + Movie.year + "</td>");
				}
			}
		});
	}

$(document).ready(function(){
	getAllMovies();
	
	//open modal for logging 
	document.getElementById("searchMovies").addEventListener("click", function(){
		console.log('upao');
		document.querySelector(".bg-modal").style.display = "flex";
	});
	
	//close modal for logging
	document.querySelector(".close").addEventListener("click", function(){
		document.querySelector(".bg-modal").style.display = "none";
	});
	
	//close modal when click on search button
	document.getElementById("search").addEventListener("click", function(){
		document.querySelector(".bg-modal").style.display = "none";
	});
	
	$("#search").on("click", searchMovies);
});

