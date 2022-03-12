package org.millburntalent;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.http.HttpServletResponse;

// Local Connection class which handles all query and
// connection purposes and takes queries until close().

public class DataConnection {
	
	Connection connection = null;
	
	public DataConnection init() {
		try {
			Class.forName( "org.postgresql.Driver" );
			
			connection = DriverManager.getConnection("jdbc:postgresql:mitdb", "web", "RyAp8nwL");
		}
		
		catch (ClassNotFoundException e) {
			System.out.println("Class not found, check spelling.");
			e.printStackTrace();
			return null;
		}

		catch (SQLException e) {
			System.out.println("SQL load exception");
			e.printStackTrace();
			return null;
		}
		
		return this;
	}
	
	public ResultSet query(String query) {
		try {
			Statement statement = connection.createStatement();
			return statement.executeQuery(query);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public PreparedStatement prepareStatement(String pst) throws SQLException {
		return connection.prepareStatement(pst);
	}
	
	public void close() {
		try {
			connection.close();
		} catch (SQLException e) {
			System.out.println("SQL close exception");
			e.printStackTrace();
		}
	}
	
	public static void printFailure(HttpServletResponse response) throws IOException {
		response.setContentType("text/html");
		response.getWriter().println("Server database connection failure. Please notify the site admin at seventhsolar@gmail.com.");
	}

}