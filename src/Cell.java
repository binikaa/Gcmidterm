

	public class Cell {

		private boolean isBomb;
		private boolean isPublic;
		private int adjacentBombs;
		
		public Cell() {
			this.isBomb = false;
			this.isPublic = false;
			this.adjacentBombs = 0;
		}
		
		public String revealCell() {
			if(isPublic) {
				System.out.println("This cell has already been revealed!");
			}
			else if (isBomb) {
				return "BOOM! GAME OVER\n" + gameOver();
			}
			isPublic = true;
			return "";
		}
		
		public String gameOver() {
			if (isBomb) {
				return "|B|";
			}
			if (isPublic) {
				return "|" + Integer.toString(adjacentBombs) + "|";
			}
			return "|_|";
		}

		
		public boolean isBomb() {
			return isBomb;
		}
		public void setBomb(boolean isBomb) {
			this.isBomb = isBomb;
		}
		public boolean isPublic() {
			return isPublic;
		}
		public void setPublic(boolean isPublic) {
			this.isPublic = isPublic;
		}
		public int getAdjacentBombs() {
			if(isBomb) {
				return -1;
			}
			return adjacentBombs;
		}
		public void setAdjacentBombs(int adjacentBombs) {
			this.adjacentBombs = adjacentBombs;
		}
		
		@Override
		public String toString() {
			return "This is a bomb ";
		}
		public String getDisplayString(Cell[][] arr) {
	        for (Cell[] c : arr) {
	            if(isPublic) {
	                return "|_|";
	            }
	            if (isBomb) {
	                return "|*|";
	            }
	            return "|" + Integer.toString(adjacentBombs) + "|";
	        }
	        
	        return "Make your next selection.";
	}

}
