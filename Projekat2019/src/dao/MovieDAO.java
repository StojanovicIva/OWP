package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import model.Movie;

public class MovieDAO {
	
//-------------------------------------------------------------------------------------------------------------------------------------------------
	//ALL MOVIES
		
	public ArrayList<Movie> getAllMovies(){
		
		Connection connection = ConnectionManager.getConnection();
		
		ArrayList<Movie> movies = new ArrayList<Movie>();
				
		java.sql.Statement stmt = null;
		ResultSet rset = null;
		
		try {
			String query = "SELECT * FROM movies WHERE deleted = 0";
			
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
				int year = rset.getInt(index++);
				String description = rset.getString(index++);
				int delete = rset.getInt(index++);
				
				movies.add(new Movie(id, name, director, actors, style, duration, distributor, country, year, description,delete));
				
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
			
			String query = "SELECT name, director, actors, style, duration, distributor, country, year, description, deleted FROM movies WHERE id = ?";
					
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
				int year = rset.getInt(index++);
				String description = rset.getString(index++);
				int deleted = rset.getInt(index++);
				
				
				return new Movie(id, name, director, actors, style, duration, distributor, country, year, description, deleted);
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
		
	//FIND MOVIE BY MOVIE NAME
	
	public ArrayList<Movie> findMovieByName(String movieName) {
	
		Connection connection = ConnectionManager.getConnection();
		
		ArrayList<Movie> movies = new ArrayList<Movie>();			
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		try {
			
			String query = "SELECT id, name, director, actors, style, duration, distributor, country, year, description, deleted FROM movies WHERE name LIKE ? AND deleted = 0";
			
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
				int year = rset.getInt(index++);
				String description = rset.getString(index++);
				int deleted = rset.getInt(index++);
				
				movies.add(new Movie(id, nameMovie, director, actors, style, duration, distributor, country, year, description, deleted));
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
	
	//FIND MOVIE BY MOVIE STYLE 
	
	public ArrayList<Movie> findMovieByStyle(String movieStyle) {
			
		Connection connection = ConnectionManager.getConnection();
		
		ArrayList<Movie> movies = new ArrayList<Movie>();
						
		PreparedStatement pstmt = null;
		ResultSet rset = null;
			
		try {
				
			String query = "SELECT id, name, director, actors, style, duration, distributor, country, year, description, deleted FROM movies WHERE style LIKE ? AND deleted = 0";
			
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, "%" + movieStyle + "%");
			
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
				int year = rset.getInt(index++);
				String description = rset.getString(index++);
				int deleted = rset.getInt(index++);
				
				movies.add(new Movie(id, nameMovie, director, actors, style, duration, distributor, country, year, description, deleted));
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

	//FIND MOVIE BY MOVIE DISTRIBUTOR
	
	public ArrayList<Movie> findMovieByDistributor(String movieDistributor) {
	
		Connection connection = ConnectionManager.getConnection();
		
		ArrayList<Movie> movies = new ArrayList<Movie>();
				
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		try {
			
			String query = "SELECT id, name, director, actors, style, duration, distributor, country, year, description, deleted FROM movies WHERE distributor LIKE ? AND deleted = 0";
			
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, "%" + movieDistributor + "%");
			
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
				int year = rset.getInt(index++);
				String description = rset.getString(index++);
				int deleted = rset.getInt(index++);
				
				movies.add(new Movie(id, nameMovie, director, actors, style, duration, distributor, country, year, description, deleted));
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
	
	//FIND MOVIE BY COUNTRY
	
	public ArrayList<Movie> findMovieByCountry(String movieCountry) {
		
		Connection connection = ConnectionManager.getConnection();
		
		ArrayList<Movie> movies = new ArrayList<Movie>();
				
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		try {
			
			String query = "SELECT id, name, director, actors, style, duration, distributor, country, year, description, deleted FROM movies WHERE country LIKE ? AND deleted = 0";
			
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, "%" + movieCountry + "%");
			
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
				int year = rset.getInt(index++);
				String description = rset.getString(index++);
				int deleted = rset.getInt(index++);
				
				movies.add(new Movie(id, nameMovie, director, actors, style, duration, distributor, country, year, description, deleted));
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
	
	//FIND MOVIE BY RANGE OF TIME
	
	public ArrayList<Movie> findMovieByRangeOfTime(int fromThe, int to) {
		
		Connection connection = ConnectionManager.getConnection();
		
		ArrayList<Movie> movies = new ArrayList<Movie>();
				
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		try {
			
			String query = "SELECT id, name, director, actors, style, duration, distributor, country, year, description, deleted FROM movies WHERE duration >= ? AND duration <= ? AND deleted = 0";
			
			pstmt = connection.prepareStatement(query);
			
			int index=1;
			pstmt.setInt(index++, fromThe);
			pstmt.setInt(index++, to);
			
			rset = pstmt.executeQuery();
						
			while(rset.next()) {
				index=1;
				Integer id = rset.getInt(index++);
				String nameMovie = rset.getString(index++);
				String director = rset.getString(index++);
				String actors = rset.getString(index++);
				String style = rset.getString(index++);
				int duration = rset.getInt(index++);
				String distributor = rset.getString(index++);
				String country = rset.getString(index++);
				int year = rset.getInt(index++);
				String description = rset.getString(index++);
				int deleted = rset.getInt(index++);
				
				movies.add(new Movie(id, nameMovie, director, actors, style, duration, distributor, country, year, description, deleted));
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
	
	//FIND MOVIE BY RANGE OF DATE
	
	public ArrayList<Movie> findMovieByRangeOfDate(int fromThe, int to) {

		Connection connection = ConnectionManager.getConnection();
		
		ArrayList<Movie> movies = new ArrayList<Movie>();
					
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		try {
			
			String query = "SELECT id, name, director, actors, style, duration, distributor, country, year, description, deleted FROM movies WHERE year >= ? AND year <= ? AND deleted = 0";
			
			pstmt = connection.prepareStatement(query);
			
			int index=1;
			pstmt.setInt(index++, fromThe);
			pstmt.setInt(index++, to);
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				index = 1;
				Integer id = rset.getInt(index++);
				String nameMovie = rset.getString(index++);
				String director = rset.getString(index++);
				String actors = rset.getString(index++);
				String style = rset.getString(index++);
				int duration = rset.getInt(index++);
				String distributor = rset.getString(index++);
				String country = rset.getString(index++);
				int year = rset.getInt(index++);
				String description = rset.getString(index++);
				int deleted = rset.getInt(index++);
				
				movies.add(new Movie(id, nameMovie, director, actors, style, duration, distributor, country, year, description, deleted));
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
		
	//FUNCTION FOR CREATING NEW MOVIE
		
	public boolean addMovie(Movie movie) {
		
		Connection connection = ConnectionManager.getConnection();
					
		PreparedStatement pstmt = null;
		try {
			String query = "INSERT INTO movies (name, director, actors, style, duration, distributor, country, year, description, deleted) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, 0 )";
			
			pstmt = connection.prepareStatement(query);
			
			int index = 1;
			pstmt.setString(index++, movie.getName());
			pstmt.setString(index++, movie.getDirector());
			pstmt.setString(index++, movie.getActors());
			pstmt.setString(index++, movie.getStyle());
			pstmt.setInt(index++, movie.getDuration());
			pstmt.setString(index++, movie.getDistributor());
			pstmt.setString(index++, movie.getCountry());
			pstmt.setInt(index++, movie.getYear());
			pstmt.setString(index++, movie.getDescription());
			
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

	//FUNCTION FOR UPDATE
	
	public boolean update(Movie updatedMovie) {
			
		Connection connection = ConnectionManager.getConnection();

		PreparedStatement pstmt = null;
		try {
			String query = "UPDATE movies SET name = ?, director = ?, actors = ?, style = ?, duration = ?, distributor = ?, country = ?, year = ?, description = ?  WHERE id = ?";
			
			pstmt = connection.prepareStatement(query);
			int index = 1;
			pstmt.setString(index++, updatedMovie.getName());
			pstmt.setString(index++, updatedMovie.getDirector());
			pstmt.setString(index++, updatedMovie.getActors());
			pstmt.setString(index++, updatedMovie.getStyle());
			pstmt.setInt(index++, updatedMovie.getDuration());
			pstmt.setString(index++, updatedMovie.getDistributor());
			pstmt.setString(index++, updatedMovie.getCountry());
			pstmt.setInt(index++, updatedMovie.getYear());
			pstmt.setString(index++, updatedMovie.getDescription());
			pstmt.setInt(index++, updatedMovie.getId());
			
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
	
	//DELETE MOVIE WITH PROJECTION
	
	public boolean deleteMovieWithProjection (int id ) {			
			
		Connection connection = ConnectionManager.getConnection();

		PreparedStatement pstmt = null;
		try {
			String query = "UPDATE movies SET deleted = 1 WHERE id = ?";
			
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

	//DELETE MOVIE WITH NO PROJECTION
	
	public boolean delete (int id) {			
			
		Connection connection = ConnectionManager.getConnection();

		PreparedStatement pstmt = null;
		try {
			String query = "DELETE FROM movies WHERE id = ?";
			
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
