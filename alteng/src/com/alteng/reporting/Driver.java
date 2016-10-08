package com.alteng.reporting;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;
import java.util.Set;

import com.alteng.orm.ConnectionManager;

public class Driver {
	Properties prop;

	public void setProperties() throws IOException {
		prop = new Properties();
		InputStream input = null;
		input = new FileInputStream("config.properties");
		prop.load(input);
		input.close();
	}

	public void displayTree(Node root) throws ClassNotFoundException, SQLException, IOException {
		System.out.println("Manager : " + root.getId() + " : Name : " + root.getName());

		root.fetchAndSetReporteesList();

		System.out
				.println("Displaying direct reportees of : " + root.getName() + "(" + root.getReportee().size() + ")");

		Set<Node> reportees = root.getReportee();
		for (Node node : reportees) {
			System.out.println("ID : " + node.getId() + " : Name : " + node.getName());
		}
		System.out.println("***");
		for (Node node : reportees) {
			displayTree(node);
		}

	}

	public Driver() throws IOException {
		setProperties();
	}

	public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException {

		
		Driver dr = new Driver();
		dr.setProperties();

		Scanner s = new Scanner(System.in);
		int id = s.nextInt();
		s.close();
		String name = "";
		Connection con = ConnectionManager.getConnection(dr.prop);
		String sql = "SELECT ID_EMPLOYEE,EMPLOYEE_NAME FROM alteng.EMPLOYEE WHERE ID_EMPLOYEE=?";
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, id);

		ResultSet rs = stmt.executeQuery();

		while (rs.next()) {
			id = rs.getInt(1);
			name = rs.getString(2);
		}

		Node root = new Node(id, name);

		dr.displayTree(root);

	}

}
//mysql> select * from alteng.employee
//-> ;
//+-------------+-------------------------+
//| id_employee | employee_name           |
//+-------------+-------------------------+
//|           1 | Jenson Joseph Kakkattil |
//|           2 | Nibin Babu              |
//|           3 | Arun Joy                |
//|           4 | Paul Hogan              |
//|           5 | Samer Shamon            |
//|           6 | Mark Hamp               |
//|           7 | Bob Ford                |
//|           8 | Girish Sheshadri        |
//|           9 | Sunil Kumar             |
//|          10 | Ankit Sharma            |
//|          11 | Pratik Kolge            |
//+-------------+-------------------------+
//
//mysql> select * from alteng.reporting;
//+-------------+------------+
//| employee_id | manager_id |
//+-------------+------------+
//|           1 |          4 |
//|          10 |          4 |
//|           4 |          5 |
//|           6 |          5 |
//|           3 |          6 |
//|          11 |          6 |
//|           5 |          7 |
//|           8 |          7 |
//|           2 |          8 |
//|           9 |          8 |
//+-------------+------------+


//Output
//7
//Sat Oct 08 14:38:34 EDT 2016 WARN: Establishing SSL connection without server's identity verification is not recommended. According to MySQL 5.5.45+, 5.6.26+ and 5.7.6+ requirements SSL connection must be established by default if explicit option isn't set. For compliance with existing applications not using SSL the verifyServerCertificate property is set to 'false'. You need either to explicitly disable SSL by setting useSSL=false, or set useSSL=true and provide truststore for server certificate verification.
//Manager : 7 : Name : Bob Ford
//Displaying direct reportees of : Bob Ford(2)
//ID : 5 : Name : Samer Shamon
//ID : 8 : Name : Girish Sheshadri
//***
//Manager : 5 : Name : Samer Shamon
//Displaying direct reportees of : Samer Shamon(2)
//ID : 4 : Name : Paul Hogan
//ID : 6 : Name : Mark Hamp
//***
//Manager : 4 : Name : Paul Hogan
//Displaying direct reportees of : Paul Hogan(2)
//ID : 10 : Name : Ankit Sharma
//ID : 1 : Name : Jenson Joseph Kakkattil
//***
//Manager : 10 : Name : Ankit Sharma
//Displaying direct reportees of : Ankit Sharma(0)
//***
//Manager : 1 : Name : Jenson Joseph Kakkattil
//Displaying direct reportees of : Jenson Joseph Kakkattil(0)
//***
//Manager : 6 : Name : Mark Hamp
//Displaying direct reportees of : Mark Hamp(2)
//ID : 11 : Name : Pratik Kolge
//ID : 3 : Name : Arun Joy
//***
//Manager : 11 : Name : Pratik Kolge
//Displaying direct reportees of : Pratik Kolge(0)
//***
//Manager : 3 : Name : Arun Joy
//Displaying direct reportees of : Arun Joy(0)
//***
//Manager : 8 : Name : Girish Sheshadri
//Displaying direct reportees of : Girish Sheshadri(2)
//ID : 2 : Name : Nibin Babu
//ID : 9 : Name : Sunil Kumar
//***
//Manager : 2 : Name : Nibin Babu
//Displaying direct reportees of : Nibin Babu(0)
//***
//Manager : 9 : Name : Sunil Kumar
//Displaying direct reportees of : Sunil Kumar(0)
//***