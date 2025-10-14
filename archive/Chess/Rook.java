
public class Rook extends Piece{

	public String color;
	
	public Rook(String color){
		this.color = color;
	}
	
	public boolean validateMove(Piece[][] board, int currentRow, int currentCol, int newRow, int newCol) {
		//Rook can move any number of squares along a rank or file, but cannot leap over other pieces.
		//So first check if it is moving along a rank or file
		//If it is not, return false
		//If it is, check if there are any pieces in the way
		//If there are, return false
		//If there are not, return true
		//If it is not moving, return false
		
		
		//System.out.println("currentRow: " + currentRow + " currentCol: " + currentCol + " newRow: " + newRow + " newCol: " + newCol);
		if(currentRow == newRow && currentCol == newCol){
			//Did not move
			return false;
		}
		if(currentRow != newRow && currentCol != newCol){
			//Did not move along one rank/file
			return false;
		}
		
		//First I will assumed the Rook is moving along the rows.
		int offset;
		
		if(currentRow != newRow){
			if(currentRow < newRow){
				offset = 1;
			}else{
				offset = -1;
			}
			
			for(int x = currentRow + offset; x != newRow; x += offset){
				//Go from currentRow to newRow, and check every space
				if(board[x][currentCol] != null){
					//System.out.println("1 " + x);
					return false;
				}
			}
		}
	
		//Now do the same for columns
		if(currentCol != newCol){
			if(currentCol < newCol){
				offset = 1;
			}else{
				offset = -1;
			}
			
			for(int x = currentCol + offset; x != newCol; x += offset){
				//Go from currentCol to newCol, and check every space
				if(board[currentRow][x] != null){
					//System.out.println("2");
					return false;
				}
			}
		}
		
		return true;
	}
	
	public String getColor(){
		return this.color;
	}
	
	public String toString(){
		return color.charAt(0) + "R";//First letter of color + R for Rook
		
	}

}
