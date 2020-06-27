package dao;

import java.sql.Connection;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import model.Role;
import model.User;

public class UserDAO {
	
	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	
//-----------------------------------------------------------------------------------------------------------------------
		//ALL USERS
	
	public ArrayList<User> getAllUsers(){
		
		Connection connection = ConnectionManager.getConnection();
		
		ArrayList<User> users = new ArrayList<User>();
		
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
				Date date = dateFormat.parse(rset.getString(index++));
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
				Date date = dateFormat.parse(rset.getString(index++));
				Role role = Role.valueOf(rset.getString(index++));
				
				
				return new User(id, username, password, date, role);
			}		
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
 
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
		
		Connection connection = ConnectionManager.getConnection();

		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		try {
			String query = "SELECT id, registrationDate, role FROM users WHERE deleted = FALSE AND username = ? AND password = ? AND deleted = false";

			pstmt = connection.prepareStatement(query);
			
			int index = 1;
			pstmt.setString(index++, username);
			pstmt.setString(index++, password);

			rset = pstmt.executeQuery();

			if (rset.next()) {
				index = 1;
				int id = rset.getInt(index++);
				Date registrationDate = dateFormat.parse(rset.getString(index++));
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
				connection.close();
			} catch (Exception ex1) {
				ex1.printStackTrace();
			}
		}
		return null;		
	}

//--------------------------------------------------------------------------------------------------------------------------
	
	//FIND USER BY USERNAME
	
	public User findUserByUserName(String username) {		
	
		Connection connection = ConnectionManager.getConnection();
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		try {
			
			String query = "SELECT id, password, registrationDate, role FROM users WHERE username = ? AND deleted = false";
			
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, username);
			
			rset = pstmt.executeQuery();
			
			int index;
			if(rset.next()) {
				index = 1;
				Integer id = rset.getInt(index++);
				String password = rset.getString(index++);
				Date registrationDate = dateFormat.parse(rset.getString(index++));
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
	
	//FUNCTION FOR CREATING NEW USER
	
	public boolean addUser(User user) {
				
		Connection connection = ConnectionManager.getConnection();
		
		PreparedStatement pstmt = null;
		try {
			String query = "INSERT INTO users (username, password, role) VALUES(?, ?, ?)";
			
			pstmt = connection.prepareStatement(query);
			
			int index = 1;
			pstmt.setString(index++, user.getUsername());
			pstmt.setString(index++, user.getPassword());
			pstmt.setString(index++, user.getRole().toString());
			
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
	
	//FUNCTION FOR SEARCHING
	
	public ArrayList<User> findUser(String uName){
	
		Connection connection = ConnectionManager.getConnection();
		
		ArrayList<User> users = new ArrayList<User>();
				
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		try {
			
			String query = "SELECT id, username, password, registrationDate, role FROM users WHERE username LIKE ? and deleted = false";
			
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, uName + "%");
			
			rset = pstmt.executeQuery();
			
			int index;
			while(rset.next()) {
				index = 1;
				Integer id = rset.getInt(index++);
				String username = rset.getString(index++);
				String password = rset.getString(index++);
				Date registrationDate = dateFormat.parse(rset.getString(index++));
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

//----------------------------------------------------------------------------------------------------------------------------

	//FIND USER BY ROLE
	
	public ArrayList<User> findUserByRole(String role){
	
		Connection connection = ConnectionManager.getConnection();
		
		ArrayList<User> users = new ArrayList<User>();
				
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		try {
			
			String query = "SELECT id, username, password, registrationDate, role FROM users WHERE role = ? and deleted = false";
			
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, role);
			
			rset = pstmt.executeQuery();
			
			int index;
			while(rset.next()) {
				index = 1;
				Integer id = rset.getInt(index++);
				String username = rset.getString(index++);
				String password = rset.getString(index++);
				Date registrationDate = dateFormat.parse(rset.getString(index++));
				Role rolee = Role.valueOf(rset.getString(index++));
				
				users.add( new User(id, username, password, registrationDate, rolee));
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
	
//----------------------------------------------------------------------------------------------------------------------------
		
	//DELETE USER
	
	public boolean delete (int id) {

		Connection connection = ConnectionManager.getConnection();
			
		PreparedStatement pstmt = null;
		try {
			String query = "DELETE FROM users WHERE id = ?";
			
			int index = 1;
			pstmt = connection.prepareStatement(query);			
			pstmt.setInt(index++, id);
			
			return pstmt.executeUpdate() == 1;
			
		}catch(Exception ex) {
			ex.printStackTrace();

		}finally {
			try {pstmt.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {connection.close();} catch (Exception ex1) {ex1.printStackTrace();}		
		}
		return false;
}

//----------------------------------------------------------------------------------------------------------------------------
	//DELETE LOGIC
	
	public boolean deleteUserLogic (int id ) {			
			
		Connection connection = ConnectionManager.getConnection();

		PreparedStatement pstmt = null;
		try {
			String query = "UPDATE users SET deleted = 1 WHERE id = ?";
			
			pstmt = connection.prepareStatement(query);
			int index = 1;

			pstmt.setInt(index++, id);
			
			return pstmt.executeUpdate() == 1;
			
		}catch(Exception ex) {
			ex.printStackTrace();

		}finally {
			try {pstmt.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {connection.close();} catch (Exception ex1) {ex1.printStackTrace();}		
		}
		return false;
	}

//----------------------------------------------------------------------------------------------------------------------------

	//FUNCTION FOR UPDATE PASSWORD
	
	public boolean updatePass (int id, String pass) {

		Connection connection = ConnectionManager.getConnection();
		
		PreparedStatement pstmt = null;
		try {
			String query = "UPDATE users SET password = ? WHERE id = ? AND deleted = false";
			
			pstmt = connection.prepareStatement(query);
			
			int index = 1;
			pstmt.setString(index++, pass);
			pstmt.setInt(index++, id);
			
			return pstmt.executeUpdate() == 1;
			
		}catch(Exception ex) {
			ex.printStackTrace();

		}finally {
			try {pstmt.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {connection.close();} catch (Exception ex1) {ex1.printStackTrace();}		
		}
		return false;
}
//----------------------------------------------------------------------------------------------------------------------------
	
	//FUNCTION FOR UPDATE ROLE 
	
	public boolean updateRole (int id, String role) {

		Connection connection = ConnectionManager.getConnection();
		PreparedStatement pstmt = null;
		try {
			String query = "UPDATE users SET role = ? WHERE id = ? AND deleted = false";
			
			pstmt = connection.prepareStatement(query);
			
			int index = 1;
			pstmt.setString(index++, role);		
			pstmt.setInt(index++, id);
		
			return pstmt.executeUpdate() == 1;
			
		}catch(Exception ex) {
			ex.printStackTrace();

		}finally {
			try {pstmt.close();} catch (Exception ex1) {ex1.printStackTrace();}
			try {connection.close();} catch (Exception ex1) {ex1.printStackTrace();}		
		}
		return false;
	}
	
//-------------------------------------------------------------------------------------------------------------------

}