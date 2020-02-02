package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import model.Hall;
import model.Movie;
import model.Seat;

public class SeatDAO {

	//ALL SEATS 
	
		public ArrayList<Seat> getAllSeats(int hallId){
			ArrayList<Seat> seats = new ArrayList<Seat>();
			
			java.sql.Connection connection = ConnectionManager.getConnection();
			
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
							
							HallDAO hallDao = new HallDAO();
							Hall hall = hallDao.findHallById(hallId);
							
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
			
}
