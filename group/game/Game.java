
package game;

import board.Board;
import java.util.Scanner;
import pieces.Piece;
import utils.MoveValidator;
import utils.Position;

public class Game {
    private Board board;
    private Player whitePlayer;
    private Player blackPlayer;
    private boolean whiteTurn;

    public Game() {
        board = new Board();
        whitePlayer = new Player("white");
        blackPlayer = new Player("black");
        whiteTurn = true;
    }

    public void start() {
        try(Scanner scanner = new Scanner(System.in)){
            while (true) {
                board.display();
                Player current = whiteTurn ? whitePlayer : blackPlayer;
                System.out.println(current.getColor() + "'s turn");

                System.out.print("Enter move (e.g., E2 E4): ");
                String[] input = scanner.nextLine().split(" ");

                if(input.length != 2){
                    System.out.println("Invalid input format: ");
                    continue;
                }

                Position from = new Position(input[0]);
                Position to = new Position(input[1]);

                Piece piece = board.getPiece(from);
                if (piece == null || !piece.getColor().equals(current.getColor())) {
                    System.out.println("Invalid piece selection.");
                    continue;
                }

                if (MoveValidator.isLegalMove(piece, to, board.getGrid())) {
                    board.movePiece(from, to);
                    whiteTurn = !whiteTurn;
                } else {
                    System.out.println("Illegal move.");
                }
                
            }
        }
    }
}