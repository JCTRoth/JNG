package jng.tests.tutor.suites;

import junit.framework.Test;
import junit.framework.TestSuite;

public class JngTestsuiteAllTutors {
	
	public static Test suite() {
		
		TestSuite suite = new TestSuite("All tutors tests for Jng");
		
		suite.addTest(jng.tests.tutor.suites.JngTestsuiteMinimalTutors.suite());
		suite.addTest(jng.tests.tutor.suites.JngTestsuiteExtended1Tutors.suite());
		suite.addTest(jng.tests.tutor.suites.JngTestsuiteExtended2Tutors.suite());
		suite.addTest(jng.tests.tutor.suites.JngTestsuiteExtended3Tutors.suite());
		
		return suite;
	}
}
