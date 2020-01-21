package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import model.ProjectType;



public class ProjectTypeDAO {

	
		//ALL PROJECT TYPES
		
	public ArrayList<ProjectType> getAllProjectTypes(){
		ArrayList<ProjectType> projectTypes = new ArrayList<ProjectType>();
		
		java.sql.Connection connection = ConnectionManager.getConnection();
		
		java.sql.Statement stmt = null;
		ResultSet rset = null;
		
		try {
			String query = "SELECT * FROM projecttype";
			
			stmt = connection.createStatement();
			rset = stmt.executeQuery(query);
			
			int index;
			while(rset.next()) {
				index = 1;
				
				int id = rset.getInt(index++);
				String name = rset.getString(index++);
				
				projectTypes.add(new ProjectType(id, name));
				
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
		return projectTypes;
	}

//-----------------------------------------------------------------------------------------------------------------------------------------------------------------	

	//SELECTED ONE

	public ProjectType findProjectTypeById(int id) {
		
		Connection connection = ConnectionManager.getConnection();
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		try {
			
			String query = "SELECT name FROM projecttype WHERE id = ?";
					
					pstmt = connection.prepareStatement(query);
					pstmt.setInt(1, id);
					
					rset = pstmt.executeQuery();
					
					int index;
					if(rset.next()) {
						index = 1;
						
						String name = rset.getString(index++);
						
						return new ProjectType(id, name);
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
