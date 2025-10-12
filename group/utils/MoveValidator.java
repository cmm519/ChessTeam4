package utils;

import pieces.Piece;

public class MoveValidator {

    public static boolean isLegalMove(Piece piece, Position to, Piece[][] board) {
        if (piece == null || to == null) return false;

        // Prevent moving to a square occupied by same color
        Piece target = board[to.getRow()][to.getCol()];
        if (target != null && target.getColor().equals(piece.getColor())) return false;

        // Delegate to piece-specific logic
        return piece.isValidMove(to, board);
    }
}
