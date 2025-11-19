package PH1.pieces;

import PH1.utils.Position;

/**
 * Represents a Rook piece in the game of chess.
 */
public class Rook extends Piece {

    /**
     * Constructs a Rook with a specified color and starting position.
     * @param color The color of the piece ("white" or "black").
     * @param position The starting position of the piece on the board.
     */
    public Rook(String color, Position position) {
        super(color, position);
    }

    /**
     * Validates if a move to a new position is legal for a Rook.
     * A Rook moves horizontally or vertically through any number of unoccupied squares.
     * @param to The destination position for the move.
     * @param board The current state of the game board.
     * @return true if the move is valid, false otherwise.
     */
    @Override
    public boolean isValidMove(Position to, Piece[][] board) {
        int rowDiff = to.getRow() - position.getRow();
        int colDiff = to.getCol() - position.getCol();

        // Must move along a rank or file, not both.
        if (rowDiff != 0 && colDiff != 0) return false;

        // Check if the path to the destination is clear of other pieces.
        int stepRow = Integer.signum(rowDiff);
        int stepCol = Integer.signum(colDiff);
        int r = position.getRow() + stepRow;
        int c = position.getCol() + stepCol;

        while (r != to.getRow() || c != to.getCol()) {
            if (board[r][c] != null) return false; // Path is blocked.
            r += stepRow;
            c += stepCol;
        }

        // Destination square must be empty or contain an opponent's piece.
        return board[to.getRow()][to.getCol()] == null ||
               !board[to.getRow()][to.getCol()].getColor().equals(color);
    }
    
    /**
     * Gets the text symbol representing the Rook.
     * @return "wR" for a white Rook, "bR" for a black Rook.
     */
    @Override
    public String getSymbol(){
        return color.equals("white") ? "wR" : "bR";
    }
}