package model;

import java.sql.Timestamp;
import java.util.Date;

public class Projection {

	private int id;
	private Movie movie;
	private ProjectType type;
	private Hall hall;
	private Date dateAndTime;
	private int price;
	private User adminsName;
	
	public Projection() {};
	
	public Projection(int id, Movie movie, ProjectType type, Hall hall, Date dateAndTime, int price, User adminsName) {
		this.id = id;
		this.movie = movie;
		this.type = type;
		this.hall = hall;
		this.dateAndTime = dateAndTime;
		this.price = price;
		this.adminsName = adminsName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Movie getMovie() {
		return movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}

	public ProjectType getType() {
		return type;
	}

	public void setType(ProjectType type) {
		this.type = type;
	}

	public Hall getHall() {
		return hall;
	}

	public void setHall(Hall hall) {
		this.hall = hall;
	}

	public Date getDateAndTime() {
		return dateAndTime;
	}

	public void setDateAndTime(Date dateAndTime) {
		this.dateAndTime = dateAndTime;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public User getAdminsName() {
		return adminsName;
	}

	public void setAdminsName(User adminsName) {
		this.adminsName = adminsName;
	}
	
	
}
