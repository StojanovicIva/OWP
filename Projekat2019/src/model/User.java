package model;

import java.util.Date;

public class User {

	private int id;
	private String username;
	private String password;
	private Date registrationDate;
	private Role role;
	private boolean deleted;

	public User() {
	};

	// constructor for new User
	@SuppressWarnings("static-access")
	public User(String username, String password, Role role) {
		this.id = -1;
		this.username = username;
		this.password = password;
		this.registrationDate = null;
		this.role = role.USER;
	};

	// constructor for reading
	public User(int id, String username, Date registrationDate, Role role) {
		this.id = id;
		this.username = username;
		this.registrationDate = registrationDate;
		this.role = role;
	}
	
	public User(int id, String username, String password, Date registrationDate, Role role) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.registrationDate = registrationDate;
		this.role = role;
	}

	
	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

}
