package utils;

import board.Board;
import pieces.King;
import pieces.Piece;

public class MoveValidator {

    /**
     * Finds the position of a king of a specific color.
     * @param kingColor The color of the king to find.
     * @param board The board to search on.
     * @return The Position of the king, or null if not found.
     */
    private static Position findKingPosition(String kingColor, Piece[][] board) {
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                Piece p = board[r][c];
                if (p != null && p.getColor().equals(kingColor) && p instanceof King) {
                    return p.getPosition();
                }
            }
        }
        return null;
    }

    /**
     * Checks if a specific square is under attack by an opponent.
     * @param square The position of the square to check.
     * @param attackerColor The color of the attacking player.
     * @param board The current board state.
     * @return true if the square is under attack, false otherwise.
     */
    public static boolean isSquareUnderAttack(Position square, String attackerColor, Piece[][] board) {
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                Piece piece = board[r][c];
                if (piece != null && piece.getColor().equals(attackerColor)) {
                    if (piece.isValidMove(square, board)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    /**
     * Checks if a king is currently in check.
     * @param kingColor The color of the king to check.
     * @param board The current board state.
     * @return true if the king is in check, false otherwise.
     */
    public static boolean isKingInCheck(String kingColor, Piece[][] board) {
        Position kingPos = findKingPosition(kingColor, board);
        if (kingPos == null) return false; // Should not happen
        
        String attackerColor = kingColor.equals("white") ? "black" : "white";
        return isSquareUnderAttack(kingPos, attackerColor, board);
    }

    /**
     * Determines if a player has any legal moves. A move is legal if it does not
     * result in the player's own king being in check.
     * @param playerColor The color of the player to check.
     * @param board The current Board object.
     * @return true if the player has at least one legal move, false otherwise.
     */
    public static boolean hasAnyLegalMoves(String playerColor, Board board) {//Straight from GPT, did not trace this nonsense but it works 
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                Piece piece = board.getGrid()[r][c];
                if (piece != null && piece.getColor().equals(playerColor)) {
                    // Check all possible destination squares for this piece
                    for (int to_r = 0; to_r < 8; to_r++) {
                        for (int to_c = 0; to_c < 8; to_c++) {
                            Position to = new Position(to_r, to_c);
                            if (piece.isValidMove(to, board.getGrid())) {
                                // Simulate the move on a temporary board
                                Board tempBoard = board.copy();
                                tempBoard.movePiece(piece.getPosition(), to);
                                // If the king is NOT in check after this move, we found a legal move.
                                if (!isKingInCheck(playerColor, tempBoard.getGrid())) {
                                    return true; // A legal move exists
                                }
                            }
                        }
                    }
                }
            }
        }
        return false; // No legal moves were found
    }

    /**
     * Determines if the current situation is checkmate.
     * @param kingColor The color of the king that might be in checkmate.
     * @param board The current Board object.
     * @return true if it is checkmate, false otherwise.
     */
    public static boolean isCheckmate(String kingColor, Board board) {
        // A player can only be in checkmate if they are currently in check.
        if (!isKingInCheck(kingColor, board.getGrid())) {
            return false;
        }
        // If they are in check, they are in checkmate if they have no legal moves.
        return !hasAnyLegalMoves(kingColor, board);
    }
}