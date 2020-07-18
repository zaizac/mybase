/**
 * 
 */
package com.idm.service;

import java.util.Random;

/**
 * @author mary.jane
 *
 */
public class Array2DTest {

	public static void main(String[] args) {
		int[][] array = new int[10][10];
		Random rand = new Random();
		
		System.out.println("\n10x10 Array of Integer:\n");
		// Generate random 10x10 2D Array Integers
		for (int row = 0; row < array.length; row++) {
			for (int col = 0; col < array[row].length; col++) {
				// Random numbers between 0 - 100
				array[row][col] = rand.nextInt(100); 
				System.out.print(array[row][col] + "\t");
			}
			System.out.println();
		}
		
		System.out.println("\nSorted 10x10 Array of Integer:\n");
		int rowNew = 0, colNew = 0, val, idx;
		
		// Loop 2D Array Row
		for (int row = 0; row < array.length; row++) {
			// Loop 2D Array Column
			for (int col = 0; col < array[row].length; col++) {
				// Current value
				val = array[row][col];

				// loop for comparison and swapping
				for (int x = row; x < array.length; x++) {
					if(x == row) {
						idx = col + 1;
					} else {
						idx = 0; 
					}
					for (; idx < array[x].length; idx++) {
						if(array[x][idx] <= val) {
							val = array[x][idx];
							rowNew = x;
							colNew = idx;
						}
					}
				}
				array[rowNew][colNew] = array[row][col];
				array[row][col] = val;
				System.out.print(array[row][col] + "\t");
			}
			System.out.println();
		}
		
	}
	
}
