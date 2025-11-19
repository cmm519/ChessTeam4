package PH1.utils;

public class Position {
    private int row;
    private int col;

    public Position(String notation) {
        // Convert the notation to uppercase to handle both "b7" and "B7"
        String upperNotation = notation.toUpperCase();

        // Perform calculations on the uppercase string
        this.col = upperNotation.charAt(0) - 'A';
        this.row = 8 - Character.getNumericValue(upperNotation.charAt(1));
    }
public Position(int row, int col) {
    this.row = row;
    this.col = col;
}
    public int getRow() { 
        return row; 
    }

    public int getCol() { 
        return col; 
    }
}