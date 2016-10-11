package com.alteng.summercamp;

import java.util.Calendar;
import java.util.Comparator;

public class Student_SunToSat_Comparator implements Comparator<Student> {

	@Override
	public int compare(Student o1, Student o2) {

		return o1.getCal().get(Calendar.DAY_OF_WEEK) - o2.getCal().get(Calendar.DAY_OF_WEEK);
	}

}
