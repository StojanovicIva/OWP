package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import model.Movie;
import model.Report;

public class ReportsDAO {
	

//------------------------------------------------------------------------------------------------------------------
	

	//DAO FUNCTION FOR ADMINISTRATOR AND HIS/HERS REPORTS
	
	public ArrayList<Report> getReportsForSelectedDateAndTime(Date fromThe, Date to) {

		Connection connection = ConnectionManager.getConnection();
		
		ArrayList<Report> reports = new ArrayList<Report>();
					
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		try {
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

			String query = "SELECT m.name, m.id, COUNT(DISTINCT p.id) AS numberOfProjections, (SELECT COUNT(t.id) FROM tickets t WHERE t.projectionId = p.id) AS soldTickets, SUM(COALESCE(p.price, 0)) * ((SELECT COUNT(t.id) FROM tickets t WHERE t.projectionId = p.id) > 0) AS totalPrice FROM movies AS m LEFT OUTER JOIN (SELECT * FROM projections AS pro WHERE pro.deleted = 0 AND pro.date > ? AND pro.date < ? ) AS p ON m.id = p.movie LEFT OUTER JOIN (SELECT * FROM tickets as ti WHERE ti.projectionId = (SELECT id FROM projections p)) AS t ON t.projectionId = p.id WHERE m.id = p.movie GROUP BY name";
			
			pstmt = connection.prepareStatement(query);
			
			int index=1;
			pstmt.setString(index++, dateFormat.format(fromThe));
			pstmt.setString(index++, dateFormat.format(to));
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				index = 1;
				String movieName = rset.getString(index++);
				int movie= rset.getInt(index++);
				int numberOfProjections = rset.getInt(index++);
				int soldTickets = rset.getInt(index++);
				int totalPrice = rset.getInt(index++);
				
				reports.add(new Report(movie,movieName, numberOfProjections, soldTickets, totalPrice));
				
				System.out.println("reports: " + reports);
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
		return reports;		
	}
//---------------------------------------------------------------------------------------------------------------------------
}