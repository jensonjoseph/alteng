package com.alteng.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map.Entry;
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

	public static void main(String[] args) throws IOException {
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
		System.out.print("Done");

	}

}
