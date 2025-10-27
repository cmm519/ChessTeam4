import javax.swing.*;
import java.awt.*;

public class ChessBoardGUI{
	private static final int ROWS = 8;
	private static final int COLS = 8;
	private final JPanel[][] gameBoard = new JPanel[ROWS][COLS];

	
	//custom lighter colors for the board
	Color lightColor = new Color(240,217,181);
	Color darkColor = new Color(181,136,99);
 
	//unicode for the chess pieces
	private static final String[][] initBoard = {
		{"\u2656","\u2658","\u2657","\u2655","\u2654","\u2657","\u2658","\u2656"},
		{"\u2659","\u2659","\u2659","\u2659","\u2659","\u2659","\u2659","\u2659"},
		{"","","","","","","",""},
		{"","","","","","","",""},
		{"","","","","","","",""},
		{"","","","","","","",""},
		{"\u265F","\u265F","\u265F","\u265F","\u265F","\u265F","\u265F","\u265F"},		
		{"\u265C","\u265E","\u265D","\u265B","\u265A","\u265D","\u265E","\u265C"}
	};
 //

	public ChessBoardGUI() {
	
		//create frame
		JFrame frame = new JFrame("ChessBoard GUI");
        frame.setSize(500,500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new GridLayout(ROWS, COLS));

		for (int i=0; i<ROWS; i++){
			for (int j=0;j<COLS;j++) {
				JPanel panel = new JPanel(new BorderLayout());
				panel.setBackground((i+j) % 2 == 0 ? lightColor : darkColor);
				String pieces = initBoard[i][j];
				if(!pieces.isEmpty()){
					JLabel label = new JLabel(pieces,SwingConstants.CENTER);
					label.setFont(new Font("Serif",Font.BOLD,32));
					panel.add(label);
				}
				frame.add(panel);
				gameBoard[i][j] = panel;	
			}
		}
		frame.setVisible(true);

	}
	public static void main(String[] argc){
		SwingUtilities.invokeLater(ChessBoardGUI::new); 
    }
}
