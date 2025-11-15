package PH1.pieces;

import utils.Position;

public class King extends Piece {
    public King (String color, Position position) {
        super(color, position);
    }

    @Override
    public boolean isValidMove(Position to, Piece[][] board) {

        int rowDiff = to.getRow() - position.getRow();
        int colDiff = Math.abs(to.getCol() - position.getCol());

        if(Math.abs(rowDiff) != Math.abs(colDiff)) return false;

        int stepRow = Integer.signum(rowDiff);
        int stepCol = Integer.signum(colDiff);
        int r = position.getRow() + stepRow;
        int c = position.getCol() + stepCol;

        while (r != to.getRow() && c != to.getCol()){
            if(board[r][c] != null) return false;
            r += stepRow;
            c += stepCol;
        }

        return board[to.getRow()][to.getCol()] == null ||
                !board[to.getRow()][to.getCol()].getColor().equals(color);
    }
    @Override
    public String getSymbol(){
        return color.equals("white") ? "wK" :"bK";
    }
}