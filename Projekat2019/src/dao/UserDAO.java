package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;

import model.Role;
import model.User;

public class UserDAO {
	
	
		//ALL USERS
	
	public ArrayList<User> getAllUsers(){
		ArrayList<User> users = new ArrayList<User>();
		
		java.sql.Connection connection = ConnectionManager.getConnection();
		
		java.sql.Statement stmt = null;
		ResultSet rset = null;
		
		try {
			String query = "SELECT * FROM users WHERE deleted = 0";
			
			stmt = connection.createStatement();
			rset = stmt.executeQuery(query);
			
			int index;
			while(rset.next()) {
				index = 1;
				
				int id = rset.getInt(index++);
				String username = rset.getString(index++);
				String pass = rset.getString(index++);
				Timestamp date = rset.getTimestamp(index++);
				Role role = Role.valueOf(rset.getString(index++));
				
				users.add(new User(id, username, pass, date, role));
				
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				stmt.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
			try {
				connection.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		return users;
	}
	
//-----------------------------------------------------------------------------------------------------------------------------------------------------------------	

		//SELECTED ONE
	
	public User findUserById(int id) {
		
		Connection connection = ConnectionManager.getConnection();
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		try {
			
			String query = "SELECT username, password, registrationDate, role FROM users WHERE id = ?";
					
					pstmt = connection.prepareStatement(query);
					pstmt.setInt(1, id);
					
					rset = pstmt.executeQuery();
					
					int index;
					if(rset.next()) {
						index = 1;
						
						String username = rset.getString(index++);
						String password = rset.getString(index++);
						Timestamp date = rset.getTimestamp(index++);
						Role role = Role.valueOf(rset.getString(index++));
						
						return new User(id, username, password, date, role);
					}		
				}catch(Exception e) {
					e.printStackTrace();
				}finally {
					try {
						pstmt.close();
					}catch(Exception e) {
						e.printStackTrace();
					}
					try {
						connection.close();
					}catch(Exception e) {
						e.printStackTrace();
					}
				}
			return null;	
			}
	
//-------------------------------------------------------------------------------------------------------------------------------------------------
	
	//LOGIN 
	
	public User login(String username, String password) {

		Connection conn = ConnectionManager.getConnection();

		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			String query = "SELECT id, registrationDate, role FROM users WHERE deleted = FALSE AND username = ? AND password = ?";

			pstmt = conn.prepareStatement(query);
			int index = 1;
			pstmt.setString(index++, username);
			pstmt.setString(index++, password);

			rset = pstmt.executeQuery();

			if (rset.next()) {
				index = 1;

				int id = rset.getInt(index++);
				Timestamp registrationDate = rset.getTimestamp(index++);
				Role role = Role.valueOf(rset.getString(index++));

				return new User(id, username, password, registrationDate, role);

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
			} catch (Exception ex1) {
				ex1.printStackTrace();
			}
			try {
				rset.close();
			} catch (Exception ex1) {
				ex1.printStackTrace();
			}
			try {
				conn.close();
			} catch (Exception ex1) {
				ex1.printStackTrace();
			}
		}
		return null;		
	}

//--------------------------------------------------------------------------------------------------------------------------
	
	//find user by username
	public static User findUserByUserName(String username) {
		Connection connection = ConnectionManager.getConnection();
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		try {
			
			String query = "SELECT id, password, registrationDate, role FROM users WHERE username = ?";
			
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, username);
			
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				int index = 1;
				Integer id = rset.getInt(index++);
				String password = rset.getString(index++);
				Timestamp registrationDate = rset.getTimestamp(index++);
				Role role = Role.valueOf(rset.getString(index++));
				
				return new User(id, username, password, registrationDate, role);
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
			} catch (Exception ex1) {
				ex1.printStackTrace();
			}
			try {
				rset.close();
			} catch (Exception ex1) {
				ex1.printStackTrace();
			}
			try {
				connection.close();
			} catch (Exception ex1) {
				ex1.printStackTrace();
			}
		}
		return null;		
	}
	
//-----------------------------------------------------------------------------------------------------------------------------
	
	//function for creating new user
	
	public boolean addUser(User user) {
		
		Connection connection = ConnectionManager.getConnection();
		
		PreparedStatement pstmt = null;
		try {
			String query = "INSERT INTO users (username, password) VALUES(?, ?)";
			
			pstmt = connection.prepareStatement(query);
			
			int index = 1;
			pstmt.setString(index++, user.getUsername());
			pstmt.setString(index++, user.getPassword());
			
			return pstmt.executeUpdate() == 1;
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				pstmt.close();
			}catch(Exception e1) {
				e1.printStackTrace();
			}
			try {
				connection.close();
			}catch(Exception e1) {
				e1.printStackTrace();
			}
		}
		return false;
	}
	
//----------------------------------------------------------------------------------------------------------------------------
	
	//for searching
	public ArrayList<User> findUser(String uName){
		ArrayList<User> users = new ArrayList<User>();
		
		Connection connection = ConnectionManager.getConnection();
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		try {
			
			String query = "SELECT id, username, password, registrationDate, role FROM users WHERE username LIKE ? and deleted = false";
			
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, uName + "%");
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				int index = 1;
				Integer id = rset.getInt(index++);
				String username = rset.getString(index++);
				String password = rset.getString(index++);
				Timestamp registrationDate = rset.getTimestamp(index++);
				Role role = Role.valueOf(rset.getString(index++));
				
				users.add( new User(id, username, password, registrationDate, role));
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
			} catch (Exception ex1) {
				ex1.printStackTrace();
			}
			try {
				rset.close();
			} catch (Exception ex1) {
				ex1.printStackTrace();
			}
			try {
				connection.close();
			} catch (Exception ex1) {
				ex1.printStackTrace();
			}
		}
		return users;		
	}
	}