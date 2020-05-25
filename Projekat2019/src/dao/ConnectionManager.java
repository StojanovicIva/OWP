package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
	
	private static final String DATABASE_NAME ="DataBase.db";
	private static final String WINDOWS_PATH = "C:\\Users\\Iva\\git\\OWP\\Projekat2019\\db\\" +  DATABASE_NAME;
	private static final String PATH = WINDOWS_PATH;
	private static Connection connection;

//---------------------------------------------------------------------------------------------------------------------
	
	public static void open() {
		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:" + PATH);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

//--------------------------------------------------------------------------------------------------------------------
	
	public static Connection getConnection() {
		try {
			return DriverManager.getConnection("jdbc:sqlite:" + PATH);	
		} catch (Exception e) {
			return null;
		}		
	}

//--------------------------------------------------------------------------------------------------------------------
	public static void close() {
		try {
			connection.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
}