$.get("./LoginServlet", function(data){
	var loggedinUser = data.loggedinUser;
	var projectionId;
	
function openOneTicket(){
	
	var id = window.location.search.split("=")[1];
	
	$.get("./TicketServlet", {id:id}, function(data){
		
		if(data.status == "success"){
			var ticket = data.ticket;
			
			var movie = $("#movieName");
			var dateAndTime = $("#dateAndTime");
			movie.append("<a id ='movieName' href = './movie.html?id=" + ticket.projection.movie.id + "'>"+ ticket.projection.movie.name +"</a>");
			dateAndTime.append("<a id = 'dateAndTime' href='./projection.html?id=" + ticket.projection.id + "'>" + new Date(ticket.projection.dateAndTime).toString().substring(4,21) + "</a>");

			$("#type").val(data.ticket.projection.type.name);
			$("#hall").val(data.ticket.projection.hall.name);
			$("#seat").val(data.ticket.seat.number);
			$("#price").val(data.ticket.projection.price);
			
			projectionId = data.ticket.projection.id;
		}else if(data.status == "fail"){
			alert("Something went wrong! Please try again!");
			window.location.reload();
		}
	});
	 
}

$(document).ready(function(){
	openOneTicket();
	
	
	if(loggedinUser.role == "ADMINISTRATOR"){
		openOneTicket();
		var id = window.location.search.split("=")[1];
		$("#delete").prepend("<button id='deleteButton' type = 'submit'> DELETE TICKET</button>");

		$("#deleteButton").on("click", function(data){
			
			var answer = window.confirm("Are You sure You want to delete user?");
			
			if(answer){
	
				
				$.get("./DeleteTicketServlet", {id:id}, function(data){
				
					if(data.status == "success"){
						alert("Deleted!");
						window.location.replace("./index.html");	
					}else if(data.status == "fail"){
						alert("Something went wrong!Please try again!");
						window.location.reload();
					}

				});
		}
		});
		
	}
});
});