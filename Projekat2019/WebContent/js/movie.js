function openOneMovie(){
	
	var id = window.location.search.split("=")[1];
	
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
		}
		
	});
}

$(document).ready(function(){
	openOneMovie();
});