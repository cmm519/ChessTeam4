package board;

import pieces.*;
import utils.Position;

public class Board {
    private Piece[][] grid;

    public Board() {
        grid = new Piece[8][8];
        initializeBoard();
    }

    private void initializeBoard() {
        String black= "black";//just to avoid typos
        String white= "white";//loercase for consistency
        //white pieces
        grid[7][0] = new Rook(white , new Position("A8"));
        grid[7][1] = new Knight(white, new Position("B8"));
        grid[7][2] = new Bishop(white, new Position("C8"));
        grid[7][3] = new Queen(white , new Position("D8"));
        grid[7][4] = new King(white , new Position("E8"));
        grid[7][5] = new Bishop(white , new Position("F8"));
        grid[7][6] = new Knight(white , new Position("G8"));
        grid[7][7] = new Rook(white , new Position("H8"));

        for (int col=0;col<8;col++){
            char file = (char)('A'+col);
            grid[6][col] = new Pawn("white", new Position(file + "2"));
       }
        
        // white pieces
        grid[0][0] = new Rook(black, new Position("A1"));
        grid[0][1] = new Knight(black, new Position("B1"));
        grid[0][2] = new Bishop(black, new Position("C1"));
        grid[0][3] = new Queen(black, new Position("D1"));
        grid[0][4] = new King(black, new Position("E1"));
        grid[0][5] = new Bishop(black, new Position("F1"));
        grid[0][6] = new Knight(black, new Position("G1"));
        grid[0][7] = new Rook(black,new Position("H1"));

        for (int col=0;col<8;col++){
            char file = (char)('A'+col);
            grid[1][col] = new Pawn("black", new Position(file + "7"));
        }
       
    }

    // setters and getters
    public Piece getPiece(Position pos){
        return grid[pos.getRow()][pos.getCol()];
    }
    public void movePiece(Position from, Position to){
        Piece piece = getPiece(from);
            grid[to.getRow()][to.getCol()] = piece;
            grid[from.getRow()][from.getCol()] =null;
            piece.move(to);
        }
    
    public Piece[][] getGrid() {
        return grid;
    }

    // display
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