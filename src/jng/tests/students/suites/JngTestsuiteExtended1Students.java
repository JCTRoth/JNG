package jng.tests.students.suites;

import jng.tests.students.testcases.ParseLevelTests2Students;
import jng.tests.students.testcases.TestsExtended1Students;
import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;
import junit.framework.TestSuite;

public class JngTestsuiteExtended1Students {

	public static Test suite() {
		TestSuite suite = new TestSuite("Student tests for Jng - Extended 1");
		suite.addTest(new JUnit4TestAdapter(TestsExtended1Students.class));
		suite.addTest(new JUnit4TestAdapter(ParseLevelTests2Students.class));
		return suite;
	}
}
