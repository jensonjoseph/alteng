package com.alteng.fileparser.orm;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Hashtable;

public class Employee {
	private int id;
	private String name;
	private Hashtable<String, Float> perTable = new Hashtable<String, Float>();
	private Hashtable<String, Target> targetTable = new Hashtable<String, Target>();

	public String addCommissionRule(BufferedReader br) throws IOException {
		String line = br.readLine();
		while (line != null) {
			if (line.contains("START")) {
				break;
			} else if (line.contains("PERCENT")) {
				String[] tmp = line.split(" ");
				this.setPercentage(tmp[0], Float.parseFloat(tmp[2]));
			} else if (line.contains("TARGET")) {
				String[] tmp = line.split(" ");
				Target target = new Target(Integer.parseInt(tmp[2]), Integer.parseInt(tmp[3]));
				this.setTarget(tmp[0], target);
			}
			line = br.readLine();
		}
		return line;
	}

	public Hashtable<String, Float> getPerTable() {
		return perTable;
	}

	public void setPerTable(Hashtable<String, Float> perTable) {
		this.perTable = perTable;
	}

	public void setPercentage(String year, float val) {

		this.perTable.put(year, val);
	}

	public Hashtable<String, Target> getTargetTable() {
		return targetTable;
	}

	public void setTargetTable(Hashtable<String, Target> targetTable) {
		this.targetTable = targetTable;
	}

	public void setTarget(String year, Target target) {
		this.targetTable.put(year, target);
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

}
