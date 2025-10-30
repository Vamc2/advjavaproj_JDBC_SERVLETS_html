package com.codegnan.factories;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class connectionFactory {
	private static final String url="jdbc:mysql://localhost:3306/jfs30_32";
	private static final String user="root";
	private static final String password="root";
	
	public static Connection getConnection() throws ClassNotFoundException ,SQLException{
		Class.forName("com.mysql.cj.jdbc.Driver");
		return DriverManager.getConnection(url, user, password);
		
	}
	
}
