package PH1.pieces;

import utils.Position;

/**
 * Represents a Queen piece in the game of chess.
 */
public class Queen extends Piece {

    /**
     * Constructs a Queen with a specified color and starting position.
     * @param color The color of the piece ("white" or "black").
     * @param position The starting position of the piece on the board.
     */
    public Queen(String color, Position position) {
        super(color, position);
    }

    /**
     * Validates if a move is legal for a Queen.
     * A Queen moves like a Rook (horizontally or vertically) or a Bishop (diagonally)
     * through any number of unoccupied squares.
     * @param to The destination position for the move.
     * @param board The current state of the game board.
     * @return true if the move is valid, false otherwise.
     */
    @Override
    public boolean isValidMove(Position to, Piece[][] board) {
        int rowDiff = to.getRow() - position.getRow();
        int colDiff = to.getCol() - position.getCol();

        boolean isStraight = (rowDiff == 0 || colDiff == 0);
        boolean isDiagonal = (Math.abs(rowDiff) == Math.abs(colDiff));

        // A Queen's move must be either straight or diagonal.
        if (!isStraight && !isDiagonal) {
            return false;
        }

        // --- Path Clearing Check ---
        int stepRow = Integer.signum(rowDiff);
        int stepCol = Integer.signum(colDiff);
        int r = position.getRow() + stepRow;
        int c = position.getCol() + stepCol;

        // Iterate along the path until the destination square.
        while (r != to.getRow() || c != to.getCol()) {
            if (board[r][c] != null) {
                return false; // Path is blocked.
            }
            r += stepRow;
            c += stepCol;
        }

        // --- Destination Square Check ---
        // The destination must be empty or contain an opponent's piece.
        Piece destinationPiece = board[to.getRow()][to.getCol()];
        return destinationPiece == null || !destinationPiece.getColor().equals(this.color);
    }

    /**
     * Gets the text symbol representing the Queen.
     * @return "wQ" for a white Queen, "bQ" for a black Queen.
     */
    @Override
    public String getSymbol() {
        return color.equals("white") ? "wQ" : "bQ";
    }
}