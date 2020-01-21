package model;

import java.sql.Timestamp;

public class Ticket {

	public int id;
	public ProjectType type;
	public Seat seat;
	public Timestamp dateAndTime;
	public User user;

	public Ticket() {
	};

	public Ticket(int id, ProjectType type, Seat seat, Timestamp dateAndTime, User user) {
		this.id = id;
		this.type = type;
		this.seat = seat;
		this.dateAndTime = dateAndTime;
		this.user = user;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ProjectType getType() {
		return type;
	}

	public void setType(ProjectType type) {
		this.type = type;
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
