package PH1.pieces;

import utils.Position;

/**
 * Represents a Knight piece in the game of chess.
 */
public class Knight extends Piece {

    /**
     * Constructs a Knight with a specified color and starting position.
     * @param color The color of the piece ("white" or "black").
     * @param position The starting position of the piece on the board.
     */
    public Knight(String color, Position position) {
        super(color, position);
    }

    /**
     * Validates if a move to a new position is legal for a Rook.
     * A Knight moves in an L-shaped pattern, only checking the occupancy of the target position.
     * @param to The destination position for the move.
     * @param board The current state of the game board.
     * @return true if the move is valid, false otherwise.
     */
    @Override
    public boolean isValidMove(Position to, Piece[][] board) {
        int rowDiff = to.getRow() - position.getRow();
        int colDiff = to.getCol() - position.getCol();

        // rowDiff or colDiff is positive/negative 1, while the other is positive/negative 2.
        if((Math.abs(rowDiff) == 1 && Math.abs(colDiff) == 2) ||
        (Math.abs(rowDiff) == 2 && Math.abs(colDiff) == 1)) {
            // Destination square must be empty or contain an opponent's piece.
            return board[to.getRow()][to.getCol()] == null ||
                    !board[to.getRow()][to.getCol()].getColor().equals(color);
        }

        // Otherwise, the move is illegal.
        return false;
    }
    
    /**
     * Gets the text symbol representing the Knight.
     * @return "wN" for a white Knight, "bN" for a black Knight.
     */
    @Override
    public String getSymbol(){
        return color.equals("white") ? "wN" :"bN";
    }
}
