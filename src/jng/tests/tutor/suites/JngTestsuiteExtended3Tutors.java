package jng.tests.tutor.suites;

import jng.tests.tutor.testcases.TestsExtended3Tutors;
import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;
import junit.framework.TestSuite;

public class JngTestsuiteExtended3Tutors {

	public static Test suite() {
		TestSuite suite = new TestSuite("Student tests for Jng - Extended 3");
		suite.addTest(new JUnit4TestAdapter(TestsExtended3Tutors.class));
		return suite;
	}

}
