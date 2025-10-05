package Players;

/**
 * represent players in Chess game.
 */
public class Players{
    private String color; // white color and black color
    private List<piece> AvailablePiece; //available pieces
    //constructor
    public Players(String color, List<piece> pieces){
        this.color = color;
        this.pieces =pieces;
    }
}

public boolean makeMove(){
    
}