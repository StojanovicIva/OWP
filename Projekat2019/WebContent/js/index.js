$.get("./LoginServlet", function(data){
	var loggedinUser = data.loggedinUser;
	var allProjections;
//------------------------------------------------------------------------------------------------------------------------------
	//FUNCTION FOR GETTING ALL PROJECTIONS From DB!
	
	function getAllProjections(){
		
		allProjections;
		var table = $("#table");
		
		$.get("./AllProjections", function(data){
			console.log(data);
			
			if(data.status == "success"){
				allProjections = data.projections;
				console.log(allProjections);

				for(Projection of allProjections){
					table.append("<tr>" +
								 "<td><a href='projection.html?id=" + Projection.id + "'>" + Projection.movie.name + "</a></td>" +
								 "<td>" + new Date(Projection.dateAndTime).toString().substring(4,25) + "</td>" + 
								 "<td>" + Projection.type.name + "</td>" + 
								 "<td>" + Projection.hall.name + "</td>" + 
								 "<td>" + Projection.price + "</td>");
				}
			}else if(data.status == "fail"){
				alert("Something went wrong! Please try again!");
				window.location.reload();
			}
		});	
	}
	
//---------------------------------------------------------------------------------------------------------------------------------------------
	function sortProjections(){
		
		var criteria = $("#criteria").val();
		var ascDesc  = $("#ascOrDesc").val();
					
		 $("#table").find("tr:gt(0)").remove();
	        switch(criteria){
	            case "movie":
	                if(ascDesc == "ASC"){
	                    allProjections.sort(function(a, b){
	                        return(a.movie.name > b.movie.name) ? 1 : -1;
	                    });
	                }else{
	                  allProjections.sort(function(a,b){
	                      return (a.movie.name < b.movie.name) ? 1 : -1;
	                  });
	              }
	              break;
	            case "time":
	            	if(ascDesc =="ASC"){
	            		allProjections.sort(function(a,b){
	            			return(a.time > b.time) ? 1 : -1;
	            		});
	            	}else{
	            		allProjections.sort(function(a,b){
	            			return(a.time < b.time) ? 1 : -1;
	            		});
	            	}
	            	break;
	            case "type":
	            	if(ascDesc == "ASC"){
	            		allProjections.sort(function(a,b){
	            			return(a.type> b.type) ? 1 : -1;
	            		});
	            	}else{
	            		allProjections.sort(function(a,b){
	            			return(a.type < b.type) ? 1 : -1;
	            		});
	            	}
	            	break;
	            case "hall":
	            	if(ascDesc == "ASC"){
	            		allProjections.sort(function(a,b){
	            			return(a.hall > b.hall) ? 1 : -1;
	            		});
	            	}else{
	            		allProjections.sort(function(a,b){
	            			return (a.hall < b.hall) ? 1 : -1;
	            		});
	            	}
	            	break;
	            case "price":
	            	if(ascDesc == "ASC"){
	            		allProjections.sort(function(a,b){
	            			return(a.price > b.price) ? 1 : (a.price == b.price) ? 0 : -1;
	            		});
	            	}else{
	            		allProjections.sort(function(a,b){
	            			return(a.price < b.price) ? 1 : -1;
	            		});
	            	}
	            	break;
	            default:
	                return;
	        }
			for(Projection of allProjections){
				$("#table").append("<tr>" +
							 "<td><a href='projection.html?id=" + Projection.id + "'>" + Projection.movie.name + "</a></td>" +
							 "<td>" + new Date(Projection.dateAndTime).toString().substring(4,25) + "</td>" + 
							 "<td>" + Projection.type.name + "</td>" + 
							 "<td>" + Projection.hall.name + "</td>" + 
							 "<td>" + Projection.price + "</td>");
			}
	   
	 
	}
		
//---------------------------------------------------------------------------------------------------------------------------
	
	function searchProjections(){
			
			var option = $("#criterium").val();
			var input  = $("#searchInput").val();
			
			if(option == "price"){
				
				var FromThe = $("#FromThe").val();
				var To = $("#To").val();
				
				var params = {
						"criterium" : option,
						"fromThe" : FromThe,
						"to" : To
				}
			}	
			else if(option == "dateAndTime"){
				
				var fromThe = $("#fromTheDate").val();
				var to = $("#toDate").val();

				var params ={
						criterium : "dateAndTime",
						fromTheDate : fromThe,
						to : to
				}
				$.post("./SearchProjectionServlet", params, function(data){
					
					if(data.status == "success"){
						projections = data.projections;
						
						console.log("search: " + projections);
						
						$("#table").find("tr:gt(0)").remove();
						
						for(Projection of projections){
							$("#table").append("<tr>" +
										 "<td><a href='projection.html?id=" + Projection.id + "'>" + Projection.movie.name + "</a></td>" +
										 "<td>" + new Date(Projection.dateAndTime).toString().substring(4,25) + "</td>" + 
										 "<td>" + Projection.type.name + "</td>" + 
										 "<td>" + Projection.hall.name + "</td>" + 
										 "<td>" + Projection.price + "</td>");
						}
					}else if(data.status == "fail"){
						alert("Something went wrong! Please try again!");
						window.location.reload();
					}
					
				});
			} else if(option == "type"){
				var selectType = $("#selectType");
				
				$.get("./ProjectionTypeServlet", function(data){
				
					if(data.status == "success"){
						var allTypes = data.projectTypes;
						
						for(type of allTypes){
							selectType.append("<option value = '" + type.id + "'> + " + type.name +"</option>");
						}
						$("#selectType").change(function(){
							
							var value = $(this).val();
							params = {
									"criterium" : "type",
									"value" : value
							}
							$.post("./SearchProjectionServlet", params, function(data){

								if(data.status == "success"){
									projections = data.projections;
									
									console.log("search: " + projections);
									
									$("#table").find("tr:gt(0)").remove();
									
									for(Projection of projections){
										$("#table").append("<tr>" +
													 "<td><a href='projection.html?id=" + Projection.id + "'>" + Projection.movie.name + "</a></td>" +
													 "<td>" + new Date(Projection.dateAndTime).toString().substring(4,25) + "</td>" + 
													 "<td>" + Projection.type.name + "</td>" + 
													 "<td>" + Projection.hall.name + "</td>" + 
													 "<td>" + Projection.price + "</td>");
									}
								}else if(data.status == "fail"){
									alert("Something went wrong! Please try again!");
									window.location.reload();
								}
							});
						});
					}else if(data.status == "fail"){
						alert("Something went wrong! Please try again!");
						window.location.reload();
					}
				});
			
			}else if(option == "hall"){
				var selectHall = $("#selectHall");
				
				$.get("./HallServlet", function(data){
				
					if(data.status == "success"){
						var allHalls = data.halls;
						
						for(hall of allHalls){
							selectHall.append("<option value = '" + hall.id + "'> + " + hall.name +"</option>");
						}									
						$("#selectHall").change(function(){
							
							var value = $(this).val();
							params = {
									"criterium" : "hall",
									"value" : value
							}
							$.post("./SearchProjectionServlet", params, function(data){

								if(data.status == "success"){
									projections = data.projections;
									
									console.log("search: " + projections);
									
									$("#table").find("tr:gt(0)").remove();
									
									for(Projection of projections){
										$("#table").append("<tr>" +
													 "<td><a href='projection.html?id=" + Projection.id + "'>" + Projection.movie.name + "</a></td>" +
													 "<td>" + new Date(Projection.dateAndTime).toString().substring(4,25) + "</td>" + 
													 "<td>" + Projection.type.name + "</td>" + 
													 "<td>" + Projection.hall.name + "</td>" + 
													 "<td>" + Projection.price + "</td>");
									}
								}else if(data.status == "fail"){
									alert("Something went wrong! Please try again!");
									window.location.reload();
								}
							});
						});
					}else if(data.status == "fail"){
						alert("Something went wrong! Please try again!");
						window.location.reload();
					}
				});
			}else if(option == "movieName"){
				var params = {
						"criterium" : option,
						"input" : input
				}
			}else if(option == "nameAndDate"){
				
				var fromThe = $("#fromTheDate").val();
				var to = $("#toDate").val();
				
				var params = {
						"criterium" : "nameAndDate",
						"input" : input,
						"fromTheDate" : fromThe,
						"toDate" : to
				}
				
			}else if(option =="nameAndPrice"){
				
				var fromThe = $("#FromThe").val();
				var to = $("#To").val();
				
				console.log("from the: " + fromThe);
				console.log("to: " + to);
				
				var params = {
						"criterium" : "nameAndPrice",
						"input" : input,
						"fromThe" : fromThe,
						"to" : to
 				}
			}else if(option = "dateAndPrice"){
				
				var fromTheDate = $("#fromTheDate").val();
				var toDate = $("#toDate").val();
				var fromThe = $("#FromThe").val();
				var to = $("#To").val();
				
				console.log("from the date " + fromTheDate);
				console.log("to date " + toDate);
				console.log("from the " + fromThe);
				console.log("to " + to);
				
				params = {
						"criterium" : "dateAndPrice",
						"fromTheDate" : fromTheDate,
						"toDate" : toDate,
						"fromThe" : fromThe,
						"to" : to
				}
			}
			else{
				var params = {
						"criterium" : option,
						"input" : input
				}
		
			}
			$.get("./SearchProjectionServlet", params, function(data){
				
				if(data.status == "success"){
					projections = data.projections;
					
					console.log("search: " + projections);
					
					$("#table").find("tr:gt(0)").remove();
					
					for(Projection of projections){
						$("#table").append("<tr>" +
									 "<td><a href='projection.html?id=" + Projection.id + "'>" + Projection.movie.name + "</a></td>" +
									 "<td>" + new Date(Projection.dateAndTime).toString().substring(4,25) + "</td>" + 
									 "<td>" + Projection.type.name + "</td>" + 
									 "<td>" + Projection.hall.name + "</td>" + 
									 "<td>" + Projection.price + "</td>");
					}
				}else if(data.status == "fail"){
					alert("Something went wrong! Please try again!");
					window.location.reload();
				}
			});			
		}
		
//---------------------------------------------------------------------------------------------------------------------------
		

$(document).ready(function(){
	
	getAllProjections();

	//open modal for logging 
	document.getElementById("login").addEventListener("click", function(){
		document.querySelector(".bg-modal").style.display = "flex";
	});
	
	//close modal for logging
	document.querySelector(".close").addEventListener("click", function(){
		document.querySelector(".bg-modal").style.display = "none";
	});
	
	//open modal for sorting
	document.getElementById("sortProjection").addEventListener("click", function(){
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
	
	//open modal for searching
	document.getElementById("searchProjection").addEventListener("click", function(){
		document.querySelector(".b-modal").style.display="flex";
	});
	
	//close modal for searching
	document.querySelector(".clos").addEventListener("click", function(){
		document.querySelector(".b-modal").style.display = "none";
	});
	
	//close modal when click on search button
	document.getElementById("search").addEventListener("click", function(){
		document.querySelector(".b-modal").style.display = "none";
		
	});
	

	$("#sort").on("click", sortProjections);
	$("#search").on("click", searchProjections);
	
	$("#criterium").change(function(){
		
		var value = $(this).val();
		
		if(value == "price"){
			
			document.getElementById('searchInput').style.display = "none";
			document.getElementById('typeDiv').style.display = "none";
			document.getElementById('hallDiv').style.display = "none";
			document.getElementById('timeDiv').style.display = "none";								
			document.getElementById('rangeDiv').style.display = "block";								
		
		}else if(value == "type"){
			
			document.getElementById('searchInput').style.display = "none";
			document.getElementById('rangeDiv').style.display = "none";		
			document.getElementById('hallDiv').style.display = "none";
			document.getElementById('timeDiv').style.display = "none";								
			document.getElementById('typeDiv').style.display = "block";			
			
			var selectType = $("#selectType");
			
			$.get("./ProjectionTypeServlet", function(data){
			
				if(data.status == "success"){
					var allTypes = data.projectTypes;
					
					for(type of allTypes){
						selectType.append("<option value = '" + type.id + "'> + " + type.name +"</option>");
					}
					$("#selectType").change(function(){
						
						var value = $(this).val();
						params = {
								"criterium" : "type",
								"value" : value
						}
						$.post("./SearchProjectionServlet", params, function(data){

							if(data.status == "success"){
								projections = data.projections;
								
								console.log("search: " + projections);
								
								$("#table").find("tr:gt(0)").remove();
								
								for(Projection of projections){
									$("#table").append("<tr>" +
												 "<td><a href='projection.html?id=" + Projection.id + "'>" + Projection.movie.name + "</a></td>" +
												 "<td>" + new Date(Projection.dateAndTime).toString().substring(4,25) + "</td>" + 
												 "<td>" + Projection.type.name + "</td>" + 
												 "<td>" + Projection.hall.name + "</td>" + 
												 "<td>" + Projection.price + "</td>");
								}
							}else if(data.status == "fail"){
								alert("Something went wrong! Please try again!");
								window.location.reload();
							}
						});
					});
				}else if(data.status == "fail"){
					alert("Something went wrong! Please try again!");
					window.location.reload();
				}
			});
		
		}else if(value == "hall"){
			
			document.getElementById('searchInput').style.display = "none";
			document.getElementById('rangeDiv').style.display = "none";								
			document.getElementById('typeDiv').style.display = "none";
			document.getElementById('timeDiv').style.display = "none";								
			document.getElementById('hallDiv').style.display = "block";				
			
			var selectHall = $("#selectHall");
			
			$.get("./HallServlet", function(data){
			
				if(data.status == "success"){
					var allHalls = data.halls;
					
					for(hall of allHalls){
						selectHall.append("<option value = '" + hall.id + "'> + " + hall.name +"</option>");
					}									
					$("#selectHall").change(function(){
						
						var value = $(this).val();
						params = {
								"criterium" : "hall",
								"value" : value
						}
						$.post("./SearchProjectionServlet", params, function(data){

							if(data.status == "success"){
								projections = data.projections;
								
								console.log("search: " + projections);
								
								$("#table").find("tr:gt(0)").remove();
								
								for(Projection of projections){
									$("#table").append("<tr>" +
												 "<td><a href='projection.html?id=" + Projection.id + "'>" + Projection.movie.name + "</a></td>" +
												 "<td>" + new Date(Projection.dateAndTime).toString().substring(4,25) + "</td>" + 
												 "<td>" + Projection.type.name + "</td>" + 
												 "<td>" + Projection.hall.name + "</td>" + 
												 "<td>" + Projection.price + "</td>");
								}
							}else if(data.status == "fail"){
								alert("Something went wrong! Please try again!");
								window.location.reload();
							}
						});
					});
				}else if(data.status == "fail"){
					alert("Something went wrong! Please try again!");
					window.location.reload();
				}
			});

				
				
		}else if(value == "dateAndTime"){
			
			document.getElementById('searchInput').style.display = "none";
			document.getElementById('rangeDiv').style.display = "none";								
			document.getElementById('typeDiv').style.display = "none";
			document.getElementById('hallDiv').style.display = "none";	
			document.getElementById('timeDiv').style.display = "block";								

		}else if(value == "nameAndDate"){
			
			document.getElementById('searchInput').style.display = "block";
			document.getElementById('rangeDiv').style.display = "none";								
			document.getElementById('typeDiv').style.display = "none";
			document.getElementById('hallDiv').style.display = "none";	
			document.getElementById('timeDiv').style.display = "block";	
		
		}else if(value == "nameAndPrice"){
		
			document.getElementById('searchInput').style.display = "block";
			document.getElementById('rangeDiv').style.display = "block";								
			document.getElementById('typeDiv').style.display = "none";
			document.getElementById('hallDiv').style.display = "none";	
			document.getElementById('timeDiv').style.display = "none";	
		
		}else if(value == "dateAndPrice"){
		
			document.getElementById('searchInput').style.display = "none";
			document.getElementById('rangeDiv').style.display = "block";								
			document.getElementById('typeDiv').style.display = "none";
			document.getElementById('hallDiv').style.display = "none";	
			document.getElementById('timeDiv').style.display = "block";	
		}
		
		else{
		    
			document.getElementById('rangeDiv').style.display = "none";
			document.getElementById('hallDiv').style.display = "none";								
		    document.getElementById('searchInput').style.display = "block";
			document.getElementById('typeDiv').style.display = "none";	
			document.getElementById('timeDiv').style.display = "none";								

		    }
	});

//--------------------------------------------------------------------------------------------------------------------------------
//FUNCTION FOR LOGGING!
	$("#logIn").submit(function(e){
		
		var parameters = {
			username : $('#username').val(),
			password : $('#password').val()
		};
		
		$.post("./LoginServlet", parameters, function(data){
			console.log(data);
			
			if(data.status == "success"){
				window.location.reload();
				console.log(data);
			}
			else if(data.status == "fail"){
				alert("Wrong username or password!");
				window.location.replace("./index.html");
			}
		});
	});
	
//----------------------------------------------------------------------------------------------------------------------------------


	//if loggedinUser is basic user
		
	if(loggedinUser != null ){
		
		//changing login to logout
		$("#login").text("Log out");
		
		$("#login").click(function(event){
			event.preventDefault();
			$.get("LogoutServlet", function(data){
				if(data.status == "unauthenticated"){
					window.location.replace("./index.html");
				}
			});
			return false;
		});
		
		//changing about to myProfile
		$("#about").text("My profile");
			
			$("#about").click(function(event){

				window.location.replace("./User.html?id=" + loggedinUser.id);

			});
			
		//if loggedinUser is admin

		if(loggedinUser.role == "ADMINISTRATOR"){
			
			//add li for all users
			var ul = $("#ul");
			ul.append("<li><a href = './users.html'>All users</a></li>");
			ul.append("<li><a href = './Reports.html'>Reports</a></li>");
			
			$("#Drop").append("<a id='AddProjection' href= './AddProjection.html'>Add Projection</a>");	
		}
	}
//----------------------------------------------------------------------------------------------------------------------------------
	});
});