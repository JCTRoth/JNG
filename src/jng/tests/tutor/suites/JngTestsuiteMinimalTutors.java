package jng.tests.tutor.suites;

import jng.tests.tutor.testcases.ParseLevelTests1Tutors;
import jng.tests.tutor.testcases.TestsMinimalTutors;
import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;
import junit.framework.TestSuite;

public class JngTestsuiteMinimalTutors {

	public static Test suite() {
		TestSuite suite = new TestSuite("Student tests for Jng - Minimal");
		suite.addTest(new JUnit4TestAdapter(TestsMinimalTutors.class));
		suite.addTest(new JUnit4TestAdapter(ParseLevelTests1Tutors.class));
		return suite;
	}

}
