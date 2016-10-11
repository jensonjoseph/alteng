package com.alteng.summercamp;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Locale;
import java.util.Scanner;

public class StudentParser {
	ArrayList<Student> students = new ArrayList<Student>();

	public void display() {
		System.out.println("***");
		for (Student student : students) {
			System.out.println("Student name :" + student.getName() + "\t" + "DOB : "
					+ Day.values()[student.getCal().get(Calendar.DAY_OF_WEEK) - 1] + "|" + student.getCal().getTime());
		}
		System.out.println("***");
	}

	public static void main(String[] args) throws FileNotFoundException, ParseException {
		File f = new File("C:/Jenson/gitRepository/alteng/alteng/src/summercamp.txt");
		Scanner scan = new Scanner(f);
		// Letter Date or Time Component Presentation Examples
		// G Era designator Text AD
		// y Year Year 1996; 96
		// Y Week year Year 2009; 09
		// M Month in year Month July; Jul; 07
		// w Week in year Number 27
		// W Week in month Number 2
		// D Day in year Number 189
		// d Day in month Number 10
		// F Day of week in month Number 2
		// E Day name in week Text Tuesday; Tue
		// u Day number of week (1 = Monday, ..., 7 = Sunday) Number 1
		// a Am/pm marker Text PM
		// H Hour in day (0-23) Number 0
		// k Hour in day (1-24) Number 24
		// K Hour in am/pm (0-11) Number 0
		// h Hour in am/pm (1-12) Number 12
		// m Minute in hour Number 30
		// s Second in minute Number 55
		// S Millisecond Number 978
		// z Time zone General time zone Pacific Standard Time; PST; GMT-08:00
		// Z Time zone RFC 822 time zone -0800
		// X Time zone ISO 8601 time zone -08; -0800; -08:00

		StudentParser driver = new StudentParser();
		while (scan.hasNext()) {
			String line = scan.nextLine();
			String[] tmp = line.split(",");
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
			Calendar cal = Calendar.getInstance();

			cal.setTime(df.parse(tmp[1]));
			Student s = new Student(tmp[0], cal);
			driver.students.add(s);
			System.out.println(Day.values()[s.getCal().get(Calendar.DAY_OF_WEEK) - 1] + "|" + s.getCal().getTime());

		}
		scan.close();
		driver.display();
		Collections.sort(driver.students, new StudentComparator());
		driver.display();
		System.out.println("Done");
	}

}
