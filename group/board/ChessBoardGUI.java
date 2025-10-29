import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ChessBoardGUI{
	private static final int ROWS = 8;
	private static final int COLS = 8;
	private final JPanel[][] gameBoard = new JPanel[ROWS][COLS];
	private JPanel selectedPanel = null;
	private JLabel selectedLabel = null;

	
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
 

	public ChessBoardGUI() {
	
		//create frame
		JFrame frame = new JFram
