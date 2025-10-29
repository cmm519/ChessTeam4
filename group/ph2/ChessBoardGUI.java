import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

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
		JFrame frame = new JFrame("ChessBoard GUI");
        frame.setSize(500,500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new GridLayout(ROWS, COLS));


		// create a MouseAdapter instance to handle all clicks
		MouseAdapter boardListener = new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e){
				JPanel clickPanel = (JPanel)e.getSource();
				handleMouseClick(clickPanel);
			}
		};



		for (int i = 0; i < ROWS; i++){
			for (int j = 0; j < COLS; j++) {
				JPanel panel = new JPanel(new BorderLayout());
				panel.setBackground((i+j) % 2 == 0 ? lightColor : darkColor);

				// add listener to each step
				panel.addMouseListener(boardListener);


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

	private void handleMouseClick(JPanel clickPanel){
		if(selectedPanel == null){
			if(clickPanel.getComponentCount() > 0){
				selectedPanel = clickPanel;
				selectedLabel = (JLabel) selectedPanel.getComponent(0);
				//highlight selection
				selectedPanel.setBorder(BorderFactory.createLineBorder(Color.GREEN,3)); 
			}
		}else{
				selectedPanel.remove(selectedLabel);
				selectedPanel.setBorder(null);

				
        		// Remove any piece in the destination panel
        		clickPanel.removeAll();

        		// Add the selected piece to the destination
       	 		clickPanel.add(selectedLabel);

				// Refresh both panels
				selectedPanel.revalidate();
				selectedPanel.repaint();
				clickPanel.revalidate();
				clickPanel.repaint();

				// Clear selection
				selectedPanel = null;
				selectedLabel = null;
		}
	}

	public static void main(String[] argc){
		SwingUtilities.invokeLater(ChessBoardGUI::new); 
    }
}