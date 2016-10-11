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
import java.util.Hashtable;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;

public class StudentParser {
	ArrayList<Student> students = new ArrayList<Student>();
	DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
	TreeMap<String, Integer> dayCount = new TreeMap<String, Integer>(new CountComparator());

	public void displayList() {
		System.out.println("***");
		for (Student student : students) {
			System.out.println(
					"Student name :" + student.getName() + "\t" + "DOB : " + df.format(student.getCal().getTime())
							+ "\t|\t" + Day.values()[student.getCal().get(Calendar.DAY_OF_WEEK) - 1]);
		}
		System.out.println("***");
	}

	public void displayDayCount() {
		for (Student student : students) {
			if (dayCount.containsKey(Day.values()[student.getCal().get(Calendar.DAY_OF_WEEK) - 1].toString())) {
				int tmp = dayCount.get(Day.values()[student.getCal().get(Calendar.DAY_OF_WEEK) - 1].toString());
				tmp++;
				dayCount.put(Day.values()[student.getCal().get(Calendar.DAY_OF_WEEK) - 1].toString(), tmp);
			} else {
				dayCount.put(Day.values()[student.getCal().get(Calendar.DAY_OF_WEEK) - 1].toString(), 1);
			}
		}

		Set<Entry<String, Integer>> entries = dayCount.entrySet();
		System.out.println("*** COUNT ***");
		for (Entry<String, Integer> entry : entries) {

			System.out.println(entry.getKey() + "\t" + entry.getValue());

		}

	}

	public static void main(String[] args) throws FileNotFoundException, ParseException {
		File f = new File("C:/Jenson/gitRepository/alteng/alteng/src/summercamp.txt");
		Scanner scan = new Scanner(f);

		StudentParser driver = new StudentParser();
		while (scan.hasNext()) {
			String line = scan.nextLine();
			String[] tmp = line.split(",");

			Calendar cal = Calendar.getInstance();

			cal.setTime(driver.df.parse(tmp[1]));
			Student s = new Student(tmp[0], cal);
			driver.students.add(s);
			// System.out.println(Day.values()[s.getCal().get(Calendar.DAY_OF_WEEK)
			// - 1].toString() + "|"
			// + driver.df.format(s.getCal().getTime()) + " " + s.getName());

		}
		scan.close();
		System.out.println("Unsorted List");
		driver.displayList();
		Collections.sort(driver.students, new Student_SunToSat_Comparator());
		System.out.println("Sorted List by day of week[Sunday to Saturday]");
		driver.displayList();
		Collections.sort(driver.students, new Student_AlphabeticDay_Comparator());
		System.out.println("Sorted List by day of week[Alphabetic]");
		driver.displayList();
		driver.displayDayCount();
		System.out.println("Done");
	}

}
/*
Input
John,1988-12-20
Maria,1988-11-18
Smith,1987-01-03
Jibin,1984-08-09
John,1988-12-20
Smith,1987-01-03
Jibin,1984-08-09
John,1988-12-20
Maria,1988-11-18
Smith,1987-01-03
*/


/*Output
 * Unsorted List
***
Student name :John	DOB : 1988-12-20	|	TUESDAY
Student name :Maria	DOB : 1988-11-18	|	FRIDAY
Student name :Smith	DOB : 1987-01-03	|	SATURDAY
Student name :Jibin	DOB : 1984-08-09	|	THURSDAY
Student name :John	DOB : 1988-12-20	|	TUESDAY
Student name :Smith	DOB : 1987-01-03	|	SATURDAY
Student name :Jibin	DOB : 1984-08-09	|	THURSDAY
Student name :John	DOB : 1988-12-20	|	TUESDAY
Student name :Maria	DOB : 1988-11-18	|	FRIDAY
Student name :Smith	DOB : 1987-01-03	|	SATURDAY
***
Sorted List by day of week[Sunday to Saturday]
***
Student name :John	DOB : 1988-12-20	|	TUESDAY
Student name :John	DOB : 1988-12-20	|	TUESDAY
Student name :John	DOB : 1988-12-20	|	TUESDAY
Student name :Jibin	DOB : 1984-08-09	|	THURSDAY
Student name :Jibin	DOB : 1984-08-09	|	THURSDAY
Student name :Maria	DOB : 1988-11-18	|	FRIDAY
Student name :Maria	DOB : 1988-11-18	|	FRIDAY
Student name :Smith	DOB : 1987-01-03	|	SATURDAY
Student name :Smith	DOB : 1987-01-03	|	SATURDAY
Student name :Smith	DOB : 1987-01-03	|	SATURDAY
***
Sorted List by day of week[Alphabetic]
***
Student name :Maria	DOB : 1988-11-18	|	FRIDAY
Student name :Maria	DOB : 1988-11-18	|	FRIDAY
Student name :Smith	DOB : 1987-01-03	|	SATURDAY
Student name :Smith	DOB : 1987-01-03	|	SATURDAY
Student name :Smith	DOB : 1987-01-03	|	SATURDAY
Student name :Jibin	DOB : 1984-08-09	|	THURSDAY
Student name :Jibin	DOB : 1984-08-09	|	THURSDAY
Student name :John	DOB : 1988-12-20	|	TUESDAY
Student name :John	DOB : 1988-12-20	|	TUESDAY
Student name :John	DOB : 1988-12-20	|	TUESDAY
***
*** COUNT ***
FRIDAY	2
SATURDAY	3
THURSDAY	2
TUESDAY	3
Done

 */
 