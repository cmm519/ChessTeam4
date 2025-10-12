package utils;

public class Position {
    private int row;
    private int col;

    public Position(String notation) {
        this.col = notation.charAt(0) - 'A';
        this.row = 8 - Character.getNumericValue(notation.charAt(1));
    }

    public int getRow() { return row; }
    public int getCol() { return col; }
}
