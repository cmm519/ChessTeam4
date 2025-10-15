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
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                board.display();
                Player current = whiteTurn ? whitePlayer : blackPlayer;
                String currentPlayerColor = current.getColor();
                
                // Check for checkmate or stalemate at the start of the turn
                if (MoveValidator.isCheckmate(currentPlayerColor, board)) {
                    System.out.println("Checkmate! " + (whiteTurn ? "Black" : "White") + " wins!");
                    break;
                }
                // (Optional: You could add a stalemate check here as well)

                System.out.println(currentPlayerColor + "'s turn");
                if (MoveValidator.isKingInCheck(currentPlayerColor, board.getGrid())) {
                    System.out.println("You are in check!");
                }

                System.out.print("Enter move (e.g., E2 E4): ");
                String[] input = scanner.nextLine().toUpperCase().split(" ");

                if (input.length != 2) {
                    System.out.println("Invalid input format.");
                    continue;
                }

                Position from = new Position(input[0]);
                Position to = new Position(input[1]);

                Piece piece = board.getPiece(from);
                if (piece == null || !piece.getColor().equals(currentPlayerColor)) {
                    System.out.println("Invalid piece selection.");//Dont know whats going on here
                    continue;
                }

                // First, check if the move is valid for the piece itself
                if (piece.isValidMove(to, board.getGrid())) {
                    // Then, simulate the move to see if it puts the current player in check
                    Board tempBoard = board.copy();
                    tempBoard.movePiece(from, to);

                    if (MoveValidator.isKingInCheck(currentPlayerColor, tempBoard.getGrid())) {//Most of the runtime is spent here
                        System.out.println("Illegal move: This move would leave your king in check.");
                    } else {
                        // If the move is fully legal, apply it to the main board
                        board.movePiece(from, to);
                        whiteTurn = !whiteTurn; // Switch turns
                    }
                } else {
                    System.out.println("Illegal move.");
                }
            }
        }
    }
}