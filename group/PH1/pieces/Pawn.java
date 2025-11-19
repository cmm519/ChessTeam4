package PH1.pieces;

import PH1.utils.Position;

public class Pawn extends Piece {
    public Pawn(String color, Position position) {
        super(color, position);
    }
    //TODO en passante and promotion
    @Override
    public boolean isValidMove(Position to, Piece[][] board) {
        int dir = color.equals("white") ? -1 : 1;
        int startRow = color.equals("white") ? 6 : 1;

        int rowDiff = to.getRow() - position.getRow();
        int colDiff = Math.abs(to.getCol() - position.getCol());

        // Forward move
        if (colDiff == 0) {
            if (rowDiff == dir && board[to.getRow()][to.getCol()] == null) return true;
            if (position.getRow() == startRow && rowDiff == 2 * dir && board[to.getRow()][to.getCol()] == null) return true;
        }

        // Diagonal capture
        if (colDiff == 1 && rowDiff == dir && board[to.getRow()][to.getCol()] != null &&
            !board[to.getRow()][to.getCol()].getColor().equals(color)) return true;

        return false;
    }
    @Override
    public String getSymbol(){
        return color.equals("white") ? "wp" :"bp";
    }
}
