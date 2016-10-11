package com.alteng.summercamp;

import java.util.Calendar;
import java.util.Comparator;

public class Student_AlphabeticDay_Comparator implements Comparator<Student> {

	@Override
	public int compare(Student o1, Student o2) {

		return Day.values()[o1.getCal().get(Calendar.DAY_OF_WEEK) - 1].toString()
				.compareTo(Day.values()[o2.getCal().get(Calendar.DAY_OF_WEEK) - 1].toString());
	}

}
