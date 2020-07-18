package com.idm.service;

import java.io.CharArrayReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

public class Test {
	
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
		
		arraySort();

		int myArray[][] = { 
				{ 39, 27, 11, 42, 93, 20, 70, 64, 56, 24 }, 
				{ 10, 93, 91, 90, 47, 39, 29, 57, 87, 72 }, 
				{ 54, 78, 56, 89, 23, 41, 35, 12, 9,  67 }, 
				{ 24, 64, 20, 65, 56, 12, 35, 76, 40, 17 } 
			};
//		sortRowWise(myArray);
	}

	private static void sort2DArray(int[] array) {
		int temp;
		for (int i = 1; i < array.length; i++) {
			System.out.println("----------------Value of i: " + i + " Array Current Value: " + array[i]);
			for (int j = i; j > 0; j--) {
				System.out.println("\tValue of j: " + j + " - Sort Current Value: " + array[j]);
				if (array[j] < array[j - 1]) {
					temp = array[j];
					array[j] = array[j - 1];
					array[j - 1] = temp;
				}
			}
		}
		for (int i = 0; i < array.length; i++) {
			System.out.print(array[i] + " ");
		}
	}
	
	static int sortRowWise(int m[][]) { 
        // loop for rows of matrix 
        for (int i = 0; i < m.length; i++) { 
  
            // loop for column of matrix 
            for (int j = 0; j < m[i].length; j++) { 
  
                // loop for comparison and swapping 
                for (int k = 0; k < m[i].length - j - 1; k++) { 
                    if (m[i][k] > m[i][k + 1]) { 
                        // swapping of elements 
                        int t = m[i][k]; 
                        m[i][k] = m[i][k + 1]; 
                        m[i][k + 1] = t; 
                    } 
                } 
                
            } 
        } 
  
        System.out.println();
        // printing the sorted matrix 
        for (int i = 0; i < m.length; i++) { 
            for (int j = 0; j < m[i].length; j++) 
                System.out.print(m[i][j] + "\t"); 
            System.out.println(); 
        } 
  
        return 0; 
    } 
	
	static void arraySort() {
        int[][] array = new int[10][10];
        Random rand = new Random();
        
        // Assign random integer to a 2D array
        System.out.println("\n10x10 Array of Integer:\n");
        for(int i = 0; i < array.length; i++) {
            for(int j = 0; j < array[i].length; j++) {
                array[i][j] = rand.nextInt(100);
                System.out.print(array[i][j] + "\t");
            }
            System.out.println();
        }

        System.out.println("\nSorted 10x10 Array of Integer:\n");
        int small, row = 0, col = 0, z;
        // loop for rows
        for(int k = 0; k < array.length; k++) {
        	System.out.println("row: " + Arrays.toString(array[k]));
        	// loop for column
        	for(int p = 0; p < array[k].length; p++) {
                small = array[k][p];
                System.out.println("curr col: " + small);
                
                // loop for comparison and swapping
                for(int i = k; i < array.length; i++) {
                	System.out.println("3rd loop: " + Arrays.toString(array[i]));
                    if(i == k) {
                    	z = p + 1;
                    } else {
                    	z = 0;
                    }
                    System.out.println("what is z? " + z);
                    for(;z < array[i].length; z++) {
                    	System.out.println("compare to curr col: " + array[i][z]);
                        if(array[i][z] <= small) {
                            small = array[i][z];
                            row = i;
                            col = z;
                        }
                    }
                }
                array[row][col] = array[k][p];
                array[k][p] = small;
                System.out.print(array[k][p] + "\t\n----------------\n");
            }
            System.out.println();
        }
	}
}
