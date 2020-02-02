package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import model.Hall;
import model.Movie;
import model.ProjectType;
import model.Projection;
import model.Role;
import model.Seat;
import model.Ticket;
import model.User;

public class TicketDAO {

	
	public ArrayList<Ticket> getTicketsForProjection(int id){
		ArrayList<Ticket> tickets = new ArrayList<Ticket>();
		
		java.sql.Connection connection = ConnectionManager.getConnection();
		
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
				Timestamp dateOfPurchase = rset.getTimestamp(index++);
				int userId = rset.getInt(index++);
				
				
				ProjectionDAO proDAO = new ProjectionDAO();
				Projection projection = proDAO.findProjectionById(projectionId);
				
				SeatDAO seatDao = new SeatDAO();
				Seat seat = seatDao.findSeatById(seatId);
				
				UserDAO userDao = new UserDAO();
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

	public ArrayList<Ticket> getAllForUser(int idUser){
		ArrayList<Ticket> tickets = new ArrayList<Ticket>();
		
		java.sql.Connection connection = ConnectionManager.getConnection();
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		try {
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date datee = new Date();
		
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
				Timestamp dateOfPurchase = rset.getTimestamp(index++);
				int userId = rset.getInt(index++);
				
				ProjectionDAO projectionDAO = new ProjectionDAO();
				Projection projection = projectionDAO.findProjectionById(projectionId);
				
				SeatDAO seatDAO = new SeatDAO();
				Seat seat = seatDAO.findSeatById(seatId);
				
				UserDAO userDAO = new UserDAO();
				User user = userDAO.findUserById(userId);
								
				
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
						Timestamp date = rset.getTimestamp(index++);
						int userId = rset.getInt(index++);			
						
						ProjectionDAO projectionDAO = new ProjectionDAO();
						Projection projection = projectionDAO.findProjectionById(projectionId);
						
						SeatDAO seatDAO = new SeatDAO();
						Seat seat = seatDAO.findSeatById(seatId);
						
						UserDAO userDAO = new UserDAO();
						User user = userDAO.findUserById(userId);
						
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
	
}
