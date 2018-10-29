

	public class Cell {

		private boolean isBomb;
		private boolean isPublic;
		private boolean isFlagged;
		private int adjacentBombs;
		
		public Cell() {
			this.isBomb = false;
			this.isPublic = false;
			this.setFlagged(false);
			this.adjacentBombs = 0;
		}
		
		public void revealCell() {
			if(isPublic) {
				System.out.println("That cell has already been revealed.");
			}
			if (!(isPublic) && (!isBomb)) {
				isPublic = true;
			}
			
		}

		public boolean isFlagged() {
			return isFlagged;
		}

		public void setFlagged(boolean isFlagged) {
			this.isFlagged = isFlagged;
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
			return "I am a bomb.";
		}
		public String getDisplayString(Cell[][] arr) {
	        for (Cell[] c : arr) {
	            if(!isPublic) {
	                return "|_|";
	            }
	            if (isFlagged) {
	            	return "|?|";
	            }
	            if (isBomb) {
	                return "|B|";
	            }
	            return "|" + Integer.toString(adjacentBombs) + "|";
	        }
	        
	        return "Make your next selection.";
	}

}
