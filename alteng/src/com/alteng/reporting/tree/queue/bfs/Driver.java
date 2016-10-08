package com.alteng.reporting.tree.queue.bfs;

import java.awt.event.HierarchyBoundsAdapter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Properties;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

import com.alteng.fileparser.orm.ConnectionManager;

public class Driver {
	Properties prop;
	Queue<Node> hierarchyQueue = new LinkedList<Node>();

	public void setProperties() throws IOException {
		prop = new Properties();
		InputStream input = null;
		input = new FileInputStream("config.properties");
		prop.load(input);
		input.close();
	}

	public void display(Node n) {
		System.out.println("***Start***");
		System.out.println(
				"Manager (" + n.getId() + ") : " + n.getName() + " has " + n.getReportee().size() + " reportees.");
		for (Node child : n.getReportee()) {
			System.out.println("Reportees (" + child.getId() + ") : " + child.getName());
		}
		System.out.println("***End***");
	}

	public void generateTree() throws ClassNotFoundException, SQLException, IOException {
		while (!hierarchyQueue.isEmpty()) {
			Node node = hierarchyQueue.remove();
			node.fetchAndSetReporteesList(hierarchyQueue);
			display(node);
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
		dr.hierarchyQueue.add(root);
		dr.generateTree();

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
// 7
// ***Start***
// Manager (7) : Bob Ford has 2 reportees.
// Reportees (8) : Girish Sheshadri
// Reportees (5) : Samer Shamon
// ***End***
// ***Start***
// Manager (5) : Samer Shamon has 2 reportees.
// Reportees (4) : Paul Hogan
// Reportees (6) : Mark Hamp
// ***End***
// ***Start***
// Manager (8) : Girish Sheshadri has 2 reportees.
// Reportees (2) : Nibin Babu
// Reportees (9) : Sunil Kumar
// ***End***
// ***Start***
// Manager (4) : Paul Hogan has 2 reportees.
// Reportees (10) : Ankit Sharma
// Reportees (1) : Jenson Joseph Kakkattil
// ***End***
// ***Start***
// Manager (6) : Mark Hamp has 2 reportees.
// Reportees (11) : Pratik Kolge
// Reportees (3) : Arun Joy
// ***End***
// ***Start***
// Manager (2) : Nibin Babu has 0 reportees.
// ***End***
// ***Start***
// Manager (9) : Sunil Kumar has 0 reportees.
// ***End***
// ***Start***
// Manager (1) : Jenson Joseph Kakkattil has 0 reportees.
// ***End***
// ***Start***
// Manager (10) : Ankit Sharma has 0 reportees.
// ***End***
// ***Start***
// Manager (3) : Arun Joy has 0 reportees.
// ***End***
// ***Start***
// Manager (11) : Pratik Kolge has 0 reportees.
// ***End***
