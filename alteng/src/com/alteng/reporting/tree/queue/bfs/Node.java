package com.alteng.reporting.tree.queue.bfs;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Properties;
import java.util.Queue;
import java.util.Set;

import com.alteng.fileparser.orm.ConnectionManager;

public class Node {
	private int id;
	private String name;
	private Set<Node> reportee = new HashSet<Node>();

	public Node(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public void fetchAndSetReporteesList(Queue<Node> hierarchyQueue) throws ClassNotFoundException, SQLException, IOException {
		Properties prop = new Properties();
		InputStream input = null;
		input = new FileInputStream("config.properties");
		prop.load(input);
		input.close();
		Set<Node> reporteeList = new HashSet<Node>();
		Connection con = ConnectionManager.getConnection(prop);
		String sql = "SELECT EMPLOYEE_ID, EMPLOYEE_NAME FROM EMPLOYEE join REPORTING on ID_EMPLOYEE =EMPLOYEE_ID WHERE MANAGER_ID=?";
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, id);

		ResultSet rs = stmt.executeQuery();
		int reporteeID;
		String reporteeName = "";
		while (rs.next()) {
			reporteeID = rs.getInt(1);
			reporteeName = rs.getString(2);
			Node tmp=new Node(reporteeID, reporteeName);
			reportee.add(tmp);
			hierarchyQueue.add(tmp);
		}
		// return reporteeList;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Node> getReportee() {
		return reportee;
	}

	public void setReportee(Set<Node> reportee) {
		this.reportee = reportee;
	}
}