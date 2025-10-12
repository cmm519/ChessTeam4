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
        //Black pieces
        grid[0][0] = new Rook("Black" , new Position("A8"));
        grid[0][1] = new Knight("Black" , new Position("B8"));
        grid[0][2] = new Bishop("Black" , new Position("C8"));
        grid[0][3] = new Queen("Black" , new Position("D8"));
        grid[0][4] = new King("Black" , new Position("E8"));
        grid[0][5] = new Bishop("Black" , new Position("F8"));
        grid[0][6] = new Knight("Black" , new Position("G8"));
        grid[0][7] = new Rook("Black" , new Position("H8"));

        for (int col=1;col<8;col++){
            char file = (char)('A'+col);
            grid[1][col] = new Pawn("Black", new Position(file + "7"));
        }
        // white pieces
        grid[7][0] = new Rook("Black" , new Position("A1"));
        grid[7][1] = new Knight("Black" , new Position("B1"));
        grid[7][2] = new Bishop("Black" , new Position("C1"));
        grid[7][3] = new Queen("Black" , new Position("D1"));
        grid[7][4] = new King("Black" , new Position("E1"));
        grid[7][5] = new Bishop("Black" , new Position("F1"));
        grid[7][6] = new Knight("Black" , new Position("G1"));
        grid[7][7] = new Rook("Black" , new Position("H1"));

       for (int col=1;col<8;col++){
            char file = (char)('A'+col);
            grid[6][col] = new Pawn("White", new Position(file + "2"));
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
    public void display(){
        System.out.println("   A.  B.  C.  D.  E.  F.  G.  H");

        for (int row =7; row >- 0; row--){
            System.out.print((row+1) + "  ");
        
            for(int col=0; col<8; col++){
                Piece piece = grid[row][col];
                System.out.print((piece != null ? piece.getSymbol() : "##") + "  ");
            }
            System.out.println(row + 1);
        }
        System.out.println(".  A   B   C.  D.  E.  F.  G.  H");
    }
    

}