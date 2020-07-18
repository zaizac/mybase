/**
 * 
 */
package com.idm.service;

/**
 * @author mary.jane
 *
 */
public class SwitchTest {

	int maxSwitch = 32;
	
	boolean[] switches = new boolean[maxSwitch];
	
	Boolean get(int switchNumber) {
		if(switchNumber >= maxSwitch) {
			return null;
		}
		return switches[switchNumber];
	}
	
	Boolean set(int switchNumber) {
		if(switchNumber >= maxSwitch) {
			return null;
		}
		switches[switchNumber] = !switches[switchNumber];
		return switches[switchNumber];
	}
	

	public static void main(String[] args) {
		
		Test test = new Test();
		System.out.println(test.set(31));
		System.out.println(test.set(31));
		System.out.println(test.set(30));
		System.out.println(test.get(30));
		System.out.println(test.set(30));
		System.out.println(test.get(30));
		System.out.println(test.set(32));
	}
	
}
