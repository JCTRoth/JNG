package jng.tests.tutor.suites;

import jng.tests.tutor.testcases.ParseLevelTests3Tutors;
import jng.tests.tutor.testcases.TestsExtended2Tutors;
import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;
import junit.framework.TestSuite;

public class JngTestsuiteExtended2Tutors {

	public static Test suite() {
		TestSuite suite = new TestSuite("Student tests for Jng - Extended 2");
		suite.addTest(new JUnit4TestAdapter(TestsExtended2Tutors.class));
		suite.addTest(new JUnit4TestAdapter(ParseLevelTests3Tutors.class));
		return suite;
	}

}
