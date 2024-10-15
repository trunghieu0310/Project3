package com.javaweb.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionJDBCUtil {
	static final String URL = "jdbc:mysql://localhost:3306/estatebasic";
	 static final String USER = "root";
	 static final String PASSWORD = "123456";
	 public static Connection getConnection() {
		 Connection conn = null;
		 try {
			 conn = DriverManager.getConnection(URL,USER,PASSWORD);
			 return conn;
		 }
		 catch(SQLException e) {
			 e.printStackTrace();
		 }
		 return conn;
	 }
}
