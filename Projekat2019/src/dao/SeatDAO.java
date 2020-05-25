package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import model.Hall;
import model.Seat;

public class SeatDAO {

//---------------------------------------------------------------------------------------------------------------------
	//ALL SEATS 
	
	public ArrayList<Seat> getAllSeats(int hallId){

		Connection connection = ConnectionManager.getConnection();
		
		ArrayList<Seat> seats = new ArrayList<Seat>();
					
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		try {
			String query = "SELECT id, number, hallId FROM seats WHERE hallId = ?";
			
			pstmt = connection.prepareStatement(query);
			pstmt.setInt(1, hallId);
			
			rset = pstmt.executeQuery();
			
			int index;							
			while(rset.next()) {
				index = 1;				
				int id = rset.getInt(index++);
				int number = rset.getInt(index++);
				int HallId = rset.getInt(index++);
				
				
				HallDAO dao = new HallDAO();
				Hall hall = dao.findHallById(HallId); 
				
						
				seats.add(new Seat(id, number, hall));				
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
		return seats;
	}

//----------------------------------------------------------------------------------------------------------
		
	//SELECTED ONE

	public Seat findSeatById(int id) {
		
		Connection connection = ConnectionManager.getConnection();
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		try {
			
			String query = "SELECT id, number, hallId FROM seats WHERE id = ?";
				
			pstmt = connection.prepareStatement(query);
			pstmt.setInt(1, id);
			
			rset = pstmt.executeQuery();
			
			int index;									
			if(rset.next()) {
				index = 1;				
				int idSeat = rset.getInt(index++);
				int number = rset.getInt(index++);
				int hallId = rset.getInt(index++);
				
				
				HallDAO dao = new HallDAO();
				Hall hall = dao.findHallById(hallId);

				
				return new Seat(idSeat, number, hall);
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
	
	//COUNTING FREE SEATS
	
	public int CountFreeSeats(int id){
		
		Connection connection = ConnectionManager.getConnection();		
		int numberOfUnableSeats = 0;					
			
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		try {
			
			String query = "SELECT COUNT(seatId) FROM tickets WHERE projectionId = ? ";
			
			pstmt = connection.prepareStatement(query);
			pstmt.setInt(1, id);
			
			rset = pstmt.executeQuery();
	
			numberOfUnableSeats = rset.getInt(1);
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
		return numberOfUnableSeats;
	}

//-----------------------------------------------------------------------------------------------------------------------------------------------------------------	

	//COUNTING NUMBER OF ALL SEATS IN ONE HALL
	
	public int CountNumberOfSeatsInHall(int id){
		
		Connection connection = ConnectionManager.getConnection();
		int numberOfSeats = 0;
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		try {
			String query = "SELECT COUNT(id) FROM seats WHERE hallId = ? ";
			
			pstmt = connection.prepareStatement(query);
			pstmt.setInt(1, id);
			
			rset = pstmt.executeQuery();
	
			numberOfSeats = rset.getInt(1);

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
		return numberOfSeats;
	}

//-----------------------------------------------------------------------------------------------------------------------------------------------------------------	
}