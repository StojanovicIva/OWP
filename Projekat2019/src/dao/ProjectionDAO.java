package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;

import model.Hall;
import model.Movie;
import model.ProjectType;
import model.Projection;
import model.Role;
import model.User;

public class ProjectionDAO {

	
	//ALL PROJECTIONS
	
	public ArrayList<Projection> getAllProjections(){
		ArrayList<Projection> projections = new ArrayList<Projection>();
		
		java.sql.Connection connection = ConnectionManager.getConnection();
		
		java.sql.Statement stmt = null;
		ResultSet rset = null;
		
		try {
			String query = "SELECT * FROM projections";
			
			stmt = connection.createStatement();
			rset = stmt.executeQuery(query);
			
			int index;
			while(rset.next()) {
				index = 1;
				
				int id = rset.getInt(index++);
				int movieId = rset.getInt(index++);
				int typeId = rset.getInt(index++);
				int hallId = rset.getInt(index++);
				Timestamp date = rset.getTimestamp(index++);
				int price = rset.getInt(index++);
				int adminsNameId = rset.getInt(index++);
				
				MovieDAO movieDAO = new MovieDAO();
				Movie movie = movieDAO.findMovieById(movieId);
				
				ProjectTypeDAO ptDAO = new ProjectTypeDAO();
				ProjectType type = ptDAO.findProjectTypeById(typeId);
				
				HallDAO hallDAO = new HallDAO();
				Hall hall = hallDAO.findHallById(hallId);
				
				UserDAO userDAO = new UserDAO();
				User user = userDAO.findUserById(adminsNameId);
				
				
				
				projections.add(new Projection(id, movie, type, hall, date, price, user));
				
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
		return projections;
	}

//-----------------------------------------------------------------------------------------------------------------------------------------------------------------	

	//SELECTED ONE

	public Projection findProjectionById(int id) {
		
		Connection connection = ConnectionManager.getConnection();
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		try {
			
			String query = "SELECT movie, type, hall, date, price, adminsName FROM projections WHERE id = ?";
					
					pstmt = connection.prepareStatement(query);
					pstmt.setInt(1, id);
					
					rset = pstmt.executeQuery();
					
					int index;
					if(rset.next()) {
						index = 1;
						
						int movieId = rset.getInt(index++);
						int typeId = rset.getInt(index++);
						int hallId = rset.getInt(index++);
						Timestamp date = rset.getTimestamp(index++);
						int price = rset.getInt(index++);
						int adminsNameId = rset.getInt(index++);
						
						MovieDAO movieDAO = new MovieDAO();
						Movie movie = movieDAO.findMovieById(movieId);
						
						ProjectTypeDAO projectTypeDAO = new ProjectTypeDAO();
						ProjectType type = projectTypeDAO.findProjectTypeById(typeId);
						
						HallDAO hallDAO = new HallDAO();
						Hall hall = hallDAO.findHallById(hallId);
						
						UserDAO userDAO = new UserDAO();
						User user = userDAO.findUserById(adminsNameId);
						
						return new Projection(id, movie, type, hall, date, price, user);
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

}
