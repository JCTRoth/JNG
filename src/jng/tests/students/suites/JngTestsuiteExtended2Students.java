package jng.tests.students.suites;

import jng.tests.students.testcases.ParseLevelTests3Students;
import jng.tests.students.testcases.TestsExtended2Students;
import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;
import junit.framework.TestSuite;

public class JngTestsuiteExtended2Students {

	public static Test suite() {
		TestSuite suite = new TestSuite("Student tests for Jng - Extended 2");
		suite.addTest(new JUnit4TestAdapter(TestsExtended2Students.class));
		suite.addTest(new JUnit4TestAdapter(ParseLevelTests3Students.class));
		return suite;
	}
}
