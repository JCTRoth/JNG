package jng.tests.students.suites;

import jng.tests.students.testcases.ParseLevelTests1Students;
import jng.tests.students.testcases.TestsMinimalStudents;
import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;
import junit.framework.TestSuite;

public class JngTestsuiteMinimalStudents {

	public static Test suite() {
		TestSuite suite = new TestSuite("Student tests for Jng - Minimal");
		suite.addTest(new JUnit4TestAdapter(TestsMinimalStudents.class));
		suite.addTest(new JUnit4TestAdapter(ParseLevelTests1Students.class));
		return suite;
	}
}
