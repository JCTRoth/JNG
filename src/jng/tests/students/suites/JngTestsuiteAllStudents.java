package jng.tests.students.suites;

import junit.framework.Test;
import junit.framework.TestSuite;

public class JngTestsuiteAllStudents {
	
	public static Test suite() {
		
		TestSuite suite = new TestSuite("All student tests for Jng");
		
		suite.addTest(jng.tests.students.suites.JngTestsuiteMinimalStudents.suite());
		suite.addTest(jng.tests.students.suites.JngTestsuiteExtended1Students.suite());
		suite.addTest(jng.tests.students.suites.JngTestsuiteExtended2Students.suite());
		suite.addTest(jng.tests.students.suites.JngTestsuiteExtended3Students.suite());
		
		return suite;
	}
}
