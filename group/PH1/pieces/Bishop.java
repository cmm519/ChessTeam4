package PH1.pieces;

import PH1.utils.Position;

/**
 * Represents a Bishop piece in the game of chess.
 */
public class Bishop extends Piece {

    /**
     * Constructs a Bishop with a specified color and starting position.
     * @param color The color of the piece ("white" or "black").
     * @param position The starting position of the piece on the board.
     */
    public Bishop(String color, Position position) {
        super(color, position);
    }

    /**
     * Validates if a move to a new position is legal for a Bishop.
     * A Bishop moves diagonally through any number of unoccupied squares.
     * @param to The destination position for the move.
     * @param board The current state of the game board.
     * @return true if the move is valid, false otherwise.
     */
    @Override
    public boolean isValidMove(Position to, Piece[][] board) {
        int rowDiff = to.getRow() - position.getRow();
        // Correction: Calculate raw difference before taking absolute value
        int colDiff = to.getCol() - position.getCol();

        // A bishop's move must be diagonal (same change in row and column).
        if (Math.abs(rowDiff) != Math.abs(colDiff)) {
            return false;
        }

        // Check if the path to the destination is clear of other pieces.
        int stepRow = Integer.signum(rowDiff);
        int stepCol = Integer.signum(colDiff);
        int r = position.getRow() + stepRow;
        int c = position.getCol() + stepCol;

        // Correction: Loop until either the row or col matches the destination.
        while (r != to.getRow() || c != to.getCol()) {
            if (board[r][c] != null) {
                return false; // Path is blocked.
            }
            r += stepRow;
            c += stepCol;
        }

        // Destination square must be empty or contain an opponent's piece.
        Piece destinationPiece = board[to.getRow()][to.getCol()];
        return destinationPiece == null || !destinationPiece.getColor().equals(this.color);
    }

   
}