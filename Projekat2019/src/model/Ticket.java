package model;

import java.sql.Timestamp;

public class Ticket {

	public int id;
	public Projection projection;
	public Seat seat;
	public Timestamp dateAndTime;
	public User user;

	public Ticket() {
	};

	public Ticket(int id, Projection projection, Seat seat, Timestamp dateAndTime, User user) {
		this.id = id;
		this.projection = projection;
		this.seat = seat;
		this.dateAndTime = dateAndTime;
		this.user = user;
	}
	
	public Ticket (Projection projection, Seat seat, User user) {
		this.projection = projection;
		this.seat = seat;
		this.user = user;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Projection getProjection() {
		return projection;
	}

	public void setProjection(Projection projection) {
		this.projection = projection;
	}

	public Seat getSeat() {
		return seat;
	}

	public void setSeat(Seat seat) {
		this.seat = seat;
	}

	public Timestamp getDateAndTime() {
		return dateAndTime;
	}

	public void setDateAndTime(Timestamp dateAndTime) {
		this.dateAndTime = dateAndTime;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
