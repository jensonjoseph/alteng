package com.alteng.orm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

public class EmployeeParser {

	ArrayList<Employee> empList = new ArrayList<Employee>();

	public String createEmployee(BufferedReader br, String line) throws IOException {
		String[] tmp = line.split(" ");
		Employee e = new Employee();

		empList.add(e);
		e.setId(Integer.parseInt(tmp[1]));
		StringBuffer name = new StringBuffer(tmp[2]);
		for (int i = 3; i < tmp.length; i++) {
			name.append(" " + tmp[i]);
		}
		e.setName(name.toString());
		line = br.readLine();
		while (line != null) {
			if (line.contains("START")) {
				break;
			} else if (line.contains("COMM_RULE")) {
				line = e.addCommissionRule(br);
			}
			if (line != null) {
				if (!line.contains("START")) {
					line = br.readLine();
				}
			}
		}
		return line;
	};

	public void display() {

		for (Employee e : empList) {
			System.out.println(e.getId() + " " + e.getName());
			Hashtable<String, Float> perTable = e.getPerTable();
			Set<Entry<String, Float>> percEntrySet = perTable.entrySet();
			for (Entry<String, Float> entry : percEntrySet) {
				System.out.println(entry.getKey() + " : " + entry.getValue());
			}
			Hashtable<String, Target> targetTable = e.getTargetTable();
			Set<Entry<String, Target>> targerEntrySet = targetTable.entrySet();

			for (Entry<String, Target> entry : targerEntrySet) {
				System.out.println(
						entry.getKey() + " " + entry.getValue().getAmount() + " " + entry.getValue().getBonus());
			}
		}
	}

	public void insert() throws IOException, ClassNotFoundException {
		Properties prop = getProperties();
		Connection con = null;
		try {
			con = ConnectionManager.getConnection(prop);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		for (Employee e : empList) {
			System.out.println(e.getId() + " " + e.getName());
			try {
				String query = "INSERT INTO ALTENG.EMPLOYEE VALUES(?,?)";
				PreparedStatement stmt = con.prepareStatement(query);
				stmt.setInt(1, e.getId());
				stmt.setString(2, e.getName());
				stmt.executeUpdate();
				stmt.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}

			Hashtable<String, Float> perTable = e.getPerTable();

			Set<Entry<String, Float>> percEntrySet = perTable.entrySet();
			for (Entry<String, Float> entry : percEntrySet) {

				System.out.println(entry.getKey() + " : " + entry.getValue());

				try {
					String query = "INSERT INTO ALTENG.COMM_PERCENTAGE VALUES(?,?,?)";
					PreparedStatement stmt = con.prepareStatement(query);
					stmt.setInt(1, e.getId());
					stmt.setInt(2, Integer.parseInt(entry.getKey()));
					stmt.setFloat(3, entry.getValue());
					stmt.executeUpdate();
					stmt.close();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
			Hashtable<String, Target> targetTable = e.getTargetTable();
			Set<Entry<String, Target>> targerEntrySet = targetTable.entrySet();

			for (Entry<String, Target> entry : targerEntrySet) {
				System.out.println(
						entry.getKey() + " " + entry.getValue().getAmount() + " " + entry.getValue().getBonus());
				try {
					String query = "INSERT INTO ALTENG.COMM_TARGET VALUES(?,?,?,?)";
					PreparedStatement stmt = con.prepareStatement(query);
					stmt.setInt(1, e.getId());
					stmt.setInt(2, Integer.parseInt(entry.getKey()));
					stmt.setInt(3, entry.getValue().getAmount());
					stmt.setInt(4, entry.getValue().getBonus());
					stmt.executeUpdate();
					stmt.close();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
		}

	}

	public Properties getProperties() throws IOException {
		Properties prop = new Properties();
		InputStream input = null;
		input = new FileInputStream("config.properties");
		prop.load(input);
		input.close();

		return prop;
	}

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		// first argument to program is the input file path
		// Sample file available in com.alteng.resources package
		
		
		File f = new File(args[0]);
		FileInputStream fis = new FileInputStream(f);
		InputStreamReader isr = new InputStreamReader(fis);
		BufferedReader br = new BufferedReader(isr);

		EmployeeParser driver = new EmployeeParser();

		String line = br.readLine();
		while (line != null) {
			// read file until you reach EOF
			if (line.contains("START")) {
				line = driver.createEmployee(br, line);
				// createEmployee would either return START line or EOF
			}
			if (line != null) {
				if (!line.contains("START")) {
					// if line has START then skip reading next line
					line = br.readLine();
				}
			}
		}

		driver.display();

		driver.insert();
		System.out.print("Done");

	}

}
// Input file
// START 1 Jenson Joseph Kakkattil
// .....
// 990
// 991
// COMM_RULE
// 2014 PERCENT 2.7
// 2014 TARGET 140000 4000
// START 2 Nibin Babu
// .....
// 990
// 991
// COMM_RULE
// 2014 PERCENT 2.7
// 2014 TARGET 140000 4000
// START 3 Arun Joy
// .....
// 990
// 991
// COMM_RULE
// 2014 PERCENT 2.7
// 2015 PERCENT 2.7
// 2014 TARGET 140000 4000
// 2015 TARGET 180000 8000
// 2015 TARGET 140000 9000
//
//
// mysql> select * from alteng.employee;
// +-------------+-------------------------+
// | id_employee | employee_name |
// +-------------+-------------------------+
// | 1 | Jenson Joseph Kakkattil |
// | 2 | Nibin Babu |
// | 3 | Arun Joy |
// +-------------+-------------------------+
//
// mysql> select * from alteng.comm_percentage;
// +-------------+------+---------+
// | id_employee | year | percent |
// +-------------+------+---------+
// | 1 | 2014 | 2.70 |
// | 2 | 2014 | 2.70 |
// | 3 | 2014 | 2.70 |
// | 3 | 2015 | 2.70 |
// +-------------+------+---------+
//
// mysql> select * from alteng.comm_target;
// +-------------+------+--------+-------+
// | id_employee | year | target | bonus |
// +-------------+------+--------+-------+
// | 1 | 2014 | 140000 | 4000 |
// | 2 | 2014 | 140000 | 4000 |
// | 3 | 2014 | 140000 | 4000 |
// | 3 | 2015 | 140000 | 9000 |
// +-------------+------+--------+-------+
 