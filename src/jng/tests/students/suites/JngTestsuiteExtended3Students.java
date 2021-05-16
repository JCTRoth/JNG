package jng.tests.students.suites;

import jng.tests.students.testcases.TestsExtended3Students;
import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;
import junit.framework.TestSuite;

public class JngTestsuiteExtended3Students {

	public static Test suite() {
		TestSuite suite = new TestSuite("Student tests for Jng - Extended 3");
		suite.addTest(new JUnit4TestAdapter(TestsExtended3Students.class));
		return suite;
	}
}
