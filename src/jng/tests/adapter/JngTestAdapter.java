package jng.tests.adapter;

import jng.tests.adapter.interfaces.IJngTestAdapterExtended1;
import jng.tests.adapter.interfaces.IJngTestAdapterExtended2;
import jng.tests.adapter.interfaces.IJngTestAdapterExtended3;
import jng.tests.adapter.interfaces.IJngTestAdapterMinimal;

/**
 * Implement this class to link your generated adapters to the test suites.
 */
public class JngTestAdapter {

	public static IJngTestAdapterMinimal getMinimal(){
		return new JngTestAdapterMinimal();
	}
	
	public static IJngTestAdapterExtended1 getExtended1(){
		return new JngTestAdapterExtended1();
	}
	
	public static IJngTestAdapterExtended2 getExtended2(){
		return new JngTestAdapterExtended2();
	}
	
	public static IJngTestAdapterExtended3 getExtended3(){
		return new JngTestAdapterExtended3();
	}
}
