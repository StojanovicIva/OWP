$.get("./LoginServlet", function(data){
	var loggedinUser = data.loggedinUser;
	
	var allReports
	
	function getReports(){
		if(loggedinUser.role === "ADMINISTRATOR"){
	
			var FromDateAndTime = $("#FromDateAndTime").val();
			var ToDateAndTime = $("#ToDateAndTime").val();
			
			var params = {
				"fromDateAndTime" : FromDateAndTime,
				"toDateAndTime" : ToDateAndTime
			};
			
			$.post("./ReportsServlet", params, function(data){
				console.log(data.reports);
				
				if(data.status == "success"){
					allReports = data.reports;
					document.getElementById('table').style.display = "block";
					document.getElementById('reportsDiv').style.display = "none";				
					
					for(Report of allReports){
						
						$("#table").append("<tr>" +
									 "<td><a href='movie.html?id="+ Report.movie +"'>" + Report.movieName + "</a></td>" +
									 "<td>" + Report.numberOfProjections +  "</td>" + 
									 "<td>" + Report.numberOfSoldTickets + "</td>" + 
									 "<td>" + Report.totalPrice + "</td>");				
						
						}
						console.log("Projections " + data.numberOfProjections + " tickets: " + data.numberOfSoldTickets + " price " + data.totalPrice);
					
					 
						$("#table").append("<tr>" +
											"<td>IN TOTAL: </td>" +
											"<td>" + data.numberOfProjections + "</td>" +
											"<td>" + data.numberOfSoldTickets + "</td>" + 
											"<td>" + data.totalPrice + "</td>");
				
				}else if(data.status == "fail"){
					alert("Something went wrong! Please try again!");
					window.location.reload();
				}
			});						
		}										
	}
	
//---------------------------------------------------------------------------------------------------------------------------------------------------------
	function sortReports(){
		
		var criteria = $("#criteria").val();
		var ascDesc  = $("#ascOrDesc").val();
					
		 $("#table").find("tr:gt(0)").remove();
	        switch(criteria){
	            case "projections":
	            	console.log("Projections!");
	                if(ascDesc == "ASC"){
	                    allReports.sort(function(a, b){
	                        return(a.numberOfProjections > b.numberOfProjections) ? 1 : -1;
	                    });
	                }else{
	                  allReports.sort(function(a,b){
	                      return (a.numberOfProjections < b.numberOfProjections) ? 1 : -1;
	                  });
	              }
	              break;
	            case "tickets":
	            	if(ascDesc =="ASC"){
	            		allReports.sort(function(a,b){
	            			return(a.numberOfSoldTickets > b.numberOfSoldTickets) ? 1 : -1;
	            		});
	            	}else{
	            		allReports.sort(function(a,b){
	            			return(a.numberOfSoldTickets < b.numberOfSoldTickets) ? 1 : -1;
	            		});
	            	}
	            	break;
	            case "price":
	            	if(ascDesc == "ASC"){
	            		allReports.sort(function(a,b){
	            			return(a.totalPrice > b.totalPrice) ? 1 : -1;
	            		});
	            	}else{
	            		allReports.sort(function(a,b){
	            			return(a.totalPrice < b.totalPrice) ? 1 : -1;
	            		});
	            	}
	            	break;
	            default:
	                return;
	        }
			for(Report of allReports){
				$("#table").append("<tr>" +
						 "<td><a href='movie.html?id="+ Report.movie +"'>" + Report.movieName + "</a></td>" +
						 "<td>" + Report.numberOfProjections +  "</td>" + 
						 "<td>" + Report.numberOfSoldTickets + "</td>" + 
						 "<td>" + Report.totalPrice + "</td>");	
			}	 
	}
//---------------------------------------------------------------------------------------------------------------------------------------------------------	

	$(document).ready(function(){
		$("#getReportsButton").on("click", getReports);
		
		
		$("#enterReports").on("click", function(){
			
			document.getElementById('table').style.display = "none";
			$("#table").find("tr:gt(0)").remove();
			document.getElementById('reportsDiv').style.display = "block";
		});
		
		$("#sort").on("click", sortReports);
		
		//open modal for sorting
		document.getElementById("sortReports").addEventListener("click", function(){
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
	});
	
});