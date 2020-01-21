package model;

import java.util.ArrayList;

public class Movie {
	
	private int id;
	private String name;
	private String director;
	//private ArrayList<String> actors;
	private String actors;
	private String style;
	private int duration;
	private String distributor;
	private String country;
	private String year;
	private String description;
	
	public Movie() {};
	
	public Movie(int id, String name, String director, String actors, String style, int duration, String distributor,
				 String country, String year, String description) {
		this.id = id;
		this.name = name;
		this.director = director;
		this.actors = actors;
		this.style = style;
		this.duration = duration;
		this.distributor = distributor;
		this.country = country;
		this.year = year;
		this.description = description;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public String getActors() {
		return actors;
	}

	public void setActors(String actors) {
		this.actors = actors;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public String getDistributor() {
		return distributor;
	}

	public void setDistributor(String distributor) {
		this.distributor = distributor;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
