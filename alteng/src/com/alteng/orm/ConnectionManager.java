package com.alteng.orm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionManager {
	private ConnectionManager() {

	}

	private static Connection con = null;

	public static Connection getConnection(Properties prop) throws ClassNotFoundException, SQLException {
		if (con == null) {
			String url = "jdbc:mysql://" + prop.getProperty("DB_SERVER") + ":" + prop.getProperty("DB_PORT") + "/"
					+ prop.getProperty("DB_NAME");
			String userId = prop.getProperty("DB_USER");
			String password = prop.getProperty("DB_USER");
			Class.forName(prop.getProperty("CLASSNAME"));
			con = DriverManager.getConnection(url, userId, password);
		}

		return con;
	}
}
