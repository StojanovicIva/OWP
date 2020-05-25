package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import model.Hall;
import model.Movie;
import model.ProjectType;
import model.Projection;
import model.User;

public class ProjectionDAO {

	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	Date datee = new Date();

//-------------------------------------------------------------------------------------------------------------------------------------------------

	//ALL PROJECTIONS
	
	public ArrayList<Projection> getAllProjections(){

		Connection connection = ConnectionManager.getConnection();
		
		ArrayList<Projection> projections = new ArrayList<Projection>();			
		
		java.sql.Statement stmt = null;
		ResultSet rset = null;
		
		try {
		
			String query = "SELECT p.id, p.movie, p.type, p.hall, p.date, p.price, p.adminsName FROM projections AS p JOIN movies AS m ON p.movie = m.id WHERE p.date >= '" +  dateFormat.format(datee) + "' AND m.deleted = 0  AND p.deleted = 0 ORDER BY m.name, p.date";
					
			stmt = connection.createStatement();
			rset = stmt.executeQuery(query);
					
			int index;			
			while(rset.next()) {
				index = 1;
				
				int id = rset.getInt(index++);
				int movieId = rset.getInt(index++);
				int typeId = rset.getInt(index++);
				int hallId = rset.getInt(index++);
				Date date = dateFormat.parse(rset.getString(index++));
				int price = rset.getInt(index++);
				int adminsNameId = rset.getInt(index++);
				
				
				MovieDAO movieDAO = new MovieDAO();
				ProjectTypeDAO ptDAO = new ProjectTypeDAO();
				HallDAO hallDAO = new HallDAO();
				UserDAO userDAO = new UserDAO();
				
				Movie movie = movieDAO.findMovieById(movieId);			
				ProjectType type = ptDAO.findProjectTypeById(typeId);				
				Hall hall = hallDAO.findHallById(hallId);				
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
				Date date = dateFormat.parse(rset.getString(index++));
				int price = rset.getInt(index++);
				int adminsNameId = rset.getInt(index++);
				
				
				MovieDAO movieDAO = new MovieDAO();
				ProjectTypeDAO ptDAO = new ProjectTypeDAO();
				HallDAO hallDAO = new HallDAO();
				UserDAO userDAO = new UserDAO();
				
				Movie movie = movieDAO.findMovieById(movieId);						
				ProjectType type = ptDAO.findProjectTypeById(typeId);						
				Hall hall = hallDAO.findHallById(hallId);						
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

	//FIND PROJECTION BY MOVIE
	
	public ArrayList<Projection> findProjectionByMovie(String movieName) {
		
		Connection connection = ConnectionManager.getConnection();
		
		ArrayList<Projection> projections = new ArrayList<Projection>();
				
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		try {
			
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
				Date date = dateFormat.parse(rset.getString(index++));
				int price = rset.getInt(index++);
				int adminsNameId = rset.getInt(index++);

				
				MovieDAO movieDAO = new MovieDAO();
				ProjectTypeDAO ptDAO = new ProjectTypeDAO();
				HallDAO hallDAO = new HallDAO();
				UserDAO userDAO = new UserDAO();

				Movie movie = movieDAO.findMovieById(movieId);				
				ProjectType projectType = ptDAO.findProjectTypeById(typeId);				
				Hall hall = hallDAO.findHallById(hallId);				
				User user = userDAO.findUserById(adminsNameId);
				
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
	
	//FIND PROJECTION BY PRICE
	
	public ArrayList<Projection> findProjectionByPrice(int fromThe, int to) {
		
		Connection connection = ConnectionManager.getConnection();
		
		ArrayList<Projection> projections = new ArrayList<Projection>();
				
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		try {
			
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
				Date date = dateFormat.parse(rset.getString(index++));
				Integer price = rset.getInt(index++);
				Integer adminsNameId = rset.getInt(index++);
				
				
				MovieDAO movieDAO = new MovieDAO();
				ProjectTypeDAO ptDAO = new ProjectTypeDAO();
				HallDAO hallDAO = new HallDAO();
				UserDAO userDAO = new UserDAO();

				Movie movie = movieDAO.findMovieById(movieId);				
				ProjectType projectType = ptDAO.findProjectTypeById(typeId);
				Hall hall = hallDAO.findHallById(hallId);
				User user = userDAO.findUserById(adminsNameId);
				
				
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
	
	//FIND PROJECTION BY TYPE

	public ArrayList<Projection> findProjectionByType (int type) {
	
		Connection connection = ConnectionManager.getConnection();
		
		ArrayList<Projection> projections = new ArrayList<Projection>();
				
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		try {
			
			String query = "SELECT p.id, p.movie, p.type, p.hall, p.date, p.price, p.adminsName  FROM projections AS p JOIN projecttype AS pt ON p.type = pt.id WHERE pt.id LIKE ? AND p.date >='" +  dateFormat.format(datee) + "'";
			
			pstmt = connection.prepareStatement(query);
			pstmt.setInt(1, type);
			
			rset = pstmt.executeQuery();
			
			int index;
			while(rset.next()) {
				index = 1;
				int id = rset.getInt(index++);
				int movieId = rset.getInt(index++);
				int typeId = rset.getInt(index++);
				int hallId = rset.getInt(index++);
				Date date = dateFormat.parse(rset.getString(index++));
				int price = rset.getInt(index++);
				int adminsNameId = rset.getInt(index++);
				

				MovieDAO movieDAO = new MovieDAO();
				ProjectTypeDAO ptDAO = new ProjectTypeDAO();
				HallDAO hallDAO = new HallDAO();
				UserDAO userDAO = new UserDAO();

				Movie movie = movieDAO.findMovieById(movieId);								
				ProjectType projectType = ptDAO.findProjectTypeById(typeId);						
				Hall hall = hallDAO.findHallById(hallId);							
				User user = userDAO.findUserById(adminsNameId);
				
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

	//FIND PROJECTION BY HALL
	
	public ArrayList<Projection> findProjectionByHall (int hall) {
	
		Connection connection = ConnectionManager.getConnection();
		
		ArrayList<Projection> projections = new ArrayList<Projection>();
				
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		try {
			
			String query = "SELECT p.id, p.movie, p.type, p.hall, p.date, p.price, p.adminsName  FROM projections AS p JOIN hall AS h ON p.hall = h.id WHERE h.id LIKE ? AND p.date >='" +  dateFormat.format(datee) + "'";
			
			pstmt = connection.prepareStatement(query);
			pstmt.setInt(1, hall);
			
			rset = pstmt.executeQuery();
			
			int index;
			while(rset.next()) {
				index = 1;
				int id = rset.getInt(index++);
				int movieId = rset.getInt(index++);
				int typeId = rset.getInt(index++);
				int hallId = rset.getInt(index++);
				Date date = dateFormat.parse(rset.getString(index++));
				int price = rset.getInt(index++);
				int adminsNameId = rset.getInt(index++);
				
				
				MovieDAO movieDAO = new MovieDAO();
				ProjectTypeDAO ptDAO = new ProjectTypeDAO();
				HallDAO hallDAO = new HallDAO();
				UserDAO userDAO = new UserDAO();

				Movie movie = movieDAO.findMovieById(movieId);				
				ProjectType projectType = ptDAO.findProjectTypeById(typeId);				
				Hall Hall = hallDAO.findHallById(hallId);				
				User user = userDAO.findUserById(adminsNameId);
				
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
	
	//FIND PROJECTION BY DATE
	
	public ArrayList<Projection> findProjectionByDate(Date fromTheDate, Date toDate) {
		
		Connection connection = ConnectionManager.getConnection();
		
		ArrayList<Projection> projections = new ArrayList<Projection>();
				
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		try {			
			
			String query = "SELECT id, movie, type, hall, date, price, adminsName FROM projections WHERE date >= ? AND  date <= ?";
			
			pstmt = connection.prepareStatement(query);
			
			int index=1;

			pstmt.setString(index++, dateFormat.format(fromTheDate));
			pstmt.setString(index++, dateFormat.format(toDate));
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				index=1;
				Integer id = rset.getInt(index++);
				Integer movieId = rset.getInt(index++);
				Integer typeId = rset.getInt(index++);
				Integer hallId = rset.getInt(index++);
				Date date = dateFormat.parse(rset.getString(index++));
				Integer price = rset.getInt(index++);
				Integer adminsNameId = rset.getInt(index++);
				
								
				MovieDAO movieDAO = new MovieDAO();
				ProjectTypeDAO ptDAO = new ProjectTypeDAO();
				HallDAO hallDAO = new HallDAO();
				UserDAO userDAO = new UserDAO();
				
				Movie movie = movieDAO.findMovieById(movieId);				
				ProjectType projectType = ptDAO.findProjectTypeById(typeId);				
				Hall hall = hallDAO.findHallById(hallId);				
				User user = userDAO.findUserById(adminsNameId);
				
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
//----------------------------------------------------------------------------------------------------------------

	//FIND PROJECTION BY DATE AND NAME
	
	public ArrayList<Projection> findProjectionByDateAndName(String name, Date fromTheDate, Date toDate) {
		
		Connection connection = ConnectionManager.getConnection();
		
		ArrayList<Projection> projections = new ArrayList<Projection>();
				
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		try {
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			
			String query = "SELECT p.id, p.movie, p.type, p.hall, p.date, p.price, p.adminsName  FROM projections AS p JOIN movies AS m ON p.movie = m.id WHERE m.name LIKE ? AND p.date >= ? AND p.date <= ?";

			pstmt = connection.prepareStatement(query);
			
			int index=1;
			
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
				Date date = dateFormat.parse(rset.getString(index++));
				Integer price = rset.getInt(index++);
				Integer adminsNameId = rset.getInt(index++);
				
				
				MovieDAO movieDAO = new MovieDAO();
				ProjectTypeDAO ptDAO = new ProjectTypeDAO();
				HallDAO hallDAO = new HallDAO();
				UserDAO userDAO = new UserDAO();
				
				Movie movie = movieDAO.findMovieById(movieId);				
				ProjectType projectType = ptDAO.findProjectTypeById(typeId);				
				Hall hall = hallDAO.findHallById(hallId);				
				User user = userDAO.findUserById(adminsNameId);
				
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

//FIND PROJECTION BY PRICE AND NAME

public ArrayList<Projection> findProjectionByPriceAndName(String name, int fromThe, int to) {
	
	Connection connection = ConnectionManager.getConnection();
	
	ArrayList<Projection> projections = new ArrayList<Projection>();
			
	PreparedStatement pstmt = null;
	ResultSet rset = null;
	
	try {
		
		String query = "SELECT p.id, p.movie, p.type, p.hall, p.date, p.price, p.adminsName FROM projections AS p JOIN movies AS m ON p.movie = m.id WHERE m.name LIKE ? AND p.price > ? AND p.price < ?  AND p.date >= current_timestamp()";

		pstmt = connection.prepareStatement(query);
		
		int index=1;

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
			Date date = dateFormat.parse(rset.getString(index++));
			Integer price = rset.getInt(index++);
			Integer adminsNameId = rset.getInt(index++);
		
							
			MovieDAO movieDAO = new MovieDAO();
			ProjectTypeDAO ptDAO = new ProjectTypeDAO();
			HallDAO hallDAO = new HallDAO();
			UserDAO userDAO = new UserDAO();

			Movie movie = movieDAO.findMovieById(movieId);				
			ProjectType projectType = ptDAO.findProjectTypeById(typeId);				
			Hall hall = hallDAO.findHallById(hallId);				
			User user = userDAO.findUserById(adminsNameId);
			
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

	//FIND PROJECTION BY PRICE AND DATE
	
	public ArrayList<Projection> findProjectionByPriceAndDate(Date fromTheDate, Date toDate, int fromThe, int to) {
		
		Connection connection = ConnectionManager.getConnection();
		
		ArrayList<Projection> projections = new ArrayList<Projection>();		
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		try {
			
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
				Date date = dateFormat.parse(rset.getString(index++));
				Integer price = rset.getInt(index++);
				Integer adminsNameId = rset.getInt(index++);
			
				
				MovieDAO movieDAO = new MovieDAO();
				ProjectTypeDAO ptDAO = new ProjectTypeDAO();
				HallDAO hallDAO = new HallDAO();
				UserDAO userDAO = new UserDAO();
				
				Movie movie = movieDAO.findMovieById(movieId);
				ProjectType projectType = ptDAO.findProjectTypeById(typeId);				
				Hall hall = hallDAO.findHallById(hallId);				
				User user = userDAO.findUserById(adminsNameId);
			
				
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

	//FIND PROJECTION BY MOVIE ID
	
	public ArrayList<Projection> findProjectionByMovieId(int movieId) {
		
		Connection connection = ConnectionManager.getConnection();
		
		ArrayList<Projection> projections = new ArrayList<Projection>();
				
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		try {
			
			String query = "SELECT p.id, p.movie, p.type, p.hall, p.date, p.price, p.adminsName  FROM projections AS p JOIN movies AS m ON p.movie = m.id WHERE m.id = ? AND p.date >= date('now')";
			
			pstmt = connection.prepareStatement(query);
			pstmt.setInt(1, movieId);
			
			rset = pstmt.executeQuery();
			
			int index; 
			while(rset.next()) {
				index = 1;				
				int id = rset.getInt(index++);
				int movie = rset.getInt(index++);
				int typeId = rset.getInt(index++);
				int hallId = rset.getInt(index++);
				Date date = dateFormat.parse(rset.getString(index++));
				int price = rset.getInt(index++);
				int adminsNameId = rset.getInt(index++);

				
				MovieDAO movieDAO = new MovieDAO();
				ProjectTypeDAO ptDAO = new ProjectTypeDAO();
				HallDAO hallDAO = new HallDAO();
				UserDAO userDAO = new UserDAO();
				
				Movie moviee = movieDAO.findMovieById(movie);								
				ProjectType projectType = ptDAO.findProjectTypeById(typeId);				
				Hall hall = hallDAO.findHallById(hallId);				
				User user = userDAO.findUserById(adminsNameId);
				
				
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

	//FUNCTION FOR CREATING NEW PROJECTION
	
	public boolean addProjection(Projection projection) {
				
		Connection connection = ConnectionManager.getConnection();
		
		PreparedStatement pstmt = null;
		try {

			String query = "INSERT INTO projections (movie, type, hall, date, price , adminsName, deleted) VALUES(?, ?, ?, ?, ?, ?, 0)";
			
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
			

//-----------------------------------------------------------------------------------------------------------------------------
	
	//SELECTED ONE PROJECTION 

	public Projection isItInFuture(int id) {
		
		Connection connection = ConnectionManager.getConnection();
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		try {
			
			String query = "SELECT movie, type, hall, date, price, adminsName FROM projections WHERE id = ? AND date >= '" +  dateFormat.format(datee) + "'";
			
			pstmt = connection.prepareStatement(query);
			pstmt.setInt(1, id);
			
			rset = pstmt.executeQuery();
			
			int index;													
			
			if(rset.next()) {
				index = 1;
				
				int movieId = rset.getInt(index++);
				int typeId = rset.getInt(index++);
				int hallId = rset.getInt(index++);
				Date date = dateFormat.parse(rset.getString(index++));
				int price = rset.getInt(index++);
				int adminsNameId = rset.getInt(index++);
				
				
				MovieDAO movieDAO = new MovieDAO();
				ProjectTypeDAO ptDAO = new ProjectTypeDAO();
				HallDAO hallDAO = new HallDAO();
				UserDAO userDAO = new UserDAO();
				
				Movie movie = movieDAO.findMovieById(movieId);						
				ProjectType type = ptDAO.findProjectTypeById(typeId);						
				Hall hall = hallDAO.findHallById(hallId);						
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

	//DELETE PROJECTION WITH TICKETS
	
	public boolean deleteProjectionWithTickets (int id ) {			
		
		Connection connection = ConnectionManager.getConnection();

		PreparedStatement pstmt = null;
		
		try {
		
			String query = "UPDATE projections SET deleted = 1 WHERE id = ?";
			
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
	
	//DELETE PROJECTION WITH NO TICKETS
	
	public boolean delete (int id) {			
		
		Connection connection = ConnectionManager.getConnection();

		PreparedStatement pstmt = null;
		try {
			String query = "DELETE FROM projections WHERE id = ?";
			
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
}
//--------------------------------------------------------------------------------------------------------------------