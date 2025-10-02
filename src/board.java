//need 8x8 array or 2 int places as an abstract arrayy
package board;//
public class board{

  
  public void resetBoard()//taken from https://www.geeksforgeeks.org/dsa/design-a-chess-game/
    {
        // initialize white pieces
        boxes[0][0] = new Spot(0, 0, new rook(true)); //Supporting classes incomplete at this tie 10/1
        boxes[0][1] = new Spot(0, 1, new knight(true));
        boxes[0][2] = new Spot(0, 2, new bishop(true));
        //...
        boxes[1][0] = new Spot(1, 0, new pawn(true));
        boxes[1][1] = new Spot(1, 1, new pawn(true));
        //...

        // initialize black pieces
        boxes[7][0] = new Spot(7, 0, new rook(false));
        boxes[7][1] = new Spot(7, 1, new knight(false));
        boxes[7][2] = new Spot(7, 2, new bishop(false));
        //...
        boxes[6][0] = new Spot(6, 0, new pawn(false));
        boxes[6][1] = new Spot(6, 1, new pawn(false));
        //...

        // initialize remaining boxes without any piece
        for (int i = 2; i < 6; i++) {
            for (int j = 0; j < 8; j++) {
                boxes[i][j] = new Spot(i, j, null);
            }
        }
    }
}
//creates an instance of peices??
  //creates and intiial board
  //stores the state of board
  
  
}
