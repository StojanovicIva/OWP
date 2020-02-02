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
import model.User;

public class ProjectionDAO {

	
	//ALL PROJECTIONS
	
	public ArrayList<Projection> getAllProjections(){
		ArrayList<Projection> projections = new ArrayList<Projection>();
		
		java.sql.Connection connection = ConnectionManager.getConnection();
		
		java.sql.Statement stmt = null;
		ResultSet rset = null;
		
		try {
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date datee = new Date();
		
			String query = "SELECT p.id, p.movie, p.type, p.hall, p.date, p.price, p.adminsName FROM projections AS p JOIN movies AS m ON p.movie = m.id WHERE p.date >= '" + dateFormat.format(datee) + "' AND m.deleted = 0 ORDER BY m.name, p.date";
						
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

	public ArrayList<Projection> findProjectionByMovie(String movieName) {
		
		
		ArrayList<Projection> projections = new ArrayList<Projection>();
		
		Connection connection = ConnectionManager.getConnection();
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		try {
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date datee = new Date();
			
			String query = "SELECT p.id, p.movie, p.type, p.hall, p.date, p.price, p.adminsName  FROM projections AS p JOIN movies AS m ON p.movie = m.id WHERE m.name LIKE ? AND p.date >='" +  dateFormat.format(datee) + "'";
			
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, movieName + "%");
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				int index = 1;
				
				int id = rset.getInt(index++);
				int movieId = rset.getInt(index++);
				int typeId = rset.getInt(index++);
				int hallId = rset.getInt(index++);
				Timestamp date = rset.getTimestamp(index++);
				int price = rset.getInt(index++);
				int adminsNameId = rset.getInt(index++);
				
				MovieDAO mDao = new MovieDAO();
				Movie movie = mDao.findMovieById(movieId);
				
				ProjectTypeDAO ptDao = new ProjectTypeDAO();
				ProjectType projectType = ptDao.findProjectTypeById(typeId);
				
				HallDAO hDao = new HallDAO();
				Hall hall = hDao.findHallById(hallId);
				
				UserDAO uDao = new UserDAO();
				User user = uDao.findUserById(adminsNameId);
				
				projections.add(new Projection(id, movie, projectType, hall, date, price, user));
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
		return projections;		
	}
		
//-----------------------------------------------------------------------------------------------------------------------------
	
	public ArrayList<Projection> findProjectionByPrice(int fromThe, int to) {
		
		ArrayList<Projection> projections = new ArrayList<Projection>();
		
		Connection connection = ConnectionManager.getConnection();
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		try {
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date datee = new Date();
			
			String query = "SELECT id, movie, type, hall, date, price, adminsName FROM projections WHERE price > ? AND price < ? AND date >='" +  dateFormat.format(datee) + "'";
			
			pstmt = connection.prepareStatement(query);
			
			int index=1;
			pstmt.setInt(index++, fromThe);
			pstmt.setInt(index++, to);
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				index=1;
				Integer id = rset.getInt(index++);
				Integer movieId = rset.getInt(index++);
				Integer typeId = rset.getInt(index++);
				Integer hallId = rset.getInt(index++);
				Timestamp date = rset.getTimestamp(index++);
				Integer price = rset.getInt(index++);
				Integer adminsNameId = rset.getInt(index++);
				
				MovieDAO mDao = new MovieDAO();
				Movie movie = mDao.findMovieById(movieId);
				
				ProjectTypeDAO ptDao = new ProjectTypeDAO();
				ProjectType projectType = ptDao.findProjectTypeById(typeId);
				
				HallDAO hDao = new HallDAO();
				Hall hall = hDao.findHallById(hallId);
				
				UserDAO uDao = new UserDAO();
				User user = uDao.findUserById(adminsNameId);
				
				projections.add(new Projection(id, movie, projectType, hall, date, price, user));
				
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
		return projections;		
	}
			
//-----------------------------------------------------------------------------------------------------------------------------
	

	public ArrayList<Projection> findProjectionByType (int type) {
	
		ArrayList<Projection> projections = new ArrayList<Projection>();
		
		Connection connection = ConnectionManager.getConnection();
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		try {
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date datee = new Date();
			
			String query = "SELECT p.id, p.movie, p.type, p.hall, p.date, p.price, p.adminsName  FROM projections AS p JOIN projecttype AS pt ON p.type = pt.id WHERE pt.id LIKE ? AND p.date >='" +  dateFormat.format(datee) + "'";
			
			pstmt = connection.prepareStatement(query);
			pstmt.setInt(1, type);
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				int index = 1;
				int id = rset.getInt(index++);
				int movieId = rset.getInt(index++);
				int typeId = rset.getInt(index++);
				int hallId = rset.getInt(index++);
				Timestamp date = rset.getTimestamp(index++);
				int price = rset.getInt(index++);
				int adminsNameId = rset.getInt(index++);
				
				MovieDAO mDao = new MovieDAO();
				Movie movie = mDao.findMovieById(movieId);
				
				ProjectTypeDAO ptDao = new ProjectTypeDAO();
				ProjectType projectType = ptDao.findProjectTypeById(typeId);
				
				HallDAO hDao = new HallDAO();
				Hall hall = hDao.findHallById(hallId);
				
				UserDAO uDao = new UserDAO();
				User user = uDao.findUserById(adminsNameId);
				
				projections.add(new Projection(id, movie, projectType, hall, date, price, user));
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
		return projections;		
	}
	
//-----------------------------------------------------------------------------------------------------------------------------

	public ArrayList<Projection> findProjectionByHall (int hall) {
	
		ArrayList<Projection> projections = new ArrayList<Projection>();
		
		Connection connection = ConnectionManager.getConnection();
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		try {
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date datee = new Date();
			
			String query = "SELECT p.id, p.movie, p.type, p.hall, p.date, p.price, p.adminsName  FROM projections AS p JOIN hall AS h ON p.hall = h.id WHERE h.id LIKE ? AND p.date >='" +  dateFormat.format(datee) + "'";
			
			pstmt = connection.prepareStatement(query);
			pstmt.setInt(1, hall);
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				int index = 1;
				int id = rset.getInt(index++);
				int movieId = rset.getInt(index++);
				int typeId = rset.getInt(index++);
				int hallId = rset.getInt(index++);
				Timestamp date = rset.getTimestamp(index++);
				int price = rset.getInt(index++);
				int adminsNameId = rset.getInt(index++);
				
				MovieDAO mDao = new MovieDAO();
				Movie movie = mDao.findMovieById(movieId);
				
				ProjectTypeDAO ptDao = new ProjectTypeDAO();
				ProjectType projectType = ptDao.findProjectTypeById(typeId);
				
				HallDAO hDao = new HallDAO();
				Hall Hall = hDao.findHallById(hallId);
				
				UserDAO uDao = new UserDAO();
				User user = uDao.findUserById(adminsNameId);
				
				projections.add(new Projection(id, movie, projectType, Hall, date, price, user));
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
		return projections;		
	}
	
//-----------------------------------------------------------------------------------------------------------------------------
	
	public ArrayList<Projection> findProjectionByDate(Date fromTheDate, Date toDate) {
		
		ArrayList<Projection> projections = new ArrayList<Projection>();
		
		Connection connection = ConnectionManager.getConnection();
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		try {
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			
			String query = "SELECT id, movie, type, hall, date, price, adminsName FROM projections WHERE date >= ? AND  date <= ?";
			
			pstmt = connection.prepareStatement(query);
			
			int index=1;
//			pstmt.setDate(index++, (java.sql.Date) fromTheDate);
//			pstmt.setDate(index++, (java.sql.Date) toDate);
			
			pstmt.setString(index++, dateFormat.format(fromTheDate));
			pstmt.setString(index++, dateFormat.format(toDate));
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				index=1;
				Integer id = rset.getInt(index++);
				Integer movieId = rset.getInt(index++);
				Integer typeId = rset.getInt(index++);
				Integer hallId = rset.getInt(index++);
				Timestamp date = rset.getTimestamp(index++);
				Integer price = rset.getInt(index++);
				Integer adminsNameId = rset.getInt(index++);
				
				MovieDAO mDao = new MovieDAO();
				Movie movie = mDao.findMovieById(movieId);
				
				ProjectTypeDAO ptDao = new ProjectTypeDAO();
				ProjectType projectType = ptDao.findProjectTypeById(typeId);
				
				HallDAO hDao = new HallDAO();
				Hall hall = hDao.findHallById(hallId);
				
				UserDAO uDao = new UserDAO();
				User user = uDao.findUserById(adminsNameId);
				
				projections.add(new Projection(id, movie, projectType, hall, date, price, user));
				
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
		return projections;		
	}
			
//-----------------------------------------------------------------------------------------------------------------------------
			
	public boolean addProjection(Projection projection) {
		
		Connection connection = ConnectionManager.getConnection();
		
		PreparedStatement pstmt = null;
		try {
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

			String query = "INSERT INTO projections (movie, type, hall, date, price , adminsName) VALUES(?, ?, ?, ?, ?, ?)";
			
			pstmt = connection.prepareStatement(query);
			
			int index = 1;
			pstmt.setInt(index++, projection.getMovie().getId());
			pstmt.setInt(index++, projection.getType().getId());
			pstmt.setInt(index++, projection.getHall().getId());
			pstmt.setString(index++, dateFormat.format(projection.getDateAndTime()));
			pstmt.setInt(index++, projection.getPrice());
			pstmt.setInt(index++, projection.getAdminsName().getId());
			
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
//----------------------------------------------------------------------------------------------------------------
	
	public ArrayList<Projection> findProjectionByDateAndName(String name, Date fromTheDate, Date toDate) {
		
		ArrayList<Projection> projections = new ArrayList<Projection>();
		
		Connection connection = ConnectionManager.getConnection();
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		try {
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			
			String query = "SELECT p.id, p.movie, p.type, p.hall, p.date, p.price, p.adminsName  FROM projections AS p JOIN movies AS m ON p.movie = m.id WHERE m.name LIKE ? AND p.date >= ? AND p.date <= ?";

			pstmt = connection.prepareStatement(query);
			
			int index=1;
//			pstmt.setDate(index++, (java.sql.Date) fromTheDate);
//			pstmt.setDate(index++, (java.sql.Date) toDate);
			
			pstmt.setString(index++, name + "%");
			pstmt.setString(index++, dateFormat.format(fromTheDate));
			pstmt.setString(index++, dateFormat.format(toDate));
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				index=1;
				Integer id = rset.getInt(index++);
				Integer movieId = rset.getInt(index++);
				Integer typeId = rset.getInt(index++);
				Integer hallId = rset.getInt(index++);
				Timestamp date = rset.getTimestamp(index++);
				Integer price = rset.getInt(index++);
				Integer adminsNameId = rset.getInt(index++);
				
				MovieDAO mDao = new MovieDAO();
				Movie movie = mDao.findMovieById(movieId);
				
				ProjectTypeDAO ptDao = new ProjectTypeDAO();
				ProjectType projectType = ptDao.findProjectTypeById(typeId);
				
				HallDAO hDao = new HallDAO();
				Hall hall = hDao.findHallById(hallId);
				
				UserDAO uDao = new UserDAO();
				User user = uDao.findUserById(adminsNameId);
				
				projections.add(new Projection(id, movie, projectType, hall, date, price, user));
				
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
		return projections;		
	}
			
//-----------------------------------------------------------------------------------------------------------------------------
	public ArrayList<Projection> findProjectionByPriceAndName(String name, int fromThe, int to) {
		
		ArrayList<Projection> projections = new ArrayList<Projection>();
		
		Connection connection = ConnectionManager.getConnection();
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		try {
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			Date datee = new Date();
			
			String query = "SELECT p.id, p.movie, p.type, p.hall, p.date, p.price, p.adminsName FROM projections AS p JOIN movies AS m ON p.movie = m.id WHERE m.name LIKE ? AND p.price > ? AND p.price < ?  AND p.date >= current_timestamp()";

			pstmt = connection.prepareStatement(query);
			
			int index=1;
//			pstmt.setDate(index++, (java.sql.Date) fromTheDate);
//			pstmt.setDate(index++, (java.sql.Date) toDate);
			
			pstmt.setString(index++, name + "%");
			pstmt.setInt(index++, fromThe);
			pstmt.setInt(index++, to);
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				index=1;
				Integer id = rset.getInt(index++);
				Integer movieId = rset.getInt(index++);
				Integer typeId = rset.getInt(index++);
				Integer hallId = rset.getInt(index++);
				Timestamp date = rset.getTimestamp(index++);
				Integer price = rset.getInt(index++);
				Integer adminsNameId = rset.getInt(index++);
				
				MovieDAO mDao = new MovieDAO();
				Movie movie = mDao.findMovieById(movieId);
				
				ProjectTypeDAO ptDao = new ProjectTypeDAO();
				ProjectType projectType = ptDao.findProjectTypeById(typeId);
				
				HallDAO hDao = new HallDAO();
				Hall hall = hDao.findHallById(hallId);
				
				UserDAO uDao = new UserDAO();
				User user = uDao.findUserById(adminsNameId);
				
				projections.add(new Projection(id, movie, projectType, hall, date, price, user));
				
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
		return projections;		
	}
			
//-----------------------------------------------------------------------------------------------------------------------------
	public ArrayList<Projection> findProjectionByPriceAndDate(Date fromTheDate, Date toDate, int fromThe, int to) {
		
		ArrayList<Projection> projections = new ArrayList<Projection>();
		
		Connection connection = ConnectionManager.getConnection();
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		try {
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			
			String query = "SELECT id, movie, type, hall, date, price, adminsName FROM projections WHERE date >= ? AND  date <= ? AND price >= ? AND price <= ?";

			pstmt = connection.prepareStatement(query);
			
			int index=1;

			pstmt.setString(index++, dateFormat.format(fromTheDate));
			pstmt.setString(index++, dateFormat.format(toDate));		
			pstmt.setInt(index++, fromThe);
			pstmt.setInt(index++, to);
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				index=1;
				Integer id = rset.getInt(index++);
				Integer movieId = rset.getInt(index++);
				Integer typeId = rset.getInt(index++);
				Integer hallId = rset.getInt(index++);
				Timestamp date = rset.getTimestamp(index++);
				Integer price = rset.getInt(index++);
				Integer adminsNameId = rset.getInt(index++);
				
				MovieDAO mDao = new MovieDAO();
				Movie movie = mDao.findMovieById(movieId);
				
				ProjectTypeDAO ptDao = new ProjectTypeDAO();
				ProjectType projectType = ptDao.findProjectTypeById(typeId);
				
				HallDAO hDao = new HallDAO();
				Hall hall = hDao.findHallById(hallId);
				
				UserDAO uDao = new UserDAO();
				User user = uDao.findUserById(adminsNameId);
				
				projections.add(new Projection(id, movie, projectType, hall, date, price, user));
				
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
		return projections;		
	}
			
//-----------------------------------------------------------------------------------------------------------------------------
	public ArrayList<Projection> findProjectionByMovieId(int movieId) {
		
		
		ArrayList<Projection> projections = new ArrayList<Projection>();
		
		Connection connection = ConnectionManager.getConnection();
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		try {
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date datee = new Date();
			
			String query = "SELECT p.id, p.movie, p.type, p.hall, p.date, p.price, p.adminsName  FROM projections AS p JOIN movies AS m ON p.movie = m.id WHERE m.id = ? AND p.date >= current_timestamp()";
			
			pstmt = connection.prepareStatement(query);
			pstmt.setInt(1, movieId);
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				int index = 1;
				
				int id = rset.getInt(index++);
				int movie = rset.getInt(index++);
				int typeId = rset.getInt(index++);
				int hallId = rset.getInt(index++);
				Timestamp date = rset.getTimestamp(index++);
				int price = rset.getInt(index++);
				int adminsNameId = rset.getInt(index++);
				
				MovieDAO mDao = new MovieDAO();
				Movie moviee = mDao.findMovieById(movie);
				
				ProjectTypeDAO ptDao = new ProjectTypeDAO();
				ProjectType projectType = ptDao.findProjectTypeById(typeId);
				
				HallDAO hDao = new HallDAO();
				Hall hall = hDao.findHallById(hallId);
				
				UserDAO uDao = new UserDAO();
				User user = uDao.findUserById(adminsNameId);
				
				projections.add(new Projection(id, moviee, projectType, hall, date, price, user));
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
		return projections;		
	}
		
//-----------------------------------------------------------------------------------------------------------------------------
	

}
