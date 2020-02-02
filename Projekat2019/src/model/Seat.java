package model;

public class Seat {

	
	private int id;
	private int number;
	private Hall hall;
	
	public Seat() {};
	
	public Seat(int id, int number, Hall hall) {
		this.id = id;
		this.number = number;
		this.hall = hall;
	}
	
	public Seat(int number, Hall hall) {
		this.number = number;
		this.hall = hall;
	}

	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public Hall getHall() {
		return hall;
	}

	public void setHall(Hall hall) {
		this.hall = hall;
	}
	
	
}
