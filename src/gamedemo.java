package gcdemos;

import java.util.Random;
import java.util.Scanner;

public class gamedemo{
	public static void main(String[] args) {
		Scanner scnr = new Scanner(System.in);
		System.out.println("Enter the number of Grids for the play ");
		int gridNumber= scnr.nextInt();
		int random1=randomNumberGenerator(gridNumber);
		int random2 = randomNumberGenerator(gridNumber);
		System.out.println("["+random1+" "+ random2+ "]");
		
		
		
		
	}
	public static int randomNumberGenerator(int rangeNumber)
	{
		Random rand =new Random();
		int d= rand.nextInt(rangeNumber);
		return d;
	}
}





