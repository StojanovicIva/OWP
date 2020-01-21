package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;

import model.Hall;
import model.ProjectType;
import model.Role;
import model.User;

public class HallDAO {

	//ALL Halls
	
	public ArrayList<Hall> getAllHalls(){
		ArrayList<Hall> halls = new ArrayList<Hall>();
		
		java.sql.Connection connection = ConnectionManager.getConnection();
		
		java.sql.Statement stmt = null;
		ResultSet rset = null;
		
		try {
			String query = "SELECT * FROM hall";
			
			stmt = connection.createStatement();
			rset = stmt.executeQuery(query);
			
			int index;
			while(rset.next()) {
				index = 1;
				
				int id = rset.getInt(index++);
				String name = rset.getString(index++);
				int ptID = rset.getInt(index++);
				
				
				ProjectTypeDAO dao = new ProjectTypeDAO();
				ProjectType projectType = dao.findProjectTypeById(ptID); 
				
						
				halls.add(new Hall(id, name, projectType));
				
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
		return halls;
	}

//-----------------------------------------------------------------------------------------------------------------------------------------------------------------	

	//SELECTED ONE

	public Hall findHallById(int id) {
		
		Connection connection = ConnectionManager.getConnection();
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		try {
			
			String query = "SELECT name, projectType FROM hall WHERE id = ?";
					
					pstmt = connection.prepareStatement(query);
					pstmt.setInt(1, id);
					
					rset = pstmt.executeQuery();
					
					int index;
					if(rset.next()) {
						index = 1;
						
						String name = rset.getString(index++);
						int ptID = rset.getInt(index++);
				
						ProjectTypeDAO dao = new ProjectTypeDAO();
						ProjectType projectType = dao.findProjectTypeById(ptID); 
						
						
						return new Hall(id, name, projectType);
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
