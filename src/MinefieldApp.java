import java.util.Scanner;
import java.util.Arrays;
import java.util.concurrent.RecursiveAction;

public class MinefieldApp {

	public static void main(String[] args)
	{
		// Ask for the size of the minefield
			int numOfBombs;	
		System.out.println("Enter the the size of mine field you want to play? \n");
		System.out.println("Make your choice from number 1 to 4");
		System.out.println("1 - To play with 4 * 4 grid ");
		System.out.println("2 - To play with 8 * 8 grid ");
		System.out.println("3 - To play with 16 * 16 grid ");
		System.out.println("4 - To play with Customzed grid ");
		Scanner scr = new Scanner(System.in);
		int choice = scr.nextInt();
		if(choice == 4)
		{
			System.out.println("Enter the number,you want for your grid");
			int userChoice=scr.nextInt();
			System.out.println("Enter the number of Bombs you want? Must enter between 1 and " + userChoice * userChoice);
			int userBombChoice=scr.nextInt();
			if(userBombChoice < 0 || userBombChoice > (userChoice * userChoice - 1) )
				System.out.println("Invalid choice");
			
		}
		
		
		switch(choice)
		{
		case 1 :numOfBombs= 5;break;
		case 2 :numOfBombs =10;break;
		case 3 :numOfBombs= 32;break;			
		}
		
		public static Cell[][] board = new Cell[3][3];

			
			

			for (int i = 0; i < board.length; i++) {
				for (int n = 0; n < board.length; n++) {
					Cell c = new Cell();
					board[i][n] = c;
				}
			}
			
			board[0][0].setBomb(true);
//			board[0][1].setBomb(true);
//			board[0][2].setBomb(true);
			
			for (int i = 0; i < board.length; i++) {
				for (int n = 0; n < board.length; n++) {
					calcAdjBombs(board, i, n);
				}
			}
			
			
//			for (int i = 0; i < board.length; i++) {
//				for (int n = 0; n < board.length; n++) {
//					System.out.print(board[i][n].getAdjacentBombs() + " ");
//				}
//				System.out.println();
//			}
			
			//what coordinates?
			// they say 1, 1
			System.out.println(board[1][1].revealCell());
			
			for (int i = 0; i < board.length; i++) {
				for (int n = 0; n < board.length; n++) {
					System.out.print(getDisplayString(board, i, n));
				}
				System.out.println();
			}
			

		
		}
		public static String getDisplayString(Cell[][] arr, int i, int n) {
			if(!arr[i][n].isPublic()) {
				return "|_|";
			}
			if (arr[i][n].isBomb()) {
				return "|*|";
			}
			return "|" + arr[i][n].getAdjacentBombs() + "|";
		}
		
		
		public static void calcAdjBombs(Cell[][] board, int i, int n) {
			int bombCount = 0;
			
			int rUpper = Math.min(i + 2, board.length);
			int rLow = Math.max(i - 1, 0);
			
			int cUpper = Math.min(n + 2, board.length);
			int cLow = Math.max(n - 1, 0);
			
			
			for (int r = rLow; r < rUpper; r++) {
				for (int c = cLow; c < cUpper; c++) {
					if (board[r][c].isBomb()) {
						bombCount++;
					}
				
				}
			
			}
			
			
			board[i][n].setAdjacentBombs(bombCount);
		
		}
		
		
		

	}


