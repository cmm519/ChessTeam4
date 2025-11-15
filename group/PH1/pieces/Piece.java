package PH1.pieces;

import utils.Position;

/**
 * Represents the abstract concept of a chess piece.
 * This class provides common properties like color and position,
 * and declares abstract methods for piece-specific behavior.
 */
public abstract class Piece {
    /** The color of the piece ("white" or "black"). */
    protected String color;

    /** The current position of the piece on the board. */
    protected Position position;

    /**
     * Constructs a new Piece with a given color and position.
     *
     * @param color The color of the piece.
     * @param position The initial position of the piece.
     */
    public Piece(String color, Position position) {
        this.color = color;
        this.position = position;
    }

    /**
     * Determines if a move to a destination square is valid.
     * This method must be implemented by each concrete piece class.
     *
     * @param to The destination position of the move.
     * @param board The current state of the game board.
     * @return true if the move is valid according to the piece's rules, false otherwise.
     */
    public abstract boolean isValidMove(Position to, Piece[][] board);

    /**
     * Updates the piece's internal position to a new position.
     *
     * @param to The new position for the piece.
     */
    public void move(Position to) {
        this.position = to;
    }

    /**
     * Gets the color of the piece.
     *
     * @return The color string ("white" or "black").
     */
    public String getColor() {
        return color;
    }

    /**
     * Gets the current position of the piece.
     *
     * @return The Position object representing the piece's location on the board.
     */
    public Position getPosition() {
        return position;
    }

     /**
     * Gets the text symbol representing the Bishop.
     * @return "wB" for a white Bishop, "bB" for a black Bishop.
     */
    
    public String getSymbol() {
        return color.equals("white") ? "wB" : "bB";
    }
}
         