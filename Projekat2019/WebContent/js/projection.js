function openOneProjection(){
	
	var id = window.location.search.split("=")[1];
	
	$.get("./ProjectionServlet", {id:id}, function(data){
		
		if(data.status == "success"){
			console.log(data);
			var projection = data.projection;
			
			var movie = $("#movieName");
			movie.append("<a id ='movieName' href = './movie.html?id=" + projection.movie.id + "'>"+ projection.movie.name +"</a>")
			//movie.href = "movie.html?id=" + data.projection.movie.id;
			//alert(movie.id);
			
//			var movieLink = $("#movieLink");
//			movieLink.attr("href", "neki string");

			$("#dateAndTime").val(new Date(projection.dateAndTime).toString().substring(4,21));
			$("#type").val(data.projection.type.name);
			$("#hall").val(data.projection.hall.name);
			$("#price").val(data.projection.price);

		}
		
	});
}

$(document).ready(function(){
	openOneProjection();
});