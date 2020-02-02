$.get("./LoginServlet", function(data){
	var loggedinUser = data.loggedinUser;
	var allProjections;
	var seats = [];
	var id = window.location.search.split("=")[1];

	
//------------------------------------------------------------------------------------------------------------------------------
	//FUNCTION FOR GETTING ALL PROJECTIONS From DB!
	
	
	function getAllProjections(){
		
		allProjections;
		var table = $("#table");
		
		$.post("./AllProjections", {id:id}, function(data){
			console.log(data);
			
			if(data.status == "success"){
				allProjections = data.projections;
				
				for(Projection of allProjections){
					table.append("<tr>" +
								 "<td><a href='projection.html?id=" + Projection.id + "'>" + new Date(Projection.dateAndTime).toString().substring(4,25) + "</a></td>" + 
								 "<td>" + Projection.type.name + "</td>" + 
								 "<td>" + Projection.hall.name + "</td>" + 
								 "<td>" + Projection.price + "</td>" + 
								 "<td><button type='submit' id='" + Projection.id + "' class='wantThisProjection'> WANT FOR THIS PROJECTION</button></td>");
				}
				var projectionId
				$(".wantThisProjection").on("click", function(event){							
//					document.getElementById('firstStep').style.display = "none";
					document.getElementById('secondStep').style.display = "inline-block";

					projectionId = this.id;
					var price;
					$.get("./ProjectionServlet", {id: this.id}, function(data){
						if(data.status == "success"){
 
							$("#table").hide();
							$("#firstStep").append("<table id='secondTable'>" +
													"<tr>" + 
													"<th>Movie Name</th>" + 
													"<th>Date and time</th>" + 
													"<th> Type</th>" + 
													"<th>Hall</th>" + 
													"<th>Price</th>"+
													"</tr>" +
													"</table>");
							
							$("#secondTable").append("<tr>" +
									 "<td><a href='movie.html?id='" + data.projection.movie.id + "'>" + data.projection.movie.name + "</a></td>" +
									 "<td>" + new Date(data.projection.dateAndTime).toString().substring(4,25) + "</td>" + 
									 "<td>" + data.projection.type.name + "</td>" + 
									 "<td>" + data.projection.hall.name + "</td>" + 
									 "<td>" + data.projection.price + "</td>");
							price = parseInt(data.projection.price);
						} 
						
						projectionId = data.projection.id;
						var seatNumber;
						
						$.get("./CountSeatsServlet", {id: data.projection.hall.id}, function(data){
							if(data.status == "success"){
								
								for(Seat of data.seats){
									seatNumber = Seat.number;
									$("#secondStep").append("<button type='submit' class='seatButton' id='" + Seat.id + "' value = '" + seatNumber +"'>" + seatNumber + "</button>" + "	");
									
								}																		
								
								
								$.get("./SeatsFromTickets", {id: projectionId}, function(data){
									console.log("id: " + projectionId);
									if(data.status == "success"){
										
										for(Ticket of data.tickets){
											console.log(Ticket.seat);
											seat = Ticket.seat.id;
											console.log(seat);
											
											notFreeSeat(seat);
											seats.push(seat);
										}						
									}
								});
								
								var seatId;
								var choosen = [];
								var choosenId = [];
								var seatNum;
								$(".seatButton").on("click", function(event){							
//									document.getElementById('thirdStep').style.display = "inline-block";

									seatNum = this.value;
									choosen.push(seatNum);
									
									seatId = this.id;
									seats.push(parseInt(seatId));	
									choosenId.push(parseInt(seatId));
									
									disableSeat(choosenId);
									notFreeSeat(seatId);
									console.log(seats);
									document.getElementById('next').style.display = "inline-block";
								});
								$("#next").on("click", function(event){
									document.getElementById('next').style.display = "none";
									document.getElementById('secondStep').style.display = "none";
									document.getElementById('thirdStep').style.display = "inline-block";
									
									$("#choosenSeats").val(choosen);
									var numberOfTickets = choosen.length;
									alert(numberOfTickets);
									
									var fullPrice = price * parseInt(numberOfTickets);
									$("#fullPrice").val(fullPrice);
									
									$(".thirdStep").append("<button type ='submit' id='getTicket'>BUY</button>");
								
									$("#getTicket").on("click", function(event){
										for (t of choosenId){
											
											console.log("Projection id: " + projectionId + " seat id: " + t + " user: "  + loggedinUser.id);
											
											params = {
													"projectionId" : projectionId,
													"seatId" : t,
													"user" : loggedinUser.id
											}
											
											
											$.post("./BuyTicketServlet", params, function(data){
												
												if(data.status == "success"){
													window.location.replace("./User.html?id=" + loggedinUser.id);
													
												}
											});
										}
									});
								
								});
								

							}
						});
						
					});
				});
			}
		});	

}
	
//---------------------------------------------------------------------------------------------------------------------------------------------

	
	
	function disableSeat(choosenId){
		
		var end = Math.max.apply(Math, choosenId);
		var start = Math.min.apply(Math, choosenId);
			
		if(end%4 != 0){
			end = parseInt(end) + 1;					
		}
		if(start%4 != 1){
			start = parseInt(start) - 1;	
		}
		
		$(".seatButton").each(function(){
			
			var id = parseInt(this.id);
			
			if(id < start || id > end ){
				$(this).prop("disabled", true);				
			}else if(!choosenId.includes(id) && !seats.includes(id)){
				$(this).prop("disabled", false);
			}
		});
	}
	
	function notFreeSeat(seatId){
		$(".seatButton").each(function(){
			if(parseInt(this.id) == parseInt(seatId)){
				$(this).prop("disabled", true);

			}
		});
		
	}

	
	
	$(document).ready(function(){
		
		getAllProjections();
		
	});
	
});
	