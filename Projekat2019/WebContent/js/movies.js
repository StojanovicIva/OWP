$.get("./LoginServlet", function(data){
	var loggedinUser = data.loggedinUser;
	
	var allMovies;
	
	function getAllMovies(){
				 
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
			
			if(option == "time"){
				
				var FromThe = $("#FromThe").val();
				var To = $("#To").val();
				
				var params = {
						"criterium" : option,
						"fromThe" : FromThe,
						"to" : To
				}
			}	
			else if(option == "date"){
				
				var FromThe = $("#FromThe").val();
				var To = $("#To").val();
				
				var params = {
						"criterium" : option,
						"fromThe" : FromThe,
						"to" : To
				}
			}
			else{
				var params = {
						"criterium" : option,
						"input" : input
				}
		
			}
			$.get("./SearchMovieServlet", params, function(data){
				
				if(data.status == "success"){
					movies = data.movies;
					
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
		
	//---------------------------------------------------------------------------------------------------------------------------
		
	function sortMovies(){
			
			var criteria = $("#criteria").val();
			var ascDesc  = $("#ascOrDesc").val();
						
			 $("#table").find("tr:gt(0)").remove();
		        switch(criteria){
		            case "name":
		                if(ascDesc == "ASC"){
		                    allMovies.sort(function(a, b){
		                        return(a.name > b.name) ? 1 : -1;
		                    });
		                }else{
		                  allMovies.sort(function(a,b){
		                      return (a.name < b.name) ? 1 : -1;
		                  });
		              }
		              break;
		            case "style":
		            	if(ascDesc =="ASC"){
		            		allMovies.sort(function(a,b){
		            			return(a.style > b.style) ? 1 : -1;
		            		});
		            	}else{
		            		allMovies.sort(function(a,b){
		            			return(a.style < b.style) ? 1 : -1;
		            		});
		            	}
		            	break;
		            case "distributor":
		            	if(ascDesc == "ASC"){
		            		allMovies.sort(function(a,b){
		            			return(a.distributor > b.distributor) ? 1 : -1;
		            		});
		            	}else{
		            		allMovies.sort(function(a,b){
		            			return(a.distributor < b.distributor) ? 1 : -1;
		            		});
		            	}
		            	break;
		            case "country":
		            	if(ascDesc == "ASC"){
		            		allMovies.sort(function(a,b){
		            			return(a.country > b.country) ? 1 : -1;
		            		});
		            	}else{
		            		allMovies.sort(function(a,b){
		            			return (a.country < b.country) ? 1 : -1;
		            		});
		            	}
		            	break;
		            case "time":
		            	if(ascDesc == "ASC"){
		            		allMovies.sort(function(a,b){
		            			return(a.duration > b.duration) ? 1 : (a.duration == b.duration) ? 0 : -1;
		            		});
		            	}else{
		            		allMovies.sort(function(a,b){
		            			return(a.duration < b.duration) ? 1 : -1;
		            		});
		            	}
		            	break;
		            case "year":
		            	if(ascDesc == "ASC"){
		            		allMovies.sort(function(a,b){
		            			return(a.year > b.year) ? 1 :  (a.year == b.year) ? 0 : -1;
		            		});
		            	}else{
		            		allMovies.sort(function(a,b){
		            			return (a.year < b.year) ? 1 : -1;
		            		});
		            	}
		            	break;
		            default:
		                return;
		        }
				for(Movie of allMovies){
					$("#table").append("<tr>" + 
							 "<td><a href ='movie.html?id=" + Movie.id + "' > " + Movie.name + "</a></td>" +
							 "<td>" + Movie.style + "</td>" +
							 "<td>" + Movie.duration + "</td>" +
							 "<td>" + Movie.distributor + "</td>" + 
							 "<td>" + Movie.country + "</td>" + 
							 "<td>" + Movie.year + "</td>");
				}
		   
		 
		}
			
	//---------------------------------------------------------------------------------------------------------------------------
	
	$(document).ready(function(){	
	
	
		getAllMovies();
		
		//open modal for searching 
		document.getElementById("searchMovies").addEventListener("click", function(){
			document.querySelector(".bg-modal").style.display = "flex";
		});
		
		//close modal for searching
		document.querySelector(".close").addEventListener("click", function(){
			document.querySelector(".bg-modal").style.display = "none";
		});
		
		//close modal when click on search button
		document.getElementById("search").addEventListener("click", function(){
			document.querySelector(".bg-modal").style.display = "none";
		});
		
		//open modal for sorting
		document.getElementById("sortMovies").addEventListener("click", function(){
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
		
		if(loggedinUser != null && loggedinUser.role == "ADMINISTRATOR"){
			
			var ul = $("#ul");
			ul.append("<li><a href = './AddMovie.html'>Add new movie</a></li>");
			
		}
		
		$("#sort").on("click", sortMovies);
		$("#search").on("click", searchMovies);
		
		$("#criterium").change(function(){
			
			var value = $(this).val();
			
			if(value == "time"){
				
				document.getElementById('searchInput').style.display = "none";
				document.getElementById('rangeDiv').style.display = "block";								
			
			} else if(value == "date"){ 
			
				document.getElementById('searchInput').style.display = "none";
				document.getElementById('rangeDiv').style.display = "block";
			}
			else{
			    document.getElementById('rangeDiv').style.display = "none";
			    document.getElementById('searchInput').style.display = "block";
			    }
		});
	});
});
	
