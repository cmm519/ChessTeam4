package pieces;

import utils.Position;

public abstract class Piece {
    protected String color;
    protected Position position;

    public Piece(String color, Position position) {
        this.color = color;
        this.position = position;
    }

    public abstract boolean isValidMove(Position to, Piece[][] board);

    public void move(Position to) {
        this.position = to;
    }

    public String getColor() {
        return color;
    }

    public Position getPosition() {
        return position;
    }
    public abstract String getSymbol();
}
