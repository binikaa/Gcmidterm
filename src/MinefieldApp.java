import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;
public class MinefieldApp {

	public static void main(String[] args) {
		
		Scanner scnr = new Scanner(System.in);
		boolean gameOver = false;
		
		// Ask for the size of the minefield
		System.out.println("Welcome to Minesweeper!");
		int choice = getBoard(scnr);
		

		// Ask for the size of the Minefield from player
		
		System.out.println("Welcome to Minesweeper! \nEnter the size of the board: ");
		//int choice = getValidInt(scnr);// method to validate player input 
		
		Cell[][] board = new Cell[choice][choice]; // create cell object 2-d array  and store it in board variable and initialize it with choice


		populateBoard(board); // method to put all cell objects in board

		// randomly distribute bombs throughout
		setBombs(board, choice);
		
		// calculates all adjacent bombs
		for (int i = 0; i < board.length; i++) {// calculate nearby bombs for every cell object 
			for (int n = 0; n < board.length; n++) {
				calcAdjBombs(board, i, n);
			}
		}
		
		while(!gameOver) {
			
			displayBoard(board);
			String action = getAction(scnr);
			
			if (action.equalsIgnoreCase("reveal")) {
				System.out.println("Enter the row of the cell you'd like to reveal: (Enter 0-" + (choice-1) + ")");
				int row = getCoordinates(choice, scnr);
				System.out.println("Enter the column of the cell you'd like to reveal: (Enter 0-" + (choice-1) + ")");
				int col = getCoordinates(choice, scnr);
				
				if (board[row][col].isFlagged()) {
					System.out.println("That cell has been flagged!");
				}
				if (board[row][col].isBomb() && !board[row][col].isFlagged()) {
					gameOver(board);
					break;
				}
				clearZeros(board, row, col);
				
			}
			
			if (action.equalsIgnoreCase("flag")) {
				
				System.out.println("Enter the row of the cell you'd like to flag: (Enter 0-" + (choice-1) + ")");
				int row =  getCoordinates(choice, scnr);
				System.out.println("Enter the column of the cell you'd like to flag: (Enter 0-" + (choice-1) + ")");
				int col =  getCoordinates(choice, scnr);
				
				flagCell(board, row, col);
			}
			
			if (action.equalsIgnoreCase("remove")) {
				
				System.out.println("Enter the row of the flag you'd like to remove: (Enter 0-" + (choice-1) + ")");
				int row =  getCoordinates(choice, scnr);
				System.out.println("Enter the column of the flag you'd like to remove: (Enter 0-" + (choice-1) + ")");
				int col =  getCoordinates(choice, scnr);
				
				removeFlag(board, row, col);
			}
			
			
			
			
			if(victoryCondition(board)) {
			System.out.println("You win!");
			displayBoard(board);
			gameOver = true;
			}
		
		}
		System.out.println("Thanks for playing!");
	}
	public static void removeFlag(Cell[][] arr, int i, int n) {
		if (arr[i][n].isFlagged()) {
			arr[i][n].setFlagged(false);
			arr[i][n].setPublic(false);
			System.out.println("That cell has been un-flagged.");
			return;
		}
		
		System.out.println("That cell has not been flagged.");
	}
	
	public static int getBoard(Scanner scnr) {
		System.out.println("How many rows and columns should the (square) board have?\nFor best results, "
				+ "between 3 and 25 is recommended.");
		
		int num = getValidInt(scnr);
		while (num < 3 || num > 25) {
			System.out.println("Between 3 and 25 rows/columns is recommended. Please choose from that range: ");
			num = getValidInt(scnr);
		}
		return num;
	}
	
	public static int getCoordinates(int choice, Scanner scnr) {
		
		int num = getValidInt(scnr);
		while (num < 0 || (num > (choice-1))) {
			System.out.println("Please enter an integer between 0 and " + (choice-1));
			num = getValidInt(scnr);
		}
		
		return num;
		
	}
	
