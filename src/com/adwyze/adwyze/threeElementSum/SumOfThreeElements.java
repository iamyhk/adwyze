package com.adwyze.adwyze.threeElementSum;

import java.util.Scanner;

public class SumOfThreeElements {
	void sortArray(int[] inputArray,int length) {
		int i,j,min,temp;
		for(i=0;i<length;i++){
			min = i;
			for(j=i+1;j<length;j++){
				if(inputArray[j]<inputArray[min])
					min = j;
			}
			//Swap elements
			temp = inputArray[min];
			inputArray[min] = inputArray[i];
			inputArray[i] = temp;
		}
	}
	boolean searchElements(int[] inputArray,int length,int sum){
		int first, last;
		for (int i = 0; i < length - 2; i++)
	    {
	 
	        first = i + 1; // index of the first element in the remaining elements
	        last = length-1; // index of the last element
	        while (first < last)
	        {
	            if( inputArray[i] + inputArray[first] + inputArray[last] == sum)
	            {
	                System.out.println("Triplet is: "+inputArray[i]+" "+inputArray[first]+" "+inputArray[last]);
	                return true;
	            }
	            else if (inputArray[i] + inputArray[first] + inputArray[last] < sum)
	                first++;
	            else 
	                last--;
	        }
	    }
		return false;
	}
	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		try{
			System.out.print("Enter Elements Sum: ");
			int sum = sc.nextInt();
			System.out.println("\nEnter Array Length");
			int inputaArrayLength = sc.nextInt();
			int[] inputArray = new int[inputaArrayLength];//{12, 3, 4, 1, 6, 9};//
			for(int i=0;i<inputaArrayLength;i++){
				System.out.print("Enter "+i+"th element of array: ");
				inputArray[i] = sc.nextInt();
				System.out.println();
			}
			SumOfThreeElements obj = new SumOfThreeElements();
			obj.sortArray(inputArray,inputaArrayLength);
			/*
			 * System.out.println("Sorted array is :");
			for(int i=0;i<inputArray.length;i++){
				System.out.print(inputArray[i]+" ");
			}*/
			boolean result = obj.searchElements(inputArray, inputaArrayLength, sum);
		    if(!result)
		    	System.out.println("No Triplets found");
		}catch(java.util.InputMismatchException e){
			System.out.println("Please enter a valid input");
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
}
