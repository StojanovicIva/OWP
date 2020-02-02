function openOneTicket(){
	
	var id = window.location.search.split("=")[1];
	
	$.get("./TicketServlet", {id:id}, function(data){
		
		if(data.status == "success"){
			console.log(data);
			var ticket = data.ticket;
			
			var movie = $("#movieName");
			var dateAndTime = $("#dateAndTime");
			movie.append("<a id ='movieName' href = './movie.html?id=" + ticket.projection.movie.id + "'>"+ ticket.projection.movie.name +"</a>");
			dateAndTime.append("<a id = 'dateAndTime' href='./projection.html?id=" + ticket.projection.id + "'>" + new Date(ticket.projection.dateAndTime).toString().substring(4,21) + "</a>");

			$("#type").val(data.ticket.projection.type.name);
			$("#hall").val(data.ticket.projection.hall.name);
			$("#seat").val(data.ticket.seat.number);
			$("#price").val(data.ticket.projection.price);

		}
		
	});
}

$(document).ready(function(){
	openOneTicket();
});