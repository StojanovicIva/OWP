package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import model.Projection;
import model.Role;
import model.Seat;
import model.Ticket;
import model.User;

public class TicketDAO {
	
	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	
//---------------------------------------------------------------------------------------------------------------------------
	//ALL TICKETS
	
public ArrayList<Ticket> getAllTickets(){
	
	Connection connection = ConnectionManager.getConnection();
	
	ArrayList<Ticket> tickets = new ArrayList<Ticket>();
	
	java.sql.Statement stmt = null;
	ResultSet rset = null;
	
	try {
		String query = "SELECT * FROM tickets WHERE deleted = 0";
		
		stmt = connection.createStatement();
		rset = stmt.executeQuery(query);
		
		int index;
		while(rset.next()) {
			index = 1;				
			int id = rset.getInt(index++);
			int projectionId = rset.getInt(index++);
			int seatId = rset.getInt(index++);
			Date date = dateFormat.parse(rset.getString(index++));
			int userId = rset.getInt(index++);
			
			ProjectionDAO proDao = new ProjectionDAO();
			Projection projection = proDao.findProjectionById(projectionId);
			
			SeatDAO seatDao = new SeatDAO();
			Seat seat = seatDao.findSeatById(seatId);
			
			UserDAO userDao = new UserDAO();
			User user = userDao.findUserById(userId);
			
			tickets.add(new Ticket(id, projection, seat, date, user));				
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
	return tickets;
}

//-----------------------------------------------------------------------------------------------------------------------------------------------------------------	

	//GET TICKETS FOR PROJECTION
	
	public ArrayList<Ticket> getTicketsForProjection(int id){
		
		Connection connection = ConnectionManager.getConnection();
		
		ArrayList<Ticket> tickets = new ArrayList<Ticket>();
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		try {
			String query = "SELECT id, projectionId, seatId, dateOfPurchase, userId FROM tickets WHERE projectionId = ? ";
			
			pstmt = connection.prepareStatement(query);
			pstmt.setInt(1, id);
			
			rset = pstmt.executeQuery();
			
			int index;
			while(rset.next()) {
				index = 1;
				
				int ticketId = rset.getInt(index++);
				int projectionId = rset.getInt(index++);
				int seatId = rset.getInt(index++);
				Date dateOfPurchase = dateFormat.parse(rset.getString(index++));
				int userId = rset.getInt(index++);
				
				
				ProjectionDAO proDAO = new ProjectionDAO();
				SeatDAO seatDao = new SeatDAO();
				UserDAO userDao = new UserDAO();
				
				Projection projection = proDAO.findProjectionById(projectionId);				
				Seat seat = seatDao.findSeatById(seatId);							
				User user = userDao.findUserById(userId);
				
				
				tickets.add(new Ticket(ticketId, projection, seat, dateOfPurchase, user));				
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
		return tickets;
	}
	
//-----------------------------------------------------------------------------------------------------------------------------------------------------------------	

	//FUNCTION FOR CREATING NEW TICKET
	
	public boolean addTicket(Ticket ticket) {
				
		Connection connection = ConnectionManager.getConnection();
		
		PreparedStatement pstmt = null;
		
		try {
			String query = "INSERT INTO tickets (projectionId, seatId, userId) VALUES(?, ?, ?)";
			
			pstmt = connection.prepareStatement(query);
			
			int index = 1;
			pstmt.setInt(index++, ticket.getProjection().getId());
			pstmt.setInt(index++, ticket.getSeat().getId());
			pstmt.setInt(index++, ticket.getUser().getId());
			
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

	//GET ALL TICKETS FOR ONE USER
	
	public ArrayList<Ticket> getAllForUser(int idUser){
		
		Connection connection = ConnectionManager.getConnection();
		
		ArrayList<Ticket> tickets = new ArrayList<Ticket>();
				
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		try {
			
			String query = "SELECT t.id, t.projectionId, t.seatId, t.dateOfPurchase, t.userId FROM tickets AS t WHERE t.userId = ? ORDER BY t.dateOfPurchase DESC";
									
			pstmt = connection.prepareStatement(query);
			pstmt.setInt(1, idUser);			
			rset = pstmt.executeQuery();
			
			int index;			
			while(rset.next()) {
				index = 1;				
				int id = rset.getInt(index++);
				int projectionId = rset.getInt(index++);
				int seatId = rset.getInt(index++);
				Date dateOfPurchase = dateFormat.parse(rset.getString(index++));
				int userId = rset.getInt(index++);
			

				ProjectionDAO proDAO = new ProjectionDAO();
				SeatDAO seatDao = new SeatDAO();
				UserDAO userDao = new UserDAO();
								
				Projection projection = proDAO.findProjectionById(projectionId);				
				Seat seat = seatDao.findSeatById(seatId);				
				User user = userDao.findUserById(userId);
												
				tickets.add(new Ticket(id, projection, seat, dateOfPurchase, user));				
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
		return tickets;
	}

//-----------------------------------------------------------------------------------------------------------------------------------------------------------------	
	
	//FIND ONE TICKET BY ID
	
	public Ticket findTicketById(int id) {
				
		Connection connection = ConnectionManager.getConnection();
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		try {
			
			String query = "SELECT projectionId, seatId, dateOfPurchase, userId FROM tickets WHERE id = ?";
					
			pstmt = connection.prepareStatement(query);
			pstmt.setInt(1, id);
			
			rset = pstmt.executeQuery();
			
			int index;			
			if(rset.next()) {
				index = 1;				
				int projectionId = rset.getInt(index++);
				int seatId = rset.getInt(index++);
				Date date = dateFormat.parse(rset.getString(index++));
				int userId = rset.getInt(index++);			
			

				ProjectionDAO proDAO = new ProjectionDAO();
				SeatDAO seatDao = new SeatDAO();
				UserDAO userDao = new UserDAO();
				
				Projection projection = proDAO.findProjectionById(projectionId);						
				Seat seat = seatDao.findSeatById(seatId);						
				User user = userDao.findUserById(userId);

				
				return new Ticket(id, projection, seat, date, user);
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


	//DELETE TICKET
	
	public boolean delete (int id) {

		Connection connection = ConnectionManager.getConnection();
			
		PreparedStatement pstmt = null;
		try {
			String query = "DELETE FROM tickets WHERE id = ?";
			
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
}