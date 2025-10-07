package Players;

/**
 * represent players in Chess game.
 */

import java.util.List;
import java.util.ArrayList;;
import java.util.Scanner;


public class Players{
    public boolean white;
    public boolean black;

    private String playerName;
    private List<Piece> pieces = new ArrayList();
    private List<Move> moves = new ArrayList();

    //constructor
    public Players(String name, boolean black){
        this.name = name;
        this.black = black;
	initiallizePieces();
    }
    //methods, setter and white is flase 
    public void setBlack(boolean black){
	this.black =  black;
    }
    
    //if checked is black, white is not	
    public void setChecked(){
	this.checked = checked;;
    }

    // getter to get nake of player
    public String getPlayerName(){
	this.playerName = playerName;
    }



		
}

/**
* assume player to enter a move and excute to the chessboard
*/

public void  makeMove(){
  








}
