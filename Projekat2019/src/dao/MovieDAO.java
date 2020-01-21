package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;

import model.Movie;
import model.Role;
import model.User;

public class MovieDAO {

	
	//ALL MOVIES
		
	public ArrayList<Movie> getAllMovies(){
		ArrayList<Movie> movies = new ArrayList<Movie>();
		
		java.sql.Connection connection = ConnectionManager.getConnection();
		
		java.sql.Statement stmt = null;
		ResultSet rset = null;
		
		try {
			String query = "SELECT * FROM movies";
			
			stmt = connection.createStatement();
			rset = stmt.executeQuery(query);
			
			int index;
			while(rset.next()) {
				index = 1;
				
				int id = rset.getInt(index++);
				String name = rset.getString(index++);
				String director = rset.getString(index++);
				String actors = rset.getString(index++);
				String style = rset.getString(index++);
				int duration = rset.getInt(index++);
				String distributor = rset.getString(index++);
				String country = rset.getString(index++);
				String year = rset.getString(index++);
				String description = rset.getString(index++);
				
				movies.add(new Movie(id, name, director, actors, style, duration, distributor, country, year, description));
				
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
		return movies;
	}

//-----------------------------------------------------------------------------------------------------------------------------------------------------------------	

	//SELECTED ONE

	public Movie findMovieById(int id) {
		
		Connection connection = ConnectionManager.getConnection();
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		try {
			
			String query = "SELECT name, director, actors, style, duration, distributor, country, year, description FROM movies WHERE id = ?";
					
					pstmt = connection.prepareStatement(query);
					pstmt.setInt(1, id);
					
					rset = pstmt.executeQuery();
					
					int index;
					if(rset.next()) {
						index = 1;
						
						String name = rset.getString(index++);
						String director = rset.getString(index++);
						String actors = rset.getString(index++);
						String style = rset.getString(index++);
						int duration = rset.getInt(index++);
						String distributor = rset.getString(index++);
						String country = rset.getString(index++);
						String year = rset.getString(index++);
						String description = rset.getString(index++);
						
						
						return new Movie(id, name, director, actors, style, duration, distributor, country, year, description);
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
	
	//find movie 
	//TO DO: OPSEG VREMENA I GODINA!!!
	
	public ArrayList<Movie> findMovieByName(String movieName) {
	
		System.out.println("Upao u dao name");
		ArrayList<Movie> movies = new ArrayList<Movie>();
		
		Connection connection = ConnectionManager.getConnection();
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		try {
			
			String query = "SELECT id, name, director, actors, style, duration, distributor, country, year, description FROM movies WHERE name LIKE ? ";
			
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, movieName + "%");
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				int index = 1;
				Integer id = rset.getInt(index++);
				String nameMovie = rset.getString(index++);
				String director = rset.getString(index++);
				String actors = rset.getString(index++);
				String style = rset.getString(index++);
				int duration = rset.getInt(index++);
				String distributor = rset.getString(index++);
				String country = rset.getString(index++);
				String year = rset.getString(index++);
				String description = rset.getString(index++);
				
				movies.add(new Movie(id, nameMovie, director, actors, style, duration, distributor, country, year, description));
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
		return movies;		
	}
		
	//-----------------------------------------------------------------------------------------------------------------------------
public ArrayList<Movie> findMovieByStyle(String movieStyle) {
		
		ArrayList<Movie> movies = new ArrayList<Movie>();
		
		Connection connection = ConnectionManager.getConnection();
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		try {
			
			String query = "SELECT id, name, director, actors, style, duration, distributor, country, year, description FROM movies WHERE style LIKE ? ";
			
			pstmt = connection.prepareStatement(query);
			pstmt.setString(4, movieStyle + "%");
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				int index = 1;
				Integer id = rset.getInt(index++);
				String nameMovie = rset.getString(index++);
				String director = rset.getString(index++);
				String actors = rset.getString(index++);
				String style = rset.getString(index++);
				int duration = rset.getInt(index++);
				String distributor = rset.getString(index++);
				String country = rset.getString(index++);
				String year = rset.getString(index++);
				String description = rset.getString(index++);
				
				movies.add(new Movie(id, nameMovie, director, actors, style, duration, distributor, country, year, description));
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
		return movies;		
	}
		
	//-----------------------------------------------------------------------------------------------------------------------------
public ArrayList<Movie> findMovieByDistributor(String movieDistributor) {
	
	ArrayList<Movie> movies = new ArrayList<Movie>();
	
	Connection connection = ConnectionManager.getConnection();
	
	PreparedStatement pstmt = null;
	ResultSet rset = null;
	
	try {
		
		String query = "SELECT id, name, director, actors, style, duration, distributor, country, year, description FROM movies WHERE distributor LIKE ? ";
		
		pstmt = connection.prepareStatement(query);
		pstmt.setString(6, movieDistributor + "%");
		
		rset = pstmt.executeQuery();
		
		while(rset.next()) {
			int index = 1;
			Integer id = rset.getInt(index++);
			String nameMovie = rset.getString(index++);
			String director = rset.getString(index++);
			String actors = rset.getString(index++);
			String style = rset.getString(index++);
			int duration = rset.getInt(index++);
			String distributor = rset.getString(index++);
			String country = rset.getString(index++);
			String year = rset.getString(index++);
			String description = rset.getString(index++);
			
			movies.add(new Movie(id, nameMovie, director, actors, style, duration, distributor, country, year, description));
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
	return movies;		
}
	
//-----------------------------------------------------------------------------------------------------------------------------
public ArrayList<Movie> findMovieByCountry(String movieCountry) {
	
	
	ArrayList<Movie> movies = new ArrayList<Movie>();
	
	Connection connection = ConnectionManager.getConnection();
	
	PreparedStatement pstmt = null;
	ResultSet rset = null;
	
	try {
		
		String query = "SELECT id, name, director, actors, style, duration, distributor, country, year, description FROM movies WHERE country LIKE ? ";
		
		pstmt = connection.prepareStatement(query);
		pstmt.setString(7, movieCountry + "%");
		
		rset = pstmt.executeQuery();
		
		while(rset.next()) {
			int index = 1;
			Integer id = rset.getInt(index++);
			String nameMovie = rset.getString(index++);
			String director = rset.getString(index++);
			String actors = rset.getString(index++);
			String style = rset.getString(index++);
			int duration = rset.getInt(index++);
			String distributor = rset.getString(index++);
			String country = rset.getString(index++);
			String year = rset.getString(index++);
			String description = rset.getString(index++);
			
			movies.add(new Movie(id, nameMovie, director, actors, style, duration, distributor, country, year, description));
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
	return movies;		
}
	
//-----------------------------------------------------------------------------------------------------------------------------
	
}
