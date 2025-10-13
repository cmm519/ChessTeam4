package board;

import pieces.*;
import utils.Position;

/**
 * Represents the chess board and manages its state.
 * This class is responsible for creating the 8x8 grid, initializing the pieces
 * in their standard starting positions, handling piece movements, and displaying
 * the board's current state.
 */
public class Board {
    /**
     * A 2D array representing the 8x8 chessboard grid.
     * Each element holds a Piece object or is null if the square is empty.
     */
    private Piece[][] grid;

    /**
     * Constructs a new Board instance.
     * It initializes the 8x8 grid and calls the method to place all pieces
     * in their starting positions.
     */
    public Board() {
        grid = new Piece[8][8];
        initializeBoard();
    }

    /**
     * Sets up the board with all pieces in their standard initial positions.
     * This private helper method is called by the constructor. It places the
     * black and white pieces on their respective ranks.
     */
    private void initializeBoard() {
        String black = "black"; // Used to avoid typos and ensure consistency
        String white = "white"; // Lowercase for consistency with piece logic

        // --- Black Pieces ---
        grid[0][0] = new Rook(black, new Position("A8"));
        grid[0][1] = new Knight(black, new Position("B8"));
        grid[0][2] = new Bishop(black, new Position("C8"));
        grid[0][3] = new Queen(black, new Position("D8"));
        grid[0][4] = new King(black, new Position("E8"));
        grid[0][5] = new Bishop(black, new Position("F8"));
        grid[0][6] = new Knight(black, new Position("G8"));
        grid[0][7] = new Rook(black, new Position("H8"));
        // Black Pawns
        for (int col = 0; col < 8; col++) {
            char file = (char) ('A' + col);
            grid[1][col] = new Pawn(black, new Position(file + "7"));
        }

        // --- White Pieces ---
        grid[7][0] = new Rook(white, new Position("A1"));
        grid[7][1] = new Knight(white, new Position("B1"));
        grid[7][2] = new Bishop(white, new Position("C1"));
        grid[7][3] = new Queen(white, new Position("D1"));
        grid[7][4] = new King(white, new Position("E1"));
        grid[7][5] = new Bishop(white, new Position("F1"));
        grid[7][6] = new Knight(white, new Position("G1"));
        grid[7][7] = new Rook(white, new Position("H1"));
        // White Pawns
        for (int col = 0; col < 8; col++) {
            char file = (char) ('A' + col);
            grid[6][col] = new Pawn(white, new Position(file + "2"));
        }
    }

    /**
     * Retrieves the piece at a specified position on the board.
     * @param pos The {@link Position} object representing the square to check.
     * @return The {@link Piece} at the given position, or {@code null} if the square is empty.
     */
    public Piece getPiece(Position pos) {
        return grid[pos.getRow()][pos.getCol()];
    }

    /**
     * Moves a piece from a starting position to a destination position on the grid.
     * This method updates the grid and the piece's internal position.
     * It checks if a piece exists at the 'from' position before attempting to move.
     * @param from The starting {@link Position} of the piece.
     * @param to The destination {@link Position} for the piece.
     */
    public void movePiece(Position from, Position to) {
        Piece piece = getPiece(from);
        if (piece != null) {
            grid[to.getRow()][to.getCol()] = piece;
            grid[from.getRow()][from.getCol()] = null;
            piece.move(to);
        }
    }

    /**
     * Gets the entire board grid.
     * @return A 2D array of {@link Piece} objects representing the current state of the board.
     */
    public Piece[][] getGrid() {
        return grid;
    }

    /**
     * Prints a text-based representation of the current board state to the console.
     * The board is displayed from the perspective of the white player, with rank '1'
     * at the bottom. Empty squares are represented by "##".
     */
    public void display() {
        System.out.println("\n    A   B   C   D   E   F   G   H");
        System.out.println("  ---------------------------------");
        // Loop from row 0 to 7 to print board with white at the bottom
        for (int row = 0; row < 8; row++) {
            // Print rank numbers (8, 7, ..., 1)
            System.out.print((8 - row) + " |");
            for (int col = 0; col < 8; col++) {
                Piece piece = grid[row][col];
                System.out.print(" " + (piece != null ? piece.getSymbol() : "##") + " ");
            }
            System.out.println("| " + (8 - row));
        }
        System.out.println("  ---------------------------------");
        System.out.println("    A   B   C   D   E   F   G   H\n");
    }
}