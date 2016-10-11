package com.alteng.summercamp;

import java.util.Calendar;

public class Student {
	String name;
	Calendar cal;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Calendar getCal() {
		return cal;
	}

	public void setCal(Calendar cal) {
		this.cal = cal;
	}

	Student(String name, Calendar date) {
		this.name = name;
		this.cal = date;
	}
}