	public static int getValidInt(Scanner scnr) {
		
		
		try {
			int num = scnr.nextInt();
			return num;
		} catch (InputMismatchException e) {
			System.out.println("Invalid entry. Please enter an integer.");
			scnr.next();
			return getValidInt(scnr);
		}
		
	}
	
	public static String getAction(Scanner scnr) {
		System.out.println("\nDo you want to reveal a cell, flag a bomb, or remove a flag? (Enter reveal||flag||remove)");
		String userA = scnr.nextLine();
		
		while (!userA.equalsIgnoreCase("reveal") && !userA.equalsIgnoreCase("flag") && !userA.equalsIgnoreCase("remove")) {
			System.out.println("You must choose to either reveal a cell or flag a bomb. (Enter reveal||flag||remove)");
			userA = scnr.nextLine();
		}
		
		return userA;
	}
	
	public static void flagCell(Cell[][] arr, int i, int n) {
		arr[i][n].setFlagged(true);
		arr[i][n].setPublic(true);
	}
	
	public static void clearZeros(Cell[][] arr, int i, int n) {
		
		// Base case one: this isn't a valid coordinate
		if (i < 0 || n < 0 || i >= arr.length || n >= arr[i].length) {
			return;
		}
		
		Cell thisCell = arr[i][n];
		int numAdjBombs = thisCell.getAdjacentBombs();
		
		// Base case two: this cell is already public
		if (thisCell.isPublic()) {
			return;
		}
		
		// Base case three: this cell has bomb neighbors
		if (numAdjBombs > 0) {
			thisCell.setPublic(true);
			return;
		}
		
		
		// Recursive case: reveal this cell and neighbors. 
		thisCell.setPublic(true);
		clearZeros(arr, i-1, n); // North
		clearZeros(arr, i+1, n); // South
		clearZeros(arr, i, n+1); // East
		clearZeros(arr, i, n-1); // West
		clearZeros(arr, i-1, n+1); // North East
		clearZeros(arr, i-1, n-1); // North West
		clearZeros(arr, i+1, n+1); // South East
		clearZeros(arr, i+1, n-1); // South West
	}
	
	public static boolean victoryCondition(Cell[][] arr) {
		for (int j = 0; j < arr.length; j++) {
			for (int p = 0; p < arr.length; p++) {
				if(!arr[j][p].isFlagged() && arr[j][p].isBomb()) {
					return false;
				}
				if (arr[j][p].isFlagged() && !arr[j][p].isBomb()) {
					return false;
				}
			}
		}
		return true;
	}
	
	public static void displayBoard(Cell[][] arr) {
		for (int i = 0; i < arr.length; i++) {
			for (int n = 0; n < arr.length; n++) {
				System.out.print(arr[i][n].getDisplayString(arr));
			}
			System.out.println();
		}
	}
	
	public static void gameOver(Cell[][] arr) {
		System.out.println("BOOM! Game over!");
		
		for(int i = 0; i < arr.length; i++) {
			for (int n = 0; n < arr.length; n++) {
				if (arr[i][n].isBomb()) {
					arr[i][n].setPublic(true);
				}
				System.out.print(arr[i][n].getDisplayString(arr));
			}
			System.out.println();
		}
		
	}

	public static void setBombs(Cell[][] arr, int num) {
		

		for (int i = 0; i < (num + (num / 2)); i++) {
			int r = randomnumber.randomNum(0, num);
			int q = randomnumber.randomNum(0, num);
			if (arr[r][q].isBomb()) {
				i--;
			}
			arr[r][q].setBomb(true);
			
		}

	}

	public static void populateBoard(Cell[][] arr) {// creating arr array to store all cell objects 

		for (int i = 0; i < arr.length; i++) {
			for (int n = 0; n < arr.length; n++) {
				Cell c = new Cell();
				arr[i][n] = c;
			}
		}
  
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
