$(document).ready(function(){
	
//	$("#addMovie").submit(function(e){
//		alert("CLICK");
//		var params = {
//				"name" : $("#name").val(),
//				"director" : $("#director").val(),
//				"actors" : $("#actors").val(),
//				"style" : $("#style").val(),
//				"duration" : $("#duration").val(),
//				"distributor" : $("#distributor").val(),
//				"country" : $("#country").val(),
//				"year" : $("#year").val(),
//				"description" : $("#description").val()
//		}
//		
//		$.post("./AddMovieServlet", params, function(data){
//			console.log(data);
//		})
		if(data.status == "success"){
			
			window.location.replace("Movies.html");
		}
		
	//});
	
});