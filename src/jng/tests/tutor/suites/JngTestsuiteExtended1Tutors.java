package jng.tests.tutor.suites;

import jng.tests.tutor.testcases.ParseLevelTests2Tutors;
import jng.tests.tutor.testcases.TestsExtended1Tutors;
import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;
import junit.framework.TestSuite;

public class JngTestsuiteExtended1Tutors {

	public static Test suite() {
		TestSuite suite = new TestSuite("Student tests for Jng - Extended 1");
		suite.addTest(new JUnit4TestAdapter(TestsExtended1Tutors.class));
		suite.addTest(new JUnit4TestAdapter(ParseLevelTests2Tutors.class));
		return suite;
	}

}
