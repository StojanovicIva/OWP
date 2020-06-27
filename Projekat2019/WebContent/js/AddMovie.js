$.get("./LoginServlet", function(data){
	var loggedinUser = data.loggedinUser;
	
	
function disableButton(){
	
	var styleValue = $("#style").val();
	if(!isNaN(styleValue)){
		//alert("In field 'STYLE' you enter some numbers!");
		//$("#addMovieButton").disabled = true;
	}
	
	var durationValue = $("#duration").val();
	if(isNaN(durationValue)){
		//alert("In field 'DURATION' you enter some letters!");
		//$("#addMovieButton").disabled = true;
	}
	
	var yearValue = $("#year").val();
	if(isNaN(yearValue)){
		//alert("In field 'YEAR' you enter some letters!");
		//$("#addMovieButton").disabled = true;
	}
	
	var distributorValue = $("#distributor").val();
	if(!isNaN(distributorValue)){
		//alert("In field 'DISTRIBUTOR' you enter some numbers!");
		//$("#addMovieButton").disabled = true;
	}
	
	var countryValue = $("#country").val();
	if(!isNaN(countryValue)){
		//alert("In field 'COUNTRY' you enter some numbers!");
		//$("#addMovieButton").disabled = true;
	}
	
	var directorValue = $("#director").val();
	if(!isNaN(directorValue)){
		//alert("In field 'DIRECTOR' you enter some numbers!");
		//$("#addMovieButton").disabled = true;
	}
	
	var actorsValue = $("#actors").val();
	if(!isNaN(actorsValue)){
		//alert("In field 'ACTORS' you enter some numbers!");
		//$("#addMovieButton").disabled = true;
	}
}

	$(document).ready(function(){	
		
		if(loggedinUser.role == "ADMINISTRATOR"){
			
			
			$("#addMovieButton").on("click", function(){				
				var params = {
					"name": $("#name").val(),
					"style" : $("#style").val(),
					"duration" : $("#duration").val(),
					"year" : $("#year").val(),
					"distributor" : $("#distributor").val(),
					"country" : $("#country").val(),
					"director" : $("#director").val(),
					"actors" : $("#actors").val(),
					"description" : $("#description").val()
				}
				$.post("./AddMovieServlet", params, function(data){
					if(data.status == "success"){
						alert("Movie successfully added");						
						window.location.replace("./Movies.html");
					}else if(data.status == "fail"){
						alert("Something went wrong! Please, try again!");
						window.location.reload();
					}
				});
			});
		}
		
	});
});