$.get("./LoginServlet", function(data){
	var loggedinUser = data.loggedinUser;
function openOneProjection(){
	
	var id = window.location.search.split("=")[1];
	
	$.get("./ProjectionServlet", {id:id}, function(data){
		
		if(data.status == "success"){
			console.log(data);
			var projection = data.projection;
						
			var movie = $("#movieName");
			movie.append("<a id ='movieName' href = './movie.html?id=" + projection.movie.id + "'>"+ projection.movie.name +"</a>")

			$("#dateAndTime").val(new Date(projection.dateAndTime).toString().substring(4,21));
			$("#type").val(data.projection.type.name);
			$("#hall").val(data.projection.hall.name);
			$("#price").val(data.projection.price);
			$("#numberOfFreeSeats").val(data.numberOfFreeSeats);

		}else if(data.status == "fail"){
			alert("Something went wrong! Please try again!");
			window.location.reload();
		}		
	});
	
	$.get("./ProjectionIsInFuture", {id:id}, function(data){
		console.log(data);
		
		if(data.status == "success" && data.numberOfFreeSeats > 0 && loggedinUser != null && loggedinUser.role === "USER"){
			
			$("#ul").append(
					"<li id='getTicketLi'><button class = 'blinking' type='submit' id= 'getTicketButton'>CLICK HERE AND GET TICKET</button></li>");
			
			$("#getTicketButton").on("click", function(event){							
				window.location.replace("./BuyingATicket.html?movieId=" + data.projection.movie.id + "&projectionId=" + data.projection.id);
				
			});
		}else if(data.status == "fail"){
			alert("Something went wrong! Please try again!");
			window.location.reload();
		}
		else if(loggedinUser != null && loggedinUser.role == "ADMINISTRATOR"){
			document.getElementById('tableOfTickets').style.display = "block";
			document.getElementById('h4').style.display = "block";


			$("#button").append("<button id='deleteButton' type= 'submit'> DELETE MOVIE</button>");
			
			
			$("#deleteButton").on("click", function(data){
				
				var answer = window.confirm("Are You sure You want to delete movie?");
				
				if(answer){

					$.get("./DeleteProjectionServlet" ,  {id:id}, function(data){
						if(data.status == "success"){
							window.location.replace("./index.html");
						}else if(data.status == "fail"){
							alert("Something went wrong! Please try again!");
							window.location.reload();
						}
					});
				}								
			});
			
			$.get("./TicketsForProjection", {id:id}, function(data){
				
				if(data.status == "success"){
					for(ticket of data.tickets){
						$("#tableOfTickets").append("<tr>" + 
										   "<td>" + " * -> *  " +"</td>" +
										   "<td><a href='User.html?id='" + ticket.userId + "'>" + ticket.user.username + "</a></td>" +  
										   "<td><a href='Ticket.html?id=" + ticket.id +"'>" + new Date(ticket.dateAndTime).toString().substring(0,21) + "</a></td></tr>");
					}
				}else if(data.status == "fail"){
					alert("Something went wrong! Please try again!");
					window.location.reload();
				}
			});
			
		}
		
	});
}

$(document).ready(function(){
	openOneProjection();
});
});