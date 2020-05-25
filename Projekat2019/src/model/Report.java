package model;

public class Report {
	
	private int movie;
	private String movieName;
	private int numberOfProjections;
	private int numberOfSoldTickets;
	private int totalPrice;
	
	
	public Report(){};
	
	public Report(int movie, String movieName, int numberOfProjections, int numberOfSoldTickets, int totalPrice){
		this.movie = movie;
		this.movieName = movieName;
		this.numberOfProjections = numberOfProjections;
		this.numberOfSoldTickets = numberOfSoldTickets;
		this.totalPrice = totalPrice;
	}

	
	public String getMovieName() {
		return movieName;
	}

	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}

	public int getMovie() {
		return movie;
	}

	public void setMovie(int movie) {
		this.movie = movie;
	}

	public int getNumberOfProjections() {
		return numberOfProjections;
	}

	public void setNumberOfProjections(int numberOfProjections) {
		this.numberOfProjections = numberOfProjections;
	}

	public int getNumberOfSoldTickets() {
		return numberOfSoldTickets;
	}

	public void setNumberOfSoldTickets(int numberOfSoldTickets) {
		this.numberOfSoldTickets = numberOfSoldTickets;
	}

	public int getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}
	
	
}
